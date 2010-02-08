/*
 * Copyright (C) 2008-2010 by Holger Arndt
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

package org.ujmp.core.mapper;

import java.lang.reflect.Constructor;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ujmp.core.Matrix;
import org.ujmp.core.bigdecimalmatrix.impl.DefaultDenseBigDecimalMatrix2D;
import org.ujmp.core.bigdecimalmatrix.impl.DefaultSparseBigDecimalMatrix;
import org.ujmp.core.bigdecimalmatrix.stub.AbstractDenseBigDecimalMatrix;
import org.ujmp.core.bigdecimalmatrix.stub.AbstractDenseBigDecimalMatrix2D;
import org.ujmp.core.bigdecimalmatrix.stub.AbstractSparseBigDecimalMatrix;
import org.ujmp.core.bigdecimalmatrix.stub.AbstractSparseBigDecimalMatrix2D;
import org.ujmp.core.bigintegermatrix.impl.DefaultDenseBigIntegerMatrix2D;
import org.ujmp.core.bigintegermatrix.impl.DefaultSparseBigIntegerMatrix;
import org.ujmp.core.bigintegermatrix.stub.AbstractDenseBigIntegerMatrix;
import org.ujmp.core.bigintegermatrix.stub.AbstractDenseBigIntegerMatrix2D;
import org.ujmp.core.bigintegermatrix.stub.AbstractSparseBigIntegerMatrix;
import org.ujmp.core.bigintegermatrix.stub.AbstractSparseBigIntegerMatrix2D;
import org.ujmp.core.booleanmatrix.impl.DefaultDenseBooleanMatrix2D;
import org.ujmp.core.booleanmatrix.impl.DefaultSparseBooleanMatrix;
import org.ujmp.core.booleanmatrix.stub.AbstractDenseBooleanMatrix;
import org.ujmp.core.booleanmatrix.stub.AbstractDenseBooleanMatrix2D;
import org.ujmp.core.booleanmatrix.stub.AbstractSparseBooleanMatrix;
import org.ujmp.core.booleanmatrix.stub.AbstractSparseBooleanMatrix2D;
import org.ujmp.core.bytematrix.impl.ArrayDenseByteMatrix2D;
import org.ujmp.core.bytematrix.impl.DefaultSparseByteMatrix;
import org.ujmp.core.bytematrix.stub.AbstractDenseByteMatrix;
import org.ujmp.core.bytematrix.stub.AbstractDenseByteMatrix2D;
import org.ujmp.core.bytematrix.stub.AbstractSparseByteMatrix;
import org.ujmp.core.bytematrix.stub.AbstractSparseByteMatrix2D;
import org.ujmp.core.charmatrix.impl.ArrayDenseCharMatrix2D;
import org.ujmp.core.charmatrix.impl.DefaultSparseCharMatrix;
import org.ujmp.core.charmatrix.stub.AbstractDenseCharMatrix;
import org.ujmp.core.charmatrix.stub.AbstractDenseCharMatrix2D;
import org.ujmp.core.charmatrix.stub.AbstractSparseCharMatrix;
import org.ujmp.core.charmatrix.stub.AbstractSparseCharMatrix2D;
import org.ujmp.core.datematrix.impl.DefaultDenseDateMatrix2D;
import org.ujmp.core.datematrix.impl.DefaultSparseDateMatrix;
import org.ujmp.core.datematrix.stub.AbstractDenseDateMatrix;
import org.ujmp.core.datematrix.stub.AbstractDenseDateMatrix2D;
import org.ujmp.core.datematrix.stub.AbstractSparseDateMatrix;
import org.ujmp.core.datematrix.stub.AbstractSparseDateMatrix2D;
import org.ujmp.core.doublematrix.impl.DefaultDenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.impl.DefaultSparseDoubleMatrix;
import org.ujmp.core.doublematrix.stub.AbstractDenseDoubleMatrix;
import org.ujmp.core.doublematrix.stub.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.stub.AbstractSparseDoubleMatrix;
import org.ujmp.core.doublematrix.stub.AbstractSparseDoubleMatrix2D;
import org.ujmp.core.floatmatrix.impl.ArrayDenseFloatMatrix2D;
import org.ujmp.core.floatmatrix.impl.DefaultSparseFloatMatrix;
import org.ujmp.core.floatmatrix.stub.AbstractDenseFloatMatrix;
import org.ujmp.core.floatmatrix.stub.AbstractDenseFloatMatrix2D;
import org.ujmp.core.floatmatrix.stub.AbstractSparseFloatMatrix;
import org.ujmp.core.floatmatrix.stub.AbstractSparseFloatMatrix2D;
import org.ujmp.core.intmatrix.impl.DefaultDenseIntMatrix2D;
import org.ujmp.core.intmatrix.impl.DefaultSparseIntMatrix;
import org.ujmp.core.intmatrix.stub.AbstractDenseIntMatrix;
import org.ujmp.core.intmatrix.stub.AbstractDenseIntMatrix2D;
import org.ujmp.core.intmatrix.stub.AbstractSparseIntMatrix;
import org.ujmp.core.intmatrix.stub.AbstractSparseIntMatrix2D;
import org.ujmp.core.longmatrix.impl.DefaultDenseLongMatrix2D;
import org.ujmp.core.longmatrix.impl.DefaultSparseLongMatrix;
import org.ujmp.core.longmatrix.stub.AbstractDenseLongMatrix;
import org.ujmp.core.longmatrix.stub.AbstractDenseLongMatrix2D;
import org.ujmp.core.longmatrix.stub.AbstractSparseLongMatrix;
import org.ujmp.core.longmatrix.stub.AbstractSparseLongMatrix2D;
import org.ujmp.core.mapmatrix.DefaultMapMatrix;
import org.ujmp.core.mapmatrix.MapMatrix;
import org.ujmp.core.objectmatrix.impl.DefaultDenseObjectMatrix2D;
import org.ujmp.core.objectmatrix.impl.DefaultSparseObjectMatrix;
import org.ujmp.core.objectmatrix.stub.AbstractDenseObjectMatrix;
import org.ujmp.core.objectmatrix.stub.AbstractDenseObjectMatrix2D;
import org.ujmp.core.objectmatrix.stub.AbstractSparseObjectMatrix;
import org.ujmp.core.objectmatrix.stub.AbstractSparseObjectMatrix2D;
import org.ujmp.core.shortmatrix.impl.DefaultDenseShortMatrix2D;
import org.ujmp.core.shortmatrix.impl.DefaultSparseShortMatrix;
import org.ujmp.core.shortmatrix.stub.AbstractDenseShortMatrix;
import org.ujmp.core.shortmatrix.stub.AbstractDenseShortMatrix2D;
import org.ujmp.core.shortmatrix.stub.AbstractSparseShortMatrix;
import org.ujmp.core.shortmatrix.stub.AbstractSparseShortMatrix2D;
import org.ujmp.core.stringmatrix.impl.DefaultDenseStringMatrix2D;
import org.ujmp.core.stringmatrix.impl.DefaultSparseStringMatrix;
import org.ujmp.core.stringmatrix.stub.AbstractDenseStringMatrix;
import org.ujmp.core.stringmatrix.stub.AbstractDenseStringMatrix2D;
import org.ujmp.core.stringmatrix.stub.AbstractSparseStringMatrix;
import org.ujmp.core.stringmatrix.stub.AbstractSparseStringMatrix2D;

public class MatrixMapper implements ClassMapper {

	private transient static final Logger logger = Logger.getLogger(MatrixMapper.class.getName());

	private static final Class<?>[] LONGARRAY = new Class<?>[] { new long[] {}.getClass() };

	private final MapMatrix<Class<? extends Matrix>, String> matrixClasses = new DefaultMapMatrix<Class<? extends Matrix>, String>();

	private Constructor<?> denseBooleanMatrix2DConstructor = null;

	private Constructor<?> denseByteMatrix2DConstructor = null;

	private Constructor<?> denseCharMatrix2DConstructor = null;

	private Constructor<?> denseDateMatrix2DConstructor = null;

	private Constructor<?> denseDoubleMatrix2DConstructor = null;

	private Constructor<?> denseFloatMatrix2DConstructor = null;

	private Constructor<?> denseIntMatrix2DConstructor = null;

	private Constructor<?> denseLongMatrix2DConstructor = null;

	private Constructor<?> denseShortMatrix2DConstructor = null;

	private Constructor<?> denseObjectMatrix2DConstructor = null;

	private Constructor<?> denseStringMatrix2DConstructor = null;

	private Constructor<?> denseBooleanMatrixMultiDConstructor = null;

	private Constructor<?> denseByteMatrixMultiDConstructor = null;

	private Constructor<?> denseCharMatrixMultiDConstructor = null;

	private Constructor<?> denseDateMatrixMultiDConstructor = null;

	private Constructor<?> denseDoubleMatrixMultiDConstructor = null;

	private Constructor<?> denseFloatMatrixMultiDConstructor = null;

	private Constructor<?> denseIntMatrixMultiDConstructor = null;

	private Constructor<?> denseLongMatrixMultiDConstructor = null;

	private Constructor<?> denseShortMatrixMultiDConstructor = null;

	private Constructor<?> denseObjectMatrixMultiDConstructor = null;

	private Constructor<?> denseStringMatrixMultiDConstructor = null;

	private Constructor<?> denseBigIntegerMatrixMultiDConstructor = null;

	private Constructor<?> denseBigDecimalMatrixMultiDConstructor = null;

	private Constructor<?> sparseBooleanMatrix2DConstructor = null;

	private Constructor<?> sparseByteMatrix2DConstructor = null;

	private Constructor<?> sparseCharMatrix2DConstructor = null;

	private Constructor<?> sparseDateMatrix2DConstructor = null;

	private Constructor<?> sparseDoubleMatrix2DConstructor = null;

	private Constructor<?> sparseFloatMatrix2DConstructor = null;

	private Constructor<?> sparseIntMatrix2DConstructor = null;

	private Constructor<?> sparseLongMatrix2DConstructor = null;

	private Constructor<?> sparseShortMatrix2DConstructor = null;

	private Constructor<?> sparseObjectMatrix2DConstructor = null;

	private Constructor<?> sparseStringMatrix2DConstructor = null;

	private Constructor<?> sparseBooleanMatrixMultiDConstructor = null;

	private Constructor<?> sparseByteMatrixMultiDConstructor = null;

	private Constructor<?> sparseCharMatrixMultiDConstructor = null;

	private Constructor<?> sparseDateMatrixMultiDConstructor = null;

	private Constructor<?> sparseDoubleMatrixMultiDConstructor = null;

	private Constructor<?> sparseFloatMatrixMultiDConstructor = null;

	private Constructor<?> sparseIntMatrixMultiDConstructor = null;

	private Constructor<?> sparseLongMatrixMultiDConstructor = null;

	private Constructor<?> sparseShortMatrixMultiDConstructor = null;

	private Constructor<?> sparseObjectMatrixMultiDConstructor = null;

	private Constructor<?> sparseStringMatrixMultiDConstructor = null;

	private Constructor<?> sparseBigIntegerMatrixMultiDConstructor = null;

	private Constructor<?> sparseBigIntegerMatrix2DConstructor = null;

	private Constructor<?> denseBigIntegerMatrix2DConstructor = null;

	private Constructor<?> sparseBigDecimalMatrixMultiDConstructor = null;

	private Constructor<?> sparseBigDecimalMatrix2DConstructor = null;

	private Constructor<?> denseBigDecimalMatrix2DConstructor = null;

	private static MatrixMapper instance = null;

	public static MatrixMapper getInstance() {
		if (instance == null) {
			instance = new MatrixMapper();
		}
		return instance;
	}

	private MatrixMapper() {
		findMatrixClasses();
	}

	private void findMatrixClasses() {
		try {
			setDenseBooleanMatrix2DClassName(DefaultDenseBooleanMatrix2D.class.getName());
			setDenseBooleanMatrixMultiDClassName(DefaultSparseBooleanMatrix.class.getName());
			setSparseBooleanMatrix2DClassName(DefaultSparseBooleanMatrix.class.getName());
			setSparseBooleanMatrixMultiDClassName(DefaultSparseBooleanMatrix.class.getName());

			setDenseByteMatrix2DClassName(ArrayDenseByteMatrix2D.class.getName());
			setDenseByteMatrixMultiDClassName(DefaultSparseByteMatrix.class.getName());
			setSparseByteMatrix2DClassName(DefaultSparseByteMatrix.class.getName());
			setSparseByteMatrixMultiDClassName(DefaultSparseByteMatrix.class.getName());

			setDenseCharMatrix2DClassName(ArrayDenseCharMatrix2D.class.getName());
			setDenseCharMatrixMultiDClassName(DefaultSparseCharMatrix.class.getName());
			setSparseCharMatrix2DClassName(DefaultSparseCharMatrix.class.getName());
			setSparseCharMatrixMultiDClassName(DefaultSparseCharMatrix.class.getName());

			setDenseDateMatrix2DClassName(DefaultDenseDateMatrix2D.class.getName());
			setDenseDateMatrixMultiDClassName(DefaultSparseDateMatrix.class.getName());
			setSparseDateMatrix2DClassName(DefaultSparseDateMatrix.class.getName());
			setSparseDateMatrixMultiDClassName(DefaultSparseDateMatrix.class.getName());

			setDenseDoubleMatrix2DClassName(DefaultDenseDoubleMatrix2D.class.getName());
			setDenseDoubleMatrixMultiDClassName(DefaultSparseDoubleMatrix.class.getName());
			setSparseDoubleMatrix2DClassName(DefaultSparseDoubleMatrix.class.getName());
			setSparseDoubleMatrixMultiDClassName(DefaultSparseDoubleMatrix.class.getName());

			setDenseFloatMatrix2DClassName(ArrayDenseFloatMatrix2D.class.getName());
			setDenseFloatMatrixMultiDClassName(DefaultSparseFloatMatrix.class.getName());
			setSparseFloatMatrix2DClassName(DefaultSparseFloatMatrix.class.getName());
			setSparseFloatMatrixMultiDClassName(DefaultSparseFloatMatrix.class.getName());

			setDenseIntMatrix2DClassName(DefaultDenseIntMatrix2D.class.getName());
			setDenseIntMatrixMultiDClassName(DefaultSparseIntMatrix.class.getName());
			setSparseIntMatrix2DClassName(DefaultSparseIntMatrix.class.getName());
			setSparseIntMatrixMultiDClassName(DefaultSparseIntMatrix.class.getName());

			setDenseLongMatrix2DClassName(DefaultDenseLongMatrix2D.class.getName());
			setDenseLongMatrixMultiDClassName(DefaultSparseLongMatrix.class.getName());
			setSparseLongMatrix2DClassName(DefaultSparseLongMatrix.class.getName());
			setSparseLongMatrixMultiDClassName(DefaultSparseLongMatrix.class.getName());

			setDenseObjectMatrix2DClassName(DefaultDenseObjectMatrix2D.class.getName());
			setDenseObjectMatrixMultiDClassName(DefaultSparseObjectMatrix.class.getName());
			setSparseObjectMatrix2DClassName(DefaultSparseObjectMatrix.class.getName());
			setSparseObjectMatrixMultiDClassName(DefaultSparseObjectMatrix.class.getName());

			setDenseShortMatrix2DClassName(DefaultDenseShortMatrix2D.class.getName());
			setDenseShortMatrixMultiDClassName(DefaultSparseShortMatrix.class.getName());
			setSparseShortMatrix2DClassName(DefaultSparseShortMatrix.class.getName());
			setSparseShortMatrixMultiDClassName(DefaultSparseShortMatrix.class.getName());

			setDenseStringMatrix2DClassName(DefaultDenseStringMatrix2D.class.getName());
			setDenseStringMatrixMultiDClassName(DefaultSparseStringMatrix.class.getName());
			setSparseStringMatrix2DClassName(DefaultSparseStringMatrix.class.getName());
			setSparseStringMatrixMultiDClassName(DefaultSparseStringMatrix.class.getName());

			setDenseBigIntegerMatrix2DClassName(DefaultDenseBigIntegerMatrix2D.class.getName());
			setDenseBigIntegerMatrixMultiDClassName(DefaultSparseBigIntegerMatrix.class.getName());
			setSparseBigIntegerMatrix2DClassName(DefaultSparseBigIntegerMatrix.class.getName());
			setSparseBigIntegerMatrixMultiDClassName(DefaultSparseBigIntegerMatrix.class.getName());

			setDenseBigDecimalMatrix2DClassName(DefaultDenseBigDecimalMatrix2D.class.getName());
			setDenseBigDecimalMatrixMultiDClassName(DefaultSparseBigDecimalMatrix.class.getName());
			setSparseBigDecimalMatrix2DClassName(DefaultSparseBigDecimalMatrix.class.getName());
			setSparseBigDecimalMatrixMultiDClassName(DefaultSparseBigDecimalMatrix.class.getName());

		} catch (Exception e) {
			logger.log(Level.SEVERE, "Default Matrix classes not found, this should never happen",
					e);
		}
	}

	public void setDenseDoubleMatrix2DClassName(String className) throws Exception {
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

	public void setDenseDoubleMatrixMultiDClassName(String className) throws Exception {
		matrixClasses.put(AbstractDenseDoubleMatrix.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = DefaultSparseDoubleMatrix.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		denseDoubleMatrixMultiDConstructor = matrixClass.getConstructor(LONGARRAY);
	}

	public void setSparseDoubleMatrix2DClassName(String className) throws Exception {
		matrixClasses.put(AbstractSparseDoubleMatrix2D.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = DefaultSparseDoubleMatrix.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		sparseDoubleMatrix2DConstructor = matrixClass.getConstructor(LONGARRAY);
	}

	public void setSparseDoubleMatrixMultiDClassName(String className) throws Exception {
		matrixClasses.put(AbstractSparseDoubleMatrix.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = DefaultSparseDoubleMatrix.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		sparseDoubleMatrixMultiDConstructor = matrixClass.getConstructor(LONGARRAY);
	}

	public void setDenseBooleanMatrix2DClassName(String className) throws Exception {
		matrixClasses.put(AbstractDenseBooleanMatrix2D.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = DefaultDenseBooleanMatrix2D.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		denseBooleanMatrix2DConstructor = matrixClass.getConstructor(LONGARRAY);
	}

	public void setDenseBooleanMatrixMultiDClassName(String className) throws Exception {
		matrixClasses.put(AbstractDenseBooleanMatrix.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = DefaultSparseBooleanMatrix.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		denseBooleanMatrixMultiDConstructor = matrixClass.getConstructor(LONGARRAY);
	}

	public void setSparseBooleanMatrix2DClassName(String className) throws Exception {
		matrixClasses.put(AbstractSparseBooleanMatrix2D.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = DefaultSparseBooleanMatrix.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		sparseBooleanMatrix2DConstructor = matrixClass.getConstructor(LONGARRAY);
	}

	public void setSparseBooleanMatrixMultiDClassName(String className) throws Exception {
		matrixClasses.put(AbstractSparseBooleanMatrix.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = DefaultSparseBooleanMatrix.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		sparseBooleanMatrixMultiDConstructor = matrixClass.getConstructor(LONGARRAY);
	}

	public void setDenseFloatMatrix2DClassName(String className) throws Exception {
		matrixClasses.put(AbstractDenseFloatMatrix2D.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = ArrayDenseFloatMatrix2D.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		denseFloatMatrix2DConstructor = matrixClass.getConstructor(LONGARRAY);
	}

	public void setDenseFloatMatrixMultiDClassName(String className) throws Exception {
		matrixClasses.put(AbstractDenseFloatMatrix.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = DefaultSparseFloatMatrix.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		denseFloatMatrixMultiDConstructor = matrixClass.getConstructor(LONGARRAY);
	}

	public void setSparseFloatMatrix2DClassName(String className) throws Exception {
		matrixClasses.put(AbstractSparseFloatMatrix2D.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = DefaultSparseFloatMatrix.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		sparseFloatMatrix2DConstructor = matrixClass.getConstructor(LONGARRAY);
	}

	public void setSparseFloatMatrixMultiDClassName(String className) throws Exception {
		matrixClasses.put(AbstractSparseFloatMatrix.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = DefaultSparseFloatMatrix.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		sparseFloatMatrixMultiDConstructor = matrixClass.getConstructor(LONGARRAY);
	}

	public void setDenseIntMatrix2DClassName(String className) throws Exception {
		matrixClasses.put(AbstractDenseIntMatrix2D.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = DefaultDenseIntMatrix2D.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		denseIntMatrix2DConstructor = matrixClass.getConstructor(LONGARRAY);
	}

	public void setDenseIntMatrixMultiDClassName(String className) throws Exception {
		matrixClasses.put(AbstractDenseIntMatrix.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = DefaultSparseIntMatrix.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		denseIntMatrixMultiDConstructor = matrixClass.getConstructor(LONGARRAY);
	}

	public void setSparseIntMatrix2DClassName(String className) throws Exception {
		matrixClasses.put(AbstractSparseIntMatrix2D.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = DefaultSparseIntMatrix.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		sparseIntMatrix2DConstructor = matrixClass.getConstructor(LONGARRAY);
	}

	public void setSparseIntMatrixMultiDClassName(String className) throws Exception {
		matrixClasses.put(AbstractSparseIntMatrix.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = DefaultSparseIntMatrix.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		sparseIntMatrixMultiDConstructor = matrixClass.getConstructor(LONGARRAY);
	}

	public void setDenseShortMatrix2DClassName(String className) throws Exception {
		matrixClasses.put(AbstractDenseShortMatrix2D.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = DefaultDenseShortMatrix2D.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		denseShortMatrix2DConstructor = matrixClass.getConstructor(LONGARRAY);
	}

	public void setDenseShortMatrixMultiDClassName(String className) throws Exception {
		matrixClasses.put(AbstractDenseShortMatrix.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = DefaultSparseShortMatrix.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		denseShortMatrixMultiDConstructor = matrixClass.getConstructor(LONGARRAY);
	}

	public void setSparseShortMatrix2DClassName(String className) throws Exception {
		matrixClasses.put(AbstractSparseShortMatrix2D.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = DefaultSparseShortMatrix.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		sparseShortMatrix2DConstructor = matrixClass.getConstructor(LONGARRAY);
	}

	public void setSparseShortMatrixMultiDClassName(String className) throws Exception {
		matrixClasses.put(AbstractSparseShortMatrix.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = DefaultSparseShortMatrix.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		sparseShortMatrixMultiDConstructor = matrixClass.getConstructor(LONGARRAY);
	}

	public void setDenseLongMatrix2DClassName(String className) throws Exception {
		matrixClasses.put(AbstractDenseLongMatrix2D.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = DefaultDenseLongMatrix2D.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		denseLongMatrix2DConstructor = matrixClass.getConstructor(LONGARRAY);
	}

	public void setDenseLongMatrixMultiDClassName(String className) throws Exception {
		matrixClasses.put(AbstractDenseLongMatrix.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = DefaultSparseLongMatrix.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		denseLongMatrixMultiDConstructor = matrixClass.getConstructor(LONGARRAY);
	}

	public void setSparseLongMatrix2DClassName(String className) throws Exception {
		matrixClasses.put(AbstractSparseLongMatrix2D.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = DefaultSparseLongMatrix.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		sparseLongMatrix2DConstructor = matrixClass.getConstructor(LONGARRAY);
	}

	public void setSparseLongMatrixMultiDClassName(String className) throws Exception {
		matrixClasses.put(AbstractSparseLongMatrix.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = DefaultSparseLongMatrix.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		sparseLongMatrixMultiDConstructor = matrixClass.getConstructor(LONGARRAY);
	}

	public void setDenseByteMatrix2DClassName(String className) throws Exception {
		matrixClasses.put(AbstractDenseByteMatrix2D.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = ArrayDenseByteMatrix2D.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		denseByteMatrix2DConstructor = matrixClass.getConstructor(LONGARRAY);
	}

	public void setDenseByteMatrixMultiDClassName(String className) throws Exception {
		matrixClasses.put(AbstractDenseByteMatrix.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = DefaultSparseByteMatrix.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		denseByteMatrixMultiDConstructor = matrixClass.getConstructor(LONGARRAY);
	}

	public void setSparseByteMatrix2DClassName(String className) throws Exception {
		matrixClasses.put(AbstractSparseByteMatrix2D.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = DefaultSparseByteMatrix.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		sparseByteMatrix2DConstructor = matrixClass.getConstructor(LONGARRAY);
	}

	public void setSparseByteMatrixMultiDClassName(String className) throws Exception {
		matrixClasses.put(AbstractSparseByteMatrix.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = DefaultSparseByteMatrix.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		sparseByteMatrixMultiDConstructor = matrixClass.getConstructor(LONGARRAY);
	}

	public void setDenseCharMatrix2DClassName(String className) throws Exception {
		matrixClasses.put(AbstractDenseCharMatrix2D.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = ArrayDenseCharMatrix2D.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		denseCharMatrix2DConstructor = matrixClass.getConstructor(LONGARRAY);
	}

	public void setDenseCharMatrixMultiDClassName(String className) throws Exception {
		matrixClasses.put(AbstractDenseCharMatrix.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = DefaultSparseCharMatrix.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		denseCharMatrixMultiDConstructor = matrixClass.getConstructor(LONGARRAY);
	}

	public void setSparseCharMatrix2DClassName(String className) throws Exception {
		matrixClasses.put(AbstractSparseCharMatrix2D.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = DefaultSparseCharMatrix.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		sparseCharMatrix2DConstructor = matrixClass.getConstructor(LONGARRAY);
	}

	public void setSparseCharMatrixMultiDClassName(String className) throws Exception {
		matrixClasses.put(AbstractSparseCharMatrix.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = DefaultSparseCharMatrix.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		sparseCharMatrixMultiDConstructor = matrixClass.getConstructor(LONGARRAY);
	}

	public void setDenseDateMatrix2DClassName(String className) throws Exception {
		matrixClasses.put(AbstractDenseDateMatrix2D.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = DefaultDenseDateMatrix2D.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		denseDateMatrix2DConstructor = matrixClass.getConstructor(LONGARRAY);
	}

	public void setDenseDateMatrixMultiDClassName(String className) throws Exception {
		matrixClasses.put(AbstractDenseDateMatrix.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = DefaultSparseDateMatrix.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		denseDateMatrixMultiDConstructor = matrixClass.getConstructor(LONGARRAY);
	}

	public void setSparseDateMatrix2DClassName(String className) throws Exception {
		matrixClasses.put(AbstractSparseDateMatrix2D.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = DefaultSparseDateMatrix.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		sparseDateMatrix2DConstructor = matrixClass.getConstructor(LONGARRAY);
	}

	public void setSparseDateMatrixMultiDClassName(String className) throws Exception {
		matrixClasses.put(AbstractSparseDateMatrix.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = DefaultSparseDateMatrix.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		sparseDateMatrixMultiDConstructor = matrixClass.getConstructor(LONGARRAY);
	}

	public void setDenseStringMatrix2DClassName(String className) throws Exception {
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

	public void setDenseBigIntegerMatrix2DClassName(String className) throws Exception {
		matrixClasses.put(AbstractDenseBigIntegerMatrix2D.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = DefaultDenseBigIntegerMatrix2D.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		denseBigIntegerMatrix2DConstructor = matrixClass.getConstructor(LONGARRAY);
	}

	public void setDenseBigDecimalMatrix2DClassName(String className) throws Exception {
		matrixClasses.put(AbstractDenseBigDecimalMatrix2D.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = DefaultDenseBigDecimalMatrix2D.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		denseBigDecimalMatrix2DConstructor = matrixClass.getConstructor(LONGARRAY);
	}

	public void setDenseStringMatrixMultiDClassName(String className) throws Exception {
		matrixClasses.put(AbstractDenseStringMatrix.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = DefaultSparseStringMatrix.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		denseStringMatrixMultiDConstructor = matrixClass.getConstructor(LONGARRAY);
	}

	public void setDenseBigIntegerMatrixMultiDClassName(String className) throws Exception {
		matrixClasses.put(AbstractDenseBigIntegerMatrix.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = DefaultSparseBigIntegerMatrix.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		denseBigIntegerMatrixMultiDConstructor = matrixClass.getConstructor(LONGARRAY);
	}

	public void setDenseBigDecimalMatrixMultiDClassName(String className) throws Exception {
		matrixClasses.put(AbstractDenseBigDecimalMatrix.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = DefaultSparseBigDecimalMatrix.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		denseBigDecimalMatrixMultiDConstructor = matrixClass.getConstructor(LONGARRAY);
	}

	public void setSparseStringMatrix2DClassName(String className) throws Exception {
		matrixClasses.put(AbstractSparseStringMatrix2D.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = DefaultSparseStringMatrix.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		sparseStringMatrix2DConstructor = matrixClass.getConstructor(LONGARRAY);
	}

	public void setSparseBigIntegerMatrix2DClassName(String className) throws Exception {
		matrixClasses.put(AbstractSparseBigIntegerMatrix2D.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = DefaultSparseBigIntegerMatrix.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		sparseBigIntegerMatrix2DConstructor = matrixClass.getConstructor(LONGARRAY);
	}

	public void setSparseBigDecimalMatrix2DClassName(String className) throws Exception {
		matrixClasses.put(AbstractSparseBigDecimalMatrix2D.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = DefaultSparseBigDecimalMatrix.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		sparseBigDecimalMatrix2DConstructor = matrixClass.getConstructor(LONGARRAY);
	}

	public void setSparseStringMatrixMultiDClassName(String className) throws Exception {
		matrixClasses.put(AbstractSparseStringMatrix.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = DefaultSparseStringMatrix.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		sparseStringMatrixMultiDConstructor = matrixClass.getConstructor(LONGARRAY);
	}

	public void setSparseBigIntegerMatrixMultiDClassName(String className) throws Exception {
		matrixClasses.put(AbstractSparseBigIntegerMatrix.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = DefaultSparseBigIntegerMatrix.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		sparseBigIntegerMatrixMultiDConstructor = matrixClass.getConstructor(LONGARRAY);
	}

	public void setSparseBigDecimalMatrixMultiDClassName(String className) throws Exception {
		matrixClasses.put(AbstractSparseBigDecimalMatrix.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = DefaultSparseBigDecimalMatrix.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		sparseBigDecimalMatrixMultiDConstructor = matrixClass.getConstructor(LONGARRAY);
	}

	public void setDenseObjectMatrix2DClassName(String className) throws Exception {
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

	public void setDenseObjectMatrixMultiDClassName(String className) throws Exception {
		matrixClasses.put(AbstractDenseObjectMatrix.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = DefaultSparseObjectMatrix.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		denseObjectMatrixMultiDConstructor = matrixClass.getConstructor(LONGARRAY);
	}

	public void setSparseObjectMatrix2DClassName(String className) throws Exception {
		matrixClasses.put(AbstractSparseObjectMatrix2D.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = DefaultSparseObjectMatrix.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		sparseObjectMatrix2DConstructor = matrixClass.getConstructor(LONGARRAY);
	}

	public void setSparseObjectMatrixMultiDClassName(String className) throws Exception {
		matrixClasses.put(AbstractSparseObjectMatrix.class, className);
		Class<?> matrixClass = null;
		try {
			matrixClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			matrixClass = DefaultSparseObjectMatrix.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + matrixClass
					+ " instead.");
		}
		sparseObjectMatrixMultiDConstructor = matrixClass.getConstructor(LONGARRAY);
	}

	public MapMatrix<Class<? extends Matrix>, String> getMatrixClasses() {
		return matrixClasses;
	}

	public Constructor<?> getDenseBooleanMatrix2DConstructor() {
		return denseBooleanMatrix2DConstructor;
	}

	public Constructor<?> getDenseByteMatrix2DConstructor() {
		return denseByteMatrix2DConstructor;
	}

	public Constructor<?> getDenseCharMatrix2DConstructor() {
		return denseCharMatrix2DConstructor;
	}

	public Constructor<?> getDenseDateMatrix2DConstructor() {
		return denseDateMatrix2DConstructor;
	}

	public Constructor<?> getDenseDoubleMatrix2DConstructor() {
		return denseDoubleMatrix2DConstructor;
	}

	public Constructor<?> getDenseFloatMatrix2DConstructor() {
		return denseFloatMatrix2DConstructor;
	}

	public Constructor<?> getDenseIntMatrix2DConstructor() {
		return denseIntMatrix2DConstructor;
	}

	public Constructor<?> getDenseLongMatrix2DConstructor() {
		return denseLongMatrix2DConstructor;
	}

	public Constructor<?> getDenseShortMatrix2DConstructor() {
		return denseShortMatrix2DConstructor;
	}

	public Constructor<?> getDenseObjectMatrix2DConstructor() {
		return denseObjectMatrix2DConstructor;
	}

	public Constructor<?> getDenseStringMatrix2DConstructor() {
		return denseStringMatrix2DConstructor;
	}

	public Constructor<?> getDenseBooleanMatrixMultiDConstructor() {
		return denseBooleanMatrixMultiDConstructor;
	}

	public Constructor<?> getDenseByteMatrixMultiDConstructor() {
		return denseByteMatrixMultiDConstructor;
	}

	public Constructor<?> getDenseCharMatrixMultiDConstructor() {
		return denseCharMatrixMultiDConstructor;
	}

	public Constructor<?> getDenseDateMatrixMultiDConstructor() {
		return denseDateMatrixMultiDConstructor;
	}

	public Constructor<?> getDenseDoubleMatrixMultiDConstructor() {
		return denseDoubleMatrixMultiDConstructor;
	}

	public Constructor<?> getDenseFloatMatrixMultiDConstructor() {
		return denseFloatMatrixMultiDConstructor;
	}

	public Constructor<?> getDenseIntMatrixMultiDConstructor() {
		return denseIntMatrixMultiDConstructor;
	}

	public Constructor<?> getDenseLongMatrixMultiDConstructor() {
		return denseLongMatrixMultiDConstructor;
	}

	public Constructor<?> getDenseShortMatrixMultiDConstructor() {
		return denseShortMatrixMultiDConstructor;
	}

	public Constructor<?> getDenseObjectMatrixMultiDConstructor() {
		return denseObjectMatrixMultiDConstructor;
	}

	public Constructor<?> getDenseStringMatrixMultiDConstructor() {
		return denseStringMatrixMultiDConstructor;
	}

	public Constructor<?> getDenseBigIntegerMatrixMultiDConstructor() {
		return denseBigIntegerMatrixMultiDConstructor;
	}

	public Constructor<?> getDenseBigDecimalMatrixMultiDConstructor() {
		return denseBigDecimalMatrixMultiDConstructor;
	}

	public Constructor<?> getSparseBooleanMatrix2DConstructor() {
		return sparseBooleanMatrix2DConstructor;
	}

	public Constructor<?> getSparseByteMatrix2DConstructor() {
		return sparseByteMatrix2DConstructor;
	}

	public Constructor<?> getSparseCharMatrix2DConstructor() {
		return sparseCharMatrix2DConstructor;
	}

	public Constructor<?> getSparseDateMatrix2DConstructor() {
		return sparseDateMatrix2DConstructor;
	}

	public Constructor<?> getSparseDoubleMatrix2DConstructor() {
		return sparseDoubleMatrix2DConstructor;
	}

	public Constructor<?> getSparseFloatMatrix2DConstructor() {
		return sparseFloatMatrix2DConstructor;
	}

	public Constructor<?> getSparseIntMatrix2DConstructor() {
		return sparseIntMatrix2DConstructor;
	}

	public Constructor<?> getSparseLongMatrix2DConstructor() {
		return sparseLongMatrix2DConstructor;
	}

	public Constructor<?> getSparseShortMatrix2DConstructor() {
		return sparseShortMatrix2DConstructor;
	}

	public Constructor<?> getSparseObjectMatrix2DConstructor() {
		return sparseObjectMatrix2DConstructor;
	}

	public Constructor<?> getSparseStringMatrix2DConstructor() {
		return sparseStringMatrix2DConstructor;
	}

	public Constructor<?> getSparseBooleanMatrixMultiDConstructor() {
		return sparseBooleanMatrixMultiDConstructor;
	}

	public Constructor<?> getSparseByteMatrixMultiDConstructor() {
		return sparseByteMatrixMultiDConstructor;
	}

	public Constructor<?> getSparseCharMatrixMultiDConstructor() {
		return sparseCharMatrixMultiDConstructor;
	}

	public Constructor<?> getSparseDateMatrixMultiDConstructor() {
		return sparseDateMatrixMultiDConstructor;
	}

	public Constructor<?> getSparseDoubleMatrixMultiDConstructor() {
		return sparseDoubleMatrixMultiDConstructor;
	}

	public Constructor<?> getSparseFloatMatrixMultiDConstructor() {
		return sparseFloatMatrixMultiDConstructor;
	}

	public Constructor<?> getSparseIntMatrixMultiDConstructor() {
		return sparseIntMatrixMultiDConstructor;
	}

	public Constructor<?> getSparseLongMatrixMultiDConstructor() {
		return sparseLongMatrixMultiDConstructor;
	}

	public Constructor<?> getSparseShortMatrixMultiDConstructor() {
		return sparseShortMatrixMultiDConstructor;
	}

	public Constructor<?> getSparseObjectMatrixMultiDConstructor() {
		return sparseObjectMatrixMultiDConstructor;
	}

	public Constructor<?> getSparseStringMatrixMultiDConstructor() {
		return sparseStringMatrixMultiDConstructor;
	}

	public Constructor<?> getSparseBigIntegerMatrixMultiDConstructor() {
		return sparseBigIntegerMatrixMultiDConstructor;
	}

	public Constructor<?> getSparseBigDecimalMatrixMultiDConstructor() {
		return sparseBigDecimalMatrixMultiDConstructor;
	}

	public Constructor<?> getSparseBigIntegerMatrix2DConstructor() {
		return sparseBigIntegerMatrix2DConstructor;
	}

	public Constructor<?> getSparseBigDecimalMatrix2DConstructor() {
		return sparseBigDecimalMatrix2DConstructor;
	}

	public Constructor<?> getDenseBigIntegerMatrix2DConstructor() {
		return denseBigIntegerMatrix2DConstructor;
	}

	public Constructor<?> getDenseBigDecimalMatrix2DConstructor() {
		return denseBigDecimalMatrix2DConstructor;
	}

}
