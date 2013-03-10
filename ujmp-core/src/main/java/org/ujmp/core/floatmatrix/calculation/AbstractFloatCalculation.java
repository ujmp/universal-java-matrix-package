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

package org.ujmp.core.floatmatrix.calculation;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.AbstractCalculation;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.floatmatrix.FloatMatrix;
import org.ujmp.core.floatmatrix.impl.FloatCalculationMatrix;

public abstract class AbstractFloatCalculation extends AbstractCalculation implements
		FloatCalculation {
	private static final long serialVersionUID = 7992575645594878948L;

	public AbstractFloatCalculation(Matrix... sources) {
		super(sources);
	}

	public AbstractFloatCalculation(int dimension, Matrix... sources) {
		super(dimension, sources);
	}

	public final FloatMatrix calcLink() throws MatrixException {
		return new FloatCalculationMatrix(this);
	}

	public final FloatMatrix calcNew() throws MatrixException {
		FloatMatrix result = (FloatMatrix) Matrix.Factory.zeros(ValueType.FLOAT, getSize());
		for (long[] c : result.allCoordinates()) {
			result.setAsFloat(getFloat(c), c);
		}
		if (getAnnotation() != null) {
			result.setAnnotation(getAnnotation().clone());
		}
		return result;
	}

	public final Matrix calcOrig() throws MatrixException {
		if (!Coordinates.equals(getSource().getSize(), getSize())) {
			throw new MatrixException(
					"Cannot change Matrix size. Use calc(Ret.NEW) or calc(Ret.LINK) instead.");
		}
		for (long[] c : getSource().allCoordinates()) {
			getSource().setAsFloat(getFloat(c), c);
		}
		getSource().notifyGUIObject();
		return getSource();
	}

	// this method is doing nothing, but it has to be there for submatrix or
	// selection where it is overridden
	public void setFloat(float value, long... coordinates) throws MatrixException {
	}

	public final ValueType getValueType() {
		return ValueType.FLOAT;
	}

}
