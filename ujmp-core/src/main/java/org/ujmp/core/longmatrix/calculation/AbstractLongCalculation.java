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

package org.ujmp.core.longmatrix.calculation;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.AbstractCalculation;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.longmatrix.LongMatrix;
import org.ujmp.core.longmatrix.impl.LongCalculationMatrix;

public abstract class AbstractLongCalculation extends AbstractCalculation implements
		LongCalculation {
	private static final long serialVersionUID = -2401843008270253704L;

	public AbstractLongCalculation(Matrix... sources) {
		super(sources);
	}

	public AbstractLongCalculation(int dimension, Matrix... sources) {
		super(dimension, sources);
	}

	public final LongMatrix calcLink() {
		return new LongCalculationMatrix(this);
	}

	public final LongMatrix calcNew() {
		LongMatrix result = (LongMatrix) Matrix.Factory.zeros(ValueType.LONG, getSize());
		for (long[] c : result.allCoordinates()) {
			result.setAsLong(getLong(c), c);
		}
		if (getMetaData() != null) {
			result.setMetaData(getMetaData().clone());
		}
		return result;
	}

	public final Matrix calcOrig() {
		if (!Coordinates.equals(getSource().getSize(), getSize())) {
			throw new RuntimeException(
					"Cannot change Matrix size. Use calc(Ret.NEW) or calc(Ret.LINK) instead.");
		}
		for (long[] c : getSource().allCoordinates()) {
			getSource().setAsLong(getLong(c), c);
		}
		getSource().notifyGUIObject();
		return getSource();
	}

	// this method is doing nothing, but it has to be there for submatrix or
	// selection where it is overridden
	public void setLong(long value, long... coordinates) {
	}

	public final ValueType getValueType() {
		return ValueType.LONG;
	}

}
