/*
 * Copyright (C) 2008-2011 by Holger Arndt
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

package org.ujmp.complete;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import org.junit.Test;
import org.ujmp.core.Matrix;
import org.ujmp.core.util.matrices.UJMPPluginsMatrix;

public class TestPlugins {

	@Test
	public void testPlugins() {
		Matrix m = new UJMPPluginsMatrix();
		for (int r = 0; r < m.getRowCount(); r++) {
			String name = m.getAsString(r, 0);
			String status = m.getAsString(r, 4);

			// SST, JDBC, PDFBox and Hadoop require Java 1.6, they
			// cannot be tested with 1.5
			if ("ujmp-sst".equals(name)
					&& "1.5".equals(System.getProperty("java.specification.version"))) {
				assertNotSame(name, "ok", status);
			} else if ("ujmp-hadoop".equals(name)
					&& "1.5".equals(System.getProperty("java.specification.version"))) {
				assertNotSame(name, "ok", status);
			} else if ("ujmp-jdbc".equals(name)
					&& "1.5".equals(System.getProperty("java.specification.version"))) {
				assertNotSame(name, "ok", status);
			} else if ("ujmp-pdfbox".equals(name)
					&& "1.5".equals(System.getProperty("java.specification.version"))) {
				assertNotSame(name, "ok", status);
			} else if ("ujmp-ejml".equals(name)
					&& "1.5".equals(System.getProperty("java.specification.version"))) {
				assertNotSame(name, "ok", status);
			} else {
				assertEquals(name, "ok", status);
			}
		}
	}

}
