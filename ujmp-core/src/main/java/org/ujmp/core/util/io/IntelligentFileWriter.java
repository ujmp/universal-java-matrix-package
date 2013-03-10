/*
 * Copyright (C) 2008-2013 by Holger Arndt
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

package org.ujmp.core.util.io;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.Flushable;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipOutputStream;

public class IntelligentFileWriter extends Writer implements Appendable, Closeable, Flushable {

	private static final String SEVENZIPOUTPUTSTREAM = "org.ujmp.j7zip.SevenZipOutputStream";

	private FileWriter fw = null;

	private OutputStream zip = null;

	private BufferedWriter bw = null;

	private String encoding = "UTF-8";

	public IntelligentFileWriter(String filename) throws IOException, ClassNotFoundException {
		this(filename, false);
	}

	public IntelligentFileWriter(String filename, boolean append) throws IOException,
			ClassNotFoundException {
		this(new File(filename), append);
	}

	public IntelligentFileWriter(OutputStream outputStream) throws IOException {
		bw = new BufferedWriter(new OutputStreamWriter(outputStream, encoding));
	}

	public IntelligentFileWriter(File file) throws IOException {
		this(file, false);
	}

	public IntelligentFileWriter(File file, boolean append) throws IOException {
		if (file.getAbsolutePath().toLowerCase().endsWith(".gz")) {
			zip = new GZIPOutputStream(new FileOutputStream(file, append));
			bw = new BufferedWriter(new OutputStreamWriter(zip, encoding));
		} else if (file.getAbsolutePath().toLowerCase().endsWith(".z")) {
			zip = new ZipOutputStream(new FileOutputStream(file, append));
			bw = new BufferedWriter(new OutputStreamWriter(zip, encoding));
		} else if (file.getAbsolutePath().toLowerCase().endsWith(".7z")) {
			try {
				Class<?> c = Class.forName(SEVENZIPOUTPUTSTREAM);
				Constructor<?> con = c.getConstructor(new Class[] { FileOutputStream.class });
				zip = (OutputStream) con.newInstance(new Object[] { new FileOutputStream(file,
						append) });
			} catch (ClassNotFoundException e) {
				throw new IOException("Could not find ujmp-j7zip in classpath");
			} catch (Exception e) {
				throw new IOException("Could not create SevenZipOutputStream");
			}
			bw = new BufferedWriter(new OutputStreamWriter(zip, encoding));
		} else {
			fw = new FileWriter(file, append);
			bw = new BufferedWriter(fw);
		}
	}

	public static final void save(String filename, String text) throws Exception {
		save(new File(filename), text);
	}

	public static final void save(File file, String text) throws Exception {
		IntelligentFileWriter fw = new IntelligentFileWriter(file, false);
		fw.write(text);
		fw.close();
	}

	public static final void append(String filename, String text) throws Exception {
		IntelligentFileWriter fw = new IntelligentFileWriter(filename, true);
		fw.write(text);
		fw.close();
	}

	public void close() throws IOException {
		if (bw != null)
			bw.close();
		if (fw != null)
			fw.close();
		if (zip != null)
			zip.close();
	}

	public void flush() throws IOException {
		if (bw != null)
			bw.flush();
		if (fw != null)
			fw.flush();
		if (zip != null)
			zip.flush();
	}

	public void write(char[] cbuf, int off, int len) throws IOException {
		bw.write(cbuf, off, len);
	}

	public static void write(OutputStream os, String text) {
		try {
			if (text == null) {
				text = "";
			}
			IntelligentFileWriter fw = new IntelligentFileWriter(os);
			fw.write(text);
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
