/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
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

package org.ujmp.core.calculation.general.statistical;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.ujmp.core.DoubleMatrix2D;
import org.ujmp.core.IntMatrix2D;
import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.Matrix.EntryType;
import org.ujmp.core.calculation.AbstractDoubleCalculation;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.matrices.basic.DefaultDenseIntMatrix2D;
import org.ujmp.core.util.MathUtil;

public class MutualInformation extends AbstractDoubleCalculation {
	private static final long serialVersionUID = -4891250637894943873L;

	public MutualInformation(Matrix matrix) {
		super(matrix);
	}

	@Override
	public double getDouble(long... coordinates) throws MatrixException {
		return calculate(coordinates[ROW], coordinates[COLUMN], getSource());
	}

	@Override
	public long[] getSize() {
		return new long[] { getSource().getColumnCount(), getSource().getColumnCount() };
	}

	public static final double calculate(long var1, long var2, Matrix matrix) {
		double count = matrix.getRowCount();

		Map<Double, Double> count1 = new HashMap<Double, Double>();
		Map<Double, Double> count2 = new HashMap<Double, Double>();
		Map<String, Double> count12 = new HashMap<String, Double>();

		// count absolute frequency
		for (int r = 0; r < matrix.getRowCount(); r++) {
			double value1 = matrix.getAsDouble(r, var1);
			double value2 = matrix.getAsDouble(r, var2);

			Double c1 = count1.get(value1);
			c1 = (c1 == null) ? 0.0 : c1;
			count1.put(value1, c1 + 1.0);

			Double c2 = count2.get(value2);
			c2 = (c2 == null) ? 0.0 : c2;
			count2.put(value2, c2 + 1);

			Double c12 = count12.get(value1 + "," + value2);
			c12 = (c12 == null) ? 0.0 : c12;
			count12.put(value1 + "," + value2, c12 + 1);
		}

		// calculate relative frequency
		for (Double value1 : count1.keySet()) {
			Double c1 = count1.get(value1);
			count1.put(value1, c1 / count);
		}

		for (Double value2 : count2.keySet()) {
			Double c2 = count2.get(value2);
			count2.put(value2, c2 / count);
		}

		for (String value12 : count12.keySet()) {
			Double c12 = count12.get(value12);
			count12.put(value12, c12 / count);
		}

		// calculate mutual information
		double mutualInformation = 0.0;
		for (Double value1 : count1.keySet()) {
			double p1 = count1.get(value1);
			for (Double value2 : count2.keySet()) {
				double p2 = count2.get(value2);
				Double p12 = count12.get(value1 + "," + value2);
				if (p12 != null) {
					mutualInformation += p12 * MathUtil.log2(p12 / (p1 * p2));
				}
			}
		}

		// System.out.println(count1);
		// System.out.println(count2);
		// System.out.println(count12);
		// System.out.println(mutualInformation);

		return mutualInformation;
	}

	public static DoubleMatrix2D calcNew(Matrix matrix) {
		return calcNew(matrix.convert(EntryType.INTEGER));
	}

	public static DoubleMatrix2D calcNew(IntMatrix2D matrix) {
		DefaultDenseIntMatrix2D matrix2 = (DefaultDenseIntMatrix2D) matrix;
		long count = matrix.getColumnCount();
		int samples = (int) matrix.getRowCount();
		DoubleMatrix2D result = (DoubleMatrix2D) MatrixFactory.zeros(Matrix.EntryType.DOUBLE, count, count);
		int[] d_dc = new int[(int) count];
		// int[][] matrixInt = matrix.toIntArray();
		Arrays.fill(d_dc, (int) matrix.getMaxValue() + 1);
		int aVal, bVal;
		for (int a = 0; a < count; a++) {
			for (int b = 0; b <= a; b++) {
				double mutual = 0;

				double[][] Nab = new double[d_dc[a]][d_dc[b]];
				double[] Na = new double[d_dc[a]];
				double[] Nb = new double[d_dc[b]];
				for (int k = (int) matrix.getRowCount() - 1; k >= 0; k--) {
					aVal = matrix2.getInt(k, a);// dataset[aIndex][k];
					bVal = matrix2.getInt(k, b);// dataset[bIndex][k];
					// aVal = matrixInt[k][a];
					// bVal = matrixInt[k][b];
					Na[aVal]++;
					Nb[bVal]++;
					Nab[aVal][bVal]++;
				}
				double[] NaLog = new double[d_dc[a]];
				double[] NbLog = new double[d_dc[b]];
				double log2 = Math.log(2);
				for (int j = d_dc[b] - 1; j >= 0; j--) {
					Nb[j] /= samples;
					if (Nb[j] != 0)
						NbLog[j] = Math.log(Nb[j]);
				}
				for (int i = d_dc[a] - 1; i >= 0; i--) {
					Na[i] /= samples;
					if (Na[i] != 0)
						NaLog[i] = Math.log(Na[i]);
					for (int j = d_dc[b] - 1; j >= 0; j--) {
						Nab[i][j] /= samples;

						if (Na[i] != 0 && Nb[j] != 0 && Nab[i][j] != 0) {
							mutual += Nab[i][j] * (Math.log(Nab[i][j]) - NaLog[i] - NbLog[j]) / log2;
						}
					}
				}
				mutual = (mutual < 0) ? 0 : mutual;
				result.setDouble(mutual, a, b);
				result.setDouble(mutual, b, a);
			}

		}

		return result;
	}
}