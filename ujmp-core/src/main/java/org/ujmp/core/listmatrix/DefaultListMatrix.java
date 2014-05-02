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

package org.ujmp.core.listmatrix;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.ujmp.core.matrix.factory.DenseMatrix2DFactory;

public class DefaultListMatrix<T> extends AbstractListMatrix<T> {
	private static final long serialVersionUID = -6381864884046078055L;

	private final List<T> list;

	public DefaultListMatrix(List<T> list) {
		this.list = list;
	}

	public DefaultListMatrix(Set<T> set) {
		this.list = new ArrayList<T>(set);
	}

	public DefaultListMatrix(Collection<T> list) {
		if (list instanceof List) {
			this.list = (List<T>) list;
		} else {
			this.list = new ArrayList<T>(list);
		}
	}

	public DefaultListMatrix() {
		this.list = new ArrayList<T>();
	}

	public List<T> getList() {
		return list;
	}

	public DenseMatrix2DFactory<DefaultListMatrix<T>> getFactory() {
		throw new RuntimeException("not implemented");
	}

}
