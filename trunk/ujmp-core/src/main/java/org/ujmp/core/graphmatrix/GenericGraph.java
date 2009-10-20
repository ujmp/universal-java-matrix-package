/*
 * Copyright (C) 2008-2009 by Holger Arndt
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

import java.util.Collection;
import java.util.List;

public interface GenericGraph<N, E> {

	public List<N> getNodeList();

	public boolean isDirected();

	public void setDirected(boolean directed);

	public Collection<E> getEdgeList();

	public E getEdgeValue(long nodeIndex1, long nodeIndex2);

	/**
	 * @holger Wuerde ich weglassen
	 * @deprecated
	 */
	@Deprecated
	public E getEdgeValue(N node1, N node2);

	/**
	 * @holger Methode macht keinen Sinn, da Kante nich erzeugt werden kann.
	 * @param node1
	 * @param node2
	 * @deprecated
	 */
	@Deprecated
	public void addEdge(N node1, N node2);

	/**
	 * @holger Methode macht keinen Sinn, da Kante nich erzeugt werden kann.
	 * @param nodeIndex1
	 * @param nodeIndex2
	 * @deprecated
	 */
	@Deprecated
	public void addEdge(long nodeIndex1, long nodeIndex2);

	/**
	 * @holger Methode macht keinen Sinn, da Kante nich erzeugt werden kann.
	 * @param node1
	 * @param node2
	 * @deprecated
	 */
	@Deprecated
	public void addDirectedEdge(N node1, N node2);

	/**
	 * @holger Methode macht keinen Sinn, da Kante nich erzeugt werden kann.
	 * @param nodeIndex1
	 * @param nodeIndex2
	 * @deprecated
	 */
	@Deprecated
	public void addDirectedEdge(long nodeIndex1, long nodeIndex2);

	/**
	 * @holger Wuerde ich weglassen
	 * @param edgeObject
	 * @param nodeIndex1
	 * @param nodeIndex2
	 * @deprecated
	 */
	@Deprecated
	public void setDirectedEdge(E edgeObject, long nodeIndex1, long nodeIndex2);

	public void setEdge(E edgeObject, long nodeIndex1, long nodeIndex2);

	/**
	 * @holger Wuerde ich weglassen
	 * @deprecated
	 */
	@Deprecated
	public void setDirectedEdge(E edgeObject, N node1, N node2);

	/**
	 * @holger Wuerde ich weglassen
	 * @deprecated
	 */
	@Deprecated
	public void setEdge(E edgeObject, N node1, N node2);

	public void setUndirectedEdge(E edgeObject, long nodeIndex1, long nodeIndex2);

	/**
	 * @holger Wuerde ich weglassen
	 * @deprecated
	 */
	@Deprecated
	public void setUndirectedEdge(E edgeObject, N node1, N node2);

	/**
	 * @holger Wuerde ich weglassen
	 * @deprecated
	 */
	@Deprecated
	public void addUndirectedEdge(N node1, N node2);

	/**
	 * @holger Wuerde ich weglassen
	 * @deprecated
	 */
	@Deprecated
	public void addUndirectedEdge(long nodeIndex1, long nodeIndex2);

	/**
	 * @holger Wuerde ich weglassen
	 * @deprecated
	 */
	@Deprecated
	public void removeDirectedEdge(long nodeIndex1, long nodeIndex2);

	public void removeEdge(long nodeIndex1, long nodeIndex2);

	/**
	 * @holger Wuerde ich weglassen
	 * @deprecated
	 */
	@Deprecated
	public void removeDirectedEdge(N node1, N node2);

	/**
	 * @holger Wuerde ich weglassen
	 * @deprecated
	 */
	@Deprecated
	public void removeEdge(N node1, N node2);

	public void removeUndirectedEdge(long nodeIndex1, long nodeIndex2);

	/**
	 * @holger Wuerde ich weglassen
	 * @deprecated
	 */
	@Deprecated
	public void removeUndirectedEdge(N node1, N node2);

	public N getNode(long index);

	public void addNode(N node);

	public void setNode(N node, long index);

	/**
	 * @holger Wuerde ich weglassen, es gibt ja addNode und setNode
	 * @deprecated
	 */
	@Deprecated
	public void insertNode(N node, long index);

	public void removeNode(N node);

	public void removeNode(long node);

	public boolean isConnected(long nodeIndex1, long nodeIndex2);

	/**
	 * @holger Wuerde ich weglassen
	 * @deprecated
	 */
	@Deprecated
	public boolean isConnected(N node1, N node2);

	public long getIndexOfNode(N node);

	public int getEdgeCount();

	public int getNodeCount();

	public int getChildCount(long nodeIndex);

	/**
	 * @holger Wuerde ich weglassen
	 * @deprecated
	 */
	@Deprecated
	public int getChildCount(N node);

	public int getParentCount(long nodeIndex);

	/**
	 * @holger Wuerde ich weglassen
	 * @deprecated
	 */
	@Deprecated
	public int getParentCount(N node);

	public int getDegree(long nodeIndex);

	/**
	 * @holger Wuerde ich weglassen
	 * @deprecated
	 */
	@Deprecated
	public int getDegree(N node);

	public List<N> getChildren(long nodeIndex);

	public List<Long> getChildIndices(long nodeIndex);

	/**
	 * @holger Wuerde ich weglassen
	 * @deprecated
	 */
	@Deprecated
	public List<Long> getChildIndices(N node);

	/**
	 * @holger Wuerde ich weglassen
	 * @deprecated
	 */
	@Deprecated
	public List<N> getChildren(N node);

	public List<N> getParents(long nodeIndex);

	/**
	 * @holger Wuerde ich weglassen
	 * @deprecated
	 */
	@Deprecated
	public List<N> getParents(N node);

	public List<Long> getParentIndices(long nodeIndex);

	/**
	 * @holger Wuerde ich weglassen
	 * @deprecated
	 */
	@Deprecated
	public List<Long> getParentIndices(N node);

	/**
	 * @holger Wuerde ich weglassen
	 * @deprecated
	 */
	@Deprecated
	public void addChild(N node, N child);

	/**
	 * @holger Wuerde ich weglassen
	 * @deprecated
	 */
	@Deprecated
	public void addChild(long nodeIndex, long childIndex);

	/**
	 * @holger Wuerde ich weglassen
	 * @deprecated
	 */
	@Deprecated
	public void addParent(N node, N parent);

	/**
	 * @holger Wuerde ich weglassen
	 * @deprecated
	 */
	@Deprecated
	public void addParent(long nodeIndex, long parentIndex);

}
