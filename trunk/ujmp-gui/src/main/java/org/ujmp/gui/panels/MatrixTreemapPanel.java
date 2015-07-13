/*
 * Copyright (C) 2008-2015 by Holger Arndt
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

package org.ujmp.gui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ujmp.core.treematrix.TreeMatrix;
import org.ujmp.core.util.ColorUtil;
import org.ujmp.core.util.Sortable;

public class MatrixTreemapPanel extends BufferedPanel {
	private static final long serialVersionUID = -6345970654224113023L;

	public MatrixTreemapPanel(TreeMatrix<?> matrix) {
		super(new UnbufferedMatrixTreemapPanel(matrix));
	}
}

class UnbufferedMatrixTreemapPanel extends AbstractPanel implements MouseListener {
	private static final long serialVersionUID = -535908084636801903L;

	private final TreeMatrix<?> treeMatrix;

	public UnbufferedMatrixTreemapPanel(TreeMatrix<?> matrix) {
		super(matrix.getGUIObject());
		this.treeMatrix = matrix;
		setPreferredSize(new Dimension(600, 400));
		this.addMouseListener(this);
	}

	public void paintComponent(Graphics g) {
		Object root = treeMatrix.getRoot();
		List<?> children = treeMatrix.getChildren(root);
		List<Sortable<Long, Object>> childCounts = new ArrayList<Sortable<Long, Object>>();

		for (Object child : children) {
			long count = treeMatrix.getChildCountRecursive(child);
			Sortable<Long, Object> s = new Sortable<Long, Object>(1 + count, child);
			childCounts.add(s);
		}
		Collections.sort(childCounts);

		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
		paintTreeRect(g, childCounts, 0, 0, getWidth(), getHeight());
	}

	private void paintTreeRect(Graphics g, List<Sortable<Long, Object>> childCounts, int x, int y, int width, int height) {
		if (width <= 2 || height <= 2) {
			return;
		}

		if (childCounts.isEmpty()) {
			g.setColor(Color.black);
			g.drawRect(x + 1, y + 1, width - 1, height - 1);
		} else if (childCounts.size() == 1) {
			Sortable<Long, Object> s = childCounts.remove(childCounts.size() - 1);

			if (s.getComparable() > 1) {
				List<Sortable<Long, Object>> subChildCounts = new ArrayList<Sortable<Long, Object>>();
				List<?> children = treeMatrix.getChildren(s.getObject());
				for (Object child : children) {
					long count = treeMatrix.getChildCountRecursive(child);
					s = new Sortable<Long, Object>(1 + count, child);
					subChildCounts.add(s);
				}
				Collections.sort(subChildCounts);
				paintTreeRect(g, subChildCounts, x, y, width, height);
			} else {
				g.setColor(ColorUtil.fromObject(s.getObject()));
				g.fillRect(x + 1, y + 1, width - 1, height - 1);
			}

		} else {
			long sum = 0;
			for (Sortable<Long, Object> s : childCounts) {
				sum += s.getComparable();
			}
			Sortable<Long, Object> s = childCounts.remove(childCounts.size() - 1);

			double factor = (double) s.getComparable() / (double) sum;
			if (width > height) {
				int w = (int) ((double) width * factor);

				// biggest element
				if (s.getComparable() > 1) {
					List<Sortable<Long, Object>> subChildCounts = new ArrayList<Sortable<Long, Object>>();
					List<?> children = treeMatrix.getChildren(s.getObject());
					for (Object child : children) {
						long count = treeMatrix.getChildCountRecursive(child);
						s = new Sortable<Long, Object>(1 + count, child);
						subChildCounts.add(s);
					}
					Collections.sort(subChildCounts);
					paintTreeRect(g, subChildCounts, x, y, w, height);
				} else {
					g.setColor(ColorUtil.fromObject(s.getObject()));
					g.fillRect(x + 1, y + 1, w - 1, height - 1);
				}

				// remaining elements
				paintTreeRect(g, childCounts, x + w, y, width - w, height);

			} else {
				int h = (int) ((double) height * factor);

				// biggest element
				if (s.getComparable() > 1) {
					List<Sortable<Long, Object>> subChildCounts = new ArrayList<Sortable<Long, Object>>();
					List<?> children = treeMatrix.getChildren(s.getObject());
					for (Object child : children) {
						long count = treeMatrix.getChildCountRecursive(child);
						s = new Sortable<Long, Object>(1 + count, child);
						subChildCounts.add(s);
					}
					Collections.sort(subChildCounts);
					paintTreeRect(g, subChildCounts, x, y, width, h);
				} else {
					g.setColor(ColorUtil.fromObject(s.getObject()));
					g.fillRect(x + 1, y + 1, width - 1, h - 1);
				}

				// remaining elements
				paintTreeRect(g, childCounts, x, y + h, width, height - h);
			}

		}

	}

	public void mouseClicked(MouseEvent e) {
		System.out.println(e);

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
}
