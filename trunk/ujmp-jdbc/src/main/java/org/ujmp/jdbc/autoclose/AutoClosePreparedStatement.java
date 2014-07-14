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

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

public class AutoClosePreparedStatement implements PreparedStatement {

	private final AutoCloseConnection connection;
	private final PreparedStatement statement;

	public AutoClosePreparedStatement(AutoCloseConnection connection, PreparedStatement statement) {
		this.connection = connection;
		this.statement = statement;
	}

	public ResultSet executeQuery() throws SQLException {
		connection.resetTimer();
		return new AutoCloseResultSet(connection, statement.executeQuery());
	}

	public int executeUpdate() throws SQLException {
		connection.resetTimer();
		return statement.executeUpdate();
	}

	public void setNull(int parameterIndex, int sqlType) throws SQLException {
		connection.resetTimer();
		statement.setNull(parameterIndex, sqlType);
	}

	public void setBoolean(int parameterIndex, boolean x) throws SQLException {
		connection.resetTimer();
		statement.setBoolean(parameterIndex, x);
	}

	public void setByte(int parameterIndex, byte x) throws SQLException {
		connection.resetTimer();
		statement.setByte(parameterIndex, x);
	}

	public void setShort(int parameterIndex, short x) throws SQLException {
		connection.resetTimer();
		statement.setShort(parameterIndex, x);
	}

	public void setInt(int parameterIndex, int x) throws SQLException {
		connection.resetTimer();
		statement.setInt(parameterIndex, x);
	}

	public void setLong(int parameterIndex, long x) throws SQLException {
		connection.resetTimer();
		statement.setLong(parameterIndex, x);
	}

	public void setFloat(int parameterIndex, float x) throws SQLException {
		connection.resetTimer();
		statement.setFloat(parameterIndex, x);
	}

	public void setDouble(int parameterIndex, double x) throws SQLException {
		connection.resetTimer();
		statement.setDouble(parameterIndex, x);
	}

	public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
		connection.resetTimer();
		statement.setBigDecimal(parameterIndex, x);
	}

	public void setString(int parameterIndex, String x) throws SQLException {
		connection.resetTimer();
		statement.setString(parameterIndex, x);
	}

	public void setBytes(int parameterIndex, byte[] x) throws SQLException {
		connection.resetTimer();
		statement.setBytes(parameterIndex, x);
	}

	public void setDate(int parameterIndex, Date x) throws SQLException {
		connection.resetTimer();
		statement.setDate(parameterIndex, x);
	}

	public void setTime(int parameterIndex, Time x) throws SQLException {
		connection.resetTimer();
		statement.setTime(parameterIndex, x);
	}

	public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
		connection.resetTimer();
		statement.setTimestamp(parameterIndex, x);
	}

	public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
		connection.resetTimer();
		statement.setAsciiStream(parameterIndex, x);
	}

	@SuppressWarnings("deprecation")
	public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
		connection.resetTimer();
		statement.setUnicodeStream(parameterIndex, x, length);
	}

	public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
		connection.resetTimer();
		statement.setBinaryStream(parameterIndex, x);
	}

	public void clearParameters() throws SQLException {
		connection.resetTimer();
		statement.clearParameters();
	}

	public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
		connection.resetTimer();
		statement.setObject(parameterIndex, x, targetSqlType);
	}

	public void setObject(int parameterIndex, Object x) throws SQLException {
		connection.resetTimer();
		statement.setObject(parameterIndex, x);
	}

	public boolean execute() throws SQLException {
		connection.resetTimer();
		return statement.execute();
	}

	public void addBatch() throws SQLException {
		connection.resetTimer();
		statement.addBatch();
	}

	public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
		connection.resetTimer();
		statement.setCharacterStream(parameterIndex, reader, length);
	}

	public void setRef(int parameterIndex, Ref x) throws SQLException {
		connection.resetTimer();
		statement.setRef(parameterIndex, x);
	}

	public void setBlob(int parameterIndex, Blob x) throws SQLException {
		connection.resetTimer();
		statement.setBlob(parameterIndex, x);
	}

	public void setClob(int parameterIndex, Clob x) throws SQLException {
		connection.resetTimer();
		statement.setClob(parameterIndex, x);
	}

	public void setArray(int parameterIndex, Array x) throws SQLException {
		connection.resetTimer();
		statement.setArray(parameterIndex, x);
	}

	public ResultSetMetaData getMetaData() throws SQLException {
		connection.resetTimer();
		return statement.getMetaData();
	}

	public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
		connection.resetTimer();
		statement.setDate(parameterIndex, x, cal);
	}

	public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
		connection.resetTimer();
		statement.setTime(parameterIndex, x, cal);
	}

	public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
		connection.resetTimer();
		statement.setTimestamp(parameterIndex, x, cal);
	}

	public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {
		connection.resetTimer();
		statement.setNull(parameterIndex, sqlType, typeName);
	}

	public void setURL(int parameterIndex, URL x) throws SQLException {
		connection.resetTimer();
		statement.setURL(parameterIndex, x);
	}

	public ParameterMetaData getParameterMetaData() throws SQLException {
		connection.resetTimer();
		return statement.getParameterMetaData();
	}

	public void setRowId(int parameterIndex, RowId x) throws SQLException {
		connection.resetTimer();
		statement.setRowId(parameterIndex, x);
	}

	public void setNString(int parameterIndex, String value) throws SQLException {
		connection.resetTimer();
		statement.setNString(parameterIndex, value);
	}

	public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {
		connection.resetTimer();
		statement.setNCharacterStream(parameterIndex, value, length);
	}

	public void setNClob(int parameterIndex, NClob value) throws SQLException {
		connection.resetTimer();
		statement.setNClob(parameterIndex, value);
	}

	public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
		connection.resetTimer();
		statement.setClob(parameterIndex, reader, length);
	}

	public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {
		connection.resetTimer();
		statement.setBlob(parameterIndex, inputStream, length);
	}

	public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
		connection.resetTimer();
		statement.setNClob(parameterIndex, reader, length);
	}

	public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
		connection.resetTimer();
		statement.setSQLXML(parameterIndex, xmlObject);
	}

	public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException {
		connection.resetTimer();
		statement.setObject(parameterIndex, x, targetSqlType, scaleOrLength);
	}

	public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
		connection.resetTimer();
		statement.setAsciiStream(parameterIndex, x, length);
	}

	public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
		connection.resetTimer();
		statement.setBinaryStream(parameterIndex, x, length);
	}

	public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
		connection.resetTimer();
		statement.setCharacterStream(parameterIndex, reader, length);
	}

	public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
		connection.resetTimer();
		statement.setAsciiStream(parameterIndex, x);
	}

	public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
		connection.resetTimer();
		statement.setBinaryStream(parameterIndex, x);
	}

	public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
		connection.resetTimer();
		statement.setCharacterStream(parameterIndex, reader);
	}

	public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
		connection.resetTimer();
		statement.setNCharacterStream(parameterIndex, value);
	}

	public void setClob(int parameterIndex, Reader reader) throws SQLException {
		connection.resetTimer();
		statement.setClob(parameterIndex, reader);
	}

	public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
		connection.resetTimer();
		statement.setBlob(parameterIndex, inputStream);
	}

	public void setNClob(int parameterIndex, Reader reader) throws SQLException {
		connection.resetTimer();
		statement.setNClob(parameterIndex, reader);
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
		try {
			return statement.isClosed();
		} catch (AbstractMethodError e) {
			// SQLite Statement seems to be closed after it is executed once
			return true;
		}
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
