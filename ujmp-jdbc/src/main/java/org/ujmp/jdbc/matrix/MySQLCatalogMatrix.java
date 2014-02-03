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

public class MySQLCatalogMatrix extends AbstractMapMatrix<String, Matrix> {
	private static final long serialVersionUID = 8777880644085058629L;

	private String url;
	private String catalogName;
	private String user;
	private String password;

	private Map<String, Matrix> map = null;

	public MySQLCatalogMatrix(String url, String catalogName, String user, String password) {
		this.url = url;
		this.catalogName = catalogName;
		this.user = user;
		this.password = password;
	}

	@Override
	public Map<String, Matrix> getMap() {
		if (map == null) {
			synchronized (this) {
				if (map == null) {
					try {
						map = new MySQLCatalogMap(url, catalogName, user, password);
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

class MySQLCatalogMap extends AbstractMap<String, Matrix> {
	private static final long serialVersionUID = -67336238817050153L;

	private Map<String, Matrix> map = new TreeMap<String, Matrix>();

	public MySQLCatalogMap(String url, String catalogName, String user, String password) throws ClassNotFoundException, SQLException {
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
		c.setCatalog(catalogName);
		DatabaseMetaData meta = c.getMetaData();
		ResultSet rs = meta.getTables(null, null, "%", null);

		while (rs.next()) {
			String tableName = rs.getString(3);
			Matrix tableMatrix = new DenseMySQLMatrix2D(url + "/" + catalogName, "select * from " + tableName, user, password);
			map.put(tableName, tableMatrix);
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