/*
 * Copyright (C) 2008-2009 Holger Arndt, A. Naegele and M. Bundschus
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

import java.lang.reflect.Method;

import org.ujmp.core.enums.DB;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.objectmatrix.ObjectMatrix2D;

public class ImportMatrixJDBC {

	public static ObjectMatrix2D fromDatabase(String url, String sqlStatement, String username,
			String password) {
		try {
			Class<?> c = Class.forName("org.ujmp.jdbc.ImportMatrixJDBC");
			Method method = c.getMethod("fromDatabase", new Class[] { String.class, String.class,
					String.class, String.class });
			ObjectMatrix2D matrix = (ObjectMatrix2D) method.invoke(null, url, sqlStatement,
					username, password);
			return matrix;
		} catch (Exception e) {
			throw new MatrixException("ujmp-jdbc not found in classpath", e);
		}
	}

	public static ObjectMatrix2D fromDatabase(DB type, String host, int port, String database,
			String sqlStatement, String username, String password) {
		try {
			Class<?> c = Class.forName("org.ujmp.jdbc.ImportMatrixJDBC");
			Method method = c.getMethod("fromDatabase", new Class[] { DB.class, String.class,
					Integer.TYPE, String.class, String.class, String.class, String.class });
			ObjectMatrix2D matrix = (ObjectMatrix2D) method.invoke(null, type, host, port,
					database, sqlStatement, username, password);
			return matrix;
		} catch (Exception e) {
			throw new MatrixException("ujmp-jdbc not found in classpath", e);
		}
	}

}
