/*
 * Copyright (C) 2008-2015 by Holger Arndt
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

import java.util.TreeMap;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.mapmatrix.DefaultMapMatrix;
import org.ujmp.core.mapmatrix.MapMatrix;

public class ExtractAnnotation extends AbstractObjectCalculation {
	private static final long serialVersionUID = 1461447576658284276L;

	private long[] size = null;

	public ExtractAnnotation(Matrix m, int dim) {
		super(dim, m);
		size = Coordinates.copyOf(m.getSize());
		size[dim]--;
		setMetaData(new DefaultMapMatrix<String, Object>(new TreeMap<String, Object>()));
		getMetaData().put(Matrix.LABEL, m.getLabelObject());

		if (dim == ROW) {
			MapMatrix<String, Object> a = m.getMetaData();
			if (a != null) {
				Matrix ai = (Matrix) a.get(Matrix.DIMENSIONMETADATA + COLUMN);
				if (ai != null) {
					ai = ai.deleteRows(Ret.NEW, 0);
					getMetaData().put(Matrix.DIMENSIONMETADATA + COLUMN, ai);
				}
			}
			getMetaData().put(Matrix.DIMENSIONMETADATA + ROW, m.selectRows(Ret.NEW, 0));
		} else if (dim == COLUMN) {
			MapMatrix<String, Object> a = m.getMetaData();
			if (a != null) {
				Matrix ai = (Matrix) a.get(Matrix.DIMENSIONMETADATA + ROW);
				if (ai != null) {
					ai = ai.selectColumns(Ret.NEW, 0);
					getMetaData().put(Matrix.DIMENSIONMETADATA + ROW, ai);
				}
			}
			getMetaData().put(Matrix.DIMENSIONMETADATA + COLUMN, m.selectColumns(Ret.NEW, 0));
		} else {
			throw new RuntimeException("only supported for 2D matrices");
		}
	}

	public Object getObject(long... coordinates) {
		coordinates = Coordinates.copyOf(coordinates);
		coordinates[getDimension()]++;
		return getSource().getAsObject(coordinates);
	}

	public long[] getSize() {
		return size;
	}

}
