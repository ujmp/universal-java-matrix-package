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

package org.ujmp.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.ujmp.core.Matrix;
import org.ujmp.core.enums.DBType;
import org.ujmp.core.objectmatrix.DenseObjectMatrix2D;

public class ImportMatrixJDBC {

	public static DenseObjectMatrix2D fromDatabase(String url,
			String sqlStatement, String username, String password)
			throws Exception {
		if (url.startsWith("jdbc:mysql://")) {
			Class.forName("com.mysql.jdbc.Driver");
		} else if (url.startsWith("jdbc:postgresql://")) {
			Class.forName("org.postgresql.Driver");
		} else {
			throw new RuntimeException("Database format not supported: " + url);
		}

		Connection connection = DriverManager.getConnection(url, username,
				password);

		return fromDatabase(connection, sqlStatement);
	}

	public static DenseObjectMatrix2D fromDatabase(Connection connection,
			String sqlStatement) throws Exception {

		System.out.print("importing...");

		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sqlStatement);
		resultSet.setFetchSize(10000);

		ResultSetMetaData rsMetaData = resultSet.getMetaData();
		int columnCount = rsMetaData.getColumnCount();

		List<Object[]> allRows = new ArrayList<Object[]>();
		String[] columnLabels = new String[columnCount];
		String[] columnClassNames = new String[columnCount];

		for (int c = 0; c < columnCount; c++) {
			columnLabels[c] = rsMetaData.getColumnLabel(c + 1);
			columnClassNames[c] = rsMetaData.getColumnClassName(c + 1);
		}

		while (resultSet.next()) {
			Object[] oneRow = new Object[columnCount];
			for (int c = 0; c < columnCount; c++) {
				oneRow[c] = resultSet.getObject(c + 1);
			}
			allRows.add(oneRow);
			if (allRows.size() % 1000 == 0) {
				System.out.print(".");
			}
			if (allRows.size() % 100000 == 0) {
				break;
			}
		}

		resultSet.close();
		statement.close();

		Object[][] matrixData = new Object[allRows.size()][];
		for (int r = 0; r < allRows.size(); r++) {
			matrixData[r] = allRows.get(r);
		}

		DenseObjectMatrix2D matrix = Matrix.Factory.linkToArray(matrixData);
		DatabaseMetaData meta = connection.getMetaData();
		matrix.setLabel(meta.getURL() + " - " + sqlStatement);

		for (int c = 0; c < columnCount; c++) {
			matrix.setColumnLabel(c, columnLabels[c]);
		}

		System.out.println("done");

		return matrix;
	}

	public static DenseObjectMatrix2D fromDatabase(DBType type, String host,
			int port, String databasename, String sqlStatement,
			String username, String password) throws Exception {
		switch (type) {
		case MySQL:
			return fromDatabase("jdbc:mysql://" + host + ":" + port + "/"
					+ databasename, sqlStatement, username, password);
		default:
			throw new RuntimeException("not supported: " + type);
		}
	}
}
