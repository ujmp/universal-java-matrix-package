/*
 * Copyright (C) 2008-2009 by Holger Arndt
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

package org.ujmp.gui.editor;

import java.awt.Component;
import java.util.EventObject;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;

public class MatrixTableCellEditor extends DefaultCellEditor {
	private static final long serialVersionUID = 3405924999431274803L;

	public MatrixTableCellEditor() {
		super(new JTextField());
		delegate = new EditorDelegate() {
			private static final long serialVersionUID = 3595116301664542217L;

			
			public void setValue(Object value) {
				String text = "";
				if (value != null) {
					text = value.toString();
				}
				((JTextField) editorComponent).setText(text);
			}

			
			public Object getCellEditorValue() {
				String text = ((JTextField) editorComponent).getText();
				return text;
			}

			
			public boolean shouldSelectCell(EventObject anEvent) {
				((JTextField) editorComponent).selectAll();
				return super.shouldSelectCell(anEvent);
			}
		};
	}

	
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		delegate.setValue(value);
		((JTextField) editorComponent).selectAll();
		return editorComponent;
	}

}
