/*
 * Copyright (C) 2008-2015 by Holger Arndt
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

import java.io.File;
import java.util.Collection;

import org.elasticsearch.client.Client;
import org.ujmp.core.mapmatrix.DefaultMapMatrix;
import org.ujmp.core.mapmatrix.MapMatrix;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.io.FileUtil;

public class ElasticsearchFileIndex {

	public static final String ID = ElasticsearchIndex.ID;
	public static final String META = "meta";
	public static final String DATA = "data";

	private final ElasticsearchDataMap dataMap;
	private final ElasticsearchIndex metaIndex;

	public ElasticsearchFileIndex(String hostname, String index) {
		this(hostname, ElasticsearchIndex.DEFAULTPORT, index, META, DATA);
	}

	public ElasticsearchFileIndex(String hostname, String index, String metaType, String dataType) {
		this(hostname, ElasticsearchIndex.DEFAULTPORT, index, metaType, dataType);
	}

	public ElasticsearchFileIndex(String hostname, int port, String index, String metaType, String dataType) {
		this(ElasticsearchUtil.createTransportClient(hostname, port), index, metaType, dataType);
	}

	public ElasticsearchFileIndex(Client client, String index, String metaType, String dataType) {
		this.dataMap = new ElasticsearchDataMap(client, index, dataType);
		this.metaIndex = new ElasticsearchIndex(client, index, metaType);
	}

	public void addFile(File file) throws Exception {
		if (file == null) {
			return;
		}

		MapMatrix<String, Object> map = new DefaultMapMatrix<String, Object>();
		map.put(ID, file.getAbsolutePath());
		map.put("_id", file.getAbsolutePath());
		map.put("is_file", file.isFile());
		map.put("is_directory", file.isDirectory());
		map.put("length", file.length());
		map.put("last_modified", file.lastModified());
		map.put("delete_time", 0);
		map.put("name", file.getName());
		map.put("can_read", file.canRead());
		map.put("can_write", file.canWrite());
		map.put("can_execute", file.canExecute());
		map.put("parent", file.getParent());

		if (!file.isDirectory()) {
			byte[] data = FileUtil.getBytes(file);
			String id = MathUtil.md5(data);
			dataMap.put(id, data);
			map.put("data_id", id);
		}

		metaIndex.put(map);
	}

	public Collection<MapMatrix<String, Object>> listFiles() {
		return metaIndex.values();
	}

}
