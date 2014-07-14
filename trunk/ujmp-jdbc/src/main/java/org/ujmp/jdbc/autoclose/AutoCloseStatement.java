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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

public class AutoCloseStatement implements Statement {

	private final AutoCloseConnection connection;
	private final Statement statement;

	public AutoCloseStatement(AutoCloseConnection connection, Statement statement) {
		this.connection = connection;
		this.statement = statement;
	}

	public ResultSet executeQuery(String sql) throws SQLException {
		connection.resetTimer();
		return new AutoCloseResultSet(connection, statement.executeQuery(sql));
	}

	public int executeUpdate(String sql) throws SQLException {
		connection.resetTimer();
		return statement.executeUpdate(sql);
	}

	public void close() throws SQLException {
		connection.resetTimer();
		statement.close();
	}

	public int getMaxFieldSize() throws SQLException {
		connection.resetTimer();
		return statement.getMaxFieldSize();
	}

	public void setMaxFieldSize(int max) throws SQLException {
		connection.resetTimer();
		statement.setMaxFieldSize(max);
	}

	public int getMaxRows() throws SQLException {
		connection.resetTimer();
		return statement.getMaxRows();
	}

	public void setMaxRows(int max) throws SQLException {
		connection.resetTimer();
		statement.setMaxRows(max);
	}

	public void setEscapeProcessing(boolean enable) throws SQLException {
		connection.resetTimer();
		statement.setEscapeProcessing(enable);
	}

	public int getQueryTimeout() throws SQLException {
		connection.resetTimer();
		return statement.getQueryTimeout();
	}

	public void setQueryTimeout(int seconds) throws SQLException {
		connection.resetTimer();
		statement.setQueryTimeout(seconds);
	}

	public void cancel() throws SQLException {
		connection.resetTimer();
		statement.cancel();
	}

	public SQLWarning getWarnings() throws SQLException {
		connection.resetTimer();
		return statement.getWarnings();
	}

	public void clearWarnings() throws SQLException {
		connection.resetTimer();
		statement.clearWarnings();
	}

	public void setCursorName(String name) throws SQLException {
		connection.resetTimer();
		statement.setCursorName(name);
	}

	public boolean execute(String sql) throws SQLException {
		connection.resetTimer();
		return statement.execute(sql);
	}

	public ResultSet getResultSet() throws SQLException {
		connection.resetTimer();
		return new AutoCloseResultSet(connection, statement.getResultSet());
	}

	public int getUpdateCount() throws SQLException {
		connection.resetTimer();
		return statement.getUpdateCount();
	}

	public boolean getMoreResults() throws SQLException {
		connection.resetTimer();
		return statement.getMoreResults();
	}

	public void setFetchDirection(int direction) throws SQLException {
		connection.resetTimer();
		statement.setFetchDirection(direction);
	}

	public int getFetchDirection() throws SQLException {
		connection.resetTimer();
		return statement.getFetchDirection();
	}

	public void setFetchSize(int rows) throws SQLException {
		connection.resetTimer();
		statement.setFetchSize(rows);
	}

	public int getFetchSize() throws SQLException {
		connection.resetTimer();
		return statement.getFetchSize();
	}

	public int getResultSetConcurrency() throws SQLException {
		connection.resetTimer();
		return statement.getResultSetConcurrency();
	}

	public int getResultSetType() throws SQLException {
		connection.resetTimer();
		return statement.getResultSetType();
	}

	public void addBatch(String sql) throws SQLException {
		connection.resetTimer();
		statement.addBatch(sql);
	}

	public void clearBatch() throws SQLException {
		connection.resetTimer();
		statement.clearBatch();
	}

	public int[] executeBatch() throws SQLException {
		connection.resetTimer();
		return statement.executeBatch();
	}

	public Connection getConnection() throws SQLException {
		connection.resetTimer();
		return statement.getConnection();
	}

	public boolean getMoreResults(int current) throws SQLException {
		connection.resetTimer();
		return statement.getMoreResults(current);
	}

	public ResultSet getGeneratedKeys() throws SQLException {
		connection.resetTimer();
		return new AutoCloseResultSet(connection, statement.getGeneratedKeys());
	}

	public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
		connection.resetTimer();
		return statement.executeUpdate(sql, autoGeneratedKeys);
	}

	public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
		connection.resetTimer();
		return statement.executeUpdate(sql, columnIndexes);
	}

	public int executeUpdate(String sql, String[] columnNames) throws SQLException {
		connection.resetTimer();
		return statement.executeUpdate(sql, columnNames);
	}

	public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
		connection.resetTimer();
		return statement.execute(sql, autoGeneratedKeys);
	}

	public boolean execute(String sql, int[] columnIndexes) throws SQLException {
		connection.resetTimer();
		return statement.execute(sql, columnIndexes);
	}

	public boolean execute(String sql, String[] columnNames) throws SQLException {
		connection.resetTimer();
		return statement.execute(sql, columnNames);
	}

	public int getResultSetHoldability() throws SQLException {
		connection.resetTimer();
		return statement.getResultSetHoldability();
	}

	public boolean isClosed() throws SQLException {
		connection.resetTimer();
		return statement.isClosed();
	}

	public void setPoolable(boolean poolable) throws SQLException {
		connection.resetTimer();
		statement.setPoolable(poolable);
	}

	public boolean isPoolable() throws SQLException {
		connection.resetTimer();
		return statement.isPoolable();
	}

	public void closeOnCompletion() throws SQLException {
		connection.resetTimer();
		statement.closeOnCompletion();
	}

	public boolean isCloseOnCompletion() throws SQLException {
		connection.resetTimer();
		return statement.isCloseOnCompletion();
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		connection.resetTimer();
		return statement.unwrap(iface);
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		connection.resetTimer();
		return statement.isWrapperFor(iface);
	}

	public String toString() {
		connection.resetTimer();
		return statement.toString();
	}

	public boolean equals(Object obj) {
		connection.resetTimer();
		return statement.equals(obj);
	}

	public int hashCode() {
		connection.resetTimer();
		return statement.hashCode();
	}

}
