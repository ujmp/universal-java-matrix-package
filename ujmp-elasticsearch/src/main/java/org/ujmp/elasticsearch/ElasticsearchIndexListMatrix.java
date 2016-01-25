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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.elasticsearch.action.admin.indices.status.IndicesStatusResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.ujmp.core.Matrix;
import org.ujmp.core.listmatrix.AbstractListMatrix;

public class ElasticsearchIndexListMatrix extends AbstractListMatrix<Matrix> {
	private static final long serialVersionUID = 6665231410976080744L;

	private final Client client;

	public ElasticsearchIndexListMatrix(String hostname) {
		this(new TransportClient().addTransportAddress(new InetSocketTransportAddress(hostname, 9300)));
	}

	public ElasticsearchIndexListMatrix(String hostname, int port) {
		this(new TransportClient().addTransportAddress(new InetSocketTransportAddress(hostname, port)));
	}

	public ElasticsearchIndexListMatrix(Client client) {
		this.client = client;
	}

	@Override
	public synchronized Matrix get(int index) {
		IndicesStatusResponse response = client.admin().indices().prepareStatus().execute().actionGet();
		Set<String> indexSet = new TreeSet<String>(response.getIndices().keySet());
		List<String> indexList = new ArrayList<String>(indexSet);
		return Matrix.Factory.linkToValue(indexList.get(index));
	}

	public synchronized void addIndex(String indexName) {
		client.admin().indices().prepareCreate(indexName).execute().actionGet();
	}

	public synchronized void removeIndex(String... indices) {
		client.admin().indices().prepareDelete(indices).execute().actionGet();
	}

	@Override
	public void add(int index, Matrix element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public synchronized int size() {
		IndicesStatusResponse response = client.admin().indices().prepareStatus().execute().actionGet();
		return response.getIndices().size();
	}

	@Override
	public boolean addToList(Matrix t) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addToList(int index, Matrix element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Matrix removeFromList(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeFromList(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Matrix setToList(int index, Matrix element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clearList() {
		throw new UnsupportedOperationException();
	}

}
