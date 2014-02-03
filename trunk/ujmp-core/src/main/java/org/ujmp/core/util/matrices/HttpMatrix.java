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

package org.ujmp.core.util.matrices;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.ujmp.core.Matrix;
import org.ujmp.core.collections.map.AbstractMap;
import org.ujmp.core.listmatrix.DefaultListMatrix;
import org.ujmp.core.listmatrix.ListMatrix;
import org.ujmp.core.mapmatrix.AbstractMapMatrix;
import org.ujmp.core.mapmatrix.MapMatrix;

public class HttpMatrix extends AbstractMapMatrix<String, Matrix> {
	private static final long serialVersionUID = -8091708294752801016L;

	private final Map<String, Matrix> map;

	public HttpMatrix() {
		this("http://");
	}

	public HttpMatrix(String protocol) {
		map = new HttpMap(protocol);
	}

	@Override
	public Map<String, Matrix> getMap() {
		return map;
	}

	@Override
	public MapMatrix<String, Matrix> clone() {
		return null;
	}

}

class HttpMap extends AbstractMap<String, Matrix> {
	private static final long serialVersionUID = 3144851365925667008L;

	private final String defaultProtocol;

	public HttpMap(String defaultProtocol) {
		if (defaultProtocol == null || !defaultProtocol.endsWith("/")) {
			defaultProtocol += "/";
		}
		this.defaultProtocol = defaultProtocol;
	}

	@Override
	public void clear() {
	}

	@Override
	public Matrix get(Object key) {
		try {
			String urlString = String.valueOf(key);
			if (!urlString.startsWith("https://") && !urlString.startsWith("http://")) {
				urlString = defaultProtocol + urlString;
			}
			URL url = new URL(urlString);
			InputStream is = url.openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = null;
			ListMatrix<String> matrix = new DefaultListMatrix<String>();
			while ((line = br.readLine()) != null) {
				matrix.add(line);
			}
			return matrix;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Set<String> keySet() {
		return Collections.emptySet();
	}

	@Override
	public Matrix put(String key, Matrix value) {
		return null;
	}

	@Override
	public Matrix remove(Object key) {
		return null;
	}

	@Override
	public int size() {
		return 0;
	}
}