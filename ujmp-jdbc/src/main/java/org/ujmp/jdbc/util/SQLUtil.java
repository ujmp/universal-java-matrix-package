/*
 * Copyright (C) 2008-2016 by Holger Arndt
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

package org.ujmp.jdbc.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.ujmp.core.util.MathUtil;

public abstract class SQLUtil {

	public static final String MARIADB_PARAMETERS = "?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&connectTimeout=120000&socketTimeout=120000&useFractionalSeconds=true&rewriteBatchedStatements=true";

	public static final String URL = "URL";
	public static final String TABLENAME = "TableName";
	public static final String DATABASENAME = "DatabaseName";
	public static final String KEYCOLUMNNAME = "KeyColumnName";
	public static final String VALUECOLUMNNAME = "ValueColumnName";
	public static final String KEYCLASS = "KeyClass";
	public static final String VALUECLASS = "ValueClass";
	public static final String SQLDIALECT = "SQLDialect";

	public static final Properties DEFAULTPROPERTIES = new Properties();
	public static final Properties MYSQLPROPERTIES = new Properties();
	public static final Properties DERBYPROPERTIES = new Properties();
	static {
		DEFAULTPROPERTIES.put("createDatabaseIfNotExist", "true");
		DEFAULTPROPERTIES.put("useUnicode", "true");
		DEFAULTPROPERTIES.put("characterEncoding", "utf8");
		DEFAULTPROPERTIES.put("zeroDateTimeBehavior", "convertToNull");
		MYSQLPROPERTIES.put("useCompression", "true");
		DERBYPROPERTIES.put("create", "true");
	}

	public static final String MYSQLDEFAULTCHARSET = "utf8";

	protected final int maxKeyLength = 1024;

	public enum SQLDialect {
		MYSQL, POSTGRESQL, MSSQL, HSQLDB, ORACLE, DERBY, HIVE, DB2, SQLITE, H2
	};

	public static SQLDialect getSQLDialect(String url) {
		if (url.startsWith("jdbc:mysql:")) {
			return SQLDialect.MYSQL;
		} else if (url.startsWith("jdbc:hsqldb:")) {
			return SQLDialect.HSQLDB;
		} else if (url.startsWith("jdbc:postgresql:")) {
			return SQLDialect.POSTGRESQL;
		} else if (url.startsWith("jdbc:oracle:")) {
			return SQLDialect.ORACLE;
		} else if (url.startsWith("jdbc:sqlserver:")) {
			return SQLDialect.MSSQL;
		} else if (url.startsWith("jdbc:derby:")) {
			return SQLDialect.DERBY;
		} else if (url.startsWith("jdbc:hive:")) {
			return SQLDialect.HIVE;
		} else if (url.startsWith("jdbc:db2:")) {
			return SQLDialect.DB2;
		} else if (url.startsWith("jdbc:sqlite:")) {
			return SQLDialect.SQLITE;
		} else if (url.startsWith("jdbc:h2:")) {
			return SQLDialect.H2;
		} else {
			throw new IllegalArgumentException("SQL dialect unknown for URL: " + url);
		}
	}

	public static void loadDriver(String url) {
		try {
			switch (getSQLDialect(url)) {
			case SQLITE:
				Class.forName("org.sqlite.JDBC");
				break;
			case H2:
				Class.forName("org.h2.Driver");
				break;
			case HSQLDB:
				Class.forName("org.hsqldb.jdbc.JDBCDriver");
				break;
			case MYSQL:
				Class.forName("com.mysql.jdbc.Driver");
				break;
			case DERBY:
				Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
				break;
			default:
				break;
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("JDBC driver not found", e);
		}
	}

	public static Properties createProperties(String url, String username, String password) {
		Properties properties = new Properties();
		if (username != null) {
			properties.put("user", username);
		}
		if (password != null) {
			properties.put("password", password);
		}
		properties.putAll(DEFAULTPROPERTIES);
		switch (getSQLDialect(url)) {
		case MYSQL:
			properties.putAll(MYSQLPROPERTIES);
			break;
		case DERBY:
			properties.putAll(DERBYPROPERTIES);
			break;
		default:
			break;
		}
		return properties;
	}

	public static String getDropTableSQL(SQLDialect sqlDialect, String tableName) {
		switch (sqlDialect) {
		case MYSQL:
			return "DROP TABLE `" + tableName + "`";
		default:
			return "DROP TABLE \"" + tableName + "\"";
		}
	}

	public static PreparedStatement getDropTableStatement(Connection connection, SQLDialect sqlDialect, String tableName)
			throws SQLException {
		return connection.prepareStatement(getDropTableSQL(sqlDialect, tableName));
	}

	public static PreparedStatement getTruncateTableStatement(Connection connection, SQLDialect sqlDialect,
			String tableName) throws SQLException {
		return connection.prepareStatement(getTruncateTableSQL(sqlDialect, tableName));
	}

	public static String getTruncateTableSQL(SQLDialect sqlDialect, String tableName) {
		switch (sqlDialect) {
		case MYSQL:
			return "TRUNCATE TABLE `" + tableName + "`";
		case SQLITE:
			return "DELETE FROM \"" + tableName + "\"";
		default:
			return "TRUNCATE TABLE \"" + tableName + "\"";
		}
	}

	public static PreparedStatement getCountStatement(Connection connection, SQLDialect sqlDialect, String tableName)
			throws SQLException {
		return connection.prepareStatement(getCountSQL(sqlDialect, tableName));
	}

	public static String getCountSQL(SQLDialect sqlDialect, String tableName) {
		switch (sqlDialect) {
		case MYSQL:
			return "SELECT COUNT(1) FROM `" + tableName + "`";
		default:
			return "SELECT COUNT(1) FROM \"" + tableName + "\"";
		}
	}

	public static PreparedStatement getDeleteIdStatement(Connection connection, SQLDialect sqlDialect,
			String tableName, String keyColumnName) throws SQLException {
		return connection.prepareStatement(getDeleteIdSQL(sqlDialect, tableName, keyColumnName));
	}

	public static String getDeleteIdSQL(SQLDialect sqlDialect, String tableName, String keyColumnName) {
		switch (sqlDialect) {
		case MYSQL:
			return "DELETE FROM `" + tableName + "` WHERE `" + keyColumnName + "` = ?";
		default:
			return "DELETE FROM \"" + tableName + "\" WHERE \"" + keyColumnName + "\" = ?";
		}
	}

	public static PreparedStatement getSelectIdsStatement(Connection connection, SQLDialect sqlDialect,
			String tableName, String keyColumnName) throws SQLException {
		PreparedStatement ps = connection.prepareStatement(getSelectIdsSQL(sqlDialect, tableName, keyColumnName),
				ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		ps.setFetchSize(100);
		return ps;
	}

	public static String getSelectIdsSQL(SQLDialect sqlDialect, String tableName, String keyColumnName) {
		switch (sqlDialect) {
		case MYSQL:
			return "SELECT `" + keyColumnName + "` FROM `" + tableName + "`";
		default:
			return "SELECT \"" + keyColumnName + "\" FROM \"" + tableName + "\"";
		}
	}

	public static PreparedStatement getInsertKeyValueStatement(Connection connection, SQLDialect sqlDialect,
			String tableName, String keyColumnName, String valueColumnName) throws SQLException {
		return connection.prepareStatement(getInsertKeyValueSQL(sqlDialect, tableName, keyColumnName, valueColumnName));
	}

	public static String getInsertKeyValueSQL(SQLDialect sqlDialect, String tableName, String keyColumnName,
			String valueColumnName) {
		switch (sqlDialect) {
		case MYSQL:
			return "INSERT INTO `" + tableName + "` (`" + keyColumnName + "`, `" + valueColumnName + "`) VALUES (?,?)";
		default:
			return "INSERT INTO \"" + tableName + "\" (\"" + keyColumnName + "\", \"" + valueColumnName
					+ "\") VALUES (?,?)";
		}
	}

	public static PreparedStatement getInsertIdStatement(Connection connection, SQLDialect sqlDialect,
			String tableName, String columnName) throws SQLException {
		return connection.prepareStatement(getInsertIdSQL(sqlDialect, tableName, columnName));
	}

	public static String getInsertIdSQL(SQLDialect sqlDialect, String tableName, String columnName) {
		switch (sqlDialect) {
		case MYSQL:
			return "INSERT INTO `" + tableName + "` (`" + columnName + "`) VALUES (?)";
		default:
			return "INSERT INTO \"" + tableName + "\" (\"" + columnName + "\") VALUES (?)";
		}
	}

	public static PreparedStatement getUpdateKeyValueStatement(Connection connection, SQLDialect sqlDialect,
			String tableName, String keyColumnName, String valueColumnName) throws SQLException {
		return connection.prepareStatement(getUpdateKeyValueSQL(sqlDialect, tableName, keyColumnName, valueColumnName));
	}

	public static String getUpdateKeyValueSQL(SQLDialect sqlDialect, String tableName, String keyColumnName,
			String valueColumnName) {
		switch (sqlDialect) {
		case MYSQL:
			return "UPDATE `" + tableName + "` SET `" + valueColumnName + "` = ? WHERE `" + keyColumnName + "` = ?";
		default:
			return "UPDATE \"" + tableName + "\" SET \"" + valueColumnName + "\" = ? WHERE \"" + keyColumnName
					+ "\" = ?";
		}
	}

	public static PreparedStatement getValueForKeyStatement(Connection connection, SQLDialect sqlDialect,
			String tableName, String keyColumnName, String valueColumnName) throws SQLException {
		return connection.prepareStatement(getValueForKeySQL(sqlDialect, tableName, keyColumnName, valueColumnName));
	}

	public static String getValueForKeySQL(SQLDialect sqlDialect, String tableName, String keyColumnName,
			String valueColumnName) {
		switch (sqlDialect) {
		case MYSQL:
			return "SELECT `" + valueColumnName + "` FROM `" + tableName + "` WHERE `" + keyColumnName
					+ "` = ? LIMIT 1";
		case DERBY:
			return "SELECT \"" + valueColumnName + "\" FROM \"" + tableName + "\" WHERE \"" + keyColumnName + "\" = ?";
		case H2:
			return "SELECT \"" + valueColumnName + "\" FROM \"" + tableName + "\" WHERE \"" + keyColumnName
					+ "\" = ? LIMIT 1";
		default:
			return "SELECT \"" + valueColumnName + "\" FROM \"" + tableName + "\" WHERE \"" + keyColumnName
					+ "\" = ? LIMIT 1";
		}
	}

	public static PreparedStatement getExistsStatement(Connection connection, SQLDialect sqlDialect, String tableName,
			String valueColumnName) throws SQLException {
		return connection.prepareStatement(getExistsSQL(sqlDialect, tableName, valueColumnName));
	}

	public static String getExistsSQL(SQLDialect sqlDialect, String tableName, String columnName) {
		switch (sqlDialect) {
		case MYSQL:
			return "SELECT EXISTS(SELECT 1 FROM `" + tableName + "` WHERE `" + columnName + "` = ? LIMIT 1)";
		case DERBY:
			return "SELECT COUNT(1)>0 FROM \"" + tableName + "\" WHERE CAST(\"" + columnName
					+ "\" AS VARCHAR(128)) = CAST(? AS VARCHAR(128))";
		case HSQLDB:
			return "SELECT COUNT(1) FROM (SELECT 1 FROM \"" + tableName + "\" WHERE \"" + columnName
					+ "\" = ? LIMIT 1)";
		default:
			return "SELECT EXISTS(SELECT 1 FROM \"" + tableName + "\" WHERE \"" + columnName + "\" = ? LIMIT 1)";
		}
	}

	public static String getDatabaseName(String url) {
		String[] fields = url.split("/");
		fields = fields[fields.length - 1].split("\\?");
		return fields[0];
	}

	public static boolean tableExists(Connection connection, String tableName) throws SQLException {
		DatabaseMetaData dbm = connection.getMetaData();
		ResultSet tables = dbm.getTables(null, null, tableName, null);
		boolean exists = false;
		if (tables.next()) {
			exists = true;
		}
		tables.close();
		return exists;
	}

	public static PreparedStatement getCreateKeyValueStringTableStatement(Connection connection, SQLDialect sqlDialect,
			String tableName, String keyColumnName, String valueColumnName) throws SQLException {
		return connection.prepareStatement(getCreateKeyValueStringTableSQL(sqlDialect, tableName, keyColumnName,
				valueColumnName));
	}

	public static PreparedStatement getCreateKeyValueByteTableStatement(Connection connection, SQLDialect sqlDialect,
			String tableName, String keyColumnName, String valueColumnName) throws SQLException {
		return connection.prepareStatement(getCreateKeyValueByteTableSQL(sqlDialect, tableName, keyColumnName,
				valueColumnName));
	}

	public static String getCreateKeyValueStringTableSQL(SQLDialect sqlDialect, String tableName, String keyColumnName,
			String valueColumnName) {
		StringBuilder sql = new StringBuilder();
		switch (sqlDialect) {
		case MYSQL:
			sql.append("CREATE TABLE `" + tableName + "`");
			sql.append(" (");
			sql.append("`" + keyColumnName + "` VARCHAR(1024) ");
			sql.append("COLLATE utf8_bin NOT NULL, ");
			sql.append("`" + valueColumnName + "` LONGTEXT ");
			sql.append("COLLATE utf8_bin NOT NULL, ");
			sql.append("PRIMARY KEY (`" + keyColumnName + "`(190))");
			sql.append(") ");
			sql.append("DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=compressed");
			break;
		case HSQLDB:
			sql.append("CREATE TABLE \"" + tableName + "\"");
			sql.append(" (");
			sql.append("\"" + keyColumnName + "\" VARCHAR(255) PRIMARY KEY, ");
			sql.append("\"" + valueColumnName + "\" LONGVARCHAR ");
			sql.append(") ");
			break;
		case DERBY:
			sql.append("CREATE TABLE \"" + tableName + "\"");
			sql.append(" (");
			sql.append("\"" + keyColumnName + "\" VARCHAR(255) PRIMARY KEY, ");
			sql.append("\"" + valueColumnName + "\" LONG VARCHAR ");
			sql.append(") ");
			break;
		case H2:
			sql.append("CREATE TABLE \"" + tableName + "\"");
			sql.append(" (");
			sql.append("\"" + keyColumnName + "\" VARCHAR(255) PRIMARY KEY, ");
			sql.append("\"" + valueColumnName + "\" VARCHAR(1024) ");
			sql.append(") ");
			break;
		default:
			sql.append("CREATE TABLE \"" + tableName + "\"");
			sql.append(" (");
			sql.append("\"" + keyColumnName + "\" VARCHAR(255) PRIMARY KEY, ");
			sql.append("\"" + valueColumnName + "\" TEXT ");
			sql.append(") ");
			break;
		}
		return sql.toString();
	}

	public static String getColumnTypeSQL(SQLDialect sqlDialect, Class<?> columnClass, boolean isKeyColumn) {
		switch (sqlDialect) {
		case MYSQL:
			if (columnClass == String.class) {
				return isKeyColumn ? "VARCHAR(1024) COLLATE utf8_bin NOT NULL" : "LONGTEXT COLLATE utf8_bin NOT NULL";
			} else if (columnClass == Integer.class) {
				return isKeyColumn ? "INT NOT NULL" : "INT";
			} else if (columnClass == Long.class) {
				return isKeyColumn ? "LONGINT NOT NULL" : "LONGINT";
			} else if (columnClass == Float.class) {
				return isKeyColumn ? "FLOAT NOT NULL" : "FLOAT";
			} else if (columnClass == Double.class) {
				return isKeyColumn ? "DOUBLE NOT NULL" : "DOUBLE";
			} else {
				return isKeyColumn ? "VARBINARY(1024) NOT NULL" : "LONGBLOB";
			}
		default:
			if (columnClass == String.class) {
				return isKeyColumn ? "VARCHAR(255) NOT NULL" : "TEXT";
			} else {
				return isKeyColumn ? "VARBINARY(255) NOT NULL" : "TEXT";
			}
		}
	}

	public static String getCreateKeyValueTableSQL(SQLDialect sqlDialect, String tableName, String keyColumnName,
			Class<?> keyClass, String valueColumnName, Class<?> valueClass) {
		StringBuilder sql = new StringBuilder();
		switch (sqlDialect) {
		case MYSQL:
			sql.append("CREATE TABLE `" + tableName + "` (");
			sql.append("`" + keyColumnName + "` " + getColumnTypeSQL(sqlDialect, keyClass, true) + ", ");
			sql.append("`" + valueColumnName + "` " + getColumnTypeSQL(sqlDialect, valueClass, false) + ", ");
			sql.append("PRIMARY KEY (`" + keyColumnName + "`(190))");
			sql.append(") DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=compressed");
			break;
		case HSQLDB:
			sql.append("CREATE TABLE \"" + tableName + "\" (");
			sql.append("\"" + keyColumnName + "\" VARCHAR(255) PRIMARY KEY, ");
			sql.append("\"" + valueColumnName + "\" LONGVARCHAR ");
			sql.append(") ");
			break;
		case DERBY:
			sql.append("CREATE TABLE \"" + tableName + "\" (");
			sql.append("\"" + keyColumnName + "\" VARCHAR(255) PRIMARY KEY, ");
			sql.append("\"" + valueColumnName + "\" LONG VARCHAR ");
			sql.append(") ");
			break;
		default:
			sql.append("CREATE TABLE \"" + tableName + "\" (");
			sql.append("\"" + keyColumnName + "\" VARCHAR(255) PRIMARY KEY, ");
			sql.append("\"" + valueColumnName + "\" TEXT ");
			sql.append(") ");
			break;
		}
		return sql.toString();
	}

	public static String getCreateKeyValueByteTableSQL(SQLDialect sqlDialect, String tableName, String keyColumnName,
			String valueColumnName) {
		StringBuilder sql = new StringBuilder();
		switch (sqlDialect) {
		case MYSQL:
			sql.append("CREATE TABLE `" + tableName + "`");
			sql.append(" (");
			sql.append("`" + keyColumnName + "` VARCHAR(1024) ");
			sql.append("COLLATE utf8_bin NOT NULL, ");
			sql.append("`" + valueColumnName + "` LONGBLOB ");
			sql.append("NOT NULL, ");
			sql.append("PRIMARY KEY (`" + keyColumnName + "`(190))");
			sql.append(") ");
			sql.append("DEFAULT CHARSET=utf8 COLLATE=utf8_bin");
			break;
		case HSQLDB:
			sql.append("CREATE TABLE \"" + tableName + "\"");
			sql.append(" (");
			sql.append("\"" + keyColumnName + "\" VARCHAR(255) PRIMARY KEY, ");
			sql.append("\"" + valueColumnName + "\" LONGVARBINARY ");
			sql.append(") ");
			break;
		default:
			sql.append("CREATE TABLE \"" + tableName + "\"");
			sql.append(" (");
			sql.append("\"" + keyColumnName + "\" VARCHAR(255) PRIMARY KEY, ");
			sql.append("\"" + valueColumnName + "\" TEXT ");
			sql.append(") ");
			break;
		}
		return sql.toString();
	}

	public static void createKeyValueStringTable(Connection connection, SQLDialect sqlDialect, String tableName,
			String keyColumnName, String valueColumnName) throws SQLException {
		PreparedStatement statement = SQLUtil.getCreateKeyValueStringTableStatement(connection, sqlDialect, tableName,
				keyColumnName, valueColumnName);
		statement.execute();
		statement.close();
	}

	public static void createKeyValueByteTable(Connection connection, SQLDialect sqlDialect, String tableName,
			String keyColumnName, String valueColumnName) throws SQLException {
		PreparedStatement statement = SQLUtil.getCreateKeyValueByteTableStatement(connection, sqlDialect, tableName,
				keyColumnName, valueColumnName);
		statement.execute();
		statement.close();
	}

	public static List<String> getColumnNames(Connection connection, String tableName) throws SQLException {
		List<String> columnNames = new LinkedList<String>();
		DatabaseMetaData dbm = connection.getMetaData();
		ResultSet rs = dbm.getColumns(null, null, tableName, null);
		while (rs.next()) {
			String keyColumnName = rs.getString("COLUMN_NAME");
			columnNames.add(keyColumnName);
		}
		rs.close();
		return columnNames;
	}

	public static PreparedStatement getSelectAllLimit1Statement(Connection connection, SQLDialect sqlDialect,
			String tableName) throws SQLException {
		return connection.prepareStatement(getSelectAllLimit1SQL(sqlDialect, tableName));
	}

	public static String getSelectAllLimit1SQL(SQLDialect sqlDialect, String tableName) {
		switch (sqlDialect) {
		case MYSQL:
			return "SELECT * FROM `" + tableName + "` LIMIT 1";
		default:
			return "SELECT * FROM \"" + tableName + "\" LIMIT 1";
		}
	}

	public static List<String> getPrimaryKeyColumnNames(Connection connection, String tableName) throws SQLException {
		List<String> primaryKeyColumnNames = new LinkedList<String>();
		DatabaseMetaData dbm = connection.getMetaData();
		ResultSet rs = dbm.getPrimaryKeys(null, null, tableName);
		while (rs.next()) {
			String keyColumnName = rs.getString("COLUMN_NAME");
			primaryKeyColumnNames.add(keyColumnName);
		}
		rs.close();
		return primaryKeyColumnNames;
	}

	public static void createKeyStringTable(Connection connection, SQLDialect sqlDialect, String tableName,
			String columnName) throws SQLException {
		PreparedStatement statement = SQLUtil.getCreateKeyStringTableStatement(connection, sqlDialect, tableName,
				columnName);
		statement.execute();
		statement.close();
	}

	public static PreparedStatement getCreateKeyStringTableStatement(Connection connection, SQLDialect sqlDialect,
			String tableName, String columnName) throws SQLException {
		return connection.prepareStatement(getCreateKeyStringTableSQL(sqlDialect, tableName, columnName));
	}

	public static String getCreateKeyStringTableSQL(SQLDialect sqlDialect, String tableName, String columnName) {
		StringBuilder sql = new StringBuilder();
		switch (sqlDialect) {
		case MYSQL:
			sql.append("CREATE TABLE `" + tableName + "`");
			sql.append(" (");
			sql.append("`" + columnName + "` VARCHAR(255) ");
			sql.append("COLLATE utf8_bin NOT NULL, ");
			sql.append("PRIMARY KEY (`" + columnName + "`)");
			sql.append(") ");
			sql.append("DEFAULT CHARSET=utf8 COLLATE=utf8_bin");
			break;
		default:
			sql.append("CREATE TABLE \"" + tableName + "\"");
			sql.append(" (");
			sql.append("\"" + columnName + "\" VARCHAR(255) PRIMARY KEY ");
			sql.append(") ");
			break;
		}
		return sql.toString();
	}

	public static PreparedStatement getSelectIdStatement(Connection connection, SQLDialect sqlDialect,
			String tableName, String columnName) throws SQLException {
		return connection.prepareStatement(getSelectIdSQL(sqlDialect, tableName, columnName));
	}

	public static String getSelectIdSQL(SQLDialect sqlDialect, String tableName, String columnName) {
		switch (sqlDialect) {
		case MYSQL:
			return "SELECT `" + columnName + "` FROM `" + tableName + "` WHERE `" + columnName + "` = ? LIMIT 1";
		default:
			return "SELECT \"" + columnName + "\" FROM \"" + tableName + "\" WHERE \"" + columnName + "\" = ? LIMIT 1";
		}
	}

	public static PreparedStatement getCreateDatabaseStatement(Connection connection, SQLDialect sqlDialect,
			String databaseName) throws SQLException {
		return connection.prepareStatement(getCreateDatabaseSQL(sqlDialect, databaseName));
	}

	public static String getCreateDatabaseSQL(SQLDialect sqlDialect, String databaseName) {
		switch (sqlDialect) {
		case MYSQL:
		case SQLITE:
		case H2:
			return "CREATE DATABASE `" + databaseName + "`";
		default:
			return "CREATE DATABASE \"" + databaseName + "\"";
		}
	}

	public static PreparedStatement getDropDatabaseStatement(Connection connection, SQLDialect sqlDialect,
			String databaseName) throws SQLException {
		return connection.prepareStatement(getDropDatabaseSQL(sqlDialect, databaseName));
	}

	public static String getDropDatabaseSQL(SQLDialect sqlDialect, String databaseName) {
		switch (sqlDialect) {
		case MYSQL:
		case SQLITE:
		case H2:
			return "DROP DATABASE `" + databaseName + "`";
		default:
			return "DROP DATABASE \"" + databaseName + "\"";
		}
	}

	public static Object getObject(ResultSet rs, int position, Class<?> objectClass) throws SQLException {
		Object object = rs.getObject(position);
		if (objectClass == null) {
			return object;
		} else if (objectClass == String.class) {
			return String.valueOf(object);
		} else if (objectClass == Integer.class) {
			if (object instanceof Integer) {
				return ((Integer) object).intValue();
			} else if (object instanceof Long) {
				return ((Long) object).intValue();
			} else if (object instanceof Double) {
				return ((Double) object).intValue();
			} else if (object instanceof Float) {
				return ((Float) object).intValue();
			} else if (object instanceof Byte) {
				return ((Byte) object).intValue();
			} else if (object instanceof Short) {
				return ((Short) object).intValue();
			} else if (object instanceof BigDecimal) {
				return ((BigDecimal) object).intValue();
			} else if (object instanceof BigInteger) {
				return ((BigInteger) object).intValue();
			} else if (object instanceof Boolean) {
				return ((Boolean) object).booleanValue() ? 1 : 0;
			} else if (object instanceof String) {
				return MathUtil.getInt(object);
			}
		} else if (objectClass == Long.class) {
			if (object instanceof Integer) {
				return ((Integer) object).longValue();
			} else if (object instanceof Long) {
				return ((Long) object).longValue();
			} else if (object instanceof Double) {
				return ((Double) object).longValue();
			} else if (object instanceof Float) {
				return ((Float) object).longValue();
			} else if (object instanceof Byte) {
				return ((Byte) object).longValue();
			} else if (object instanceof Short) {
				return ((Short) object).longValue();
			} else if (object instanceof BigDecimal) {
				return ((BigDecimal) object).longValue();
			} else if (object instanceof BigInteger) {
				return ((BigInteger) object).longValue();
			} else if (object instanceof Boolean) {
				return ((Boolean) object).booleanValue() ? 1 : 0;
			} else if (object instanceof String) {
				return MathUtil.getLong(object);
			}
		} else if (objectClass == Double.class) {
			if (object instanceof Integer) {
				return ((Integer) object).doubleValue();
			} else if (object instanceof Long) {
				return ((Long) object).doubleValue();
			} else if (object instanceof Double) {
				return ((Double) object).doubleValue();
			} else if (object instanceof Float) {
				return ((Float) object).doubleValue();
			} else if (object instanceof Byte) {
				return ((Byte) object).doubleValue();
			} else if (object instanceof Short) {
				return ((Short) object).doubleValue();
			} else if (object instanceof BigDecimal) {
				return ((BigDecimal) object).doubleValue();
			} else if (object instanceof BigInteger) {
				return ((BigInteger) object).doubleValue();
			} else if (object instanceof Boolean) {
				return ((Boolean) object).booleanValue() ? 1 : 0;
			} else if (object instanceof String) {
				return MathUtil.getDouble(object);
			}
		} else if (objectClass == Float.class) {
			if (object instanceof Integer) {
				return ((Integer) object).floatValue();
			} else if (object instanceof Long) {
				return ((Long) object).floatValue();
			} else if (object instanceof Double) {
				return ((Double) object).floatValue();
			} else if (object instanceof Float) {
				return ((Float) object).floatValue();
			} else if (object instanceof Byte) {
				return ((Byte) object).floatValue();
			} else if (object instanceof Short) {
				return ((Short) object).floatValue();
			} else if (object instanceof BigDecimal) {
				return ((BigDecimal) object).floatValue();
			} else if (object instanceof BigInteger) {
				return ((BigInteger) object).floatValue();
			} else if (object instanceof Boolean) {
				return ((Boolean) object).booleanValue() ? 1 : 0;
			} else if (object instanceof String) {
				return MathUtil.getFloat(object);
			}
		} else if (objectClass == Short.class) {
			if (object instanceof Integer) {
				return ((Integer) object).shortValue();
			} else if (object instanceof Long) {
				return ((Long) object).shortValue();
			} else if (object instanceof Double) {
				return ((Double) object).shortValue();
			} else if (object instanceof Float) {
				return ((Float) object).shortValue();
			} else if (object instanceof Byte) {
				return ((Byte) object).shortValue();
			} else if (object instanceof Short) {
				return ((Short) object).shortValue();
			} else if (object instanceof BigDecimal) {
				return ((BigDecimal) object).shortValue();
			} else if (object instanceof BigInteger) {
				return ((BigInteger) object).shortValue();
			} else if (object instanceof Boolean) {
				return ((Boolean) object).booleanValue() ? 1 : 0;
			} else if (object instanceof String) {
				return MathUtil.getShort(object);
			}
		}
		throw new RuntimeException("cannot convert object " + object + " to " + objectClass);
	}

}
