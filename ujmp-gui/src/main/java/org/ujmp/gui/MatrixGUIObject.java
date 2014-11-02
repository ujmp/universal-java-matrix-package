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

package org.ujmp.gui;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

import javax.swing.event.EventListenerList;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.gui.table.FastListSelectionModel64;
import org.ujmp.gui.table.TableModel64;
import org.ujmp.gui.util.DataItem;

public interface MatrixGUIObject extends GUIObject, TableModel64 {

	public Color getColorAt(long rowIndex, long columnIndex);

	public EventListenerList getListenerList();

	public void fireValueChanged(final long row, final long column, final Object value);

	public Matrix getMatrix();

	public FastListSelectionModel64 getRowSelectionModel();

	public FastListSelectionModel64 getColumnSelectionModel();

	public void setMouseOverCoordinates(long... coordinates);

	public long[] getMouseOverCoordinates();

	public boolean isIconUpToDate();

	public void setIconUpToDate(boolean b);

	public void setIcon(BufferedImage image);

	public boolean isColumnCountUpToDate();

	public void setColumnCount(long columnCount);

	public void setColumnCountUpToDate(boolean b);

	public boolean isRowCountUpToDate();

	public void setRowCount(long rowCount);

	public void setRowCountUpToDate(boolean b);

	public List<Coordinates> getTodo();

	public Map<Coordinates, DataItem> getDataCache();

}
