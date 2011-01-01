/*
 * Copyright (C) 2008-2011 by Holger Arndt
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

package org.ujmp.gui.clipboard;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.io.Serializable;

import org.ujmp.core.Matrix;
import org.ujmp.core.enums.FileFormat;
import org.ujmp.core.exceptions.MatrixException;

public class MatrixSelection implements Transferable, ClipboardOwner, Serializable {
	private static final long serialVersionUID = -8462961141636462510L;

	public static final int STRING = 0;

	public static final int IMAGE = 1;

	public static final int MATRIX = 2;

	// private static final DataFlavor[] flavors = { DataFlavor.stringFlavor,
	// new DataFlavor("image/jpeg", "JPG Image"),
	// MatrixFlavor.matrixFlavor };

	private static final DataFlavor[] flavors = { DataFlavor.stringFlavor };

	private String stringData = null;

	public MatrixSelection(Matrix matrix) throws MatrixException, IOException {
		stringData = matrix.exportToString(FileFormat.CSV);
	}

	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
		if (flavor.equals(flavors[STRING])) {
			return stringData;
		} else {
			throw new UnsupportedFlavorException(flavor);
		}
	}

	public DataFlavor[] getTransferDataFlavors() {
		return flavors.clone();
	}

	public boolean isDataFlavorSupported(DataFlavor flavor) {
		for (int i = 0; i < flavors.length; i++) {
			if (flavor.equals(flavors[i])) {
				return true;
			}
		}
		return false;
	}

	public void lostOwnership(Clipboard clipboard, Transferable contents) {
	}

}
