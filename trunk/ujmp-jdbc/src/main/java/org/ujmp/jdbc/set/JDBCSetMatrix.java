/*
 * Copyright (C) 2008-2015 by Holger Arndt
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
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.ujmp.core.interfaces.Erasable;
import org.ujmp.core.setmatrix.AbstractSetMatrix;
import org.ujmp.core.util.MathUtil;
import org.ujmp.jdbc.autoclose.AutoOpenCloseConnection;
import org.ujmp.jdbc.util.JDBCKeyIterator;
import org.ujmp.jdbc.util.SQLUtil;
import org.ujmp.jdbc.util.SQLUtil.SQLDialect;

public class JDBCSetMatrix<V> extends AbstractSetMatrix<V> implements Closeable, Erasable, Flushable {
	private static final long serialVersionUID = -8233006198283847196L;

	private boolean tableExists;
	private transient Connection connection;
	private transient ResultSet resultSet = null;

	private transient PreparedStatement truncateTableStatement = null;
	private transient PreparedStatement insertStatement = null;
	private transient PreparedStatement deleteStatement = null;
	private transient PreparedStatement containsKeyStatement = null;
	private transient PreparedStatement countStatement = null;
	private transient PreparedStatement selectAllStatement = null;
	private transient PreparedStatement dropTableStatement = null;

	public static <V> JDBCSetMatrix<V> connectToDerby() throws SQLException, IOException {
		return new JDBCSetMatrix<V>("jdbc:derby:"
				+ new File(System.getProperty("java.io.tmpdir") + File.separator + "ujmp" + System.nanoTime()
						+ "derby.temp"), null, null, null, null);
	}

	public static <V> JDBCSetMatrix<V> connectToDerby(File folderName) throws SQLException {
		return new JDBCSetMatrix<V>("jdbc:derby:" + folderName.getAbsolutePath() + "/", null, null, null, null);
	}

	public static <V> JDBCSetMatrix<V> connectToDerby(File folderName, String tableName) throws SQLException {
		return new JDBCSetMatrix<V>("jdbc:derby:" + folderName.getAbsolutePath() + "/", null, null, tableName, null);
	}

	public static <V> JDBCSetMatrix<V> connectToH2() throws SQLException, IOException {
		return new JDBCSetMatrix<V>("jdbc:h2:" + File.createTempFile("ujmp", "h2.temp"), null, null, null, null);
	}

	public static <V> JDBCSetMatrix<V> connectToH2(File file) throws SQLException {
		return new JDBCSetMatrix<V>("jdbc:h2:" + file.getAbsolutePath(), null, null, null, null);
	}

	public static <V> JDBCSetMatrix<V> connectToH2(File file, String tableName) throws SQLException {
		return new JDBCSetMatrix<V>("jdbc:h2:" + file.getAbsolutePath(), null, null, tableName, null);
	}

	public static <V> JDBCSetMatrix<V> connectToSQLite() throws SQLException, IOException {
		return new JDBCSetMatrix<V>("jdbc:sqlite:" + File.createTempFile("ujmp", "sqlite.temp"), null, null, null, null);
	}

	public static <V> JDBCSetMatrix<V> connectToSQLite(File file) throws SQLException {
		return new JDBCSetMatrix<V>("jdbc:sqlite:" + file.getAbsolutePath(), null, null, null, null);
	}

	public static <V> JDBCSetMatrix<V> connectToSQLite(File file, String tableName) throws SQLException {
		return new JDBCSetMatrix<V>("jdbc:sqlite:" + file.getAbsolutePath(), null, null, tableName, null);
	}

	public static <V> JDBCSetMatrix<V> connectToMySQL(String serverName, int port, String username, String password,
			String databaseName, String tableName, String columnName) throws SQLException {
		return new JDBCSetMatrix<V>("jdbc:mysql://" + serverName + ":" + port + "/" + databaseName, username, password,
				tableName, columnName);
	}

	public static <V> JDBCSetMatrix<V> connectToMySQL(String serverName, int port, String userName, String password,
			String databaseName, String tableName) throws SQLException {
		return new JDBCSetMatrix<V>("jdbc:mysql://" + serverName + ":" + port + "/" + databaseName, userName, password,
				tableName, null);
	}

	public static <V> JDBCSetMatrix<V> connectToHSQLDB() throws SQLException, IOException {
		return new JDBCSetMatrix<V>("jdbc:hsqldb:file:/" + File.createTempFile("hsqldb-stringset", ".temp"), "SA", "",
				null, null);
	}

	public static <V> JDBCSetMatrix<V> connectToHSQLDB(File fileName) throws SQLException {
		return new JDBCSetMatrix<V>("jdbc:hsqldb:file:/" + fileName.getAbsolutePath(), "SA", "", null, null);
	}

	public static <V> JDBCSetMatrix<V> connectToHSQLDB(File fileName, String tableName) throws SQLException {
		return new JDBCSetMatrix<V>("jdbc:hsqldb:file:/" + fileName.getAbsolutePath(), "SA", "", tableName, null);
	}

	public static <V> JDBCSetMatrix<V> connectToHSQLDB(File fileName, String userName, String password, String tableName)
			throws SQLException {
		return new JDBCSetMatrix<V>("jdbc:hsqldb:file:/" + fileName.getAbsolutePath(), userName, password, tableName,
				null);
	}

	public static <V> JDBCSetMatrix<V> connectToHSQLDB(File fileName, String userName, String password,
			String tableName, String columnName) throws SQLException {
		return new JDBCSetMatrix<V>("jdbc:hsqldb:file:/" + fileName.getAbsolutePath(), userName, password, tableName,
				columnName);
	}

	private JDBCSetMatrix(String url, String username, String password, String tableName, String columnName)
			throws SQLException {
		this(new AutoOpenCloseConnection(url, username, password), tableName, columnName);
	}

	private JDBCSetMatrix(Connection connection, String tableName, String columnName) throws SQLException {
		this.connection = connection;
		String url = connection.getMetaData().getURL();
		setMetaData(SQLUtil.URL, url);
		setMetaData(SQLUtil.SQLDIALECT, SQLUtil.getSQLDialect(url));
		setMetaData(SQLUtil.DATABASENAME, SQLUtil.getDatabaseName(url));
		setMetaData(SQLUtil.TABLENAME, tableName == null ? "ujmp_set_" + UUID.randomUUID() : tableName);
		setLabel(getTableName());
		this.tableExists = SQLUtil.tableExists(connection, getTableName());

		if (!tableExists) {
			if (columnName == null || columnName.isEmpty()) {
				setMetaData(SQLUtil.KEYCOLUMNNAME, "id");
				setColumnLabel(0, "id");
			} else {
				setMetaData(SQLUtil.KEYCOLUMNNAME, columnName);
				setColumnLabel(0, columnName);
			}
			createTable(getTableName(), getKeyColumnName());
		} else {
			if (columnName == null || columnName.isEmpty()) {
				List<String> keyColumnNames = SQLUtil.getPrimaryKeyColumnNames(connection, getTableName());
				if (keyColumnNames.size() == 1) {
					setMetaData(SQLUtil.KEYCOLUMNNAME, keyColumnNames.get(0));
					setColumnLabel(0, keyColumnNames.get(0));
				} else {
					throw new RuntimeException("cannot determine id column");
				}
			} else {
				setMetaData(SQLUtil.KEYCOLUMNNAME, columnName);
				setColumnLabel(0, columnName);
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

	public final String getKeyColumnName() {
		return getMetaDataString(SQLUtil.KEYCOLUMNNAME);
	}

	public final SQLDialect getSQLDialect() {
		Object sqlDialect = getMetaData(SQLUtil.SQLDIALECT);
		if (sqlDialect instanceof SQLDialect) {
			return (SQLDialect) getMetaData(SQLUtil.SQLDIALECT);
		} else {
			return null;
		}
	}

	protected final synchronized void clearSet() {
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

	@Override
	protected final synchronized boolean removeFromSet(Object key) {
		if (key == null) {
			throw new RuntimeException("object cannot be null");
		}
		if (!tableExists) {
			return false;
		}
		try {
			if (deleteStatement == null || deleteStatement.isClosed()) {
				deleteStatement = SQLUtil.getDeleteIdStatement(connection, getSQLDialect(), getTableName(),
						getKeyColumnName());
			}
			deleteStatement.setObject(1, key);
			return deleteStatement.executeUpdate() > 0;
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
	protected synchronized boolean addToSet(V o) {
		if (o == null) {
			throw new RuntimeException("object cannot be null");
		} else if (getKeyClass() == null) {
			setMetaData(SQLUtil.KEYCLASS, o.getClass());
		}
		try {
			if (!contains(o)) {
				if (insertStatement == null || insertStatement.isClosed()) {
					insertStatement = SQLUtil.getInsertIdStatement(connection, getSQLDialect(), getTableName(),
							getKeyColumnName());
				}
				insertStatement.setObject(1, o);
				insertStatement.executeUpdate();
				return true;
			}
			return false;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public synchronized boolean contains(Object o) {
		if (o == null) {
			throw new RuntimeException("object cannot be null");
		}
		if (!tableExists) {
			return false;
		}
		try {
			if (containsKeyStatement == null || containsKeyStatement.isClosed()) {
				containsKeyStatement = SQLUtil.getExistsStatement(connection, getSQLDialect(), getTableName(),
						getKeyColumnName());
			}
			containsKeyStatement.setObject(1, o);
			if (resultSet != null && !resultSet.isClosed()) {
				resultSet.close();
			}
			resultSet = containsKeyStatement.executeQuery();
			boolean found = false;
			if (resultSet.next()) {
				found = MathUtil.getBoolean(resultSet.getObject(1));
			}
			resultSet.close();
			return found;
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

	private final synchronized void createTable(String tableName, String columnName) throws SQLException {
		// ToDo: create tables other than String
		SQLUtil.createKeyStringTable(getConnection(), getSQLDialect(), tableName, columnName);
		this.tableExists = true;
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

	public final synchronized Iterator<V> iterator() {
		if (!tableExists) {
			return Collections.emptyIterator();
		}
		try {
			if (selectAllStatement == null || selectAllStatement.isClosed()) {
				selectAllStatement = SQLUtil.getSelectIdsStatement(connection, getSQLDialect(), getTableName(),
						getKeyColumnName());
			}
			if (resultSet != null && !resultSet.isClosed()) {
				resultSet.close();
			}
			resultSet = selectAllStatement.executeQuery();
			return new JDBCKeyIterator<V>(resultSet, getKeyClass());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
