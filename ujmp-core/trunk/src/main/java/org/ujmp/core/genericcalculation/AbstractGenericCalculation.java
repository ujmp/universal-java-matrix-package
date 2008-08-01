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

package org.ujmp.core.genericcalculation;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.ujmp.core.Matrix;
import org.ujmp.core.Matrix.EntryType;
import org.ujmp.core.annotation.Annotation;
import org.ujmp.core.coordinates.CoordinateIterator2D;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.doublecalculation.Calculation;
import org.ujmp.core.doublecalculation.Calculation.Calc;
import org.ujmp.core.doublecalculation.Calculation.Ret;
import org.ujmp.core.doublecalculation.basic.Convert;
import org.ujmp.core.doublecalculation.basic.Divide;
import org.ujmp.core.doublecalculation.basic.Minus;
import org.ujmp.core.doublecalculation.basic.Mtimes;
import org.ujmp.core.doublecalculation.basic.Plus;
import org.ujmp.core.doublecalculation.basic.Times;
import org.ujmp.core.doublecalculation.basic.Transpose;
import org.ujmp.core.doublecalculation.entrywise.basic.Abs;
import org.ujmp.core.doublecalculation.entrywise.basic.Log;
import org.ujmp.core.doublecalculation.entrywise.basic.Power;
import org.ujmp.core.doublecalculation.entrywise.basic.Sign;
import org.ujmp.core.doublecalculation.entrywise.basic.Sqrt;
import org.ujmp.core.doublecalculation.entrywise.creators.Eye;
import org.ujmp.core.doublecalculation.entrywise.creators.Ones;
import org.ujmp.core.doublecalculation.entrywise.creators.Rand;
import org.ujmp.core.doublecalculation.entrywise.creators.Randn;
import org.ujmp.core.doublecalculation.entrywise.creators.Zeros;
import org.ujmp.core.doublecalculation.entrywise.hyperbolic.Cosh;
import org.ujmp.core.doublecalculation.entrywise.hyperbolic.Sinh;
import org.ujmp.core.doublecalculation.entrywise.hyperbolic.Tanh;
import org.ujmp.core.doublecalculation.entrywise.rounding.Ceil;
import org.ujmp.core.doublecalculation.entrywise.rounding.Floor;
import org.ujmp.core.doublecalculation.entrywise.rounding.Round;
import org.ujmp.core.doublecalculation.entrywise.trigonometric.Cos;
import org.ujmp.core.doublecalculation.entrywise.trigonometric.Sin;
import org.ujmp.core.doublecalculation.entrywise.trigonometric.Tan;
import org.ujmp.core.doublecalculation.general.statistical.Max;
import org.ujmp.core.doublecalculation.general.statistical.Min;
import org.ujmp.core.doublecalculation.general.statistical.Sum;
import org.ujmp.core.exceptions.MatrixException;

public abstract class AbstractGenericCalculation<A> implements GenericCalculation<A> {

	protected static final Logger logger = Logger.getLogger(AbstractGenericCalculation.class.getName());

	private Matrix[] sources = null;

	private int dimension = NONE;

	public AbstractGenericCalculation(Matrix... sources) {
		this.sources = sources;
	}

	public AbstractGenericCalculation(int dimension, Matrix... sources) {
		this.sources = sources;
		this.dimension = dimension;
	}

	public boolean isSparse() {
		return false;
	}

	public long getValueCount() {
		return Coordinates.product(getSize());
	}

	public Iterable<long[]> availableCoordinates() {
		return allCoordinates();
	}

	public Iterable<long[]> allCoordinates() {
		return new CoordinateIterator2D(getSize());
	}

	public boolean contains(long... coordinates) {
		return Coordinates.isSmallerThan(coordinates, getSize());
	}

	public abstract double getDouble(long... coordinates) throws MatrixException;

	public abstract void setDouble(double value, long... coordinates) throws MatrixException;

	public abstract A getObject(long... coordinates) throws MatrixException;

	public abstract void setObject(Object value, long... coordinates) throws MatrixException;

	public abstract String getString(long... coordinates) throws MatrixException;

	public abstract void setString(String value, long... coordinates) throws MatrixException;

	public Annotation getAnnotation() {
		return sources == null ? null : sources[0].getAnnotation();
	}

	public final Matrix calcLink() {
		return new GenericCalculationMatrix(this);
	}

	public static Matrix calc(Calc calculation, Ret returnType, int dimension, Matrix source0, Matrix... sources)
			throws MatrixException {
		switch (calculation) {
		case CLONE:
			return new Convert(source0).calc(returnType);
		case ZEROS:
			return new Zeros(source0).calc(returnType);
		case ONES:
			return new Ones(source0).calc(returnType);
		case RAND:
			return new Rand(source0).calc(returnType);
		case RANDN:
			return new Randn(source0).calc(returnType);
		case ROUND:
			return new Round(source0).calc(returnType);
		case CEIL:
			return new Ceil(source0).calc(returnType);
		case FLOOR:
			return new Floor(source0).calc(returnType);
		case EYE:
			return new Eye(source0).calc(returnType);
		case ABS:
			return new Abs(source0).calc(returnType);
		case LOG:
			return new Log(source0).calc(returnType);
		case TRANSPOSE:
			return new Transpose(source0).calc(returnType);
		case TIMES:
			return new Times(false, source0, sources[0]).calc(returnType);
		case MTIMES:
			return new Mtimes(false, source0, sources[0]).calc(returnType);
		case DIVIDE:
			return new Divide(source0, sources[0]).calc(returnType);
		case POWER:
			return new Power(source0, sources[0]).calc(returnType);
		case MIN:
			return new Min(dimension, source0).calc(returnType);
		case MAX:
			return new Max(dimension, source0).calc(returnType);
		case SIGN:
			return new Sign(source0).calc(returnType);
		case SQRT:
			return new Sqrt(source0).calc(returnType);
		case PLUS:
			return new Plus(false, source0, sources[0]).calc(returnType);
		case MINUS:
			return new Minus(false, source0, sources[0]).calc(returnType);
		case SUM:
			return new Sum(dimension, false, source0).calc(returnType);
		case SIN:
			return new Sin(source0).calc(returnType);
		case COS:
			return new Cos(source0).calc(returnType);
		case TAN:
			return new Tan(source0).calc(returnType);
		case SINH:
			return new Sinh(source0).calc(returnType);
		case COSH:
			return new Cosh(source0).calc(returnType);
		case TANH:
			return new Tanh(source0).calc(returnType);
		default:
			logger.log(Level.WARNING, "not implemented", new MatrixException("not implemented"));
			return null;
		}
	}

	public final Matrix getSource() {
		return sources[0];
	}

	public final Matrix[] getSources() {
		return sources;
	}

	public void setSources(Matrix... sources) {
		this.sources = sources;
	}

	public int getDimension() {
		return dimension;
	}

	public void setDimension(int dimension) {
		this.dimension = dimension;
	}

	public long[] getSize() {
		return getSource().getSize();
	}

	public abstract EntryType getEntryType();

	public final Matrix calc(Ret returnType) throws MatrixException {
		switch (returnType) {
		case ORIG:
			return calcOrig();
		case LINK:
			return calcLink();
		default: // must be NEW
			return calcNew();
		}
	}

}
