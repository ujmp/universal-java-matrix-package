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

package org.ujmp.core.io;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.ujmp.core.Matrix;

public class TestCSVImportExport {

	@Test
	public void testClipboardCSV1() throws IOException {
		Matrix m1 = Matrix.Factory.randn(8, 6);
		m1.exportTo().clipboard().asCSV();
		Matrix m2 = Matrix.Factory.randn(8, 6);
		m2.importFrom().clipboard().asCSV();
		assertEquals(m1, m2);
	}

	@Test
	public void testClipboardCSV2() throws IOException {
		Matrix m1 = Matrix.Factory.randn(8, 6);
		m1.exportTo().clipboard().asCSV(':');
		Matrix m2 = Matrix.Factory.randn(8, 6);
		m2.importFrom().clipboard().asCSV(':');
		assertEquals(m1, m2);
	}

	@Test
	public void testClipboardCSV3() throws IOException {
		Matrix m1 = Matrix.Factory.randn(8, 6);
		m1.exportTo().clipboard().asCSV(':', '#');
		Matrix m2 = Matrix.Factory.randn(8, 6);
		m2.importFrom().clipboard().asCSV(':', '#');
		assertEquals(m1, m2);
	}

	@Test
	public void testClipboardCSV4() throws IOException {
		Matrix m1 = Matrix.Factory.randn(8, 6);
		m1.exportTo().clipboard().asCSV();
		Matrix m2 = Matrix.Factory.importFrom().clipboard().asCSV();
		assertEquals(m1, m2);
	}

	@Test
	public void testFileCSV1() throws IOException {
		File file = File.createTempFile("ujmp-junit", ".tmp");
		file.deleteOnExit();
		Matrix m1 = Matrix.Factory.randn(8, 6);
		m1.exportTo().file(file).asCSV();
		Matrix m2 = Matrix.Factory.randn(8, 6);
		m2.importFrom().file(file).asCSV();
		assertEquals(m1, m2);
	}

	@Test
	public void testFileCSV2() throws IOException {
		File file = File.createTempFile("ujmp-junit", ".tmp");
		file.deleteOnExit();
		Matrix m1 = Matrix.Factory.randn(8, 6);
		m1.exportTo().file(file).asCSV(':');
		Matrix m2 = Matrix.Factory.randn(8, 6);
		m2.importFrom().file(file).asCSV(':');
		assertEquals(m1, m2);
	}

	@Test
	public void testFileCSV3() throws IOException {
		File file = File.createTempFile("ujmp-junit", ".tmp");
		file.deleteOnExit();
		Matrix m1 = Matrix.Factory.randn(8, 6);
		m1.exportTo().file(file).asCSV(':', '#');
		Matrix m2 = Matrix.Factory.randn(8, 6);
		m2.importFrom().file(file).asCSV(':', '#');
		assertEquals(m1, m2);
	}

	@Test
	public void testFileCSV4() throws IOException {
		File file = File.createTempFile("ujmp-junit", ".tmp");
		file.deleteOnExit();
		Matrix m1 = Matrix.Factory.randn(8, 6);
		m1.exportTo().file(file).asCSV();
		Matrix m2 = Matrix.Factory.importFrom().file(file).asCSV();
		assertEquals(m1, m2);
	}
}
