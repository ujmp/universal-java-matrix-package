package org.ujmp.core.doublematrix.impl;

import junit.framework.TestCase;

import org.junit.Test;

public class TestBlockDenseDouble2DMatrix extends TestCase {

	@Test
	public void testThatTransposeColumnMajorMatrixGivesCorrectResult() throws Exception {
		BlockDenseDoubleMatrix2D blockMat = Fixture.createBlockRowLayoutWithGeneratedData(25, 15,
				10, BlockOrder.COLUMNMAJOR);
		transposeAndCompare(blockMat);
	}

	@Test
	public void testThatTransposeLargeMatrixGivesCorrectResult() throws Exception {
		BlockDenseDoubleMatrix2D blockMat = Fixture.createBlockRowLayoutWithGeneratedData(1024,
				897, 111, BlockOrder.ROWMAJOR);
		transposeAndCompare(blockMat);
	}

	@Test
	public void testThatTransposeMatrixGivesCorrectResult() throws Exception {
		BlockDenseDoubleMatrix2D blockMat = Fixture.createBlockRowLayoutWithGeneratedData(25, 15,
				5, BlockOrder.ROWMAJOR);
		DefaultDenseDoubleMatrix2D denseMat = new DefaultDenseDoubleMatrix2D(blockMat);
		System.out.println(blockMat);
		System.out.println();
		System.out.println(blockMat.transpose());

		Fixture.compare(blockMat, denseMat);
		Fixture.compare(blockMat.transpose(), denseMat.transpose());

		System.out.println();
		System.out.println(denseMat.transpose());

	}

	private void transposeAndCompare(BlockDenseDoubleMatrix2D blockMat) {
		DefaultDenseDoubleMatrix2D denseMat = new DefaultDenseDoubleMatrix2D(blockMat);
		Fixture.compare(blockMat, denseMat);
		Fixture.compare(blockMat.transpose(), denseMat.transpose());
	}

}
