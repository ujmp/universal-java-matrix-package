/*
 * Copyright (C) 2008-2015 by Holger Arndt
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

package org.ujmp.core.matrix.factory;

import java.io.File;
import java.io.IOException;

import org.ujmp.core.DenseMatrix;
import org.ujmp.core.Matrix;
import org.ujmp.core.SparseMatrix;
import org.ujmp.core.bigdecimalmatrix.impl.DefaultDenseBigDecimalMatrix2D;
import org.ujmp.core.bigdecimalmatrix.impl.DefaultSparseBigDecimalMatrix;
import org.ujmp.core.bigintegermatrix.impl.DefaultDenseBigIntegerMatrix2D;
import org.ujmp.core.bigintegermatrix.impl.DefaultSparseBigIntegerMatrix;
import org.ujmp.core.booleanmatrix.impl.ArrayDenseBooleanMatrix2D;
import org.ujmp.core.booleanmatrix.impl.DefaultSparseBooleanMatrix;
import org.ujmp.core.bytematrix.impl.DefaultDenseByteMatrix2D;
import org.ujmp.core.bytematrix.impl.DefaultSparseByteMatrix;
import org.ujmp.core.charmatrix.impl.DefaultDenseCharMatrix2D;
import org.ujmp.core.charmatrix.impl.DefaultSparseCharMatrix;
import org.ujmp.core.doublematrix.impl.DefaultDenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.impl.DefaultDenseDoubleMatrixMultiD;
import org.ujmp.core.doublematrix.impl.DefaultSparseDoubleMatrix;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.floatmatrix.impl.DefaultDenseFloatMatrix2D;
import org.ujmp.core.floatmatrix.impl.DefaultSparseFloatMatrix;
import org.ujmp.core.intmatrix.impl.DefaultDenseIntMatrix2D;
import org.ujmp.core.intmatrix.impl.DefaultSparseIntMatrix;
import org.ujmp.core.longmatrix.impl.DefaultDenseLongMatrix2D;
import org.ujmp.core.longmatrix.impl.DefaultSparseLongMatrix;
import org.ujmp.core.objectmatrix.ObjectMatrix;
import org.ujmp.core.objectmatrix.impl.DefaultSparseObjectMatrix;
import org.ujmp.core.shortmatrix.impl.DefaultDenseShortMatrix2D;
import org.ujmp.core.shortmatrix.impl.DefaultSparseShortMatrix;
import org.ujmp.core.stringmatrix.impl.DefaultDenseStringMatrix2D;
import org.ujmp.core.stringmatrix.impl.DefaultSparseStringMatrix;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.SerializationUtil;

public class DefaultDenseMatrixFactory extends AbstractMatrixFactory<DenseMatrix> {

	public final DenseMatrix zeros(long... size) {
		if (size.length == 2) {
			return new DefaultDenseDoubleMatrix2D(MathUtil.longToInt(size[ROW]),
					MathUtil.longToInt(size[COLUMN]));
		} else if (size.length > 2) {
			return new DefaultDenseDoubleMatrixMultiD(size);
		} else {
			throw new RuntimeException("Size must be at least 2-dimensional");
		}
	}

	public final DenseMatrix zeros(ValueType valueType, long... size) {
		switch (size.length) {
		case 0:
			throw new RuntimeException("Size not defined");
		case 1:
			throw new RuntimeException("Size must be at least 2-dimensional");
		default:
			switch (valueType) {
			case BIGDECIMAL:
				return new DefaultDenseBigDecimalMatrix2D(MathUtil.longToInt(size[ROW]),
						MathUtil.longToInt(size[COLUMN]));
			case BIGINTEGER:
				return new DefaultDenseBigIntegerMatrix2D(MathUtil.longToInt(size[ROW]),
						MathUtil.longToInt(size[COLUMN]));
			case BOOLEAN:
				return new ArrayDenseBooleanMatrix2D(MathUtil.longToInt(size[ROW]),
						MathUtil.longToInt(size[COLUMN]));
			case BYTE:
				return new DefaultDenseByteMatrix2D(MathUtil.longToInt(size[ROW]),
						MathUtil.longToInt(size[COLUMN]));
			case CHAR:
				return new DefaultDenseCharMatrix2D(MathUtil.longToInt(size[ROW]),
						MathUtil.longToInt(size[COLUMN]));
			case DOUBLE:
				if (size.length == 2) {
					return new DefaultDenseDoubleMatrix2D(MathUtil.longToInt(size[ROW]),
							MathUtil.longToInt(size[COLUMN]));
				} else {
					return new DefaultDenseDoubleMatrixMultiD(size);
				}
			case FLOAT:
				return new DefaultDenseFloatMatrix2D(MathUtil.longToInt(size[ROW]),
						MathUtil.longToInt(size[COLUMN]));
			case INT:
				return new DefaultDenseIntMatrix2D(MathUtil.longToInt(size[ROW]),
						MathUtil.longToInt(size[COLUMN]));
			case LONG:
				return new DefaultDenseLongMatrix2D(MathUtil.longToInt(size[ROW]),
						MathUtil.longToInt(size[COLUMN]));
			case OBJECT:
				return ObjectMatrix.Factory.zeros(size);
			case SHORT:
				return new DefaultDenseShortMatrix2D(MathUtil.longToInt(size[ROW]),
						MathUtil.longToInt(size[COLUMN]));
			case STRING:
				return new DefaultDenseStringMatrix2D(MathUtil.longToInt(size[ROW]),
						MathUtil.longToInt(size[COLUMN]));
			default:
				throw new RuntimeException("unknown value type: " + valueType);
			}
		}
	}

	public final SparseMatrix sparse(ValueType valueType, long... size) {
		switch (size.length) {
		case 0:
			throw new RuntimeException("Size not defined");
		case 1:
			throw new RuntimeException("Size must be at least 2-dimensional");
		default:
			switch (valueType) {
			case BIGDECIMAL:
				return new DefaultSparseBigDecimalMatrix(size);
			case BIGINTEGER:
				return new DefaultSparseBigIntegerMatrix(size);
			case BOOLEAN:
				return new DefaultSparseBooleanMatrix(size);
			case BYTE:
				return new DefaultSparseByteMatrix(size);
			case CHAR:
				return new DefaultSparseCharMatrix(size);
			case DOUBLE:
				return new DefaultSparseDoubleMatrix(size);
			case FLOAT:
				return new DefaultSparseFloatMatrix(size);
			case INT:
				return new DefaultSparseIntMatrix(size);
			case LONG:
				return new DefaultSparseLongMatrix(size);
			case OBJECT:
				return new DefaultSparseObjectMatrix(size);
			case SHORT:
				return new DefaultSparseShortMatrix(size);
			case STRING:
				return new DefaultSparseStringMatrix(size);
			default:
				throw new RuntimeException("unknown value type: " + valueType);
			}
		}
	}

	public DenseMatrix zeros(long rows, long columns) {
		return zeros(new long[] { rows, columns });
	}

	public Matrix load(File file) throws IOException, ClassNotFoundException {
		return (Matrix) SerializationUtil.loadCompressed(file);
	}

	public Matrix load(String filename) throws IOException, ClassNotFoundException {
		return load(new File(filename));
	}

}
