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

package org.ujmp.core.export.exporter;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Writer;

import org.ujmp.core.Matrix;
import org.ujmp.core.export.format.MatrixMatlabScriptExportFormat;
import org.ujmp.core.export.format.MatrixSerializedExportFormat;
import org.ujmp.core.util.Base64;

public class DefaultMatrixStreamSerializedExporter extends AbstractMatrixStreamExporter implements MatrixSerializedExportFormat {

    public DefaultMatrixStreamSerializedExporter(Matrix matrix, OutputStream outputStream) {
        super(matrix, outputStream);
    }

    public void asSerialized() throws IOException {
        final OutputStream os = getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(getMatrix());
    }

}
