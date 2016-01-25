/*
 * Copyright (C) 2008-2016 by Holger Arndt
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.ujmp.core.Matrix;
import org.ujmp.core.graphmatrix.GraphMatrix;

import edu.uci.ics.jung.graph.AbstractTypedGraph;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;

public class GraphMatrixWrapper<V, E> extends AbstractTypedGraph<V, EdgeWrapper<E>> implements
		DirectedGraph<V, EdgeWrapper<E>> {
	private static final long serialVersionUID = -3871581250021217530L;

	private final GraphMatrix<V, E> graphMatrix;

	public GraphMatrixWrapper(GraphMatrix<V, E> graphMatrix) {
		super(EdgeType.DIRECTED);
		this.graphMatrix = graphMatrix;
	}

	public Collection<EdgeWrapper<E>> getInEdges(V vertex) {
		long childIndex = graphMatrix.getIndexOfNode(vertex);
		List<Long> parentIndices = graphMatrix.getParentIndices(vertex);
		List<EdgeWrapper<E>> edges = new ArrayList<EdgeWrapper<E>>();
		for (long parentIndex : parentIndices) {
			E edge = graphMatrix.getEdge(parentIndex, childIndex);
			EdgeWrapper<E> edgeWrapper = new EdgeWrapper<E>(parentIndex, childIndex, edge);
			edges.add(edgeWrapper);
		}
		return edges;
	}

	public Collection<EdgeWrapper<E>> getOutEdges(V vertex) {
		long parentIndex = graphMatrix.getIndexOfNode(vertex);
		List<Long> childIndices = graphMatrix.getChildIndices(vertex);
		List<EdgeWrapper<E>> edges = new ArrayList<EdgeWrapper<E>>();
		for (long childIndex : childIndices) {
			E edge = graphMatrix.getEdge(parentIndex, childIndex);
			EdgeWrapper<E> edgeWrapper = new EdgeWrapper<E>(parentIndex, childIndex, edge);
			edges.add(edgeWrapper);
		}
		return edges;
	}

	public Collection<V> getPredecessors(V vertex) {
		return graphMatrix.getParents(vertex);
	}

	public Collection<V> getSuccessors(V vertex) {
		return graphMatrix.getChildren(vertex);
	}

	public V getSource(EdgeWrapper<E> directed_edge) {
		return graphMatrix.getNode(directed_edge.getCoordinates().getLongCoordinates()[Matrix.ROW]);
	}

	public V getDest(EdgeWrapper<E> directed_edge) {
		return graphMatrix.getNode(directed_edge.getCoordinates().getLongCoordinates()[Matrix.COLUMN]);
	}

	public boolean isSource(V vertex, EdgeWrapper<E> edge) {
		V source = getSource(edge);
		return vertex == source;
	}

	public boolean isDest(V vertex, EdgeWrapper<E> edge) {
		V dest = getDest(edge);
		return vertex == dest;
	}

	public Pair<V> getEndpoints(EdgeWrapper<E> edge) {
		long[] c = edge.getCoordinates().getLongCoordinates();
		return new Pair<V>(graphMatrix.getNode(c[0]), graphMatrix.getNode(c[1]));
	}

	public Collection<EdgeWrapper<E>> getEdges() {
		return new EdgeWrapperCollection<E>(graphMatrix);
	}

	public Collection<V> getVertices() {
		return graphMatrix.getNodeList();
	}

	public boolean containsVertex(V vertex) {
		return graphMatrix.getNodeList().contains(vertex);
	}

	public boolean containsEdge(EdgeWrapper<E> edge) {
		for (EdgeWrapper<E> edgeWrapper : getEdges()) {
			if (edgeWrapper.equals(edge)) {
				return true;
			}
		}
		return false;
	}

	public int getEdgeCount() {
		return graphMatrix.getEdgeCount();
	}

	public int getVertexCount() {
		return graphMatrix.getNodeCount();
	}

	public Collection<V> getNeighbors(V vertex) {
		List<V> neighbors = new ArrayList<V>();
		neighbors.addAll(graphMatrix.getParents(vertex));
		neighbors.addAll(graphMatrix.getChildren(vertex));
		return neighbors;
	}

	public Collection<EdgeWrapper<E>> getIncidentEdges(V vertex) {
		List<EdgeWrapper<E>> edges = new ArrayList<EdgeWrapper<E>>();
		edges.addAll(getInEdges(vertex));
		edges.addAll(getOutEdges(vertex));
		return edges;
	}

	public boolean addVertex(V vertex) {
		graphMatrix.addNode(vertex);
		return true;
	}

	public boolean removeVertex(V vertex) {
		graphMatrix.removeNode(vertex);
		return true;
	}

	public boolean removeEdge(EdgeWrapper<E> edge) {
		long[] coordinates = edge.getCoordinates().getLongCoordinates();
		graphMatrix.removeEdge(coordinates[Matrix.ROW], coordinates[Matrix.COLUMN]);
		return true;
	}

	@Override
	public boolean addEdge(EdgeWrapper<E> edge, Pair<? extends V> endpoints, EdgeType edgeType) {
		graphMatrix.setEdge(edge.getEdge(), endpoints.getFirst(), endpoints.getSecond());
		if (edgeType == EdgeType.UNDIRECTED) {
			graphMatrix.setEdge(edge.getEdge(), endpoints.getSecond(), endpoints.getFirst());
		}
		return true;
	}

	public GraphMatrix<V, E> getGraphMatrix() {
		return graphMatrix;
	}

}
