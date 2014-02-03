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

package org.ujmp.core.collections.map;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public abstract class AbstractMap<K, V> extends java.util.AbstractMap<K, V> implements Serializable {
	private static final long serialVersionUID = -6429342188863787235L;

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

					Iterator<K> it = keySet().iterator();

					public boolean hasNext() {
						return it.hasNext();
					}

					public V next() {
						return get(it.next());
					}

					public void remove() {
						throw new RuntimeException("not implemented");
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
		return new AbstractSet<Entry<K, V>>() {

			@Override
			public Iterator<java.util.Map.Entry<K, V>> iterator() {
				return new Iterator<Entry<K, V>>() {

					Iterator<K> it = keySet().iterator();

					public boolean hasNext() {
						return it.hasNext();
					}

					public java.util.Map.Entry<K, V> next() {
						final K k = it.next();
						final V v = get(k);
						return new java.util.Map.Entry<K, V>() {

							public K getKey() {
								return k;
							}

							public V getValue() {
								return v;
							}

							public V setValue(V value) {
								throw new RuntimeException("not implemented");
							}
						};
					}

					public void remove() {
						throw new RuntimeException("not implemented");
					}
				};
			}

			@Override
			public int size() {
				return size();
			}
		};
	}

	@SuppressWarnings("unchecked")
	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		int size = s.readInt();
		for (int i = 0; i < size; i++) {
			try {
				K k = (K) s.readObject();
				V v = (V) s.readObject();
				put(k, v);
			} catch (OptionalDataException e) {
				return;
			}
		}
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();
		s.writeInt(size());
		for (Object k : keySet()) {
			Object v = get(k);
			s.writeObject(k);
			s.writeObject(v);
		}
	}

	public String toString() {
		if (isEmpty()) {
			return "{}";
		}

		StringBuilder sb = new StringBuilder();
		sb.append('{');
		Set<K> keys = keySet();
		int i = 0;
		for (K k : keys) {
			V v = get(k);
			sb.append(k);
			sb.append('=');
			sb.append(v);
			if (i++ < keys.size() - 1) {
				sb.append(',').append(' ');
			}
		}

		return sb.append('}').toString();
	}

	public Map<K, V> get(K... keys) {
		Map<K, V> map = new HashMap<K, V>();
		for (K key : keys) {
			map.put(key, get(key));
		}
		return map;
	}

	public abstract void clear();

	public abstract V get(Object key);

	public abstract Set<K> keySet();

	public abstract V put(K key, V value);

	public abstract V remove(Object key);

	public abstract int size();

}
