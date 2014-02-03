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

import java.nio.charset.Charset;
import java.util.Map;
import java.util.Set;



public class StringEncoderMap extends AbstractMap<String, String> {
	private static final long serialVersionUID = 1948316336092537067L;

	private final Map<String, byte[]> map;

	private final Charset charset;

	public StringEncoderMap(Map<String, byte[]> map) {
		this(map, Charset.defaultCharset());
	}

	public StringEncoderMap(Map<String, byte[]> map, Charset charset) {
		this.map = map;
		this.charset = charset;
	}

	public void clear() {
		map.clear();
	}

	public String get(Object key) {
		try {
			byte[] data = map.get(key);
			return data == null ? null : new String(data, charset);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Set<String> keySet() {
		return map.keySet();
	}

	public String put(String key, String value) {
		try {
			String oldValue = get(key);
			map.put(key, value.getBytes(charset));
			return oldValue;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String remove(Object key) {
		String oldValue = get(key);
		map.remove(key);
		return oldValue;
	}

	public int size() {
		return map.size();
	}

}
