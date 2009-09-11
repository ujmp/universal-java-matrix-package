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

package org.ujmp.core.matrix;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.swing.JFrame;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.annotation.Annotation;
import org.ujmp.core.annotation.DefaultAnnotation;
import org.ujmp.core.booleanmatrix.BooleanMatrix;
import org.ujmp.core.booleanmatrix.calculation.And;
import org.ujmp.core.booleanmatrix.calculation.Eq;
import org.ujmp.core.booleanmatrix.calculation.Ge;
import org.ujmp.core.booleanmatrix.calculation.Gt;
import org.ujmp.core.booleanmatrix.calculation.Le;
import org.ujmp.core.booleanmatrix.calculation.Lt;
import org.ujmp.core.booleanmatrix.calculation.Ne;
import org.ujmp.core.booleanmatrix.calculation.Not;
import org.ujmp.core.booleanmatrix.calculation.Or;
import org.ujmp.core.booleanmatrix.calculation.ToBooleanMatrix;
import org.ujmp.core.booleanmatrix.calculation.Xor;
import org.ujmp.core.bytematrix.ByteMatrix;
import org.ujmp.core.bytematrix.calculation.ToByteMatrix;
import org.ujmp.core.calculation.Calculation;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.charmatrix.CharMatrix;
import org.ujmp.core.charmatrix.calculation.ToCharMatrix;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.datematrix.DateMatrix;
import org.ujmp.core.datematrix.calculation.ToDateMatrix;
import org.ujmp.core.doublematrix.DoubleMatrix;
import org.ujmp.core.doublematrix.calculation.DoubleCalculations;
import org.ujmp.core.doublematrix.calculation.ToDoubleMatrix;
import org.ujmp.core.doublematrix.calculation.basic.Atimes;
import org.ujmp.core.doublematrix.calculation.basic.Divide;
import org.ujmp.core.doublematrix.calculation.basic.Minus;
import org.ujmp.core.doublematrix.calculation.basic.Mtimes;
import org.ujmp.core.doublematrix.calculation.basic.Plus;
import org.ujmp.core.doublematrix.calculation.basic.Times;
import org.ujmp.core.doublematrix.calculation.entrywise.basic.Abs;
import org.ujmp.core.doublematrix.calculation.entrywise.basic.Exp;
import org.ujmp.core.doublematrix.calculation.entrywise.basic.Log;
import org.ujmp.core.doublematrix.calculation.entrywise.basic.Log10;
import org.ujmp.core.doublematrix.calculation.entrywise.basic.Log2;
import org.ujmp.core.doublematrix.calculation.entrywise.basic.Power;
import org.ujmp.core.doublematrix.calculation.entrywise.basic.Sign;
import org.ujmp.core.doublematrix.calculation.entrywise.basic.Sqrt;
import org.ujmp.core.doublematrix.calculation.entrywise.creators.Eye;
import org.ujmp.core.doublematrix.calculation.entrywise.creators.Ones;
import org.ujmp.core.doublematrix.calculation.entrywise.creators.Rand;
import org.ujmp.core.doublematrix.calculation.entrywise.creators.Randn;
import org.ujmp.core.doublematrix.calculation.entrywise.creators.Zeros;
import org.ujmp.core.doublematrix.calculation.entrywise.hyperbolic.Cosh;
import org.ujmp.core.doublematrix.calculation.entrywise.hyperbolic.Sinh;
import org.ujmp.core.doublematrix.calculation.entrywise.hyperbolic.Tanh;
import org.ujmp.core.doublematrix.calculation.entrywise.rounding.Ceil;
import org.ujmp.core.doublematrix.calculation.entrywise.rounding.Floor;
import org.ujmp.core.doublematrix.calculation.entrywise.rounding.Round;
import org.ujmp.core.doublematrix.calculation.entrywise.trigonometric.Cos;
import org.ujmp.core.doublematrix.calculation.entrywise.trigonometric.Sin;
import org.ujmp.core.doublematrix.calculation.entrywise.trigonometric.Tan;
import org.ujmp.core.doublematrix.calculation.general.decomposition.EVD;
import org.ujmp.core.doublematrix.calculation.general.decomposition.Ginv;
import org.ujmp.core.doublematrix.calculation.general.decomposition.Inv;
import org.ujmp.core.doublematrix.calculation.general.decomposition.LU;
import org.ujmp.core.doublematrix.calculation.general.decomposition.Pinv;
import org.ujmp.core.doublematrix.calculation.general.decomposition.Princomp;
import org.ujmp.core.doublematrix.calculation.general.decomposition.QR;
import org.ujmp.core.doublematrix.calculation.general.decomposition.SVD;
import org.ujmp.core.doublematrix.calculation.general.misc.Center;
import org.ujmp.core.doublematrix.calculation.general.misc.DiscretizeToColumns;
import org.ujmp.core.doublematrix.calculation.general.misc.FadeIn;
import org.ujmp.core.doublematrix.calculation.general.misc.FadeOut;
import org.ujmp.core.doublematrix.calculation.general.misc.Standardize;
import org.ujmp.core.doublematrix.calculation.general.misc.TfIdf;
import org.ujmp.core.doublematrix.calculation.general.missingvalues.AddMissing;
import org.ujmp.core.doublematrix.calculation.general.missingvalues.CountMissing;
import org.ujmp.core.doublematrix.calculation.general.missingvalues.Impute;
import org.ujmp.core.doublematrix.calculation.general.missingvalues.Impute.ImputationMethod;
import org.ujmp.core.doublematrix.calculation.general.statistical.Corrcoef;
import org.ujmp.core.doublematrix.calculation.general.statistical.Cov;
import org.ujmp.core.doublematrix.calculation.general.statistical.Cumprod;
import org.ujmp.core.doublematrix.calculation.general.statistical.Cumsum;
import org.ujmp.core.doublematrix.calculation.general.statistical.Diff;
import org.ujmp.core.doublematrix.calculation.general.statistical.IndexOfMax;
import org.ujmp.core.doublematrix.calculation.general.statistical.IndexOfMin;
import org.ujmp.core.doublematrix.calculation.general.statistical.Max;
import org.ujmp.core.doublematrix.calculation.general.statistical.Mean;
import org.ujmp.core.doublematrix.calculation.general.statistical.Min;
import org.ujmp.core.doublematrix.calculation.general.statistical.MutualInformation;
import org.ujmp.core.doublematrix.calculation.general.statistical.Prod;
import org.ujmp.core.doublematrix.calculation.general.statistical.Std;
import org.ujmp.core.doublematrix.calculation.general.statistical.Sum;
import org.ujmp.core.doublematrix.calculation.general.statistical.Var;
import org.ujmp.core.enums.FileFormat;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.floatmatrix.FloatMatrix;
import org.ujmp.core.floatmatrix.calculation.ToFloatMatrix;
import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.core.interfaces.HasLabel;
import org.ujmp.core.intmatrix.IntMatrix;
import org.ujmp.core.intmatrix.calculation.Discretize;
import org.ujmp.core.intmatrix.calculation.ToIntMatrix;
import org.ujmp.core.intmatrix.calculation.Discretize.DiscretizationMethod;
import org.ujmp.core.io.ExportMatrix;
import org.ujmp.core.listmatrix.DefaultListMatrix;
import org.ujmp.core.listmatrix.ListMatrix;
import org.ujmp.core.longmatrix.LongMatrix;
import org.ujmp.core.longmatrix.calculation.ToLongMatrix;
import org.ujmp.core.mapmatrix.DefaultMapMatrix;
import org.ujmp.core.mapmatrix.MapMatrix;
import org.ujmp.core.objectmatrix.ObjectMatrix;
import org.ujmp.core.objectmatrix.calculation.Bootstrap;
import org.ujmp.core.objectmatrix.calculation.Convert;
import org.ujmp.core.objectmatrix.calculation.Deletion;
import org.ujmp.core.objectmatrix.calculation.Fill;
import org.ujmp.core.objectmatrix.calculation.Flipdim;
import org.ujmp.core.objectmatrix.calculation.Replace;
import org.ujmp.core.objectmatrix.calculation.Selection;
import org.ujmp.core.objectmatrix.calculation.Shuffle;
import org.ujmp.core.objectmatrix.calculation.Sort;
import org.ujmp.core.objectmatrix.calculation.Swap;
import org.ujmp.core.objectmatrix.calculation.ToObjectMatrix;
import org.ujmp.core.objectmatrix.calculation.Transpose;
import org.ujmp.core.objectmatrix.calculation.Unique;
import org.ujmp.core.objectmatrix.impl.ReshapedObjectMatrix;
import org.ujmp.core.setmatrix.DefaultSetMatrix;
import org.ujmp.core.setmatrix.SetMatrix;
import org.ujmp.core.shortmatrix.ShortMatrix;
import org.ujmp.core.shortmatrix.calculation.ToShortMatrix;
import org.ujmp.core.stringmatrix.StringMatrix;
import org.ujmp.core.stringmatrix.calculation.LowerCase;
import org.ujmp.core.stringmatrix.calculation.RemovePunctuation;
import org.ujmp.core.stringmatrix.calculation.RemoveWords;
import org.ujmp.core.stringmatrix.calculation.ReplaceRegex;
import org.ujmp.core.stringmatrix.calculation.Stem;
import org.ujmp.core.stringmatrix.calculation.ToStringMatrix;
import org.ujmp.core.stringmatrix.calculation.UpperCase;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.StringUtil;
import org.ujmp.core.util.UJMPSettings;

public abstract class AbstractMatrix extends Number implements Matrix {
	private static final long serialVersionUID = 5264103919889924711L;

	/**
	 * A logger used for <code>Matrix</code> and all subclasses
	 */
	protected transient static final Logger logger = Logger.getLogger(Matrix.class.getName());

	private static long runningId = 0;

	static {
		try {
			runningId = 31 * System.nanoTime() + System.currentTimeMillis();
		} catch (Throwable t) {
		}
		try {
			UJMPSettings.initialize();
		} catch (Throwable t) {
		}
		try {
			long mem = Runtime.getRuntime().maxMemory();
			if (mem < 133234688) {
				logger.log(Level.WARNING, "Available memory is very low: " + (mem / 1024 / 1024)
						+ "M");
				logger.log(Level.WARNING,
						"Invoke Java with the parameter -Xmx512M to increase available memory");
			}
		} catch (Throwable t) {
		}
	}

	private transient GUIObject guiObject = null;

	private long id = 0;

	private Annotation annotation = null;

	public AbstractMatrix() {
		id = runningId++;
	}

	public final long getCoreObjectId() {
		return id;
	}

	public double getAsDouble(long... coordinates) {
		return MathUtil.getDouble(getAsObject(coordinates));
	}

	public void setAsDouble(double v, long... coordinates) {
		setAsObject(v, coordinates);
	}

	public final Object getPreferredObject(long... coordinates) throws MatrixException {
		return MathUtil.getPreferredObject(getAsObject(coordinates));
	}

	public final Object getMatrixAnnotation() {
		return annotation == null ? null : annotation.getMatrixAnnotation();
	}

	public ValueType getValueType() {
		return ValueType.OBJECT;
	}

	public final void setMatrixAnnotation(Object value) {
		if (annotation == null) {
			annotation = new DefaultAnnotation();
		}
		annotation.setMatrixAnnotation(value);
	}

	public final Object getAxisAnnotation(int axis, long positionOnAxis) {
		return annotation == null ? null : annotation.getAxisAnnotation(axis, positionOnAxis);
	}

	public final Object getAxisAnnotation(int axis) {
		return annotation == null ? null : annotation.getAxisAnnotation(axis);
	}

	public final void setAxisAnnotation(int axis, long positionOnAxis, Object value) {
		if (annotation == null) {
			annotation = new DefaultAnnotation();
		}
		annotation.setAxisAnnotation(axis, positionOnAxis, value);
	}

	public final void setAxisAnnotation(int axis, Object value) {
		if (annotation == null) {
			annotation = new DefaultAnnotation();
		}
		annotation.setAxisAnnotation(axis, value);
	}

	public final GUIObject getGUIObject() {
		if (guiObject == null) {
			try {
				Class<?> c = Class.forName("org.ujmp.gui.MatrixGUIObject");
				Constructor<?> con = c.getConstructor(new Class<?>[] { Matrix.class });
				guiObject = (GUIObject) con.newInstance(new Object[] { this });
			} catch (Exception e) {
				logger.log(Level.WARNING, "cannot create matrix gui object", e);
			}
		}
		return guiObject;
	}

	public final boolean containsMissingValues() throws MatrixException {
		for (long[] c : allCoordinates()) {
			double v = getAsDouble(c);
			if (MathUtil.isNaNOrInfinite(v)) {
				return true;
			}
		}
		return false;
	}

	public final double getEuklideanValue() throws MatrixException {
		double sum = 0.0;
		for (long[] c : allCoordinates()) {
			sum += Math.pow(getAsDouble(c), 2.0);
		}
		return Math.sqrt(sum);
	}

	public final Matrix clone() throws CloneNotSupportedException {
		try {
			return copy();
		} catch (MatrixException e) {
			logger.log(Level.WARNING, "Could not clone Matrix, returning original Matrix", e);
			return this;
		}
	}

	public final Matrix select(Ret returnType, long[]... selection) throws MatrixException {
		return new Selection(this, selection).calc(returnType);
	}

	public final Matrix select(Ret returnType, Collection<? extends Number>... selection)
			throws MatrixException {
		return new Selection(this, selection).calc(returnType);
	}

	public Matrix selectRows(Ret returnType, long... rows) throws MatrixException {
		return select(returnType, rows, null);
	}

	public final Matrix select(Ret returnType, String selection) throws MatrixException {
		return new Selection(this, selection).calc(returnType);
	}

	public Matrix selectColumns(Ret returnType, long... columns) throws MatrixException {
		return select(returnType, null, columns);
	}

	@SuppressWarnings("unchecked")
	public final Matrix selectRows(Ret returnType, Collection<? extends Number> rows)
			throws MatrixException {
		return select(returnType, rows, null);
	}

	@SuppressWarnings("unchecked")
	public final Matrix selectColumns(Ret returnType, Collection<? extends Number> columns)
			throws MatrixException {
		return select(returnType, null, columns);
	}

	public Matrix impute(Ret returnType, ImputationMethod method, Object... parameters)
			throws MatrixException {
		return new Impute(this, method, parameters).calc(returnType);
	}

	public Matrix discretize(Ret returnType, int dimension, DiscretizationMethod method,
			int numberOfBins) throws MatrixException {
		return new Discretize(this, dimension, method, numberOfBins).calc(returnType);
	}

	public Matrix indexOfMax(Ret returnType, int dimension) throws MatrixException {
		return new IndexOfMax(dimension, this).calc(returnType);
	}

	public Matrix indexOfMin(Ret returnType, int dimension) throws MatrixException {
		return new IndexOfMin(dimension, this).calc(returnType);
	}

	public Matrix standardize(Ret returnType, int dimension, boolean ignoreNaN)
			throws MatrixException {
		return new Standardize(ignoreNaN, dimension, this).calc(returnType);
	}

	public Matrix atimes(Ret returnType, boolean ignoreNaN, Matrix matrix) throws MatrixException {
		return new Atimes(ignoreNaN, this, matrix).calc(returnType);
	}

	public Matrix inv() throws MatrixException {
		if (getDimensionCount() != 2 || getRowCount() != getColumnCount()) {
			throw new MatrixException(
					"inverse only possible for square matrices. use pinv or ginv instead");
		}
		return new Inv(this).calcNew();
	}

	public Matrix ginv() throws MatrixException {
		return new Ginv(this).calcNew();
	}

	public Matrix princomp() throws MatrixException {
		return new Princomp(this).calcNew();
	}

	public Matrix pinv() throws MatrixException {
		return new Pinv(this).calcNew();
	}

	public Matrix center(Ret returnType, int dimension, boolean ignoreNaN) throws MatrixException {
		return new Center(ignoreNaN, dimension, this).calc(returnType);
	}

	public Matrix copy() throws MatrixException {
		return Convert.calcNew(this);
	}

	public boolean isResizeable() {
		return false;
	}

	public final Matrix convert(ValueType newValueType) throws MatrixException {
		return Convert.calcNew(newValueType, this);
	}

	public final Matrix replaceRegex(Ret returnType, Pattern search, String replacement)
			throws MatrixException {
		return new ReplaceRegex(this, search, replacement).calc(returnType);
	}

	public final Matrix replace(Ret returnType, Object search, Object replacement)
			throws MatrixException {
		return new Replace(this, search, replacement).calc(returnType);
	}

	public final Matrix replaceRegex(Ret returnType, String search, String replacement)
			throws MatrixException {
		return new ReplaceRegex(this, search, replacement).calc(returnType);
	}

	public Matrix times(double factor) throws MatrixException {
		return Times.calc(false, this, factor);
	}

	public Matrix times(Matrix matrix) throws MatrixException {
		return Times.calc(false, this, matrix);
	}

	public Matrix divide(Matrix m) throws MatrixException {
		return Divide.calc(this, m);
	}

	public Matrix divide(double factor) throws MatrixException {
		return Divide.calc(this, factor);
	}

	public Matrix divide(Ret returnType, boolean ignoreNaN, double factor) throws MatrixException {
		return new Divide(ignoreNaN, this, factor).calc(returnType);
	}

	public Matrix times(Ret returnType, boolean ignoreNaN, double factor) throws MatrixException {
		return new Times(ignoreNaN, this, factor).calc(returnType);
	}

	public Matrix times(Ret returnType, boolean ignoreNaN, Matrix factor) throws MatrixException {
		return new Times(ignoreNaN, this, factor).calc(returnType);
	}

	public Matrix divide(Ret returnType, boolean ignoreNaN, Matrix factor) throws MatrixException {
		return new Divide(ignoreNaN, this, factor).calc(returnType);
	}

	public final Matrix power(Ret returnType, double power) throws MatrixException {
		return new Power(this, power).calc(returnType);
	}

	public final Matrix power(Ret returnType, Matrix power) throws MatrixException {
		return new Power(this, power).calc(returnType);
	}

	public final Matrix gt(Ret returnType, Matrix matrix) throws MatrixException {
		return new Gt(this, matrix).calc(returnType);
	}

	public final Matrix gt(Ret returnType, double value) throws MatrixException {
		return new Gt(this, value).calc(returnType);
	}

	public final Matrix and(Ret returnType, Matrix matrix) throws MatrixException {
		return new And(this, matrix).calc(returnType);
	}

	public final Matrix and(Ret returnType, boolean value) throws MatrixException {
		return new And(this, value).calc(returnType);
	}

	public final Matrix or(Ret returnType, Matrix matrix) throws MatrixException {
		return new Or(this, matrix).calc(returnType);
	}

	public final Matrix or(Ret returnType, boolean value) throws MatrixException {
		return new Or(this, value).calc(returnType);
	}

	public final Matrix xor(Ret returnType, Matrix matrix) throws MatrixException {
		return new Xor(this, matrix).calc(returnType);
	}

	public final Matrix xor(Ret returnType, boolean value) throws MatrixException {
		return new Xor(this, value).calc(returnType);
	}

	public final Matrix not(Ret returnType) throws MatrixException {
		return new Not(this).calc(returnType);
	}

	public final Matrix lt(Ret returnType, Matrix matrix) throws MatrixException {
		return new Lt(this, matrix).calc(returnType);
	}

	public final Matrix lt(Ret returnType, double value) throws MatrixException {
		return new Lt(this, value).calc(returnType);
	}

	public final Matrix ge(Ret returnType, Matrix matrix) throws MatrixException {
		return new Ge(this, matrix).calc(returnType);
	}

	public final Matrix ge(Ret returnType, double value) throws MatrixException {
		return new Ge(this, value).calc(returnType);
	}

	public final Matrix le(Ret returnType, Matrix matrix) throws MatrixException {
		return new Le(this, matrix).calc(returnType);
	}

	public final Matrix le(Ret returnType, double value) throws MatrixException {
		return new Le(this, value).calc(returnType);
	}

	public final Matrix eq(Ret returnType, Matrix matrix) throws MatrixException {
		return new Eq(this, matrix).calc(returnType);
	}

	public final Matrix eq(Ret returnType, Object value) throws MatrixException {
		return new Eq(this, value).calc(returnType);
	}

	public final Matrix ne(Ret returnType, Matrix matrix) throws MatrixException {
		return new Ne(this, matrix).calc(returnType);
	}

	public final Matrix ne(Ret returnType, Object value) throws MatrixException {
		return new Ne(this, value).calc(returnType);
	}

	public long getValueCount() {
		return Coordinates.product(getSize());
	}

	public final long[] getCoordinatesOfMaximum() throws MatrixException {
		double max = -Double.MAX_VALUE;
		long[] maxc = Coordinates.copyOf(getSize());
		Arrays.fill(maxc, -1);
		for (long[] c : allCoordinates()) {
			double v = getAsDouble(c);
			if (v > max) {
				max = v;
				maxc = Coordinates.copyOf(c);
			}
		}
		return maxc;
	}

	public final long[] getCoordinatesOfMinimum() throws MatrixException {
		double min = Double.MAX_VALUE;
		long[] minc = Coordinates.copyOf(getSize());
		Arrays.fill(minc, -1);
		for (long[] c : allCoordinates()) {
			double v = getAsDouble(c);
			if (v < min) {
				min = v;
				minc = Coordinates.copyOf(c);
			}
		}
		return minc;
	}

	public Iterable<long[]> selectedCoordinates(String selection) throws MatrixException {
		return select(Ret.LINK, selection).allCoordinates();
	}

	public Iterable<long[]> selectedCoordinates(long[]... selection) throws MatrixException {
		return select(Ret.LINK, selection).allCoordinates();
	}

	public boolean isTransient() {
		return false;
	}

	public Iterable<long[]> nonZeroCoordinates() {
		return availableCoordinates();
	}

	public Iterable<long[]> availableCoordinates() {
		return allCoordinates();
	}

	public double[][] toDoubleArray() throws MatrixException {
		int r = (int) getRowCount();
		int c = (int) getColumnCount();
		double[][] values = new double[r][c];
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				values[i][j] = getAsDouble(i, j);
			}
		}
		return values;
	}

	public Object[][] toObjectArray() throws MatrixException {
		int r = (int) getRowCount();
		int c = (int) getColumnCount();
		Object[][] values = new Object[r][c];
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				values[i][j] = getAsObject(i, j);
			}
		}
		return values;
	}

	public int[][] toIntArray() throws MatrixException {
		int r = (int) getRowCount();
		int c = (int) getColumnCount();
		int[][] values = new int[r][c];
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				values[i][j] = getAsInt(i, j);
			}
		}
		return values;
	}

	public long[][] toLongArray() throws MatrixException {
		int r = (int) getRowCount();
		int c = (int) getColumnCount();
		long[][] values = new long[r][c];
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				values[i][j] = getAsLong(i, j);
			}
		}
		return values;
	}

	public short[][] toShortArray() throws MatrixException {
		int r = (int) getRowCount();
		int c = (int) getColumnCount();
		short[][] values = new short[r][c];
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				values[i][j] = getAsShort(i, j);
			}
		}
		return values;
	}

	public char[][] toCharArray() throws MatrixException {
		int r = (int) getRowCount();
		int c = (int) getColumnCount();
		char[][] values = new char[r][c];
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				values[i][j] = getAsChar(i, j);
			}
		}
		return values;
	}

	public String[][] toStringArray() throws MatrixException {
		int r = (int) getRowCount();
		int c = (int) getColumnCount();
		String[][] values = new String[r][c];
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				values[i][j] = getAsString(i, j);
			}
		}
		return values;
	}

	public byte[][] toByteArray() throws MatrixException {
		int r = (int) getRowCount();
		int c = (int) getColumnCount();
		byte[][] values = new byte[r][c];
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				values[i][j] = getAsByte(i, j);
			}
		}
		return values;
	}

	public boolean[][] toBooleanArray() throws MatrixException {
		int r = (int) getRowCount();
		int c = (int) getColumnCount();
		boolean[][] values = new boolean[r][c];
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				values[i][j] = getAsBoolean(i, j);
			}
		}
		return values;
	}

	public float[][] toFloatArray() throws MatrixException {
		int r = (int) getRowCount();
		int c = (int) getColumnCount();
		float[][] values = new float[r][c];
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				values[i][j] = getAsFloat(i, j);
			}
		}
		return values;
	}

	public Date[][] toDateArray() throws MatrixException {
		int r = (int) getRowCount();
		int c = (int) getColumnCount();
		Date[][] values = new Date[r][c];
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				values[i][j] = getAsDate(i, j);
			}
		}
		return values;
	}

	public BigDecimal[][] toBigDecimalArray() throws MatrixException {
		int r = (int) getRowCount();
		int c = (int) getColumnCount();
		BigDecimal[][] values = new BigDecimal[r][c];
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				values[i][j] = getAsBigDecimal(i, j);
			}
		}
		return values;
	}

	public BigInteger[][] toBigIntegerArray() throws MatrixException {
		int r = (int) getRowCount();
		int c = (int) getColumnCount();
		BigInteger[][] values = new BigInteger[r][c];
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				values[i][j] = getAsBigInteger(i, j);
			}
		}
		return values;
	}

	public final Matrix sqrt(Ret returnType) throws MatrixException {
		return new Sqrt(this).calc(returnType);
	}

	public final Matrix round(Ret returnType) throws MatrixException {
		return new Round(this).calc(returnType);
	}

	public final Matrix ceil(Ret returnType) throws MatrixException {
		return new Ceil(this).calc(returnType);
	}

	public final Matrix floor(Ret returnType) throws MatrixException {
		return new Floor(this).calc(returnType);
	}

	public final JFrame showGUI() {
		try {
			Class<?> c = Class.forName("org.ujmp.gui.util.FrameManager");
			Method method = c.getMethod("showFrame", new Class[] { GUIObject.class });
			Object o = method.invoke(null, new Object[] { getGUIObject() });
			return (JFrame) o;
		} catch (Exception e) {
			logger.log(Level.WARNING, "cannot show GUI", e);
			return null;
		}
	}

	public void notifyGUIObject() {
		if (guiObject != null) {
			guiObject.fireValueChanged();
		}
	}

	public Matrix mtimes(Matrix matrix) throws MatrixException {
		return DoubleCalculations.mtimes.calc(this, matrix);
	}

	public Matrix mtimes(Ret returnType, boolean ignoreNaN, Matrix matrix) throws MatrixException {
		return new Mtimes(ignoreNaN, this, matrix).calc(returnType);
	}

	public Matrix mtimes(double value) throws MatrixException {
		return times(value);
	}

	public Matrix mtimes(Ret returnType, boolean ignoreNaN, double value) throws MatrixException {
		return times(returnType, ignoreNaN, value);
	}

	public boolean getAsBoolean(long... coordinates) throws MatrixException {
		return MathUtil.getBoolean(getAsObject(coordinates));
	}

	public void setAsBoolean(boolean value, long... coordinates) throws MatrixException {
		setAsDouble(value ? 1.0 : 0.0, coordinates);
	}

	public int getAsInt(long... coordinates) throws MatrixException {
		return (int) getAsDouble(coordinates);
	}

	public void setAsInt(int value, long... coordinates) throws MatrixException {
		setAsDouble(value, coordinates);
	}

	public byte getAsByte(long... coordinates) throws MatrixException {
		return (byte) getAsDouble(coordinates);
	}

	public void setAsByte(byte value, long... coordinates) throws MatrixException {
		setAsDouble(value, coordinates);
	}

	public char getAsChar(long... coordinates) throws MatrixException {
		return (char) getAsDouble(coordinates);
	}

	public BigInteger getAsBigInteger(long... coordinates) throws MatrixException {
		return MathUtil.getBigInteger(getAsObject(coordinates));
	}

	public BigDecimal getAsBigDecimal(long... coordinates) throws MatrixException {
		return MathUtil.getBigDecimal(getAsObject(coordinates));
	}

	public void setAsChar(char value, long... coordinates) throws MatrixException {
		setAsDouble(value, coordinates);
	}

	public void setAsBigDecimal(BigDecimal value, long... coordinates) throws MatrixException {
		if (value == null) {
			setAsDouble(0, coordinates);
		} else {
			setAsDouble(value.doubleValue(), coordinates);
		}
	}

	public void setAsBigInteger(BigInteger value, long... coordinates) throws MatrixException {
		if (value == null) {
			setAsLong(0, coordinates);
		} else {
			setAsLong(value.longValue(), coordinates);
		}
	}

	public float getAsFloat(long... coordinates) throws MatrixException {
		return (float) getAsDouble(coordinates);
	}

	public void setAsFloat(float value, long... coordinates) throws MatrixException {
		setAsDouble(value, coordinates);
	}

	public short getAsShort(long... coordinates) throws MatrixException {
		return (short) getAsDouble(coordinates);
	}

	public Matrix getAsMatrix(long... coordinates) throws MatrixException {
		return MathUtil.getMatrix(getAsObject(coordinates));
	}

	public void setAsMatrix(Matrix m, long... coordinates) throws MatrixException {
		setAsObject(m, coordinates);
	}

	public void setAsShort(short value, long... coordinates) throws MatrixException {
		setAsDouble(value, coordinates);
	}

	public long getAsLong(long... coordinates) throws MatrixException {
		return (long) getAsDouble(coordinates);
	}

	public void setAsLong(long value, long... coordinates) throws MatrixException {
		setAsDouble(value, coordinates);
	}

	public Date getAsDate(long... coordinates) throws MatrixException {
		return MathUtil.getDate(getAsObject(coordinates));
	}

	public void setAsDate(Date date, long... coordinates) throws MatrixException {
		setAsObject(date, coordinates);
	}

	public final Matrix delete(Ret returnType, String selection) throws MatrixException {
		return new Deletion(this, selection).calc(returnType);
	}

	public final Matrix delete(Ret returnType, Collection<? extends Number>... selection)
			throws MatrixException {
		return new Deletion(this, selection).calc(returnType);
	}

	public final Matrix delete(Ret returnType, long[]... selection) throws MatrixException {
		return new Deletion(this, selection).calc(returnType);
	}

	public final Matrix deleteRows(Ret returnType, long... rows) throws MatrixException {
		return delete(returnType, rows, new long[] {});
	}

	@SuppressWarnings("unchecked")
	public final Matrix deleteRows(Ret returnType, Collection<? extends Number> rows)
			throws MatrixException {
		return delete(returnType, rows, new ArrayList<Long>());
	}

	@SuppressWarnings("unchecked")
	public final Matrix deleteColumns(Ret returnType, Collection<? extends Number> columns)
			throws MatrixException {
		return delete(returnType, new ArrayList<Long>(), columns);
	}

	public final Matrix deleteColumns(Ret returnType, long... columns) throws MatrixException {
		return delete(returnType, new long[] {}, columns);
	}

	public Matrix minus(Ret returnType, boolean ignoreNaN, double v) throws MatrixException {
		return new Minus(ignoreNaN, this, v).calc(returnType);
	}

	public Matrix minus(Ret returnType, boolean ignoreNaN, Matrix m) throws MatrixException {
		return new Minus(ignoreNaN, this, m).calc(returnType);
	}

	public Matrix plus(Ret returnType, boolean ignoreNaN, double v) throws MatrixException {
		return new Plus(ignoreNaN, this, v).calc(returnType);
	}

	public Matrix plus(Ret returnType, boolean ignoreNaN, Matrix m) throws MatrixException {
		return new Plus(ignoreNaN, this, m).calc(returnType);
	}

	public Matrix transpose() throws MatrixException {
		return Transpose.calc(this);
	}

	public Matrix transpose(Ret returnType) throws MatrixException {
		return new Transpose(this).calc(returnType);
	}

	public Matrix mean(Ret returnType, int dimension, boolean ignoreNaN) throws MatrixException {
		return new Mean(dimension, ignoreNaN, this).calc(returnType);
	}

	public Matrix var(Ret returnType, int dimension, boolean ignoreNaN) throws MatrixException {
		return new Var(dimension, ignoreNaN, this).calc(returnType);
	}

	public Matrix std(Ret returnType, int dimension, boolean ignoreNaN) throws MatrixException {
		return new Std(dimension, ignoreNaN, this).calc(returnType);
	}

	public long getColumnCount() {
		return getSize(COLUMN);
	}

	public long getRowCount() {
		return getSize(ROW);
	}

	public long getZCount() {
		return getSize(Z);
	}

	public final long getSize(int dimension) {
		return getSize()[dimension];
	}

	public Matrix prod(Ret returnType, int dimension, boolean ignoreNaN) throws MatrixException {
		return new Prod(dimension, ignoreNaN, this).calc(returnType);
	}

	public Matrix diff(Ret returnType, int dimension, boolean ignoreNaN) throws MatrixException {
		return new Diff(dimension, ignoreNaN, this).calc(returnType);
	}

	public final Matrix sum(Ret returnType, int dimension, boolean ignoreNaN)
			throws MatrixException {
		return new Sum(dimension, ignoreNaN, this).calc(returnType);
	}

	public final Matrix sign(Ret returnType) throws MatrixException {
		return new Sign(this).calc(returnType);
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		try {
			final String EOL = System.getProperty("line.separator");

			long rowCount = getRowCount();
			long columnCount = getColumnCount();
			for (int row = 0; row < rowCount && row < UJMPSettings.getMaxRowsToPrint(); row++) {
				for (int col = 0; col < columnCount && col < UJMPSettings.getMaxColumnsToPrint(); col++) {
					Object o = getAsObject(row, col);
					String v = StringUtil.format(o);
					while (v.length() < 10) {
						v = " " + v;
					}
					s.append(v);
				}
				s.append(EOL);
			}

			if (rowCount == 0 || columnCount == 0) {
				s.append("[" + rowCount + "x" + columnCount + "]" + EOL);
			} else if (rowCount > UJMPSettings.getMaxRowsToPrint()
					|| columnCount > UJMPSettings.getMaxColumnsToPrint()) {
				s.append("[...]");
			}
		} catch (Exception e) {
			logger.log(Level.WARNING, "could not execute toString()", e);
		}
		return s.toString();
	}

	public final int getDimensionCount() {
		return getSize().length;
	}

	public final Matrix ones(Ret ret) throws MatrixException {
		return new Ones(this).calc(ret);
	}

	public final Matrix fill(Ret ret, Object value) throws MatrixException {
		return new Fill(this, value).calc(ret);
	}

	public final Matrix zeros(Ret ret) throws MatrixException {
		return new Zeros(this).calc(ret);
	}

	public final Matrix eye(Ret ret) throws MatrixException {
		return new Eye(this).calc(ret);
	}

	public Matrix plus(double v) throws MatrixException {
		return Plus.calc(false, this, v);
	}

	public Matrix plus(Matrix m) throws MatrixException {
		return Plus.calc(false, this, m);
	}

	public Matrix minus(double v) throws MatrixException {
		return Minus.calc(false, this, v);
	}

	public Matrix minus(Matrix m) throws MatrixException {
		return Minus.calc(false, this, m);
	}

	public void clear() {
		new Zeros(this).calc(Ret.ORIG);
	}

	public final Matrix rand(Ret ret) throws MatrixException {
		return new Rand(this).calc(ret);
	}

	public final Matrix randn(Ret ret) throws MatrixException {
		return new Randn(this).calc(ret);
	}

	public final int compareTo(Matrix m) {
		try {
			return new Double(doubleValue()).compareTo(m.doubleValue());
		} catch (MatrixException e) {
			logger.log(Level.WARNING, "could not compare", e);
			return Integer.MAX_VALUE;
		}
	}

	public int rank() throws MatrixException {
		int rank = 0;

		Matrix[] usv = svd();
		Matrix s = usv[1];

		for (int i = (int) Math.min(s.getSize(ROW), s.getSize(COLUMN)); --i >= 0;) {
			if (Math.abs(s.getAsDouble(i, i)) > UJMPSettings.getTolerance()) {
				rank++;
			}
		}

		return rank;
	}

	public boolean isSymmetric() {
		long rows = getRowCount();
		long cols = getColumnCount();
		if (rows != cols) {
			return false;
		}
		for (long r = 0; r < rows; r++) {
			for (long c = 0; c < cols; c++) {
				if (MathUtil.equals(getAsObject(r, c), getAsObject(c, r))) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean isEmpty() throws MatrixException {
		for (long[] c : availableCoordinates()) {
			if (getAsDouble(c) != 0.0) {
				return false;
			}
		}
		return true;
	}

	public final Matrix abs(Ret returnType) throws MatrixException {
		return new Abs(this).calc(returnType);
	}

	public final Matrix log(Ret returnType) throws MatrixException {
		return new Log(this).calc(returnType);
	}

	public final Matrix exp(Ret returnType) throws MatrixException {
		return new Exp(this).calc(returnType);
	}

	public final Matrix sort(Ret returnType) throws MatrixException {
		return new Sort(this).calc(returnType);
	}

	public final Matrix sort(Ret returnType, long column) throws MatrixException {
		return new Sort(this, column).calc(returnType);
	}

	public final Matrix cumsum(boolean ignoreNaN) throws MatrixException {
		return new Cumsum(this, ignoreNaN).calcNew();
	}

	public final Matrix cumprod(boolean ignoreNaN) throws MatrixException {
		return new Cumprod(this, ignoreNaN).calcNew();
	}

	public final Matrix log2(Ret returnType) throws MatrixException {
		return new Log2(this).calc(returnType);
	}

	public final Matrix log10(Ret returnType) throws MatrixException {
		return new Log10(this).calc(returnType);
	}

	public final boolean isDiagonal() throws MatrixException {
		if (!isSquare()) {
			return false;
		}
		for (long[] c : allCoordinates()) {
			double v = getAsDouble(c);
			if (v != 0.0) {
				for (int i = 1; i < c.length; i++) {
					if (c[i - 1] != c[i]) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public final boolean isSquare() {
		return getSize().length == 2 && getColumnCount() == getRowCount();
	}

	public double euklideanDistanceTo(Matrix m, boolean ignoreNaN) throws MatrixException {
		return minkowskiDistanceTo(m, 2, ignoreNaN);
	}

	public double manhattenDistanceTo(Matrix m, boolean ignoreNaN) throws MatrixException {
		return minkowskiDistanceTo(m, 1, ignoreNaN);
	}

	public double minkowskiDistanceTo(Matrix m, double p, boolean ignoreNaN) throws MatrixException {
		double sum = 0.0;
		if (ignoreNaN) {
			for (long[] c : allCoordinates()) {
				sum += MathUtil.ignoreNaN(Math
						.pow(Math.abs((getAsDouble(c)) - m.getAsDouble(c)), p));
			}
		} else {
			for (long[] c : allCoordinates()) {
				sum += Math.pow(Math.abs((getAsDouble(c)) - m.getAsDouble(c)), p);
			}
		}
		return Math.pow(sum, 1 / p);
	}

	public double chebyshevDistanceTo(Matrix m, boolean ignoreNaN) throws MatrixException {
		double max = 0.0;
		if (ignoreNaN) {
			for (long[] c : allCoordinates()) {
				double v = MathUtil.ignoreNaN(Math.abs((getAsDouble(c) - m.getAsDouble(c))));
				max = v > max ? v : max;
			}
		} else {
			for (long[] c : allCoordinates()) {
				double v = Math.abs((getAsDouble(c) - m.getAsDouble(c)));
				max = v > max ? v : max;
			}
		}
		return max;
	}

	public Matrix min(Ret returnType, int dimension) throws MatrixException {
		return new Min(dimension, this).calc(returnType);
	}

	public Matrix max(Ret returnType, int dimension) throws MatrixException {
		return new Max(dimension, this).calc(returnType);
	}

	public final Matrix addMissing(Ret returnType, int dimension, double... percentMissing)
			throws MatrixException {
		return new AddMissing(dimension, this, percentMissing).calc(returnType);
	}

	public Matrix countMissing(Ret returnType, int dimension) throws MatrixException {
		return new CountMissing(dimension, this).calc(returnType);
	}

	public final boolean isScalar() {
		return getColumnCount() == 1 && getRowCount() == 1;
	}

	public final boolean isRowVector() {
		return getColumnCount() == 1 && getRowCount() != 1;
	}

	public final boolean isColumnVector() {
		return getColumnCount() != 1 && getRowCount() == 1;
	}

	public final boolean isMultidimensionalMatrix() {
		return getColumnCount() != 1 && getRowCount() != 1;
	}

	public Matrix sinh(Ret returnType) throws MatrixException {
		return new Sinh(this).calc(returnType);
	}

	public Matrix cosh(Ret returnType) throws MatrixException {
		return new Cosh(this).calc(returnType);
	}

	public Matrix tanh(Ret returnType) throws MatrixException {
		return new Tanh(this).calc(returnType);
	}

	public Matrix sin(Ret returnType) throws MatrixException {
		return new Sin(this).calc(returnType);
	}

	public Matrix cos(Ret returnType) throws MatrixException {
		return new Cos(this).calc(returnType);
	}

	public Matrix tan(Ret returnType) throws MatrixException {
		return new Tan(this).calc(returnType);
	}

	public Matrix cov(Ret returnType, boolean ignoreNaN) throws MatrixException {
		return new Cov(ignoreNaN, this).calc(returnType);
	}

	public Matrix corrcoef(Ret returnType, boolean ignoreNaN) throws MatrixException {
		return new Corrcoef(ignoreNaN, this).calc(returnType);
	}

	public Matrix mutualInf(Ret returnType) throws MatrixException {
		return new MutualInformation(this).calc(returnType);
	}

	public Matrix pairedTTest(Ret returnType) throws MatrixException {
		try {
			Class<?> c = Class.forName("org.ujmp.commonsmath.PairedTTest");
			Constructor<?> con = c.getConstructor(Matrix.class);
			Calculation<?, ?> calc = (Calculation<?, ?>) con.newInstance(this);
			return calc.calc(returnType);
		} catch (Exception e) {
			throw new MatrixException("could not calculate", e);
		}
	}

	public Matrix bootstrap(Ret returnType) throws MatrixException {
		return new Bootstrap(this).calc(returnType);
	}

	public Matrix lowerCase(Ret returnType) throws MatrixException {
		return new LowerCase(this).calc(returnType);
	}

	public Matrix upperCase(Ret returnType) throws MatrixException {
		return new UpperCase(this).calc(returnType);
	}

	public Matrix tfIdf(boolean calculateTf, boolean calculateIdf, boolean normalize)
			throws MatrixException {
		return new TfIdf(this, calculateTf, calculateIdf, normalize).calc(Ret.NEW);
	}

	public Matrix removePunctuation(Ret ret) throws MatrixException {
		return new RemovePunctuation(this).calc(ret);
	}

	public Matrix stem(Ret ret) throws MatrixException {
		return new Stem(this).calc(ret);
	}

	public Matrix removeWords(Ret ret, Collection<String> words) throws MatrixException {
		return new RemoveWords(this, words).calc(ret);
	}

	public Matrix unique(Ret returnType) throws MatrixException {
		return new Unique(this).calc(returnType);
	}

	public Matrix bootstrap(Ret returnType, int count) throws MatrixException {
		return new Bootstrap(this, count).calc(returnType);
	}

	public Matrix transpose(Ret returnType, int dimension1, int dimension2) throws MatrixException {
		return new Transpose(this, dimension1, dimension2).calc(returnType);
	}

	public Matrix swap(Ret returnType, int dimension, long pos1, long pos2) throws MatrixException {
		return new Swap(dimension, pos1, pos2, this).calc(returnType);
	}

	public Matrix flipdim(Ret returnType, int dimension) throws MatrixException {
		return new Flipdim(dimension, this).calc(returnType);
	}

	public final Matrix shuffle(Ret returnType) throws MatrixException {
		return new Shuffle(this).calc(returnType);
	}

	public final double trace() throws MatrixException {
		double sum = 0.0;
		for (long i = Math.min(getRowCount(), getColumnCount()); --i >= 0;) {
			sum += getAsDouble(i, i);
		}
		return sum;
	}

	public final void exportToFile(File file, Object... parameters) throws MatrixException,
			IOException {
		ExportMatrix.toFile(file, this, parameters);
	}

	public final void exportToClipboard(FileFormat format, Object... parameters)
			throws MatrixException, IOException {
		ExportMatrix.toClipboard(format, this, parameters);
	}

	public final void exportToFile(String file, Object... parameters) throws MatrixException,
			IOException {
		ExportMatrix.toFile(file, this, parameters);
	}

	public final void exportToFile(FileFormat format, String filename, Object... parameters)
			throws MatrixException, IOException {
		ExportMatrix.toFile(format, filename, this, parameters);
	}

	public final void exportToFile(FileFormat format, File file, Object... parameters)
			throws MatrixException, IOException {
		ExportMatrix.toFile(format, file, this, parameters);
	}

	public final void exportToStream(FileFormat format, OutputStream outputStream,
			Object... parameters) throws MatrixException, IOException {
		ExportMatrix.toStream(format, outputStream, this, parameters);
	}

	public final void exportToWriter(FileFormat format, Writer writer, Object... parameters)
			throws MatrixException, IOException {
		ExportMatrix.toWriter(format, writer, this, parameters);
	}

	public final void setLabel(String label) {
		setMatrixAnnotation(label);
	}

	public final String getLabel() {
		Object o = getMatrixAnnotation();
		if (o == null) {
			return null;
		}
		if (o instanceof String) {
			return (String) o;
		}
		if (o instanceof HasLabel) {
			return ((HasLabel) o).getLabel();
		}
		return o.toString();
	}

	public void setAsString(String string, long... coordinates) throws MatrixException {
		setAsObject(string, coordinates);
	}

	public boolean isReadOnly() {
		return false;
	}

	public String getAsString(long... coordinates) throws MatrixException {
		return StringUtil.convert(getAsObject(coordinates));
	}

	public final double getMaxValue() throws MatrixException {
		return Max.calc(this);
	}

	public final double getMinValue() throws MatrixException {
		return Min.calc(this);
	}

	public final double getMeanValue() throws MatrixException {
		return Mean.calc(this);
	}

	public final double getStdValue() throws MatrixException {
		return std(Ret.NEW, Matrix.ALL, true).getEuklideanValue();
	}

	public final double getValueSum() throws MatrixException {
		double sum = 0.0;
		for (long[] c : allCoordinates()) {
			sum += getAsDouble(c);
		}
		return sum;
	}

	public final double getAbsoluteValueSum() throws MatrixException {
		double sum = 0.0;
		for (long[] c : allCoordinates()) {
			sum += Math.abs(getAsDouble(c));
		}
		return sum;
	}

	public final String getColumnLabel(long col) {
		return StringUtil.convert(getAxisAnnotation(COLUMN, col));
	}

	public final String getRowLabel(long row) {
		return StringUtil.convert(getAxisAnnotation(ROW, row));
	}

	public final long getRowForLabel(Object object) {
		return getPositionForLabel(ROW, object);
	}

	public final long getColumnForLabel(Object object) {
		return getPositionForLabel(COLUMN, object);
	}

	public final long getPositionForLabel(int dimension, Object object) {
		return annotation == null ? -1 : annotation.getPositionForLabel(dimension, object);
	}

	public final Object getRowObject(long row) {
		return getAxisAnnotation(ROW, row);
	}

	public final Object getColumnObject(long col) {
		return getAxisAnnotation(COLUMN, col);
	}

	public final void setColumnLabel(long col, String label) {
		setAxisAnnotation(COLUMN, col, label);
	}

	public final void setRowLabel(long row, String label) {
		setAxisAnnotation(ROW, row, label);
	}

	public final void setRowObject(long row, Object o) {
		setAxisAnnotation(ROW, row, o);
	}

	public final void setColumnObject(long col, Object o) {
		setAxisAnnotation(COLUMN, col, o);
	}

	public final double getAbsoluteValueMean() throws MatrixException {
		return getAbsoluteValueSum() / getValueCount();
	}

	public final Matrix toRowVector() throws MatrixException {
		if (isRowVector()) {
			return this;
		} else if (isColumnVector()) {
			return transpose();
		} else {
			return reshape(Coordinates.product(getSize()), 1);
		}
	}

	public final Matrix toColumnVector() throws MatrixException {
		if (isColumnVector()) {
			return this;
		} else if (isRowVector()) {
			return transpose();
		} else {
			return reshape(1, (int) Coordinates.product(getSize()));
		}
	}

	public Matrix replaceMissingBy(Matrix matrix) throws MatrixException {
		Matrix ret = MatrixFactory.zeros(getSize());
		for (long[] c : allCoordinates()) {
			double v = getAsDouble(c);
			if (MathUtil.isNaNOrInfinite(v)) {
				ret.setAsDouble(matrix.getAsDouble(c), c);
			} else {
				ret.setAsDouble(getAsDouble(c), c);
			}
		}
		return ret;
	}

	public final Matrix deleteColumnsWithMissingValues(Ret returnType) throws MatrixException {
		Matrix mv = countMissing(Ret.NEW, Matrix.ROW);
		List<Long> sel = new ArrayList<Long>();
		for (long c = 0; c < mv.getColumnCount(); c++) {
			if (mv.getAsDouble(0, c) == 0.0)
				sel.add(c);
		}
		long[] longsel = new long[sel.size()];
		for (int i = sel.size(); --i >= 0;) {
			longsel[i] = sel.get(i);
		}
		return selectColumns(returnType, longsel);
	}

	public final Matrix deleteRowsWithMissingValues(Ret returnType, long threshold)
			throws MatrixException {
		Matrix mv = countMissing(Ret.NEW, Matrix.COLUMN);
		List<Long> sel = new ArrayList<Long>();
		for (long r = 0; r < mv.getRowCount(); r++) {
			if (mv.getAsLong(r, 0) < threshold)
				sel.add(r);
		}
		if (sel.isEmpty()) {
			return MatrixFactory.emptyMatrix();
		} else {
			return selectRows(returnType, sel);
		}
	}

	public final Matrix appendHorizontally(Matrix m) throws MatrixException {
		return append(COLUMN, m);
	}

	public Iterable<Object> allValues() {
		return new Iterable<Object>() {

			public Iterator<Object> iterator() {
				return new Iterator<Object>() {

					private Iterator<long[]> it = allCoordinates().iterator();

					public boolean hasNext() {
						return it.hasNext();
					}

					public Object next() {
						return getAsObject(it.next());
					}

					public void remove() {
						it.remove();
					}
				};
			}
		};
	}

	public final Matrix appendVertically(Matrix m) throws MatrixException {
		return append(ROW, m);
	}

	public final Matrix append(int dimension, Matrix m) throws MatrixException {
		return MatrixFactory.concat(dimension, this, m);
	}

	public final Matrix discretizeToColumns(long column) throws MatrixException {
		return new DiscretizeToColumns(this, false, column).calc(Ret.NEW);
	}

	public final Matrix subMatrix(Ret returnType, long startRow, long startColumn, long endRow,
			long endColumn) throws MatrixException {
		long[] rows = MathUtil.sequenceLong(startRow, endRow);
		long[] columns = MathUtil.sequenceLong(startColumn, endColumn);
		return select(returnType, rows, columns);
	}

	public Matrix[] svd() throws MatrixException {
		return SVD.calcNew(this);
	}

	public Matrix[] evd() throws MatrixException {
		return EVD.calcNew(this);
	}

	public Matrix[] qr() throws MatrixException {
		return QR.calcNew(this);
	}

	public Matrix[] lu() throws MatrixException {
		return LU.calcNew(this);
	}

	public final String exportToString(FileFormat format, Object... parameters)
			throws MatrixException, IOException {
		return ExportMatrix.toString(format, this, parameters);
	}

	public void setSize(long... size) {
		throw new MatrixException("operation not possible: cannot change size of matrix");
	}

	public final Matrix reshape(long... newSize) {
		return new ReshapedObjectMatrix(this, newSize);
	}

	public final double doubleValue() throws MatrixException {
		if (isScalar()) {
			return getAsDouble(0, 0);
		}
		return getMeanValue();
	}

	public final int intValue() throws MatrixException {
		if (isScalar()) {
			return getAsInt(0, 0);
		}
		return (int) getMeanValue();
	}

	public final char charValue() throws MatrixException {
		if (isScalar()) {
			return getAsChar(0, 0);
		}
		return (char) getMeanValue();
	}

	public final BigInteger bigIntegerValue() throws MatrixException {
		if (isScalar()) {
			return getAsBigInteger(0, 0);
		}
		return BigInteger.valueOf((long) getMeanValue());
	}

	public final BigDecimal bigDecimalValue() throws MatrixException {
		if (isScalar()) {
			return getAsBigDecimal(0, 0);
		}
		return BigDecimal.valueOf(getMeanValue());
	}

	public final Matrix fadeIn(Ret ret, int dimension) throws MatrixException {
		return new FadeIn(dimension, this).calc(ret);
	}

	public final Matrix fadeOut(Ret ret, int dimension) throws MatrixException {
		return new FadeOut(dimension, this).calc(ret);
	}

	public final float floatValue() throws MatrixException {
		if (isScalar()) {
			return getAsFloat(0, 0);
		}
		return (float) getMeanValue();
	}

	public final long longValue() throws MatrixException {
		if (isScalar()) {
			return getAsLong(0, 0);
		}
		return (long) getMeanValue();
	}

	public final Date dateValue() throws MatrixException {
		if (isScalar()) {
			return getAsDate(0, 0);
		}
		return MathUtil.getDate(getMeanValue());
	}

	public final boolean booleanValue() throws MatrixException {
		if (isScalar()) {
			return getAsBoolean(0, 0);
		}
		return getMeanValue() != 0;
	}

	public final String stringValue() throws MatrixException {
		if (isScalar()) {
			return getAsString(0, 0);
		} else {
			return toString();
		}
	}

	public final double getRMS() throws MatrixException {
		double sum = 0.0;
		long count = 0;
		for (long[] c : allCoordinates()) {
			sum += Math.pow(getAsDouble(c), 2.0);
			count++;
		}
		sum /= count;
		return Math.sqrt(sum);
	}

	public final Annotation getAnnotation() {
		return annotation;
	}

	public final void setAnnotation(Annotation annotation) {
		this.annotation = annotation;
	}

	public final boolean equalsAnnotation(Object o) {
		if (this == o) {
			return true;
		}
		if (o instanceof Matrix) {
			Matrix m = (Matrix) o;
			Annotation a1 = getAnnotation();
			Annotation a2 = m.getAnnotation();
			if (a1 != null) {
				if (!a1.equals(a2)) {
					return false;
				}
			} else if (a2 != null) {
				return false;
			}
		}
		return true;
	}

	public final boolean equals(Object o) {
		return equalsContent(o) && equalsAnnotation(o);
	}

	public final boolean equalsContent(Object o) {
		try {
			if (this == o) {
				return true;
			}
			if (o instanceof Matrix) {
				Matrix m = (Matrix) o;
				if (!Coordinates.equals(getSize(), m.getSize())) {
					return false;
				}
				if (isSparse() && m.isSparse()) {
					for (long[] c : availableCoordinates()) {
						Object o1 = getAsObject(c);
						Object o2 = m.getAsObject(c);
						if ((o1 == null && o2 != null) || (o1 != null && o2 == null)) {
							return false;
						}
						if (o1 != null && o2 != null) {
							if (!o1.equals(o2)) {
								return false;
							}
						} else {
							return false;
						}
					}
					for (long[] c : m.availableCoordinates()) {
						Object o1 = getAsObject(c);
						Object o2 = m.getAsObject(c);
						if ((o1 == null && o2 != null) || (o1 != null && o2 == null)) {
							return false;
						}
						if (o1 != null && o2 != null) {
							if (!o1.equals(o2)) {
								return false;
							}
						} else {
							return false;
						}
					}
				} else {
					for (long[] c : allCoordinates()) {
						Object o1 = getAsObject(c);
						Object o2 = m.getAsObject(c);
						if ((o1 == null && o2 != null) || (o1 != null && o2 == null)) {
							return false;
						}
						if (o1 != null && o2 != null) {
							if (o1.getClass().equals(o2.getClass())) {
								if (!o1.equals(o2)) {
									return false;
								}
							} else {
								if (!MathUtil.equals(o1, o2)) {
									return false;
								}
							}
						} else if (o1 == null && o2 == null) {
						} else {
							return false;
						}
					}
				}
				return true;
			}
			return false;
		} catch (Exception e) {
			logger.log(Level.WARNING, "could not compare", e);
			return false;
		}
	}

	public final BooleanMatrix toBooleanMatrix() {
		return new ToBooleanMatrix(this).calcLink();
	}

	public final ByteMatrix toByteMatrix() {
		return new ToByteMatrix(this).calcLink();
	}

	public final CharMatrix toCharMatrix() {
		return new ToCharMatrix(this).calcLink();
	}

	public final DateMatrix toDateMatrix() {
		return new ToDateMatrix(this).calcLink();
	}

	public final DoubleMatrix toDoubleMatrix() {
		return new ToDoubleMatrix(this).calcLink();
	}

	public final FloatMatrix toFloatMatrix() {
		return new ToFloatMatrix(this).calcLink();
	}

	public final IntMatrix toIntMatrix() {
		return new ToIntMatrix(this).calcLink();
	}

	public final LongMatrix toLongMatrix() {
		return new ToLongMatrix(this).calcLink();
	}

	public final ObjectMatrix toObjectMatrix() {
		return new ToObjectMatrix(this).calcLink();
	}

	public final ShortMatrix toShortMatrix() {
		return new ToShortMatrix(this).calcLink();
	}

	public final StringMatrix toStringMatrix() {
		return new ToStringMatrix(this).calcLink();
	}

	public double norm1() {
		long rows = getRowCount();
		long cols = getColumnCount();
		double max = 0;
		for (long c = 0; c < cols; c++) {
			double sum = 0;
			for (long r = 0; r < rows; r++) {
				sum += Math.abs(getAsDouble(r, c));
			}
			max = Math.max(max, sum);
		}
		return max;
	}

	public double norm2() {
		return svd()[1].getAsDouble(0, 0);
	}

	public double normInf() {
		long rows = getRowCount();
		long cols = getColumnCount();
		double max = 0;
		for (long r = 0; r < rows; r++) {
			double sum = 0;
			for (long c = 0; c < cols; c++) {
				sum += Math.abs(getAsDouble(r, c));
			}
			max = Math.max(max, sum);
		}
		return max;
	}

	public double normF() {
		long rows = getRowCount();
		long cols = getColumnCount();
		double result = 0;
		for (long ro = 0; ro < rows; ro++) {
			for (long c = 0; c < cols; c++) {
				double b = getAsDouble(ro, c);
				double temp = 0.0;
				if (Math.abs(result) > Math.abs(b)) {
					temp = b / result;
					temp = Math.abs(result) * Math.sqrt(1 + temp * temp);
				} else if (b != 0) {
					temp = result / b;
					temp = Math.abs(b) * Math.sqrt(1 + temp * temp);
				} else {
					temp = 0.0;
				}
				result = temp;
			}
		}
		return result;
	}

	public ListMatrix<?> toListMatrix() {
		if (this instanceof ListMatrix<?>) {
			return (ListMatrix<?>) this;
		} else {
			ListMatrix<Object> list = new DefaultListMatrix<Object>();
			for (int row = 0; row < getRowCount(); row++) {
				list.add(getAsObject(row, 0));
			}
			return list;
		}
	}

	public SetMatrix<?> toSetMatrix() {
		if (this instanceof SetMatrix<?>) {
			return (SetMatrix<?>) this;
		} else {
			SetMatrix<Object> set = new DefaultSetMatrix<Object>();
			for (int row = 0; row < getRowCount(); row++) {
				set.add(getAsObject(row, 0));
			}
			return set;
		}
	}

	public MapMatrix<?, ?> toMapMatrix() {
		if (this instanceof MapMatrix<?, ?>) {
			return (MapMatrix<?, ?>) this;
		} else {
			MapMatrix<Object, Object> map = new DefaultMapMatrix<Object, Object>();
			for (int row = 0; row < getRowCount(); row++) {
				map.put(getAsObject(row, 0), getAsObject(row, 1));
			}
			return map;
		}
	}

	public final boolean isSparse() {
		switch (getStorageType()) {
		case DENSE:
			return false;
		case SET:
			return false;
		case TREE:
			return true;
		case MAP:
			return false;
		case LIST:
			return false;
		case SPARSE:
			return true;
		case GRAPH:
			return true;
		default:
			throw new MatrixException("unknown storage type: " + getStorageType());
		}
	}

	public boolean containsBigInteger(BigInteger v) {
		for (long[] c : availableCoordinates()) {
			if (v.equals(getAsBigInteger(c))) {
				return true;
			}
		}
		return false;
	}

	public boolean containsBigDecimal(BigDecimal v) {
		for (long[] c : availableCoordinates()) {
			if (v.equals(getAsBigDecimal(c))) {
				return true;
			}
		}
		return false;
	}

	public boolean containsDate(Date v) {
		for (long[] c : availableCoordinates()) {
			if (v.equals(getAsDate(c))) {
				return true;
			}
		}
		return false;
	}

	public boolean containsObject(Object o) {
		for (long[] c : availableCoordinates()) {
			if (o.equals(getAsObject(c))) {
				return true;
			}
		}
		return false;
	}

	public boolean containsString(String s) {
		for (long[] c : availableCoordinates()) {
			if (s.equals(getAsString(c))) {
				return true;
			}
		}
		return false;
	}

	public boolean containsBoolean(boolean v) {
		for (long[] c : availableCoordinates()) {
			if (getAsBoolean(c) == v) {
				return true;
			}
		}
		return false;
	}

	public boolean containsByte(byte v) {
		for (long[] c : availableCoordinates()) {
			if (getAsByte(c) == v) {
				return true;
			}
		}
		return false;
	}

	public boolean containsChar(char v) {
		for (long[] c : availableCoordinates()) {
			if (getAsChar(c) == v) {
				return true;
			}
		}
		return false;
	}

	public boolean containsDouble(double v) {
		for (long[] c : availableCoordinates()) {
			if (getAsDouble(c) == v) {
				return true;
			}
		}
		return false;
	}

	public boolean containsFloat(float v) {
		for (long[] c : availableCoordinates()) {
			if (getAsFloat(c) == v) {
				return true;
			}
		}
		return false;
	}

	public boolean containsInt(int v) {
		for (long[] c : availableCoordinates()) {
			if (getAsInt(c) == v) {
				return true;
			}
		}
		return false;
	}

	public boolean containsLong(long v) {
		for (long[] c : availableCoordinates()) {
			if (getAsLong(c) == v) {
				return true;
			}
		}
		return false;
	}

	public boolean containsShort(short v) {
		for (long[] c : availableCoordinates()) {
			if (getAsShort(c) == v) {
				return true;
			}
		}
		return false;
	}

	public boolean containsNull() {
		for (long[] c : allCoordinates()) {
			if (getAsDouble(c) == 0.0) {
				return true;
			}
		}
		return false;
	}

}
