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

package org.ujmp.pdfbox;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.InputStream;

import org.junit.Test;
import org.ujmp.core.Matrix;
import org.ujmp.core.util.ResourceUtil;

public class TestImportPDF {

	public static final String PDFRESOURCE = "org/ujmp/pdfbox/test.pdf";

	@Test
	public void testImportPDFFile() throws Exception {
		File file = File.createTempFile("test", ".pdf");

		ResourceUtil.copyToFile(PDFRESOURCE, file);

		assertTrue(file.exists());

		Matrix m = Matrix.Factory.importFrom().file(file).asPDF();

		String s = m.getAsString(0, 0);
		assertEquals("test", s.trim());

		file.delete();

		assertFalse(file.exists());
	}

	@Test
	public void testImportPDFStream() throws Exception {
		InputStream is = ResourceUtil.getResourceAsStream(PDFRESOURCE);

		Matrix m = Matrix.Factory.importFrom().stream(is).asPDF();

		String s = m.getAsString(0, 0);
		assertEquals("test", s.trim());
	}
}
