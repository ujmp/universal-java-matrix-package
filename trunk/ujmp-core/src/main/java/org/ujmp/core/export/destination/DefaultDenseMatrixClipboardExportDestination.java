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

package org.ujmp.core.export.destination;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.io.StringWriter;

import org.ujmp.core.DenseMatrix;
import org.ujmp.core.enums.DBType;
import org.ujmp.core.export.exporter.DefaultDenseMatrixWriterCSVExporter;
import org.ujmp.core.export.exporter.DefaultDenseMatrixWriterMatlabScriptExporter;
import org.ujmp.core.export.exporter.DefaultDenseMatrixWriterSQLExporter;

public class DefaultDenseMatrixClipboardExportDestination extends
		AbstractDenseMatrixClipboardDestination {

	public DefaultDenseMatrixClipboardExportDestination(DenseMatrix matrix) {
		super(matrix);
	}

	public void asXLS() {
		throw new RuntimeException("not implemented");
	}

	public void asPLT(Object... params) {
		throw new RuntimeException("not implemented");
	}

	public void asCSV(String columnSeparator, String lineSeparator) throws IOException {
		StringWriter writer = new StringWriter();
		new DefaultDenseMatrixWriterCSVExporter(getMatrix(), writer).asCSV(columnSeparator,
				lineSeparator);
		writer.close();
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		StringSelection ms = new StringSelection(writer.toString());
		clipboard.setContents(ms, ms);
	}

	public void asCSV(String columnSeparator) throws IOException {
		asCSV(columnSeparator, System.getProperty("line.separator"));
	}

	public void asCSV() throws IOException {
		asCSV("\t");
	}

	public void asSQL(DBType db, String databaseName, String tableName) throws IOException {
		StringWriter writer = new StringWriter();
		new DefaultDenseMatrixWriterSQLExporter(getMatrix(), writer).asSQL(db, databaseName,
				tableName);
		writer.close();
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		StringSelection ms = new StringSelection(writer.toString());
		clipboard.setContents(ms, ms);
	}

	public void asMatlabScript(String variableName) throws IOException {
		StringWriter writer = new StringWriter();
		new DefaultDenseMatrixWriterMatlabScriptExporter(getMatrix(), writer)
				.asMatlabScript(variableName);
		writer.close();
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		StringSelection ms = new StringSelection(writer.toString());
		clipboard.setContents(ms, ms);
	}

}
