/*
 * Copyright (C) 2008-2009 Holger Arndt, A. Naegele and M. Bundschus
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

package org.ujmp.core.charmatrix.calculation;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.calculation.AbstractCalculation;
import org.ujmp.core.charmatrix.CharMatrix;
import org.ujmp.core.charmatrix.impl.CharCalculationMatrix;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;

public abstract class AbstractCharCalculation extends AbstractCalculation<Matrix, CharMatrix>
		implements CharCalculation {
	private static final long serialVersionUID = -3251131937680203514L;

	public AbstractCharCalculation(Matrix... sources) {
		super(sources);
	}

	public AbstractCharCalculation(int dimension, Matrix... sources) {
		super(dimension, sources);
	}

	public final CharMatrix calcNew() throws MatrixException {
		CharMatrix result = (CharMatrix) MatrixFactory.zeros(ValueType.CHAR, getSize());
		// TODO: copy annotation
		for (long[] c : result.allCoordinates()) {
			result.setAsChar(getChar(c), c);
		}
		return result;
	}

	public final CharMatrix calcLink() throws MatrixException {
		return new CharCalculationMatrix(this);
	}

	public final Matrix calcOrig() throws MatrixException {
		if (!Coordinates.equals(getSource().getSize(), getSize())) {
			throw new MatrixException(
					"Cannot change Matrix size. Use calc(Ret.NEW) or calc(Ret.LINK) instead.");
		}
		for (long[] c : getSource().allCoordinates()) {
			getSource().setAsChar(getChar(c), c);
		}
		getSource().notifyGUIObject();
		return getSource();
	}

	// this method is doing nothing, but it has to be there for submatrix or
	// selection where it is overridden
	public void setChar(char value, long... coordinates) throws MatrixException {
	}

	
	public final ValueType getValueType() {
		return ValueType.CHAR;
	}

}
