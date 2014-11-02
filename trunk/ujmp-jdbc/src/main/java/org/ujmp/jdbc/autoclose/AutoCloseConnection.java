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

package org.ujmp.jdbc.autoclose;

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
import java.util.TimerTask;
import java.util.concurrent.Executor;

import org.ujmp.core.util.UJMPTimer;

public class AutoCloseConnection implements Connection {

	public static final int DEFAULTMAXIDLETIME = 3600000;
	public static final int DEFAULTCHECKINTERVAL = 1000;

	private final Connection connection;
	private final TimerTask timerTask;
	private final UJMPTimer timer;

	private long lastAccessTime = System.currentTimeMillis();

	public AutoCloseConnection(final Connection c) {
		this(c, DEFAULTMAXIDLETIME);
	}

	public AutoCloseConnection(final Connection c, final int maxIdleTime) {
		this(c, maxIdleTime, DEFAULTCHECKINTERVAL);
	}

	public AutoCloseConnection(final Connection c, final int maxIdleTime, final long checkInterval) {
		this.connection = c;
		timerTask = new TimerTask() {

			@Override
			public void run() {
				try {
					if (connection != null && !connection.isClosed()) {
						long idleTime = System.currentTimeMillis() - lastAccessTime;
						if (idleTime >= maxIdleTime) {
							System.out.println("closing connection after " + idleTime + " ms");
							close();
						}

					}
				} catch (Exception e) {
				}
			}
		};
		timer = UJMPTimer.newInstance("AutoCloseConnection");
		timer.schedule(timerTask, checkInterval, checkInterval);
	}

	public final synchronized <T> T unwrap(Class<T> iface) throws SQLException {
		resetTimer();
		return connection.unwrap(iface);
	}

	public final void resetTimer() {
		lastAccessTime = System.currentTimeMillis();
	}

	public final synchronized boolean isWrapperFor(Class<?> iface) throws SQLException {
		resetTimer();
		return connection.isWrapperFor(iface);
	}

	public final synchronized Statement createStatement() throws SQLException {
		resetTimer();
		return new AutoCloseStatement(this, connection.createStatement());
	}

	public final synchronized PreparedStatement prepareStatement(String sql) throws SQLException {
		resetTimer();
		return new AutoClosePreparedStatement(this, connection.prepareStatement(sql));
	}

	public final synchronized CallableStatement prepareCall(String sql) throws SQLException {
		resetTimer();
		return new AutoCloseCallableStatement(this, connection.prepareCall(sql));
	}

	public final synchronized String nativeSQL(String sql) throws SQLException {
		resetTimer();
		return connection.nativeSQL(sql);
	}

	public final synchronized void setAutoCommit(boolean autoCommit) throws SQLException {
		resetTimer();
		connection.setAutoCommit(autoCommit);
	}

	public final synchronized boolean getAutoCommit() throws SQLException {
		resetTimer();
		return connection.getAutoCommit();
	}

	public final synchronized void commit() throws SQLException {
		resetTimer();
		connection.commit();
	}

	public final synchronized void rollback() throws SQLException {
		resetTimer();
		connection.rollback();
	}

	public final synchronized void close() throws SQLException {
		timer.cancel();
		timer.purge();
		connection.close();
	}

	public final synchronized boolean isClosed() throws SQLException {
		resetTimer();
		return connection.isClosed();
	}

	public final synchronized DatabaseMetaData getMetaData() throws SQLException {
		resetTimer();
		return connection.getMetaData();
	}

	public final synchronized void setReadOnly(boolean readOnly) throws SQLException {
		resetTimer();
		connection.setReadOnly(readOnly);
	}

	public final synchronized boolean isReadOnly() throws SQLException {
		resetTimer();
		return connection.isReadOnly();
	}

	public final synchronized void setCatalog(String catalog) throws SQLException {
		resetTimer();
		connection.setCatalog(catalog);
	}

	public final synchronized String getCatalog() throws SQLException {
		resetTimer();
		return connection.getCatalog();
	}

	public final synchronized void setTransactionIsolation(int level) throws SQLException {
		resetTimer();
		connection.setTransactionIsolation(level);
	}

	public final synchronized int getTransactionIsolation() throws SQLException {
		resetTimer();
		return connection.getTransactionIsolation();
	}

	public final synchronized SQLWarning getWarnings() throws SQLException {
		resetTimer();
		return connection.getWarnings();
	}

	public final synchronized void clearWarnings() throws SQLException {
		resetTimer();
		connection.clearWarnings();
	}

	public final synchronized Statement createStatement(int resultSetType, int resultSetConcurrency)
			throws SQLException {
		resetTimer();
		return new AutoCloseStatement(this, connection.createStatement(resultSetType, resultSetConcurrency));
	}

	public final synchronized PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
			throws SQLException {
		resetTimer();
		return new AutoClosePreparedStatement(this, connection.prepareStatement(sql, resultSetType,
				resultSetConcurrency));
	}

	public final synchronized CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency)
			throws SQLException {
		resetTimer();
		return new AutoCloseCallableStatement(this, connection.prepareCall(sql, resultSetType, resultSetConcurrency));
	}

	public final synchronized Map<String, Class<?>> getTypeMap() throws SQLException {
		resetTimer();
		return connection.getTypeMap();
	}

	public final synchronized void setTypeMap(Map<String, Class<?>> map) throws SQLException {
		resetTimer();
		connection.setTypeMap(map);
	}

	public final synchronized void setHoldability(int holdability) throws SQLException {
		resetTimer();
		connection.setHoldability(holdability);
	}

	public final synchronized int getHoldability() throws SQLException {
		resetTimer();
		return connection.getHoldability();
	}

	public final synchronized Savepoint setSavepoint() throws SQLException {
		resetTimer();
		return connection.setSavepoint();
	}

	public final synchronized Savepoint setSavepoint(String name) throws SQLException {
		resetTimer();
		return connection.setSavepoint(name);
	}

	public final synchronized void rollback(Savepoint savepoint) throws SQLException {
		resetTimer();
		connection.rollback(savepoint);
	}

	public final synchronized void releaseSavepoint(Savepoint savepoint) throws SQLException {
		resetTimer();
		connection.releaseSavepoint(savepoint);
	}

	public final synchronized Statement createStatement(int resultSetType, int resultSetConcurrency,
			int resultSetHoldability) throws SQLException {
		resetTimer();
		return new AutoCloseStatement(this, connection.createStatement(resultSetType, resultSetConcurrency,
				resultSetHoldability));
	}

	public final synchronized PreparedStatement prepareStatement(String sql, int resultSetType,
			int resultSetConcurrency, int resultSetHoldability) throws SQLException {
		resetTimer();
		return new AutoClosePreparedStatement(this, connection.prepareStatement(sql, resultSetType,
				resultSetConcurrency, resultSetHoldability));
	}

	public final synchronized CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
			int resultSetHoldability) throws SQLException {
		resetTimer();
		return new AutoCloseCallableStatement(this, connection.prepareCall(sql, resultSetType, resultSetConcurrency,
				resultSetHoldability));
	}

	public final synchronized PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
		resetTimer();
		return new AutoClosePreparedStatement(this, connection.prepareStatement(sql, autoGeneratedKeys));
	}

	public final synchronized PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
		resetTimer();
		return new AutoClosePreparedStatement(this, connection.prepareStatement(sql, columnIndexes));
	}

	public final synchronized PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
		resetTimer();
		return new AutoClosePreparedStatement(this, connection.prepareStatement(sql, columnNames));
	}

	public final synchronized Clob createClob() throws SQLException {
		resetTimer();
		return connection.createClob();
	}

	public final synchronized Blob createBlob() throws SQLException {
		resetTimer();
		return connection.createBlob();
	}

	public final synchronized NClob createNClob() throws SQLException {
		resetTimer();
		return connection.createNClob();
	}

	public final synchronized SQLXML createSQLXML() throws SQLException {
		resetTimer();
		return connection.createSQLXML();
	}

	public final synchronized boolean isValid(int timeout) throws SQLException {
		resetTimer();
		return connection.isValid(timeout);
	}

	public final synchronized void setClientInfo(String name, String value) throws SQLClientInfoException {
		resetTimer();
		connection.setClientInfo(name, value);
	}

	public final synchronized void setClientInfo(Properties properties) throws SQLClientInfoException {
		resetTimer();
		connection.setClientInfo(properties);
	}

	public final synchronized String getClientInfo(String name) throws SQLException {
		resetTimer();
		return connection.getClientInfo(name);
	}

	public final synchronized Properties getClientInfo() throws SQLException {
		resetTimer();
		return connection.getClientInfo();
	}

	public final synchronized Array createArrayOf(String typeName, Object[] elements) throws SQLException {
		resetTimer();
		return connection.createArrayOf(typeName, elements);
	}

	public final synchronized Struct createStruct(String typeName, Object[] attributes) throws SQLException {
		resetTimer();
		return connection.createStruct(typeName, attributes);
	}

	public final synchronized void setSchema(String schema) throws SQLException {
		resetTimer();
		connection.setSchema(schema);
	}

	public final synchronized String getSchema() throws SQLException {
		resetTimer();
		return connection.getSchema();
	}

	public final synchronized void abort(Executor executor) throws SQLException {
		resetTimer();
		connection.abort(executor);
	}

	public final synchronized void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
		resetTimer();
		connection.setNetworkTimeout(executor, milliseconds);
	}

	public final synchronized int getNetworkTimeout() throws SQLException {
		resetTimer();
		return connection.getNetworkTimeout();
	}

	public String toString() {
		resetTimer();
		return connection.toString();
	}

	public boolean equals(Object obj) {
		resetTimer();
		return connection.equals(obj);
	}

	public int hashCode() {
		resetTimer();
		return connection.hashCode();
	}

}
