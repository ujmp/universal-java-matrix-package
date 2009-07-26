package org.ujmp.core.util.concurrent;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public abstract class PFor {

	private static final ThreadLocal<ThreadPoolExecutor> executors = new ThreadLocal<ThreadPoolExecutor>();

	private static final ThreadLocal<List<Future<Object>>> futures = new ThreadLocal<List<Future<Object>>>();

	private Object[] objects = null;

	public PFor(int threads, int first, int last, Object... objects) {
		this.objects = objects;

		if (threads <= 2) {
			for (int i = first; i <= last; i++) {
				step(i);
			}
		} else {
			ThreadPoolExecutor es = executors.get();
			if (es == null) {
				es = new ThreadPoolExecutor(threads, threads, 500L, TimeUnit.MILLISECONDS,
						new LinkedBlockingQueue<Runnable>());
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

			for (int i = 0; i < threads; i++) {
				list.add(es.submit(new StepCallable(first + i, last, threads)));
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
		this(4, first, last, objects);
	}

	public abstract void step(int i);

	public final Object getObject(int i) {
		return objects[i];
	}

	class StepCallable implements Callable<Object> {

		private int first = 0;

		private int last = 0;

		private int stepsize = 0;

		public StepCallable(int first, int last, int stepsize) {
			this.first = first;
			this.last = last;
			this.stepsize = stepsize;
		}

		@Override
		public Object call() throws Exception {
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
