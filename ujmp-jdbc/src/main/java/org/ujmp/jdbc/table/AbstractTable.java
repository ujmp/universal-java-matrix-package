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

package org.ujmp.jdbc.table;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.ujmp.core.collections.AbstractCollection;

public abstract class AbstractTable extends AbstractCollection<Map<String, Object>> implements Table {
	private static final long serialVersionUID = 4638678082267652430L;

	public final Map<String, Object> getFirst(Object... fieldsAndValues) {
		if (fieldsAndValues.length % 2 != 0) {
			throw new RuntimeException("use format: [key1],[value1],[key2],[value2],...");
		}
		Map<String, Object> map = new TreeMap<String, Object>();
		for (int i = 0; i < fieldsAndValues.length; i += 2) {
			map.put(String.valueOf(fieldsAndValues[i]), fieldsAndValues[i + 1]);
		}
		return getFirst(map);
	}

	public final List<Map<String, Object>> getAll(Object... fieldsAndValues) {
		if (fieldsAndValues.length % 2 != 0) {
			throw new RuntimeException("use format: [key1],[value1],[key2],[value2],...");
		}
		Map<String, Object> map = new TreeMap<String, Object>();
		for (int i = 0; i < fieldsAndValues.length; i += 2) {
			map.put(String.valueOf(fieldsAndValues[i]), fieldsAndValues[i + 1]);
		}
		return getAll(map);
	}

	public final void add(Object... fieldsAndValues) {
		if (fieldsAndValues.length % 2 != 0) {
			throw new RuntimeException("use format: [key1],[value1],[key2],[value2],...");
		}
		Map<String, Object> map = new TreeMap<String, Object>();
		for (int i = 0; i < fieldsAndValues.length; i += 2) {
			map.put(String.valueOf(fieldsAndValues[i]), fieldsAndValues[i + 1]);
		}
		add(map);
	}

	public boolean removeAll(Collection<?> c) {
		boolean removedSomething = false;
		for (Object o : c) {
			if (remove(o)) {
				removedSomething = true;
			}
		}
		return removedSomething;
	}

	public boolean retainAll(Collection<?> c) {
		throw new RuntimeException("not implemented");
	}

	public boolean containsAll(Collection<?> c) {
		for (Object o : c) {
			if (!contains(o)) {
				return false;
			}
		}
		return true;
	}

}
