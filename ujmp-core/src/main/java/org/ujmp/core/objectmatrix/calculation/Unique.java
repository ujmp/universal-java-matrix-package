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

package org.ujmp.core.objectmatrix.calculation;

import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.setmatrix.DefaultSetMatrix;
import org.ujmp.core.setmatrix.SetMatrix;

public class Unique extends AbstractObjectCalculation {
	private static final long serialVersionUID = -5621298156781794790L;

	private SetMatrix<Object> uniqueObjects = null;

	public Unique(Matrix m) {
		super(m);
	}

	
	public Object getObject(long... coordinates) throws MatrixException {
		createUniqueObjects();
		return uniqueObjects.getAsObject(coordinates);
	}

	private void createUniqueObjects() {
		if (uniqueObjects == null) {
			uniqueObjects = new DefaultSetMatrix<Object>();
			for (long[] c : getSource().availableCoordinates()) {
				uniqueObjects.add(getSource().getAsObject(c));
			}
		}
	}

	
	public long[] getSize() {
		createUniqueObjects();
		return uniqueObjects.getSize();
	}

}
