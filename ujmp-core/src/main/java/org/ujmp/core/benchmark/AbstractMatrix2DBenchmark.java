/*
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
import java.net.Inet4Address;
import java.net.UnknownHostException;
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

	private static final double TOOLONG = 999999;

	private static final double NOTAVAILABLE = 0;

	private static final double ERRORTIME = Double.NaN;

	private final String transposeSizes = "2x2,3x3,4x4,5x5,6x6,7x7,8x8,9x9,10x10,20x20,30x30,40x40,50x50,60x60,70x70,80x80,90x90,100x100,200x200,300x300,400x400,500x500,600x600,700x700,800x800,900x900,1000x1000,2000x2000";

	private final String timesSizes = "2x2,3x3,4x4,5x5,6x6,7x7,8x8,9x9,10x10,20x20,30x30,40x40,50x50,60x60,70x70,80x80,90x90,100x100,200x200,300x300,400x400,500x500,600x600,700x700,800x800,900x900,1000x1000,2000x2000";

	private final String plusSizes = "2x2,3x3,4x4,5x5,6x6,7x7,8x8,9x9,10x10,20x20,30x30,40x40,50x50,60x60,70x70,80x80,90x90,100x100,200x200,300x300,400x400,500x500,600x600,700x700,800x800,900x900,1000x1000,2000x2000";

	private final String mtimesSizes = "2x2,3x3,4x4,5x5,6x6,7x7,8x8,9x9,10x10,20x20,30x30,40x40,50x50,60x60,70x70,80x80,90x90,100x100,200x200,300x300,400x400,500x500,600x600,700x700,800x800,900x900,1000x1000,2000x2000";

	private final String invSizes = "2x2,3x3,4x4,5x5,6x6,7x7,8x8,9x9,10x10,20x20,30x30,40x40,50x50,60x60,70x70,80x80,90x90,100x100,200x200,300x300,400x400,500x500,600x600,700x700,800x800,900x900,1000x1000,2000x2000";

	private final String solveSquareSizes = "2x2,3x3,4x4,5x5,6x6,7x7,8x8,9x9,10x10,20x20,30x30,40x40,50x50,60x60,70x70,80x80,90x90,100x100,200x200,300x300,400x400,500x500,600x600,700x700,800x800,900x900,1000x1000,2000x2000";

	private final String solveTallSizes = "3x2,4x3,5x4,6x5,7x6,8x7,9x8,10x9,11x10,21x20,31x30,41x40,51x50,61x60,71x70,81x80,91x90,101x100,201x200,301x300,401x400,501x500,601x600,701x700,801x800,901x900,1001x1000,2001x2000";

	private final String svdSizes = "2x2,3x3,4x4,5x5,6x6,7x7,8x8,9x9,10x10,20x20,30x30,40x40,50x50,60x60,70x70,80x80,90x90,100x100,200x200,300x300,400x400,500x500,1000x1000,2000x2000";

	private final String eigSizes = "2x2,3x3,4x4,5x5,6x6,7x7,8x8,9x9,10x10,20x20,30x30,40x40,50x50,60x60,70x70,80x80,90x90,100x100,200x200,300x300,400x400,500x500,1000x1000,2000x2000";

	private final String qrSizes = "2x2,3x3,4x4,5x5,6x6,7x7,8x8,9x9,10x10,20x20,30x30,40x40,50x50,60x60,70x70,80x80,90x90,100x100,200x200,300x300,400x400,500x500,600x600,700x700,800x800,900x900,1000x1000,2000x2000";

	private final String luSizes = "2x2,3x3,4x4,5x5,6x6,7x7,8x8,9x9,10x10,20x20,30x30,40x40,50x50,60x60,70x70,80x80,90x90,100x100,200x200,300x300,400x400,500x500,600x600,700x700,800x800,900x900,1000x1000,2000x2000";

	private final String cholSizes = "2x2,3x3,4x4,5x5,6x6,7x7,8x8,9x9,10x10,20x20,30x30,40x40,50x50,60x60,70x70,80x80,90x90,100x100,200x200,300x300,400x400,500x500,600x600,700x700,800x800,900x900,1000x1000,2000x2000";

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

	public boolean isSkipSlowLibraries() {
		return "true".equals(System.getProperty("skipSlowLibraries"));
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

	public void setSkipSlowLibraries(boolean b) {
		System.setProperty("skipSlowLibraries", "" + b);
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

	public void runAllTests() throws Exception {
		setRunAllTests();
		run();
	}

	public void setRunAllTests() throws Exception {
		setSkipSlowLibraries(true);
		setBurnInRuns(3);
		setRunsPerMatrix(10);

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
		File resultFile = new File(getResultDir() + getMatrixLabel() + "/" + test + ".csv");
		if (resultFile.exists()) {
			System.out.println("old results available, skipping " + test + " for "
					+ getMatrixLabel());
			return;
		}
		String[] sizes = getTransposeSizes().split(",");
		Matrix result = MatrixFactory.zeros(ValueType.STRING, getRunsPerMatrix(), sizes.length);
		result.setLabel(getMatrixLabel() + "-" + test);

		for (int s = 0; s < sizes.length; s++) {
			long[] size = Coordinates.parseString(sizes[s]);
			result.setColumnLabel(s, Coordinates.toString('x', size));
			System.out.print(test + " [" + Coordinates.toString('x', size) + "]: ");
			System.out.flush();

			for (int i = 0; i < getBurnInRuns(); i++) {
				benchmarkTransposeNew(i, size);
				System.out.print("#");
				System.out.flush();
			}
			for (int i = 0; i < getRunsPerMatrix(); i++) {
				double t = benchmarkTransposeNew(i, size);
				result.setAsDouble(t, i, s);
				System.out.print(".");
				System.out.flush();
			}

			Matrix mean = result.mean(Ret.NEW, Matrix.ROW, true);
			Matrix std = result.std(Ret.NEW, Matrix.ROW, true);
			mean.setLabel(mean.getLabel() + "-mean");
			std.setLabel(std.getLabel() + "-std");
			System.out.println(" " + mean.getAsInt(0, s) + "+-" + std.getAsInt(0, s) + "ms");
		}

		Matrix temp = MatrixFactory.vertCat(result.getAnnotation().getDimensionMatrix(Matrix.ROW),
				result);
		temp.exportToFile(FileFormat.CSV, resultFile);
	}

	public void runBenchmarkTimesScalarNew() throws Exception {
		String test = "timesScalarNew";
		File resultFile = new File(getResultDir() + getMatrixLabel() + "/" + test + ".csv");
		if (resultFile.exists()) {
			System.out.println("old results available, skipping " + test + " for "
					+ getMatrixLabel());
			return;
		}
		String[] sizes = getTimesSizes().split(",");
		Matrix result = MatrixFactory.zeros(ValueType.STRING, getRunsPerMatrix(), sizes.length);
		result.setLabel(getMatrixLabel() + "-" + test);

		for (int s = 0; s < sizes.length; s++) {
			long[] size = Coordinates.parseString(sizes[s]);
			result.setColumnLabel(s, Coordinates.toString('x', size));
			System.out.print(test + " [" + Coordinates.toString('x', size) + "]: ");
			System.out.flush();

			for (int i = 0; i < getBurnInRuns(); i++) {
				benchmarkTimesScalarNew(i, size);
				System.out.print("#");
				System.out.flush();
			}
			for (int i = 0; i < getRunsPerMatrix(); i++) {
				double t = benchmarkTimesScalarNew(i, size);
				result.setAsDouble(t, i, s);
				System.out.print(".");
				System.out.flush();
			}

			Matrix mean = result.mean(Ret.NEW, Matrix.ROW, true);
			Matrix std = result.std(Ret.NEW, Matrix.ROW, true);
			mean.setLabel(mean.getLabel() + "-mean");
			std.setLabel(std.getLabel() + "-std");
			System.out.println(" " + mean.getAsInt(0, s) + "+-" + std.getAsInt(0, s) + "ms");
		}

		Matrix temp = MatrixFactory.vertCat(result.getAnnotation().getDimensionMatrix(Matrix.ROW),
				result);
		temp.exportToFile(FileFormat.CSV, resultFile);
	}

	public void runBenchmarkPlusMatrixNew() throws Exception {
		String test = "plusMatrixNew";
		File resultFile = new File(getResultDir() + getMatrixLabel() + "/" + test + ".csv");
		if (resultFile.exists()) {
			System.out.println("old results available, skipping " + test + " for "
					+ getMatrixLabel());
			return;
		}
		String[] sizes = getTimesSizes().split(",");
		Matrix result = MatrixFactory.zeros(ValueType.STRING, getRunsPerMatrix(), sizes.length);
		result.setLabel(getMatrixLabel() + "-" + test);

		for (int s = 0; s < sizes.length; s++) {
			long[] size = Coordinates.parseString(sizes[s]);
			result.setColumnLabel(s, Coordinates.toString('x', size));
			System.out.print(test + " [" + Coordinates.toString('x', size) + "]: ");
			System.out.flush();

			for (int i = 0; i < getBurnInRuns(); i++) {
				benchmarkPlusMatrixNew(i, size, size);
				System.out.print("#");
				System.out.flush();
			}
			for (int i = 0; i < getRunsPerMatrix(); i++) {
				double t = benchmarkPlusMatrixNew(i, size, size);
				result.setAsDouble(t, i, s);
				System.out.print(".");
				System.out.flush();
			}

			Matrix mean = result.mean(Ret.NEW, Matrix.ROW, true);
			Matrix std = result.std(Ret.NEW, Matrix.ROW, true);
			mean.setLabel(mean.getLabel() + "-mean");
			std.setLabel(std.getLabel() + "-std");
			System.out.println(" " + mean.getAsInt(0, s) + "+-" + std.getAsInt(0, s) + "ms");
		}

		Matrix temp = MatrixFactory.vertCat(result.getAnnotation().getDimensionMatrix(Matrix.ROW),
				result);
		temp.exportToFile(FileFormat.CSV, resultFile);
	}

	public void runBenchmarkInv() throws Exception {
		String test = "inv";
		File resultFile = new File(getResultDir() + getMatrixLabel() + "/" + test + ".csv");
		if (resultFile.exists()) {
			System.out.println("old results available, skipping " + test + " for "
					+ getMatrixLabel());
			return;
		}
		String[] sizes = getInvSizes().split(",");
		Matrix result = MatrixFactory.zeros(ValueType.STRING, getRunsPerMatrix(), sizes.length);
		result.setLabel(getMatrixLabel() + "-" + test);

		for (int s = 0; s < sizes.length; s++) {
			long[] size = Coordinates.parseString(sizes[s]);
			result.setColumnLabel(s, Coordinates.toString('x', size));
			System.out.print(test + " [" + Coordinates.toString('x', size) + "]: ");
			System.out.flush();

			for (int i = 0; i < getBurnInRuns(); i++) {
				benchmarkInv(i, size);
				System.out.print("#");
				System.out.flush();
			}
			for (int i = 0; i < getRunsPerMatrix(); i++) {
				double t = benchmarkInv(i, size);
				result.setAsDouble(t, i, s);
				System.out.print(".");
				System.out.flush();
			}

			Matrix mean = result.mean(Ret.NEW, Matrix.ROW, true);
			Matrix std = result.std(Ret.NEW, Matrix.ROW, true);
			mean.setLabel(mean.getLabel() + "-mean");
			std.setLabel(std.getLabel() + "-std");
			System.out.println(" " + mean.getAsInt(0, s) + "+-" + std.getAsInt(0, s) + "ms");
		}

		Matrix temp = MatrixFactory.vertCat(result.getAnnotation().getDimensionMatrix(Matrix.ROW),
				result);
		temp.exportToFile(FileFormat.CSV, resultFile);
	}

	public void runBenchmarkSVD() throws Exception {
		String test = "svd";
		File resultFile = new File(getResultDir() + getMatrixLabel() + "/" + test + ".csv");
		if (resultFile.exists()) {
			System.out.println("old results available, skipping " + test + " for "
					+ getMatrixLabel());
			return;
		}
		String[] sizes = getSVDSizes().split(",");
		Matrix result = MatrixFactory.zeros(ValueType.STRING, getRunsPerMatrix(), sizes.length);
		result.setLabel(getMatrixLabel() + "-" + test);

		for (int s = 0; s < sizes.length; s++) {
			long[] size = Coordinates.parseString(sizes[s]);
			result.setColumnLabel(s, Coordinates.toString('x', size));
			System.out.print(test + " [" + Coordinates.toString('x', size) + "]: ");
			System.out.flush();

			for (int i = 0; i < getBurnInRuns(); i++) {
				benchmarkSVD(i, size);
				System.out.print("#");
				System.out.flush();
			}
			for (int i = 0; i < getRunsPerMatrix(); i++) {
				double t = benchmarkSVD(i, size);
				result.setAsDouble(t, i, s);
				System.out.print(".");
				System.out.flush();
			}

			Matrix mean = result.mean(Ret.NEW, Matrix.ROW, true);
			Matrix std = result.std(Ret.NEW, Matrix.ROW, true);
			mean.setLabel(mean.getLabel() + "-mean");
			std.setLabel(std.getLabel() + "-std");
			System.out.println(" " + mean.getAsInt(0, s) + "+-" + std.getAsInt(0, s) + "ms");
		}

		Matrix temp = MatrixFactory.vertCat(result.getAnnotation().getDimensionMatrix(Matrix.ROW),
				result);
		temp.exportToFile(FileFormat.CSV, resultFile);
	}

	public void runBenchmarkSolveSquare() throws Exception {
		String test = "solveSquare";
		File resultFile = new File(getResultDir() + getMatrixLabel() + "/" + test + ".csv");
		if (resultFile.exists()) {
			System.out.println("old results available, skipping " + test + " for "
					+ getMatrixLabel());
			return;
		}
		String[] sizes = getSolveSquareSizes().split(",");
		Matrix result = MatrixFactory.zeros(ValueType.STRING, getRunsPerMatrix(), sizes.length);
		result.setLabel(getMatrixLabel() + "-" + test);

		for (int s = 0; s < sizes.length; s++) {
			long[] size = Coordinates.parseString(sizes[s]);
			result.setColumnLabel(s, Coordinates.toString('x', size));
			System.out.print(test + " [" + Coordinates.toString('x', size) + "]: ");
			System.out.flush();

			for (int i = 0; i < getBurnInRuns(); i++) {
				benchmarkSolve(i, size);
				System.out.print("#");
				System.out.flush();
			}
			for (int i = 0; i < getRunsPerMatrix(); i++) {
				double t = benchmarkSolve(i, size);
				result.setAsDouble(t, i, s);
				System.out.print(".");
				System.out.flush();
			}

			Matrix mean = result.mean(Ret.NEW, Matrix.ROW, true);
			Matrix std = result.std(Ret.NEW, Matrix.ROW, true);
			mean.setLabel(mean.getLabel() + "-mean");
			std.setLabel(std.getLabel() + "-std");
			System.out.println(" " + mean.getAsInt(0, s) + "+-" + std.getAsInt(0, s) + "ms");
		}

		Matrix temp = MatrixFactory.vertCat(result.getAnnotation().getDimensionMatrix(Matrix.ROW),
				result);
		temp.exportToFile(FileFormat.CSV, resultFile);
	}

	public void runBenchmarkSolveTall() throws Exception {
		String test = "solveTall";
		File resultFile = new File(getResultDir() + getMatrixLabel() + "/" + test + ".csv");
		if (resultFile.exists()) {
			System.out.println("old results available, skipping " + test + " for "
					+ getMatrixLabel());
			return;
		}
		String[] sizes = getSolveTallSizes().split(",");
		Matrix result = MatrixFactory.zeros(ValueType.STRING, getRunsPerMatrix(), sizes.length);
		result.setLabel(getMatrixLabel() + "-" + test);

		for (int s = 0; s < sizes.length; s++) {
			long[] size = Coordinates.parseString(sizes[s]);
			result.setColumnLabel(s, Coordinates.toString('x', size));
			System.out.print(test + " [" + Coordinates.toString('x', size) + "]: ");
			System.out.flush();

			for (int i = 0; i < getBurnInRuns(); i++) {
				benchmarkSolve(i, size);
				System.out.print("#");
				System.out.flush();
			}
			for (int i = 0; i < getRunsPerMatrix(); i++) {
				double t = benchmarkSolve(i, size);
				result.setAsDouble(t, i, s);
				System.out.print(".");
				System.out.flush();
			}

			Matrix mean = result.mean(Ret.NEW, Matrix.ROW, true);
			Matrix std = result.std(Ret.NEW, Matrix.ROW, true);
			mean.setLabel(mean.getLabel() + "-mean");
			std.setLabel(std.getLabel() + "-std");
			System.out.println(" " + mean.getAsInt(0, s) + "+-" + std.getAsInt(0, s) + "ms");
		}

		Matrix temp = MatrixFactory.vertCat(result.getAnnotation().getDimensionMatrix(Matrix.ROW),
				result);
		temp.exportToFile(FileFormat.CSV, resultFile);
	}

	public void runBenchmarkQR() throws Exception {
		String test = "qr";
		File resultFile = new File(getResultDir() + getMatrixLabel() + "/" + test + ".csv");
		if (resultFile.exists()) {
			System.out.println("old results available, skipping " + test + " for "
					+ getMatrixLabel());
			return;
		}
		String[] sizes = getQRSizes().split(",");
		Matrix result = MatrixFactory.zeros(ValueType.STRING, getRunsPerMatrix(), sizes.length);
		result.setLabel(getMatrixLabel() + "-" + test);

		for (int s = 0; s < sizes.length; s++) {
			long[] size = Coordinates.parseString(sizes[s]);
			result.setColumnLabel(s, Coordinates.toString('x', size));
			System.out.print(test + " [" + Coordinates.toString('x', size) + "]: ");
			System.out.flush();

			for (int i = 0; i < getBurnInRuns(); i++) {
				benchmarkQR(i, size);
				System.out.print("#");
				System.out.flush();
			}
			for (int i = 0; i < getRunsPerMatrix(); i++) {
				double t = benchmarkQR(i, size);
				result.setAsDouble(t, i, s);
				System.out.print(".");
				System.out.flush();
			}

			Matrix mean = result.mean(Ret.NEW, Matrix.ROW, true);
			Matrix std = result.std(Ret.NEW, Matrix.ROW, true);
			mean.setLabel(mean.getLabel() + "-mean");
			std.setLabel(std.getLabel() + "-std");
			System.out.println(" " + mean.getAsInt(0, s) + "+-" + std.getAsInt(0, s) + "ms");
		}

		Matrix temp = MatrixFactory.vertCat(result.getAnnotation().getDimensionMatrix(Matrix.ROW),
				result);
		temp.exportToFile(FileFormat.CSV, resultFile);
	}

	public void runBenchmarkLU() throws Exception {
		String test = "lu";
		File resultFile = new File(getResultDir() + getMatrixLabel() + "/" + test + ".csv");
		if (resultFile.exists()) {
			System.out.println("old results available, skipping " + test + " for "
					+ getMatrixLabel());
			return;
		}
		String[] sizes = getLUSizes().split(",");
		Matrix result = MatrixFactory.zeros(ValueType.STRING, getRunsPerMatrix(), sizes.length);
		result.setLabel(getMatrixLabel() + "-" + test);

		for (int s = 0; s < sizes.length; s++) {
			long[] size = Coordinates.parseString(sizes[s]);
			result.setColumnLabel(s, Coordinates.toString('x', size));
			System.out.print(test + " [" + Coordinates.toString('x', size) + "]: ");
			System.out.flush();

			for (int i = 0; i < getBurnInRuns(); i++) {
				benchmarkLU(i, size);
				System.out.print("#");
				System.out.flush();
			}
			for (int i = 0; i < getRunsPerMatrix(); i++) {
				double t = benchmarkLU(i, size);
				result.setAsDouble(t, i, s);
				System.out.print(".");
				System.out.flush();
			}

			Matrix mean = result.mean(Ret.NEW, Matrix.ROW, true);
			Matrix std = result.std(Ret.NEW, Matrix.ROW, true);
			mean.setLabel(mean.getLabel() + "-mean");
			std.setLabel(std.getLabel() + "-std");
			System.out.println(" " + mean.getAsInt(0, s) + "+-" + std.getAsInt(0, s) + "ms");
		}

		Matrix temp = MatrixFactory.vertCat(result.getAnnotation().getDimensionMatrix(Matrix.ROW),
				result);
		temp.exportToFile(FileFormat.CSV, resultFile);
	}

	public void runBenchmarkEig() throws Exception {
		String test = "eig";
		File resultFile = new File(getResultDir() + getMatrixLabel() + "/" + test + ".csv");
		if (resultFile.exists()) {
			System.out.println("old results available, skipping " + test + " for "
					+ getMatrixLabel());
			return;
		}
		String[] sizes = getEigSizes().split(",");
		Matrix result = MatrixFactory.zeros(ValueType.STRING, getRunsPerMatrix(), sizes.length);
		result.setLabel(getMatrixLabel() + "-" + test);

		for (int s = 0; s < sizes.length; s++) {
			long[] size = Coordinates.parseString(sizes[s]);
			result.setColumnLabel(s, Coordinates.toString('x', size));
			System.out.print(test + " [" + Coordinates.toString('x', size) + "]: ");
			System.out.flush();

			for (int i = 0; i < getBurnInRuns(); i++) {
				benchmarkEig(i, size);
				System.out.print("#");
				System.out.flush();
			}
			for (int i = 0; i < getRunsPerMatrix(); i++) {
				double t = benchmarkEig(i, size);
				result.setAsDouble(t, i, s);
				System.out.print(".");
				System.out.flush();
			}

			Matrix mean = result.mean(Ret.NEW, Matrix.ROW, true);
			Matrix std = result.std(Ret.NEW, Matrix.ROW, true);
			mean.setLabel(mean.getLabel() + "-mean");
			std.setLabel(std.getLabel() + "-std");
			System.out.println(" " + mean.getAsInt(0, s) + "+-" + std.getAsInt(0, s) + "ms");
		}

		Matrix temp = MatrixFactory.vertCat(result.getAnnotation().getDimensionMatrix(Matrix.ROW),
				result);
		temp.exportToFile(FileFormat.CSV, resultFile);
	}

	public void runBenchmarkChol() throws Exception {
		String test = "chol";
		File resultFile = new File(getResultDir() + getMatrixLabel() + "/" + test + ".csv");
		if (resultFile.exists()) {
			System.out.println("old results available, skipping " + test + " for "
					+ getMatrixLabel());
			return;
		}
		String[] sizes = getCholSizes().split(",");
		Matrix result = MatrixFactory.zeros(ValueType.STRING, getRunsPerMatrix(), sizes.length);
		result.setLabel(getMatrixLabel() + "-" + test);

		for (int s = 0; s < sizes.length; s++) {
			long[] size = Coordinates.parseString(sizes[s]);
			result.setColumnLabel(s, Coordinates.toString('x', size));
			System.out.print(test + " [" + Coordinates.toString('x', size) + "]: ");
			System.out.flush();

			for (int i = 0; i < getBurnInRuns(); i++) {
				benchmarkChol(i, size);
				System.out.print("#");
				System.out.flush();
			}
			for (int i = 0; i < getRunsPerMatrix(); i++) {
				double t = benchmarkChol(i, size);
				result.setAsDouble(t, i, s);
				System.out.print(".");
				System.out.flush();
			}

			Matrix mean = result.mean(Ret.NEW, Matrix.ROW, true);
			Matrix std = result.std(Ret.NEW, Matrix.ROW, true);
			mean.setLabel(mean.getLabel() + "-mean");
			std.setLabel(std.getLabel() + "-std");
			System.out.println(" " + mean.getAsInt(0, s) + "+-" + std.getAsInt(0, s) + "ms");
		}

		Matrix temp = MatrixFactory.vertCat(result.getAnnotation().getDimensionMatrix(Matrix.ROW),
				result);
		temp.exportToFile(FileFormat.CSV, resultFile);
	}

	public String getResultDir() throws UnknownHostException {
		return "results/" + getHostName() + "/" + System.getProperty("os.name") + "/Java"
				+ System.getProperty("java.version") + "/";
	}

	public String getHostName() {
		try {
			return Inet4Address.getLocalHost().getHostName();
		} catch (Exception e) {
			return "localhost";
		}
	}

	public void runBenchmarkMtimesNew() throws Exception {
		String test = "mtimesNew";
		File resultFile = new File(getResultDir() + getMatrixLabel() + "/" + test + ".csv");
		if (resultFile.exists()) {
			System.out.println("old results available, skipping " + test + " for "
					+ getMatrixLabel());
			return;
		}
		String[] sizes = getMtimesSizes().split(",");
		Matrix result = MatrixFactory.zeros(ValueType.STRING, getRunsPerMatrix(), sizes.length);
		result.setLabel(getMatrixLabel() + "-" + test);

		for (int s = 0; s < sizes.length; s++) {
			long[] size = Coordinates.parseString(sizes[s]);
			result.setColumnLabel(s, Coordinates.toString('x', size));
			System.out.print(test + " [" + Coordinates.toString('x', size) + "]: ");
			System.out.flush();

			for (int i = 0; i < getBurnInRuns(); i++) {
				benchmarkMtimesNew(i, size, size);
				System.out.print("#");
				System.out.flush();
			}
			for (int i = 0; i < getRunsPerMatrix(); i++) {
				double t = benchmarkMtimesNew(i, size, size);
				result.setAsDouble(t, i, s);
				System.out.print(".");
				System.out.flush();
			}

			Matrix mean = result.mean(Ret.NEW, Matrix.ROW, true);
			Matrix std = result.std(Ret.NEW, Matrix.ROW, true);
			mean.setLabel(mean.getLabel() + "-mean");
			std.setLabel(std.getLabel() + "-std");
			System.out.println(" " + mean.getAsInt(0, s) + "+-" + std.getAsInt(0, s) + "ms");
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
				System.err.print("-");
				System.err.flush();
				return NOTAVAILABLE;
			}
			GCUtil.gc();
			long t0 = System.nanoTime();
			r = m.copy();
			long t1 = System.nanoTime();
			if (r == null) {
				System.err.print("e");
				System.err.flush();
				return ERRORTIME;
			}
			return (t1 - t0) / 1000000.0;
		} catch (Throwable e) {
			System.err.print("e");
			System.err.flush();
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
				System.err.print("-");
				System.err.flush();
				return NOTAVAILABLE;
			}
			rand(run, m);
			GCUtil.gc();
			long t0 = System.nanoTime();
			r = m.transpose();
			long t1 = System.nanoTime();
			if (r == null) {
				System.err.print("e");
				System.err.flush();
				return ERRORTIME;
			}
			return (t1 - t0) / 1000000.0;
		} catch (Throwable e) {
			System.err.print("e");
			System.err.flush();
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
				System.err.print("-");
				System.err.flush();
				return NOTAVAILABLE;
			}
			rand(run, m);
			double a = MathUtil.nextDouble();
			GCUtil.gc();
			long t0 = System.nanoTime();
			r = m.times(a);
			long t1 = System.nanoTime();
			if (r == null) {
				System.err.print("e");
				System.err.flush();
				return ERRORTIME;
			}
			return (t1 - t0) / 1000000.0;
		} catch (Throwable e) {
			System.err.print("e");
			System.err.flush();
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
				System.err.print("-");
				System.err.flush();
				return NOTAVAILABLE;
			}
			if (isSkipSlowLibraries() && m.getClass().getName().startsWith("org.ujmp.orbital.")
					&& Coordinates.product(size) > 40000) {
				System.err.print("skip ");
				System.err.flush();
				return TOOLONG;
			}
			if (isSkipSlowLibraries() && m.getClass().getName().startsWith("org.ujmp.jlinalg.")
					&& Coordinates.product(size) > 160000) {
				System.err.print("skip ");
				System.err.flush();
				return TOOLONG;
			}
			if (isSkipSlowLibraries() && m.getClass().getName().startsWith("org.ujmp.jscience.")
					&& Coordinates.product(size) > 160000) {
				System.err.print("skip ");
				System.err.flush();
				return TOOLONG;
			}
			if (isSkipSlowLibraries() && m.getClass().getName().startsWith("org.ujmp.vecmath.")
					&& Coordinates.product(size) > 1000000) {
				System.err.print("skip ");
				System.err.flush();
				return TOOLONG;
			}

			rand(run, m);
			GCUtil.gc();
			long t0 = System.nanoTime();
			r = m.inv();
			long t1 = System.nanoTime();
			if (r == null) {
				System.err.print("e");
				System.err.flush();
				return ERRORTIME;
			}
			return (t1 - t0) / 1000000.0;
		} catch (Throwable e) {
			System.err.print("e");
			System.err.flush();
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
				System.err.print("-");
				System.err.flush();
				return NOTAVAILABLE;
			}
			if (isSkipSlowLibraries() && m.getClass().getName().startsWith("org.ujmp.vecmath.")
					&& Coordinates.product(size) > 40000) {
				System.err.print("skip ");
				System.err.flush();
				return TOOLONG;
			}

			rand(run, m);
			GCUtil.gc();
			long t0 = System.nanoTime();
			r = m.svd();
			long t1 = System.nanoTime();
			if (r == null) {
				System.err.print("e");
				System.err.flush();
				return ERRORTIME;
			}
			return (t1 - t0) / 1000000.0;
		} catch (NoSuchMethodException e) {
			System.err.print("-");
			System.err.flush();
			return NOTAVAILABLE;
		} catch (Throwable e) {
			System.err.print("e");
			System.err.flush();
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
				System.err.print("-");
				System.err.flush();
				return NOTAVAILABLE;
			}
			randSymm(run, m);
			GCUtil.gc();
			long t0 = System.nanoTime();
			r = m.eig();
			long t1 = System.nanoTime();
			if (r == null) {
				System.err.print("e");
				System.err.flush();
				return ERRORTIME;
			}
			return (t1 - t0) / 1000000.0;
		} catch (NoSuchMethodException e) {
			System.err.print("-");
			System.err.flush();
			return NOTAVAILABLE;
		} catch (Throwable e) {
			System.err.print("e");
			System.err.flush();
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
				System.err.print("-");
				System.err.flush();
				return NOTAVAILABLE;
			}
			if (isSkipSlowLibraries() && a.getClass().getName().startsWith("org.ujmp.orbital.")
					&& Coordinates.product(size) > 40000) {
				System.err.print("skip ");
				System.err.flush();
				return TOOLONG;
			}
			if (isSkipSlowLibraries() && a.getClass().getName().startsWith("org.ujmp.jlinalg.")
					&& Coordinates.product(size) > 160000) {
				System.err.print("skip ");
				System.err.flush();
				return TOOLONG;
			}
			if (isSkipSlowLibraries() && a.getClass().getName().startsWith("org.ujmp.jscience.")
					&& Coordinates.product(size) > 160000) {
				System.err.print("skip ");
				System.err.flush();
				return TOOLONG;
			}
			x = createMatrix(size);
			rand(run, a);
			rand(run, x);
			Matrix m = new DefaultDenseDoubleMatrix2D(a).mtimes(new DefaultDenseDoubleMatrix2D(x));
			m = createMatrix(m);
			GCUtil.gc();
			long t0 = System.nanoTime();
			b = a.solve(m);
			long t1 = System.nanoTime();
			if (b == null) {
				System.err.print("e");
				System.err.flush();
				return ERRORTIME;
			}
			return (t1 - t0) / 1000000.0;
		} catch (NoSuchMethodException e) {
			System.err.print("-");
			System.err.flush();
			return NOTAVAILABLE;
		} catch (Throwable e) {
			System.err.print("e");
			System.err.flush();
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
				System.err.print("-");
				System.err.flush();
				return NOTAVAILABLE;
			}
			rand(run, m);
			GCUtil.gc();
			long t0 = System.nanoTime();
			r = m.qr();
			long t1 = System.nanoTime();
			if (r == null) {
				System.err.print("e");
				System.err.flush();
				return ERRORTIME;
			}
			return (t1 - t0) / 1000000.0;
		} catch (NoSuchMethodException e) {
			System.err.print("-");
			System.err.flush();
			return NOTAVAILABLE;
		} catch (Throwable e) {
			System.err.print("e");
			System.err.flush();
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
				System.err.print("-");
				System.err.flush();
				return NOTAVAILABLE;
			}
			randPositiveDefinite(run, m);
			GCUtil.gc();
			long t0 = System.nanoTime();
			r = m.chol();
			long t1 = System.nanoTime();
			if (r == null) {
				System.err.print("e");
				System.err.flush();
				return ERRORTIME;
			}
			return (t1 - t0) / 1000000.0;
		} catch (NoSuchMethodException e) {
			System.err.print("-");
			System.err.flush();
			return NOTAVAILABLE;
		} catch (Throwable e) {
			System.err.print("e");
			System.err.flush();
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
				System.err.print("-");
				System.err.flush();
				return NOTAVAILABLE;
			}
			if (isSkipSlowLibraries() && m.getClass().getName().startsWith("org.ujmp.orbital.")
					&& Coordinates.product(size) > 160000) {
				System.err.print("skip ");
				System.err.flush();
				return TOOLONG;
			}
			if (isSkipSlowLibraries() && m.getClass().getName().startsWith("org.ujmp.jscience.")
					&& Coordinates.product(size) > 250000) {
				System.err.print("skip ");
				System.err.flush();
				return TOOLONG;
			}

			rand(run, m);
			GCUtil.gc();
			long t0 = System.nanoTime();
			r = m.lu();
			long t1 = System.nanoTime();
			if (r == null) {
				System.err.print("e");
				System.err.flush();
				return ERRORTIME;
			}
			return (t1 - t0) / 1000000.0;
		} catch (NoSuchMethodException e) {
			System.err.print("-");
			System.err.flush();
			return NOTAVAILABLE;
		} catch (Throwable e) {
			System.err.print("e");
			System.err.flush();
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
				System.err.print("-");
				System.err.flush();
				return NOTAVAILABLE;
			}
			if (isSkipSlowLibraries() && m0.getClass().getName().startsWith("org.ujmp.sst.")
					&& Coordinates.product(size0) > 400000) {
				System.err.print("skip ");
				System.err.flush();
				return TOOLONG;
			}
			if (isSkipSlowLibraries() && m0.getClass().getName().startsWith("org.ujmp.owlpack.")
					&& Coordinates.product(size0) > 500000) {
				System.err.print("skip ");
				System.err.flush();
				return TOOLONG;
			}
			if (isSkipSlowLibraries() && m0.getClass().getName().startsWith("org.ujmp.orbital.")
					&& Coordinates.product(size0) > 100000) {
				System.err.print("skip ");
				System.err.flush();
				return TOOLONG;
			}
			if (isSkipSlowLibraries() && m0.getClass().getName().startsWith("org.ujmp.mantissa.")
					&& Coordinates.product(size0) > 900000) {
				System.err.print("skip ");
				System.err.flush();
				return TOOLONG;
			}
			if (isSkipSlowLibraries() && m0.getClass().getName().startsWith("org.ujmp.jsci.")
					&& Coordinates.product(size0) > 900000) {
				System.err.print("skip ");
				System.err.flush();
				return TOOLONG;
			}
			if (isSkipSlowLibraries() && m0.getClass().getName().startsWith("org.ujmp.jmatrices.")
					&& Coordinates.product(size0) > 40000) {
				System.err.print("skip ");
				System.err.flush();
				return TOOLONG;
			}
			if (isSkipSlowLibraries() && m0.getClass().getName().startsWith("org.ujmp.jlinalg.")
					&& Coordinates.product(size0) > 200000) {
				System.err.print("skip ");
				System.err.flush();
				return TOOLONG;
			}
			if (isSkipSlowLibraries() && m0.getClass().getName().startsWith("org.ujmp.vecmath.")
					&& Coordinates.product(size0) > 800 * 800) {
				System.err.print("skip ");
				System.err.flush();
				return TOOLONG;
			}

			rand(run, m0);
			rand(run * 999, m1);
			GCUtil.gc();
			long t0 = System.nanoTime();
			r = m0.mtimes(m1);
			long t1 = System.nanoTime();
			if (r == null) {
				System.err.print("e");
				System.err.flush();
				return ERRORTIME;
			}
			return (t1 - t0) / 1000000.0;
		} catch (Throwable e) {
			System.err.print("e");
			System.err.flush();
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
				System.err.print("-");
				System.err.flush();
				return NOTAVAILABLE;
			}
			rand(run, m0);
			rand(run * 999, m1);
			GCUtil.gc();
			long t0 = System.nanoTime();
			r = m0.plus(m1);
			long t1 = System.nanoTime();
			if (r == null) {
				System.err.print("e");
				System.err.flush();
				return ERRORTIME;
			}
			return (t1 - t0) / 1000000.0;
		} catch (Throwable e) {
			System.err.print("e");
			System.err.flush();
			return ERRORTIME;
		}
	}

}
