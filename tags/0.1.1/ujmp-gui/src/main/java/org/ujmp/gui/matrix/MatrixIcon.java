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

package org.ujmp.gui.matrix;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

import org.ujmp.core.Matrix;

public class MatrixIcon extends MatrixRenderer implements Icon {
	private static final long serialVersionUID = -3351801048513775919L;

	private Matrix matrix = null;

	public MatrixIcon(Matrix matrix) {
		this.matrix = matrix;
	}

	public int getIconHeight() {
		return 16;
	}

	public int getIconWidth() {
		return 16;
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
		g.translate(x, y);
		setSize(getIconWidth(), getIconHeight());
		paintComponent(g);
		g.translate(-x, -y);
	}

	public Matrix getMatrix() {
		return matrix;
	}

	public void setMatrix(Matrix matrix) {
		this.matrix = matrix;
	}

}
