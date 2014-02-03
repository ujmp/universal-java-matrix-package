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

import javax.swing.JFrame;

import org.ujmp.core.graphmatrix.DefaultGraphMatrix;
import org.ujmp.core.graphmatrix.GraphMatrix;
import org.ujmp.jung.JungGraphFrame;

public class GraphMatrixExample {

	public static void main(String[] args) throws Exception {

		// create a GraphMatrix with Strings as nodes and Doubles as edges
		GraphMatrix<String, Double> graphMatrix = new DefaultGraphMatrix<String, Double>();
		graphMatrix.setLabel("Interface Graph");

		// add some nodes
		graphMatrix.addNode("Matrix");
		graphMatrix.addNode("GenericMatrix");

		// connect the nodes with directed edges,
		// non-existing nodes will be added automatically
		graphMatrix.setEdge(1.0, "Matrix", "GenericMatrix");
		graphMatrix.setEdge(1.0, "Matrix", "Matrix2D");
		graphMatrix.setEdge(1.0, "Matrix", "DenseMatrix");
		graphMatrix.setEdge(1.0, "Matrix", "SpaseMatrix");
		graphMatrix.setEdge(1.0, "GenericMatrix", "BigDecimalMatrix");
		graphMatrix.setEdge(1.0, "GenericMatrix", "BigIntegerMatrix");
		graphMatrix.setEdge(1.0, "GenericMatrix", "BooleanMatrix");
		graphMatrix.setEdge(1.0, "GenericMatrix", "ByteArrayMatrix");
		graphMatrix.setEdge(1.0, "GenericMatrix", "ByteMatrix");
		graphMatrix.setEdge(1.0, "GenericMatrix", "CharMatrix");

		// visualize using JUNG
		JFrame frame = new JungGraphFrame(graphMatrix);
		frame.setVisible(true);
	}
}
