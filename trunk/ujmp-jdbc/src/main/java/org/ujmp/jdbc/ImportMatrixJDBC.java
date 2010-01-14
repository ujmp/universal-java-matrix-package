/*
 * Copyright (C) 2008-2010 by Holger Arndt
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
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import org.ujmp.core.MatrixFactory;
import org.ujmp.core.enums.DB;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.objectmatrix.ObjectMatrix2D;

public class ImportMatrixJDBC {

	public static ObjectMatrix2D fromDatabase(String url, String sqlStatement,
			String username, String password) throws Exception {
		if (url.startsWith("jdbc:mysql://")) {
			Class.forName("com.mysql.jdbc.Driver");
		} else if (url.startsWith("jdbc:postgresql://")) {
			Class.forName("org.postgresql.Driver");
		} else {
			throw new MatrixException("Database format not supported: " + url);
		}

		Connection connection = DriverManager.getConnection(url, username,
				password);
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sqlStatement);
		ResultSetMetaData rsMetaData = resultSet.getMetaData();
		long columnCount = rsMetaData.getColumnCount();
		resultSet.last();
		long rowCount = resultSet.getRow();
		resultSet.first();
		ObjectMatrix2D m = (ObjectMatrix2D) MatrixFactory.zeros(
				ValueType.OBJECT, rowCount, columnCount);

		for (int c = 0; c < columnCount; c++) {
			m.setColumnLabel(c, rsMetaData.getColumnLabel(c + 1));
		}

		for (int r = 0; r < rowCount; r++) {
			for (int c = 0; c < columnCount; c++) {
				m.setObject(resultSet.getObject(c + 1), r, c);
			}
			resultSet.next();
		}

		resultSet.close();
		statement.close();
		connection.close();
		return m;
	}

	public static ObjectMatrix2D fromDatabase(DB type, String host, int port,
			String databasename, String sqlStatement, String username,
			String password) throws Exception {
		switch (type) {
		case MySQL:
			return fromDatabase("jdbc:mysql://" + host + ":" + port + "/"
					+ databasename, sqlStatement, username, password);
		default:
			throw new MatrixException("not supported: " + type);
		}
	}
}
