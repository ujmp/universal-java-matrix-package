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

import java.applet.Applet;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.enums.ValueType;

public class UJMP extends Applet {
	private static final long serialVersionUID = 9112777043274552132L;

	public static final String UJMPVERSION = org.ujmp.core.UJMP.UJMPVERSION;

	public static void main(String[] args) {
		Matrix m = MatrixFactory.zeros(ValueType.OBJECT, 13, 9);

		m.setLabel("Welcome to UJMP");

		m.setAsObject("Please visit", 2, 3);
		m.setAsObject("http://www.ujmp.org/", 2, 4);
		m.setAsObject("for more info", 2, 5);

		m.setAsObject("Welcome", 3, 1);
		m.setAsObject("to the", 3, 2);
		m.setAsObject("Universal", 3, 3);
		m.setAsObject("Java", 3, 4);
		m.setAsObject("Matrix", 3, 5);
		m.setAsObject("Package", 3, 6);
		m.setAsObject("UJMP", 3, 7);

		m.setAsObject(100, 5, 2);
		m.setAsObject(100, 5, 6);

		m.setAsObject(1, 7, 4);

		m.setAsObject(-1, 9, 2);
		m.setAsObject(-1, 9, 6);
		m.setAsObject(-1, 10, 3);
		m.setAsObject(-1, 10, 4);
		m.setAsObject(-1, 10, 5);

		m.showGUI();
	}

	public void init() {
		main(new String[] {});
	}

}
