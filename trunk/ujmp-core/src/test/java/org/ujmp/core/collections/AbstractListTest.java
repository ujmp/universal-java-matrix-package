/*
 * Copyright (C) 2008-2009 by Holger Arndt
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

import java.io.Serializable;
import java.util.List;

import junit.framework.TestCase;

import org.ujmp.core.util.SerializationUtil;

public abstract class AbstractListTest extends TestCase {

	public abstract List<String> createList() throws Exception;

	public String getLabel() {
		return this.getClass().getSimpleName();
	}

	public void testClear() throws Exception {
		List<String> m = createList();
		assertTrue(getLabel(), m.isEmpty());
		assertEquals(getLabel(), 0, m.size());
		m.add("a");
		assertEquals(getLabel(), 1, m.size());
		assertFalse(getLabel(), m.isEmpty());
		m.add("b");
		assertEquals(getLabel(), 2, m.size());
		assertFalse(getLabel(), m.isEmpty());
		m.clear();
		assertEquals(getLabel(), 0, m.size());
		assertTrue(getLabel(), m.isEmpty());
	}

	public void testContains() throws Exception {
		List<String> m = createList();
		assertFalse(getLabel(), m.contains("a"));
		assertFalse(getLabel(), m.contains("b"));
		m.add("a");
		assertTrue(getLabel(), m.contains("a"));
		assertFalse(getLabel(), m.contains("b"));
		m.add("b");
		assertTrue(getLabel(), m.contains("a"));
		assertTrue(getLabel(), m.contains("b"));
		m.remove("a");
		assertFalse(getLabel(), m.contains("a"));
		assertTrue(getLabel(), m.contains("b"));
		m.clear();
		assertFalse(getLabel(), m.contains("a"));
		assertFalse(getLabel(), m.contains("b"));
	}

	@SuppressWarnings("unchecked")
	public void testSerialize() throws Exception {
		List<String> m = createList();
		m.add("a");
		m.add("b");
		if (m instanceof Serializable) {
			byte[] data = SerializationUtil.serialize((Serializable) m);
			List<String> m2 = (List<String>) SerializationUtil.deserialize(data);
			assertEquals(getLabel(), m, m2);
		}
	}

}
