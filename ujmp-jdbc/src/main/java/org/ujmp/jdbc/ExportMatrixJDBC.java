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

package org.ujmp.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.ujmp.core.Matrix;
import org.ujmp.core.enums.DBType;

public abstract class ExportMatrixJDBC {

	public static void toDatabase(Matrix matrix, String url, String tablename, String username, String password)
			throws ClassNotFoundException, SQLException {
		if (matrix == null) {
			return;
		} else if (matrix.isEmpty()) {
			return;
		}

		if (url.startsWith("jdbc:mysql://")) {
			Class.forName("com.mysql.jdbc.Driver");
		} else if (url.startsWith("jdbc:postgresql://")) {
			Class.forName("org.postgresql.Driver");
		} else {
			throw new RuntimeException("Database format not supported: " + url);
		}

		System.out.print("exporting...");

		String[] fields = url.split("/");

		if (fields.length == 4) {
			String[] params = fields[3].split("\\?");
			Connection connection = DriverManager.getConnection(fields[0] + "/" + fields[1] + "/" + fields[2],
					username, password);
			Statement statement = connection.createStatement();
			String db = "CREATE DATABASE IF NOT EXISTS " + params[0];
			statement.executeUpdate(db);
			statement.close();
			connection.close();
		}

		Connection connection = DriverManager.getConnection(url, username, password);

		Statement statement = connection.createStatement();

		// String sql = "CREATE TABLE IF NOT EXISTS `" + tablename + "` (";
		// for (int c = 0; c < matrix.getColumnCount(); c++) {
		// sql += "`" + getColumnName(matrix, c) + "` "
		// + getColumnType(getDBTypeFromUrl(url), matrix, c);
		// if (c < matrix.getColumnCount() - 1) {
		// sql += ", ";
		// }
		// }
		// sql += ");";
		//
		// statement.executeUpdate(sql);
		//
		// sql = "INSERT IGNORE INTO `" + tablename + "` (";
		// for (int c = 0; c < matrix.getColumnCount(); c++) {
		// sql += "`" + getColumnName(matrix, c) + "`";
		// if (c < matrix.getColumnCount() - 1) {
		// sql += ", ";
		// }
		// }
		// sql += ") VALUES (";
		// for (int c = 0; c < matrix.getColumnCount(); c++) {
		// sql += "?";
		// if (c < matrix.getColumnCount() - 1) {
		// sql += ", ";
		// }
		// }
		// sql += ");";
		//
		//
		// PreparedStatement ps = connection.prepareStatement(sql);
		//
		// for (long r = 0; r < matrix.getRowCount(); r++) {
		// for (int c = 0; c < matrix.getColumnCount(); c++) {
		// ps.setObject(c + 1, matrix.getAsObject(r, c));
		// }
		// ps.addBatch();
		// if (r % 1000 == 0) {
		// System.out.print(".");
		// // ps.executeBatch();
		// }
		// }
		//
		// ps.executeBatch();
		// ps.close();
		//
		// connection.commit();
		// connection.close();

		System.out.println("done");
	}

	private static DBType getDBTypeFromUrl(String url) {
		if (url == null) {
			return null;
		} else if (url.startsWith("jdbc:mysql")) {
			return DBType.MySQL;
		} else {
			return null;
		}
	}

	public static void toDatabase(Matrix matrix, DBType type, String host, int port, String databasename,
			String tablename, String username, String password) throws ClassNotFoundException, SQLException {
		switch (type) {
		case MySQL:
			toDatabase(matrix, "jdbc:mysql://" + host + ":" + port + "/" + databasename, tablename, username, password);
			break;
		default:
			throw new RuntimeException("not supported: " + type);
		}
	}

}
