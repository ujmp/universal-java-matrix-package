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

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

import org.ujmp.core.util.AbstractPlugin;

public class Plugin extends AbstractPlugin {

	private TreeSet<Object> dependencies = null;

	private final List<String> neededClasses = new ArrayList<String>();

	public Plugin() {
		// dependencies.add("ujmp-core");
		// dependencies.add("mysql-connector-java.jar");
		// dependencies.add("postgresql.jar");
		// dependencies.add("derby.jar");
		// dependencies.add("hsqldb.jar");
		neededClasses.add("com.mysql.jdbc.Driver");
		neededClasses.add("org.postgresql.Driver");
		neededClasses.add("org.apache.derby.database.Database");
		neededClasses.add("org.hsqldb.Database");
	}

	public String getDescription() {
		return "provides import functionalities for JDBC databases";
	}

	public Collection<String> getNeededClasses() {
		return neededClasses;
	}

	public Collection<Object> getDependencies() {
		if (dependencies == null) {
			dependencies = new TreeSet<Object>();
			try {
				InputStream is = Plugin.class.getClassLoader().getResourceAsStream("pom.xml");
				if (is != null) {
					LineNumberReader reader = new LineNumberReader(new InputStreamReader(is));
					StringBuilder sb = new StringBuilder();
					String line = null;
					while ((line = reader.readLine()) != null) {
						sb.append(line);
					}
					reader.close();
					String s = sb.toString().replace("\r\n", "").replace("\n", "").replace("\t", "");
					String[] fields = s.split("<");

					for (int i = 0; i < fields.length; i++) {
						String f = fields[i];
						if (f.toLowerCase().startsWith("dependency>")) {

							String groupId = "";
							String artifactId = "";
							while (!f.toLowerCase().startsWith("/dependency>")) {
								f = fields[++i];
								if (f.startsWith("artifactId>")) {
									artifactId = f.substring(11);
								} else if (f.startsWith("groupId>")) {
									groupId = f.substring(8);
								}
							}
							dependencies.add(groupId + "." + artifactId);
						}
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return dependencies;
	}

}
