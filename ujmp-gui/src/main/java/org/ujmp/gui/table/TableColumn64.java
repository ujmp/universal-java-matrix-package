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

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.ujmp.core.util.MathUtil;

public class TableColumn64 extends TableColumn {
	private static final long serialVersionUID = -7383466850570837014L;

	private long modelIndex64;

	private final TableColumnModel64 tableColumnModel;

	public TableColumn64(TableColumnModel64 tableColumnModel, long modelIndex) {
		this(tableColumnModel, modelIndex, null, null);
	}

	public TableColumn64(TableColumnModel64 tableColumnModel, long modelIndex, TableCellRenderer cellRenderer,
			TableCellEditor cellEditor) {
		super(MathUtil.longToIntClip(modelIndex), tableColumnModel.getColumnWidth(modelIndex), cellRenderer, cellEditor);
		this.tableColumnModel = tableColumnModel;
		this.modelIndex64 = modelIndex;
		this.maxWidth = width;
		this.minWidth = width;
	}

	public void setModelIndex(long modelIndex) {
		this.modelIndex64 = modelIndex;
	}

	public void setModelIndex(int modelIndex) {
		this.modelIndex64 = modelIndex;
	}

	public int getModelIndex() {
		return MathUtil.longToInt(modelIndex64);
	}

	public void setWidth(int width) {
		super.setWidth(width);
		setPreferredWidth(width);
		tableColumnModel.setColumnWidth(modelIndex64, width);
	}

	public int getWidth() {
		return tableColumnModel.getColumnWidth(modelIndex64);
	}

}
