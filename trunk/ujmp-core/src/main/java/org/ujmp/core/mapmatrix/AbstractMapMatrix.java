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

package org.ujmp.core.mapmatrix;

import java.io.Closeable;
import java.io.IOException;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ujmp.core.objectmatrix.stub.AbstractDenseObjectMatrix2D;

public abstract class AbstractMapMatrix<K, V> extends AbstractDenseObjectMatrix2D implements
		MapMatrix<K, V> {
	private static final long serialVersionUID = 5571429371462164416L;

	private volatile boolean isIndexUpToDate = false;
	private final List<K> keyIndexList = new ArrayList<K>();

	public AbstractMapMatrix() {
		super(0, 2);
	}

	public synchronized final long[] getSize() {
		return new long[] { size(), 2 };
	}

	public synchronized final Object getObject(long row, long column) {
		return getObject((int) row, (int) column);
	}

	public synchronized final Object getObject(int row, int column) {
		Object mapKey = getKey(row);
		if (column == 0) {
			return mapKey;
		} else if (column == 1) {
			return (mapKey == null ? null : get(mapKey));
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public synchronized final void setObject(Object value, int row, int column) {
		if (column == 0) {
			// remove old key and add new key with old value
			K key = getKey(row);
			V oldValue = get(key);
			remove(key);
			put((K) value, oldValue);
		} else if (column == 1) {
			K key = getKey(row);
			put(key, (V) value);
		}
	}

	public synchronized final void setObject(Object value, long row, long column) {
		setObject(value, (int) row, (int) column);
	}

	public synchronized MapMatrix<K, V> clone() {
		buildIndexIfNecessary();
		MapMatrix<K, V> clone = new DefaultMapMatrix<K, V>();
		clone.putAll(this);
		for (K key : keyIndexList) {
			V value = get(key);
			clone.put(key, value);
		}
		return clone;
	}

	private synchronized final K getKey(int index) {
		buildIndexIfNecessary();
		K k = null;
		if (index >= 0 && index < keyIndexList.size()) {
			k = keyIndexList.get(index);
		}
		return k;
	}

	private synchronized void buildIndexIfNecessary() {
		if (!isIndexUpToDate) {
			Iterator<K> it = keySet().iterator();
			while (it.hasNext()) {
				keyIndexList.add(it.next());
			}
			if (it instanceof Closeable) {
				try {
					((Closeable) it).close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
			isIndexUpToDate = true;
		}
	}

	public synchronized boolean containsKey(Object key) {
		buildIndexIfNecessary();
		return keyIndexList.contains(key);
	}

	public synchronized boolean containsValue(Object value) {
		buildIndexIfNecessary();
		for (K key : keyIndexList) {
			if (value.equals(get(key))) {
				return true;
			}
		}
		return false;
	}

	public synchronized Set<java.util.Map.Entry<K, V>> entrySet() {
		final AbstractMapMatrix<K, V> map = this;
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
								throw new UnsupportedOperationException();
							}
						};
					}

					public void remove() {
						throw new UnsupportedOperationException();
					}
				};
			}

			@Override
			public int size() {
				return map.size();
			}
		};
	}

	public synchronized int size() {
		buildIndexIfNecessary();
		return keyIndexList.size();
	}

	public final synchronized boolean isEmpty() {
		buildIndexIfNecessary();
		return size() == 0;
	}

	public synchronized final V put(K key, V value) {
		keyIndexList.clear();
		V v = putIntoMap(key, value);
		fireValueChanged();
		return v;
	}

	public synchronized final void putAll(Map<? extends K, ? extends V> map) {
		for (K k : map.keySet()) {
			put(k, map.get(k));
		}
	}

	public synchronized final V remove(Object key) {
		keyIndexList.clear();
		V v = removeFromMap(key);
		fireValueChanged();
		return v;
	}

	public synchronized final Collection<V> values() {
		buildIndexIfNecessary();
		final AbstractMapMatrix<K, V> map = this;
		return new AbstractCollection<V>() {
			@Override
			public Iterator<V> iterator() {
				return new Iterator<V>() {
					Iterator<K> it = keyIndexList.iterator();

					public boolean hasNext() {
						return it.hasNext();
					}

					public V next() {
						return get(it.next());
					}

					public void remove() {
						throw new UnsupportedOperationException();
					}
				};
			}

			@Override
			public int size() {
				return map.size();
			}
		};
	}

	public final void clear() {
		keyIndexList.clear();
		clearMap();
	}

	protected abstract void clearMap();

	protected abstract V removeFromMap(Object key);

	protected abstract V putIntoMap(K key, V value);
}
