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

package org.ujmp.core.genericmatrix;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
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
import org.ujmp.core.calculation.AbstractCalculation;
import org.ujmp.core.calculation.Calculation;
import org.ujmp.core.calculation.Calculation.Calc;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.doublecalculation.basic.Convert;
import org.ujmp.core.doublecalculation.basic.Deletion;
import org.ujmp.core.doublecalculation.basic.DiscretizeToColumns;
import org.ujmp.core.doublecalculation.basic.Divide;
import org.ujmp.core.doublecalculation.basic.Minus;
import org.ujmp.core.doublecalculation.basic.Mtimes;
import org.ujmp.core.doublecalculation.basic.Plus;
import org.ujmp.core.doublecalculation.basic.Selection;
import org.ujmp.core.doublecalculation.basic.Times;
import org.ujmp.core.doublecalculation.basic.Transpose;
import org.ujmp.core.doublecalculation.entrywise.basic.Abs;
import org.ujmp.core.doublecalculation.entrywise.basic.Log;
import org.ujmp.core.doublecalculation.entrywise.basic.Log10;
import org.ujmp.core.doublecalculation.entrywise.basic.Log2;
import org.ujmp.core.doublecalculation.entrywise.basic.Power;
import org.ujmp.core.doublecalculation.entrywise.basic.Sign;
import org.ujmp.core.doublecalculation.entrywise.basic.Sqrt;
import org.ujmp.core.doublecalculation.entrywise.creators.Eye;
import org.ujmp.core.doublecalculation.entrywise.creators.Fill;
import org.ujmp.core.doublecalculation.entrywise.creators.Ones;
import org.ujmp.core.doublecalculation.entrywise.creators.Rand;
import org.ujmp.core.doublecalculation.entrywise.creators.Randn;
import org.ujmp.core.doublecalculation.entrywise.creators.Zeros;
import org.ujmp.core.doublecalculation.entrywise.hyperbolic.Cosh;
import org.ujmp.core.doublecalculation.entrywise.hyperbolic.Sinh;
import org.ujmp.core.doublecalculation.entrywise.hyperbolic.Tanh;
import org.ujmp.core.doublecalculation.entrywise.replace.Replace;
import org.ujmp.core.doublecalculation.entrywise.replace.ReplaceRegex;
import org.ujmp.core.doublecalculation.entrywise.rounding.Ceil;
import org.ujmp.core.doublecalculation.entrywise.rounding.Floor;
import org.ujmp.core.doublecalculation.entrywise.rounding.Round;
import org.ujmp.core.doublecalculation.entrywise.trigonometric.Cos;
import org.ujmp.core.doublecalculation.entrywise.trigonometric.Sin;
import org.ujmp.core.doublecalculation.entrywise.trigonometric.Tan;
import org.ujmp.core.doublecalculation.general.misc.Bootstrap;
import org.ujmp.core.doublecalculation.general.misc.Center;
import org.ujmp.core.doublecalculation.general.misc.Shuffle;
import org.ujmp.core.doublecalculation.general.misc.Standardize;
import org.ujmp.core.doublecalculation.general.missingvalues.AddMissing;
import org.ujmp.core.doublecalculation.general.missingvalues.CountMissing;
import org.ujmp.core.doublecalculation.general.missingvalues.ImputeEM;
import org.ujmp.core.doublecalculation.general.missingvalues.ImputeKNN;
import org.ujmp.core.doublecalculation.general.missingvalues.ImputeMean;
import org.ujmp.core.doublecalculation.general.missingvalues.ImputeRegression;
import org.ujmp.core.doublecalculation.general.missingvalues.ImputeZero;
import org.ujmp.core.doublecalculation.general.solving.Inv;
import org.ujmp.core.doublecalculation.general.solving.Pinv;
import org.ujmp.core.doublecalculation.general.solving.Princomp;
import org.ujmp.core.doublecalculation.general.statistical.Corrcoef;
import org.ujmp.core.doublecalculation.general.statistical.Cov;
import org.ujmp.core.doublecalculation.general.statistical.IndexOfMax;
import org.ujmp.core.doublecalculation.general.statistical.IndexOfMin;
import org.ujmp.core.doublecalculation.general.statistical.Max;
import org.ujmp.core.doublecalculation.general.statistical.Mean;
import org.ujmp.core.doublecalculation.general.statistical.Min;
import org.ujmp.core.doublecalculation.general.statistical.MutualInformation;
import org.ujmp.core.doublecalculation.general.statistical.PairedTTest;
import org.ujmp.core.doublecalculation.general.statistical.Std;
import org.ujmp.core.doublecalculation.general.statistical.Sum;
import org.ujmp.core.doublecalculation.general.statistical.Var;
import org.ujmp.core.enums.AnnotationTransfer;
import org.ujmp.core.enums.FileFormat;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.core.interfaces.HasLabel;
import org.ujmp.core.interfaces.HasSourceMatrix;
import org.ujmp.core.io.ExportMatrix;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.StringUtil;
import org.ujmp.core.util.UJMPSettings;

public abstract class AbstractGenericMatrix<A> implements Matrix {

	/**
	 * A logger used for <code>Matrix</code> and all subclasses
	 */
	protected transient static final Logger logger = Logger.getLogger(Matrix.class.getName());

	static {
		UJMPSettings.initialize();
		long mem = Runtime.getRuntime().maxMemory();
		if (mem < 133234688) {
			logger.log(Level.WARNING, "Available memory is very low: " + (mem / 1024 / 1024) + "M");
			logger.log(Level.FINE, "Use the parameter -Xmx512M to increase the available memory");
		}
	}

	private transient GUIObject guiObject = null;

	private Annotation annotation = null;

	public abstract A getObject(long... coordinates) throws MatrixException;

	public final Object getMatrixAnnotation() {
		return annotation == null ? null : annotation.getMatrixAnnotation();
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
				Class<?> c = Class.forName("org.ujmp.gui.matrix.MatrixGUIObject");
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
			if (v != v || v == Double.NEGATIVE_INFINITY || v == Double.POSITIVE_INFINITY) {
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

	@Override
	public final Matrix clone() {
		try {
			return copy(AnnotationTransfer.COPY);
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

	public Matrix imputeKNN(Ret returnType, int k) throws MatrixException {
		return new ImputeKNN(this, k).calc(returnType);
	}

	public Matrix imputeMean(Ret returnType, int dimension) throws MatrixException {
		return new ImputeMean(dimension, this).calc(returnType);
	}

	// public Matrix imputeNeuralNetwork(Ret returnType, int k, double
	// tolerance,
	// double learningRate,
	// int maxIterations) throws MatrixException {
	// return new ImputeNeuralNetwork(this, k, tolerance, learningRate,
	// maxIterations)
	// .calc(returnType);
	// }

	public Matrix imputeRegression(Ret returnType) throws MatrixException {
		return new ImputeRegression(this).calc(returnType);
	}

	public Matrix imputeRegression(Ret returnType, Matrix firstGuess) throws MatrixException {
		return new ImputeRegression(this, firstGuess).calc(returnType);
	}

	public Matrix imputeEM(Ret returnType) throws MatrixException {
		return new ImputeEM(this).calc(returnType);
	}

	// public Matrix imputeRescale(Ret returnType, int dimension) throws
	// MatrixException {
	// return new ImputeRescale(dimension, this).calc(returnType);
	// }

	public Matrix imputeZero(Ret returnType) throws MatrixException {
		return new ImputeZero(this).calc(returnType);
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

	public Matrix inv() throws MatrixException {
		return new Inv(this).calcNew();
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
		return copy(AnnotationTransfer.LINK);
	}

	public Matrix copy(AnnotationTransfer annotationTransfer) throws MatrixException {
		return Convert.calcNew(annotationTransfer, this);
	}

	public boolean isResizeable() {
		return false;
	}

	public final Matrix convert(ValueType newValueType) throws MatrixException {
		return Convert.calcNew(newValueType, AnnotationTransfer.COPY, this);
	}

	public final Matrix convert(ValueType newValueType, AnnotationTransfer annotationTransfer)
			throws MatrixException {
		return Convert.calcNew(newValueType, annotationTransfer, this);
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

	public Iterable<long[]> selectedCoordinates(String selection) {
		try {
			return select(Ret.LINK, selection).allCoordinates();
		} catch (MatrixException e) {
			logger.log(Level.WARNING, "could not select coordinates", e);
			return null;
		}
	}

	public Iterable<long[]> selectedCoordinates(long[]... selection) throws MatrixException {
		return select(Ret.LINK, selection).allCoordinates();
	}

	public boolean isTransient() {
		return false;
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
				values[i][j] = getObject(i, j);
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

	public Matrix calcNew(String calculation, Matrix... matrices) throws MatrixException {
		return calcNew(Calc.valueOf(calculation.toUpperCase()), matrices);
	}

	public Matrix calcNew(String calculation, int dimension, Matrix... matrices)
			throws MatrixException {
		return calcNew(Calc.valueOf(calculation.toUpperCase()), dimension, matrices);
	}

	public Matrix calc(String calculation, Ret returnType, Matrix... matrices)
			throws MatrixException {
		return calc(Calc.valueOf(calculation.toUpperCase()), returnType, NONE, matrices);
	}

	public Matrix calc(String calculation, Ret returnType, int dimension, Matrix... matrices)
			throws MatrixException {
		return calc(Calc.valueOf(calculation.toUpperCase()), returnType, dimension, matrices);
	}

	public Matrix calcNew(Calc calculation, Matrix... matrices) throws MatrixException {
		return calc(calculation, Ret.NEW, NONE, matrices);
	}

	public Matrix calcNew(Calc calculation, int dimension, Matrix... matrices)
			throws MatrixException {
		return calc(calculation, Ret.NEW, dimension, matrices);
	}

	public Matrix calc(Calc calculation, Ret returnType, Matrix... matrices) throws MatrixException {
		return calc(calculation, returnType, ALL, matrices);
	}

	public Matrix calc(Calc calculation, Ret returnType, int dimension, Matrix... matrices)
			throws MatrixException {
		return AbstractCalculation.calc(calculation, returnType, dimension, this, matrices);
	}

	public Matrix calcNew(Calculation calculation) throws MatrixException {
		return calc(calculation, Ret.NEW);
	}

	public Matrix calc(Calculation calculation, Ret returnType) throws MatrixException {
		return calculation.calc(returnType);
	}

	public final void notifyGUIObject() {
		if (this instanceof HasSourceMatrix) {
			((HasSourceMatrix) this).getSourceMatrix().notifyGUIObject();
		}
		if (guiObject != null) {
			guiObject.fireValueChanged();
		}
	}

	public Matrix mtimes(Matrix matrix) throws MatrixException {
		return Mtimes.calc(false, this, matrix);
	}

	public boolean getAsBoolean(long... coordinates) throws MatrixException {
		return getAsDouble(coordinates) != 0.0;
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

	public void setAsChar(char value, long... coordinates) throws MatrixException {
		setAsDouble(value, coordinates);
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
		Object o = getObject(coordinates);
		if (o == null) {
			return null;
		}
		if (o instanceof Date) {
			return (Date) o;
		}
		if (o instanceof Long) {
			return new Date((Long) o);
		}
		if (o instanceof String) {
			try {
				return DateFormat.getInstance().parse((String) o);
			} catch (ParseException e) {
			}
		}
		return new Date(getAsLong(coordinates));
	}

	public void setAsDate(Date date, long... coordinates) throws MatrixException {
		setObject(date, coordinates);
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
		return calc(new Minus(ignoreNaN, this, v), returnType);
	}

	public Matrix minus(Ret returnType, boolean ignoreNaN, Matrix m) throws MatrixException {
		return calc(new Minus(ignoreNaN, this, m), returnType);
	}

	public Matrix plus(Ret returnType, boolean ignoreNaN, double v) throws MatrixException {
		return calc(new Plus(ignoreNaN, this, v), returnType);
	}

	public Matrix plus(Ret returnType, boolean ignoreNaN, Matrix m) throws MatrixException {
		return calc(new Plus(ignoreNaN, this, m), returnType);
	}

	public Matrix atimes(Ret returnType, boolean ignoreNaN, Matrix matrix) throws MatrixException {

		if (returnType != Ret.NEW) {
			throw new MatrixException("not yet supported");
		}

		int i, j, k, count;
		double sum;

		if (this.getColumnCount() != matrix.getRowCount()) {
			logger.log(Level.WARNING, "matrices have wrong size");
			return null;
		}

		long rowCount = getRowCount();
		long columnCount = getColumnCount();
		long mColumnCount = matrix.getColumnCount();

		Matrix ret = MatrixFactory.zeros(rowCount, mColumnCount);

		if (ignoreNaN) {

			for (i = 0; i < rowCount; i++) {
				for (j = 0; j < mColumnCount; j++) {
					sum = 0.0;
					count = 0;
					for (k = 0; k < columnCount; k++) {
						double v1 = getAsDouble(i, k);
						double v2 = matrix.getAsDouble(k, j);
						if (!MathUtil.isNaNOrInfinite(v1) && !MathUtil.isNaNOrInfinite(v2)) {
							sum += v1 * v2;
							count++;
						}
					}
					ret.setAsDouble(sum / count, i, j);
				}
			}

		} else {

			for (i = 0; i < rowCount; i++) {
				for (j = 0; j < mColumnCount; j++) {
					sum = 0.0;
					count = 0;
					for (k = 0; k < columnCount; k++) {
						double v1 = getAsDouble(i, k);
						double v2 = matrix.getAsDouble(k, j);
						sum += v1 * v2;
						count++;
					}
					ret.setAsDouble(sum / count, i, j);
				}
			}

		}

		return ret;
	}

	public Matrix transpose() throws MatrixException {
		return Transpose.calc(this);
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

	public final Matrix sum(Ret returnType, int dimension, boolean ignoreNaN)
			throws MatrixException {
		return new Sum(dimension, ignoreNaN, this).calc(returnType);
	}

	public final Matrix sign(Ret returnType) throws MatrixException {
		return new Sign(this).calc(returnType);
	}

	@Override
	public String toString() {
		StringBuffer s = new StringBuffer();
		try {
			final String EOL = System.getProperty("line.separator");

			long rowCount = getRowCount();
			long columnCount = getColumnCount();
			for (int row = 0; row < rowCount && row < UJMPSettings.getMaxRowsToPrint(); row++) {
				for (int col = 0; col < columnCount && col < UJMPSettings.getMaxColumnsToPrint(); col++) {
					Object o = getObject(row, col);
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
			}
		} catch (Exception e) {
			logger.log(Level.WARNING, "could not execute toString()", e);
		}
		return s.toString();
	}

	public final int getDimensionCount() {
		return getSize().length;
	}

	public final Matrix ones() throws MatrixException {
		return Ones.calc(this);
	}

	public final Matrix fill(double value) throws MatrixException {
		return Fill.calc(this, value);
	}

	public final Matrix zeros() throws MatrixException {
		return Zeros.calc(this);
	}

	public final Matrix eye() throws MatrixException {
		return Eye.calc(this);
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

	public final Matrix link() throws MatrixException {
		return calc(Calc.CLONE, Ret.LINK);
	}

	public void clear() {
		try {
			calc(Calc.ZEROS, Ret.ORIG);
		} catch (MatrixException e) {
			logger.log(Level.WARNING, "could not clear Matrix", e);
		}
	}

	public final Matrix rand() throws MatrixException {
		return Rand.calc(this);
	}

	public final Matrix randn() throws MatrixException {
		return Randn.calc(this);
	}

	public final int compareTo(Matrix m) {
		try {
			return new Double(getEuklideanValue()).compareTo(m.getEuklideanValue());
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
		// TODO!!!
		return false;
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
		return new PairedTTest(this).calc(returnType);
	}

	public Matrix bootstrap(Ret returnType) throws MatrixException {
		return new Bootstrap(this).calc(returnType);
	}

	public Matrix bootstrap(Ret returnType, int count) throws MatrixException {
		return new Bootstrap(this, count).calc(returnType);
	}

	public Matrix shuffle(Ret returnType) throws MatrixException {
		return new Shuffle(this).calc(returnType);
	}

	public double trace() throws MatrixException {
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
		setObject(string, coordinates);
	}

	public boolean isReadOnly() {
		return false;
	}

	public String getAsString(long... coordinates) throws MatrixException {
		Object o = getObject(coordinates);
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
		return StringUtil.format(getAxisAnnotation(COLUMN, col));
	}

	public final String getRowLabel(long row) {
		return StringUtil.format(getAxisAnnotation(ROW, row));
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

	public final void rescaleEntries_(int axis, double targetMin, double targetMax)
			throws MatrixException {
		if (axis == ALL) {
			this.toRowVector().rescaleEntries(0, targetMin, targetMax);
		} else {

		}
		double minValue = getMinValue();
		double maxValue = getMaxValue();
		double targetDiff = targetMax - targetMin;
		double diffBefore = maxValue - minValue;
		if (diffBefore != 0) {
			// TODO: this calculation is not correct
			double scale = targetDiff / diffBefore;
			double offet = targetMin - minValue;
			for (long[] c : allCoordinates()) {
				setAsDouble(getAsDouble(c) * scale + offet, c);
			}
		}
		notifyGUIObject();
	}

	public final Matrix rescaleEntries() throws MatrixException {
		return rescaleEntries(ALL, -1.0, 1.0);
	}

	public final void rescaleEntries_() throws MatrixException {
		rescaleEntries_(ALL, -1.0, 1.0);
	}

	public final Matrix rescaleEntries(int axis, double targetMin, double targetMax)
			throws MatrixException {
		Matrix ret = MatrixFactory.zeros(getSize());
		ret.rescaleEntries_(axis, targetMin, targetMax);
		return ret;
	}

	public final void addNoise_(double noiselevel) throws MatrixException {
		for (long[] c : allCoordinates()) {
			setAsDouble(getAsDouble(c) + MathUtil.nextGaussian(0.0, noiselevel), c);
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

	public Matrix deleteColumnsWithMissingValues(Ret returnType) throws MatrixException {
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

	public Matrix deleteRowsWithMissingValues(Ret returnType) throws MatrixException {
		Matrix mv = countMissing(Ret.NEW, Matrix.COLUMN);
		List<Long> sel = new ArrayList<Long>();
		for (long r = 0; r < mv.getRowCount(); r++) {
			if (mv.getAsDouble(r, 0) == 0.0)
				sel.add(r);
		}
		long[] longsel = new long[sel.size()];
		for (int i = sel.size(); --i >= 0;) {
			longsel[i] = sel.get(i);
		}
		return selectRows(returnType, longsel);
	}

	public final void fadeIn_(int axis, long start, long end) throws MatrixException {
		if (axis == ALL) {
			this.toRowVector().fadeIn_(0, start, end);
		} else if (axis == ROW) {
			double stepsize = 1.0 / (end - start);
			for (long r = start, i = 0; r < end; r++) {
				double factor = (++i * stepsize);
				for (int c = 0; c < getSize()[COLUMN]; c++) {
					setAsDouble(getAsDouble(r, c) * factor, r, c);
				}
			}
		} else if (axis == COLUMN) {
			double stepsize = 1.0 / (end - start);
			for (long c = start, i = 0; c < end; c++) {
				double factor = (++i * stepsize);
				for (int r = 0; r < getSize()[ROW]; r++) {
					setAsDouble(getAsDouble(r, c) * factor, r, c);
				}
			}
		}
		notifyGUIObject();
	}

	public final Matrix convertIntToVector(int numberOfClasses) throws MatrixException {
		Matrix m = MatrixFactory.zeros(numberOfClasses, 1);
		for (int i = numberOfClasses - 1; i != -1; i--) {
			m.setAsDouble(-1.0, i, 0);
		}
		m.setAsDouble(1.0, (int) getAsDouble(0, 0), 0);
		return m;
	}

	public final void greaterOrZero_() throws MatrixException {
		for (long[] c : allCoordinates()) {
			double v = getAsDouble(c);
			setAsDouble(v < 0.0 ? 0.0 : v, c);
		}
	}

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

	public final Matrix appendHorizontally(Matrix m) throws MatrixException {
		return append(COLUMN, m);
	}

	public final Matrix appendVertically(Matrix m) throws MatrixException {
		return append(ROW, m);
	}

	public final Matrix append(int dimension, Matrix m) throws MatrixException {
		long[] newSize = Coordinates.copyOf(getSize());
		newSize[dimension] += m.getSize()[dimension];
		Matrix result = MatrixFactory.zeros(newSize);
		for (long[] c : allCoordinates()) {
			result.setAsDouble(getAsDouble(c), c);
		}
		for (long[] c : m.allCoordinates()) {
			long[] newC = Coordinates.copyOf(c);
			newC[dimension] += getSize()[dimension];
			result.setAsDouble(m.getAsDouble(c), newC);
		}
		return result;
	}

	public final void fadeIn_() throws MatrixException {
		fadeIn_(ROW, 0, getRowCount());
	}

	public final void fadeOut_() throws MatrixException {
		fadeOut_(ROW, 0, getRowCount());
	}

	public final void fadeOut_(int axis, long start, long end) throws MatrixException {
		if (axis == ALL) {
			this.toRowVector().fadeOut_(0, start, end);
		} else if (axis == ROW) {
			double stepsize = 1.0 / (end - start);
			for (long r = start, i = 0; r < end; r++) {
				double factor = 1.0 - (++i * stepsize);
				for (int c = 0; c < getSize()[COLUMN]; c++) {
					setAsDouble(getAsDouble(r, c) * factor, r, c);
				}
			}
		} else if (axis == COLUMN) {
			double stepsize = 1.0 / (end - start);
			for (long c = start, i = 0; c < end; c++) {
				double factor = 1.0 - (++i * stepsize);
				for (int r = 0; r < getSize()[ROW]; r++) {
					setAsDouble(getAsDouble(r, c) * factor, r, c);
				}
			}
		}
		notifyGUIObject();
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

	public Matrix addRowWithOnes() throws MatrixException {
		Matrix ret = MatrixFactory.zeros(getRowCount() + 1, getColumnCount());
		for (long[] c : allCoordinates()) {
			ret.setAsDouble(getAsDouble(c), c);
		}
		for (long c = getColumnCount() - 1; c != -1; c--) {
			ret.setAsDouble(1.0, getRowCount(), c);
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	public Matrix[] svd() throws MatrixException {
		try {
			Class<? extends Matrix> mtjc = (Class<? extends Matrix>) Class
					.forName("org.ujmp.mtj.MTJDenseDoubleMatrix2D");
			Constructor<? extends Matrix> con = mtjc.getConstructor(Matrix.class);
			Matrix mtjm = con.newInstance(this);

			return mtjm.svd();
		} catch (ClassNotFoundException e) {
			throw new MatrixException("cannot calculate SVD: add ujmp-mtj to classpath");
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

	public String exportToString(FileFormat format, Object... parameters) throws MatrixException,
			IOException {
		return ExportMatrix.toString(format, this, parameters);
	}

	public void setSize(long... size) {
		throw new MatrixException("operation not possible: cannot change size of matrix");
	}

	public final Matrix reshape(long... newSize) {
		return new ReshapedMatrix(this, newSize);
	}

	public final double getDoubleValue() throws MatrixException {
		return getEuklideanValue();
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

	@Override
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
						Object o1 = getObject(c);
						Object o2 = m.getObject(c);
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
						Object o1 = getObject(c);
						Object o2 = m.getObject(c);
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
						Object o1 = getObject(c);
						Object o2 = m.getObject(c);
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

}
