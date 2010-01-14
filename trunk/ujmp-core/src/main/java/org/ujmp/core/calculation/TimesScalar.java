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
import java.math.MathContext;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;
import org.ujmp.core.interfaces.HasDoubleArray;
import org.ujmp.core.interfaces.HasDoubleArray2D;
import org.ujmp.core.matrix.DenseMatrix;
import org.ujmp.core.matrix.DenseMatrix2D;
import org.ujmp.core.matrix.SparseMatrix;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.concurrent.PFor;

public interface TimesScalar<T> {

	public static TimesScalar<Matrix> MATRIX = new TimesScalar<Matrix>() {

		public void calc(Matrix source, BigDecimal factor, Matrix target) {
			if (source instanceof DenseMatrix && target instanceof DenseMatrix) {
				TimesScalar.DENSEMATRIX.calc((DenseMatrix) source, factor, (DenseMatrix) target);
			} else if (source instanceof SparseMatrix && target instanceof SparseMatrix) {
				TimesScalar.SPARSEMATRIX.calc((SparseMatrix) source, factor, (SparseMatrix) target);
			} else {
				MathContext mathContext = MathUtil.getDefaultMathContext();
				for (long[] c : source.allCoordinates()) {
					BigDecimal value = source.getAsBigDecimal(c);
					BigDecimal result = value.multiply(factor, mathContext);
					target.setAsBigDecimal(result, c);
				}
			}
		}

		public void calc(Matrix source, double factor, Matrix target) {
			if (source instanceof DenseMatrix && target instanceof DenseMatrix) {
				TimesScalar.DENSEMATRIX.calc((DenseMatrix) source, factor, (DenseMatrix) target);
			} else if (source instanceof SparseMatrix && target instanceof SparseMatrix) {
				TimesScalar.SPARSEMATRIX.calc((SparseMatrix) source, factor, (SparseMatrix) target);
			} else {
				calc(source, new BigDecimal(factor, MathUtil.getDefaultMathContext()), target);
			}
		}
	};

	public static TimesScalar<DenseMatrix> DENSEMATRIX = new TimesScalar<DenseMatrix>() {

		public void calc(DenseMatrix source, BigDecimal factor, DenseMatrix target) {
			if (source instanceof DenseMatrix2D && target instanceof DenseMatrix2D) {
				TimesScalar.DENSEMATRIX2D.calc((DenseMatrix2D) source, factor,
						(DenseMatrix2D) target);
			} else {
				MathContext mathContext = MathUtil.getDefaultMathContext();
				for (long[] c : source.allCoordinates()) {
					BigDecimal value = source.getAsBigDecimal(c);
					BigDecimal result = value.multiply(factor, mathContext);
					target.setAsBigDecimal(result, c);
				}
			}
		}

		public void calc(DenseMatrix source, double factor, DenseMatrix target) {
			if (source instanceof DenseMatrix2D && target instanceof DenseMatrix2D) {
				TimesScalar.DENSEMATRIX2D.calc((DenseMatrix2D) source, factor,
						(DenseMatrix2D) target);
			} else {
				calc(source, new BigDecimal(factor, MathUtil.getDefaultMathContext()), target);
			}
		}
	};

	public static TimesScalar<SparseMatrix> SPARSEMATRIX = new TimesScalar<SparseMatrix>() {

		public void calc(SparseMatrix source, BigDecimal factor, SparseMatrix target) {
			MathContext mathContext = MathUtil.getDefaultMathContext();
			for (long[] c : source.availableCoordinates()) {
				BigDecimal value = source.getAsBigDecimal(c);
				BigDecimal result = value.multiply(factor, mathContext);
				target.setAsBigDecimal(result, c);
			}
		}

		public void calc(SparseMatrix source, double factor, SparseMatrix target) {
			calc(source, new BigDecimal(factor, MathUtil.getDefaultMathContext()), target);
		}
	};

	public static TimesScalar<DenseMatrix2D> DENSEMATRIX2D = new TimesScalar<DenseMatrix2D>() {

		public void calc(final DenseMatrix2D source, final BigDecimal factor,
				final DenseMatrix2D target) {
			if (source instanceof DenseDoubleMatrix2D && target instanceof DenseDoubleMatrix2D) {
				TimesScalar.DENSEDOUBLEMATRIX2D.calc((DenseDoubleMatrix2D) source, factor,
						(DenseDoubleMatrix2D) target);
			} else {
				MathContext mathContext = MathUtil.getDefaultMathContext();
				for (int r = (int) source.getRowCount(); --r != -1;) {
					for (int c = (int) source.getColumnCount(); --c != -1;) {
						BigDecimal value = source.getAsBigDecimal(r, c);
						BigDecimal result = value.multiply(factor, mathContext);
						target.setAsBigDecimal(result, r, c);
					}
				}
			}
		}

		public void calc(DenseMatrix2D source, double factor, DenseMatrix2D target) {
			if (source instanceof DenseDoubleMatrix2D && target instanceof DenseDoubleMatrix2D) {
				TimesScalar.DENSEDOUBLEMATRIX2D.calc((DenseDoubleMatrix2D) source, factor,
						(DenseDoubleMatrix2D) target);
			} else {
				calc(source, new BigDecimal(factor, MathUtil.getDefaultMathContext()), target);
			}
		}
	};

	public static TimesScalar<DenseDoubleMatrix2D> DENSEDOUBLEMATRIX2D = new TimesScalar<DenseDoubleMatrix2D>() {

		public void calc(DenseDoubleMatrix2D source, BigDecimal factor, DenseDoubleMatrix2D target) {
			calc(source, factor.doubleValue(), target);
		}

		public void calc(DenseDoubleMatrix2D source, double factor, DenseDoubleMatrix2D target) {
			if (source instanceof HasDoubleArray2D && target instanceof HasDoubleArray2D) {
				calc(((HasDoubleArray2D) source).getDoubleArray2D(), factor,
						((HasDoubleArray2D) target).getDoubleArray2D());
			} else if (source instanceof HasDoubleArray && target instanceof HasDoubleArray) {
				calc(((HasDoubleArray) source).getDoubleArray(), factor, ((HasDoubleArray) target)
						.getDoubleArray());
			} else {
				for (int r = (int) source.getRowCount(); --r != -1;) {
					for (int c = (int) source.getColumnCount(); --c != -1;) {
						target.setDouble(factor * source.getDouble(r, c), r, c);
					}
				}
			}
		}

		private void calc(final double[][] source, final double factor, final double[][] target) {
			if (source.length >= 100 && source[0].length >= 100) {
				new PFor(0, source.length - 1) {
					public void step(int i) {
						double[] tsource = source[i];
						double[] ttarget = target[i];
						for (int c = source[0].length; --c != -1;) {
							ttarget[c] = tsource[c] * factor;
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
						ttarget[c] = tsource[c] * factor;
					}
				}
			}
		}

		private void calc(double[] source, double factor, double[] target) {
			for (int i = source.length; --i != -1;) {
				target[i] = factor * source[i];
			}
		}
	};

	public void calc(T source, BigDecimal factor, T target);

	public void calc(T source, double factor, T target);

}
