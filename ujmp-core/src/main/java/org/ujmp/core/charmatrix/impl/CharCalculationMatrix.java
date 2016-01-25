/*
 * Copyright (C) 2008-2016 by Holger Arndt
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

package org.ujmp.core.charmatrix.impl;

import org.ujmp.core.charmatrix.calculation.CharCalculation;
import org.ujmp.core.charmatrix.stub.AbstractCharMatrix;

public class CharCalculationMatrix extends AbstractCharMatrix {
	private static final long serialVersionUID = -5805683275067052860L;

	private final CharCalculation calculation;

	public CharCalculationMatrix(CharCalculation calculation) {
		super(calculation.getSize());
		this.calculation = calculation;
		setMetaData(calculation.getMetaData());
	}

	public boolean containsCoordinates(long... coordinates) {
		return calculation.containsCoordinates(coordinates);
	}

	public Iterable<long[]> availableCoordinates() {
		return calculation.availableCoordinates();
	}

	public long[] getSize() {
		size = calculation.getSize();
		return size;
	}

	public void fireValueChanged() {
		super.fireValueChanged();
		if (calculation.getSource() != null) {
			calculation.getSource().fireValueChanged();
		}
	}

	public char getChar(long... coordinates) {
		return calculation.getChar(coordinates);
	}

	public void setChar(char value, long... coordinates) {
		calculation.setChar(value, coordinates);
	}

	public final boolean isSparse() {
		return false;
	}

	public final void clear() {
		throw new RuntimeException("matrix cannot be modified");
	}
}
