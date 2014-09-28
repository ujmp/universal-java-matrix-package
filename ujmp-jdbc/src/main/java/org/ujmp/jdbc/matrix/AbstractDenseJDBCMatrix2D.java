/*
 * Copyright (C) 2008-2014 by Holger Arndt
 *
 * This file is part of the Universal Java Matrix Package (UJMP).
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership and licensing.
 *
 * UJMP is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * UJMP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with UJMP; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

package org.ujmp.jdbc.matrix;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;

import org.ujmp.core.collections.map.SoftHashMap;
import org.ujmp.core.objectmatrix.DenseObjectMatrix2D;
import org.ujmp.core.objectmatrix.stub.AbstractDenseObjectMatrix2D;
import org.ujmp.jdbc.autoclose.AutoCloseConnection;

public abstract class AbstractDenseJDBCMatrix2D extends AbstractDenseObjectMatrix2D implements Closeable {
	private static final long serialVersionUID = -9077208839474846706L;

	private int chunkSize = 1000;

	private Map<Long, DenseObjectMatrix2D> dataCache = new SoftHashMap<Long, DenseObjectMatrix2D>();

	private String url = null;

	private String username = null;

	private String password = null;

	private String sqlStatement = null;

	private Connection connection = null;

	public AbstractDenseJDBCMatrix2D(String url, String sqlStatement, String username, String password) {
		super(0, 0);
		this.url = url;
		this.username = username;
		this.password = password;
		this.sqlStatement = sqlStatement;
	}

	public AbstractDenseJDBCMatrix2D(Connection connection, String sqlStatement) {
		super(0, 0);
		this.connection = connection;
		this.sqlStatement = sqlStatement;
	}

	public String getSQLStatement() {
		return sqlStatement;
	}

	public synchronized Object getObject(int row, int column) {
		return getObject((long) row, (long) column);
	}

	public synchronized Object getObject(long row, long column) {
		try {
			long chunkId = row / chunkSize;
			DenseObjectMatrix2D m = dataCache.get(chunkId);

			if (m == null) {
				String sql = sqlStatement + " LIMIT ?,?";
				PreparedStatement ps = getConnection().prepareStatement(sql);

				System.out.println(row + " - " + chunkId);

				long offset = chunkId * chunkSize;

				ps.setLong(1, offset);
				ps.setLong(2, chunkSize);

				ResultSet rs = ps.executeQuery();

				m = DenseObjectMatrix2D.Factory.zeros(chunkSize, getColumnCount());

				long r = 0;
				while (rs.next()) {
					for (int c = 0; c < getColumnCount(); c++) {
						try {
							m.setObject(rs.getObject(c + 1), r, c);
						} catch (SQLException e) {
							if ("S1009".equals(e.getSQLState())) {
								// ignore Value '0000-00-00' can not be
								// represented
							} else {
								throw e;
							}

						}
					}
					r++;
				}

				dataCache.put(chunkId, m);

				rs.close();
				ps.close();
			}

			return m.getObject(row % chunkSize, column);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public final String getSelectString() {
		return sqlStatement;
	}

	public synchronized void setObject(Object value, long row, long column) {
	}

	public synchronized void setObject(Object value, int row, int column) {
	}

	public synchronized long[] getSize() {
		try {
			if (getRowCount() == 0) {
				long rowCount = 0;
				PreparedStatement ps = getConnection()
						.prepareStatement("SELECT COUNT(1) FROM (" + sqlStatement + ") t");
				ResultSet rsRows = ps.executeQuery();
				if (rsRows.next()) {
					rowCount = rsRows.getLong(1);
				}
				rsRows.close();
				ps.close();

				long columnCount = 0;
				ps = getConnection().prepareStatement("SELECT * FROM (" + sqlStatement + ") t LIMIT 1");
				ResultSet rsCols = ps.executeQuery();
				if (rsCols.next()) {
					columnCount = rsCols.getMetaData().getColumnCount();
				}
				if (getLabelObject() == null) {
					setLabel(getUrl() + " " + getSelectString());
					ResultSetMetaData rsm = rsCols.getMetaData();
					for (int c = 0; c < rsm.getColumnCount(); c++) {
						setColumnLabel(c, rsm.getColumnLabel(c + 1));
					}
				}
				rsCols.close();
				ps.close();
				size[ROW] = rowCount;
				size[COLUMN] = columnCount;
			}
			return size;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public synchronized void close() throws IOException {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			throw new IOException(e.toString());
		}
	}

	public synchronized Connection getConnection() throws SQLException {
		if (connection == null) {
			connection = new AutoCloseConnection(DriverManager.getConnection(getUrl(), getUsername(), getPassword()));
		}
		return connection;
	}

	public String getUrl() {
		return url;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

}