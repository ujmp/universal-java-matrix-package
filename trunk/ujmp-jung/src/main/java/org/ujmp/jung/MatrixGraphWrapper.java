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

import java.util.HashMap;
import java.util.Map;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.gui.MatrixGUIObject;

import edu.uci.ics.jung.graph.Edge;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.impl.DirectedSparseEdge;
import edu.uci.ics.jung.graph.impl.DirectedSparseGraph;
import edu.uci.ics.jung.graph.impl.DirectedSparseVertex;
import edu.uci.ics.jung.utils.UserData;

public class MatrixGraphWrapper extends DirectedSparseGraph implements TableModelListener {

	public static final int MAXEDGES = 256000;

	public static final int MAXVERTICES = 128000;

	public static final double MINEDGEVALUE = 0.5;

	private MatrixGUIObject matrix = null;

	private final Map<Integer, Vertex> vertices = new HashMap<Integer, Vertex>();

	public MatrixGraphWrapper(Matrix m) throws MatrixException {
		this((MatrixGUIObject) m.getGUIObject());
	}

	public MatrixGraphWrapper(MatrixGUIObject m) throws MatrixException {
		super();
		this.matrix = m;

		if (m != null) {

			// limit the number of vertices
			int stepsize = matrix.getColumnCount() / MAXVERTICES + 1;

			for (int i = 0; i < matrix.getColumnCount(); i += stepsize) {
				Vertex v = new DirectedSparseVertex();
				v.setUserDatum(JungGraphPanel.Data.Column, i, UserData.SHARED);
				if (matrix.getColumnName(i) != null) {
					v.setUserDatum(JungGraphPanel.Data.Label, matrix.getColumnName(i), UserData.SHARED);
				} else
					v.setUserDatum(JungGraphPanel.Data.Label, "" + i, UserData.SHARED);

				addVertex(v);
				vertices.put(i, v);

				if (i % 100 == 0) {
					System.out.println(i);
				}
			}

			System.out.println("edges");

			int i = 0;
			for (long[] rc : matrix.coordinates()) {
				if (i++ % 100 == 0) {
					System.out.println(i);
				}
				double value = matrix.getDoubleValueAt(rc);
				if (Math.abs(value) >= MINEDGEVALUE) {
					Vertex v1 = getVertex((int) rc[Coordinates.ROW]);
					Vertex v2 = getVertex((int) rc[Coordinates.COLUMN]);
					if (v1 != null && v2 != null) {
						if (numEdges() < MAXEDGES) {
							Edge e = new DirectedSparseEdge(v1, v2);
							e.addUserDatum(JungGraphPanel.Data.Label, matrix.getDoubleValueAt(rc), UserData.SHARED);
							e.addUserDatum(JungGraphPanel.Data.RowColumn, rc, UserData.SHARED);
							e.addUserDatum(JungGraphPanel.Data.Value, matrix.getDoubleValueAt(rc), UserData.SHARED);
							addEdge(e);
						}
					}
				}
			}

			m.addTableModelListener(this);

		}
	}

	public void tableChanged() throws MatrixException {
		if (true) {
			return;
		}
		for (long[] rc : matrix.coordinates()) {

			Vertex v1 = getVertex((int) rc[Coordinates.ROW]);
			Vertex v2 = getVertex((int) rc[Coordinates.COLUMN]);

			if (v1 != null && v2 != null) {

				Edge e = v1.findEdge(v2);
				double newValue = matrix.getDoubleValueAt(rc);

				if (e == null && Math.abs(newValue) >= MINEDGEVALUE) {
					if (numEdges() < MAXEDGES) {
						e = new DirectedSparseEdge(v1, v2);
						e.addUserDatum(JungGraphPanel.Data.Label, matrix.getDoubleValueAt(rc), UserData.SHARED);
						e.addUserDatum(JungGraphPanel.Data.RowColumn, rc, UserData.SHARED);
						e.addUserDatum(JungGraphPanel.Data.Value, matrix.getDoubleValueAt(rc), UserData.SHARED);
						addEdge(e);
					}
				} else if (e != null && Math.abs(newValue) >= MINEDGEVALUE) {
					double oldValue = (Double) e.getUserDatum(JungGraphPanel.Data.Value);
					if (oldValue != newValue) {
						e.setUserDatum(JungGraphPanel.Data.Value, newValue, UserData.SHARED);
					}
				} else if (e != null && Math.abs(newValue) < MINEDGEVALUE) {
					removeEdge(e);
				}
			}
		}
		mGraphListenerHandler.handleAdd((Edge) null);
	}

	private Vertex getVertex(int index) {
		return vertices.get(index);
		// if (v == null) {
		// for (Object o : getVertices()) {
		// v = (Vertex) o;
		// int row = (Integer) v.getUserDatum(Data.Column);
		// if (row == index) {
		// vertices.put(index, v);
		// return v;
		// }
		// }
		// }
		// return null;
	}

	public void tableChanged(TableModelEvent e) {
		try {
			tableChanged();
		} catch (MatrixException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	
	protected void finalize() throws Throwable {
		super.finalize();
		matrix.removeTableModelListener(this);
	}

}
