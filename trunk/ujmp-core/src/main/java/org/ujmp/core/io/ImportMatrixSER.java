/*
 * Copyright (C) 2008-2010 by Holger Arndt
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

package org.ujmp.core.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.enums.ValueType;

public class ImportMatrixSER {

	public static Matrix fromFile(File file, Object... parameters) throws FileNotFoundException,
			IOException, ClassNotFoundException {
		FileInputStream stream = new FileInputStream(file);
		Matrix m = fromStream(stream);
		stream.close();
		return m;
	}

	public static Matrix fromStream(InputStream stream, Object... parameters)
			throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream s = new ObjectInputStream(new BufferedInputStream(stream));

		ValueType valueType = (ValueType) s.readObject();
		boolean isSparse = s.readBoolean();
		int sizeLength = s.readInt();
		long[] size = new long[sizeLength];
		for (int i = 0; i < sizeLength; i++) {
			size[i] = s.readLong();
		}

		Matrix m = null;
		if (isSparse) {
			m = MatrixFactory.sparse(valueType, size);
		} else {
			m = MatrixFactory.dense(valueType, size);
		}

		long[] c = new long[sizeLength];
		while (s.readBoolean()) {
			for (int i = 0; i < sizeLength; i++) {
				c[i] = s.readLong();
			}
			switch (valueType) {
			case DOUBLE:
				m.setAsDouble(s.readDouble(), c);
				break;
			case INT:
				m.setAsInt(s.readInt(), c);
				break;
			default:
				m.setAsObject(s.readObject(), c);
				break;
			}
		}

		s.close();
		return m;
	}
}
