/*
 * Copyright (C) 2008-2016 by Holger Arndt
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

package org.ujmp.core.timeseries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.ujmp.core.Matrix;
import org.ujmp.core.collections.composite.SortedListSet;
import org.ujmp.core.doublematrix.stub.AbstractDenseDoubleMatrix2D;

public class TimeSeriesMatrix extends AbstractDenseDoubleMatrix2D {
	private static final long serialVersionUID = 4326920011599023858L;

	public enum Interpolation {
		NONE, STEPS, LINEAR
	};

	private Interpolation defaultInterpolation = Interpolation.NONE;

	private final Map<Integer, Interpolation> seriesInterpolations = new HashMap<Integer, Interpolation>();

	private final List<SortedMap<Long, Double>> series = new ArrayList<SortedMap<Long, Double>>();

	private final SortedListSet<Long> timestampsListSet = new SortedListSet<Long>();

	public TimeSeriesMatrix() {
		super(1, 1);
	}

	public void addEvent(long timestamp, Matrix value) {
		if (value.getRowCount() != 1) {
			throw new RuntimeException("matrix cannot have more than one row");
		}
		for (int id = 0; id < value.getColumnCount(); id++) {
			addEvent(timestamp, id + 1, value.getAsDouble(0, id));
		}
	}

	public Interpolation getInterpolation(int seriesId) {
		Interpolation i = seriesInterpolations.get(seriesId);
		if (i == null) {
			return defaultInterpolation;
		} else {
			return i;
		}
	}

	public Interpolation getDefaultInterpolation() {
		return defaultInterpolation;
	}

	public void setDefaultInterpolation(Interpolation defaultInterpolation) {
		this.defaultInterpolation = defaultInterpolation;
	}

	/**
	 * Adds the events of a new Matrix to the time series. The first column of
	 * the matrix must contain the timestamps.
	 * 
	 * @param events
	 *            matrix with events to add
	 */
	public void addEvents(Matrix events) {
		int seriesCount = getSeriesCount();
		for (int r = 0; r < events.getRowCount(); r++) {
			long timestamp = events.getAsLong(r, 0);
			for (int c = 1; c < events.getColumnCount(); c++) {
				double value = events.getAsDouble(r, c);
				addEvent(timestamp, seriesCount + c - 1, value);
			}
		}
	}

	public void addEvent(long timestamp, int column, double value) {
		int seriesId = column - 1;
		while (series.size() <= seriesId) {
			series.add(new TreeMap<Long, Double>());
		}
		SortedMap<Long, Double> map = series.get(seriesId);
		map.put(timestamp, value);
		timestampsListSet.add(timestamp);
	}

	public int getEventCount() {
		return timestampsListSet.size();
	}

	public int getSeriesCount() {
		return series.size();
	}

	public List<Long> getTimestamps() {
		return timestampsListSet;
	}

	public long[] getSize() {
		size[ROW] = getEventCount();
		size[COLUMN] = getSeriesCount() + 1;
		return size;
	}

	public long getRowCount() {
		return getEventCount();
	}

	public long getColumnCount() {
		return getSeriesCount() + 1;
	}

	public double getDouble(long row, long column) {
		return getDouble((int) row, (int) column);
	}

	public double getDouble(int row, int column) {

		if (row < 0 || column >= getColumnCount() || row >= getRowCount()) {
			return Double.NaN;
		}

		int seriesId = column - 1;
		long timestamp = timestampsListSet.get(row);
		if (column == 0) {
			return timestamp;
		} else {
			SortedMap<Long, Double> map = series.get(seriesId);
			switch (getInterpolation(seriesId)) {
			case NONE:
				Double v = map.get(timestamp);
				if (v == null) {
					return Double.NaN;
				} else {
					return v;
				}

			case STEPS:
				Iterator<Long> it = map.keySet().iterator();
				double value = 0.0;
				while (it.hasNext()) {
					long t = it.next();
					if (t > timestamp) {
						break;
					} else {
						value = map.get(t);
					}
				}
				return value;

			default:
				throw new RuntimeException("Interpolation method not (yet) supported: "
						+ getInterpolation(seriesId));
			}
		}
	}

	public void setDouble(double value, long row, long column) {
		throw new RuntimeException("please use addEvent() for making changes");
	}

	public void setDouble(double value, int row, int column) {
		throw new RuntimeException("please use addEvent() for making changes");
	}

	public void setInterpolation(int column, Interpolation interpolation) {
		int seriesId = column - 1;
		seriesInterpolations.put(seriesId, interpolation);
	}

	public long getRowForTime(long time) {
		long rows = getRowCount();
		for (long r = 0; r < rows; r++) {
			long t = getAsLong(r, 0);
			if (t == time) {
				return r;
			}
		}
		return -1;
	}

	public double getAsDoubleForTime(long time, long column) {
		long row = getRowForTime(time);
		if (row < 0 || column >= getColumnCount()) {
			return Double.NaN;
		}
		return getAsDouble(row, column);
	}

	public long getTimestamp(long row) {
		if (row < 0 | row >= timestampsListSet.size()) {
			return 0;
		} else {
			return timestampsListSet.get((int) row);
		}
	}

	public long getMinTimestamp() {
		if (timestampsListSet.isEmpty()) {
			return 0;
		} else {
			return timestampsListSet.first();
		}
	}

	public long getMaxTimestamp() {
		if (timestampsListSet.isEmpty()) {
			return 0;
		} else {
			return timestampsListSet.last();
		}
	}

}
