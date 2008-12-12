/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
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
import java.util.HashMap;
import java.util.Map;

import org.ujmp.core.collections.SoftHashMap;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.objectmatrix.AbstractDenseMatrix2D;

public abstract class AbstractDenseJDBCMatrix2D extends AbstractDenseMatrix2D implements Closeable {

	private final Map<Integer, Connection> connections = new HashMap<Integer, Connection>();

	private String url = null;

	private String username = "sa";

	private String password = "";

	private final Map<Integer, PreparedStatement> selectStatements = new HashMap<Integer, PreparedStatement>();

	private final Map<Integer, ResultSet> resultSets = new SoftHashMap<Integer, ResultSet>();

	private String sqlStatement = null;

	private long[] size = null;

	private final int resultSize = Integer.MAX_VALUE;

	private final int connectionCount = 1;

	private int statementId = 0;

	public AbstractDenseJDBCMatrix2D(String url, String sqlStatement, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
		this.sqlStatement = sqlStatement;
	}

	public String getSQLStatement() {
		return sqlStatement;
	}

	@Override
	public synchronized Object getObject(long row, long column) {
		try {
			ResultSet rs = getResultSet(row);
			return rs.getObject((int) column + 1);
		} catch (SQLException e) {
			if ("S1009".equals(e.getSQLState())) {
				// ignore Value '0000-00-00' can not be represented
				return null;
			}
			throw new MatrixException(e);
		}
	}

	public final String getSelectString() {
		return sqlStatement;
	}

	@Override
	public synchronized void setObject(Object value, long row, long column) {
	}

	@Override
	public synchronized long[] getSize() {
		try {
			if (size == null) {
				ResultSet rs = getResultSet(1);
				ResultSetMetaData rsMetaData = rs.getMetaData();
				long columnCount = rsMetaData.getColumnCount();
				rs.last();
				long rowCount = rs.getRow();
				size = new long[] { rowCount, columnCount };
			}
			return size;
		} catch (SQLException e) {
			throw new MatrixException(e);
		}
	}

	@Override
	public synchronized void close() throws IOException {
		try {
			for (Connection connection : connections.values()) {
				if (connection != null) {
					connection.close();
				}
			}
		} catch (SQLException e) {
			throw new IOException(e);
		}
	}

	public synchronized ResultSet getResultSet(long row) throws SQLException {
		int pos = (int) row / resultSize;
		int offset = pos * resultSize;
		int remain = (int) row - offset;
		ResultSet resultSet = resultSets.get(pos);
		if (resultSet == null || resultSet.isClosed()) {
			PreparedStatement ps = getSelectStatement();
			// ps.setInt(1, resultSize);
			// ps.setInt(2, offset);
			resultSet = ps.executeQuery();
			resultSets.put(pos, resultSet);
			if (getMatrixAnnotation() == null) {
				setMatrixAnnotation(getUrl() + " " + getSelectString());
				ResultSetMetaData rsm = resultSet.getMetaData();
				for (int c = 0; c < rsm.getColumnCount(); c++) {
					setColumnLabel(c, rsm.getColumnLabel(c + 1));
				}
			}
		}
		resultSet.absolute(remain + 1);
		// resultSet.next();
		return resultSet;
	}

	public synchronized PreparedStatement getSelectStatement() throws SQLException {
		PreparedStatement selectStatement = selectStatements.get(statementId);
		if (selectStatement == null) {
			selectStatement = getConnection(statementId).prepareStatement(getSelectString(),
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		}
		statementId = ++statementId > connectionCount ? 0 : statementId;
		return selectStatement;
	}

	public synchronized Connection getConnection(int id) throws SQLException {
		Connection connection = connections.get(id);
		if (connection == null) {
			connection = DriverManager.getConnection(getUrl(), getUsername(), getPassword());
			// DatabaseMetaData dbm = connection.getMetaData();
			// dbm = null;
			// ResultSet rs = dbm.getTables(null, null, "%", null);
			connections.put(id, connection);
		}
		id = ++id >= connectionCount ? 0 : id;
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
