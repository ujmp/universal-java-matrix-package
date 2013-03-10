/*
 * Copyright (C) 2008-2013 by Holger Arndt
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

package org.ujmp.core.calculation;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.HasColumnMajorDoubleArray1D;
import org.ujmp.core.interfaces.HasRowMajorDoubleArray2D;
import org.ujmp.core.matrix.DenseMatrix;
import org.ujmp.core.matrix.DenseMatrix2D;
import org.ujmp.core.matrix.SparseMatrix;
import org.ujmp.core.util.VerifyUtil;
import org.ujmp.core.util.concurrent.PFor;

public class Transpose {

	public static final TransposeCalculation<Matrix, Matrix> MATRIX = new TransposeMatrix();

	public static final TransposeCalculation<DenseMatrix, DenseMatrix> DENSEMATRIX = new TransposeDenseMatrix();

	public static final TransposeCalculation<DenseMatrix2D, DenseMatrix2D> DENSEMATRIX2D = new TransposeDenseMatrix2D();

	public static final TransposeCalculation<DenseDoubleMatrix2D, DenseDoubleMatrix2D> DENSEDOUBLEMATRIX2D = new TransposeDenseDoubleMatrix2D();

	public static final TransposeCalculation<SparseMatrix, SparseMatrix> SPARSEMATRIX = new TransposeSparseMatrix();

}

class TransposeMatrix implements TransposeCalculation<Matrix, Matrix> {

	public final void calc(final Matrix source, final Matrix target) {
		if (source == target) {
			throw new MatrixException("cannot transpose into original matrix");
		}
		if (source instanceof DenseDoubleMatrix2D && target instanceof DenseDoubleMatrix2D) {
			Transpose.DENSEDOUBLEMATRIX2D.calc((DenseDoubleMatrix2D) source,
					(DenseDoubleMatrix2D) target);
		} else if (source instanceof DenseMatrix2D && target instanceof DenseMatrix2D) {
			Transpose.DENSEMATRIX2D.calc((DenseMatrix2D) source, (DenseMatrix2D) target);
		} else if (source instanceof DenseMatrix && target instanceof DenseMatrix) {
			Transpose.DENSEMATRIX.calc((DenseMatrix) source, (DenseMatrix) target);
		} else if (source instanceof SparseMatrix && target instanceof SparseMatrix) {
			Transpose.SPARSEMATRIX.calc((SparseMatrix) source, (SparseMatrix) target);
		} else {
			VerifyUtil.assert2D(source);
			VerifyUtil.assert2D(target);
			VerifyUtil.assertEquals(source.getRowCount(), target.getColumnCount(),
					"matrices have wrong size");
			VerifyUtil.assertEquals(source.getColumnCount(), target.getRowCount(),
					"matrices have wrong size");
			for (long[] c : source.allCoordinates()) {
				Object o = source.getAsObject(c);
				target.setAsObject(o, Coordinates.transpose(c));
			}
		}
	}
};

class TransposeDenseMatrix implements TransposeCalculation<DenseMatrix, DenseMatrix> {

	public final void calc(final DenseMatrix source, final DenseMatrix target) {
		if (source instanceof DenseMatrix2D && target instanceof DenseMatrix2D) {
			Transpose.DENSEMATRIX2D.calc((DenseMatrix2D) source, (DenseMatrix2D) target);
		} else {
			VerifyUtil.assert2D(source);
			VerifyUtil.assert2D(target);
			VerifyUtil.assertEquals(source.getRowCount(), target.getColumnCount(),
					"matrices have wrong size");
			VerifyUtil.assertEquals(source.getColumnCount(), target.getRowCount(),
					"matrices have wrong size");
			for (long[] c : source.allCoordinates()) {
				Object o = source.getAsObject(c);
				target.setAsObject(o, Coordinates.transpose(c));
			}
		}
	}
};

class TransposeSparseMatrix implements TransposeCalculation<SparseMatrix, SparseMatrix> {

	public final void calc(final SparseMatrix source, final SparseMatrix target) {
		VerifyUtil.assert2D(source);
		VerifyUtil.assert2D(target);
		VerifyUtil.assertEquals(source.getRowCount(), target.getColumnCount(),
				"matrices have wrong size");
		VerifyUtil.assertEquals(source.getColumnCount(), target.getRowCount(),
				"matrices have wrong size");
		for (long[] c : source.availableCoordinates()) {
			Object o = source.getAsObject(c);
			target.setAsObject(o, Coordinates.transpose(c));
		}
	}
};

class TransposeDenseMatrix2D implements TransposeCalculation<DenseMatrix2D, DenseMatrix2D> {

	public final void calc(final DenseMatrix2D source, final DenseMatrix2D target) {
		if (source instanceof DenseDoubleMatrix2D && target instanceof DenseDoubleMatrix2D) {
			Transpose.DENSEDOUBLEMATRIX2D.calc((DenseDoubleMatrix2D) source,
					(DenseDoubleMatrix2D) target);
		} else {
			VerifyUtil.assert2D(source);
			VerifyUtil.assert2D(target);
			VerifyUtil.assertEquals(source.getRowCount(), target.getColumnCount(),
					"matrices have wrong size");
			VerifyUtil.assertEquals(source.getColumnCount(), target.getRowCount(),
					"matrices have wrong size");
			for (int r = (int) source.getRowCount(); --r != -1;) {
				for (int c = (int) source.getColumnCount(); --c != -1;) {
					Object o = source.getAsObject(r, c);
					target.setAsObject(o, c, r);
				}
			}
		}
	}
};

class TransposeDenseDoubleMatrix2D implements
		TransposeCalculation<DenseDoubleMatrix2D, DenseDoubleMatrix2D> {

	public final void calc(final DenseDoubleMatrix2D source, final DenseDoubleMatrix2D target) {
		if (source instanceof HasColumnMajorDoubleArray1D
				&& target instanceof HasColumnMajorDoubleArray1D) {
			calc((int) source.getRowCount(), (int) source.getColumnCount(),
					((HasColumnMajorDoubleArray1D) source).getColumnMajorDoubleArray1D(),
					((HasColumnMajorDoubleArray1D) target).getColumnMajorDoubleArray1D());
		} else if (source instanceof HasRowMajorDoubleArray2D
				&& target instanceof HasRowMajorDoubleArray2D) {
			calc(((HasRowMajorDoubleArray2D) source).getRowMajorDoubleArray2D(),
					((HasRowMajorDoubleArray2D) target).getRowMajorDoubleArray2D());
		} else {
			VerifyUtil.assert2D(source);
			VerifyUtil.assert2D(target);
			VerifyUtil.assertEquals(source.getRowCount(), target.getColumnCount(),
					"matrices have wrong size");
			VerifyUtil.assertEquals(source.getColumnCount(), target.getRowCount(),
					"matrices have wrong size");
			for (int r = (int) source.getRowCount(); --r != -1;) {
				for (int c = (int) source.getColumnCount(); --c != -1;) {
					target.setDouble(source.getDouble(r, c), c, r);
				}
			}
		}
	}

	private final void calc(final double[][] source, final double[][] target) {
		VerifyUtil.assertNotNull(source, "source cannot be null");
		VerifyUtil.assertNotNull(target, "target cannot be null");
		VerifyUtil.assertNotNull(source[0], "source must be 2d");
		VerifyUtil.assertNotNull(target[0], "target must be 2d");
		VerifyUtil.assertEquals(source.length, target.length, "matrices have wrong size");
		VerifyUtil.assertEquals(source[0].length, target[0].length, "matrices have wrong size");
		final int retcols = source.length;
		final int retrows = source[0].length;
		if (retcols * retrows > 10000) {
			new PFor(0, retrows - 1) {
				@Override
				public void step(int i) {
					for (int c = 0; c < retcols; c++) {
						target[i][c] = source[c][i];
					}
				}
			};
		} else {
			for (int r = 0; r < retrows; r++) {
				for (int c = 0; c < retcols; c++) {
					target[r][c] = source[c][r];
				}
			}
		}
	}

	private final void calc(final int rows, final int cols, final double[] source,
			final double[] target) {
		VerifyUtil.assertNotNull(source, "source cannot be null");
		VerifyUtil.assertNotNull(target, "target cannot be null");
		VerifyUtil.assertEquals(source.length, target.length, "matrices have different sizes");
		if (source.length > 10000) {
			new PFor(0, rows - 1) {

				@Override
				public void step(int i) {
					for (int r = 0; r < cols; r++) {
						target[i * cols + r] = source[r * rows + i];
					}
				}
			};
		} else {
			for (int c = 0; c < rows; c++) {
				for (int r = 0; r < cols; r++) {
					target[c * cols + r] = source[r * rows + c];
				}
			}
		}
	}
};
