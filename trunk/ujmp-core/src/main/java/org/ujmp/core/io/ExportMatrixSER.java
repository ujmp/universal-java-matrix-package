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

package org.ujmp.core.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import org.ujmp.core.Matrix;
import org.ujmp.core.enums.ValueType;

public class ExportMatrixSER {

	public static final void toFile(File file, Matrix m, Object... parameters) throws IOException {
		FileOutputStream out = new FileOutputStream(file);
		toStream(out, m, parameters);
		out.close();
	}

	public static final void toStream(OutputStream stream, Matrix m, Object... parameters)
			throws IOException {
		ObjectOutputStream s = new ObjectOutputStream(new BufferedOutputStream(stream));

		long[] size = m.getSize();
		int sizeLength = size.length;
		ValueType valueType = m.getValueType();

		s.writeObject(valueType);
		s.writeBoolean(m.isSparse());
		s.writeInt(sizeLength);
		for (int i = 0; i < sizeLength; i++) {
			s.writeLong(size[i]);
		}

		for (long[] c : m.availableCoordinates()) {
			s.writeBoolean(true); // hasNext
			for (int i = 0; i < sizeLength; i++) {
				s.writeLong(c[i]);
			}
			switch (valueType) {
			case DOUBLE:
				s.writeDouble(m.getAsDouble(c));
				break;
			case INT:
				s.writeInt(m.getAsInt(c));
				break;
			default:
				s.writeObject(m.getAsObject(c));
				break;
			}
		}
		s.writeBoolean(false); // hasNext
		s.close();
	}

}
