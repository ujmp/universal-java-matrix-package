/*
 * Copyright (C) 2008-2016 by Holger Arndt
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

package org.ujmp.core.longmatrix.calculation;

import org.ujmp.core.Matrix;
import org.ujmp.core.longmatrix.impl.DefaultSparseLongMatrix;
import org.ujmp.core.mapmatrix.DefaultMapMatrix;
import org.ujmp.core.mapmatrix.MapMatrix;

public class DocTerm extends AbstractLongCalculation {
	private static final long serialVersionUID = 9021699761386822606L;

	private MapMatrix<String, Long> wordMapping = null;

	private Matrix result = null;

	public DocTerm(Matrix m) {
		super(m);
	}

	public long getLong(long... coordinates) {
		if (result == null) {
			result = calculate();
		}
		return result.getAsLong(coordinates);
	}

	public long[] getSize() {
		if (result == null) {
			result = calculate();
		}
		return result.getSize();
	}

	public boolean isSparse() {
		return true;
	}

	private Matrix calculate() {
		wordMapping = new DefaultMapMatrix<String, Long>();
		Matrix m = getSource();
		for (long[] c : m.availableCoordinates()) {
			String s = m.getAsString(c);
			if (s != null) {
				String[] words = s.split("\\s+");
				for (String w : words) {
					if (w.length() == 0) {
						continue;
					}
					Long i = wordMapping.get(w);
					if (i == null) {
						wordMapping.put(w, wordMapping.getRowCount());
					}
				}
			}
		}
		result = new DefaultSparseLongMatrix(m.getRowCount(), wordMapping.getRowCount());

		long rowCount = m.getRowCount();
		long colCount = m.getColumnCount();
		for (long row = 0; row < rowCount; row++) {
			for (long col = 0; col < colCount; col++) {
				String string = m.getAsString(row, col);
				if (string != null && string.length() > 0) {
					String[] words = string.split("[\\s]+");
					for (String w : words) {
						if (w.length() == 0) {
							continue;
						}
						long i = wordMapping.get(w);
						int count = result.getAsInt(row, i);
						result.setAsInt(++count, row, i);
					}
				}
			}
		}
		return result;
	}

}
