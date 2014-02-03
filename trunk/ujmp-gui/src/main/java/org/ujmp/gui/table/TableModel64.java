/*
 * Copyright (C) 2008-2014 by Holger Arndt
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

import javax.swing.table.TableModel;

public interface TableModel64 extends TableModel {

	public long getRowCount64();

	public long getColumnCount64();

	public String getColumnName(long columnIndex);

	public Class<?> getColumnClass(long columnIndex);

	public boolean isCellEditable(long rowIndex, long columnIndex);

	public Object getValueAt(long rowIndex, long columnIndex);

	public void setValueAt(Object aValue, long rowIndex, long columnIndex);

	public void addTableModelListener(TableModelListener64 l);

	public void removeTableModelListener(TableModelListener64 l);
}
