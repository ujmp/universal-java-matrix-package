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

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.ujmp.core.Matrix;
import org.ujmp.core.collections.map.AbstractMap;
import org.ujmp.core.mapmatrix.AbstractMapMatrix;
import org.ujmp.core.mapmatrix.MapMatrix;

public class MySQLDatabaseMapMatrix extends AbstractMapMatrix<String, Matrix> {
	private static final long serialVersionUID = -1738017101718658172L;

	private String url;
	private String user;
	private String password;

	private Map<String, Matrix> map = null;

	public MySQLDatabaseMapMatrix(String host, int port, String user, String password) {
		this("jdbc:mysql://" + host, user, password);
	}

	public MySQLDatabaseMapMatrix(String url, String user, String password) {
		this.url = url;
		this.user = user;
		this.password = password;
	}

	@Override
	public Map<String, Matrix> getMap() {
		if (map == null) {
			synchronized (this) {
				if (map == null) {
					try {
						map = new MySQLDatabaseMap(url, user, password);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return map;
	}

	@Override
	public MapMatrix<String, Matrix> clone() {
		return null;
	}

}

class MySQLDatabaseMap extends AbstractMap<String, Matrix> {
	private static final long serialVersionUID = -7610356458485490663L;

	private final Map<String, Matrix> map = new TreeMap<String, Matrix>();

	public MySQLDatabaseMap(String url, String user, String password) throws ClassNotFoundException, SQLException {
		if (url.startsWith("jdbc:hsqldb:")) {
			Class.forName("org.hsqldb.jdbcDriver");
		} else if (url.startsWith("jdbc:mysql:")) {
			Class.forName("com.mysql.jdbc.Driver");
		} else if (url.startsWith("jdbc:postgresql:")) {
			Class.forName("org.postgresql.Driver");
		} else if (url.startsWith("jdbc:derby:")) {
			Class.forName("org.apache.derby.jdbc.Driver40");
		}

		Connection c = DriverManager.getConnection(url, user, password);
		DatabaseMetaData meta = c.getMetaData();
		ResultSet rs = meta.getCatalogs();

		while (rs.next()) {
			String catalogName = rs.getString("TABLE_CAT");
			Matrix catalogMatrix = new MySQLCatalogMatrix(url, catalogName, user, password);
			map.put(catalogName, catalogMatrix);
		}

		rs.close();
		c.close();
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public Matrix get(Object key) {
		return map.get(key);
	}

	@Override
	public Set<String> keySet() {
		return map.keySet();
	}

	@Override
	public Matrix put(String key, Matrix value) {
		return map.put(key, value);
	}

	@Override
	public Matrix remove(Object key) {
		return map.remove(key);
	}

	@Override
	public int size() {
		return map.size();
	}
}