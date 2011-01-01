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

package org.ujmp.core.util;

import java.io.File;

public class CreateInterfaces {

	public static final String PATH = System.getProperty("user.dir") + "/src/main/java/";

	public static final String[] CELLTYPES = { "", "BigDecimal", "BigInteger", "Boolean", "Byte",
			"Char", "Date", "Double", "Float", "Int", "Long", "Short", "String" };

	public static final String[] DENSESPARSE = { "", "Dense", "Sparse" };

	public static final String[] DIMS = { "", "2D" };

	public static void main(String[] args) throws Exception {
		createInterfaces();
	}

	public static void createInterfaces() throws Exception {
		for (String densesparse : DENSESPARSE) {
			for (String cell : CELLTYPES) {
				for (String dim : DIMS) {
					if ("".equals(densesparse) && "".equals(cell) && "".equals(dim)) {
						continue;
					}

					String packagename = "org.ujmp.core." + cell.toLowerCase() + "matrix";
					String interfacename = densesparse + cell + "Matrix" + dim;
					String filename = PATH + packagename.replaceAll("\\.", "/") + "/"
							+ interfacename + ".java";
					System.out.println(new File(filename).exists() + " " + filename + " "
							+ packagename + "." + interfacename);
				}
			}
		}
	}
}
