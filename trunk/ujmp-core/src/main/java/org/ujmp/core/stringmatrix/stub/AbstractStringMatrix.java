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

package org.ujmp.core.stringmatrix.stub;

import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.genericmatrix.stub.AbstractGenericMatrix;
import org.ujmp.core.stringmatrix.StringMatrix;
import org.ujmp.core.util.MathUtil;

public abstract class AbstractStringMatrix extends AbstractGenericMatrix<String> implements
		StringMatrix {

	private static final long serialVersionUID = -8163097072559207L;

	
	public final String getObject(long... coordinates) throws MatrixException {
		return getAsString(coordinates);
	}

	public final void setObject(String o, long... coordinates) throws MatrixException {
		setAsString(o, coordinates);
	}

	
	public final double getAsDouble(long... coordinates) {
		return MathUtil.getDouble(getAsString(coordinates));
	}

	
	public final void setAsDouble(double value, long... coordinates) throws MatrixException {
		setAsString("" + value, coordinates);
	}

	
	public final String getAsString(long... coordinates) {
		return getString(coordinates);
	}

	
	public final void setAsString(String string, long... coordinates) throws MatrixException {
		setString(string, coordinates);
	}

	
	public final ValueType getValueType() {
		return ValueType.STRING;
	}

}
