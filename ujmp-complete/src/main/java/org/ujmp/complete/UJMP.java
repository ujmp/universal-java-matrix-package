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

package org.ujmp.complete;

import org.ujmp.complete.benchmark.CompleteMatrixBenchmark;
import org.ujmp.core.Matrix;
import org.ujmp.core.benchmark.BenchmarkConfig;
import org.ujmp.core.mapmatrix.DefaultMapMatrix;
import org.ujmp.core.mapmatrix.MapMatrix;
import org.ujmp.core.util.CommandLineUtil;
import org.ujmp.core.util.MathUtil;

public class UJMP extends org.ujmp.gui.UJMP {
	private static final long serialVersionUID = 6107915206776591113L;

	public static void main(String[] args) throws Exception {
		MapMatrix<String, Object> generalOptions = new DefaultMapMatrix<String, Object>();
		generalOptions.put("benchmark", false);
		generalOptions.put("help", false);
		generalOptions.put("version", false);
		MapMatrix<String, Object> config = generalOptions.clone();
		CommandLineUtil.parse(config, args);

		if (MathUtil.getBoolean(config.get("benchmark"))) {
			CompleteMatrixBenchmark.main(args);
		} else if (MathUtil.getBoolean(config.get("version"))) {
			System.out.println("UJMP v" + org.ujmp.core.UJMP.UJMPVERSION);
		} else if (MathUtil.getBoolean(config.get("help"))) {
			System.out.println("Usage: UJMP [OPTION]...");
			System.out.println();
			System.out.println("General Options:");
			CommandLineUtil.printOptions(generalOptions);
			System.out.println();
			System.out.println("Options for Benchmark:");
			CommandLineUtil.printOptions(new BenchmarkConfig());
		} else {
			Matrix m = Matrix.Factory.welcomeMatrix();
			m.showGUI();
		}
	}
}
