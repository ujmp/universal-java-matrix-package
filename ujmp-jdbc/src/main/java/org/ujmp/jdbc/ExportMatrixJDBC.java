/*
 * Copyright (C) 2008-2011 by Holger Arndt
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
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.ujmp.core.Matrix;
import org.ujmp.core.enums.DB;
import org.ujmp.core.exceptions.MatrixException;

public abstract class ExportMatrixJDBC {

	public static void toDatabase(Matrix matrix, String url, String tablename,
			String username, String password) throws ClassNotFoundException,
			SQLException {
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

		String s = "CREATE TABLE IF NOT EXISTS " + tablename + " (";
		for (int c = 0; c < matrix.getColumnCount(); c++) {
			s += "`" + getColumnName(matrix, c) + "` TEXT";
			if (c < matrix.getColumnCount() - 1) {
				s += ", ";
			}
		}
		s += ");";

		statement.executeUpdate(s);

		s = "INSERT IGNORE INTO " + tablename + " (";
		for (int c = 0; c < matrix.getColumnCount(); c++) {
			s += "`" + getColumnName(matrix, c) + "`";
			if (c < matrix.getColumnCount() - 1) {
				s += ", ";
			}
		}
		s += ") VALUES (";
		for (int c = 0; c < matrix.getColumnCount(); c++) {
			s += "?";
			if (c < matrix.getColumnCount() - 1) {
				s += ", ";
			}
		}
		s += ");";

		PreparedStatement ps = connection.prepareStatement(s);

		for (long r = 0; r < matrix.getRowCount(); r++) {
			for (int c = 0; c < matrix.getColumnCount(); c++) {
				ps.setString(c + 1, matrix.getAsString(r, c));
			}
			ps.addBatch();
			if (r % 1000 == 0) {
				ps.executeBatch();
			}
		}

		ps.executeBatch();

		ps.close();

		connection.close();
	}

	private static final String getColumnName(Matrix matrix, long column) {
		String columnName = matrix.getColumnLabel(column);
		if (columnName == null || columnName.length() == 0) {
			columnName = "column " + column;
		}
		return columnName;
	}

	public static void toDatabase(Matrix matrix, DB type, String host,
			int port, String databasename, String tablename, String username,
			String password) throws ClassNotFoundException, SQLException {
		switch (type) {
		case MySQL:
			toDatabase(matrix, "jdbc:mysql://" + host + ":" + port + "/"
					+ databasename, tablename, username, password);
			break;
		default:
			throw new MatrixException("not supported: " + type);
		}
	}

}
