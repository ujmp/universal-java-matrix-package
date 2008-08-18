package org.ujmp.core.timeseries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.ujmp.core.Matrix;
import org.ujmp.core.collections.SetToListWrapper;
import org.ujmp.core.doublematrix.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.exceptions.MatrixException;

public class TimeSeriesMatrix extends AbstractDenseDoubleMatrix2D {
	private static final long serialVersionUID = 4326920011599023858L;

	public enum Interpolation {
		NONE, STEPS, LINEAR
	};

	private Interpolation defaultInterpolation = Interpolation.NONE;

	private Map<Integer, Interpolation> seriesInterpolations = new HashMap<Integer, Interpolation>();

	private List<SortedMap<Long, Double>> series = new ArrayList<SortedMap<Long, Double>>();

	private SortedSet<Long> timestampsSet = new TreeSet<Long>();

	private List<Long> timestampsList = new SetToListWrapper<Long>(timestampsSet);

	public void addEvent(long timestamp, Matrix value) {
		if (value.getRowCount() != 1) {
			throw new MatrixException("matrix cannot have more than one row");
		}
		for (int id = 0; id < value.getColumnCount(); id++) {
			addEvent(timestamp, id, value.getAsDouble(0, id));
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

	public void addEvent(long timestamp, int seriesId, double value) {
		while (series.size() <= seriesId) {
			series.add(new TreeMap<Long, Double>());
		}
		SortedMap<Long, Double> map = series.get(seriesId);
		map.put(timestamp, value);
		timestampsSet.add(timestamp);
	}

	public int getEventCount() {
		return timestampsSet.size();
	}

	public int getSeriesCount() {
		return series.size();
	}

	public List<Long> getTimestamps() {
		return timestampsList;
	}

	@Override
	public long[] getSize() {
		return new long[] { getEventCount(), getSeriesCount() + 1 };
	}

	@Override
	public double getDouble(long row, long column) {
		int seriesId = (int) column - 1;
		long timestamp = timestampsList.get((int) row);
		if (column == 0) {
			return timestamp;
		} else {
			SortedMap<Long, Double> map = series.get(seriesId);
			switch (getInterpolation(seriesId)) {
			case NONE:
				Double v = map.get(timestamp);
				if (v == null) {
					return 0.0;
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
				throw new MatrixException("Interpolation method not (yet) supported: "
						+ getInterpolation(seriesId));
			}
		}
	}

	@Override
	public void setDouble(double value, long row, long column) {
		throw new MatrixException("please use addEvent() for changes");
	}

	public void setInterpolation(int seriesId, Interpolation interpolation) {
		seriesInterpolations.put(seriesId, interpolation);
	}

}
