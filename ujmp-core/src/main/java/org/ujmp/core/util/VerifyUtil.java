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

package org.ujmp.core.util;

import java.util.Arrays;
import java.util.Collection;

import org.ujmp.core.Matrix;

public abstract class VerifyUtil {

    public static final void verifyTrue(boolean test, String message, Object... messageArgs) {
        if (!test) {
            String text = messageArgs.length == 0 ? message : String.format(message, messageArgs);
            throw new IllegalArgumentException(text);
        }
    }

    public static final void verifyFalse(boolean test, String message, Object... messageArgs) {
        if (!test) {
            String text = messageArgs.length == 0 ? message : String.format(message, messageArgs);
            throw new IllegalArgumentException(text);
        }
    }

    public static final void verifyTrue(boolean test, String message) {
        if (!test) {
            throw new IllegalArgumentException(message);
        }
    }

    public static final void verifyFalse(boolean test, String message) {
        if (test) {
            throw new IllegalArgumentException(message);
        }
    }

    public static final void verifySameSize(final Matrix m1, final Matrix m2) {
        verifyTrue(Arrays.equals(m1.getSize(), m2.getSize()), "matrices have different sizes");
    }

    public static final void verifySameSize(Matrix... matrices) {
        verifyTrue(matrices.length > 1, "more than one matrix must be provided");
        for (int i = matrices.length; --i != 0; ) {
            verifyTrue(Arrays.equals(matrices[i].getSize(), matrices[i - 1].getSize()), "matrices have different sizes");
        }
    }

    public static final void verifySameSize(double[][] source1, double[][] source2, double[][] target) {
        verifyNotNull(source1, "matrix1 cannot be null");
        verifyNotNull(source2, "matrix2 cannot be null");
        verifyNotNull(target, "matrix3 cannot be null");
        verifyNotNull(source1[0], "matrix1 must be 2d");
        verifyNotNull(source2[0], "matrix2 must be 2d");
        verifyNotNull(target[0], "matrix3 must be 2d");
        verifyEquals(source1.length, source2.length, "matrix1 and matrix2 have different sizes");
        verifyEquals(source2.length, target.length, "matrix1 and matrix3 have different sizes");
        verifyEquals(source1[0].length, source2[0].length, "matrix1 and matrix2 have different sizes");
        verifyEquals(source2[0].length, target[0].length, "matrix1 and matrix3 have different sizes");
    }

    public static final void verifyEquals(int i1, int i2, String message) {
        verifyTrue(i1 == i2, message);
    }

    public static final void verifyEquals(double d1, double d2, double tol, String message) {
        verifyTrue(Math.abs(d1 - d2) < tol, message);
    }

    public static final void verifyEquals(float d1, float d2, float tol, String message) {
        verifyTrue(Math.abs(d1 - d2) < tol, message);
    }

    public static final void verifyEquals(long[] s1, long[] s2, String message) {
        verifyTrue(Arrays.equals(s1, s2), message);
    }

    public static final void verifyEquals(int[] s1, int[] s2, String message) {
        verifyTrue(Arrays.equals(s1, s2), message);
    }

    public static final void verifyEquals(double[] s1, double[] s2, String message) {
        verifyTrue(Arrays.equals(s1, s2), message);
    }

    public static final void verifyEquals(float[] s1, float[] s2, String message) {
        verifyTrue(Arrays.equals(s1, s2), message);
    }

    public static final void verifyNotNull(Matrix m) {
        verifyNotNull(m, "matrix is null");
    }

    public static final void verifyNotNull(Object o, String message) {
        verifyFalse(o == null, message);
    }

    public static final void verifyNull(Object o, String message) {
        verifyTrue(o == null, message);
    }

    public static final void verifySameSize(double[] source1, double[] source2, double[] target) {
        verifyNotNull(source1, "matrix1 cannot be null");
        verifyNotNull(source2, "matrix2 cannot be null");
        verifyNotNull(target, "matrix3 cannot be null");
        verifyEquals(source1.length, source2.length, "matrix1 and matrix2 have different sizes");
        verifyEquals(source2.length, target.length, "matrix1 and matrix3 have different sizes");
    }

    public static final void verifySameSize(double[][] source, double[][] target) {
        verifyNotNull(source, "matrix1 cannot be null");
        verifyNotNull(target, "matrix2 cannot be null");
        verifyNotNull(source[0], "matrix1 must be 2d");
        verifyNotNull(target[0], "matrix2 must be 2d");
        verifyEquals(source.length, target.length, "matrix1 and matrix2 have different sizes");
        verifyEquals(source[0].length, target[0].length, "matrix1 and matrix2 have different sizes");
    }

    public static final void verifySameSize(double[] source, double[] target) {
        verifyNotNull(source, "matrix1 cannot be null");
        verifyNotNull(target, "matrix2 cannot be null");
        verifyEquals(source.length, target.length, "matrix1 and matrix2 have different sizes");
    }

    public static final void verify2D(Matrix m) {
        verifyNotNull(m, "matrix cannot be null");
        verifyEquals(m.getDimensionCount(), 2, "matrix is not 2d");
    }

    public static final void verifyEquals(long l1, long l2, String message) {
        verifyTrue(l1 == l2, message);
    }

    public static final void verify2D(long... size) {
        verifyNotNull(size, "size cannot be null");
        verifyTrue(size.length == 2, "size must be 2d");

    }

    public static final void verifySquare(Matrix matrix) {
        verify2D(matrix);
        verifyTrue(matrix.getRowCount() == matrix.getColumnCount(), "matrix must be square");
    }

    public static final void verifySingleValue(Matrix matrix) {
        verify2D(matrix);
        verifyTrue(matrix.getRowCount() == 1, "matrix must be 1x1");
        verifyTrue(matrix.getColumnCount() == 1, "matrix must be 1x1");
    }

    public static final void verifyNotEmpty(Object... objects) {
        verifyTrue(objects.length > 0, "object array must not be empty");
    }

    public static final void verifyNotEmpty(Collection<?> collection) {
        verifyNotNull(collection, "collection must not be null");
        verifyFalse(collection.isEmpty(), "collection must not be empty");
    }

    public static final void verifyNotEmpty(String... strings) {
        verifyTrue(strings.length > 0, "string array must not be empty");
    }

    public static void verifyNotNull(Object object) {
        verifyNotNull(object, "parameter must not be null");
    }
}
