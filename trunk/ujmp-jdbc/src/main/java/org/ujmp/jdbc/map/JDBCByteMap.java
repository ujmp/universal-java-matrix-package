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

import java.io.File;
import java.io.IOException;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class JDBCByteMap extends AbstractJDBCMap<byte[]> {
	private static final long serialVersionUID = 4744307432617930795L;

	public static JDBCByteMap connectToMySQL(String serverName, int port, String databaseName, String tableName,
			String username, String password, String columnForKeys, String columnForValues) throws SQLException {
		return new JDBCByteMap("jdbc:mysql://" + serverName + ":" + port + "/" + databaseName
				+ "?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull", tableName, username,
				password, columnForKeys, columnForValues);
	}

	public static JDBCByteMap connectToMySQL(String serverName, int port, String databaseName, String tableName,
			String userName, String password) throws SQLException {
		return new JDBCByteMap("jdbc:mysql://" + serverName + ":" + port + "/" + databaseName
				+ "?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull", tableName, userName,
				password, null, null);
	}

	public static JDBCByteMap connectToHSQLDB() throws SQLException, IOException {
		return new JDBCByteMap("jdbc:hsqldb:file:/" + File.createTempFile("hsqldb", ".temp"), null, "SA", "", null,
				null);
	}

	public static JDBCByteMap connectToHSQLDB(File fileName) throws SQLException {
		return new JDBCByteMap("jdbc:hsqldb:file:/" + fileName.getAbsolutePath(), null, "SA", "", null, null);
	}

	public static JDBCByteMap connectToHSQLDB(File fileName, String tableName) throws SQLException {
		return new JDBCByteMap("jdbc:hsqldb:file:/" + fileName.getAbsolutePath(), tableName, "SA", "", null, null);
	}

	public static JDBCByteMap connectToHSQLDB(File fileName, String tableName, String userName, String password)
			throws SQLException {
		return new JDBCByteMap("jdbc:hsqldb:file:/" + fileName.getAbsolutePath(), tableName, userName, password, null,
				null);
	}

	public static JDBCByteMap connect(String url, String tableName, String userName, String password,
			String keyColumnName, String valueColumnName) throws SQLException {
		return new JDBCByteMap(url, tableName, userName, password, keyColumnName, valueColumnName);
	}

	public static JDBCByteMap connect(String url, String tableName, String userName, String password)
			throws SQLException {
		return new JDBCByteMap(url, tableName, userName, password, null, null);
	}

	private JDBCByteMap(String url, String tableName, String username, String password, String keyColumnName,
			String valueColumnName) throws SQLException {
		super(url, tableName, username, password, keyColumnName, valueColumnName);
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
				sql.append("`" + this.valueColumn + "` LONGBLOB ");
				sql.append("NOT NULL, ");
				sql.append("PRIMARY KEY (`" + this.keyColumn + "`)");
				sql.append(") ");
				sql.append("DEFAULT CHARSET=utf8 COLLATE=utf8_bin");
			} else if (url.startsWith("jdbc:hsqldb")) {
				sql.append("CREATE TABLE " + this.tableName + "");
				sql.append(" (");
				sql.append("" + this.keyColumn + " VARCHAR(255) PRIMARY KEY, ");
				sql.append("" + this.valueColumn + " LONGVARBINARY ");
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

			// figure out key and value column

			// this only works for two columns
			List<String> colunmNames = new LinkedList<String>();
			String sql = null;
			if (url.startsWith("jdbc:mysql")) {
				sql = "SELECT * FROM `" + this.tableName + "` LIMIT 1";
			} else {
				sql = "SELECT * FROM " + this.tableName + " LIMIT 1";
			}
			PreparedStatement ps = getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.getMetaData().getColumnCount() != 2) {
				throw new RuntimeException("table must have two columns");
			} else {
				colunmNames.add(rs.getMetaData().getColumnName(1));
				colunmNames.add(rs.getMetaData().getColumnName(2));
			}
			rs.close();
			ps.close();

			// if no key column is defined, assume that it is the primary key
			// column
			if (this.keyColumn == null) {
				rs = dbm.getPrimaryKeys(null, null, this.tableName);
				if (rs.next()) {
					this.keyColumn = rs.getString("COLUMN_NAME");
					colunmNames.remove(this.keyColumn);
				} else {
					throw new RuntimeException("table must have a primary key");
				}
				if (rs.next()) {
					throw new RuntimeException("table must not have more than one primary key");
				}
				rs.close();
			}

			// if no value column is defined, assume that it is the one which is
			// not the primary key
			if (this.valueColumn == null) {
				this.valueColumn = colunmNames.get(0);
			}
		}
	}

	@Override
	public synchronized byte[] put(String key, byte[] value) {
		if (key == null) {
			throw new RuntimeException("key cannot be null");
		}
		try {
			byte[] oldValue = get(key);
			if (oldValue == null) {
				if (insertStatement == null || insertStatement.isClosed()) {
					String sql = null;
					if (url.startsWith("jdbc:mysql")) {
						sql = "INSERT INTO `" + tableName + "` (`" + keyColumn + "`, `" + valueColumn
								+ "`) VALUES (?,?)";
					} else {
						sql = "INSERT INTO " + tableName + " (" + keyColumn + ", " + valueColumn + ") VALUES (?,?)";
					}
					insertStatement = getConnection().prepareStatement(sql);
				}
				insertStatement.setString(1, String.valueOf(key));
				insertStatement.setBytes(2, (byte[]) value);
				insertStatement.executeUpdate();
			} else if (!Arrays.equals(oldValue, value)) {
				if (updateStatement == null || updateStatement.isClosed()) {
					String sql = null;
					if (url.startsWith("jdbc:mysql")) {
						sql = "UPDATE `" + tableName + "` SET `" + valueColumn + "`=? WHERE `" + keyColumn + "`=?";
					} else {
						sql = "UPDATE " + tableName + " SET " + valueColumn + "=? WHERE " + keyColumn + "=?";
					}
					updateStatement = getConnection().prepareStatement(sql);
				}
				updateStatement.setBytes(1, (byte[]) value);
				updateStatement.setString(2, String.valueOf(key));
				updateStatement.executeUpdate();
			}
			return oldValue;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public synchronized byte[] get(Object key) {
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
					sql = "SELECT `" + valueColumn + "` FROM `" + tableName + "` WHERE `" + keyColumn + "` = ? LIMIT 1";
				} else {
					sql = "SELECT " + valueColumn + " FROM " + tableName + " WHERE " + keyColumn + " = ? LIMIT 1";
				}
				selectStatement = getConnection().prepareStatement(sql);
			}
			selectStatement.setString(1, String.valueOf(key));
			ResultSet rs = selectStatement.executeQuery();
			byte[] data = null;
			if (rs.next()) {
				data = rs.getBytes(1);
			}
			rs.close();
			return data;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
