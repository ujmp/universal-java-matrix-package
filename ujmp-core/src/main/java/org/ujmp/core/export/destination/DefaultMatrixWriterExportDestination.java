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
import java.io.Writer;

import org.ujmp.core.Matrix;
import org.ujmp.core.enums.DBType;
import org.ujmp.core.export.exporter.DefaultMatrixWriterCSVExporter;
import org.ujmp.core.export.exporter.DefaultMatrixWriterLatexExporter;
import org.ujmp.core.export.exporter.DefaultMatrixWriterMatlabScriptExporter;
import org.ujmp.core.export.exporter.DefaultMatrixWriterRScriptExporter;
import org.ujmp.core.export.exporter.DefaultMatrixWriterSQLExporter;

public class DefaultMatrixWriterExportDestination extends AbstractMatrixWriterExportDestination {

	public DefaultMatrixWriterExportDestination(Matrix matrix, Writer writer) {
		super(matrix, writer);
	}

	public void asDenseCSV(char columnSeparator, char enclosingCharacter) throws IOException {
		new DefaultMatrixWriterCSVExporter(getMatrix(), getWriter()).asDenseCSV(columnSeparator,
				enclosingCharacter);
	}

	public void asDenseCSV(char columnSeparator) throws IOException {
		new DefaultMatrixWriterCSVExporter(getMatrix(), getWriter()).asDenseCSV(columnSeparator);
	}

	public void asDenseCSV() throws IOException {
		new DefaultMatrixWriterCSVExporter(getMatrix(), getWriter()).asDenseCSV();
	}

	public void asSQL(DBType db, String databaseName, String tableName) throws IOException {
		new DefaultMatrixWriterSQLExporter(getMatrix(), getWriter()).asSQL(db, databaseName,
				tableName);
	}

	public void asMatlabScript(String variableName) throws IOException {
		new DefaultMatrixWriterMatlabScriptExporter(getMatrix(), getWriter())
				.asMatlabScript(variableName);
	}

	public void asRScript(String variableName) throws IOException {
		new DefaultMatrixWriterRScriptExporter(getMatrix(), getWriter()).asRScript(variableName);
	}

	public void asLatex() throws IOException {
		new DefaultMatrixWriterLatexExporter(getMatrix(), getWriter()).asLatex();
	}

}
