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

package org.ujmp.core.collections.map;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Set;

public class WebMap extends AbstractMap<String, String> {
	private static final long serialVersionUID = 4489821220210347429L;

	private String userAgent = "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; Trident/6.0)";
	private boolean useCaches = false;
	private int connectTimeout = 3000;
	private Charset charset = Charset.defaultCharset();

	public WebMap() {
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public boolean isUseCaches() {
		return useCaches;
	}

	public void setUseCaches(boolean useCaches) {
		this.useCaches = useCaches;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public Charset getCharset() {
		return charset;
	}

	public void setCharset(Charset charset) {
		this.charset = charset;
	}

	@Override
	public void clear() {
		throw new RuntimeException("cannot delete the web");
	}

	@Override
	public String get(Object key) {
		if (key == null) {
			throw new RuntimeException("key cannot be null");
		}
		try {
			URL url = new URL(String.valueOf(key));
			URLConnection connection = url.openConnection();
			connection.setRequestProperty("User-Agent", userAgent);
			connection.setUseCaches(useCaches);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setConnectTimeout(connectTimeout);
			InputStream input = connection.getInputStream();
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int n = 0;
			while (-1 != (n = input.read(buffer))) {
				output.write(buffer, 0, n);
			}
			return new String(output.toByteArray(), charset);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Set<String> keySet() {
		throw new RuntimeException("cannot get all URLs of the web");
	}

	@Override
	public String put(String key, String value) {
		throw new RuntimeException("cannot submit a page to the web");
	}

	@Override
	public String remove(Object key) {
		throw new RuntimeException("cannot remove a page from the web");
	}

	@Override
	public int size() {
		return Integer.MAX_VALUE;
	}

}
