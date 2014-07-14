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

package org.ujmp.jdbc.set;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.ujmp.core.collections.set.AbstractSet;
import org.ujmp.core.interfaces.Erasable;
import org.ujmp.jdbc.SQLUtil;
import org.ujmp.jdbc.SQLUtil.SQLDialect;
import org.ujmp.jdbc.autoclose.AutoOpenCloseConnection;

public abstract class AbstractJDBCSet<V> extends AbstractSet<V> implements Closeable, Erasable {
	private static final long serialVersionUID = 4186997036615287357L;

	private final SQLDialect sqlDialect;

	private final String url;
	private final String databaseName;
	private final String tableName;
	private final String columnName;

	private final Connection connection;

	private transient PreparedStatement truncateTableStatement = null;
	private transient PreparedStatement insertStatement = null;
	private transient PreparedStatement deleteStatement = null;
	private transient PreparedStatement dropTableStatement = null;
	private transient PreparedStatement selectStatement = null;
	private transient PreparedStatement selectAllStatement = null;
	private transient PreparedStatement countStatement = null;

	protected AbstractJDBCSet(String url, String username, String password, String tableName, String columnName)
			throws SQLException {
		this(new AutoOpenCloseConnection(url, username, password), tableName, columnName);
	}

	protected AbstractJDBCSet(Connection connection, String tableName, String columnName) throws SQLException {
		this.connection = connection;
		this.url = connection.getMetaData().getURL();
		this.sqlDialect = SQLUtil.getSQLDialect(url);
		this.databaseName = SQLUtil.getDatabaseName(url);
		this.tableName = tableName == null ? "ujmp_set_" + System.currentTimeMillis() : tableName;

		if (!SQLUtil.tableExists(connection, getTableName())) {
			if (columnName == null || columnName.isEmpty()) {
				this.columnName = "id";
			} else {
				this.columnName = columnName;
			}
			createTable(getTableName(), this.columnName);
		} else {
			if (columnName == null || columnName.isEmpty()) {
				List<String> keyColumnNames = SQLUtil.getPrimaryKeyColumnNames(connection, getTableName());
				if (keyColumnNames.size() == 1) {
					this.columnName = keyColumnNames.get(0);
				} else {
					throw new RuntimeException("cannot determine id column");
				}
			} else {
				this.columnName = columnName;
			}
		}
	}

	public final Connection getConnection() {
		return connection;
	}

	@SuppressWarnings("unchecked")
	@Override
	public synchronized boolean contains(Object o) {
		if (o == null) {
			throw new RuntimeException("object cannot be null");
		}
		try {
			if (selectStatement == null || selectStatement.isClosed()) {
				selectStatement = SQLUtil.getSelectIdStatement(connection, sqlDialect, tableName, columnName);
			}
			setKey(selectStatement, 1, (V) o);
			ResultSet rs = selectStatement.executeQuery();
			boolean found = false;
			if (rs.next()) {
				found = true;
			}
			rs.close();
			return found;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public final synchronized boolean remove(Object key) {
		if (key == null) {
			throw new RuntimeException("object cannot be null");
		}
		try {
			if (deleteStatement == null || deleteStatement.isClosed()) {
				deleteStatement = SQLUtil.getDeleteIdStatement(connection, sqlDialect, tableName, columnName);
			}
			setKey(deleteStatement, 1, (V) key);
			return deleteStatement.executeUpdate() > 0;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected abstract void createTable(String tableName, String columnName) throws SQLException;

	public final String getTableName() {
		return tableName;
	}

	public final String getDatabaseName() {
		return databaseName;
	}

	@Override
	public final synchronized void clear() {
		try {
			if (truncateTableStatement == null || truncateTableStatement.isClosed()) {
				truncateTableStatement = SQLUtil.getTruncateTableStatement(connection, sqlDialect, tableName);
				truncateTableStatement.executeUpdate();
				truncateTableStatement.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public synchronized boolean add(V o) {
		if (o == null) {
			throw new RuntimeException("object cannot be null");
		}
		try {
			boolean wasThere = contains(o);
			if (!wasThere) {
				if (insertStatement == null || insertStatement.isClosed()) {
					insertStatement = SQLUtil.getInsertIdStatement(connection, sqlDialect, tableName, columnName);
				}
				setKey(insertStatement, 1, (V) o);
				insertStatement.executeUpdate();
				return true;
			}
			return false;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public final synchronized int size() {
		try {
			if (countStatement == null || countStatement.isClosed()) {
				countStatement = SQLUtil.getCountStatement(connection, sqlDialect, tableName);
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

	public final synchronized void close() throws IOException {
		try {
			if (connection == null && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e) {
			throw new IOException(e);
		}
	}

	@Override
	public final synchronized Iterator<V> iterator() {
		// todo: use iterator
		Set<V> keys = new HashSet<V>();
		try {
			if (selectAllStatement == null || selectAllStatement.isClosed()) {
				selectAllStatement = SQLUtil.getSelectIdsStatement(connection, sqlDialect, tableName, columnName);
			}
			ResultSet rs = selectAllStatement.executeQuery();
			while (rs.next()) {
				keys.add(getKey(rs, 1));
			}
			rs.close();
			return keys.iterator();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public final synchronized void erase() throws IOException {
		try {
			if (dropTableStatement == null || dropTableStatement.isClosed()) {
				dropTableStatement = SQLUtil.getDropTableStatement(connection, sqlDialect, tableName);
				dropTableStatement.executeUpdate();
				dropTableStatement.close();
			}
		} catch (SQLException e) {
			throw new IOException(e);
		}
	}

	public final SQLDialect getSQLDialect() {
		return sqlDialect;
	}

	protected abstract void setKey(PreparedStatement statement, int position, V key) throws SQLException;

	protected abstract V getKey(ResultSet rs, int position) throws SQLException;

}
