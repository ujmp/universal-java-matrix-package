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

package org.ujmp.jung;

import java.util.Collection;
import java.util.Iterator;

import org.ujmp.core.Matrix;
import org.ujmp.core.collections.AbstractCollection;
import org.ujmp.core.graphmatrix.GraphMatrix;
import org.ujmp.core.util.MathUtil;

public class EdgeWrapperCollection<E> extends AbstractCollection<EdgeWrapper<E>> {
	private static final long serialVersionUID = 5767878887189116726L;

	private final GraphMatrix<?, E> graphMatrix;

	public EdgeWrapperCollection(GraphMatrix<?, E> graphMatrix) {
		this.graphMatrix = graphMatrix;
	}

	public boolean add(EdgeWrapper<E> e) {
		throw new UnsupportedOperationException("not allowed");
	}

	public void clear() {
		throw new UnsupportedOperationException("not allowed");
	}

	public boolean contains(Object o) {
		if (o == null) {
			return false;
		}
		if (o instanceof EdgeWrapper) {
			return graphMatrix.containsCoordinates(((EdgeWrapper<?>) o).getCoordinates().getLongCoordinates());
		}
		return false;
	}

	public Iterator<EdgeWrapper<E>> iterator() {
		Iterator<EdgeWrapper<E>> iterator = new Iterator<EdgeWrapper<E>>() {

			Iterator<long[]> coordinates = graphMatrix.availableCoordinates().iterator();

			public boolean hasNext() {
				return coordinates.hasNext();
			}

			public EdgeWrapper<E> next() {
				long[] c = coordinates.next();
				E edge = graphMatrix.getEdge(c[Matrix.ROW], c[Matrix.COLUMN]);
				return new EdgeWrapper<E>(c[Matrix.ROW], c[Matrix.COLUMN], edge);
			}

			public void remove() {
				coordinates.remove();
			}
		};
		return iterator;
	}

	public boolean remove(Object o) {
		throw new UnsupportedOperationException("not allowed");
	}

	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException("not allowed");
	}

	public int size() {
		return MathUtil.longToInt(graphMatrix.getValueCount());
	}

}
