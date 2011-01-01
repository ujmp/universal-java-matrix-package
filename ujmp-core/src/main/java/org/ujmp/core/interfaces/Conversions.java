/*
 * Copyright (C) 2008-2011 by Holger Arndt
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

import org.ujmp.core.bigdecimalmatrix.BigDecimalMatrix;
import org.ujmp.core.bigintegermatrix.BigIntegerMatrix;
import org.ujmp.core.booleanmatrix.BooleanMatrix;
import org.ujmp.core.bytematrix.ByteMatrix;
import org.ujmp.core.charmatrix.CharMatrix;
import org.ujmp.core.datematrix.DateMatrix;
import org.ujmp.core.doublematrix.DoubleMatrix;
import org.ujmp.core.floatmatrix.FloatMatrix;
import org.ujmp.core.intmatrix.IntMatrix;
import org.ujmp.core.listmatrix.ListMatrix;
import org.ujmp.core.longmatrix.LongMatrix;
import org.ujmp.core.mapmatrix.MapMatrix;
import org.ujmp.core.objectmatrix.ObjectMatrix;
import org.ujmp.core.setmatrix.SetMatrix;
import org.ujmp.core.shortmatrix.ShortMatrix;
import org.ujmp.core.stringmatrix.StringMatrix;

public interface Conversions {

	public BooleanMatrix toBooleanMatrix();

	public BigDecimalMatrix toBigDecimalMatrix();

	public BigIntegerMatrix toBigIntegerMatrix();

	public ByteMatrix toByteMatrix();

	public CharMatrix toCharMatrix();

	public DateMatrix toDateMatrix();

	public DoubleMatrix toDoubleMatrix();

	public FloatMatrix toFloatMatrix();

	public IntMatrix toIntMatrix();

	public LongMatrix toLongMatrix();

	public ShortMatrix toShortMatrix();

	public StringMatrix toStringMatrix();

	public ObjectMatrix toObjectMatrix();

	public ListMatrix<?> toListMatrix();

	public SetMatrix<?> toSetMatrix();

	public MapMatrix<?, ?> toMapMatrix();

}
