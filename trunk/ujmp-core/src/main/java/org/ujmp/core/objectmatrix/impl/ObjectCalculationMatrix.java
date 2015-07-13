/*
 * Copyright (C) 2008-2015 by Holger Arndt
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

package org.ujmp.core.objectmatrix.impl;

import org.ujmp.core.objectmatrix.calculation.ObjectCalculation;
import org.ujmp.core.objectmatrix.stub.AbstractDenseObjectMatrix;

public class ObjectCalculationMatrix extends AbstractDenseObjectMatrix {
	private static final long serialVersionUID = -2992446453080748754L;

	private final ObjectCalculation calculation;

	public ObjectCalculationMatrix(ObjectCalculation calculation) {
		super(calculation.getSize());
		this.calculation = calculation;
		setMetaData(calculation.getMetaData());
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

	public Object getObject(long... coordinates) {
		return calculation.getObject(coordinates);
	}

	public void setObject(Object value, long... coordinates) {
		calculation.setObject(value, coordinates);
	}

}
