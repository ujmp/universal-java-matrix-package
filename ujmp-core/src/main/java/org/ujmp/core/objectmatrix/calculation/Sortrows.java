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

package org.ujmp.core.objectmatrix.calculation;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.longmatrix.LongMatrix2D;
import org.ujmp.core.mapmatrix.DefaultMapMatrix;
import org.ujmp.core.mapmatrix.MapMatrix;
import org.ujmp.core.objectmatrix.impl.DefaultSparseObjectMatrix;
import org.ujmp.core.util.Sortable;

/**
 * Sorts the rows of a matrix
 */
public class Sortrows extends AbstractObjectCalculation {
	private static final long serialVersionUID = -6935375114060680121L;

	private LongMatrix2D index = null;

	private long column = 0;

	private boolean reverse = false;

	public Sortrows(Matrix m, long column, boolean reverse) {
		super(m);
		this.column = Math.abs(column);
		this.reverse = reverse;
		createSortIndex();
	}

	public Object getObject(long... coordinates) {
		return getSource().getAsObject(index.getLong(coordinates[ROW], 0), coordinates[COLUMN]);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void createSortIndex() {
		Matrix m = getSource();
		long rowCount = m.getRowCount();
		List<Sortable<?, Long>> rows = new ArrayList<Sortable<?, Long>>();

		switch (m.getValueType()) {
		case BIGDECIMAL:
			for (long r = 0; r < rowCount; r++) {
				BigDecimal c = m.getAsBigDecimal(r, column);
				rows.add(new Sortable<BigDecimal, Long>(c, r, true));
			}
			break;
		case BIGINTEGER:
			for (long r = 0; r < rowCount; r++) {
				BigInteger c = m.getAsBigInteger(r, column);
				rows.add(new Sortable<BigInteger, Long>(c, r, true));
			}
			break;
		case DOUBLE:
			for (long r = 0; r < rowCount; r++) {
				Double c = m.getAsDouble(r, column);
				rows.add(new Sortable<Double, Long>(c, r, true));
			}
			break;
		case INT:
			for (long r = 0; r < rowCount; r++) {
				Integer c = m.getAsInt(r, column);
				rows.add(new Sortable<Integer, Long>(c, r, true));
			}
			break;
		case FLOAT:
			for (long r = 0; r < rowCount; r++) {
				Float c = m.getAsFloat(r, column);
				rows.add(new Sortable<Float, Long>(c, r, true));
			}
			break;
		case CHAR:
			for (long r = 0; r < rowCount; r++) {
				Character c = m.getAsChar(r, column);
				rows.add(new Sortable<Character, Long>(c, r, true));
			}
			break;
		case BYTE:
			for (long r = 0; r < rowCount; r++) {
				Byte c = m.getAsByte(r, column);
				rows.add(new Sortable<Byte, Long>(c, r, true));
			}
			break;
		case BOOLEAN:
			for (long r = 0; r < rowCount; r++) {
				Boolean c = m.getAsBoolean(r, column);
				rows.add(new Sortable<Boolean, Long>(c, r, true));
			}
			break;
		case LONG:
			for (long r = 0; r < rowCount; r++) {
				Long c = m.getAsLong(r, column);
				rows.add(new Sortable<Long, Long>(c, r, true));
			}
			break;
		case SHORT:
			for (long r = 0; r < rowCount; r++) {
				Short c = m.getAsShort(r, column);
				rows.add(new Sortable<Short, Long>(c, r, true));
			}
			break;
		default:
			for (long r = 0; r < rowCount; r++) {
				String c = m.getAsString(r, column);
				rows.add(new Sortable<String, Long>(c, r, true));
			}
			break;
		}

		// cast to List seems to solve compile problems for some people
		Collections.sort((List) rows);
		if (reverse) {
			Collections.reverse((List) rows);
		}

		LongMatrix2D indexMatrix = LongMatrix2D.Factory.zeros(rows.size(), 1);

		MapMatrix<String, Object> annotation = m.getMetaData();
		if (annotation != null) {
			annotation = new DefaultMapMatrix<String, Object>(new TreeMap<String, Object>());
			for (String key : m.getMetaData().keySet()) {
				Object o = m.getMetaData(key);
				if (o instanceof Matrix) {
					annotation.put(key, ((Matrix) o).clone());
				} else {
					annotation.put(key, o);
				}
			}
			setMetaData(annotation);
		}

		for (int r = 0; r < rows.size(); r++) {
			indexMatrix.setLong((Long) (rows.get(r)).getObject(), r, 0);
			if (annotation != null) {
				Object o = m.getDimensionMetaData(Matrix.COLUMN,
						new long[] { (Long) (rows.get(r)).getObject(), 0 });

				// annotation.setAxisAnnotation(Matrix.COLUMN, o, new long[] {
				// r, 0 });

				Matrix ma = (Matrix) annotation.get(Matrix.DIMENSIONMETADATA + Matrix.COLUMN);
				if (ma == null) {
					ma = new DefaultSparseObjectMatrix(1, 1);
					annotation.put(Matrix.DIMENSIONMETADATA + Matrix.COLUMN, ma);
				}
				if (!Coordinates.isSmallerThan(new long[] { r, 0 }, ma.getSize())) {
					long[] newSize = Coordinates.max(ma.getSize(),
							Coordinates.plus(new long[] { r, 0 }, 1));
					ma.setSize(newSize);
				}
				ma.setAsObject(o, new long[] { r, 0 });

			}
		}

		this.index = indexMatrix;
	}

	public LongMatrix2D getIndex() {
		return index;
	}

}
