/*
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
import org.ujmp.core.util.GCUtil;
import org.ujmp.core.util.MathUtil;

public class TimesScalarBenchmarkTask extends AbstractBenchmarkTask {

	public TimesScalarBenchmarkTask(long benchmarkSeed,
			Class<? extends DoubleMatrix2D> matrixClass, BenchmarkConfig config) {
		super(benchmarkSeed, matrixClass, config.getTimesSizes(), config);
	}

	@Override
	public double task(Class<? extends Matrix> matrixClass, long benchmarkSeed, int run, long[] size) {
		long t0, t1;
		DoubleMatrix2D m = null;
		Matrix r = null;
		try {
			m = BenchmarkUtil.createMatrix(matrixClass, size);
			if (!m.getClass().getName().startsWith("org.ujmp.core")
					&& m.getClass().getDeclaredMethod("times", Double.TYPE) == null) {
				System.out.print("-");
				System.out.flush();
				return BenchmarkConfig.NOTAVAILABLE;
			}
			BenchmarkUtil.rand(benchmarkSeed, run, 0, m);
			GCUtil.purgeMemory();
			t0 = System.nanoTime();
			r = m.times(MathUtil.nextDouble());
			t1 = System.nanoTime();
			if (r == null) {
				System.out.print("e");
				System.out.flush();
				return BenchmarkConfig.ERROR;
			}
			return (t1 - t0) / 1000000.0;
		} catch (Throwable e) {
			System.out.print("e");
			System.out.flush();
			return BenchmarkConfig.ERROR;
		}
	}

	@Override
	public String getTaskName() {
		return "timesScalar";
	}

}
