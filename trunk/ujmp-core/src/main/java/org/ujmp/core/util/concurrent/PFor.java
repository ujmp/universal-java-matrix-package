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

package org.ujmp.core.util.concurrent;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;


public abstract class PFor {

	private static final ThreadLocal<ThreadPoolExecutor> executors = new ThreadLocal<ThreadPoolExecutor>();

	private static final ThreadLocal<List<Future<Object>>> futures = new ThreadLocal<List<Future<Object>>>();

	private Object[] objects = null;

	private static int processors = 2;

	static {
		try {
			processors = Runtime.getRuntime().availableProcessors();
		} catch (Throwable t) {
		}
	}

	public PFor(int threads, int first, int last, Object... objects) {
		this.objects = objects;

		if (threads < 2) {
			for (int i = first; i <= last; i++) {
				step(i);
			}
		} else {
			ThreadPoolExecutor es = executors.get();
			if (es == null) {
				es = new UJMPThreadPoolExecutor("PFor", threads, threads);
				executors.set(es);
			} else {
				es.setCorePoolSize(threads);
				es.setMaximumPoolSize(threads);
			}

			List<Future<Object>> list = futures.get();
			if (list == null) {
				list = new LinkedList<Future<Object>>();
				futures.set(list);
			}

			last++;
			double stepsize = (double) (last - first) / threads;

			for (int i = 0; i < threads; i++) {
				int starti = (int) Math.ceil(first + i * stepsize);
				int endi = (int) Math.ceil(first + (i + 1) * stepsize);
				list.add(es.submit(new StepCallable(starti, endi)));
			}

			for (Future<Object> f : list) {
				try {
					f.get();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			list.clear();
			es.setCorePoolSize(0);
		}
	}

	public PFor(int first, int last, Object... objects) {
		this(processors + 1, first, last, objects);
	}

	public abstract void step(int i);

	public final Object getObject(int i) {
		return objects[i];
	}

	class StepCallable implements Callable<Object> {

		private int first = 0;

		private int last = 0;

		public StepCallable(int first, int last) {
			this.first = first;
			this.last = last;
		}

		public Object call() throws Exception {
			try {
				for (int i = first; i < last; i++) {
					step(i);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

	}

	public static void main(String[] args) throws Exception {
		int count = 5;
		int start = 0;
		int end = 40;
		int sum = 0;
		double stepsize = (double) (end - start) / count;

		for (int i = 0; i < count; i++) {
			int starti = (int) Math.ceil(start + i * stepsize);
			int endi = (int) Math.ceil(start + (i + 1) * stepsize);
			System.out.println(i + ": " + starti + " " + endi);
			sum += endi - starti;
		}
		System.out.println("stepsize:" + stepsize);
		System.out.println("sum: " + (sum - 1));
	}
}
