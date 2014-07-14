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

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class DefaultJDBCMapMatrix<K, V> extends AbstractJDBCMapMatrix<K, V> {
	private static final long serialVersionUID = 4744307432617930795L;

	public static <K, V> DefaultJDBCMapMatrix<K, V> connectToMySQL(String serverName, int port, String username,
			String password, String databaseName, String tableName, String columnForKeys, String columnForValues)
			throws SQLException {
		return new DefaultJDBCMapMatrix<K, V>("jdbc:mysql://" + serverName + ":" + port + "/" + databaseName, username,
				password, tableName, columnForKeys, columnForValues);
	}

	public static <K, V> DefaultJDBCMapMatrix<K, V> connectToMySQL(String serverName, int port, String userName,
			String password, String databaseName, String tableName) throws SQLException {
		return new DefaultJDBCMapMatrix<K, V>("jdbc:mysql://" + serverName + ":" + port + "/" + databaseName, userName,
				password, tableName, null, null);
	}

	public static <K, V> DefaultJDBCMapMatrix<K, V> connectToHSQLDB() throws SQLException, IOException {
		return new DefaultJDBCMapMatrix<K, V>("jdbc:hsqldb:file:/" + File.createTempFile("ujmp", "hsqldb.temp"), null,
				null, null, null, null);
	}

	public static <K, V> DefaultJDBCMapMatrix<K, V> connectToSQLite() throws SQLException, IOException {
		return new DefaultJDBCMapMatrix<K, V>("jdbc:sqlite:" + File.createTempFile("ujmp", "sqlite.temp"), null, null,
				null, null, null);
	}

	public static <K, V> DefaultJDBCMapMatrix<K, V> connectToH2() throws SQLException, IOException {
		return new DefaultJDBCMapMatrix<K, V>("jdbc:h2:" + File.createTempFile("ujmp", "h2.temp"), null, null, null,
				null, null);
	}

	public static <K, V> DefaultJDBCMapMatrix<K, V> connectToDerby() throws SQLException, IOException {
		return new DefaultJDBCMapMatrix<K, V>("jdbc:derby:"
				+ new File(System.getProperty("java.io.tmpdir") + File.separator + "ujmp" + System.nanoTime()
						+ "derby.temp"), null, null, null, null, null);
	}

	public static <K, V> DefaultJDBCMapMatrix<K, V> connectToHSQLDB(File file) throws SQLException {
		return new DefaultJDBCMapMatrix<K, V>("jdbc:hsqldb:file:/" + file.getAbsolutePath(), null, null, null, null,
				null);
	}

	public static <K, V> DefaultJDBCMapMatrix<K, V> connectToH2(File file) throws SQLException {
		return new DefaultJDBCMapMatrix<K, V>("jdbc:h2:" + file.getAbsolutePath(), null, null, null, null, null);
	}

	public static <K, V> DefaultJDBCMapMatrix<K, V> connectToH2(File file, String tableName) throws SQLException {
		return new DefaultJDBCMapMatrix<K, V>("jdbc:h2:" + file.getAbsolutePath(), null, null, tableName, null, null);
	}

	public static <K, V> DefaultJDBCMapMatrix<K, V> connectToDerby(File folderName) throws SQLException {
		return new DefaultJDBCMapMatrix<K, V>("jdbc:derby:" + folderName.getAbsolutePath() + "/", null, null, null,
				null, null);
	}

	public static <K, V> DefaultJDBCMapMatrix<K, V> connectToDerby(File folderName, String tableName)
			throws SQLException {
		return new DefaultJDBCMapMatrix<K, V>("jdbc:derby:" + folderName.getAbsolutePath() + "/", null, null,
				tableName, null, null);
	}

	public static <K, V> DefaultJDBCMapMatrix<K, V> connectToSQLite(File file) throws SQLException {
		return new DefaultJDBCMapMatrix<K, V>("jdbc:sqlite:" + file.getAbsolutePath(), null, null, null, null, null);
	}

	public static <K, V> DefaultJDBCMapMatrix<K, V> connectToSQLite(File file, String tableName) throws SQLException {
		return new DefaultJDBCMapMatrix<K, V>("jdbc:sqlite:" + file.getAbsolutePath(), null, null, tableName, null,
				null);
	}

	public static <K, V> DefaultJDBCMapMatrix<K, V> connectToHSQLDB(File file, String tableName) throws SQLException {
		return new DefaultJDBCMapMatrix<K, V>("jdbc:hsqldb:file:/" + file.getAbsolutePath(), null, null, tableName,
				null, null);
	}

	public static <K, V> DefaultJDBCMapMatrix<K, V> connectToHSQLDB(File file, String userName, String password,
			String tableName) throws SQLException {
		return new DefaultJDBCMapMatrix<K, V>("jdbc:hsqldb:file:/" + file.getAbsolutePath(), userName, password,
				tableName, null, null);
	}

	public static <K, V> DefaultJDBCMapMatrix<K, V> connectToHSQLDB(File file, String userName, String password,
			String tableName, String keyColumnName, String valueColumnName) throws SQLException {
		return new DefaultJDBCMapMatrix<K, V>("jdbc:hsqldb:file:/" + file.getAbsolutePath(), userName, password,
				tableName, keyColumnName, valueColumnName);
	}

	public static <K, V> DefaultJDBCMapMatrix<K, V> connect(String url, String userName, String password,
			String tableName, String keyColumnName, String valueColumnName) throws SQLException {
		return new DefaultJDBCMapMatrix<K, V>(url, userName, password, tableName, keyColumnName, valueColumnName);
	}

	public static <K, V> DefaultJDBCMapMatrix<K, V> connect(String url, String userName, String password,
			String tableName) throws SQLException {
		return new DefaultJDBCMapMatrix<K, V>(url, userName, password, tableName, null, null);
	}

	public static <K, V> DefaultJDBCMapMatrix<K, V> connect(Connection connection, String tableName,
			String keyColumnName, String valueColumnName) throws SQLException {
		return new DefaultJDBCMapMatrix<K, V>(connection, tableName, keyColumnName, valueColumnName);
	}

	private DefaultJDBCMapMatrix(String url, String username, String password, String tableName, String keyColumnName,
			String valueColumnName) throws SQLException {
		super(url, username, password, tableName, keyColumnName, valueColumnName);
	}

	private DefaultJDBCMapMatrix(Connection connection, String tableName, String keyColumnName, String valueColumnName)
			throws SQLException {
		super(connection, tableName, keyColumnName, valueColumnName);
	}

}
