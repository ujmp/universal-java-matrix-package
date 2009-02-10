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

package org.ujmp.core.calculation;

import org.ujmp.core.Matrix;
import org.ujmp.core.annotation.Annotation;
import org.ujmp.core.coordinates.CoordinateIterator;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.doublematrix.calculation.basic.Divide;
import org.ujmp.core.doublematrix.calculation.basic.Minus;
import org.ujmp.core.doublematrix.calculation.basic.Mtimes;
import org.ujmp.core.doublematrix.calculation.basic.Plus;
import org.ujmp.core.doublematrix.calculation.basic.Times;
import org.ujmp.core.doublematrix.calculation.entrywise.basic.Abs;
import org.ujmp.core.doublematrix.calculation.entrywise.basic.Log;
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
import org.ujmp.core.doublematrix.calculation.general.statistical.Max;
import org.ujmp.core.doublematrix.calculation.general.statistical.Min;
import org.ujmp.core.doublematrix.calculation.general.statistical.Sum;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.objectmatrix.calculation.Convert;
import org.ujmp.core.objectmatrix.calculation.Transpose;

public abstract class AbstractCalculation implements Calculation {

	private static final long serialVersionUID = -36063772015381070L;

	private Matrix[] sources = null;

	private int dimension = NONE;

	public AbstractCalculation(Matrix... sources) {
		this.sources = sources;
	}

	public AbstractCalculation(int dimension, Matrix... sources) {
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
		return new CoordinateIterator(getSize());
	}

	public boolean contains(long... coordinates) {
		return Coordinates.isSmallerThan(coordinates, getSize());
	}

	public Annotation getAnnotation() {
		return sources == null ? null : sources[0].getAnnotation();
	}

	public final Matrix calcLink() {
		return new CalculationMatrix(this);
	}

	public static Matrix calc(Calc calculation, Ret returnType, int dimension, Matrix source0,
			Matrix... sources) throws MatrixException {
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
			throw new MatrixException("Calculation not implemented: " + calculation);
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

	public abstract ValueType getValueType();

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

	public Matrix[] calcMulti() throws MatrixException {
		return new Matrix[] { calcNew() };
	}

}
