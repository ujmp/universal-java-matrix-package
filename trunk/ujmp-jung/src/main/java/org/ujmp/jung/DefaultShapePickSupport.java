/*
 * Copyright (C) 2008-2009 Holger Arndt, A. Naegele and M. Bundschus
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

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

import edu.uci.ics.jung.graph.Edge;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.utils.Pair;
import edu.uci.ics.jung.visualization.Layout;
import edu.uci.ics.jung.visualization.ShapePickSupport;

public class DefaultShapePickSupport extends ShapePickSupport {

	
	public Edge getEdge(double x, double y) {
		Layout layout = hasGraphLayout.getGraphLayout();

		// as a Line has no area, we can't always use edgeshape.contains(point)
		// so we
		// make a small rectangular pickArea around the point and check if the
		// edgeshape.intersects(pickArea)
		Rectangle2D pickArea = new Rectangle2D.Float((float) x - pickSize / 2,
				(float) y - pickSize / 2, pickSize, pickSize);
		Edge closest = null;
		double minDistance = Double.MAX_VALUE;
		while (true) {
			try {
				for (Iterator<?> iter = layout.getGraph().getEdges().iterator(); iter
						.hasNext();) {

					if (hasShapeFunctions != null) {
						Edge e = (Edge) iter.next();
						Pair pair = e.getEndpoints();
						Vertex v1 = (Vertex) pair.getFirst();
						Vertex v2 = (Vertex) pair.getSecond();
						boolean isLoop = v1.equals(v2);
						Point2D p1 = layoutTransformer.layoutTransform(layout
								.getLocation(v1));
						Point2D p2 = layoutTransformer.layoutTransform(layout
								.getLocation(v2));
						if (p1 == null || p2 == null)
							continue;
						float x1 = (float) p1.getX();
						float y1 = (float) p1.getY();
						float x2 = (float) p2.getX();
						float y2 = (float) p2.getY();

						// translate the edge to the starting vertex
						AffineTransform xform = AffineTransform
								.getTranslateInstance(x1, y1);

						Shape edgeShape = hasShapeFunctions
								.getEdgeShapeFunction().getShape(e);
						if (isLoop) {
							// make the loops proportional to the size of the
							// vertex
							Shape s2 = hasShapeFunctions
									.getVertexShapeFunction().getShape(v2);
							Rectangle2D s2Bounds = s2.getBounds2D();
							xform.scale(s2Bounds.getWidth(), s2Bounds
									.getHeight());
							// move the loop so that the nadir is centered in
							// the vertex
							xform.translate(0, -edgeShape.getBounds2D()
									.getHeight() / 2);
						} else {
							float dx = x2 - x1;
							float dy = y2 - y1;
							// rotate the edge to the angle between the vertices
							double theta = Math.atan2(dy, dx);
							xform.rotate(theta);
							// stretch the edge to span the distance between the
							// vertices
							float dist = (float) Math.sqrt(dx * dx + dy * dy);
							xform.scale(dist, 1.0f);
						}

						// transform the edge to its location and dimensions
						edgeShape = xform.createTransformedShape(edgeShape);

						// because of the transform, the edgeShape is now a
						// GeneralPath
						// see if this edge is the closest of any that intersect
						if (edgeShape.intersects(pickArea)) {
							float cx = 0;
							float cy = 0;
							float[] f = new float[6];
							PathIterator pi = edgeShape.getPathIterator(null);
							if (pi.isDone() == false) {
								pi.next();
								pi.currentSegment(f);
								cx = f[0];
								cy = f[1];
								if (pi.isDone() == false) {
									pi.currentSegment(f);
									cx = f[0];
									cy = f[1];
								}
							}
							float dx = (float) (cx - x);
							float dy = (float) (cy - y);
							float dist = dx * dx + dy * dy;
							if (dist < minDistance) {
								minDistance = dist;
								closest = e;
							}
						}
					}
				}
				break;
			} catch (ConcurrentModificationException cme) {
			}
		}
		return closest;
	}

}
