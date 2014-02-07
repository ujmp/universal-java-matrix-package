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

package org.ujmp.core.graphmatrix;

import java.util.List;

import org.ujmp.core.genericmatrix.GenericMatrix2D;

public interface GraphMatrix<N, E> extends GenericMatrix2D<E> {

	public List<N> getNodeList();

	public boolean isDirected();

	public void setDirected(boolean directed);

	public E getEdge(long nodeIndex1, long nodeIndex2);

	public E getEdge(N node1, N node2);

	public void setEdge(E edge, long nodeIndex1, long nodeIndex2);

	public void setEdge(E edge, N node1, N node2);

	public N getNode(long index);

	public void addNode(N node);

	public void setNode(N node, long index);

	public void removeNode(N node);

	public void removeNode(long node);

	public boolean isConnected(long nodeIndex1, long nodeIndex2);

	public boolean isConnected(N node1, N node2);

	public long getIndexOfNode(N node);

	public int getEdgeCount();

	public int getNodeCount();

	public int getChildCount(long nodeIndex);

	public int getChildCount(N node);

	public int getParentCount(long nodeIndex);

	public int getParentCount(N node);

	public int getDegree(long nodeIndex);

	public int getDegree(N node);

	public List<N> getChildren(long nodeIndex);

	public List<N> getChildren(N node);

	public List<Long> getChildIndices(long nodeIndex);

	public List<Long> getChildIndices(N node);

	public List<N> getParents(long nodeIndex);

	public List<N> getParents(N node);

	public List<Long> getParentIndices(long nodeIndex);

	public List<Long> getParentIndices(N node);

	public void removeEdge(N node1, N node2);

	public void removeEdge(long nodeIndex1, long nodeIndex2);

}
