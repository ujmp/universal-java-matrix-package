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

package org.ujmp.core.util;

import java.util.Random;

/**
 * Better than java.util.Random and almost as fast
 */
public class RandomMersenne extends Random {
	private static final long serialVersionUID = 5949991470054762974L;

	private long u;
	private long v = 4101842887655102017L;
	private long w = 1;

	public RandomMersenne() {
		this(System.nanoTime());
	}

	public RandomMersenne(long seed) {
		u = seed ^ v;
		nextLong();
		v = u;
		nextLong();
		w = v;
		nextLong();
	}

	public long nextLong() {
		u = u * 2862933555777941757L + 7046029254386353087L;
		v ^= v >>> 17;
		v ^= v << 31;
		v ^= v >>> 8;
		w = 4294957665L * (w & 0xffffffff) + (w >>> 32);
		long x = u ^ (u << 21);
		x ^= x >>> 35;
		x ^= x << 4;
		long ret = (x + v) ^ w;
		return ret;
	}

	protected int next(int bits) {
		return (int) (nextLong() >>> (64 - bits));
	}

	public synchronized void setSeed(long seed) {
		u = seed ^ v;
		nextLong();
		v = u;
		nextLong();
		w = v;
		nextLong();
		super.setSeed(seed);
	}

}
