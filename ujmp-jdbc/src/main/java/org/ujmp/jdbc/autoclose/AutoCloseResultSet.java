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

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

public class AutoCloseResultSet implements ResultSet {

	private final AutoCloseConnection connection;
	private final ResultSet resultSet;

	public AutoCloseResultSet(AutoCloseConnection connection, ResultSet resultSet) {
		this.connection = connection;
		this.resultSet = resultSet;
	}

	public ResultSetMetaData getMetaData() throws SQLException {
		connection.resetTimer();
		return resultSet.getMetaData();
	}

	public void close() throws SQLException {
		connection.resetTimer();
		resultSet.close();
	}

	public SQLWarning getWarnings() throws SQLException {
		connection.resetTimer();
		return resultSet.getWarnings();
	}

	public void clearWarnings() throws SQLException {
		connection.resetTimer();
		resultSet.clearWarnings();
	}

	public void setFetchDirection(int direction) throws SQLException {
		connection.resetTimer();
		resultSet.setFetchDirection(direction);
	}

	public int getFetchDirection() throws SQLException {
		connection.resetTimer();
		return resultSet.getFetchDirection();
	}

	public void setFetchSize(int rows) throws SQLException {
		connection.resetTimer();
		resultSet.setFetchSize(rows);
	}

	public int getFetchSize() throws SQLException {
		connection.resetTimer();
		return resultSet.getFetchSize();
	}

	public boolean isClosed() throws SQLException {
		connection.resetTimer();
		try {
			return resultSet.isClosed();
		} catch (AbstractMethodError e) {
			// isClosed() is abstract in SQLite
			return false;
		}
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		connection.resetTimer();
		return resultSet.unwrap(iface);
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		connection.resetTimer();
		return resultSet.isWrapperFor(iface);
	}

	public boolean wasNull() throws SQLException {
		connection.resetTimer();
		return resultSet.wasNull();
	}

	public String getString(int parameterIndex) throws SQLException {
		connection.resetTimer();
		return resultSet.getString(parameterIndex);
	}

	public boolean getBoolean(int parameterIndex) throws SQLException {
		connection.resetTimer();
		return resultSet.getBoolean(parameterIndex);
	}

	public byte getByte(int parameterIndex) throws SQLException {
		connection.resetTimer();
		return resultSet.getByte(parameterIndex);
	}

	public short getShort(int parameterIndex) throws SQLException {
		connection.resetTimer();
		return resultSet.getShort(parameterIndex);
	}

	public int getInt(int parameterIndex) throws SQLException {
		connection.resetTimer();
		return resultSet.getInt(parameterIndex);
	}

	public long getLong(int parameterIndex) throws SQLException {
		connection.resetTimer();
		return resultSet.getLong(parameterIndex);
	}

	public float getFloat(int parameterIndex) throws SQLException {
		connection.resetTimer();
		return resultSet.getFloat(parameterIndex);
	}

	public double getDouble(int parameterIndex) throws SQLException {
		connection.resetTimer();
		return resultSet.getDouble(parameterIndex);
	}

	@SuppressWarnings("deprecation")
	public BigDecimal getBigDecimal(int parameterIndex, int scale) throws SQLException {
		connection.resetTimer();
		return resultSet.getBigDecimal(parameterIndex, scale);
	}

	public byte[] getBytes(int parameterIndex) throws SQLException {
		connection.resetTimer();
		return resultSet.getBytes(parameterIndex);
	}

	public Date getDate(int parameterIndex) throws SQLException {
		connection.resetTimer();
		return resultSet.getDate(parameterIndex);
	}

	public Time getTime(int parameterIndex) throws SQLException {
		connection.resetTimer();
		return resultSet.getTime(parameterIndex);
	}

	public Timestamp getTimestamp(int parameterIndex) throws SQLException {
		connection.resetTimer();
		return resultSet.getTimestamp(parameterIndex);
	}

	public Object getObject(int parameterIndex) throws SQLException {
		connection.resetTimer();
		return resultSet.getObject(parameterIndex);
	}

	public BigDecimal getBigDecimal(int parameterIndex) throws SQLException {
		connection.resetTimer();
		return resultSet.getBigDecimal(parameterIndex);
	}

	public Object getObject(int parameterIndex, Map<String, Class<?>> map) throws SQLException {
		connection.resetTimer();
		return resultSet.getObject(parameterIndex, map);
	}

	public Ref getRef(int parameterIndex) throws SQLException {
		connection.resetTimer();
		return resultSet.getRef(parameterIndex);
	}

	public Blob getBlob(int parameterIndex) throws SQLException {
		connection.resetTimer();
		return resultSet.getBlob(parameterIndex);
	}

	public Clob getClob(int parameterIndex) throws SQLException {
		connection.resetTimer();
		return resultSet.getClob(parameterIndex);
	}

	public Array getArray(int parameterIndex) throws SQLException {
		connection.resetTimer();
		return resultSet.getArray(parameterIndex);
	}

	public Date getDate(int parameterIndex, Calendar cal) throws SQLException {
		connection.resetTimer();
		return resultSet.getDate(parameterIndex, cal);
	}

	public Time getTime(int parameterIndex, Calendar cal) throws SQLException {
		connection.resetTimer();
		return resultSet.getTime(parameterIndex, cal);
	}

	public Timestamp getTimestamp(int parameterIndex, Calendar cal) throws SQLException {
		connection.resetTimer();
		return resultSet.getTimestamp(parameterIndex, cal);
	}

	public URL getURL(int parameterIndex) throws SQLException {
		connection.resetTimer();
		return resultSet.getURL(parameterIndex);
	}

	public String getString(String parameterName) throws SQLException {
		connection.resetTimer();
		return resultSet.getString(parameterName);
	}

	public boolean getBoolean(String parameterName) throws SQLException {
		connection.resetTimer();
		return resultSet.getBoolean(parameterName);
	}

	public byte getByte(String parameterName) throws SQLException {
		connection.resetTimer();
		return resultSet.getByte(parameterName);
	}

	public short getShort(String parameterName) throws SQLException {
		connection.resetTimer();
		return resultSet.getShort(parameterName);
	}

	public int getInt(String parameterName) throws SQLException {
		connection.resetTimer();
		return resultSet.getInt(parameterName);
	}

	public long getLong(String parameterName) throws SQLException {
		connection.resetTimer();
		return resultSet.getLong(parameterName);
	}

	public float getFloat(String parameterName) throws SQLException {
		connection.resetTimer();
		return resultSet.getFloat(parameterName);
	}

	public double getDouble(String parameterName) throws SQLException {
		connection.resetTimer();
		return resultSet.getDouble(parameterName);
	}

	public byte[] getBytes(String parameterName) throws SQLException {
		connection.resetTimer();
		return resultSet.getBytes(parameterName);
	}

	public Date getDate(String parameterName) throws SQLException {
		connection.resetTimer();
		return resultSet.getDate(parameterName);
	}

	public Time getTime(String parameterName) throws SQLException {
		connection.resetTimer();
		return resultSet.getTime(parameterName);
	}

	public Timestamp getTimestamp(String parameterName) throws SQLException {
		connection.resetTimer();
		return resultSet.getTimestamp(parameterName);
	}

	public Object getObject(String parameterName) throws SQLException {
		connection.resetTimer();
		return resultSet.getObject(parameterName);
	}

	public BigDecimal getBigDecimal(String parameterName) throws SQLException {
		connection.resetTimer();
		return resultSet.getBigDecimal(parameterName);
	}

	public Object getObject(String parameterName, Map<String, Class<?>> map) throws SQLException {
		connection.resetTimer();
		return resultSet.getObject(parameterName, map);
	}

	public Ref getRef(String parameterName) throws SQLException {
		connection.resetTimer();
		return resultSet.getRef(parameterName);
	}

	public Blob getBlob(String parameterName) throws SQLException {
		connection.resetTimer();
		return resultSet.getBlob(parameterName);
	}

	public Clob getClob(String parameterName) throws SQLException {
		connection.resetTimer();
		return resultSet.getNClob(parameterName);
	}

	public Array getArray(String parameterName) throws SQLException {
		connection.resetTimer();
		return resultSet.getArray(parameterName);
	}

	public Date getDate(String parameterName, Calendar cal) throws SQLException {
		connection.resetTimer();
		return resultSet.getDate(parameterName, cal);
	}

	public Time getTime(String parameterName, Calendar cal) throws SQLException {
		connection.resetTimer();
		return resultSet.getTime(parameterName, cal);
	}

	public Timestamp getTimestamp(String parameterName, Calendar cal) throws SQLException {
		connection.resetTimer();
		return resultSet.getTimestamp(parameterName, cal);
	}

	public URL getURL(String parameterName) throws SQLException {
		connection.resetTimer();
		return resultSet.getURL(parameterName);
	}

	public RowId getRowId(int parameterIndex) throws SQLException {
		connection.resetTimer();
		return resultSet.getRowId(parameterIndex);
	}

	public RowId getRowId(String parameterName) throws SQLException {
		connection.resetTimer();
		return resultSet.getRowId(parameterName);
	}

	public NClob getNClob(int parameterIndex) throws SQLException {
		connection.resetTimer();
		return resultSet.getNClob(parameterIndex);
	}

	public NClob getNClob(String parameterName) throws SQLException {
		connection.resetTimer();
		return resultSet.getNClob(parameterName);
	}

	public SQLXML getSQLXML(int parameterIndex) throws SQLException {
		connection.resetTimer();
		return resultSet.getSQLXML(parameterIndex);
	}

	public SQLXML getSQLXML(String parameterName) throws SQLException {
		connection.resetTimer();
		return resultSet.getSQLXML(parameterName);
	}

	public String getNString(int parameterIndex) throws SQLException {
		connection.resetTimer();
		return resultSet.getNString(parameterIndex);
	}

	public String getNString(String parameterName) throws SQLException {
		connection.resetTimer();
		return resultSet.getNString(parameterName);
	}

	public Reader getNCharacterStream(int parameterIndex) throws SQLException {
		connection.resetTimer();
		return resultSet.getNCharacterStream(parameterIndex);
	}

	public Reader getNCharacterStream(String parameterName) throws SQLException {
		connection.resetTimer();
		return resultSet.getNCharacterStream(parameterName);
	}

	public Reader getCharacterStream(int parameterIndex) throws SQLException {
		connection.resetTimer();
		return resultSet.getCharacterStream(parameterIndex);
	}

	public Reader getCharacterStream(String parameterName) throws SQLException {
		connection.resetTimer();
		return resultSet.getCharacterStream(parameterName);
	}

	public <T> T getObject(int parameterIndex, Class<T> type) throws SQLException {
		connection.resetTimer();
		return resultSet.getObject(parameterIndex, type);
	}

	public <T> T getObject(String parameterName, Class<T> type) throws SQLException {
		connection.resetTimer();
		return resultSet.getObject(parameterName, type);
	}

	public boolean next() throws SQLException {
		connection.resetTimer();
		return resultSet.next();
	}

	public InputStream getAsciiStream(int columnIndex) throws SQLException {
		connection.resetTimer();
		return resultSet.getAsciiStream(columnIndex);
	}

	@SuppressWarnings("deprecation")
	public InputStream getUnicodeStream(int columnIndex) throws SQLException {
		connection.resetTimer();
		return resultSet.getUnicodeStream(columnIndex);
	}

	public InputStream getBinaryStream(int columnIndex) throws SQLException {
		connection.resetTimer();
		return resultSet.getBinaryStream(columnIndex);
	}

	@SuppressWarnings("deprecation")
	public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
		connection.resetTimer();
		return resultSet.getBigDecimal(columnLabel, scale);
	}

	public InputStream getAsciiStream(String columnLabel) throws SQLException {
		connection.resetTimer();
		return resultSet.getAsciiStream(columnLabel);
	}

	@SuppressWarnings("deprecation")
	public InputStream getUnicodeStream(String columnLabel) throws SQLException {
		connection.resetTimer();
		return resultSet.getUnicodeStream(columnLabel);
	}

	public InputStream getBinaryStream(String columnLabel) throws SQLException {
		connection.resetTimer();
		return resultSet.getBinaryStream(columnLabel);
	}

	public String getCursorName() throws SQLException {
		connection.resetTimer();
		return resultSet.getCursorName();
	}

	public int findColumn(String columnLabel) throws SQLException {
		connection.resetTimer();
		return resultSet.findColumn(columnLabel);
	}

	public boolean isBeforeFirst() throws SQLException {
		connection.resetTimer();
		return resultSet.isBeforeFirst();
	}

	public boolean isAfterLast() throws SQLException {
		connection.resetTimer();
		return resultSet.isAfterLast();
	}

	public boolean isFirst() throws SQLException {
		connection.resetTimer();
		return resultSet.isFirst();
	}

	public boolean isLast() throws SQLException {
		connection.resetTimer();
		return resultSet.isLast();
	}

	public void beforeFirst() throws SQLException {
		connection.resetTimer();
		resultSet.beforeFirst();
	}

	public void afterLast() throws SQLException {
		connection.resetTimer();
		resultSet.afterLast();
	}

	public boolean first() throws SQLException {
		connection.resetTimer();
		return resultSet.first();
	}

	public boolean last() throws SQLException {
		connection.resetTimer();
		return resultSet.last();
	}

	public int getRow() throws SQLException {
		connection.resetTimer();
		return resultSet.getRow();
	}

	public boolean absolute(int row) throws SQLException {
		connection.resetTimer();
		return resultSet.absolute(row);
	}

	public boolean relative(int rows) throws SQLException {
		connection.resetTimer();
		return resultSet.relative(rows);
	}

	public boolean previous() throws SQLException {
		connection.resetTimer();
		return resultSet.previous();
	}

	public int getType() throws SQLException {
		connection.resetTimer();
		return resultSet.getType();
	}

	public int getConcurrency() throws SQLException {
		connection.resetTimer();
		return resultSet.getConcurrency();
	}

	public boolean rowUpdated() throws SQLException {
		connection.resetTimer();
		return resultSet.rowUpdated();
	}

	public boolean rowInserted() throws SQLException {
		connection.resetTimer();
		return resultSet.rowInserted();
	}

	public boolean rowDeleted() throws SQLException {
		connection.resetTimer();
		return resultSet.rowDeleted();
	}

	public void updateNull(int columnIndex) throws SQLException {
		connection.resetTimer();
		resultSet.updateNull(columnIndex);
	}

	public void updateBoolean(int columnIndex, boolean x) throws SQLException {
		connection.resetTimer();
		resultSet.updateBoolean(columnIndex, x);
	}

	public void updateByte(int columnIndex, byte x) throws SQLException {
		connection.resetTimer();
		resultSet.updateByte(columnIndex, x);
	}

	public void updateShort(int columnIndex, short x) throws SQLException {
		connection.resetTimer();
		resultSet.updateShort(columnIndex, x);
	}

	public void updateInt(int columnIndex, int x) throws SQLException {
		connection.resetTimer();
		resultSet.updateInt(columnIndex, x);
	}

	public void updateLong(int columnIndex, long x) throws SQLException {
		connection.resetTimer();
		resultSet.updateLong(columnIndex, x);
	}

	public void updateFloat(int columnIndex, float x) throws SQLException {
		connection.resetTimer();
		resultSet.updateFloat(columnIndex, x);
	}

	public void updateDouble(int columnIndex, double x) throws SQLException {
		connection.resetTimer();
		resultSet.updateDouble(columnIndex, x);
	}

	public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
		connection.resetTimer();
		resultSet.updateBigDecimal(columnIndex, x);
	}

	public void updateString(int columnIndex, String x) throws SQLException {
		connection.resetTimer();
		resultSet.updateString(columnIndex, x);
	}

	public void updateBytes(int columnIndex, byte[] x) throws SQLException {
		connection.resetTimer();
		resultSet.updateBytes(columnIndex, x);
	}

	public void updateDate(int columnIndex, Date x) throws SQLException {
		connection.resetTimer();
		resultSet.updateDate(columnIndex, x);
	}

	public void updateTime(int columnIndex, Time x) throws SQLException {
		connection.resetTimer();
		resultSet.updateTime(columnIndex, x);
	}

	public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
		connection.resetTimer();
		resultSet.updateTimestamp(columnIndex, x);
	}

	public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {
		connection.resetTimer();
		resultSet.updateAsciiStream(columnIndex, x, length);
	}

	public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
		connection.resetTimer();
		resultSet.updateBinaryStream(columnIndex, x, length);
	}

	public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
		connection.resetTimer();
		resultSet.updateCharacterStream(columnIndex, x, length);
	}

	public void updateObject(int columnIndex, Object x, int scaleOrLength) throws SQLException {
		connection.resetTimer();
		resultSet.updateObject(columnIndex, x, scaleOrLength);
	}

	public void updateObject(int columnIndex, Object x) throws SQLException {
		connection.resetTimer();
		resultSet.updateObject(columnIndex, x);
	}

	public void updateNull(String columnLabel) throws SQLException {
		connection.resetTimer();
		resultSet.updateNull(columnLabel);
	}

	public void updateBoolean(String columnLabel, boolean x) throws SQLException {
		connection.resetTimer();
		resultSet.updateBoolean(columnLabel, x);
	}

	public void updateByte(String columnLabel, byte x) throws SQLException {
		connection.resetTimer();
		resultSet.updateByte(columnLabel, x);
	}

	public void updateShort(String columnLabel, short x) throws SQLException {
		connection.resetTimer();
		resultSet.updateShort(columnLabel, x);
	}

	public void updateInt(String columnLabel, int x) throws SQLException {
		connection.resetTimer();
		resultSet.updateInt(columnLabel, x);
	}

	public void updateLong(String columnLabel, long x) throws SQLException {
		connection.resetTimer();
		resultSet.updateLong(columnLabel, x);
	}

	public void updateFloat(String columnLabel, float x) throws SQLException {
		connection.resetTimer();
		resultSet.updateFloat(columnLabel, x);
	}

	public void updateDouble(String columnLabel, double x) throws SQLException {
		connection.resetTimer();
		resultSet.updateDouble(columnLabel, x);
	}

	public void updateBigDecimal(String columnLabel, BigDecimal x) throws SQLException {
		connection.resetTimer();
		resultSet.updateBigDecimal(columnLabel, x);
	}

	public void updateString(String columnLabel, String x) throws SQLException {
		connection.resetTimer();
		resultSet.updateString(columnLabel, x);
	}

	public void updateBytes(String columnLabel, byte[] x) throws SQLException {
		connection.resetTimer();
		resultSet.updateBytes(columnLabel, x);
	}

	public void updateDate(String columnLabel, Date x) throws SQLException {
		connection.resetTimer();
		resultSet.updateDate(columnLabel, x);
	}

	public void updateTime(String columnLabel, Time x) throws SQLException {
		connection.resetTimer();
		resultSet.updateTime(columnLabel, x);
	}

	public void updateTimestamp(String columnLabel, Timestamp x) throws SQLException {
		connection.resetTimer();
		resultSet.updateTimestamp(columnLabel, x);
	}

	public void updateAsciiStream(String columnLabel, InputStream x, int length) throws SQLException {
		connection.resetTimer();
		resultSet.updateAsciiStream(columnLabel, x, length);
	}

	public void updateBinaryStream(String columnLabel, InputStream x, int length) throws SQLException {
		connection.resetTimer();
		resultSet.updateBinaryStream(columnLabel, x, length);
	}

	public void updateCharacterStream(String columnLabel, Reader reader, int length) throws SQLException {
		connection.resetTimer();
		resultSet.updateCharacterStream(columnLabel, reader, length);
	}

	public void updateObject(String columnLabel, Object x, int scaleOrLength) throws SQLException {
		connection.resetTimer();
		resultSet.updateObject(columnLabel, x, scaleOrLength);
	}

	public void updateObject(String columnLabel, Object x) throws SQLException {
		connection.resetTimer();
		resultSet.updateObject(columnLabel, x);
	}

	public void insertRow() throws SQLException {
		connection.resetTimer();
		resultSet.insertRow();
	}

	public void updateRow() throws SQLException {
		connection.resetTimer();
		resultSet.updateRow();
	}

	public void deleteRow() throws SQLException {
		connection.resetTimer();
		resultSet.deleteRow();
	}

	public void refreshRow() throws SQLException {
		connection.resetTimer();
		resultSet.refreshRow();
	}

	public void cancelRowUpdates() throws SQLException {
		connection.resetTimer();
		resultSet.cancelRowUpdates();
	}

	public void moveToInsertRow() throws SQLException {
		connection.resetTimer();
		resultSet.moveToInsertRow();
	}

	public void moveToCurrentRow() throws SQLException {
		connection.resetTimer();
		resultSet.moveToCurrentRow();
	}

	public Statement getStatement() throws SQLException {
		connection.resetTimer();
		return new AutoCloseStatement(connection, getStatement());
	}

	public void updateRef(int columnIndex, Ref x) throws SQLException {
		connection.resetTimer();
		resultSet.updateRef(columnIndex, x);
	}

	public void updateRef(String columnLabel, Ref x) throws SQLException {
		connection.resetTimer();
		resultSet.updateRef(columnLabel, x);
	}

	public void updateBlob(int columnIndex, Blob x) throws SQLException {
		connection.resetTimer();
		resultSet.updateBlob(columnIndex, x);
	}

	public void updateBlob(String columnLabel, Blob x) throws SQLException {
		connection.resetTimer();
		resultSet.updateBlob(columnLabel, x);
	}

	public void updateClob(int columnIndex, Clob x) throws SQLException {
		connection.resetTimer();
		resultSet.updateClob(columnIndex, x);
	}

	public void updateClob(String columnLabel, Clob x) throws SQLException {
		connection.resetTimer();
		resultSet.updateClob(columnLabel, x);
	}

	public void updateArray(int columnIndex, Array x) throws SQLException {
		connection.resetTimer();
		resultSet.updateArray(columnIndex, x);
	}

	public void updateArray(String columnLabel, Array x) throws SQLException {
		connection.resetTimer();
		resultSet.updateArray(columnLabel, x);
	}

	public void updateRowId(int columnIndex, RowId x) throws SQLException {
		connection.resetTimer();
		resultSet.updateRowId(columnIndex, x);
	}

	public void updateRowId(String columnLabel, RowId x) throws SQLException {
		connection.resetTimer();
		resultSet.updateRowId(columnLabel, x);
	}

	public int getHoldability() throws SQLException {
		connection.resetTimer();
		return resultSet.getHoldability();
	}

	public void updateNString(int columnIndex, String nString) throws SQLException {
		connection.resetTimer();
		resultSet.updateNString(columnIndex, nString);
	}

	public void updateNString(String columnLabel, String nString) throws SQLException {
		connection.resetTimer();
		resultSet.updateNString(columnLabel, nString);
	}

	public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
		connection.resetTimer();
		resultSet.updateNClob(columnIndex, nClob);
	}

	public void updateNClob(String columnLabel, NClob nClob) throws SQLException {
		connection.resetTimer();
		resultSet.updateNClob(columnLabel, nClob);
	}

	public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
		connection.resetTimer();
		resultSet.updateSQLXML(columnIndex, xmlObject);
	}

	public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {
		connection.resetTimer();
		resultSet.updateSQLXML(columnLabel, xmlObject);
	}

	public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
		connection.resetTimer();
		resultSet.updateNCharacterStream(columnIndex, x, length);
	}

	public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
		connection.resetTimer();
		resultSet.updateNCharacterStream(columnLabel, reader, length);
	}

	public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
		connection.resetTimer();
		resultSet.updateAsciiStream(columnIndex, x, length);
	}

	public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
		connection.resetTimer();
		resultSet.updateBinaryStream(columnIndex, x, length);
	}

	public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
		connection.resetTimer();
		resultSet.updateCharacterStream(columnIndex, x, length);
	}

	public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
		connection.resetTimer();
		resultSet.updateAsciiStream(columnLabel, x, length);
	}

	public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
		connection.resetTimer();
		resultSet.updateBinaryStream(columnLabel, x, length);
	}

	public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
		connection.resetTimer();
		resultSet.updateCharacterStream(columnLabel, reader, length);
	}

	public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {
		connection.resetTimer();
		resultSet.updateBlob(columnIndex, inputStream, length);
	}

	public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {
		connection.resetTimer();
		resultSet.updateBlob(columnLabel, inputStream, length);
	}

	public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
		connection.resetTimer();
		resultSet.updateClob(columnIndex, reader, length);
	}

	public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
		connection.resetTimer();
		resultSet.updateClob(columnLabel, reader, length);
	}

	public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
		connection.resetTimer();
		resultSet.updateNClob(columnIndex, reader, length);
	}

	public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
		connection.resetTimer();
		resultSet.updateNClob(columnLabel, reader, length);
	}

	public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
		connection.resetTimer();
		resultSet.updateNCharacterStream(columnIndex, x);
	}

	public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
		connection.resetTimer();
		resultSet.updateNCharacterStream(columnLabel, reader);
	}

	public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
		connection.resetTimer();
		resultSet.updateAsciiStream(columnIndex, x);
	}

	public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
		connection.resetTimer();
		resultSet.updateBinaryStream(columnIndex, x);
	}

	public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
		connection.resetTimer();
		resultSet.updateCharacterStream(columnIndex, x);
	}

	public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
		connection.resetTimer();
		resultSet.updateAsciiStream(columnLabel, x);
	}

	public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
		connection.resetTimer();
		resultSet.updateBinaryStream(columnLabel, x);
	}

	public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {
		connection.resetTimer();
		resultSet.updateCharacterStream(columnLabel, reader);
	}

	public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
		connection.resetTimer();
		resultSet.updateBlob(columnIndex, inputStream);
	}

	public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {
		connection.resetTimer();
		resultSet.updateBlob(columnLabel, inputStream);
	}

	public void updateClob(int columnIndex, Reader reader) throws SQLException {
		connection.resetTimer();
		resultSet.updateClob(columnIndex, reader);
	}

	public void updateClob(String columnLabel, Reader reader) throws SQLException {
		connection.resetTimer();
		resultSet.updateClob(columnLabel, reader);
	}

	public void updateNClob(int columnIndex, Reader reader) throws SQLException {
		connection.resetTimer();
		resultSet.updateNClob(columnIndex, reader);
	}

	public void updateNClob(String columnLabel, Reader reader) throws SQLException {
		connection.resetTimer();
		resultSet.updateNClob(columnLabel, reader);
	}

	public String toString() {
		connection.resetTimer();
		return resultSet.toString();
	}

	public boolean equals(Object obj) {
		connection.resetTimer();
		return resultSet.equals(obj);
	}

	public int hashCode() {
		connection.resetTimer();
		return resultSet.hashCode();
	}
}
