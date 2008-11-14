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

package org.ujmp.gui.matrix.actions;

import javax.swing.Action;
import javax.swing.JComponent;

import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.HasMatrixList;
import org.ujmp.gui.MatrixGUIObject;

public class MeanAction extends MatrixAction {
	private static final long serialVersionUID = 9202273744246930760L;

	public MeanAction(JComponent c, MatrixGUIObject m, HasMatrixList v) {
		super(c, m, v);
		putValue(Action.NAME, "Mean");
		putValue(Action.SHORT_DESCRIPTION, "Calculates the mean of the entries in this matrix");
	}

	@Override
	public Object call() throws MatrixException {
		Matrix result = getMatrixObject().getMatrix().mean(Ret.NEW, getDimension(), true);
		result.showGUI();
		return result;
	}

}
