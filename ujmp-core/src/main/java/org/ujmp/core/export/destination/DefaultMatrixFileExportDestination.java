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

package org.ujmp.core.export.destination;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.ujmp.core.Matrix;
import org.ujmp.core.enums.DBType;
import org.ujmp.core.export.exporter.DefaultMatrixWriterCSVExporter;
import org.ujmp.core.export.exporter.DefaultMatrixWriterLatexExporter;
import org.ujmp.core.export.exporter.DefaultMatrixWriterMatlabScriptExporter;
import org.ujmp.core.export.exporter.DefaultMatrixWriterRScriptExporter;
import org.ujmp.core.export.exporter.DefaultMatrixWriterSQLExporter;

public class DefaultMatrixFileExportDestination extends AbstractMatrixFileExportDestination {

	public DefaultMatrixFileExportDestination(Matrix matrix, File file) {
		super(matrix, file);
	}

	public void asDenseCSV(char columnSeparator, char enclosingCharacter) throws IOException {
		FileWriter fileWriter = new FileWriter(getFile());
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		new DefaultMatrixWriterCSVExporter(getMatrix(), bufferedWriter).asDenseCSV(columnSeparator,
				enclosingCharacter);
		bufferedWriter.close();
		fileWriter.close();
	}

	public void asDenseCSV(char columnSeparator) throws IOException {
		asDenseCSV(columnSeparator, '\0');
	}

	public void asDenseCSV() throws IOException {
		asDenseCSV('\t');
	}

	public void asSQL(DBType db, String databaseName, String tableName) throws IOException {
		FileWriter fileWriter = new FileWriter(getFile());
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		new DefaultMatrixWriterSQLExporter(getMatrix(), bufferedWriter).asSQL(db, databaseName,
				tableName);
		bufferedWriter.close();
		fileWriter.close();
	}

	public void asMatlabScript(String variableName) throws IOException {
		FileWriter fileWriter = new FileWriter(getFile());
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		new DefaultMatrixWriterMatlabScriptExporter(getMatrix(), bufferedWriter)
				.asMatlabScript(variableName);
		bufferedWriter.close();
		fileWriter.close();
	}

	public void asRScript(String variableName) throws IOException {
		FileWriter fileWriter = new FileWriter(getFile());
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		new DefaultMatrixWriterRScriptExporter(getMatrix(), bufferedWriter).asRScript(variableName);
		bufferedWriter.close();
		fileWriter.close();
	}

	public void asLatex() throws IOException {
		FileWriter fileWriter = new FileWriter(getFile());
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		new DefaultMatrixWriterLatexExporter(getMatrix(), bufferedWriter).asLatex();
		bufferedWriter.close();
		fileWriter.close();
	}

	public void asXLS() throws IOException {
		// TODO Auto-generated method stub

	}

	public void asPLT(Object... parameters) throws IOException {
		// TODO Auto-generated method stub

	}

}
