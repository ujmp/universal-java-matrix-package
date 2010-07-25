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

import java.io.File;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.enums.FileFormat;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.objectmatrix.stub.AbstractObjectMatrix;
import org.ujmp.core.util.VerifyUtil;
import org.ujmp.core.util.io.FileUtil;

public abstract class LinkMatrix {

	public static Matrix toFile(File file, Object... parameters) throws MatrixException,
			IOException {
		return toFile(FileUtil.guessFormat(file), file, parameters);
	}

	public static Matrix toFile(FileFormat format, File file, Object... parameters)
			throws MatrixException, IOException {
		try {
			Class<?> c = Class.forName("org.ujmp.core.io.LinkMatrix" + format.name());
			Method m = c.getMethod("toFile", new Class<?>[] { File.class, Object[].class });
			Matrix matrix = (Matrix) m.invoke(null, file, parameters);
			VerifyUtil.assertNotNull(matrix, "matrix could not be linked");
			return matrix;
		} catch (ClassNotFoundException e) {
			try {
				return new DelayedContentMatrix(format, file, parameters);
			} catch (ClassCastException ex) {
				throw new MatrixException("format not supported: " + format, e);
			}
		} catch (NoSuchMethodException e) {
			throw new MatrixException("format not supported: " + format, e);
		} catch (IllegalAccessException e) {
			throw new MatrixException("format not supported: " + format, e);
		} catch (InvocationTargetException e) {
			throw new MatrixException("could not import", e);
		}
	}
}

class DelayedContentMatrix extends AbstractObjectMatrix {
	private static final long serialVersionUID = -2594340094573426876L;

	private SoftReference<Matrix> matrix = null;

	private FileFormat fileformat = null;
	private File file = null;
	private Object[] parameters = null;

	public DelayedContentMatrix(FileFormat fileformat, File file, Object... parameters) {
		this.fileformat = fileformat;
		this.file = file;
		this.parameters = parameters;
	}

	public Object getObject(long... coordinates) throws MatrixException {
		return getMatrix().getAsObject(coordinates);
	}

	private Matrix getMatrix() {
		if (matrix == null || matrix.get() == null) {
			try {
				matrix = new SoftReference<Matrix>(MatrixFactory.importFromFile(fileformat, file,
						parameters));
			} catch (Exception e) {
				return MatrixFactory.emptyMatrix();
			}
		}
		return matrix.get();
	}

	public void setObject(Object value, long... coordinates) {
		getMatrix().setAsObject(value, coordinates);
	}

	public boolean contains(long... coordinates) throws MatrixException {
		return getMatrix().contains(coordinates);
	}

	public long[] getSize() {
		return getMatrix().getSize();
	}

	public final StorageType getStorageType() {
		return getMatrix().getStorageType();
	}

}