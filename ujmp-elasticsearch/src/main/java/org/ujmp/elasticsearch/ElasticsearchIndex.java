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

import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.print.attribute.UnmodifiableSetException;

import org.elasticsearch.action.count.CountResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder.Operator;
import org.elasticsearch.search.SearchHit;
import org.ujmp.core.collections.list.FastArrayList;
import org.ujmp.core.collections.set.AbstractSet;
import org.ujmp.core.mapmatrix.AbstractMapMatrix;
import org.ujmp.core.mapmatrix.MapMatrix;
import org.ujmp.core.util.MathUtil;

public class ElasticsearchIndex extends AbstractMapMatrix<String, MapMatrix<String, Object>> implements Closeable {
	private static final long serialVersionUID = -7047080106649021619L;

	public static final int SCROLLTIMEOUT = 600000;
	public static final int SCROLLSIZE = 1000;

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

	public String getIndex() {
		return index;
	}

	public int size() {
		MatchAllQueryBuilder query = QueryBuilders.matchAllQuery();
		CountResponse response = client.prepareCount(index).setTypes(type).setQuery(query).execute().actionGet();
		return MathUtil.longToInt(response.getCount());
	}

	public MapMatrix<String, Object> get(Object key) {
		GetResponse getResponse = client.prepareGet(index, type, String.valueOf(key)).execute().actionGet();
		return new ElasticsearchSample(this, getResponse.getSource());
	}

	public Set<String> keySet() {
		return new KeySet(this);
	}

	@Override
	protected void clearMap() {
		throw new UnsupportedOperationException();
	}

	@Override
	protected MapMatrix<String, Object> removeFromMap(Object key) {
		MapMatrix<String, Object> old = get(key);
		client.prepareDelete(index, type, String.valueOf(key)).execute().actionGet();
		return old;
	}

	@Override
	protected MapMatrix<String, Object> putIntoMap(String key, MapMatrix<String, Object> value) {
		MapMatrix<String, Object> old = get(key);
		if (!value.containsKey(ID)) {
			value.put(ID, key);
		}
		client.prepareIndex(index, type, key).setSource(value).execute().actionGet();
		return old;
	}

	public Client getClient() {
		return client;
	}

	public List<Map<String, Object>> search(String query) {
		List<Map<String, Object>> list = new FastArrayList<Map<String, Object>>();

		QueryBuilder qb = QueryBuilders.queryString(query).defaultOperator(Operator.AND);
		SearchResponse response = client.prepareSearch(index, type).setSearchType(SearchType.QUERY_AND_FETCH)
				.setQuery(qb).setFrom(0).setSize(10).setExplain(true).execute().actionGet();

		SearchHit[] results = response.getHits().getHits();

		for (SearchHit hit : results) {
			list.add(hit.getSource());
		}

		return list;
	}

	public int count(String string) {
		QueryBuilder query = QueryBuilders.queryString(string).defaultOperator(Operator.AND);
		CountResponse response = client.prepareCount(index).setTypes(type).setQuery(query).execute().actionGet();
		return MathUtil.longToInt(response.getCount());
	}

	public void close() throws IOException {
		if (client != null) {
			client.close();
		}
	}

	public String getType() {
		return type;
	}
}

class KeySet extends AbstractSet<String> {
	private static final long serialVersionUID = -9047566077947415546L;

	private final ElasticsearchIndex index;

	public KeySet(ElasticsearchIndex index) {
		this.index = index;
	}

	@Override
	public boolean add(String value) {
		throw new UnmodifiableSetException();
	}

	@Override
	public boolean remove(Object o) {
		throw new UnmodifiableSetException();
	}

	@Override
	public void clear() {
		throw new UnmodifiableSetException();
	}

	@Override
	public boolean contains(Object o) {
		return index.containsKey(o);
	}

	@Override
	public Iterator<String> iterator() {
		return new KeyIterator(index);
	}

	@Override
	public int size() {
		return index.size();
	}
}

class KeyIterator implements Iterator<String> {

	private final ElasticsearchIndex index;
	private Iterator<SearchHit> iterator;
	private SearchResponse scrollResp;

	public KeyIterator(ElasticsearchIndex index) {
		this.index = index;
		MatchAllQueryBuilder query = QueryBuilders.matchAllQuery();
		scrollResp = index.getClient().prepareSearch(index.getIndex()).setTypes(index.getType())
				.addFields(new String[] {}).setSearchType(SearchType.SCAN)
				.setScroll(new TimeValue(ElasticsearchIndex.SCROLLTIMEOUT)).setQuery(query)
				.setSize(ElasticsearchIndex.SCROLLSIZE).execute().actionGet();
		scrollResp = index.getClient().prepareSearchScroll(scrollResp.getScrollId())
				.setScroll(new TimeValue(ElasticsearchIndex.SCROLLTIMEOUT)).execute().actionGet();
		iterator = scrollResp.getHits().iterator();
	}

	public boolean hasNext() {
		if (iterator != null && !iterator.hasNext()) {
			scrollResp = index.getClient().prepareSearchScroll(scrollResp.getScrollId())
					.setScroll(new TimeValue(ElasticsearchIndex.SCROLLTIMEOUT)).execute().actionGet();
			if (scrollResp.getHits().getHits().length == 0) {
				iterator = null;
			} else {
				iterator = scrollResp.getHits().iterator();
			}
		}
		if (iterator != null && iterator.hasNext()) {
			return true;
		} else {
			return false;
		}
	}

	public String next() {
		if (iterator != null) {
			SearchHit hit = iterator.next();
			return hit.getId();
		} else {
			throw new NoSuchElementException();
		}
	}

	public void remove() {
		throw new UnmodifiableSetException();

	}

}