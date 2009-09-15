package org.ujmp.complete;

import java.lang.reflect.Constructor;

import junit.framework.TestCase;

import org.ujmp.colt.ColtDenseDoubleMatrix2D;
import org.ujmp.colt.ColtSparseDoubleMatrix2D;
import org.ujmp.commonsmath.CommonsMathDenseDoubleMatrix2D;
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
import org.ujmp.jama.JamaDenseDoubleMatrix2D;
import org.ujmp.jampack.JampackDenseDoubleMatrix2D;
import org.ujmp.jmatrices.JMatricesDenseDoubleMatrix2D;
import org.ujmp.jsci.JSciDenseDoubleMatrix2D;
import org.ujmp.jscience.JScienceDenseDoubleMatrix2D;
import org.ujmp.mantissa.MantissaDenseDoubleMatrix2D;
import org.ujmp.mtj.MTJDenseDoubleMatrix2D;
import org.ujmp.ojalgo.OjalgoDenseDoubleMatrix2D;
import org.ujmp.parallelcolt.ParallelColtDenseDoubleMatrix2D;
import org.ujmp.parallelcolt.ParallelColtSparseDoubleMatrix2D;
import org.ujmp.sst.SSTDenseDoubleMatrix;
import org.ujmp.vecmath.VecMathDenseDoubleMatrix2D;

@SuppressWarnings("unchecked")
public class TestCompareMatrices extends TestCase {

	private static final double TOLERANCE = 1e-4;

	public static Class<Matrix>[] ALLFLOATMATRIXCLASSES = new Class[] {
			DefaultDenseDoubleMatrix2D.class, ArrayDenseDoubleMatrix2D.class,
			DefaultSparseDoubleMatrix.class, DefaultDenseBigDecimalMatrix2D.class,
			ArrayDenseBigDecimalMatrix2D.class, DefaultSparseBigDecimalMatrix.class,
			DefaultDenseFloatMatrix2D.class, ArrayDenseFloatMatrix2D.class,
			DefaultSparseFloatMatrix.class, ColtDenseDoubleMatrix2D.class,
			ColtSparseDoubleMatrix2D.class, CommonsMathDenseDoubleMatrix2D.class,
			JamaDenseDoubleMatrix2D.class, JMatricesDenseDoubleMatrix2D.class,
			JSciDenseDoubleMatrix2D.class, JScienceDenseDoubleMatrix2D.class,
			MantissaDenseDoubleMatrix2D.class, MTJDenseDoubleMatrix2D.class,
			OjalgoDenseDoubleMatrix2D.class, ParallelColtDenseDoubleMatrix2D.class,
			ParallelColtSparseDoubleMatrix2D.class, SSTDenseDoubleMatrix.class,
			VecMathDenseDoubleMatrix2D.class, JampackDenseDoubleMatrix2D.class };

	private Matrix getMatrix(Class<Matrix> mclass, Matrix m) {
		try {
			Constructor<Matrix> con = mclass.getConstructor(Matrix.class);
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

		for (Class<Matrix> mclass : ALLFLOATMATRIXCLASSES) {
			Matrix m1 = getMatrix(mclass, ref1);
			Matrix m2 = getMatrix(mclass, ref2);
			Matrix m3 = m1.mtimes(m2);
			assertEquals(mclass.toString(), 0.0, ref3.minus(m3).doubleValue(), TOLERANCE);
		}
	}

	public void testPlus() throws Exception {
		Matrix ref1 = MatrixFactory.randn(10, 10);
		Matrix ref2 = MatrixFactory.randn(10, 10);
		Matrix ref3 = ref1.plus(Ret.LINK, true, ref2);

		for (Class<Matrix> mclass : ALLFLOATMATRIXCLASSES) {
			Matrix m1 = getMatrix(mclass, ref1);
			Matrix m2 = getMatrix(mclass, ref2);
			Matrix m3 = m1.plus(m2);
			assertEquals(mclass.toString(), 0.0, ref3.minus(m3).doubleValue(), TOLERANCE);
		}
	}

	public void testMinus() throws Exception {
		Matrix ref1 = MatrixFactory.randn(10, 10);
		Matrix ref2 = MatrixFactory.randn(10, 10);
		Matrix ref3 = ref1.minus(Ret.LINK, true, ref2);

		for (Class<Matrix> mclass : ALLFLOATMATRIXCLASSES) {
			Matrix m1 = getMatrix(mclass, ref1);
			Matrix m2 = getMatrix(mclass, ref2);
			Matrix m3 = m1.minus(m2);
			assertEquals(mclass.toString(), 0.0, ref3.minus(m3).doubleValue(), TOLERANCE);
		}
	}

	public void testTimes() throws Exception {
		Matrix ref1 = MatrixFactory.randn(10, 10);
		Matrix ref2 = MatrixFactory.randn(10, 10);
		Matrix ref3 = ref1.times(Ret.LINK, true, ref2);

		for (Class<Matrix> mclass : ALLFLOATMATRIXCLASSES) {
			Matrix m1 = getMatrix(mclass, ref1);
			Matrix m2 = getMatrix(mclass, ref2);
			Matrix m3 = m1.times(m2);
			assertEquals(mclass.toString(), 0.0, ref3.minus(m3).doubleValue(), TOLERANCE);
		}
	}

	public void testDivide() throws Exception {
		Matrix ref1 = MatrixFactory.randn(10, 10);
		Matrix ref2 = MatrixFactory.randn(10, 10);
		Matrix ref3 = ref1.divide(Ret.LINK, true, ref2);

		for (Class<Matrix> mclass : ALLFLOATMATRIXCLASSES) {
			Matrix m1 = getMatrix(mclass, ref1);
			Matrix m2 = getMatrix(mclass, ref2);
			Matrix m3 = m1.divide(m2);
			assertEquals(mclass.toString(), 0.0, ref3.minus(m3).doubleValue(), TOLERANCE);
		}
	}

	public void testInverse() throws Exception {
		Matrix ref1 = MatrixFactory.randn(10, 10);
		Matrix ref2 = ref1.inv();

		for (Class<Matrix> mclass : ALLFLOATMATRIXCLASSES) {
			Matrix m1 = getMatrix(mclass, ref1);
			Matrix m2 = m1.inv();
			Matrix m3 = m1.pinv();
			Matrix m4 = m1.ginv();
			assertEquals(mclass.toString(), 0.0, ref2.minus(m2).doubleValue(), TOLERANCE);
			assertEquals(mclass.toString(), 0.0, ref2.minus(m3).doubleValue(), TOLERANCE);
			assertEquals(mclass.toString(), 0.0, ref2.minus(m4).doubleValue(), TOLERANCE);
		}
	}

	public void testTranspose() throws Exception {
		Matrix ref1 = MatrixFactory.randn(10, 10);
		Matrix ref2 = ref1.transpose(Ret.LINK);

		for (Class<Matrix> mclass : ALLFLOATMATRIXCLASSES) {
			Matrix m1 = getMatrix(mclass, ref1);
			Matrix m2 = m1.transpose();
			assertEquals(mclass.toString(), 0.0, ref2.minus(m2).doubleValue(), TOLERANCE);
		}
	}

}
