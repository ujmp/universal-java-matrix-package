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

package org.ujmp.jblas.calculation;

import org.jblas.DoubleMatrix;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.MtimesCalculation;
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;
import org.ujmp.core.interfaces.HasColumnMajorDoubleArray1D;
import org.ujmp.core.util.MathUtil;
import org.ujmp.jblas.JBlasDenseDoubleMatrix2D;

public class Mtimes implements MtimesCalculation<Matrix, Matrix, Matrix> {

	public void calc(Matrix source1, Matrix source2, Matrix target) {
		final DoubleMatrix m1;
		final DoubleMatrix m2;
		if (source1 instanceof JBlasDenseDoubleMatrix2D) {
			m1 = ((JBlasDenseDoubleMatrix2D) source1).getWrappedObject();
		} else if (source1 instanceof HasColumnMajorDoubleArray1D) {
			m1 = new JBlasDenseDoubleMatrix2D(MathUtil.longToInt(source1.getRowCount()), MathUtil.longToInt(source1
					.getColumnCount()), ((HasColumnMajorDoubleArray1D) source1).getColumnMajorDoubleArray1D())
					.getWrappedObject();
		} else {
			m1 = new JBlasDenseDoubleMatrix2D(source1).getWrappedObject();
		}
		if (source2 instanceof JBlasDenseDoubleMatrix2D) {
			m2 = ((JBlasDenseDoubleMatrix2D) source2).getWrappedObject();
		} else if (source2 instanceof HasColumnMajorDoubleArray1D) {
			m2 = new JBlasDenseDoubleMatrix2D(MathUtil.longToInt(source2.getRowCount()), MathUtil.longToInt(source2
					.getColumnCount()), ((HasColumnMajorDoubleArray1D) source2).getColumnMajorDoubleArray1D())
					.getWrappedObject();
		} else {
			m2 = new JBlasDenseDoubleMatrix2D(source2).getWrappedObject();
		}
		if (target instanceof JBlasDenseDoubleMatrix2D) {
			final DoubleMatrix t = ((JBlasDenseDoubleMatrix2D) target).getWrappedObject();
			m1.mmuli(m2, t);
		} else if (target instanceof HasColumnMajorDoubleArray1D) {
			final DoubleMatrix t = new DoubleMatrix((int) target.getRowCount(), (int) target.getColumnCount(),
					((HasColumnMajorDoubleArray1D) target).getColumnMajorDoubleArray1D());
			m1.mmuli(m2, t);
		} else if (target instanceof DenseDoubleMatrix2D) {
			final DenseDoubleMatrix2D t = (DenseDoubleMatrix2D) target;
			final DoubleMatrix r = new DoubleMatrix((int) source1.getRowCount(), (int) source2.getColumnCount());
			m1.mmuli(m2, r);
			for (long[] c : target.allCoordinates()) {
				t.setDouble(r.get((int) c[0], (int) c[1]), c[0], c[1]);
			}
		} else {
			final DoubleMatrix r = new DoubleMatrix((int) source1.getRowCount(), (int) source2.getColumnCount());
			m1.mmuli(m2, r);
			for (long[] c : target.allCoordinates()) {
				target.setAsDouble(r.get((int) c[0], (int) c[1]), c);
			}
		}
	}
}
