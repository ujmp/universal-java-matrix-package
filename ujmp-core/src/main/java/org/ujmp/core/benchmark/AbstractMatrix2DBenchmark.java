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
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.MathUtil;

public abstract class AbstractMatrix2DBenchmark {

	public static final long[] SIZE100X100 = new long[] { 100, 100 };

	public static final long[] SIZE100X1000 = new long[] { 100, 1000 };

	public static final long[] SIZE100X10000 = new long[] { 100, 10000 };

	public static final long[] SIZE100X100000 = new long[] { 100, 100000 };

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

	private final List<long[]> invSizes = Arrays.asList(new long[][] { SIZE1000X1000 });

	private final List<long[][]> mtimesSizes = Arrays.asList(new long[][][] { { SIZE1000X1000,
			SIZE1000X1000 } });

	private boolean runInit = false;

	private boolean runCreate = false;

	private boolean runCopy = false;

	private boolean runPlusScalarNew = false;

	private boolean runPlusScalarOrig = false;

	private boolean runTimesScalarNew = false;

	private boolean runTimesScalarOrig = false;

	private boolean runTransposeOrig = false;

	public abstract Matrix createMatrix(long... size) throws MatrixException;

	public abstract Matrix createMatrix(Matrix source) throws MatrixException;

	public void setRunsPerMatrix(int runs) {
		System.setProperty("runsPerMatrix", "" + runs);
	}

	public int getRunsPerMatrix() {
		return MathUtil.getInt(System.getProperty("runsPerMatrix"));
	}

	public boolean isRunInit() {
		return runInit;
	}

	public void setRunInit(boolean runInit) {
		this.runInit = runInit;
	}

	public boolean isRunCreate() {
		return runCreate;
	}

	public void setRunCreate(boolean runCreate) {
		this.runCreate = runCreate;
	}

	public boolean isRunCopy() {
		return runCopy;
	}

	public void setRunCopy(boolean runCopy) {
		this.runCopy = runCopy;
	}

	public boolean isRunPlusScalarNew() {
		return runPlusScalarNew;
	}

	public void setRunPlusScalarNew(boolean runPlusScalarNew) {
		this.runPlusScalarNew = runPlusScalarNew;
	}

	public boolean isRunPlusScalarOrig() {
		return runPlusScalarOrig;
	}

	public void setRunPlusScalarOrig(boolean runPlusScalarOrig) {
		this.runPlusScalarOrig = runPlusScalarOrig;
	}

	public boolean isRunTimesScalarNew() {
		return runTimesScalarNew;
	}

	public void setRunTimesScalarNew(boolean runTimesScalarNew) {
		this.runTimesScalarNew = runTimesScalarNew;
	}

	public boolean isRunTimesScalarOrig() {
		return runTimesScalarOrig;
	}

	public void setRunTimesScalarOrig(boolean runTimesScalarOrig) {
		this.runTimesScalarOrig = runTimesScalarOrig;
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

	public void setRunTransposeNew(boolean b) {
		System.setProperty("runTransposeNew", "" + b);
	}

	public void setRunMtimesNew(boolean b) {
		System.setProperty("runMtimesNew", "" + b);
	}

	public void setRunInv(boolean b) {
		System.setProperty("runInv", "" + b);
	}

	public void setInv(boolean b) {
		System.setProperty("runInv", "" + b);
	}

	public boolean isRunTransposeOrig() {
		return runTransposeOrig;
	}

	public void setRunTransposeOrig(boolean runTransposeOrig) {
		this.runTransposeOrig = runTransposeOrig;
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
		System.out.println("===============================================================");
		System.out.println(createMatrix(1, 1).getClass().getSimpleName());
		System.out.println("===============================================================");

		List<Matrix> result = new ArrayList<Matrix>();

		if (runInit) {
			init();
		}

		if (runCreate) {
			result.add(runBenchmarkCreate());
		}

		if (runCopy) {
			result.add(runBenchmarkCopy());
		}

		if (runPlusScalarNew) {
			result.add(runBenchmarkPlusScalarNew());
		}

		if (runPlusScalarOrig) {
			result.add(runBenchmarkPlusScalarOrig());
		}

		if (runTimesScalarNew) {
			result.add(runBenchmarkTimesScalarNew());
		}

		if (runTimesScalarOrig) {
			result.add(runBenchmarkTimesScalarOrig());
		}

		if (isRunTransposeNew()) {
			result.add(runBenchmarkTransposeNew());
		}

		if (runTransposeOrig) {
			result.add(runBenchmarkTransposeOrig());
		}

		if (isRunMtimesNew()) {
			result.add(runBenchmarkMtimesNew());
		}

		if (isRunInv()) {
			result.add(runBenchmarkInv());
		}

		System.out.println();

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

	public Matrix runBenchmarkMtimesNew() {
		Matrix result = MatrixFactory.zeros(getRunsPerMatrix(), mtimesSizes.size());
		System.out.print("mtimes (new matrix): ");

		for (int i = 0; i < getRunsPerMatrix(); i++) {
			for (int s = 0; s < mtimesSizes.size(); s++) {
				long[][] sizes = mtimesSizes.get(s);
				long[] size0 = sizes[0];
				long[] size1 = sizes[1];
				long t = benchmarkMtimesNew(size0, size1);
				result.setAsDouble(t, i, s);
				System.out.print(".");
			}
		}

		System.out.println();
		Matrix m = result.mean(Ret.NEW, Matrix.ROW, true);
		m.setLabel("mtimes (new matrix)");
		return m;
	}

	public long benchmarkCreate(long... size) {
		try {
			long t0 = System.currentTimeMillis();
			createMatrix(size);
			long t1 = System.currentTimeMillis();
			return t1 - t0;
		} catch (Throwable e) {
			System.out.print("*");
			return -1;
		}
	}

	public long benchmarkPlusScalarNew(long... size) {
		Matrix m = null, r = null;
		try {
			m = createMatrix(size);
			if (m.getClass().getDeclaredMethod("plus", Double.TYPE) == null) {
				System.out.print("*");
				return -1;
			}
			long t0 = System.currentTimeMillis();
			r = m.plus(2);
			long t1 = System.currentTimeMillis();
			return t1 - t0;
		} catch (Throwable e) {
			System.out.print("*");
			return r == null ? -2 : -3;
		}
	}

	public long benchmarkPlusScalarOrig(long... size) {
		Matrix m = null, r = null;
		try {
			m = createMatrix(size);
			if (m.getClass().getDeclaredMethod("plus", Ret.class, Boolean.TYPE, Double.TYPE) == null) {
				System.out.print("*");
				return -1;
			}
			long t0 = System.currentTimeMillis();
			r = m.plus(Ret.ORIG, false, 2);
			long t1 = System.currentTimeMillis();
			return t1 - t0;
		} catch (Throwable e) {
			System.out.print("*");
			return r == null ? -2 : -3;
		}
	}

	public long benchmarkCopy(long... size) {
		Matrix m = null, r = null;
		try {
			m = createMatrix(size);
			if (m.getClass().getDeclaredMethod("copy") == null) {
				System.out.print("*");
				return -1;
			}
			long t0 = System.currentTimeMillis();
			r = m.copy();
			long t1 = System.currentTimeMillis();
			return t1 - t0;
		} catch (Throwable e) {
			System.out.print("*");
			return r == null ? -2 : -3;
		}
	}

	public long benchmarkTimesScalarNew(long... size) {
		Matrix m = null, r = null;
		try {
			m = createMatrix(size);
			if (m.getClass().getDeclaredMethod("times", Double.TYPE) == null) {
				System.out.print("*");
				return -1;
			}
			long t0 = System.currentTimeMillis();
			r = m.times(2);
			long t1 = System.currentTimeMillis();
			return t1 - t0;
		} catch (Throwable e) {
			System.out.print("*");
			return r == null ? -2 : -3;
		}
	}

	public long benchmarkTimesScalarOrig(long... size) {
		Matrix m = null, r = null;
		try {
			m = createMatrix(size);
			if (m.getClass().getDeclaredMethod("times", Ret.class, Boolean.TYPE, Double.TYPE) == null) {
				System.out.print("*");
				return -1;
			}
			long t0 = System.currentTimeMillis();
			r = m.times(Ret.ORIG, false, 2);
			long t1 = System.currentTimeMillis();
			return t1 - t0;
		} catch (Throwable e) {
			System.out.print("*");
			return r == null ? -2 : -3;
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
			return t1 - t0;
		} catch (Throwable e) {
			System.out.print("*");
			return r == null ? -2 : -3;
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
			return t1 - t0;
		} catch (Throwable e) {
			System.out.print("*");
			return r == null ? -2 : -3;
		}
	}

	public long benchmarkInv(long... size) {
		Matrix m = null, r = null;
		try {
			m = createMatrix(size);
			m.randn(Ret.ORIG);
			long t0 = System.currentTimeMillis();
			r = m.inv();
			long t1 = System.currentTimeMillis();
			return t1 - t0;
		} catch (Throwable e) {
			System.out.print("*");
			return r == null ? -2 : -3;
		}
	}

	public long benchmarkMtimesNew(long[] size0, long[] size1) {
		Matrix m0 = null, m1 = null, r = null;
		try {
			m0 = createMatrix(size0);
			m1 = createMatrix(size1);
			m0.randn(Ret.ORIG);
			m1.randn(Ret.ORIG);
			long t0 = System.currentTimeMillis();
			r = m0.mtimes(m1);
			long t1 = System.currentTimeMillis();
			return t1 - t0;
		} catch (Throwable e) {
			return r == null ? -2 : -3;
		}
	}

}
