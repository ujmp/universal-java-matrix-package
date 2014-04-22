/*
 * Copyright (C) 2008-2014 by Holger Arndt
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

package org.ujmp.core.calculation;

import java.io.Serializable;

import org.ujmp.core.Matrix;
import org.ujmp.core.annotation.Annotation;
import org.ujmp.core.enums.ValueType;

/**
 * Interface for matrix calculations.
 * 
 * @author arndt
 */
public interface Calculation extends Serializable {

	public static final int ALL = Matrix.ALL;

	public static final int NONE = Matrix.NONE;

	public static final int ROW = Matrix.ROW;

	public static final int COLUMN = Matrix.COLUMN;

	public enum Ret {
		NEW, LINK, ORIG
	};

	public static final Ret NEW = Ret.NEW;

	public static final Ret LINK = Ret.LINK;

	public static final Ret ORIG = Ret.ORIG;

	public Matrix calc(Ret returnType);

	public Matrix calcNew();

	public Matrix calcLink();

	public Matrix calcOrig();

	public Annotation getAnnotation();

	public void setAnnotation(Annotation annotation);

	public Iterable<long[]> availableCoordinates();

	public boolean contains(long... coordinates);

	public Matrix getSource();

	public Matrix[] getSources();

	public int getDimension();

	public long[] getSize();

	public long getRowCount();

	public long getColumnCount();

	public ValueType getValueType();

}
