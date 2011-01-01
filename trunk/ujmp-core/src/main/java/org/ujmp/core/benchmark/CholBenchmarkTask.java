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

package org.ujmp.core.benchmark;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.DoubleMatrix2D;
import org.ujmp.core.util.SerializationUtil;

public class CholBenchmarkTask extends AbstractBenchmarkTask {

	public CholBenchmarkTask(long benchmarkSeed, Class<? extends DoubleMatrix2D> matrixClass,
			BenchmarkConfig config) {
		super(benchmarkSeed, matrixClass, config.getCholSizes(), config);
	}

	@Override
	public BenchmarkResult task(Class<? extends Matrix> matrixClass, long benchmarkSeed, int run,
			long[] size) {
		final long t0, t1, m0, m1;
		final DoubleMatrix2D m;
		Matrix r = null;
		try {
			m = BenchmarkUtil.createMatrix(matrixClass, size);
			if (!m.getClass().getName().startsWith("org.ujmp.core")
					&& m.getClass().getDeclaredMethod("chol") == null) {
				System.out.print("-");
				System.out.flush();
				return BenchmarkResult.NOTAVAILABLE;
			}
			BenchmarkUtil.randPositiveDefinite(benchmarkSeed, run, 0, m);
			BenchmarkUtil.purgeMemory(getConfig());
			m0 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
			t0 = System.nanoTime();
			r = m.chol();
			t1 = System.nanoTime();
			m1 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
			if (r == null) {
				System.out.print("e");
				System.out.flush();
				return BenchmarkResult.ERROR;
			}
			Matrix result = r.mtimes(r.transpose());
			double diff = BenchmarkUtil.difference(result, m);
			result = null;
			long mem = m1 - m0 - SerializationUtil.sizeOf(r);
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
		return "chol";
	}

}
