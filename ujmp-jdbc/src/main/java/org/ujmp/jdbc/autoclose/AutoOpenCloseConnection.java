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

package org.ujmp.jdbc.autoclose;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import org.ujmp.jdbc.util.SQLUtil;
import org.ujmp.jdbc.util.SQLUtil.SQLDialect;

public class AutoOpenCloseConnection implements Connection {

	private final String url;
	private final Properties properties;

	private final int maxIdleTime;
	private final int checkInterval;

	private Connection connection = null;

	public AutoOpenCloseConnection(final String url, final String username, final String password) {
		this(url, username, password, AutoCloseConnection.DEFAULTMAXIDLETIME);
	}

	public AutoOpenCloseConnection(final String url, final String username, final String password, final int maxIdleTime) {
		this(url, SQLUtil.createProperties(url, username, password), maxIdleTime,
				AutoCloseConnection.DEFAULTCHECKINTERVAL);
	}

	public AutoOpenCloseConnection(final String url, final Properties properties, final int maxIdleTime,
			final int checkInterval) {
		this.url = url;
		this.maxIdleTime = maxIdleTime;
		this.checkInterval = checkInterval;
		this.properties = new Properties();
		this.properties.putAll(properties);
	}

	private synchronized Connection getConnection() throws SQLException {
		if (connection == null || connection.isClosed()) {
			SQLUtil.loadDriver(url);
			Connection c = DriverManager.getConnection(url, properties);
			connection = new AutoCloseConnection(c, maxIdleTime, checkInterval);
			if (SQLUtil.getSQLDialect(url) == SQLDialect.MYSQL) {
				Statement statement = connection.createStatement();
				statement.execute("SET NAMES " + SQLUtil.MYSQLDEFAULTCHARSET);
				statement.close();
			} else if (SQLUtil.getSQLDialect(url) == SQLDialect.SQLITE) {
				Statement statement = connection.createStatement();
				statement.execute("PRAGMA synchronous = Full");
				statement.close();
			}
		}
		return connection;
	}

	public final synchronized <T> T unwrap(Class<T> iface) throws SQLException {
		return getConnection().unwrap(iface);
	}

	public final synchronized boolean isWrapperFor(Class<?> iface) throws SQLException {
		return getConnection().isWrapperFor(iface);
	}

	public final synchronized Statement createStatement() throws SQLException {
		return getConnection().createStatement();
	}

	public final synchronized PreparedStatement prepareStatement(String sql) throws SQLException {
		return getConnection().prepareStatement(sql);
	}

	public final synchronized CallableStatement prepareCall(String sql) throws SQLException {
		return getConnection().prepareCall(sql);
	}

	public final synchronized String nativeSQL(String sql) throws SQLException {
		return getConnection().nativeSQL(sql);
	}

	public final synchronized void setAutoCommit(boolean autoCommit) throws SQLException {
		getConnection().setAutoCommit(autoCommit);
	}

	public final synchronized boolean getAutoCommit() throws SQLException {
		return getConnection().getAutoCommit();
	}

	public final synchronized void commit() throws SQLException {
		getConnection().commit();
	}

	public final synchronized void rollback() throws SQLException {
		getConnection().rollback();
	}

	public final synchronized void close() throws SQLException {
		if (connection != null && !connection.isClosed()) {
			connection.close();
		}
	}

	public final synchronized boolean isClosed() throws SQLException {
		if (connection != null) {
			return connection.isClosed();
		} else {
			return true;
		}
	}

	public final synchronized DatabaseMetaData getMetaData() throws SQLException {
		return getConnection().getMetaData();
	}

	public final synchronized void setReadOnly(boolean readOnly) throws SQLException {
		getConnection().setReadOnly(readOnly);
	}

	public final synchronized boolean isReadOnly() throws SQLException {
		return getConnection().isReadOnly();
	}

	public final synchronized void setCatalog(String catalog) throws SQLException {
		getConnection().setCatalog(catalog);
	}

	public final synchronized String getCatalog() throws SQLException {
		return getConnection().getCatalog();
	}

	public final synchronized void setTransactionIsolation(int level) throws SQLException {
		getConnection().setTransactionIsolation(level);
	}

	public final synchronized int getTransactionIsolation() throws SQLException {
		return getConnection().getTransactionIsolation();
	}

	public final synchronized SQLWarning getWarnings() throws SQLException {
		return getConnection().getWarnings();
	}

	public final synchronized void clearWarnings() throws SQLException {
		getConnection().clearWarnings();
	}

	public final synchronized Statement createStatement(int resultSetType, int resultSetConcurrency)
			throws SQLException {
		return getConnection().createStatement(resultSetType, resultSetConcurrency);
	}

	public final synchronized PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
			throws SQLException {
		return getConnection().prepareStatement(sql, resultSetType, resultSetConcurrency);
	}

	public final synchronized CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency)
			throws SQLException {
		return getConnection().prepareCall(sql, resultSetType, resultSetConcurrency);
	}

	public final synchronized Map<String, Class<?>> getTypeMap() throws SQLException {
		return getConnection().getTypeMap();
	}

	public final synchronized void setTypeMap(Map<String, Class<?>> map) throws SQLException {
		getConnection().setTypeMap(map);
	}

	public final synchronized void setHoldability(int holdability) throws SQLException {
		getConnection().setHoldability(holdability);
	}

	public final synchronized int getHoldability() throws SQLException {
		return getConnection().getHoldability();
	}

	public final synchronized Savepoint setSavepoint() throws SQLException {
		return getConnection().setSavepoint();
	}

	public final synchronized Savepoint setSavepoint(String name) throws SQLException {
		return getConnection().setSavepoint(name);
	}

	public final synchronized void rollback(Savepoint savepoint) throws SQLException {
		getConnection().rollback(savepoint);
	}

	public final synchronized void releaseSavepoint(Savepoint savepoint) throws SQLException {
		getConnection().releaseSavepoint(savepoint);
	}

	public final synchronized Statement createStatement(int resultSetType, int resultSetConcurrency,
			int resultSetHoldability) throws SQLException {
		return getConnection().createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	public final synchronized PreparedStatement prepareStatement(String sql, int resultSetType,
			int resultSetConcurrency, int resultSetHoldability) throws SQLException {
		return getConnection().prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	public final synchronized CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
			int resultSetHoldability) throws SQLException {
		return getConnection().prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	public final synchronized PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
		return getConnection().prepareStatement(sql, autoGeneratedKeys);
	}

	public final synchronized PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
		return getConnection().prepareStatement(sql, columnIndexes);
	}

	public final synchronized PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
		return getConnection().prepareStatement(sql, columnNames);
	}

	public final synchronized Clob createClob() throws SQLException {
		return getConnection().createClob();
	}

	public final synchronized Blob createBlob() throws SQLException {
		return getConnection().createBlob();
	}

	public final synchronized NClob createNClob() throws SQLException {
		return getConnection().createNClob();
	}

	public final synchronized SQLXML createSQLXML() throws SQLException {
		return getConnection().createSQLXML();
	}

	public final synchronized boolean isValid(int timeout) throws SQLException {
		return getConnection().isValid(timeout);
	}

	public final synchronized void setClientInfo(String name, String value) throws SQLClientInfoException {
		try {
			getConnection().setClientInfo(name, value);
		} catch (SQLException e) {
			throw new SQLClientInfoException();
		}
	}

	public final synchronized void setClientInfo(Properties properties) throws SQLClientInfoException {
		try {
			getConnection().setClientInfo(properties);
		} catch (SQLException e) {
			throw new SQLClientInfoException();
		}
	}

	public final synchronized String getClientInfo(String name) throws SQLException {
		return properties.getProperty(name);
	}

	public final synchronized Properties getClientInfo() throws SQLException {
		return properties;
	}

	public final synchronized Array createArrayOf(String typeName, Object[] elements) throws SQLException {
		return getConnection().createArrayOf(typeName, elements);
	}

	public final synchronized Struct createStruct(String typeName, Object[] attributes) throws SQLException {
		return getConnection().createStruct(typeName, attributes);
	}

	public final synchronized void setSchema(String schema) throws SQLException {
		getConnection().setSchema(schema);
	}

	public final synchronized String getSchema() throws SQLException {
		return getConnection().getSchema();
	}

	public final synchronized void abort(Executor executor) throws SQLException {
		getConnection().abort(executor);
	}

	public final synchronized void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
		getConnection().setNetworkTimeout(executor, milliseconds);
	}

	public final synchronized int getNetworkTimeout() throws SQLException {
		return getConnection().getNetworkTimeout();
	}

	public String toString() {
		try {
			return getConnection().toString();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean equals(Object obj) {
		try {
			return getConnection().equals(obj);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int hashCode() {
		try {
			return getConnection().hashCode();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
