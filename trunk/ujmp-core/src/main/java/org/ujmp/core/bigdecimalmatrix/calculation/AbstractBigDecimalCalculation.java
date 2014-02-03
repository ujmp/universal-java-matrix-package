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

package org.ujmp.core.bigdecimalmatrix.calculation;

import java.math.BigDecimal;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.bigdecimalmatrix.BigDecimalMatrix;
import org.ujmp.core.bigdecimalmatrix.impl.BigDecimalCalculationMatrix;
import org.ujmp.core.calculation.AbstractCalculation;
import org.ujmp.core.enums.ValueType;

public abstract class AbstractBigDecimalCalculation extends AbstractCalculation implements
		BigDecimalCalculation {
	private static final long serialVersionUID = -8319068678826334197L;

	public AbstractBigDecimalCalculation(Matrix... sources) {
		super(sources);
	}

	public AbstractBigDecimalCalculation(int dimension, Matrix... sources) {
		super(dimension, sources);
	}

	public final BigDecimalMatrix calcNew()  {
		BigDecimalMatrix result = (BigDecimalMatrix) Matrix.Factory.zeros(ValueType.BIGDECIMAL,
				getSize());
		for (long[] c : result.allCoordinates()) {
			result.setAsBigDecimal(getBigDecimal(c), c);
		}
		if (getAnnotation() != null) {
			result.setAnnotation(getAnnotation().clone());
		}
		return result;
	}

	public final BigDecimalMatrix calcLink()  {
		return new BigDecimalCalculationMatrix(this);
	}

	public final Matrix calcOrig()  {
		if (!Coordinates.equals(getSource().getSize(), getSize())) {
			throw new RuntimeException(
					"Cannot change Matrix size. Use calc(Ret.NEW) or calc(Ret.LINK) instead.");
		}
		for (long[] c : getSource().allCoordinates()) {
			getSource().setAsBigDecimal(getBigDecimal(c), c);
		}
		getSource().notifyGUIObject();
		return getSource();
	}

	// this method is doing nothing, but it has to be there for submatrix or
	// selection where it is overridden
	public void setBigDecimal(BigDecimal value, long... coordinates)  {
	}

	public final ValueType getValueType() {
		return ValueType.BIGDECIMAL;
	}

}
