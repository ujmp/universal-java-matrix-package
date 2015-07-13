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

package org.ujmp.ehcache;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.interfaces.Erasable;
import org.ujmp.core.objectmatrix.ObjectMatrix2D;
import org.ujmp.core.objectmatrix.stub.AbstractMapToTiledMatrix2DWrapper;

public class EhcacheTiledObjectMatrix2D extends AbstractMapToTiledMatrix2DWrapper implements Erasable, Flushable,
		Closeable {
	private static final long serialVersionUID = 4324063544046176423L;

	public EhcacheTiledObjectMatrix2D(long rows, long columns) throws IOException {
		super(new EhcacheMap<Coordinates, ObjectMatrix2D>(), rows, columns);
	}

	public EhcacheTiledObjectMatrix2D(Matrix source) throws IOException {
		super(new EhcacheMap<Coordinates, ObjectMatrix2D>(), source);
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();
		for (long[] c : availableCoordinates()) {
			s.writeObject(Coordinates.wrap(c));
			s.writeObject(getObject(c));
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		while (true) {
			try {
				Coordinates c = (Coordinates) s.readObject();
				Object o = s.readObject();
				setObject(o, c.getLongCoordinates());
			} catch (OptionalDataException e) {
				return;
			}
		}
	}

	public void erase() throws IOException {
		((Erasable) getMap()).erase();
	}

	public void flush() throws IOException {
		((Flushable) getMap()).flush();
	}

	public void close() throws IOException {
		((Closeable) getMap()).close();
	}

}
