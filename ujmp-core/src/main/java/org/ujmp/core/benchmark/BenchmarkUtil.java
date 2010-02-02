/*
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

package org.ujmp.core.benchmark;

import java.net.Inet4Address;
import java.util.Random;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.DoubleMatrix2D;

public abstract class BenchmarkUtil {

	public static String getResultDir() {
		return "results/" + getHostName() + "/" + System.getProperty("os.name") + "/Java"
				+ System.getProperty("java.version") + "/";
	}

	public static String getHostName() {
		try {
			return Inet4Address.getLocalHost().getHostName();
		} catch (Exception e) {
			return "localhost";
		}
	}

	public static void rand(long benchmarkSeed, long seed, DoubleMatrix2D matrix) {
		Random random = new Random(benchmarkSeed + seed);
		int rows = (int) matrix.getRowCount();
		int cols = (int) matrix.getColumnCount();
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				matrix.setDouble(random.nextDouble() - 0.5, r, c);
			}
		}
	}

	public static DoubleMatrix2D createMatrix(Class<? extends Matrix> matrixClass, long... size) {
		try {
			return (DoubleMatrix2D) matrixClass.getConstructor(long[].class).newInstance(size);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static DoubleMatrix2D createMatrix(Class<? extends Matrix> matrixClass, Matrix source) {
		try {
			return (DoubleMatrix2D) matrixClass.getConstructor(Matrix.class).newInstance(source);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
