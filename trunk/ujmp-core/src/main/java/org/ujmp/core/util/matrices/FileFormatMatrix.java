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

package org.ujmp.core.util.matrices;

import java.util.Arrays;

import org.ujmp.core.filematrix.FileFormat;
import org.ujmp.core.objectmatrix.stub.AbstractDenseObjectMatrix2D;

public class FileFormatMatrix extends AbstractDenseObjectMatrix2D {
	private static final long serialVersionUID = 1386986795129799225L;

	public FileFormatMatrix() {
		super(FileFormat.values().length, 3);
		setLabel("Supported File Formats");
		setColumnLabel(0, "File Format");
		setColumnLabel(1, "Description");
		setColumnLabel(2, "Extensions");
	}

	public Object getObject(long row, long column) {
		return getObject((int) row, (int) column);
	}

	public Object getObject(int row, int column) {
		FileFormat f = FileFormat.values()[row];
		switch (column) {
		case 1:
			return f.getDescription();
		case 2:
			return Arrays.asList(f.getExtensions());
		default:
			return f.name();
		}
	}

	public void setObject(Object value, long row, long column) {
	}

	public void setObject(Object value, int row, int column) {
	}

}