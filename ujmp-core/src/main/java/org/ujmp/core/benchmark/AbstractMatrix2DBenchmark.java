/*
 * Copyright (C) 2008-2016 by Holger Arndt
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
import org.ujmp.core.util.StringUtil;
import org.ujmp.core.util.UJMPSettings;

public abstract class AbstractMatrix2DBenchmark implements MatrixBenchmark {

	private static final BenchmarkConfig config = new BenchmarkConfig();

	public abstract DoubleMatrix2D createMatrix(long... size);

	public abstract DoubleMatrix2D createMatrix(Matrix source);

	private static long benchmarkSeed = 3345454363676l;

	public AbstractMatrix2DBenchmark() {
		benchmarkSeed = System.currentTimeMillis();
	}

	public final String getMatrixLabel() {
		return createMatrix(1, 1).getClass().getSimpleName();
	}

	public final Class<? extends DoubleMatrix2D> getMatrixClass() {
		return createMatrix(1, 1).getClass();
	}

	public final BenchmarkConfig getConfig() {
		return config;
	}

	public void setName(String name) {
		config.setName(name);
	}

	public final void run() {
		if (Runtime.getRuntime().maxMemory() < 900 * 1024 * 1024) {
			throw new RuntimeException("You must start Java with more memory: -Xmx1024M");
		}

		// adjust maximal size to fit into memory:
		// max memory for A*B=C (three matrices, 8 Byte for double)
		int newMaxSize = (int) Math.sqrt(Runtime.getRuntime().maxMemory() / 24);
		if (config.getMaxSize() > newMaxSize) {
			config.setMaxSize(newMaxSize);
		}

		System.out.println("===============================================================");
		System.out.println(" UJMP matrix benchmark");
		System.out.println("===============================================================");
		System.out.println(" Settings:");
		System.out.println("   numberOfThreads: " + config.getNumberOfThreads());
		System.out.println("   gcMemory: " + config.isGCMemory());
		System.out.println("   purgeMemory: " + config.isPurgeMemory());
		System.out.println("   burnInRuns: " + config.getBurnInRuns());
		System.out.println("   runs: " + config.getRuns());
		System.out.println("   maxTime: " + config.getMaxTime());
		System.out.println("   maxStd: " + config.getMaxStd());
		System.out.println("   minSize: "
				+ Coordinates.toString("x", config.getSquareSizes().get(0)));
		System.out.println("   maxSize: "
				+ Coordinates.toString("x",
						config.getSquareSizes().get(config.getSquareSizes().size() - 1)));

		System.out.println();

		try {
			System.out.println("===============================================================");
			System.out.println(createMatrix(1, 1).getClass().getSimpleName());
			System.out.println("===============================================================");

			long t0 = System.currentTimeMillis();

			UJMPSettings.getInstance().setUseCommonsMath(config.isUseCommonsMath());
			UJMPSettings.getInstance().setUseMTJ(config.isUseMTJ());
			UJMPSettings.getInstance().setUseEJML(config.isUseEJML());
			UJMPSettings.getInstance().setUseJBlas(config.isUseJBlas());
			UJMPSettings.getInstance().setUseOjalgo(config.isUseOjalgo());
			UJMPSettings.getInstance().setUseParallelColt(config.isUseParallelColt());
			UJMPSettings.getInstance().setUseBlockMatrixMultiply(config.isUseBlockMatrixMultiply());

			UJMPSettings.getInstance().setDefaultBlockSize(config.getDefaultBlockSize());

			if (config.isRunTimesScalar()) {
				new TimesScalarBenchmarkTask(benchmarkSeed, getMatrixClass(), getConfig()).run();
			}

			if (config.isRunPlusMatrix()) {
				new PlusMatrixBenchmarkTask(benchmarkSeed, getMatrixClass(), getConfig()).run();
			}

			if (config.isRunTranspose()) {
				new TransposeBenchmarkTask(benchmarkSeed, getMatrixClass(), getConfig()).run();
			}

			if (config.isRunMtimes()) {
				new MtimesBenchmarkTask(benchmarkSeed, getMatrixClass(), getConfig()).run();
			}

			if (config.isRunInv()) {
				new InvBenchmarkTask(benchmarkSeed, getMatrixClass(), getConfig()).run();
			}

			if (config.isRunInvSPD()) {
				new InvSPDBenchmarkTask(benchmarkSeed, getMatrixClass(), getConfig()).run();
			}

			if (config.isRunSolveSquare()) {
				new SolveSquareBenchmarkTask(benchmarkSeed, getMatrixClass(), getConfig()).run();
			}

			if (config.isRunSolveTall()) {
				new SolveTallBenchmarkTask(benchmarkSeed, getMatrixClass(), getConfig()).run();
			}

			if (config.isRunSVD()) {
				new SVDBenchmarkTask(benchmarkSeed, getMatrixClass(), getConfig()).run();
			}

			if (config.isRunEig()) {
				new EigBenchmarkTask(benchmarkSeed, getMatrixClass(), getConfig()).run();
			}

			if (config.isRunChol()) {
				new CholBenchmarkTask(benchmarkSeed, getMatrixClass(), getConfig()).run();
			}

			if (config.isRunLU()) {
				new LUBenchmarkTask(benchmarkSeed, getMatrixClass(), getConfig()).run();
			}

			if (config.isRunQR()) {
				new QRBenchmarkTask(benchmarkSeed, getMatrixClass(), getConfig()).run();
			}

			long t1 = System.currentTimeMillis();

			System.out.println();
			System.out.println("Benchmark runtime: " + StringUtil.duration(t1 - t0));
			System.out.println();

		} catch (UnsupportedClassVersionError e) {
			System.out.println("this library is not compatible with the current Java version");
			System.out.println("it cannot be included in the benchmark");
			System.out.println();
		} catch (Exception e) {
			System.out.println("there was some error with this library");
			System.out.println("it cannot be included in the benchmark");
			e.printStackTrace();
		}
	}

}
