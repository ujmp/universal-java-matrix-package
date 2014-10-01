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

import java.io.IOException;
import java.io.StringWriter;

import org.ujmp.core.Matrix;
import org.ujmp.core.enums.DBType;
import org.ujmp.core.export.exporter.DefaultMatrixWriterCSVExporter;
import org.ujmp.core.export.exporter.DefaultMatrixWriterLatexExporter;
import org.ujmp.core.export.exporter.DefaultMatrixWriterMatlabScriptExporter;
import org.ujmp.core.export.exporter.DefaultMatrixWriterRScriptExporter;
import org.ujmp.core.export.exporter.DefaultMatrixWriterSQLExporter;

public class DefaultMatrixStringExportDestination extends AbstractMatrixStringExportDestination {

	public DefaultMatrixStringExportDestination(Matrix matrix) {
		super(matrix);
	}

	public String asDenseCSV(char columnSeparator, char enclosingCharacter) throws IOException {
		StringWriter writer = new StringWriter();
		new DefaultMatrixWriterCSVExporter(getMatrix(), writer).asDenseCSV(columnSeparator,
				enclosingCharacter);
		writer.close();
		return writer.toString();
	}

	public String asDenseCSV(char columnSeparator) throws IOException {
		return asDenseCSV(columnSeparator, '\0');
	}

	public String asDenseCSV() throws IOException {
		return asDenseCSV('\t');
	}

	public String asSQL(DBType db, String databaseName, String tableName) throws IOException {
		StringWriter writer = new StringWriter();
		new DefaultMatrixWriterSQLExporter(getMatrix(), writer).asSQL(db, databaseName, tableName);
		writer.close();
		return writer.toString();
	}

	public String asMatlabScript(String variableName) throws IOException {
		StringWriter writer = new StringWriter();
		new DefaultMatrixWriterMatlabScriptExporter(getMatrix(), writer)
				.asMatlabScript(variableName);
		writer.close();
		return writer.toString();
	}

	public String asRScript(String variableName) throws IOException {
		StringWriter writer = new StringWriter();
		new DefaultMatrixWriterRScriptExporter(getMatrix(), writer).asRScript(variableName);
		writer.close();
		return writer.toString();
	}

	public String asLatex() throws IOException {
		StringWriter writer = new StringWriter();
		new DefaultMatrixWriterLatexExporter(getMatrix(), writer).asLatex();
		writer.close();
		return writer.toString();
	}

}
