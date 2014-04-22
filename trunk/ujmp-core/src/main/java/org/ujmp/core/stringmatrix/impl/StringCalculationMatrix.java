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

package org.ujmp.core.stringmatrix.impl;

import org.ujmp.core.DenseMatrix2D;
import org.ujmp.core.matrix.factory.DenseMatrix2DFactory;
import org.ujmp.core.stringmatrix.calculation.StringCalculation;
import org.ujmp.core.stringmatrix.stub.AbstractStringMatrix;

public class StringCalculationMatrix extends AbstractStringMatrix {
	private static final long serialVersionUID = 8325887621560644634L;

	private StringCalculation calculation = null;

	public StringCalculationMatrix(StringCalculation calculation) {
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

	public String getString(long... coordinates) {
		return calculation.getString(coordinates);
	}

	public void setString(String value, long... coordinates) {
		calculation.setString(value, coordinates);
	}

	public DenseMatrix2DFactory<? extends DenseMatrix2D> getFactory() {
		throw new RuntimeException("not implemented");
	}

	public final boolean isSparse() {
		return false;
	}
}
