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

package org.ujmp.core.export.destinationselector;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import org.ujmp.core.DenseMatrix;
import org.ujmp.core.export.destination.DefaultDenseMatrixClipboardExportDestination;
import org.ujmp.core.export.destination.DefaultDenseMatrixFileExportDestination;
import org.ujmp.core.export.destination.DefaultDenseMatrixStreamExportDestination;
import org.ujmp.core.export.destination.DefaultDenseMatrixStringExportDestination;
import org.ujmp.core.export.destination.DefaultDenseMatrixWriterExportDestination;
import org.ujmp.core.export.destination.MatrixClipboardExportDestination;
import org.ujmp.core.export.destination.MatrixFileExportDestination;
import org.ujmp.core.export.destination.MatrixStreamExportDestination;
import org.ujmp.core.export.destination.MatrixStringExportDestination;
import org.ujmp.core.export.destination.MatrixWriterExportDestination;

public class DefaultDenseMatrixExportDestinationSelector implements MatrixExportDestinationSelector {

	private final DenseMatrix matrix;

	public DefaultDenseMatrixExportDestinationSelector(DenseMatrix matrix) {
		this.matrix = matrix;
	}

	public final DenseMatrix getMatrix() {
		return matrix;
	}

	public final MatrixClipboardExportDestination clipboard() {
		return new DefaultDenseMatrixClipboardExportDestination(getMatrix());
	}

	public final MatrixFileExportDestination file(String filename) throws IOException {
		return file(new File(filename));
	}

	public final MatrixFileExportDestination file(File file) throws IOException {
		return new DefaultDenseMatrixFileExportDestination(getMatrix(), file);
	}

	public final MatrixWriterExportDestination writer(Writer writer) throws IOException {
		return new DefaultDenseMatrixWriterExportDestination(getMatrix(), writer);
	}

	public MatrixStringExportDestination string() {
		return new DefaultDenseMatrixStringExportDestination(getMatrix());
	}

	public MatrixStreamExportDestination stream(OutputStream outputStream) {
		return new DefaultDenseMatrixStreamExportDestination(getMatrix(), outputStream);
	}

}
