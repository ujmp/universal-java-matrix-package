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

package org.ujmp.core.bigintegermatrix.impl;

import java.math.BigInteger;

import org.ujmp.core.Matrix;
import org.ujmp.core.bigintegermatrix.calculation.BigIntegerCalculation;
import org.ujmp.core.bigintegermatrix.stub.AbstractBigIntegerMatrix;
import org.ujmp.core.matrix.factory.BaseMatrixFactory;

public class BigIntegerCalculationMatrix extends AbstractBigIntegerMatrix {
	private static final long serialVersionUID = 311922117437271156L;

	private final BigIntegerCalculation calculation;

	public BigIntegerCalculationMatrix(BigIntegerCalculation calculation) {
		super(calculation.getSize());
		this.calculation = calculation;
		setMetaData(calculation.getMetaData());
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

	public BigInteger getBigInteger(long... coordinates) {
		return calculation.getBigInteger(coordinates);
	}

	public void setBigInteger(BigInteger value, long... coordinates) {
		calculation.setBigInteger(value, coordinates);
	}

	public BaseMatrixFactory<? extends Matrix> getFactory() {
		throw new RuntimeException("not implemented");
	}
	
	public final boolean isSparse() {
		return false;
	}

}
