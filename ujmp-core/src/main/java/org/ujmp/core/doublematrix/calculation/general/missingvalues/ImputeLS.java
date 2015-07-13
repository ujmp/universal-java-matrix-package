/*
 * Copyright (C) 2008-2015 by Holger Arndt
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

package org.ujmp.core.doublematrix.calculation.general.missingvalues;

import java.lang.reflect.Constructor;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.calculation.AbstractDoubleCalculation;
import org.ujmp.core.doublematrix.calculation.DoubleCalculation;
import org.ujmp.core.doublematrix.calculation.general.missingvalues.Impute.ImputationMethod;

public class ImputeLS extends AbstractDoubleCalculation {
	private static final long serialVersionUID = 6803633047911888483L;

	private Matrix imp = null;

	private ImputationMethod method = null;

	public ImputeLS(Matrix matrix, ImputationMethod method) {
		super(matrix);
		this.method = method;
	}

	public static boolean isAvailable() {
		try {
			Class.forName("Impute");
		} catch (ClassNotFoundException e) {
			return false;
		}
		return true;
	}

	public double getDouble(long... coordinates) {
		if (imp == null) {
			try {

				DoubleCalculation calc = null;

				try {
					if (calc == null) {
						Class<?> c = Class.forName("org.ujmp.lsimpute.LSImpute");
						Constructor<?> con = c.getConstructor(Matrix.class, ImputationMethod.class);

						calc = (DoubleCalculation) con.newInstance(getSource(), method);
					}
				} catch (ClassNotFoundException e) {
				}

				if (calc == null) {
					throw new RuntimeException("could not find LSimpute.jar in your classpath");
				}

				imp = calc.calcNew();

			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return imp.getAsDouble(coordinates);
	}
}
