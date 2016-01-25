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

package org.ujmp.ojalgo.calculation;

import org.ojalgo.matrix.decomposition.OjalgoDecompositionUtil;
import org.ojalgo.matrix.store.PrimitiveDenseStore;
import org.ujmp.core.Matrix;
import org.ujmp.ojalgo.OjalgoDenseDoubleMatrix2D;

public class LU implements org.ujmp.core.doublematrix.calculation.general.decomposition.LU<Matrix> {

    public static LU INSTANCE = new LU();

    public Matrix[] calc(Matrix source) {
        final org.ojalgo.matrix.decomposition.LU<Double> lu = OjalgoDecompositionUtil.lu();
        PrimitiveDenseStore matrix;
        if (source instanceof OjalgoDenseDoubleMatrix2D) {
            matrix = ((OjalgoDenseDoubleMatrix2D) source).getWrappedObject();
        } else {
            matrix = new OjalgoDenseDoubleMatrix2D(source).getWrappedObject();
        }
        lu.compute(matrix);
        final Matrix l = new OjalgoDenseDoubleMatrix2D(lu.getL());
        final Matrix u = new OjalgoDenseDoubleMatrix2D(lu.getU());
        final int m = (int) source.getRowCount();
        final int[] piv = lu.getPivotOrder();
        final Matrix p = new OjalgoDenseDoubleMatrix2D(m, m);
        for (int i = 0; i < m; i++) {
            p.setAsDouble(1, i, piv[i]);
        }
        return new Matrix[]{l, u, p};
    }

    public Matrix solve(Matrix a, Matrix b) {
        final org.ojalgo.matrix.decomposition.LU<Double> lu = OjalgoDecompositionUtil.lu();
        PrimitiveDenseStore a2;
        PrimitiveDenseStore b2;
        if (a instanceof OjalgoDenseDoubleMatrix2D) {
            a2 = ((OjalgoDenseDoubleMatrix2D) a).getWrappedObject();
        } else {
            a2 = new OjalgoDenseDoubleMatrix2D(a).getWrappedObject();
        }
        if (b instanceof OjalgoDenseDoubleMatrix2D) {
            b2 = ((OjalgoDenseDoubleMatrix2D) b).getWrappedObject();
        } else {
            b2 = new OjalgoDenseDoubleMatrix2D(b).getWrappedObject();
        }
        lu.compute(a2);
        return new OjalgoDenseDoubleMatrix2D(lu.solve(b2));
    }

}
