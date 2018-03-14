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

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import org.elasticsearch.action.admin.cluster.state.ClusterStateResponse;
import org.elasticsearch.client.Client;
import org.ujmp.core.mapmatrix.AbstractMapMatrix;

public class ElasticsearchCluster extends AbstractMapMatrix<String, ElasticsearchIndex> {
	private static final long serialVersionUID = 6665231410976080744L;

	private final Client client;

	public ElasticsearchCluster(String hostname) throws UnknownHostException {
		this(ElasticsearchUtil.createTransportClient(hostname, ElasticsearchIndex.DEFAULTPORT));
	}

	public ElasticsearchCluster(String hostname, int port) throws UnknownHostException {
		this(ElasticsearchUtil.createTransportClient(hostname, port));
	}

	public ElasticsearchCluster(Client client) {
		this.client = client;
	}

	@Override
	public synchronized ElasticsearchIndex get(Object indexName) {
		if (keySet().contains(indexName)) {
			return new ElasticsearchIndex(client, String.valueOf(indexName));
		} else {
			return null;
		}
	}

	@Override
	public synchronized int size() {
		return keySet().size();
	}

	@Override
	public Set<String> keySet() {
		ClusterStateResponse response = client.admin().cluster().prepareState().execute().actionGet();
		return new TreeSet<String>(Arrays.asList(response.getState().getMetaData().getConcreteAllIndices()));
	}

	@Override
	protected void clearMap() {
		throw new RuntimeException("refusing to delete all indices at one");
	}

	@Override
	protected ElasticsearchIndex removeFromMap(Object indexName) {
		client.admin().indices().prepareDelete(String.valueOf(indexName)).execute().actionGet();
		return null;
	}

	@Override
	protected ElasticsearchIndex putIntoMap(String indexName, ElasticsearchIndex value) {
		client.admin().indices().prepareCreate(indexName).execute().actionGet();
		return new ElasticsearchIndex(client, indexName);
	}

	public ElasticsearchIndex createIndex(String indexName) {
		return put(indexName, null);
	}

}
