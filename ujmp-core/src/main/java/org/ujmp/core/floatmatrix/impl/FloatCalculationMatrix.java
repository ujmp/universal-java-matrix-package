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

package org.ujmp.core.floatmatrix.impl;

import org.ujmp.core.DenseMatrix2D;
import org.ujmp.core.floatmatrix.calculation.FloatCalculation;
import org.ujmp.core.floatmatrix.stub.AbstractFloatMatrix;
import org.ujmp.core.matrix.factory.DenseMatrix2DFactory;

public class FloatCalculationMatrix extends AbstractFloatMatrix {
	private static final long serialVersionUID = 1908338654416069952L;

	private FloatCalculation calculation = null;

	public FloatCalculationMatrix(FloatCalculation calculation) {
		super(calculation.getSize());
		this.calculation = calculation;
		setMetaData(calculation.getMetaData());
	}

	public boolean contains(long... coordinates) {
		return calculation.contains(coordinates);
	}

	public Iterable<long[]> availableCoordinates() {
		return calculation.availableCoordinates();
	}

	public long[] getSize() {
		return calculation.getSize();
	}

	public void fireValueChanged() {
		super.fireValueChanged();
		if (calculation.getSource() != null) {
			calculation.getSource().fireValueChanged();
		}
	}

	public float getFloat(long... coordinates) {
		return calculation.getFloat(coordinates);
	}

	public void setFloat(float value, long... coordinates) {
		calculation.setFloat(value, coordinates);
	}

	public DenseMatrix2DFactory<? extends DenseMatrix2D> getFactory() {
		throw new RuntimeException("not implemented");
	}

	public final boolean isSparse() {
		return false;
	}

}
