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
import java.util.Map;

import junit.framework.TestCase;

import org.ujmp.core.interfaces.Erasable;
import org.ujmp.core.util.SerializationUtil;

public abstract class AbstractMapTest extends TestCase {

	public abstract Map<String, Object> createMap() throws Exception;

	public String getLabel() {
		return this.getClass().getSimpleName();
	}

	public void testPutAndGet() throws Exception {
		Map<String, Object> m = createMap();
		m.put("a", "test1");
		m.put("b", "test2");
		assertEquals(getLabel(), "test1", m.get("a"));
		assertEquals(getLabel(), "test2", m.get("b"));
		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
	}

	@SuppressWarnings("unchecked")
	public void testSerialize() throws Exception {
		Map<String, Object> m = createMap();
		m.put("a", "test1");
		m.put("b", "test2");
		if (m instanceof Serializable) {
			byte[] data = SerializationUtil.serialize((Serializable) m);
			Map<Object, Object> m2 = (Map<Object, Object>) SerializationUtil.deserialize(data);
			assertEquals(getLabel(), m, m2);
		}

	}

}
