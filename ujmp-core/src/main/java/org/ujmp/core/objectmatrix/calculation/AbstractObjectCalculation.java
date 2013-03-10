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

package org.ujmp.core.objectmatrix.calculation;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.calculation.AbstractCalculation;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.objectmatrix.ObjectMatrix;
import org.ujmp.core.objectmatrix.impl.ObjectCalculationMatrix;

public abstract class AbstractObjectCalculation extends AbstractCalculation implements
		ObjectCalculation {
	private static final long serialVersionUID = 7767220107834181824L;

	public AbstractObjectCalculation(Matrix... sources) {
		super(sources);
	}

	public AbstractObjectCalculation(int dimension, Matrix... sources) {
		super(dimension, sources);
	}

	public final ObjectMatrix calcLink() throws MatrixException {
		return new ObjectCalculationMatrix(this);
	}

	public final Matrix calcNew() throws MatrixException {
		// Matrix result = MatrixFactory.zeros(getSource().getValueType(),
		// getSize());
		Matrix result = MatrixFactory.zeros(getValueType(), getSize());
		for (long[] c : result.allCoordinates()) {
			result.setAsObject(getObject(c), c);
		}
		if (getAnnotation() != null) {
			result.setAnnotation(getAnnotation().clone());
		}
		return result;
	}

	public Matrix calcOrig() throws MatrixException {
		if (!Coordinates.equals(getSource().getSize(), getSize())) {
			throw new MatrixException(
					"Cannot change Matrix size. Use calc(Ret.NEW) or calc(Ret.LINK) instead.");
		}
		for (long[] c : getSource().allCoordinates()) {
			getSource().setAsObject(getObject(c), c);
		}
		getSource().notifyGUIObject();
		return getSource();
	}

	// this method is doing nothing, but it has to be there for submatrix or
	// selection where it is overridden
	public void setObject(Object value, long... coordinates) throws MatrixException {
	}

	public ValueType getValueType() {
		return getSource().getValueType();
	}

}
