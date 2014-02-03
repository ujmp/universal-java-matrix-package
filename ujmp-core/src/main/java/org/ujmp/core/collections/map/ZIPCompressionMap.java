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

import java.util.Map;
import java.util.Set;
import java.util.zip.Deflater;

import org.ujmp.core.util.CompressionUtil;

public class ZIPCompressionMap extends AbstractMap<String, byte[]> {
	private static final long serialVersionUID = 6030730020161656575L;

	private final int compressionLevel;

	private final Map<String, byte[]> map;

	public ZIPCompressionMap(Map<String, byte[]> map) {
		this.map = map;
		this.compressionLevel = Deflater.DEFAULT_COMPRESSION;
	}

	public ZIPCompressionMap(Map<String, byte[]> map, int compressionLevel) {
		this.map = map;
		this.compressionLevel = compressionLevel;
	}

	public void clear() {
		map.clear();
	}

	public byte[] get(Object key) {
		try {
			byte[] data = map.get(key);
			return data == null ? null : CompressionUtil.zipDecompress(data);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Set<String> keySet() {
		return map.keySet();
	}

	public byte[] put(String key, byte[] value) {
		byte[] oldValue = get(key);
		map.put(key, CompressionUtil.zipCompress(value, compressionLevel));
		return oldValue;
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
