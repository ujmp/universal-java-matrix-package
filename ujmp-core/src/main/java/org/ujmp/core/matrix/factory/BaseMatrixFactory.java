/*
 * Copyright (C) 2008-2015 by Holger Arndt
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

package org.ujmp.core.matrix.factory;

import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;

public interface BaseMatrixFactory<T extends Matrix> {

	public static final Ret LINK = Matrix.LINK;
	public static final Ret ORIG = Matrix.ORIG;
	public static final Ret NEW = Matrix.NEW;

	public static final int Y = Matrix.Y;
	public static final int X = Matrix.X;
	public static final int Z = Matrix.Z;

	public static final int ROW = Matrix.ROW;
	public static final int COLUMN = Matrix.COLUMN;
	public static final int ALL = Matrix.ALL;
	public static final int NONE = Matrix.NONE;

	public T zeros(long rows, long columns);

	public T zeros(long... size);
}
