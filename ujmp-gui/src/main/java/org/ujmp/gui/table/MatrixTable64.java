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

package org.ujmp.gui.table;

import org.ujmp.gui.MatrixGUIObject;
import org.ujmp.gui.editor.MatrixTableCellEditor;
import org.ujmp.gui.renderer.MatrixValueTableCellRenderer;

public class MatrixTable64 extends JTable64 {
	private static final long serialVersionUID = -4334674835582761405L;

	public static final int SMALLCOLUMNWIDTH = 80;
	public static final int LARGECOLUMNWIDTH = 200;
	public static final int ROWHEADERCOLUMNWIDTH = 80;
	public static final int ROWHEIGHT = 25;

	public MatrixTable64(MatrixGUIObject matrix) {
		super(matrix, new DefaultTableColumnModel64(matrix), matrix.getRowSelectionModel());
		getColumnModel().setSelectionModel(matrix.getColumnSelectionModel());
		getTableHeader().setReorderingAllowed(false);
		setColumnSelectionAllowed(true);
		setDefaultRenderer(Object.class, new MatrixValueTableCellRenderer());
		setDefaultEditor(Object.class, new MatrixTableCellEditor());
	}

}
