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

package org.ujmp.core.benchmark;

import java.io.File;
import java.util.List;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;
import org.ujmp.core.enums.FileFormat;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.matrix.Matrix2D;
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
		File resultFile = new File(BenchmarkUtil.getResultDir(getConfig()) + getMatrixLabel() + "/"
				+ getTaskName() + ".csv");
		File diffFile = new File(BenchmarkUtil.getResultDir(getConfig()) + getMatrixLabel() + "/"
				+ getTaskName() + "-diff.csv");
		if (resultFile.exists()) {
			System.out.println("old results available, skipping " + getTaskName() + " for "
					+ getMatrixLabel());
			return;
		}
		Matrix2D resultTime = (Matrix2D) MatrixFactory.zeros(ValueType.STRING, config.getRuns(),
				sizes.size());
		Matrix2D resultDiff = (Matrix2D) MatrixFactory.zeros(ValueType.STRING, config.getRuns(),
				sizes.size());

		resultTime.setLabel(getMatrixLabel() + "-" + getTaskName());
		resultDiff.setLabel(getMatrixLabel() + "-" + getTaskName() + "-diff");

		boolean stopped = false;
		for (int s = 0; !stopped && s < sizes.size(); s++) {
			long[] size = sizes.get(s);
			resultTime.setColumnLabel(s, String.valueOf(size[Matrix.ROW]));
			resultDiff.setColumnLabel(s, String.valueOf(size[Matrix.ROW]));
			double bestStd = Double.MAX_VALUE;
			int tmpTrialCount = config.getDefaultTrialCount();
			DenseDoubleMatrix2D tmpTime = DenseDoubleMatrix2D.factory.zeros(config.getRuns(), 1);
			DenseDoubleMatrix2D bestTime = DenseDoubleMatrix2D.factory.zeros(config.getRuns(), 1);
			DenseDoubleMatrix2D tmpDiff = DenseDoubleMatrix2D.factory.zeros(config.getRuns(), 1);
			DenseDoubleMatrix2D bestDiff = DenseDoubleMatrix2D.factory.zeros(config.getRuns(), 1);
			for (int c = 0; !stopped && c < tmpTrialCount; c++) {
				System.out.print(getTaskName() + " [" + Coordinates.toString('x', size) + "] ");
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
					long t1 = System.currentTimeMillis();
					if (t == 0.0 || Double.isNaN(t) || t1 - t0 > config.getMaxTime()) {
						stopped = true;
					}
					tmpTime.setAsDouble(t, i, 0);
					tmpDiff.setAsDouble(diff, i, 0);
					System.out.print(".");
					System.out.flush();
				}

				double mean = tmpTime.getMeanValue();
				double meanDiff = tmpDiff.getMeanValue();
				double std = tmpTime.getStdValue();
				double tempStd = std / mean * 100.0;
				System.out.print(" " + MathUtil.round(mean, 3) + "+-" + MathUtil.round(std, 3)
						+ "ms (+-" + MathUtil.round(tempStd, 1) + "%)");
				if (!MathUtil.isNaNOrInfinite(meanDiff)) {
					System.out.print(" diff:" + meanDiff + " ");
				}
				if (tempStd > config.getMaxStd()) {
					System.out.print(" standard deviation too large, result discarded");
					if (tmpTrialCount < config.getMaxTrialCount()) {
						tmpTrialCount++;
					}
				}
				if (tempStd < bestStd) {
					bestStd = tempStd;
					for (int i = 0; i < config.getRuns(); i++) {
						bestTime.setDouble(tmpTime.getDouble(i, 0), i, 0);
						bestDiff.setDouble(tmpDiff.getDouble(i, 0), i, 0);
					}
				}
				System.out.println();
			}

			for (int i = 0; !stopped && i < config.getRuns(); i++) {
				resultTime.setAsDouble(bestTime.getDouble(i, 0), i, s);
				resultDiff.setAsDouble(bestDiff.getDouble(i, 0), i, s);
			}
		}

		Matrix temp = MatrixFactory.vertCat(resultTime.getAnnotation().getDimensionMatrix(
				Matrix.ROW), resultTime);
		Matrix diff = MatrixFactory.vertCat(resultDiff.getAnnotation().getDimensionMatrix(
				Matrix.ROW), resultDiff);
		try {
			temp.exportToFile(FileFormat.CSV, resultFile);
			if (!diff.containsMissingValues()) {
				diff.exportToFile(FileFormat.CSV, diffFile);
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
