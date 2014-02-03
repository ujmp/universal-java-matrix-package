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

import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;

public class SimilarityMatrixExample {

	public static void main(String[] args) throws Exception {

		// create matrix with 10 correlated columns, 100 rows, correlation 0.1
		Matrix correlated = Matrix.Factory.correlatedColumns(100, 10, 0.1);

		// calculate similarity and store in new matrix,
		// ignore missing values if present
		Matrix similarity = correlated.cosineSimilarity(Ret.NEW, true);

		// show on screen
		correlated.showGUI();
		similarity.showGUI();
	}

}
