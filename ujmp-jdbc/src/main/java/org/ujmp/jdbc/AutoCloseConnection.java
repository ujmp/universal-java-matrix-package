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

package org.ujmp.jdbc;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
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
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;

public class AutoCloseConnection implements Connection {

	private final Connection connection;

	private Timer timer = new Timer();

	private long lastAccessTime = System.currentTimeMillis();

	public AutoCloseConnection(final Connection c) {
		this(c, 10000);
	}

	public AutoCloseConnection(final Connection c, final long maxIdleTime) {
		this(c, maxIdleTime, 1000);
	}

	public AutoCloseConnection(final Connection c, final long maxIdleTime, final long checkInterval) {
		this.connection = c;
		TimerTask timerTask = new TimerTask() {

			@Override
			public void run() {
				try {
					if (connection != null && !connection.isClosed()) {
						long idleTime = System.currentTimeMillis() - lastAccessTime;
						if (idleTime >= maxIdleTime) {
							close();
						}

					}
				} catch (Exception e) {
				}
			}
		};
		timer.schedule(timerTask, checkInterval, checkInterval);
	}

	public synchronized <T> T unwrap(Class<T> iface) throws SQLException {
		resetTimer();
		return connection.unwrap(iface);
	}

	private void resetTimer() {
		lastAccessTime = System.currentTimeMillis();
	}

	public synchronized boolean isWrapperFor(Class<?> iface) throws SQLException {
		resetTimer();
		return connection.isWrapperFor(iface);
	}

	public synchronized Statement createStatement() throws SQLException {
		resetTimer();
		return connection.createStatement();
	}

	public synchronized PreparedStatement prepareStatement(String sql) throws SQLException {
		resetTimer();
		return connection.prepareStatement(sql);
	}

	public synchronized CallableStatement prepareCall(String sql) throws SQLException {
		resetTimer();
		return connection.prepareCall(sql);
	}

	public synchronized String nativeSQL(String sql) throws SQLException {
		resetTimer();
		return connection.nativeSQL(sql);
	}

	public synchronized void setAutoCommit(boolean autoCommit) throws SQLException {
		resetTimer();
		connection.setAutoCommit(autoCommit);
	}

	public synchronized boolean getAutoCommit() throws SQLException {
		resetTimer();
		return connection.getAutoCommit();
	}

	public synchronized void commit() throws SQLException {
		resetTimer();
		connection.commit();
	}

	public synchronized void rollback() throws SQLException {
		resetTimer();
		connection.rollback();
	}

	public synchronized void close() throws SQLException {
		System.out.println("close");
		timer.cancel();
		connection.close();
	}

	public synchronized boolean isClosed() throws SQLException {
		resetTimer();
		return connection.isClosed();
	}

	public synchronized DatabaseMetaData getMetaData() throws SQLException {
		resetTimer();
		return connection.getMetaData();
	}

	public synchronized void setReadOnly(boolean readOnly) throws SQLException {
		resetTimer();
		connection.setReadOnly(readOnly);
	}

	public synchronized boolean isReadOnly() throws SQLException {
		resetTimer();
		return connection.isReadOnly();
	}

	public synchronized void setCatalog(String catalog) throws SQLException {
		resetTimer();
		connection.setCatalog(catalog);
	}

	public synchronized String getCatalog() throws SQLException {
		resetTimer();
		return connection.getCatalog();
	}

	public synchronized void setTransactionIsolation(int level) throws SQLException {
		resetTimer();
		connection.setTransactionIsolation(level);
	}

	public synchronized int getTransactionIsolation() throws SQLException {
		resetTimer();
		return connection.getTransactionIsolation();
	}

	public synchronized SQLWarning getWarnings() throws SQLException {
		resetTimer();
		return connection.getWarnings();
	}

	public synchronized void clearWarnings() throws SQLException {
		resetTimer();
		connection.clearWarnings();
	}

	public synchronized Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
		resetTimer();
		return connection.createStatement(resultSetType, resultSetConcurrency);
	}

	public synchronized PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		resetTimer();
		return connection.prepareStatement(sql, resultSetType, resultSetConcurrency);
	}

	public synchronized CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		resetTimer();
		return connection.prepareCall(sql, resultSetType, resultSetConcurrency);
	}

	public synchronized Map<String, Class<?>> getTypeMap() throws SQLException {
		resetTimer();
		return connection.getTypeMap();
	}

	public synchronized void setTypeMap(Map<String, Class<?>> map) throws SQLException {
		resetTimer();
		connection.setTypeMap(map);
	}

	public synchronized void setHoldability(int holdability) throws SQLException {
		resetTimer();
		connection.setHoldability(holdability);
	}

	public synchronized int getHoldability() throws SQLException {
		resetTimer();
		return connection.getHoldability();
	}

	public synchronized Savepoint setSavepoint() throws SQLException {
		resetTimer();
		return connection.setSavepoint();
	}

	public synchronized Savepoint setSavepoint(String name) throws SQLException {
		resetTimer();
		return connection.setSavepoint(name);
	}

	public synchronized void rollback(Savepoint savepoint) throws SQLException {
		resetTimer();
		connection.rollback(savepoint);
	}

	public synchronized void releaseSavepoint(Savepoint savepoint) throws SQLException {
		resetTimer();
		connection.releaseSavepoint(savepoint);
	}

	public synchronized Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
		resetTimer();
		return connection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	public synchronized PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		resetTimer();
		return connection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	public synchronized CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		resetTimer();
		return connection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	public synchronized PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
		resetTimer();
		return connection.prepareStatement(sql, autoGeneratedKeys);
	}

	public synchronized PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
		resetTimer();
		return connection.prepareStatement(sql, columnIndexes);
	}

	public synchronized PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
		resetTimer();
		return connection.prepareStatement(sql, columnNames);
	}

	public synchronized Clob createClob() throws SQLException {
		resetTimer();
		return connection.createClob();
	}

	public synchronized Blob createBlob() throws SQLException {
		resetTimer();
		return connection.createBlob();
	}

	public synchronized NClob createNClob() throws SQLException {
		resetTimer();
		return connection.createNClob();
	}

	public synchronized SQLXML createSQLXML() throws SQLException {
		resetTimer();
		return connection.createSQLXML();
	}

	public synchronized boolean isValid(int timeout) throws SQLException {
		resetTimer();
		return connection.isValid(timeout);
	}

	public synchronized void setClientInfo(String name, String value) throws SQLClientInfoException {
		resetTimer();
		connection.setClientInfo(name, value);

	}

	public synchronized void setClientInfo(Properties properties) throws SQLClientInfoException {
		resetTimer();
		connection.setClientInfo(properties);
	}

	public synchronized String getClientInfo(String name) throws SQLException {
		resetTimer();
		return connection.getClientInfo(name);
	}

	public synchronized Properties getClientInfo() throws SQLException {
		resetTimer();
		return connection.getClientInfo();
	}

	public synchronized Array createArrayOf(String typeName, Object[] elements) throws SQLException {
		resetTimer();
		return connection.createArrayOf(typeName, elements);
	}

	public synchronized Struct createStruct(String typeName, Object[] attributes) throws SQLException {
		resetTimer();
		return connection.createStruct(typeName, attributes);
	}

	public synchronized void setSchema(String schema) throws SQLException {
		resetTimer();
		connection.setSchema(schema);
	}

	public synchronized String getSchema() throws SQLException {
		resetTimer();
		return connection.getSchema();
	}

	public synchronized void abort(Executor executor) throws SQLException {
		resetTimer();
		connection.abort(executor);
	}

	public synchronized void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
		resetTimer();
		connection.setNetworkTimeout(executor, milliseconds);
	}

	public synchronized int getNetworkTimeout() throws SQLException {
		resetTimer();
		return connection.getNetworkTimeout();
	}

}
