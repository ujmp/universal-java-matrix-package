/*
 * Copyright (C) 2008-2016 by Holger Arndt
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

import java.io.InputStream;

import org.ujmp.core.Matrix;
import org.ujmp.core.intmatrix.impl.ImageMatrix;

public class ImageMatrixExample {

	public static void main(String[] args) throws Exception {

		// get an image
		InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("org/ujmp/examples/bigdata.jpg");

		// load image into matrix. of course, this works with files, too.
		Matrix imageMatrix = new ImageMatrix(is);

		// show on screen
		imageMatrix.showGUI();

		is.close();
	}
}
