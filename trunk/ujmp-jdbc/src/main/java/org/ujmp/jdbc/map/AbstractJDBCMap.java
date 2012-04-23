/*
 * Copyright (C) 2008-2012 by Holger Arndt
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

package org.ujmp.jdbc.map;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.ujmp.core.collections.map.AbstractMap;
import org.ujmp.core.interfaces.Erasable;

public abstract class AbstractJDBCMap<V> extends AbstractMap<String, V> implements Closeable, Erasable {
	private static final long serialVersionUID = -2850934349684973487L;

	protected String url = null;
	protected String username = null;
	protected String password = null;
	protected String keyColumn = null;
	protected String valueColumn = null;
	protected String tableName = null;

	protected final int maxKeyLength = 255;

	private transient Connection connection = null;

	private transient PreparedStatement truncateTableStatement = null;
	protected transient PreparedStatement insertStatement = null;
	protected transient PreparedStatement updateStatement = null;
	private transient PreparedStatement deleteStatement = null;
	protected transient PreparedStatement selectStatement = null;
	private transient PreparedStatement countStatement = null;
	private transient PreparedStatement keyStatement = null;

	protected AbstractJDBCMap(String url, String tableName, String username, String password, String keyColumnName,
			String valueColumnName) throws SQLException {
		this.url = url;
		this.username = username;
		this.password = password;
		this.tableName = tableName;
		this.keyColumn = keyColumnName;
		this.valueColumn = valueColumnName;

		// if no table name is defined, create one
		if (this.tableName == null) {
			this.tableName = "temp_map_" + System.currentTimeMillis();
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
	public final synchronized Set<String> keySet() {
		Set<String> keys = new HashSet<String>();
		try {
			if (keyStatement == null || keyStatement.isClosed()) {
				String sql = null;
				if (url.startsWith("jdbc:mysql")) {
					sql = "SELECT `" + keyColumn + "` FROM `" + tableName + "`";
				} else {
					sql = "SELECT " + keyColumn + " FROM " + tableName;
				}
				keyStatement = getConnection().prepareStatement(sql);
			}
			ResultSet rs = keyStatement.executeQuery();
			while (rs.next()) {
				keys.add(rs.getString(1));
			}
			rs.close();
			return keys;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public final synchronized V remove(Object key) {
		if (key == null) {
			throw new RuntimeException("key cannot be null");
		}
		try {
			V oldValue = get(key);
			if (oldValue != null) {
				if (deleteStatement == null || deleteStatement.isClosed()) {
					String sql = null;
					if (url.startsWith("jdbc:mysql")) {
						sql = "DELETE FROM `" + tableName + "` WHERE `" + keyColumn + "` = ?";
					} else {
						sql = "DELETE FROM " + tableName + " WHERE " + keyColumn + " = ?";
					}
					deleteStatement = getConnection().prepareStatement(sql);
				}
				deleteStatement.setString(1, String.valueOf(key));
				deleteStatement.executeUpdate();
			}
			return oldValue;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public final synchronized int size() {
		try {
			if (countStatement == null || countStatement.isClosed()) {
				String sql = null;
				if (url.startsWith("jdbc:mysql")) {
					sql = "SELECT COUNT(`" + keyColumn + "`) FROM `" + tableName + "`";
				} else {
					sql = "SELECT COUNT(" + keyColumn + ") FROM " + tableName;
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
