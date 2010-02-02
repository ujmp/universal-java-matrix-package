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

public class PlusMatrixTask extends AbstractBenchmarkTask {

	public PlusMatrixTask(long benchmarkSeed, Class<? extends Matrix> matrixClass, String sizes,
			int blockCount, int burnInRuns, int runs) {
		super(benchmarkSeed, matrixClass, sizes, blockCount, burnInRuns, runs);
	}

	@Override
	public double task(Class<? extends Matrix> matrixClass, long benchmarkSeed, int run,
			long[]... sizes) {
		DoubleMatrix2D m0 = null, m1 = null;
		Matrix r = null;
		try {
			m0 = BenchmarkUtil.createMatrix(matrixClass, sizes[0]);
			m1 = BenchmarkUtil.createMatrix(matrixClass, sizes[1]);
			if (!m0.getClass().getName().startsWith("org.ujmp.core")
					&& m0.getClass().getDeclaredMethod("plus", Matrix.class) == null) {
				System.out.print("-");
				System.out.flush();
				return NOTAVAILABLE;
			}
			BenchmarkUtil.rand(benchmarkSeed, run, m0);
			BenchmarkUtil.rand(benchmarkSeed, run * 999, m1);
			GCUtil.purgeMemory();
			long t0 = System.nanoTime();
			r = m0.plus(m1);
			long t1 = System.nanoTime();
			if (r == null) {
				System.out.print("e");
				System.out.flush();
				return ERRORTIME;
			}
			return (t1 - t0) / 1000000.0;
		} catch (Throwable e) {
			System.out.print("e");
			System.out.flush();
			return ERRORTIME;
		}
	}

	@Override
	public String getTaskName() {
		return "plusMatrixNew";
	}

}
