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

package org.ujmp.core.io;

import java.io.File;
import java.lang.reflect.Method;
import java.sql.Connection;

import org.ujmp.core.Matrix;
import org.ujmp.core.enums.DBType;
import org.ujmp.core.objectmatrix.DenseObjectMatrix2D;

public class LinkMatrixJDBC {

	public static DenseObjectMatrix2D toDatabase(String url, String sqlStatement, String username,
			String password) {
		try {
			Class<?> c = Class.forName("org.ujmp.jdbc.LinkMatrixJDBC");
			Method method = c.getMethod("toDatabase", new Class[] { String.class, String.class,
					String.class, String.class });
			DenseObjectMatrix2D matrix = (DenseObjectMatrix2D) method.invoke(null, url,
					sqlStatement, username, password);
			return matrix;
		} catch (Exception e) {
			throw new RuntimeException("ujmp-jdbc not found in classpath", e);
		}
	}

	public static DenseObjectMatrix2D toDatabase(DBType type, String host, int port,
			String database, String sqlStatement, String username, String password) {
		try {
			Class<?> c = Class.forName("org.ujmp.jdbc.LinkMatrixJDBC");
			Method method = c.getMethod("toDatabase", new Class[] { DBType.class, String.class,
					Integer.TYPE, String.class, String.class, String.class, String.class });
			DenseObjectMatrix2D matrix = (DenseObjectMatrix2D) method.invoke(null, type, host,
					port, database, sqlStatement, username, password);
			return matrix;
		} catch (Exception e) {
			throw new RuntimeException("ujmp-jdbc not found in classpath", e);
		}
	}

	public static DenseObjectMatrix2D toDatabase(Connection connection, String sqlStatement) {
		try {
			Class<?> c = Class.forName("org.ujmp.jdbc.LinkMatrixJDBC");
			Method method = c.getMethod("toDatabase",
					new Class[] { Connection.class, String.class });
			DenseObjectMatrix2D matrix = (DenseObjectMatrix2D) method.invoke(null, connection,
					sqlStatement);
			return matrix;
		} catch (Exception e) {
			throw new RuntimeException("ujmp-jdbc not found in classpath", e);
		}
	}

	public static Matrix toDatabase(File file) {
		try {
			Class<?> c = Class.forName("org.ujmp.jdbc.LinkMatrixJDBC");
			Method method = c.getMethod("toDatabase", new Class[] { File.class });
			DenseObjectMatrix2D matrix = (DenseObjectMatrix2D) method.invoke(null, file);
			return matrix;
		} catch (Exception e) {
			throw new RuntimeException("ujmp-jdbc not found in classpath", e);
		}
	}

}
