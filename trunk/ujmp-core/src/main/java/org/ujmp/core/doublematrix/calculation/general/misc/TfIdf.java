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

package org.ujmp.core.doublematrix.calculation.general.misc;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.calculation.AbstractDoubleCalculation;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.longmatrix.calculation.DocTerm;
import org.ujmp.core.util.MathUtil;

public class TfIdf extends AbstractDoubleCalculation {
	private static final long serialVersionUID = 4262822624560201379L;

	/**
	 * matrix with [documents x terms]
	 */
	private Matrix docTerm = null;

	private Matrix sumPerDoc = null;

	private Matrix sumPerTerm = null;

	private boolean calculateTf = false;

	private boolean calculateIdf = false;

	private boolean normalize = false;

	public TfIdf(Matrix matrix, boolean calculateTf, boolean calculateIdf, boolean normalize) {
		super(matrix);
		this.calculateTf = calculateTf;
		this.calculateIdf = calculateIdf;
		this.normalize = normalize;
		if (normalize) {
			throw new MatrixException("not yet implemented");
		}
	}

	
	public double getDouble(long... coordinates) throws MatrixException {
		if (docTerm == null) {
			calculate();
		}

		double tf = docTerm.getAsDouble(coordinates);
		double idf = 1.0;

		double numDocs = docTerm.getRowCount();

		if (calculateTf) {
			tf = docTerm.getAsDouble(coordinates) / sumPerDoc.getAsDouble(coordinates[ROW], 0);
		}

		if (calculateIdf) {
			idf = MathUtil.log10(numDocs / sumPerTerm.getAsDouble(0, coordinates[COLUMN]));
		}

		double result = tf * idf;
		return MathUtil.isNaNOrInfinite(result) ? 0.0 : result;
	}

	private void calculate() {
		docTerm = new DocTerm(getSource()).calcNew();
		if (calculateTf) {
			sumPerDoc = docTerm.sum(Ret.NEW, Matrix.COLUMN, true);
		}
		if (calculateIdf) {
			sumPerTerm = docTerm.toBooleanMatrix().sum(Ret.NEW, Matrix.ROW, true);
		}
	}

	
	public long[] getSize() {
		if (docTerm == null) {
			calculate();
		}
		return docTerm.getSize();
	}

}
