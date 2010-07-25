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

package org.ujmp.core.graphmatrix;

import java.util.ArrayList;
import java.util.List;

import org.ujmp.core.Coordinates;
import org.ujmp.core.genericmatrix.stub.AbstractSparseGenericMatrix2D;
import org.ujmp.core.objectmatrix.SparseObjectMatrix2D;
import org.ujmp.core.objectmatrix.factory.SparseObjectMatrix2DFactory;
import org.ujmp.core.util.MathUtil;

public abstract class AbstractGraphMatrix<N, E> extends AbstractSparseGenericMatrix2D<E> implements
		GraphMatrix<N, E> {
	private static final long serialVersionUID = -4939918585100574441L;

	public boolean contains(long... coordinates) {
		return getEdgeList().contains(new Coordinates(coordinates));
	}

	public boolean isConnected(N node1, N node2) {
		long index1 = getIndexOfNode(node1);
		long index2 = getIndexOfNode(node2);
		return isConnected(index1, index2);
	}

	public List<N> getParents(long index) {
		List<Long> indices = getParentIndices(index);
		List<N> objects = new ArrayList<N>(indices.size());
		for (int i = 0; i < indices.size(); i++) {
			objects.add(getNode(indices.get(i)));
		}
		return objects;
	}

	public List<N> getParents(N node) {
		List<Long> indices = getParentIndices(node);
		List<N> objects = new ArrayList<N>(indices.size());
		for (int i = 0; i < indices.size(); i++) {
			objects.add(getNode(indices.get(i)));
		}
		return objects;
	}

	public List<N> getChildren(long index) {
		List<Long> indices = getChildIndices(index);
		List<N> objects = new ArrayList<N>(indices.size());
		for (int i = 0; i < indices.size(); i++) {
			objects.add(getNode(indices.get(i)));
		}
		return objects;
	}

	public List<Long> getParentIndices(N node) {
		long index = getIndexOfNode(node);
		return getParentIndices(index);
	}

	public E getEdge(N node1, N node2) {
		long index1 = getIndexOfNode(node1);
		long index2 = getIndexOfNode(node2);
		return getEdge(index1, index2);
	}

	public int getDegree(N node) {
		return getParentCount(node) + getChildCount(node);
	}

	public int getDegree(long nodeIndex) {
		return getParentCount(nodeIndex) + getChildCount(nodeIndex);
	}

	public int getChildCount(N node) {
		return getChildCount(getIndexOfNode(node));
	}

	public int getParentCount(N node) {
		long index = getIndexOfNode(node);
		return getParentCount(index);
	}

	public List<Long> getChildIndices(N node) {
		long index = getIndexOfNode(node);
		return getChildIndices(index);
	}

	public List<N> getChildren(N node) {
		List<Long> indices = getChildIndices(node);
		List<N> objects = new ArrayList<N>(indices.size());
		for (int i = 0; i < indices.size(); i++) {
			objects.add(getNode(indices.get(i)));
		}
		return objects;
	}

	public Iterable<long[]> availableCoordinates() {
		// TODO: improve
		return super.availableCoordinates();
	}

	public long[] getSize() {
		return new long[] { getNodeCount(), getNodeCount() };
	}

	public E getObject(long row, long column) {
		return getEdge(row, column);
	}

	public E getObject(int row, int column) {
		return getEdge(row, column);
	}

	public long getValueCount() {
		return getEdgeList().size();
	}

	public final boolean isConnected(long node1, long node2) {
		return MathUtil.getBoolean(getObject(node1, node2));
	}

	public final long getIndexOfNode(N o) {
		return getNodeList().indexOf(o);
	}

	public final StorageType getStorageType() {
		return StorageType.GRAPH;
	}

	public SparseObjectMatrix2DFactory getFactory() {
		return SparseObjectMatrix2D.factory;
	}

}
