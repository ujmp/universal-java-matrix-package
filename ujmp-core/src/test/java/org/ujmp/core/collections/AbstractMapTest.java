/*
 * Copyright (C) 2008-2010 by Holger Arndt
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
import java.util.Map;

import org.junit.Test;
import org.ujmp.core.interfaces.Erasable;
import org.ujmp.core.util.SerializationUtil;

public abstract class AbstractMapTest {

	public abstract Map<String, String> createMap() throws Exception;

	public String getLabel() {
		return this.getClass().getSimpleName();
	}

	@Test(timeout = 1000)
	public void testPutAndGet() throws Exception {
		Map<String, String> m = createMap();
		m.put("a", "test1");
		m.put("b", "test2");
		assertEquals(getLabel(), "test1", m.get("a"));
		assertEquals(getLabel(), "test2", m.get("b"));
		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
	}

	// TODO
	@Test(timeout = 1000)
	public void testClear() throws Exception {
		Map<String, String> m = createMap();
		assertTrue(getLabel(), m.isEmpty());
		assertEquals(getLabel(), 0, m.size());
		m.put("a", "test1");
		assertEquals(getLabel(), 1, m.size());
		assertFalse(getLabel(), m.isEmpty());
		m.put("b", "test2");
		assertEquals(getLabel(), 2, m.size());
		assertFalse(getLabel(), m.isEmpty());
		m.clear();
		assertEquals(getLabel(), 0, m.size());
		assertTrue(getLabel(), m.isEmpty());
	}

	// TODO
	@Test(timeout = 1000)
	public void testContainsKey() throws Exception {
		Map<String, String> m = createMap();
		assertFalse(getLabel(), m.containsKey("a"));
		assertFalse(getLabel(), m.containsKey("b"));
		m.put("a", "test1");
		assertTrue(getLabel(), m.containsKey("a"));
		assertFalse(getLabel(), m.containsKey("b"));
		m.put("b", "test2");
		assertTrue(getLabel(), m.containsKey("a"));
		assertTrue(getLabel(), m.containsKey("b"));
		m.remove("a");
		assertFalse(getLabel(), m.containsKey("a"));
		assertTrue(getLabel(), m.containsKey("b"));
		m.clear();
		assertFalse(getLabel(), m.containsKey("a"));
		assertFalse(getLabel(), m.containsKey("b"));
	}

	// TODO
	@Test(timeout = 1000)
	public void testContainsValue() throws Exception {
		Map<String, String> m = createMap();
		assertFalse(getLabel(), m.containsValue("test1"));
		assertFalse(getLabel(), m.containsValue("test2"));
		m.put("a", "test1");
		assertTrue(getLabel(), m.containsValue("test1"));
		assertFalse(getLabel(), m.containsValue("test2"));
		m.put("b", "test2");
		assertTrue(getLabel(), m.containsValue("test1"));
		assertTrue(getLabel(), m.containsValue("test2"));
		m.remove("a");
		assertFalse(getLabel(), m.containsValue("test1"));
		assertTrue(getLabel(), m.containsValue("test2"));
		m.clear();
		assertFalse(getLabel(), m.containsValue("test1"));
		assertFalse(getLabel(), m.containsValue("test2"));
	}

	// TODO
	@SuppressWarnings("unchecked")
	@Test(timeout = 1000)
	public void testSerialize() throws Exception {
		Map<String, String> m = createMap();
		m.put("a", "test1");
		m.put("b", "test2");
		if (m instanceof Serializable) {
			byte[] data = SerializationUtil.serialize((Serializable) m);
			Map<String, String> m2 = (Map<String, String>) SerializationUtil.deserialize(data);
			assertEquals(getLabel(), m, m2);
		}
	}

}
