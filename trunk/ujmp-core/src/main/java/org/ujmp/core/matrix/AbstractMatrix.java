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
import org.ujmp.core.doublematrix.calculation.general.decomposition.Ginv;
import org.ujmp.core.doublematrix.calculation.general.decomposition.Pinv;
import org.ujmp.core.doublematrix.calculation.general.decomposition.Princomp;
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
import org.ujmp.core.intmatrix.calculation.ToIntMatrix;
import org.ujmp.core.io.ExportMatrix;
import org.ujmp.core.listmatrix.DefaultListMatrix;
import org.ujmp.core.listmatrix.ListMatrix;
import org.ujmp.core.longmatrix.LongMatrix;
import org.ujmp.core.longmatrix.calculation.DocTerm;
import org.ujmp.core.longmatrix.calculation.ToLongMatrix;
import org.ujmp.core.mapmatrix.DefaultMapMatrix;
import org.ujmp.core.mapmatrix.MapMatrix;
import org.ujmp.core.objectmatrix.ObjectMatrix;
import org.ujmp.core.objectmatrix.calculation.Bootstrap;
import org.ujmp.core.objectmatrix.calculation.Convert;
import org.ujmp.core.objectmatrix.calculation.Deletion;
import org.ujmp.core.objectmatrix.calculation.Distinct;
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

	@Override
	public final long getCoreObjectId() {
		return id;
	}

	@Override
	public double getAsDouble(long... coordinates) {
		return MathUtil.getDouble(getAsObject(coordinates));
	}

	@Override
	public void setAsDouble(double v, long... coordinates) {
		setAsObject(v, coordinates);
	}

	@Override
	public final Object getPreferredObject(long... coordinates) throws MatrixException {
		return MathUtil.getPreferredObject(getAsObject(coordinates));
	}

	@Override
	public final Object getMatrixAnnotation() {
		return annotation == null ? null : annotation.getMatrixAnnotation();
	}

	@Override
	public ValueType getValueType() {
		return ValueType.OBJECT;
	}

	@Override
	public final void setMatrixAnnotation(Object value) {
		if (annotation == null) {
			annotation = new DefaultAnnotation();
		}
		annotation.setMatrixAnnotation(value);
	}

	@Override
	public final Object getAxisAnnotation(int axis, long positionOnAxis) {
		return annotation == null ? null : annotation.getAxisAnnotation(axis, positionOnAxis);
	}

	@Override
	public final Object getAxisAnnotation(int axis) {
		return annotation == null ? null : annotation.getAxisAnnotation(axis);
	}

	@Override
	public final void setAxisAnnotation(int axis, long positionOnAxis, Object value) {
		if (annotation == null) {
			annotation = new DefaultAnnotation();
		}
		annotation.setAxisAnnotation(axis, positionOnAxis, value);
	}

	@Override
	public final void setAxisAnnotation(int axis, Object value) {
		if (annotation == null) {
			annotation = new DefaultAnnotation();
		}
		annotation.setAxisAnnotation(axis, value);
	}

	@Override
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

	@Override
	public final boolean containsMissingValues() throws MatrixException {
		for (long[] c : allCoordinates()) {
			double v = getAsDouble(c);
			if (v != v || v == Double.NEGATIVE_INFINITY || v == Double.POSITIVE_INFINITY) {
				return true;
			}
		}
		return false;
	}

	@Override
	public final double getEuklideanValue() throws MatrixException {
		double sum = 0.0;
		for (long[] c : allCoordinates()) {
			sum += Math.pow(getAsDouble(c), 2.0);
		}
		return Math.sqrt(sum);
	}

	@Override
	public final Matrix clone() throws CloneNotSupportedException {
		try {
			return copy();
		} catch (MatrixException e) {
			logger.log(Level.WARNING, "Could not clone Matrix, returning original Matrix", e);
			return this;
		}
	}

	@Override
	public final Matrix select(Ret returnType, long[]... selection) throws MatrixException {
		return new Selection(this, selection).calc(returnType);
	}

	@Override
	public final Matrix select(Ret returnType, Collection<? extends Number>... selection)
			throws MatrixException {
		return new Selection(this, selection).calc(returnType);
	}

	@Override
	public Matrix selectRows(Ret returnType, long... rows) throws MatrixException {
		return select(returnType, rows, null);
	}

	@Override
	public final Matrix select(Ret returnType, String selection) throws MatrixException {
		return new Selection(this, selection).calc(returnType);
	}

	@Override
	public Matrix selectColumns(Ret returnType, long... columns) throws MatrixException {
		return select(returnType, null, columns);
	}

	@Override
	@SuppressWarnings("unchecked")
	public final Matrix selectRows(Ret returnType, Collection<? extends Number> rows)
			throws MatrixException {
		return select(returnType, rows, null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public final Matrix selectColumns(Ret returnType, Collection<? extends Number> columns)
			throws MatrixException {
		return select(returnType, null, columns);
	}

	@Override
	public Matrix impute(Ret returnType, ImputationMethod method, Object... parameters)
			throws MatrixException {
		return new Impute(this, method, parameters).calc(returnType);
	}

	@Override
	public Matrix indexOfMax(Ret returnType, int dimension) throws MatrixException {
		return new IndexOfMax(dimension, this).calc(returnType);
	}

	@Override
	public Matrix indexOfMin(Ret returnType, int dimension) throws MatrixException {
		return new IndexOfMin(dimension, this).calc(returnType);
	}

	@Override
	public Matrix standardize(Ret returnType, int dimension, boolean ignoreNaN)
			throws MatrixException {
		return new Standardize(ignoreNaN, dimension, this).calc(returnType);
	}

	@Override
	public Matrix atimes(Ret returnType, boolean ignoreNaN, Matrix matrix) throws MatrixException {
		return new Atimes(ignoreNaN, this, matrix).calc(returnType);
	}

	@Override
	public Matrix inv() throws MatrixException {
		if (getDimensionCount() != 2 || getRowCount() != getColumnCount()) {
			throw new MatrixException(
					"inverse only possible for square matrices. use pinv or ginv instead");
		}
		return ginv();
	}

	@Override
	public Matrix ginv() throws MatrixException {
		return new Ginv(this).calcNew();
	}

	@Override
	public Matrix princomp() throws MatrixException {
		return new Princomp(this).calcNew();
	}

	@Override
	public Matrix pinv() throws MatrixException {
		return new Pinv(this).calcNew();
	}

	@Override
	public Matrix center(Ret returnType, int dimension, boolean ignoreNaN) throws MatrixException {
		return new Center(ignoreNaN, dimension, this).calc(returnType);
	}

	@Override
	public Matrix copy() throws MatrixException {
		return Convert.calcNew(this);
	}

	@Override
	public boolean isResizeable() {
		return false;
	}

	@Override
	public final Matrix convert(ValueType newValueType) throws MatrixException {
		return Convert.calcNew(newValueType, this);
	}

	@Override
	public final Matrix replaceRegex(Ret returnType, Pattern search, String replacement)
			throws MatrixException {
		return new ReplaceRegex(this, search, replacement).calc(returnType);
	}

	@Override
	public final Matrix replace(Ret returnType, Object search, Object replacement)
			throws MatrixException {
		return new Replace(this, search, replacement).calc(returnType);
	}

	@Override
	public final Matrix replaceRegex(Ret returnType, String search, String replacement)
			throws MatrixException {
		return new ReplaceRegex(this, search, replacement).calc(returnType);
	}

	@Override
	public Matrix times(double factor) throws MatrixException {
		return Times.calc(false, this, factor);
	}

	@Override
	public Matrix times(Matrix matrix) throws MatrixException {
		return Times.calc(false, this, matrix);
	}

	@Override
	public Matrix divide(Matrix m) throws MatrixException {
		return Divide.calc(this, m);
	}

	@Override
	public Matrix divide(double factor) throws MatrixException {
		return Divide.calc(this, factor);
	}

	@Override
	public Matrix divide(Ret returnType, boolean ignoreNaN, double factor) throws MatrixException {
		return new Divide(ignoreNaN, this, factor).calc(returnType);
	}

	@Override
	public Matrix times(Ret returnType, boolean ignoreNaN, double factor) throws MatrixException {
		return new Times(ignoreNaN, this, factor).calc(returnType);
	}

	@Override
	public Matrix times(Ret returnType, boolean ignoreNaN, Matrix factor) throws MatrixException {
		return new Times(ignoreNaN, this, factor).calc(returnType);
	}

	@Override
	public Matrix divide(Ret returnType, boolean ignoreNaN, Matrix factor) throws MatrixException {
		return new Divide(ignoreNaN, this, factor).calc(returnType);
	}

	@Override
	public final Matrix power(Ret returnType, double power) throws MatrixException {
		return new Power(this, power).calc(returnType);
	}

	@Override
	public final Matrix power(Ret returnType, Matrix power) throws MatrixException {
		return new Power(this, power).calc(returnType);
	}

	@Override
	public final Matrix gt(Ret returnType, Matrix matrix) throws MatrixException {
		return new Gt(this, matrix).calc(returnType);
	}

	@Override
	public final Matrix gt(Ret returnType, double value) throws MatrixException {
		return new Gt(this, value).calc(returnType);
	}

	@Override
	public final Matrix and(Ret returnType, Matrix matrix) throws MatrixException {
		return new And(this, matrix).calc(returnType);
	}

	@Override
	public final Matrix and(Ret returnType, boolean value) throws MatrixException {
		return new And(this, value).calc(returnType);
	}

	@Override
	public final Matrix or(Ret returnType, Matrix matrix) throws MatrixException {
		return new Or(this, matrix).calc(returnType);
	}

	@Override
	public final Matrix or(Ret returnType, boolean value) throws MatrixException {
		return new Or(this, value).calc(returnType);
	}

	@Override
	public final Matrix xor(Ret returnType, Matrix matrix) throws MatrixException {
		return new Xor(this, matrix).calc(returnType);
	}

	@Override
	public final Matrix xor(Ret returnType, boolean value) throws MatrixException {
		return new Xor(this, value).calc(returnType);
	}

	@Override
	public final Matrix not(Ret returnType) throws MatrixException {
		return new Not(this).calc(returnType);
	}

	@Override
	public final Matrix lt(Ret returnType, Matrix matrix) throws MatrixException {
		return new Lt(this, matrix).calc(returnType);
	}

	@Override
	public final Matrix lt(Ret returnType, double value) throws MatrixException {
		return new Lt(this, value).calc(returnType);
	}

	@Override
	public final Matrix ge(Ret returnType, Matrix matrix) throws MatrixException {
		return new Ge(this, matrix).calc(returnType);
	}

	@Override
	public final Matrix ge(Ret returnType, double value) throws MatrixException {
		return new Ge(this, value).calc(returnType);
	}

	@Override
	public final Matrix le(Ret returnType, Matrix matrix) throws MatrixException {
		return new Le(this, matrix).calc(returnType);
	}

	@Override
	public final Matrix le(Ret returnType, double value) throws MatrixException {
		return new Le(this, value).calc(returnType);
	}

	@Override
	public final Matrix eq(Ret returnType, Matrix matrix) throws MatrixException {
		return new Eq(this, matrix).calc(returnType);
	}

	@Override
	public final Matrix eq(Ret returnType, Object value) throws MatrixException {
		return new Eq(this, value).calc(returnType);
	}

	@Override
	public final Matrix ne(Ret returnType, Matrix matrix) throws MatrixException {
		return new Ne(this, matrix).calc(returnType);
	}

	@Override
	public final Matrix ne(Ret returnType, Object value) throws MatrixException {
		return new Ne(this, value).calc(returnType);
	}

	@Override
	public long getValueCount() {
		return Coordinates.product(getSize());
	}

	@Override
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

	@Override
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

	@Override
	public Iterable<long[]> selectedCoordinates(String selection) throws MatrixException {
		return select(Ret.LINK, selection).allCoordinates();
	}

	@Override
	public Iterable<long[]> selectedCoordinates(long[]... selection) throws MatrixException {
		return select(Ret.LINK, selection).allCoordinates();
	}

	@Override
	public boolean isTransient() {
		return false;
	}

	@Override
	public Iterable<long[]> nonZeroCoordinates() {
		return availableCoordinates();
	}

	@Override
	public Iterable<long[]> availableCoordinates() {
		return allCoordinates();
	}

	@Override
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

	@Override
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

	@Override
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

	@Override
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

	@Override
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

	@Override
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

	@Override
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

	@Override
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

	@Override
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

	@Override
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

	@Override
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

	@Override
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

	@Override
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

	@Override
	public final Matrix sqrt(Ret returnType) throws MatrixException {
		return new Sqrt(this).calc(returnType);
	}

	@Override
	public final Matrix round(Ret returnType) throws MatrixException {
		return new Round(this).calc(returnType);
	}

	@Override
	public final Matrix ceil(Ret returnType) throws MatrixException {
		return new Ceil(this).calc(returnType);
	}

	@Override
	public final Matrix floor(Ret returnType) throws MatrixException {
		return new Floor(this).calc(returnType);
	}

	@Override
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

	@Override
	public void notifyGUIObject() {
		if (guiObject != null) {
			guiObject.fireValueChanged();
		}
	}

	@Override
	public Matrix mtimes(Matrix matrix) throws MatrixException {
		return DoubleCalculations.mtimes.calc(this, matrix);
	}

	@Override
	public Matrix mtimes(Ret returnType, boolean ignoreNaN, Matrix matrix) throws MatrixException {
		return new Mtimes(ignoreNaN, this, matrix).calc(returnType);
	}

	@Override
	public Matrix mtimes(double value) throws MatrixException {
		return times(value);
	}

	@Override
	public Matrix mtimes(Ret returnType, boolean ignoreNaN, double value) throws MatrixException {
		return times(returnType, ignoreNaN, value);
	}

	@Override
	public boolean getAsBoolean(long... coordinates) throws MatrixException {
		return MathUtil.getBoolean(getAsObject(coordinates));
	}

	@Override
	public void setAsBoolean(boolean value, long... coordinates) throws MatrixException {
		setAsDouble(value ? 1.0 : 0.0, coordinates);
	}

	@Override
	public int getAsInt(long... coordinates) throws MatrixException {
		return (int) getAsDouble(coordinates);
	}

	@Override
	public void setAsInt(int value, long... coordinates) throws MatrixException {
		setAsDouble(value, coordinates);
	}

	@Override
	public byte getAsByte(long... coordinates) throws MatrixException {
		return (byte) getAsDouble(coordinates);
	}

	@Override
	public void setAsByte(byte value, long... coordinates) throws MatrixException {
		setAsDouble(value, coordinates);
	}

	@Override
	public char getAsChar(long... coordinates) throws MatrixException {
		return (char) getAsDouble(coordinates);
	}

	@Override
	public BigInteger getAsBigInteger(long... coordinates) throws MatrixException {
		return MathUtil.getBigInteger(getAsObject(coordinates));
	}

	@Override
	public BigDecimal getAsBigDecimal(long... coordinates) throws MatrixException {
		return MathUtil.getBigDecimal(getAsObject(coordinates));
	}

	@Override
	public void setAsChar(char value, long... coordinates) throws MatrixException {
		setAsDouble(value, coordinates);
	}

	@Override
	public void setAsBigDecimal(BigDecimal value, long... coordinates) throws MatrixException {
		if (value == null) {
			setAsDouble(0, coordinates);
		} else {
			setAsDouble(value.doubleValue(), coordinates);
		}
	}

	@Override
	public void setAsBigInteger(BigInteger value, long... coordinates) throws MatrixException {
		if (value == null) {
			setAsLong(0, coordinates);
		} else {
			setAsLong(value.longValue(), coordinates);
		}
	}

	@Override
	public float getAsFloat(long... coordinates) throws MatrixException {
		return (float) getAsDouble(coordinates);
	}

	@Override
	public void setAsFloat(float value, long... coordinates) throws MatrixException {
		setAsDouble(value, coordinates);
	}

	@Override
	public short getAsShort(long... coordinates) throws MatrixException {
		return (short) getAsDouble(coordinates);
	}

	@Override
	public Matrix getAsMatrix(long... coordinates) throws MatrixException {
		return MathUtil.getMatrix(getAsObject(coordinates));
	}

	@Override
	public void setAsMatrix(Matrix m, long... coordinates) throws MatrixException {
		setAsObject(m, coordinates);
	}

	@Override
	public void setAsShort(short value, long... coordinates) throws MatrixException {
		setAsDouble(value, coordinates);
	}

	@Override
	public long getAsLong(long... coordinates) throws MatrixException {
		return (long) getAsDouble(coordinates);
	}

	@Override
	public void setAsLong(long value, long... coordinates) throws MatrixException {
		setAsDouble(value, coordinates);
	}

	@Override
	public Date getAsDate(long... coordinates) throws MatrixException {
		return MathUtil.getDate(getAsObject(coordinates));
	}

	@Override
	public void setAsDate(Date date, long... coordinates) throws MatrixException {
		setAsObject(date, coordinates);
	}

	@Override
	public final Matrix delete(Ret returnType, String selection) throws MatrixException {
		return new Deletion(this, selection).calc(returnType);
	}

	@Override
	public final Matrix delete(Ret returnType, Collection<? extends Number>... selection)
			throws MatrixException {
		return new Deletion(this, selection).calc(returnType);
	}

	@Override
	public final Matrix delete(Ret returnType, long[]... selection) throws MatrixException {
		return new Deletion(this, selection).calc(returnType);
	}

	@Override
	public final Matrix deleteRows(Ret returnType, long... rows) throws MatrixException {
		return delete(returnType, rows, new long[] {});
	}

	@Override
	@SuppressWarnings("unchecked")
	public final Matrix deleteRows(Ret returnType, Collection<? extends Number> rows)
			throws MatrixException {
		return delete(returnType, rows, new ArrayList<Long>());
	}

	@Override
	@SuppressWarnings("unchecked")
	public final Matrix deleteColumns(Ret returnType, Collection<? extends Number> columns)
			throws MatrixException {
		return delete(returnType, new ArrayList<Long>(), columns);
	}

	@Override
	public final Matrix deleteColumns(Ret returnType, long... columns) throws MatrixException {
		return delete(returnType, new long[] {}, columns);
	}

	@Override
	public Matrix minus(Ret returnType, boolean ignoreNaN, double v) throws MatrixException {
		return new Minus(ignoreNaN, this, v).calc(returnType);
	}

	@Override
	public Matrix minus(Ret returnType, boolean ignoreNaN, Matrix m) throws MatrixException {
		return new Minus(ignoreNaN, this, m).calc(returnType);
	}

	@Override
	public Matrix plus(Ret returnType, boolean ignoreNaN, double v) throws MatrixException {
		return new Plus(ignoreNaN, this, v).calc(returnType);
	}

	@Override
	public Matrix plus(Ret returnType, boolean ignoreNaN, Matrix m) throws MatrixException {
		return new Plus(ignoreNaN, this, m).calc(returnType);
	}

	@Override
	public Matrix transpose() throws MatrixException {
		return Transpose.calc(this);
	}

	@Override
	public Matrix transpose(Ret returnType) throws MatrixException {
		return new Transpose(this).calc(returnType);
	}

	@Override
	public Matrix mean(Ret returnType, int dimension, boolean ignoreNaN) throws MatrixException {
		return new Mean(dimension, ignoreNaN, this).calc(returnType);
	}

	@Override
	public Matrix var(Ret returnType, int dimension, boolean ignoreNaN) throws MatrixException {
		return new Var(dimension, ignoreNaN, this).calc(returnType);
	}

	@Override
	public Matrix std(Ret returnType, int dimension, boolean ignoreNaN) throws MatrixException {
		return new Std(dimension, ignoreNaN, this).calc(returnType);
	}

	@Override
	public long getColumnCount() {
		return getSize(COLUMN);
	}

	@Override
	public long getRowCount() {
		return getSize(ROW);
	}

	@Override
	public long getZCount() {
		return getSize(Z);
	}

	@Override
	public final long getSize(int dimension) {
		return getSize()[dimension];
	}

	@Override
	public Matrix prod(Ret returnType, int dimension, boolean ignoreNaN) throws MatrixException {
		return new Prod(dimension, ignoreNaN, this).calc(returnType);
	}

	@Override
	public Matrix diff(Ret returnType, int dimension, boolean ignoreNaN) throws MatrixException {
		return new Diff(dimension, ignoreNaN, this).calc(returnType);
	}

	@Override
	public final Matrix sum(Ret returnType, int dimension, boolean ignoreNaN)
			throws MatrixException {
		return new Sum(dimension, ignoreNaN, this).calc(returnType);
	}

	@Override
	public final Matrix sign(Ret returnType) throws MatrixException {
		return new Sign(this).calc(returnType);
	}

	@Override
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

	@Override
	public final int getDimensionCount() {
		return getSize().length;
	}

	@Override
	public final Matrix ones(Ret ret) throws MatrixException {
		return new Ones(this).calc(ret);
	}

	@Override
	public final Matrix fill(Ret ret, Object value) throws MatrixException {
		return new Fill(this, value).calc(ret);
	}

	@Override
	public final Matrix zeros(Ret ret) throws MatrixException {
		return new Zeros(this).calc(ret);
	}

	@Override
	public final Matrix eye(Ret ret) throws MatrixException {
		return new Eye(this).calc(ret);
	}

	@Override
	public Matrix plus(double v) throws MatrixException {
		return Plus.calc(false, this, v);
	}

	@Override
	public Matrix plus(Matrix m) throws MatrixException {
		return Plus.calc(false, this, m);
	}

	@Override
	public Matrix minus(double v) throws MatrixException {
		return Minus.calc(false, this, v);
	}

	@Override
	public Matrix minus(Matrix m) throws MatrixException {
		return Minus.calc(false, this, m);
	}

	@Override
	public final Matrix link() throws MatrixException {
		return toObjectMatrix();
	}

	@Override
	public void clear() {
		new Zeros(this).calc(Ret.ORIG);
	}

	@Override
	public final Matrix rand(Ret ret) throws MatrixException {
		return new Rand(this).calc(ret);
	}

	@Override
	public final Matrix randn(Ret ret) throws MatrixException {
		return new Randn(this).calc(ret);
	}

	@Override
	public final int compareTo(Matrix m) {
		try {
			return new Double(doubleValue()).compareTo(m.doubleValue());
		} catch (MatrixException e) {
			logger.log(Level.WARNING, "could not compare", e);
			return Integer.MAX_VALUE;
		}
	}

	@Override
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

	@Override
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

	@Override
	public boolean isEmpty() throws MatrixException {
		for (long[] c : availableCoordinates()) {
			if (getAsDouble(c) != 0.0) {
				return false;
			}
		}
		return true;
	}

	@Override
	public final Matrix abs(Ret returnType) throws MatrixException {
		return new Abs(this).calc(returnType);
	}

	@Override
	public final Matrix log(Ret returnType) throws MatrixException {
		return new Log(this).calc(returnType);
	}

	@Override
	public final Matrix sort(Ret returnType) throws MatrixException {
		return new Sort(this).calc(returnType);
	}

	@Override
	public final Matrix sort(Ret returnType, long column) throws MatrixException {
		return new Sort(this, column).calc(returnType);
	}

	@Override
	public final Matrix cumsum(boolean ignoreNaN) throws MatrixException {
		return new Cumsum(this, ignoreNaN).calcNew();
	}

	@Override
	public final Matrix cumprod(boolean ignoreNaN) throws MatrixException {
		return new Cumprod(this, ignoreNaN).calcNew();
	}

	@Override
	public final Matrix distinct(Ret returnType) throws MatrixException {
		return new Distinct(this).calc(returnType);
	}

	@Override
	public final Matrix log2(Ret returnType) throws MatrixException {
		return new Log2(this).calc(returnType);
	}

	@Override
	public final Matrix log10(Ret returnType) throws MatrixException {
		return new Log10(this).calc(returnType);
	}

	@Override
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

	@Override
	public final boolean isSquare() {
		return getSize().length == 2 && getColumnCount() == getRowCount();
	}

	@Override
	public double euklideanDistanceTo(Matrix m, boolean ignoreNaN) throws MatrixException {
		return minkowskiDistanceTo(m, 2, ignoreNaN);
	}

	@Override
	public double manhattenDistanceTo(Matrix m, boolean ignoreNaN) throws MatrixException {
		return minkowskiDistanceTo(m, 1, ignoreNaN);
	}

	@Override
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

	@Override
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

	@Override
	public Matrix min(Ret returnType, int dimension) throws MatrixException {
		return new Min(dimension, this).calc(returnType);
	}

	@Override
	public Matrix max(Ret returnType, int dimension) throws MatrixException {
		return new Max(dimension, this).calc(returnType);
	}

	@Override
	public final Matrix addMissing(Ret returnType, int dimension, double... percentMissing)
			throws MatrixException {
		return new AddMissing(dimension, this, percentMissing).calc(returnType);
	}

	@Override
	public Matrix countMissing(Ret returnType, int dimension) throws MatrixException {
		return new CountMissing(dimension, this).calc(returnType);
	}

	@Override
	public final boolean isScalar() {
		return getColumnCount() == 1 && getRowCount() == 1;
	}

	@Override
	public final boolean isRowVector() {
		return getColumnCount() == 1 && getRowCount() != 1;
	}

	@Override
	public final boolean isColumnVector() {
		return getColumnCount() != 1 && getRowCount() == 1;
	}

	@Override
	public final boolean isMultidimensionalMatrix() {
		return getColumnCount() != 1 && getRowCount() != 1;
	}

	@Override
	public Matrix sinh(Ret returnType) throws MatrixException {
		return new Sinh(this).calc(returnType);
	}

	@Override
	public Matrix cosh(Ret returnType) throws MatrixException {
		return new Cosh(this).calc(returnType);
	}

	@Override
	public Matrix tanh(Ret returnType) throws MatrixException {
		return new Tanh(this).calc(returnType);
	}

	@Override
	public Matrix sin(Ret returnType) throws MatrixException {
		return new Sin(this).calc(returnType);
	}

	@Override
	public Matrix cos(Ret returnType) throws MatrixException {
		return new Cos(this).calc(returnType);
	}

	@Override
	public Matrix tan(Ret returnType) throws MatrixException {
		return new Tan(this).calc(returnType);
	}

	@Override
	public Matrix cov(Ret returnType, boolean ignoreNaN) throws MatrixException {
		return new Cov(ignoreNaN, this).calc(returnType);
	}

	@Override
	public Matrix corrcoef(Ret returnType, boolean ignoreNaN) throws MatrixException {
		return new Corrcoef(ignoreNaN, this).calc(returnType);
	}

	@Override
	public Matrix mutualInf(Ret returnType) throws MatrixException {
		return new MutualInformation(this).calc(returnType);
	}

	@Override
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

	@Override
	public Matrix bootstrap(Ret returnType) throws MatrixException {
		return new Bootstrap(this).calc(returnType);
	}

	@Override
	public Matrix lowerCase(Ret returnType) throws MatrixException {
		return new LowerCase(this).calc(returnType);
	}

	@Override
	public Matrix upperCase(Ret returnType) throws MatrixException {
		return new UpperCase(this).calc(returnType);
	}

	@Override
	public Matrix tfIdf(boolean calculateTf, boolean calculateIdf, boolean normalize)
			throws MatrixException {
		return new TfIdf(this, calculateTf, calculateIdf, normalize).calc(Ret.NEW);
	}

	@Override
	public Matrix removePunctuation(Ret ret) throws MatrixException {
		return new RemovePunctuation(this).calc(ret);
	}

	@Override
	public Matrix stem(Ret ret) throws MatrixException {
		return new Stem(this).calc(ret);
	}

	@Override
	public Matrix removeWords(Ret ret, Collection<String> words) throws MatrixException {
		return new RemoveWords(this, words).calc(ret);
	}

	@Override
	public Matrix unique(Ret returnType) throws MatrixException {
		return new Unique(this).calc(returnType);
	}

	@Override
	public Matrix bootstrap(Ret returnType, int count) throws MatrixException {
		return new Bootstrap(this, count).calc(returnType);
	}

	@Override
	public Matrix transpose(Ret returnType, int dimension1, int dimension2) throws MatrixException {
		return new Transpose(this, dimension1, dimension2).calc(returnType);
	}

	@Override
	public Matrix swap(Ret returnType, int dimension, long pos1, long pos2) throws MatrixException {
		return new Swap(dimension, pos1, pos2, this).calc(returnType);
	}

	@Override
	public Matrix flipdim(Ret returnType, int dimension) throws MatrixException {
		return new Flipdim(dimension, this).calc(returnType);
	}

	@Override
	public final Matrix shuffle(Ret returnType) throws MatrixException {
		return new Shuffle(this).calc(returnType);
	}

	@Override
	public final double trace() throws MatrixException {
		double sum = 0.0;
		for (long i = Math.min(getRowCount(), getColumnCount()); --i >= 0;) {
			sum += getAsDouble(i, i);
		}
		return sum;
	}

	@Override
	public final void exportToFile(File file, Object... parameters) throws MatrixException,
			IOException {
		ExportMatrix.toFile(file, this, parameters);
	}

	@Override
	public final void exportToClipboard(FileFormat format, Object... parameters)
			throws MatrixException, IOException {
		ExportMatrix.toClipboard(format, this, parameters);
	}

	@Override
	public final void exportToFile(String file, Object... parameters) throws MatrixException,
			IOException {
		ExportMatrix.toFile(file, this, parameters);
	}

	@Override
	public final void exportToFile(FileFormat format, String filename, Object... parameters)
			throws MatrixException, IOException {
		ExportMatrix.toFile(format, filename, this, parameters);
	}

	@Override
	public final void exportToFile(FileFormat format, File file, Object... parameters)
			throws MatrixException, IOException {
		ExportMatrix.toFile(format, file, this, parameters);
	}

	@Override
	public final void exportToStream(FileFormat format, OutputStream outputStream,
			Object... parameters) throws MatrixException, IOException {
		ExportMatrix.toStream(format, outputStream, this, parameters);
	}

	@Override
	public final void exportToWriter(FileFormat format, Writer writer, Object... parameters)
			throws MatrixException, IOException {
		ExportMatrix.toWriter(format, writer, this, parameters);
	}

	@Override
	public final void setLabel(String label) {
		setMatrixAnnotation(label);
	}

	@Override
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

	@Override
	public void setAsString(String string, long... coordinates) throws MatrixException {
		setAsObject(string, coordinates);
	}

	@Override
	public boolean isReadOnly() {
		return false;
	}

	@Override
	public String getAsString(long... coordinates) throws MatrixException {
		return StringUtil.convert(getAsObject(coordinates));
	}

	@Override
	public final double getMaxValue() throws MatrixException {
		return Max.calc(this);
	}

	@Override
	public final double getMinValue() throws MatrixException {
		return Min.calc(this);
	}

	@Override
	public final double getMeanValue() throws MatrixException {
		return Mean.calc(this);
	}

	@Override
	public final double getStdValue() throws MatrixException {
		return std(Ret.NEW, Matrix.ALL, true).getEuklideanValue();
	}

	@Override
	public final double getValueSum() throws MatrixException {
		double sum = 0.0;
		for (long[] c : allCoordinates()) {
			sum += getAsDouble(c);
		}
		return sum;
	}

	@Override
	public final double getAbsoluteValueSum() throws MatrixException {
		double sum = 0.0;
		for (long[] c : allCoordinates()) {
			sum += Math.abs(getAsDouble(c));
		}
		return sum;
	}

	@Override
	public final String getColumnLabel(long col) {
		return StringUtil.convert(getAxisAnnotation(COLUMN, col));
	}

	@Override
	public final String getRowLabel(long row) {
		return StringUtil.convert(getAxisAnnotation(ROW, row));
	}

	@Override
	public final long getRowForLabel(Object object) {
		return getPositionForLabel(ROW, object);
	}

	@Override
	public final long getColumnForLabel(Object object) {
		return getPositionForLabel(COLUMN, object);
	}

	@Override
	public final long getPositionForLabel(int dimension, Object object) {
		return annotation == null ? -1 : annotation.getPositionForLabel(dimension, object);
	}

	@Override
	public final Object getRowObject(long row) {
		return getAxisAnnotation(ROW, row);
	}

	@Override
	public final Object getColumnObject(long col) {
		return getAxisAnnotation(COLUMN, col);
	}

	@Override
	public final void setColumnLabel(long col, String label) {
		setAxisAnnotation(COLUMN, col, label);
	}

	@Override
	public final void setRowLabel(long row, String label) {
		setAxisAnnotation(ROW, row, label);
	}

	@Override
	public final void setRowObject(long row, Object o) {
		setAxisAnnotation(ROW, row, o);
	}

	@Override
	public final void setColumnObject(long col, Object o) {
		setAxisAnnotation(COLUMN, col, o);
	}

	@Override
	public final double getAbsoluteValueMean() throws MatrixException {
		return getAbsoluteValueSum() / getValueCount();
	}

	@Override
	public final Matrix toRowVector() throws MatrixException {
		if (isRowVector()) {
			return this;
		} else if (isColumnVector()) {
			return transpose();
		} else {
			return reshape(Coordinates.product(getSize()), 1);
		}
	}

	@Override
	public final Matrix toColumnVector() throws MatrixException {
		if (isColumnVector()) {
			return this;
		} else if (isRowVector()) {
			return transpose();
		} else {
			return reshape(1, (int) Coordinates.product(getSize()));
		}
	}

	@Override
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

	@Override
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

	@Override
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

	@Override
	public final Matrix convertIntToVector(int numberOfClasses) throws MatrixException {
		Matrix m = MatrixFactory.zeros(numberOfClasses, 1);
		for (int i = numberOfClasses - 1; i != -1; i--) {
			m.setAsDouble(-1.0, i, 0);
		}
		m.setAsDouble(1.0, (int) getAsDouble(0, 0), 0);
		return m;
	}

	@Override
	public final void greaterOrZero_() throws MatrixException {
		for (long[] c : allCoordinates()) {
			double v = getAsDouble(c);
			setAsDouble(v < 0.0 ? 0.0 : v, c);
		}
	}

	@Override
	public final void scaleRowsToOne_() throws MatrixException {
		for (long r = getRowCount() - 1; r != -1; r--) {
			double sum = 0.0;
			for (long c = getColumnCount() - 1; c != -1; c--) {
				sum += Math.abs(getAsDouble(r, c));
			}
			sum = sum / getRowCount();
			for (long c = getColumnCount() - 1; c != -1; c--) {
				setAsDouble(getAsDouble(r, c) / sum, r, c);
			}
		}
	}

	@Override
	public final Matrix appendHorizontally(Matrix m) throws MatrixException {
		return append(COLUMN, m);
	}

	@Override
	// TODO: this can be done more efficiently with an iterator
	public Iterable<Object> allValues() {
		List<Object> list = new ArrayList<Object>();
		for (long[] c : availableCoordinates()) {
			list.add(getAsObject(c));
		}
		return list;
	}

	@Override
	public final Matrix appendVertically(Matrix m) throws MatrixException {
		return append(ROW, m);
	}

	@Override
	public final Matrix append(int dimension, Matrix m) throws MatrixException {
		long[] newSize = Coordinates.copyOf(getSize());
		newSize[dimension] += m.getSize()[dimension];
		Matrix result = MatrixFactory.zeros(getValueType(), newSize);
		for (long[] c : allCoordinates()) {
			result.setAsObject(getAsObject(c), c);
		}
		for (long[] c : m.allCoordinates()) {
			long[] newC = Coordinates.copyOf(c);
			newC[dimension] += getSize()[dimension];
			result.setAsObject(m.getAsObject(c), newC);
		}
		return result;
	}

	@Override
	public final Matrix discretizeToColumns(long column) throws MatrixException {
		return new DiscretizeToColumns(this, false, column).calc(Ret.NEW);
	}

	@Override
	public final Matrix subMatrix(Ret returnType, long startRow, long startColumn, long endRow,
			long endColumn) throws MatrixException {
		long[] rows = MathUtil.sequenceLong(startRow, endRow);
		long[] columns = MathUtil.sequenceLong(startColumn, endColumn);
		return select(returnType, rows, columns);
	}

	@Override
	public Matrix addColumnWithOnes() throws MatrixException {
		Matrix ret = MatrixFactory.zeros(getRowCount(), getColumnCount() + 1);
		for (long[] c : allCoordinates()) {
			ret.setAsDouble(getAsDouble(c), c);
		}
		for (long r = getRowCount() - 1; r != -1; r--) {
			ret.setAsDouble(1.0, r, getColumnCount());
		}
		return ret;
	}

	@Override
	public final Matrix addRowWithOnes() throws MatrixException {
		Matrix ret = MatrixFactory.zeros(getRowCount() + 1, getColumnCount());
		for (long[] c : allCoordinates()) {
			ret.setAsDouble(getAsDouble(c), c);
		}
		for (long c = getColumnCount() - 1; c != -1; c--) {
			ret.setAsDouble(1.0, getRowCount(), c);
		}
		return ret;
	}

	@Override
	public Matrix[] svd() throws MatrixException {
		return SVD.calcNew(this);
	}

	@Override
	public final String exportToString(FileFormat format, Object... parameters)
			throws MatrixException, IOException {
		return ExportMatrix.toString(format, this, parameters);
	}

	@Override
	public void setSize(long... size) {
		throw new MatrixException("operation not possible: cannot change size of matrix");
	}

	@Override
	public final Matrix reshape(long... newSize) {
		return new ReshapedObjectMatrix(this, newSize);
	}

	@Override
	public final double doubleValue() throws MatrixException {
		if (isScalar()) {
			return getAsDouble(0, 0);
		}
		return getMeanValue();
	}

	@Override
	public final int intValue() throws MatrixException {
		if (isScalar()) {
			return getAsInt(0, 0);
		}
		return (int) getMeanValue();
	}

	@Override
	public final char charValue() throws MatrixException {
		if (isScalar()) {
			return getAsChar(0, 0);
		}
		return (char) getMeanValue();
	}

	@Override
	public final BigInteger bigIntegerValue() throws MatrixException {
		if (isScalar()) {
			return getAsBigInteger(0, 0);
		}
		return BigInteger.valueOf((long) getMeanValue());
	}

	@Override
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

	@Override
	public final float floatValue() throws MatrixException {
		if (isScalar()) {
			return getAsFloat(0, 0);
		}
		return (float) getMeanValue();
	}

	@Override
	public final long longValue() throws MatrixException {
		if (isScalar()) {
			return getAsLong(0, 0);
		}
		return (long) getMeanValue();
	}

	@Override
	public final Date dateValue() throws MatrixException {
		if (isScalar()) {
			return getAsDate(0, 0);
		}
		return MathUtil.getDate(getMeanValue());
	}

	@Override
	public final boolean booleanValue() throws MatrixException {
		if (isScalar()) {
			return getAsBoolean(0, 0);
		}
		return getMeanValue() != 0;
	}

	@Override
	public final String stringValue() throws MatrixException {
		if (isScalar()) {
			return getAsString(0, 0);
		} else {
			return toString();
		}
	}

	@Override
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

	@Override
	public final Annotation getAnnotation() {
		return annotation;
	}

	@Override
	public final void setAnnotation(Annotation annotation) {
		this.annotation = annotation;
	}

	@Override
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

	@Override
	public final boolean equals(Object o) {
		return equalsContent(o) && equalsAnnotation(o);
	}

	@Override
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

	@Override
	public final BooleanMatrix toBooleanMatrix() {
		return new ToBooleanMatrix(this).calcLink();
	}

	@Override
	public final ByteMatrix toByteMatrix() {
		return new ToByteMatrix(this).calcLink();
	}

	@Override
	public final CharMatrix toCharMatrix() {
		return new ToCharMatrix(this).calcLink();
	}

	@Override
	public final DateMatrix toDateMatrix() {
		return new ToDateMatrix(this).calcLink();
	}

	@Override
	public final DoubleMatrix toDoubleMatrix() {
		return new ToDoubleMatrix(this).calcLink();
	}

	@Override
	public final FloatMatrix toFloatMatrix() {
		return new ToFloatMatrix(this).calcLink();
	}

	@Override
	public final IntMatrix toIntMatrix() {
		return new ToIntMatrix(this).calcLink();
	}

	@Override
	public final LongMatrix toLongMatrix() {
		return new ToLongMatrix(this).calcLink();
	}

	@Override
	public final ObjectMatrix toObjectMatrix() {
		return new ToObjectMatrix(this).calcLink();
	}

	@Override
	public final ShortMatrix toShortMatrix() {
		return new ToShortMatrix(this).calcLink();
	}

	@Override
	public final StringMatrix toStringMatrix() {
		return new ToStringMatrix(this).calcLink();
	}

	@Override
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

	@Override
	public double norm2() {
		return svd()[1].getAsDouble(0, 0);
	}

	@Override
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

	@Override
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

	@Override
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

	@Override
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

	@Override
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

	@Override
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

	@Override
	public boolean containsBigInteger(BigInteger v) {
		for (long[] c : availableCoordinates()) {
			if (v.equals(getAsBigInteger(c))) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean containsBigDecimal(BigDecimal v) {
		for (long[] c : availableCoordinates()) {
			if (v.equals(getAsBigDecimal(c))) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean containsDate(Date v) {
		for (long[] c : availableCoordinates()) {
			if (v.equals(getAsDate(c))) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean containsObject(Object o) {
		for (long[] c : availableCoordinates()) {
			if (o.equals(getAsObject(c))) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean containsString(String s) {
		for (long[] c : availableCoordinates()) {
			if (s.equals(getAsString(c))) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean containsBoolean(boolean v) {
		for (long[] c : availableCoordinates()) {
			if (getAsBoolean(c) == v) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean containsByte(byte v) {
		for (long[] c : availableCoordinates()) {
			if (getAsByte(c) == v) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean containsChar(char v) {
		for (long[] c : availableCoordinates()) {
			if (getAsChar(c) == v) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean containsDouble(double v) {
		for (long[] c : availableCoordinates()) {
			if (getAsDouble(c) == v) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean containsFloat(float v) {
		for (long[] c : availableCoordinates()) {
			if (getAsFloat(c) == v) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean containsInt(int v) {
		for (long[] c : availableCoordinates()) {
			if (getAsInt(c) == v) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean containsLong(long v) {
		for (long[] c : availableCoordinates()) {
			if (getAsLong(c) == v) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean containsShort(short v) {
		for (long[] c : availableCoordinates()) {
			if (getAsShort(c) == v) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean containsNull() {
		for (long[] c : allCoordinates()) {
			if (getAsDouble(c) == 0.0) {
				return true;
			}
		}
		return false;
	}

}
