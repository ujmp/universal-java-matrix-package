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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.GCUtil;
import org.ujmp.core.util.MathUtil;

public abstract class AbstractMatrix2DBenchmark {

	public static final double TOOLONG = 999999;

	public static final double NOTAVAILABLE = 0;

	public static final double ERRORTIME = Double.NaN;

	public static final long[] SIZE100X100 = new long[] { 100, 100 };

	public static final long[] SIZE100X1000 = new long[] { 100, 1000 };

	public static final long[] SIZE100X10000 = new long[] { 100, 10000 };

	public static final long[] SIZE100X100000 = new long[] { 100, 100000 };

	public static final long[] SIZE200X200 = new long[] { 200, 200 };

	public static final long[] SIZE500X500 = new long[] { 500, 500 };

	public static final long[] SIZE1000X100 = new long[] { 1000, 100 };

	public static final long[] SIZE1000X1000 = new long[] { 1000, 1000 };

	public static final long[] SIZE2000X2000 = new long[] { 2000, 2000 };

	public static final long[] SIZE1000X10000 = new long[] { 1000, 10000 };

	public static final long[] SIZE1000X100000 = new long[] { 1000, 100000 };

	public static final long[] SIZE10000X100 = new long[] { 10000, 100 };

	public static final long[] SIZE10000X1000 = new long[] { 10000, 1000 };

	public static final long[] SIZE5000X5000 = new long[] { 5000, 5000 };

	public static final long[] SIZE10000X10000 = new long[] { 10000, 10000 };

	public static final long[] SIZE10000X100000 = new long[] { 10000, 100000 };

	public static final long[] SIZE100000X100 = new long[] { 100000, 100 };

	public static final long[] SIZE100000X1000 = new long[] { 100000, 1000 };

	public static final long[] SIZE100000X10000 = new long[] { 100000, 10000 };

	public static final long[] SIZE100000X100000 = new long[] { 100000, 100000 };

	private final List<long[]> transposeSizes = Arrays.asList(new long[][] { SIZE2000X2000 });

	private final List<long[]> copySizes = Arrays.asList(new long[][] { SIZE5000X5000 });

	private final List<long[]> initSizes = Arrays.asList(new long[][] { SIZE5000X5000 });

	private final List<long[]> createSizes = Arrays.asList(new long[][] { SIZE5000X5000 });

	private final List<long[]> plusSizes = Arrays.asList(new long[][] { SIZE5000X5000 });

	private final List<long[]> timesSizes = Arrays.asList(new long[][] { SIZE5000X5000 });

	private final List<long[]> invSizes = Arrays.asList(new long[][] { SIZE500X500 });

	private final List<long[]> svdSizes = Arrays.asList(new long[][] { SIZE200X200 });

	private final List<long[]> evdSizes = Arrays.asList(new long[][] { SIZE200X200 });

	private final List<long[]> qrSizes = Arrays.asList(new long[][] { SIZE500X500 });

	private final List<long[]> cholSizes = Arrays.asList(new long[][] { SIZE1000X1000 });

	private final List<long[]> luSizes = Arrays.asList(new long[][] { SIZE1000X1000 });

	private final List<long[][]> mtimesSizes = Arrays.asList(new long[][][] { { SIZE500X500,
			SIZE500X500 } });

	public abstract Matrix createMatrix(long... size) throws MatrixException;

	public abstract Matrix createMatrix(Matrix source) throws MatrixException;

	public AbstractMatrix2DBenchmark() {
	}

	public static void setRunsPerMatrix(int runs) {
		System.setProperty("runsPerMatrix", "" + runs);
	}

	public static int getRunsPerMatrix() {
		return MathUtil.getInt(System.getProperty("runsPerMatrix"));
	}

	public static void setBurnInRuns(int runs) {
		System.setProperty("burnInRuns", "" + runs);
	}

	public static int getBurnInRuns() {
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

	public boolean isRunPlusScalarNew() {
		return "true".equals(System.getProperty("runPlusScalarNew"));
	}

	public void setRunPlusScalarNew(boolean runPlusScalarNew) {
		System.setProperty("runPlusScalarNew", "" + runPlusScalarNew);
	}

	public boolean isRunPlusScalarOrig() {
		return "true".equals(System.getProperty("runPlusScalarOrig"));
	}

	public void setRunPlusScalarOrig(boolean runPlusScalarOrig) {
		System.setProperty("runPlusScalarOrig", "" + runPlusScalarOrig);
	}

	public boolean isRunTimesScalarNew() {
		return "true".equals(System.getProperty("runTimesScalarNew"));
	}

	public void setRunTimesScalarNew(boolean runTimesScalarNew) {
		System.setProperty("runTimesScalarNew", "" + runTimesScalarNew);
	}

	public boolean isRunTimesScalarOrig() {
		return "true".equals(System.getProperty("runTimesScalarOrig"));
	}

	public void setRunTimesScalarOrig(boolean runTimesScalarOrig) {
		System.setProperty("runTimesScalarOrig", "" + runTimesScalarOrig);
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

	public boolean isSkipSlowLibraries() {
		return "true".equals(System.getProperty("skipSlowLibraries"));
	}

	public boolean isRunSVD() {
		return "true".equals(System.getProperty("runSVD"));
	}

	public boolean isRunChol() {
		return "true".equals(System.getProperty("runChol"));
	}

	public boolean isRunEVD() {
		return "true".equals(System.getProperty("runEVD"));
	}

	public boolean isRunQR() {
		return "true".equals(System.getProperty("runQR"));
	}

	public boolean isRunLU() {
		return "true".equals(System.getProperty("runLU"));
	}

	public static void setRunTransposeNew(boolean b) {
		System.setProperty("runTransposeNew", "" + b);
	}

	public static void setRunMtimesNew(boolean b) {
		System.setProperty("runMtimesNew", "" + b);
	}

	public static void setRunInv(boolean b) {
		System.setProperty("runInv", "" + b);
	}

	public static void setRunSVD(boolean b) {
		System.setProperty("runSVD", "" + b);
	}

	public static void setRunQR(boolean b) {
		System.setProperty("runQR", "" + b);
	}

	public static void setRunEVD(boolean b) {
		System.setProperty("runEVD", "" + b);
	}

	public static void setSkipSlowLibraries(boolean b) {
		System.setProperty("skipSlowLibraries", "" + b);
	}

	public static void setRunLU(boolean b) {
		System.setProperty("runLU", "" + b);
	}

	public static void setRunChol(boolean b) {
		System.setProperty("runChol", "" + b);
	}

	public boolean isRunTransposeOrig() {
		return "true".equals(System.getProperty("runTransposeOrig"));
	}

	public void setRunTransposeOrig(boolean runTransposeOrig) {
		System.setProperty("runTransposeOrig", "" + runTransposeOrig);
	}

	public List<long[]> getTransposeSizes() {
		return transposeSizes;
	}

	public List<long[]> getCopySizes() {
		return copySizes;
	}

	public List<long[]> getInitSizes() {
		return initSizes;
	}

	public List<long[]> getCreateSizes() {
		return createSizes;
	}

	public List<long[]> getPlusSizes() {
		return plusSizes;
	}

	public List<long[]> getTimesSizes() {
		return timesSizes;
	}

	public List<long[]> getInvSizes() {
		return invSizes;
	}

	public List<long[][]> getMtimesSizes() {
		return mtimesSizes;
	}

	public List<Matrix> run() throws Exception {
		List<Matrix> result = new ArrayList<Matrix>();

		try {

			System.out.println("===============================================================");
			System.out.println(createMatrix(1, 1).getClass().getSimpleName());
			System.out.println("===============================================================");

			if (isRunInit()) {
				init();
			}

			if (isRunCreate()) {
				result.add(runBenchmarkCreate());
			}

			if (isRunCopy()) {
				result.add(runBenchmarkCopy());
			}

			if (isRunPlusScalarNew()) {
				result.add(runBenchmarkPlusScalarNew());
			}

			if (isRunPlusScalarOrig()) {
				result.add(runBenchmarkPlusScalarOrig());
			}

			if (isRunTimesScalarNew()) {
				result.add(runBenchmarkTimesScalarNew());
			}

			if (isRunTimesScalarOrig()) {
				result.add(runBenchmarkTimesScalarOrig());
			}

			if (isRunTransposeNew()) {
				Matrix[] r = runBenchmarkTransposeNew();
				result.add(r[0]);
				result.add(r[1]);
			}

			if (isRunTransposeOrig()) {
				result.add(runBenchmarkTransposeOrig());
			}

			if (isRunMtimesNew()) {
				Matrix[] r = runBenchmarkMtimesNew();
				result.add(r[0]);
				result.add(r[1]);
			}

			if (isRunInv()) {
				Matrix[] r = runBenchmarkInv();
				result.add(r[0]);
				result.add(r[1]);
			}

			if (isRunSVD()) {
				Matrix[] r = runBenchmarkSVD();
				result.add(r[0]);
				result.add(r[1]);
			}

			if (isRunEVD()) {
				Matrix[] r = runBenchmarkEVD();
				result.add(r[0]);
				result.add(r[1]);
			}

			if (isRunQR()) {
				Matrix[] r = runBenchmarkQR();
				result.add(r[0]);
				result.add(r[1]);
			}

			if (isRunLU()) {
				Matrix[] r = runBenchmarkLU();
				result.add(r[0]);
				result.add(r[1]);
			}

			if (isRunChol()) {
				Matrix[] r = runBenchmarkChol();
				result.add(r[0]);
				result.add(r[1]);
			}

			System.out.println();

		} catch (UnsupportedClassVersionError e) {
			System.out.println("this library is not compatible with the current Java version");
			System.out.println("it cannot be included in the benchmark");
			System.out.println();
		} catch (Exception e) {
			System.out.println("there was some error with this library");
			System.out.println("it cannot be included in the benchmark");
			System.out.println();
		}

		return result;
	}

	public Matrix init() {
		Matrix result = MatrixFactory.zeros(getRunsPerMatrix(), initSizes.size());
		System.out.print("init: ");
		System.out.flush();

		for (int i = 0; i < getRunsPerMatrix(); i++) {
			for (int s = 0; s < initSizes.size(); s++) {
				long[] size = initSizes.get(s);
				double t = benchmarkCreate(size);
				result.setAsDouble(t, i, s);
				System.out.print(".");
				System.out.flush();
			}
		}

		System.out.println();
		Matrix m = result.mean(Ret.NEW, Matrix.ROW, true);
		m.setLabel("init");
		return m;
	}

	public Matrix runBenchmarkCreate() {
		Matrix result = MatrixFactory.zeros(getRunsPerMatrix(), createSizes.size());
		System.out.print("create: ");
		System.out.flush();

		for (int i = 0; i < getRunsPerMatrix(); i++) {
			for (int s = 0; s < createSizes.size(); s++) {
				long[] size = createSizes.get(s);
				double t = benchmarkCreate(size);
				result.setAsDouble(t, i, s);
				System.out.print(".");
				System.out.flush();
			}
		}

		System.out.println();
		Matrix m = result.mean(Ret.NEW, Matrix.ROW, true);
		m.setLabel("create");
		return m;
	}

	public Matrix runBenchmarkPlusScalarNew() {
		Matrix result = MatrixFactory.zeros(getRunsPerMatrix(), plusSizes.size());
		System.out.print("plus scalar (new matrix): ");
		System.out.flush();

		for (int i = 0; i < getRunsPerMatrix(); i++) {
			for (int s = 0; s < plusSizes.size(); s++) {
				long[] size = plusSizes.get(s);
				double t = benchmarkPlusScalarNew(size);
				result.setAsDouble(t, i, s);
				System.out.print(".");
				System.out.flush();
			}
		}

		System.out.println();
		Matrix m = result.mean(Ret.NEW, Matrix.ROW, true);
		m.setLabel("plus scalar (new matrix)");
		return m;
	}

	public Matrix runBenchmarkCopy() {
		Matrix result = MatrixFactory.zeros(getRunsPerMatrix(), copySizes.size());
		System.out.print("copy: ");
		System.out.flush();

		for (int i = 0; i < getRunsPerMatrix(); i++) {
			for (int s = 0; s < copySizes.size(); s++) {
				long[] size = copySizes.get(s);
				double t = benchmarkCopy(size);
				result.setAsDouble(t, i, s);
				System.out.print(".");
				System.out.flush();
			}
		}

		System.out.println();
		Matrix m = result.mean(Ret.NEW, Matrix.ROW, true);
		m.setLabel("copy");
		return m;
	}

	public Matrix runBenchmarkPlusScalarOrig() {
		Matrix result = MatrixFactory.zeros(getRunsPerMatrix(), plusSizes.size());
		System.out.print("plus scalar (original matrix): ");
		System.out.flush();

		for (int i = 0; i < getRunsPerMatrix(); i++) {
			for (int s = 0; s < plusSizes.size(); s++) {
				long[] size = plusSizes.get(s);
				double t = benchmarkPlusScalarOrig(size);
				result.setAsDouble(t, i, s);
				System.out.print(".");
				System.out.flush();
			}
		}

		System.out.println();
		Matrix m = result.mean(Ret.NEW, Matrix.ROW, true);
		m.setLabel("plus scalar (original matrix)");
		return m;
	}

	public Matrix runBenchmarkTimesScalarNew() {
		Matrix result = MatrixFactory.zeros(getRunsPerMatrix(), timesSizes.size());
		System.out.print("times scalar (new matrix): ");
		System.out.flush();

		for (int i = 0; i < getRunsPerMatrix(); i++) {
			for (int s = 0; s < timesSizes.size(); s++) {
				long[] size = timesSizes.get(s);
				double t = benchmarkTimesScalarNew(size);
				result.setAsDouble(t, i, s);
				System.out.print(".");
				System.out.flush();
			}
		}

		System.out.println();
		Matrix m = result.mean(Ret.NEW, Matrix.ROW, true);
		m.setLabel("times scalar (new matrix)");
		return m;
	}

	public Matrix runBenchmarkTimesScalarOrig() {
		Matrix result = MatrixFactory.zeros(getRunsPerMatrix(), timesSizes.size());
		System.out.print("times scalar (original matrix): ");
		System.out.flush();

		for (int i = 0; i < getRunsPerMatrix(); i++) {
			for (int s = 0; s < timesSizes.size(); s++) {
				long[] size = timesSizes.get(s);
				double t = benchmarkTimesScalarOrig(size);
				result.setAsDouble(t, i, s);
				System.out.print(".");
				System.out.flush();
			}
		}

		System.out.println();
		Matrix m = result.mean(Ret.NEW, Matrix.ROW, true);
		m.setLabel("times scalar (original matrix)");
		return m;
	}

	public Matrix[] runBenchmarkTransposeNew() {
		Matrix result = MatrixFactory.zeros(getRunsPerMatrix(), transposeSizes.size());

		for (int s = 0; s < transposeSizes.size(); s++) {
			long[] size = transposeSizes.get(s);
			System.out.print("transpose new matrix [" + Coordinates.toString(size) + "]: ");
			System.out.flush();

			for (int i = 0; i < getBurnInRuns(); i++) {
				benchmarkTransposeNew(size);
				System.out.print("#");
				System.out.flush();
			}
			for (int i = 0; i < getRunsPerMatrix(); i++) {
				double t = benchmarkTransposeNew(size);
				result.setAsDouble(t, i, s);
				System.out.print(".");
				System.out.flush();
			}
		}

		Matrix mean = result.mean(Ret.NEW, Matrix.ROW, true);
		mean.setLabel("transpose new matrix (mean)");
		for (int c = 0; c < result.getColumnCount(); c++) {
			mean.setColumnLabel(c, result.getColumnLabel(c));
		}
		Matrix std = result.std(Ret.NEW, Matrix.ROW, true);
		std.setLabel("transpose new matrix (std)");
		for (int c = 0; c < result.getColumnCount(); c++) {
			std.setColumnLabel(c, result.getColumnLabel(c));
		}
		System.out.println(" " + mean.getAsInt(0, 0) + "+-" + std.getAsInt(0, 0) + "ms");
		return new Matrix[] { mean, std };
	}

	public Matrix runBenchmarkTransposeOrig() {
		Matrix result = MatrixFactory.zeros(getRunsPerMatrix(), transposeSizes.size());
		System.out.print("transpose (original matrix): ");
		System.out.flush();

		for (int i = 0; i < getRunsPerMatrix(); i++) {
			for (int s = 0; s < transposeSizes.size(); s++) {
				long[] size = transposeSizes.get(s);
				double t = benchmarkTransposeOrig(size);
				result.setAsDouble(t, i, s);
				System.out.print(".");
				System.out.flush();
			}
		}

		System.out.println();
		Matrix m = result.mean(Ret.NEW, Matrix.ROW, true);
		m.setLabel("transpose (original matrix)");
		return m;
	}

	public Matrix[] runBenchmarkInv() {
		Matrix result = MatrixFactory.zeros(getRunsPerMatrix(), invSizes.size());

		for (int s = 0; s < invSizes.size(); s++) {
			long[] size = invSizes.get(s);
			System.out.print("inverse [" + Coordinates.toString(size) + "]: ");
			System.out.flush();

			for (int i = 0; i < getBurnInRuns(); i++) {
				benchmarkInv(size);
				System.out.print("#");
				System.out.flush();
			}
			for (int i = 0; i < getRunsPerMatrix(); i++) {
				double t = benchmarkInv(size);
				result.setAsDouble(t, i, s);
				System.out.print(".");
				System.out.flush();
			}
		}

		Matrix mean = result.mean(Ret.NEW, Matrix.ROW, true);
		mean.setLabel("inv (mean)");
		for (int c = 0; c < result.getColumnCount(); c++) {
			mean.setColumnLabel(c, result.getColumnLabel(c));
		}
		Matrix std = result.std(Ret.NEW, Matrix.ROW, true);
		std.setLabel("inv (std)");
		for (int c = 0; c < result.getColumnCount(); c++) {
			std.setColumnLabel(c, result.getColumnLabel(c));
		}
		System.out.println(" " + mean.getAsInt(0, 0) + "+-" + std.getAsInt(0, 0) + "ms");
		return new Matrix[] { mean, std };
	}

	public Matrix[] runBenchmarkSVD() {
		Matrix result = MatrixFactory.zeros(getRunsPerMatrix(), svdSizes.size());

		for (int s = 0; s < svdSizes.size(); s++) {
			long[] size = svdSizes.get(s);
			System.out.print("SVD [" + Coordinates.toString(size) + "]: ");
			System.out.flush();

			for (int i = 0; i < getBurnInRuns(); i++) {
				benchmarkSVD(size);
				System.out.print("#");
				System.out.flush();
			}
			for (int i = 0; i < getRunsPerMatrix(); i++) {
				double t = benchmarkSVD(size);
				result.setAsDouble(t, i, s);
				System.out.print(".");
				System.out.flush();
			}
		}

		Matrix mean = result.mean(Ret.NEW, Matrix.ROW, true);
		mean.setLabel("SVD (mean)");
		for (int c = 0; c < result.getColumnCount(); c++) {
			mean.setColumnLabel(c, result.getColumnLabel(c));
		}
		Matrix std = result.std(Ret.NEW, Matrix.ROW, true);
		std.setLabel("SVD (std)");
		for (int c = 0; c < result.getColumnCount(); c++) {
			std.setColumnLabel(c, result.getColumnLabel(c));
		}
		System.out.println(" " + mean.getAsInt(0, 0) + "+-" + std.getAsInt(0, 0) + "ms");
		return new Matrix[] { mean, std };
	}

	public Matrix[] runBenchmarkEVD() {
		Matrix result = MatrixFactory.zeros(getRunsPerMatrix(), evdSizes.size());

		for (int s = 0; s < evdSizes.size(); s++) {
			long[] size = evdSizes.get(s);
			System.out.print("EVD [" + Coordinates.toString(size) + "]: ");
			System.out.flush();

			for (int i = 0; i < getBurnInRuns(); i++) {
				benchmarkEVD(size);
				System.out.print("#");
				System.out.flush();
			}
			for (int i = 0; i < getRunsPerMatrix(); i++) {
				double t = benchmarkEVD(size);
				result.setAsDouble(t, i, s);
				System.out.print(".");
				System.out.flush();
			}
		}

		Matrix mean = result.mean(Ret.NEW, Matrix.ROW, true);
		mean.setLabel("EVD (mean)");
		for (int c = 0; c < result.getColumnCount(); c++) {
			mean.setColumnLabel(c, result.getColumnLabel(c));
		}
		Matrix std = result.std(Ret.NEW, Matrix.ROW, true);
		std.setLabel("EVD (std)");
		for (int c = 0; c < result.getColumnCount(); c++) {
			std.setColumnLabel(c, result.getColumnLabel(c));
		}
		System.out.println(" " + mean.getAsInt(0, 0) + "+-" + std.getAsInt(0, 0) + "ms");
		return new Matrix[] { mean, std };
	}

	public Matrix[] runBenchmarkQR() {
		Matrix result = MatrixFactory.zeros(getRunsPerMatrix(), qrSizes.size());

		for (int s = 0; s < qrSizes.size(); s++) {
			long[] size = qrSizes.get(s);
			System.out.print("QR [" + Coordinates.toString(size) + "]: ");
			System.out.flush();

			for (int i = 0; i < getBurnInRuns(); i++) {
				benchmarkQR(size);
				System.out.print("#");
				System.out.flush();
			}
			for (int i = 0; i < getRunsPerMatrix(); i++) {
				double t = benchmarkQR(size);
				result.setAsDouble(t, i, s);
				System.out.print(".");
				System.out.flush();
			}
		}

		Matrix mean = result.mean(Ret.NEW, Matrix.ROW, true);
		mean.setLabel("QR (mean)");
		for (int c = 0; c < result.getColumnCount(); c++) {
			mean.setColumnLabel(c, result.getColumnLabel(c));
		}
		Matrix std = result.std(Ret.NEW, Matrix.ROW, true);
		std.setLabel("QR (std)");
		for (int c = 0; c < result.getColumnCount(); c++) {
			std.setColumnLabel(c, result.getColumnLabel(c));
		}
		System.out.println(" " + mean.getAsInt(0, 0) + "+-" + std.getAsInt(0, 0) + "ms");
		return new Matrix[] { mean, std };
	}

	public Matrix[] runBenchmarkLU() {
		Matrix result = MatrixFactory.zeros(getRunsPerMatrix(), luSizes.size());

		for (int s = 0; s < luSizes.size(); s++) {
			long[] size = luSizes.get(s);
			System.out.print("LU [" + Coordinates.toString(size) + "]: ");
			System.out.flush();

			for (int i = 0; i < getBurnInRuns(); i++) {
				benchmarkLU(size);
				System.out.print("#");
				System.out.flush();
			}
			for (int i = 0; i < getRunsPerMatrix(); i++) {
				double t = benchmarkLU(size);
				result.setAsDouble(t, i, s);
				System.out.print(".");
				System.out.flush();
			}
		}

		Matrix mean = result.mean(Ret.NEW, Matrix.ROW, true);
		mean.setLabel("LU (mean)");
		for (int c = 0; c < result.getColumnCount(); c++) {
			mean.setColumnLabel(c, result.getColumnLabel(c));
		}
		Matrix std = result.std(Ret.NEW, Matrix.ROW, true);
		std.setLabel("LU (std)");
		for (int c = 0; c < result.getColumnCount(); c++) {
			std.setColumnLabel(c, result.getColumnLabel(c));
		}
		System.out.println(" " + mean.getAsInt(0, 0) + "+-" + std.getAsInt(0, 0) + "ms");
		return new Matrix[] { mean, std };
	}

	public Matrix[] runBenchmarkChol() {
		Matrix result = MatrixFactory.zeros(getRunsPerMatrix(), cholSizes.size());

		for (int s = 0; s < cholSizes.size(); s++) {
			long[] size = cholSizes.get(s);
			System.out.print("Chol [" + Coordinates.toString(size) + "]: ");
			System.out.flush();

			for (int i = 0; i < getBurnInRuns(); i++) {
				benchmarkChol(size);
				System.out.print("#");
				System.out.flush();
			}
			for (int i = 0; i < getRunsPerMatrix(); i++) {
				double t = benchmarkChol(size);
				result.setAsDouble(t, i, s);
				System.out.print(".");
				System.out.flush();
			}
		}

		Matrix mean = result.mean(Ret.NEW, Matrix.ROW, true);
		mean.setLabel("Chol (mean)");
		for (int c = 0; c < result.getColumnCount(); c++) {
			mean.setColumnLabel(c, result.getColumnLabel(c));
		}
		Matrix std = result.std(Ret.NEW, Matrix.ROW, true);
		std.setLabel("Chol (std)");
		for (int c = 0; c < result.getColumnCount(); c++) {
			std.setColumnLabel(c, result.getColumnLabel(c));
		}
		System.out.println(" " + mean.getAsInt(0, 0) + "+-" + std.getAsInt(0, 0) + "ms");
		return new Matrix[] { mean, std };
	}

	public Matrix[] runBenchmarkMtimesNew() {
		Matrix result = MatrixFactory.zeros(getRunsPerMatrix(), mtimesSizes.size());

		for (int s = 0; s < mtimesSizes.size(); s++) {
			long[][] sizes = mtimesSizes.get(s);
			long[] size0 = sizes[0];
			long[] size1 = sizes[1];
			System.out.print("mtimes new matrix [" + Coordinates.toString(size0) + "x"
					+ Coordinates.toString(size1) + "]: ");
			System.out.flush();

			for (int i = 0; i < getBurnInRuns(); i++) {
				benchmarkMtimesNew(size0, size1);
				System.out.print("#");
				System.out.flush();
			}
			for (int i = 0; i < getRunsPerMatrix(); i++) {
				double t = benchmarkMtimesNew(size0, size1);
				result.setLabel(Coordinates.toString(size0) + "|" + Coordinates.toString(size1));
				result.setAsDouble(t, i, s);
				System.out.print(".");
				System.out.flush();
			}
		}

		Matrix mean = result.mean(Ret.NEW, Matrix.ROW, true);
		mean.setLabel("mtimes new matrix (mean)");
		for (int c = 0; c < result.getColumnCount(); c++) {
			mean.setColumnLabel(c, result.getColumnLabel(c));
		}
		Matrix std = result.std(Ret.NEW, Matrix.ROW, true);
		std.setLabel("mtimes new matrix (std)");
		for (int c = 0; c < result.getColumnCount(); c++) {
			std.setColumnLabel(c, result.getColumnLabel(c));
		}
		System.out.println(" " + mean.getAsInt(0, 0) + "+-" + std.getAsInt(0, 0) + "ms");
		return new Matrix[] { mean, std };
	}

	public double benchmarkCreate(long... size) {
		try {
			GCUtil.gc();
			long t0 = System.currentTimeMillis();
			createMatrix(size);
			long t1 = System.currentTimeMillis();
			return t1 - t0;
		} catch (Throwable e) {
			System.err.print("e");
			System.err.flush();
			return ERRORTIME;
		}
	}

	public double benchmarkPlusScalarNew(long... size) {
		Matrix m = null, r = null;
		try {
			GCUtil.gc();
			m = createMatrix(size);
			if (m.getClass().getDeclaredMethod("plus", Double.TYPE) == null) {
				System.err.print("-");
				System.err.flush();
				return NOTAVAILABLE;
			}
			long t0 = System.currentTimeMillis();
			r = m.plus(2);
			long t1 = System.currentTimeMillis();
			if (r == null) {
				System.err.print("e");
				System.err.flush();
				return ERRORTIME;
			}
			return t1 - t0;
		} catch (Throwable e) {
			System.err.print("e");
			System.err.flush();
			return ERRORTIME;
		}
	}

	public double benchmarkPlusScalarOrig(long... size) {
		Matrix m = null, r = null;
		try {
			GCUtil.gc();
			m = createMatrix(size);
			if (m.getClass().getDeclaredMethod("plus", Ret.class, Boolean.TYPE, Double.TYPE) == null) {
				System.err.print("-");
				System.err.flush();
				return NOTAVAILABLE;
			}
			long t0 = System.currentTimeMillis();
			r = m.plus(Ret.ORIG, false, 2);
			long t1 = System.currentTimeMillis();
			if (r == null) {
				System.err.print("e");
				System.err.flush();
				return ERRORTIME;
			}
			return t1 - t0;
		} catch (Throwable e) {
			System.err.print("e");
			System.err.flush();
			return ERRORTIME;
		}
	}

	public double benchmarkCopy(long... size) {
		Matrix m = null, r = null;
		try {
			GCUtil.gc();
			m = createMatrix(size);
			if (!m.getClass().getName().startsWith("org.ujmp.core")
					&& m.getClass().getDeclaredMethod("copy") == null) {
				System.err.print("-");
				System.err.flush();
				return NOTAVAILABLE;
			}
			long t0 = System.currentTimeMillis();
			r = m.copy();
			long t1 = System.currentTimeMillis();
			if (r == null) {
				System.err.print("e");
				System.err.flush();
				return ERRORTIME;
			}
			return t1 - t0;
		} catch (Throwable e) {
			System.err.print("e");
			System.err.flush();
			return ERRORTIME;
		}
	}

	public double benchmarkTimesScalarNew(long... size) {
		Matrix m = null, r = null;
		try {
			GCUtil.gc();
			m = createMatrix(size);
			if (m.getClass().getDeclaredMethod("times", Double.TYPE) == null) {
				System.err.print("-");
				System.err.flush();
				return NOTAVAILABLE;
			}
			long t0 = System.currentTimeMillis();
			r = m.times(2);
			long t1 = System.currentTimeMillis();
			if (r == null) {
				System.err.print("e");
				System.err.flush();
				return ERRORTIME;
			}
			return t1 - t0;
		} catch (Throwable e) {
			System.err.print("e");
			System.err.flush();
			return ERRORTIME;
		}
	}

	public double benchmarkTimesScalarOrig(long... size) {
		Matrix m = null, r = null;
		try {
			GCUtil.gc();
			m = createMatrix(size);
			if (m.getClass().getDeclaredMethod("times", Ret.class, Boolean.TYPE, Double.TYPE) == null) {
				System.err.print("-");
				System.err.flush();
				return NOTAVAILABLE;
			}
			long t0 = System.currentTimeMillis();
			r = m.times(Ret.ORIG, false, 2);
			long t1 = System.currentTimeMillis();
			if (r == null) {
				System.err.print("e");
				System.err.flush();
				return ERRORTIME;
			}
			return t1 - t0;
		} catch (Throwable e) {
			System.err.print("e");
			System.err.flush();
			return ERRORTIME;
		}
	}

	public double benchmarkTransposeNew(long... size) {
		Matrix m = null, r = null;
		try {
			GCUtil.gc();
			m = createMatrix(size);
			m.randn(Ret.ORIG);
			long t0 = System.currentTimeMillis();
			r = m.transpose();
			long t1 = System.currentTimeMillis();
			if (r == null) {
				System.err.print("e");
				System.err.flush();
				return ERRORTIME;
			}
			return t1 - t0;
		} catch (Throwable e) {
			System.err.print("e");
			System.err.flush();
			return ERRORTIME;
		}
	}

	public double benchmarkTransposeOrig(long... size) {
		Matrix m = null, r = null;
		try {
			GCUtil.gc();
			m = createMatrix(size);
			m.randn(Ret.ORIG);
			long t0 = System.currentTimeMillis();
			r = m.transpose(Ret.ORIG);
			long t1 = System.currentTimeMillis();
			if (r == null) {
				System.err.print("e");
				System.err.flush();
				return ERRORTIME;
			}
			return t1 - t0;
		} catch (Throwable e) {
			System.err.print("e");
			System.err.flush();
			return ERRORTIME;
		}
	}

	public double benchmarkInv(long... size) {
		Matrix m = null, r = null;
		try {
			GCUtil.gc();
			m = createMatrix(size);
			if (!m.getClass().getName().startsWith("org.ujmp.core")
					&& m.getClass().getDeclaredMethod("inv") == null) {
				System.err.print("-");
				System.err.flush();
				return NOTAVAILABLE;
			}

			if (isSkipSlowLibraries() && m.getClass().getName().startsWith("org.ujmp.orbital.")) {
				System.err.print("skip(x180)");
				System.err.flush();
				return TOOLONG;
			}
			if (isSkipSlowLibraries() && m.getClass().getName().startsWith("org.ujmp.jscience.")) {
				System.err.print("skip(x30)");
				System.err.flush();
				return TOOLONG;
			}

			m.randn(Ret.ORIG);
			long t0 = System.currentTimeMillis();
			r = m.inv();
			long t1 = System.currentTimeMillis();
			if (r == null) {
				System.err.print("e");
				System.err.flush();
				return ERRORTIME;
			}
			return t1 - t0;
		} catch (Throwable e) {
			System.err.print("e");
			System.err.flush();
			return ERRORTIME;
		}
	}

	public double benchmarkSVD(long... size) {
		Matrix m = null;
		Matrix[] r = null;
		try {
			GCUtil.gc();
			m = createMatrix(size);
			if (!m.getClass().getName().startsWith("org.ujmp.core")
					&& m.getClass().getDeclaredMethod("svd") == null) {
				System.err.print("-");
				System.err.flush();
				return NOTAVAILABLE;
			}

			if (isSkipSlowLibraries() && m.getClass().getName().startsWith("org.ujmp.vecmath.")) {
				System.err.print("skip(x25)");
				System.err.flush();
				return TOOLONG;
			}

			m.randn(Ret.ORIG);
			long t0 = System.currentTimeMillis();
			r = m.svd();
			long t1 = System.currentTimeMillis();
			if (r == null) {
				System.err.print("e");
				System.err.flush();
				return ERRORTIME;
			}
			return t1 - t0;
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

	public double benchmarkEVD(long... size) {
		Matrix m = null;
		Matrix[] r = null;
		try {
			GCUtil.gc();
			m = createMatrix(size);
			if (!m.getClass().getName().startsWith("org.ujmp.core.")
					&& m.getClass().getDeclaredMethod("eig") == null) {
				System.err.print("-");
				System.err.flush();
				return NOTAVAILABLE;
			}
			m.randn(Ret.ORIG);
			long t0 = System.currentTimeMillis();
			r = m.eig();
			long t1 = System.currentTimeMillis();
			if (r == null) {
				System.err.print("e");
				System.err.flush();
				return ERRORTIME;
			}
			return t1 - t0;
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

	public double benchmarkQR(long... size) {
		Matrix m = null;
		Matrix[] r = null;
		try {
			GCUtil.gc();
			m = createMatrix(size);
			if (!m.getClass().getName().startsWith("org.ujmp.core")
					&& m.getClass().getDeclaredMethod("qr") == null) {
				System.err.print("-");
				System.err.flush();
				return NOTAVAILABLE;
			}
			m.randn(Ret.ORIG);
			long t0 = System.currentTimeMillis();
			r = m.qr();
			long t1 = System.currentTimeMillis();
			if (r == null) {
				System.err.print("e");
				System.err.flush();
				return ERRORTIME;
			}
			return t1 - t0;
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

	public double benchmarkChol(long... size) {
		Matrix m = null;
		Matrix r = null;
		try {
			GCUtil.gc();
			m = createMatrix(size);
			if (!m.getClass().getName().startsWith("org.ujmp.core")
					&& m.getClass().getDeclaredMethod("chol") == null) {
				System.err.print("-");
				System.err.flush();
				return NOTAVAILABLE;
			}

			// if (m.getClass().getName().startsWith("org.ujmp.parallelcolt."))
			// {
			// System.err.print("skip(deadlock)");
			// System.err.flush();
			// return TOOLONG;
			// }

			m.randn(Ret.ORIG);
			long t0 = System.currentTimeMillis();
			r = m.chol();
			long t1 = System.currentTimeMillis();
			if (r == null) {
				System.err.print("e");
				System.err.flush();
				return ERRORTIME;
			}
			return t1 - t0;
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

	public double benchmarkLU(long... size) {
		Matrix m = null;
		Matrix[] r = null;
		try {
			GCUtil.gc();
			m = createMatrix(size);
			if (!m.getClass().getName().startsWith("org.ujmp.core")
					&& m.getClass().getDeclaredMethod("lu") == null) {
				System.err.print("-");
				System.err.flush();
				return NOTAVAILABLE;
			}

			if (isSkipSlowLibraries() && m.getClass().getName().startsWith("org.ujmp.orbital.")) {
				System.err.print("skip(x200)");
				System.err.flush();
				return TOOLONG;
			}
			if (isSkipSlowLibraries() && m.getClass().getName().startsWith("org.ujmp.jscience.")) {
				System.err.print("skip(x70)");
				System.err.flush();
				return TOOLONG;
			}

			m.randn(Ret.ORIG);
			long t0 = System.currentTimeMillis();
			r = m.lu();
			long t1 = System.currentTimeMillis();
			if (r == null) {
				System.err.print("e");
				System.err.flush();
				return ERRORTIME;
			}
			return t1 - t0;
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

	public double benchmarkMtimesNew(long[] size0, long[] size1) {
		Matrix m0 = null, m1 = null, r = null;
		try {
			GCUtil.gc();
			m0 = createMatrix(size0);
			m1 = createMatrix(size1);

			if (isSkipSlowLibraries() && m0.getClass().getName().startsWith("org.ujmp.orbital.")) {
				System.err.print("skip(x100)");
				System.err.flush();
				return TOOLONG;
			}
			if (isSkipSlowLibraries() && m0.getClass().getName().startsWith("org.ujmp.jmatrices.")) {
				System.err.print("skip(x800)");
				System.err.flush();
				return TOOLONG;
			}

			m0.randn(Ret.ORIG);
			m1.randn(Ret.ORIG);
			long t0 = System.currentTimeMillis();
			r = m0.mtimes(m1);
			long t1 = System.currentTimeMillis();
			if (r == null) {
				System.err.print("e");
				System.err.flush();
				return ERRORTIME;
			}
			return t1 - t0;
		} catch (Throwable e) {
			System.err.print("e");
			System.err.flush();
			return ERRORTIME;
		}
	}

	public static void configureDefault() {
		AbstractMatrix2DBenchmark.setSkipSlowLibraries(true);

		AbstractMatrix2DBenchmark.setBurnInRuns(0);
		AbstractMatrix2DBenchmark.setRunsPerMatrix(1);

		AbstractMatrix2DBenchmark.setRunTransposeNew(true);
		AbstractMatrix2DBenchmark.setRunMtimesNew(true);
		AbstractMatrix2DBenchmark.setRunInv(true);
		AbstractMatrix2DBenchmark.setRunSVD(true);
		AbstractMatrix2DBenchmark.setRunEVD(true);
		AbstractMatrix2DBenchmark.setRunQR(true);
		AbstractMatrix2DBenchmark.setRunLU(true);
		AbstractMatrix2DBenchmark.setRunChol(true);
	}

}
