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

import org.ujmp.core.SparseMatrix;
import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.gui.MatrixGUIObject;

public class SparseFromNonZeros extends AbstractMatrixAction {
	private static final long serialVersionUID = 1244539115578610795L;

	public SparseFromNonZeros(JComponent c, MatrixGUIObject matrix, GUIObject v) {
		super(c, matrix, v);
		putValue(Action.NAME, "Sparse from Non-Zeros...");
		putValue(Action.SHORT_DESCRIPTION, "Creates a sparse matrix from non-zero entries");
	}

	public Object call() {
		try {
			SparseMatrix m = SparseMatrix.Factory.sparse(getMatrix());
			return m;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
