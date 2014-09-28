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

import org.ujmp.core.DenseMatrix;
import org.ujmp.core.enums.DBType;
import org.ujmp.core.export.exporter.DefaultDenseMatrixWriterCSVExporter;
import org.ujmp.core.export.exporter.DefaultDenseMatrixWriterLatexExporter;
import org.ujmp.core.export.exporter.DefaultDenseMatrixWriterMatlabScriptExporter;
import org.ujmp.core.export.exporter.DefaultDenseMatrixWriterRScriptExporter;
import org.ujmp.core.export.exporter.DefaultDenseMatrixWriterSQLExporter;

public class DefaultDenseMatrixStringExportDestination extends
		AbstractDenseMatrixStringExportDestination {

	public DefaultDenseMatrixStringExportDestination(DenseMatrix matrix) {
		super(matrix);
	}

	public String asCSV(char columnSeparator, char enclosingCharacter) throws IOException {
		StringWriter writer = new StringWriter();
		new DefaultDenseMatrixWriterCSVExporter(getMatrix(), writer).asCSV(columnSeparator,
				enclosingCharacter);
		writer.close();
		return writer.toString();
	}

	public String asCSV(char columnSeparator) throws IOException {
		return asCSV(columnSeparator, '\0');
	}

	public String asCSV() throws IOException {
		return asCSV('\t');
	}

	public String asSQL(DBType db, String databaseName, String tableName) throws IOException {
		StringWriter writer = new StringWriter();
		new DefaultDenseMatrixWriterSQLExporter(getMatrix(), writer).asSQL(db, databaseName,
				tableName);
		writer.close();
		return writer.toString();
	}

	public String asMatlabScript(String variableName) throws IOException {
		StringWriter writer = new StringWriter();
		new DefaultDenseMatrixWriterMatlabScriptExporter(getMatrix(), writer)
				.asMatlabScript(variableName);
		writer.close();
		return writer.toString();
	}

	public String asRScript(String variableName) throws IOException {
		StringWriter writer = new StringWriter();
		new DefaultDenseMatrixWriterRScriptExporter(getMatrix(), writer).asRScript(variableName);
		writer.close();
		return writer.toString();
	}

	public String asLatex() throws IOException {
		StringWriter writer = new StringWriter();
		new DefaultDenseMatrixWriterLatexExporter(getMatrix(), writer).asLatex();
		writer.close();
		return writer.toString();
	}

}
