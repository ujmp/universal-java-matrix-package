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

package org.ujmp.jung;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPopupMenu;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.apache.commons.collections15.Transformer;
import org.ujmp.core.graphmatrix.GraphMatrix;
import org.ujmp.gui.MatrixGUIObject;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout2;
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout2;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.DefaultVisualizationModel;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.layout.LayoutTransition;
import edu.uci.ics.jung.visualization.renderers.BasicVertexLabelRenderer.InsidePositioner;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import edu.uci.ics.jung.visualization.util.Animator;

public class JungVisualizationViewer<N, E> extends VisualizationViewer<N, EdgeWrapper<E>> implements MouseListener,
		TableModelListener, ComponentListener {
	private static final long serialVersionUID = 7328433763448698033L;

	public static enum GraphLayout {
		CircleLayout, FRLayout, FRLayout2, ISOMLayout, KKLayout, SpringLayout, SpringLayout2
	};

	private final Graph<N, EdgeWrapper<E>> graph;
	private boolean showNodeLabels;
	private boolean showEdgeLabels;
	private final Transformer<N, String> emptyNodeLabelTransformer;
	private final Transformer<EdgeWrapper<E>, String> emptyEdgeLabelTransformer;

	public JungVisualizationViewer(Graph<N, EdgeWrapper<E>> graph, boolean showNodeLabels, boolean showEdgeLabels) {
		super(graph.getVertexCount() < 1000 ? new FRLayout<N, EdgeWrapper<E>>(graph)
				: new ISOMLayout<N, EdgeWrapper<E>>(graph), new Dimension(800, 600));

		this.addComponentListener(this);

		this.graph = graph;
		this.showNodeLabels = showNodeLabels;
		this.showEdgeLabels = showEdgeLabels;

		if (graph instanceof GraphMatrixWrapper) {
			((MatrixGUIObject) ((GraphMatrixWrapper<N, E>) graph).getGraphMatrix().getGUIObject())
					.addTableModelListener(this);
		}

		emptyNodeLabelTransformer = getRenderContext().getVertexLabelTransformer();
		emptyEdgeLabelTransformer = getRenderContext().getEdgeLabelTransformer();

		final DefaultModalGraphMouse<N, E> graphMouse = new DefaultModalGraphMouse<N, E>();
		setGraphMouse(graphMouse);
		graphMouse.setMode(Mode.PICKING);

		getRenderContext().setEdgeDrawPaintTransformer(new ColorTransformer<EdgeWrapper<E>>());
		getRenderContext().setArrowFillPaintTransformer(new ColorTransformer<EdgeWrapper<E>>());
		getRenderContext().setArrowDrawPaintTransformer(new ColorTransformer<EdgeWrapper<E>>());

		getRenderContext().setVertexFillPaintTransformer(new ColorTransformer<N>(getPickedVertexState()));

		getRenderer().getVertexLabelRenderer().setPositioner(new InsidePositioner());
		getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.AUTO);

		if (showNodeLabels) {
			getRenderContext().setVertexLabelTransformer(new ToStringLabeller<N>());
		}

		if (showEdgeLabels) {
			getRenderContext().setEdgeLabelTransformer(new ToStringLabeller<EdgeWrapper<E>>());
		}

		setVertexToolTipTransformer(new ToStringLabeller<N>());

		addMouseListener(this);
	}

	public JungVisualizationViewer(Graph<N, EdgeWrapper<E>> graph) {
		this(graph, graph.getVertexCount() < 100, graph.getVertexCount() < 100);
	}

	public JungVisualizationViewer(GraphMatrix<N, E> graphMatrix) {
		this(new GraphMatrixWrapper<N, E>(graphMatrix));
	}

	public JungVisualizationViewer(GraphMatrix<N, E> graphMatrix, boolean showNodeLabels, boolean showEdgeLabels) {
		this(new GraphMatrixWrapper<N, E>(graphMatrix), showNodeLabels, showEdgeLabels);
	}

	public void switchLayout(GraphLayout type) {
		Layout<N, EdgeWrapper<E>> layout = null;

		switch (type) {
		case KKLayout:
			layout = new KKLayout<N, EdgeWrapper<E>>(graph);
			break;
		case FRLayout:
			layout = new FRLayout<N, EdgeWrapper<E>>(graph);
			break;
		case ISOMLayout:
			layout = new ISOMLayout<N, EdgeWrapper<E>>(graph);
			break;
		case SpringLayout:
			layout = new SpringLayout<N, EdgeWrapper<E>>(graph);
			break;
		case CircleLayout:
			layout = new CircleLayout<N, EdgeWrapper<E>>(graph);
			break;
		case FRLayout2:
			layout = new FRLayout2<N, EdgeWrapper<E>>(graph);
			break;
		case SpringLayout2:
			layout = new SpringLayout2<N, EdgeWrapper<E>>(graph);
			break;
		default:
			break;
		}

		if (graph.getVertexCount() < 100) {
			layout.setInitializer(getGraphLayout());
			layout.setSize(getSize());
			LayoutTransition<N, EdgeWrapper<E>> lt = new LayoutTransition<N, EdgeWrapper<E>>(this, getGraphLayout(),
					layout);
			Animator animator = new Animator(lt);
			animator.start();
			getRenderContext().getMultiLayerTransformer().setToIdentity();
			repaint(500);
		} else {
			setModel(new DefaultVisualizationModel<N, EdgeWrapper<E>>(layout));
			repaint(500);
		}
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

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public boolean isShowNodeLabels() {
		return showNodeLabels;
	}

	public void setShowNodeLabels(boolean showNodeLabels) {
		this.showNodeLabels = showNodeLabels;
		if (showNodeLabels) {
			getRenderContext().setVertexLabelTransformer(new ToStringLabeller<N>());
		} else {
			getRenderContext().setVertexLabelTransformer(emptyNodeLabelTransformer);
		}
		repaint(500);
	}

	public boolean isShowEdgeLabels() {
		return showEdgeLabels;
	}

	public void setShowEdgeLabels(boolean showEdgeLabels) {
		this.showEdgeLabels = showEdgeLabels;
		if (showEdgeLabels) {
			getRenderContext().setEdgeLabelTransformer(new ToStringLabeller<EdgeWrapper<E>>());
		} else {
			getRenderContext().setEdgeLabelTransformer(emptyEdgeLabelTransformer);
		}
		repaint(500);
	}

	public Graph<N, EdgeWrapper<E>> getGraph() {
		return graph;
	}

	public void tableChanged(TableModelEvent e) {
		repaint(500);
	}

	public void componentResized(ComponentEvent e) {
		getGraphLayout().setSize(getSize());
		setModel(new DefaultVisualizationModel<N, EdgeWrapper<E>>(getGraphLayout()));
		repaint(500);
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void componentShown(ComponentEvent e) {
	}

	public void componentHidden(ComponentEvent e) {
	}

}
