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

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

import org.ujmp.core.Matrix;
import org.ujmp.core.implementations.AbstractMatrixTest;

public class TestJDBCSparseObjectMatrix extends AbstractMatrixTest {

	public Matrix createMatrix(long... size) throws Exception {
		return new JDBCSparseObjectMatrix(size);
//		File tempFile = File.createTempFile("hsqldb", "");
//		try {
//			Class.forName("org.hsqldb.jdbcDriver");
//			String username = "SA";
//			String password = "";
//			String tableName = "matrix";
//			String columnForValue = "value";
//			String[] columnsForCoordinates = new String[] { "row", "column" };
//			String url = "jdbc:hsqldb:" + tempFile + ";shutdown=true";
//			String sql = "CREATE TABLE matrix (value float, row int, column int)";
//			Connection c = DriverManager.getConnection(url, username, password);
//			c.createStatement().execute(sql);
//			Matrix m = new JDBCSparseObjectMatrix(size, c, tableName,
//					columnForValue, columnsForCoordinates);
//			return m;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		} finally {
//			if (tempFile.exists()) {
//				tempFile.delete();
//			}
//		}

	}

	public Matrix createMatrix(Matrix source) throws Exception {
		Matrix m = createMatrix(source.getSize());
		for (long[] c : source.availableCoordinates()) {
			m.setAsDouble(source.getAsDouble(c), c);

		}
		return m;
	}

	public boolean isTestLarge() {
		return false;
	}

	@Override
	public void testAvailableCoordinateIterator2D() throws Exception {
		// TODO
	}

}
