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

package org.ujmp.jmatio;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;

import com.jmatio.io.MatFileReader;
import com.jmatio.types.MLArray;
import com.jmatio.types.MLDouble;

public class ImportMatrixMAT {

	public static Matrix fromFile(File file, Object... parameters) throws IOException {
		String key = null;
		if (parameters != null && parameters.length > 0 && parameters[0] instanceof String) {
			key = (String) parameters[0];
		}
		MatFileReader reader = new MatFileReader();
		Map<String, MLArray> map = reader.read(file);
		if (key == null) {
			key = map.keySet().iterator().next();
		}
		MLArray array = map.get(key);
		if (array == null) {
			throw new MatrixException("matrix with label [" + key + "] was not found in .mat file");
		} else if (array instanceof MLDouble) {
			return new MLDoubleMatrix((MLDouble) array);
		} else {
			throw new MatrixException("This type is not yet supported: " + array.getClass());
		}
	}

}
