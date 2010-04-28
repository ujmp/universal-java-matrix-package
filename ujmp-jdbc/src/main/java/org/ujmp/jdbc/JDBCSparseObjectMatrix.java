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
import java.sql.SQLException;

import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.Erasable;
import org.ujmp.core.objectmatrix.stub.AbstractSparseObjectMatrix;
import org.ujmp.core.util.MathUtil;

public class JDBCSparseObjectMatrix extends AbstractSparseObjectMatrix
		implements Closeable, Erasable {
	private static final long serialVersionUID = -5801269687893136766L;

	private boolean useExtendedSQL = false;

	private transient Connection connection = null;

	private transient PreparedStatement getEntryStatement = null;

	private transient PreparedStatement insertEntryStatement = null;

	private transient PreparedStatement deleteEntryStatement = null;

	private transient PreparedStatement selectAllStatement = null;

	private final String url;

	private final String username;

	private String password = null;

	private final String tableName;

	private final String[] columnsForCoordinates;

	private final String columnForValue;

	private final long[] size;

	public JDBCSparseObjectMatrix(long[] size, String url, String username,
			String password, String tableName, String columnForValue,
			String... columnsForCoordinates) throws ClassNotFoundException {
		this.url = url;
		this.size = size;
		this.username = username;
		this.password = password;
		this.tableName = tableName;
		this.columnForValue = columnForValue;
		this.columnsForCoordinates = columnsForCoordinates;

		if (url.startsWith("jdbc:hsqldb:")) {
			Class.forName("org.hsqldb.jdbcDriver");
		} else if (url.startsWith("jdbc:mysql:")) {
			Class.forName("org.mysql.jdbc.Driver");
		} else if (url.startsWith("jdbc:postgresql:")) {
			Class.forName("org.postgresql.Driver");
		} else if (url.startsWith("jdbc:derby:")) {
			Class.forName("org.apache.derby.jdbc.Driver40");
		}

		createTableIfNotExists();
	}

	public JDBCSparseObjectMatrix(long[] size, Connection connection,
			String tableName, String columnForValue,
			String... columnsForCoordinates) throws SQLException {
		this.size = size;
		this.connection = connection;
		this.username = connection.getMetaData().getUserName();
		this.url = connection.getMetaData().getURL();
		this.tableName = tableName;
		this.columnForValue = columnForValue;
		this.columnsForCoordinates = columnsForCoordinates;
		createTableIfNotExists();
	}

	private void createTableIfNotExists() {
		// TODO Auto-generated method stub

	}

	private PreparedStatement getSelectAllStatement() throws SQLException {
		if (selectAllStatement == null) {
			StringBuilder s = new StringBuilder();
			s.append("select ");
			s.append(columnForValue);
			s.append(", ");
			for (int i = 0; i < columnsForCoordinates.length; i++) {
				s.append(columnsForCoordinates[i]);
				if (i < columnsForCoordinates.length - 1) {
					s.append(", ");
				}
			}
			s.append(" from ");
			s.append(tableName);
			selectAllStatement = getConnection().prepareStatement(s.toString());
		}
		return selectAllStatement;
	}

	private PreparedStatement getGetEntryStatement() throws SQLException {
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
			getEntryStatement = getConnection().prepareStatement(s.toString());
		}
		return getEntryStatement;
	}

	private PreparedStatement getInsertEntryStatement() throws SQLException {
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
			s.append(")");

			if (useExtendedSQL) {
				s.append(" on duplicate key update ");
				s.append(columnForValue);
				s.append("=?");
			}

			insertEntryStatement = getConnection().prepareStatement(
					s.toString());
		}
		return insertEntryStatement;
	}

	private PreparedStatement getDeleteEntryStatement() throws SQLException {
		if (deleteEntryStatement == null) {
			StringBuilder s = new StringBuilder();
			s.append("delete from ");
			s.append(tableName);
			s.append(" where ");
			for (int i = 0; i < columnsForCoordinates.length; i++) {
				s.append(columnsForCoordinates[i]);
				s.append("=?");
				if (i < columnsForCoordinates.length - 1) {
					s.append(" and ");
				}
			}
			deleteEntryStatement = getConnection().prepareStatement(
					s.toString());
		}
		return deleteEntryStatement;
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

	private void deleteObject(long... coordinates) throws SQLException {
		PreparedStatement ps = getDeleteEntryStatement();
		for (int i = 0; i < coordinates.length; i++) {
			ps.setLong(i + 1, coordinates[i]);
		}
		ps.execute();
	}

	public synchronized void setObject(Object value, long... coordinates) {
		try {
			if (MathUtil.getDouble(value) == 0.0) {
				deleteObject(coordinates);
			} else {

				if (!useExtendedSQL) {
					deleteObject(coordinates);
				}

				PreparedStatement ps = getInsertEntryStatement();

				ps.setObject(1, value);
				for (int i = 0; i < coordinates.length; i++) {
					ps.setLong(i + 2, coordinates[i]);
				}

				if (useExtendedSQL) {
					ps.setObject(coordinates.length + 2, value);
				}

				ps.executeUpdate();
			}
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

	public synchronized long[] getSize() {
		return size;
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
		if (connection == null || connection.isClosed()) {
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

	public void erase() throws IOException {
		// TODO
		// delete database table
	}

}
