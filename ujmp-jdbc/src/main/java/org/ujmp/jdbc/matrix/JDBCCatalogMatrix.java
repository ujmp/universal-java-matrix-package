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

package org.ujmp.jdbc.matrix;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.ujmp.core.Matrix;
import org.ujmp.core.listmatrix.AbstractListMatrix;
import org.ujmp.jdbc.autoclose.AutoOpenCloseConnection;
import org.ujmp.jdbc.util.SQLUtil;

public class JDBCCatalogMatrix extends AbstractListMatrix<Matrix> {
	private static final long serialVersionUID = 8777880644085058629L;

	public static final String URL = "URL";
	public static final String DATABASENAME = "DatabaseName";
	public static final String SQLDIALECT = "SQLDialect";

	private transient Connection connection;

	public static JDBCCatalogMatrix connectToSQLite(File file) throws SQLException {
		return new JDBCCatalogMatrix("jdbc:sqlite:" + file.getAbsolutePath(), null, null, null);
	}

	protected JDBCCatalogMatrix(String url, String username, String password, String catalogName) throws SQLException {
		this(new AutoOpenCloseConnection(url, username, password), catalogName);
	}

	public JDBCCatalogMatrix(Connection connection, String catalogName) throws SQLException {
		this.connection = connection;
		String url = connection.getMetaData().getURL();
		setMetaData(URL, url);
		setMetaData(SQLDIALECT, SQLUtil.getSQLDialect(url));
		setMetaData(DATABASENAME, catalogName);
	}

	@Override
	public synchronized Matrix get(int index) {
		try {
			connection.setCatalog(getCatalogName());
			DatabaseMetaData meta = connection.getMetaData();
			ResultSet rs = meta.getTables(null, null, "%", null);
			int count = 0;
			String tableName = null;
			while (rs.next()) {
				if (count == index) {
					tableName = rs.getString(3);
					break;
				}
				count++;
			}
			// Matrix tableMatrix = new DenseMySQLMatrix2D(url + "/" +
			// catalogName,
			// "select * from " + tableName, user,
			// password);
			rs.close();
			Matrix m = Matrix.Factory.zeros(10, 10);
			m.setLabel(tableName);
			return m;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public String getCatalogName() {
		return getMetaDataString(DATABASENAME);
	}

	@Override
	public boolean addToList(Matrix t) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addToList(int index, Matrix element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Matrix removeFromList(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeFromList(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Matrix setToList(int index, Matrix element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clearList() {
		throw new UnsupportedOperationException();
	}

	@Override
	public synchronized int size() {
		try {
			connection.setCatalog(getCatalogName());
			DatabaseMetaData meta = connection.getMetaData();
			ResultSet rs = meta.getTables(null, null, "%", null);
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