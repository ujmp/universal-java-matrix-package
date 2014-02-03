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

import java.io.File;
import java.util.List;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.Matrix2D;
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.util.MathUtil;

public abstract class AbstractBenchmarkTask {

	private BenchmarkConfig config = null;

	private Class<? extends Matrix> matrixClass = null;

	private List<long[]> sizes = null;

	private long benchmarkSeed = 0;

	public AbstractBenchmarkTask(long benchmarkSeed, Class<? extends Matrix> matrixClass,
			List<long[]> sizes, BenchmarkConfig config) {
		this.matrixClass = matrixClass;
		this.config = config;
		this.sizes = sizes;
	}

	public BenchmarkConfig getConfig() {
		return config;
	}

	public void run() {
		File timeFile = new File(BenchmarkUtil.getResultDir(getConfig()) + getMatrixLabel() + "/"
				+ getTaskName() + ".csv");
		File diffFile = new File(BenchmarkUtil.getResultDir(getConfig()) + getMatrixLabel() + "/"
				+ getTaskName() + "-diff.csv");
		File memFile = new File(BenchmarkUtil.getResultDir(getConfig()) + getMatrixLabel() + "/"
				+ getTaskName() + "-mem.csv");
		if (timeFile.exists()) {
			System.out.println("old results available, skipping " + getTaskName() + " for "
					+ getMatrixLabel());
			return;
		}
		Matrix2D resultTime = (Matrix2D) Matrix.Factory.zeros(ValueType.STRING, config.getRuns(),
				sizes.size());
		Matrix2D resultDiff = (Matrix2D) Matrix.Factory.zeros(ValueType.STRING, config.getRuns(),
				sizes.size());
		Matrix2D resultMem = (Matrix2D) Matrix.Factory.zeros(ValueType.STRING, config.getRuns(),
				sizes.size());

		resultTime.setLabel(getMatrixLabel() + "-" + getTaskName());
		resultDiff.setLabel(getMatrixLabel() + "-" + getTaskName() + "-diff");
		resultMem.setLabel(getMatrixLabel() + "-" + getTaskName() + "-mem");

		// create column labels for all sizes
		for (int s = 0; s < sizes.size(); s++) {
			long[] size = sizes.get(s);
			resultTime.setColumnLabel(s, String.valueOf(size[Matrix.ROW]));
			resultDiff.setColumnLabel(s, String.valueOf(size[Matrix.ROW]));
			resultMem.setColumnLabel(s, String.valueOf(size[Matrix.ROW]));
		}

		boolean stopped = false;
		for (int s = 0; !stopped && s < sizes.size(); s++) {
			long[] size = sizes.get(s);
			double bestStd = Double.MAX_VALUE;
			int tmpTrialCount = config.getMinTrialCount();
			DenseDoubleMatrix2D curTime = DenseDoubleMatrix2D.Factory.zeros(config.getRuns(), 1);
			DenseDoubleMatrix2D bestTime = DenseDoubleMatrix2D.Factory.zeros(config.getRuns(), 1);
			DenseDoubleMatrix2D curDiff = DenseDoubleMatrix2D.Factory.zeros(config.getRuns(), 1);
			DenseDoubleMatrix2D bestDiff = DenseDoubleMatrix2D.Factory.zeros(config.getRuns(), 1);
			DenseDoubleMatrix2D curMem = DenseDoubleMatrix2D.Factory.zeros(config.getRuns(), 1);
			DenseDoubleMatrix2D bestMem = DenseDoubleMatrix2D.Factory.zeros(config.getRuns(), 1);
			for (int c = 0; !stopped && c < tmpTrialCount; c++) {
				System.out.print(getTaskName() + " [" + Coordinates.toString("x", size) + "] ");
				System.out.print((c + 1) + "/" + tmpTrialCount + ": ");
				System.out.flush();

				for (int i = 0; !stopped && i < config.getBurnInRuns(); i++) {
					long t0 = System.currentTimeMillis();
					BenchmarkResult r = task(matrixClass, benchmarkSeed + c, i, size);
					double t = r.getTime();
					long t1 = System.currentTimeMillis();
					if (t == 0.0 || Double.isNaN(t) || t1 - t0 > config.getMaxTime()) {
						stopped = true;
					}
					System.out.print("#");
					System.out.flush();
				}
				for (int i = 0; !stopped && i < config.getRuns(); i++) {
					long t0 = System.currentTimeMillis();
					BenchmarkResult r = task(matrixClass, benchmarkSeed + c, i, size);
					double t = r.getTime();
					double diff = r.getDifference();
					long mem = r.getMem();
					long t1 = System.currentTimeMillis();
					if (t == 0.0 || Double.isNaN(t) || t1 - t0 > config.getMaxTime()) {
						stopped = true;
					}
					curTime.setAsDouble(t, i, 0);
					curDiff.setAsDouble(diff, i, 0);
					curMem.setAsLong(mem, i, 0);
					System.out.print(".");
					System.out.flush();
				}

				double meanTime = curTime.getMeanValue();
				double meanDiff = curDiff.getMeanValue();
				double meanMem = curMem.getMeanValue();
				double stdTime = curTime.getStdValue();
				double percentStd = stdTime / meanTime * 100.0;
				System.out.print(" " + MathUtil.round(meanTime, 3) + "+-"
						+ MathUtil.round(stdTime, 3) + "ms (+-" + MathUtil.round(percentStd, 1)
						+ "%)");
				if (!MathUtil.isNaNOrInfinite(meanDiff)) {
					System.out.print(" diff:" + meanDiff + " ");
				}
				System.out.print(" mem:" + (int) meanMem + " Bytes ");
				if (percentStd > config.getMaxStd()) {
					System.out.print(" standard deviation too large, result discarded");
					if (tmpTrialCount < config.getMaxTrialCount()) {
						tmpTrialCount++;
					}
				}
				if (percentStd < bestStd) {
					bestStd = percentStd;
					for (int i = 0; i < config.getRuns(); i++) {
						bestTime.setDouble(curTime.getDouble(i, 0), i, 0);
						bestDiff.setDouble(curDiff.getDouble(i, 0), i, 0);
						bestMem.setDouble(curMem.getDouble(i, 0), i, 0);
					}
				}
				System.out.println();
			}

			for (int i = 0; !stopped && i < config.getRuns(); i++) {
				resultTime.setAsDouble(bestTime.getDouble(i, 0), i, s);
				resultDiff.setAsDouble(bestDiff.getDouble(i, 0), i, s);
				resultMem.setAsDouble(bestMem.getDouble(i, 0), i, s);
			}
		}

		Matrix temp = Matrix.Factory.vertCat(
				resultTime.getAnnotation().getDimensionMatrix(Matrix.ROW), resultTime);
		Matrix diff = Matrix.Factory.vertCat(
				resultDiff.getAnnotation().getDimensionMatrix(Matrix.ROW), resultDiff);
		Matrix mem = Matrix.Factory.vertCat(resultMem.getAnnotation()
				.getDimensionMatrix(Matrix.ROW), resultMem);
		try {
			temp.export().toFile(timeFile).asCSV();
			mem.export().toFile(memFile).asCSV();
			if (!diff.containsMissingValues()) {
				diff.export().toFile(diffFile).asCSV();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public abstract BenchmarkResult task(Class<? extends Matrix> matrixClass, long benchmarkSeed,
			int run, long[] size);

	public abstract String getTaskName();

	public String getMatrixLabel() {
		return matrixClass.getSimpleName();
	}

}
