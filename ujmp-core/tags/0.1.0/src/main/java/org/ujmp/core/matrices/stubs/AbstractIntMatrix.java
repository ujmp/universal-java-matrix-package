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

package org.ujmp.core.matrices.stubs;

import org.ujmp.core.IntMatrix;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.MathUtil;

public abstract class AbstractIntMatrix extends AbstractGenericMatrix<Integer> implements IntMatrix {

	@Override
	public final Integer getObject(long... coordinates) throws MatrixException {
		return getAsInt(coordinates);
	}

	public final void setObject(Object o, long... coordinates) throws MatrixException {
		setAsDouble(MathUtil.getDouble(o), coordinates);
	}

	@Override
	public final int getAsInt(long... coordinates) throws MatrixException {
		return getInt(coordinates);
	}

	@Override
	public final void setAsInt(int value, long... coordinates) throws MatrixException {
		setInt(value, coordinates);
	}

	public final EntryType getEntryType() {
		return EntryType.INTEGER;
	}

}
