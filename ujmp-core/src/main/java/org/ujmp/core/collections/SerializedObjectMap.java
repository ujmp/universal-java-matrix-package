/*
 * Copyright (C) 2008-2009 Holger Arndt, A. Naegele and M. Bundschus
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

package org.ujmp.core.collections;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import org.ujmp.core.util.SerializationUtil;

public class SerializedObjectMap<V> extends AbstractDiskMap<V> {

	public SerializedObjectMap() throws IOException {
		this((File) null, true);
	}

	public SerializedObjectMap(String path) throws IOException {
		this(new File(path), true);
	}

	public SerializedObjectMap(boolean useGZip) throws IOException {
		this((File) null, useGZip);
	}

	public SerializedObjectMap(String path, boolean useGZip) throws IOException {
		this(new File(path), useGZip);
	}

	public SerializedObjectMap(File path) throws IOException {
		this(path, true);
	}

	public SerializedObjectMap(File path, boolean useGZip) throws IOException {
		super(path, useGZip);
	}

	public final void write(OutputStream os, V value) {
		try {
			SerializationUtil.serialize((Serializable) value, os);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public V read(InputStream is) {
		try {
			return (V) SerializationUtil.deserialize(is);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
