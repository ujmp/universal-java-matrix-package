/*
 * Copyright (C) 2008-2013 by Holger Arndt
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

import org.ujmp.core.interfaces.HasLabel;

public interface HasAnnotation extends HasLabel {

	public Annotation getAnnotation();

	public void setAnnotation(Annotation annotation);

	public Object getAxisAnnotation(int dimension, long... position);

	public String getAxisLabel(int dimension);

	public Object getAxisLabelObject(int dimension);

	public void setAxisAnnotation(int dimension, Object label, long... position);

	public void setAxisLabel(int dimension, String label);

	public void setAxisLabelObject(int dimension, Object label);

	public String getColumnLabel(long col);

	public String getRowLabel(long row);

	public Object getRowLabelObject(long row);

	public Object getColumnLabelObject(long col);

	public void setColumnLabel(long col, String label);

	public void setRowLabel(long row, String label);

	public void setRowLabelObject(long row, Object o);

	public void setColumnLabelObject(long col, Object o);

	public long getRowForLabel(Object object);

	public long getColumnForLabel(Object object);

	public long[] getPositionForLabel(int dimension, Object label);

}
