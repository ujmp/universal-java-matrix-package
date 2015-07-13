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

package org.ujmp.core.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public abstract class SerializationUtil {

	public static void serialize(Object obj, OutputStream outputStream) throws IOException {
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(outputStream);
			out.writeObject(obj);
		} catch (IOException ex) {
			throw ex;
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException ex) {
				// ignore close exception
			}
		}
	}

	public static Object deserialize(InputStream inputStream) throws ClassNotFoundException,
			IOException {
		ObjectInputStream in = null;
		try {
			// stream closed in the finally
			in = new ObjectInputStream(inputStream);
			return in.readObject();
		} catch (ClassNotFoundException ex) {
			throw ex;
		} catch (IOException ex) {
			throw ex;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				// ignore close exception
			}
		}
	}

	public static byte[] serialize(Serializable o) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		serialize(o, bos);
		return bos.toByteArray();
	}

	public static Object deserialize(byte[] data) throws ClassNotFoundException, IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		Object o = deserialize(bis);
		return o;
	}

	public static long sizeOf(Serializable o) throws IOException {
		return serialize(o).length;
	}

	public static Object load(String filename) throws ClassNotFoundException, IOException {
		return load(new File(filename));
	}

	public static Object load(File file) throws ClassNotFoundException, IOException {
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream bis = new BufferedInputStream(fis);
		Object o = deserialize(bis);
		bis.close();
		fis.close();
		return o;
	}

	public static Object loadCompressed(String filename) throws ClassNotFoundException, IOException {
		return loadCompressed(new File(filename));
	}

	public static Object loadCompressed(File file) throws ClassNotFoundException, IOException {
		FileInputStream fis = new FileInputStream(file);
		GZIPInputStream bis = new GZIPInputStream(fis);
		Object o = deserialize(bis);
		bis.close();
		fis.close();
		return o;
	}

	public static void save(String filename, Object o) throws IOException {
		save(new File(filename), o);
	}

	public static void save(File file, Object o) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		serialize(o, bos);
		bos.close();
		fos.close();
	}

	public static void saveCompressed(String filename, Object o) throws IOException {
		saveCompressed(new File(filename), o);
	}

	public static void saveCompressed(File file, Object o) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		GZIPOutputStream bos = new GZIPOutputStream(fos);
		serialize(o, bos);
		bos.close();
		fos.close();
	}

}
