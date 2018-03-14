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

package org.ujmp.core.interfaces;

import java.util.List;

import org.ujmp.core.Matrix;
import org.ujmp.core.enums.ValueType;

public interface BaseMatrixProperties {

	Iterable<Object> allValues();

	ValueType getValueType();

	long getValueCount();

	boolean isReadOnly();

	boolean isSingular();

	boolean equals(Object o);

	boolean equalsContent(Object o);

	boolean equalsAnnotation(Object o);

	boolean isDiagonal();

	boolean isSquare();

	boolean isSymmetric();

	boolean isEmpty();

	boolean isColumnVector();

	boolean isRowVector();

	boolean isScalar();

	boolean isResizable();

	boolean isMultidimensionalMatrix();

	boolean isSparse();

	boolean isTransient();

	List<Matrix> getRowList();

	List<Matrix> getColumnList();

	long getRowCount();

	long getColumnCount();

	long getZCount();

	long getSize(int dimension);

	long[] getSize();

	/**
	 * Sets the size of the matrix. This is an optional method that is not
	 * implemented for all matrices. If this method is not implemented, a
	 * <code>MatrixException</code> is thrown.
	 * 
	 * @param size
	 *            the new size of the matrix
	 */
	void setSize(long... size);

	int getDimensionCount();

	String toString();

	String toHtml();

	String toJson();

}
