/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
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

import org.ujmp.core.enums.DB;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.objectmatrix.ObjectMatrix2D;

public class LinkMatrixJDBC {

	public static ObjectMatrix2D toDatabase(String url, String sqlStatement, String username, String password)
			throws Exception {
		if (url.startsWith("jdbc:mysql://")) {
			return new DenseMySQLMatrix2D(url, sqlStatement, username, password);
		} else if (url.startsWith("jdbc:postgresql://")) {
			return new DensePostgreSQLMatrix2D(url, sqlStatement, username, password);
		} else if (url.startsWith("jdbc:derby://")) {
			return new DenseDerbyMatrix2D(url, sqlStatement, username, password);
		} else if (url.startsWith("jdbc:hsqldb://")) {
			return new DenseHSQLDBMatrix2D(url, sqlStatement, username, password);
		} else {
			throw new MatrixException("Database format not supported: " + url);
		}
	}

	public static ObjectMatrix2D toDatabase(DB type, String host, int port, String databasename, String sqlStatement,
			String username, String password) throws Exception {
		switch (type) {
		case MySQL:
			return toDatabase("jdbc:mysql://" + host + ":" + port + "/" + databasename, sqlStatement, username,
					password);
		case PostgreSQL:
			return toDatabase("jdbc:postgresql://" + host + ":" + port + "/" + databasename, sqlStatement, username,
					password);
		case Derby:
			return toDatabase("jdbc:derby://" + host + ":" + port + "/" + databasename, sqlStatement, username,
					password);
		case HSQLDB:
			return toDatabase("jdbc:hsqldb://" + host + ":" + port + "/" + databasename, sqlStatement, username,
					password);
		default:
			throw new MatrixException("Database type unknown: " + type);
		}
	}
}
