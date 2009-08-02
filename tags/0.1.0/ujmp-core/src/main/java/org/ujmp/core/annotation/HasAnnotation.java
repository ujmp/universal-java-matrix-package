/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
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

package org.ujmp.core.annotation;

public interface HasAnnotation {

	public Annotation getAnnotation();

	public void setAnnotation(Annotation annotation);

	public Object getAxisAnnotation(int axis, int positionOnAxis);

	public Object getAxisAnnotation(int axis);

	public void setAxisAnnotation(int axis, int positionOnAxis, Object value);

	public void setAxisAnnotation(int axis, Object value);

	public Object getMatrixAnnotation();

	public void setMatrixAnnotation(Object annotation);

	public String getColumnLabel(int col);

	public String getRowLabel(int row);

	public Object getRowObject(int row);

	public Object getColumnObject(int col);

	public void setColumnLabel(int col, String label);

	public void setRowLabel(int row, String label);

	public void setRowObject(int row, Object o);

	public void setColumnObject(int col, Object o);

}
