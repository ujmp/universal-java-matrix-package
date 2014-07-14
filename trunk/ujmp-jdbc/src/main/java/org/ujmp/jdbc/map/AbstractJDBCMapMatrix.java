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
import java.io.Flushable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.ujmp.core.Matrix;
import org.ujmp.core.collections.set.AbstractSet;
import org.ujmp.core.interfaces.Erasable;
import org.ujmp.core.mapmatrix.AbstractMapMatrix;
import org.ujmp.core.matrix.factory.BaseMatrixFactory;
import org.ujmp.core.util.MathUtil;
import org.ujmp.jdbc.SQLUtil;
import org.ujmp.jdbc.SQLUtil.SQLDialect;
import org.ujmp.jdbc.autoclose.AutoOpenCloseConnection;

public abstract class AbstractJDBCMapMatrix<K, V> extends AbstractMapMatrix<K, V> implements Closeable, Erasable,
		Flushable {
	private static final long serialVersionUID = -2850934349684973487L;

	public static final String URL = "URL";
	public static final String TABLENAME = "TableName";
	public static final String DATABASENAME = "DatabaseName";
	public static final String KEYCOLUMNNAME = "KeyColumnName";
	public static final String VALUECOLUMNNAME = "ValueColumnName";
	public static final String KEYCLASS = "KeyClass";
	public static final String VALUECLASS = "ValueClass";
	public static final String SQLDIALECT = "SQLDialect";

	private boolean tableExists;

	private transient Connection connection;

	private transient PreparedStatement truncateTableStatement = null;
	private transient PreparedStatement insertStatement = null;
	private transient PreparedStatement updateStatement = null;
	private transient PreparedStatement deleteStatement = null;
	private transient PreparedStatement selectByKeyStatement = null;
	private transient PreparedStatement containsKeyStatement = null;
	private transient PreparedStatement containsValueStatement = null;
	private transient PreparedStatement keyStatement = null;
	private transient PreparedStatement dropTableStatement = null;

	protected AbstractJDBCMapMatrix(String url, String username, String password, String tableName,
			String keyColumnName, String valueColumnName) throws SQLException {
		this(new AutoOpenCloseConnection(url, username, password), tableName, keyColumnName, valueColumnName);
	}

	protected AbstractJDBCMapMatrix(Connection connection, String tableName, String keyColumnName,
			String valueColumnName) throws SQLException {
		this.connection = connection;
		String url = connection.getMetaData().getURL();
		setMetaData(URL, url);
		setMetaData(SQLDIALECT, SQLUtil.getSQLDialect(url));
		setMetaData(DATABASENAME, SQLUtil.getDatabaseName(url));
		setMetaData(TABLENAME, tableName == null ? "ujmp_map_" + UUID.randomUUID() : tableName);
		setLabel(getTableName());
		this.tableExists = SQLUtil.tableExists(connection, getTableName());

		if (!tableExists) {
			if (keyColumnName == null || keyColumnName.isEmpty()) {
				setMetaData(KEYCOLUMNNAME, "id");
				setColumnLabel(0, "id");
			} else {
				setMetaData(KEYCOLUMNNAME, keyColumnName);
				setColumnLabel(0, keyColumnName);
			}
			if (valueColumnName == null || valueColumnName.isEmpty()) {
				setMetaData(VALUECOLUMNNAME, "data");
				setColumnLabel(1, "data");
			} else {
				setMetaData(VALUECOLUMNNAME, valueColumnName);
				setColumnLabel(1, valueColumnName);
			}
			createTable(getTableName(), getKeyColumnName(), getValueColumnName());
		} else {
			if (keyColumnName == null || keyColumnName.isEmpty()) {
				List<String> keyColumnNames = SQLUtil.getPrimaryKeyColumnNames(connection, getTableName());
				if (keyColumnNames.size() == 1) {
					setMetaData(KEYCOLUMNNAME, keyColumnNames.get(0));
					setColumnLabel(0, keyColumnNames.get(0));
				} else {
					throw new RuntimeException("cannot determine id column");
				}
			} else {
				setMetaData(KEYCOLUMNNAME, keyColumnName);
				setColumnLabel(0, keyColumnName);
			}
			if (valueColumnName == null || valueColumnName.isEmpty()) {
				List<String> columnNames = SQLUtil.getColumnNames(connection, getTableName());
				columnNames.remove(keyColumnName);
				if (columnNames.size() == 1) {
					setMetaData(VALUECOLUMNNAME, columnNames.get(0));
					setColumnLabel(1, columnNames.get(0));
				} else {
					List<String> keyColumnNames = SQLUtil.getPrimaryKeyColumnNames(connection, getTableName());
					columnNames.removeAll(keyColumnNames);
					if (columnNames.size() == 1) {
						setMetaData(VALUECOLUMNNAME, columnNames.get(0));
						setColumnLabel(1, columnNames.get(0));
					} else {
						throw new RuntimeException("cannot determine data column");
					}
				}
			} else {
				setMetaData(VALUECOLUMNNAME, valueColumnName);
				setColumnLabel(1, valueColumnName);
			}
		}
	}

	public final Connection getConnection() {
		return connection;
	}

	public final String getURL() {
		return getMetaDataString(URL);
	}

	public final String getTableName() {
		return getMetaDataString(TABLENAME);
	}

	public final String getDatabaseName() {
		return getMetaDataString(DATABASENAME);
	}

	public final Class<?> getKeyClass() {
		return (Class<?>) getMetaData(KEYCLASS);
	}

	public final Class<?> getValueClass() {
		return (Class<?>) getMetaData(VALUECLASS);
	}

	public final String getKeyColumnName() {
		return getMetaDataString(KEYCOLUMNNAME);
	}

	public final String getValueColumnName() {
		return getMetaDataString(VALUECOLUMNNAME);
	}

	public final SQLDialect getSQLDialect() {
		Object sqlDialect = getMetaData(SQLDIALECT);
		if (sqlDialect instanceof SQLDialect) {
			return (SQLDialect) getMetaData(SQLDIALECT);
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
			ResultSet rs = keyStatement.executeQuery();
			return new JDBCKeySet(this, rs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
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
				setKey(deleteStatement, 1, (K) key);
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
		}
		if (getKeyClass() == null && key != null) {
			setMetaData(KEYCLASS, key.getClass());
		}
		if (getValueClass() == null && value != null) {
			setMetaData(VALUECLASS, value.getClass());
		}
		try {
			V oldValue = get(key);
			if (oldValue == null) {
				if (insertStatement == null || insertStatement.isClosed()) {
					insertStatement = SQLUtil.getInsertKeyValueStatement(connection, getSQLDialect(), getTableName(),
							getKeyColumnName(), getValueColumnName());
				}
				setKey(insertStatement, 1, key);
				setValue(insertStatement, 2, value);
				insertStatement.executeUpdate();
			} else if (!oldValue.equals(value)) {
				if (updateStatement == null || updateStatement.isClosed()) {
					updateStatement = SQLUtil.getUpdateKeyValueStatement(connection, getSQLDialect(), getTableName(),
							getKeyColumnName(), getValueColumnName());
				}
				setValue(updateStatement, 1, value);
				setKey(updateStatement, 2, key);
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
			setMetaData(KEYCLASS, key.getClass());
		}
		try {
			if (selectByKeyStatement == null || selectByKeyStatement.isClosed()) {
				selectByKeyStatement = SQLUtil.getValueForKeyStatement(connection, getSQLDialect(), getTableName(),
						getKeyColumnName(), getValueColumnName());
			}
			setKey(selectByKeyStatement, 1, (K) key);
			ResultSet rs = selectByKeyStatement.executeQuery();
			V value = null;
			if (rs.next()) {
				value = getValue(rs, 1);
			}
			rs.close();
			if (getValueClass() == null && value != null) {
				setMetaData(VALUECLASS, value.getClass());
			}
			return value;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
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
			setKey(containsKeyStatement, 1, (K) key);
			ResultSet rs = containsKeyStatement.executeQuery();
			boolean containsKey = false;
			if (rs.next()) {
				containsKey = MathUtil.getBoolean(rs.getObject(1));
			}
			rs.close();
			return containsKey;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
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
			setValue(containsValueStatement, 1, (V) value);
			ResultSet rs = containsValueStatement.executeQuery();
			boolean containsValue = false;
			if (rs.next()) {
				containsValue = MathUtil.getBoolean(rs.getObject(1));
			}
			rs.close();
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
		setMetaData(SQLDIALECT, (SQLDialect) is.readObject());
		setMetaData(DATABASENAME, is.readUTF());
		setMetaData(TABLENAME, is.readUTF());
		setMetaData(KEYCOLUMNNAME, is.readUTF());
		setMetaData(VALUECOLUMNNAME, is.readUTF());
		String url = is.readUTF();
		setMetaData(URL, url);
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

	public void flush() throws IOException {
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

	class JDBCKeyIterator implements Iterator<K> {

		private final ResultSet rs;
		private K currentKey = null;

		public JDBCKeyIterator(ResultSet rs) {
			this.rs = rs;
			try {
				if (rs.next()) {
					currentKey = getKey(rs, 1);
				}
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}

		public boolean hasNext() {
			return currentKey != null;
		}

		public K next() {
			try {
				K lastKey = currentKey;
				if (rs.next()) {
					currentKey = getKey(rs, 1);
				} else {
					currentKey = null;
				}
				return lastKey;
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	class JDBCKeySet extends AbstractSet<K> {
		private static final long serialVersionUID = 1429255834963921385L;

		private final AbstractJDBCMapMatrix<K, V> map;
		private final ResultSet rs;

		public JDBCKeySet(AbstractJDBCMapMatrix<K, V> map, ResultSet rs) {
			this.map = map;
			this.rs = rs;
		}

		@Override
		public boolean add(K value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean remove(Object o) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void clear() {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean contains(Object o) {
			return map.containsKey(o);
		}

		@Override
		public Iterator<K> iterator() {
			return new JDBCKeyIterator(rs);
		}

		@Override
		public int size() {
			return map.size();
		}
	}

	public BaseMatrixFactory<? extends Matrix> getFactory() {
		return null;
	}

	protected void setKey(PreparedStatement statement, int position, K key) throws SQLException {
		statement.setObject(position, key);
	}

	protected void setValue(PreparedStatement statement, int position, V value) throws SQLException {
		statement.setObject(position, value);
	}

	@SuppressWarnings("unchecked")
	protected K getKey(ResultSet rs, int position) throws SQLException {
		return (K) SQLUtil.getObject(rs, position, getKeyClass());
	}

	@SuppressWarnings("unchecked")
	protected V getValue(ResultSet rs, int position) throws SQLException {
		return (V) SQLUtil.getObject(rs, position, getValueClass());
	}

	protected void createTable(String tableName, String keyColumnName, String valueColumnName) throws SQLException {
		SQLUtil.createKeyValueStringTable(getConnection(), getSQLDialect(), tableName, keyColumnName, valueColumnName);
		this.tableExists = true;
	}

}
