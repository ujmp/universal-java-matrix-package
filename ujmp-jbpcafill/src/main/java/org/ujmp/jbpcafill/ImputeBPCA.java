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

package org.ujmp.jbpcafill;

import java.io.File;
import java.lang.reflect.Method;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.calculation.AbstractDoubleCalculation;

public class ImputeBPCA extends AbstractDoubleCalculation {
	private static final long serialVersionUID = -8635313044017639669L;

	private Matrix xImputed = null;

	public ImputeBPCA(Matrix matrix) {
		super(matrix);
	}

	public double getDouble(long... coordinates) {
		if (xImputed == null) {
			createMatrix();
		}
		return xImputed.getAsDouble(coordinates);
	}

	private void createMatrix() {
		try {
			Matrix m = getSource();
			m = m.replaceRegex(Ret.NEW, "NaN", "999");
			File file1 = File.createTempFile("matrix", ".csv");
			File file2 = File.createTempFile("matrix", ".csv");
			m.exportTo().file(file1).asDenseCSV();
			Class<?> c = Class.forName("JBPCAfill");
			Method me = c.getMethod("main", String[].class);
			me.invoke(null, new Object[] { new String[] { file1.toString(), file2.toString() } });
			m = Matrix.Factory.importFrom().file(file2).asDenseCSV();
			m = m.replaceRegex(Ret.NEW, ",", "");
			file1.delete();
			file2.delete();
			xImputed = m;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
