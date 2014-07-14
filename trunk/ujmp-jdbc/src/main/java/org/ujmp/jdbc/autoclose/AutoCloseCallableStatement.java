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
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
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
import java.util.Map;

public class AutoCloseCallableStatement implements CallableStatement {

	private final AutoCloseConnection connection;
	private final CallableStatement statement;

	public AutoCloseCallableStatement(AutoCloseConnection connection, CallableStatement statement) {
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

	public void registerOutParameter(int parameterIndex, int sqlType) throws SQLException {
		connection.resetTimer();
		statement.registerOutParameter(parameterIndex, sqlType);
	}

	public void registerOutParameter(int parameterIndex, int sqlType, int scale) throws SQLException {
		connection.resetTimer();
		statement.registerOutParameter(parameterIndex, sqlType, scale);
	}

	public boolean wasNull() throws SQLException {
		connection.resetTimer();
		return statement.wasNull();
	}

	public String getString(int parameterIndex) throws SQLException {
		connection.resetTimer();
		return statement.getString(parameterIndex);
	}

	public boolean getBoolean(int parameterIndex) throws SQLException {
		connection.resetTimer();
		return statement.getBoolean(parameterIndex);
	}

	public byte getByte(int parameterIndex) throws SQLException {
		connection.resetTimer();
		return statement.getByte(parameterIndex);
	}

	public short getShort(int parameterIndex) throws SQLException {
		connection.resetTimer();
		return statement.getShort(parameterIndex);
	}

	public int getInt(int parameterIndex) throws SQLException {
		connection.resetTimer();
		return statement.getInt(parameterIndex);
	}

	public long getLong(int parameterIndex) throws SQLException {
		connection.resetTimer();
		return statement.getLong(parameterIndex);
	}

	public float getFloat(int parameterIndex) throws SQLException {
		connection.resetTimer();
		return statement.getFloat(parameterIndex);
	}

	public double getDouble(int parameterIndex) throws SQLException {
		connection.resetTimer();
		return statement.getDouble(parameterIndex);
	}

	@SuppressWarnings("deprecation")
	public BigDecimal getBigDecimal(int parameterIndex, int scale) throws SQLException {
		connection.resetTimer();
		return statement.getBigDecimal(parameterIndex, scale);
	}

	public byte[] getBytes(int parameterIndex) throws SQLException {
		connection.resetTimer();
		return statement.getBytes(parameterIndex);
	}

	public Date getDate(int parameterIndex) throws SQLException {
		connection.resetTimer();
		return statement.getDate(parameterIndex);
	}

	public Time getTime(int parameterIndex) throws SQLException {
		connection.resetTimer();
		return statement.getTime(parameterIndex);
	}

	public Timestamp getTimestamp(int parameterIndex) throws SQLException {
		connection.resetTimer();
		return statement.getTimestamp(parameterIndex);
	}

	public Object getObject(int parameterIndex) throws SQLException {
		connection.resetTimer();
		return statement.getObject(parameterIndex);
	}

	public BigDecimal getBigDecimal(int parameterIndex) throws SQLException {
		connection.resetTimer();
		return statement.getBigDecimal(parameterIndex);
	}

	public Object getObject(int parameterIndex, Map<String, Class<?>> map) throws SQLException {
		connection.resetTimer();
		return statement.getObject(parameterIndex, map);
	}

	public Ref getRef(int parameterIndex) throws SQLException {
		connection.resetTimer();
		return statement.getRef(parameterIndex);
	}

	public Blob getBlob(int parameterIndex) throws SQLException {
		connection.resetTimer();
		return statement.getBlob(parameterIndex);
	}

	public Clob getClob(int parameterIndex) throws SQLException {
		connection.resetTimer();
		return statement.getClob(parameterIndex);
	}

	public Array getArray(int parameterIndex) throws SQLException {
		connection.resetTimer();
		return statement.getArray(parameterIndex);
	}

	public Date getDate(int parameterIndex, Calendar cal) throws SQLException {
		connection.resetTimer();
		return statement.getDate(parameterIndex, cal);
	}

	public Time getTime(int parameterIndex, Calendar cal) throws SQLException {
		connection.resetTimer();
		return statement.getTime(parameterIndex, cal);
	}

	public Timestamp getTimestamp(int parameterIndex, Calendar cal) throws SQLException {
		connection.resetTimer();
		return statement.getTimestamp(parameterIndex, cal);
	}

	public void registerOutParameter(int parameterIndex, int sqlType, String typeName) throws SQLException {
		connection.resetTimer();
		statement.registerOutParameter(parameterIndex, sqlType, typeName);
	}

	public void registerOutParameter(String parameterName, int sqlType) throws SQLException {
		connection.resetTimer();
		statement.registerOutParameter(parameterName, sqlType);
	}

	public void registerOutParameter(String parameterName, int sqlType, int scale) throws SQLException {
		connection.resetTimer();
		statement.registerOutParameter(parameterName, sqlType, scale);
	}

	public void registerOutParameter(String parameterName, int sqlType, String typeName) throws SQLException {
		connection.resetTimer();
		statement.registerOutParameter(parameterName, sqlType, typeName);
	}

	public URL getURL(int parameterIndex) throws SQLException {
		connection.resetTimer();
		return statement.getURL(parameterIndex);
	}

	public void setURL(String parameterName, URL val) throws SQLException {
		connection.resetTimer();
		statement.setURL(parameterName, val);
	}

	public void setNull(String parameterName, int sqlType) throws SQLException {
		connection.resetTimer();
		statement.setNull(parameterName, sqlType);
	}

	public void setBoolean(String parameterName, boolean x) throws SQLException {
		connection.resetTimer();
		statement.setBoolean(parameterName, x);
	}

	public void setByte(String parameterName, byte x) throws SQLException {
		connection.resetTimer();
		statement.setByte(parameterName, x);
	}

	public void setShort(String parameterName, short x) throws SQLException {
		connection.resetTimer();
		statement.setShort(parameterName, x);
	}

	public void setInt(String parameterName, int x) throws SQLException {
		connection.resetTimer();
		statement.setInt(parameterName, x);
	}

	public void setLong(String parameterName, long x) throws SQLException {
		connection.resetTimer();
		statement.setLong(parameterName, x);
	}

	public void setFloat(String parameterName, float x) throws SQLException {
		connection.resetTimer();
		statement.setFloat(parameterName, x);
	}

	public void setDouble(String parameterName, double x) throws SQLException {
		connection.resetTimer();
		statement.setDouble(parameterName, x);
	}

	public void setBigDecimal(String parameterName, BigDecimal x) throws SQLException {
		connection.resetTimer();
		statement.setBigDecimal(parameterName, x);
	}

	public void setString(String parameterName, String x) throws SQLException {
		connection.resetTimer();
		statement.setString(parameterName, x);
	}

	public void setBytes(String parameterName, byte[] x) throws SQLException {
		connection.resetTimer();
		statement.setBytes(parameterName, x);
	}

	public void setDate(String parameterName, Date x) throws SQLException {
		connection.resetTimer();
		statement.setDate(parameterName, x);
	}

	public void setTime(String parameterName, Time x) throws SQLException {
		connection.resetTimer();
		statement.setTime(parameterName, x);
	}

	public void setTimestamp(String parameterName, Timestamp x) throws SQLException {
		connection.resetTimer();
		statement.setTimestamp(parameterName, x);
	}

	public void setAsciiStream(String parameterName, InputStream x, int length) throws SQLException {
		connection.resetTimer();
		statement.setAsciiStream(parameterName, x, length);
	}

	public void setBinaryStream(String parameterName, InputStream x, int length) throws SQLException {
		connection.resetTimer();
		statement.setBinaryStream(parameterName, x, length);
	}

	public void setObject(String parameterName, Object x, int targetSqlType, int scale) throws SQLException {
		connection.resetTimer();
		statement.setObject(parameterName, x, targetSqlType, scale);
	}

	public void setObject(String parameterName, Object x, int targetSqlType) throws SQLException {
		connection.resetTimer();
		statement.setObject(parameterName, x, targetSqlType);
	}

	public void setObject(String parameterName, Object x) throws SQLException {
		connection.resetTimer();
		statement.setObject(parameterName, x);
	}

	public void setCharacterStream(String parameterName, Reader reader, int length) throws SQLException {
		connection.resetTimer();
		statement.setCharacterStream(parameterName, reader, length);
	}

	public void setDate(String parameterName, Date x, Calendar cal) throws SQLException {
		connection.resetTimer();
		statement.setDate(parameterName, x, cal);
	}

	public void setTime(String parameterName, Time x, Calendar cal) throws SQLException {
		connection.resetTimer();
		statement.setTime(parameterName, x, cal);
	}

	public void setTimestamp(String parameterName, Timestamp x, Calendar cal) throws SQLException {
		connection.resetTimer();
		statement.setTimestamp(parameterName, x, cal);
	}

	public void setNull(String parameterName, int sqlType, String typeName) throws SQLException {
		connection.resetTimer();
		statement.setNull(parameterName, sqlType, typeName);
	}

	public String getString(String parameterName) throws SQLException {
		connection.resetTimer();
		return statement.getString(parameterName);
	}

	public boolean getBoolean(String parameterName) throws SQLException {
		connection.resetTimer();
		return statement.getBoolean(parameterName);
	}

	public byte getByte(String parameterName) throws SQLException {
		connection.resetTimer();
		return statement.getByte(parameterName);
	}

	public short getShort(String parameterName) throws SQLException {
		connection.resetTimer();
		return statement.getShort(parameterName);
	}

	public int getInt(String parameterName) throws SQLException {
		connection.resetTimer();
		return statement.getInt(parameterName);
	}

	public long getLong(String parameterName) throws SQLException {
		connection.resetTimer();
		return statement.getLong(parameterName);
	}

	public float getFloat(String parameterName) throws SQLException {
		connection.resetTimer();
		return statement.getFloat(parameterName);
	}

	public double getDouble(String parameterName) throws SQLException {
		connection.resetTimer();
		return statement.getDouble(parameterName);
	}

	public byte[] getBytes(String parameterName) throws SQLException {
		connection.resetTimer();
		return statement.getBytes(parameterName);
	}

	public Date getDate(String parameterName) throws SQLException {
		connection.resetTimer();
		return statement.getDate(parameterName);
	}

	public Time getTime(String parameterName) throws SQLException {
		connection.resetTimer();
		return statement.getTime(parameterName);
	}

	public Timestamp getTimestamp(String parameterName) throws SQLException {
		connection.resetTimer();
		return statement.getTimestamp(parameterName);
	}

	public Object getObject(String parameterName) throws SQLException {
		connection.resetTimer();
		return statement.getObject(parameterName);
	}

	public BigDecimal getBigDecimal(String parameterName) throws SQLException {
		connection.resetTimer();
		return statement.getBigDecimal(parameterName);
	}

	public Object getObject(String parameterName, Map<String, Class<?>> map) throws SQLException {
		connection.resetTimer();
		return statement.getObject(parameterName, map);
	}

	public Ref getRef(String parameterName) throws SQLException {
		connection.resetTimer();
		return statement.getRef(parameterName);
	}

	public Blob getBlob(String parameterName) throws SQLException {
		connection.resetTimer();
		return statement.getBlob(parameterName);
	}

	public Clob getClob(String parameterName) throws SQLException {
		connection.resetTimer();
		return statement.getNClob(parameterName);
	}

	public Array getArray(String parameterName) throws SQLException {
		connection.resetTimer();
		return statement.getArray(parameterName);
	}

	public Date getDate(String parameterName, Calendar cal) throws SQLException {
		connection.resetTimer();
		return statement.getDate(parameterName, cal);
	}

	public Time getTime(String parameterName, Calendar cal) throws SQLException {
		connection.resetTimer();
		return statement.getTime(parameterName, cal);
	}

	public Timestamp getTimestamp(String parameterName, Calendar cal) throws SQLException {
		connection.resetTimer();
		return statement.getTimestamp(parameterName, cal);
	}

	public URL getURL(String parameterName) throws SQLException {
		connection.resetTimer();
		return statement.getURL(parameterName);
	}

	public RowId getRowId(int parameterIndex) throws SQLException {
		connection.resetTimer();
		return statement.getRowId(parameterIndex);
	}

	public RowId getRowId(String parameterName) throws SQLException {
		connection.resetTimer();
		return statement.getRowId(parameterName);
	}

	public void setRowId(String parameterName, RowId x) throws SQLException {
		connection.resetTimer();
		statement.setRowId(parameterName, x);
	}

	public void setNString(String parameterName, String value) throws SQLException {
		connection.resetTimer();
		statement.setNString(parameterName, value);
	}

	public void setNCharacterStream(String parameterName, Reader value, long length) throws SQLException {
		connection.resetTimer();
		statement.setNCharacterStream(parameterName, value, length);
	}

	public void setNClob(String parameterName, NClob value) throws SQLException {
		connection.resetTimer();
		statement.setNClob(parameterName, value);
	}

	public void setClob(String parameterName, Reader reader, long length) throws SQLException {
		connection.resetTimer();
		statement.setClob(parameterName, reader, length);
	}

	public void setBlob(String parameterName, InputStream inputStream, long length) throws SQLException {
		connection.resetTimer();
		statement.setBlob(parameterName, inputStream, length);
	}

	public void setNClob(String parameterName, Reader reader, long length) throws SQLException {
		connection.resetTimer();
		statement.setNClob(parameterName, reader, length);
	}

	public NClob getNClob(int parameterIndex) throws SQLException {
		connection.resetTimer();
		return statement.getNClob(parameterIndex);
	}

	public NClob getNClob(String parameterName) throws SQLException {
		connection.resetTimer();
		return statement.getNClob(parameterName);
	}

	public void setSQLXML(String parameterName, SQLXML xmlObject) throws SQLException {
		connection.resetTimer();
		statement.setSQLXML(parameterName, xmlObject);
	}

	public SQLXML getSQLXML(int parameterIndex) throws SQLException {
		connection.resetTimer();
		return statement.getSQLXML(parameterIndex);
	}

	public SQLXML getSQLXML(String parameterName) throws SQLException {
		connection.resetTimer();
		return statement.getSQLXML(parameterName);
	}

	public String getNString(int parameterIndex) throws SQLException {
		connection.resetTimer();
		return statement.getNString(parameterIndex);
	}

	public String getNString(String parameterName) throws SQLException {
		connection.resetTimer();
		return statement.getNString(parameterName);
	}

	public Reader getNCharacterStream(int parameterIndex) throws SQLException {
		connection.resetTimer();
		return statement.getNCharacterStream(parameterIndex);
	}

	public Reader getNCharacterStream(String parameterName) throws SQLException {
		connection.resetTimer();
		return statement.getNCharacterStream(parameterName);
	}

	public Reader getCharacterStream(int parameterIndex) throws SQLException {
		connection.resetTimer();
		return statement.getCharacterStream(parameterIndex);
	}

	public Reader getCharacterStream(String parameterName) throws SQLException {
		connection.resetTimer();
		return statement.getCharacterStream(parameterName);
	}

	public void setBlob(String parameterName, Blob x) throws SQLException {
		connection.resetTimer();
		statement.setBlob(parameterName, x);
	}

	public void setClob(String parameterName, Clob x) throws SQLException {
		connection.resetTimer();
		statement.setClob(parameterName, x);
	}

	public void setAsciiStream(String parameterName, InputStream x, long length) throws SQLException {
		connection.resetTimer();
		statement.setAsciiStream(parameterName, x, length);
	}

	public void setBinaryStream(String parameterName, InputStream x, long length) throws SQLException {
		connection.resetTimer();
		statement.setBinaryStream(parameterName, x, length);
	}

	public void setCharacterStream(String parameterName, Reader reader, long length) throws SQLException {
		connection.resetTimer();
		statement.setCharacterStream(parameterName, reader, length);
	}

	public void setAsciiStream(String parameterName, InputStream x) throws SQLException {
		connection.resetTimer();
		statement.setAsciiStream(parameterName, x);
	}

	public void setBinaryStream(String parameterName, InputStream x) throws SQLException {
		connection.resetTimer();
		statement.setBinaryStream(parameterName, x);
	}

	public void setCharacterStream(String parameterName, Reader reader) throws SQLException {
		connection.resetTimer();
		statement.setCharacterStream(parameterName, reader);
	}

	public void setNCharacterStream(String parameterName, Reader value) throws SQLException {
		connection.resetTimer();
		statement.setNCharacterStream(parameterName, value);
	}

	public void setClob(String parameterName, Reader reader) throws SQLException {
		connection.resetTimer();
		statement.setClob(parameterName, reader);
	}

	public void setBlob(String parameterName, InputStream inputStream) throws SQLException {
		connection.resetTimer();
		statement.setBlob(parameterName, inputStream);
	}

	public void setNClob(String parameterName, Reader reader) throws SQLException {
		connection.resetTimer();
		statement.setNClob(parameterName, reader);
	}

	public <T> T getObject(int parameterIndex, Class<T> type) throws SQLException {
		connection.resetTimer();
		return statement.getObject(parameterIndex, type);
	}

	public <T> T getObject(String parameterName, Class<T> type) throws SQLException {
		connection.resetTimer();
		return statement.getObject(parameterName, type);
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
