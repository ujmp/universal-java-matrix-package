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

package org.ujmp.gui.actions;

import javax.swing.Action;
import javax.swing.JComponent;

import org.ujmp.core.Matrix;
import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.core.util.matrices.MandelbrotMatrix;
import org.ujmp.gui.MatrixGUIObject;

public class MandelbrotMatrixAction extends AbstractMatrixAction {
	private static final long serialVersionUID = 8708344146012762782L;

	public MandelbrotMatrixAction(JComponent c, MatrixGUIObject m, GUIObject v) {
		super(c, m, v);
		putValue(Action.NAME, "Mandelbrot Matrix");
		putValue(Action.SHORT_DESCRIPTION, "creates a matrix from the mandelbrot set");
	}

	public Object call() {
		Matrix m = new MandelbrotMatrix();
		return m;
	}

}
