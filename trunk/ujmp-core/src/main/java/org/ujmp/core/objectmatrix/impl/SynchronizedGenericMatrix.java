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

package org.ujmp.core.objectmatrix.impl;

import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.genericmatrix.stub.AbstractGenericMatrix;

public class SynchronizedGenericMatrix<T> extends AbstractGenericMatrix<T> {
	private static final long serialVersionUID = -4456493053286654056L;

	private Matrix matrix = null;

	public SynchronizedGenericMatrix(Matrix source) {
		super(source);
		this.matrix = source;
		setAnnotation(source.getAnnotation());
	}

	public synchronized long[] getSize() {
		return matrix.getSize();
	}

	public synchronized double getAsDouble(long... coordinates) throws MatrixException {
		return matrix.getAsDouble(coordinates);
	}

	public synchronized long getValueCount() {
		return matrix.getValueCount();
	}

	public synchronized void setAsDouble(double value, long... coordinates) throws MatrixException {
		matrix.setAsDouble(value, coordinates);
	}

	@SuppressWarnings("unchecked")
	public synchronized T getObject(long... c) throws MatrixException {
		return (T) matrix.getAsObject(c);
	}

	public synchronized void setObject(T value, long... c) throws MatrixException {
		matrix.setAsObject(value, c);
	}

	public synchronized boolean contains(long... coordinates) {
		return matrix.contains(coordinates);
	}

	public synchronized boolean isReadOnly() {
		return matrix.isReadOnly();
	}

	public synchronized StorageType getStorageType() {
		return matrix.getStorageType();
	}

}
