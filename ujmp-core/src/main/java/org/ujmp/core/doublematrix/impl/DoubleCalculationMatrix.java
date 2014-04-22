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

package org.ujmp.core.doublematrix.impl;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.calculation.DoubleCalculation;
import org.ujmp.core.doublematrix.stub.AbstractDoubleMatrix;
import org.ujmp.core.matrix.factory.BaseMatrixFactory;

public class DoubleCalculationMatrix extends AbstractDoubleMatrix {
	private static final long serialVersionUID = 4906742566162718886L;

	private final DoubleCalculation calculation;

	public DoubleCalculationMatrix(DoubleCalculation calculation) {
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

	public double getDouble(long... coordinates) {
		return calculation.getDouble(coordinates);
	}

	public void setDouble(double value, long... coordinates) {
		calculation.setDouble(value, coordinates);
	}

	public double getDouble(long row, long column) {
		return calculation.getDouble(row, column);
	}

	public double getDouble(int row, int column) {
		return calculation.getDouble(row, column);
	}

	public void setDouble(double value, long row, long column) {
		calculation.setDouble(value, row, column);
	}

	public BaseMatrixFactory<? extends Matrix> getFactory() {
		throw new RuntimeException("not implemented");
	}

	public void setDouble(double value, int row, int column) {
		calculation.setDouble(value, row, column);
	}

	public Double getObject(long row, long column) {
		return calculation.getDouble(row, column);
	}

	public void setObject(Double value, long row, long column) {
		calculation.setDouble(value, row, column);
	}

	public Double getObject(int row, int column) {
		return calculation.getDouble(row, column);
	}

	public void setObject(Double value, int row, int column) {
		calculation.setDouble(value, row, column);
	}

	public final boolean isSparse() {
		return false;
	}

}
