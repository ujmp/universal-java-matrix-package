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

package org.ujmp.core.interfaces;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.ujmp.core.Matrix.StorageType;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;

public interface BasicMatrixProperties {

	public Iterable<Object> allValues();

	public ValueType getValueType();

	public long getValueCount();

	public boolean isReadOnly();

	public boolean equals(Object o);

	public boolean equalsContent(Object o);

	public boolean equalsAnnotation(Object o);

	public int rank() throws MatrixException;

	public double trace() throws MatrixException;

	public boolean isDiagonal() throws MatrixException;

	public boolean isSquare();

	public boolean isSymmetric();

	public boolean isEmpty() throws MatrixException;

	public boolean isColumnVector();

	public boolean isRowVector();

	public boolean isScalar();

	public boolean isResizeable();

	public boolean isMultidimensionalMatrix();

	public boolean isSparse();

	public boolean isTransient();

	public boolean containsMissingValues() throws MatrixException;

	public double doubleValue() throws MatrixException;

	public int intValue() throws MatrixException;

	public long longValue() throws MatrixException;

	public short shortValue() throws MatrixException;

	public byte byteValue() throws MatrixException;

	public boolean booleanValue() throws MatrixException;

	public String stringValue() throws MatrixException;

	public Date dateValue() throws MatrixException;

	public char charValue() throws MatrixException;

	public BigInteger bigIntegerValue() throws MatrixException;

	public BigDecimal bigDecimalValue() throws MatrixException;

	public float floatValue() throws MatrixException;

	public double getMinValue() throws MatrixException;

	public double getMeanValue() throws MatrixException;

	public double getStdValue() throws MatrixException;

	public double getMaxValue() throws MatrixException;

	public double getEuklideanValue() throws MatrixException;

	public double getValueSum() throws MatrixException;

	public double getAbsoluteValueSum() throws MatrixException;

	public double getAbsoluteValueMean() throws MatrixException;

	public double getRMS() throws MatrixException;

	public long getRowCount();

	public long getColumnCount();

	public long getZCount();

	public long getSize(int dimension);

	public long[] getSize();

	/**
	 * Sets the size of the matrix. This is an optional method that is not
	 * implemented for all matrices. If this method is not implemented, a
	 * <code>MatrixException</code> is thrown.
	 * 
	 * @param size
	 *            the new size of the matrix
	 */
	public void setSize(long... size);

	public int getDimensionCount();

	public String toString();

	public double norm1();

	public double norm2();

	public double normF();

	public double normInf();

	public StorageType getStorageType();

	public boolean containsNull();

	public boolean containsBigDecimal(BigDecimal v);

	public boolean containsBigInteger(BigInteger v);

	public boolean containsBoolean(boolean v);

	public boolean containsByte(byte v);

	public boolean containsChar(char v);

	public boolean containsDate(Date v);

	public boolean containsDouble(double v);

	public boolean containsFloat(float v);

	public boolean containsInt(int v);

	public boolean containsLong(long v);

	public boolean containsObject(Object o);

	public boolean containsShort(short v);

	public boolean containsString(String s);

}
