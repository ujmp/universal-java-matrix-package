/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ujmp.core.Matrix.AnnotationTransfer;
import org.ujmp.core.Matrix.EntryType;
import org.ujmp.core.Matrix.Format;
import org.ujmp.core.doublecalculation.basic.Convert;
import org.ujmp.core.doublecalculation.entrywise.creators.Eye;
import org.ujmp.core.doublecalculation.entrywise.creators.Fill;
import org.ujmp.core.doublecalculation.entrywise.creators.Ones;
import org.ujmp.core.doublecalculation.entrywise.creators.Rand;
import org.ujmp.core.doublecalculation.entrywise.creators.Randn;
import org.ujmp.core.doublecalculation.general.misc.Dense2Sparse;
import org.ujmp.core.doublematrix.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.DefaultDenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.DenseFileMatrix2D;
import org.ujmp.core.doublematrix.DoubleMatrix;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.genericmatrix.SynchronizedGenericMatrix;
import org.ujmp.core.intmatrix.DefaultDenseIntMatrix2D;
import org.ujmp.core.io.ImportMatrix;
import org.ujmp.core.io.LinkMatrix;
import org.ujmp.core.listmatrix.DefaultListMatrix;
import org.ujmp.core.mapmatrix.DefaultMapMatrix;
import org.ujmp.core.mapmatrix.MapMatrix;
import org.ujmp.core.objectmatrix.AbstractDenseObjectMatrix2D;
import org.ujmp.core.objectmatrix.BufferedObjectMatrix;
import org.ujmp.core.objectmatrix.DefaultDenseObjectMatrix2D;
import org.ujmp.core.stringmatrix.AbstractDenseStringMatrix2D;
import org.ujmp.core.stringmatrix.DefaultDenseStringMatrix2D;
import org.ujmp.core.stringmatrix.FileListMatrix;
import org.ujmp.core.util.MathUtil;

/**
 * This class provides a factory for matrix generation. Use
 * <code>zeros(rows, columns)</code> or <code>sparse(rows, columns)</code>
 * to create empty matrices.
 * 
 * 
 * 
 * @author Holger Arndt
 */
public abstract class MatrixFactory {

	protected transient static final Logger logger = Logger
			.getLogger(MatrixFactory.class.getName());

	private static final Class<?>[] LONGARRAY = new Class<?>[] { new long[] {}.getClass() };

	public static final int ROW = Matrix.ROW;

	public static final int COLUMN = Matrix.COLUMN;

	public static final int Y = Matrix.Y;

	public static final int X = Matrix.X;

	public static final int Z = Matrix.Z;

	public static final int ALL = Matrix.ALL;

	public static final int NONE = Matrix.NONE;

	private static MapMatrix<Class<? extends Matrix>, String> matrixClasses = new DefaultMapMatrix<Class<? extends Matrix>, String>();

	static {
		findMatrixClasses();
	}

	private static Constructor<?> denseBooleanMatrix2DConstructor = null;

	private static Constructor<?> denseByteMatrix2DConstructor = null;

	private static Constructor<?> denseCharMatrix2DConstructor = null;

	private static Constructor<?> denseDateMatrix2DConstructor = null;

	private static Constructor<?> denseDoubleMatrix2DConstructor = null;

	private static Constructor<?> denseFloatMatrix2DConstructor = null;

	private static Constructor<?> denseIntMatrix2DConstructor = null;

	private static Constructor<?> denseLongMatrix2DConstructor = null;

	private static Constructor<?> denseShortMatrix2DConstructor = null;

	private static Constructor<?> denseObjectMatrix2DConstructor = null;

	private static Constructor<?> denseStringMatrix2DConstructor = null;

	private static Constructor<?> denseBooleanMatrix3DConstructor = null;

	private static Constructor<?> denseByteMatrix3DConstructor = null;

	private static Constructor<?> denseCharMatrix3DConstructor = null;

	private static Constructor<?> denseDateMatrix3DConstructor = null;

	private static Constructor<?> denseDoubleMatrix3DConstructor = null;

	private static Constructor<?> denseFloatMatrix3DConstructor = null;

	private static Constructor<?> denseIntMatrix3DConstructor = null;

	private static Constructor<?> denseLongMatrix3DConstructor = null;

	private static Constructor<?> denseShortMatrix3DConstructor = null;

	private static Constructor<?> denseObjectMatrix3DConstructor = null;

	private static Constructor<?> denseStringMatrix3DConstructor = null;

	private static Constructor<?> denseBooleanMatrixMultiDConstructor = null;

	private static Constructor<?> denseByteMatrixMultiDConstructor = null;

	private static Constructor<?> denseCharMatrixMultiDConstructor = null;

	private static Constructor<?> denseDateMatrixMultiDConstructor = null;

	private static Constructor<?> denseDoubleMatrixMultiDConstructor = null;

	private static Constructor<?> denseFloatMatrixMultiDConstructor = null;

	private static Constructor<?> denseIntMatrixMultiDConstructor = null;

	private static Constructor<?> denseLongMatrixMultiDConstructor = null;

	private static Constructor<?> denseShortMatrixMultiDConstructor = null;

	private static Constructor<?> denseObjectMatrixMultiDConstructor = null;

	private static Constructor<?> denseStringMatrixMultiDConstructor = null;

	private static Constructor<?> sparseBooleanMatrix2DConstructor = null;

	private static Constructor<?> sparseByteMatrix2DConstructor = null;

	private static Constructor<?> sparseCharMatrix2DConstructor = null;

	private static Constructor<?> sparseDateMatrix2DConstructor = null;

	private static Constructor<?> sparseDoubleMatrix2DConstructor = null;

	private static Constructor<?> sparseFloatMatrix2DConstructor = null;

	private static Constructor<?> sparseIntMatrix2DConstructor = null;

	private static Constructor<?> sparseLongMatrix2DConstructor = null;

	private static Constructor<?> sparseShortMatrix2DConstructor = null;

	private static Constructor<?> sparseObjectMatrix2DConstructor = null;

	private static Constructor<?> sparseStringMatrix2DConstructor = null;

	private static Constructor<?> sparseBooleanMatrix3DConstructor = null;

	private static Constructor<?> sparseByteMatrix3DConstructor = null;

	private static Constructor<?> sparseCharMatrix3DConstructor = null;

	private static Constructor<?> sparseDateMatrix3DConstructor = null;

	private static Constructor<?> sparseDoubleMatrix3DConstructor = null;

	private static Constructor<?> sparseFloatMatrix3DConstructor = null;

	private static Constructor<?> sparseIntMatrix3DConstructor = null;

	private static Constructor<?> sparseLongMatrix3DConstructor = null;

	private static Constructor<?> sparseShortMatrix3DConstructor = null;

	private static Constructor<?> sparseObjectMatrix3DConstructor = null;

	private static Constructor<?> sparseStringMatrix3DConstructor = null;

	private static Constructor<?> sparseBooleanMatrixMultiDConstructor = null;

	private static Constructor<?> sparseByteMatrixMultiDConstructor = null;

	private static Constructor<?> sparseCharMatrixMultiDConstructor = null;

	private static Constructor<?> sparseDateMatrixMultiDConstructor = null;

	private static Constructor<?> sparseDoubleMatrixMultiDConstructor = null;

	private static Constructor<?> sparseFloatMatrixMultiDConstructor = null;

	private static Constructor<?> sparseIntMatrixMultiDConstructor = null;

	private static Constructor<?> sparseLongMatrixMultiDConstructor = null;

	private static Constructor<?> sparseShortMatrixMultiDConstructor = null;

	private static Constructor<?> sparseObjectMatrixMultiDConstructor = null;

	private static Constructor<?> sparseStringMatrixMultiDConstructor = null;

	private static void findMatrixClasses() {
		try {
			setDenseDoubleMatrix2DClassName("org.ujmp.mtj.MTJDenseDoubleMatrix2D");
			setDenseObjectMatrix2DClassName(DefaultDenseObjectMatrix2D.class.getName());
			setDenseStringMatrix2DClassName(DefaultDenseStringMatrix2D.class.getName());
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Matrix classes not found, this should never happen");
		}
	}

	public static void setDenseDoubleMatrix2DClassName(String className) throws Exception {
		matrixClasses.put(AbstractDenseDoubleMatrix2D.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = DefaultDenseDoubleMatrix2D.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		denseDoubleMatrix2DConstructor = matrixClass.getConstructor(LONGARRAY);
	}

	public static void setDenseStringMatrix2DClassName(String className) throws Exception {
		matrixClasses.put(AbstractDenseStringMatrix2D.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = DefaultDenseStringMatrix2D.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		denseStringMatrix2DConstructor = matrixClass.getConstructor(LONGARRAY);
	}

	public static void setDenseObjectMatrix2DClassName(String className) throws Exception {
		matrixClasses.put(AbstractDenseObjectMatrix2D.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = DefaultDenseObjectMatrix2D.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		denseObjectMatrix2DConstructor = matrixClass.getConstructor(LONGARRAY);
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
		Matrix result = MatrixFactory.copyFromMatrix(AnnotationTransfer.COPY, matrices[0]);
		for (int i = 1; i < matrices.length; i++) {
			result = result.append(dimension, matrices[i]);
		}
		return result;
	}

	public static final Matrix concat(int dimension, Collection<Matrix> matrices)
			throws MatrixException {
		List<Matrix> list = new ArrayList<Matrix>(matrices);
		Matrix result = MatrixFactory.copyFromMatrix(AnnotationTransfer.COPY, list.get(0));
		for (int i = 1; i < matrices.size(); i++) {
			result = result.append(dimension, list.get(i));
		}
		return result;
	}

	public static final Matrix importFromArray(double[]... values) {
		int rows = values.length;
		int columns = 0;
		for (int i = values.length - 1; i >= 0; i--) {
			columns = Math.max(columns, values[i].length);
		}
		Matrix m = MatrixFactory.zeros(rows, columns);
		for (int i = rows - 1; i >= 0; i--) {
			for (int j = values[i].length - 1; j >= 0; j--) {
				m.setAsDouble(values[i][j], i, j);
			}
		}
		return m;
	}

	public static final DefaultDenseDoubleMatrix2D linkToArray(double[]... values) {
		return new DefaultDenseDoubleMatrix2D(values);
	}

	public static final DefaultDenseDoubleMatrix2D linkToArray(double... values) {
		return new DefaultDenseDoubleMatrix2D(values);
	}

	public static final DefaultDenseIntMatrix2D linkToArray(int[]... values) {
		return new DefaultDenseIntMatrix2D(values);
	}

	public static final DefaultDenseDoubleMatrix2D linkToArray(int... values) {
		double[] doubleValues = MathUtil.toDoubleArray(values);
		return new DefaultDenseDoubleMatrix2D(doubleValues);
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

	public static final Matrix randn(EntryType entryType, long... size) throws MatrixException {
		return Randn.calc(entryType, size);
	}

	public static final Matrix rand(long... size) throws MatrixException {
		return Rand.calc(size);
	}

	public static final Matrix rand(EntryType entryType, long... size) throws MatrixException {
		return Rand.calc(entryType, size);
	}

	public static final Matrix copyFromMatrix(EntryType newEntryType,
			AnnotationTransfer annotationTransfer, Matrix matrix) throws MatrixException {
		return Convert.calcNew(newEntryType, annotationTransfer, matrix);
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

	public static final Matrix sharedMatrix(Matrix m) throws MatrixException {
		try {
			Class<?> c = Class.forName("org.jdmp.jgroups.ReplicatedSparseMatrix");
			Constructor<?> constr = c.getConstructor(new Class[] { Matrix.class });
			Matrix matrix = (Matrix) constr.newInstance(new Object[] { m });
			return matrix;
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

	public static final DefaultDenseDoubleMatrix2D linkToValue(double value) {
		return new DefaultDenseDoubleMatrix2D(new double[][] { { value } });
	}

	public static Matrix zeros(EntryType entryType, long... size) throws MatrixException {
		try {
			switch (size.length) {
			case 0:
				throw new MatrixException("Size not defined");
			case 1:
				throw new MatrixException("Size must be at least 2-dimensional");
			case 2:
				switch (entryType) {
				case DOUBLE:
					return (Matrix) denseDoubleMatrix2DConstructor.newInstance(size);
				case OBJECT:
					return (Matrix) denseObjectMatrix2DConstructor.newInstance(size);
				case STRING:
					return (Matrix) denseStringMatrix2DConstructor.newInstance(size);
				case GENERIC:
					throw new MatrixException(
							"use new DefaultDenseGenericMatrix<T>() to create generic matrices.");
				default:
					throw new MatrixException("entry type not yet supported: " + entryType);
				}
			case 3:
				switch (entryType) {
				case DOUBLE:
					return (Matrix) denseDoubleMatrix3DConstructor.newInstance(size);
				default:
					throw new MatrixException("entry type not yet supported: " + entryType);
				}
			default:
				switch (entryType) {
				case DOUBLE:
					return (Matrix) denseDoubleMatrixMultiDConstructor.newInstance(size);
				default:
					throw new MatrixException("entry type not yet supported: " + entryType);
				}
			}
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

	public static Matrix ones(EntryType entryType, long... size) throws MatrixException {
		return Ones.calc(entryType, size);
	}

	public static Matrix eye(long... size) throws MatrixException {
		return Eye.calc(size);
	}

	public static Matrix eye(EntryType entryType, long... size) throws MatrixException {
		return Eye.calc(entryType, size);
	}

	public static final Matrix createVectorForClass(int classID, int classCount)
			throws MatrixException {
		Matrix matrix = MatrixFactory.zeros(classCount, 1);
		matrix.setAsDouble(1.0, classID, 0);
		return matrix;
	}

	public static final DefaultDenseObjectMatrix2D linkToArray(Object[]... valueList) {
		return new DefaultDenseObjectMatrix2D(valueList);
	}

	public static final FileListMatrix linkToDir(String dir) {
		return new FileListMatrix(dir);
	}

	public static final DefaultMapMatrix<?, ?> linkToMap(Map<?, ?> map) {
		return new DefaultMapMatrix(map);
	}

	public static final DefaultListMatrix<Object> linkToArray(Object... objects) {
		return new DefaultListMatrix<Object>(objects);
	}

	public static final DefaultListMatrix<?> linkToCollection(Collection<?> list) {
		return new DefaultListMatrix<Object>(list);
	}

	public static Matrix importFromStream(Format format, InputStream stream, Object... parameters)
			throws MatrixException, IOException {
		return ImportMatrix.fromStream(format, stream, parameters);
	}

	public static Matrix importFromString(Format format, String string, Object... parameters)
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
	public static final SynchronizedGenericMatrix<?> synchronizedMatrix(Matrix matrix) {
		return new SynchronizedGenericMatrix(matrix);
	}

	public static final DoubleMatrix linkToBinaryFile(String filename, int rowCount, int columnCount) {
		return new DenseFileMatrix2D(new File(filename), rowCount, columnCount);
	}

	public static final Matrix linkToJDBC(String url, String tablename, String username,
			String password) {
		try {
			Class<?> c = Class.forName("org.ujmp.jdbc.DenseODBCMatrix2D");
			Constructor<?> constr = c.getConstructor(new Class[] { String.class, String.class,
					String.class, String.class });
			Matrix odbcMatrix = (Matrix) constr.newInstance(new Object[] { url, tablename,
					username, password });
			return new BufferedObjectMatrix(odbcMatrix);
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

	public final static Matrix sparse(long... size) throws MatrixException {
		return sparse(EntryType.DOUBLE, size);
	}

	public static final Matrix sparse(Matrix indices) {
		return Dense2Sparse.calc(indices);
	}

	public final static Matrix sparse(EntryType entryType, long... size) throws MatrixException {
		switch (entryType) {
		default:
			throw new MatrixException("entry type not yet supported: " + entryType);
		}
	}

	public static Matrix zeros(long... size) throws MatrixException {
		return zeros(EntryType.DOUBLE, size);
	}

	public static final Matrix linkToFile(Format format, File file, Object... parameters)
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

	public static final Matrix importFromFile(Format format, String file, Object... parameters)
			throws MatrixException, IOException {
		return ImportMatrix.fromFile(format, new File(file), parameters);
	}

	public static final Matrix importFromFile(Format format, File file, Object... parameters)
			throws MatrixException, IOException {
		return ImportMatrix.fromFile(format, file, parameters);
	}

	public static Matrix importFromClipboard(Format format, Object... parameters)
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

}
