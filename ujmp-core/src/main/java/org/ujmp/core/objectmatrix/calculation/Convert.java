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

import org.ujmp.core.Matrix;
import org.ujmp.core.annotation.Annotation;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;

public class Convert {
	public static final long serialVersionUID = 6393277198816850597L;

	public static Matrix calcNew(ValueType valueType, Matrix source) throws MatrixException {
		Matrix ret = Matrix.Factory.zeros(valueType, source.getSize());
		for (long[] c : source.availableCoordinates()) {
			ret.setAsObject(source.getAsObject(c), c);
		}
		Annotation a = source.getAnnotation();
		if (a != null) {
			ret.setAnnotation(a.clone());
		}
		return ret;
	}

	public static Matrix calcNew(Matrix matrix) throws MatrixException {
		return calcNew(matrix.getValueType(), matrix);
	}

}
