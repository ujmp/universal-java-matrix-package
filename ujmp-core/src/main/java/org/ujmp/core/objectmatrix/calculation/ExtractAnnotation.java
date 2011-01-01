/*
 * Copyright (C) 2008-2011 by Holger Arndt
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
import org.ujmp.core.annotation.Annotation;
import org.ujmp.core.annotation.DefaultAnnotation;
import org.ujmp.core.exceptions.MatrixException;

public class ExtractAnnotation extends AbstractObjectCalculation {
	private static final long serialVersionUID = 1461447576658284276L;

	private long[] size = null;

	public ExtractAnnotation(Matrix m, int dim) {
		super(dim, m);
		size = Coordinates.copyOf(m.getSize());
		size[dim]--;
		setAnnotation(new DefaultAnnotation(size.length));
		getAnnotation().setLabelObject(m.getLabelObject());

		if (dim == ROW) {
			Annotation a = m.getAnnotation();
			if (a != null) {
				Matrix ai = a.getDimensionMatrix(COLUMN);
				ai = ai.deleteRows(Ret.NEW, 0);
				getAnnotation().setDimensionMatrix(COLUMN, ai);
			}
			getAnnotation().setDimensionMatrix(ROW, m.selectRows(Ret.NEW, 0));
		} else if (dim == COLUMN) {
			Annotation a = m.getAnnotation();
			if (a != null) {
				Matrix ai = a.getDimensionMatrix(ROW);
				ai = ai.selectColumns(Ret.NEW, 0);
				getAnnotation().setDimensionMatrix(ROW, ai);
			}
			getAnnotation().setDimensionMatrix(COLUMN, m.selectColumns(Ret.NEW, 0));
		} else {
			throw new MatrixException("only supported for 2D matrices");
		}
	}

	public Object getObject(long... coordinates) throws MatrixException {
		coordinates = Coordinates.copyOf(coordinates);
		coordinates[getDimension()]++;
		return getSource().getAsObject(coordinates);
	}

	public long[] getSize() {
		return size;
	}

}
