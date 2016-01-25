/*
 * Copyright (C) 2008-2016 by Holger Arndt
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

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;
import org.ujmp.core.Matrix;
import org.ujmp.core.mapmatrix.DefaultMapMatrix;
import org.ujmp.core.mapmatrix.MapMatrix;

public class TestXMLUtil {

	@Test
	public void testMap() throws Exception {
		File tempFile = File.createTempFile("xmltest", ".xml");
		MapMatrix<Object, Object> m1 = new DefaultMapMatrix<Object, Object>();
		m1.put("test1", "value1");
		m1.put("test2", "value2");
		m1.put("test3", "value3");
		XMLUtil.write(tempFile, m1);
		Matrix m2 = XMLUtil.parse(tempFile);
		assertEquals(m1, m2);
		tempFile.delete();
	}

}
