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

package org.ujmp.core.doublematrix.impl;

import org.ujmp.core.collections.Dictionary;
import org.ujmp.core.doublematrix.stub.AbstractSparseDoubleMatrix2D;
import org.ujmp.core.mapmatrix.DefaultMapMatrix;
import org.ujmp.core.mapmatrix.MapMatrix;
import org.ujmp.core.util.MathUtil;

public class SparseFeatureVector extends AbstractSparseDoubleMatrix2D {
	private static final long serialVersionUID = -4011983790653257058L;

	private final Dictionary dictionary;

	private final MapMatrix<String, Double> values = new DefaultMapMatrix<String, Double>();

	public SparseFeatureVector(Dictionary dictionary) {
		super(0, 1);
		this.dictionary = dictionary;
	}

	// TODO: improve
	public final Iterable<long[]> availableCoordinates() {
		return allCoordinates();
	}

	public double getDouble(long row, long column) {
		return getDouble(MathUtil.longToInt(row), MathUtil.longToInt(column));
	}

	public void setDouble(double value, long row, long column) {
		setDouble(value, MathUtil.longToInt(row), MathUtil.longToInt(column));
	}

	public double getDouble(int row, int column) {
		String featureName = dictionary.get(row);
		return getFeatureValue(featureName);
	}

	public void setDouble(double value, int row, int column) {
		String featureName = dictionary.get(row);
		setFeatureValue(featureName, value);
	}

	public boolean containsCoordinates(long... coordinates) {
		String featureName = dictionary.get(MathUtil.longToInt(coordinates[ROW]));
		return hasFeature(featureName);
	}

	public boolean hasFeature(String featureName) {
		return getFeatureValue(featureName) != 0;
	}

	public long[] getSize() {
		size[ROW] = dictionary.size();
		return size;
	}

	public double getFeatureValue(String featureName) {
		Double value = values.get(featureName);
		return value == null ? 0 : value;
	}

	public void setFeatureValue(String featureName, double value) {
		dictionary.add(featureName);
		if (value == 0) {
			values.remove(featureName);
		} else {
			values.put(featureName, value);
		}
	}

	public final void clear() {
		values.clear();
	}
}
