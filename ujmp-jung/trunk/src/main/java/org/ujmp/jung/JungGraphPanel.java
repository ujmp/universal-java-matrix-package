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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.logging.Logger;

import javax.swing.JPopupMenu;

import org.ujmp.gui.interfaces.CanBeRepainted;
import org.ujmp.gui.interfaces.CanRenderGraph;
import org.ujmp.gui.io.ExportJPEG;
import org.ujmp.gui.io.ExportPDF;
import org.ujmp.gui.io.ExportPNG;
import org.ujmp.gui.util.GraphicsExecutor;

import edu.uci.ics.jung.exceptions.FatalException;
import edu.uci.ics.jung.graph.ArchetypeEdge;
import edu.uci.ics.jung.graph.ArchetypeVertex;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.decorators.EdgeShape;
import edu.uci.ics.jung.graph.decorators.EdgeStringer;
import edu.uci.ics.jung.graph.decorators.VertexStringer;
import edu.uci.ics.jung.graph.event.GraphEvent;
import edu.uci.ics.jung.graph.event.GraphEventListener;
import edu.uci.ics.jung.graph.event.GraphEventType;
import edu.uci.ics.jung.graph.impl.DirectedSparseGraph;
import edu.uci.ics.jung.visualization.DefaultVisualizationModel;
import edu.uci.ics.jung.visualization.FRLayout;
import edu.uci.ics.jung.visualization.ISOMLayout;
import edu.uci.ics.jung.visualization.Layout;
import edu.uci.ics.jung.visualization.LayoutMutable;
import edu.uci.ics.jung.visualization.PluggableRenderer;
import edu.uci.ics.jung.visualization.SpringLayout;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.contrib.CircleLayout;
import edu.uci.ics.jung.visualization.contrib.KKLayout;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;

public abstract class JungGraphPanel extends VisualizationViewer implements MouseListener, ComponentListener,
		GraphEventListener, CanBeRepainted, CanRenderGraph {
	private static final long serialVersionUID = 5056858150031378990L;

	protected static final Logger logger = Logger.getLogger(JungGraphPanel.class.getName());

	public static enum GraphLayout {
		FRLayout, KKLayout, ISOMLayout, SpringLayout, CircleLayout
	};

	public static enum Data {
		JDMPObject, Object, Label, Column, RowColumn, Value, Inhibitory, Excitatory, Time, Matrix, Type, Reference
	};

	protected Graph graph = new DirectedSparseGraph();

	private boolean showEdges = true;

	private boolean showEdgeLabels = true;

	private boolean showVertexLabels = true;

	private BufferedImage bufferedImage = null;

	public JungGraphPanel() {
		super(new DefaultVisualizationModel(new FRLayout(new DirectedSparseGraph())), new PluggableRenderer(),
				new Dimension(800, 600));

		addComponentListener(this);

		setDoubleBuffered(true);
		setPreferredSize(new Dimension(800, 600));

		addMouseListener(this);
		setPickSupport(new DefaultShapePickSupport());

		addPostRenderPaintable(new VisualizationViewer.Paintable() {
			int x;

			int y;

			Font font;

			FontMetrics metrics;

			int swidth;

			int sheight;

			public void paint(Graphics g) {
				if (true)
					return;
				// TODO: make look good
				Dimension d = getSize();
				if (font == null) {
					font = new Font(g.getFont().getName(), Font.ITALIC, 10);
					metrics = g.getFontMetrics(font);
					// swidth =
					// metrics.stringWidth(GlobalConfiguration.getInstance().getVersionLabel());
					sheight = metrics.getMaxAscent() + metrics.getMaxDescent();
					x = (d.width - swidth - 10);
					y = (int) (d.height - sheight * 1.5);
				}
				g.setFont(font);
				Color oldColor = g.getColor();
				g.setColor(Color.lightGray);
				// g.drawString(GlobalConfiguration.getInstance().getVersionLabel(),
				// x, y);
				g.setColor(oldColor);
			}

			public boolean useTransform() {
				return false;
			}
		});

		((PluggableRenderer) getRenderer()).setEdgeShapeFunction(new EdgeShape.QuadCurve());

		((PluggableRenderer) getRenderer()).setVertexStringer(new VertexStringer() {
			public String getLabel(ArchetypeVertex v) {
				if (isShowVertexLabels() && v.getUserDatum(Data.Label) != null)
					return "" + v.getUserDatum(Data.Label);
				return null;
			}
		});

		((PluggableRenderer) getRenderer()).setEdgeStringer(new EdgeStringer() {
			public String getLabel(ArchetypeEdge e) {
				if (isShowEdgeLabels() && e.getUserDatum(Data.Label) != null)
					return "" + e.getUserDatum(Data.Label);
				return null;
			}
		});

		ModalGraphMouse graphMouse = new DefaultModalGraphMouse();
		graphMouse.setMode(ModalGraphMouse.Mode.PICKING);
		setGraphMouse(graphMouse);

		// this.stop();

	}

	public void componentHidden(ComponentEvent e) {
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void componentResized(ComponentEvent e) {
		GraphicsExecutor.scheduleUpdate(this);
	}

	public void componentShown(ComponentEvent e) {
		GraphicsExecutor.scheduleUpdate(this);
	}

	public void setGraph(Graph g) {
		graph.removeListener(this, GraphEventType.ALL_SINGLE_EVENTS);
		graph = g;
		graph.addListener(this, GraphEventType.ALL_SINGLE_EVENTS);
		setModel(new DefaultVisualizationModel(new FRLayout(graph)));
		GraphicsExecutor.scheduleUpdate(this);
	}

	// change method to public to allow pdf export
	@Override
	public void renderGraph(Graphics2D g2d) {
		super.renderGraph(g2d);
	}

	public final void repaintUI() {
		if (getModel().getGraphLayout() instanceof LayoutMutable) {
			((LayoutMutable) getModel().getGraphLayout()).update();
		}
		if (!isVisRunnerRunning()) {
			try {
				init();
			} catch (FatalException e) {
				System.out.println("Fatal Error in JungGraphPanel");
			}
		}
		BufferedImage tempBufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics g = tempBufferedImage.getGraphics();
		super.paintComponent(g);
		g.dispose();
		bufferedImage = tempBufferedImage;
	}

	@Override
	public void paintComponent(Graphics g) {
		if (ui != null) {
			Graphics scratchGraphics = (g == null) ? null : g.create();
			try {
				ui.update(scratchGraphics, this);
			} finally {
				scratchGraphics.dispose();
			}
		}

		if (bufferedImage != null) {
			g.drawImage(bufferedImage, 0, 0, getWidth(), getHeight(), null);
		} else {
			g.setColor(Color.GRAY);
			g.drawLine(0, 0, getWidth(), getHeight());
			g.drawLine(getWidth(), 0, 0, getHeight());
			GraphicsExecutor.scheduleUpdate(this);
		}
	}

	public final boolean isShowEdges() {
		return showEdges;
	}

	public final void setShowEdges(boolean showEdges) {
		this.showEdges = showEdges;
	}

	public final boolean isShowEdgeLabels() {
		return showEdgeLabels;
	}

	public final void setShowEdgeLabels(boolean showEdgeLabels) {
		this.showEdgeLabels = showEdgeLabels;
	}

	public final boolean isShowVertexLabels() {
		return showVertexLabels;
	}

	public final void setShowVertexLabels(boolean showVertexLabels) {
		this.showVertexLabels = showVertexLabels;
	}

	public final void exportToPDF(File file) {
		Color oldbg = getBackground();
		setBackground(Color.white);
		ExportPDF.save(file, this);
		this.setBackground(oldbg);
	}

	public final void exportToJPEG(File file) {
		Color oldbg = this.getBackground();
		this.setBackground(Color.white);
		ExportJPEG.save(file, this, 1600);
		this.setBackground(oldbg);
	}

	public final void exportToPNG(File file) {
		Color oldbg = this.getBackground();
		this.setBackground(Color.white);
		ExportPNG.save(file, this, 1600);
		this.setBackground(oldbg);
	}

	public final void mouseClicked(MouseEvent e) {
		switch (e.getButton()) {
		case MouseEvent.BUTTON1:
			break;
		case MouseEvent.BUTTON2:
			break;
		case MouseEvent.BUTTON3:
			JPopupMenu popup = null;
			popup = new JungGraphActions(this);
			popup.show(e.getComponent(), e.getX(), e.getY());
			break;
		}
	}

	public final void mousePressed(MouseEvent e) {
	}

	public final void mouseReleased(MouseEvent e) {
	}

	public final void mouseEntered(MouseEvent e) {
	}

	public final void mouseExited(MouseEvent e) {
	}

	public void switchLayout(GraphLayout type) {
		stop();

		Layout layout = null;

		switch (type) {
		case KKLayout:
			layout = new KKLayout(graph);
			break;
		case FRLayout:
			layout = new FRLayout(graph);
			break;
		case ISOMLayout:
			layout = new ISOMLayout(graph);
			break;
		case SpringLayout:
			layout = new SpringLayout(graph);
			break;
		case CircleLayout:
			layout = new CircleLayout(graph);
			break;
		}

		setModel(new DefaultVisualizationModel(layout));

		restart();
		GraphicsExecutor.scheduleUpdate(this);
	}

	public void edgeAdded(GraphEvent event) {
		GraphicsExecutor.scheduleUpdate(this);
	}

	public void edgeRemoved(GraphEvent event) {
		GraphicsExecutor.scheduleUpdate(this);
	}

	public void vertexAdded(GraphEvent event) {
		GraphicsExecutor.scheduleUpdate(this);
	}

	public void vertexRemoved(GraphEvent event) {
		GraphicsExecutor.scheduleUpdate(this);
	}

}
