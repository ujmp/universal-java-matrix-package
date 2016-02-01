/*
 * Copyright (C) 2008-2016 by Holger Arndt
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

import static org.ujmp.core.util.VerifyUtil.verifyTrue;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.regex.Pattern;

import javax.swing.JFrame;

import org.ujmp.core.bigdecimalmatrix.BaseBigDecimalMatrix;
import org.ujmp.core.bigdecimalmatrix.calculation.ToBigDecimalMatrix;
import org.ujmp.core.bigintegermatrix.BigIntegerMatrix;
import org.ujmp.core.bigintegermatrix.calculation.ToBigIntegerMatrix;
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
import org.ujmp.core.collections.Dictionary;
import org.ujmp.core.collections.list.FastArrayList;
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.DoubleMatrix;
import org.ujmp.core.doublematrix.calculation.ToDoubleMatrix;
import org.ujmp.core.doublematrix.calculation.basic.Atimes;
import org.ujmp.core.doublematrix.calculation.basic.DivideMatrix;
import org.ujmp.core.doublematrix.calculation.basic.DivideScalar;
import org.ujmp.core.doublematrix.calculation.basic.MinusMatrix;
import org.ujmp.core.doublematrix.calculation.basic.MinusScalar;
import org.ujmp.core.doublematrix.calculation.basic.Mtimes;
import org.ujmp.core.doublematrix.calculation.basic.PlusMatrix;
import org.ujmp.core.doublematrix.calculation.basic.PlusScalar;
import org.ujmp.core.doublematrix.calculation.basic.TimesMatrix;
import org.ujmp.core.doublematrix.calculation.basic.TimesScalar;
import org.ujmp.core.doublematrix.calculation.entrywise.basic.Abs;
import org.ujmp.core.doublematrix.calculation.entrywise.basic.Exp;
import org.ujmp.core.doublematrix.calculation.entrywise.basic.Log;
import org.ujmp.core.doublematrix.calculation.entrywise.basic.Log10;
import org.ujmp.core.doublematrix.calculation.entrywise.basic.Log2;
import org.ujmp.core.doublematrix.calculation.entrywise.basic.Power;
import org.ujmp.core.doublematrix.calculation.entrywise.basic.Sign;
import org.ujmp.core.doublematrix.calculation.entrywise.basic.Sqrt;
import org.ujmp.core.doublematrix.calculation.entrywise.creators.Eye;
import org.ujmp.core.doublematrix.calculation.entrywise.creators.NaNs;
import org.ujmp.core.doublematrix.calculation.entrywise.creators.Ones;
import org.ujmp.core.doublematrix.calculation.entrywise.creators.Rand;
import org.ujmp.core.doublematrix.calculation.entrywise.creators.Randn;
import org.ujmp.core.doublematrix.calculation.entrywise.creators.Zeros;
import org.ujmp.core.doublematrix.calculation.entrywise.hyperbolic.Cosh;
import org.ujmp.core.doublematrix.calculation.entrywise.hyperbolic.Sinh;
import org.ujmp.core.doublematrix.calculation.entrywise.hyperbolic.Tanh;
import org.ujmp.core.doublematrix.calculation.entrywise.misc.GrayScale;
import org.ujmp.core.doublematrix.calculation.entrywise.misc.GrayScale.ColorChannel;
import org.ujmp.core.doublematrix.calculation.entrywise.misc.LogisticFunction;
import org.ujmp.core.doublematrix.calculation.entrywise.rounding.Ceil;
import org.ujmp.core.doublematrix.calculation.entrywise.rounding.Floor;
import org.ujmp.core.doublematrix.calculation.entrywise.rounding.Round;
import org.ujmp.core.doublematrix.calculation.entrywise.trigonometric.Cos;
import org.ujmp.core.doublematrix.calculation.entrywise.trigonometric.Sin;
import org.ujmp.core.doublematrix.calculation.entrywise.trigonometric.Tan;
import org.ujmp.core.doublematrix.calculation.general.decomposition.Chol;
import org.ujmp.core.doublematrix.calculation.general.decomposition.Eig;
import org.ujmp.core.doublematrix.calculation.general.decomposition.Ginv;
import org.ujmp.core.doublematrix.calculation.general.decomposition.Inv;
import org.ujmp.core.doublematrix.calculation.general.decomposition.InvSPD;
import org.ujmp.core.doublematrix.calculation.general.decomposition.LU;
import org.ujmp.core.doublematrix.calculation.general.decomposition.Pinv;
import org.ujmp.core.doublematrix.calculation.general.decomposition.Princomp;
import org.ujmp.core.doublematrix.calculation.general.decomposition.QR;
import org.ujmp.core.doublematrix.calculation.general.decomposition.SVD;
import org.ujmp.core.doublematrix.calculation.general.decomposition.Solve;
import org.ujmp.core.doublematrix.calculation.general.decomposition.SolveSPD;
import org.ujmp.core.doublematrix.calculation.general.misc.Center;
import org.ujmp.core.doublematrix.calculation.general.misc.CosineSimilarity;
import org.ujmp.core.doublematrix.calculation.general.misc.DiscretizeToColumns;
import org.ujmp.core.doublematrix.calculation.general.misc.FadeIn;
import org.ujmp.core.doublematrix.calculation.general.misc.FadeOut;
import org.ujmp.core.doublematrix.calculation.general.misc.MinkowskiDistance;
import org.ujmp.core.doublematrix.calculation.general.misc.Normalize;
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
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.export.destinationselector.DefaultMatrixExportDestinationSelector;
import org.ujmp.core.export.destinationselector.MatrixExportDestinationSelector;
import org.ujmp.core.floatmatrix.FloatMatrix;
import org.ujmp.core.floatmatrix.calculation.ToFloatMatrix;
import org.ujmp.core.importer.sourceselector.DefaultMatrixImportSourceSelector;
import org.ujmp.core.importer.sourceselector.MatrixImportSourceSelector;
import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.core.interfaces.HasColumnMajorDoubleArray1D;
import org.ujmp.core.interfaces.HasLabel;
import org.ujmp.core.interfaces.HasRowMajorDoubleArray2D;
import org.ujmp.core.intmatrix.IntMatrix;
import org.ujmp.core.intmatrix.calculation.Discretize;
import org.ujmp.core.intmatrix.calculation.Discretize.DiscretizationMethod;
import org.ujmp.core.intmatrix.calculation.DiscretizeDictionary;
import org.ujmp.core.intmatrix.calculation.ToIntMatrix;
import org.ujmp.core.listmatrix.DefaultListMatrix;
import org.ujmp.core.listmatrix.ListMatrix;
import org.ujmp.core.longmatrix.LongMatrix;
import org.ujmp.core.longmatrix.calculation.ToLongMatrix;
import org.ujmp.core.mapmatrix.DefaultMapMatrix;
import org.ujmp.core.mapmatrix.MapMatrix;
import org.ujmp.core.matrix.factory.BaseMatrixFactory;
import org.ujmp.core.objectmatrix.ObjectMatrix;
import org.ujmp.core.objectmatrix.calculation.Bootstrap;
import org.ujmp.core.objectmatrix.calculation.Concatenation;
import org.ujmp.core.objectmatrix.calculation.Convert;
import org.ujmp.core.objectmatrix.calculation.Deletion;
import org.ujmp.core.objectmatrix.calculation.Diag;
import org.ujmp.core.objectmatrix.calculation.ExtractAnnotation;
import org.ujmp.core.objectmatrix.calculation.Fill;
import org.ujmp.core.objectmatrix.calculation.Flipdim;
import org.ujmp.core.objectmatrix.calculation.IncludeAnnotation;
import org.ujmp.core.objectmatrix.calculation.Replace;
import org.ujmp.core.objectmatrix.calculation.Reshape;
import org.ujmp.core.objectmatrix.calculation.Selection;
import org.ujmp.core.objectmatrix.calculation.SetContent;
import org.ujmp.core.objectmatrix.calculation.Shuffle;
import org.ujmp.core.objectmatrix.calculation.Sortrows;
import org.ujmp.core.objectmatrix.calculation.Squeeze;
import org.ujmp.core.objectmatrix.calculation.SubMatrix;
import org.ujmp.core.objectmatrix.calculation.Swap;
import org.ujmp.core.objectmatrix.calculation.ToObjectMatrix;
import org.ujmp.core.objectmatrix.calculation.Transpose;
import org.ujmp.core.objectmatrix.calculation.Tril;
import org.ujmp.core.objectmatrix.calculation.Triu;
import org.ujmp.core.objectmatrix.calculation.Unique;
import org.ujmp.core.objectmatrix.calculation.UniqueValueCount;
import org.ujmp.core.objectmatrix.impl.DefaultSparseObjectMatrix;
import org.ujmp.core.setmatrix.DefaultSetMatrix;
import org.ujmp.core.setmatrix.SetMatrix;
import org.ujmp.core.shortmatrix.ShortMatrix;
import org.ujmp.core.shortmatrix.calculation.ToShortMatrix;
import org.ujmp.core.stringmatrix.StringMatrix;
import org.ujmp.core.stringmatrix.calculation.ConvertEncoding;
import org.ujmp.core.stringmatrix.calculation.LowerCase;
import org.ujmp.core.stringmatrix.calculation.RemovePunctuation;
import org.ujmp.core.stringmatrix.calculation.RemoveWords;
import org.ujmp.core.stringmatrix.calculation.ReplaceRegex;
import org.ujmp.core.stringmatrix.calculation.Stem;
import org.ujmp.core.stringmatrix.calculation.ToStringMatrix;
import org.ujmp.core.stringmatrix.calculation.Translate;
import org.ujmp.core.stringmatrix.calculation.UpperCase;
import org.ujmp.core.util.CoordinateIterator;
import org.ujmp.core.util.CoordinateIterator2D;
import org.ujmp.core.util.DecompositionOps;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.SerializationUtil;
import org.ujmp.core.util.StringUtil;
import org.ujmp.core.util.UJMPFormat;
import org.ujmp.core.util.UJMPSettings;
import org.ujmp.core.util.VerifyUtil;
import org.ujmp.core.util.concurrent.PForEquidistant;
import org.ujmp.core.util.io.MatrixSocketThread;

public abstract class AbstractMatrix extends Number implements Matrix {
	private static final long serialVersionUID = 5264103919889924711L;

	private static long runningId = 0;

	protected transient GUIObject guiObject = null;

	protected long[] size;

	private final long id;

	private MapMatrix<String, Object> metaData = null;

	static {
		runningId = System.nanoTime() + System.currentTimeMillis();
		DecompositionOps.init();
		try {
			long mem = Runtime.getRuntime().maxMemory();
			if (mem < 133234688) {
				System.err.println("Available memory is very low: " + (mem / 1000000) + "M");
				System.err.println(
						"Invoke Java with the parameter -Xmx512M to increase available memory");
			}
		} catch (Throwable ignored) {
		}
	}

	public List<Matrix> getRowList() {
		// not quite optimized
		List<Matrix> list = new FastArrayList<Matrix>();
		for (int r = 0; r < getRowCount(); r++) {
			list.add(selectRows(Ret.LINK, r));
		}
		return list;
	}

	public List<Matrix> getColumnList() {
		// not quite optimized
		List<Matrix> list = new FastArrayList<Matrix>();
		for (int c = 0; c < getColumnCount(); c++) {
			list.add(selectRows(Ret.LINK, c));
		}
		return list;
	}

	protected AbstractMatrix(final long[] size) {
		verifyTrue(size.length > 1, "matrix must be at least 2d");
		for (int i = size.length - 1; i != -1; i--) {
			verifyTrue(size[i] >= 0, "coordinates must be positive");
		}
		this.size = size;
		id = runningId++;
	}

	public BaseMatrixFactory<? extends Matrix> getFactory() {
		if (isSparse()) {
			return SparseMatrix.Factory;
		} else {
			return DenseMatrix.Factory;
		}
	}

	public final Iterable<long[]> allCoordinates() {
		if (getDimensionCount() == 2) {
			return new CoordinateIterator2D(getSize());
		} else {
			return new CoordinateIterator(getSize());
		}
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

	public final Object getPreferredObject(long... coordinates) {
		return MathUtil.getPreferredObject(getAsObject(coordinates));
	}

	public ValueType getValueType() {
		return ValueType.OBJECT;
	}

	public Matrix getMetaDataDimensionMatrix(int dimension) {
		if (metaData == null) {
			metaData = new DefaultMapMatrix<String, Object>(new TreeMap<String, Object>());
		}
		Matrix m = (Matrix) metaData.get(DIMENSIONMETADATA + dimension);
		if (m == null) {
			long[] t = new long[getDimensionCount()];
			Arrays.fill(t, 1);
			m = new DefaultSparseObjectMatrix(t);
			metaData.put(DIMENSIONMETADATA + dimension, m);
		}
		return m;
	}

	public final Object getDimensionMetaData(int dimension, long... position) {
		if (metaData == null) {
			return null;
		} else {
			Matrix m = getMetaDataDimensionMatrix(dimension);
			long old = position[dimension];
			position[dimension] = 0;
			Object o = null;
			if (Coordinates.isSmallerThan(position, m.getSize())) {
				o = m.getAsObject(position);
			}
			position[dimension] = old;
			return o;
		}
	}

	public void setMetaDataDimensionMatrix(int dimension, Matrix matrix) {
		if (metaData == null) {
			metaData = new DefaultMapMatrix<String, Object>(new TreeMap<String, Object>());
		}
		metaData.put(DIMENSIONMETADATA + dimension, matrix);
	}

	public final String getDimensionLabel(int dimension) {
		return metaData == null ? null
				: StringUtil.getString(metaData.get(DIMENSIONMETADATA + dimension));
	}

	public final void setDimensionMetaData(int dimension, Object label, long... position) {
		if (metaData == null) {
			metaData = new DefaultMapMatrix<String, Object>(new TreeMap<String, Object>());
		}
		Matrix m = getMetaDataDimensionMatrix(dimension);
		long old = position[dimension];
		position[dimension] = 0;
		if (!Coordinates.isSmallerThan(position, m.getSize())) {
			long[] newSize = Coordinates.max(m.getSize(), Coordinates.plus(position, 1));
			m.setSize(newSize);
		}
		m.setAsObject(label, position);
		position[dimension] = old;
	}

	public final void setDimensionLabel(int dimension, Object label) {
		if (metaData == null) {
			metaData = new DefaultMapMatrix<String, Object>(new TreeMap<String, Object>());
		}
		Matrix m = getMetaDataDimensionMatrix(dimension);
		m.setLabel(label);
	}

	public GUIObject getGUIObject() {
		if (guiObject == null) {
			try {
				Class<?> c = Class.forName("org.ujmp.gui.DefaultMatrixGUIObject");
				Constructor<?> con = c.getConstructor(new Class<?>[] { Matrix.class });
				guiObject = (GUIObject) con.newInstance(new Object[] { this });
			} catch (Exception e) {
				throw new RuntimeException("cannot create matrix gui object", e);
			}
		}
		return guiObject;
	}

	public final boolean containsMissingValues() {
		for (long[] c : allCoordinates()) {
			double v = getAsDouble(c);
			if (MathUtil.isNaNOrInfinite(v)) {
				return true;
			}
		}
		return false;
	}

	public int getDimensionCount() {
		return getSize().length;
	}

	public final double getEuklideanValue() {
		double sum = 0.0;
		for (long[] c : allCoordinates()) {
			sum += Math.pow(getAsDouble(c), 2.0);
		}
		return Math.sqrt(sum);
	}

	public Matrix clone() {
		return Convert.calcNew(this);
	}

	public final Matrix select(Ret returnType, long[]... selection) {
		return new Selection(this, selection).calc(returnType);
	}

	public void save(String filename) throws IOException {
		save(new File(filename));
	}

	public void save(File file) throws IOException {
		SerializationUtil.saveCompressed(file, this);
	}

	public final Matrix select(Ret returnType, Collection<? extends Number>... selection) {
		return new Selection(this, selection).calc(returnType);
	}

	public Matrix selectRows(Ret returnType, long... rows) {
		return select(returnType, rows, null);
	}

	public final Matrix select(Ret returnType, String selection) {
		return new Selection(this, selection).calc(returnType);
	}

	public Matrix selectColumns(Ret returnType, long... columns) {
		return select(returnType, null, columns);
	}

	@SuppressWarnings("unchecked")
	public final Matrix selectRows(Ret returnType, Collection<? extends Number> rows) {
		return select(returnType, rows, null);
	}

	@SuppressWarnings("unchecked")
	public final Matrix selectColumns(Ret returnType, Collection<? extends Number> columns) {
		return select(returnType, null, columns);
	}

	public Matrix impute(Ret returnType, ImputationMethod method, Object... parameters) {
		return new Impute(this, method, parameters).calc(returnType);
	}

	public Matrix discretize(Ret returnType, int dimension, DiscretizationMethod method,
			int numberOfBins) {
		return new Discretize(this, dimension, method, numberOfBins).calc(returnType);
	}

	public Matrix discretize(Ret returnType, Dictionary dictionary) {
		return new DiscretizeDictionary(this, dictionary).calc(returnType);
	}

	public Matrix discretizeToBoolean(int targetColumnCount) {
		Matrix ret = SparseMatrix2D.Factory.zeros(getRowCount(), targetColumnCount);
		for (long[] c : availableCoordinates()) {
			Object o = getAsObject(c);
			String s = "col" + c[COLUMN] + ":" + o;
			ret.setAsBoolean(true, c[ROW], Math.abs(s.hashCode()) % targetColumnCount);
		}
		return ret;
	}

	public Matrix indexOfMax(Ret returnType, int dimension) {
		return new IndexOfMax(dimension, this).calc(returnType);
	}

	public Matrix indexOfMin(Ret returnType, int dimension) {
		return new IndexOfMin(dimension, this).calc(returnType);
	}

	public Matrix standardize(Ret returnType, int dimension) {
		return new Standardize(dimension, this).calc(returnType);
	}

	public Matrix normalize(Ret returnType, int dimension) {
		return new Normalize(dimension, this).calc(returnType);
	}

	public Matrix atimes(Ret returnType, boolean ignoreNaN, Matrix matrix) {
		return new Atimes(ignoreNaN, this, matrix).calc(returnType);
	}

	public Matrix inv() {
		return Inv.INSTANCE.calc(this);
	}

	public Matrix invSymm() {
		return Inv.INSTANCE.calc(this);
	}

	public Matrix invSPD() {
		return InvSPD.INSTANCE.calc(this);
	}

	public Matrix solve(Matrix b) {
		return Solve.INSTANCE.calc(this, b);
	}

	public Matrix solveSymm(Matrix b) {
		return Solve.INSTANCE.calc(this, b);
	}

	public Matrix solveSPD(Matrix b) {
		return SolveSPD.INSTANCE.calc(this, b);
	}

	public Matrix ginv() {
		return new Ginv(this).calcNew();
	}

	public Matrix princomp() {
		return new Princomp(this).calcNew();
	}

	public Matrix pinv() {
		return new Pinv(this).calcNew();
	}

	public Matrix pinv(int k) {
		Matrix[] usv = svd(k);
		Matrix u = usv[0];
		Matrix s = usv[1];
		Matrix v = usv[2];

		for (int i = (int) Math.min(s.getRowCount(), s.getColumnCount()); --i >= 0;) {
			double d = s.getAsDouble(i, i);
			if (Math.abs(d) > UJMPSettings.getInstance().getTolerance()) {
				s.setAsDouble(1.0 / d, i, i);
			} else {
				s.setAsDouble(0.0, i, i);
			}
		}

		return v.mtimes(s.transpose()).mtimes(u.transpose());
	}

	public Matrix center(Ret returnType, int dimension, boolean ignoreNaN) {
		return new Center(ignoreNaN, dimension, this).calc(returnType);
	}

	public boolean isResizable() {
		return false;
	}

	public final Matrix convert(ValueType newValueType) {
		return Convert.calcNew(newValueType, this);
	}

	public final Matrix replaceRegex(Ret returnType, Pattern search, String replacement) {
		return new ReplaceRegex(this, search, replacement).calc(returnType);
	}

	public final Matrix replace(Ret returnType, Object search, Object replacement) {
		return new Replace(this, search, replacement).calc(returnType);
	}

	public final Matrix replaceRegex(Ret returnType, String search, String replacement) {
		return new ReplaceRegex(this, search, replacement).calc(returnType);
	}

	public Matrix times(double factor) {
		Matrix result = this.getFactory().zeros(getSize());
		Matrix.timesScalar.calc(this, factor, result);
		return result;
	}

	public Matrix tanh() {
		final int rows = MathUtil.longToInt(getRowCount());
		final int cols = MathUtil.longToInt(getColumnCount());
		final Matrix result = Matrix.Factory.zeros(rows, cols);
		if (UJMPSettings.getInstance().getNumberOfThreads() > 1 && rows >= 100 && cols >= 100) {
			new PForEquidistant(0, rows - 1) {
				public void step(int i) {
					for (int c = 0; c < cols; c++) {
						result.setAsDouble(Math.tanh(getAsDouble(i, c)), i, c);
					}
				}
			};
		} else {
			for (int r = 0; r < rows; r++) {
				for (int c = 0; c < cols; c++) {
					result.setAsDouble(Math.tanh(getAsDouble(r, c)), r, c);
				}
			}
		}
		return result;
	}

	public Matrix times(Matrix m) {
		Matrix result = this.getFactory().zeros(getSize());
		Matrix.timesMatrix.calc(this, m, result);
		return result;
	}

	public Matrix divide(Matrix m) {
		Matrix result = this.getFactory().zeros(getSize());
		Matrix.divideMatrix.calc(this, m, result);
		return result;
	}

	public Matrix divide(double divisor) {
		Matrix result = this.getFactory().zeros(getSize());
		Matrix.divideScalar.calc(this, divisor, result);
		return result;
	}

	public Matrix divide(Ret returnType, boolean ignoreNaN, double factor) {
		return new DivideScalar(ignoreNaN, this, factor).calc(returnType);
	}

	public Matrix times(Ret returnType, boolean ignoreNaN, double factor) {
		return new TimesScalar(ignoreNaN, this, factor).calc(returnType);
	}

	public Matrix times(Ret returnType, boolean ignoreNaN, Matrix factor) {
		return new TimesMatrix(ignoreNaN, this, factor).calc(returnType);
	}

	public Matrix divide(Ret returnType, boolean ignoreNaN, Matrix factor) {
		return new DivideMatrix(ignoreNaN, this, factor).calc(returnType);
	}

	public final Matrix power(Ret returnType, double power) {
		return new Power(this, power).calc(returnType);
	}

	public final Matrix power(Ret returnType, Matrix power) {
		return new Power(this, power).calc(returnType);
	}

	public final Matrix gt(Ret returnType, Matrix matrix) {
		return new Gt(this, matrix).calc(returnType);
	}

	public final Matrix gt(Ret returnType, double value) {
		return new Gt(this, value).calc(returnType);
	}

	public final Matrix and(Ret returnType, Matrix matrix) {
		return new And(this, matrix).calc(returnType);
	}

	public final Matrix and(Ret returnType, boolean value) {
		return new And(this, value).calc(returnType);
	}

	public final Matrix or(Ret returnType, Matrix matrix) {
		return new Or(this, matrix).calc(returnType);
	}

	public final Matrix or(Ret returnType, boolean value) {
		return new Or(this, value).calc(returnType);
	}

	public final Matrix xor(Ret returnType, Matrix matrix) {
		return new Xor(this, matrix).calc(returnType);
	}

	public final Matrix xor(Ret returnType, boolean value) {
		return new Xor(this, value).calc(returnType);
	}

	public final Matrix not(Ret returnType) {
		return new Not(this).calc(returnType);
	}

	public final Matrix lt(Ret returnType, Matrix matrix) {
		return new Lt(this, matrix).calc(returnType);
	}

	public final Matrix lt(Ret returnType, double value) {
		return new Lt(this, value).calc(returnType);
	}

	public final Matrix ge(Ret returnType, Matrix matrix) {
		return new Ge(this, matrix).calc(returnType);
	}

	public final Matrix ge(Ret returnType, double value) {
		return new Ge(this, value).calc(returnType);
	}

	public final Matrix le(Ret returnType, Matrix matrix) {
		return new Le(this, matrix).calc(returnType);
	}

	public final Matrix le(Ret returnType, double value) {
		return new Le(this, value).calc(returnType);
	}

	public final Matrix eq(Ret returnType, Matrix matrix) {
		return new Eq(this, matrix).calc(returnType);
	}

	public final Matrix eq(Ret returnType, Object value) {
		return new Eq(this, value).calc(returnType);
	}

	public final Matrix ne(Ret returnType, Matrix matrix) {
		return new Ne(this, matrix).calc(returnType);
	}

	public final Matrix ne(Ret returnType, Object value) {
		return new Ne(this, value).calc(returnType);
	}

	public long getValueCount() {
		return Coordinates.product(getSize());
	}

	public final long[] getCoordinatesOfMaximum() {
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

	public final long[] getCoordinatesOfMinimum() {
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
		return select(Ret.LINK, selection).allCoordinates();
	}

	public Iterable<long[]> selectedCoordinates(long[]... selection) {
		return select(Ret.LINK, selection).allCoordinates();
	}

	public boolean isTransient() {
		return false;
	}

	public Iterable<long[]> nonZeroCoordinates() {
		return availableCoordinates();
	}

	public double[][] toDoubleArray() {
		final int rows = (int) getRowCount();
		final int columns = (int) getColumnCount();
		final double[][] values = new double[rows][columns];
		if (this instanceof HasColumnMajorDoubleArray1D) {
			final double[] m = ((HasColumnMajorDoubleArray1D) this).getColumnMajorDoubleArray1D();
			for (int r = 0; r < rows; r++) {
				final double[] valuesr = values[r];
				for (int c = 0; c < columns; c++) {
					valuesr[c] = m[c * rows + r];
				}
			}
		} else if (this instanceof HasRowMajorDoubleArray2D) {
			final double[][] m = ((HasRowMajorDoubleArray2D) this).getRowMajorDoubleArray2D();
			for (int r = 0; r < rows; r++) {
				System.arraycopy(m[r], 0, values[r], 0, columns);
			}
		} else if (this instanceof DenseDoubleMatrix2D) {
			final DenseDoubleMatrix2D m = (DenseDoubleMatrix2D) this;
			for (int r = 0; r < rows; r++) {
				final double[] valuesr = values[r];
				for (int c = 0; c < columns; c++) {
					valuesr[c] = m.getDouble(r, c);
				}
			}
		} else {
			for (int r = 0; r < rows; r++) {
				final double[] valuesr = values[r];
				for (int c = 0; c < columns; c++) {
					valuesr[c] = getAsDouble(r, c);
				}
			}
		}
		return values;
	}

	public Object[][] toObjectArray() {
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

	public int[][] toIntArray() {
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

	public long[][] toLongArray() {
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

	public short[][] toShortArray() {
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

	public char[][] toCharArray() {
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

	public String[][] toStringArray() {
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

	public byte[][] toByteArray() {
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

	public boolean[][] toBooleanArray() {
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

	public float[][] toFloatArray() {
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

	public Date[][] toDateArray() {
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

	public BigDecimal[][] toBigDecimalArray() {
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

	public BigInteger[][] toBigIntegerArray() {
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

	public final Matrix sqrt(Ret returnType) {
		return new Sqrt(this).calc(returnType);
	}

	public final Matrix round(Ret returnType) {
		return new Round(this).calc(returnType);
	}

	public final Matrix ceil(Ret returnType) {
		return new Ceil(this).calc(returnType);
	}

	public final Matrix extractAnnotation(Ret returnType, int dimension) {
		return new ExtractAnnotation(this, dimension).calc(returnType);
	}

	public final Matrix includeAnnotation(Ret returnType, int dimension) {
		return new IncludeAnnotation(this, dimension).calc(returnType);
	}

	public final Matrix floor(Ret returnType) {
		return new Floor(this).calc(returnType);
	}

	public final JFrame showGUI() {
		try {
			Class<?> c = Class.forName("org.ujmp.gui.util.FrameManager");
			Method method = c.getMethod("showFrame", new Class[] { GUIObject.class });
			Object o = method.invoke(null, new Object[] { getGUIObject() });
			return (JFrame) o;
		} catch (Exception e) {
			throw new RuntimeException("cannot show GUI", e);
		}
	}

	public void fireValueChanged() {
		if (guiObject != null) {
			guiObject.fireValueChanged();
		}
	}

	public void fireValueChanged(Coordinates coordinates, Object object) {
		if (guiObject != null) {
			guiObject.fireValueChanged(coordinates, object);
		}
	}

	public void fireValueChanged(Coordinates start, Coordinates end) {
		if (guiObject != null) {
			guiObject.fireValueChanged(start, end);
		}
	}

	public Matrix mtimes(Matrix matrix) {
		Matrix result = getFactory().zeros(getRowCount(), matrix.getColumnCount());
		Matrix.mtimes.calc(this, matrix, result);
		return result;
	}

	public Matrix mtimes(Ret returnType, boolean ignoreNaN, Matrix matrix) {
		return new Mtimes(ignoreNaN, this, matrix).calc(returnType);
	}

	public boolean getAsBoolean(long... coordinates) {
		return MathUtil.getBoolean(getAsObject(coordinates));
	}

	public void setAsBoolean(boolean value, long... coordinates) {
		setAsDouble(value ? 1.0 : 0.0, coordinates);
	}

	public int getAsInt(long... coordinates) {
		return (int) getAsDouble(coordinates);
	}

	public void setAsInt(int value, long... coordinates) {
		setAsDouble(value, coordinates);
	}

	public byte getAsByte(long... coordinates) {
		return (byte) getAsDouble(coordinates);
	}

	public byte[] getAsByteArray(long... coordinates) {
		final Object o = getAsObject(coordinates);
		if (o == null) {
			return null;
		} else if (o instanceof String) {
			return ((String) o).getBytes();
		} else {
			return null;
		}
	}

	public void setAsByteArray(byte[] value, long... coordinates) {
		if (value == null) {
			setAsObject(null, coordinates);
		} else {
			setAsString(new String(value));
		}
	}

	public void setAsByte(byte value, long... coordinates) {
		setAsDouble(value, coordinates);
	}

	public char getAsChar(long... coordinates) {
		return (char) getAsDouble(coordinates);
	}

	public BigInteger getAsBigInteger(long... coordinates) {
		return MathUtil.getBigInteger(getAsObject(coordinates));
	}

	public BigDecimal getAsBigDecimal(long... coordinates) {
		return MathUtil.getBigDecimal(getAsObject(coordinates));
	}

	public void setAsChar(char value, long... coordinates) {
		setAsDouble(value, coordinates);
	}

	public void setAsBigDecimal(BigDecimal value, long... coordinates) {
		if (value == null) {
			setAsDouble(Double.NaN, coordinates);
		} else {
			setAsDouble(value.doubleValue(), coordinates);
		}
	}

	public void setAsBigInteger(BigInteger value, long... coordinates) {
		if (value == null) {
			setAsLong(0l, coordinates);
		} else {
			setAsLong(value.longValue(), coordinates);
		}
	}

	public float getAsFloat(long... coordinates) {
		return (float) getAsDouble(coordinates);
	}

	public void setAsFloat(float value, long... coordinates) {
		setAsDouble(value, coordinates);
	}

	public short getAsShort(long... coordinates) {
		return (short) getAsDouble(coordinates);
	}

	public Matrix getAsMatrix(long... coordinates) {
		return MathUtil.getMatrix(getAsObject(coordinates));
	}

	public void setAsMatrix(Matrix m, long... coordinates) {
		setAsObject(m, coordinates);
	}

	public void setAsShort(short value, long... coordinates) {
		setAsDouble(value, coordinates);
	}

	public long getAsLong(long... coordinates) {
		return (long) getAsDouble(coordinates);
	}

	public void setAsLong(long value, long... coordinates) {
		setAsDouble(value, coordinates);
	}

	public Date getAsDate(long... coordinates) {
		return MathUtil.getDate(getAsObject(coordinates));
	}

	public void setAsDate(Date date, long... coordinates) {
		setAsObject(date, coordinates);
	}

	public final Matrix delete(Ret returnType, String selection) {
		return new Deletion(this, selection).calc(returnType);
	}

	public final Matrix delete(Ret returnType, Collection<? extends Number>... selection) {
		return new Deletion(this, selection).calc(returnType);
	}

	public final Matrix delete(Ret returnType, long[]... selection) {
		return new Deletion(this, selection).calc(returnType);
	}

	public final Matrix deleteRows(Ret returnType, long... rows) {
		return delete(returnType, rows, new long[] {});
	}

	@SuppressWarnings("unchecked")
	public final Matrix deleteRows(Ret returnType, Collection<? extends Number> rows) {
		return delete(returnType, rows, new ArrayList<Long>());
	}

	@SuppressWarnings("unchecked")
	public final Matrix deleteColumns(Ret returnType, Collection<? extends Number> columns) {
		return delete(returnType, new ArrayList<Long>(), columns);
	}

	public final Matrix deleteColumns(Ret returnType, long... columns) {
		return delete(returnType, new long[] {}, columns);
	}

	public Matrix minus(Ret returnType, boolean ignoreNaN, double v) {
		return new MinusScalar(ignoreNaN, this, v).calc(returnType);
	}

	public Matrix minus(Ret returnType, boolean ignoreNaN, Matrix m) {
		return new MinusMatrix(ignoreNaN, this, m).calc(returnType);
	}

	public Matrix plus(Ret returnType, boolean ignoreNaN, double v) {
		return new PlusScalar(ignoreNaN, this, v).calc(returnType);
	}

	public Matrix plus(Ret returnType, boolean ignoreNaN, Matrix m) {
		return new PlusMatrix(ignoreNaN, this, m).calc(returnType);
	}

	public Matrix transpose() {
		Matrix result = getFactory().zeros(Coordinates.transpose(getSize()));
		Matrix.transpose.calc(this, result);
		return result;
	}

	public Matrix transpose(Ret returnType) {
		return new Transpose(this).calc(returnType);
	}

	public Matrix diag(Ret returnType) {
		return new Diag(this).calc(returnType);
	}

	public Matrix mean(Ret returnType, int dimension, boolean ignoreNaN) {
		return new Mean(dimension, ignoreNaN, this).calc(returnType);
	}

	public Matrix var(Ret returnType, int dimension, boolean ignoreNaN, boolean besselsCorrection) {
		return new Var(dimension, ignoreNaN, this, besselsCorrection).calc(returnType);
	}

	public Matrix std(Ret returnType, int dimension, boolean ignoreNaN, boolean besselsCorrection) {
		return new Std(dimension, ignoreNaN, this, besselsCorrection).calc(returnType);
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

	public long[] getSize() {
		return size;
	}

	public Matrix prod(Ret returnType, int dimension, boolean ignoreNaN) {
		return new Prod(dimension, ignoreNaN, this).calc(returnType);
	}

	public Matrix diff(Ret returnType, int dimension, boolean ignoreNaN) {
		return new Diff(dimension, ignoreNaN, this).calc(returnType);
	}

	public final Matrix sum(Ret returnType, int dimension, boolean ignoreNaN) {
		return new Sum(dimension, ignoreNaN, this).calc(returnType);
	}

	public final Matrix sign(Ret returnType) {
		return new Sign(this).calc(returnType);
	}

	public String toString() {
		if (this instanceof MapMatrix) {
			return UJMPFormat.getMapInstance().format(this);
		} else {
			return UJMPFormat.getMultiLineInstance().format(this);
		}
	}

	public String toHtml() {
		try {
			return exportTo().string().asHtml();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public final Matrix ones(Ret ret) {
		return new Ones(this).calc(ret);
	}

	public final Matrix nans(Ret ret) {
		return new NaNs(this).calc(ret);
	}

	public final Matrix fill(Ret ret, Object value) {
		return new Fill(this, value).calc(ret);
	}

	public final Matrix zeros(Ret ret) {
		return new Zeros(this).calc(ret);
	}

	public final Matrix eye(Ret ret) {
		return new Eye(this).calc(ret);
	}

	public Matrix plus(double value) {
		Matrix result = this.getFactory().zeros(getSize());
		Matrix.plusScalar.calc(this, value, result);
		return result;
	}

	public Matrix plus(Matrix m) {
		Matrix result = this.getFactory().zeros(getSize());
		Matrix.plusMatrix.calc(this, m, result);
		return result;
	}

	public Matrix minus(double value) {
		Matrix result = this.getFactory().zeros(getSize());
		Matrix.minusScalar.calc(this, value, result);
		return result;
	}

	public Matrix minus(Matrix m) {
		Matrix result = this.getFactory().zeros(getSize());
		Matrix.minusMatrix.calc(this, m, result);
		return result;
	}

	public final Matrix rand(Ret ret) {
		return new Rand(this).calc(ret);
	}

	public final Matrix randn(Ret ret) {
		return new Randn(this).calc(ret);
	}

	// TODO: this should also work for objects and Strings
	public int compareTo(Matrix m) {
		double v1 = doubleValue();
		double v2 = m.doubleValue();
		return new Double(v1).compareTo(v2);
	}

	public int rank() {
		int rank = 0;

		Matrix[] usv = svd();
		Matrix s = usv[1];

		for (int i = (int) Math.min(s.getSize(ROW), s.getSize(COLUMN)); --i >= 0;) {
			if (Math.abs(s.getAsDouble(i, i)) > UJMPSettings.getInstance().getTolerance()) {
				rank++;
			}
		}

		return rank;
	}

	public final boolean isSPD() {
		if (getDimensionCount() != 2) {
			return false;
		}
		if (!isSquare()) {
			return false;
		}
		return new Chol.CholMatrix(this).isSPD();
	}

	public final boolean isSymmetric() {
		if (getDimensionCount() != 2) {
			throw new RuntimeException("only supported for 2d matrices");
		}
		if (isSquare()) {
			return false;
		}

		if (this instanceof DenseDoubleMatrix2D) {
			DenseDoubleMatrix2D m = (DenseDoubleMatrix2D) this;
			for (long r = getRowCount(); --r >= 0;) {
				for (long c = getColumnCount(); --c >= 0;) {
					if (m.getDouble(r, c) != m.getDouble(c, r)) {
						return false;
					}
				}
			}
		} else {
			for (long[] c : availableCoordinates()) {
				Object o1 = getAsObject(c);
				Object o2 = getAsObject(Coordinates.transpose(c));
				if (!MathUtil.equals(o1, o2)) {
					return false;
				}
			}
		}

		return true;
	}

	public boolean isEmpty() {
		for (long[] c : availableCoordinates()) {
			if (getAsDouble(c) != 0.0) {
				return false;
			}
		}
		return true;
	}

	public final Matrix abs(Ret returnType) {
		return new Abs(this).calc(returnType);
	}

	public final Matrix log(Ret returnType) {
		return new Log(this).calc(returnType);
	}

	public final Matrix setContent(Ret returnType, Matrix newContent, long... position) {
		return new SetContent(this, newContent, position).calc(returnType);
	}

	public final Matrix exp(Ret returnType) {
		return new Exp(this).calc(returnType);
	}

	public final Matrix logistic(Ret returnType) {
		return new LogisticFunction(this).calc(returnType);
	}

	public final Matrix sortrows(Ret returnType, long column, boolean reverse) {
		return new Sortrows(this, column, reverse).calc(returnType);
	}

	public final Matrix cumsum(boolean ignoreNaN) {
		return new Cumsum(this, ignoreNaN).calcNew();
	}

	public final Matrix cumprod(boolean ignoreNaN) {
		return new Cumprod(this, ignoreNaN).calcNew();
	}

	public final Matrix log2(Ret returnType) {
		return new Log2(this).calc(returnType);
	}

	public final Matrix log10(Ret returnType) {
		return new Log10(this).calc(returnType);
	}

	public final boolean isDiagonal() {
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
		return getDimensionCount() == 2 && getColumnCount() == getRowCount();
	}

	public double euklideanDistanceTo(Matrix m, boolean ignoreNaN) {
		return minkowskiDistanceTo(m, 2, ignoreNaN);
	}

	public double det() {
		if (getDimensionCount() != 2 || !isSquare()) {
			throw new RuntimeException("only supported for 2d square matrices");
		}
		return new LU.LUMatrix(this).det();
	}

	public double pdet() {
		Matrix s = svd()[1];
		double prod = 1;
		for (int i = 0; i < s.getRowCount(); i++) {
			double v = s.getAsDouble(i, i);
			if (v > 0) {
				prod *= v;
			}
		}
		return prod;
	}

	public boolean isSingular() {
		if (getDimensionCount() != 2 || !isSquare()) {
			return false;
		}
		return !new LU.LUMatrix(this).isNonsingular();
	}

	public double manhattenDistanceTo(Matrix m, boolean ignoreNaN) {
		return minkowskiDistanceTo(m, 1, ignoreNaN);
	}

	public Matrix manhattenDistance(Ret returnType, boolean ignoreNaN) {
		return minkowskiDistance(returnType, 1, ignoreNaN);
	}

	public Matrix euklideanDistance(Ret returnType, boolean ignoreNaN) {
		return minkowskiDistance(returnType, 2, ignoreNaN);
	}

	public final Matrix minkowskiDistance(Ret returnType, double p, boolean ignoreNaN) {
		return new MinkowskiDistance(this, p, ignoreNaN).calc(returnType);
	}

	public final Matrix cosineSimilarity(Ret returnType, boolean ignoreNaN) {
		return new CosineSimilarity(this, ignoreNaN).calc(returnType);
	}

	public double cosineSimilarityTo(Matrix m, boolean ignoreNaN) {
		return CosineSimilarity.getCosineSimilartiy(this, m, ignoreNaN);
	}

	public double minkowskiDistanceTo(Matrix m, double p, boolean ignoreNaN) {
		VerifyUtil.verifySameSize(this, m);
		double sum = 0.0;
		if (ignoreNaN) {
			for (long[] c : allCoordinates()) {
				sum += MathUtil
						.ignoreNaN(Math.pow(Math.abs((getAsDouble(c)) - m.getAsDouble(c)), p));
			}
		} else {
			for (long[] c : allCoordinates()) {
				sum += Math.pow(Math.abs((getAsDouble(c)) - m.getAsDouble(c)), p);
			}
		}
		return Math.pow(sum, 1 / p);
	}

	public double chebyshevDistanceTo(Matrix m, boolean ignoreNaN) {
		VerifyUtil.verifySameSize(this, m);
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

	public Matrix min(Ret returnType, int dimension) {
		return new Min(dimension, this).calc(returnType);
	}

	public Matrix max(Ret returnType, int dimension) {
		return new Max(dimension, this).calc(returnType);
	}

	public final Matrix addMissing(Ret returnType, int dimension, double... percentMissing) {
		return new AddMissing(dimension, this, percentMissing).calc(returnType);
	}

	public Matrix countMissing(Ret returnType, int dimension) {
		return new CountMissing(dimension, this).calc(returnType);
	}

	public final boolean isScalar() {
		return Coordinates.product(getSize()) == 1;
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

	public Matrix sinh(Ret returnType) {
		return new Sinh(this).calc(returnType);
	}

	public Matrix cosh(Ret returnType) {
		return new Cosh(this).calc(returnType);
	}

	public Matrix tanh(Ret returnType) {
		return new Tanh(this).calc(returnType);
	}

	public Matrix sin(Ret returnType) {
		return new Sin(this).calc(returnType);
	}

	public Matrix cos(Ret returnType) {
		return new Cos(this).calc(returnType);
	}

	public Matrix tril(Ret returnType, int k) {
		return new Tril(this, k).calc(returnType);
	}

	public Matrix triu(Ret returnType, int k) {
		return new Triu(this, k).calc(returnType);
	}

	public Matrix tan(Ret returnType) {
		return new Tan(this).calc(returnType);
	}

	public Matrix cov(Ret returnType, boolean ignoreNaN, boolean besselsCorrection) {
		return new Cov(ignoreNaN, this, besselsCorrection).calc(returnType);
	}

	public Matrix corrcoef(Ret returnType, boolean ignoreNaN, boolean besselsCorrection) {
		return new Corrcoef(ignoreNaN, this, besselsCorrection).calc(returnType);
	}

	public Matrix mutualInf(Ret returnType) {
		return new MutualInformation(this).calc(returnType);
	}

	public Matrix pairedTTest(Ret returnType) {
		try {
			Class<?> c = Class.forName("org.ujmp.commonsmath.PairedTTest");
			Constructor<?> con = c.getConstructor(Matrix.class);
			Calculation calc = (Calculation) con.newInstance(this);
			return calc.calc(returnType);
		} catch (Exception e) {
			throw new RuntimeException("could not calculate", e);
		}
	}

	public Matrix bootstrap(Ret returnType) {
		return new Bootstrap(this).calc(returnType);
	}

	public Matrix grayScale(Ret returnType, ColorChannel colorChannel) {
		return new GrayScale(this, colorChannel).calc(returnType);
	}

	public Matrix translate(Ret returnType, String sourceLanguage, String targetLanguage) {
		return new Translate(this, sourceLanguage, targetLanguage).calc(returnType);
	}

	public Matrix lowerCase(Ret returnType) {
		return new LowerCase(this).calc(returnType);
	}

	public Matrix upperCase(Ret returnType) {
		return new UpperCase(this).calc(returnType);
	}

	public Matrix convertEncoding(Ret returnType, String encoding) {
		return new ConvertEncoding(this, encoding).calc(returnType);
	}

	public Matrix tfIdf(boolean calculateTf, boolean calculateIdf, boolean normalize) {
		return new TfIdf(this, calculateTf, calculateIdf, normalize).calc(Ret.NEW);
	}

	public Matrix removePunctuation(Ret ret) {
		return new RemovePunctuation(this).calc(ret);
	}

	public Matrix stem(Ret ret) {
		return new Stem(this).calc(ret);
	}

	public Matrix removeWords(Ret ret, Collection<String> words) {
		return new RemoveWords(this, words).calc(ret);
	}

	public Matrix unique(Ret returnType) {
		return new Unique(this).calc(returnType);
	}

	public Matrix uniqueValueCount(Ret returnType, int dimension) {
		return new UniqueValueCount(this, dimension).calc(returnType);
	}

	public Matrix bootstrap(Ret returnType, int count) {
		return new Bootstrap(this, count).calc(returnType);
	}

	public Matrix transpose(Ret returnType, int dimension1, int dimension2) {
		return new Transpose(this, dimension1, dimension2).calc(returnType);
	}

	public Matrix swap(Ret returnType, int dimension, long pos1, long pos2) {
		return new Swap(dimension, pos1, pos2, this).calc(returnType);
	}

	public Matrix flipdim(Ret returnType, int dimension) {
		return new Flipdim(dimension, this).calc(returnType);
	}

	public final Matrix shuffle(Ret returnType) {
		return new Shuffle(this).calc(returnType);
	}

	public final double trace() {
		double sum = 0.0;
		for (long i = Math.min(getRowCount(), getColumnCount()); --i >= 0;) {
			sum += getAsDouble(i, i);
		}
		return sum;
	}

	public void setLabel(Object label) {
		if (metaData == null) {
			metaData = new DefaultMapMatrix<String, Object>(new TreeMap<String, Object>());
		}
		metaData.put(LABEL, label);
	}

	public final String getLabel() {
		Object o = getLabelObject();
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

	public void setAsString(String string, long... coordinates) {
		setAsObject(string, coordinates);
	}

	public boolean isReadOnly() {
		return false;
	}

	public String getAsString(long... coordinates) {
		return StringUtil.convert(getAsObject(coordinates));
	}

	public final double getMaxValue() {
		return Max.calc(this);
	}

	public final double getMinValue() {
		return Min.calc(this);
	}

	public final double getMeanValue() {
		return Mean.calc(this);
	}

	public final double getStdValue() {
		return std(Ret.NEW, Matrix.ALL, true, true).getEuklideanValue();
	}

	public final double getValueSum() {
		double sum = 0.0;
		for (long[] c : allCoordinates()) {
			sum += getAsDouble(c);
		}
		return sum;
	}

	public final double getAbsoluteValueSum() {
		double sum = 0.0;
		for (long[] c : allCoordinates()) {
			sum += Math.abs(getAsDouble(c));
		}
		return sum;
	}

	public final String getColumnLabel(long col) {
		if (getDimensionCount() != 2) {
			throw new RuntimeException("This function is only supported for 2D matrices");
		}
		return StringUtil.convert(getDimensionMetaData(ROW, new long[] { 0, col }));
	}

	public final String getRowLabel(long row) {
		if (getDimensionCount() != 2) {
			throw new RuntimeException("This function is only supported for 2D matrices");
		}
		return StringUtil.convert(getDimensionMetaData(COLUMN, new long[] { row, 0 }));
	}

	public final long getRowForLabel(Object object) {
		if (getDimensionCount() != 2) {
			throw new RuntimeException("This function is only supported for 2D matrices");
		}
		return getPositionForLabel(COLUMN, object)[ROW];
	}

	public final long getColumnForLabel(Object object) {
		if (getDimensionCount() != 2) {
			throw new RuntimeException("This function is only supported for 2D matrices");
		}
		return getPositionForLabel(ROW, object)[COLUMN];
	}

	public final long[] getPositionForLabel(int dimension, Object label) {
		if (label == null) {
			throw new RuntimeException("label is null");
		}
		if (metaData == null) {
			long[] t = Coordinates.copyOf(getSize());
			t[dimension] = -1;
			return t;
		} else {
			Matrix m = getMetaDataDimensionMatrix(dimension);
			for (long[] c : m.availableCoordinates()) {
				Object o = m.getAsObject(c);
				if (label.equals(o)) {
					return c;
				}
			}
			long[] t = new long[getDimensionCount()];
			Arrays.fill(t, -1);
			return t;
		}
	}

	public final void setColumnLabel(long col, Object label) {
		if (getDimensionCount() != 2) {
			throw new RuntimeException("This function is only supported for 2D matrices");
		}
		setDimensionMetaData(ROW, label, new long[] { 0, col });
	}

	public final void setRowLabel(long row, Object label) {
		if (getDimensionCount() != 2) {
			throw new RuntimeException("This function is only supported for 2D matrices");
		}
		setDimensionMetaData(COLUMN, label, new long[] { row, 0 });
	}

	public final double getAbsoluteValueMean() {
		return getAbsoluteValueSum() / getValueCount();
	}

	public final Matrix toRowVector(Ret returnType) {
		if (isRowVector()) {
			return this;
		} else if (isColumnVector()) {
			return transpose(returnType);
		} else {
			return reshape(returnType, Coordinates.product(getSize()), 1);
		}
	}

	public final Matrix toColumnVector(Ret returnType) {
		if (isColumnVector()) {
			return this;
		} else if (isRowVector()) {
			return transpose(returnType);
		} else {
			return reshape(returnType, 1, (int) Coordinates.product(getSize()));
		}
	}

	public Matrix replaceMissingBy(Matrix matrix) {
		Matrix ret = Matrix.Factory.zeros(getSize());
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

	public final Matrix deleteColumnsWithMissingValues(Ret returnType) {
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

	public final Matrix deleteRowsWithMissingValues(Ret returnType, long threshold) {
		Matrix mv = countMissing(Ret.NEW, Matrix.COLUMN);
		List<Long> sel = new ArrayList<Long>();
		for (long r = 0; r < mv.getRowCount(); r++) {
			if (mv.getAsLong(r, 0) < threshold)
				sel.add(r);
		}
		if (sel.isEmpty()) {
			return Matrix.Factory.emptyMatrix();
		} else {
			return selectRows(returnType, sel);
		}
	}

	public final Matrix appendHorizontally(Ret returnType, Matrix... matrices) {
		return append(returnType, COLUMN, matrices);
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

	public final Matrix appendVertically(Ret returnType, Matrix... matrices) {
		return append(returnType, ROW, matrices);
	}

	public final Matrix append(Ret returnType, int dimension, Matrix... matrices) {
		Matrix[] mtotal = new Matrix[matrices.length + 1];
		mtotal[0] = this;
		System.arraycopy(matrices, 0, mtotal, 1, matrices.length);
		return new Concatenation(dimension, mtotal).calc(returnType);
	}

	public final Matrix discretizeToColumns(long column) {
		return new DiscretizeToColumns(this, false, column).calc(Ret.NEW);
	}

	public final Matrix subMatrix(Ret returnType, long... startAndEndCoordinates) {
		return new SubMatrix(this, startAndEndCoordinates).calc(returnType);
	}

	public Matrix[] svd() {
		return SVD.INSTANCE.calc(this);
	}

	public final Matrix[] svd(int k) {

		// how many iterations for self-multiplication, good values seem to be
		// in the range 3 to 8
		final int iterations = 3;

		// self-multiplication
		Matrix aSquared = this.mtimes(transpose());
		for (int i = 0; i < iterations; i++) {
			aSquared = aSquared.mtimes(aSquared);
		}

		// multiply with random matrix
		Matrix o = Matrix.Factory.randn(getRowCount(), k);
		Matrix y = aSquared.mtimes(this).mtimes(o);

		// decompose
		Matrix[] qr = y.qr();
		Matrix q = qr[0];

		// calculate low rank SVD
		Matrix b = q.transpose().mtimes(this);
		Matrix[] svd = b.svd();
		Matrix uHat = svd[0];
		Matrix s = svd[1];
		Matrix v = svd[2];
		Matrix u = q.mtimes(uHat);

		return new Matrix[] { u, s, v };
	}

	public Matrix[] eig() {
		return Eig.INSTANCE.calc(this);
	}

	public Matrix[] eigSymm() {
		return Eig.INSTANCE.calc(this);
	}

	public Matrix[] qr() {
		return QR.INSTANCE.calc(this);
	}

	public Matrix[] lu() {
		return LU.INSTANCE.calc(this);
	}

	public Matrix chol() {
		return Chol.INSTANCE.calc(this);
	}

	public void setSize(long... size) {
		throw new RuntimeException("operation not possible: cannot change size of matrix");
	}

	public final Matrix reshape(Ret returnType, long... newSize) {
		return new Reshape(this, newSize).calc(returnType);
	}

	public final Matrix squeeze(Ret returnType) {
		return new Squeeze(this).calc(returnType);
	}

	public final double doubleValue() {
		if (isScalar()) {
			return getAsDouble(0, 0);
		}
		return Double.NaN;
	}

	public final int intValue() {
		if (isScalar()) {
			return getAsInt(0, 0);
		}
		return 0;
	}

	public final char charValue() {
		if (isScalar()) {
			return getAsChar(0, 0);
		}
		return 0;
	}

	public final BigInteger bigIntegerValue() {
		if (isScalar()) {
			return getAsBigInteger(0, 0);
		}
		return null;
	}

	public final BigDecimal bigDecimalValue() {
		if (isScalar()) {
			return getAsBigDecimal(0, 0);
		}
		return null;
	}

	public final Matrix fadeIn(Ret ret, int dimension) {
		return new FadeIn(dimension, this).calc(ret);
	}

	public final Matrix fadeOut(Ret ret, int dimension) {
		return new FadeOut(dimension, this).calc(ret);
	}

	public final float floatValue() {
		if (isScalar()) {
			return getAsFloat(0, 0);
		}
		return Float.NaN;
	}

	public final long longValue() {
		if (isScalar()) {
			return getAsLong(0, 0);
		}
		return 0;
	}

	public final Date dateValue() {
		if (isScalar()) {
			return getAsDate(0, 0);
		}
		return MathUtil.getDate(null);
	}

	public final boolean booleanValue() {
		if (isScalar()) {
			return getAsBoolean(0, 0);
		}
		return false;
	}

	public final String stringValue() {
		if (isScalar()) {
			return getAsString(0, 0);
		} else {
			return toString();
		}
	}

	public final double getRMS() {
		double sum = 0.0;
		long count = 0;
		for (long[] c : allCoordinates()) {
			sum += Math.pow(getAsDouble(c), 2.0);
			count++;
		}
		sum /= count;
		return Math.sqrt(sum);
	}

	public final Object getMetaData(Object key) {
		return metaData == null ? null : metaData.get(key);
	}

	public final Matrix getMetaDataMatrix(Object key) {
		return metaData == null ? null : MathUtil.getMatrix(metaData.get(key));
	}

	public final double getMetaDataDouble(Object key) {
		return metaData == null ? Double.NaN : MathUtil.getDouble(metaData.get(key));
	}

	public final String getMetaDataString(Object key) {
		return metaData == null ? null : StringUtil.getString(metaData.get(key));
	}

	public final MapMatrix<String, Object> getMetaData() {
		return metaData;
	}

	public final void setMetaData(MapMatrix<String, Object> metaData) {
		this.metaData = metaData;
	}

	public final void setMetaData(String key, Object value) {
		if (metaData == null) {
			metaData = new DefaultMapMatrix<String, Object>(new TreeMap<String, Object>());
		}
		metaData.put(key, value);
	}

	public final boolean equalsAnnotation(Object o) {
		if (this == o) {
			return true;
		}
		if (o instanceof Matrix) {
			Matrix m = (Matrix) o;
			MapMatrix<String, Object> a1 = getMetaData();
			MapMatrix<String, Object> a2 = m.getMetaData();
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

	public boolean equals(Object o) {
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
						} else if (o1 == null && o2 == null) {
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
						} else if (o1 == null && o2 == null) {
						} else {
							return false;
						}
					}
				} else if (this instanceof MapMatrix && m instanceof MapMatrix) {
					MapMatrix<?, ?> map1 = (MapMatrix<?, ?>) this;
					MapMatrix<?, ?> map2 = (MapMatrix<?, ?>) m;
					for (Object key : map1.keySet()) {
						Object o1 = map1.get(key);
						Object o2 = map2.get(key);
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
			throw new RuntimeException("could not compare", e);
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

	public final BaseBigDecimalMatrix toBigDecimalMatrix() {
		return new ToBigDecimalMatrix(this).calcLink();
	}

	public final BigIntegerMatrix toBigIntegerMatrix() {
		return new ToBigIntegerMatrix(this).calcLink();
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

	public MatrixExportDestinationSelector exportTo() {
		if (this instanceof DenseMatrix) {
			return new DefaultMatrixExportDestinationSelector((DenseMatrix) this);
		} else {
			throw new RuntimeException("export only works for dense matrices");
		}
	}

	public MatrixImportSourceSelector importFrom() {
		if (this instanceof DenseMatrix) {
			return new DefaultMatrixImportSourceSelector(this);
		} else {
			throw new RuntimeException("import only works for dense matrices");
		}
	}

	public final String getId() {
		return metaData == null ? null : StringUtil.getString(metaData.get(ID));
	}

	public final void setId(String id) {
		if (metaData == null) {
			metaData = new DefaultMapMatrix<String, Object>(new TreeMap<String, Object>());
		}
		metaData.put(ID, id);
	}

	public final String getDescription() {
		return metaData == null ? null : StringUtil.getString(metaData.get(DESCRIPTION));
	}

	public final Object getLabelObject() {
		return metaData == null ? null : metaData.get(LABEL);
	}

	public final void setDescription(String description) {
		if (metaData == null) {
			metaData = new DefaultMapMatrix<String, Object>(new TreeMap<String, Object>());
		}
		metaData.put(DESCRIPTION, description);
	}

	public final void share(String hostname, int port) throws UnknownHostException, IOException {
		ServerSocket serverSocket = new ServerSocket(port, 50, InetAddress.getByName(hostname));
		new MatrixSocketThread(this, serverSocket);
	}

	public final void share(int port) throws IOException {
		ServerSocket serverSocket = new ServerSocket(port);
		new MatrixSocketThread(this, serverSocket);
	}

}
