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

package org.ujmp.core.util;

import org.ujmp.core.mapmatrix.MapMatrix;

public abstract class CommandLineUtil {

	public static final void parse(MapMatrix<String, Object> matrix, String... args) {
		for (String s : args) {
			if (s != null) {
				if (s.startsWith("--")) {
					addArgument(matrix, s.replaceFirst("--", ""));
				} else if (s.startsWith("-")) {
					addArgument(matrix, s.replaceFirst("-", ""));
				} else {
					matrix.put("name", s);
				}
			}
		}
	}

	private static void addArgument(MapMatrix<String, Object> matrix, String s) {
		if (s.contains("=")) {
			String[] keyValue = s.split("=");
			String key = keyValue[0];
			String value = keyValue[1];
			addKeyValue(matrix, key, value);
		} else {
			addKeyValue(matrix, s, true);
		}
	}

	private static void addKeyValue(MapMatrix<String, Object> matrix, String key, Object value) {
		for (String k : matrix.keySet()) {
			if (k.equalsIgnoreCase(key)) {
				matrix.put(k, value);
				break;
			}
		}
	}

	public static final void printOptions(MapMatrix<String, Object> config) {
		for (String s : config.keySet()) {
			System.out.println(StringUtil.pad("--" + s, 20) + " (default: " + config.get(s) + ")");
		}
	}
}
