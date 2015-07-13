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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.Inflater;

public abstract class CompressionUtil {

	public static byte[] zipCompress(byte[] input) {
		return zipCompress(input, Deflater.DEFAULT_COMPRESSION);
	}

	public static byte[] zipCompress(byte[] input, int level) {
		Deflater compressor = new Deflater();
		compressor.setLevel(level);
		compressor.setInput(input);
		compressor.finish();
		ByteArrayOutputStream bos = new ByteArrayOutputStream(input.length);
		byte[] buf = new byte[1024];
		while (!compressor.finished()) {
			int count = compressor.deflate(buf);
			bos.write(buf, 0, count);
		}
		try {
			bos.close();
		} catch (IOException e) {
		}
		return bos.toByteArray();
	}

	public static byte[] zipDecompress(byte[] input) throws IOException {
		Inflater inflator = new Inflater();
		inflator.setInput(input);
		ByteArrayOutputStream bos = new ByteArrayOutputStream(input.length);
		byte[] buf = new byte[1024];
		try {
			while (true) {
				int count = inflator.inflate(buf);
				if (count > 0) {
					bos.write(buf, 0, count);
				} else if (count == 0 && inflator.finished()) {
					break;
				} else {
					throw new RuntimeException("bad zip data, size:" + input.length);
				}
			}
		} catch (DataFormatException t) {
			throw new RuntimeException(t);
		} finally {
			inflator.end();
		}
		return bos.toByteArray();
	}

	public static byte[] gzipCompress(byte[] input) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			GZIPOutputStream os = new GZIPOutputStream(bos);
			os.write(input);
			os.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return bos.toByteArray();
	}

	public static long copy(InputStream input, OutputStream output) throws IOException {
		byte[] buffer = new byte[1024];
		long count = 0;
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}

	public static byte[] gzipDecompress(byte[] input) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			GZIPInputStream is = new GZIPInputStream(new ByteArrayInputStream(input));
			copy(is, out);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return out.toByteArray();
	}
}
