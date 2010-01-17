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
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;
import org.ujmp.core.interfaces.HasDoubleArray;
import org.ujmp.core.interfaces.HasDoubleArray2D;
import org.ujmp.core.matrix.DenseMatrix;
import org.ujmp.core.matrix.DenseMatrix2D;
import org.ujmp.core.matrix.SparseMatrix;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.UJMPSettings;
import org.ujmp.core.util.concurrent.PForEquidistant;

public interface MinusScalar<T> {

	public static MinusScalar<Matrix> INSTANCE = new MinusScalar<Matrix>() {

		public void calc(final Matrix source, final BigDecimal value, final Matrix target) {
			if (source instanceof DenseMatrix && target instanceof DenseMatrix) {
				MinusScalar.DENSEMATRIX.calc((DenseMatrix) source, value, (DenseMatrix) target);
			} else if (source instanceof SparseMatrix && target instanceof SparseMatrix) {
				MinusScalar.SPARSEMATRIX.calc((SparseMatrix) source, value, (SparseMatrix) target);
			} else {
				for (long[] c : source.allCoordinates()) {
					BigDecimal svalue = source.getAsBigDecimal(c);
					BigDecimal result = MathUtil.minus(svalue, value);
					target.setAsBigDecimal(result, c);
				}
			}
		}

		public void calc(final Matrix source, final double value, final Matrix target) {
			if (source instanceof DenseMatrix && target instanceof DenseMatrix) {
				MinusScalar.DENSEMATRIX.calc((DenseMatrix) source, value, (DenseMatrix) target);
			} else if (source instanceof SparseMatrix && target instanceof SparseMatrix) {
				MinusScalar.SPARSEMATRIX.calc((SparseMatrix) source, value, (SparseMatrix) target);
			} else {
				calc(source, new BigDecimal(value, MathUtil.getDefaultMathContext()), target);
			}
		}
	};

	public static MinusScalar<DenseMatrix> DENSEMATRIX = new MinusScalar<DenseMatrix>() {

		public void calc(final DenseMatrix source, final BigDecimal value, final DenseMatrix target) {
			if (source instanceof DenseMatrix2D && target instanceof DenseMatrix2D) {
				MinusScalar.DENSEMATRIX2D.calc((DenseMatrix2D) source, value,
						(DenseMatrix2D) target);
			} else {
				for (long[] c : source.allCoordinates()) {
					BigDecimal svalue = source.getAsBigDecimal(c);
					BigDecimal result = MathUtil.minus(svalue, value);
					target.setAsBigDecimal(result, c);
				}
			}
		}

		public void calc(final DenseMatrix source, final double value, final DenseMatrix target) {
			if (source instanceof DenseMatrix2D && target instanceof DenseMatrix2D) {
				MinusScalar.DENSEMATRIX2D.calc((DenseMatrix2D) source, value,
						(DenseMatrix2D) target);
			} else {
				calc(source, new BigDecimal(value, MathUtil.getDefaultMathContext()), target);
			}
		}
	};

	public static MinusScalar<SparseMatrix> SPARSEMATRIX = new MinusScalar<SparseMatrix>() {

		public void calc(final SparseMatrix source, final BigDecimal value,
				final SparseMatrix target) {
			for (long[] c : source.availableCoordinates()) {
				BigDecimal svalue = source.getAsBigDecimal(c);
				BigDecimal result = MathUtil.minus(svalue, value);
				target.setAsBigDecimal(result, c);
			}
		}

		public void calc(SparseMatrix source, double value, SparseMatrix target) {
			calc(source, new BigDecimal(value, MathUtil.getDefaultMathContext()), target);
		}
	};

	public static MinusScalar<DenseMatrix2D> DENSEMATRIX2D = new MinusScalar<DenseMatrix2D>() {

		public void calc(final DenseMatrix2D source, final BigDecimal value,
				final DenseMatrix2D target) {
			if (source instanceof DenseDoubleMatrix2D && target instanceof DenseDoubleMatrix2D) {
				MinusScalar.DENSEDOUBLEMATRIX2D.calc((DenseDoubleMatrix2D) source, value,
						(DenseDoubleMatrix2D) target);
			} else {
				for (int r = (int) source.getRowCount(); --r != -1;) {
					for (int c = (int) source.getColumnCount(); --c != -1;) {
						BigDecimal svalue = source.getAsBigDecimal(r, c);
						BigDecimal result = MathUtil.minus(svalue, value);
						target.setAsBigDecimal(result, r, c);
					}
				}
			}
		}

		public void calc(final DenseMatrix2D source, final double value, final DenseMatrix2D target) {
			if (source instanceof DenseDoubleMatrix2D && target instanceof DenseDoubleMatrix2D) {
				MinusScalar.DENSEDOUBLEMATRIX2D.calc((DenseDoubleMatrix2D) source, value,
						(DenseDoubleMatrix2D) target);
			} else {
				calc(source, new BigDecimal(value, MathUtil.getDefaultMathContext()), target);
			}
		}
	};

	public static MinusScalar<DenseDoubleMatrix2D> DENSEDOUBLEMATRIX2D = new MinusScalar<DenseDoubleMatrix2D>() {

		public void calc(DenseDoubleMatrix2D source, BigDecimal value, DenseDoubleMatrix2D target) {
			calc(source, value.doubleValue(), target);
		}

		public void calc(final DenseDoubleMatrix2D source, final double value,
				final DenseDoubleMatrix2D target) {
			if (source instanceof HasDoubleArray2D && target instanceof HasDoubleArray2D) {
				calc(((HasDoubleArray2D) source).getDoubleArray2D(), value,
						((HasDoubleArray2D) target).getDoubleArray2D());
			} else if (source instanceof HasDoubleArray && target instanceof HasDoubleArray) {
				calc(((HasDoubleArray) source).getDoubleArray(), value, ((HasDoubleArray) target)
						.getDoubleArray());
			} else {
				for (int r = (int) source.getRowCount(); --r != -1;) {
					for (int c = (int) source.getColumnCount(); --c != -1;) {
						target.setDouble(source.getDouble(r, c) - value, r, c);
					}
				}
			}
		}

		private void calc(final double[][] source, final double value, final double[][] target) {
			if (UJMPSettings.getNumberOfThreads() > 1 && source.length >= 100
					&& source[0].length >= 100) {
				new PForEquidistant(0, source.length - 1) {
					public void step(int i) {
						double[] tsource = source[i];
						double[] ttarget = target[i];
						for (int c = source[0].length; --c != -1;) {
							ttarget[c] = tsource[c] - value;
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
						ttarget[c] = tsource[c] - value;
					}
				}
			}
		}

		private void calc(final double[] source, final double value, final double[] target) {
			final int length = source.length;
			for (int i = 0; i < length; i++) {
				target[i] = source[i] - value;
			}
		}
	};

	public void calc(T source, BigDecimal value, T target);

	public void calc(T source, double value, T target);

}
