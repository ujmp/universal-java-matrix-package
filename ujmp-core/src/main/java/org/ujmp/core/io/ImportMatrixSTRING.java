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

package org.ujmp.core.io;

public abstract class ImportMatrixSTRING extends ImportMatrixCSV {

	// public static final Matrix fromString(String string, Object...
	// parameters)
	// throws MatrixException {
	// StringReader sr = new StringReader(string);
	// IntelligentFileReader r = new IntelligentFileReader(sr);
	// Matrix m = fromReader(r);
	// r.close();
	// return m;
	// }
	//
	// public static final Matrix fromStream(InputStream stream, Object...
	// parameters)
	// throws MatrixException, IOException {
	// InputStreamReader r = new InputStreamReader(stream, "UTF-8");
	// Matrix m = fromReader(r, parameters);
	// r.close();
	// return m;
	// }
	//
	// public static final Matrix fromFile(File file, Object... parameters)
	// throws MatrixException,
	// IOException {
	// FileInputStream lr = new FileInputStream(file);
	// Matrix m = fromStream(lr, parameters);
	// m.setLabel(file.getAbsolutePath());
	// lr.close();
	// return m;
	// }
	//
	// public static final Matrix fromReader(Reader reader, Object...
	// parameters)
	// throws MatrixException {
	// StringBuilder s = new StringBuilder();
	//
	// try {
	// IntelligentFileReader lr = new IntelligentFileReader(reader);
	// String line = null;
	// while ((line = lr.readLine()) != null) {
	// if (line.length() > 0) {
	// s.append(line + "\n");
	// }
	// }
	// lr.close();
	//
	// Matrix m = MatrixFactory.zeros(ValueType.STRING, 1, 1);
	// m.setAsString(s.toString(), 0, 0);
	//
	// return m;
	// } catch (Exception e) {
	// throw new MatrixException(e);
	// }
	// }

}
