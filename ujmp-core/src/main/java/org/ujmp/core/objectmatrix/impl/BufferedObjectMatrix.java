/*
 * Copyright (C) 2008-2014 by Holger Arndt
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
import java.util.SortedSet;
import java.util.TreeSet;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.matrix.factory.BaseMatrixFactory;
import org.ujmp.core.objectmatrix.stub.AbstractSparseObjectMatrix;

public class BufferedObjectMatrix extends AbstractSparseObjectMatrix implements Flushable {
	private static final long serialVersionUID = 7750549087897737457L;

	private boolean lazy = true;

	private Matrix inputBuffer = null;

	private SortedSet<Coordinates> outputToDoBuffer = Collections
			.synchronizedSortedSet(new TreeSet<Coordinates>());

	private SortedSet<Coordinates> inputToDoBuffer = Collections
			.synchronizedSortedSet(new TreeSet<Coordinates>());

	private int outputBufferSize = Integer.MAX_VALUE;

	private Matrix original = null;

	private Thread writeThread = null;

	private Thread readThread = null;

	private static final EmptyObject EMPTYOBJECT = new EmptyObject();

	public BufferedObjectMatrix(Matrix original) {
		super(original.getSize());
		this.original = original;
		inputBuffer = new DefaultSparseObjectMatrix(original.getSize());
		setInputBufferSize(0);
		setOutputBufferSize(Integer.MAX_VALUE);
		writeThread = new WriteThread();
		writeThread.start();
		readThread = new ReadThread();
		readThread.start();
	}

	public BufferedObjectMatrix(Matrix original, int outputBufferSize) {
		super(original.getSize());
		this.original = original;
		setInputBufferSize(0);
		setOutputBufferSize(outputBufferSize);
	}

	public BufferedObjectMatrix(Matrix original, int outputBufferSize, int inputBufferSize) {
		super(original.getSize());
		this.original = original;
		setInputBufferSize(inputBufferSize);
		setOutputBufferSize(outputBufferSize);
	}

	public synchronized long[] getSize() {
		return inputBuffer.getSize();
	}

	public synchronized Object getObject(long... coordinates) {
		Object o = null;
		o = inputBuffer.getAsObject(coordinates);
		if (o == null) {
			if (lazy) {
				Coordinates c = Coordinates.wrap(coordinates);
				if (!inputToDoBuffer.contains(c)) {
					inputToDoBuffer.add(c.clone());
				}
			} else {
				o = original.getAsObject(coordinates);
				if (o == null) {
					inputBuffer.setAsObject(EMPTYOBJECT, coordinates);
				} else {
					inputBuffer.setAsObject(o, coordinates);
				}
			}
		} else if (o == EMPTYOBJECT) {
			return null;
		}
		return o;
	}

	public synchronized long getValueCount() {
		return original.getValueCount();
	}

	public synchronized void setObject(Object value, long... coordinates) {
		inputBuffer.setAsObject(value, coordinates);
		outputToDoBuffer.add(Coordinates.wrap(coordinates).clone());
	}

	public synchronized void setInputBufferSize(int numElements) {
		if (numElements < 1) {
			inputBuffer = new VolatileSparseObjectMatrix(original.getSize());
		} else {
			inputBuffer = new DefaultSparseObjectMatrix(original.getSize());
		}
	}

	public synchronized void setOutputBufferSize(int numElements) {
		try {
			flush();
			outputToDoBuffer = Collections.synchronizedSortedSet(new TreeSet<Coordinates>());
			outputBufferSize = numElements;
		} catch (IOException e) {
			throw new RuntimeException("could not set output buffer", e);
		}
	}

	public synchronized void flush() throws IOException {
		while (outputToDoBuffer != null && outputToDoBuffer.size() != 0) {
			try {
				outputToDoBuffer.wait();
			} catch (InterruptedException e) {
				throw new RuntimeException("could not flush buffer", e);
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
					while (outputToDoBuffer != null && !outputToDoBuffer.isEmpty()) {
						Coordinates c = outputToDoBuffer.first();
						outputToDoBuffer.remove(c);
						Object value = inputBuffer.getAsObject(c.getLongCoordinates());
						original.setAsObject(value, c.getLongCoordinates());
					}
					Thread.sleep(100);
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException("error writing to matrix", e);
				}
			}
		}
	}

	class ReadThread extends Thread {

		public void run() {
			long t = System.currentTimeMillis();
			boolean update = false;
			while (true) {
				try {
					while (inputToDoBuffer != null && !inputToDoBuffer.isEmpty()) {
						update = true;
						Coordinates c = inputToDoBuffer.first();
						inputToDoBuffer.remove(c);
						Object value = original.getAsObject(c.getLongCoordinates());
						inputBuffer.setAsObject(value, c.getLongCoordinates());
						if (System.currentTimeMillis() - t > 500) {
							t = System.currentTimeMillis();
							fireValueChanged();
						}
					}
					if (update) {
						update = false;
						fireValueChanged();
					}
					Thread.sleep(100);
				} catch (Exception e) {
					e.printStackTrace();
					// throw new RuntimeException("error writing to matrix", e);
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

	public BaseMatrixFactory<? extends Matrix> getFactory() {
		throw new RuntimeException("not implemented");
	}

}

class EmptyObject {
}
