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

package org.ujmp.jdbc.matrix;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.ujmp.core.listmatrix.AbstractListMatrix;
import org.ujmp.jdbc.autoclose.AutoOpenCloseConnection;
import org.ujmp.jdbc.util.SQLUtil;
import org.ujmp.jdbc.util.SQLUtil.SQLDialect;

public class JDBCCatalogListMatrix extends AbstractListMatrix<JDBCCatalogMatrix> {
	private static final long serialVersionUID = -1738017101718658172L;

	public static final String CONNECTION = "Connection";
	public static final String URL = "URL";
	public static final String SQLDIALECT = "SQLDialect";
	public static final String DATABASENAME = "DatabaseName";

	private final Connection connection;

	public static JDBCCatalogListMatrix connectToSQLite(File file) throws SQLException {
		return new JDBCCatalogListMatrix("jdbc:sqlite:" + file.getAbsolutePath(), null, null);
	}

	protected JDBCCatalogListMatrix(String url, String username, String password) throws SQLException {
		this(new AutoOpenCloseConnection(url, username, password));
	}

	public JDBCCatalogListMatrix(Connection connection) throws SQLException {
		this.connection = connection;
		String url = connection.getMetaData().getURL();
		setMetaData(URL, url);
		setMetaData(SQLDIALECT, SQLUtil.getSQLDialect(url));
	}

	public final SQLDialect getSQLDialect() {
		Object sqlDialect = getMetaData(SQLDIALECT);
		if (sqlDialect instanceof SQLDialect) {
			return (SQLDialect) getMetaData(SQLDIALECT);
		} else {
			return null;
		}
	}

	public synchronized void removeDatabase(String database) {
		try {
			PreparedStatement st = SQLUtil.getDropDatabaseStatement(connection, getSQLDialect(), database);
			st.executeUpdate();
			st.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public synchronized void addDatabase(String database) {
		try {
			PreparedStatement st = SQLUtil.getCreateDatabaseStatement(connection, getSQLDialect(), database);
			st.executeUpdate();
			st.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getCatalogName(int index) throws SQLException {
		DatabaseMetaData meta = connection.getMetaData();
		ResultSet rs = meta.getCatalogs();
		rs.absolute(index + 1);
		String catalogName = rs.getString(1);
		rs.close();
		return catalogName;
	}

	@Override
	public JDBCCatalogMatrix get(int index) {
		try {
			String catalogName = getCatalogName(index);
			JDBCCatalogMatrix matrix = new JDBCCatalogMatrix(connection, catalogName);
			return matrix;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean addToList(JDBCCatalogMatrix t) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addToList(int index, JDBCCatalogMatrix element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public JDBCCatalogMatrix removeFromList(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeFromList(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public JDBCCatalogMatrix setToList(int index, JDBCCatalogMatrix element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clearList() {
		throw new UnsupportedOperationException();
	}

	@Override
	public synchronized int size() {
		try {
			DatabaseMetaData meta = connection.getMetaData();
			ResultSet rs = meta.getCatalogs();
			int count = 0;
			while (rs.next()) {
				count++;
			}
			rs.close();
			return count;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}