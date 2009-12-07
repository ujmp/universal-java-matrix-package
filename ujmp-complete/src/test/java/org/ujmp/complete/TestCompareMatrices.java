package org.ujmp.complete;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.ujmp.colt.ColtDenseDoubleMatrix2D;
import org.ujmp.colt.ColtSparseDoubleMatrix2D;
import org.ujmp.commonsmath.CommonsMathArrayDenseDoubleMatrix2D;
import org.ujmp.commonsmath.CommonsMathBlockDenseDoubleMatrix2D;
import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.bigdecimalmatrix.impl.ArrayDenseBigDecimalMatrix2D;
import org.ujmp.core.bigdecimalmatrix.impl.DefaultDenseBigDecimalMatrix2D;
import org.ujmp.core.bigdecimalmatrix.impl.DefaultSparseBigDecimalMatrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.doublematrix.impl.ArrayDenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.impl.DefaultDenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.impl.DefaultSparseDoubleMatrix;
import org.ujmp.core.floatmatrix.impl.ArrayDenseFloatMatrix2D;
import org.ujmp.core.floatmatrix.impl.DefaultDenseFloatMatrix2D;
import org.ujmp.core.floatmatrix.impl.DefaultSparseFloatMatrix;
import org.ujmp.ejml.EJMLDenseDoubleMatrix2D;
import org.ujmp.jama.JamaDenseDoubleMatrix2D;
import org.ujmp.jampack.JampackDenseDoubleMatrix2D;
import org.ujmp.jlinalg.JLinalgDenseDoubleMatrix2D;
import org.ujmp.jmatrices.JMatricesDenseDoubleMatrix2D;
import org.ujmp.jsci.JSciDenseDoubleMatrix2D;
import org.ujmp.jscience.JScienceDenseDoubleMatrix2D;
import org.ujmp.mantissa.MantissaDenseDoubleMatrix2D;
import org.ujmp.mtj.MTJDenseDoubleMatrix2D;
import org.ujmp.ojalgo.OjalgoDenseDoubleMatrix2D;
import org.ujmp.orbital.OrbitalDenseDoubleMatrix2D;
import org.ujmp.owlpack.OwlpackDenseDoubleMatrix2D;
import org.ujmp.parallelcolt.ParallelColtDenseDoubleMatrix2D;
import org.ujmp.parallelcolt.ParallelColtSparseDoubleMatrix2D;
import org.ujmp.sst.SSTDenseDoubleMatrix;
import org.ujmp.vecmath.VecMathDenseDoubleMatrix2D;

public class TestCompareMatrices extends TestCase {

	private static final double TOLERANCE = 1e-4;

	public static List<Class<? extends Matrix>> ALLFLOATMATRIXCLASSES = null;

	static {
		ALLFLOATMATRIXCLASSES = new ArrayList<Class<? extends Matrix>>();
		ALLFLOATMATRIXCLASSES.add(DefaultDenseDoubleMatrix2D.class);
		ALLFLOATMATRIXCLASSES.add(ArrayDenseDoubleMatrix2D.class);
		ALLFLOATMATRIXCLASSES.add(DefaultSparseDoubleMatrix.class);
		ALLFLOATMATRIXCLASSES.add(DefaultDenseBigDecimalMatrix2D.class);
		ALLFLOATMATRIXCLASSES.add(ArrayDenseBigDecimalMatrix2D.class);
		ALLFLOATMATRIXCLASSES.add(DefaultSparseBigDecimalMatrix.class);
		ALLFLOATMATRIXCLASSES.add(DefaultDenseFloatMatrix2D.class);
		ALLFLOATMATRIXCLASSES.add(ArrayDenseFloatMatrix2D.class);
		ALLFLOATMATRIXCLASSES.add(DefaultSparseFloatMatrix.class);
		ALLFLOATMATRIXCLASSES.add(ColtDenseDoubleMatrix2D.class);
		ALLFLOATMATRIXCLASSES.add(ColtSparseDoubleMatrix2D.class);
		ALLFLOATMATRIXCLASSES.add(CommonsMathArrayDenseDoubleMatrix2D.class);
		ALLFLOATMATRIXCLASSES.add(CommonsMathBlockDenseDoubleMatrix2D.class);
		ALLFLOATMATRIXCLASSES.add(JamaDenseDoubleMatrix2D.class);
		ALLFLOATMATRIXCLASSES.add(JLinalgDenseDoubleMatrix2D.class);
		ALLFLOATMATRIXCLASSES.add(JMatricesDenseDoubleMatrix2D.class);
		ALLFLOATMATRIXCLASSES.add(JSciDenseDoubleMatrix2D.class);
		ALLFLOATMATRIXCLASSES.add(JScienceDenseDoubleMatrix2D.class);
		ALLFLOATMATRIXCLASSES.add(MantissaDenseDoubleMatrix2D.class);
		ALLFLOATMATRIXCLASSES.add(MTJDenseDoubleMatrix2D.class);
		ALLFLOATMATRIXCLASSES.add(OjalgoDenseDoubleMatrix2D.class);
		ALLFLOATMATRIXCLASSES.add(OrbitalDenseDoubleMatrix2D.class);
		ALLFLOATMATRIXCLASSES.add(OwlpackDenseDoubleMatrix2D.class);
		ALLFLOATMATRIXCLASSES.add(ParallelColtDenseDoubleMatrix2D.class);
		ALLFLOATMATRIXCLASSES.add(ParallelColtSparseDoubleMatrix2D.class);
		ALLFLOATMATRIXCLASSES.add(VecMathDenseDoubleMatrix2D.class);
		ALLFLOATMATRIXCLASSES.add(JampackDenseDoubleMatrix2D.class);

		// some libraries do not support Java 5
		if (!"1.5".equals(System.getProperty("java.specification.version"))) {
			ALLFLOATMATRIXCLASSES.add(SSTDenseDoubleMatrix.class);
			ALLFLOATMATRIXCLASSES.add(EJMLDenseDoubleMatrix2D.class);
		}

	}

	private Matrix getMatrix(Class<? extends Matrix> mclass, Matrix m) {
		try {
			Constructor<? extends Matrix> con = mclass.getConstructor(Matrix.class);
			return con.newInstance(m);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public void testMtimes() throws Exception {
		Matrix ref1 = MatrixFactory.randn(10, 5);
		Matrix ref2 = MatrixFactory.randn(5, 15);
		Matrix ref3 = ref1.mtimes(Ret.LINK, true, ref2);

		for (Class<? extends Matrix> mclass : ALLFLOATMATRIXCLASSES) {
			try {
				Matrix m1 = getMatrix(mclass, ref1);
				Matrix m2 = getMatrix(mclass, ref2);
				Matrix m3 = m1.mtimes(m2);
				assertEquals(mclass.toString(), 0.0, ref3.minus(m3).doubleValue(), TOLERANCE);
			} catch (Throwable e) {
				assertEquals(mclass.toString() + ": " + e, true, false);
			}
		}
	}

	public void testPlus() throws Exception {
		Matrix ref1 = MatrixFactory.randn(10, 10);
		Matrix ref2 = MatrixFactory.randn(10, 10);
		Matrix ref3 = ref1.plus(Ret.LINK, true, ref2);

		for (Class<? extends Matrix> mclass : ALLFLOATMATRIXCLASSES) {
			try {
				Matrix m1 = getMatrix(mclass, ref1);
				Matrix m2 = getMatrix(mclass, ref2);
				Matrix m3 = m1.plus(m2);
				assertEquals(mclass.toString(), 0.0, ref3.minus(m3).doubleValue(), TOLERANCE);
			} catch (Throwable e) {
				assertEquals(mclass.toString() + ": " + e, true, false);
			}
		}
	}

	public void testMinus() throws Exception {
		Matrix ref1 = MatrixFactory.randn(10, 10);
		Matrix ref2 = MatrixFactory.randn(10, 10);
		Matrix ref3 = ref1.minus(Ret.LINK, true, ref2);

		for (Class<? extends Matrix> mclass : ALLFLOATMATRIXCLASSES) {
			try {
				Matrix m1 = getMatrix(mclass, ref1);
				Matrix m2 = getMatrix(mclass, ref2);
				Matrix m3 = m1.minus(m2);
				assertEquals(mclass.toString(), 0.0, ref3.minus(m3).doubleValue(), TOLERANCE);
			} catch (Throwable e) {
				assertEquals(mclass.toString() + ": " + e, true, false);
			}
		}
	}

	public void testTimes() throws Exception {
		Matrix ref1 = MatrixFactory.randn(10, 10);
		Matrix ref2 = MatrixFactory.randn(10, 10);
		Matrix ref3 = ref1.times(Ret.LINK, true, ref2);

		for (Class<? extends Matrix> mclass : ALLFLOATMATRIXCLASSES) {
			try {
				Matrix m1 = getMatrix(mclass, ref1);
				Matrix m2 = getMatrix(mclass, ref2);
				Matrix m3 = m1.times(m2);
				assertEquals(mclass.toString(), 0.0, ref3.minus(m3).doubleValue(), TOLERANCE);
			} catch (Throwable e) {
				assertEquals(mclass.toString() + ": " + e, true, false);
			}
		}

	}

	public void testDivide() throws Exception {
		Matrix ref1 = MatrixFactory.randn(10, 10);
		Matrix ref2 = MatrixFactory.randn(10, 10);
		Matrix ref3 = ref1.divide(Ret.LINK, true, ref2);

		for (Class<? extends Matrix> mclass : ALLFLOATMATRIXCLASSES) {
			try {
				Matrix m1 = getMatrix(mclass, ref1);
				Matrix m2 = getMatrix(mclass, ref2);
				Matrix m3 = m1.divide(m2);
				assertEquals(mclass.toString(), 0.0, ref3.minus(m3).doubleValue(), TOLERANCE);
			} catch (Throwable e) {
				assertEquals(mclass.toString() + ": " + e, true, false);
			}
		}
	}

	public void testInverse() throws Exception {
		Matrix ref1 = MatrixFactory.zeros(3, 3);
		ref1.setAsDouble(1.0, 0, 0);
		ref1.setAsDouble(2.0, 1, 0);
		ref1.setAsDouble(3.0, 2, 0);
		ref1.setAsDouble(4.0, 0, 1);
		ref1.setAsDouble(1.0, 1, 1);
		ref1.setAsDouble(2.0, 2, 1);
		ref1.setAsDouble(3.0, 0, 2);
		ref1.setAsDouble(7.0, 1, 2);
		ref1.setAsDouble(1.0, 2, 2);
		Matrix ref2 = ref1.inv();

		for (Class<? extends Matrix> mclass : ALLFLOATMATRIXCLASSES) {
			try {
				Matrix m1 = getMatrix(mclass, ref1);

				if (m1.getClass().getName().startsWith("org.ujmp.owlpack.")) {
					return;
				}

				Matrix m2 = m1.inv();
				Matrix m3 = m1.pinv();
				Matrix m4 = m1.ginv();
				assertEquals(mclass.toString(), 0.0, ref2.minus(m2).doubleValue(), TOLERANCE);
				assertEquals(mclass.toString(), 0.0, ref2.minus(m3).doubleValue(), TOLERANCE);
				assertEquals(mclass.toString(), 0.0, ref2.minus(m4).doubleValue(), TOLERANCE);
			} catch (Throwable e) {
				assertEquals(mclass.toString() + ": " + e, true, false);
			}
		}
	}

	public void testTranspose() throws Exception {
		Matrix ref1 = MatrixFactory.randn(5, 5);
		Matrix ref2 = ref1.transpose(Ret.LINK);

		for (Class<? extends Matrix> mclass : ALLFLOATMATRIXCLASSES) {
			try {
				Matrix m1 = getMatrix(mclass, ref1);
				Matrix m2 = m1.transpose();
				assertEquals(mclass.toString(), 0.0, ref2.minus(m2).doubleValue(), TOLERANCE);
			} catch (Throwable e) {
				assertEquals(mclass.toString() + ": " + e, true, false);
			}
		}
	}

}
