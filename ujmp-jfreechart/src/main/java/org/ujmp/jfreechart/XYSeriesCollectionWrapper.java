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

package org.ujmp.jfreechart;

import org.jfree.data.xy.XYSeriesCollection;
import org.ujmp.gui.MatrixGUIObject;

public class XYSeriesCollectionWrapper extends XYSeriesCollection {
	private static final long serialVersionUID = 963347559253591538L;

	private int maxSeriesCount = 10;

	private MatrixGUIObject matrix = null;

	public XYSeriesCollectionWrapper(MatrixGUIObject m) {
		this.matrix = m;

		int size = (int) Math.min(m.getColumnCount(), maxSeriesCount);
		for (int i = 0; i < size; i++) {
			addSeries(new XYSeriesWrapper(matrix, i));
		}
	}

	// public void setRange(Range range) {
	// int size = Math.min(getSeriesCount(), MAXTRACES);
	// for (int i = 0; i < size; i++) {
	// ((XYSeriesWrapper) getSeries(i)).setRange(range);
	// }
	// }

	// public ValueMarker getMeanMarker(int series) {
	// return ((XYSeriesWrapper) getSeries(series)).getMeanMarker();
	// }

	// public IntervalMarker getStandardDeviationMarker(int series) {
	// return ((XYSeriesWrapper) getSeries(series))
	// .getStandardDeviationMarker();
	// }

	// public IntervalMarker getMinMaxMarker(int series) {
	// return ((XYSeriesWrapper) getSeries(series)).getMinMaxMarker();
	// }
}
