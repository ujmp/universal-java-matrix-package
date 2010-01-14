/*
 * Copyright (C) 2008-2010 by Holger Arndt
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

package org.ujmp.jdbc;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.objectmatrix.stub.AbstractSparseObjectMatrix;

public class JDBCSparseObjectMatrix extends AbstractSparseObjectMatrix
		implements Closeable {
	private static final long serialVersionUID = -5801269687893136766L;

	private Connection connection = null;

	private PreparedStatement getEntryStatement = null;

	private PreparedStatement insertEntryStatement = null;

	private PreparedStatement deleteEntryStatement = null;

	private PreparedStatement getSizeStatement = null;

	private String url = null;

	private String username = null;

	private String password = null;

	private String tableName = null;

	private String[] columnsForCoordinates = null;

	private String columnForValue = null;

	private long[] size = null;

	public JDBCSparseObjectMatrix(String url, String username, String password,
			String tableName, String columnForValue,
			String... columnsForCoordinates) throws ClassNotFoundException {
		this.url = url;
		this.username = username;
		this.password = password;
		this.tableName = tableName;
		this.columnForValue = columnForValue;
		this.columnsForCoordinates = columnsForCoordinates;

		// if (url.startsWith("jdbc:mysql://")) {
		// Class.forName("com.mysql.jdbc.Driver");
		// }else if (url.startsWith("jdbc:derby://")) {
		// Class.forName("com.mysql.jdbc.Driver");
		// }
	}

	private PreparedStatement getGetEntryStatement() {
		try {
			if (getEntryStatement == null) {
				StringBuilder s = new StringBuilder();
				s.append("select ");
				s.append(columnForValue);
				s.append(" from ");
				s.append(tableName);
				s.append(" where ");
				for (int i = 0; i < columnsForCoordinates.length; i++) {
					s.append(columnsForCoordinates[i]);
					s.append("=?");
					if (i < columnsForCoordinates.length - 1) {
						s.append(" and ");
					}
				}
				getEntryStatement = getConnection().prepareStatement(
						s.toString());
			}
			return getEntryStatement;
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

	private PreparedStatement getInsertEntryStatement() {
		try {
			if (insertEntryStatement == null) {
				StringBuilder s = new StringBuilder();
				s.append("insert into ");
				s.append(tableName);
				s.append(" (");
				s.append(columnForValue);
				s.append(", ");
				for (int i = 0; i < columnsForCoordinates.length; i++) {
					s.append(columnsForCoordinates[i]);
					if (i < columnsForCoordinates.length - 1) {
						s.append(", ");
					}
				}
				s.append(") values (?, ");
				for (int i = 0; i < columnsForCoordinates.length; i++) {
					s.append("?");
					if (i < columnsForCoordinates.length - 1) {
						s.append(", ");
					}
				}
				s.append(") on duplicate key update ");
				s.append(columnForValue);
				s.append("=?");
				insertEntryStatement = getConnection().prepareStatement(
						s.toString());
			}
			return insertEntryStatement;
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

	private PreparedStatement getGetSizeStatement() {
		try {
			if (getSizeStatement == null) {
				StringBuilder s = new StringBuilder();
				s.append("select ");
				for (int i = 0; i < columnsForCoordinates.length; i++) {
					s.append("max(" + columnsForCoordinates[i] + ")");
					if (i < columnsForCoordinates.length - 1) {
						s.append(", ");
					}
				}
				s.append(" from ");
				s.append(tableName);
				getSizeStatement = getConnection().prepareStatement(
						s.toString());
			}
			return getSizeStatement;
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

	public synchronized Object getObject(long... coordinates) {
		try {
			PreparedStatement ps = getGetEntryStatement();
			for (int i = 0; i < coordinates.length; i++) {
				ps.setLong(i + 1, coordinates[i]);
			}
			ResultSet rs = ps.executeQuery();
			Object o = null;
			if (rs.next()) {
				o = rs.getObject(1);
			}
			rs.close();
			return o;
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

	public synchronized void setObject(Object value, long... coordinates) {
		try {
			PreparedStatement ps = getInsertEntryStatement();
			ps.setObject(1, value);
			ps.setObject(coordinates.length + 2, value);
			for (int i = 0; i < coordinates.length; i++) {
				ps.setLong(i + 2, coordinates[i]);
			}
			ps.executeUpdate();
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

	public synchronized long[] getSize() {
		try {
			if (size == null) {
				PreparedStatement ps = getGetSizeStatement();
				ResultSet rs = ps.executeQuery();
				rs.next();
				ResultSetMetaData rsMetaData = rs.getMetaData();
				int s = rsMetaData.getColumnCount();
				size = new long[s];
				for (int i = 0; i < s; i++) {
					size[i] = rs.getLong(i + 1) + 1;
				}
				ps.close();
				rs.close();
			}
			return size;
		} catch (SQLException e) {
			throw new MatrixException(e);
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
		if (connection == null || !connection.isClosed()) {
			connection = DriverManager.getConnection(getUrl(), getUsername(),
					getPassword());
			// DatabaseMetaData dbm = connection.getMetaData();
			// dbm = null;
			// ResultSet rs = dbm.getTables(null, null, "%", null);

			// rs = meta.getPrimaryKeys(null, null, "table");
			// while (rs.next()) {
			// String columnName = rs.getString("COLUMN_NAME");
			// System.out
			// .println("getPrimaryKeys(): columnName=" + columnName);
			// }
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

	public boolean contains(long... coordinates) throws MatrixException {
		return getObject(coordinates) != null;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		if (connection != null) {
			if (!connection.isClosed()) {
				connection.close();
			}
			connection = null;
		}
	}

}
