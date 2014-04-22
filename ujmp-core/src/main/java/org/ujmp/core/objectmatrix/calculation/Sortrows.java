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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ujmp.core.Matrix;
import org.ujmp.core.annotation.Annotation;
import org.ujmp.core.longmatrix.LongMatrix2D;
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

	private void createSortIndex() {
		Matrix m = getSource();
		long rowCount = m.getRowCount();
		List<Sortable<?, ?>> rows = new ArrayList<Sortable<?, ?>>();

		switch (m.getValueType()) {
		case BIGDECIMAL:
			for (long r = 0; r < rowCount; r++) {
				BigDecimal c = m.getAsBigDecimal(r, column);
				Sortable<?, ?> s = new Sortable<BigDecimal, Long>(c, r, true);
				rows.add(s);
			}
			break;
		case BIGINTEGER:
			for (long r = 0; r < rowCount; r++) {
				BigInteger c = m.getAsBigInteger(r, column);
				Sortable<?, ?> s = new Sortable<BigInteger, Long>(c, r, true);
				rows.add(s);
			}
			break;
		case DOUBLE:
			for (long r = 0; r < rowCount; r++) {
				Double c = m.getAsDouble(r, column);
				Sortable<?, ?> s = new Sortable<Double, Long>(c, r, true);
				rows.add(s);
			}
			break;
		case INT:
			for (long r = 0; r < rowCount; r++) {
				Integer c = m.getAsInt(r, column);
				Sortable<?, ?> s = new Sortable<Integer, Long>(c, r, true);
				rows.add(s);
			}
			break;
		case FLOAT:
			for (long r = 0; r < rowCount; r++) {
				Float c = m.getAsFloat(r, column);
				Sortable<?, ?> s = new Sortable<Float, Long>(c, r, true);
				rows.add(s);
			}
			break;
		case CHAR:
			for (long r = 0; r < rowCount; r++) {
				Character c = m.getAsChar(r, column);
				Sortable<?, ?> s = new Sortable<Character, Long>(c, r, true);
				rows.add(s);
			}
			break;
		case BYTE:
			for (long r = 0; r < rowCount; r++) {
				Byte c = m.getAsByte(r, column);
				Sortable<?, ?> s = new Sortable<Byte, Long>(c, r, true);
				rows.add(s);
			}
			break;
		case BOOLEAN:
			for (long r = 0; r < rowCount; r++) {
				Boolean c = m.getAsBoolean(r, column);
				Sortable<?, ?> s = new Sortable<Boolean, Long>(c, r, true);
				rows.add(s);
			}
			break;
		case LONG:
			for (long r = 0; r < rowCount; r++) {
				Long c = m.getAsLong(r, column);
				Sortable<?, ?> s = new Sortable<Long, Long>(c, r, true);
				rows.add(s);
			}
			break;
		case SHORT:
			for (long r = 0; r < rowCount; r++) {
				Short c = m.getAsShort(r, column);
				Sortable<?, ?> s = new Sortable<Short, Long>(c, r, true);
				rows.add(s);
			}
			break;
		default:
			for (long r = 0; r < rowCount; r++) {
				String c = m.getAsString(r, column);
				Sortable<?, ?> s = new Sortable<String, Long>(c, r, true);
				rows.add(s);
			}
			break;
		}

		Collections.sort(rows);
		if (reverse) {
			Collections.reverse(rows);
		}

		LongMatrix2D indexMatrix = LongMatrix2D.Factory.zeros(rows.size(), 1);

		Annotation annotation = m.getAnnotation();
		if (annotation != null) {
			annotation = m.getAnnotation().clone();
			setAnnotation(annotation);
		}

		for (int r = 0; r < rows.size(); r++) {
			indexMatrix.setLong((Long) (rows.get(r)).getObject(), r, 0);
			if (annotation != null) {
				Object o = m.getAxisAnnotation(Matrix.COLUMN,
						new long[] { (Long) (rows.get(r)).getObject(), 0 });
				annotation.setAxisAnnotation(Matrix.COLUMN, o, new long[] { r, 0 });
			}
		}

		this.index = indexMatrix;
	}

	public LongMatrix2D getIndex() {
		return index;
	}

}
