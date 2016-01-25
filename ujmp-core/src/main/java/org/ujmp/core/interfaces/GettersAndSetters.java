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

package org.ujmp.core.interfaces;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.ujmp.core.Matrix;

/**
 * This interface declares the getters and setters for the entries in the Matrix
 * for the most important Java types: boolean, int, char, float, double, long,
 * Date, Object and String.
 * <p>
 * The matrix will try to convert its content to the desired format as good as
 * possible. For example, a Matrix with String entries will try to parse each
 * String to a Double when you invoke getDoubleValue(). Note, that there will be
 * no exception if the entry cannot be converted. Instead, the result will be
 * either null, 0, false or Double.NaN depending on the method.
 */
public interface GettersAndSetters {

	/**
	 * Returns a double representation of an entry in the matrix. The stored
	 * value will be converted to a double as good as possible.
	 * 
	 * @param coordinates
	 *            location of the entry
	 * @return a double representation of the entry @
	 */
	public double getAsDouble(long... coordinates);

	/**
	 * Sets an entry in the matrix to a double value. If the matrix cannot store
	 * double values, the value will be represented as good as possible.
	 * 
	 * @param value
	 *            double value
	 * @param coordinates
	 *            location of the entry @
	 */
	public void setAsDouble(double value, long... coordinates);

	/**
	 * Returns a raw entry in the matrix as it is stored. If the matrix supports
	 * Generics, the return type will match the type that is stored.
	 * 
	 * @param coordinates
	 *            location of the entry
	 * @return entry object @
	 */
	public Object getAsObject(long... coordinates);

	/**
	 * Sets an entry in the matrix to an object. If the matrix cannot store this
	 * object type, the value will be represented as good as possible.
	 * 
	 * @param o
	 *            the object to store
	 * @param coordinates
	 *            location of the entry @
	 */
	public void setAsObject(Object o, long... coordinates);

	/**
	 * Returns a String representation of an entry in the matrix. The stored
	 * value will be converted to a String as good as possible.
	 * 
	 * @param coordinates
	 *            location of the entry
	 * @return a String representation of the entry @
	 */
	public String getAsString(long... coordinates);

	/**
	 * Sets an entry in the matrix to a String value. If the matrix cannot store
	 * Strings, the value will be represented as good as possible.
	 * 
	 * @param value
	 *            String value
	 * @param coordinates
	 *            location of the entry @
	 */
	public void setAsString(String string, long... coordinates);

	/**
	 * Converts the content of a matrix into a 2-dimensional array of double
	 * values.
	 * 
	 * @return double array with matrix entries @
	 */
	public double[][] toDoubleArray();

	/**
	 * Returns a byte representation of an entry in the matrix. The stored value
	 * will be converted to a byte as good as possible.
	 * 
	 * @param coordinates
	 *            location of the entry
	 * @return a byte representation of the entry @
	 */
	public byte getAsByte(long... coordinates);

	/**
	 * Returns a byte array representation of an entry in the matrix. The stored
	 * value will be converted to a byte array as good as possible.
	 * 
	 * @param coordinates
	 *            location of the entry
	 * @return a byte array representation of the entry @
	 */
	public byte[] getAsByteArray(long... coordinates);

	/**
	 * Sets an entry in the matrix to a byte value. If the matrix cannot store
	 * byte values, the value will be represented as good as possible.
	 * 
	 * @param value
	 *            byte value
	 * @param coordinates
	 *            location of the entry @
	 */
	public void setAsByte(byte value, long... coordinates);

	/**
	 * Sets an entry in the matrix to a byte array. If the matrix cannot store
	 * byte array, the value will be represented as good as possible.
	 * 
	 * @param value
	 *            byte array
	 * @param coordinates
	 *            location of the entry @
	 */
	public void setAsByteArray(byte[] value, long... coordinates);

	/**
	 * Converts the content of a matrix into a 2-dimensional array of boolean
	 * values.
	 * 
	 * @return boolean array with matrix entries @
	 */
	public boolean[][] toBooleanArray();

	public BigDecimal[][] toBigDecimalArray();

	public BigInteger[][] toBigIntegerArray();

	/**
	 * Converts the content of a matrix into a 2-dimensional array of byte
	 * values.
	 * 
	 * @return byte array with matrix entries @
	 */
	public byte[][] toByteArray();

	/**
	 * Returns a byte representation of an entry in the matrix. The stored value
	 * will be converted to a boolean as good as possible.
	 * 
	 * @param coordinates
	 *            location of the entry
	 * @return a boolean representation of the entry @
	 */
	public boolean getAsBoolean(long... coordinates);

	public BigInteger getAsBigInteger(long... coordinates);

	public BigDecimal getAsBigDecimal(long... coordinates);

	/**
	 * Sets an entry in the matrix to a boolean value. If the matrix cannot
	 * store byte values, the value will be represented as good as possible.
	 * 
	 * @param value
	 *            boolean value
	 * @param coordinates
	 *            location of the entry @
	 */
	public void setAsBoolean(boolean value, long... coordinates);

	public void setAsBigInteger(BigInteger value, long... coordinates);

	public void setAsBigDecimal(BigDecimal value, long... coordinates);

	/**
	 * Returns a char representation of an entry in the matrix. The stored value
	 * will be converted to a char as good as possible.
	 * 
	 * @param coordinates
	 *            location of the entry
	 * @return a char representation of the entry @
	 */
	public char getAsChar(long... coordinates);

	/**
	 * Sets an entry in the matrix to a char value. If the matrix cannot store
	 * char values, the value will be represented as good as possible.
	 * 
	 * @param value
	 *            char value
	 * @param coordinates
	 *            location of the entry @
	 */
	public void setAsChar(char value, long... coordinates);

	/**
	 * Converts the content of a matrix into a 2-dimensional array of char
	 * values.
	 * 
	 * @return char array with matrix entries @
	 */
	public char[][] toCharArray();

	/**
	 * Returns a Date representation of an entry in the matrix. The stored value
	 * will be converted to a Date object as good as possible.
	 * 
	 * @param coordinates
	 *            location of the entry
	 * @return a String representation of the entry @
	 */
	public Date getAsDate(long... coordinates);

	/**
	 * Sets an entry in the matrix to a Date value. If the matrix cannot store
	 * Date objects, the value will be represented as good as possible.
	 * 
	 * @param value
	 *            Date object to store
	 * @param coordinates
	 *            location of the entry @
	 */
	public void setAsDate(Date date, long... coordinates);

	/**
	 * Converts the content of a matrix into a 2-dimensional array of Date
	 * values.
	 * 
	 * @return Date array with matrix entries @
	 */
	public Date[][] toDateArray();

	/**
	 * Returns a float representation of an entry in the matrix. The stored
	 * value will be converted to a float as good as possible.
	 * 
	 * @param coordinates
	 *            location of the entry
	 * @return a float representation of the entry @
	 */
	public float getAsFloat(long... coordinates);

	/**
	 * Sets an entry in the matrix to a float value. If the matrix cannot store
	 * float values, the value will be represented as good as possible.
	 * 
	 * @param value
	 *            float value
	 * @param coordinates
	 *            location of the entry @
	 */
	public void setAsFloat(float value, long... coordinates);

	/**
	 * Converts the content of a matrix into a 2-dimensional array of float
	 * values.
	 * 
	 * @return float array with matrix entries @
	 */
	public float[][] toFloatArray();

	/**
	 * Returns an int representation of an entry in the matrix. The stored value
	 * will be converted to an int as good as possible.
	 * 
	 * @param coordinates
	 *            location of the entry
	 * @return an int representation of the entry @
	 */
	public int getAsInt(long... coordinates);

	/**
	 * Sets an entry in the matrix to an int value. If the matrix cannot store
	 * int values, the value will be represented as good as possible.
	 * 
	 * @param value
	 *            int value
	 * @param coordinates
	 *            location of the entry @
	 */
	public void setAsInt(int value, long... coordinates);

	/**
	 * Converts the content of a matrix into a 2-dimensional array of int
	 * values.
	 * 
	 * @return int array with matrix entries @
	 */
	public int[][] toIntArray();

	/**
	 * Returns a long representation of an entry in the matrix. The stored value
	 * will be converted to a long as good as possible.
	 * 
	 * @param coordinates
	 *            location of the entry
	 * @return a long representation of the entry @
	 */
	public long getAsLong(long... coordinates);

	/**
	 * Sets an entry in the matrix to a long value. If the matrix cannot store
	 * long values, the value will be represented as good as possible.
	 * 
	 * @param value
	 *            long value
	 * @param coordinates
	 *            location of the entry @
	 */
	public void setAsLong(long value, long... coordinates);

	/**
	 * Converts the content of a matrix into a 2-dimensional array of long
	 * values.
	 * 
	 * @return long array with matrix entries @
	 */
	public long[][] toLongArray();

	/**
	 * Converts the content of a matrix into a 2-dimensional array of Objects.
	 * 
	 * @return Object array with matrix entries @
	 */
	public Object[][] toObjectArray();

	/**
	 * Returns a short representation of an entry in the matrix. The stored
	 * value will be converted to a short as good as possible.
	 * 
	 * @param coordinates
	 *            location of the entry
	 * @return a short representation of the entry @
	 */
	public short getAsShort(long... coordinates);

	/**
	 * Sets an entry in the matrix to a short value. If the matrix cannot store
	 * short values, the value will be represented as good as possible.
	 * 
	 * @param value
	 *            short value
	 * @param coordinates
	 *            location of the entry @
	 */
	public void setAsShort(short value, long... coordinates);

	/**
	 * Converts the content of a matrix into a 2-dimensional array of short
	 * values.
	 * 
	 * @return short array with matrix entries @
	 */
	public short[][] toShortArray();

	/**
	 * Converts the content of a matrix into a 2-dimensional array of Strings.
	 * 
	 * @return String array with matrix entries @
	 */
	public String[][] toStringArray();

	/**
	 * Returns a representation of the entry in the matrix that reflects the
	 * true object best. E.g. if the entry is a String "-5.3", this method
	 * returns a double with the equivalent value. For a String "text" the
	 * string itself is returned.
	 * 
	 * @param coordinates
	 *            location of the entry
	 * @return object that represents the matrix entry best @
	 */
	public Object getPreferredObject(long... coordinates);

	public Matrix getAsMatrix(long... coordinates);

	public void setAsMatrix(Matrix m, long... coordinates);
}
