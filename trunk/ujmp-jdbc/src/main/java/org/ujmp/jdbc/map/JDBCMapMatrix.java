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

package org.ujmp.jdbc.map;

import java.io.Closeable;
import java.io.File;
import java.io.Flushable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.ujmp.core.interfaces.Erasable;
import org.ujmp.core.mapmatrix.AbstractMapMatrix;
import org.ujmp.core.util.MathUtil;
import org.ujmp.jdbc.autoclose.AutoOpenCloseConnection;
import org.ujmp.jdbc.util.JDBCKeySet;
import org.ujmp.jdbc.util.SQLUtil;
import org.ujmp.jdbc.util.SQLUtil.SQLDialect;

public class JDBCMapMatrix<K, V> extends AbstractMapMatrix<K, V> implements Closeable, Erasable, Flushable {
	private static final long serialVersionUID = 4744307432617930795L;

	private boolean tableExists;
	private transient Connection connection;
	private transient ResultSet resultSet = null;

	private transient PreparedStatement truncateTableStatement = null;
	private transient PreparedStatement insertStatement = null;
	private transient PreparedStatement updateStatement = null;
	private transient PreparedStatement deleteStatement = null;
	private transient PreparedStatement selectByKeyStatement = null;
	private transient PreparedStatement containsKeyStatement = null;
	private transient PreparedStatement containsValueStatement = null;
	private transient PreparedStatement keyStatement = null;
	private transient PreparedStatement dropTableStatement = null;
	private transient PreparedStatement countStatement = null;

	private JDBCMapMatrix(String url, String username, String password, String tableName, String keyColumnName,
			String valueColumnName) throws SQLException {
		this(new AutoOpenCloseConnection(url, username, password), tableName, keyColumnName, valueColumnName);
	}

	private JDBCMapMatrix(Connection connection, String tableName, String keyColumnName, String valueColumnName)
			throws SQLException {
		this.connection = connection;
		String url = connection.getMetaData().getURL();
		setMetaData(SQLUtil.URL, url);
		setMetaData(SQLUtil.SQLDIALECT, SQLUtil.getSQLDialect(url));
		setMetaData(SQLUtil.DATABASENAME, SQLUtil.getDatabaseName(url));
		setMetaData(SQLUtil.TABLENAME, tableName == null ? "ujmp_map_" + UUID.randomUUID() : tableName);
		setLabel(getTableName());
		this.tableExists = SQLUtil.tableExists(connection, getTableName());

		if (!tableExists) {
			if (keyColumnName == null || keyColumnName.isEmpty()) {
				setMetaData(SQLUtil.KEYCOLUMNNAME, "id");
				setColumnLabel(0, "id");
			} else {
				setMetaData(SQLUtil.KEYCOLUMNNAME, keyColumnName);
				setColumnLabel(0, keyColumnName);
			}
			if (valueColumnName == null || valueColumnName.isEmpty()) {
				setMetaData(SQLUtil.VALUECOLUMNNAME, "data");
				setColumnLabel(1, "data");
			} else {
				setMetaData(SQLUtil.VALUECOLUMNNAME, valueColumnName);
				setColumnLabel(1, valueColumnName);
			}
			createTable(getTableName(), getKeyColumnName(), getValueColumnName());
		} else {
			if (keyColumnName == null || keyColumnName.isEmpty()) {
				List<String> keyColumnNames = SQLUtil.getPrimaryKeyColumnNames(connection, getTableName());
				if (keyColumnNames.size() == 1) {
					setMetaData(SQLUtil.KEYCOLUMNNAME, keyColumnNames.get(0));
					setColumnLabel(0, keyColumnNames.get(0));
				} else {
					throw new RuntimeException("cannot determine id column");
				}
			} else {
				setMetaData(SQLUtil.KEYCOLUMNNAME, keyColumnName);
				setColumnLabel(0, keyColumnName);
			}
			if (valueColumnName == null || valueColumnName.isEmpty()) {
				List<String> columnNames = SQLUtil.getColumnNames(connection, getTableName());
				columnNames.remove(keyColumnName);
				if (columnNames.size() == 1) {
					setMetaData(SQLUtil.VALUECOLUMNNAME, columnNames.get(0));
					setColumnLabel(1, columnNames.get(0));
				} else {
					List<String> keyColumnNames = SQLUtil.getPrimaryKeyColumnNames(connection, getTableName());
					columnNames.removeAll(keyColumnNames);
					if (columnNames.size() == 1) {
						setMetaData(SQLUtil.VALUECOLUMNNAME, columnNames.get(0));
						setColumnLabel(1, columnNames.get(0));
					} else {
						throw new RuntimeException("cannot determine data column");
					}
				}
			} else {
				setMetaData(SQLUtil.VALUECOLUMNNAME, valueColumnName);
				setColumnLabel(1, valueColumnName);
			}
		}
	}

	public final Connection getConnection() {
		return connection;
	}

	public final String getURL() {
		return getMetaDataString(SQLUtil.URL);
	}

	public final String getTableName() {
		return getMetaDataString(SQLUtil.TABLENAME);
	}

	public final String getDatabaseName() {
		return getMetaDataString(SQLUtil.DATABASENAME);
	}

	public final Class<?> getKeyClass() {
		return (Class<?>) getMetaData(SQLUtil.KEYCLASS);
	}

	public final Class<?> getValueClass() {
		return (Class<?>) getMetaData(SQLUtil.VALUECLASS);
	}

	public final String getKeyColumnName() {
		return getMetaDataString(SQLUtil.KEYCOLUMNNAME);
	}

	public final String getValueColumnName() {
		return getMetaDataString(SQLUtil.VALUECOLUMNNAME);
	}

	public final SQLDialect getSQLDialect() {
		Object sqlDialect = getMetaData(SQLUtil.SQLDIALECT);
		if (sqlDialect instanceof SQLDialect) {
			return (SQLDialect) getMetaData(SQLUtil.SQLDIALECT);
		} else {
			return null;
		}
	}

	@Override
	protected final synchronized void clearMap() {
		if (!tableExists) {
			return;
		}
		try {
			if (truncateTableStatement == null || truncateTableStatement.isClosed()) {
				truncateTableStatement = SQLUtil.getTruncateTableStatement(connection, getSQLDialect(), getTableName());
				if (resultSet != null && !resultSet.isClosed()) {
					resultSet.close();
				}
				truncateTableStatement.executeUpdate();
				truncateTableStatement.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public final synchronized Set<K> keySet() {
		if (!tableExists) {
			return Collections.emptySet();
		}
		try {
			if (keyStatement == null || keyStatement.isClosed()) {
				keyStatement = SQLUtil.getSelectIdsStatement(connection, getSQLDialect(), getTableName(),
						getKeyColumnName());
			}
			if (resultSet != null && !resultSet.isClosed()) {
				resultSet.close();
			}
			resultSet = keyStatement.executeQuery();
			return new JDBCKeySet<K>(this, resultSet, getKeyClass());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected final synchronized V removeFromMap(Object key) {
		if (key == null) {
			throw new RuntimeException("key cannot be null");
		}
		if (!tableExists) {
			return null;
		}
		try {
			V oldValue = get(key);
			if (oldValue != null) {
				if (deleteStatement == null || deleteStatement.isClosed()) {
					deleteStatement = SQLUtil.getDeleteIdStatement(connection, getSQLDialect(), getTableName(),
							getKeyColumnName());
				}
				deleteStatement.setObject(1, key);
				deleteStatement.executeUpdate();
			}
			return oldValue;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public final synchronized void close() throws IOException {
		try {
			if (connection == null && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e) {
			throw new IOException(e);
		}
	}

	public final synchronized void erase() throws IOException {
		if (!tableExists) {
			return;
		}
		try {
			if (dropTableStatement == null || dropTableStatement.isClosed()) {
				dropTableStatement = SQLUtil.getDropTableStatement(connection, getSQLDialect(), getTableName());
				dropTableStatement.executeUpdate();
				dropTableStatement.close();
			}
		} catch (SQLException e) {
			throw new IOException(e);
		}
	}

	@Override
	protected final synchronized V putIntoMap(K key, V value) {
		if (key == null) {
			throw new RuntimeException("key cannot be null");
		} else if (getKeyClass() == null) {
			setMetaData(SQLUtil.KEYCLASS, key.getClass());
		}
		if (getValueClass() == null && value != null) {
			setMetaData(SQLUtil.VALUECLASS, value.getClass());
		}
		try {
			V oldValue = get(key);
			if (oldValue == null) {
				if (insertStatement == null || insertStatement.isClosed()) {
					insertStatement = SQLUtil.getInsertKeyValueStatement(connection, getSQLDialect(), getTableName(),
							getKeyColumnName(), getValueColumnName());
				}
				insertStatement.setObject(1, key);
				insertStatement.setObject(2, value);
				insertStatement.executeUpdate();
			} else if (!oldValue.equals(value)) {
				if (updateStatement == null || updateStatement.isClosed()) {
					updateStatement = SQLUtil.getUpdateKeyValueStatement(connection, getSQLDialect(), getTableName(),
							getKeyColumnName(), getValueColumnName());
				}
				updateStatement.setObject(1, value);
				updateStatement.setObject(2, key);
				updateStatement.executeUpdate();
			}
			return oldValue;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public final synchronized V get(Object key) {
		if (key == null) {
			throw new RuntimeException("key cannot be null");
		}
		if (!tableExists) {
			return null;
		}
		if (getKeyClass() == null && key != null) {
			setMetaData(SQLUtil.KEYCLASS, key.getClass());
		}
		try {
			if (selectByKeyStatement == null || selectByKeyStatement.isClosed()) {
				selectByKeyStatement = SQLUtil.getValueForKeyStatement(connection, getSQLDialect(), getTableName(),
						getKeyColumnName(), getValueColumnName());
			}
			selectByKeyStatement.setObject(1, key);
			if (resultSet != null && !resultSet.isClosed()) {
				resultSet.close();
			}
			resultSet = selectByKeyStatement.executeQuery();
			V value = null;
			if (resultSet.next()) {
				value = (V) SQLUtil.getObject(resultSet, 1, getValueClass());
			}
			resultSet.close();
			if (getValueClass() == null && value != null) {
				setMetaData(SQLUtil.VALUECLASS, value.getClass());
			}
			return value;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public final synchronized boolean containsKey(Object key) {
		if (key == null) {
			throw new RuntimeException("key cannot be null");
		}
		if (!tableExists) {
			return false;
		}
		try {
			if (containsKeyStatement == null || containsKeyStatement.isClosed()) {
				containsKeyStatement = SQLUtil.getExistsStatement(connection, getSQLDialect(), getTableName(),
						getKeyColumnName());
			}
			containsKeyStatement.setObject(1, key);
			if (resultSet != null && !resultSet.isClosed()) {
				resultSet.close();
			}
			resultSet = containsKeyStatement.executeQuery();
			boolean containsKey = false;
			if (resultSet.next()) {
				containsKey = MathUtil.getBoolean(resultSet.getObject(1));
			}
			resultSet.close();
			return containsKey;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public final synchronized boolean containsValue(Object value) {
		if (value == null) {
			throw new RuntimeException("value cannot be null");
		}
		if (!tableExists) {
			return false;
		}
		try {
			if (containsValueStatement == null || containsValueStatement.isClosed()) {
				containsValueStatement = SQLUtil.getExistsStatement(connection, getSQLDialect(), getTableName(),
						getValueColumnName());
			}
			containsValueStatement.setObject(1, value);
			if (resultSet != null && !resultSet.isClosed()) {
				resultSet.close();
			}
			resultSet = containsValueStatement.executeQuery();
			boolean containsValue = false;
			if (resultSet.next()) {
				containsValue = MathUtil.getBoolean(resultSet.getObject(1));
			}
			resultSet.close();
			return containsValue;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected void beforeWriteObject(ObjectOutputStream os) throws IOException {
		try {
			os.writeObject(getSQLDialect());
			os.writeUTF(getDatabaseName());
			os.writeUTF(getTableName());
			os.writeUTF(getKeyColumnName());
			os.writeUTF(getValueColumnName());
			os.writeUTF(connection.getMetaData().getURL());
			if (connection.getMetaData().getUserName() != null) {
				os.writeBoolean(true);
				os.writeUTF(connection.getMetaData().getUserName());
			} else {
				os.writeBoolean(false);
			}
			if (connection.getClientInfo("Password") != null) {
				os.writeBoolean(true);
				os.writeUTF(connection.getClientInfo("Password"));
			} else {
				os.writeBoolean(false);
			}
		} catch (SQLException e) {
			throw new IOException(e);
		}
	}

	protected void beforeReadObject(ObjectInputStream is) throws IOException, ClassNotFoundException {
		setMetaData(SQLUtil.SQLDIALECT, (SQLDialect) is.readObject());
		setMetaData(SQLUtil.DATABASENAME, is.readUTF());
		setMetaData(SQLUtil.TABLENAME, is.readUTF());
		setMetaData(SQLUtil.KEYCOLUMNNAME, is.readUTF());
		setMetaData(SQLUtil.VALUECOLUMNNAME, is.readUTF());
		String url = is.readUTF();
		setMetaData(SQLUtil.URL, url);
		boolean containsUsername = is.readBoolean();
		String username = null;
		if (containsUsername) {
			username = is.readUTF();
		}
		boolean containsPassword = is.readBoolean();
		String password = null;
		if (containsPassword) {
			password = is.readUTF();
		}
		connection = new AutoOpenCloseConnection(url, username, password);
	}

	public synchronized void flush() throws IOException {
		try {
			switch (getSQLDialect()) {
			case H2:
				// seems to have no effect
				// PreparedStatement ps =
				// connection.prepareStatement("CHECKPOINT SYNC");
				// ps.execute();
				break;
			case HSQLDB:
				// seems to have no effect
				// ps = connection.prepareStatement("CHECKPOINT");
				// ps.execute();
				break;
			default:
				break;
			}
			getConnection().commit();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public final synchronized int size() {
		try {
			if (!tableExists) {
				return 0;
			}
			if (countStatement == null || countStatement.isClosed()) {
				countStatement = SQLUtil.getCountStatement(connection, getSQLDialect(), getTableName());
			}
			ResultSet rs = countStatement.executeQuery();
			int size = -1;
			if (rs.next()) {
				size = rs.getInt(1);
			} else {
				throw new RuntimeException("cannot count entries");
			}
			rs.close();
			return size;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private final synchronized void createTable(String tableName, String keyColumnName, String valueColumnName)
			throws SQLException {
		// ToDo: create tables other than String
		SQLUtil.createKeyValueStringTable(getConnection(), getSQLDialect(), tableName, keyColumnName, valueColumnName);
		this.tableExists = true;
	}

	public static <K, V> JDBCMapMatrix<K, V> connectToMySQL(String serverName, int port, String username,
			String password, String databaseName, String tableName, String columnForKeys, String columnForValues)
			throws SQLException {
		return new JDBCMapMatrix<K, V>("jdbc:mysql://" + serverName + ":" + port + "/" + databaseName, username,
				password, tableName, columnForKeys, columnForValues);
	}

	public static <K, V> JDBCMapMatrix<K, V> connectToMySQL(String serverName, int port, String userName,
			String password, String databaseName, String tableName) throws SQLException {
		return new JDBCMapMatrix<K, V>("jdbc:mysql://" + serverName + ":" + port + "/" + databaseName, userName,
				password, tableName, null, null);
	}

	public static <K, V> JDBCMapMatrix<K, V> connectToHSQLDB() throws SQLException, IOException {
		return new JDBCMapMatrix<K, V>("jdbc:hsqldb:file:/" + File.createTempFile("ujmp", "hsqldb.temp"), null, null,
				null, null, null);
	}

	public static <K, V> JDBCMapMatrix<K, V> connectToSQLite() throws SQLException, IOException {
		return new JDBCMapMatrix<K, V>("jdbc:sqlite:" + File.createTempFile("ujmp", "sqlite.temp"), null, null, null,
				null, null);
	}

	public static <K, V> JDBCMapMatrix<K, V> connectToDerby() throws SQLException, IOException {
		return new JDBCMapMatrix<K, V>("jdbc:derby:"
				+ new File(System.getProperty("java.io.tmpdir") + File.separator + "ujmp" + System.nanoTime()
						+ "derby.temp"), null, null, null, null, null);
	}

	public static <K, V> JDBCMapMatrix<K, V> connectToHSQLDB(File file) throws SQLException {
		return new JDBCMapMatrix<K, V>("jdbc:hsqldb:file:/" + file.getAbsolutePath(), null, null, null, null, null);
	}

	public static <K, V> JDBCMapMatrix<K, V> connectToH2() throws SQLException, IOException {
		return new JDBCMapMatrix<K, V>("jdbc:h2:" + File.createTempFile("ujmp", "h2.temp"), null, null, null, null,
				null);
	}

	public static <K, V> JDBCMapMatrix<K, V> connectToH2(File file) throws SQLException {
		return new JDBCMapMatrix<K, V>("jdbc:h2:" + file.getAbsolutePath(), null, null, null, null, null);
	}

	public static <K, V> JDBCMapMatrix<K, V> connectToH2(File file, String tableName) throws SQLException {
		return new JDBCMapMatrix<K, V>("jdbc:h2:" + file.getAbsolutePath(), null, null, tableName, null, null);
	}

	public static <K, V> JDBCMapMatrix<K, V> connectToDerby(File folderName) throws SQLException {
		return new JDBCMapMatrix<K, V>("jdbc:derby:" + folderName.getAbsolutePath() + "/", null, null, null, null, null);
	}

	public static <K, V> JDBCMapMatrix<K, V> connectToDerby(File folderName, String tableName) throws SQLException {
		return new JDBCMapMatrix<K, V>("jdbc:derby:" + folderName.getAbsolutePath() + "/", null, null, tableName, null,
				null);
	}

	public static <K, V> JDBCMapMatrix<K, V> connectToSQLite(File file) throws SQLException {
		return new JDBCMapMatrix<K, V>("jdbc:sqlite:" + file.getAbsolutePath(), null, null, null, null, null);
	}

	public static <K, V> JDBCMapMatrix<K, V> connectToSQLite(File file, String tableName) throws SQLException {
		return new JDBCMapMatrix<K, V>("jdbc:sqlite:" + file.getAbsolutePath(), null, null, tableName, null, null);
	}

	public static <K, V> JDBCMapMatrix<K, V> connectToHSQLDB(File file, String tableName) throws SQLException {
		return new JDBCMapMatrix<K, V>("jdbc:hsqldb:file:/" + file.getAbsolutePath(), null, null, tableName, null, null);
	}

	public static <K, V> JDBCMapMatrix<K, V> connectToHSQLDB(File file, String userName, String password,
			String tableName) throws SQLException {
		return new JDBCMapMatrix<K, V>("jdbc:hsqldb:file:/" + file.getAbsolutePath(), userName, password, tableName,
				null, null);
	}

	public static <K, V> JDBCMapMatrix<K, V> connectToHSQLDB(File file, String userName, String password,
			String tableName, String keyColumnName, String valueColumnName) throws SQLException {
		return new JDBCMapMatrix<K, V>("jdbc:hsqldb:file:/" + file.getAbsolutePath(), userName, password, tableName,
				keyColumnName, valueColumnName);
	}

	public static <K, V> JDBCMapMatrix<K, V> connect(String url, String userName, String password, String tableName,
			String keyColumnName, String valueColumnName) throws SQLException {
		return new JDBCMapMatrix<K, V>(url, userName, password, tableName, keyColumnName, valueColumnName);
	}

	public static <K, V> JDBCMapMatrix<K, V> connect(String url, String userName, String password, String tableName)
			throws SQLException {
		return new JDBCMapMatrix<K, V>(url, userName, password, tableName, null, null);
	}

	public static <K, V> JDBCMapMatrix<K, V> connect(Connection connection, String tableName, String keyColumnName,
			String valueColumnName) throws SQLException {
		return new JDBCMapMatrix<K, V>(connection, tableName, keyColumnName, valueColumnName);
	}

}
