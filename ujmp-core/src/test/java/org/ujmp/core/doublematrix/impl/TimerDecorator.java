/*
 * Copyright (C) 2010 by Frode Carlsen
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
package org.ujmp.core.doublematrix.impl;

import static java.lang.System.out;

import java.util.concurrent.Callable;

public final class TimerDecorator<M> implements Callable<M> {
	private final int k;
	private final int i;
	private final Callable<M> run;
	private final int j;

	public TimerDecorator(final long i, final long j, final long k, final Callable<M> run) {
		this((int) i, (int) j, (int) k, run);
	}

	public TimerDecorator(final int i, final int j, final int k, final Callable<M> run) {
		this.k = k;
		this.i = i;

		this.run = run;
		this.j = j;
	}

	public M call() throws Exception {
		final long start = System.nanoTime();
		M c = run.call();

		float durationMillis = (System.nanoTime() - start) / 1E6f;

		out.format("\nDuration [%s]= %s\n\t: %s x %s x %s; MFLOPS = %s; %s", Runtime.getRuntime().availableProcessors(),
				durationMillis, i, j, k, (2L * i * j * k / 1E6) / durationMillis * 1000, c.getClass().getSimpleName());
		return c;
	}
}