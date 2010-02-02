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
import java.util.Random;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.DoubleMatrix2D;
import org.ujmp.core.doublematrix.impl.DefaultDenseDoubleMatrix2D;
import org.ujmp.core.enums.FileFormat;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.GCUtil;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.StringUtil;

public abstract class AbstractMatrix2DBenchmark {

	public static final int BURNINRUNS = 1;

	public static final int RUNS = 5;

	public static final int BLOCKCOUNT = 5;

	public static final double MAXTIME = 10000;

	private static final double NOTAVAILABLE = 0;

	private static final double ERRORTIME = Double.NaN;

	private final String defaultSizes = "2x2,3x3,4x4,5x5,10x10,20x20,50x50,100x100,200x200,500x500,1000x1000,2000x2000";

	private final String transposeSizes = defaultSizes;

	private final String timesSizes = defaultSizes;

	private final String plusSizes = defaultSizes;

	private final String mtimesSizes = defaultSizes;

	private final String invSizes = defaultSizes;

	private final String solveSquareSizes = defaultSizes;

	private final String solveTallSizes = "4x2,6x3,8x4,10x5,20x10,40x20,100x50,200x100,400x200,1000x500,2000x1000";

	private final String svdSizes = defaultSizes;

	private final String eigSizes = defaultSizes;

	private final String qrSizes = defaultSizes;

	private final String luSizes = defaultSizes;

	private final String cholSizes = defaultSizes;

	public abstract DoubleMatrix2D createMatrix(long... size) throws MatrixException;

	public abstract DoubleMatrix2D createMatrix(Matrix source) throws MatrixException;

	private long benchmarkSeed = 3345454363676l;

	public AbstractMatrix2DBenchmark() {
		this(System.currentTimeMillis());
	}

	public AbstractMatrix2DBenchmark(long benchmarkSeed) {
		this.benchmarkSeed = benchmarkSeed;
	}

	public void setRunsPerMatrix(int runs) {
		System.setProperty("runsPerMatrix", "" + runs);
	}

	public int getRunsPerMatrix() {
		return MathUtil.getInt(System.getProperty("runsPerMatrix"));
	}

	public int getBlockCount() {
		return BLOCKCOUNT;
	}

	public void setBurnInRuns(int runs) {
		System.setProperty("burnInRuns", "" + runs);
	}

	public String getMatrixLabel() {
		return createMatrix(1, 1).getClass().getSimpleName();
	}

	public int getBurnInRuns() {
		return MathUtil.getInt(System.getProperty("burnInRuns"));
	}

	public boolean isRunInit() {
		return "true".equals(System.getProperty("runInit"));
	}

	public void setRunInit(boolean runInit) {
		System.setProperty("runInit", "" + runInit);
	}

	public boolean isRunCreate() {
		return "true".equals(System.getProperty("runCreate"));
	}

	public void setRunCreate(boolean runCreate) {
		System.setProperty("runCreate", "" + runCreate);
	}

	public boolean isRunCopy() {
		return "true".equals(System.getProperty("runCopy"));
	}

	public void setRunCopy(boolean runCopy) {
		System.setProperty("runCopy", "" + runCopy);
	}

	public boolean isRunTimesScalarNew() {
		return "true".equals(System.getProperty("runTimesScalarNew"));
	}

	public boolean isRunPlusMatrixNew() {
		return "true".equals(System.getProperty("runPlusMatrixNew"));
	}

	public void setRunTimesScalarNew(boolean runTimesScalarNew) {
		System.setProperty("runTimesScalarNew", "" + runTimesScalarNew);
	}

	public void setRunPlusMatrixNew(boolean runPlusMatrixNew) {
		System.setProperty("runPlusMatrixNew", "" + runPlusMatrixNew);
	}

	public boolean isRunTransposeNew() {
		return "true".equals(System.getProperty("runTransposeNew"));
	}

	public boolean isRunMtimesNew() {
		return "true".equals(System.getProperty("runMtimesNew"));
	}

	public boolean isRunInv() {
		return "true".equals(System.getProperty("runInv"));
	}

	public boolean isRunSolveSquare() {
		return "true".equals(System.getProperty("runSolveSquare"));
	}

	public boolean isRunSolveTall() {
		return "true".equals(System.getProperty("runSolveTall"));
	}

	public boolean isRunSVD() {
		return "true".equals(System.getProperty("runSVD"));
	}

	public boolean isRunChol() {
		return "true".equals(System.getProperty("runChol"));
	}

	public boolean isRunEig() {
		return "true".equals(System.getProperty("runEig"));
	}

	public boolean isRunQR() {
		return "true".equals(System.getProperty("runQR"));
	}

	public boolean isRunLU() {
		return "true".equals(System.getProperty("runLU"));
	}

	public void setRunTransposeNew(boolean b) {
		System.setProperty("runTransposeNew", "" + b);
	}

	public void setRunMtimesNew(boolean b) {
		System.setProperty("runMtimesNew", "" + b);
	}

	public void setRunInv(boolean b) {
		System.setProperty("runInv", "" + b);
	}

	public void setRunSolveSquare(boolean b) {
		System.setProperty("runSolveSquare", "" + b);
	}

	public void setRunSolveTall(boolean b) {
		System.setProperty("runSolveTall", "" + b);
	}

	public void setRunSVD(boolean b) {
		System.setProperty("runSVD", "" + b);
	}

	public void setRunQR(boolean b) {
		System.setProperty("runQR", "" + b);
	}

	public void setRunEig(boolean b) {
		System.setProperty("runEig", "" + b);
	}

	public void setRunLU(boolean b) {
		System.setProperty("runLU", "" + b);
	}

	public void setRunChol(boolean b) {
		System.setProperty("runChol", "" + b);
	}

	public String getTransposeSizes() {
		return transposeSizes;
	}

	public String getTimesSizes() {
		return timesSizes;
	}

	public String getPlusSizes() {
		return plusSizes;
	}

	public String getMtimesSizes() {
		return mtimesSizes;
	}

	public String getInvSizes() {
		return invSizes;
	}

	public String getEigSizes() {
		return eigSizes;
	}

	public String getSVDSizes() {
		return svdSizes;
	}

	public String getSolveSquareSizes() {
		return solveSquareSizes;
	}

	public String getSolveTallSizes() {
		return solveTallSizes;
	}

	public String getQRSizes() {
		return qrSizes;
	}

	public String getLUSizes() {
		return luSizes;
	}

	public String getCholSizes() {
		return cholSizes;
	}

	public void runAllTests(int burnInRuns, int runs) throws Exception {
		setRunAllTests(burnInRuns, runs);
		run();
	}

	public void setRunAllTests(int burnInRuns, int runs) throws Exception {
		setBurnInRuns(burnInRuns);
		setRunsPerMatrix(runs);

		setRunTimesScalarNew(true);
		setRunPlusMatrixNew(true);
		setRunTransposeNew(true);
		setRunMtimesNew(true);
		setRunInv(true);
		setRunSolveSquare(true);
		setRunSolveTall(true);
		setRunSVD(true);
		setRunEig(true);
		setRunQR(true);
		setRunLU(true);
		setRunChol(true);
	}

	public void run() throws Exception {
		if (Runtime.getRuntime().maxMemory() < 980 * 1024 * 1024) {
			throw new Exception("You must start Java with more memory: -Xmx1024M");
		}

		try {
			System.out.println("===============================================================");
			System.out.println(createMatrix(1, 1).getClass().getSimpleName());
			System.out.println("===============================================================");

			long t0 = System.currentTimeMillis();

			if (isRunTimesScalarNew()) {
				runBenchmarkTimesScalarNew();
			}

			if (isRunPlusMatrixNew()) {
				runBenchmarkPlusMatrixNew();
				// new PlusMatrixTask(454535435l,
				// DefaultDenseDoubleMatrix2D.class, getPlusSizes(), 5,
				// 1, 5).run();
			}

			if (isRunTransposeNew()) {
				runBenchmarkTransposeNew();
			}

			if (isRunMtimesNew()) {
				runBenchmarkMtimesNew();
			}

			if (isRunInv()) {
				runBenchmarkInv();
			}

			if (isRunSolveSquare()) {
				runBenchmarkSolveSquare();
			}

			if (isRunSolveTall()) {
				runBenchmarkSolveTall();
			}

			if (isRunSVD()) {
				runBenchmarkSVD();
			}

			if (isRunEig()) {
				runBenchmarkEig();
			}

			if (isRunQR()) {
				runBenchmarkQR();
			}

			if (isRunLU()) {
				runBenchmarkLU();
			}

			if (isRunChol()) {
				runBenchmarkChol();
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

	public void runBenchmarkTransposeNew() throws Exception {
		String test = "transposeNew";
		File resultFile = new File(BenchmarkUtil.getResultDir() + getMatrixLabel() + "/" + test
				+ ".csv");
		if (resultFile.exists()) {
			System.out.println("old results available, skipping " + test + " for "
					+ getMatrixLabel());
			return;
		}
		String[] sizes = getTransposeSizes().split(",");
		Matrix result = MatrixFactory.zeros(ValueType.STRING, getRunsPerMatrix(), sizes.length);
		result.setLabel(getMatrixLabel() + "-" + test);

		boolean stopped = false;
		for (int s = 0; !stopped && s < sizes.length; s++) {
			long[] size = Coordinates.parseString(sizes[s]);
			result.setColumnLabel(s, Coordinates.toString('x', size));
			System.out.print(test + " [" + Coordinates.toString('x', size) + "]: ");
			System.out.flush();

			for (int i = 0; !stopped && i < getBurnInRuns(); i++) {
				double t = benchmarkTransposeNew(i, size);
				if (t == 0.0 || Double.isNaN(t) || t > MAXTIME) {
					stopped = true;
				}
				System.out.print("#");
				System.out.flush();
			}
			for (int i = 0; !stopped && i < getRunsPerMatrix(); i++) {
				double t = benchmarkTransposeNew(i, size);
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
			System.out.println(" " + mean.getAsInt(0, s) + "+-" + std.getAsInt(0, s) + "ms (+-"
					+ Math.round(std.getAsDouble(0, s) / mean.getAsDouble(0, s) * 100) + "%)");
		}

		Matrix temp = MatrixFactory.vertCat(result.getAnnotation().getDimensionMatrix(Matrix.ROW),
				result);
		temp.exportToFile(FileFormat.CSV, resultFile);
	}

	public void runBenchmarkTimesScalarNew() throws Exception {
		String test = "timesScalarNew";
		File resultFile = new File(BenchmarkUtil.getResultDir() + getMatrixLabel() + "/" + test
				+ ".csv");
		if (resultFile.exists()) {
			System.out.println("old results available, skipping " + test + " for "
					+ getMatrixLabel());
			return;
		}
		String[] sizes = getTimesSizes().split(",");
		Matrix result = MatrixFactory.zeros(ValueType.STRING, getRunsPerMatrix(), sizes.length);
		result.setLabel(getMatrixLabel() + "-" + test);

		boolean stopped = false;
		for (int s = 0; !stopped && s < sizes.length; s++) {
			for (int c = 0; c < getBlockCount(); c++) {
				long[] size = Coordinates.parseString(sizes[s]);
				result.setColumnLabel(s, Coordinates.toString('x', size));
				System.out.print(test + " [" + Coordinates.toString('x', size) + "] ");
				System.out.print((c + 1) + "/" + getBlockCount() + ": ");
				System.out.flush();

				for (int i = 0; !stopped && i < getBurnInRuns(); i++) {
					double t = benchmarkTimesScalarNew(i, size);
					if (t == 0.0 || Double.isNaN(t) || t > MAXTIME) {
						stopped = true;
					}
					System.out.print("#");
					System.out.flush();
				}
				for (int i = 0; !stopped && i < getRunsPerMatrix(); i++) {
					double t = benchmarkTimesScalarNew(i, size);
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
				double stdPercent = std.getAsDouble(0, s) / mean.getAsDouble(0, s) * 100.0;
				System.out.println(" " + mean.getAsInt(0, s) + "+-" + std.getAsInt(0, s) + "ms (+-"
						+ Math.round(stdPercent) + "%)");
			}
		}

		Matrix temp = MatrixFactory.vertCat(result.getAnnotation().getDimensionMatrix(Matrix.ROW),
				result);
		temp.exportToFile(FileFormat.CSV, resultFile);
	}

	public void runBenchmarkPlusMatrixNew() throws Exception {
		String test = "plusMatrixNew";
		File resultFile = new File(BenchmarkUtil.getResultDir() + getMatrixLabel() + "/" + test
				+ ".csv");
		if (resultFile.exists()) {
			System.out.println("old results available, skipping " + test + " for "
					+ getMatrixLabel());
			return;
		}
		String[] sizes = getTimesSizes().split(",");
		Matrix result = MatrixFactory.zeros(ValueType.STRING, getRunsPerMatrix(), sizes.length);
		result.setLabel(getMatrixLabel() + "-" + test);

		boolean stopped = false;
		for (int s = 0; !stopped && s < sizes.length; s++) {
			for (int c = 0; c < getBlockCount(); c++) {
				long[] size = Coordinates.parseString(sizes[s]);
				result.setColumnLabel(s, Coordinates.toString('x', size));
				System.out.print(test + " [" + Coordinates.toString('x', size) + "] ");
				System.out.print((c + 1) + "/" + getBlockCount() + ": ");
				System.out.flush();

				for (int i = 0; !stopped && i < getBurnInRuns(); i++) {
					double t = benchmarkPlusMatrixNew(i, size, size);
					if (t == 0.0 || Double.isNaN(t) || t > MAXTIME) {
						stopped = true;
					}
					System.out.print("#");
					System.out.flush();
				}
				for (int i = 0; !stopped && i < getRunsPerMatrix(); i++) {
					double t = benchmarkPlusMatrixNew(i, size, size);
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
				System.out.println(" " + mean.getAsInt(0, s) + "+-" + std.getAsInt(0, s) + "ms (+-"
						+ Math.round(std.getAsDouble(0, s) / mean.getAsDouble(0, s) * 100) + "%)");
			}
		}

		Matrix temp = MatrixFactory.vertCat(result.getAnnotation().getDimensionMatrix(Matrix.ROW),
				result);
		temp.exportToFile(FileFormat.CSV, resultFile);
	}

	public void runBenchmarkInv() throws Exception {
		String test = "inv";
		File resultFile = new File(BenchmarkUtil.getResultDir() + getMatrixLabel() + "/" + test
				+ ".csv");
		if (resultFile.exists()) {
			System.out.println("old results available, skipping " + test + " for "
					+ getMatrixLabel());
			return;
		}
		String[] sizes = getInvSizes().split(",");
		Matrix result = MatrixFactory.zeros(ValueType.STRING, getRunsPerMatrix(), sizes.length);
		result.setLabel(getMatrixLabel() + "-" + test);

		boolean stopped = false;
		for (int s = 0; !stopped && s < sizes.length; s++) {
			long[] size = Coordinates.parseString(sizes[s]);
			result.setColumnLabel(s, Coordinates.toString('x', size));
			System.out.print(test + " [" + Coordinates.toString('x', size) + "]: ");
			System.out.flush();

			for (int i = 0; !stopped && i < getBurnInRuns(); i++) {
				double t = benchmarkInv(i, size);
				if (t == 0.0 || Double.isNaN(t) || t > MAXTIME) {
					stopped = true;
				}
				System.out.print("#");
				System.out.flush();
			}
			for (int i = 0; !stopped && i < getRunsPerMatrix(); i++) {
				double t = benchmarkInv(i, size);
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
			System.out.println(" " + mean.getAsInt(0, s) + "+-" + std.getAsInt(0, s) + "ms (+-"
					+ Math.round(std.getAsDouble(0, s) / mean.getAsDouble(0, s) * 100) + "%)");
		}

		Matrix temp = MatrixFactory.vertCat(result.getAnnotation().getDimensionMatrix(Matrix.ROW),
				result);
		temp.exportToFile(FileFormat.CSV, resultFile);
	}

	public void runBenchmarkSVD() throws Exception {
		String test = "svd";
		File resultFile = new File(BenchmarkUtil.getResultDir() + getMatrixLabel() + "/" + test
				+ ".csv");
		if (resultFile.exists()) {
			System.out.println("old results available, skipping " + test + " for "
					+ getMatrixLabel());
			return;
		}
		String[] sizes = getSVDSizes().split(",");
		Matrix result = MatrixFactory.zeros(ValueType.STRING, getRunsPerMatrix(), sizes.length);
		result.setLabel(getMatrixLabel() + "-" + test);

		boolean stopped = false;
		for (int s = 0; !stopped && s < sizes.length; s++) {
			long[] size = Coordinates.parseString(sizes[s]);
			result.setColumnLabel(s, Coordinates.toString('x', size));
			System.out.print(test + " [" + Coordinates.toString('x', size) + "]: ");
			System.out.flush();

			for (int i = 0; !stopped && i < getBurnInRuns(); i++) {
				double t = benchmarkSVD(i, size);
				if (t == 0.0 || Double.isNaN(t) || t > MAXTIME) {
					stopped = true;
				}
				System.out.print("#");
				System.out.flush();
			}
			for (int i = 0; !stopped && i < getRunsPerMatrix(); i++) {
				double t = benchmarkSVD(i, size);
				result.setAsDouble(t, i, s);
				System.out.print(".");
				System.out.flush();
			}

			Matrix mean = result.mean(Ret.NEW, Matrix.ROW, true);
			Matrix std = result.std(Ret.NEW, Matrix.ROW, true);
			mean.setLabel(mean.getLabel() + "-mean");
			std.setLabel(std.getLabel() + "-std");
			System.out.println(" " + mean.getAsInt(0, s) + "+-" + std.getAsInt(0, s) + "ms (+-"
					+ Math.round(std.getAsDouble(0, s) / mean.getAsDouble(0, s) * 100) + "%)");
		}

		Matrix temp = MatrixFactory.vertCat(result.getAnnotation().getDimensionMatrix(Matrix.ROW),
				result);
		temp.exportToFile(FileFormat.CSV, resultFile);
	}

	public void runBenchmarkSolveSquare() throws Exception {
		String test = "solveSquare";
		File resultFile = new File(BenchmarkUtil.getResultDir() + getMatrixLabel() + "/" + test
				+ ".csv");
		if (resultFile.exists()) {
			System.out.println("old results available, skipping " + test + " for "
					+ getMatrixLabel());
			return;
		}
		String[] sizes = getSolveSquareSizes().split(",");
		Matrix result = MatrixFactory.zeros(ValueType.STRING, getRunsPerMatrix(), sizes.length);
		result.setLabel(getMatrixLabel() + "-" + test);

		boolean stopped = false;
		for (int s = 0; !stopped && s < sizes.length; s++) {
			long[] size = Coordinates.parseString(sizes[s]);
			result.setColumnLabel(s, Coordinates.toString('x', size));
			System.out.print(test + " [" + Coordinates.toString('x', size) + "]: ");
			System.out.flush();

			for (int i = 0; !stopped && i < getBurnInRuns(); i++) {
				double t = benchmarkSolve(i, size);
				if (t == 0.0 || Double.isNaN(t) || t > MAXTIME) {
					stopped = true;
				}
				System.out.print("#");
				System.out.flush();
			}
			for (int i = 0; !stopped && i < getRunsPerMatrix(); i++) {
				double t = benchmarkSolve(i, size);
				result.setAsDouble(t, i, s);
				System.out.print(".");
				System.out.flush();
			}

			Matrix mean = result.mean(Ret.NEW, Matrix.ROW, true);
			Matrix std = result.std(Ret.NEW, Matrix.ROW, true);
			mean.setLabel(mean.getLabel() + "-mean");
			std.setLabel(std.getLabel() + "-std");
			System.out.println(" " + mean.getAsInt(0, s) + "+-" + std.getAsInt(0, s) + "ms (+-"
					+ Math.round(std.getAsDouble(0, s) / mean.getAsDouble(0, s) * 100) + "%)");
		}

		Matrix temp = MatrixFactory.vertCat(result.getAnnotation().getDimensionMatrix(Matrix.ROW),
				result);
		temp.exportToFile(FileFormat.CSV, resultFile);
	}

	public void runBenchmarkSolveTall() throws Exception {
		String test = "solveTall";
		File resultFile = new File(BenchmarkUtil.getResultDir() + getMatrixLabel() + "/" + test
				+ ".csv");
		if (resultFile.exists()) {
			System.out.println("old results available, skipping " + test + " for "
					+ getMatrixLabel());
			return;
		}
		String[] sizes = getSolveTallSizes().split(",");
		Matrix result = MatrixFactory.zeros(ValueType.STRING, getRunsPerMatrix(), sizes.length);
		result.setLabel(getMatrixLabel() + "-" + test);

		boolean stopped = false;
		for (int s = 0; !stopped && s < sizes.length; s++) {
			long[] size = Coordinates.parseString(sizes[s]);
			result.setColumnLabel(s, Coordinates.toString('x', size));
			System.out.print(test + " [" + Coordinates.toString('x', size) + "]: ");
			System.out.flush();

			for (int i = 0; !stopped && i < getBurnInRuns(); i++) {
				double t = benchmarkSolve(i, size);
				if (t == 0.0 || Double.isNaN(t) || t > MAXTIME) {
					stopped = true;
				}
				System.out.print("#");
				System.out.flush();
			}
			for (int i = 0; !stopped && i < getRunsPerMatrix(); i++) {
				double t = benchmarkSolve(i, size);
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
			System.out.println(" " + mean.getAsInt(0, s) + "+-" + std.getAsInt(0, s) + "ms (+-"
					+ Math.round(std.getAsDouble(0, s) / mean.getAsDouble(0, s) * 100) + "%)");
		}

		Matrix temp = MatrixFactory.vertCat(result.getAnnotation().getDimensionMatrix(Matrix.ROW),
				result);
		temp.exportToFile(FileFormat.CSV, resultFile);
	}

	public void runBenchmarkQR() throws Exception {
		String test = "qr";
		File resultFile = new File(BenchmarkUtil.getResultDir() + getMatrixLabel() + "/" + test
				+ ".csv");
		if (resultFile.exists()) {
			System.out.println("old results available, skipping " + test + " for "
					+ getMatrixLabel());
			return;
		}
		String[] sizes = getQRSizes().split(",");
		Matrix result = MatrixFactory.zeros(ValueType.STRING, getRunsPerMatrix(), sizes.length);
		result.setLabel(getMatrixLabel() + "-" + test);

		boolean stopped = false;
		for (int s = 0; !stopped && s < sizes.length; s++) {
			long[] size = Coordinates.parseString(sizes[s]);
			result.setColumnLabel(s, Coordinates.toString('x', size));
			System.out.print(test + " [" + Coordinates.toString('x', size) + "]: ");
			System.out.flush();

			for (int i = 0; !stopped && i < getBurnInRuns(); i++) {
				double t = benchmarkQR(i, size);
				if (t == 0.0 || Double.isNaN(t) || t > MAXTIME) {
					stopped = true;
				}
				System.out.print("#");
				System.out.flush();
			}
			for (int i = 0; !stopped && i < getRunsPerMatrix(); i++) {
				double t = benchmarkQR(i, size);
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
			System.out.println(" " + mean.getAsInt(0, s) + "+-" + std.getAsInt(0, s) + "ms (+-"
					+ Math.round(std.getAsDouble(0, s) / mean.getAsDouble(0, s) * 100) + "%)");
		}

		Matrix temp = MatrixFactory.vertCat(result.getAnnotation().getDimensionMatrix(Matrix.ROW),
				result);
		temp.exportToFile(FileFormat.CSV, resultFile);
	}

	public void runBenchmarkLU() throws Exception {
		String test = "lu";
		File resultFile = new File(BenchmarkUtil.getResultDir() + getMatrixLabel() + "/" + test
				+ ".csv");
		if (resultFile.exists()) {
			System.out.println("old results available, skipping " + test + " for "
					+ getMatrixLabel());
			return;
		}
		String[] sizes = getLUSizes().split(",");
		Matrix result = MatrixFactory.zeros(ValueType.STRING, getRunsPerMatrix(), sizes.length);
		result.setLabel(getMatrixLabel() + "-" + test);

		boolean stopped = false;
		for (int s = 0; !stopped && s < sizes.length; s++) {
			long[] size = Coordinates.parseString(sizes[s]);
			result.setColumnLabel(s, Coordinates.toString('x', size));
			System.out.print(test + " [" + Coordinates.toString('x', size) + "]: ");
			System.out.flush();

			for (int i = 0; !stopped && i < getBurnInRuns(); i++) {
				double t = benchmarkLU(i, size);
				if (t == 0.0 || Double.isNaN(t) || t > MAXTIME) {
					stopped = true;
				}
				System.out.print("#");
				System.out.flush();
			}
			for (int i = 0; !stopped && i < getRunsPerMatrix(); i++) {
				double t = benchmarkLU(i, size);
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
			System.out.println(" " + mean.getAsInt(0, s) + "+-" + std.getAsInt(0, s) + "ms (+-"
					+ Math.round(std.getAsDouble(0, s) / mean.getAsDouble(0, s) * 100) + "%)");
		}

		Matrix temp = MatrixFactory.vertCat(result.getAnnotation().getDimensionMatrix(Matrix.ROW),
				result);
		temp.exportToFile(FileFormat.CSV, resultFile);
	}

	public void runBenchmarkEig() throws Exception {
		String test = "eig";
		File resultFile = new File(BenchmarkUtil.getResultDir() + getMatrixLabel() + "/" + test
				+ ".csv");
		if (resultFile.exists()) {
			System.out.println("old results available, skipping " + test + " for "
					+ getMatrixLabel());
			return;
		}
		String[] sizes = getEigSizes().split(",");
		Matrix result = MatrixFactory.zeros(ValueType.STRING, getRunsPerMatrix(), sizes.length);
		result.setLabel(getMatrixLabel() + "-" + test);

		boolean stopped = false;
		for (int s = 0; !stopped && s < sizes.length; s++) {
			long[] size = Coordinates.parseString(sizes[s]);
			result.setColumnLabel(s, Coordinates.toString('x', size));
			System.out.print(test + " [" + Coordinates.toString('x', size) + "]: ");
			System.out.flush();

			for (int i = 0; !stopped && i < getBurnInRuns(); i++) {
				double t = benchmarkEig(i, size);
				if (t == 0.0 || Double.isNaN(t) || t > MAXTIME) {
					stopped = true;
				}
				System.out.print("#");
				System.out.flush();
			}
			for (int i = 0; !stopped && i < getRunsPerMatrix(); i++) {
				double t = benchmarkEig(i, size);
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
			System.out.println(" " + mean.getAsInt(0, s) + "+-" + std.getAsInt(0, s) + "ms (+-"
					+ Math.round(std.getAsDouble(0, s) / mean.getAsDouble(0, s) * 100) + "%)");
		}

		Matrix temp = MatrixFactory.vertCat(result.getAnnotation().getDimensionMatrix(Matrix.ROW),
				result);
		temp.exportToFile(FileFormat.CSV, resultFile);
	}

	public void runBenchmarkChol() throws Exception {
		String test = "chol";
		File resultFile = new File(BenchmarkUtil.getResultDir() + getMatrixLabel() + "/" + test
				+ ".csv");
		if (resultFile.exists()) {
			System.out.println("old results available, skipping " + test + " for "
					+ getMatrixLabel());
			return;
		}
		String[] sizes = getCholSizes().split(",");
		Matrix result = MatrixFactory.zeros(ValueType.STRING, getRunsPerMatrix(), sizes.length);
		result.setLabel(getMatrixLabel() + "-" + test);

		boolean stopped = false;
		for (int s = 0; !stopped && s < sizes.length; s++) {
			long[] size = Coordinates.parseString(sizes[s]);
			result.setColumnLabel(s, Coordinates.toString('x', size));
			System.out.print(test + " [" + Coordinates.toString('x', size) + "]: ");
			System.out.flush();

			for (int i = 0; !stopped && i < getBurnInRuns(); i++) {
				double t = benchmarkChol(i, size);
				if (t == 0.0 || Double.isNaN(t) || t > MAXTIME) {
					stopped = true;
				}
				System.out.print("#");
				System.out.flush();
			}
			for (int i = 0; !stopped && i < getRunsPerMatrix(); i++) {
				double t = benchmarkChol(i, size);
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
			System.out.println(" " + mean.getAsInt(0, s) + "+-" + std.getAsInt(0, s) + "ms (+-"
					+ Math.round(std.getAsDouble(0, s) / mean.getAsDouble(0, s) * 100) + "%)");
		}

		Matrix temp = MatrixFactory.vertCat(result.getAnnotation().getDimensionMatrix(Matrix.ROW),
				result);
		temp.exportToFile(FileFormat.CSV, resultFile);
	}

	public void runBenchmarkMtimesNew() throws Exception {
		String test = "mtimesNew";
		File resultFile = new File(BenchmarkUtil.getResultDir() + getMatrixLabel() + "/" + test
				+ ".csv");
		if (resultFile.exists()) {
			System.out.println("old results available, skipping " + test + " for "
					+ getMatrixLabel());
			return;
		}
		String[] sizes = getMtimesSizes().split(",");
		Matrix result = MatrixFactory.zeros(ValueType.STRING, getRunsPerMatrix(), sizes.length);
		result.setLabel(getMatrixLabel() + "-" + test);

		boolean stopped = false;
		for (int s = 0; !stopped && s < sizes.length; s++) {
			for (int c = 0; c < getBlockCount(); c++) {
				long[] size = Coordinates.parseString(sizes[s]);
				result.setColumnLabel(s, Coordinates.toString('x', size));
				System.out.print(test + " [" + Coordinates.toString('x', size) + "] ");
				System.out.print((c + 1) + "/" + getBlockCount() + ": ");
				System.out.flush();

				for (int i = 0; !stopped && i < getBurnInRuns(); i++) {
					double t = benchmarkMtimesNew(i, size, size);
					if (t == 0.0 || Double.isNaN(t) || t > MAXTIME) {
						stopped = true;
					}
					System.out.print("#");
					System.out.flush();
				}
				for (int i = 0; !stopped && i < getRunsPerMatrix(); i++) {
					double t = benchmarkMtimesNew(i, size, size);
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
				System.out.println(" " + mean.getAsInt(0, s) + "+-" + std.getAsInt(0, s) + "ms (+-"
						+ Math.round(std.getAsDouble(0, s) / mean.getAsDouble(0, s) * 100) + "%)");
			}
		}

		Matrix temp = MatrixFactory.vertCat(result.getAnnotation().getDimensionMatrix(Matrix.ROW),
				result);
		temp.exportToFile(FileFormat.CSV, resultFile);
	}

	public double benchmarkCopy(long... size) {
		Matrix m = null, r = null;
		try {
			m = createMatrix(size);
			if (!m.getClass().getName().startsWith("org.ujmp.core")
					&& m.getClass().getDeclaredMethod("copy") == null) {
				System.out.print("-");
				System.out.flush();
				return NOTAVAILABLE;
			}
			GCUtil.purgeMemory();
			long t0 = System.nanoTime();
			r = m.copy();
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

	public double benchmarkTransposeNew(int run, long... size) {
		DoubleMatrix2D m = null;
		Matrix r = null;
		try {
			m = createMatrix(size);
			if (!m.getClass().getName().startsWith("org.ujmp.core")
					&& m.getClass().getDeclaredMethod("transpose") == null) {
				System.out.print("-");
				System.out.flush();
				return NOTAVAILABLE;
			}
			rand(run, m);
			GCUtil.purgeMemory();
			long t0 = System.nanoTime();
			r = m.transpose();
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

	public double benchmarkTimesScalarNew(int run, long... size) {
		DoubleMatrix2D m = null;
		Matrix r = null;
		try {
			m = createMatrix(size);
			if (!m.getClass().getName().startsWith("org.ujmp.core")
					&& m.getClass().getDeclaredMethod("times", Double.TYPE) == null) {
				System.out.print("-");
				System.out.flush();
				return NOTAVAILABLE;
			}
			rand(run, m);
			double a = MathUtil.nextDouble();
			GCUtil.purgeMemory();
			long t0 = System.nanoTime();
			r = m.times(a);
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

	public void rand(long seed, DoubleMatrix2D matrix) {
		Random random = new Random(benchmarkSeed + seed);
		int rows = (int) matrix.getRowCount();
		int cols = (int) matrix.getColumnCount();
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				matrix.setDouble(random.nextDouble() - 0.5, r, c);
			}
		}
	}

	public void randSymm(long seed, DoubleMatrix2D matrix) {
		Random random = new Random(benchmarkSeed + 31 * seed);
		int rows = (int) matrix.getRowCount();
		int cols = (int) matrix.getColumnCount();
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols && c <= r; c++) {
				double f = random.nextDouble() - 0.5;
				matrix.setDouble(f - 0.5, r, c);
				matrix.setDouble(f - 0.5, c, r);
			}
		}
	}

	public void randPositiveDefinite(long seed, DoubleMatrix2D matrix) {
		Random random = new Random(benchmarkSeed + 31 * seed);
		DenseDoubleMatrix2D temp = new DefaultDenseDoubleMatrix2D(matrix.getSize());
		int rows = (int) temp.getRowCount();
		int cols = (int) temp.getColumnCount();
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				temp.setDouble(random.nextDouble(), r, c);
			}
		}
		DenseDoubleMatrix2D result = (DenseDoubleMatrix2D) temp.mtimes(temp.transpose());
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				matrix.setDouble(result.getDouble(r, c), r, c);
			}
		}
	}

	public double benchmarkInv(int run, long... size) {
		DoubleMatrix2D m = null;
		Matrix r = null;
		try {
			m = createMatrix(size);
			if (!m.getClass().getName().startsWith("org.ujmp.core")
					&& m.getClass().getDeclaredMethod("inv") == null) {
				System.out.print("-");
				System.out.flush();
				return NOTAVAILABLE;
			}

			rand(run, m);
			GCUtil.purgeMemory();
			long t0 = System.nanoTime();
			r = m.inv();
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

	public double benchmarkSVD(int run, long... size) {
		DoubleMatrix2D m = null;
		Matrix[] r = null;
		try {
			m = createMatrix(size);
			if (!m.getClass().getName().startsWith("org.ujmp.core")
					&& m.getClass().getDeclaredMethod("svd") == null) {
				System.out.print("-");
				System.out.flush();
				return NOTAVAILABLE;
			}

			rand(run, m);
			GCUtil.purgeMemory();
			long t0 = System.nanoTime();
			r = m.svd();
			long t1 = System.nanoTime();
			if (r == null) {
				System.out.print("e");
				System.out.flush();
				return ERRORTIME;
			}
			return (t1 - t0) / 1000000.0;
		} catch (NoSuchMethodException e) {
			System.out.print("-");
			System.out.flush();
			return NOTAVAILABLE;
		} catch (Throwable e) {
			System.out.print("e");
			System.out.flush();
			return ERRORTIME;
		}
	}

	public double benchmarkEig(int run, long... size) {
		DoubleMatrix2D m = null;
		Matrix[] r = null;
		try {
			m = createMatrix(size);
			if (!m.getClass().getName().startsWith("org.ujmp.core.")
					&& m.getClass().getDeclaredMethod("eig") == null) {
				System.out.print("-");
				System.out.flush();
				return NOTAVAILABLE;
			}

			randSymm(run, m);
			GCUtil.purgeMemory();
			long t0 = System.nanoTime();
			r = m.eig();
			long t1 = System.nanoTime();
			if (r == null) {
				System.out.print("e");
				System.out.flush();
				return ERRORTIME;
			}
			return (t1 - t0) / 1000000.0;
		} catch (NoSuchMethodException e) {
			System.out.print("-");
			System.out.flush();
			return NOTAVAILABLE;
		} catch (Throwable e) {
			System.out.print("e");
			System.out.flush();
			return ERRORTIME;
		}
	}

	public double benchmarkSolve(int run, long... size) {
		DoubleMatrix2D a = null;
		DoubleMatrix2D x = null;
		Matrix b = null;
		try {
			a = createMatrix(size);
			if (!a.getClass().getName().startsWith("org.ujmp.core.")
					&& a.getClass().getDeclaredMethod("solve", Matrix.class) == null) {
				System.out.print("-");
				System.out.flush();
				return NOTAVAILABLE;
			}

			x = createMatrix(size);
			rand(run, a);
			rand(run, x);
			Matrix m = new DefaultDenseDoubleMatrix2D(a).mtimes(new DefaultDenseDoubleMatrix2D(x));
			m = createMatrix(m);
			GCUtil.purgeMemory();
			long t0 = System.nanoTime();
			b = a.solve(m);
			long t1 = System.nanoTime();
			if (b == null) {
				System.out.print("e");
				System.out.flush();
				return ERRORTIME;
			}
			return (t1 - t0) / 1000000.0;
		} catch (NoSuchMethodException e) {
			System.out.print("-");
			System.out.flush();
			return NOTAVAILABLE;
		} catch (Throwable e) {
			System.out.print("e");
			System.out.flush();
			return ERRORTIME;
		}
	}

	public double benchmarkQR(int run, long... size) {
		DoubleMatrix2D m = null;
		Matrix[] r = null;
		try {
			m = createMatrix(size);
			if (!m.getClass().getName().startsWith("org.ujmp.core")
					&& m.getClass().getDeclaredMethod("qr") == null) {
				System.out.print("-");
				System.out.flush();
				return NOTAVAILABLE;
			}
			rand(run, m);
			GCUtil.purgeMemory();
			long t0 = System.nanoTime();
			r = m.qr();
			long t1 = System.nanoTime();
			if (r == null) {
				System.out.print("e");
				System.out.flush();
				return ERRORTIME;
			}
			return (t1 - t0) / 1000000.0;
		} catch (NoSuchMethodException e) {
			System.out.print("-");
			System.out.flush();
			return NOTAVAILABLE;
		} catch (Throwable e) {
			System.out.print("e");
			System.out.flush();
			return ERRORTIME;
		}
	}

	public double benchmarkChol(int run, long... size) {
		DoubleMatrix2D m = null;
		Matrix r = null;
		try {
			m = createMatrix(size);
			if (!m.getClass().getName().startsWith("org.ujmp.core")
					&& m.getClass().getDeclaredMethod("chol") == null) {
				System.out.print("-");
				System.out.flush();
				return NOTAVAILABLE;
			}
			randPositiveDefinite(run, m);
			GCUtil.purgeMemory();
			long t0 = System.nanoTime();
			r = m.chol();
			long t1 = System.nanoTime();
			if (r == null) {
				System.out.print("e");
				System.out.flush();
				return ERRORTIME;
			}
			return (t1 - t0) / 1000000.0;
		} catch (NoSuchMethodException e) {
			System.out.print("-");
			System.out.flush();
			return NOTAVAILABLE;
		} catch (Throwable e) {
			System.out.print("e");
			System.out.flush();
			return ERRORTIME;
		}
	}

	public double benchmarkLU(int run, long... size) {
		DoubleMatrix2D m = null;
		Matrix[] r = null;
		try {
			m = createMatrix(size);
			if (!m.getClass().getName().startsWith("org.ujmp.core")
					&& m.getClass().getDeclaredMethod("lu") == null) {
				System.out.print("-");
				System.out.flush();
				return NOTAVAILABLE;
			}

			rand(run, m);
			GCUtil.purgeMemory();
			long t0 = System.nanoTime();
			r = m.lu();
			long t1 = System.nanoTime();
			if (r == null) {
				System.out.print("e");
				System.out.flush();
				return ERRORTIME;
			}
			return (t1 - t0) / 1000000.0;
		} catch (NoSuchMethodException e) {
			System.out.print("-");
			System.out.flush();
			return NOTAVAILABLE;
		} catch (Throwable e) {
			System.out.print("e");
			System.out.flush();
			return ERRORTIME;
		}
	}

	public double benchmarkMtimesNew(int run, long[] size0, long[] size1) {
		DoubleMatrix2D m0 = null, m1 = null;
		Matrix r = null;
		try {
			m0 = createMatrix(size0);
			m1 = createMatrix(size1);
			if (!m0.getClass().getName().startsWith("org.ujmp.core")
					&& m0.getClass().getDeclaredMethod("mtimes", Matrix.class) == null) {
				System.out.print("-");
				System.out.flush();
				return NOTAVAILABLE;
			}

			rand(run, m0);
			rand(run * 999, m1);
			GCUtil.purgeMemory();
			long t0 = System.nanoTime();
			r = m0.mtimes(m1);
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

	public double benchmarkPlusMatrixNew(int run, long[] size0, long[] size1) {
		DoubleMatrix2D m0 = null, m1 = null;
		Matrix r = null;
		try {
			m0 = createMatrix(size0);
			m1 = createMatrix(size1);
			if (!m0.getClass().getName().startsWith("org.ujmp.core")
					&& m0.getClass().getDeclaredMethod("plus", Matrix.class) == null) {
				System.out.print("-");
				System.out.flush();
				return NOTAVAILABLE;
			}
			rand(run, m0);
			rand(run * 999, m1);
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

}
