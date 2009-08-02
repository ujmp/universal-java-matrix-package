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

package org.ujmp.core.objectmatrix.calculation;

import java.util.HashSet;
import java.util.Set;

import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.listmatrix.DefaultListMatrix;
import org.ujmp.core.listmatrix.ListMatrix;
import org.ujmp.core.objectmatrix.ObjectMatrix2D;

public class Distinct extends AbstractObjectCalculation {
	private static final long serialVersionUID = 9160233290884903745L;

	private ObjectMatrix2D distinctObjects = null;

	public Distinct(Matrix m) {
		super(m);
	}

	@Override
	public Object getObject(long... coordinates) throws MatrixException {
		if (distinctObjects == null) {
			createDistinctObjectMatrix();
		}
		return distinctObjects.getObject(coordinates);
	}

	public long[] getSize() {
		if (distinctObjects == null) {
			createDistinctObjectMatrix();
		}
		return distinctObjects.getSize();
	}

	private void createDistinctObjectMatrix() {
		Set<Object> objects = new HashSet<Object>();
		Matrix m = getSource();
		for (long[] c : m.availableCoordinates()) {
			objects.add(m.getObject(c));
		}
		ListMatrix<Object> obj = new DefaultListMatrix<Object>(objects);
		distinctObjects = obj;
	}

}
