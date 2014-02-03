/*
 * Copyright (C) 2008-2014 by Holger Arndt
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

package org.ujmp.core.benchmark;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.DoubleMatrix2D;
import org.ujmp.core.doublematrix.impl.DefaultDenseDoubleMatrix2D;
import org.ujmp.core.util.SerializationUtil;

public class SolveSquareBenchmarkTask extends AbstractBenchmarkTask {

	public SolveSquareBenchmarkTask(long benchmarkSeed,
			Class<? extends DoubleMatrix2D> matrixClass, BenchmarkConfig config) {
		super(benchmarkSeed, matrixClass, config.getSolveSquareSizes(), config);
	}

	@Override
	public BenchmarkResult task(Class<? extends Matrix> matrixClass, long benchmarkSeed, int run,
			long[] size) {
		final long t0, t1, m0, m1;
		final DoubleMatrix2D a, x;
		Matrix b1 = null, b2 = null, result = null;
		try {
			a = BenchmarkUtil.createMatrix(matrixClass, size);
			if (!a.getClass().getName().startsWith("org.ujmp.core.")
					&& a.getClass().getDeclaredMethod("solve", Matrix.class) == null) {
				System.out.print("-");
				System.out.flush();
				return BenchmarkResult.NOTAVAILABLE;
			}
			x = DenseDoubleMatrix2D.Factory.zeros(size[1], size[0]);
			BenchmarkUtil.rand(benchmarkSeed, run, 0, a);
			BenchmarkUtil.rand(benchmarkSeed, run, 1, x);
			b1 = new DefaultDenseDoubleMatrix2D(a).mtimes(new DefaultDenseDoubleMatrix2D(x));
			b2 = BenchmarkUtil.createMatrix(matrixClass, b1);
			BenchmarkUtil.purgeMemory(getConfig());
			m0 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
			t0 = System.nanoTime();
			result = a.solve(b2);
			t1 = System.nanoTime();
			m1 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
			if (result == null) {
				System.out.print("e");
				System.out.flush();
				return BenchmarkResult.ERROR;
			}
			double diff = BenchmarkUtil.difference(result, x);
			result = null;
			long mem = m1 - m0 - SerializationUtil.sizeOf(result);
			mem = mem > 0 ? mem : 0;
			return new BenchmarkResult((t1 - t0) / 1000000.0, diff, mem);
		} catch (Throwable e) {
			System.out.print("e");
			System.out.flush();
			return BenchmarkResult.ERROR;
		}
	}

	@Override
	public String getTaskName() {
		return "solveSquare";
	}

}
