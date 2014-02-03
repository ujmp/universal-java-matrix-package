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
import org.ujmp.core.doublematrix.DoubleMatrix2D;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.SerializationUtil;

public class TimesScalarBenchmarkTask extends AbstractBenchmarkTask {

	public TimesScalarBenchmarkTask(long benchmarkSeed,
			Class<? extends DoubleMatrix2D> matrixClass, BenchmarkConfig config) {
		super(benchmarkSeed, matrixClass, config.getTimesSizes(), config);
	}

	@Override
	public BenchmarkResult task(Class<? extends Matrix> matrixClass, long benchmarkSeed, int run,
			long[] size) {
		final long t0, t1, e0, e1;
		final DoubleMatrix2D m;
		Matrix r = null;
		try {
			m = BenchmarkUtil.createMatrix(matrixClass, size);
			if (!m.getClass().getName().startsWith("org.ujmp.core")
					&& m.getClass().getDeclaredMethod("times", Double.TYPE) == null) {
				System.out.print("-");
				System.out.flush();
				return BenchmarkResult.NOTAVAILABLE;
			}
			BenchmarkUtil.rand(benchmarkSeed, run, 0, m);
			BenchmarkUtil.purgeMemory(getConfig());
			e0 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
			t0 = System.nanoTime();
			r = m.times(MathUtil.nextDouble());
			t1 = System.nanoTime();
			e1 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
			if (r == null) {
				System.out.print("e");
				System.out.flush();
				return BenchmarkResult.ERROR;
			}
			long mem = e1 - e0 - SerializationUtil.sizeOf(r);
			mem = mem > 0 ? mem : 0;
			return new BenchmarkResult((t1 - t0) / 1000000.0, mem);
		} catch (Throwable e) {
			System.out.print("e");
			System.out.flush();
			return BenchmarkResult.ERROR;
		}
	}

	@Override
	public String getTaskName() {
		return "timesScalar";
	}

}
