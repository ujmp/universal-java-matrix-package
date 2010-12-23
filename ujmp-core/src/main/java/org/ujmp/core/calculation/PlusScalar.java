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

public class PlusScalar {
	public static final PlusScalarCalculation<Matrix, Matrix> MATRIX = new PlusScalarMatrix();

	public static final PlusScalarCalculation<DenseMatrix, DenseMatrix> DENSEMATRIX = new PlusScalarDenseMatrix();

	public static final PlusScalarCalculation<DenseMatrix2D, DenseMatrix2D> DENSEMATRIX2D = new PlusScalarDenseMatrix2D();

	public static final PlusScalarCalculation<DenseDoubleMatrix2D, DenseDoubleMatrix2D> DENSEDOUBLEMATRIX2D = new PlusScalarDenseDoubleMatrix2D();

	public static final PlusScalarCalculation<SparseMatrix, SparseMatrix> SPARSEMATRIX = new PlusScalarSparseMatrix();
}

class PlusScalarMatrix implements PlusScalarCalculation<Matrix, Matrix> {

	public final void calc(final Matrix source, final BigDecimal value, final Matrix target) {
		if (source instanceof DenseMatrix && target instanceof DenseMatrix) {
			PlusScalar.DENSEMATRIX.calc((DenseMatrix) source, value, (DenseMatrix) target);
		} else if (source instanceof SparseMatrix && target instanceof SparseMatrix) {
			PlusScalar.SPARSEMATRIX.calc((SparseMatrix) source, value, (SparseMatrix) target);
		} else {
			VerifyUtil.assertSameSize(source, target);
			for (long[] c : source.allCoordinates()) {
				BigDecimal svalue = source.getAsBigDecimal(c);
				BigDecimal result = MathUtil.plus(svalue, value);
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

	public final void calc(final Matrix source, final double value, final Matrix target) {
		if (source instanceof DenseMatrix && target instanceof DenseMatrix) {
			PlusScalar.DENSEMATRIX.calc((DenseMatrix) source, value, (DenseMatrix) target);
		} else if (source instanceof SparseMatrix && target instanceof SparseMatrix) {
			PlusScalar.SPARSEMATRIX.calc((SparseMatrix) source, value, (SparseMatrix) target);
		} else {
			calc(source, new BigDecimal(value, MathUtil.getDefaultMathContext()), target);
		}
	}
};

class PlusScalarDenseMatrix implements PlusScalarCalculation<DenseMatrix, DenseMatrix> {

	public final void calc(final DenseMatrix source, final BigDecimal value,
			final DenseMatrix target) {
		if (source instanceof DenseMatrix2D && target instanceof DenseMatrix2D) {
			PlusScalar.DENSEMATRIX2D.calc((DenseMatrix2D) source, value, (DenseMatrix2D) target);
		} else {
			VerifyUtil.assertSameSize(source, target);
			for (long[] c : source.allCoordinates()) {
				BigDecimal svalue = source.getAsBigDecimal(c);
				BigDecimal result = MathUtil.plus(svalue, value);
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

	public final void calc(final DenseMatrix source, final double value, final DenseMatrix target) {
		if (source instanceof DenseMatrix2D && target instanceof DenseMatrix2D) {
			PlusScalar.DENSEMATRIX2D.calc((DenseMatrix2D) source, value, (DenseMatrix2D) target);
		} else {
			calc(source, new BigDecimal(value, MathUtil.getDefaultMathContext()), target);
		}
	}
};

class PlusScalarSparseMatrix implements PlusScalarCalculation<SparseMatrix, SparseMatrix> {

	public final void calc(final SparseMatrix source, final BigDecimal value,
			final SparseMatrix target) {
		VerifyUtil.assertSameSize(source, target);
		for (long[] c : source.availableCoordinates()) {
			BigDecimal svalue = source.getAsBigDecimal(c);
			BigDecimal result = MathUtil.plus(svalue, value);
			target.setAsBigDecimal(result, c);
		}
		if (source != target) {
			Annotation a = source.getAnnotation();
			if (a != null) {
				target.setAnnotation(a.clone());
			}
		}
	}

	public final void calc(SparseMatrix source, double value, SparseMatrix target) {
		calc(source, new BigDecimal(value, MathUtil.getDefaultMathContext()), target);
	}
};

class PlusScalarDenseMatrix2D implements PlusScalarCalculation<DenseMatrix2D, DenseMatrix2D> {

	public final void calc(final DenseMatrix2D source, final BigDecimal value,
			final DenseMatrix2D target) {
		if (source instanceof DenseDoubleMatrix2D && target instanceof DenseDoubleMatrix2D) {
			PlusScalar.DENSEDOUBLEMATRIX2D.calc((DenseDoubleMatrix2D) source, value,
					(DenseDoubleMatrix2D) target);
		} else {
			VerifyUtil.assertSameSize(source, target);
			for (int r = (int) source.getRowCount(); --r != -1;) {
				for (int c = (int) source.getColumnCount(); --c != -1;) {
					BigDecimal svalue = source.getAsBigDecimal(r, c);
					BigDecimal result = MathUtil.plus(svalue, value);
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

	public final void calc(final DenseMatrix2D source, final double value,
			final DenseMatrix2D target) {
		if (source instanceof DenseDoubleMatrix2D && target instanceof DenseDoubleMatrix2D) {
			PlusScalar.DENSEDOUBLEMATRIX2D.calc((DenseDoubleMatrix2D) source, value,
					(DenseDoubleMatrix2D) target);
		} else {
			calc(source, new BigDecimal(value, MathUtil.getDefaultMathContext()), target);
		}
	}
};

class PlusScalarDenseDoubleMatrix2D implements
		PlusScalarCalculation<DenseDoubleMatrix2D, DenseDoubleMatrix2D> {

	public final void calc(final DenseDoubleMatrix2D source, final BigDecimal value,
			final DenseDoubleMatrix2D target) {
		calc(source, value.doubleValue(), target);
	}

	public final void calc(final DenseDoubleMatrix2D source, final double value,
			final DenseDoubleMatrix2D target) {
		if (source instanceof HasColumnMajorDoubleArray1D
				&& target instanceof HasColumnMajorDoubleArray1D) {
			calc(((HasColumnMajorDoubleArray1D) source).getColumnMajorDoubleArray1D(), value,
					((HasColumnMajorDoubleArray1D) target).getColumnMajorDoubleArray1D());
		} else if (source instanceof HasRowMajorDoubleArray2D
				&& target instanceof HasRowMajorDoubleArray2D) {
			calc(((HasRowMajorDoubleArray2D) source).getRowMajorDoubleArray2D(), value,
					((HasRowMajorDoubleArray2D) target).getRowMajorDoubleArray2D());
		} else {
			VerifyUtil.assertSameSize(source, target);
			for (int r = (int) source.getRowCount(); --r != -1;) {
				for (int c = (int) source.getColumnCount(); --c != -1;) {
					target.setDouble(source.getDouble(r, c) + value, r, c);
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

	private final void calc(final double[][] source, final double value, final double[][] target) {
		VerifyUtil.assertSameSize(source, target);
		if (UJMPSettings.getNumberOfThreads() > 1 && source.length >= 100
				&& source[0].length >= 100) {
			new PForEquidistant(0, source.length - 1) {
				public void step(int i) {
					double[] tsource = source[i];
					double[] ttarget = target[i];
					for (int c = source[0].length; --c != -1;) {
						ttarget[c] = tsource[c] + value;
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
					ttarget[c] = tsource[c] + value;
				}
			}
		}
	}

	private final void calc(final double[] source, final double value, final double[] target) {
		VerifyUtil.assertSameSize(source, target);
		final int length = source.length;
		for (int i = 0; i < length; i++) {
			target[i] = source[i] + value;
		}
	}
};
