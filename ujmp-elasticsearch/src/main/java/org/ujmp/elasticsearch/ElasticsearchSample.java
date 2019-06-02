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

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.elasticsearch.search.SearchHit;
import org.ujmp.core.mapmatrix.AbstractMapMatrix;
import org.ujmp.core.util.StringUtil;

public class ElasticsearchSample extends AbstractMapMatrix<String, Object> {
	private static final long serialVersionUID = -9091555583281035794L;

	public static final String ID = ElasticsearchIndex.ID;
	public static final String SCORE = ElasticsearchIndex.SCORE;

	private ElasticsearchIndex elasticsearchIndex = null;

	private final Map<String, Object> map;

	public ElasticsearchSample() {
		this.map = new TreeMap<String, Object>();
	}

	public ElasticsearchSample(Map<String, Object> map) {
		this.map = map;
	}

	public ElasticsearchSample(ElasticsearchIndex elasticsearchIndex) {
		this.elasticsearchIndex = elasticsearchIndex;
		this.map = new TreeMap<String, Object>();
	}

	public ElasticsearchSample(ElasticsearchIndex elasticsearchIndex, Map<String, Object> map) {
		this.elasticsearchIndex = elasticsearchIndex;
		this.map = map;
		setId(StringUtil.getString(map.get(ID)));
	}

	public ElasticsearchSample(ElasticsearchIndex elasticsearchIndex, SearchHit searchHit) {
		this.elasticsearchIndex = elasticsearchIndex;
		setId(searchHit.getId());
		if (searchHit.getSourceAsMap() != null) {
			this.map = searchHit.getSourceAsMap();
		} else {
			this.map = new TreeMap<String, Object>();
		}
		setScore(searchHit.getScore());
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
		map.clear();
		if (elasticsearchIndex != null) {
			elasticsearchIndex.remove(this.get(ID));
		}
	}

	@Override
	protected Object removeFromMap(Object key) {
		Object old = map.remove(key);
		if (elasticsearchIndex != null) {
			elasticsearchIndex.put(this);
		}
		return old;
	}

	@Override
	protected Object putIntoMap(String key, Object value) {
		if (ID.equals(key)) {
			if (getId() == null) {
				setId(key);
			} else {
				throw new RuntimeException("id cannot be changed");
			}
			return null;
		} else {
			Object old = map.put(key, value);
			if (elasticsearchIndex != null && !SCORE.equals(key)) {
				elasticsearchIndex.updateField(this.getId(), key, value);
			}
			return old;
		}
	}

	public double getScore() {
		return getMetaDataDouble(SCORE);
	}

	public void setScore(double score) {
		setMetaData(SCORE, score);
	}

	public ElasticsearchIndex getIndex() {
		return elasticsearchIndex;
	}

	protected void setIndex(ElasticsearchIndex index) {
		this.elasticsearchIndex = index;
	}

}
