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

import java.util.Set;

import org.elasticsearch.action.count.CountResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.ujmp.core.mapmatrix.AbstractMapMatrix;
import org.ujmp.core.mapmatrix.MapMatrix;
import org.ujmp.core.util.MathUtil;

public class ElasticsearchIndex extends AbstractMapMatrix<String, MapMatrix<String, Object>> {
	private static final long serialVersionUID = -7047080106649021619L;

	public static final String ID = "_id";

	private final Client client;
	private final String index;
	private final String type;

	public ElasticsearchIndex(String hostname, String index, String type) {
		this("elasticsearch", hostname, 9300, index, type);
	}

	public ElasticsearchIndex(String clustername, String hostname, String index, String type) {
		this(clustername, hostname, 9300, index, type);
	}

	public ElasticsearchIndex(String hostname, int port, String index, String type) {
		this("elasticsearch", hostname, port, index, type);
	}

	public ElasticsearchIndex(String clustername, String hostname, int port, String index, String type) {
		this(new TransportClient(ImmutableSettings.settingsBuilder().put("cluster.name", clustername).build())
				.addTransportAddress(new InetSocketTransportAddress(hostname, port)), index, type);
	}

	public ElasticsearchIndex(Client client, String index, String type) {
		this.client = client;
		this.index = index;
		this.type = type;
	}

	public void put(MapMatrix<String, Object> map) {
		Object id = map.get(ID);
		if (id != null && id instanceof String) {
			put((String) id, map);
		} else {
			throw new IllegalArgumentException("id field missing");
		}
	}

	public int size() {
		MatchAllQueryBuilder query = QueryBuilders.matchAllQuery();
		// SearchResponse result =
		// client.prepareSearch(index).setQuery(query).execute().actionGet();

		CountResponse response = client.prepareCount(index).setQuery(query).execute().actionGet();

		return MathUtil.longToInt(response.getCount());
	}

	public MapMatrix<String, Object> get(Object key) {
		throw new UnsupportedOperationException();
	}

	public Set<String> keySet() {
		throw new UnsupportedOperationException();
	}

	@Override
	protected void clearMap() {
		throw new UnsupportedOperationException();
	}

	@Override
	protected MapMatrix<String, Object> removeFromMap(Object key) {
		DeleteResponse response = client.prepareDelete(index, type, String.valueOf(key)).execute().actionGet();
		return null;
	}

	@Override
	protected MapMatrix<String, Object> putIntoMap(String key, MapMatrix<String, Object> value) {
		value.put(ID, key);
		IndexResponse response = client.prepareIndex(index, type, key).setSource(value).execute().actionGet();
		return null;
	}

	public Client getClient() {
		return client;
	}

}
