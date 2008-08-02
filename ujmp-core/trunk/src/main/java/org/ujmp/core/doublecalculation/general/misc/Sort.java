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

package org.ujmp.core.doublecalculation.general.misc;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.intmatrix.IntMatrix2D;
import org.ujmp.core.objectcalculation.AbstractObjectCalculation;
import org.ujmp.core.util.Sortable;

/**
 * Sorts the rows of a matrix
 * 
 * 
 * 
 */
public class Sort extends AbstractObjectCalculation {
	private static final long serialVersionUID = -6935375114060680121L;

	private IntMatrix2D index = null;

	public Sort(Matrix m) {
		super(m);
	}

	@Override
	public Object getObject(long... coordinates) throws MatrixException {
		if (index == null) {
			createSortIndex();
		}
		return getSource().getObject(coordinates[0], index.getAsInt(coordinates));
	}

	private void createSortIndex() {
		Matrix m = getSource();
		IntMatrix2D indexMatrix = (IntMatrix2D) MatrixFactory.zeros(ValueType.INT, m.getSize());
		for (long i = 0; i < m.getRowCount(); i++) {
			SortedSet<Sortable<?, Long>> sortedSet = new TreeSet<Sortable<?, Long>>();
			for (long j = 0; j < m.getColumnCount(); j++) {
				Comparable c = (Comparable) m.getObject(i, j);
				Sortable<?, Long> s = new Sortable(c, j, true);
				sortedSet.add(s);
			}
			Iterator<Sortable<?, Long>> it = sortedSet.iterator();
			long j = 0;
			while (it.hasNext()) {
				Sortable<?, Long> s = it.next();
				long index = s.getObject();
				indexMatrix.setInt((int) index, i, j);
				j++;
			}
		}
		this.index = indexMatrix;
	}

	public IntMatrix2D getIndex() {
		return index;
	}

}
