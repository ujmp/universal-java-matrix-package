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

package org.ujmp.examples;

import java.io.File;

import org.ujmp.core.Matrix;

public class ExtractExcelDataExample {

	public static void main(String[] args) throws Exception {

		// find all Excel files in one directory
		File[] files = new File("c:/temp/").listFiles();

		// create matrix to store result
		Matrix result = Matrix.Factory.zeros(files.length, 2);

		// iterate over all files
		for (int i = 0; i < files.length; i++) {

			// import file as matrix
			Matrix m = Matrix.Factory.importFrom().file(files[i]).asDenseCSV();

			// store file name in result matrix
			result.setAsString(files[i].getName(), i, 0);

			// search for "Invoice"
			if (m.containsString("Invoice"))

				// extract value at row 10 and column 3 and store in result
				result.setAsDouble(m.getAsDouble(10, 3), i, 1);
		}

		// display result on screen
		result.showGUI();
	}

}
