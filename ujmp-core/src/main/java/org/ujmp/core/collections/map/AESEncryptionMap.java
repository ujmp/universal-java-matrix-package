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

package org.ujmp.core.collections.map;

import java.security.Key;
import java.util.Map;
import java.util.Set;

import javax.crypto.spec.SecretKeySpec;

import org.ujmp.core.util.EncryptionUtil;

public class AESEncryptionMap extends AbstractMap<String, byte[]> {
	private static final long serialVersionUID = 3309840187574931950L;

	private final Map<String, byte[]> map;

	private final Key aesKey;

	private final byte[] iv;

	public AESEncryptionMap(Map<String, byte[]> map, byte[] key) {
		this.map = map;
		this.aesKey = new SecretKeySpec(key, "AES");
		this.iv = EncryptionUtil.IV16;
	}

	public AESEncryptionMap(Map<String, byte[]> map, byte[] key, byte[] iv) {
		this.map = map;
		this.aesKey = new SecretKeySpec(key, "AES");
		this.iv = iv;
	}

	public AESEncryptionMap(Map<String, byte[]> map, Key key) {
		this.map = map;
		this.aesKey = key;
		this.iv = EncryptionUtil.IV16;
	}

	public AESEncryptionMap(Map<String, byte[]> map, Key key, byte[] iv) {
		this.map = map;
		this.aesKey = key;
		this.iv = iv;
	}

	public void clear() {
		map.clear();
	}

	public byte[] get(Object key) {
		try {
			byte[] data = map.get(key);
			return data == null ? null : EncryptionUtil.aesDecrypt(data, aesKey, iv);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Set<String> keySet() {
		return map.keySet();
	}

	public byte[] put(String key, byte[] value) {
		byte[] oldValue = get(key);
		try {
			map.put(key, EncryptionUtil.aesEncrypt(value, aesKey, iv));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
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
