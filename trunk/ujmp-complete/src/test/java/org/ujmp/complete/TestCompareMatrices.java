package org.ujmp.complete;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.ujmp.colt.ColtDenseDoubleMatrix2D;
import org.ujmp.colt.ColtSparseDoubleMatrix2D;
import org.ujmp.commonsmath.CommonsMathArrayDenseDoubleMatrix2D;
import org.ujmp.commonsmath.CommonsMathBlockDenseDoubleMatrix2D;
import org.ujmp.core.Matrix;
import org.ujmp.core.bigdecimalmatrix.impl.ArrayDenseBigDecimalMatrix2D;
import org.ujmp.core.bigdecimalmatrix.impl.DefaultDenseBigDecimalMatrix2D;
import org.ujmp.core.bigdecimalmatrix.impl.DefaultSparseBigDecimalMatrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.impl.ArrayDenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.impl.BlockDenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.impl.DefaultDenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.impl.DefaultDenseDoubleMatrixMultiD;
import org.ujmp.core.doublematrix.impl.DefaultSparseDoubleMatrix;
import org.ujmp.core.doublematrix.impl.MortonDenseDoubleMartrix2D;
import org.ujmp.core.floatmatrix.impl.ArrayDenseFloatMatrix2D;
import org.ujmp.core.floatmatrix.impl.DefaultDenseFloatMatrix2D;
import org.ujmp.core.floatmatrix.impl.DefaultSparseFloatMatrix;
import org.ujmp.core.util.MathUtil;
import org.ujmp.ejml.EJMLDenseDoubleMatrix2D;
import org.ujmp.jama.JamaDenseDoubleMatrix2D;
import org.ujmp.jampack.JampackDenseDoubleMatrix2D;
import org.ujmp.jblas.JBlasDenseDoubleMatrix2D;
import org.ujmp.jlinalg.JLinAlgDenseDoubleMatrix2D;
import org.ujmp.jmatrices.JMatricesDenseDoubleMatrix2D;
import org.ujmp.jsci.JSciDenseDoubleMatrix2D;
import org.ujmp.jscience.JScienceDenseDoubleMatrix2D;
import org.ujmp.mantissa.MantissaDenseDoubleMatrix2D;
import org.ujmp.mtj.MTJDenseDoubleMatrix2D;
import org.ujmp.ojalgo.OjalgoDenseDoubleMatrix2D;
import org.ujmp.orbital.OrbitalDenseDoubleMatrix2D;
import org.ujmp.parallelcolt.ParallelColtDenseDoubleMatrix2D;
import org.ujmp.parallelcolt.ParallelColtSparseDoubleMatrix2D;
import org.ujmp.sst.SSTDenseDoubleMatrix2D;
import org.ujmp.vecmath.VecMathDenseDoubleMatrix2D;

public class TestCompareMatrices {

	private static final double TOLERANCE = 1e-4;

	public static List<Class<? extends Matrix>> ALLFLOATMATRIXCLASSES = null;

	static {
		ALLFLOATMATRIXCLASSES = new ArrayList<Class<? extends Matrix>>();
		ALLFLOATMATRIXCLASSES.add(DefaultDenseDoubleMatrix2D.class);
		ALLFLOATMATRIXCLASSES.add(ArrayDenseDoubleMatrix2D.class);
		ALLFLOATMATRIXCLASSES.add(BlockDenseDoubleMatrix2D.class);
		ALLFLOATMATRIXCLASSES.add(MortonDenseDoubleMartrix2D.class);
		ALLFLOATMATRIXCLASSES.add(DefaultDenseDoubleMatrixMultiD.class);
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
		ALLFLOATMATRIXCLASSES.add(JLinAlgDenseDoubleMatrix2D.class);
		ALLFLOATMATRIXCLASSES.add(JBlasDenseDoubleMatrix2D.class);
		ALLFLOATMATRIXCLASSES.add(JMatricesDenseDoubleMatrix2D.class);
		ALLFLOATMATRIXCLASSES.add(JSciDenseDoubleMatrix2D.class);
		ALLFLOATMATRIXCLASSES.add(JScienceDenseDoubleMatrix2D.class);
		ALLFLOATMATRIXCLASSES.add(MantissaDenseDoubleMatrix2D.class);
		ALLFLOATMATRIXCLASSES.add(MTJDenseDoubleMatrix2D.class);
		ALLFLOATMATRIXCLASSES.add(OjalgoDenseDoubleMatrix2D.class);
		ALLFLOATMATRIXCLASSES.add(OrbitalDenseDoubleMatrix2D.class);
		ALLFLOATMATRIXCLASSES.add(ParallelColtDenseDoubleMatrix2D.class);
		ALLFLOATMATRIXCLASSES.add(ParallelColtSparseDoubleMatrix2D.class);
		ALLFLOATMATRIXCLASSES.add(VecMathDenseDoubleMatrix2D.class);
		ALLFLOATMATRIXCLASSES.add(JampackDenseDoubleMatrix2D.class);

		// some libraries do not support Java 5
		if (!"1.5".equals(System.getProperty("java.specification.version"))) {
			ALLFLOATMATRIXCLASSES.add(SSTDenseDoubleMatrix2D.class);
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

	@Test
	public void testMtimesSmall() throws Exception {
		Matrix ref1 = DenseDoubleMatrix2D.Factory.randn(10, 5);
		Matrix ref2 = DenseDoubleMatrix2D.Factory.randn(5, 15);
		Matrix ref3 = ref1.mtimes(Ret.LINK, true, ref2);

		for (Class<? extends Matrix> mclass : ALLFLOATMATRIXCLASSES) {
			try {
				// JBlas not supported for 64 bit on windows
				if (System.getProperty("os.name").toLowerCase().contains("windows")
						&& System.getProperty("java.vm.name").contains("64")
						&& mclass.getName().startsWith("org.ujmp.jblas")) {
					continue;
				}
				Matrix m1 = getMatrix(mclass, ref1);
				Matrix m2 = getMatrix(mclass, ref2);
				Matrix m3 = m1.mtimes(m2);
				assertEquals(mclass.toString(), 0.0, ref3.minus(m3).getRMS(), TOLERANCE);
			} catch (Throwable e) {
				assertEquals(mclass.toString() + ": " + e, true, false);
			}
		}
	}

	@Test
	public void testMtimesLarge() throws Exception {
		Matrix ref1 = DenseDoubleMatrix2D.Factory.randn(101, 100);
		Matrix ref2 = DenseDoubleMatrix2D.Factory.randn(100, 102);
		Matrix ref3 = ref1.mtimes(Ret.LINK, true, ref2);

		for (Class<? extends Matrix> mclass : ALLFLOATMATRIXCLASSES) {
			try {
				// JBlas not supported for 64 bit on windows
				if (System.getProperty("os.name").toLowerCase().contains("windows")
						&& System.getProperty("java.vm.name").contains("64")
						&& mclass.getName().startsWith("org.ujmp.jblas")) {
					continue;
				}
				Matrix m1 = getMatrix(mclass, ref1);
				Matrix m2 = getMatrix(mclass, ref2);
				Matrix m3 = m1.mtimes(m2);
				if (mclass.toString().contains("JLinAlg")) {
					// result is not exact, but ok
					assertEquals(mclass.toString(), 0.0, ref3.minus(m3).getRMS(), 0.05);
				} else {
					assertEquals(mclass.toString(), 0.0, ref3.minus(m3).getRMS(), TOLERANCE);
				}
			} catch (Throwable e) {
				assertEquals(mclass.toString() + ": " + e, true, false);
			}
		}
	}

	@Test
	public void testPlusMatrixSmall() throws Exception {
		Matrix ref1 = DenseDoubleMatrix2D.Factory.randn(11, 10);
		Matrix ref2 = DenseDoubleMatrix2D.Factory.randn(11, 10);
		Matrix ref3 = ref1.plus(Ret.LINK, true, ref2);

		for (Class<? extends Matrix> mclass : ALLFLOATMATRIXCLASSES) {
			try {
				Matrix m1 = getMatrix(mclass, ref1);
				Matrix m2 = getMatrix(mclass, ref2);
				Matrix m3 = m1.plus(m2);
				assertEquals(mclass.toString(), 0.0, ref3.minus(m3).getRMS(), TOLERANCE);
			} catch (Throwable e) {
				assertEquals(mclass.toString() + ": " + e, true, false);
			}
		}
	}

	@Test
	public void testPlusMatrixLarge() throws Exception {
		Matrix ref1 = DenseDoubleMatrix2D.Factory.randn(101, 100);
		Matrix ref2 = DenseDoubleMatrix2D.Factory.randn(101, 100);
		Matrix ref3 = ref1.plus(Ret.LINK, true, ref2);

		for (Class<? extends Matrix> mclass : ALLFLOATMATRIXCLASSES) {
			try {
				Matrix m1 = getMatrix(mclass, ref1);
				Matrix m2 = getMatrix(mclass, ref2);
				Matrix m3 = m1.plus(m2);
				assertEquals(mclass.toString(), 0.0, ref3.minus(m3).getRMS(), TOLERANCE);
			} catch (Throwable e) {
				assertEquals(mclass.toString() + ": " + e, true, false);
			}
		}
	}

	@Test
	public void testMinusMatrixSmall() throws Exception {
		Matrix ref1 = DenseDoubleMatrix2D.Factory.randn(11, 10);
		Matrix ref2 = DenseDoubleMatrix2D.Factory.randn(11, 10);
		Matrix ref3 = ref1.minus(Ret.LINK, true, ref2);

		for (Class<? extends Matrix> mclass : ALLFLOATMATRIXCLASSES) {
			try {
				Matrix m1 = getMatrix(mclass, ref1);
				Matrix m2 = getMatrix(mclass, ref2);
				Matrix m3 = m1.minus(m2);
				assertEquals(mclass.toString(), 0.0, ref3.minus(m3).getRMS(), TOLERANCE);
			} catch (Throwable e) {
				assertEquals(mclass.toString() + ": " + e, true, false);
			}
		}
	}

	@Test
	public void testMinusMatrixLarge() throws Exception {
		Matrix ref1 = DenseDoubleMatrix2D.Factory.randn(101, 100);
		Matrix ref2 = DenseDoubleMatrix2D.Factory.randn(101, 100);
		Matrix ref3 = ref1.minus(Ret.LINK, true, ref2);

		for (Class<? extends Matrix> mclass : ALLFLOATMATRIXCLASSES) {
			try {
				Matrix m1 = getMatrix(mclass, ref1);
				Matrix m2 = getMatrix(mclass, ref2);
				Matrix m3 = m1.minus(m2);
				assertEquals(mclass.toString(), 0.0, ref3.minus(m3).getRMS(), TOLERANCE);
			} catch (Throwable e) {
				assertEquals(mclass.toString() + ": " + e, true, false);
			}
		}
	}

	@Test
	public void testTimesMatrixSmall() throws Exception {
		Matrix ref1 = DenseDoubleMatrix2D.Factory.randn(11, 10);
		Matrix ref2 = DenseDoubleMatrix2D.Factory.randn(11, 10);
		Matrix ref3 = ref1.times(Ret.LINK, true, ref2);

		for (Class<? extends Matrix> mclass : ALLFLOATMATRIXCLASSES) {
			try {
				Matrix m1 = getMatrix(mclass, ref1);
				Matrix m2 = getMatrix(mclass, ref2);
				Matrix m3 = m1.times(m2);
				assertEquals(mclass.toString(), 0.0, ref3.minus(m3).getRMS(), TOLERANCE);
			} catch (Throwable e) {
				assertEquals(mclass.toString() + ": " + e, true, false);
			}
		}
	}

	@Test
	public void testTimesMatrixLarge() throws Exception {
		Matrix ref1 = DenseDoubleMatrix2D.Factory.randn(101, 100);
		Matrix ref2 = DenseDoubleMatrix2D.Factory.randn(101, 100);
		Matrix ref3 = ref1.times(Ret.LINK, true, ref2);

		for (Class<? extends Matrix> mclass : ALLFLOATMATRIXCLASSES) {
			try {
				Matrix m1 = getMatrix(mclass, ref1);
				Matrix m2 = getMatrix(mclass, ref2);
				Matrix m3 = m1.times(m2);
				assertEquals(mclass.toString(), 0.0, ref3.minus(m3).getRMS(), TOLERANCE);
			} catch (Throwable e) {
				assertEquals(mclass.toString() + ": " + e, true, false);
			}
		}
	}

	@Test
	public void testTimesScalarSmall() throws Exception {
		Matrix ref1 = DenseDoubleMatrix2D.Factory.randn(11, 10);
		double ref2 = MathUtil.nextDouble();
		Matrix ref3 = ref1.times(Ret.LINK, true, ref2);

		for (Class<? extends Matrix> mclass : ALLFLOATMATRIXCLASSES) {
			try {
				Matrix m1 = getMatrix(mclass, ref1);
				Matrix m3 = m1.times(ref2);
				assertEquals(mclass.toString(), 0.0, ref3.minus(m3).getRMS(), TOLERANCE);
			} catch (Throwable e) {
				assertEquals(mclass.toString() + ": " + e, true, false);
			}
		}
	}

	@Test
	public void testPlusScalarSmall() throws Exception {
		Matrix ref1 = DenseDoubleMatrix2D.Factory.randn(11, 10);
		double ref2 = MathUtil.nextDouble();
		Matrix ref3 = ref1.plus(Ret.LINK, true, ref2);

		for (Class<? extends Matrix> mclass : ALLFLOATMATRIXCLASSES) {
			try {
				Matrix m1 = getMatrix(mclass, ref1);
				Matrix m3 = m1.plus(ref2);
				Matrix delta = ref3.minus(m3);
				assertEquals(mclass.toString(), 0.0, delta.getRMS(), TOLERANCE);
			} catch (Throwable e) {
				assertEquals(mclass.toString() + ": " + e, true, false);
			}
		}
	}

	@Test
	public void testPlusScalarLarge() throws Exception {
		Matrix ref1 = DenseDoubleMatrix2D.Factory.randn(101, 100);
		double ref2 = MathUtil.nextDouble();
		Matrix ref3 = ref1.plus(Ret.LINK, true, ref2);

		for (Class<? extends Matrix> mclass : ALLFLOATMATRIXCLASSES) {
			try {
				Matrix m1 = getMatrix(mclass, ref1);
				Matrix m3 = m1.plus(ref2);
				assertEquals(mclass.toString(), 0.0, ref3.minus(m3).getRMS(), TOLERANCE);
			} catch (Throwable e) {
				assertEquals(mclass.toString() + ": " + e, true, false);
			}
		}
	}

	@Test
	public void testMinusScalarLarge() throws Exception {
		Matrix ref1 = DenseDoubleMatrix2D.Factory.randn(101, 100);
		double ref2 = MathUtil.nextDouble();
		Matrix ref3 = ref1.minus(Ret.LINK, true, ref2);

		for (Class<? extends Matrix> mclass : ALLFLOATMATRIXCLASSES) {
			try {
				Matrix m1 = getMatrix(mclass, ref1);
				Matrix m3 = m1.minus(ref2);
				assertEquals(mclass.toString(), 0.0, ref3.minus(m3).getRMS(), TOLERANCE);
			} catch (Throwable e) {
				assertEquals(mclass.toString() + ": " + e, true, false);
			}
		}
	}

	@Test
	public void testMinusScalarSmall() throws Exception {
		Matrix ref1 = DenseDoubleMatrix2D.Factory.randn(11, 10);
		double ref2 = MathUtil.nextDouble();
		Matrix ref3 = ref1.minus(Ret.LINK, true, ref2);

		for (Class<? extends Matrix> mclass : ALLFLOATMATRIXCLASSES) {
			try {
				Matrix m1 = getMatrix(mclass, ref1);
				Matrix m3 = m1.minus(ref2);
				assertEquals(mclass.toString(), 0.0, ref3.minus(m3).getRMS(), TOLERANCE);
			} catch (Throwable e) {
				assertEquals(mclass.toString() + ": " + e, true, false);
			}
		}
	}

	@Test
	public void testDivideScalarSmall() throws Exception {
		Matrix ref1 = DenseDoubleMatrix2D.Factory.randn(11, 10);
		double ref2 = MathUtil.nextDouble();
		Matrix ref3 = ref1.divide(Ret.LINK, true, ref2);

		for (Class<? extends Matrix> mclass : ALLFLOATMATRIXCLASSES) {
			try {
				Matrix m1 = getMatrix(mclass, ref1);
				Matrix m3 = m1.divide(ref2);
				assertEquals(mclass.toString(), 0.0, ref3.minus(m3).getRMS(), TOLERANCE);
			} catch (Throwable e) {
				assertEquals(mclass.toString() + ": " + e, true, false);
			}
		}
	}

	@Test
	public void testDivideScalarLarge() throws Exception {
		Matrix ref1 = DenseDoubleMatrix2D.Factory.randn(101, 100);
		double ref2 = MathUtil.nextDouble();
		Matrix ref3 = ref1.divide(Ret.LINK, true, ref2);

		for (Class<? extends Matrix> mclass : ALLFLOATMATRIXCLASSES) {
			try {
				Matrix m1 = getMatrix(mclass, ref1);
				Matrix m3 = m1.divide(ref2);
				assertEquals(mclass.toString(), 0.0, ref3.minus(m3).getRMS(), TOLERANCE);
			} catch (Throwable e) {
				assertEquals(mclass.toString() + ": " + e, true, false);
			}
		}
	}

	@Test
	public void testTimesScalarLarge() throws Exception {
		Matrix ref1 = DenseDoubleMatrix2D.Factory.randn(101, 100);
		double ref2 = MathUtil.nextDouble();
		Matrix ref3 = ref1.times(Ret.LINK, true, ref2);

		for (Class<? extends Matrix> mclass : ALLFLOATMATRIXCLASSES) {
			try {
				Matrix m1 = getMatrix(mclass, ref1);
				Matrix m3 = m1.times(ref2);
				assertEquals(mclass.toString(), 0.0, ref3.minus(m3).getRMS(), TOLERANCE);
			} catch (Throwable e) {
				assertEquals(mclass.toString() + ": " + e, true, false);
			}
		}
	}

	@Test
	public void testDivideMatrixSmall() throws Exception {
		Matrix ref1 = DenseDoubleMatrix2D.Factory.randn(11, 10);
		Matrix ref2 = DenseDoubleMatrix2D.Factory.randn(11, 10);
		Matrix ref3 = ref1.divide(Ret.LINK, true, ref2);

		for (Class<? extends Matrix> mclass : ALLFLOATMATRIXCLASSES) {
			try {
				Matrix m1 = getMatrix(mclass, ref1);
				Matrix m2 = getMatrix(mclass, ref2);
				Matrix m3 = m1.divide(m2);
				assertEquals(mclass.toString(), 0.0, ref3.minus(m3).getRMS(), TOLERANCE);
			} catch (Throwable e) {
				assertEquals(mclass.toString() + ": " + e, true, false);
			}
		}
	}

	@Test
	public void testDivideMatrixLarge() throws Exception {
		Matrix ref1 = DenseDoubleMatrix2D.Factory.randn(101, 100);
		Matrix ref2 = DenseDoubleMatrix2D.Factory.randn(101, 100);
		Matrix ref3 = ref1.divide(Ret.LINK, true, ref2);

		for (Class<? extends Matrix> mclass : ALLFLOATMATRIXCLASSES) {
			try {
				Matrix m1 = getMatrix(mclass, ref1);
				Matrix m2 = getMatrix(mclass, ref2);
				Matrix m3 = m1.divide(m2);
				assertEquals(mclass.toString(), 0.0, ref3.minus(m3).getRMS(), TOLERANCE);
			} catch (Throwable e) {
				assertEquals(mclass.toString() + ": " + e, true, false);
			}
		}
	}

	@Test
	public void testInverse() throws Exception {
		Matrix ref1 = Matrix.Factory.zeros(3, 3);
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
				// JBlas not supported for 64 bit on windows
				if (System.getProperty("os.name").toLowerCase().contains("windows")
						&& System.getProperty("java.vm.name").contains("64")
						&& mclass.getName().startsWith("org.ujmp.jblas")) {
					continue;
				}

				Matrix m1 = getMatrix(mclass, ref1);

				if (m1.getClass().getName().startsWith("org.ujmp.owlpack.")) {
					return;
				}

				Matrix m2 = m1.inv();
				Matrix m3 = m1.pinv();
				Matrix m4 = m1.ginv();
				assertEquals(mclass.toString(), 0.0, ref2.minus(m2).getRMS(), TOLERANCE);
				assertEquals(mclass.toString(), 0.0, ref2.minus(m3).getRMS(), TOLERANCE);
				assertEquals(mclass.toString(), 0.0, ref2.minus(m4).getRMS(), TOLERANCE);
			} catch (Throwable e) {
				assertEquals(mclass.toString() + ": " + e, true, false);
			}
		}
	}

	@Test
	public void testTransposeSmallSquare() throws Exception {
		Matrix ref1 = DenseDoubleMatrix2D.Factory.randn(10, 10);
		Matrix ref2 = ref1.transpose(Ret.LINK);

		for (Class<? extends Matrix> mclass : ALLFLOATMATRIXCLASSES) {
			try {
				Matrix m1 = getMatrix(mclass, ref1);
				Matrix m2 = m1.transpose();
				assertEquals(mclass.toString(), 0.0, ref2.minus(m2).getRMS(), TOLERANCE);
			} catch (Throwable e) {
				assertEquals(mclass.toString() + ": " + e, true, false);
			}
		}
	}

	@Test
	public void testTransposeSmallTall() throws Exception {
		Matrix ref1 = DenseDoubleMatrix2D.Factory.randn(15, 10);
		Matrix ref2 = ref1.transpose(Ret.LINK);

		for (Class<? extends Matrix> mclass : ALLFLOATMATRIXCLASSES) {
			try {
				Matrix m1 = getMatrix(mclass, ref1);
				Matrix m2 = m1.transpose();
				assertEquals(mclass.toString(), 0.0, ref2.minus(m2).getRMS(), TOLERANCE);
			} catch (Throwable e) {
				assertEquals(mclass.toString() + ": " + e, true, false);
			}
		}
	}

	@Test
	public void testTransposeSmallWide() throws Exception {
		Matrix ref1 = DenseDoubleMatrix2D.Factory.randn(10, 15);
		Matrix ref2 = ref1.transpose(Ret.LINK);

		for (Class<? extends Matrix> mclass : ALLFLOATMATRIXCLASSES) {
			try {
				Matrix m1 = getMatrix(mclass, ref1);
				Matrix m2 = m1.transpose();
				assertEquals(mclass.toString(), 0.0, ref2.minus(m2).getRMS(), TOLERANCE);
			} catch (Throwable e) {
				assertEquals(mclass.toString() + ": " + e, true, false);
			}
		}
	}

	@Test
	public void testTransposeLargeSquare() throws Exception {
		Matrix ref1 = DenseDoubleMatrix2D.Factory.randn(101, 101);
		Matrix ref2 = ref1.transpose(Ret.LINK);

		for (Class<? extends Matrix> mclass : ALLFLOATMATRIXCLASSES) {
			try {
				Matrix m1 = getMatrix(mclass, ref1);
				Matrix m2 = m1.transpose();
				assertEquals(mclass.toString(), 0.0, ref2.minus(m2).getRMS(), TOLERANCE);
			} catch (Throwable e) {
				assertEquals(mclass.toString() + ": " + e, true, false);
			}
		}
	}

	@Test
	public void testTransposeLargeTall() throws Exception {
		Matrix ref1 = DenseDoubleMatrix2D.Factory.randn(101, 100);
		Matrix ref2 = ref1.transpose(Ret.LINK);

		for (Class<? extends Matrix> mclass : ALLFLOATMATRIXCLASSES) {
			try {
				Matrix m1 = getMatrix(mclass, ref1);
				Matrix m2 = m1.transpose();
				assertEquals(mclass.toString(), 0.0, ref2.minus(m2).getRMS(), TOLERANCE);
			} catch (Throwable e) {
				assertEquals(mclass.toString() + ": " + e, true, false);
			}
		}
	}

	@Test
	public void testTransposeLargeWide() throws Exception {
		Matrix ref1 = DenseDoubleMatrix2D.Factory.randn(100, 101);
		Matrix ref2 = ref1.transpose(Ret.LINK);

		for (Class<? extends Matrix> mclass : ALLFLOATMATRIXCLASSES) {
			try {
				Matrix m1 = getMatrix(mclass, ref1);
				Matrix m2 = m1.transpose();
				assertEquals(mclass.toString(), 0.0, ref2.minus(m2).getRMS(), TOLERANCE);
			} catch (Throwable e) {
				assertEquals(mclass.toString() + ": " + e, true, false);
			}
		}
	}

}
