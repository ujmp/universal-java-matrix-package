/*
 * Copyright (C) 2008-2009 Holger Arndt, A. Naegele and M. Bundschus
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

import junit.framework.TestCase;

public class TestRingBufferList extends TestCase {

	public void testAdd() {
		RingBufferList<Integer> rl = new RingBufferList<Integer>(4);

		assertEquals(0, rl.size());

		rl.add(0);
		assertEquals(1, rl.size());
		assertEquals(0, (int) rl.get(0));

		rl.add(1);
		assertEquals(2, rl.size());
		assertEquals(1, (int) rl.get(1));

		rl.add(2);
		assertEquals(3, rl.size());
		assertEquals(2, (int) rl.get(2));

		rl.set(1, 5);
		assertEquals(3, rl.size());
		assertEquals(5, (int) rl.get(1));

		rl.add(3);
		assertEquals(4, rl.size());
		assertEquals(3, (int) rl.get(3));

		rl.add(4);
		assertEquals(4, rl.size());
		assertEquals(4, (int) rl.get(3));

		rl.add(5);
		assertEquals(4, rl.size());
		assertEquals(5, (int) rl.get(3));

	}

}
