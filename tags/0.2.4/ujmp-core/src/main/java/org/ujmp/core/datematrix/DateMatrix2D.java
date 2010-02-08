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

package org.ujmp.core.datematrix;

import java.util.Date;

import org.ujmp.core.datematrix.factory.DateMatrix2DFactory;
import org.ujmp.core.datematrix.factory.DefaultDataMatrix2DFactory;
import org.ujmp.core.genericmatrix.GenericMatrix2D;

public interface DateMatrix2D extends DateMatrix, GenericMatrix2D<Date> {

	public static DateMatrix2DFactory factory = new DefaultDataMatrix2DFactory();

	public Date getDate(long row, long column);

	public void setDate(Date value, long row, long column);

	public Date getDate(int row, int column);

	public void setDate(Date value, int row, int column);
}
