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

import org.ujmp.core.objectmatrix.stub.AbstractDenseObjectMatrix2D;

public class EmptyMatrix extends AbstractDenseObjectMatrix2D {
	private static final long serialVersionUID = 1226331953770561766L;

	public EmptyMatrix() {
		super(0, 0);
	}

	public Object getObject(long row, long column) {
		return null;
	}

	public void setObject(Object value, long row, long column) {
	}

	public Object getObject(int row, int column) {
		return null;
	}

	public void setObject(Object value, int row, int column) {
	}

}
