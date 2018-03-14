/*
 * Copyright (C) 2008-2016 by Holger Arndt
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
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.print.attribute.UnmodifiableSetException;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.forcemerge.ForceMergeResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.ujmp.core.collections.set.AbstractSet;
import org.ujmp.core.listmatrix.DefaultListMatrix;
import org.ujmp.core.listmatrix.ListMatrix;
import org.ujmp.core.mapmatrix.AbstractMapMatrix;
import org.ujmp.core.mapmatrix.MapMatrix;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.StringUtil;

public class ElasticsearchIndex extends AbstractMapMatrix<String, Map<String, Object>> implements Closeable {
	private static final long serialVersionUID = -7047080106649021619L;

	public static final int DEFAULTPORT = 9300;

	public static final String DEFAULTTYPE = "doc";

	public static final String ID = "_id";
	public static final String SCORE = "score";

	private final Client client;
	private final AdminClient adminClient;
	private final String indexName;
	private final IndicesAdminClient indicesAdminClient;

	private int scrollTimeout = 600000;
	private int scrollsize = 500;

	public ElasticsearchIndex(String hostname, String index) throws UnknownHostException {
		this(hostname, DEFAULTPORT, index);
	}

	public ElasticsearchIndex(String hostname, int port, String index) throws UnknownHostException {
		this(ElasticsearchUtil.createTransportClient(hostname, port), index);
	}

	public ElasticsearchIndex(Client client, String indexName) {
		this.client = client;
		this.adminClient = client.admin();
		this.indicesAdminClient = adminClient.indices();
		this.indexName = indexName;
		if (!indexExists()) {
			createIndex();
		}
	}

	public synchronized Map<String, Object> put(Map<String, Object> map) {
		if (map == null) {
			throw new RuntimeException("cannot add empty map");
		}
		if (map instanceof MapMatrix) {
			MapMatrix<String, Object> mapMatrix = (MapMatrix<String, Object>) map;
			if (StringUtil.isEmpty(mapMatrix.getId())) {
				return putIntoMap(MathUtil.guid(), map);
			} else {
				return putIntoMap(mapMatrix.getId(), map);
			}
		} else {
			Object id = map.get(ID);
			if (id != null && id instanceof String) {
				return putIntoMap((String) id, map);
			} else {
				return putIntoMap(MathUtil.guid(), map);
			}
		}
	}

	private synchronized boolean indexExists() {
		IndicesExistsResponse response = indicesAdminClient.exists(new IndicesExistsRequest(indexName)).actionGet();
		return response.isExists();
	}

	private synchronized void createIndex() {
		CreateIndexResponse response = indicesAdminClient.create(new CreateIndexRequest(indexName)).actionGet();
		if (!response.isAcknowledged()) {
			throw new RuntimeException("cannot create index " + indexName);
		}
		adminClient.cluster().prepareHealth().setWaitForYellowStatus().execute().actionGet();
	}

	public synchronized void delete() {
		DeleteIndexResponse response = indicesAdminClient.delete(new DeleteIndexRequest(indexName)).actionGet();
		if (!response.isAcknowledged()) {
			throw new RuntimeException("cannot delete index " + indexName);
		}
		adminClient.cluster().prepareHealth().setWaitForYellowStatus().execute().actionGet();
	}

	public String getIndexName() {
		return indexName;
	}

	public synchronized int size() {
		MatchAllQueryBuilder query = QueryBuilders.matchAllQuery();
		SearchResponse response = client.prepareSearch(indexName)
				.setSource(new SearchSourceBuilder().size(0).query(query)).get();
		return MathUtil.longToInt(response.getHits().getTotalHits());
	}

	public synchronized ElasticsearchSample get(Object key) {
		GetResponse getResponse = client.prepareGet(indexName, null, String.valueOf(key)).execute().actionGet();
		Map<String, Object> map = getResponse.getSource();
		if (map == null) {
			return null;
		} else {
			map.put(ID, getResponse.getId());
			return new ElasticsearchSample(this, map);
		}
	}

	public synchronized Set<String> keySet() {
		return new KeySet(this);
	}

	@Override
	protected synchronized void clearMap() {
		MatchAllQueryBuilder query = QueryBuilders.matchAllQuery();
		BulkByScrollResponse response = DeleteByQueryAction.INSTANCE.newRequestBuilder(client).filter(query)
				.source(indexName).get();
	}

	public synchronized void optimize() {
		ForceMergeResponse response = client.admin().indices().prepareForceMerge(indexName).execute().actionGet();
	}

	@Override
	protected synchronized MapMatrix<String, Object> removeFromMap(Object key) {
		MapMatrix<String, Object> old = get(key);
		client.prepareDelete(indexName, DEFAULTTYPE, String.valueOf(key)).execute().actionGet();
		return old;
	}

	@Override
	protected synchronized MapMatrix<String, Object> putIntoMap(String key, Map<String, Object> value) {
		if (key == null) {
			throw new RuntimeException("key cannot be null");
		}
		if (value == null) {
			remove(key);
			return null;
		} else {
			Object tmpKey = null;
			if (value.containsKey(ID)) {
				tmpKey = value.remove(key);
			}
			client.prepareIndex(indexName, DEFAULTTYPE, key).setSource(value).execute().actionGet();
			if (value instanceof ElasticsearchSample) {
				((ElasticsearchSample) value).setIndex(this);
			}
			if (tmpKey != null) {
				value.put(ID, tmpKey);
			}
			return null;
		}
	}

	public int getScrollTimeout() {
		return scrollTimeout;
	}

	public void setScrollTimeout(int scrollTimeout) {
		this.scrollTimeout = scrollTimeout;
	}

	public int getScrollsize() {
		return scrollsize;
	}

	public void setScrollsize(int scrollsize) {
		this.scrollsize = scrollsize;
	}

	public Client getClient() {
		return client;
	}

	public synchronized ListMatrix<ElasticsearchSample> search(String queryString, int size) {
		return search(QueryBuilders.queryStringQuery(queryString), size);
	}

	public synchronized ListMatrix<ElasticsearchSample> search(QueryBuilder queryBuilder, int size) {
		ListMatrix<ElasticsearchSample> list = new DefaultListMatrix<ElasticsearchSample>();

		SearchResponse response = client.prepareSearch(indexName).setTypes(DEFAULTTYPE)
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH).setQuery(queryBuilder).setSize(size).get();

		SearchHit[] results = response.getHits().getHits();

		for (SearchHit hit : results) {
			ElasticsearchSample sample = new ElasticsearchSample(this, hit);
			list.add(sample);
		}

		return list;
	}

	public synchronized void close() throws IOException {
		if (client != null) {
			client.close();
		}
	}

	public synchronized void update(String id, String key, Object value) {
		client.prepareUpdate(indexName, DEFAULTTYPE, id).setDoc(key, value).execute().actionGet();
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

		scrollResp = index.getClient().prepareSearch(index.getIndexName())
				.setScroll(new TimeValue(index.getScrollTimeout())).setQuery(query).setSize(index.getScrollsize())
				.get();

		iterator = scrollResp.getHits().iterator();
	}

	public boolean hasNext() {
		if (iterator != null && !iterator.hasNext()) {

			scrollResp = index.getClient().prepareSearchScroll(scrollResp.getScrollId())
					.setScroll(new TimeValue(index.getScrollTimeout())).execute().actionGet();
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