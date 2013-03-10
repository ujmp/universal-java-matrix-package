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

package org.ujmp.core.datematrix.calculation;

import java.util.Date;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.calculation.AbstractCalculation;
import org.ujmp.core.datematrix.DateMatrix;
import org.ujmp.core.datematrix.impl.DateCalculationMatrix;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;

public abstract class AbstractDateCalculation extends AbstractCalculation implements
		DateCalculation {
	private static final long serialVersionUID = -7928696345352218988L;

	public AbstractDateCalculation(Matrix... sources) {
		super(sources);
	}

	public AbstractDateCalculation(int dimension, Matrix... sources) {
		super(dimension, sources);
	}

	public final DateMatrix calcNew() throws MatrixException {
		DateMatrix result = (DateMatrix) Matrix.Factory.zeros(ValueType.DATE, getSize());
		for (long[] c : result.allCoordinates()) {
			result.setAsDate(getDate(c), c);
		}
		if (getAnnotation() != null) {
			result.setAnnotation(getAnnotation().clone());
		}
		return result;
	}

	public final DateMatrix calcLink() throws MatrixException {
		return new DateCalculationMatrix(this);
	}

	public final Matrix calcOrig() throws MatrixException {
		if (!Coordinates.equals(getSource().getSize(), getSize())) {
			throw new MatrixException(
					"Cannot change Matrix size. Use calc(Ret.NEW) or calc(Ret.LINK) instead.");
		}
		for (long[] c : getSource().allCoordinates()) {
			getSource().setAsDate(getDate(c), c);
		}
		getSource().notifyGUIObject();
		return getSource();
	}

	// this method is doing nothing, but it has to be there for submatrix or
	// selection where it is overridden
	public void setDate(Date value, long... coordinates) throws MatrixException {
	}

	public final ValueType getValueType() {
		return ValueType.DATE;
	}

}
