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

import java.io.File;
import java.io.IOException;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class JDBCStringSet extends AbstractJDBCSet<String> {
	private static final long serialVersionUID = -8233006198283847196L;

	private JDBCStringSet(String url, String tableName, String username, String password, String columnName)
			throws SQLException {
		super(url, tableName, username, password, columnName);
	}

	public static JDBCStringSet connectToMySQL(String serverName, int port, String databaseName, String tableName,
			String username, String password, String columnName) throws SQLException {
		return new JDBCStringSet("jdbc:mysql://" + serverName + ":" + port + "/" + databaseName
				+ "?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull", tableName, username,
				password, columnName);
	}

	public static JDBCStringSet connectToMySQL(String serverName, int port, String databaseName, String tableName,
			String userName, String password) throws SQLException {
		return new JDBCStringSet("jdbc:mysql://" + serverName + ":" + port + "/" + databaseName
				+ "?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull", tableName, userName,
				password, null);
	}

	public static JDBCStringSet connectToHSQLDB() throws SQLException, IOException {
		return new JDBCStringSet("jdbc:hsqldb:file:/" + File.createTempFile("hsqldb-stringset", ".temp"), null, "SA",
				"", null);
	}

	public static JDBCStringSet connectToHSQLDB(File fileName) throws SQLException {
		return new JDBCStringSet("jdbc:hsqldb:file:/" + fileName.getAbsolutePath(), null, "SA", "", null);
	}

	public static JDBCStringSet connectToHSQLDB(File fileName, String tableName) throws SQLException {
		return new JDBCStringSet("jdbc:hsqldb:file:/" + fileName.getAbsolutePath(), tableName, "SA", "", null);
	}

	public static JDBCStringSet connectToHSQLDB(File fileName, String tableName, String userName, String password)
			throws SQLException {
		return new JDBCStringSet("jdbc:hsqldb:file:/" + fileName.getAbsolutePath(), tableName, userName, password, null);
	}

	public static JDBCStringSet connectToHSQLDB(File fileName, String tableName, String userName, String password,
			String columnName) throws SQLException {
		return new JDBCStringSet("jdbc:hsqldb:file:/" + fileName.getAbsolutePath(), tableName, userName, password,
				columnName);
	}

	@Override
	protected void initDatabase() throws SQLException {
		// check if table already exists and create if necessary
		DatabaseMetaData dbm = getConnection().getMetaData();
		ResultSet tables = dbm.getTables(null, null, this.tableName, null);
		if (!tables.next()) {
			tables.close();

			if (this.columnName == null) {
				this.columnName = "id";
			}

			StringBuilder sql = new StringBuilder();

			if (url.startsWith("jdbc:mysql")) {
				sql.append("CREATE TABLE `" + this.tableName + "`");
				sql.append(" (");
				sql.append("`" + this.columnName + "` VARCHAR(255) ");
				sql.append("COLLATE utf8_bin NOT NULL, ");
				sql.append("PRIMARY KEY (`" + this.columnName + "`)");
				sql.append(") ");
				sql.append("DEFAULT CHARSET=utf8 COLLATE=utf8_bin");
			} else if (url.startsWith("jdbc:hsqldb")) {
				sql.append("CREATE TABLE " + this.tableName + "");
				sql.append(" (");
				sql.append("" + this.columnName + " VARCHAR(255) PRIMARY KEY, ");
				sql.append(") ");
			} else {
				sql.append("CREATE TABLE " + this.tableName + "");
				sql.append(" (");
				sql.append("" + this.columnName + " VARCHAR(255) PRIMARY KEY, ");
				sql.append(") ");
			}
			PreparedStatement statement;
			statement = getConnection().prepareStatement(sql.toString());
			statement.execute();
			statement.close();
		} else {
			tables.close();

			// figure out column

			// this only works for one column
			String sql = null;
			if (url.startsWith("jdbc:mysql")) {
				sql = "SELECT * FROM `" + this.tableName + "` LIMIT 1";
			} else {
				sql = "SELECT * FROM " + this.tableName + " LIMIT 1";
			}
			PreparedStatement ps = getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.getMetaData().getColumnCount() != 1) {
				throw new RuntimeException("table must have one column for automatic detection");
			} else {
				this.columnName = rs.getMetaData().getColumnName(1);
			}
			rs.close();
			ps.close();
		}
	}

	@Override
	public synchronized boolean add(String o) {
		if (o == null) {
			throw new RuntimeException("object cannot be null");
		} else if (!(o instanceof String)) {
			throw new RuntimeException("object must be a string");
		}
		try {
			boolean wasThere = contains(o);
			if (!wasThere) {
				if (insertStatement == null || insertStatement.isClosed()) {
					String sql = null;
					if (url.startsWith("jdbc:mysql")) {
						sql = "INSERT INTO `" + tableName + "` (`" + columnName + "`) VALUES (?)";
					} else {
						sql = "INSERT INTO " + tableName + " (" + columnName + ") VALUES (?)";
					}
					insertStatement = getConnection().prepareStatement(sql);
				}
				insertStatement.setString(1, String.valueOf(o));
				insertStatement.executeUpdate();
			} else {
				if (updateStatement == null || updateStatement.isClosed()) {
					String sql = null;
					if (url.startsWith("jdbc:mysql")) {
						sql = "UPDATE `" + tableName + "` SET `" + columnName + "`=? WHERE `" + columnName + "`=?";
					} else {
						sql = "UPDATE " + tableName + " SET " + columnName + "=? WHERE " + columnName + "=?";
					}
					updateStatement = getConnection().prepareStatement(sql);
				}
				updateStatement.setString(1, String.valueOf(o));
				updateStatement.setString(2, String.valueOf(o));
				updateStatement.executeUpdate();
			}
			return wasThere;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public final synchronized boolean remove(Object o) {
		if (o == null) {
			throw new RuntimeException("object cannot be null");
		} else if (!(o instanceof String)) {
			throw new RuntimeException("object must be a string");
		}
		try {
			boolean wasThere = contains(o);
			if (wasThere) {
				if (deleteStatement == null || deleteStatement.isClosed()) {
					String sql = null;
					if (url.startsWith("jdbc:mysql")) {
						sql = "DELETE FROM `" + tableName + "` WHERE `" + columnName + "` = ?";
					} else {
						sql = "DELETE FROM " + tableName + " WHERE " + columnName + " = ?";
					}
					deleteStatement = getConnection().prepareStatement(sql);
				}
				deleteStatement.setString(1, String.valueOf(o));
				deleteStatement.executeUpdate();
			}
			return wasThere;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean contains(Object o) {
		if (o == null) {
			throw new RuntimeException("object cannot be null");
		} else if (!(o instanceof String)) {
			throw new RuntimeException("object must be a string");
		}
		try {
			if (selectStatement == null || selectStatement.isClosed()) {
				String sql = null;
				if (url.startsWith("jdbc:mysql")) {
					sql = "SELECT `" + columnName + "` FROM `" + tableName + "` WHERE `" + columnName + "` = ? LIMIT 1";
				} else {
					sql = "SELECT " + columnName + " FROM " + tableName + " WHERE " + columnName + " = ? LIMIT 1";
				}
				selectStatement = getConnection().prepareStatement(sql);
			}
			selectStatement.setString(1, String.valueOf(o));
			ResultSet rs = selectStatement.executeQuery();
			boolean found = false;
			if (rs.next()) {
				found = true;
			}
			rs.close();
			return found;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public synchronized Iterator<String> iterator() {
		try {
			if (selectAllStatement == null || selectAllStatement.isClosed()) {
				String sql = null;
				if (url.startsWith("jdbc:mysql")) {
					sql = "SELECT `" + columnName + "` FROM `" + tableName + "`";
				} else {
					sql = "SELECT " + columnName + " FROM " + tableName;
				}
				selectAllStatement = getConnection().prepareStatement(sql);
			}
			// TODO: create iterator instead of copy
			ResultSet rs = selectAllStatement.executeQuery();
			Set<String> set = new HashSet<String>();
			while (rs.next()) {
				set.add(rs.getString(1));
			}
			return set.iterator();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
