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

package org.ujmp.core.util.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import org.ujmp.core.util.UJMPSettings;

public abstract class PForEquidistant {

	private final Object[] objects;

	public PForEquidistant(final int threads, final int first, final int last,
			final Object... objects) {
		this.objects = objects;

		if (threads < 2) {
			for (int i = first; i <= last; i++) {
				step(i);
			}
		} else {
			final ThreadPoolExecutor es = UJMPThreadPoolExecutor.getInstance(threads);

			final Future<?>[] list = new Future[threads];

			for (int i = 0; i < threads; i++) {
				list[i] = es.submit(new StepCallable(first + i, last, threads));
			}

			for (Future<?> f : list) {
				try {
					f.get();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public PForEquidistant(final int first, final int last, final Object... objects) {
		this(UJMPSettings.getNumberOfThreads(), first, last, objects);
	}

	public abstract void step(final int i);

	public final Object getObject(final int i) {
		return objects[i];
	}

	class StepCallable implements Callable<Object> {
		private final int first;
		private final int last;
		private final int stepsize;

		public StepCallable(final int first, final int last, final int stepsize) {
			this.first = first;
			this.last = last;
			this.stepsize = stepsize;
		}

		public final Void call() throws Exception {
			try {
				for (int i = first; i <= last; i += stepsize) {
					step(i);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

	}

}
