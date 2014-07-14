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

package org.ujmp.jdbc.table;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

public class JDBCTable extends AbstractTable {
	private static final long serialVersionUID = 8283985768904867255L;

	protected String url = null;
	protected String username = null;
	protected String password = null;
	protected Set<String> columnsForKeys = null;
	protected String tableName = null;
	private boolean tableExists = false;

	private transient Connection connection = null;

	private transient PreparedStatement truncateTableStatement = null;
	protected transient Map<Set<String>, PreparedStatement> insertStatements = null;
	protected transient PreparedStatement updateStatement = null;
	private transient PreparedStatement deleteStatement = null;
	protected transient PreparedStatement selectStatement = null;
	private transient PreparedStatement countStatement = null;
	private transient PreparedStatement keyStatement = null;

	public JDBCTable(String url, String tableName, String userName, String password, String... columnsForKeys) {
		this.url = url;
		this.username = userName;
		this.password = password;
		this.tableName = tableName;
		this.columnsForKeys = new TreeSet<String>(Arrays.asList(columnsForKeys));

		if (columnsForKeys.length == 0) {
			throw new RuntimeException("no key columns defined");
		}

		// if no table name is defined, create one
		if (this.tableName == null) {
			this.tableName = "temp_table_" + System.currentTimeMillis();
		}
	}

	protected synchronized Connection getConnection() throws SQLException {
		if (connection == null || connection.isClosed()) {
			connection = DriverManager.getConnection(url, username, password);
		}
		return connection;
	}

	public boolean tableExists() throws SQLException {
		if (!tableExists) {
			DatabaseMetaData dbm = getConnection().getMetaData();
			ResultSet tables = dbm.getTables(null, null, "%", null);
			while (tables.next()) {
				if (tableName.equalsIgnoreCase(tables.getString(3))) {
					tableExists = true;
				}
			}
			tables.close();
		}
		return tableExists;
	}

	private void initDatabase(Map<String, Object> fieldsAndValues) throws SQLException {
		// check if table already exists and create if necessary
		if (!tableExists) {
			Set<String> valueColumns = new TreeSet<String>();
			for (String field : fieldsAndValues.keySet()) {
				if (!columnsForKeys.contains(field)) {
					valueColumns.add(field);
				}
			}

			StringBuilder sql = new StringBuilder();

			sql.append("CREATE TABLE ");
			sql.append(this.tableName);
			sql.append(" (");
			for (String keyColumn : columnsForKeys) {
				sql.append(keyColumn);
				sql.append(" VARCHAR(255), ");
			}
			for (String valueColumn : valueColumns) {
				sql.append(valueColumn);
				sql.append(" TEXT, ");
			}
			sql.append("PRIMARY KEY (");
			int i = 0;
			for (String keyColumn : columnsForKeys) {
				sql.append(keyColumn);
				if (i++ < columnsForKeys.size() - 1) {
					sql.append(", ");
				}
			}
			sql.append("))");

			PreparedStatement statement;
			statement = getConnection().prepareStatement(sql.toString());
			statement.execute();
			statement.close();
		} else {
			// if no key column is defined, assume that it is the primary key
			// column
			// if (this.keyColumn == null) {
			// ResultSet rs = dbm.getPrimaryKeys(null, null, this.tableName);
			// if (rs.next()) {
			// this.keyColumn = rs.getString("COLUMN_NAME");
			// } else {
			// throw new RuntimeException("table must have a primary key");
			// }
			// if (rs.next()) {
			// throw new
			// RuntimeException("table must not have more than one primary key");
			// }
			// rs.close();
			// }
		}
	}

	public static JDBCTable connectToMySQL(String serverName, int port, String databaseName, String tableName,
			String username, String password, String... columnsForKeys) throws SQLException {
		return new JDBCTable("jdbc:mysql://" + serverName + ":" + port + "/" + databaseName
				+ "?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull", tableName, username,
				password, columnsForKeys);
	}

	public static JDBCTable connectToMySQL(String serverName, int port, String databaseName, String tableName,
			String userName, String password) throws SQLException {
		return new JDBCTable("jdbc:mysql://" + serverName + ":" + port + "/" + databaseName
				+ "?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull", tableName, userName,
				password);
	}

	public static JDBCTable connectToHSQLDB(File filename, String tableName, String... columnForKeys) {
		return new JDBCTable("jdbc:hsqldb:file:/" + filename, tableName, "SA", "", columnForKeys);
	}

	public boolean add(Map<String, Object> fieldsAndValues) {
		try {
			Set<String> keySet = fieldsAndValues.keySet();
			if (insertStatements == null) {
				insertStatements = new HashMap<Set<String>, PreparedStatement>();
			}
			PreparedStatement insertStatement = insertStatements.get(keySet);
			if (insertStatement == null || insertStatement.isClosed()) {
				initDatabase(fieldsAndValues);
				StringBuilder sql = new StringBuilder();
				sql.append("insert into " + tableName + " (");
				int i = 0;
				for (String key : keySet) {
					sql.append(key);
					if (i++ < keySet.size() - 1) {
						sql.append(", ");
					}
				}
				sql.append(") values (");
				for (; i > 0; i--) {
					sql.append("?");
					if (i > 1) {
						sql.append(", ");
					}
				}
				sql.append(")");
				insertStatement = getConnection().prepareStatement(sql.toString());
			}
			int i = 1;
			for (Entry<String, Object> entry : fieldsAndValues.entrySet()) {
				insertStatement.setString(i++, "" + entry.getValue());
			}
			int count = insertStatement.executeUpdate();
			return count != 0;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Map<String, Object> getFirst(Map<String, Object> keys) {

		return null;
	}

	public List<Map<String, Object>> getAll(Map<String, Object> keys) {
		// TODO Auto-generated method stub
		return null;
	}

	public int size() {
		try {
			if (!tableExists()) {
				return 0;
			}
			int count = -1;
			if (countStatement == null || countStatement.isClosed()) {
				String sql = "select count(*) from " + tableName;
				countStatement = getConnection().prepareStatement(sql);
			}
			ResultSet rs = countStatement.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
			rs.close();
			return count;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	public Iterator<Map<String, Object>> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	public void clear() {
		try {
			if (truncateTableStatement == null || truncateTableStatement.isClosed()) {
				String sql = "truncate table " + tableName;
				truncateTableStatement = getConnection().prepareStatement(sql);
			}
			truncateTableStatement.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public boolean update(Map<String, Object> keyFieldsAndValues, Map<String, Object> updatedFieldsAndValues) {
		// TODO Auto-generated method stub
		return false;
	}
}
