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

package org.ujmp.jdbc.set;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.ujmp.core.collections.set.AbstractSet;
import org.ujmp.core.interfaces.Erasable;

public abstract class AbstractJDBCSet<V> extends AbstractSet<V> implements Closeable, Erasable {
	private static final long serialVersionUID = 4186997036615287357L;

	protected final String url;
	protected final String username;
	protected final String password;
	protected String columnName = null;
	protected String tableName = null;

	protected final int maxKeyLength = 255;

	private transient Connection connection = null;

	private transient PreparedStatement truncateTableStatement = null;
	protected transient PreparedStatement insertStatement = null;
	protected transient PreparedStatement updateStatement = null;
	protected transient PreparedStatement deleteStatement = null;
	protected transient PreparedStatement selectStatement = null;
	protected transient PreparedStatement selectAllStatement = null;
	private transient PreparedStatement countStatement = null;

	protected AbstractJDBCSet(String url, String tableName, String username, String password, String columnName)
			throws SQLException {
		this.url = url;
		this.username = username;
		this.password = password;
		this.tableName = tableName;
		this.columnName = columnName;

		// if no table name is defined, create one
		if (this.tableName == null) {
			this.tableName = "temp_set_" + System.currentTimeMillis();
		}

		initDatabase();
	}

	protected abstract void initDatabase() throws SQLException;

	@Override
	public final synchronized void clear() {
		try {
			if (truncateTableStatement == null || truncateTableStatement.isClosed()) {
				String sql = null;
				if (url.startsWith("jdbc:mysql")) {
					sql = "TRUNCATE TABLE `" + tableName + "`";
				} else {
					sql = "TRUNCATE TABLE " + tableName;
				}
				truncateTableStatement = getConnection().prepareStatement(sql);
			}
			truncateTableStatement.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	protected synchronized Connection getConnection() throws SQLException {
		if (connection == null || connection.isClosed()) {
			connection = DriverManager.getConnection(url, username, password);
		}
		return connection;
	}

	@Override
	public final synchronized int size() {
		try {
			if (countStatement == null || countStatement.isClosed()) {
				String sql = null;
				if (url.startsWith("jdbc:mysql")) {
					sql = "SELECT COUNT(`" + columnName + "`) FROM `" + tableName + "`";
				} else {
					sql = "SELECT COUNT(" + columnName + ") FROM " + tableName;
				}
				countStatement = getConnection().prepareStatement(sql);
			}
			ResultSet rs = countStatement.executeQuery();
			int size = -1;
			if (rs.next()) {
				size = rs.getInt(1);
			} else {
				throw new RuntimeException("cannot count entries in map");
			}
			rs.close();
			return size;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public final synchronized void close() throws IOException {
		try {
			if (connection == null && !connection.isClosed()) {
				connection.close();
				connection = null;
			}
		} catch (SQLException e) {
			throw new IOException(e);
		}
	}

	public final synchronized void erase() throws IOException {
		try {
			String sql = null;
			if (url.startsWith("jdbc:mysql")) {
				sql = "DROP TABLE `" + tableName + "`";
			} else {
				sql = "DROP TABLE " + tableName;
			}
			PreparedStatement remove = getConnection().prepareStatement(sql);
			remove.executeUpdate();
			remove.close();
		} catch (SQLException e) {
			throw new IOException(e);
		}
	}

}
