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
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.enums.FileFormat;
import org.ujmp.core.enums.ValueType;

public abstract class AbstractBenchmarkTask {

	private int MAXTIME = 10000;

	public static final double NOTAVAILABLE = 0;

	public static final double ERRORTIME = Double.NaN;

	private Class<? extends Matrix> matrixClass = null;

	private String[] sizes = null;

	private int blockCount = 5;

	private int runs = 5;

	private int burnInRuns = 1;

	private long benchmarkSeed = 0;

	public AbstractBenchmarkTask(long benchmarkSeed, Class<? extends Matrix> matrixClass,
			String sizes, int blockCount, int burnInRuns, int runs) {
		this.matrixClass = matrixClass;

		this.sizes = sizes.split(",");
		this.blockCount = blockCount;
		this.burnInRuns = burnInRuns;
		this.runs = runs;
		this.benchmarkSeed = benchmarkSeed;
	}

	public void run() {
		File resultFile = new File(BenchmarkUtil.getResultDir() + getMatrixLabel() + "/"
				+ getTaskName() + ".csv");
		if (resultFile.exists()) {
			System.out.println("old results available, skipping " + getTaskName() + " for "
					+ getMatrixLabel());
			return;
		}
		Matrix result = MatrixFactory.zeros(ValueType.STRING, runs, sizes.length);
		result.setLabel(getMatrixLabel() + "-" + getTaskName());

		boolean stopped = false;
		for (int s = 0; !stopped && s < sizes.length; s++) {
			double bestStd = Double.MAX_VALUE;
			for (int c = 0; c < blockCount; c++) {
				long[] size = Coordinates.parseString(sizes[s]);
				result.setColumnLabel(s, Coordinates.toString('x', size));
				System.out.print(getTaskName() + " [" + Coordinates.toString('x', size) + "] ");
				System.out.print((c + 1) + "/" + blockCount + ": ");
				System.out.flush();

				for (int i = 0; !stopped && i < burnInRuns; i++) {
					double t = task(matrixClass, benchmarkSeed, i, size, size);
					if (t == 0.0 || Double.isNaN(t) || t > MAXTIME) {
						stopped = true;
					}
					System.out.print("#");
					System.out.flush();
				}
				for (int i = 0; !stopped && i < runs; i++) {
					double t = task(matrixClass, benchmarkSeed, i, size, size);
					if (t == 0.0 || Double.isNaN(t) || t > MAXTIME) {
						stopped = true;
					}
					result.setAsDouble(t, i, s);
					System.out.print(".");
					System.out.flush();
				}

				Matrix mean = result.mean(Ret.NEW, Matrix.ROW, true);
				Matrix std = result.std(Ret.NEW, Matrix.ROW, true);
				mean.setLabel(mean.getLabel() + "-mean");
				std.setLabel(std.getLabel() + "-std");
				double tempStd = std.getAsDouble(0, s) / mean.getAsDouble(0, s) * 100;
				System.out.print(" " + mean.getAsInt(0, s) + "+-" + std.getAsInt(0, s) + "ms (+-"
						+ Math.round(tempStd) + "%)");
				if (tempStd < bestStd) {
					bestStd = tempStd;
					System.out.print(" best until now");
				}
				System.out.println();
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
			long[]... sizes);

	public abstract String getTaskName();

	public String getMatrixLabel() {
		return matrixClass.getCanonicalName();
	}

}
