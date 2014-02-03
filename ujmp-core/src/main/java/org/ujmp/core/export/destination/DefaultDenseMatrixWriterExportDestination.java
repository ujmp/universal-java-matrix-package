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

import org.ujmp.core.DenseMatrix;
import org.ujmp.core.enums.DBType;
import org.ujmp.core.export.exporter.DefaultDenseMatrixWriterCSVExporter;
import org.ujmp.core.export.exporter.DefaultDenseMatrixWriterLatexExporter;
import org.ujmp.core.export.exporter.DefaultDenseMatrixWriterMatlabScriptExporter;
import org.ujmp.core.export.exporter.DefaultDenseMatrixWriterRScriptExporter;
import org.ujmp.core.export.exporter.DefaultDenseMatrixWriterSQLExporter;

public class DefaultDenseMatrixWriterExportDestination extends
		AbstractDenseMatrixWriterExportDestination {

	public DefaultDenseMatrixWriterExportDestination(DenseMatrix matrix, Writer writer) {
		super(matrix, writer);
	}

	public void asCSV(String columnSeparator, String lineSeparator) throws IOException {
		new DefaultDenseMatrixWriterCSVExporter(getMatrix(), getWriter()).asCSV(columnSeparator,
				lineSeparator);
	}

	public void asCSV(String columnSeparator) throws IOException {
		new DefaultDenseMatrixWriterCSVExporter(getMatrix(), getWriter()).asCSV(columnSeparator);
	}

	public void asCSV() throws IOException {
		new DefaultDenseMatrixWriterCSVExporter(getMatrix(), getWriter()).asCSV();
	}

	public void asSQL(DBType db, String databaseName, String tableName) throws IOException {
		new DefaultDenseMatrixWriterSQLExporter(getMatrix(), getWriter()).asSQL(db, databaseName,
				tableName);
	}

	public void asMatlabScript(String variableName) throws IOException {
		new DefaultDenseMatrixWriterMatlabScriptExporter(getMatrix(), getWriter())
				.asMatlabScript(variableName);
	}

	public void asRScript(String variableName) throws IOException {
		new DefaultDenseMatrixWriterRScriptExporter(getMatrix(), getWriter())
				.asRScript(variableName);
	}

	public void asLatex() throws IOException {
		new DefaultDenseMatrixWriterLatexExporter(getMatrix(), getWriter()).asLatex();
	}

}
