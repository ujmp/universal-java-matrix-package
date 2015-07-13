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

package org.ujmp.core.util;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.json.JSONTokener;
import org.ujmp.core.Matrix;
import org.ujmp.core.listmatrix.ListMatrix;
import org.ujmp.core.mapmatrix.MapMatrix;
import org.ujmp.core.setmatrix.SetMatrix;

public abstract class JsonUtil {

	public static String toJson(Matrix m) {
		if (m instanceof ListMatrix) {
			return toJson((ListMatrix<?>) m);
		} else if (m instanceof MapMatrix) {
			return toJson((MapMatrix<?, ?>) m);
		} else if (m instanceof SetMatrix) {
			return toJson((SetMatrix<?>) m);
		} else {
			StringBuilder s = new StringBuilder();
			return s.toString();
		}
	}

	public static String toJson(ListMatrix<?> m) {
		StringBuilder s = new StringBuilder();
		return s.toString();
	}

	public static String toJson(MapMatrix<?, ?> m) {
		StringBuilder s = new StringBuilder();
		return s.toString();
	}

	public static String toJson(SetMatrix<?> m) {
		StringBuilder s = new StringBuilder();
		return s.toString();
	}

	public static Matrix parseJson(String string) throws IOException {
		StringReader s = new StringReader(string);
		return parseJson(s);
	}

	public static Matrix parseJson(Reader reader) throws IOException {
		return parseJson(new JSONTokener(reader));
	}

	public static Matrix parseJson(JSONTokener t) throws IOException {
		Object obj = t.nextValue();
		if (obj instanceof String) {
			return Matrix.Factory.linkToValue((String) obj);
		}
		// TODO
		return null;
	}
}
