/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Paint;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.gui.MatrixGUIObject;
import org.ujmp.gui.colormap.ColorMap;

import edu.uci.ics.jung.graph.ArchetypeVertex;
import edu.uci.ics.jung.graph.Edge;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.decorators.EdgeShape;
import edu.uci.ics.jung.graph.decorators.ToolTipFunction;
import edu.uci.ics.jung.graph.decorators.VertexPaintFunction;
import edu.uci.ics.jung.graph.decorators.VertexStringer;
import edu.uci.ics.jung.graph.impl.DirectedSparseGraph;
import edu.uci.ics.jung.visualization.AbstractLayout;
import edu.uci.ics.jung.visualization.DefaultSettableVertexLocationFunction;
import edu.uci.ics.jung.visualization.PluggableRenderer;
import edu.uci.ics.jung.visualization.ShapePickSupport;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.contrib.KKLayout;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;

public class ProjectionPanel extends JPanel implements MouseListener {
	private static final long serialVersionUID = -6575744654019554947L;

	// private HasVariables iVariables = null;

	private final int xAxis = 0;

	private final int yAxis = 1;

	private Graph g = null;

	private VisualizationViewer vv = null;

	AbstractLayout layout = null;

	DefaultSettableVertexLocationFunction vertexLocations = new DefaultSettableVertexLocationFunction();

	public ProjectionPanel(Object iVariables) throws MatrixException {
		// this.iVariables = iVariables;

		this.addMouseListener(this);

		g = new DirectedSparseGraph();

		layout = new KKLayout(g);

		// Layout layout = new ISOMLayout(g);
		PluggableRenderer pr = new PluggableRenderer();

		vv = new VisualizationViewer(layout, pr);

		vv.setPickSupport(new ShapePickSupport());
		pr.setEdgeShapeFunction(new EdgeShape.QuadCurve());
		pr.setVertexStringer(new VertexStringer() {
			public String getLabel(ArchetypeVertex v) {
				return "" + v.getUserDatum(JungGraphPanel.Data.Label);
			}
		});

		pr.setVertexPaintFunction(new VertexPaintFunction() {
			public Paint getFillPaint(Vertex v) {
				return fromDouble((Double) v.getUserDatum(JungGraphPanel.Data.Time));
			}

			public Paint getDrawPaint(Vertex v) {
				return Color.BLACK;
			}
		});

		// pr.setVertexShapeFunction(new VertexShapeFunction() {
		// public Shape getShape(Vertex v) {
		// if (v.getUserDatum("label").equals("test1"))
		// return new Rectangle2D.Float(-25, -15, 50, 30);
		// else
		// return new Rectangle2D.Float(-15, -15, 30, 30);
		// }
		// });

		vv.setToolTipFunction(new ToolTipFunction() {

			public String getToolTipText(Vertex v) {
				return ((MatrixGUIObject) v.getUserDatum(JungGraphPanel.Data.Matrix)).getToolTipText();
			}

			public String getToolTipText(Edge e) {
				return "";
			}

			public String getToolTipText(MouseEvent event) {
				return "";
			}
		});

		// pr.setEdgePaintFunction(new PickableEdgePaintFunction(pr,
		// Color.black,
		// Color.cyan));

		this.setLayout(new BorderLayout());

		final ModalGraphMouse graphMouse = new DefaultModalGraphMouse();
		// graphMouse.setMode(ModalGraphMouse.Mode.PICKING);
		vv.setGraphMouse(graphMouse);

		updateGraph();

		this.add(vv, BorderLayout.CENTER);
	}

	public void updateGraph() throws MatrixException {
		vv.stop();

		g.removeAllEdges();
		g.removeAllVertices();

		// Variable var = null;

		// if (var != null) {
		// for (int i = 0; i < var.getMatrixList().size(); i++) {
		// Matrix m = var.getMatrixList().get(i);
		// Vertex v = new DirectedSparseVertex();
		// v.setUserDatum(Data.Label, m.getLabel(), UserData.SHARED);
		// v.setUserDatum(Data.Matrix, m, UserData.SHARED);
		// g.addVertex(v);
		// double x = m.getAsDouble(this.xAxis % m.getRowCount(), this.xAxis /
		// m.getRowCount());
		// double y = m.getAsDouble(this.yAxis % m.getRowCount(), this.yAxis /
		// m.getRowCount());
		// v.setUserDatum(layout.getBaseKey(), new Coordinates(x * 50, y * 50),
		// UserData.SHARED);
		// // layout.lockVertex(v);
		// }
		// }
		vv.restart();
	}

	public void mouseClicked(MouseEvent e) {
		System.out.println("click");
		try {
			updateGraph();
		} catch (MatrixException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	private static final Color fromDouble(double v) {
		// inf = 255 255 0 yellow
		// 1 = 0 255 0 green
		// 0 = 0 0 0 black
		// -1 = 255 0 0 red
		// -inf = 255 0 255 magenta
		// nan = 0 255 255 cyan
		if (v == Double.MIN_VALUE || Double.isNaN(v))
			return (Color.MAGENTA);
		else if (Double.isInfinite(v))
			return (Color.CYAN);
		else if (v > 1.0)
			return (ColorMap.colorGreenToYellow[(int) (255.0 * Math.tanh((v - 1.0) / 10.0))]);
		else if (v > 0.0)
			return (ColorMap.colorBlackToGreen[(int) (255.0 * v)]);
		else if (v > -1.0)
			return (ColorMap.colorRedToBlack[(int) (255.0 * (v + 1.0))]);
		else
			return (ColorMap.colorRedToMagenta[(int) (255.0 * Math.tanh((-v - 1.0) / 10.0))]);
	}

}
