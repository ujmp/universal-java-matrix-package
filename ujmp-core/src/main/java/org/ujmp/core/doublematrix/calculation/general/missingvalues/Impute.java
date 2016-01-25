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

package org.ujmp.core.doublematrix.calculation.general.missingvalues;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.calculation.AbstractDoubleCalculation;
import org.ujmp.core.doublematrix.calculation.DoubleCalculation;

public class Impute extends AbstractDoubleCalculation {
	private static final long serialVersionUID = -8899889992449926887L;

	public enum ImputationMethod {
		Zero, RowMean, ColumnMean, Regression, KNN, EM, BPCA, EMimputeGene, EMimputeArray, LSimputeGene, LSimputeArray, LSimputeCombined, LSimputeAdaptive
	};

	private Matrix imp = null;

	private ImputationMethod method = null;

	private Object[] parameters = null;

	public Impute(Matrix matrix, ImputationMethod method, Object... parameters) {
		super(matrix);
		this.method = method;
		this.parameters = parameters;
	}

	public double getDouble(long... coordinates) {
		if (imp == null) {
			try {

				DoubleCalculation calc = null;

				switch (method) {
				case Zero:
					calc = new ImputeZero(getSource());
					break;
				case RowMean:
					calc = new ImputeMean(Matrix.ROW, getSource());
					break;
				case ColumnMean:
					calc = new ImputeMean(Matrix.COLUMN, getSource());
					break;
				case BPCA:
					calc = new ImputeBPCA(getSource());
					break;
				case KNN:
					calc = new ImputeKNN(getSource(), parameters);
					break;
				case EM:
					calc = new ImputeEM(getSource());
					break;
				case Regression:
					calc = new ImputeRegression(getSource());
					break;
				case EMimputeGene:
					calc = new ImputeLS(getSource(), method);
					break;
				case EMimputeArray:
					calc = new ImputeLS(getSource(), method);
					break;
				case LSimputeGene:
					calc = new ImputeLS(getSource(), method);
					break;
				case LSimputeArray:
					calc = new ImputeLS(getSource(), method);
					break;
				case LSimputeCombined:
					calc = new ImputeLS(getSource(), method);
					break;
				case LSimputeAdaptive:
					calc = new ImputeLS(getSource(), method);
					break;
				}

				imp = calc.calcNew();

			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return imp.getAsDouble(coordinates);
	}

}
