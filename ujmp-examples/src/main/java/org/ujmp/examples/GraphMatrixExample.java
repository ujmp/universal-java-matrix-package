/*
 * Copyright (C) 2008-2014 by Holger Arndt
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

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.ujmp.core.AbstractMatrix;
import org.ujmp.core.DenseMatrix;
import org.ujmp.core.DenseMatrix2D;
import org.ujmp.core.Matrix;
import org.ujmp.core.Matrix2D;
import org.ujmp.core.SparseMatrix;
import org.ujmp.core.SparseMatrix2D;
import org.ujmp.core.doublematrix.DenseDoubleMatrix;
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.DoubleMatrix;
import org.ujmp.core.doublematrix.DoubleMatrix2D;
import org.ujmp.core.doublematrix.SparseDoubleMatrix;
import org.ujmp.core.doublematrix.SparseDoubleMatrix2D;
import org.ujmp.core.doublematrix.impl.DefaultDenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.stub.AbstractDenseDoubleMatrix;
import org.ujmp.core.doublematrix.stub.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.stub.AbstractDoubleMatrix;
import org.ujmp.core.doublematrix.stub.AbstractDoubleMatrix2D;
import org.ujmp.core.genericmatrix.DenseGenericMatrix;
import org.ujmp.core.genericmatrix.DenseGenericMatrix2D;
import org.ujmp.core.genericmatrix.GenericMatrix;
import org.ujmp.core.genericmatrix.GenericMatrix2D;
import org.ujmp.core.genericmatrix.SparseGenericMatrix;
import org.ujmp.core.genericmatrix.SparseGenericMatrix2D;
import org.ujmp.core.genericmatrix.stub.AbstractDenseGenericMatrix;
import org.ujmp.core.genericmatrix.stub.AbstractDenseGenericMatrix2D;
import org.ujmp.core.genericmatrix.stub.AbstractGenericMatrix;
import org.ujmp.core.graphmatrix.DefaultGraphMatrix;
import org.ujmp.core.graphmatrix.GraphMatrix;
import org.ujmp.core.matrix.factory.AbstractMatrixFactory;
import org.ujmp.core.matrix.factory.DefaultMatrixFactory;
import org.ujmp.core.matrix.factory.MatrixFactory;
import org.ujmp.jung.JungGraphFrame;

public class GraphMatrixExample {

	public static void main(String[] args) throws Exception {

		// create a GraphMatrix with Strings as nodes and Doubles as edges
		GraphMatrix<String, Double> graphMatrix = new DefaultGraphMatrix<String, Double>();
		graphMatrix.setLabel("Interface Graph");

		List<Class<?>> classList = new ArrayList<Class<?>>();
		classList.add(Matrix.class);

		classList.add(Matrix2D.class);
		classList.add(DenseMatrix.class);
		classList.add(DenseMatrix2D.class);
		classList.add(SparseMatrix.class);
		classList.add(SparseMatrix2D.class);
		classList.add(GenericMatrix.class);
		classList.add(DoubleMatrix.class);
		classList.add(GenericMatrix2D.class);
		classList.add(DenseGenericMatrix2D.class);
		classList.add(SparseGenericMatrix2D.class);
		classList.add(DenseGenericMatrix.class);
		classList.add(SparseGenericMatrix.class);
		classList.add(DoubleMatrix2D.class);
		classList.add(DenseDoubleMatrix2D.class);
		classList.add(DenseDoubleMatrix.class);
		classList.add(SparseDoubleMatrix2D.class);
		classList.add(SparseDoubleMatrix.class);

		classList.add(AbstractMatrix.class);
		classList.add(AbstractGenericMatrix.class);
		classList.add(AbstractDenseGenericMatrix.class);
		classList.add(AbstractDenseGenericMatrix2D.class);
		classList.add(AbstractDoubleMatrix.class);
		classList.add(AbstractDoubleMatrix2D.class);
		classList.add(AbstractDenseDoubleMatrix.class);
		classList.add(AbstractDenseDoubleMatrix2D.class);

		classList.add(DefaultDenseDoubleMatrix2D.class);

		classList.add(MatrixFactory.class);
		classList.add(AbstractMatrixFactory.class);
		classList.add(DefaultMatrixFactory.class);

		for (Class<?> c1 : classList) {
			for (Class<?> c2 : classList) {
				if (c2.getSuperclass() == c1) {
					graphMatrix.setEdge(1.0, c1.getSimpleName(), c2.getSimpleName());
				}
				for (Class<?> c3 : c2.getInterfaces()) {
					if (c1 == c3) {
						graphMatrix.setEdge(1.0, c1.getSimpleName(), c2.getSimpleName());
					}
				}
			}
		}

		// visualize using JUNG
		JFrame frame = new JungGraphFrame(graphMatrix);
		frame.setVisible(true);
	}
}
