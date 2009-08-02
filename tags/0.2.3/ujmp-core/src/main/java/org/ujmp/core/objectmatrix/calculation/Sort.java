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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.longmatrix.LongMatrix2D;
import org.ujmp.core.util.Sortable;

/**
 * Sorts the rows of a matrix
 * 
 * 
 * 
 */
public class Sort extends AbstractObjectCalculation {
	private static final long serialVersionUID = -6935375114060680121L;

	private LongMatrix2D index = null;

	private long column = 0;

	public Sort(Matrix m) {
		super(m);
	}

	public Sort(Matrix m, long column) {
		super(m);
		this.column = column;
	}

	@Override
	public Object getObject(long... coordinates) throws MatrixException {
		if (index == null) {
			createSortIndex();
		}
		return getSource().getAsObject(index.getLong(coordinates[ROW], 0), coordinates[COLUMN]);
	}

	/**
	 * seems not to work
	 */
	// private void createSortIndex() {
	// Matrix m = getSource();
	// IntMatrix2D indexMatrix = new DefaultDenseIntMatrix2D(m.getSize());
	// for (long i = 0; i < m.getRowCount(); i++) {
	// SortedSet<Sortable<?, Long>> sortedSet = new TreeSet<Sortable<?,
	// Long>>();
	// for (long j = 0; j < m.getColumnCount(); j++) {
	// Comparable c = (Comparable) m.getObject(i, j);
	// Sortable<?, Long> s = new Sortable(c, j, true);
	// sortedSet.add(s);
	// }
	// Iterator<Sortable<?, Long>> it = sortedSet.iterator();
	// long j = 0;
	// while (it.hasNext()) {
	// Sortable<?, Long> s = it.next();
	// long index = s.getObject();
	// indexMatrix.setInt((int) index, i, j);
	// j++;
	// }
	// }
	// this.index = indexMatrix;
	// }
	@SuppressWarnings("unchecked")
	private void createSortIndex() {
		Matrix m = getSource();
		List<Sortable> rows = new ArrayList<Sortable>();

		for (long r = 0; r < m.getRowCount(); r++) {
			Comparable<?> c = (Comparable<?>) m.getAsObject(r, column);
			Sortable s = new Sortable(c, r, true);
			rows.add(s);
		}

		Collections.sort(rows);

		LongMatrix2D indexMatrix = (LongMatrix2D) MatrixFactory.zeros(ValueType.LONG, rows.size(),
				1);

		for (int r = 0; r < rows.size(); r++) {
			indexMatrix.setLong((Long) (rows.get(r)).getObject(), r, 0);
		}

		this.index = indexMatrix;
	}

	public LongMatrix2D getIndex() {
		return index;
	}

}
