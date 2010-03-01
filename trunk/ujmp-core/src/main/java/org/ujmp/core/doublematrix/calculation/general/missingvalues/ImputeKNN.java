/*
 * Copyright (C) 2008-2010 by Holger Arndt
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

package org.ujmp.core.doublematrix.calculation.general.missingvalues;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.calculation.AbstractDoubleCalculation;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.Sortable;

public class ImputeKNN extends AbstractDoubleCalculation {
	private static final long serialVersionUID = -4923873199518001578L;

	private Matrix distanceMatrix = null;

	private int k = 1;

	public ImputeKNN(Matrix matrix, Object... parameters) {
		super(matrix);
		if (parameters.length != 0) {
			k = MathUtil.getInt(parameters[0]);
		}
	}

	private List<Integer> getCandidates(long... coordinates) {
		List<Integer> candidates = new ArrayList<Integer>();
		for (int r = 0; r < getSource().getRowCount(); r++) {
			if (coordinates[ROW] == r) {
				continue;
			}
			if (!MathUtil.isNaNOrInfinite(getSource().getAsDouble(r, coordinates[COLUMN]))) {
				candidates.add(r);
			}
		}
		return candidates;
	}

	private Matrix getDistanceMatrix() {
		Matrix distanceMatrix = Matrix.factory.dense(getSource().getRowCount(), getSource()
				.getRowCount());
		for (int r = 0; r < getSource().getRowCount(); r++) {
			for (int c = 0; c < getSource().getRowCount(); c++) {
				if (r != c) {
					Matrix m1 = getSource().selectRows(Ret.LINK, r);
					Matrix m2 = getSource().selectRows(Ret.LINK, c);
					double dist = m1.euklideanDistanceTo(m2, true);
					distanceMatrix.setAsDouble(dist, r, c);
				}
			}
		}
		return distanceMatrix;
	}

	private List<Sortable<Double, Matrix>> getSortedNeighbors(long... coordinates) {
		List<Sortable<Double, Matrix>> neighbors = new ArrayList<Sortable<Double, Matrix>>();
		List<Integer> candidates = getCandidates(coordinates);

		for (int candidateRow : candidates) {
			double dist = distanceMatrix.getAsDouble(coordinates[ROW], candidateRow);
			Matrix candidate = getSource().selectRows(Ret.LINK, candidateRow);
			neighbors.add(new Sortable<Double, Matrix>(dist, candidate));
		}

		Collections.sort(neighbors);
		return neighbors;
	}

	public double getDouble(long... coordinates) throws MatrixException {
		if (distanceMatrix == null) {
			distanceMatrix = getDistanceMatrix();
		}
		double value = getSource().getAsDouble(coordinates);
		if (MathUtil.isNaNOrInfinite(value)) {
			List<Sortable<Double, Matrix>> sortedNeighbors = getSortedNeighbors(coordinates);
			double sum = 0;
			int count = 0;
			for (Sortable<Double, Matrix> s : sortedNeighbors) {
				sum += s.getObject().getAsDouble(0, coordinates[COLUMN]);
				if (++count == k) {
					break;
				}
			}
			return sum / count;
		} else {
			return value;
		}
	}

}
