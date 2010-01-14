/*
 * Copyright (C) 2008-2010 by Holger Arndt
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

import org.ujmp.core.booleanmatrix.BooleanMatrix2D;
import org.ujmp.core.bytematrix.ByteMatrix2D;
import org.ujmp.core.charmatrix.CharMatrix2D;
import org.ujmp.core.datematrix.DateMatrix2D;
import org.ujmp.core.doublematrix.DoubleMatrix2D;
import org.ujmp.core.floatmatrix.FloatMatrix2D;
import org.ujmp.core.intmatrix.IntMatrix2D;
import org.ujmp.core.longmatrix.LongMatrix2D;
import org.ujmp.core.objectmatrix.ObjectMatrix2D;
import org.ujmp.core.shortmatrix.ShortMatrix2D;
import org.ujmp.core.stringmatrix.StringMatrix2D;

public interface Conversions2D extends Conversions {

	public BooleanMatrix2D toBooleanMatrix();

	public ByteMatrix2D toByteMatrix();

	public CharMatrix2D toCharMatrix();

	public DateMatrix2D toDateMatrix();

	public DoubleMatrix2D toDoubleMatrix();

	public FloatMatrix2D toFloatMatrix();

	public IntMatrix2D toIntMatrix();

	public LongMatrix2D toLongMatrix();

	public ShortMatrix2D toShortMatrix();

	public StringMatrix2D toStringMatrix();

	public ObjectMatrix2D toObjectMatrix();

}
