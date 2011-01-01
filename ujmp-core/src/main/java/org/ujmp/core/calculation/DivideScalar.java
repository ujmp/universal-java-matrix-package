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

package org.ujmp.core.calculation;

import java.math.BigDecimal;

import org.ujmp.core.Matrix;
import org.ujmp.core.annotation.Annotation;
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;
import org.ujmp.core.interfaces.HasColumnMajorDoubleArray1D;
import org.ujmp.core.interfaces.HasRowMajorDoubleArray2D;
import org.ujmp.core.matrix.DenseMatrix;
import org.ujmp.core.matrix.DenseMatrix2D;
import org.ujmp.core.matrix.SparseMatrix;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.UJMPSettings;
import org.ujmp.core.util.VerifyUtil;
import org.ujmp.core.util.concurrent.PForEquidistant;

public class DivideScalar {
	public static final DivideScalarCalculation<Matrix, Matrix> MATRIX = new DivideScalarMatrix();

	public static final DivideScalarCalculation<DenseMatrix, DenseMatrix> DENSEMATRIX = new DivideScalarDenseMatrix();

	public static final DivideScalarCalculation<DenseMatrix2D, DenseMatrix2D> DENSEMATRIX2D = new DivideScalarDenseMatrix2D();

	public static final DivideScalarCalculation<DenseDoubleMatrix2D, DenseDoubleMatrix2D> DENSEDOUBLEMATRIX2D = new DivideScalarDenseDoubleMatrix2D();

	public static final DivideScalarCalculation<SparseMatrix, SparseMatrix> SPARSEMATRIX = new DivideScalarSparseMatrix();
}

class DivideScalarMatrix implements DivideScalarCalculation<Matrix, Matrix> {

	public final void calc(final Matrix source, final BigDecimal divisor, final Matrix target) {
		if (source instanceof DenseMatrix && target instanceof DenseMatrix) {
			DivideScalar.DENSEMATRIX.calc((DenseMatrix) source, divisor, (DenseMatrix) target);
		} else if (source instanceof SparseMatrix && target instanceof SparseMatrix) {
			DivideScalar.SPARSEMATRIX.calc((SparseMatrix) source, divisor, (SparseMatrix) target);
		} else {
			VerifyUtil.assertSameSize(source, target);
			for (long[] c : source.allCoordinates()) {
				BigDecimal value = source.getAsBigDecimal(c);
				BigDecimal result = MathUtil.divide(value, divisor);
				target.setAsBigDecimal(result, c);
			}
			if (source != target) {
				Annotation a = source.getAnnotation();
				if (a != null) {
					target.setAnnotation(a.clone());
				}
			}
		}
	}

	public final void calc(final Matrix source, final double divisor, final Matrix target) {
		if (source instanceof DenseMatrix && target instanceof DenseMatrix) {
			DivideScalar.DENSEMATRIX.calc((DenseMatrix) source, divisor, (DenseMatrix) target);
		} else if (source instanceof SparseMatrix && target instanceof SparseMatrix) {
			DivideScalar.SPARSEMATRIX.calc((SparseMatrix) source, divisor, (SparseMatrix) target);
		} else {
			calc(source, new BigDecimal(divisor, MathUtil.getDefaultMathContext()), target);
		}
	}
};

class DivideScalarDenseMatrix implements DivideScalarCalculation<DenseMatrix, DenseMatrix> {

	public final void calc(final DenseMatrix source, final BigDecimal divisor,
			final DenseMatrix target) {
		if (source instanceof DenseMatrix2D && target instanceof DenseMatrix2D) {
			DivideScalar.DENSEMATRIX2D
					.calc((DenseMatrix2D) source, divisor, (DenseMatrix2D) target);
		} else {
			VerifyUtil.assertSameSize(source, target);
			for (long[] c : source.allCoordinates()) {
				BigDecimal value = source.getAsBigDecimal(c);
				BigDecimal result = MathUtil.divide(value, divisor);
				target.setAsBigDecimal(result, c);
			}
			if (source != target) {
				Annotation a = source.getAnnotation();
				if (a != null) {
					target.setAnnotation(a.clone());
				}
			}
		}
	}

	public final void calc(final DenseMatrix source, final double divisor, final DenseMatrix target) {
		if (source instanceof DenseMatrix2D && target instanceof DenseMatrix2D) {
			DivideScalar.DENSEMATRIX2D
					.calc((DenseMatrix2D) source, divisor, (DenseMatrix2D) target);
		} else {
			calc(source, new BigDecimal(divisor, MathUtil.getDefaultMathContext()), target);
		}
	}
};

class DivideScalarSparseMatrix implements DivideScalarCalculation<SparseMatrix, SparseMatrix> {

	public final void calc(final SparseMatrix source, final BigDecimal divisor,
			final SparseMatrix target) {
		VerifyUtil.assertSameSize(source, target);
		for (long[] c : source.availableCoordinates()) {
			BigDecimal value = source.getAsBigDecimal(c);
			BigDecimal result = MathUtil.divide(value, divisor);
			target.setAsBigDecimal(result, c);
		}
		if (source != target) {
			Annotation a = source.getAnnotation();
			if (a != null) {
				target.setAnnotation(a.clone());
			}
		}
	}

	public final void calc(SparseMatrix source, double divisor, SparseMatrix target) {
		calc(source, new BigDecimal(divisor, MathUtil.getDefaultMathContext()), target);
	}
};

class DivideScalarDenseMatrix2D implements DivideScalarCalculation<DenseMatrix2D, DenseMatrix2D> {

	public final void calc(final DenseMatrix2D source, final BigDecimal divisor,
			final DenseMatrix2D target) {
		if (source instanceof DenseDoubleMatrix2D && target instanceof DenseDoubleMatrix2D) {
			DivideScalar.DENSEDOUBLEMATRIX2D.calc((DenseDoubleMatrix2D) source, divisor,
					(DenseDoubleMatrix2D) target);
		} else {
			VerifyUtil.assertSameSize(source, target);
			for (int r = (int) source.getRowCount(); --r != -1;) {
				for (int c = (int) source.getColumnCount(); --c != -1;) {
					BigDecimal value = source.getAsBigDecimal(r, c);
					BigDecimal result = MathUtil.divide(value, divisor);
					target.setAsBigDecimal(result, r, c);
				}
			}
			if (source != target) {
				Annotation a = source.getAnnotation();
				if (a != null) {
					target.setAnnotation(a.clone());
				}
			}
		}
	}

	public final void calc(final DenseMatrix2D source, final double divisor,
			final DenseMatrix2D target) {
		if (source instanceof DenseDoubleMatrix2D && target instanceof DenseDoubleMatrix2D) {
			DivideScalar.DENSEDOUBLEMATRIX2D.calc((DenseDoubleMatrix2D) source, divisor,
					(DenseDoubleMatrix2D) target);
		} else {
			calc(source, new BigDecimal(divisor, MathUtil.getDefaultMathContext()), target);
		}
	}
};

class DivideScalarDenseDoubleMatrix2D implements
		DivideScalarCalculation<DenseDoubleMatrix2D, DenseDoubleMatrix2D> {

	public final void calc(DenseDoubleMatrix2D source, BigDecimal divisor,
			DenseDoubleMatrix2D target) {
		calc(source, divisor.doubleValue(), target);
	}

	public final void calc(final DenseDoubleMatrix2D source, final double divisor,
			final DenseDoubleMatrix2D target) {
		if (source instanceof HasColumnMajorDoubleArray1D
				&& target instanceof HasColumnMajorDoubleArray1D) {
			calc(((HasColumnMajorDoubleArray1D) source).getColumnMajorDoubleArray1D(), divisor,
					((HasColumnMajorDoubleArray1D) target).getColumnMajorDoubleArray1D());
		} else if (source instanceof HasRowMajorDoubleArray2D
				&& target instanceof HasRowMajorDoubleArray2D) {
			calc(((HasRowMajorDoubleArray2D) source).getRowMajorDoubleArray2D(), divisor,
					((HasRowMajorDoubleArray2D) target).getRowMajorDoubleArray2D());
		} else {
			VerifyUtil.assertSameSize(source, target);
			for (int r = (int) source.getRowCount(); --r != -1;) {
				for (int c = (int) source.getColumnCount(); --c != -1;) {
					target.setDouble(source.getDouble(r, c) / divisor, r, c);
				}
			}
		}
		if (source != target) {
			Annotation a = source.getAnnotation();
			if (a != null) {
				target.setAnnotation(a.clone());
			}
		}
	}

	private final void calc(final double[][] source, final double divisor, final double[][] target) {
		VerifyUtil.assertSameSize(source, target);
		if (UJMPSettings.getNumberOfThreads() > 1 && source.length >= 100
				&& source[0].length >= 100) {
			new PForEquidistant(0, source.length - 1) {
				public void step(int i) {
					double[] tsource = source[i];
					double[] ttarget = target[i];
					for (int c = source[0].length; --c != -1;) {
						ttarget[c] = tsource[c] / divisor;
					}
				}
			};
		} else {
			double[] tsource = null;
			double[] ttarget = null;
			for (int r = source.length; --r != -1;) {
				tsource = source[r];
				ttarget = target[r];
				for (int c = source[0].length; --c != -1;) {
					ttarget[c] = tsource[c] / divisor;
				}
			}
		}
	}

	private final void calc(final double[] source, final double divisor, final double[] target) {
		VerifyUtil.assertSameSize(source, target);
		final int length = source.length;
		for (int i = 0; i < length; i++) {
			target[i] = source[i] / divisor;
		}
	}
};
