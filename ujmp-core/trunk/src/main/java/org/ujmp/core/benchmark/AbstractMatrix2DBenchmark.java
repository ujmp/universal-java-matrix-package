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

public abstract class AbstractMatrix2DBenchmark {

	private static final int count = 10;

	private static final long[] size100x100 = new long[] { 100, 100 };

	private static final long[] size100x1000 = new long[] { 100, 1000 };

	private static final long[] size100x10000 = new long[] { 100, 10000 };

	private static final long[] size100x100000 = new long[] { 100, 100000 };

	private static final long[] size1000x100 = new long[] { 1000, 100 };

	private static final long[] size1000x1000 = new long[] { 1000, 1000 };

	private static final long[] size1000x10000 = new long[] { 1000, 10000 };

	private static final long[] size1000x100000 = new long[] { 1000, 100000 };

	private static final long[] size10000x100 = new long[] { 10000, 100 };

	private static final long[] size10000x1000 = new long[] { 10000, 1000 };

	private static final long[] size5000x5000 = new long[] { 5000, 5000 };

	private static final long[] size10000x10000 = new long[] { 10000, 10000 };

	private static final long[] size10000x100000 = new long[] { 10000, 100000 };

	private static final long[] size100000x100 = new long[] { 100000, 100 };

	private static final long[] size100000x1000 = new long[] { 100000, 1000 };

	private static final long[] size100000x10000 = new long[] { 100000, 10000 };

	private static final long[] size100000x100000 = new long[] { 100000, 100000 };

	public static final List<long[]> allSizes1 = Arrays
			.asList(new long[][] { size100x100, size100x1000, size100x10000, size100x100000,
					size1000x100, size1000x1000, size1000x10000, size1000x100000, size10000x100,
					size10000x1000, size10000x10000, size10000x100000, size100000x100,
					size100000x1000, size100000x10000, size100000x100000 });

	public static final List<long[]> allSizes = Arrays.asList(new long[][] { size5000x5000 });

	public static final List<long[][]> multSizes = Arrays.asList(new long[][][] { { size1000x1000,
			size1000x1000 } });

	public abstract Matrix createMatrix(long... size) throws MatrixException;

	public abstract Matrix createMatrix(Matrix source) throws MatrixException;

	public List<Matrix> run() throws Exception {
		System.out.println("===============================================================");
		System.out.println(createMatrix(1, 1).getClass().getSimpleName());
		System.out.println("===============================================================");

		List<Matrix> result = new ArrayList<Matrix>();

		init();
		// result.add(runBenchmarkCreate());
		// result.add(runBenchmarkCopy());
		// result.add(runBenchmarkPlusScalarNew());
		// result.add(runBenchmarkPlusScalarOrig());
		// result.add(runBenchmarkTimesScalarNew());
		result.add(runBenchmarkTransposeNew());
		result.add(runBenchmarkMtimesNew());

		System.out.println();

		return result;
	}

	public Matrix init() {
		Matrix result = MatrixFactory.zeros(count, allSizes.size());
		System.out.print("init: ");

		for (int i = 0; i < count; i++) {
			for (int s = 0; s < allSizes.size(); s++) {
				long[] size = allSizes.get(s);
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
		Matrix result = MatrixFactory.zeros(count, allSizes.size());
		System.out.print("create: ");

		for (int i = 0; i < count; i++) {
			for (int s = 0; s < allSizes.size(); s++) {
				long[] size = allSizes.get(s);
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
		Matrix result = MatrixFactory.zeros(count, allSizes.size());
		System.out.print("plus scalar (new matrix): ");

		for (int i = 0; i < count; i++) {
			for (int s = 0; s < allSizes.size(); s++) {
				long[] size = allSizes.get(s);
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
		Matrix result = MatrixFactory.zeros(count, allSizes.size());
		System.out.print("copy: ");

		for (int i = 0; i < count; i++) {
			for (int s = 0; s < allSizes.size(); s++) {
				long[] size = allSizes.get(s);
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
		Matrix result = MatrixFactory.zeros(count, allSizes.size());
		System.out.print("plus scalar (original matrix): ");

		for (int i = 0; i < count; i++) {
			for (int s = 0; s < allSizes.size(); s++) {
				long[] size = allSizes.get(s);
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
		Matrix result = MatrixFactory.zeros(count, allSizes.size());
		System.out.print("times scalar (new matrix): ");

		for (int i = 0; i < count; i++) {
			for (int s = 0; s < allSizes.size(); s++) {
				long[] size = allSizes.get(s);
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

	public Matrix runBenchmarkTransposeNew() {
		Matrix result = MatrixFactory.zeros(count, allSizes.size());
		System.out.print("transpose (new matrix): ");

		for (int i = 0; i < count; i++) {
			for (int s = 0; s < allSizes.size(); s++) {
				long[] size = allSizes.get(s);
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

	public Matrix runBenchmarkMtimesNew() {
		Matrix result = MatrixFactory.zeros(count, multSizes.size());
		System.out.print("mtimes (new matrix): ");

		for (int i = 0; i < count; i++) {
			for (int s = 0; s < multSizes.size(); s++) {
				long[][] sizes = multSizes.get(s);
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
			return -1;
		}
	}

	public long benchmarkPlusScalarNew(long... size) {
		Matrix m = null, r = null;
		try {
			m = createMatrix(size);
			if (m.getClass().getDeclaredMethod("plus", Double.TYPE) == null) {
				return -1;
			}
			long t0 = System.currentTimeMillis();
			r = m.plus(2);
			long t1 = System.currentTimeMillis();
			return t1 - t0;
		} catch (Throwable e) {
			return r == null ? -2 : -3;
		}
	}

	public long benchmarkPlusScalarOrig(long... size) {
		Matrix m = null, r = null;
		try {
			m = createMatrix(size);
			if (m.getClass().getDeclaredMethod("plus", Ret.class, Boolean.TYPE, Double.TYPE) == null) {
				return -1;
			}
			long t0 = System.currentTimeMillis();
			r = m.plus(Ret.ORIG, false, 2);
			long t1 = System.currentTimeMillis();
			return t1 - t0;
		} catch (Throwable e) {
			return r == null ? -2 : -3;
		}
	}

	public long benchmarkCopy(long... size) {
		Matrix m = null, r = null;
		try {
			m = createMatrix(size);
			if (m.getClass().getDeclaredMethod("copy") == null) {
				return -1;
			}
			long t0 = System.currentTimeMillis();
			r = m.copy();
			long t1 = System.currentTimeMillis();
			return t1 - t0;
		} catch (Throwable e) {
			return r == null ? -2 : -3;
		}
	}

	public long benchmarkTimesScalarNew(long... size) {
		Matrix m = null, r = null;
		try {
			m = createMatrix(size);
			if (m.getClass().getDeclaredMethod("times", Double.TYPE) == null) {
				return -1;
			}
			long t0 = System.currentTimeMillis();
			r = m.times(2);
			long t1 = System.currentTimeMillis();
			return t1 - t0;
		} catch (Throwable e) {
			return r == null ? -2 : -3;
		}
	}

	public long benchmarkTransposeNew(long... size) {
		Matrix m = null, r = null;
		try {
			m = createMatrix(size);
			m.randn(Ret.ORIG);
			if (m.getClass().getDeclaredMethod("transpose") == null) {
				return -1;
			}
			long t0 = System.currentTimeMillis();
			r = m.transpose();
			long t1 = System.currentTimeMillis();
			return t1 - t0;
		} catch (Throwable e) {
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
