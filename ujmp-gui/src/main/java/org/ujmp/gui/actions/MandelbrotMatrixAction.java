/*
 * Copyright (C) 2008-2011 by Holger Arndt
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
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.gui.MatrixGUIObject;

public class MandelbrotMatrixAction extends AbstractMatrixAction {
	private static final long serialVersionUID = 8708344146012762782L;

	public MandelbrotMatrixAction(JComponent c, MatrixGUIObject m, GUIObject v) {
		super(c, m, v);
		putValue(Action.NAME, "Mandelbrot Matrix");
		putValue(Action.SHORT_DESCRIPTION,
				"creates a matrix from the mandelbrot set");
	}

	public Object call() throws MatrixException {
		double xoffset = -.5;
		double yoffset = 0;
		double size = 2;
		int cells = 500;
		int iterations = 20;

		Matrix m = Matrix.factory.zeros(cells, cells);
		for (int column = 0; column < cells; column++) {
			for (int row = 0; row < cells; row++) {
				double x0 = xoffset - size / 2 + size * column / cells;
				double y0 = yoffset - size / 2 + size * row / cells;
				Complex z0 = new Complex(x0, y0);
				double gray = iterations - calc(z0, iterations);
				m.setAsDouble((gray - (iterations / 2)) / (iterations / 2),
						cells - 1 - row, column);
			}
		}
		m.showGUI();
		return m;
	}

	public int calc(Complex c, int iterations) {
		Complex z = c;
		for (int i = 0; i < iterations; i++) {
			if (z.abs() > 2.0) {
				return i;
			}
			z = z.times(z).plus(c);
		}
		return iterations;
	}

}

class Complex {
	double real = 0;

	double img = 0;

	public Complex(double real, double img) {
		this.real = real;
		this.img = img;
	}

	public double abs() {
		return Math.sqrt(real * real + img * img);
	}

	public Complex times(Complex c2) {
		double real2 = this.real * c2.real - this.img * c2.img;
		double img2 = this.img * c2.real + this.real * c2.img;
		return new Complex(real2, img2);
	}

	public Complex plus(Complex c2) {
		double real = this.real + c2.real;
		double img = this.img + c2.img;
		return new Complex(real, img);
	}
}