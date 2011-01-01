/*
 * Copyright (C) 2008-2011 by Holger Arndt
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
package org.ujmp.core.util;

/**
 * This distance measure yields the <i>Manhattan distance</i> between two
 * samples of <code>double</code> values.
 */
public class ManhattanDistance implements DistanceMeasure {

	/**
	 * Get the distance between two data samples.
	 * 
	 * @param sample1
	 *            the first sample of <code>double</code> values
	 * @param sample2
	 *            the second sample of <code>double</code> values
	 * @return the distance between <code>sample1</code> and
	 *         <code>sample2</code>
	 * @throws IllegalArgumentException
	 *             if the two samples contain different amounts of values
	 */
	public double getDistance(double[] sample1, double[] sample2) throws IllegalArgumentException {

		int n = sample1.length;
		if (n != sample2.length || n < 1)
			throw new IllegalArgumentException("Input arrays must have the same length.");

		double sumOfDifferences = 0;
		for (int i = 0; i < n; i++) {
			if (Double.isNaN(sample1[i]) || Double.isNaN(sample2[i]))
				continue;

			sumOfDifferences += Math.abs(sample1[i] - sample2[i]);
		}

		return sumOfDifferences;
	}
}
