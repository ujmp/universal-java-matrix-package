/*
 * Copyright (C) 2008-2009 Holger Arndt, A. Naegele and M. Bundschus
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

import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.genericmatrix.AbstractSparseGenericMatrix2D;

public abstract class AbstractGraphMatrix<N, E> extends AbstractSparseGenericMatrix2D<E> implements
		GraphMatrix<N, E> {
	private static final long serialVersionUID = -4939918585100574441L;

	public boolean contains(long... coordinates) {
		return getEdgeList().contains(new Coordinates(coordinates));
	}

	public void removeUndirectedEdge(N node1, N node2) {
		long index1 = getIndexOfNode(node1);
		long index2 = getIndexOfNode(node2);
		removeDirectedEdge(index1, index2);
		removeDirectedEdge(index2, index1);
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

	public E getEdgeValue(N node1, N node2) {
		long index1 = getIndexOfNode(node1);
		long index2 = getIndexOfNode(node2);
		return getEdgeValue(index1, index2);
	}

	public int getDegree(N node) {
		return getParentCount(node) + getChildCount(node);
	}

	public int getDegree(long nodeIndex) {
		return getParentCount(nodeIndex) + getChildCount(nodeIndex);
	}

	public void addChild(long nodeIndex, long childIndex) {
		addEdge(nodeIndex, childIndex);
	}

	public void addParent(long nodeIndex, long parentIndex) {
		addEdge(nodeIndex, parentIndex);
	}

	public int getChildCount(N node) {
		return getChildCount(getIndexOfNode(node));
	}

	public void addParent(N node, N parent) {
		long parentIndex = getIndexOfNode(node);
		long nodeIndex = getIndexOfNode(node);
		addParent(nodeIndex, parentIndex);
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

	public void removeUndirectedEdge(long nodeIndex1, long nodeIndex2) {
		removeDirectedEdge(nodeIndex1, nodeIndex2);
		removeDirectedEdge(nodeIndex2, nodeIndex1);
	}

	public void removeDirectedEdge(N node1, N node2) {
		long index1 = getIndexOfNode(node1);
		long index2 = getIndexOfNode(node2);
		removeDirectedEdge(index1, index2);
	}

	@Override
	public Iterable<long[]> availableCoordinates() {
		return getEdgeList();
	}

	public long[] getSize() {
		return new long[] { getNodeList().size(), getNodeList().size() };
	}

	public E getObject(long row, long column) {
		return getEdgeValue(row, column);
	}

	public E getObject(int row, int column) {
		return getEdgeValue(row, column);
	}

	@Override
	public long getValueCount() {
		return getEdgeList().size();
	}

	public final void addDirectedEdge(N node1, N node2) {
		long i1 = getIndexOfNode(node1);
		long i2 = getIndexOfNode(node2);
		addDirectedEdge(i1, i2);
	}

	public abstract void addUndirectedEdge(long node1, long node2);

	public abstract void addDirectedEdge(long node1, long node2);

	public final void addUndirectedEdge(N node1, N node2) {
		long i1 = getIndexOfNode(node1);
		long i2 = getIndexOfNode(node2);
		addUndirectedEdge(i1, i2);
	}

	public final boolean isConnected(long node1, long node2) {
		return getObject(node1, node2) != null;
	}

	public final long getIndexOfNode(N o) {
		return (getNodeList()).indexOf(o);
	}

	public void setUndirectedEdge(E value, long node1, long node2) {
		setDirectedEdge(value, node1, node2);
		setDirectedEdge(value, node2, node1);
	}

	// public final void setObject(E v, long... coordinates) {
	// setDirectedEdge(v, coordinates[ROW], coordinates[COLUMN]);
	// }

	public void setDirectedEdge(E edgeObject, N node1, N node2) {
		long index1 = getIndexOfNode(node1);
		long index2 = getIndexOfNode(node2);
		setDirectedEdge(edgeObject, index1, index2);
	}

	public void setUndirectedEdge(E edgeObject, N node1, N node2) {
		long index1 = getIndexOfNode(node1);
		long index2 = getIndexOfNode(node2);
		setUndirectedEdge(edgeObject, index1, index2);
	}

	@Override
	public abstract void clear();

	@Override
	public final StorageType getStorageType() {
		return StorageType.GRAPH;
	}

}
