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

package org.ujmp.core.bigdecimalmatrix.impl;

import java.math.BigDecimal;

import org.ujmp.core.bigdecimalmatrix.calculation.BigDecimalCalculation;
import org.ujmp.core.bigdecimalmatrix.stub.AbstractBigDecimalMatrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.doublematrix.calculation.entrywise.creators.Zeros;

public class BigDecimalCalculationMatrix extends AbstractBigDecimalMatrix {
	private static final long serialVersionUID = -1984605379151298934L;

	private final BigDecimalCalculation calculation;

	public BigDecimalCalculationMatrix(BigDecimalCalculation calculation) {
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

	public BigDecimal getBigDecimal(long... coordinates) {
		return calculation.getBigDecimal(coordinates);
	}

	public void setBigDecimal(BigDecimal value, long... coordinates) {
		calculation.setBigDecimal(value, coordinates);
	}

	public BigDecimal getBigDecimal(long row, long column) {
		return calculation.getBigDecimal(new long[] { row, column });
	}

	public void setBigDecimal(BigDecimal value, long row, long column) {
		calculation.setBigDecimal(value, new long[] { row, column });
	}

	public BigDecimal getBigDecimal(int row, int column) {
		return calculation.getBigDecimal(new long[] { row, column });
	}

	public void setBigDecimal(BigDecimal value, int row, int column) {
		calculation.setBigDecimal(value, new long[] { row, column });
	}

	public BigDecimal getNumber(long... coordinates) {
		return calculation.getBigDecimal(coordinates);
	}

	public void setNumber(BigDecimal value, long... coordinates) {
		calculation.setBigDecimal(value, coordinates);
	}

	public final boolean isSparse() {
		return false;
	}

	public final void clear() {
		throw new RuntimeException("matrix cannot be modified");
	}
}
