/*
 * Copyright (C) 2008-2013 by Holger Arndt
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

package org.ujmp.core.objectmatrix.calculation;

import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;

public class Replace extends AbstractObjectCalculation {
	private static final long serialVersionUID = -1668944121367955215L;

	private Object search = null;

	private Object replace = null;

	public Replace(Matrix matrix, Object search, Object replace) {
		super(matrix);
		this.search = search;
		this.replace = replace;
	}

	
	public Object getObject(long... coordinates) throws MatrixException {
		Object src = getSource().getAsObject(coordinates);

		if (search == null) {
			if (src == null) {
				return replace;
			}
		} else {
			if (search.equals(src)) {
				return replace;
			}
		}

		return src;
	}

}
