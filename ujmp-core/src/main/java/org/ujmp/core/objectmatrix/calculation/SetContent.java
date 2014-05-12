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

package org.ujmp.core.objectmatrix.calculation;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;

public class SetContent extends AbstractObjectCalculation {
	private static final long serialVersionUID = 2014347270412520806L;

	private final Matrix newContent;
	private final long[] position;

	public SetContent(Matrix source, Matrix newContent, long... position) {
		super(source);
		this.newContent = newContent;
		this.position = position;
	}

	public Object getObject(long... coordinates) {
		if (Coordinates.isSmallerThan(coordinates, position)) {
			return getSource().getAsObject(coordinates);
		}
		long[] c = Coordinates.minus(coordinates, position);
		if (Coordinates.isSmallerThan(c, newContent.getSize())) {
			return newContent.getAsObject(c);
		} else {
			return getSource().getAsObject(coordinates);
		}
	}
}
