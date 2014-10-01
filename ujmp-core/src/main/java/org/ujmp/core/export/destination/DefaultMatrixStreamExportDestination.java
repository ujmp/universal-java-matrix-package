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

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.ujmp.core.Matrix;
import org.ujmp.core.enums.DBType;
import org.ujmp.core.export.exporter.DefaultMatrixWriterCSVExporter;
import org.ujmp.core.export.exporter.DefaultMatrixWriterLatexExporter;
import org.ujmp.core.export.exporter.DefaultMatrixWriterMatlabScriptExporter;
import org.ujmp.core.export.exporter.DefaultMatrixWriterRScriptExporter;
import org.ujmp.core.export.exporter.DefaultMatrixWriterSQLExporter;

public class DefaultMatrixStreamExportDestination extends AbstractMatrixStreamExportDestination {

	public DefaultMatrixStreamExportDestination(Matrix matrix, OutputStream outputStream) {
		super(matrix, outputStream);
	}

	public void asDenseCSV(char columnSeparator, char enclosingCharacter) throws IOException {
		final OutputStreamWriter outputStreamWriter = new OutputStreamWriter(getOutputStream());
		final BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
		new DefaultMatrixWriterCSVExporter(getMatrix(), bufferedWriter).asDenseCSV(columnSeparator,
				enclosingCharacter);
		bufferedWriter.close();
		outputStreamWriter.close();
	}

	public void asDenseCSV(char columnSeparator) throws IOException {
		asDenseCSV(columnSeparator, '\0');
	}

	public void asDenseCSV() throws IOException {
		asDenseCSV('\t');
	}

	public void asSQL(DBType db, String databaseName, String tableName) throws IOException {
		final OutputStreamWriter outputStreamWriter = new OutputStreamWriter(getOutputStream());
		final BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
		new DefaultMatrixWriterSQLExporter(getMatrix(), bufferedWriter).asSQL(db, databaseName,
				tableName);
		bufferedWriter.close();
		outputStreamWriter.close();
	}

	public void asMatlabScript(String variableName) throws IOException {
		final OutputStreamWriter outputStreamWriter = new OutputStreamWriter(getOutputStream());
		final BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
		new DefaultMatrixWriterMatlabScriptExporter(getMatrix(), bufferedWriter)
				.asMatlabScript(variableName);
		bufferedWriter.close();
		outputStreamWriter.close();
	}

	public void asRScript(String variableName) throws IOException {
		final OutputStreamWriter outputStreamWriter = new OutputStreamWriter(getOutputStream());
		final BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
		new DefaultMatrixWriterRScriptExporter(getMatrix(), bufferedWriter).asRScript(variableName);
		bufferedWriter.close();
		outputStreamWriter.close();
	}

	public void asLatex() throws IOException {
		final OutputStreamWriter outputStreamWriter = new OutputStreamWriter(getOutputStream());
		final BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
		new DefaultMatrixWriterLatexExporter(getMatrix(), bufferedWriter).asLatex();
		bufferedWriter.close();
		outputStreamWriter.close();
	}

}
