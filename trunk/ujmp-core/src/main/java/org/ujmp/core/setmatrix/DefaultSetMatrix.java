/*
 * Copyright (C) 2008-2009 Holger Arndt, A. Naegele and M. Bundschus
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

package org.ujmp.core.setmatrix;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class DefaultSetMatrix<A> extends AbstractSetMatrix<A> {
	private static final long serialVersionUID = -5487932797086214329L;

	private Set<A> set = null;

	public DefaultSetMatrix(Collection<A> list) {
		if (list instanceof Set) {
			this.set = (Set<A>) list;
		} else {
			this.set = new HashSet<A>(list);
		}
	}

	public DefaultSetMatrix() {
		this.set = new HashSet<A>();
	}

	public DefaultSetMatrix(A... objects) {
		this.set = new HashSet<A>();
		for (A o : objects) {
			this.set.add(o);
		}
	}

	
	public Set<A> getSet() {
		return set;
	}

}
