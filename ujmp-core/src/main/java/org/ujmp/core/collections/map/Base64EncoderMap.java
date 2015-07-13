/*
 * Copyright (C) 2008-2015 by Holger Arndt
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

import java.util.Map;
import java.util.Set;

import org.ujmp.core.util.Base64;

public class Base64EncoderMap extends AbstractMap<String, byte[]> {
	private static final long serialVersionUID = 5372707948791472795L;

	private final Map<String, String> map;

	public Base64EncoderMap(Map<String, String> map) {
		this.map = map;
	}

	public void clear() {
		map.clear();
	}

	public byte[] get(Object key) {
		try {
			String data = map.get(key);
			return data == null ? null : Base64.decode(data);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Set<String> keySet() {
		return map.keySet();
	}

	public byte[] put(String key, byte[] value) {
		try {
			byte[] oldValue = get(key);
			map.put(key, Base64.encodeBytes(value));
			return oldValue;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public byte[] remove(Object key) {
		byte[] oldValue = get(key);
		map.remove(key);
		return oldValue;
	}

	public int size() {
		return map.size();
	}

}
