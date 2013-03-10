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

package org.ujmp.core;

import java.util.Date;

import org.ujmp.core.bigdecimalmatrix.BigDecimalMatrix;
import org.ujmp.core.bigintegermatrix.BigIntegerMatrix;
import org.ujmp.core.booleanmatrix.BooleanMatrix;
import org.ujmp.core.booleanmatrix.DenseBooleanMatrix2D;
import org.ujmp.core.booleanmatrix.impl.DefaultDenseBooleanMatrix2D;
import org.ujmp.core.bytematrix.ByteMatrix;
import org.ujmp.core.bytematrix.DenseByteMatrix2D;
import org.ujmp.core.bytematrix.impl.ArrayDenseByteMatrix2D;
import org.ujmp.core.charmatrix.CharMatrix;
import org.ujmp.core.charmatrix.DenseCharMatrix2D;
import org.ujmp.core.charmatrix.impl.ArrayDenseCharMatrix2D;
import org.ujmp.core.datematrix.DateMatrix;
import org.ujmp.core.datematrix.DenseDateMatrix2D;
import org.ujmp.core.datematrix.impl.SimpleDenseDateMatrix2D;
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.DoubleMatrix;
import org.ujmp.core.doublematrix.SparseDoubleMatrix;
import org.ujmp.core.doublematrix.impl.ArrayDenseDoubleMatrix2D;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.floatmatrix.DenseFloatMatrix2D;
import org.ujmp.core.floatmatrix.FloatMatrix;
import org.ujmp.core.floatmatrix.impl.ArrayDenseFloatMatrix2D;
import org.ujmp.core.intmatrix.DenseIntMatrix2D;
import org.ujmp.core.intmatrix.IntMatrix;
import org.ujmp.core.intmatrix.impl.SimpleDenseIntMatrix2D;
import org.ujmp.core.longmatrix.DenseLongMatrix2D;
import org.ujmp.core.longmatrix.LongMatrix;
import org.ujmp.core.longmatrix.impl.DefaultDenseLongMatrix2D;
import org.ujmp.core.longmatrix.impl.SimpleDenseLongMatrix2D;
import org.ujmp.core.matrix.factory.AbstractMatrixFactory;
import org.ujmp.core.objectmatrix.DenseObjectMatrix2D;
import org.ujmp.core.objectmatrix.ObjectMatrix;
import org.ujmp.core.objectmatrix.impl.SimpleDenseObjectMatrix2D;
import org.ujmp.core.shortmatrix.DenseShortMatrix2D;
import org.ujmp.core.shortmatrix.ShortMatrix;
import org.ujmp.core.shortmatrix.impl.SimpleDenseShortMatrix2D;
import org.ujmp.core.stringmatrix.DenseStringMatrix2D;
import org.ujmp.core.stringmatrix.StringMatrix;
import org.ujmp.core.stringmatrix.impl.SimpleDenseStringMatrix2D;

public class MatrixFactoryTemp extends AbstractMatrixFactory<Matrix> {
	private static final long serialVersionUID = -6788016781517917785L;

	public Matrix zeros(long... size) throws MatrixException {
		if (size.length == 2) {
			return DenseDoubleMatrix2D.Factory.zeros(size[ROW], size[COLUMN]);
		} else if (size.length > 2) {
			return DoubleMatrix.Factory.zeros(size);
		} else {
			throw new MatrixException("Size must be at least 2-dimensional");
		}
	}

	public final Matrix zeros(ValueType valueType, long... size) {
		switch (size.length) {
		case 0:
			throw new MatrixException("Size not defined");
		case 1:
			throw new MatrixException("Size must be at least 2-dimensional");
		default:
			switch (valueType) {
			case BIGDECIMAL:
				return BigDecimalMatrix.Factory.zeros(size);
			case BIGINTEGER:
				return BigIntegerMatrix.Factory.zeros(size);
			case BOOLEAN:
				return BooleanMatrix.Factory.zeros(size);
			case BYTE:
				return ByteMatrix.Factory.zeros(size);
			case CHAR:
				return CharMatrix.Factory.zeros(size);
			case DATE:
				return DateMatrix.Factory.zeros(size);
			case DOUBLE:
				return DoubleMatrix.Factory.zeros(size);
			case FLOAT:
				return FloatMatrix.Factory.zeros(size);
			case INT:
				return IntMatrix.Factory.zeros(size);
			case LONG:
				return LongMatrix.Factory.zeros(size);
			case OBJECT:
				return ObjectMatrix.Factory.zeros(size);
			case SHORT:
				return ShortMatrix.Factory.zeros(size);
			case STRING:
				return StringMatrix.Factory.zeros(size);
			default:
				throw new RuntimeException("unknown value type: " + valueType);
			}
		}
	}

	public Matrix like(Matrix matrix, long rowCount, long columnCount) {
		return matrix.getFactory().zeros(rowCount, columnCount);
	}

	public Matrix sparse(ValueType valueType, long... size) {
		switch (valueType) {
		case DOUBLE:
			return SparseDoubleMatrix.Factory.zeros(size);
		default:
			throw new RuntimeException("not implemented");
		}
	}

	public final Matrix importFromArray(boolean[]... values) {
		return linkToArray(values).clone();
	}

	public final Matrix importFromArray(byte[]... values) {
		return linkToArray(values).clone();
	}

	public final Matrix importFromArray(char[]... values) {
		return linkToArray(values).clone();
	}

	public final Matrix importFromArray(Date[]... values) {
		return linkToArray(values).clone();
	}

	public final Matrix importFromArray(double[]... values) {
		return linkToArray(values).clone();
	}

	public final Matrix importFromArray(float[]... values) {
		return linkToArray(values).clone();
	}

	public final Matrix importFromArray(int[]... values) {
		return linkToArray(values).clone();
	}

	public final Matrix importFromArray(long[]... values) {
		return linkToArray(values).clone();
	}

	public final Matrix importFromArray(Object[]... values) {
		return linkToArray(values).clone();
	}

	public final Matrix importFromArray(short[]... values) {
		return linkToArray(values).clone();
	}

	public final Matrix importFromArray(String[]... values) {
		return linkToArray(values).clone();
	}

	public final DenseBooleanMatrix2D linkToArray(boolean[]... values) {
		return new DefaultDenseBooleanMatrix2D(values);
	}

	public final DenseBooleanMatrix2D linkToArray(boolean... values) {
		return new DefaultDenseBooleanMatrix2D(values);
	}

	public final DenseByteMatrix2D linkToArray(byte[]... values) {
		return new ArrayDenseByteMatrix2D(values);
	}

	public final DenseByteMatrix2D linkToArray(byte... values) {
		return new ArrayDenseByteMatrix2D(values);
	}

	public final DenseCharMatrix2D linkToArray(char[]... values) {
		return new ArrayDenseCharMatrix2D(values);
	}

	public final DenseCharMatrix2D linkToArray(char... values) {
		return new ArrayDenseCharMatrix2D(values);
	}

	public final DenseDateMatrix2D linkToArray(Date[]... values) {
		return new SimpleDenseDateMatrix2D(values);
	}

	public final DenseDateMatrix2D linkToArray(Date... values) {
		return new SimpleDenseDateMatrix2D(values);
	}

	public final DenseDoubleMatrix2D linkToArray(double[]... values) {
		return new ArrayDenseDoubleMatrix2D(values);
	}

	public final DenseDoubleMatrix2D linkToArray(double... values) {
		return new ArrayDenseDoubleMatrix2D(values);
	}

	public final DenseFloatMatrix2D linkToArray(float[]... values) {
		return new ArrayDenseFloatMatrix2D(values);
	}

	public final DenseFloatMatrix2D linkToArray(float... values) {
		return new ArrayDenseFloatMatrix2D(values);
	}

	public final DenseIntMatrix2D linkToArray(int[]... values) {
		return new SimpleDenseIntMatrix2D(values);
	}

	public final DenseIntMatrix2D linkToArray(int... values) {
		return new SimpleDenseIntMatrix2D(values);
	}

	public final DenseLongMatrix2D linkToArray(long[]... values) {
		return new SimpleDenseLongMatrix2D(values);
	}

	public final DenseLongMatrix2D linkToArray(long... values) {
		return new DefaultDenseLongMatrix2D(values);
	}

	public final DenseObjectMatrix2D linkToArray(Object[]... values) {
		return new SimpleDenseObjectMatrix2D(values);
	}

	public final DenseObjectMatrix2D linkToArray(Object... values) {
		return new SimpleDenseObjectMatrix2D(values);
	}

	public final DenseShortMatrix2D linkToArray(short[]... values) {
		return new SimpleDenseShortMatrix2D(values);
	}

	public final DenseShortMatrix2D linkToArray(short... values) {
		return new SimpleDenseShortMatrix2D(values);
	}

	public final DenseStringMatrix2D linkToArray(String[]... values) {
		return new SimpleDenseStringMatrix2D(values);
	}

	public final DenseStringMatrix2D linkToArray(String... values) {
		return new SimpleDenseStringMatrix2D(values);
	}

}
