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

import javax.swing.JComponent;
import javax.swing.JOptionPane;

import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.gui.MatrixGUIObject;
import org.ujmp.gui.util.GUIUtil;

public abstract class AbstractMatrixAction extends AbstractObjectAction {
	private static final long serialVersionUID = 7005218564058853146L;

	public static final int ROW = Matrix.ROW;

	public static final int COLUMN = Matrix.COLUMN;

	public static final int ALL = Matrix.ALL;

	private GUIObject variable = null;

	public AbstractMatrixAction(JComponent c, MatrixGUIObject matrix, GUIObject v) {
		super(c, matrix);
		this.variable = v;
	}

	public Ret getRet() {
		return (Ret) GUIUtil.getObject("Select return method", Ret.ORIG, Ret.NEW, Ret.LINK);
	}

	public Ret getOrigOrNew() {
		return (Ret) GUIUtil.getObject("Select return method", Ret.ORIG, Ret.NEW);
	}

	public Ret getNewOrLink() {
		return (Ret) GUIUtil.getObject("Select return method", Ret.NEW, Ret.LINK);
	}

	public long[] getSize() {
		return GUIUtil.getSize("Enter the size of the matrix (e.g. '5,4' -> 5 rows, 4 columns)");
	}

	public int getDimension() {
		int dimension = -1;

		if (dimension == -1) {
			if (getMatrixObject().getColumnCount() == 1) {
				dimension = ROW;
			} else if (getMatrixObject().getRowCount() == 1) {
				dimension = COLUMN;
			}
		}
		if (dimension == -1) {
			int i = JOptionPane.showOptionDialog(getComponent(), "Dimension", "Select Dimension",
					JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[] { "Row", "Column", "All" },
					"Row");
			if (i == 2) {
				dimension = ALL;
			} else {
				dimension = i;
			}
		}
		return dimension;
	}

	public boolean getIgnoreMissing() {
		if (!getMatrixObject().getMatrix().containsMissingValues()) {
			return true;
		} else {
			return GUIUtil.getBoolean("Ignore missing values");
		}
	}

	public Matrix getMatrix() {
		return getMatrixObject().getMatrix();
	}

	public MatrixGUIObject getMatrixObject() {
		MatrixGUIObject m = (MatrixGUIObject) getGUIObject();
		if (m != null) {
			int startRow = m.getRowSelectionModel().getMinSelectionIndex();
			int endRow = m.getRowSelectionModel().getMaxSelectionIndex();
			int startColumn = m.getColumnSelectionModel().getMinSelectionIndex();
			int endColumn = m.getColumnSelectionModel().getMaxSelectionIndex();
			if (startRow != -1 && startColumn != -1) {
				m = (MatrixGUIObject) m.getMatrix().subMatrix(Ret.LINK, startRow, startColumn, endRow, endColumn)
						.getGUIObject();
			}
			return m;
		} else {
			// int min = variable.getRowSelectionModel().getMinSelectionIndex();
			// int max = variable.getRowSelectionModel().getMaxSelectionIndex();
			// Matrix all = new MatrixListToMatrixWrapper(variable);
			//
			// if (min >= 0 && max >= 0) {
			// Matrix selection = all.subMatrix(Ret.LINK, min, 0, max,
			// all.getColumnCount() - 1);
			// return (MatrixGUIObject) selection.getGUIObject();
			// } else {
			// return (MatrixGUIObject) all.getGUIObject();
			// }
		}
		return null;
	}

	public void setMatrix(MatrixGUIObject m) {
		setGUIObject(m);
	}

	public GUIObject getVariable() {
		return variable;
	}

}
