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

package org.ujmp.core.stringmatrix.calculation;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.calculation.AbstractCalculation;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.stringmatrix.StringCalculationMatrix;
import org.ujmp.core.stringmatrix.StringMatrix;

public abstract class AbstractStringCalculation extends AbstractCalculation<Matrix, StringMatrix>
		implements StringCalculation {
	private static final long serialVersionUID = 6467659591327635319L;

	public AbstractStringCalculation(Matrix... sources) {
		super(sources);
	}

	public AbstractStringCalculation(int dimension, Matrix... sources) {
		super(dimension, sources);
	}

	public final StringMatrix calcLink() throws MatrixException {
		return new StringCalculationMatrix(this);
	}

	public final Matrix calcOrig() throws MatrixException {
		if (!Coordinates.equals(getSource().getSize(), getSize())) {
			throw new MatrixException(
					"Cannot change Matrix size. Use calc(Ret.NEW) or calc(Ret.LINK) instead.");
		}
		for (long[] c : getSource().allCoordinates()) {
			getSource().setAsString(getString(c), c);
		}
		return getSource();
	}

	public final StringMatrix calcNew() throws MatrixException {
		StringMatrix result = (StringMatrix) MatrixFactory.zeros(ValueType.STRING, getSize());
		// TODO: copy annotation
		for (long[] c : result.allCoordinates()) {
			result.setAsString(getString(c), c);
		}
		return result;
	}

	// this method is doing nothing, but it has to be there for submatrix or
	// selection where it is overridden
	public void setString(String value, long... coordinates) throws MatrixException {
	}

	@Override
	public final ValueType getValueType() {
		return ValueType.STRING;
	}

}
