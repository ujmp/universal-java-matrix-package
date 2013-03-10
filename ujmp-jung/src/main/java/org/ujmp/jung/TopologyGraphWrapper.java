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

package org.ujmp.jung;

import java.awt.Event;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.impl.DirectedSparseGraph;

public class TopologyGraphWrapper extends DirectedSparseGraph implements ListDataListener {

	public TopologyGraphWrapper(Object iTopology) {

		Vertex v = null;

		// if (iTopology instanceof Algorithm) {
		// Algorithm a = (Algorithm) iTopology;
		// v = new DirectedSparseVertex();
		// v.setUserDatum(Data.Label, a.getLabel(), UserData.SHARED);
		// v.setUserDatum(Data.JDMPObject, a, UserData.SHARED);
		// v.setUserDatum(Data.Type, "Algorithm", UserData.SHARED);
		// addVertex(v);
		// }
		//
		// for (Algorithm a : iTopology.getAlgorithms()) {
		// v = new DirectedSparseVertex();
		// v.setUserDatum(Data.Label, a.getLabel(), UserData.SHARED);
		// v.setUserDatum(Data.JDMPObject, a, UserData.SHARED);
		// v.setUserDatum(Data.Type, "Algorithm", UserData.SHARED);
		// addVertex(v);
		// }

		// for (Variable va : iTopology.getVariableList()) {
		// v = new DirectedSparseVertex();
		// v.setUserDatum(Data.Label, va.getLabel(), UserData.SHARED);
		// v.setUserDatum(Data.JDMPObject, va, UserData.SHARED);
		// v.setUserDatum(Data.Type, "Variable", UserData.SHARED);
		// addVertex(v);
		// }

		// for (Object o1 : getVertices()) {
		// for (Object o2 : getVertices()) {
		// Vertex v1 = (Vertex) o1;
		// Vertex v2 = (Vertex) o2;
		// CoreObject u1 = (CoreObject) v1.getUserDatum(Data.JDMPObject);
		// CoreObject u2 = (CoreObject) v2.getUserDatum(Data.JDMPObject);
		// if (u1 instanceof Algorithm && u2 instanceof Variable) {
		// Algorithm a = (Algorithm) u1;
		// Variable va = (Variable) u2;
		// int index = a.getVariables().indexOf(va);
		// if (index >= 0) {
		// Edge e = null;
		// switch (a.getEdgeDirectionForVariable(index)) {
		// case Algorithm.INCOMING:
		// e = new DirectedSparseEdge(v2, v1);
		// break;
		// case Algorithm.OUTGOING:
		// e = new DirectedSparseEdge(v1, v2);
		// break;
		// case Algorithm.BIDIRECTIONAL:
		// e = new DirectedSparseEdge(v1, v2);
		// Edge e2 = new DirectedSparseEdge(v2, v1);
		// addEdge(e2);
		// break;
		// }
		// if (e != null) {
		// e.setUserDatum(Data.Label, a.getEdgeLabelForVariable(index),
		// UserData.SHARED);
		// addEdge(e);
		// }
		// }
		// }
		// if (u1 instanceof Algorithm && u2 instanceof Algorithm) {
		// Algorithm a = (Algorithm) u1;
		// Algorithm a2 = (Algorithm) u2;
		// int index = a.getIndexOfAlgorithm(a2);
		// if (index >= 0) {
		// Edge e = new DirectedSparseEdge(v1, v2);
		// e.setUserDatum(Data.Label, a.getEdgeLabelForAlgorithm(index),
		// UserData.SHARED);
		// addEdge(e);
		// }
		// }
		// }
		// }

		// iTopology.getVariableList().addListDataListener(this);
		// iTopology.addAlgorithmListListener(this);
	}

	public void variableAdded(Event e) {
		// Variable var = (Variable) e.getData();
		// Vertex v = new DirectedSparseVertex();
		// v.setUserDatum(Data.Label, var.getLabel(), UserData.SHARED);
		// v.setUserDatum(Data.JDMPObject, var, UserData.SHARED);
		// v.setUserDatum(Data.Type, "Variable", UserData.SHARED);
		// addVertex(v);
	}

	// public void algorithmAdded(AlgorithmListEvent e) {
	// Algorithm a = (Algorithm) e.getData();
	// Vertex v = new DirectedSparseVertex();
	// v.setUserDatum(Data.Label, a.getLabel(), UserData.SHARED);
	// v.setUserDatum(Data.JDMPObject, a, UserData.SHARED);
	// v.setUserDatum(Data.Type, "Algorithm", UserData.SHARED);
	// addVertex(v);
	// }
	//
	// public void algorithmRemoved(AlgorithmListEvent e) {
	//
	// }
	//
	// public void algorithmUpdated(AlgorithmListEvent e) {
	// mGraphListenerHandler.handleAdd((Edge) null);
	// }

	public void contentsChanged(ListDataEvent e) {
		// TODO Auto-generated method stub

	}

	public void intervalAdded(ListDataEvent e) {
		// TODO Auto-generated method stub

	}

	public void intervalRemoved(ListDataEvent e) {
		// TODO Auto-generated method stub

	}

}
