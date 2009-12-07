/*
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

package org.ujmp.core.benchmark;

import java.io.File;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Random;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.doublematrix.DoubleMatrix2D;
import org.ujmp.core.enums.FileFormat;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.GCUtil;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.RandomSimple;
import org.ujmp.core.util.StringUtil;

public abstract class AbstractMatrix2DBenchmark {

	private static final double TOOLONG = 999999;

	private static final double NOTAVAILABLE = 0;

	private static final double ERRORTIME = Double.NaN;

	private final String transposeSizes = "2x2,3x3,4x4,5x5,6x6,7x7,8x8,9x9,10x10,20x20,30x30,40x40,50x50,60x60,70x70,80x80,90x90,100x100,200x200,300x300,400x400,500x500,600x600,700x700,800x800,900x900,1000x1000,2000x2000,3000x3000,4000x4000,5000x5000";

	private final String mtimesSizes = "2x2,3x3,4x4,5x5,6x6,7x7,8x8,9x9,10x10,20x20,30x30,40x40,50x50,60x60,70x70,80x80,90x90,100x100,200x200,300x300,400x400,500x500,600x600,700x700,800x800,900x900,1000x1000,2000x2000";

	private final String invSizes = "2x2,3x3,4x4,5x5,6x6,7x7,8x8,9x9,10x10,20x20,30x30,40x40,50x50,60x60,70x70,80x80,90x90,100x100,200x200,300x300,400x400,500x500,600x600,700x700,800x800,900x900,1000x1000,2000x2000";

	private final String svdSizes = "2x2,3x3,4x4,5x5,6x6,7x7,8x8,9x9,10x10,20x20,30x30,40x40,50x50,60x60,70x70,80x80,90x90,100x100,200x200,300x300,400x400,500x500,600x600,700x700,800x800,900x900,1000x1000";

	private final String eigSizes = "2x2,3x3,4x4,5x5,6x6,7x7,8x8,9x9,10x10,20x20,30x30,40x40,50x50,60x60,70x70,80x80,90x90,100x100,200x200,300x300,400x400,500x500,600x600,700x700,800x800,900x900,1000x1000,2000x2000";

	private final String qrSizes = "2x2,3x3,4x4,5x5,6x6,7x7,8x8,9x9,10x10,20x20,30x30,40x40,50x50,60x60,70x70,80x80,90x90,100x100,200x200,300x300,400x400,500x500,600x600,700x700,800x800,900x900,1000x1000,2000x2000";

	private final String luSizes = "2x2,3x3,4x4,5x5,6x6,7x7,8x8,9x9,10x10,20x20,30x30,40x40,50x50,60x60,70x70,80x80,90x90,100x100,200x200,300x300,400x400,500x500,600x600,700x700,800x800,900x900,1000x1000,2000x2000";

	private final String cholSizes = "2x2,3x3,4x4,5x5,6x6,7x7,8x8,9x9,10x10,20x20,30x30,40x40,50x50,60x60,70x70,80x80,90x90,100x100,200x200,300x300,400x400,500x500,600x600,700x700,800x800,900x900,1000x1000,2000x2000";

	public abstract DoubleMatrix2D createMatrix(long... size) throws MatrixException;

	public abstract DoubleMatrix2D createMatrix(Matrix source) throws MatrixException;

	public AbstractMatrix2DBenchmark() {
	}

	public void setRunsPerMatrix(int runs) {
		System.setProperty("runsPerMatrix", "" + runs);
	}

	public int getRunsPerMatrix() {
		return MathUtil.getInt(System.getProperty("runsPerMatrix"));
	}

	public void setBurnInRuns(int runs) {
		System.setProperty("burnInRuns", "" + runs);
	}

	public String getMatrixLabel() {
		return createMatrix(1, 1).getClass().getSimpleName();
	}

	public int getBurnInRuns() {
		return MathUtil.getInt(System.getProperty("burnInRuns"));
	}

	public boolean isRunInit() {
		return "true".equals(System.getProperty("runInit"));
	}

	public void setRunInit(boolean runInit) {
		System.setProperty("runInit", "" + runInit);
	}

	public boolean isRunCreate() {
		return "true".equals(System.getProperty("runCreate"));
	}

	public void setRunCreate(boolean runCreate) {
		System.setProperty("runCreate", "" + runCreate);
	}

	public boolean isRunCopy() {
		return "true".equals(System.getProperty("runCopy"));
	}

	public void setRunCopy(boolean runCopy) {
		System.setProperty("runCopy", "" + runCopy);
	}

	public boolean isRunPlusScalarNew() {
		return "true".equals(System.getProperty("runPlusScalarNew"));
	}

	public void setRunPlusScalarNew(boolean runPlusScalarNew) {
		System.setProperty("runPlusScalarNew", "" + runPlusScalarNew);
	}

	public boolean isRunPlusScalarOrig() {
		return "true".equals(System.getProperty("runPlusScalarOrig"));
	}

	public void setRunPlusScalarOrig(boolean runPlusScalarOrig) {
		System.setProperty("runPlusScalarOrig", "" + runPlusScalarOrig);
	}

	public boolean isRunTimesScalarNew() {
		return "true".equals(System.getProperty("runTimesScalarNew"));
	}

	public void setRunTimesScalarNew(boolean runTimesScalarNew) {
		System.setProperty("runTimesScalarNew", "" + runTimesScalarNew);
	}

	public boolean isRunTimesScalarOrig() {
		return "true".equals(System.getProperty("runTimesScalarOrig"));
	}

	public void setRunTimesScalarOrig(boolean runTimesScalarOrig) {
		System.setProperty("runTimesScalarOrig", "" + runTimesScalarOrig);
	}

	public boolean isRunTransposeNew() {
		return "true".equals(System.getProperty("runTransposeNew"));
	}

	public boolean isRunMtimesNew() {
		return "true".equals(System.getProperty("runMtimesNew"));
	}

	public boolean isRunInv() {
		return "true".equals(System.getProperty("runInv"));
	}

	public boolean isSkipSlowLibraries() {
		return "true".equals(System.getProperty("skipSlowLibraries"));
	}

	public boolean isRunSVD() {
		return "true".equals(System.getProperty("runSVD"));
	}

	public boolean isRunChol() {
		return "true".equals(System.getProperty("runChol"));
	}

	public boolean isRunEig() {
		return "true".equals(System.getProperty("runEig"));
	}

	public boolean isRunQR() {
		return "true".equals(System.getProperty("runQR"));
	}

	public boolean isRunLU() {
		return "true".equals(System.getProperty("runLU"));
	}

	public void setRunTransposeNew(boolean b) {
		System.setProperty("runTransposeNew", "" + b);
	}

	public void setRunMtimesNew(boolean b) {
		System.setProperty("runMtimesNew", "" + b);
	}

	public void setRunInv(boolean b) {
		System.setProperty("runInv", "" + b);
	}

	public void setRunSVD(boolean b) {
		System.setProperty("runSVD", "" + b);
	}

	public void setRunQR(boolean b) {
		System.setProperty("runQR", "" + b);
	}

	public void setRunEig(boolean b) {
		System.setProperty("runEig", "" + b);
	}

	public void setSkipSlowLibraries(boolean b) {
		System.setProperty("skipSlowLibraries", "" + b);
	}

	public void setRunLU(boolean b) {
		System.setProperty("runLU", "" + b);
	}

	public void setRunChol(boolean b) {
		System.setProperty("runChol", "" + b);
	}

	public String getTransposeSizes() {
		return transposeSizes;
	}

	public String getMtimesSizes() {
		return mtimesSizes;
	}

	public String getInvSizes() {
		return invSizes;
	}

	public String getEigSizes() {
		return eigSizes;
	}

	public String getSVDSizes() {
		return svdSizes;
	}

	public String getQRSizes() {
		return qrSizes;
	}

	public String getLUSizes() {
		return luSizes;
	}

	public String getCholSizes() {
		return cholSizes;
	}

	public void runAllTests() throws Exception {
		setRunAllTests();
		run();
	}

	public void setRunAllTests() throws Exception {
		setBurnInRuns(3);
		setRunsPerMatrix(10);

		setRunTransposeNew(true);
		setRunMtimesNew(true);
		setRunInv(true);
		setRunSVD(true);
		setRunEig(true);
		setRunQR(true);
		setRunLU(true);
		setRunChol(true);
	}

	public void run() throws Exception {
		if (Runtime.getRuntime().maxMemory() < 980 * 1024 * 1024) {
			throw new Exception("You must start Java with more memory: -Xmx1024M");
		}

		try {
			System.out.println("===============================================================");
			System.out.println(createMatrix(1, 1).getClass().getSimpleName());
			System.out.println("===============================================================");

			long t0 = System.currentTimeMillis();

			if (isRunTransposeNew()) {
				runBenchmarkTransposeNew();
			}

			if (isRunMtimesNew()) {
				runBenchmarkMtimesNew();
			}

			if (isRunInv()) {
				runBenchmarkInv();
			}

			if (isRunSVD()) {
				runBenchmarkSVD();
			}

			if (isRunEig()) {
				runBenchmarkEig();
			}

			if (isRunQR()) {
				runBenchmarkQR();
			}

			if (isRunLU()) {
				runBenchmarkLU();
			}

			if (isRunChol()) {
				runBenchmarkChol();
			}

			long t1 = System.currentTimeMillis();

			System.out.println();
			System.out.println("Benchmark runtime: " + StringUtil.duration(t1 - t0));
			System.out.println();

		} catch (UnsupportedClassVersionError e) {
			System.out.println("this library is not compatible with the current Java version");
			System.out.println("it cannot be included in the benchmark");
			System.out.println();
		} catch (Exception e) {
			System.out.println("there was some error with this library");
			System.out.println("it cannot be included in the benchmark");
			e.printStackTrace();
		}
	}

	public void runBenchmarkTransposeNew() throws Exception {
		String[] sizes = getTransposeSizes().split(",");
		Matrix result = MatrixFactory.zeros(ValueType.STRING, getRunsPerMatrix(), sizes.length);
		result.setLabel(getMatrixLabel() + "-transposeNew");

		for (int s = 0; s < sizes.length; s++) {
			long[] size = Coordinates.parseString(sizes[s]);
			result.setColumnLabel(s, Coordinates.toString('x', size));
			System.out.print("transpose new matrix [" + Coordinates.toString('x', size) + "]: ");
			System.out.flush();

			for (int i = 0; i < getBurnInRuns(); i++) {
				benchmarkTransposeNew(i, size);
				System.out.print("#");
				System.out.flush();
			}
			for (int i = 0; i < getRunsPerMatrix(); i++) {
				double t = benchmarkTransposeNew(i, size);
				result.setAsDouble(t, i, s);
				System.out.print(".");
				System.out.flush();
			}

			Matrix mean = result.mean(Ret.NEW, Matrix.ROW, true);
			Matrix std = result.std(Ret.NEW, Matrix.ROW, true);
			mean.setLabel(mean.getLabel() + "-mean");
			std.setLabel(std.getLabel() + "-std");
			System.out.println(" " + mean.getAsInt(0, s) + "+-" + std.getAsInt(0, s) + "ms");
		}

		Matrix temp = MatrixFactory.vertCat(result.getAnnotation().getDimensionMatrix(Matrix.ROW),
				result);
		temp.exportToFile(FileFormat.CSV, new File(getResultDir() + getMatrixLabel() + "/"
				+ "transposeNew.csv"));
	}

	public void runBenchmarkInv() throws Exception {
		String[] sizes = getInvSizes().split(",");
		Matrix result = MatrixFactory.zeros(ValueType.STRING, getRunsPerMatrix(), sizes.length);
		result.setLabel(getMatrixLabel() + "-inv");

		for (int s = 0; s < sizes.length; s++) {
			long[] size = Coordinates.parseString(sizes[s]);
			result.setColumnLabel(s, Coordinates.toString('x', size));
			System.out.print("inv [" + Coordinates.toString('x', size) + "]: ");
			System.out.flush();

			for (int i = 0; i < getBurnInRuns(); i++) {
				benchmarkInv(i, size);
				System.out.print("#");
				System.out.flush();
			}
			for (int i = 0; i < getRunsPerMatrix(); i++) {
				double t = benchmarkInv(i, size);
				result.setAsDouble(t, i, s);
				System.out.print(".");
				System.out.flush();
			}

			Matrix mean = result.mean(Ret.NEW, Matrix.ROW, true);
			Matrix std = result.std(Ret.NEW, Matrix.ROW, true);
			mean.setLabel(mean.getLabel() + "-mean");
			std.setLabel(std.getLabel() + "-std");
			System.out.println(" " + mean.getAsInt(0, s) + "+-" + std.getAsInt(0, s) + "ms");
		}

		Matrix temp = MatrixFactory.vertCat(result.getAnnotation().getDimensionMatrix(Matrix.ROW),
				result);
		temp.exportToFile(FileFormat.CSV, new File(getResultDir() + getMatrixLabel() + "/"
				+ "inv.csv"));
	}

	public void runBenchmarkSVD() throws Exception {
		String[] sizes = getSVDSizes().split(",");
		Matrix result = MatrixFactory.zeros(ValueType.STRING, getRunsPerMatrix(), sizes.length);
		result.setLabel(getMatrixLabel() + "-svd");

		for (int s = 0; s < sizes.length; s++) {
			long[] size = Coordinates.parseString(sizes[s]);
			result.setColumnLabel(s, Coordinates.toString('x', size));
			System.out.print("svd [" + Coordinates.toString('x', size) + "]: ");
			System.out.flush();

			for (int i = 0; i < getBurnInRuns(); i++) {
				benchmarkSVD(i, size);
				System.out.print("#");
				System.out.flush();
			}
			for (int i = 0; i < getRunsPerMatrix(); i++) {
				double t = benchmarkSVD(i, size);
				result.setAsDouble(t, i, s);
				System.out.print(".");
				System.out.flush();
			}

			Matrix mean = result.mean(Ret.NEW, Matrix.ROW, true);
			Matrix std = result.std(Ret.NEW, Matrix.ROW, true);
			mean.setLabel(mean.getLabel() + "-mean");
			std.setLabel(std.getLabel() + "-std");
			System.out.println(" " + mean.getAsInt(0, s) + "+-" + std.getAsInt(0, s) + "ms");
		}

		Matrix temp = MatrixFactory.vertCat(result.getAnnotation().getDimensionMatrix(Matrix.ROW),
				result);
		temp.exportToFile(FileFormat.CSV, new File(getResultDir() + getMatrixLabel() + "/"
				+ "svd.csv"));
	}

	public void runBenchmarkQR() throws Exception {
		String[] sizes = getQRSizes().split(",");
		Matrix result = MatrixFactory.zeros(ValueType.STRING, getRunsPerMatrix(), sizes.length);
		result.setLabel(getMatrixLabel() + "-qr");

		for (int s = 0; s < sizes.length; s++) {
			long[] size = Coordinates.parseString(sizes[s]);
			result.setColumnLabel(s, Coordinates.toString('x', size));
			System.out.print("qr [" + Coordinates.toString('x', size) + "]: ");
			System.out.flush();

			for (int i = 0; i < getBurnInRuns(); i++) {
				benchmarkQR(i, size);
				System.out.print("#");
				System.out.flush();
			}
			for (int i = 0; i < getRunsPerMatrix(); i++) {
				double t = benchmarkQR(i, size);
				result.setAsDouble(t, i, s);
				System.out.print(".");
				System.out.flush();
			}

			Matrix mean = result.mean(Ret.NEW, Matrix.ROW, true);
			Matrix std = result.std(Ret.NEW, Matrix.ROW, true);
			mean.setLabel(mean.getLabel() + "-mean");
			std.setLabel(std.getLabel() + "-std");
			System.out.println(" " + mean.getAsInt(0, s) + "+-" + std.getAsInt(0, s) + "ms");
		}

		Matrix temp = MatrixFactory.vertCat(result.getAnnotation().getDimensionMatrix(Matrix.ROW),
				result);
		temp.exportToFile(FileFormat.CSV, new File(getResultDir() + getMatrixLabel() + "/"
				+ "qr.csv"));
	}

	public void runBenchmarkLU() throws Exception {
		String[] sizes = getLUSizes().split(",");
		Matrix result = MatrixFactory.zeros(ValueType.STRING, getRunsPerMatrix(), sizes.length);
		result.setLabel(getMatrixLabel() + "-lu");

		for (int s = 0; s < sizes.length; s++) {
			long[] size = Coordinates.parseString(sizes[s]);
			result.setColumnLabel(s, Coordinates.toString('x', size));
			System.out.print("lu [" + Coordinates.toString('x', size) + "]: ");
			System.out.flush();

			for (int i = 0; i < getBurnInRuns(); i++) {
				benchmarkLU(i, size);
				System.out.print("#");
				System.out.flush();
			}
			for (int i = 0; i < getRunsPerMatrix(); i++) {
				double t = benchmarkLU(i, size);
				result.setAsDouble(t, i, s);
				System.out.print(".");
				System.out.flush();
			}

			Matrix mean = result.mean(Ret.NEW, Matrix.ROW, true);
			Matrix std = result.std(Ret.NEW, Matrix.ROW, true);
			mean.setLabel(mean.getLabel() + "-mean");
			std.setLabel(std.getLabel() + "-std");
			System.out.println(" " + mean.getAsInt(0, s) + "+-" + std.getAsInt(0, s) + "ms");
		}

		Matrix temp = MatrixFactory.vertCat(result.getAnnotation().getDimensionMatrix(Matrix.ROW),
				result);
		temp.exportToFile(FileFormat.CSV, new File(getResultDir() + getMatrixLabel() + "/"
				+ "lu.csv"));
	}

	public void runBenchmarkEig() throws Exception {
		String[] sizes = getEigSizes().split(",");
		Matrix result = MatrixFactory.zeros(ValueType.STRING, getRunsPerMatrix(), sizes.length);
		result.setLabel(getMatrixLabel() + "-eig");

		for (int s = 0; s < sizes.length; s++) {
			long[] size = Coordinates.parseString(sizes[s]);
			result.setColumnLabel(s, Coordinates.toString('x', size));
			System.out.print("eig [" + Coordinates.toString('x', size) + "]: ");
			System.out.flush();

			for (int i = 0; i < getBurnInRuns(); i++) {
				benchmarkEig(i, size);
				System.out.print("#");
				System.out.flush();
			}
			for (int i = 0; i < getRunsPerMatrix(); i++) {
				double t = benchmarkEig(i, size);
				result.setAsDouble(t, i, s);
				System.out.print(".");
				System.out.flush();
			}

			Matrix mean = result.mean(Ret.NEW, Matrix.ROW, true);
			Matrix std = result.std(Ret.NEW, Matrix.ROW, true);
			mean.setLabel(mean.getLabel() + "-mean");
			std.setLabel(std.getLabel() + "-std");
			System.out.println(" " + mean.getAsInt(0, s) + "+-" + std.getAsInt(0, s) + "ms");
		}

		Matrix temp = MatrixFactory.vertCat(result.getAnnotation().getDimensionMatrix(Matrix.ROW),
				result);
		temp.exportToFile(FileFormat.CSV, new File(getResultDir() + getMatrixLabel() + "/"
				+ "eig.csv"));
	}

	public void runBenchmarkChol() throws Exception {
		String[] sizes = getCholSizes().split(",");
		Matrix result = MatrixFactory.zeros(ValueType.STRING, getRunsPerMatrix(), sizes.length);
		result.setLabel(getMatrixLabel() + "-chol");

		for (int s = 0; s < sizes.length; s++) {
			long[] size = Coordinates.parseString(sizes[s]);
			result.setColumnLabel(s, Coordinates.toString('x', size));
			System.out.print("chol [" + Coordinates.toString('x', size) + "]: ");
			System.out.flush();

			for (int i = 0; i < getBurnInRuns(); i++) {
				benchmarkChol(i, size);
				System.out.print("#");
				System.out.flush();
			}
			for (int i = 0; i < getRunsPerMatrix(); i++) {
				double t = benchmarkChol(i, size);
				result.setAsDouble(t, i, s);
				System.out.print(".");
				System.out.flush();
			}

			Matrix mean = result.mean(Ret.NEW, Matrix.ROW, true);
			Matrix std = result.std(Ret.NEW, Matrix.ROW, true);
			mean.setLabel(mean.getLabel() + "-mean");
			std.setLabel(std.getLabel() + "-std");
			System.out.println(" " + mean.getAsInt(0, s) + "+-" + std.getAsInt(0, s) + "ms");
		}

		Matrix temp = MatrixFactory.vertCat(result.getAnnotation().getDimensionMatrix(Matrix.ROW),
				result);
		temp.exportToFile(FileFormat.CSV, new File(getResultDir() + getMatrixLabel() + "/"
				+ "chol.csv"));
	}

	public String getResultDir() throws UnknownHostException {
		return "results/" + getHostName() + "/" + System.getProperty("os.name") + "/Java"
				+ System.getProperty("java.version") + "/";
	}

	public String getHostName() {
		try {
			return Inet4Address.getLocalHost().getHostName();
		} catch (Exception e) {
			return "localhost";
		}
	}

	public void runBenchmarkMtimesNew() throws Exception {
		String[] sizes = getMtimesSizes().split(",");
		Matrix result = MatrixFactory.zeros(ValueType.STRING, getRunsPerMatrix(), sizes.length);
		result.setLabel(getMatrixLabel() + "-mtimesNew");

		for (int s = 0; s < sizes.length; s++) {
			long[] size = Coordinates.parseString(sizes[s]);
			result.setColumnLabel(s, Coordinates.toString('x', size));
			System.out.print("mtimes new matrix [" + Coordinates.toString('x', size) + "]: ");
			System.out.flush();

			for (int i = 0; i < getBurnInRuns(); i++) {
				benchmarkMtimesNew(i, size, size);
				System.out.print("#");
				System.out.flush();
			}
			for (int i = 0; i < getRunsPerMatrix(); i++) {
				double t = benchmarkMtimesNew(i, size, size);
				result.setAsDouble(t, i, s);
				System.out.print(".");
				System.out.flush();
			}

			Matrix mean = result.mean(Ret.NEW, Matrix.ROW, true);
			Matrix std = result.std(Ret.NEW, Matrix.ROW, true);
			mean.setLabel(mean.getLabel() + "-mean");
			std.setLabel(std.getLabel() + "-std");
			System.out.println(" " + mean.getAsInt(0, s) + "+-" + std.getAsInt(0, s) + "ms");
		}

		Matrix temp = MatrixFactory.vertCat(result.getAnnotation().getDimensionMatrix(Matrix.ROW),
				result);
		temp.exportToFile(FileFormat.CSV, new File(getResultDir() + getMatrixLabel() + "/"
				+ "mtimesNew.csv"));
	}

	public double benchmarkCreate(long... size) {
		try {
			GCUtil.gc();
			long t0 = System.nanoTime();
			createMatrix(size);
			long t1 = System.nanoTime();
			return (t1 - t0) / 1000000.0;
		} catch (Throwable e) {
			System.err.print("e");
			System.err.flush();
			return ERRORTIME;
		}
	}

	public double benchmarkPlusScalarNew(long... size) {
		Matrix m = null, r = null;
		try {
			m = createMatrix(size);
			if (m.getClass().getDeclaredMethod("plus", Double.TYPE) == null) {
				System.err.print("-");
				System.err.flush();
				return NOTAVAILABLE;
			}
			GCUtil.gc();
			long t0 = System.nanoTime();
			r = m.plus(2);
			long t1 = System.nanoTime();
			if (r == null) {
				System.err.print("e");
				System.err.flush();
				return ERRORTIME;
			}
			return (t1 - t0) / 1000000.0;
		} catch (Throwable e) {
			System.err.print("e");
			System.err.flush();
			return ERRORTIME;
		}
	}

	public double benchmarkPlusScalarOrig(long... size) {
		Matrix m = null, r = null;
		try {
			m = createMatrix(size);
			if (m.getClass().getDeclaredMethod("plus", Ret.class, Boolean.TYPE, Double.TYPE) == null) {
				System.err.print("-");
				System.err.flush();
				return NOTAVAILABLE;
			}
			GCUtil.gc();
			long t0 = System.nanoTime();
			r = m.plus(Ret.ORIG, false, 2);
			long t1 = System.nanoTime();
			if (r == null) {
				System.err.print("e");
				System.err.flush();
				return ERRORTIME;
			}
			return (t1 - t0) / 1000000.0;
		} catch (Throwable e) {
			System.err.print("e");
			System.err.flush();
			return ERRORTIME;
		}
	}

	public double benchmarkCopy(long... size) {
		Matrix m = null, r = null;
		try {
			m = createMatrix(size);
			if (!m.getClass().getName().startsWith("org.ujmp.core")
					&& m.getClass().getDeclaredMethod("copy") == null) {
				System.err.print("-");
				System.err.flush();
				return NOTAVAILABLE;
			}
			GCUtil.gc();
			long t0 = System.nanoTime();
			r = m.copy();
			long t1 = System.nanoTime();
			if (r == null) {
				System.err.print("e");
				System.err.flush();
				return ERRORTIME;
			}
			return (t1 - t0) / 1000000.0;
		} catch (Throwable e) {
			System.err.print("e");
			System.err.flush();
			return ERRORTIME;
		}
	}

	public double benchmarkTimesScalarNew(long... size) {
		Matrix m = null, r = null;
		try {
			m = createMatrix(size);
			if (m.getClass().getDeclaredMethod("times", Double.TYPE) == null) {
				System.err.print("-");
				System.err.flush();
				return NOTAVAILABLE;
			}
			GCUtil.gc();
			long t0 = System.nanoTime();
			r = m.times(2);
			long t1 = System.nanoTime();
			if (r == null) {
				System.err.print("e");
				System.err.flush();
				return ERRORTIME;
			}
			return (t1 - t0) / 1000000.0;
		} catch (Throwable e) {
			System.err.print("e");
			System.err.flush();
			return ERRORTIME;
		}
	}

	public double benchmarkTimesScalarOrig(long... size) {
		Matrix m = null, r = null;
		try {
			m = createMatrix(size);
			if (m.getClass().getDeclaredMethod("times", Ret.class, Boolean.TYPE, Double.TYPE) == null) {
				System.err.print("-");
				System.err.flush();
				return NOTAVAILABLE;
			}
			GCUtil.gc();
			long t0 = System.nanoTime();
			r = m.times(Ret.ORIG, false, 2);
			long t1 = System.nanoTime();
			if (r == null) {
				System.err.print("e");
				System.err.flush();
				return ERRORTIME;
			}
			return (t1 - t0) / 1000000.0;
		} catch (Throwable e) {
			System.err.print("e");
			System.err.flush();
			return ERRORTIME;
		}
	}

	public double benchmarkTransposeNew(int run, long... size) {
		DoubleMatrix2D m = null;
		Matrix r = null;
		try {
			m = createMatrix(size);
			rand(run, m);
			GCUtil.gc();
			long t0 = System.nanoTime();
			r = m.transpose();
			long t1 = System.nanoTime();
			if (r == null) {
				System.err.print("e");
				System.err.flush();
				return ERRORTIME;
			}
			return (t1 - t0) / 1000000.0;
		} catch (Throwable e) {
			System.err.print("e");
			System.err.flush();
			return ERRORTIME;
		}
	}

	public static void rand(long seed, DoubleMatrix2D matrix) {
		Random random = new RandomSimple(3345454363676l + seed);
		int rows = (int) matrix.getRowCount();
		int cols = (int) matrix.getColumnCount();
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				matrix.setDouble(random.nextFloat() - 0.5, r, c);
			}
		}
	}

	public double benchmarkInv(int run, long... size) {
		DoubleMatrix2D m = null;
		Matrix r = null;
		try {
			m = createMatrix(size);
			if (!m.getClass().getName().startsWith("org.ujmp.core")
					&& m.getClass().getDeclaredMethod("inv") == null) {
				System.err.print("-");
				System.err.flush();
				return NOTAVAILABLE;
			}
			if (isSkipSlowLibraries() && m.getClass().getName().startsWith("org.ujmp.orbital.")
					&& Coordinates.product(size) > 40000) {
				System.err.print("skip ");
				System.err.flush();
				return TOOLONG;
			}
			if (isSkipSlowLibraries() && m.getClass().getName().startsWith("org.ujmp.jlinalg.")
					&& Coordinates.product(size) > 160000) {
				System.err.print("skip ");
				System.err.flush();
				return TOOLONG;
			}
			if (isSkipSlowLibraries() && m.getClass().getName().startsWith("org.ujmp.jscience.")
					&& Coordinates.product(size) > 160000) {
				System.err.print("skip ");
				System.err.flush();
				return TOOLONG;
			}

			rand(run, m);
			GCUtil.gc();
			long t0 = System.nanoTime();
			r = m.inv();
			long t1 = System.nanoTime();
			if (r == null) {
				System.err.print("e");
				System.err.flush();
				return ERRORTIME;
			}
			return (t1 - t0) / 1000000.0;
		} catch (Throwable e) {
			System.err.print("e");
			System.err.flush();
			return ERRORTIME;
		}
	}

	public double benchmarkSVD(int run, long... size) {
		DoubleMatrix2D m = null;
		Matrix[] r = null;
		try {
			m = createMatrix(size);
			if (!m.getClass().getName().startsWith("org.ujmp.core")
					&& m.getClass().getDeclaredMethod("svd") == null) {
				System.err.print("-");
				System.err.flush();
				return NOTAVAILABLE;
			}
			if (isSkipSlowLibraries() && m.getClass().getName().startsWith("org.ujmp.vecmath.")
					&& Coordinates.product(size) > 40000) {
				System.err.print("skip ");
				System.err.flush();
				return TOOLONG;
			}

			rand(run, m);
			GCUtil.gc();
			long t0 = System.nanoTime();
			r = m.svd();
			long t1 = System.nanoTime();
			if (r == null) {
				System.err.print("e");
				System.err.flush();
				return ERRORTIME;
			}
			return (t1 - t0) / 1000000.0;
		} catch (NoSuchMethodException e) {
			System.err.print("-");
			System.err.flush();
			return NOTAVAILABLE;
		} catch (Throwable e) {
			System.err.print("e");
			System.err.flush();
			return ERRORTIME;
		}
	}

	public double benchmarkEig(int run, long... size) {
		DoubleMatrix2D m = null;
		Matrix[] r = null;
		try {
			m = createMatrix(size);
			if (!m.getClass().getName().startsWith("org.ujmp.core.")
					&& m.getClass().getDeclaredMethod("eig") == null) {
				System.err.print("-");
				System.err.flush();
				return NOTAVAILABLE;
			}
			rand(run, m);
			GCUtil.gc();
			long t0 = System.nanoTime();
			r = m.eig();
			long t1 = System.nanoTime();
			if (r == null) {
				System.err.print("e");
				System.err.flush();
				return ERRORTIME;
			}
			return (t1 - t0) / 1000000.0;
		} catch (NoSuchMethodException e) {
			System.err.print("-");
			System.err.flush();
			return NOTAVAILABLE;
		} catch (Throwable e) {
			System.err.print("e");
			System.err.flush();
			return ERRORTIME;
		}
	}

	public double benchmarkQR(int run, long... size) {
		DoubleMatrix2D m = null;
		Matrix[] r = null;
		try {
			m = createMatrix(size);
			if (!m.getClass().getName().startsWith("org.ujmp.core")
					&& m.getClass().getDeclaredMethod("qr") == null) {
				System.err.print("-");
				System.err.flush();
				return NOTAVAILABLE;
			}
			rand(run, m);
			GCUtil.gc();
			long t0 = System.nanoTime();
			r = m.qr();
			long t1 = System.nanoTime();
			if (r == null) {
				System.err.print("e");
				System.err.flush();
				return ERRORTIME;
			}
			return (t1 - t0) / 1000000.0;
		} catch (NoSuchMethodException e) {
			System.err.print("-");
			System.err.flush();
			return NOTAVAILABLE;
		} catch (Throwable e) {
			System.err.print("e");
			System.err.flush();
			return ERRORTIME;
		}
	}

	public double benchmarkChol(int run, long... size) {
		DoubleMatrix2D m = null;
		Matrix r = null;
		try {
			m = createMatrix(size);
			if (!m.getClass().getName().startsWith("org.ujmp.core")
					&& m.getClass().getDeclaredMethod("chol") == null) {
				System.err.print("-");
				System.err.flush();
				return NOTAVAILABLE;
			}

			// if (m.getClass().getName().startsWith("org.ujmp.parallelcolt."))
			// {
			// System.err.print("skip(deadlock)");
			// System.err.flush();
			// return TOOLONG;
			// }

			rand(run, m);
			GCUtil.gc();
			long t0 = System.nanoTime();
			r = m.chol();
			long t1 = System.nanoTime();
			if (r == null) {
				System.err.print("e");
				System.err.flush();
				return ERRORTIME;
			}
			return (t1 - t0) / 1000000.0;
		} catch (NoSuchMethodException e) {
			System.err.print("-");
			System.err.flush();
			return NOTAVAILABLE;
		} catch (Throwable e) {
			System.err.print("e");
			System.err.flush();
			return ERRORTIME;
		}
	}

	public double benchmarkLU(int run, long... size) {
		DoubleMatrix2D m = null;
		Matrix[] r = null;
		try {
			m = createMatrix(size);
			if (!m.getClass().getName().startsWith("org.ujmp.core")
					&& m.getClass().getDeclaredMethod("lu") == null) {
				System.err.print("-");
				System.err.flush();
				return NOTAVAILABLE;
			}
			if (isSkipSlowLibraries() && m.getClass().getName().startsWith("org.ujmp.orbital.")
					&& Coordinates.product(size) > 160000) {
				System.err.print("skip ");
				System.err.flush();
				return TOOLONG;
			}
			if (isSkipSlowLibraries() && m.getClass().getName().startsWith("org.ujmp.jscience.")
					&& Coordinates.product(size) > 250000) {
				System.err.print("skip ");
				System.err.flush();
				return TOOLONG;
			}

			rand(run, m);
			GCUtil.gc();
			long t0 = System.nanoTime();
			r = m.lu();
			long t1 = System.nanoTime();
			if (r == null) {
				System.err.print("e");
				System.err.flush();
				return ERRORTIME;
			}
			return (t1 - t0) / 1000000.0;
		} catch (NoSuchMethodException e) {
			System.err.print("-");
			System.err.flush();
			return NOTAVAILABLE;
		} catch (Throwable e) {
			System.err.print("e");
			System.err.flush();
			return ERRORTIME;
		}
	}

	public double benchmarkMtimesNew(int run, long[] size0, long[] size1) {
		DoubleMatrix2D m0 = null, m1 = null;
		Matrix r = null;
		try {
			m0 = createMatrix(size0);
			m1 = createMatrix(size1);

			if (isSkipSlowLibraries() && m0.getClass().getName().startsWith("org.ujmp.sst.")
					&& Coordinates.product(size0) > 400000) {
				System.err.print("skip ");
				System.err.flush();
				return TOOLONG;
			}
			if (isSkipSlowLibraries() && m0.getClass().getName().startsWith("org.ujmp.owlpack.")
					&& Coordinates.product(size0) > 500000) {
				System.err.print("skip ");
				System.err.flush();
				return TOOLONG;
			}
			if (isSkipSlowLibraries() && m0.getClass().getName().startsWith("org.ujmp.orbital.")
					&& Coordinates.product(size0) > 100000) {
				System.err.print("skip ");
				System.err.flush();
				return TOOLONG;
			}
			if (isSkipSlowLibraries() && m0.getClass().getName().startsWith("org.ujmp.mantissa.")
					&& Coordinates.product(size0) > 900000) {
				System.err.print("skip ");
				System.err.flush();
				return TOOLONG;
			}
			if (isSkipSlowLibraries() && m0.getClass().getName().startsWith("org.ujmp.jsci.")
					&& Coordinates.product(size0) > 900000) {
				System.err.print("skip ");
				System.err.flush();
				return TOOLONG;
			}
			if (isSkipSlowLibraries() && m0.getClass().getName().startsWith("org.ujmp.jmatrices.")
					&& Coordinates.product(size0) > 40000) {
				System.err.print("skip ");
				System.err.flush();
				return TOOLONG;
			}
			if (isSkipSlowLibraries() && m0.getClass().getName().startsWith("org.ujmp.jlinalg.")
					&& Coordinates.product(size0) > 200000) {
				System.err.print("skip ");
				System.err.flush();
				return TOOLONG;
			}

			rand(run, m0);
			rand(run * 999, m1);
			GCUtil.gc();
			long t0 = System.nanoTime();
			r = m0.mtimes(m1);
			long t1 = System.nanoTime();
			if (r == null) {
				System.err.print("e");
				System.err.flush();
				return ERRORTIME;
			}
			return (t1 - t0) / 1000000.0;
		} catch (Throwable e) {
			System.err.print("e");
			System.err.flush();
			return ERRORTIME;
		}
	}

}
