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

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.ujmp.jdbc.SQLUtil;

public class JDBCStringSet extends AbstractJDBCSet<String> {
	private static final long serialVersionUID = -8233006198283847196L;

	private JDBCStringSet(String url, String username, String password, String tableName, String columnName)
			throws SQLException {
		super(url, username, password, tableName, columnName);
	}

	public static JDBCStringSet connectToMySQL(String serverName, int port, String username, String password,
			String databaseName, String tableName, String columnName) throws SQLException {
		return new JDBCStringSet("jdbc:mysql://" + serverName + ":" + port + "/" + databaseName, username, password,
				tableName, columnName);
	}

	public static JDBCStringSet connectToMySQL(String serverName, int port, String userName, String password,
			String databaseName, String tableName) throws SQLException {
		return new JDBCStringSet("jdbc:mysql://" + serverName + ":" + port + "/" + databaseName, userName, password,
				tableName, null);
	}

	public static JDBCStringSet connectToHSQLDB() throws SQLException, IOException {
		return new JDBCStringSet("jdbc:hsqldb:file:/" + File.createTempFile("hsqldb-stringset", ".temp"), "SA", "",
				null, null);
	}

	public static JDBCStringSet connectToHSQLDB(File fileName) throws SQLException {
		return new JDBCStringSet("jdbc:hsqldb:file:/" + fileName.getAbsolutePath(), "SA", "", null, null);
	}

	public static JDBCStringSet connectToHSQLDB(File fileName, String tableName) throws SQLException {
		return new JDBCStringSet("jdbc:hsqldb:file:/" + fileName.getAbsolutePath(), "SA", "", tableName, null);
	}

	public static JDBCStringSet connectToHSQLDB(File fileName, String userName, String password, String tableName)
			throws SQLException {
		return new JDBCStringSet("jdbc:hsqldb:file:/" + fileName.getAbsolutePath(), userName, password, tableName, null);
	}

	public static JDBCStringSet connectToHSQLDB(File fileName, String userName, String password, String tableName,
			String columnName) throws SQLException {
		return new JDBCStringSet("jdbc:hsqldb:file:/" + fileName.getAbsolutePath(), userName, password, tableName,
				columnName);
	}

	protected void createTable(String tableName, String columnName) throws SQLException {
		SQLUtil.createKeyStringTable(getConnection(), getSQLDialect(), tableName, columnName);
	}

	protected void setKey(PreparedStatement statement, int position, String key) throws SQLException {
		statement.setString(position, key);
	}

	protected String getKey(ResultSet rs, int position) throws SQLException {
		return rs.getString(position);
	}

}
