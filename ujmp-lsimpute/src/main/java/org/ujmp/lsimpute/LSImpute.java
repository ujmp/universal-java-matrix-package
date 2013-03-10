/*
 * Copyright (C) 2008-2013 by Holger Arndt
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

package org.ujmp.lsimpute;

import java.io.File;
import java.lang.reflect.Method;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.doublematrix.calculation.AbstractDoubleCalculation;
import org.ujmp.core.doublematrix.calculation.general.missingvalues.Impute.ImputationMethod;
import org.ujmp.core.enums.FileFormat;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.io.IntelligentFileWriter;

public class LSImpute extends AbstractDoubleCalculation {
	private static final long serialVersionUID = -5527190803466818167L;

	private int method = 0;

	private Matrix xImputed = null;

	public LSImpute(Matrix matrix, ImputationMethod impMethod) {
		super(matrix);
		switch (impMethod) {
		case RowMean:
			method = 0;
			break;
		case EMimputeGene:
			method = 1;
			break;
		case EMimputeArray:
			method = 2;
			break;
		case LSimputeGene:
			method = 3;
			break;
		case LSimputeArray:
			method = 4;
			break;
		case LSimputeCombined:
			method = 5;
			break;
		case LSimputeAdaptive:
			method = 6;
			break;
		default:
			throw new MatrixException("Imputation method is not supported: "
					+ impMethod);
		}

	}

	
	public double getDouble(long... coordinates) throws MatrixException {
		if (xImputed == null) {
			createMatrix();
		}
		return xImputed.getAsDouble(coordinates);
	}

	private void createMatrix() {
		try {
			Matrix m = getSource();
			m = m.replaceRegex(Ret.NEW, "NaN", "NULL");
			File file1 = File.createTempFile("matrix", ".csv");
			File file2 = File.createTempFile("matrix", ".csv");
			IntelligentFileWriter fw = new IntelligentFileWriter(file1);
			fw.write("---\t");
			for (int c = 0; c < m.getColumnCount(); c++) {
				fw.write("Col" + c + "\t");
			}
			fw.write("\n");
			for (int r = 0; r < m.getRowCount(); r++) {
				fw.write("Row" + r + "\t");
				for (int c = 0; c < m.getColumnCount(); c++) {
					fw.write("" + m.getAsObject(r, c));
					fw.write("\t");
				}
				fw.write("\n");
			}
			fw.close();
			Class<?> c = Class.forName("Impute");
			Method me = c.getMethod("main", String[].class);
			me.invoke(null, new Object[] { new String[] { file1.toString(),
					file2.toString(), "" + method } });
			m = MatrixFactory.importFromFile(FileFormat.CSV, file2, "\t");
			m = m.deleteRows(Ret.NEW, 0);
			m = m.deleteColumns(Ret.NEW, 0);
			m = m.replaceRegex(Ret.NEW, ",", "");
			file1.delete();
			file2.delete();
			xImputed = m;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
