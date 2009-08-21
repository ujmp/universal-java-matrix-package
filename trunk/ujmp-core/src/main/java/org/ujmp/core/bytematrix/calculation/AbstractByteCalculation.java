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

package org.ujmp.core.bytematrix.calculation;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.bytematrix.ByteMatrix;
import org.ujmp.core.bytematrix.impl.ByteCalculationMatrix;
import org.ujmp.core.calculation.AbstractCalculation;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;

public abstract class AbstractByteCalculation extends AbstractCalculation<Matrix, ByteMatrix>
		implements ByteCalculation {
	private static final long serialVersionUID = -6732603786169729218L;

	public AbstractByteCalculation(Matrix... sources) {
		super(sources);
	}

	public AbstractByteCalculation(int dimension, Matrix... sources) {
		super(dimension, sources);
	}

	public final ByteMatrix calcNew() throws MatrixException {
		ByteMatrix result = (ByteMatrix) MatrixFactory.zeros(ValueType.BYTE, getSize());
		// TODO: copy annotation
		for (long[] c : result.allCoordinates()) {
			result.setByte(getByte(c), c);
		}
		return result;
	}

	public final ByteMatrix calcLink() throws MatrixException {
		return new ByteCalculationMatrix(this);
	}

	public final Matrix calcOrig() throws MatrixException {
		if (!Coordinates.equals(getSource().getSize(), getSize())) {
			throw new MatrixException(
					"Cannot change Matrix size. Use calc(Ret.NEW) or calc(Ret.LINK) instead.");
		}
		for (long[] c : getSource().allCoordinates()) {
			getSource().setAsByte(getByte(c), c);
		}
		getSource().notifyGUIObject();
		return getSource();
	}

	// this method is doing nothing, but it has to be there for submatrix or
	// selection where it is overridden
	public void setByte(byte value, long... coordinates) throws MatrixException {
	}

	
	public final ValueType getValueType() {
		return ValueType.BYTE;
	}

}
