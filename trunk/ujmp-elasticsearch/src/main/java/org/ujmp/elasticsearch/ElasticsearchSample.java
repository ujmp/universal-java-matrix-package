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

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.elasticsearch.search.SearchHit;
import org.ujmp.core.mapmatrix.AbstractMapMatrix;

public class ElasticsearchSample extends AbstractMapMatrix<String, Object> {
	private static final long serialVersionUID = -9091555583281035794L;

	public static final String ID = ElasticsearchIndex.ID;
	public static final String SCORE = ElasticsearchIndex.SCORE;

	private final ElasticsearchIndex elasticsearchIndex;

	private final Map<String, Object> map;

	public ElasticsearchSample(ElasticsearchIndex elasticsearchIndex) {
		this.elasticsearchIndex = elasticsearchIndex;
		this.map = new TreeMap<String, Object>();
	}

	public ElasticsearchSample(ElasticsearchIndex elasticsearchIndex, Map<String, Object> source) {
		this.elasticsearchIndex = elasticsearchIndex;
		this.map = source;
	}

	public ElasticsearchSample(ElasticsearchIndex elasticsearchIndex, SearchHit hit) {
		this.elasticsearchIndex = elasticsearchIndex;
		setId(hit.getId());
		if (hit.getSource() != null) {
			this.map = hit.getSource();
		} else {
			this.map = new TreeMap<String, Object>();
		}
		setScore(hit.getScore());
	}

	public int size() {
		return map.size();
	}

	public Object get(Object key) {
		return map.get(key);
	}

	public Set<String> keySet() {
		return map.keySet();
	}

	@Override
	protected void clearMap() {
		elasticsearchIndex.remove(this.get(ID));
		map.clear();
	}

	@Override
	protected Object removeFromMap(Object key) {
		Object old = map.remove(key);
		elasticsearchIndex.put(this);
		return old;
	}

	@Override
	protected Object putIntoMap(String key, Object value) {
		Object old = map.put(key, value);
		if (!SCORE.equals(key)) {
			elasticsearchIndex.put(this);
		}
		return old;
	}

	public double getScore() {
		return getMetaDataDouble(SCORE);
	}

	public void setScore(double score) {
		setMetaData(SCORE, score);
	}

}
