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

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Set;

import org.ujmp.core.mapmatrix.AbstractMapMatrix;
import org.ujmp.core.util.io.HttpUtil;

public class HttpMatrix extends AbstractMapMatrix<String, String> {
	private static final long serialVersionUID = -8091708294752801016L;

	private final String defaultProtocol;

	public HttpMatrix() {
		this("http://");
	}

	public HttpMatrix(String defaultProtocol) {
		if (defaultProtocol == null || !defaultProtocol.endsWith("/")) {
			defaultProtocol += "/";
		}
		this.defaultProtocol = defaultProtocol;
	}

	public int size() {
		return 0;
	}

	public String get(Object key) {
		try {
			String urlString = String.valueOf(key);
			if (!urlString.startsWith("https://") && !urlString.startsWith("http://")) {
				urlString = defaultProtocol + urlString;
			}
			String data = HttpUtil.getStringFromUrl(urlString);
			return data;
		} catch (Exception e) {
			if (e instanceof IOException && e.getMessage().contains("code: 403")) {
				return null;
			} else {
				e.printStackTrace();
				return null;
			}
		}
	}

	public Set<String> keySet() {
		return Collections.emptySet();
	}

	protected void clearMap() {
		throw new UnsupportedOperationException();
	}

	protected String removeFromMap(Object key) {
		throw new UnsupportedOperationException();
	}

	protected String putIntoMap(String key, String value) {
		throw new UnsupportedOperationException();
	}

}
