/*
 * Copyright (C) 2008-2013 by Holger Arndt
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
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class JDBCComplexObjectMap extends AbstractJDBCMap<Map<String, Object>> implements Closeable {
	private static final long serialVersionUID = 6908668830207789243L;

	protected JDBCComplexObjectMap(String url, String tableName, String username, String password, String keyColumnName)
			throws SQLException {
		super(url, tableName, username, password, keyColumnName, null);
	}

	@Override
	public synchronized Map<String, Object> get(Object key) {
		if (key == null) {
			throw new RuntimeException("key cannot be null");
		}
		if (String.valueOf(key).length() > maxKeyLength) {
			throw new RuntimeException("key cannot be longer than " + maxKeyLength);
		}
		try {
			if (selectStatement == null || selectStatement.isClosed()) {
				String sql = null;
				if (url.startsWith("jdbc:mysql")) {
					sql = "SELECT * FROM `" + tableName + "` WHERE `" + keyColumn + "` = ? LIMIT 1";
				} else {
					sql = "SELECT * FROM " + tableName + " WHERE " + keyColumn + " = ? LIMIT 1";
				}
				selectStatement = getConnection().prepareStatement(sql);
			}
			selectStatement.setString(1, String.valueOf(key));
			ResultSet rs = selectStatement.executeQuery();
			ResultSetMetaData meta = rs.getMetaData();
			int columnCount = meta.getColumnCount();
			Map<String, Object> data = null;
			if (rs.next()) {
				data = new HashMap<String, Object>();
				for (int i = 1; i <= columnCount; i++) {
					data.put(meta.getColumnName(i), rs.getObject(i));
				}
			}
			rs.close();
			return data;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Map<String, Object> put(String key, Map<String, Object> value) {

		return null;
	}

	protected void initDatabase() throws SQLException {
		// check if table already exists and create if necessary
		DatabaseMetaData dbm = getConnection().getMetaData();
		ResultSet tables = dbm.getTables(null, null, this.tableName, null);
		if (!tables.next()) {
			tables.close();

			if (this.keyColumn == null) {
				this.keyColumn = "id";
			}
			if (this.valueColumn == null) {
				this.valueColumn = "data";
			}

			StringBuilder sql = new StringBuilder();

			if (url.startsWith("jdbc:mysql")) {
				sql.append("CREATE TABLE `" + this.tableName + "`");
				sql.append(" (");
				sql.append("`" + this.keyColumn + "` VARCHAR(255) ");
				sql.append("COLLATE utf8_bin NOT NULL, ");
				sql.append("`" + this.valueColumn + "` LONGTEXT ");
				sql.append("COLLATE utf8_bin NOT NULL, ");
				sql.append("PRIMARY KEY (`" + this.keyColumn + "`)");
				sql.append(") ");
				sql.append("DEFAULT CHARSET=utf8 COLLATE=utf8_bin");
			} else if (url.startsWith("jdbc:hsqldb")) {
				sql.append("CREATE TABLE " + this.tableName + "");
				sql.append(" (");
				sql.append("" + this.keyColumn + " VARCHAR(255) PRIMARY KEY, ");
				sql.append("" + this.valueColumn + " LONGVARCHAR ");
				sql.append(") ");
			} else {
				sql.append("CREATE TABLE " + this.tableName + "");
				sql.append(" (");
				sql.append("" + this.keyColumn + " VARCHAR(255) PRIMARY KEY, ");
				sql.append("" + this.valueColumn + " TEXT ");
				sql.append(") ");
			}
			PreparedStatement statement;
			statement = getConnection().prepareStatement(sql.toString());
			statement.execute();
			statement.close();
		} else {
			tables.close();

			// if no key column is defined, assume that it is the primary key
			// column
			if (this.keyColumn == null) {
				ResultSet rs = dbm.getPrimaryKeys(null, null, this.tableName);
				if (rs.next()) {
					this.keyColumn = rs.getString("COLUMN_NAME");
				} else {
					throw new RuntimeException("table must have a primary key");
				}
				if (rs.next()) {
					throw new RuntimeException("table must not have more than one primary key");
				}
				rs.close();
			}
		}

	}

	public static JDBCComplexObjectMap connectToMySQL(String serverName, int port, String databaseName,
			String tableName, String username, String password, String columnForKeys) throws SQLException {
		return new JDBCComplexObjectMap("jdbc:mysql://" + serverName + ":" + port + "/" + databaseName
				+ "?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull", tableName, username,
				password, columnForKeys);
	}

	public static JDBCComplexObjectMap connectToMySQL(String serverName, int port, String databaseName,
			String tableName, String userName, String password) throws SQLException {
		return new JDBCComplexObjectMap("jdbc:mysql://" + serverName + ":" + port + "/" + databaseName
				+ "?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull", tableName, userName,
				password, null);
	}

}
