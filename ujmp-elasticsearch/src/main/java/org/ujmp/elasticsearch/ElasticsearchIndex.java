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

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequest;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.index.reindex.DeleteByQueryRequestBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.ujmp.core.collections.set.AbstractSet;
import org.ujmp.core.listmatrix.DefaultListMatrix;
import org.ujmp.core.listmatrix.ListMatrix;
import org.ujmp.core.mapmatrix.AbstractMapMatrix;
import org.ujmp.core.mapmatrix.MapMatrix;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.StringUtil;

import javax.print.attribute.UnmodifiableSetException;
import java.io.Closeable;
import java.net.UnknownHostException;
import java.util.*;

public class ElasticsearchIndex extends AbstractMapMatrix<String, Map<String, Object>> implements Closeable, Iterable<ElasticsearchSample> {
    private static final long serialVersionUID = -7047080106649021619L;

    public static final int DEFAULTPORT = 9300;

    public static final String DEFAULTTYPE = "_doc";

    public static final String ID = "_id";
    public static final String SCORE = "score";

    private final Client client;
    private final AdminClient adminClient;
    private final String indexName;
    private final IndicesAdminClient indicesAdminClient;

    private int scrollTimeout = 600000;
    private int scrollsize = 500;

    private WriteRequest.RefreshPolicy refreshPolicy = WriteRequest.RefreshPolicy.WAIT_UNTIL;

    public ElasticsearchIndex(String hostname, String indexName, Map<String, Object> mapping, Map<String, Object> settings) throws UnknownHostException {
        this(hostname, DEFAULTPORT, indexName, mapping, settings);
    }


    public ElasticsearchIndex(String hostname, int port, String indexName, Map<String, Object> mapping, Map<String, Object> settings) throws UnknownHostException {
        this(ElasticsearchUtil.createTransportClient(hostname, port), indexName, mapping, settings);
    }

    public ElasticsearchIndex(String hostname, int port, String indexName, String mapping, String settings) throws UnknownHostException {
        this(ElasticsearchUtil.createTransportClient(hostname, port), indexName, mapping, settings);
    }


    public ElasticsearchIndex(String hostname, String indexName, Map<String, Object> mapping) throws UnknownHostException {
        this(hostname, DEFAULTPORT, indexName, mapping);
    }

    public ElasticsearchIndex(String hostname, String indexName) throws UnknownHostException {
        this(hostname, DEFAULTPORT, indexName);
    }

    public ElasticsearchIndex(String hostname, String indexName, String mapping, String settings) throws UnknownHostException {
        this(hostname, DEFAULTPORT, indexName, mapping, settings);
    }

    public ElasticsearchIndex(String hostname, int port, String indexName) throws UnknownHostException {
        this(ElasticsearchUtil.createTransportClient(hostname, port), indexName);
    }

    public ElasticsearchIndex(String hostname, int port, String indexName, Map<String, Object> mapping) throws UnknownHostException {
        this(ElasticsearchUtil.createTransportClient(hostname, port), indexName, mapping, null);
    }

    public ElasticsearchIndex(Client client, String indexName) {
        this(client, indexName, (String) null, (String) null);
    }

    public ElasticsearchIndex(Client client, String indexName, Map<String, Object> mapping, Map<String, Object> settings) {
        this.client = client;
        this.adminClient = client.admin();
        this.indicesAdminClient = adminClient.indices();
        this.indexName = indexName;
        if (!indexExists()) {
            createIndex(mapping, settings);
        }
        this.setMetaData(new ElasticsearchIndexStatistics(client, indexName));
    }

    public ElasticsearchIndex(Client client, String indexName, String mapping, String settings) {
        this.client = client;
        this.adminClient = client.admin();
        this.indicesAdminClient = adminClient.indices();
        this.indexName = indexName;
        if (!indexExists()) {
            createIndex(mapping, settings);
        }
        this.setMetaData(new ElasticsearchIndexStatistics(client, indexName));
    }


    public WriteRequest.RefreshPolicy getRefreshPolicy() {
        return refreshPolicy;
    }

    public void setRefreshPolicy(WriteRequest.RefreshPolicy refreshPolicy) {
        this.refreshPolicy = refreshPolicy;
    }

    public void setNumberOfReplicas(int numberOfReplicas) {
        Settings.Builder settings = Settings.builder().put("index.number_of_replicas", numberOfReplicas);
        AcknowledgedResponse response = indicesAdminClient.prepareUpdateSettings(indexName).setSettings(settings).execute().actionGet();
        if (!response.isAcknowledged()) {
            throw new RuntimeException("cannot change number of replicas for index " + indexName);
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
            if (id instanceof String) {
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


    private synchronized void createIndex(Map<String, Object> mapping, Map<String, Object> settings) {
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        if (mapping != null) {
            request.mapping(DEFAULTTYPE, mapping);
        }
        if (settings != null) {
            request.settings(settings);
        }
        CreateIndexResponse response = indicesAdminClient.create(request).actionGet();
        if (!response.isAcknowledged()) {
            throw new RuntimeException("cannot create index " + indexName);
        }
        adminClient.cluster().prepareHealth().setWaitForYellowStatus().execute().actionGet();
    }

    private synchronized void createIndex(String mapping, String settings) {
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        if (mapping != null) {
            request.mapping(DEFAULTTYPE, mapping, XContentType.JSON);
        }
        if (settings != null) {
            request.settings(settings, XContentType.JSON);
        }
        CreateIndexResponse response = indicesAdminClient.create(request).actionGet();
        if (!response.isAcknowledged()) {
            throw new RuntimeException("cannot create index " + indexName);
        }
        adminClient.cluster().prepareHealth().setWaitForYellowStatus().execute().actionGet();
    }

    public Map<String, Object> getMapping() {
        GetMappingsRequest request = new GetMappingsRequest();
        request.indices(indexName);
        GetMappingsResponse getMappingResponse = adminClient.indices().getMappings(request).actionGet();
        ImmutableOpenMap<String, MappingMetaData> mappings = getMappingResponse.getMappings().get(indexName);
        String type = mappings.keys().iterator().next().value;
        MappingMetaData mappingMetaData = mappings.get(type);
        Map<String, Object> mappingSource = mappingMetaData.getSourceAsMap();
        return mappingSource;
    }

    public String getMappingAsJson() {
        GetMappingsRequest request = new GetMappingsRequest();
        request.indices(indexName);
        GetMappingsResponse getMappingResponse = adminClient.indices().getMappings(request).actionGet();
        ImmutableOpenMap<String, MappingMetaData> mappings = getMappingResponse.getMappings().get(indexName);
        String type = mappings.keys().iterator().next().value;
        MappingMetaData mappingMetaData = mappings.get(type);
        return mappingMetaData.source().toString();
    }

    public synchronized void delete() {
        AcknowledgedResponse response = indicesAdminClient.delete(new DeleteIndexRequest(indexName)).actionGet();
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
        SearchResponse response = client.prepareSearch(indexName).setSource(new SearchSourceBuilder().size(0).query(query)).get();
        return MathUtil.longToInt(response.getHits().getTotalHits().value);
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
        new DeleteByQueryRequestBuilder(client, DeleteByQueryAction.INSTANCE).filter(query).source(indexName).get();
    }

    public synchronized void optimize() {
        client.admin().indices().prepareForceMerge(indexName).execute().actionGet();
    }

    @Override
    protected synchronized MapMatrix<String, Object> removeFromMap(Object key) {
        MapMatrix<String, Object> old = get(key);
        client.prepareDelete().setIndex(indexName).setId(String.valueOf(key)).setType(DEFAULTTYPE).execute().actionGet();
        return old;
    }


    @Override
    protected synchronized void putAllIntoMap(Map<? extends String, ? extends Map<String, Object>> map) {
        BulkRequestBuilder bulkRequestBuilder = client.prepareBulk();
        for (String key : map.keySet()) {
            Map<String, Object> value = map.get(key);
            Map<String, Object> copy = new HashMap<>(value);
            copy.remove(ID); // indexing with _id field is not allowed
            IndexRequest indexRequest = new IndexRequest().index(indexName).id(key).type(DEFAULTTYPE).source(copy);
            bulkRequestBuilder.add(indexRequest);
        }
        bulkRequestBuilder.setRefreshPolicy(refreshPolicy).execute().actionGet();
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
            Map<String, Object> copy = new HashMap<>(value);
            copy.remove(ID); // indexing with _id field is not allowed
            client.prepareIndex().setIndex(indexName).setId(key).setType(DEFAULTTYPE).setSource(copy).
                    setRefreshPolicy(refreshPolicy).execute().actionGet();
            if (value instanceof ElasticsearchSample) {
                ((ElasticsearchSample) value).setIndex(this);
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
        return search(queryBuilder, null, size);
    }

    public synchronized ListMatrix<ElasticsearchSample> search(QueryBuilder queryBuilder, SortBuilder sort, int size) {
        ListMatrix<ElasticsearchSample> list = new DefaultListMatrix<>();

        SearchRequestBuilder searchRequest = client.prepareSearch(indexName).setQuery(queryBuilder).setSize(size);
        searchRequest.setPreference("_local");
        if (sort != null) {
            searchRequest.addSort(sort);
        }
        SearchResponse response = searchRequest.get();

        SearchHit[] results = response.getHits().getHits();

        for (SearchHit hit : results) {
            ElasticsearchSample sample = new ElasticsearchSample(this, hit);
            list.add(sample);
        }

        list.setMetaData("count", response.getHits().getTotalHits().value);

        return list;
    }

    public synchronized void close() {
        if (client != null) {
            client.close();
        }
    }

    public synchronized void updateField(String id, String key, Object value) {
        client.prepareUpdate(indexName, DEFAULTTYPE, id).setDoc(key, value).setRefreshPolicy(refreshPolicy).execute().actionGet();
    }

    @Override
    public Iterator<ElasticsearchSample> iterator() {
        return new SampleIterator(this);
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

        scrollResp = index.getClient().prepareSearch(index.getIndexName()).setFetchSource("_id", "_source")
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

class SampleIterator implements Iterator<ElasticsearchSample> {

    private final ElasticsearchIndex index;
    private Iterator<SearchHit> iterator;
    private SearchResponse scrollResp;

    public SampleIterator(ElasticsearchIndex index) {
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

    public ElasticsearchSample next() {
        if (iterator != null) {
            SearchHit hit = iterator.next();
            return new ElasticsearchSample(index, hit);
        } else {
            throw new NoSuchElementException();
        }
    }

    public void remove() {
        throw new UnmodifiableSetException();
    }

}