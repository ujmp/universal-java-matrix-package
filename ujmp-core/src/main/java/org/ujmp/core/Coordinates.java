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

import java.io.Serializable;
import java.util.Arrays;

import org.ujmp.core.util.MathUtil;

public class Coordinates implements Serializable, Comparable<Coordinates> {
	private static final long serialVersionUID = 8361257560328772093L;

	public static final int X = Matrix.X;
	public static final int Y = Matrix.Y;
	public static final int Z = Matrix.Z;
	public static final int ROW = Matrix.ROW;
	public static final int COLUMN = Matrix.COLUMN;
	public static final int ALL = Matrix.ALL;

	public static final long[] ZERO2D = new long[] { 0, 0 };

	/**
	 * wrapped values
	 */
	private final long[] coordinates;

	/**
	 * Wraps an array of long values into a Coordinates object with proper
	 * hashCode(), compareTo() and equals() methods. Use Coordinates.wrap() to
	 * create a new object.
	 * 
	 * @param coordinates
	 *            array of long values to wrap
	 */
	private Coordinates(final long... coordinates) {
		this.coordinates = coordinates;
	}

	public final int hashCode() {
		return Arrays.hashCode(coordinates);
	}

	public final boolean equals(final Coordinates c) {
		return Arrays.equals(coordinates, c.coordinates);
	}

	public static final long product(final long... c) {
		long product = 1;
		for (int i = c.length - 1; i != -1; i--) {
			product *= c[i];
		}
		return product;
	}

	public static final long product(final int... c) {
		long product = 1;
		for (int i = c.length - 1; i != -1; i--) {
			product *= c[i];
		}
		return product;
	}

	public static final Coordinates wrap(final long... coordinates) {
		return new Coordinates(coordinates);
	}

	public final String toString(final String separator) {
		return toString("[", separator, "]");
	}

	public final String toString() {
		return toString("[", ",", "]");
	}

	public final String toString(final String prefix, final String separator, final String suffix) {
		final StringBuilder s = new StringBuilder();
		s.append(prefix);
		for (int i = 0; i < coordinates.length; i++) {
			s.append(coordinates[i]);
			if (i < coordinates.length - 1) {
				s.append(separator);
			}
		}
		s.append(suffix);
		return s.toString();
	}

	public final boolean equals(final Object o) {
		if (this == o) {
			return true;
		} else if (o instanceof Coordinates) {
			return equals((Coordinates) o);
		} else {
			return false;
		}
	}

	public final void fillWithValue(final long value) {
		Arrays.fill(coordinates, value);
	}

	public final void clear() {
		Arrays.fill(coordinates, 0);
	}

	public static final String toString(final long... coordinates) {
		return toString(",", coordinates);
	}

	public static final String toString(final String separator, final long... coordinates) {
		return toString("[", separator, "]", coordinates);
	}

	public static final String toString(final String prefix, final String separator,
			final String suffix, final long... coordinates) {
		final StringBuilder s = new StringBuilder();
		s.append(prefix);
		for (int i = 0; i < coordinates.length; i++) {
			s.append(coordinates[i]);
			if (i < coordinates.length - 1) {
				s.append(separator);
			}
		}
		s.append(suffix);
		return s.toString();
	}

	public final Coordinates clone() {
		return Coordinates.wrap(Arrays.copyOf(this.coordinates, this.coordinates.length));
	}

	public static final long[] plus(final long[] c1, final long[] c2) {
		return plus(new long[c1.length], c1, c2);
	}

	public static final long[] plus(final long[] result, final long[] c1, final long[] c2) {
		for (int i = result.length - 1; i != -1; i--) {
			result[i] = c1[i] + c2[i];
		}
		return result;
	}

	public static final long[] times(final long[] c1, final long[] c2) {
		return times(new long[c1.length], c1, c2);
	}

	public static final long[] times(final long[] result, final long[] c1, final long[] c2) {
		for (int i = result.length - 1; i != -1; i--) {
			result[i] = c1[i] * c2[i];
		}
		return result;
	}

	public static final long[] divide(final long[] c1, final long[] c2) {
		return divide(new long[c1.length], c1, c2);
	}

	public static final long[] divide(final long[] c1, final int[] c2) {
		return divide(new long[c1.length], c1, c2);
	}

	public static final long[] divide(final long[] result, final long[] c1, final long[] c2) {
		for (int i = result.length - 1; i != -1; i--) {
			result[i] = c1[i] / c2[i];
		}
		return result;
	}

	public static final long[] divide(final long[] result, final long[] c1, final int[] c2) {
		for (int i = result.length - 1; i != -1; i--) {
			result[i] = c1[i] / c2[i];
		}
		return result;
	}

	public static final long[] times(final long[] c, final long value) {
		return times(new long[c.length], c, value);
	}

	public static final long[] times(final long[] result, final long[] c, final long value) {
		for (int i = result.length - 1; i != -1; i--) {
			result[i] = c[i] * value;
		}
		return result;
	}

	public static final long[] divide(final long[] c, final long value) {
		return divide(new long[c.length], c, value);
	}

	public static final long[] divide(final long[] result, final long[] c, final long value) {
		for (int i = result.length - 1; i != -1; i--) {
			result[i] = c[i] / value;
		}
		return result;
	}

	public static final long[] modulo(final long[] c1, final long[] c2) {
		return modulo(new long[c1.length], c1, c2);
	}

	public static final long[] modulo(final long result[], final long[] c1, final long[] c2) {
		for (int i = result.length - 1; i != -1; i--) {
			result[i] = c1[i] % c2[i];
		}
		return result;
	}

	public static final long[] minus(final long[] c1, final long[] c2) {
		return minus(new long[c1.length], c1, c2);
	}

	public static final long[] minus(final long[] result, final long[] c1, final long[] c2) {
		for (int i = result.length - 1; i != -1; i--) {
			result[i] = c1[i] - c2[i];
		}
		return result;
	}

	public static final long[] max(final long[] c1, final long[] c2) {
		return max(new long[c1.length], c1, c2);
	}

	public static final long[] max(final long[] result, final long[] c1, final long[] c2) {
		for (int i = result.length - 1; i != -1; i--) {
			result[i] = Math.max(c1[i], c2[i]);
		}
		return result;
	}

	public static final long[] min(final long[] c1, final long[] c2) {
		return max(new long[c1.length], c1, c2);
	}

	public static final long[] min(final long[] result, final long[] c1, final long[] c2) {
		for (int i = result.length - 1; i != -1; i--) {
			result[i] = Math.min(c1[i], c2[i]);
		}
		return result;
	}

	public static final long[] parseString(final String s, final String splitRegex) {
		final String[] fields = s.split(splitRegex);
		final long[] result = new long[fields.length];
		for (int i = fields.length - 1; i != -1; i--) {
			result[i] = Long.parseLong(fields[i]);
		}
		return result;
	}

	public static final long[] parseString(final String s) {
		return parseString(s, "[x,;\t]");
	}

	public final int getDimensionCount() {
		return coordinates.length;
	}

	public static final boolean equals(final long[] c1, final long[] c2) {
		return Arrays.equals(c1, c2);
	}

	public static final boolean equals(final int[] c1, final int[] c2) {
		return Arrays.equals(c1, c2);
	}

	public static final long[] copyOf(final long[] c) {
		return Arrays.copyOf(c, c.length);
	}

	public static final long[] transpose(final long[] c) {
		return transpose(new long[c.length], c, ROW, COLUMN);
	}

	public static final long[] transpose(final long[] c, final int swapDimension1,
			final int swapDimension2) {
		return transpose(new long[c.length], c, swapDimension1, swapDimension2);
	}

	public static final long[] transpose(final long[] result, final long[] c,
			final int swapDimension1, final int swapDimension2) {
		if (result == c) {
			final long temp = c[swapDimension1];
			result[swapDimension1] = c[swapDimension2];
			result[swapDimension2] = temp;
		} else {
			result[swapDimension1] = c[swapDimension2];
			result[swapDimension2] = c[swapDimension1];
		}
		return result;
	}

	public static final boolean isSmallerThan(final long[] coordinates, final long[] size) {
		for (int i = coordinates.length - 1; i != -1; i--) {
			if (coordinates[i] >= size[i])
				return false;
		}
		return true;
	}

	public static final boolean isSmallerOrEqual(final long[] coordinates, final long[] size) {
		for (int i = coordinates.length - 1; i != -1; i--) {
			if (coordinates[i] > size[i])
				return false;
		}
		return true;
	}

	public static final boolean isGreaterThan(final long[] coordinates, final long[] size) {
		for (int i = coordinates.length - 1; i != -1; i--) {
			if (coordinates[i] <= size[i])
				return false;
		}
		return true;
	}

	public static final boolean isGreaterOrEqual(final long[] coordinates, final long[] size) {
		for (int i = coordinates.length - 1; i != -1; i--) {
			if (coordinates[i] < size[i])
				return false;
		}
		return true;
	}

	public static final long[] minus(final long[] coordinates, final long value) {
		return minus(new long[coordinates.length], coordinates, value);
	}

	public static final long[] minus(final long[] result, final long[] coordinates, final long value) {
		for (int i = coordinates.length - 1; i != -1; i--) {
			result[i] = coordinates[i] - value;
		}
		return result;
	}

	public static final long[] plus(final long[] coordinates, final long value) {
		return plus(new long[coordinates.length], coordinates, value);
	}

	public static final long[] plus(final long[] result, final long[] coordinates, final long value) {
		for (int i = coordinates.length - 1; i != -1; i--) {
			result[i] = coordinates[i] + value;
		}
		return result;
	}

	public static final boolean allEquals(final long[] coordinates, final long value) {
		for (int i = coordinates.length - 1; i != -1; i--) {
			if (coordinates[i] != value)
				return false;
		}
		return true;
	}

	public static final long manhattenDistance(final long[] c1, final long[] c2) {
		long distance = 0;
		for (int i = c1.length - 1; i != -1; i--) {
			distance += Math.abs(c1[i] - c2[i]);
		}
		return distance;
	}

	public final int compareTo(final Coordinates coordinates) {
		if (coordinates == null) {
			throw new IllegalArgumentException("coordinates cannot be null");
		} else {
			final long distance = manhattenDistance(this.coordinates, coordinates.coordinates);
			return MathUtil.longToIntClip(distance);
		}
	}

	public final long[] getLongCoordinates() {
		return this.coordinates;
	}

	public final long getRow() {
		return coordinates[ROW];
	}

	public final long getColumn() {
		return coordinates[COLUMN];
	}

}
