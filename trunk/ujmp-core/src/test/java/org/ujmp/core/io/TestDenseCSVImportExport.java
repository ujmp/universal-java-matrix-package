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

package org.ujmp.core.io;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.junit.Test;
import org.ujmp.core.Matrix;

public class TestDenseCSVImportExport {

	@Test
	public void testClipboardCSV1() throws IOException {
		Matrix m1 = Matrix.Factory.randn(8, 6);
		m1.exportTo().clipboard().asDenseCSV();
		Matrix m2 = Matrix.Factory.randn(8, 6);
		m2.importFrom().clipboard().asDenseCSV();
		assertEquals(m1, m2);
	}

	@Test
	public void testClipboardCSV2() throws IOException {
		Matrix m1 = Matrix.Factory.randn(8, 6);
		m1.exportTo().clipboard().asDenseCSV(':');
		Matrix m2 = Matrix.Factory.randn(8, 6);
		m2.importFrom().clipboard().asDenseCSV(':');
		assertEquals(m1, m2);
	}

	@Test
	public void testClipboardCSV3() throws IOException {
		Matrix m1 = Matrix.Factory.randn(8, 6);
		m1.exportTo().clipboard().asDenseCSV(':', '#');
		Matrix m2 = Matrix.Factory.randn(8, 6);
		m2.importFrom().clipboard().asDenseCSV(':', '#');
		assertEquals(m1, m2);
	}

	@Test
	public void testClipboardCSVFactory() throws IOException {
		Matrix m1 = Matrix.Factory.randn(8, 6);
		m1.exportTo().clipboard().asDenseCSV();
		Matrix m2 = Matrix.Factory.importFrom().clipboard().asDenseCSV();
		assertEquals(m1, m2);
	}

	@Test
	public void testFileCSV1() throws IOException {
		File file = File.createTempFile("ujmp-junit", ".tmp");
		file.deleteOnExit();
		Matrix m1 = Matrix.Factory.randn(8, 6);
		m1.exportTo().file(file).asDenseCSV();
		Matrix m2 = Matrix.Factory.randn(8, 6);
		m2.importFrom().file(file).asDenseCSV();
		assertEquals(m1, m2);
	}

	@Test
	public void testFileCSV2() throws IOException {
		File file = File.createTempFile("ujmp-junit", ".tmp");
		file.deleteOnExit();
		Matrix m1 = Matrix.Factory.randn(8, 6);
		m1.exportTo().file(file).asDenseCSV(':');
		Matrix m2 = Matrix.Factory.randn(8, 6);
		m2.importFrom().file(file).asDenseCSV(':');
		assertEquals(m1, m2);
	}

	@Test
	public void testFileCSV3() throws IOException {
		File file = File.createTempFile("ujmp-junit", ".tmp");
		file.deleteOnExit();
		Matrix m1 = Matrix.Factory.randn(8, 6);
		m1.exportTo().file(file).asDenseCSV(':', '#');
		Matrix m2 = Matrix.Factory.randn(8, 6);
		m2.importFrom().file(file).asDenseCSV(':', '#');
		assertEquals(m1, m2);
	}

	@Test
	public void testFileCSVFactory() throws IOException {
		File file = File.createTempFile("ujmp-junit", ".tmp");
		file.deleteOnExit();
		Matrix m1 = Matrix.Factory.randn(8, 6);
		m1.exportTo().file(file).asDenseCSV();
		Matrix m2 = Matrix.Factory.importFrom().file(file).asDenseCSV();
		assertEquals(m1, m2);
	}

	@Test
	public void testStringCSV() throws IOException {
		Matrix m1 = Matrix.Factory.randn(8, 6);
		String s = m1.exportTo().string().asDenseCSV();
		Matrix m2 = Matrix.Factory.randn(8, 6);
		m2.importFrom().string(s).asDenseCSV();
		assertEquals(m1, m2);
	}

	@Test
	public void testStringCSVFactory() throws IOException {
		Matrix m1 = Matrix.Factory.randn(8, 6);
		String s = m1.exportTo().string().asDenseCSV();
		Matrix m2 = Matrix.Factory.importFrom().string(s).asDenseCSV();
		assertEquals(m1, m2);
	}

	@Test
	public void testStreamCSV() throws IOException {
		Matrix m1 = Matrix.Factory.randn(8, 6);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		m1.exportTo().stream(os).asDenseCSV();
		Matrix m2 = Matrix.Factory.randn(8, 6);
		ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
		m2.importFrom().stream(is).asDenseCSV();
		assertEquals(m1, m2);
	}

	@Test
	public void testStreamCSVFactory() throws IOException {
		Matrix m1 = Matrix.Factory.randn(8, 6);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		m1.exportTo().stream(os).asDenseCSV();
		ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
		Matrix m2 = Matrix.Factory.importFrom().stream(is).asDenseCSV();
		assertEquals(m1, m2);
	}

	@Test
	public void testByteArrayCSV() throws IOException {
		Matrix m1 = Matrix.Factory.randn(8, 6);
		byte[] bytes = m1.exportTo().byteArray().asDenseCSV();
		Matrix m2 = Matrix.Factory.randn(8, 6);
		m2.importFrom().byteArray(bytes).asDenseCSV();
		assertEquals(m1, m2);
	}

	@Test
	public void testByteArrayCSVFactory() throws IOException {
		Matrix m1 = Matrix.Factory.randn(8, 6);
		byte[] bytes = m1.exportTo().byteArray().asDenseCSV();
		Matrix m2 = Matrix.Factory.importFrom().byteArray(bytes).asDenseCSV();
		assertEquals(m1, m2);
	}

	@Test
	public void testReaderCSV() throws IOException {
		Matrix m1 = Matrix.Factory.randn(8, 6);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		OutputStreamWriter w = new OutputStreamWriter(os);
		m1.exportTo().writer(w).asDenseCSV();
		Matrix m2 = Matrix.Factory.randn(8, 6);
		ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
		InputStreamReader r = new InputStreamReader(is);
		m2.importFrom().reader(r).asDenseCSV();
		assertEquals(m1, m2);
	}

	@Test
	public void testReaderCSVFactory() throws IOException {
		Matrix m1 = Matrix.Factory.randn(8, 6);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		OutputStreamWriter w = new OutputStreamWriter(os);
		m1.exportTo().writer(w).asDenseCSV();
		ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
		InputStreamReader r = new InputStreamReader(is);
		Matrix m2 = Matrix.Factory.importFrom().reader(r).asDenseCSV();
		assertEquals(m1, m2);
	}
}
