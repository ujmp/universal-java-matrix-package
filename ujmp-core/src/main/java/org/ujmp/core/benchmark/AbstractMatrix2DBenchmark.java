/*
 * Copyright (C) 2008-2009 Holger Arndt, A. Naegele and M. Bundschus
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
import org.ujmp.core.util.MathUtil;

public abstract class AbstractMatrix2DBenchmark {

	public static final long[] SIZE100X100 = new long[] { 100, 100 };

	public static final long[] SIZE100X1000 = new long[] { 100, 1000 };

	public static final long[] SIZE100X10000 = new long[] { 100, 10000 };

	public static final long[] SIZE100X100000 = new long[] { 100, 100000 };

	public static final long[] SIZE500X500 = new long[] { 500, 500 };

	public static final long[] SIZE1000X100 = new long[] { 1000, 100 };

	public static final long[] SIZE1000X1000 = new long[] { 1000, 1000 };

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

	private final List<long[]> transposeSizes = Arrays.asList(new long[][] { SIZE5000X5000 });

	private final List<long[]> copySizes = Arrays.asList(new long[][] { SIZE5000X5000 });

	private final List<long[]> initSizes = Arrays.asList(new long[][] { SIZE5000X5000 });

	private final List<long[]> createSizes = Arrays.asList(new long[][] { SIZE5000X5000 });

	private final List<long[]> plusSizes = Arrays.asList(new long[][] { SIZE5000X5000 });

	private final List<long[]> timesSizes = Arrays.asList(new long[][] { SIZE5000X5000 });

	private final List<long[]> invSizes = Arrays.asList(new long[][] { SIZE500X500 });

	private final List<long[]> svdSizes = Arrays.asList(new long[][] { SIZE500X500 });

	private final List<long[]> evdSizes = Arrays.asList(new long[][] { SIZE500X500 });

	private final List<long[]> qrSizes = Arrays.asList(new long[][] { SIZE500X500 });

	private final List<long[]> luSizes = Arrays.asList(new long[][] { SIZE500X500 });

	private final List<long[][]> mtimesSizes = Arrays.asList(new long[][][] { { SIZE500X500,
			SIZE500X500 } });

	public abstract Matrix createMatrix(long... size) throws MatrixException;

	public abstract Matrix createMatrix(Matrix source) throws MatrixException;

	public void setRunsPerMatrix(int runs) {
		System.setProperty("runsPerMatrix", "" + runs);
	}

	public int getRunsPerMatrix() {
		return MathUtil.getInt(System.getProperty("runsPerMatrix"));
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

	public boolean isRunSVD() {
		return "true".equals(System.getProperty("runSVD"));
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

	public static void setRunLU(boolean b) {
		System.setProperty("runLU", "" + b);
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
				result.add(runBenchmarkTransposeNew());
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
				result.add(runBenchmarkInv());
			}

			if (isRunSVD()) {
				result.add(runBenchmarkSVD());
			}

			if (isRunEVD()) {
				result.add(runBenchmarkEVD());
			}

			if (isRunQR()) {
				result.add(runBenchmarkQR());
			}

			if (isRunLU()) {
				result.add(runBenchmarkLU());
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

		for (int i = 0; i < getRunsPerMatrix(); i++) {
			for (int s = 0; s < initSizes.size(); s++) {
				long[] size = initSizes.get(s);
				long t = benchmarkCreate(size);
				result.setAsDouble(t, i, s);
				System.out.print(".");
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

		for (int i = 0; i < getRunsPerMatrix(); i++) {
			for (int s = 0; s < createSizes.size(); s++) {
				long[] size = createSizes.get(s);
				long t = benchmarkCreate(size);
				result.setAsDouble(t, i, s);
				System.out.print(".");
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

		for (int i = 0; i < getRunsPerMatrix(); i++) {
			for (int s = 0; s < plusSizes.size(); s++) {
				long[] size = plusSizes.get(s);
				long t = benchmarkPlusScalarNew(size);
				result.setAsDouble(t, i, s);
				System.out.print(".");
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

		for (int i = 0; i < getRunsPerMatrix(); i++) {
			for (int s = 0; s < copySizes.size(); s++) {
				long[] size = copySizes.get(s);
				long t = benchmarkCopy(size);
				result.setAsDouble(t, i, s);
				System.out.print(".");
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

		for (int i = 0; i < getRunsPerMatrix(); i++) {
			for (int s = 0; s < plusSizes.size(); s++) {
				long[] size = plusSizes.get(s);
				long t = benchmarkPlusScalarOrig(size);
				result.setAsDouble(t, i, s);
				System.out.print(".");
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

		for (int i = 0; i < getRunsPerMatrix(); i++) {
			for (int s = 0; s < timesSizes.size(); s++) {
				long[] size = timesSizes.get(s);
				long t = benchmarkTimesScalarNew(size);
				result.setAsDouble(t, i, s);
				System.out.print(".");
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

		for (int i = 0; i < getRunsPerMatrix(); i++) {
			for (int s = 0; s < timesSizes.size(); s++) {
				long[] size = timesSizes.get(s);
				long t = benchmarkTimesScalarOrig(size);
				result.setAsDouble(t, i, s);
				System.out.print(".");
			}
		}

		System.out.println();
		Matrix m = result.mean(Ret.NEW, Matrix.ROW, true);
		m.setLabel("times scalar (original matrix)");
		return m;
	}

	public Matrix runBenchmarkTransposeNew() {
		Matrix result = MatrixFactory.zeros(getRunsPerMatrix(), transposeSizes.size());
		System.out.print("transpose (new matrix): ");

		for (int i = 0; i < getRunsPerMatrix(); i++) {
			for (int s = 0; s < transposeSizes.size(); s++) {
				long[] size = transposeSizes.get(s);
				long t = benchmarkTransposeNew(size);
				result.setAsDouble(t, i, s);
				System.out.print(".");
			}
		}

		System.out.println();
		Matrix m = result.mean(Ret.NEW, Matrix.ROW, true);
		m.setLabel("transpose (new matrix)");
		return m;
	}

	public Matrix runBenchmarkTransposeOrig() {
		Matrix result = MatrixFactory.zeros(getRunsPerMatrix(), transposeSizes.size());
		System.out.print("transpose (original matrix): ");

		for (int i = 0; i < getRunsPerMatrix(); i++) {
			for (int s = 0; s < transposeSizes.size(); s++) {
				long[] size = transposeSizes.get(s);
				long t = benchmarkTransposeOrig(size);
				result.setAsDouble(t, i, s);
				System.out.print(".");
			}
		}

		System.out.println();
		Matrix m = result.mean(Ret.NEW, Matrix.ROW, true);
		m.setLabel("transpose (original matrix)");
		return m;
	}

	public Matrix runBenchmarkInv() {
		Matrix result = MatrixFactory.zeros(getRunsPerMatrix(), invSizes.size());
		System.out.print("inverse: ");

		for (int i = 0; i < getRunsPerMatrix(); i++) {
			for (int s = 0; s < invSizes.size(); s++) {
				long[] size = invSizes.get(s);
				long t = benchmarkInv(size);
				result.setAsDouble(t, i, s);
				System.out.print(".");
			}
		}

		System.out.println();
		Matrix m = result.mean(Ret.NEW, Matrix.ROW, true);
		m.setLabel("inverse");
		return m;
	}

	public Matrix runBenchmarkSVD() {
		Matrix result = MatrixFactory.zeros(getRunsPerMatrix(), svdSizes.size());
		System.out.print("SVD: ");

		for (int i = 0; i < getRunsPerMatrix(); i++) {
			for (int s = 0; s < svdSizes.size(); s++) {
				long[] size = svdSizes.get(s);
				long t = benchmarkSVD(size);
				result.setAsDouble(t, i, s);
				System.out.print(".");
			}
		}

		System.out.println();
		Matrix m = result.mean(Ret.NEW, Matrix.ROW, true);
		m.setLabel("SVD");
		return m;
	}

	public Matrix runBenchmarkEVD() {
		Matrix result = MatrixFactory.zeros(getRunsPerMatrix(), evdSizes.size());
		System.out.print("EVD: ");

		for (int i = 0; i < getRunsPerMatrix(); i++) {
			for (int s = 0; s < evdSizes.size(); s++) {
				long[] size = evdSizes.get(s);
				long t = benchmarkEVD(size);
				result.setAsDouble(t, i, s);
				System.out.print(".");
			}
		}

		System.out.println();
		Matrix m = result.mean(Ret.NEW, Matrix.ROW, true);
		m.setLabel("EVD");
		return m;
	}

	public Matrix runBenchmarkQR() {
		Matrix result = MatrixFactory.zeros(getRunsPerMatrix(), qrSizes.size());
		System.out.print("QR: ");

		for (int i = 0; i < getRunsPerMatrix(); i++) {
			for (int s = 0; s < qrSizes.size(); s++) {
				long[] size = qrSizes.get(s);
				long t = benchmarkQR(size);
				result.setAsDouble(t, i, s);
				System.out.print(".");
			}
		}

		System.out.println();
		Matrix m = result.mean(Ret.NEW, Matrix.ROW, true);
		m.setLabel("QR");
		return m;
	}

	public Matrix runBenchmarkLU() {
		Matrix result = MatrixFactory.zeros(getRunsPerMatrix(), luSizes.size());
		System.out.print("LU: ");

		for (int i = 0; i < getRunsPerMatrix(); i++) {
			for (int s = 0; s < luSizes.size(); s++) {
				long[] size = luSizes.get(s);
				long t = benchmarkLU(size);
				result.setAsDouble(t, i, s);
				System.out.print(".");
			}
		}

		System.out.println();
		Matrix m = result.mean(Ret.NEW, Matrix.ROW, true);
		m.setLabel("LU");
		return m;
	}

	public Matrix[] runBenchmarkMtimesNew() {
		Matrix result = MatrixFactory.zeros(getRunsPerMatrix(), mtimesSizes.size());
		System.out.print("mtimes (new matrix): ");

		for (int i = 0; i < getRunsPerMatrix(); i++) {
			for (int s = 0; s < mtimesSizes.size(); s++) {
				long[][] sizes = mtimesSizes.get(s);
				long[] size0 = sizes[0];
				long[] size1 = sizes[1];
				long t = benchmarkMtimesNew(size0, size1);
				result.setLabel(Coordinates.toString(size0) + "|" + Coordinates.toString(size1));
				result.setAsDouble(t, i, s);
				System.out.print(".");
			}
		}

		System.out.println();
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
		return new Matrix[] { mean, std };
	}

	public long benchmarkCreate(long... size) {
		try {
			long t0 = System.currentTimeMillis();
			createMatrix(size);
			long t1 = System.currentTimeMillis();
			return t1 - t0;
		} catch (Throwable e) {
			System.err.print("e");
			return Long.MAX_VALUE;
		}
	}

	public long benchmarkPlusScalarNew(long... size) {
		Matrix m = null, r = null;
		try {
			m = createMatrix(size);
			if (m.getClass().getDeclaredMethod("plus", Double.TYPE) == null) {
				System.err.print("-");
				return -1;
			}
			long t0 = System.currentTimeMillis();
			r = m.plus(2);
			long t1 = System.currentTimeMillis();
			if (r == null) {
				System.err.print("e");
				return Long.MAX_VALUE;
			}
			return t1 - t0;
		} catch (Throwable e) {
			System.err.print("e");
			return Long.MAX_VALUE;
		}
	}

	public long benchmarkPlusScalarOrig(long... size) {
		Matrix m = null, r = null;
		try {
			m = createMatrix(size);
			if (m.getClass().getDeclaredMethod("plus", Ret.class, Boolean.TYPE, Double.TYPE) == null) {
				System.err.print("-");
				return -1;
			}
			long t0 = System.currentTimeMillis();
			r = m.plus(Ret.ORIG, false, 2);
			long t1 = System.currentTimeMillis();
			if (r == null) {
				System.err.print("e");
				return Long.MAX_VALUE;
			}
			return t1 - t0;
		} catch (Throwable e) {
			System.err.print("e");
			return Long.MAX_VALUE;
		}
	}

	public long benchmarkCopy(long... size) {
		Matrix m = null, r = null;
		try {
			m = createMatrix(size);
			if (!m.getClass().getName().startsWith("org.ujmp.core")
					&& m.getClass().getDeclaredMethod("copy") == null) {
				System.err.print("-");
				return -1;
			}
			long t0 = System.currentTimeMillis();
			r = m.copy();
			long t1 = System.currentTimeMillis();
			if (r == null) {
				System.err.print("e");
				return Long.MAX_VALUE;
			}
			return t1 - t0;
		} catch (Throwable e) {
			System.err.print("e");
			return Long.MAX_VALUE;
		}
	}

	public long benchmarkTimesScalarNew(long... size) {
		Matrix m = null, r = null;
		try {
			m = createMatrix(size);
			if (m.getClass().getDeclaredMethod("times", Double.TYPE) == null) {
				System.err.print("-");
				return -1;
			}
			long t0 = System.currentTimeMillis();
			r = m.times(2);
			long t1 = System.currentTimeMillis();
			if (r == null) {
				System.err.print("e");
				return Long.MAX_VALUE;
			}
			return t1 - t0;
		} catch (Throwable e) {
			System.err.print("e");
			return Long.MAX_VALUE;
		}
	}

	public long benchmarkTimesScalarOrig(long... size) {
		Matrix m = null, r = null;
		try {
			m = createMatrix(size);
			if (m.getClass().getDeclaredMethod("times", Ret.class, Boolean.TYPE, Double.TYPE) == null) {
				System.err.print("e");
				return -1;
			}
			long t0 = System.currentTimeMillis();
			r = m.times(Ret.ORIG, false, 2);
			long t1 = System.currentTimeMillis();
			if (r == null) {
				System.err.print("e");
				return Long.MAX_VALUE;
			}
			return t1 - t0;
		} catch (Throwable e) {
			System.err.print("e");
			return Long.MAX_VALUE;
		}
	}

	public long benchmarkTransposeNew(long... size) {
		Matrix m = null, r = null;
		try {
			m = createMatrix(size);
			m.randn(Ret.ORIG);
			long t0 = System.currentTimeMillis();
			r = m.transpose();
			long t1 = System.currentTimeMillis();
			if (r == null) {
				System.err.print("e");
				return Long.MAX_VALUE;
			}
			return t1 - t0;
		} catch (Throwable e) {
			System.err.print("e");
			return Long.MAX_VALUE;
		}
	}

	public long benchmarkTransposeOrig(long... size) {
		Matrix m = null, r = null;
		try {
			m = createMatrix(size);
			m.randn(Ret.ORIG);
			long t0 = System.currentTimeMillis();
			r = m.transpose(Ret.ORIG);
			long t1 = System.currentTimeMillis();
			if (r == null) {
				System.err.print("e");
				return Long.MAX_VALUE;
			}
			return t1 - t0;
		} catch (Throwable e) {
			System.err.print("e");
			return Long.MAX_VALUE;
		}
	}

	public long benchmarkInv(long... size) {
		Matrix m = null, r = null;
		try {
			m = createMatrix(size);
			if (!m.getClass().getName().startsWith("org.ujmp.core")
					&& m.getClass().getDeclaredMethod("inv") == null) {
				System.err.print("-");
				return -1;
			}
			m.randn(Ret.ORIG);
			long t0 = System.currentTimeMillis();
			r = m.inv();
			long t1 = System.currentTimeMillis();
			if (r == null) {
				return Long.MAX_VALUE;
			}
			return t1 - t0;
		} catch (Throwable e) {
			System.err.print("e");
			return Long.MAX_VALUE;
		}
	}

	public long benchmarkSVD(long... size) {
		Matrix m = null;
		Matrix[] r = null;
		try {
			m = createMatrix(size);
			if (!m.getClass().getName().startsWith("org.ujmp.core")
					&& m.getClass().getDeclaredMethod("svd") == null) {
				System.err.print("-");
				return -1;
			}
			m.randn(Ret.ORIG);
			long t0 = System.currentTimeMillis();
			r = m.svd();
			long t1 = System.currentTimeMillis();
			if (r == null) {
				return Long.MAX_VALUE;
			}
			return t1 - t0;
		} catch (Throwable e) {
			System.err.print("e");
			return Long.MAX_VALUE;
		}
	}

	public long benchmarkEVD(long... size) {
		Matrix m = null;
		Matrix[] r = null;
		try {
			m = createMatrix(size);
			if (!m.getClass().getName().startsWith("org.ujmp.core")
					&& m.getClass().getDeclaredMethod("evd") == null) {
				System.err.print("-");
				return -1;
			}
			m.randn(Ret.ORIG);
			long t0 = System.currentTimeMillis();
			r = m.eig();
			long t1 = System.currentTimeMillis();
			if (r == null) {
				return Long.MAX_VALUE;
			}
			return t1 - t0;
		} catch (Throwable e) {
			System.err.print("e");
			return Long.MAX_VALUE;
		}
	}

	public long benchmarkQR(long... size) {
		Matrix m = null;
		Matrix[] r = null;
		try {
			m = createMatrix(size);
			if (!m.getClass().getName().startsWith("org.ujmp.core")
					&& m.getClass().getDeclaredMethod("qr") == null) {
				System.err.print("-");
				return -1;
			}
			m.randn(Ret.ORIG);
			long t0 = System.currentTimeMillis();
			r = m.qr();
			long t1 = System.currentTimeMillis();
			if (r == null) {
				return Long.MAX_VALUE;
			}
			return t1 - t0;
		} catch (Throwable e) {
			System.err.print("e");
			return Long.MAX_VALUE;
		}
	}

	public long benchmarkLU(long... size) {
		Matrix m = null;
		Matrix[] r = null;
		try {
			m = createMatrix(size);
			if (!m.getClass().getName().startsWith("org.ujmp.core")
					&& m.getClass().getDeclaredMethod("lu") == null) {
				System.err.print("-");
				return -1;
			}
			m.randn(Ret.ORIG);
			long t0 = System.currentTimeMillis();
			r = m.lu();
			long t1 = System.currentTimeMillis();
			if (r == null) {
				return Long.MAX_VALUE;
			}
			return t1 - t0;
		} catch (Throwable e) {
			System.err.print("e");
			return Long.MAX_VALUE;
		}
	}

	public long benchmarkMtimesNew(long[] size0, long[] size1) {
		Matrix m0 = null, m1 = null, r = null;
		try {
			m0 = createMatrix(size0);
			m1 = createMatrix(size1);

			if (m0.getClass().getName().startsWith("org.ujmp.orbital")) {
				// this matrix takes 100 times longer for multiplication
				System.err.print("skip");
				return -1;
			}

			m0.randn(Ret.ORIG);
			m1.randn(Ret.ORIG);
			long t0 = System.currentTimeMillis();
			r = m0.mtimes(m1);
			long t1 = System.currentTimeMillis();
			if (r == null) {
				System.err.print("e");
				return Long.MAX_VALUE;
			}
			return t1 - t0;
		} catch (Throwable e) {
			System.err.print("e");
			return Long.MAX_VALUE;
		}
	}

}
