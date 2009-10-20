/*
 * Copyright (C) 2008-2009 by Holger Arndt
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

package org.ujmp.core.bigdecimalmatrix;

import java.math.BigDecimal;

import org.ujmp.core.bigdecimalmatrix.factory.BigDecimalMatrix2DFactory;
import org.ujmp.core.bigdecimalmatrix.factory.DefaultBigDecimalMatrix2DFactory;
import org.ujmp.core.genericmatrix.GenericMatrix2D;

public interface BigDecimalMatrix2D extends BigDecimalMatrix, GenericMatrix2D<BigDecimal> {

	public static BigDecimalMatrix2DFactory factory = new DefaultBigDecimalMatrix2DFactory();

	public BigDecimal getBigDecimal(long row, long column);

	public void setBigDecimal(BigDecimal value, long row, long column);

	public BigDecimal getBigDecimal(int row, int column);

	public void setBigDecimal(BigDecimal value, int row, int column);
}
