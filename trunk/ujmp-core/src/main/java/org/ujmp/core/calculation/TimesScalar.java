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

package org.ujmp.core.calculation;

import java.math.BigDecimal;

import org.ujmp.core.DenseMatrix;
import org.ujmp.core.DenseMatrix2D;
import org.ujmp.core.Matrix;
import org.ujmp.core.SparseMatrix;
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;
import org.ujmp.core.interfaces.HasColumnMajorDoubleArray1D;
import org.ujmp.core.interfaces.HasRowMajorDoubleArray2D;
import org.ujmp.core.mapmatrix.MapMatrix;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.UJMPSettings;
import org.ujmp.core.util.VerifyUtil;
import org.ujmp.core.util.concurrent.PForEquidistant;

public class TimesScalar {
	public static final TimesScalarCalculation<Matrix, Matrix> MATRIX = new TimesScalarMatrix();

	public static final TimesScalarCalculation<DenseMatrix, DenseMatrix> DENSEMATRIX = new TimesScalarDenseMatrix();

	public static final TimesScalarCalculation<DenseMatrix2D, DenseMatrix2D> DENSEMATRIX2D = new TimesScalarDenseMatrix2D();

	public static final TimesScalarCalculation<DenseDoubleMatrix2D, DenseDoubleMatrix2D> DENSEDOUBLEMATRIX2D = new TimesScalarDenseDoubleMatrix2D();

	public static final TimesScalarCalculation<SparseMatrix, SparseMatrix> SPARSEMATRIX = new TimesScalarSparseMatrix();
}

class TimesScalarMatrix implements TimesScalarCalculation<Matrix, Matrix> {

	public final void calc(final Matrix source, final BigDecimal factor, final Matrix target) {
		if (source instanceof DenseDoubleMatrix2D && target instanceof DenseDoubleMatrix2D) {
			TimesScalar.DENSEDOUBLEMATRIX2D.calc((DenseDoubleMatrix2D) source, factor,
					(DenseDoubleMatrix2D) target);
		} else if (source instanceof DenseMatrix2D && target instanceof DenseMatrix2D) {
			TimesScalar.DENSEMATRIX2D.calc((DenseMatrix2D) source, factor,
					(DenseDoubleMatrix2D) target);
		} else if (source instanceof DenseMatrix && target instanceof DenseMatrix) {
			TimesScalar.DENSEMATRIX.calc((DenseMatrix) source, factor, (DenseMatrix) target);
		} else if (source instanceof SparseMatrix && target instanceof SparseMatrix) {
			TimesScalar.SPARSEMATRIX.calc((SparseMatrix) source, factor, (SparseMatrix) target);
		} else {
			VerifyUtil.verifySameSize(source, target);
			for (long[] c : source.allCoordinates()) {
				BigDecimal value = source.getAsBigDecimal(c);
				BigDecimal result = MathUtil.times(value, factor);
				target.setAsBigDecimal(result, c);
			}
			if (source != target) {
				MapMatrix<String, Object> a = source.getMetaData();
				if (a != null) {
					target.setMetaData(a.clone());
				}
			}
		}
	}

	public final void calc(final Matrix source, final double factor, final Matrix target) {
		if (source instanceof DenseMatrix && target instanceof DenseMatrix) {
			TimesScalar.DENSEMATRIX.calc((DenseMatrix) source, factor, (DenseMatrix) target);
		} else if (source instanceof SparseMatrix && target instanceof SparseMatrix) {
			TimesScalar.SPARSEMATRIX.calc((SparseMatrix) source, factor, (SparseMatrix) target);
		} else {
			calc(source, new BigDecimal(factor, UJMPSettings.getInstance().getMathContext()),
					target);
		}
	}
};

class TimesScalarDenseMatrix implements TimesScalarCalculation<DenseMatrix, DenseMatrix> {

	public final void calc(final DenseMatrix source, final BigDecimal factor,
			final DenseMatrix target) {
		if (source instanceof DenseMatrix2D && target instanceof DenseMatrix2D) {
			TimesScalar.DENSEMATRIX2D.calc((DenseMatrix2D) source, factor, (DenseMatrix2D) target);
		} else {
			VerifyUtil.verifySameSize(source, target);
			for (long[] c : source.allCoordinates()) {
				BigDecimal value = source.getAsBigDecimal(c);
				BigDecimal result = MathUtil.times(value, factor);
				target.setAsBigDecimal(result, c);
			}
			if (source != target) {
				MapMatrix<String, Object> a = source.getMetaData();
				if (a != null) {
					target.setMetaData(a.clone());
				}
			}
		}
	}

	public final void calc(final DenseMatrix source, final double factor, final DenseMatrix target) {
		if (source instanceof DenseMatrix2D && target instanceof DenseMatrix2D) {
			TimesScalar.DENSEMATRIX2D.calc((DenseMatrix2D) source, factor, (DenseMatrix2D) target);
		} else {
			calc(source, new BigDecimal(factor, UJMPSettings.getInstance().getMathContext()),
					target);
		}
	}
};

class TimesScalarSparseMatrix implements TimesScalarCalculation<SparseMatrix, SparseMatrix> {

	public final void calc(final SparseMatrix source, final BigDecimal factor,
			final SparseMatrix target) {
		VerifyUtil.verifySameSize(source, target);
		for (long[] c : source.availableCoordinates()) {
			BigDecimal value = source.getAsBigDecimal(c);
			BigDecimal result = MathUtil.times(value, factor);
			target.setAsBigDecimal(result, c);
		}
		if (source != target) {
			MapMatrix<String, Object> a = source.getMetaData();
			if (a != null) {
				target.setMetaData(a.clone());
			}
		}
	}

	public final void calc(final SparseMatrix source, final double factor, final SparseMatrix target) {
		calc(source, new BigDecimal(factor, UJMPSettings.getInstance().getMathContext()), target);
	}
};

class TimesScalarDenseMatrix2D implements TimesScalarCalculation<DenseMatrix2D, DenseMatrix2D> {

	public final void calc(final DenseMatrix2D source, final BigDecimal factor,
			final DenseMatrix2D target) {
		if (source instanceof DenseDoubleMatrix2D && target instanceof DenseDoubleMatrix2D) {
			TimesScalar.DENSEDOUBLEMATRIX2D.calc((DenseDoubleMatrix2D) source, factor,
					(DenseDoubleMatrix2D) target);
		} else {
			VerifyUtil.verifySameSize(source, target);
			for (int r = (int) source.getRowCount(); --r != -1;) {
				for (int c = (int) source.getColumnCount(); --c != -1;) {
					BigDecimal value = source.getAsBigDecimal(r, c);
					BigDecimal result = MathUtil.times(value, factor);
					target.setAsBigDecimal(result, r, c);
				}
			}
			if (source != target) {
				MapMatrix<String, Object> a = source.getMetaData();
				if (a != null) {
					target.setMetaData(a.clone());
				}
			}
		}
	}

	public final void calc(final DenseMatrix2D source, final double factor,
			final DenseMatrix2D target) {
		if (source instanceof DenseDoubleMatrix2D && target instanceof DenseDoubleMatrix2D) {
			TimesScalar.DENSEDOUBLEMATRIX2D.calc((DenseDoubleMatrix2D) source, factor,
					(DenseDoubleMatrix2D) target);
		} else {
			calc(source, new BigDecimal(factor, UJMPSettings.getInstance().getMathContext()),
					target);
		}
	}
};

class TimesScalarDenseDoubleMatrix2D implements
		TimesScalarCalculation<DenseDoubleMatrix2D, DenseDoubleMatrix2D> {

	public final void calc(final DenseDoubleMatrix2D source, final BigDecimal factor,
			final DenseDoubleMatrix2D target) {
		calc(source, factor.doubleValue(), target);
	}

	public final void calc(final DenseDoubleMatrix2D source, final double factor,
			final DenseDoubleMatrix2D target) {
		if (source instanceof HasColumnMajorDoubleArray1D
				&& target instanceof HasColumnMajorDoubleArray1D) {
			calc(((HasColumnMajorDoubleArray1D) source).getColumnMajorDoubleArray1D(), factor,
					((HasColumnMajorDoubleArray1D) target).getColumnMajorDoubleArray1D());
		} else if (source instanceof HasRowMajorDoubleArray2D
				&& target instanceof HasRowMajorDoubleArray2D) {
			calc(((HasRowMajorDoubleArray2D) source).getRowMajorDoubleArray2D(), factor,
					((HasRowMajorDoubleArray2D) target).getRowMajorDoubleArray2D());
		} else {
			VerifyUtil.verifySameSize(source, target);
			for (int r = (int) source.getRowCount(); --r != -1;) {
				for (int c = (int) source.getColumnCount(); --c != -1;) {
					target.setDouble(factor * source.getDouble(r, c), r, c);
				}
			}
		}
		if (source != target) {
			MapMatrix<String, Object> a = source.getMetaData();
			if (a != null) {
				target.setMetaData(a.clone());
			}
		}
	}

	private final void calc(final double[][] source, final double factor, final double[][] target) {
		VerifyUtil.verifySameSize(source, target);
		final int rows = source.length;
		final int cols = source[0].length;
		if (UJMPSettings.getInstance().getNumberOfThreads() > 1 && rows >= 100 && cols >= 100) {
			new PForEquidistant(0, rows - 1) {

				public void step(int i) {
					double[] tsource = source[i];
					double[] ttarget = target[i];
					for (int c = 0; c < cols; c++) {
						ttarget[c] = tsource[c] * factor;
					}
				}
			};
		} else {
			double[] tsource = null;
			double[] ttarget = null;
			for (int r = 0; r < rows; r++) {
				tsource = source[r];
				ttarget = target[r];
				for (int c = 0; c < cols; c++) {
					ttarget[c] = tsource[c] * factor;
				}
			}
		}
	}

	private final void calc(final double[] source, final double factor, final double[] target) {
		VerifyUtil.verifySameSize(source, target);
		final int length = source.length;
		for (int i = 0; i < length; i++) {
			target[i] = source[i] * factor;
		}
	}
};
