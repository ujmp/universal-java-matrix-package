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

package org.ujmp.core.collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.util.Set;

import org.junit.Test;
import org.ujmp.core.util.SerializationUtil;

public abstract class AbstractStringSetTest {

	public abstract Set<String> createSet() throws Exception;

	public String getLabel() {
		return this.getClass().getSimpleName();
	}

	@Test
	public void testClear() throws Exception {
		Set<String> m = createSet();
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

	@Test
	public void testContains() throws Exception {
		Set<String> m = createSet();
		assertFalse(getLabel(), m.contains("a"));
		assertFalse(getLabel(), m.contains("b"));
		m.add("a");
		assertTrue(getLabel(), m.contains("a"));
		assertFalse(getLabel(), m.contains("b"));
		m.add("b");
		assertTrue(getLabel(), m.contains("a"));
		assertTrue(getLabel(), m.contains("b"));
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
	@Test
	public void testSerialize() throws Exception {
		Set<String> m = createSet();
		m.add("a");
		m.add("b");
		if (m instanceof Serializable) {
			byte[] data = SerializationUtil.serialize((Serializable) m);
			Set<String> m2 = (Set<String>) SerializationUtil.deserialize(data);
			assertEquals(getLabel(), m, m2);
		}
	}

}
