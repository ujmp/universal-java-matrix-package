/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;

import junit.framework.TestCase;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.Matrix.Format;

public abstract class AbstractExportMatrixTest extends TestCase {

	public abstract Format getFormat();

	public Matrix getMatrix() {
		return MatrixFactory.rand(5, 8);
	}

	public String getLabel() {
		return this.getClass().getSimpleName();
	}

	public void testExportToFile() throws Exception {

		File file = File.createTempFile("testExportToFile", "." + getFormat().name().toLowerCase());
		file.deleteOnExit();

		Matrix m = getMatrix();
		m.exportToFile(getFormat(), file);

		assertTrue(getLabel(), file.exists());
		assertTrue(getLabel(), file.length() > 0);

		Matrix m2 = MatrixFactory.importFromFile(getFormat(), file);

		assertTrue(getLabel(), m.equalsContent(m2));

		file.delete();
		assertFalse(getLabel(), file.exists());

	}

	public void testExportToStream() throws Exception {

		File file = File.createTempFile("testExportToStream" + getFormat(), "." + getFormat().name().toLowerCase());
		file.deleteOnExit();

		OutputStream os = new FileOutputStream(file);

		Matrix m = getMatrix();
		m.exportToStream(getFormat(), os);

		os.close();

		assertTrue(getLabel(), file.exists());
		assertTrue(getLabel(), file.length() > 0);

		file.delete();
		assertFalse(getLabel(), file.exists());

	}

	public void testExportToWriter() throws Exception {

		File file = File.createTempFile("testExportToWriter", "." + getFormat().name().toLowerCase());
		file.deleteOnExit();

		FileWriter fw = new FileWriter(file);

		Matrix m = getMatrix();
		m.exportToWriter(getFormat(), fw);

		fw.close();

		assertTrue(getLabel(), file.exists());
		assertTrue(getLabel(), file.length() > 0);

		file.delete();
		assertFalse(getLabel(), file.exists());

	}

	public void testExportToString() throws Exception {

		Matrix m = getMatrix();
		String s = m.exportToString(getFormat());

		assertTrue(getLabel(), s != null);
		assertTrue(getLabel(), s.length() > 0);

	}

	public void testExportToClipboard() throws Exception {

		Matrix m = getMatrix();
		m.exportToClipboard(getFormat());

	}

}
