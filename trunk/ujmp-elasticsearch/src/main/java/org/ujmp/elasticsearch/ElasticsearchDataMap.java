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

package org.ujmp.elasticsearch;

import java.util.Base64;
import java.util.Set;

import org.elasticsearch.client.Client;
import org.ujmp.core.mapmatrix.AbstractMapMatrix;
import org.ujmp.core.mapmatrix.DefaultMapMatrix;
import org.ujmp.core.mapmatrix.MapMatrix;
import org.ujmp.core.util.MathUtil;

public class ElasticsearchDataMap extends AbstractMapMatrix<String, byte[]> {
	private static final long serialVersionUID = -75029573088286998L;

	public static final String ID = ElasticsearchIndex.ID;
	public static final String DATA = "data";
	public static final String LENGTH = "length";

	private final ElasticsearchIndex index;

	public ElasticsearchDataMap(String hostname, String index, String type) {
		this(hostname, ElasticsearchIndex.DEFAULTPORT, index, type);
	}

	public ElasticsearchDataMap(String hostname, int port, String index, String type) {
		this(ElasticsearchUtil.createTransportClient(hostname, port), index, type);
	}

	public ElasticsearchDataMap(Client client, String index, String type) {
		this.index = new ElasticsearchIndex(client, index, type);
	}

	public int size() {
		return index.size();
	}

	public byte[] get(Object key) {
		ElasticsearchSample s = index.get(key);
		if (s == null) {
			return null;
		}
		Object data = s.get(DATA);
		byte[] bytes = Base64.getDecoder().decode(String.valueOf(data).getBytes());
		return bytes;
	}

	public Set<String> keySet() {
		return index.keySet();
	}

	@Override
	protected void clearMap() {
		index.clear();
	}

	@Override
	protected byte[] removeFromMap(Object key) {
		index.remove(key);
		return null;
	}

	public void addData(byte[] data) {
		if (data == null) {
			return;
		} else {
			String id = MathUtil.md5(data);
			put(id, data);
		}
	}

	public void removeData(byte[] data) {
		if (data == null) {
			return;
		} else {
			String id = MathUtil.md5(data);
			remove(id);
		}
	}

	@Override
	protected byte[] putIntoMap(String key, byte[] value) {
		if (value == null) {
			return remove(key);
		} else {
			if (!index.containsKey(key)) {
				MapMatrix<String, Object> map = new DefaultMapMatrix<String, Object>();
				map.put(ID, key);
				map.put(DATA, value);
				map.put(LENGTH, value.length);
				index.put(map);
			}
			return null;
		}
	}

}
