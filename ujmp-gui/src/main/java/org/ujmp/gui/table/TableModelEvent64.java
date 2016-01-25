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

package org.ujmp.gui.table;

import javax.swing.event.TableModelEvent;

public class TableModelEvent64 extends TableModelEvent {

	private static final long serialVersionUID = -7233844328674198083L;
	protected long firstRow;
	protected long lastRow;
	protected long column;

	public TableModelEvent64(TableModel64 source) {
		this(source, 0, Integer.MAX_VALUE, ALL_COLUMNS, UPDATE);
	}

	public TableModelEvent64(TableModel64 source, long row) {
		this(source, row, row, ALL_COLUMNS, UPDATE);
	}

	public TableModelEvent64(TableModel64 source, long firstRow, long lastRow) {
		this(source, firstRow, lastRow, ALL_COLUMNS, UPDATE);
	}

	public TableModelEvent64(TableModel64 source, long firstRow, long lastRow, long column) {
		this(source, firstRow, lastRow, column, UPDATE);
	}

	public TableModelEvent64(TableModel64 source, long firstRow, long lastRow, long column, int type) {
		super(source, (int) firstRow, (int) lastRow, (int) column, type);
		this.firstRow = firstRow;
		this.lastRow = lastRow;
		this.column = column;
		this.type = type;
	}

	public long getFirstRow64() {
		return firstRow;
	};

	public long getLastRow64() {
		return lastRow;
	};

	public long getColumn64() {
		return column;
	};
}
