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

package org.ujmp.core.graphmatrix;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ujmp.core.Coordinates;
import org.ujmp.core.collections.list.ArrayIndexList;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.CoordinateSetToLongWrapper;

public class DefaultGraphMatrix<N, E> extends AbstractGraphMatrix<N, E> {
	private static final long serialVersionUID = -6103776352324576412L;

	private boolean directed = true;

	private final List<N> nodes = new ArrayIndexList<N>();

	private final Map<Coordinates, E> edges = new HashMap<Coordinates, E>();

	private final Map<Long, List<Long>> parents = new HashMap<Long, List<Long>>();

	private final Map<Long, List<Long>> children = new HashMap<Long, List<Long>>();

	public DefaultGraphMatrix() {
	}

	public DefaultGraphMatrix(List<N> nodes) {
		this.nodes.addAll(nodes);
	}

	public Collection<E> getEdgeList() {
		return edges.values();
	}

	public Iterable<long[]> availableCoordinates() {
		return new CoordinateSetToLongWrapper(edges.keySet());
	}

	public List<N> getNodeList() {
		return nodes;
	}

	public void addNode(N o) {
		nodes.add(o);
	}

	public void removeNode(N o) {
		nodes.remove(o);
	}

	public E getEdgeValue(long node1, long node2) {
		return edges.get(new Coordinates(new long[] { node1, node2 }));
	}

	public int getEdgeCount() {
		return edges.size();
	}

	public int getNodeCount() {
		return nodes.size();
	}

	public void clear() {
		// nodes.clear();
		edges.clear();
		parents.clear();
		children.clear();
	}

	public void addChild(N node, N child) {
		throw new MatrixException("not implemented!");
	}

	public int getChildCount(long nodeIndex) {
		List<Long> indices = children.get(nodeIndex);
		return indices == null ? 0 : indices.size();
	}

	public N getNode(long index) {
		return nodes.get((int) index);
	}

	public int getParentCount(long nodeIndex) {
		List<Long> indices = parents.get(nodeIndex);
		return indices == null ? 0 : indices.size();
	}

	@SuppressWarnings("unchecked")
	public List<Long> getParentIndices(long index) {
		List<Long> indices = parents.get(index);
		return indices == null ? Collections.EMPTY_LIST : indices;
	}

	@SuppressWarnings("unchecked")
	public List<Long> getChildIndices(long index) {
		List<Long> indices = children.get(index);
		return indices == null ? Collections.EMPTY_LIST : indices;
	}

	public void insertNode(N node, long index) {
		nodes.add((int) index, node);
	}

	public void removeDirectedEdge(long nodeIndex1, long nodeIndex2) {
		edges.remove(new Coordinates(new long[] { nodeIndex1, nodeIndex2 }));
	}

	public void removeNode(long node) {
		nodes.remove((int) node);
	}

	public boolean isDirected() {
		return directed;
	}

	public void setDirected(boolean directed) {
		this.directed = directed;
	}

	public void setNode(N node, long index) {
		nodes.set((int) index, node);
	}

	public synchronized void setEdge(E edge, long nodeIndex1, long nodeIndex2) {
		int nmbOfNodes = nodes.size();
		if (nodeIndex1 >= nmbOfNodes)
			throw new MatrixException("accessed node " + nodeIndex1 + ", but only " + nmbOfNodes
					+ " available");
		if (nodeIndex2 >= nmbOfNodes)
			throw new MatrixException("accessed node " + nodeIndex2 + ", but only " + nmbOfNodes
					+ " available");
		edges.put(new Coordinates(nodeIndex1, nodeIndex2), edge);
		List<Long> list = children.get(nodeIndex1);
		if (list == null) {
			list = new ArrayList<Long>();
			children.put(nodeIndex1, list);
		}
		list.add(nodeIndex2);

		list = parents.get(nodeIndex2);
		if (list == null) {
			list = new ArrayList<Long>();
			parents.put(nodeIndex2, list);
		}
		list.add(nodeIndex1);
	}

	public void setEdge(E edge, N node1, N node2) {
		long index1 = getIndexOfNode(node1);
		long index2 = getIndexOfNode(node2);
		setEdge(edge, index1, index2);
	}

	public ValueType getValueType() {
		return ValueType.OBJECT;
	}

	public E getEdge(long nodeIndex1, long nodeIndex2) {
		return null;
	}

	public void setObject(E value, long row, long column) {
	}

	public void setObject(E value, int row, int column) {
	}

}
