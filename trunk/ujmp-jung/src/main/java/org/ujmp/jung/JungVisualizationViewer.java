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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPopupMenu;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.apache.commons.collections15.Transformer;
import org.ujmp.core.graphmatrix.GraphMatrix;
import org.ujmp.gui.AbstractMatrixGUIObject;
import org.ujmp.gui.panels.AbstractPanel;
import org.ujmp.gui.util.UIDefaults;

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
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.VisualizationModel;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.GraphMouseListener;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.layout.LayoutTransition;
import edu.uci.ics.jung.visualization.renderers.BasicVertexLabelRenderer.InsidePositioner;
import edu.uci.ics.jung.visualization.renderers.DefaultEdgeLabelRenderer;
import edu.uci.ics.jung.visualization.renderers.DefaultVertexLabelRenderer;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import edu.uci.ics.jung.visualization.util.Animator;

public class JungVisualizationViewer<N, E> extends AbstractPanel implements MouseListener, TableModelListener,
		ComponentListener, GraphMouseListener<N> {
	private static final long serialVersionUID = 7328433763448698033L;

	public static enum GraphLayout {
		CircleLayout, FRLayout, FRLayout2, ISOMLayout, KKLayout, SpringLayout, SpringLayout2
	};

	private final GraphMatrixWrapper<N, E> graph;
	private boolean showNodeLabels;
	private boolean showEdgeLabels;
	private final Transformer<N, String> emptyNodeLabelTransformer;
	private final Transformer<EdgeWrapper<E>, String> emptyEdgeLabelTransformer;
	private final VisualizationViewer<N, EdgeWrapper<E>> vv;
	private final Layout<N, EdgeWrapper<E>> layout;
	private final GraphMatrix<N, E> graphMatrix;
	private final AbstractMatrixGUIObject matrixGUIObject;

	public JungVisualizationViewer(GraphMatrixWrapper<N, E> graph, boolean showNodeLabels, boolean showEdgeLabels) {
		super(graph.getGraphMatrix().getGUIObject());
		this.graph = graph;
		this.graphMatrix = graph.getGraphMatrix();
		this.matrixGUIObject = (AbstractMatrixGUIObject) graphMatrix.getGUIObject();
		this.showNodeLabels = showNodeLabels;
		this.showEdgeLabels = showEdgeLabels;

		if (graph.getVertexCount() < 1000) {
			layout = new FRLayout<N, EdgeWrapper<E>>(graph);
		} else {
			layout = new ISOMLayout<N, EdgeWrapper<E>>(graph);
		}

		VisualizationModel<N, EdgeWrapper<E>> visualizationModel = new DefaultVisualizationModel<N, EdgeWrapper<E>>(
				layout);
		vv = new VisualizationViewer<N, EdgeWrapper<E>>(visualizationModel);
		vv.setForeground(new Color(0, 0, 0, 150));
		vv.setBackground(Color.WHITE);

		DefaultModalGraphMouse<N, E> graphMouse = new DefaultModalGraphMouse<N, E>();
		vv.setGraphMouse(graphMouse);
		graphMouse.setMode(Mode.PICKING);

		RenderContext<N, EdgeWrapper<E>> rc = vv.getRenderContext();
		emptyNodeLabelTransformer = rc.getVertexLabelTransformer();
		emptyEdgeLabelTransformer = rc.getEdgeLabelTransformer();
		rc.setVertexIconTransformer(new VertexIconTransformer<N>(vv.getPickedVertexState()));
		rc.setVertexFillPaintTransformer(new ColorTransformer<N>(vv.getPickedVertexState()));
		rc.setVertexLabelRenderer(new DefaultVertexLabelRenderer(UIDefaults.SELECTEDCOLOR));
		rc.setEdgeDrawPaintTransformer(new ColorTransformer<EdgeWrapper<E>>(vv.getPickedEdgeState()));
		rc.setEdgeLabelRenderer(new DefaultEdgeLabelRenderer(UIDefaults.SELECTEDCOLOR));
		rc.setArrowFillPaintTransformer(new ColorTransformer<EdgeWrapper<E>>(vv.getPickedEdgeState()));
		rc.setArrowDrawPaintTransformer(new ColorTransformer<EdgeWrapper<E>>(vv.getPickedEdgeState()));

		vv.getRenderer().getVertexLabelRenderer().setPositioner(new InsidePositioner());
		vv.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.AUTO);

		if (showNodeLabels) {
			rc.setVertexLabelTransformer(new ToStringLabeller<N>());
		}

		if (showEdgeLabels) {
			rc.setEdgeLabelTransformer(new ToStringLabeller<EdgeWrapper<E>>());
		}

		vv.setVertexToolTipTransformer(new ToStringLabeller<N>());

		setLayout(new BorderLayout());
		add(vv, BorderLayout.CENTER);

		vv.addMouseListener(this);
		addComponentListener(this);
		vv.addGraphMouseListener(this);

		if (graph instanceof GraphMatrixWrapper) {
			((AbstractMatrixGUIObject) ((GraphMatrixWrapper<N, E>) graph).getGraphMatrix().getGUIObject())
					.addTableModelListener(this);
		}
	}

	public JungVisualizationViewer(GraphMatrixWrapper<N, E> graph) {
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
			layout.setInitializer(vv.getGraphLayout());
			layout.setSize(getSize());
			LayoutTransition<N, EdgeWrapper<E>> lt = new LayoutTransition<N, EdgeWrapper<E>>(vv, vv.getGraphLayout(),
					layout);
			Animator animator = new Animator(lt);
			animator.start();
			vv.getRenderContext().getMultiLayerTransformer().setToIdentity();
			repaint(500);
		} else {
			vv.setModel(new DefaultVisualizationModel<N, EdgeWrapper<E>>(layout));
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

	public void refreshUI() {
		vv.getGraphLayout().setSize(getSize());
		vv.setModel(new DefaultVisualizationModel<N, EdgeWrapper<E>>(vv.getGraphLayout()));
		repaint(500);
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
			vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<N>());
		} else {
			vv.getRenderContext().setVertexLabelTransformer(emptyNodeLabelTransformer);
		}
		repaint(500);
	}

	public boolean isShowEdgeLabels() {
		return showEdgeLabels;
	}

	public void setShowEdgeLabels(boolean showEdgeLabels) {
		this.showEdgeLabels = showEdgeLabels;
		if (showEdgeLabels) {
			vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller<EdgeWrapper<E>>());
		} else {
			vv.getRenderContext().setEdgeLabelTransformer(emptyEdgeLabelTransformer);
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
		vv.getGraphLayout().setSize(getSize());
		vv.setModel(new DefaultVisualizationModel<N, EdgeWrapper<E>>(vv.getGraphLayout()));
		repaint(500);
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void componentShown(ComponentEvent e) {
	}

	public void componentHidden(ComponentEvent e) {
	}

	public void graphClicked(N v, MouseEvent me) {
		long index = graphMatrix.getIndexOfNode(v);
		matrixGUIObject.getRowSelectionModel().setSelectionInterval(index, index);
		matrixGUIObject.getColumnSelectionModel().setSelectionInterval(index, index);
	}

	public void graphPressed(N v, MouseEvent me) {
		long index = graphMatrix.getIndexOfNode(v);
		matrixGUIObject.getRowSelectionModel().setSelectionInterval(index, index);
		matrixGUIObject.getColumnSelectionModel().setSelectionInterval(index, index);
	}

	public void graphReleased(N v, MouseEvent me) {
	}

}
