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

import java.io.File;

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

	private String[] sizes = null;

	private long benchmarkSeed = 0;

	public AbstractBenchmarkTask(long benchmarkSeed, Class<? extends Matrix> matrixClass,
			String sizes, BenchmarkConfig config) {
		this.matrixClass = matrixClass;
		this.config = config;
		this.sizes = sizes.split(",");
	}

	public BenchmarkConfig getConfig() {
		return config;
	}

	public void run() {
		File resultFile = new File(BenchmarkUtil.getResultDir() + getMatrixLabel() + "/"
				+ getTaskName() + ".csv");
		if (resultFile.exists()) {
			System.out.println("old results available, skipping " + getTaskName() + " for "
					+ getMatrixLabel());
			return;
		}
		Matrix2D result = (Matrix2D) MatrixFactory.zeros(ValueType.STRING, config.getRuns(),
				sizes.length);

		result.setLabel(getMatrixLabel() + "-" + getTaskName());

		boolean stopped = false;
		for (int s = 0; !stopped && s < sizes.length; s++) {
			long[] size = Coordinates.parseString(sizes[s]);
			result.setColumnLabel(s, Coordinates.toString('x', size));
			double bestStd = Double.MAX_VALUE;
			int tmpTrialCount = config.getDefaultTrialCount();
			DenseDoubleMatrix2D tmpResult = DenseDoubleMatrix2D.factory.dense(config.getRuns(), 1);
			DenseDoubleMatrix2D bestResult = DenseDoubleMatrix2D.factory.dense(config.getRuns(), 1);
			for (int c = 0; !stopped && c < tmpTrialCount; c++) {
				System.out.print(getTaskName() + " [" + Coordinates.toString('x', size) + "] ");
				System.out.print((c + 1) + "/" + tmpTrialCount + ": ");
				System.out.flush();

				for (int i = 0; !stopped && i < config.getBurnInRuns(); i++) {
					double t = task(matrixClass, benchmarkSeed, i, size);
					if (t == 0.0 || Double.isNaN(t) || t > config.getMaxTime()) {
						stopped = true;
					}
					System.out.print("#");
					System.out.flush();
				}
				for (int i = 0; !stopped && i < config.getRuns(); i++) {
					double t = task(matrixClass, benchmarkSeed, i, size);
					if (t == 0.0 || Double.isNaN(t) || t > config.getMaxTime()) {
						stopped = true;
					}
					tmpResult.setAsDouble(t, i, 0);
					System.out.print(".");
					System.out.flush();
				}

				double mean = tmpResult.getMeanValue();
				double std = tmpResult.getStdValue();
				double tempStd = std / mean * 100.0;
				System.out.print(" " + MathUtil.round(mean, 3) + "+-" + MathUtil.round(std, 3)
						+ "ms (+-" + MathUtil.round(tempStd, 1) + "%)");
				if (tempStd > config.getMaxStd()) {
					System.out.print(" standard deviation too large, result discarded");
					if (tmpTrialCount < config.getMaxTrialCount()) {
						tmpTrialCount++;
					}
				}
				if (tempStd < bestStd) {
					bestStd = tempStd;
					for (int i = 0; i < config.getRuns(); i++) {
						bestResult.setDouble(tmpResult.getDouble(i, 0), i, 0);
					}
				}
				System.out.println();
			}

			for (int i = 0; !stopped && i < config.getRuns(); i++) {
				result.setAsDouble(bestResult.getDouble(i, 0), i, s);
			}
		}

		Matrix temp = MatrixFactory.vertCat(result.getAnnotation().getDimensionMatrix(Matrix.ROW),
				result);
		try {
			temp.exportToFile(FileFormat.CSV, resultFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public abstract double task(Class<? extends Matrix> matrixClass, long benchmarkSeed, int run,
			long[] size);

	public abstract String getTaskName();

	public String getMatrixLabel() {
		return matrixClass.getSimpleName();
	}

}
