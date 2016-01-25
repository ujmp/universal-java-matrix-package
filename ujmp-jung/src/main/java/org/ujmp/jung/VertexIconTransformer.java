/*
 * Copyright (C) 2008-2016 by Holger Arndt
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
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.Icon;

import org.apache.commons.collections15.Transformer;
import org.ujmp.core.util.ColorUtil;
import org.ujmp.gui.util.UIDefaults;

import edu.uci.ics.jung.visualization.picking.PickedState;

public class VertexIconTransformer<N> implements Transformer<N, Icon> {

	private final PickedState<N> pickedVertexState;

	public VertexIconTransformer(PickedState<N> pickedVertexState) {
		this.pickedVertexState = pickedVertexState;
	}

	public Icon transform(final N v) {
		return new Icon() {

			public int getIconHeight() {
				return 10;
			}

			public int getIconWidth() {
				return 10;
			}

			public void paintIcon(Component c, Graphics g, int x, int y) {
				Graphics2D g2d = (Graphics2D) g;
				if (pickedVertexState.isPicked(v)) {
					g2d.setColor(UIDefaults.SELECTEDCOLOR);
				} else {
					g2d.setColor(ColorUtil.fromObject(v));
				}
				g2d.fillOval(x, y, 10, 10);
				g2d.setColor(new Color(150, 150, 150, 150));
				g2d.drawOval(x, y, 10, 10);
			}
		};
	}
}
