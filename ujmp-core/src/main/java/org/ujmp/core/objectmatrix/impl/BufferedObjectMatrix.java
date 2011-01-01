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

import java.io.Flushable;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.objectmatrix.stub.AbstractSparseObjectMatrix;

public class BufferedObjectMatrix extends AbstractSparseObjectMatrix implements Flushable {
	private static final long serialVersionUID = 7750549087897737457L;

	private Matrix inputBuffer = null;

	private Set<Coordinates> outputBuffer = null;

	private int outputBufferSize = Integer.MAX_VALUE;

	private Matrix original = null;

	private Thread writeThread = null;

	private static final EmptyObject EMPTYOBJECT = new EmptyObject();

	public BufferedObjectMatrix(Matrix original) {
		super(original);
		this.original = original;
		setInputBufferSize(0);
		setOutputBufferSize(Integer.MAX_VALUE);
		writeThread = new WriteThread();
		writeThread.start();
	}

	public BufferedObjectMatrix(Matrix original, int outputBufferSize) {
		super(original);
		this.original = original;
		setInputBufferSize(0);
		setOutputBufferSize(outputBufferSize);
	}

	public BufferedObjectMatrix(Matrix original, int outputBufferSize, int inputBufferSize) {
		super(original);
		this.original = original;
		setInputBufferSize(inputBufferSize);
		setOutputBufferSize(outputBufferSize);
	}

	public synchronized long[] getSize() {
		return inputBuffer.getSize();
	}

	public synchronized Object getObject(long... coordinates) throws MatrixException {
		Object o = null;
		o = inputBuffer.getAsObject(coordinates);
		if (o == null) {
			o = original.getAsObject(coordinates);
			if (o == null) {
				inputBuffer.setAsObject(EMPTYOBJECT, coordinates);
			} else {
				inputBuffer.setAsObject(o, coordinates);
			}
		} else if (o == EMPTYOBJECT) {
			return null;
		}
		return o;
	}

	public synchronized long getValueCount() {
		return original.getValueCount();
	}

	public synchronized void setObject(Object value, long... coordinates) throws MatrixException {
		inputBuffer.setAsObject(value, coordinates);
		outputBuffer.add(new Coordinates(coordinates));
	}

	public synchronized void setInputBufferSize(int numElements) {
		if (numElements < 1) {
			inputBuffer = new VolatileSparseObjectMatrix(original.getSize());
		} else {
			inputBuffer = new DefaultSparseObjectMatrix(numElements, original.getSize());
		}
	}

	public synchronized void setOutputBufferSize(int numElements) {
		try {
			flush();
			outputBuffer = Collections.synchronizedSet(new HashSet<Coordinates>());
			outputBufferSize = numElements;
		} catch (IOException e) {
			throw new MatrixException("could not set output buffer", e);
		}
	}

	public synchronized void flush() throws IOException {
		while (outputBuffer != null && outputBuffer.size() != 0) {
			try {
				outputBuffer.wait();
			} catch (InterruptedException e) {
				throw new MatrixException("could not flush buffer", e);
			}
		}
	}

	public synchronized int getOutputBufferSize() {
		return outputBufferSize;
	}

	class WriteThread extends Thread {

		public void run() {
			while (true) {
				try {
					while (outputBuffer != null && !outputBuffer.isEmpty()) {
						Coordinates c = outputBuffer.iterator().next();
						outputBuffer.remove(c);
						double value = inputBuffer.getAsDouble(c.co);
						original.setAsDouble(value, c.co);
					}
					Thread.sleep(100);
				} catch (Exception e) {
					throw new MatrixException("error writing to matrix", e);
				}
			}
		}
	}

	public boolean contains(long... coordinates) {
		return inputBuffer.contains(coordinates) || original.contains(coordinates);
	}

	public boolean isReadOnly() {
		return original.isReadOnly();
	}

}

class EmptyObject {
}
