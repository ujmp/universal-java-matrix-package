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

package org.ujmp.core.objectmatrix.calculation;

import org.ujmp.core.objectmatrix.impl.DefaultDenseObjectMatrix2D;

public class WelcomeMatrix extends DefaultDenseObjectMatrix2D {
	private static final long serialVersionUID = -4570303224080406364L;

	public WelcomeMatrix() {
		super(13, 9);
		setLabel("Welcome to UJMP");

		setAsString("Please visit", 2, 3);
		setAsString("http://www.ujmp.org/", 2, 4);
		setAsString("for more info", 2, 5);

		setAsString("Welcome", 3, 1);
		setAsString("to the", 3, 2);
		setAsString("Universal", 3, 3);
		setAsString("Java", 3, 4);
		setAsString("Matrix", 3, 5);
		setAsString("Package", 3, 6);
		setAsString("UJMP", 3, 7);

		setAsDouble(100, 5, 2);
		setAsDouble(100, 5, 6);

		setAsDouble(1, 7, 4);

		setAsDouble(-1, 9, 2);
		setAsDouble(-1, 9, 6);
		setAsDouble(-1, 10, 3);
		setAsDouble(-1, 10, 4);
		setAsDouble(-1, 10, 5);
	}

}
