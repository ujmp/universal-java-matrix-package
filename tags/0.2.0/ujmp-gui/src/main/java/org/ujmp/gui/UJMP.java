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

package org.ujmp.gui;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.enums.ValueType;

public class UJMP {

	public static final String UJMPVERSION = org.ujmp.core.UJMP.UJMPVERSION;

	public static void main(String[] args) {

		Matrix m = MatrixFactory.zeros(ValueType.OBJECT, 13, 9);

		m.setLabel("Welcome to UJMP");

		m.setObject("Please visit", 2, 3);
		m.setObject("http://www.ujmp.org/", 2, 4);
		m.setObject("for more info", 2, 5);

		m.setObject("Welcome", 3, 1);
		m.setObject("to the", 3, 2);
		m.setObject("Universal", 3, 3);
		m.setObject("Java", 3, 4);
		m.setObject("Matrix", 3, 5);
		m.setObject("Package", 3, 6);
		m.setObject("UJMP", 3, 7);

		m.setObject(100, 5, 2);
		m.setObject(100, 5, 6);

		m.setObject(1, 7, 4);

		m.setObject(-1, 9, 2);
		m.setObject(-1, 9, 6);
		m.setObject(-1, 10, 3);
		m.setObject(-1, 10, 4);
		m.setObject(-1, 10, 5);

		m.showGUI();
	}

}
