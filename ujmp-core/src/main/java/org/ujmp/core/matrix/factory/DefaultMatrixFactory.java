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

package org.ujmp.core.matrix.factory;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ujmp.core.Matrix;
import org.ujmp.core.bigdecimalmatrix.impl.DefaultDenseBigDecimalMatrix2D;
import org.ujmp.core.bigdecimalmatrix.impl.DefaultSparseBigDecimalMatrix;
import org.ujmp.core.bigintegermatrix.DenseBigIntegerMatrix2D;
import org.ujmp.core.bigintegermatrix.calculation.Fibonacci;
import org.ujmp.core.bigintegermatrix.impl.DefaultDenseBigIntegerMatrix2D;
import org.ujmp.core.bigintegermatrix.impl.DefaultSparseBigIntegerMatrix;
import org.ujmp.core.booleanmatrix.DenseBooleanMatrix2D;
import org.ujmp.core.booleanmatrix.impl.DefaultDenseBooleanMatrix2D;
import org.ujmp.core.booleanmatrix.impl.DefaultSparseBooleanMatrix;
import org.ujmp.core.bytematrix.DenseByteMatrix2D;
import org.ujmp.core.bytematrix.impl.ArrayDenseByteMatrix2D;
import org.ujmp.core.bytematrix.impl.DefaultDenseByteMatrix2D;
import org.ujmp.core.bytematrix.impl.DefaultSparseByteMatrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.charmatrix.DenseCharMatrix2D;
import org.ujmp.core.charmatrix.impl.ArrayDenseCharMatrix2D;
import org.ujmp.core.charmatrix.impl.DefaultDenseCharMatrix2D;
import org.ujmp.core.charmatrix.impl.DefaultSparseCharMatrix;
import org.ujmp.core.doublematrix.DenseDoubleMatrix;
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.DoubleMatrixMultiD;
import org.ujmp.core.doublematrix.calculation.entrywise.creators.Ones;
import org.ujmp.core.doublematrix.calculation.entrywise.creators.Pascal;
import org.ujmp.core.doublematrix.calculation.entrywise.creators.Rand;
import org.ujmp.core.doublematrix.calculation.entrywise.creators.Randn;
import org.ujmp.core.doublematrix.calculation.entrywise.creators.Range;
import org.ujmp.core.doublematrix.calculation.general.misc.Dense2Sparse;
import org.ujmp.core.doublematrix.impl.ArrayDenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.impl.DefaultDenseDoubleMatrixMultiD;
import org.ujmp.core.doublematrix.impl.DefaultSparseDoubleMatrix;
import org.ujmp.core.doublematrix.impl.DenseFileMatrix;
import org.ujmp.core.enums.DBType;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.filematrix.FileFormat;
import org.ujmp.core.filematrix.FileMatrix;
import org.ujmp.core.floatmatrix.DenseFloatMatrix2D;
import org.ujmp.core.floatmatrix.impl.ArrayDenseFloatMatrix2D;
import org.ujmp.core.floatmatrix.impl.DefaultDenseFloatMatrix2D;
import org.ujmp.core.floatmatrix.impl.DefaultSparseFloatMatrix;
import org.ujmp.core.genericmatrix.GenericMatrix;
import org.ujmp.core.importer.sourceselector.DefaultMatrixImportSourceSelector;
import org.ujmp.core.importer.sourceselector.MatrixImportSourceSelector;
import org.ujmp.core.intmatrix.DenseIntMatrix2D;
import org.ujmp.core.intmatrix.calculation.Magic;
import org.ujmp.core.intmatrix.impl.DefaultDenseIntMatrix2D;
import org.ujmp.core.intmatrix.impl.DefaultSparseIntMatrix;
import org.ujmp.core.intmatrix.impl.ImageMatrix;
import org.ujmp.core.intmatrix.impl.SimpleDenseIntMatrix2D;
import org.ujmp.core.io.ImportMatrix;
import org.ujmp.core.io.ImportMatrixJDBC;
import org.ujmp.core.io.LinkMatrix;
import org.ujmp.core.io.LinkMatrixJDBC;
import org.ujmp.core.listmatrix.DefaultListMatrix;
import org.ujmp.core.longmatrix.DenseLongMatrix2D;
import org.ujmp.core.longmatrix.impl.DefaultDenseLongMatrix2D;
import org.ujmp.core.longmatrix.impl.DefaultSparseLongMatrix;
import org.ujmp.core.longmatrix.impl.SimpleDenseLongMatrix2D;
import org.ujmp.core.mapmatrix.DefaultMapMatrix;
import org.ujmp.core.mapmatrix.MapMatrix;
import org.ujmp.core.objectmatrix.DenseObjectMatrix2D;
import org.ujmp.core.objectmatrix.ObjectMatrix;
import org.ujmp.core.objectmatrix.ObjectMatrix2D;
import org.ujmp.core.objectmatrix.calculation.Concatenation;
import org.ujmp.core.objectmatrix.calculation.Convert;
import org.ujmp.core.objectmatrix.calculation.Fill;
import org.ujmp.core.objectmatrix.calculation.Repmat;
import org.ujmp.core.objectmatrix.calculation.WelcomeMatrix;
import org.ujmp.core.objectmatrix.impl.DefaultSparseObjectMatrix;
import org.ujmp.core.objectmatrix.impl.EmptyMatrix;
import org.ujmp.core.objectmatrix.impl.SimpleDenseObjectMatrix2D;
import org.ujmp.core.objectmatrix.impl.SynchronizedGenericMatrix;
import org.ujmp.core.shortmatrix.DenseShortMatrix2D;
import org.ujmp.core.shortmatrix.impl.DefaultDenseShortMatrix2D;
import org.ujmp.core.shortmatrix.impl.DefaultSparseShortMatrix;
import org.ujmp.core.shortmatrix.impl.SimpleDenseShortMatrix2D;
import org.ujmp.core.stringmatrix.DenseStringMatrix2D;
import org.ujmp.core.stringmatrix.impl.DefaultDenseStringMatrix2D;
import org.ujmp.core.stringmatrix.impl.DefaultSparseStringMatrix;
import org.ujmp.core.stringmatrix.impl.SimpleDenseStringMatrix2D;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.matrices.AvailableProcessorsMatrix;
import org.ujmp.core.util.matrices.IrisMatrix;
import org.ujmp.core.util.matrices.LocalhostMatrix;
import org.ujmp.core.util.matrices.MemoryUsageMatrix;
import org.ujmp.core.util.matrices.RandomSeedMatrix;
import org.ujmp.core.util.matrices.RunningThreadsMatrix;
import org.ujmp.core.util.matrices.SystemEnvironmentMatrix;
import org.ujmp.core.util.matrices.SystemPropertiesMatrix;
import org.ujmp.core.util.matrices.SystemTimeMatrix;

public class DefaultMatrixFactory extends AbstractMatrixFactory<Matrix> {
	private static final long serialVersionUID = -6788016781517917785L;

	public static final EmptyMatrix EMPTYMATRIX = new EmptyMatrix();

	public final Matrix zeros(long... size) {
		if (size.length == 2) {
			return DenseDoubleMatrix2D.Factory.zeros(size[ROW], size[COLUMN]);
		} else if (size.length > 2) {
			return DoubleMatrixMultiD.Factory.zeros(size);
		} else {
			throw new RuntimeException("Size must be at least 2-dimensional");
		}
	}

	public Matrix createFromScreenshot() throws HeadlessException, AWTException {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		final BufferedImage image = new Robot().createScreenCapture(new Rectangle(screenSize));
		return linkToImage(image);
	}

	public Matrix linkToImage(BufferedImage image) {
		return new ImageMatrix(image);
	}

	public final Matrix zeros(ValueType valueType, long... size) {
		switch (size.length) {
		case 0:
			throw new RuntimeException("Size not defined");
		case 1:
			throw new RuntimeException("Size must be at least 2-dimensional");
		default:
			switch (valueType) {
			case BIGDECIMAL:
				return new DefaultDenseBigDecimalMatrix2D(MathUtil.longToInt(size[ROW]),
						MathUtil.longToInt(size[COLUMN]));
			case BIGINTEGER:
				return new DefaultDenseBigIntegerMatrix2D(MathUtil.longToInt(size[ROW]),
						MathUtil.longToInt(size[COLUMN]));
			case BOOLEAN:
				return new DefaultDenseBooleanMatrix2D(MathUtil.longToInt(size[ROW]),
						MathUtil.longToInt(size[COLUMN]));
			case BYTE:
				return new DefaultDenseByteMatrix2D(MathUtil.longToInt(size[ROW]),
						MathUtil.longToInt(size[COLUMN]));
			case CHAR:
				return new DefaultDenseCharMatrix2D(MathUtil.longToInt(size[ROW]),
						MathUtil.longToInt(size[COLUMN]));
			case DOUBLE:
				return new DefaultDenseDoubleMatrixMultiD(size);
			case FLOAT:
				return new DefaultDenseFloatMatrix2D(MathUtil.longToInt(size[ROW]),
						MathUtil.longToInt(size[COLUMN]));
			case INT:
				return new DefaultDenseIntMatrix2D(MathUtil.longToInt(size[ROW]),
						MathUtil.longToInt(size[COLUMN]));
			case LONG:
				return new DefaultDenseLongMatrix2D(MathUtil.longToInt(size[ROW]),
						MathUtil.longToInt(size[COLUMN]));
			case OBJECT:
				return ObjectMatrix.Factory.zeros(size);
			case SHORT:
				return new DefaultDenseShortMatrix2D(MathUtil.longToInt(size[ROW]),
						MathUtil.longToInt(size[COLUMN]));
			case STRING:
				return new DefaultDenseStringMatrix2D(MathUtil.longToInt(size[ROW]),
						MathUtil.longToInt(size[COLUMN]));
			default:
				throw new RuntimeException("unknown value type: " + valueType);
			}
		}
	}

	public final MatrixImportSourceSelector importSelector() {
		return new DefaultMatrixImportSourceSelector();
	}

	public final Matrix like(Matrix matrix, long rowCount, long columnCount) {
		return matrix.getFactory().zeros(rowCount, columnCount);
	}

	public final Matrix copyFromMatrix(Matrix matrix) {
		return Convert.calcNew(matrix);
	}

	public final Matrix importFromArray(boolean[]... values) {
		return linkToArray(values).clone();
	}

	public final Matrix importFromArray(byte[]... values) {
		return linkToArray(values).clone();
	}

	public final Matrix importFromArray(char[]... values) {
		return linkToArray(values).clone();
	}

	public final Matrix importFromArray(double[]... values) {
		return linkToArray(values).clone();
	}

	public final Matrix importFromArray(float[]... values) {
		return linkToArray(values).clone();
	}

	public final Matrix importFromArray(int[]... values) {
		return linkToArray(values).clone();
	}

	public final Matrix importFromArray(long[]... values) {
		return linkToArray(values).clone();
	}

	public final Matrix importFromArray(Object[]... values) {
		return linkToArray(values).clone();
	}

	public final Matrix importFromArray(short[]... values) {
		return linkToArray(values).clone();
	}

	public final Matrix importFromArray(String[]... values) {
		return linkToArray(values).clone();
	}

	public final DenseBooleanMatrix2D linkToArray(boolean[]... values) {
		return new DefaultDenseBooleanMatrix2D(values);
	}

	public final DenseBooleanMatrix2D linkToArray(boolean... values) {
		return new DefaultDenseBooleanMatrix2D(values);
	}

	public final DenseByteMatrix2D linkToArray(byte[]... values) {
		return new ArrayDenseByteMatrix2D(values);
	}

	public final DenseByteMatrix2D linkToArray(byte... values) {
		return new ArrayDenseByteMatrix2D(values);
	}

	public final DenseCharMatrix2D linkToArray(char[]... values) {
		return new ArrayDenseCharMatrix2D(values);
	}

	public final DenseCharMatrix2D linkToArray(char... values) {
		return new ArrayDenseCharMatrix2D(values);
	}

	public final DenseDoubleMatrix2D linkToArray(double[]... values) {
		return new ArrayDenseDoubleMatrix2D(values);
	}

	public final DenseDoubleMatrix2D linkToArray(double... values) {
		return new ArrayDenseDoubleMatrix2D(values);
	}

	public final DenseFloatMatrix2D linkToArray(float[]... values) {
		return new ArrayDenseFloatMatrix2D(values);
	}

	public final DenseFloatMatrix2D linkToArray(float... values) {
		return new ArrayDenseFloatMatrix2D(values);
	}

	public final DenseIntMatrix2D linkToArray(int[]... values) {
		return new SimpleDenseIntMatrix2D(values);
	}

	public final DenseIntMatrix2D linkToArray(int... values) {
		return new SimpleDenseIntMatrix2D(values);
	}

	public final DenseLongMatrix2D linkToArray(long[]... values) {
		return new SimpleDenseLongMatrix2D(values);
	}

	public final DenseLongMatrix2D linkToArray(long... values) {
		return new DefaultDenseLongMatrix2D(values);
	}

	public final DenseObjectMatrix2D linkToArray(Object[]... values) {
		return new SimpleDenseObjectMatrix2D(values);
	}

	public final DenseObjectMatrix2D linkToArray(Object... values) {
		return new SimpleDenseObjectMatrix2D(values);
	}

	public final DenseShortMatrix2D linkToArray(short[]... values) {
		return new SimpleDenseShortMatrix2D(values);
	}

	public final DenseShortMatrix2D linkToArray(short... values) {
		return new SimpleDenseShortMatrix2D(values);
	}

	public final DenseStringMatrix2D linkToArray(String[]... values) {
		return new SimpleDenseStringMatrix2D(values);
	}

	public final DenseStringMatrix2D linkToArray(String... values) {
		return new SimpleDenseStringMatrix2D(values);
	}

	public final Matrix linkToFile(FileFormat format, File file, Object... parameters)
			throws IOException {
		return LinkMatrix.toFile(format, file, parameters);
	}

	public final Matrix linkToFile(File file, Object... parameters) throws IOException {
		return LinkMatrix.toFile(file, parameters);
	}

	public final Matrix importFromFile(String filename, Object... parameters) throws IOException {
		return ImportMatrix.fromFile(new File(filename), parameters);
	}

	public final Matrix importFromFile(File file, Object... parameters) throws IOException {
		return ImportMatrix.fromFile(file, parameters);
	}

	public final Matrix importFromFile(FileFormat format, String file, Object... parameters)
			throws IOException {
		return ImportMatrix.fromFile(format, new File(file), parameters);
	}

	public final Matrix importFromResource(FileFormat format, String name, Object... parameters)
			throws IOException {
		return ImportMatrix.fromResource(format, name, parameters);
	}

	public final Matrix importFromFile(FileFormat format, File file, Object... parameters)
			throws IOException {
		return ImportMatrix.fromFile(format, file, parameters);
	}

	public final Matrix importFromClipboard(FileFormat format, Object... parameters) {
		return ImportMatrix.fromClipboard(format, parameters);
	}

	public final Matrix createVectorForClass(int classID, int classCount) {
		Matrix matrix = DenseDoubleMatrix2D.Factory.zeros(classCount, 1);
		matrix.setAsDouble(1.0, classID, 0);
		return matrix;
	}

	public final FileMatrix linkToDir(String dir) throws IOException {
		return new FileMatrix(dir);
	}

	public final FileMatrix linkToDir(File dir) throws IOException {
		return new FileMatrix(dir);
	}

	public final MapMatrix<?, ?> linkToMap(Map<?, ?> map) {
		if (map instanceof MapMatrix<?, ?>) {
			return (MapMatrix<?, ?>) map;
		} else {
			return new DefaultMapMatrix(map);
		}
	}

	public final Matrix linkToCollection(Collection<?> list) {
		if (list instanceof Matrix) {
			return (Matrix) list;
		} else {
			return new DefaultListMatrix(list);
		}
	}

	public final Matrix linkToList(List<?> list) {
		if (list instanceof Matrix) {
			return (Matrix) list;
		} else {
			return new DefaultListMatrix(list);
		}
	}

	public final Matrix linkToSet(Set<?> set) {
		if (set instanceof Matrix) {
			return (Matrix) set;
		} else {
			return new DefaultListMatrix(set);
		}
	}

	public final Matrix importFromStream(FileFormat format, InputStream stream,
			Object... parameters) throws IOException {
		return ImportMatrix.fromStream(format, stream, parameters);
	}

	public final Matrix importFromURL(FileFormat format, URL url, Object... parameters)
			throws IOException {
		return ImportMatrix.fromURL(format, url, parameters);
	}

	public final Matrix importFromURL(FileFormat format, String url, Object... parameters)
			throws IOException {
		return ImportMatrix.fromURL(format, url, parameters);
	}

	public final Matrix importFromString(FileFormat format, String string, Object... parameters) {
		return ImportMatrix.fromString(format, string, parameters);
	}

	/**
	 * Wraps another Matrix so that all methods are executed synchronized.
	 * 
	 * @param matrix
	 *            the source Matrix
	 * @return a synchronized Matrix
	 */
	public final <T> SynchronizedGenericMatrix<T> synchronizedMatrix(GenericMatrix<T> matrix) {
		return new SynchronizedGenericMatrix<T>(matrix);
	}

	public final DenseDoubleMatrix linkToBinaryFile(String filename, long... size)
			throws IOException {
		return new DenseFileMatrix(new File(filename), size);
	}

	public final ObjectMatrix2D linkToJDBC(String url, String sqlStatement, String username,
			String password) {
		return LinkMatrixJDBC.toDatabase(url, sqlStatement, username, password);
	}

	public final ObjectMatrix2D linkToJDBC(Connection connection, String sqlStatement) {
		return LinkMatrixJDBC.toDatabase(connection, sqlStatement);
	}

	public final ObjectMatrix2D linkToJDBC(DBType type, String host, int port, String database,
			String sqlStatement, String username, String password) {
		return LinkMatrixJDBC.toDatabase(type, host, port, database, sqlStatement, username,
				password);
	}

	public final ObjectMatrix importFromJDBC(String url, String sqlStatement, String username,
			String password) {
		return ImportMatrixJDBC.fromDatabase(url, sqlStatement, username, password);
	}

	public final ObjectMatrix importFromJDBC(Connection connection, String sqlStatement) {
		return ImportMatrixJDBC.fromDatabase(connection, sqlStatement);
	}

	public final ObjectMatrix importFromJDBC(DBType type, String host, int port, String database,
			String sqlStatement, String username, String password) {
		return ImportMatrixJDBC.fromDatabase(type, host, port, database, sqlStatement, username,
				password);
	}

	public final DenseDoubleMatrix2D linkToValue(double value) {
		return new ArrayDenseDoubleMatrix2D(new double[][] { { value } });
	}

	public final DenseIntMatrix2D linkToValue(int value) {
		return new SimpleDenseIntMatrix2D(new int[][] { { value } });
	}

	public final DenseCharMatrix2D linkToValue(char value) {
		return new ArrayDenseCharMatrix2D(new char[][] { { value } });
	}

	public final DenseBooleanMatrix2D linkToValue(boolean value) {
		return new DefaultDenseBooleanMatrix2D(new boolean[][] { { value } });
	}

	public final DenseByteMatrix2D linkToValue(byte value) {
		return new ArrayDenseByteMatrix2D(new byte[][] { { value } });
	}

	public final DenseShortMatrix2D linkToValue(short value) {
		return new SimpleDenseShortMatrix2D(new short[][] { { value } });
	}

	public final DenseStringMatrix2D linkToValue(String value) {
		return new SimpleDenseStringMatrix2D(new String[][] { { value } });
	}

	public final DenseLongMatrix2D linkToValue(long value) {
		return new SimpleDenseLongMatrix2D(new long[][] { { value } });
	}

	public final Matrix linkToValue(Object value) {
		if (value == null) {
			return emptyMatrix();
		} else if (value instanceof Double) {
			return new ArrayDenseDoubleMatrix2D(new double[][] { { (Double) value } });
		} else if (value instanceof Integer) {
			return new SimpleDenseIntMatrix2D(new int[][] { { (Integer) value } });
		} else if (value instanceof Float) {
			return new ArrayDenseFloatMatrix2D(new float[][] { { (Float) value } });
		} else if (value instanceof String) {
			return new SimpleDenseStringMatrix2D(new String[][] { { (String) value } });
		} else if (value instanceof Short) {
			return new SimpleDenseShortMatrix2D(new short[][] { { (Short) value } });
		} else if (value instanceof Byte) {
			return new ArrayDenseByteMatrix2D(new byte[][] { { (Byte) value } });
		} else if (value instanceof Boolean) {
			return new DefaultDenseBooleanMatrix2D(new boolean[][] { { (Boolean) value } });
		} else if (value instanceof Long) {
			return new SimpleDenseLongMatrix2D(new long[][] { { (Long) value } });
		} else {
			return new SimpleDenseObjectMatrix2D(new Object[][] { { value } });
		}
	}

	public final EmptyMatrix emptyMatrix() {
		return EMPTYMATRIX;
	}

	public final Matrix repmat(Ret returnType, Matrix matrix, long... count) {
		return new Repmat(matrix, count).calc(returnType);
	}

	public final WelcomeMatrix welcomeMatrix() {
		return new WelcomeMatrix();
	}

	public final Matrix vertCat(Matrix row, long rowCount) {
		Matrix[] matrices = new Matrix[(int) rowCount];
		Arrays.fill(matrices, row);
		return vertCat(matrices);
	}

	public final Matrix horCat(Matrix column, long columnCount) {
		Matrix[] matrices = new Matrix[(int) columnCount];
		Arrays.fill(matrices, column);
		return horCat(matrices);
	}

	public final DenseDoubleMatrix2D sequence(double start, double end) {
		return sequence(start, end, 1);
	}

	public final DenseDoubleMatrix2D sequence(double start, double end, double stepsize) {
		return Matrix.Factory.linkToArray(MathUtil.sequenceDouble(start, end, stepsize));
	}

	public final RunningThreadsMatrix runningThreads() {
		return new RunningThreadsMatrix();
	}

	public final SystemEnvironmentMatrix systemEnvironment() {
		return new SystemEnvironmentMatrix();
	}

	public final SystemPropertiesMatrix systemProperties() {
		return new SystemPropertiesMatrix();
	}

	public final Matrix horCat(Matrix... matrices) {
		return concat(COLUMN, matrices);
	}

	public final <A> Matrix vertCat(Matrix... matrices) {
		return concat(ROW, matrices);
	}

	public final <A> Matrix vertCat(Collection<Matrix> matrices) {
		return concat(ROW, matrices);
	}

	public final Matrix horCat(Collection<Matrix> matrices) {
		return concat(COLUMN, matrices);
	}

	public final Matrix concat(int dimension, Matrix... matrices) {
		return concat(dimension, Arrays.asList(matrices));
	}

	public final Matrix concat(int dimension, Collection<Matrix> matrices) {
		return new Concatenation(dimension, matrices).calc(Ret.NEW);
	}

	public final SystemTimeMatrix systemTime() {
		return new SystemTimeMatrix();
	}

	public final AvailableProcessorsMatrix availableProcessors() {
		return new AvailableProcessorsMatrix();
	}

	public final MemoryUsageMatrix memoryUsage() {
		return new MemoryUsageMatrix();
	}

	public final Matrix range(double start, double end, double stepSize) {
		return new Range(null, start, stepSize, end).calc(Ret.LINK);
	}

	public final Matrix range(double start, double end) {
		return range(start, 1.0, end);
	}

	public final RandomSeedMatrix randomSeed() {
		return new RandomSeedMatrix();
	}

	public final Matrix ones(long... size) {
		return Ones.calc(size);
	}

	public final Matrix fill(Object value, long... size) {
		return Fill.calc(value, size);
	}

	public final Matrix magic(int size) {
		return Magic.magic(size);
	}

	public final Matrix pascal(long... size) {
		return new Pascal(DenseBigIntegerMatrix2D.Factory.zeros(size[ROW], size[COLUMN]))
				.calcOrig();
	}

	public final Matrix fibonacci(int count) {
		return Fibonacci.calc(count);
	}

	public final Matrix sparse(Matrix indices) {
		return Dense2Sparse.calc(indices);
	}

	public final Matrix randn(long... size) {
		return Randn.calc(size);
	}

	public final Matrix randn(ValueType valueType, long... size) {
		return Randn.calc(valueType, size);
	}

	public final Matrix rand(long... size) {
		return Rand.calc(size);
	}

	public final Matrix rand(ValueType valueType, long... size) {
		return Rand.calc(valueType, size);
	}

	public final Matrix correlatedColumns(int rows, int columns, double correlationFactor) {
		Matrix ret = Matrix.Factory.zeros(rows, columns);

		Matrix orig = Matrix.Factory.randn(rows, 1);

		for (int c = 0; c < columns; c++) {
			Matrix rand = Matrix.Factory.randn(rows, 1);

			for (int r = 0; r < rows; r++) {
				ret.setAsDouble((orig.getAsDouble(r, 0) * correlationFactor)
						+ ((1.0 - correlationFactor) * rand.getAsDouble(r, 0)), r, c);
			}
		}

		return ret;
	}

	public final Matrix sparse(ValueType valueType, long... size) {
		switch (size.length) {
		case 0:
			throw new RuntimeException("Size not defined");
		case 1:
			throw new RuntimeException("Size must be at least 2-dimensional");
		default:
			switch (valueType) {
			case BIGDECIMAL:
				return new DefaultSparseBigDecimalMatrix(size);
			case BIGINTEGER:
				return new DefaultSparseBigIntegerMatrix(size);
			case BOOLEAN:
				return new DefaultSparseBooleanMatrix(size);
			case BYTE:
				return new DefaultSparseByteMatrix(size);
			case CHAR:
				return new DefaultSparseCharMatrix(size);
			case DOUBLE:
				return new DefaultSparseDoubleMatrix(size);
			case FLOAT:
				return new DefaultSparseFloatMatrix(size);
			case INT:
				return new DefaultSparseIntMatrix(size);
			case LONG:
				return new DefaultSparseLongMatrix(size);
			case OBJECT:
				return new DefaultSparseObjectMatrix(size);
			case SHORT:
				return new DefaultSparseShortMatrix(size);
			case STRING:
				return new DefaultSparseStringMatrix(size);
			default:
				throw new RuntimeException("unknown value type: " + valueType);
			}
		}
	}

	// Wolfer's sunspot data 1700 - 1987
	public final DenseDoubleMatrix2D sunSpotDataset() {
		return Matrix.Factory.linkToArray(new double[] { 5, 11, 16, 23, 36, 58, 29, 20, 10, 8, 3,
				0, 0, 2, 11, 27, 47, 63, 60, 39, 28, 26, 22, 11, 21, 40, 78, 122, 103, 73, 47, 35,
				11, 5, 16, 34, 70, 81, 111, 101, 73, 40, 20, 16, 5, 11, 22, 40, 60, 80.9, 83.4,
				47.7, 47.8, 30.7, 12.2, 9.6, 10.2, 32.4, 47.6, 54, 62.9, 85.9, 61.2, 45.1, 36.4,
				20.9, 11.4, 37.8, 69.8, 106.1, 100.8, 81.6, 66.5, 34.8, 30.6, 7, 19.8, 92.5, 154.4,
				125.9, 84.8, 68.1, 38.5, 22.8, 10.2, 24.1, 82.9, 132, 130.9, 118.1, 89.9, 66.6, 60,
				46.9, 41, 21.3, 16, 6.4, 4.1, 6.8, 14.5, 34, 45, 43.1, 47.5, 42.2, 28.1, 10.1, 8.1,
				2.5, 0, 1.4, 5, 12.2, 13.9, 35.4, 45.8, 41.1, 30.1, 23.9, 15.6, 6.6, 4, 1.8, 8.5,
				16.6, 36.3, 49.6, 64.2, 67, 70.9, 47.8, 27.5, 8.5, 13.2, 56.9, 121.5, 138.3, 103.2,
				85.7, 64.6, 36.7, 24.2, 10.7, 15, 40.1, 61.5, 98.5, 124.7, 96.3, 66.6, 64.5, 54.1,
				39, 20.6, 6.7, 4.3, 22.7, 54.8, 93.8, 95.8, 77.2, 59.1, 44, 47, 30.5, 16.3, 7.3,
				37.6, 74, 139, 111.2, 101.6, 66.2, 44.7, 17, 11.3, 12.4, 3.4, 6, 32.3, 54.3, 59.7,
				63.7, 63.5, 52.2, 25.4, 13.1, 6.8, 6.3, 7.1, 35.6, 73, 85.1, 78, 64, 41.8, 26.2,
				26.7, 12.1, 9.5, 2.7, 5, 24.4, 42, 63.5, 53.8, 62, 48.5, 43.9, 18.6, 5.7, 3.6, 1.4,
				9.6, 47.4, 57.1, 103.9, 80.6, 63.6, 37.6, 26.1, 14.2, 5.8, 16.7, 44.3, 63.9, 69,
				77.8, 64.9, 35.7, 21.2, 11.1, 5.7, 8.7, 36.1, 79.7, 114.4, 109.6, 88.8, 67.8, 47.5,
				30.6, 16.3, 9.6, 33.2, 92.6, 151.6, 136.3, 134.7, 83.9, 69.4, 31.5, 13.9, 4.4, 38,
				141.7, 190.2, 184.8, 159, 112.3, 53.9, 37.5, 27.9, 10.2, 15.1, 47, 93.8, 105.9,
				105.5, 104.5, 66.6, 68.9, 38, 34.5, 15.5, 12.6, 27.5, 92.5, 155.4, 154.6, 140.4,
				115.9, 66.6, 45.9, 17.9, 13.4, 29.3 });
	}

	public final IrisMatrix irisMatrix() {
		return new IrisMatrix();
	}

	public final LocalhostMatrix localhostMatrix() {
		return new LocalhostMatrix();
	}

	public Matrix zeros(long rows, long columns) {
		return zeros(new long[] { rows, columns });
	}

}
