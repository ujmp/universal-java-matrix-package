/*
 * Copyright (C) 2008-2009 by Holger Arndt
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

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;

import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;

public abstract class ImportMatrixPDF {

	public static Matrix fromFile(File file, Object... parameters) {
		try {
			Class<?> c = Class.forName("org.ujmp.pdfbox.ImportMatrixPDF");
			Method method = c.getMethod("fromFile", new Class[] { File.class, Object[].class });
			Matrix matrix = (Matrix) method.invoke(null, file, parameters);
			return matrix;
		} catch (Exception e) {
			throw new MatrixException("ujmp-pdfbox not found in classpath", e);
		}
	}

	public static Matrix fromStream(InputStream stream, Object... parameters) {
		try {
			Class<?> c = Class.forName("org.ujmp.pdfbox.ImportMatrixPDF");
			Method method = c.getMethod("fromStream", new Class[] { InputStream.class,
					Object[].class });
			Matrix matrix = (Matrix) method.invoke(null, stream, parameters);
			return matrix;
		} catch (Exception e) {
			throw new MatrixException("ujmp-pdfbox not found in classpath", e);
		}
	}

}
