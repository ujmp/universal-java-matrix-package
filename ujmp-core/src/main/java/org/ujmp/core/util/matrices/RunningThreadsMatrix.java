/*
 * Copyright (C) 2008-2014 by Holger Arndt
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

package org.ujmp.core.util.matrices;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TimerTask;

import org.ujmp.core.Matrix;
import org.ujmp.core.mapmatrix.DefaultMapMatrix;
import org.ujmp.core.util.UJMPTimer;

public class RunningThreadsMatrix extends DefaultMapMatrix<Object, Object> {
	private static final long serialVersionUID = -6988423129848472319L;

	private final UJMPTimer timer;

	public RunningThreadsMatrix() {
		super(ThreadMap.getInstance());
		setLabel("Running Threads");
		setColumnLabel(0, "Thread");
		setColumnLabel(1, "StackTrace");

		final Matrix m = this;
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				m.fireValueChanged();
			}
		};
		timer = UJMPTimer.newInstance(this.getClass().getSimpleName());
		timer.schedule(task, 1000, 1000);
	}

}

class ThreadMap implements Map<Object, Object> {

	private static ThreadMap threadMap = null;

	public static ThreadMap getInstance() {
		if (threadMap == null) {
			threadMap = new ThreadMap();
		}
		return threadMap;
	}

	public void clear() {
	}

	public boolean containsKey(Object key) {
		return Thread.getAllStackTraces().containsKey(key);
	}

	public boolean containsValue(Object value) {
		return Thread.getAllStackTraces().containsValue(value);
	}

	public Set<java.util.Map.Entry<Object, Object>> entrySet() {
		return Collections.emptySet();
	}

	public Object get(Object key) {
		StackTraceElement[] st = Thread.getAllStackTraces().get(key);
		if (st != null) {
			return Arrays.asList(st);
		} else {
			return null;
		}
	}

	public boolean isEmpty() {
		return Thread.getAllStackTraces().isEmpty();
	}

	public Set<Object> keySet() {
		return new HashSet<Object>(Thread.getAllStackTraces().keySet());
	}

	public Object put(Object key, Object value) {
		return null;
	}

	public void putAll(Map<? extends Object, ? extends Object> m) {
	}

	public Object remove(Object key) {
		return null;
	}

	public int size() {
		return Thread.getAllStackTraces().size();
	}

	public Collection<Object> values() {
		return new ArrayList<Object>(Thread.getAllStackTraces().values());
	}

}