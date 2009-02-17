package org.ujmp.core.benchmark;

import java.util.Arrays;
import java.util.List;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.exceptions.MatrixException;

public abstract class AbstractMatrix2DBenchmark {

	private static final int count = 1;

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

	private static final long[] size10000x10000 = new long[] { 10000, 10000 };

	private static final long[] size10000x100000 = new long[] { 10000, 100000 };

	private static final long[] size100000x100 = new long[] { 100000, 100 };

	private static final long[] size100000x1000 = new long[] { 100000, 1000 };

	private static final long[] size100000x10000 = new long[] { 100000, 10000 };

	private static final long[] size100000x100000 = new long[] { 100000, 100000 };

	private static final List<long[]> allSizes = Arrays
			.asList(new long[][] { size100x100, size100x1000, size100x10000, size100x100000,
					size1000x100, size1000x1000, size1000x10000, size1000x100000, size10000x100,
					size10000x1000, size10000x10000, size10000x100000, size100000x100,
					size100000x1000, size100000x10000, size100000x100000 });

	public abstract Matrix createMatrix(long... size) throws MatrixException;

	public abstract Matrix createMatrix(Matrix source) throws MatrixException;

	public void run() throws Exception {
		System.out.println("===============================================================");
		System.out.println(createMatrix(1, 1).getClass().getSimpleName());
		System.out.println("===============================================================");
		// init();
		// runBenchmarkCreate();
		runBenchmarkPlusScalarNew();
		runBenchmarkPlusScalarOrig();
		runBenchmarkTimesScalarNew();
		runBenchmarkTransposeNew();
		System.out.println();
	}

	public void init() {
		System.out.print("init: ");
		for (int i = 0; i < count; i++) {
			System.out.print(".");
			try {
				MatrixFactory.zeros(size10000x1000);
			} catch (Exception e) {
			}
			try {
				MatrixFactory.zeros(size100000x1000);
			} catch (Exception e) {
			}
		}
		System.out.println();
	}

	public void runBenchmarkCreate() {
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
		System.out.println(result);
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
		System.out.println(result);
		return result;
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
		System.out.println(result);
		return result;
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
		System.out.println(result);
		return result;
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
		System.out.println(result);
		return result;
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

}
