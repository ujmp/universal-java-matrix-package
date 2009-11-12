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

package org.ujmp.core.collections;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.ujmp.core.exceptions.MatrixException;

public abstract class AbstractMap<K, V> implements Map<K, V> {

	public boolean isEmpty() {
		return size() == 0;
	}

	public void putAll(Map<? extends K, ? extends V> map) {
		for (K k : map.keySet()) {
			put(k, map.get(k));
		}
	}

	public boolean containsKey(Object key) {
		return keySet().contains(key);
	}

	public boolean containsValue(Object value) {
		for (K key : keySet()) {
			if (value.equals(get(key))) {
				return true;
			}
		}
		return false;
	}

	public Collection<V> values() {
		return new AbstractCollection<V>() {

			@Override
			public Iterator<V> iterator() {
				return new Iterator<V>() {

					public boolean hasNext() {
						// TODO Auto-generated method stub
						return false;
					}

					public V next() {
						// TODO Auto-generated method stub
						return null;
					}

					public void remove() {
						// TODO Auto-generated method stub

					}
				};
			}

			@Override
			public int size() {
				return size();
			}
		};
	}

	public Set<java.util.Map.Entry<K, V>> entrySet() {
		throw new MatrixException("not implemented");
	}

	@SuppressWarnings("unchecked")
	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		while (true) {
			try {
				K k = (K) s.readObject();
				V v = (V) s.readObject();
				put(k, v);
			} catch (OptionalDataException e) {
				return;
			}
		}
	}

	private void writeObject(ObjectOutputStream s) throws IOException, MatrixException {
		s.defaultWriteObject();
		for (Object k : keySet()) {
			Object v = get(k);
			s.writeObject(k);
			s.writeObject(v);
		}
	}
}
