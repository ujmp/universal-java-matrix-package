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

package org.ujmp.core.shortmatrix.impl;

import org.ujmp.core.DenseMatrix2D;
import org.ujmp.core.matrix.factory.DenseMatrix2DFactory;
import org.ujmp.core.shortmatrix.calculation.ShortCalculation;
import org.ujmp.core.shortmatrix.stub.AbstractShortMatrix;

public class ShortCalculationMatrix extends AbstractShortMatrix {
	private static final long serialVersionUID = 4730692754155347926L;

	private ShortCalculation calculation = null;

	public ShortCalculationMatrix(ShortCalculation calculation) {
		super(calculation.getSize());
		this.calculation = calculation;
		setAnnotation(calculation.getAnnotation());
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

	public void notifyGUIObject() {
		super.notifyGUIObject();
		if (calculation.getSource() != null) {
			calculation.getSource().notifyGUIObject();
		}
	}

	public short getShort(long... coordinates) {
		return calculation.getShort(coordinates);
	}

	public void setShort(short value, long... coordinates) {
		calculation.setShort(value, coordinates);
	}

	public DenseMatrix2DFactory<? extends DenseMatrix2D> getFactory() {
		throw new RuntimeException("not implemented");
	}

	public final boolean isSparse() {
		return false;
	}

}
