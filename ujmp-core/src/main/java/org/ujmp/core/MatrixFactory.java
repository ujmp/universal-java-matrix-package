/*
 * Copyright (C) 2008-2009 Holger Arndt, A. Naegele and M. Bundschus
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

package org.ujmp.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.ujmp.core.booleanmatrix.DenseBooleanMatrix2D;
import org.ujmp.core.booleanmatrix.impl.DefaultDenseBooleanMatrix2D;
import org.ujmp.core.bytematrix.DenseByteMatrix2D;
import org.ujmp.core.bytematrix.impl.ArrayDenseByteMatrix2D;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.charmatrix.DenseCharMatrix2D;
import org.ujmp.core.charmatrix.impl.ArrayDenseCharMatrix2D;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.datematrix.DenseDateMatrix2D;
import org.ujmp.core.datematrix.impl.SimpleDenseDateMatrix2D;
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.calculation.entrywise.creators.Eye;
import org.ujmp.core.doublematrix.calculation.entrywise.creators.Ones;
import org.ujmp.core.doublematrix.calculation.entrywise.creators.Rand;
import org.ujmp.core.doublematrix.calculation.entrywise.creators.Randn;
import org.ujmp.core.doublematrix.calculation.entrywise.creators.Range;
import org.ujmp.core.doublematrix.calculation.general.misc.Dense2Sparse;
import org.ujmp.core.doublematrix.impl.ArrayDenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.impl.DenseFileMatrix2D;
import org.ujmp.core.enums.AnnotationTransfer;
import org.ujmp.core.enums.DB;
import org.ujmp.core.enums.FileFormat;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.floatmatrix.DenseFloatMatrix2D;
import org.ujmp.core.floatmatrix.impl.ArrayDenseFloatMatrix2D;
import org.ujmp.core.intmatrix.DenseIntMatrix2D;
import org.ujmp.core.intmatrix.impl.SimpleDenseIntMatrix2D;
import org.ujmp.core.io.ImportMatrix;
import org.ujmp.core.io.ImportMatrixJDBC;
import org.ujmp.core.io.LinkMatrix;
import org.ujmp.core.io.LinkMatrixJDBC;
import org.ujmp.core.listmatrix.DefaultListMatrix;
import org.ujmp.core.longmatrix.DenseLongMatrix2D;
import org.ujmp.core.longmatrix.impl.DefaultDenseLongMatrix2D;
import org.ujmp.core.longmatrix.impl.SimpleDenseLongMatrix2D;
import org.ujmp.core.mapmatrix.DefaultMapMatrix;
import org.ujmp.core.mapper.MatrixMapper;
import org.ujmp.core.objectmatrix.DenseObjectMatrix2D;
import org.ujmp.core.objectmatrix.ObjectMatrix2D;
import org.ujmp.core.objectmatrix.calculation.Concatenation;
import org.ujmp.core.objectmatrix.calculation.Convert;
import org.ujmp.core.objectmatrix.calculation.Fill;
import org.ujmp.core.objectmatrix.calculation.Repmat;
import org.ujmp.core.objectmatrix.impl.EmptyMatrix;
import org.ujmp.core.objectmatrix.impl.SimpleDenseObjectMatrix2D;
import org.ujmp.core.objectmatrix.impl.SynchronizedObjectMatrix;
import org.ujmp.core.shortmatrix.DenseShortMatrix2D;
import org.ujmp.core.shortmatrix.impl.SimpleDenseShortMatrix2D;
import org.ujmp.core.stringmatrix.DenseStringMatrix2D;
import org.ujmp.core.stringmatrix.impl.FileListMatrix;
import org.ujmp.core.stringmatrix.impl.SimpleDenseStringMatrix2D;
import org.ujmp.core.util.matrices.MatrixAvailableProcessors;
import org.ujmp.core.util.matrices.MatrixMemoryUsage;
import org.ujmp.core.util.matrices.MatrixRandomSeed;
import org.ujmp.core.util.matrices.MatrixRunningThreads;
import org.ujmp.core.util.matrices.MatrixSystemEnvironment;
import org.ujmp.core.util.matrices.MatrixSystemProperties;
import org.ujmp.core.util.matrices.MatrixSystemTime;

/**
 * This class provides a factory for matrix generation. Use
 * <code>dense(rows, columns)</code> or <code>sparse(rows, columns)</code> to
 * create empty matrices.
 * 
 * 
 * 
 * @author Holger Arndt
 */
public abstract class MatrixFactory {

	public static final int ROW = Matrix.ROW;

	public static final int COLUMN = Matrix.COLUMN;

	public static final int Y = Matrix.Y;

	public static final int X = Matrix.X;

	public static final int Z = Matrix.Z;

	public static final int ALL = Matrix.ALL;

	public static final int NONE = Matrix.NONE;

	public static final EmptyMatrix EMPTYMATRIX = new EmptyMatrix();

	private static MatrixMapper matrixMapper = MatrixMapper.getInstance();

	public static final Matrix systemTime() {
		return new MatrixSystemTime();
	}

	public static final Matrix availableProcessors() {
		return new MatrixAvailableProcessors();
	}

	public static final Matrix memoryUsage() {
		return new MatrixMemoryUsage();
	}

	public static final Matrix range(double start, double end, double stepSize) {
		return new Range(null, start, stepSize, end).calc(Ret.LINK);
	}

	public static final Matrix range(double start, double end) {
		return range(start, 1.0, end);
	}

	public static final Matrix randomSeed() {
		return new MatrixRandomSeed();
	}

	public static final Matrix runningThreads() {
		return new MatrixRunningThreads();
	}

	public static final Matrix systemEnvironment() {
		return new MatrixSystemEnvironment();
	}

	public static final Matrix systemProperties() {
		return new MatrixSystemProperties();
	}

	public static final Matrix horCat(Matrix... matrices) throws MatrixException {
		return concat(COLUMN, matrices);
	}

	public static final <A> Matrix vertCat(Matrix... matrices) throws MatrixException {
		return concat(ROW, matrices);
	}

	public static final <A> Matrix vertCat(Collection<Matrix> matrices) throws MatrixException {
		return concat(ROW, matrices);
	}

	public static final Matrix horCat(Collection<Matrix> matrices) throws MatrixException {
		return concat(COLUMN, matrices);
	}

	public static final Matrix concat(int dimension, Matrix... matrices) throws MatrixException {
		return concat(dimension, Arrays.asList(matrices));
	}

	public static final Matrix concat(int dimension, Collection<Matrix> matrices)
			throws MatrixException {
		return new Concatenation(dimension, matrices).calc(Ret.LINK);
	}

	public static final Matrix importFromArray(boolean[]... values) {
		return linkToArray(values).copy();
	}

	public static final Matrix importFromArray(byte[]... values) {
		return linkToArray(values).copy();
	}

	public static final Matrix importFromArray(char[]... values) {
		return linkToArray(values).copy();
	}

	public static final Matrix importFromArray(Date[]... values) {
		return linkToArray(values).copy();
	}

	public static final Matrix importFromArray(double[]... values) {
		return linkToArray(values).copy();
	}

	public static final Matrix importFromArray(float[]... values) {
		return linkToArray(values).copy();
	}

	public static final Matrix importFromArray(int[]... values) {
		return linkToArray(values).copy();
	}

	public static final Matrix importFromArray(long[]... values) {
		return linkToArray(values).copy();
	}

	public static final Matrix importFromArray(Object[]... values) {
		return linkToArray(values).copy();
	}

	public static final Matrix importFromArray(short[]... values) {
		return linkToArray(values).copy();
	}

	public static final Matrix importFromArray(String[]... values) {
		return linkToArray(values).copy();
	}

	public static final DenseBooleanMatrix2D linkToArray(boolean[]... values) {
		return new DefaultDenseBooleanMatrix2D(values);
	}

	public static final DenseBooleanMatrix2D linkToArray(boolean... values) {
		return new DefaultDenseBooleanMatrix2D(values);
	}

	public static final DenseByteMatrix2D linkToArray(byte[]... values) {
		return new ArrayDenseByteMatrix2D(values);
	}

	public static final DenseByteMatrix2D linkToArray(byte... values) {
		return new ArrayDenseByteMatrix2D(values);
	}

	public static final DenseCharMatrix2D linkToArray(char[]... values) {
		return new ArrayDenseCharMatrix2D(values);
	}

	public static final DenseCharMatrix2D linkToArray(char... values) {
		return new ArrayDenseCharMatrix2D(values);
	}

	public static final DenseDateMatrix2D linkToArray(Date[]... values) {
		return new SimpleDenseDateMatrix2D(values);
	}

	public static final DenseDateMatrix2D linkToArray(Date... values) {
		return new SimpleDenseDateMatrix2D(values);
	}

	public static final DenseDoubleMatrix2D linkToArray(double[]... values) {
		return new ArrayDenseDoubleMatrix2D(values);
	}

	public static final DenseDoubleMatrix2D linkToArray(double... values) {
		return new ArrayDenseDoubleMatrix2D(values);
	}

	public static final DenseFloatMatrix2D linkToArray(float[]... values) {
		return new ArrayDenseFloatMatrix2D(values);
	}

	public static final DenseFloatMatrix2D linkToArray(float... values) {
		return new ArrayDenseFloatMatrix2D(values);
	}

	public static final DenseIntMatrix2D linkToArray(int[]... values) {
		return new SimpleDenseIntMatrix2D(values);
	}

	public static final DenseIntMatrix2D linkToArray(int... values) {
		return new SimpleDenseIntMatrix2D(values);
	}

	public static final DenseLongMatrix2D linkToArray(long[]... values) {
		return new SimpleDenseLongMatrix2D(values);
	}

	public static final DenseLongMatrix2D linkToArray(long... values) {
		return new DefaultDenseLongMatrix2D(values);
	}

	public static final DenseObjectMatrix2D linkToArray(Object[]... values) {
		return new SimpleDenseObjectMatrix2D(values);
	}

	public static final DenseObjectMatrix2D linkToArray(Object... values) {
		return new SimpleDenseObjectMatrix2D(values);
	}

	public static final DenseShortMatrix2D linkToArray(short[]... values) {
		return new SimpleDenseShortMatrix2D(values);
	}

	public static final DenseShortMatrix2D linkToArray(short... values) {
		return new SimpleDenseShortMatrix2D(values);
	}

	public static final DenseStringMatrix2D linkToArray(String[]... values) {
		return new SimpleDenseStringMatrix2D(values);
	}

	public static final DenseStringMatrix2D linkToArray(String... values) {
		return new SimpleDenseStringMatrix2D(values);
	}

	public static final Matrix copyFromMatrix(AnnotationTransfer annotationTransfer, Matrix matrix)
			throws MatrixException {
		return Convert.calcNew(annotationTransfer, matrix);
	}

	public static final Matrix copyFromMatrix(Matrix matrix) throws MatrixException {
		return Convert.calcNew(AnnotationTransfer.LINK, matrix);
	}

	public static final Matrix randn(long... size) throws MatrixException {
		return Randn.calc(size);
	}

	public static final Matrix randn(ValueType valueType, long... size) throws MatrixException {
		return Randn.calc(valueType, size);
	}

	public static final Matrix rand(long... size) throws MatrixException {
		return Rand.calc(size);
	}

	public static final Matrix rand(ValueType valueType, long... size) throws MatrixException {
		return Rand.calc(valueType, size);
	}

	public static final Matrix copyFromMatrix(ValueType newValueType,
			AnnotationTransfer annotationTransfer, Matrix matrix) throws MatrixException {
		return Convert.calcNew(newValueType, annotationTransfer, matrix);
	}

	public static final Matrix correlatedColumns(int rows, int columns, double correlationFactor)
			throws MatrixException {
		Matrix ret = MatrixFactory.zeros(rows, columns);

		Matrix orig = MatrixFactory.randn(rows, 1);

		for (int c = 0; c < columns; c++) {
			Matrix rand = MatrixFactory.randn(rows, 1);

			for (int r = 0; r < rows; r++) {
				ret.setAsDouble((orig.getAsDouble(r, 0) * correlationFactor)
						+ ((1.0 - correlationFactor) * rand.getAsDouble(r, 0)), r, c);
			}
		}

		return ret;
	}

	public static final DenseDoubleMatrix2D linkToValue(double value) {
		return new ArrayDenseDoubleMatrix2D(new double[][] { { value } });
	}

	public static final DenseIntMatrix2D linkToValue(int value) {
		return new SimpleDenseIntMatrix2D(new int[][] { { value } });
	}

	public static final DenseCharMatrix2D linkToValue(char value) {
		return new ArrayDenseCharMatrix2D(new char[][] { { value } });
	}

	public static final DenseDateMatrix2D linkToValue(Date value) {
		return new SimpleDenseDateMatrix2D(new Date[][] { { value } });
	}

	public static final DenseBooleanMatrix2D linkToValue(boolean value) {
		return new DefaultDenseBooleanMatrix2D(new boolean[][] { { value } });
	}

	public static final DenseByteMatrix2D linkToValue(byte value) {
		return new ArrayDenseByteMatrix2D(new byte[][] { { value } });
	}

	public static final DenseShortMatrix2D linkToValue(short value) {
		return new SimpleDenseShortMatrix2D(new short[][] { { value } });
	}

	public static final DenseStringMatrix2D linkToValue(String value) {
		return new SimpleDenseStringMatrix2D(new String[][] { { value } });
	}

	public static final DenseLongMatrix2D linkToValue(long value) {
		return new SimpleDenseLongMatrix2D(new long[][] { { value } });
	}

	public static final Matrix linkToValue(Object value) {
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

	public static Matrix zeros(ValueType valueType, long... size) throws MatrixException {
		return dense(valueType, size);
	}

	public static Matrix dense(ValueType valueType, long... size) throws MatrixException {
		try {
			Constructor<?> con = null;

			switch (size.length) {
			case 0:
				throw new MatrixException("Size not defined");
			case 1:
				throw new MatrixException("Size must be at least 2-dimensional");
			case 2:
				switch (valueType) {
				case BOOLEAN:
					con = matrixMapper.getDenseBooleanMatrix2DConstructor();
					break;
				case BYTE:
					con = matrixMapper.getDenseByteMatrix2DConstructor();
					break;
				case CHAR:
					con = matrixMapper.getDenseCharMatrix2DConstructor();
					break;
				case DATE:
					con = matrixMapper.getDenseDateMatrix2DConstructor();
					break;
				case DOUBLE:
					con = matrixMapper.getDenseDoubleMatrix2DConstructor();
					break;
				case FLOAT:
					con = matrixMapper.getDenseFloatMatrix2DConstructor();
					break;
				case INT:
					con = matrixMapper.getDenseIntMatrix2DConstructor();
					break;
				case LONG:
					con = matrixMapper.getDenseLongMatrix2DConstructor();
					break;
				case OBJECT:
					con = matrixMapper.getDenseObjectMatrix2DConstructor();
					break;
				case SHORT:
					con = matrixMapper.getDenseShortMatrix2DConstructor();
					break;
				case STRING:
					con = matrixMapper.getDenseStringMatrix2DConstructor();
					break;
				case BIGINTEGER:
					con = matrixMapper.getDenseBigIntegerMatrix2DConstructor();
					break;
				case BIGDECIMAL:
					con = matrixMapper.getDenseBigDecimalMatrix2DConstructor();
					break;
				default:
					throw new MatrixException("entry type not yet supported: " + valueType);
				}
				break;
			default:
				switch (valueType) {
				case BOOLEAN:
					con = matrixMapper.getDenseBooleanMatrixMultiDConstructor();
					break;
				case BYTE:
					con = matrixMapper.getDenseByteMatrixMultiDConstructor();
					break;
				case CHAR:
					con = matrixMapper.getDenseCharMatrixMultiDConstructor();
					break;
				case DATE:
					con = matrixMapper.getDenseDateMatrixMultiDConstructor();
					break;
				case DOUBLE:
					con = matrixMapper.getDenseDoubleMatrixMultiDConstructor();
					break;
				case FLOAT:
					con = matrixMapper.getDenseFloatMatrixMultiDConstructor();
					break;
				case INT:
					con = matrixMapper.getDenseIntMatrixMultiDConstructor();
					break;
				case LONG:
					con = matrixMapper.getDenseLongMatrixMultiDConstructor();
					break;
				case OBJECT:
					con = matrixMapper.getDenseObjectMatrixMultiDConstructor();
					break;
				case SHORT:
					con = matrixMapper.getDenseShortMatrixMultiDConstructor();
					break;
				case STRING:
					con = matrixMapper.getDenseStringMatrixMultiDConstructor();
					break;
				case BIGINTEGER:
					con = matrixMapper.getDenseBigIntegerMatrixMultiDConstructor();
					break;
				case BIGDECIMAL:
					con = matrixMapper.getDenseBigDecimalMatrixMultiDConstructor();
					break;
				default:
					throw new MatrixException("entry type not yet supported: " + valueType);
				}
			}

			return (Matrix) con.newInstance(size);

		} catch (Exception e) {
			throw new MatrixException(e);
		}

	}

	public static Matrix ones(long... size) throws MatrixException {
		return Ones.calc(size);
	}

	public static Matrix fill(Object value, long... size) throws MatrixException {
		return Fill.calc(value, size);
	}

	public static Matrix ones(ValueType valueType, long... size) throws MatrixException {
		return Ones.calc(valueType, size);
	}

	public static Matrix eye(long... size) throws MatrixException {
		return Eye.calc(size);
	}

	public static Matrix eye(ValueType valueType, long... size) throws MatrixException {
		return Eye.calc(valueType, size);
	}

	public static final Matrix createVectorForClass(int classID, int classCount)
			throws MatrixException {
		Matrix matrix = MatrixFactory.zeros(classCount, 1);
		matrix.setAsDouble(1.0, classID, 0);
		return matrix;
	}

	public static final FileListMatrix linkToDir(String dir) {
		return new FileListMatrix(dir);
	}

	@SuppressWarnings("unchecked")
	public static final Matrix linkToMap(Map<?, ?> map) {
		if (map instanceof Matrix) {
			return (Matrix) map;
		} else {
			return new DefaultMapMatrix(map);
		}
	}

	public static final Matrix linkToCollection(Collection<?> list) {
		if (list instanceof Matrix) {
			return (Matrix) list;
		} else {
			return new DefaultListMatrix<Object>(list);
		}
	}

	public static Matrix importFromStream(FileFormat format, InputStream stream,
			Object... parameters) throws MatrixException, IOException {
		return ImportMatrix.fromStream(format, stream, parameters);
	}

	public static Matrix importFromURL(FileFormat format, URL url, Object... parameters)
			throws MatrixException, IOException {
		return ImportMatrix.fromURL(format, url, parameters);
	}

	public static Matrix importFromURL(FileFormat format, String url, Object... parameters)
			throws MatrixException, IOException {
		return ImportMatrix.fromURL(format, url, parameters);
	}

	public static Matrix importFromString(FileFormat format, String string, Object... parameters)
			throws MatrixException {
		return ImportMatrix.fromString(format, string, parameters);
	}

	/**
	 * Wraps another Matrix so that all methods are executed synchronized.
	 * 
	 * @param matrix
	 *            the source Matrix
	 * @return a synchronized Matrix
	 */
	public static final SynchronizedObjectMatrix synchronizedMatrix(Matrix matrix) {
		return new SynchronizedObjectMatrix(matrix);
	}

	public static final DenseDoubleMatrix2D linkToBinaryFile(String filename, int rowCount,
			int columnCount) throws IOException {
		return new DenseFileMatrix2D(new File(filename), rowCount, columnCount);
	}

	public static final ObjectMatrix2D linkToJDBC(String url, String sqlStatement, String username,
			String password) {
		return LinkMatrixJDBC.toDatabase(url, sqlStatement, username, password);
	}

	public static final ObjectMatrix2D linkToJDBC(DB type, String host, int port, String database,
			String sqlStatement, String username, String password) {
		return LinkMatrixJDBC.toDatabase(type, host, port, database, sqlStatement, username,
				password);
	}

	public static final ObjectMatrix2D importFromJDBC(String url, String sqlStatement,
			String username, String password) {
		return ImportMatrixJDBC.fromDatabase(url, sqlStatement, username, password);
	}

	public static final ObjectMatrix2D importFromJDBC(DB type, String host, int port,
			String database, String sqlStatement, String username, String password) {
		return ImportMatrixJDBC.fromDatabase(type, host, port, database, sqlStatement, username,
				password);
	}

	public final static Matrix sparse(long... size) throws MatrixException {
		return sparse(ValueType.DOUBLE, size);
	}

	public static final Matrix sparse(Matrix indices) {
		return Dense2Sparse.calc(indices);
	}

	public final static Matrix sparse(ValueType valueType, long... size) throws MatrixException {
		try {
			Constructor<?> con = null;

			switch (size.length) {
			case 0:
				throw new MatrixException("Size not defined");
			case 1:
				throw new MatrixException("Size must be at least 2-dimensional");
			case 2:
				switch (valueType) {
				case BOOLEAN:
					con = matrixMapper.getSparseBooleanMatrix2DConstructor();
					break;
				case BYTE:
					con = matrixMapper.getSparseByteMatrix2DConstructor();
					break;
				case CHAR:
					con = matrixMapper.getSparseCharMatrix2DConstructor();
					break;
				case DATE:
					con = matrixMapper.getSparseDateMatrix2DConstructor();
					break;
				case DOUBLE:
					con = matrixMapper.getSparseDoubleMatrix2DConstructor();
					break;
				case FLOAT:
					con = matrixMapper.getSparseFloatMatrix2DConstructor();
					break;
				case INT:
					con = matrixMapper.getSparseIntMatrix2DConstructor();
					break;
				case LONG:
					con = matrixMapper.getSparseLongMatrix2DConstructor();
					break;
				case OBJECT:
					con = matrixMapper.getSparseObjectMatrix2DConstructor();
					break;
				case SHORT:
					con = matrixMapper.getSparseShortMatrix2DConstructor();
					break;
				case STRING:
					con = matrixMapper.getSparseStringMatrix2DConstructor();
					break;
				case BIGINTEGER:
					con = matrixMapper.getSparseBigIntegerMatrix2DConstructor();
					break;
				case BIGDECIMAL:
					con = matrixMapper.getSparseBigDecimalMatrix2DConstructor();
					break;
				default:
					throw new MatrixException("entry type not supported: " + valueType);
				}
				break;
			default:
				switch (valueType) {
				case BOOLEAN:
					con = matrixMapper.getSparseBooleanMatrixMultiDConstructor();
					break;
				case BYTE:
					con = matrixMapper.getSparseByteMatrixMultiDConstructor();
					break;
				case CHAR:
					con = matrixMapper.getSparseCharMatrixMultiDConstructor();
					break;
				case DATE:
					con = matrixMapper.getSparseDateMatrixMultiDConstructor();
					break;
				case DOUBLE:
					con = matrixMapper.getSparseDoubleMatrixMultiDConstructor();
					break;
				case FLOAT:
					con = matrixMapper.getSparseFloatMatrixMultiDConstructor();
					break;
				case INT:
					con = matrixMapper.getSparseIntMatrixMultiDConstructor();
					break;
				case LONG:
					con = matrixMapper.getSparseLongMatrixMultiDConstructor();
					break;
				case OBJECT:
					con = matrixMapper.getSparseObjectMatrixMultiDConstructor();
					break;
				case SHORT:
					con = matrixMapper.getSparseShortMatrixMultiDConstructor();
					break;
				case STRING:
					con = matrixMapper.getSparseStringMatrixMultiDConstructor();
					break;
				case BIGINTEGER:
					con = matrixMapper.getSparseBigIntegerMatrixMultiDConstructor();
					break;
				case BIGDECIMAL:
					con = matrixMapper.getSparseBigDecimalMatrixMultiDConstructor();
					break;
				default:
					throw new MatrixException("entry type not  supported: " + valueType);
				}
			}

			return (Matrix) con.newInstance(size);

		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

	public static Matrix zeros(long... size) throws MatrixException {
		return dense(size);
	}

	public static Matrix dense(long... size) throws MatrixException {
		try {
			Constructor<?> con = null;

			switch (size.length) {
			case 0:
				throw new MatrixException("Size not defined");
			case 1:
				throw new MatrixException("Size must be at least 2-dimensional");
			case 2:
				con = matrixMapper.getDenseDoubleMatrix2DConstructor();
				break;
			default:
				con = matrixMapper.getDenseDoubleMatrixMultiDConstructor();
			}

			return (Matrix) con.newInstance(size);

		} catch (Exception e) {
			throw new MatrixException("could not create matrix with size ["
					+ Coordinates.toString(size).replaceAll(",", "x") + "]", e);
		}
	}

	public static final Matrix linkToFile(FileFormat format, File file, Object... parameters)
			throws MatrixException, IOException {
		return LinkMatrix.toFile(format, file, parameters);
	}

	public static final Matrix importFromFile(String filename, Object... parameters)
			throws MatrixException, IOException {
		return ImportMatrix.fromFile(new File(filename), parameters);
	}

	public static final Matrix importFromFile(File file, Object... parameters)
			throws MatrixException, IOException {
		return ImportMatrix.fromFile(file, parameters);
	}

	public static final Matrix importFromFile(FileFormat format, String file, Object... parameters)
			throws MatrixException, IOException {
		return ImportMatrix.fromFile(format, new File(file), parameters);
	}

	public static final Matrix importFromFile(FileFormat format, File file, Object... parameters)
			throws MatrixException, IOException {
		return ImportMatrix.fromFile(format, file, parameters);
	}

	public static Matrix importFromClipboard(FileFormat format, Object... parameters)
			throws MatrixException {
		return ImportMatrix.fromClipboard(format, parameters);
	}

	// Wolfer's sunspot data 1700 - 1987
	public static final Matrix SUNSPOTDATASET() {
		return MatrixFactory.linkToArray(new double[] { 5, 11, 16, 23, 36, 58, 29, 20, 10, 8, 3, 0,
				0, 2, 11, 27, 47, 63, 60, 39, 28, 26, 22, 11, 21, 40, 78, 122, 103, 73, 47, 35, 11,
				5, 16, 34, 70, 81, 111, 101, 73, 40, 20, 16, 5, 11, 22, 40, 60, 80.9, 83.4, 47.7,
				47.8, 30.7, 12.2, 9.6, 10.2, 32.4, 47.6, 54, 62.9, 85.9, 61.2, 45.1, 36.4, 20.9,
				11.4, 37.8, 69.8, 106.1, 100.8, 81.6, 66.5, 34.8, 30.6, 7, 19.8, 92.5, 154.4,
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

	public static final Matrix emptyMatrix() {
		return EMPTYMATRIX;
	}

	public static MatrixMapper getMatrixMapper() {
		return matrixMapper;
	}

	public static void setMatrixMapper(MatrixMapper matrixMapper) {
		MatrixFactory.matrixMapper = matrixMapper;
	}

	public static Matrix repmat(Matrix matrix, long... count) {
		return new Repmat(matrix, count).calc(Ret.LINK);
	}

}
