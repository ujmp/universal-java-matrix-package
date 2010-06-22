/*
 * Copyright (C) 2008-2010 by Holger Arndt
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

package org.ujmp.core.floatmatrix.stub;

import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.floatmatrix.FloatMatrix;
import org.ujmp.core.genericmatrix.stub.AbstractGenericMatrix;

public abstract class AbstractFloatMatrix extends AbstractGenericMatrix<Float> implements
		FloatMatrix {

	private static final long serialVersionUID = -6051872055791093546L;

	
	public final Float getObject(long... coordinates) throws MatrixException {
		return getFloat(coordinates);
	}

	
	public final void setObject(Float o, long... coordinates) throws MatrixException {
		setFloat(o, coordinates);
	}

	
	public final float getAsFloat(long... coordinates) throws MatrixException {
		return getFloat(coordinates);
	}

	
	public final void setAsFloat(float value, long... coordinates) throws MatrixException {
		setFloat(value, coordinates);
	}

	
	public final double getAsDouble(long... coordinates) throws MatrixException {
		return getFloat(coordinates);
	}

	
	public final void setAsDouble(double value, long... coordinates) throws MatrixException {
		setFloat((float) value, coordinates);
	}

	
	public final ValueType getValueType() {
		return ValueType.FLOAT;
	}

}
