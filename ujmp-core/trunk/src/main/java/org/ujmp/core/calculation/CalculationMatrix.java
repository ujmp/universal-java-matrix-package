/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
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

package org.ujmp.core.calculation;

import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.objectmatrix.AbstractMatrix;

public class CalculationMatrix extends AbstractMatrix {
	private static final long serialVersionUID = -8345796002435936888L;

	private AbstractCalculation calculation = null;

	public CalculationMatrix(AbstractCalculation calculation) {
		this.calculation = calculation;
		setAnnotation(calculation.getAnnotation());
	}

	public boolean contains(long... coordinates) {
		return calculation.contains(coordinates);
	}

	public Iterable<long[]> allCoordinates() {
		return calculation.allCoordinates();
	}

	@Override
	public Iterable<long[]> availableCoordinates() {
		return calculation.availableCoordinates();
	}

	public long[] getSize() {
		return calculation.getSize();
	}

	@Override
	public Object getObject(long... coordinates) throws MatrixException {
		return calculation.getObject(coordinates);
	}

	public void setObject(Object o, long... coordinates) throws MatrixException {
	}

	@Override
	public String getAsString(long... coordinates) throws MatrixException {
		return calculation.getString(coordinates);
	}

	public boolean isSparse() {
		return calculation.isSparse();
	}

}
