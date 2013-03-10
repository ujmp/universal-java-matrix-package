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

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.annotation.DefaultAnnotation;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;

public class IncludeAnnotation extends AbstractObjectCalculation {
	private static final long serialVersionUID = -2165678807795583946L;

	private long[] size = null;

	public IncludeAnnotation(Matrix m, int dim) {
		super(dim, m);
		size = Coordinates.copyOf(m.getSize());
		size[dim]++;
		setAnnotation(new DefaultAnnotation(getSize().length));
		getAnnotation().setLabelObject(m.getLabelObject());
	}

	public Object getObject(long... coordinates) throws MatrixException {
		coordinates = Coordinates.copyOf(coordinates);
		if (coordinates[getDimension()] == 0) {
			if (getDimension() == ROW) {
				return getSource().getAxisAnnotation(ROW, coordinates);
			} else if (getDimension() == COLUMN) {
				return getSource().getAxisAnnotation(COLUMN, coordinates);
			} else {
				throw new MatrixException("only possible for Matrix.ROW and Matrix.COLUMN");
			}
		} else {
			coordinates[getDimension()]--;
			return getSource().getAsObject(coordinates);
		}
	}

	public long[] getSize() {
		return size;
	}

	public static void main(String[] args) throws Exception {
		Matrix m = Matrix.Factory.zeros(ValueType.OBJECT, 5, 5);
		m.randn(Ret.ORIG);
		m.setLabel("test");
		m.setColumnLabel(0, "col0");
		m.setColumnLabel(1, "col1");
		m.setColumnLabel(2, "col2");
		m.setColumnLabel(3, "col3");
		m.setColumnLabel(4, "col4");
		m.setRowLabel(0, "row0");
		m.setRowLabel(1, "row1");
		m.setRowLabel(2, "row2");
		m.setRowLabel(3, "row3");
		m.setRowLabel(4, "row4");
		m.setAsDouble(Double.NaN, 2, 2);
		m.setAsDouble(Double.NEGATIVE_INFINITY, 3, 2);
		System.out.println(m);
		System.out.println(m.includeAnnotation(Ret.NEW, Matrix.COLUMN));
	}

}
