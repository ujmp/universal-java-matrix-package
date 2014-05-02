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

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;

public class DefaultMatrixGUIObject extends AbstractMatrixGUIObject {
	private static final long serialVersionUID = -5777110889052748093L;

	public DefaultMatrixGUIObject(Matrix m) {
		super(m);
	}

	public final void clear() {
		matrix.clear();
		fireValueChanged();
	}

	public final String getLabel() {
		return matrix.getLabel();
	}

	public final void setLabel(final Object label) {
		matrix.setLabel(label);
	}

	public final Object getLabelObject() {
		return matrix.getLabelObject();
	}

	public final long getColumnCount64() {
		return matrix.getColumnCount();
	}

	public final String getColumnName(final long columnIndex) {
		return matrix.getColumnLabel(columnIndex);
	}

	public final String getColumnName(final int columnIndex) {
		return getColumnName((long) columnIndex);
	}

	public final long getRowCount64() {
		return matrix.getRowCount();
	}

	public final Object getValueAt(final int rowIndex, final int columnIndex) {
		return getValueAt((long) rowIndex, (long) columnIndex);
	}

	public final Object getValueAt(final long rowIndex, final long columnIndex) {
		return matrix.getAsObject(rowIndex, columnIndex);
	}

	public final void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
		setValueAt(aValue, (long) rowIndex, (long) columnIndex);
	}

	public final void setValueAt(final Object aValue, final long rowIndex, final long columnIndex) {
		matrix.setAsObject(aValue, rowIndex, columnIndex);
		fireValueChanged(rowIndex, columnIndex, aValue);
	}

	public final String getDescription() {
		return matrix.getLabel();
	}

	public final void setDescription(final String description) {
		matrix.setLabel(description);
	}

	public final String toString() {
		if (matrix.getLabel() != null) {
			return Coordinates.toString("[", "x", "]", matrix.getSize()) + matrix.getClass().getSimpleName() + " ["
					+ matrix.getLabel() + "]";
		} else {
			return Coordinates.toString("[", "x", "]", matrix.getSize()) + matrix.getClass().getSimpleName();
		}
	}

}
