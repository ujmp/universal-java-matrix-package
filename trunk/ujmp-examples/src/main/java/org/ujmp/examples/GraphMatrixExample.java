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

package org.ujmp.examples;

import org.ujmp.core.DenseMatrix;
import org.ujmp.core.DenseMatrix2D;
import org.ujmp.core.Matrix;
import org.ujmp.core.Matrix2D;
import org.ujmp.core.SparseMatrix;
import org.ujmp.core.SparseMatrix2D;
import org.ujmp.core.bigdecimalmatrix.BaseBigDecimalMatrix;
import org.ujmp.core.bigdecimalmatrix.BigDecimalMatrix2D;
import org.ujmp.core.bigdecimalmatrix.DenseBigDecimalMatrix;
import org.ujmp.core.bigdecimalmatrix.DenseBigDecimalMatrix2D;
import org.ujmp.core.bigdecimalmatrix.SparseBigDecimalMatrix;
import org.ujmp.core.bigdecimalmatrix.SparseBigDecimalMatrix2D;
import org.ujmp.core.bigintegermatrix.BigIntegerMatrix;
import org.ujmp.core.bigintegermatrix.BigIntegerMatrix2D;
import org.ujmp.core.bigintegermatrix.DenseBigIntegerMatrix;
import org.ujmp.core.bigintegermatrix.DenseBigIntegerMatrix2D;
import org.ujmp.core.bigintegermatrix.SparseBigIntegerMatrix;
import org.ujmp.core.bigintegermatrix.SparseBigIntegerMatrix2D;
import org.ujmp.core.booleanmatrix.BooleanMatrix;
import org.ujmp.core.booleanmatrix.BooleanMatrix2D;
import org.ujmp.core.booleanmatrix.DenseBooleanMatrix;
import org.ujmp.core.booleanmatrix.DenseBooleanMatrix2D;
import org.ujmp.core.booleanmatrix.SparseBooleanMatrix;
import org.ujmp.core.booleanmatrix.SparseBooleanMatrix2D;
import org.ujmp.core.bytearraymatrix.ByteArrayMatrix;
import org.ujmp.core.bytearraymatrix.ByteArrayMatrix2D;
import org.ujmp.core.bytearraymatrix.DenseByteArrayMatrix;
import org.ujmp.core.bytearraymatrix.DenseByteArrayMatrix2D;
import org.ujmp.core.bytearraymatrix.SparseByteArrayMatrix;
import org.ujmp.core.bytearraymatrix.SparseByteArrayMatrix2D;
import org.ujmp.core.bytematrix.ByteMatrix;
import org.ujmp.core.bytematrix.ByteMatrix2D;
import org.ujmp.core.bytematrix.DenseByteMatrix;
import org.ujmp.core.bytematrix.DenseByteMatrix2D;
import org.ujmp.core.bytematrix.SparseByteMatrix;
import org.ujmp.core.bytematrix.SparseByteMatrix2D;
import org.ujmp.core.charmatrix.CharMatrix;
import org.ujmp.core.charmatrix.CharMatrix2D;
import org.ujmp.core.charmatrix.DenseCharMatrix;
import org.ujmp.core.charmatrix.DenseCharMatrix2D;
import org.ujmp.core.charmatrix.SparseCharMatrix;
import org.ujmp.core.charmatrix.SparseCharMatrix2D;
import org.ujmp.core.doublematrix.DenseDoubleMatrix;
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.DoubleMatrix;
import org.ujmp.core.doublematrix.DoubleMatrix2D;
import org.ujmp.core.doublematrix.SparseDoubleMatrix;
import org.ujmp.core.doublematrix.SparseDoubleMatrix2D;
import org.ujmp.core.floatmatrix.DenseFloatMatrix;
import org.ujmp.core.floatmatrix.DenseFloatMatrix2D;
import org.ujmp.core.floatmatrix.FloatMatrix;
import org.ujmp.core.floatmatrix.FloatMatrix2D;
import org.ujmp.core.floatmatrix.SparseFloatMatrix;
import org.ujmp.core.floatmatrix.SparseFloatMatrix2D;
import org.ujmp.core.genericmatrix.DenseGenericMatrix;
import org.ujmp.core.genericmatrix.DenseGenericMatrix2D;
import org.ujmp.core.genericmatrix.GenericMatrix;
import org.ujmp.core.genericmatrix.GenericMatrix2D;
import org.ujmp.core.genericmatrix.SparseGenericMatrix;
import org.ujmp.core.genericmatrix.SparseGenericMatrix2D;
import org.ujmp.core.graphmatrix.DefaultGraphMatrix;
import org.ujmp.core.graphmatrix.GraphMatrix;
import org.ujmp.core.intmatrix.DenseIntMatrix;
import org.ujmp.core.intmatrix.DenseIntMatrix2D;
import org.ujmp.core.intmatrix.IntMatrix;
import org.ujmp.core.intmatrix.IntMatrix2D;
import org.ujmp.core.intmatrix.SparseIntMatrix;
import org.ujmp.core.intmatrix.SparseIntMatrix2D;
import org.ujmp.core.longmatrix.DenseLongMatrix;
import org.ujmp.core.longmatrix.DenseLongMatrix2D;
import org.ujmp.core.longmatrix.LongMatrix;
import org.ujmp.core.longmatrix.LongMatrix2D;
import org.ujmp.core.longmatrix.SparseLongMatrix;
import org.ujmp.core.longmatrix.SparseLongMatrix2D;
import org.ujmp.core.objectmatrix.DenseObjectMatrix;
import org.ujmp.core.objectmatrix.DenseObjectMatrix2D;
import org.ujmp.core.objectmatrix.ObjectMatrix;
import org.ujmp.core.objectmatrix.ObjectMatrix2D;
import org.ujmp.core.objectmatrix.SparseObjectMatrix;
import org.ujmp.core.objectmatrix.SparseObjectMatrix2D;
import org.ujmp.core.shortmatrix.DenseShortMatrix;
import org.ujmp.core.shortmatrix.DenseShortMatrix2D;
import org.ujmp.core.shortmatrix.ShortMatrix;
import org.ujmp.core.shortmatrix.ShortMatrix2D;
import org.ujmp.core.shortmatrix.SparseShortMatrix;
import org.ujmp.core.shortmatrix.SparseShortMatrix2D;
import org.ujmp.core.stringmatrix.DenseStringMatrix;
import org.ujmp.core.stringmatrix.DenseStringMatrix2D;
import org.ujmp.core.stringmatrix.SparseStringMatrix;
import org.ujmp.core.stringmatrix.SparseStringMatrix2D;
import org.ujmp.core.stringmatrix.StringMatrix;
import org.ujmp.core.stringmatrix.StringMatrix2D;

public class GraphMatrixExample {

	public static void main(String[] args) throws Exception {

		// create a GraphMatrix with Strings as nodes and Doubles as edges
		GraphMatrix<String, Double> graphMatrix = new DefaultGraphMatrix<String, Double>();
		graphMatrix.setLabel("Interface Inheritance Graph");

		// collect all matrix interfaces from UJMP
		Class<?>[] classArray = new Class[] { DenseMatrix.class, DenseMatrix2D.class, Matrix.class, Matrix2D.class,
				SparseMatrix.class, SparseMatrix2D.class, BaseBigDecimalMatrix.class, BigDecimalMatrix2D.class,
				DenseBigDecimalMatrix.class, DenseBigDecimalMatrix2D.class, SparseBigDecimalMatrix.class,
				SparseBigDecimalMatrix2D.class, BigIntegerMatrix.class, BigIntegerMatrix2D.class,
				DenseBigIntegerMatrix.class, DenseBigIntegerMatrix2D.class, SparseBigIntegerMatrix.class,
				SparseBigIntegerMatrix2D.class, BooleanMatrix.class, BooleanMatrix2D.class, DenseBooleanMatrix.class,
				DenseBooleanMatrix2D.class, SparseBooleanMatrix.class, SparseBooleanMatrix2D.class,
				ByteArrayMatrix.class, ByteArrayMatrix2D.class, DenseByteArrayMatrix.class,
				DenseByteArrayMatrix2D.class, SparseByteArrayMatrix.class, SparseByteArrayMatrix2D.class,
				ByteMatrix.class, ByteMatrix2D.class, DenseByteMatrix.class, DenseByteMatrix2D.class,
				SparseByteMatrix.class, SparseByteMatrix2D.class, CharMatrix.class, CharMatrix2D.class,
				DenseCharMatrix.class, DenseCharMatrix2D.class, SparseCharMatrix.class, SparseCharMatrix2D.class,
				DoubleMatrix.class, DoubleMatrix2D.class, DenseDoubleMatrix.class, DenseDoubleMatrix2D.class,
				SparseDoubleMatrix.class, SparseDoubleMatrix2D.class, FloatMatrix.class, FloatMatrix2D.class,
				DenseFloatMatrix.class, DenseFloatMatrix2D.class, SparseFloatMatrix.class, SparseFloatMatrix2D.class,
				GenericMatrix.class, GenericMatrix2D.class, DenseGenericMatrix.class, DenseGenericMatrix2D.class,
				SparseGenericMatrix.class, SparseGenericMatrix2D.class, IntMatrix.class, IntMatrix2D.class,
				DenseIntMatrix.class, DenseIntMatrix2D.class, SparseIntMatrix.class, SparseIntMatrix2D.class,
				LongMatrix.class, LongMatrix2D.class, DenseLongMatrix.class, DenseLongMatrix2D.class,
				SparseLongMatrix.class, SparseLongMatrix2D.class, ObjectMatrix.class, ObjectMatrix2D.class,
				DenseObjectMatrix.class, DenseObjectMatrix2D.class, SparseObjectMatrix.class,
				SparseObjectMatrix2D.class, ShortMatrix.class, ShortMatrix2D.class, DenseShortMatrix.class,
				DenseShortMatrix2D.class, SparseShortMatrix.class, SparseShortMatrix2D.class, StringMatrix.class,
				StringMatrix2D.class, DenseStringMatrix.class, DenseStringMatrix2D.class, SparseStringMatrix.class,
				SparseStringMatrix2D.class };

		// find out how interfaces extend one another
		for (Class<?> c1 : classArray) {
			for (Class<?> c2 : classArray) {
				if (c2.getSuperclass() == c1) {
					// add edge when class2 extends class1
					graphMatrix.setEdge(1.0, c1.getSimpleName(), c2.getSimpleName());
				}
				for (Class<?> c3 : c2.getInterfaces()) {
					if (c1 == c3) {
						// add edge when class2 implements class1
						graphMatrix.setEdge(1.0, c1.getSimpleName(), c2.getSimpleName());
					}
				}
			}
		}

		// show on screen
		graphMatrix.showGUI();

	}
}
