/*
 * Copyright (C) 2008-2010 by Holger Arndt
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

import java.util.Map;
import java.util.WeakHashMap;

import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;
import org.ujmp.gui.MatrixGUIObject;

public class XYSeriesWrapper extends XYSeries {
	private static final long serialVersionUID = 2493663877511719452L;

	private static final int MAXITEMS = 3000;

	private final Map<Integer, XYDataItem> values = new WeakHashMap<Integer, XYDataItem>();

	private MatrixGUIObject matrix = null;

	private ValueMarker meanMarker = null;

	private IntervalMarker standardDeviationMarker = null;

	private IntervalMarker minMaxMarker = null;

	private int seriesId = 0;

	private int stepsize = 1;

	private int start = 0;

	public XYSeriesWrapper(MatrixGUIObject m, int number) {
		super(m.getColumnName(number), false, true);
		this.seriesId = number;
		this.matrix = m;
		// meanMarker = new MeanMarkerForVariable(variable, number);
		// standardDeviationMarker = new
		// StandardDeviationMarkerForVariable(variable, number);
		// minMaxMarker = new MinMaxMarkerForVariable(variable, number);
		// variable.getVariable().addVariableListener(this);
		// stepsize = (int) Math.ceil((double)
		// variable.getVariable().getMatrixCount() / (double) MAXITEMS);
	}

	// public void setRange(Range range) {
	// double length = range.getLength();
	// start = (int) Math.floor(range.getLowerBound());
	// stepsize = (int) Math.ceil(length / MAXITEMS);
	// }

	public XYDataItem getDataItem(int index) {
		// int id = start + index * stepsize;
		// if (id >= variable.getVariable().getMatrixCount()) {
		// return new XYDataItem(id, 0.0);
		// }
		//
		// Matrix matrix = variable.getVariable().getMatrix(id);
		// double value = 0.0;
		// try {
		// value = matrix.getAsDouble(number % matrix.getRowCount(), number
		// / matrix.getRowCount());
		// } catch (MatrixException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// XYDataItem xyDataItem = values.get(id);
		// if (xyDataItem == null) {
		// xyDataItem = new XYDataItem(id, value);
		// values.put(id, xyDataItem);
		// } else {
		// xyDataItem.setY(value);
		// }
		//
		// return xyDataItem;

		double row = index;
		try {
			row = Double.parseDouble(matrix.getRowName(index));
		} catch (Exception e) {
		}

		return new XYDataItem(row, matrix.getMatrix().getAsDouble(index,
				seriesId));
	}

	public int getItemCount() {
		return (int) matrix.getRowCount();
	}

	public int indexOf(Number x) {
		return (Integer) x;
	}
}

// public void valueChanged(VariableEvent e) {
// if (System.currentTimeMillis() % 10 == 0)
// fireSeriesChanged();
// }

// public ValueMarker getMeanMarker() {
// return meanMarker;
// }

// public IntervalMarker getStandardDeviationMarker() {
// return standardDeviationMarker;
// }

// public IntervalMarker getMinMaxMarker() {
// return minMaxMarker;
// }

// class StandardDeviationMarkerForVariable extends IntervalMarker {
// private static final long serialVersionUID = 4093403885413441600L;
//
// private int number = 0;
//
// private VariableGUIObject variable = null;
//
// public StandardDeviationMarkerForVariable(VariableGUIObject v, int number) {
// super(0, 0);
// this.variable = v;
// this.number = number;
// setPaint(new Color(255, 100, 100, 60));
// }
//
//	
// public double getEndValue() {
// try {
// return variable.getVariable().getMeanMatrix().getAsDouble(0, number)
// + variable.getVariable().getStandardDeviationMatrix().getAsDouble(0, number);
// } catch (MatrixException e) {
// return 0.0;
// }
// }
//
//	
// public double getStartValue() {
// try {
// return variable.getVariable().getMeanMatrix().getAsDouble(0, number)
// - variable.getVariable().getStandardDeviationMatrix().getAsDouble(0, number);
// } catch (MatrixException e) {
// return 0.0;
// }
// }
//
// }
//
// class MinMaxMarkerForVariable extends IntervalMarker {
//
// private int number = 0;
//
// private VariableGUIObject variable = null;
//
// public MinMaxMarkerForVariable(VariableGUIObject v, int number) {
// super(0, 0);
// this.variable = v;
// this.number = number;
// setPaint(new Color(255, 200, 200, 50));
// }
//
//	
// public double getEndValue() {
// try {
// return variable.getVariable().getMaxMatrix().getAsDouble(0, number);
// } catch (MatrixException e) {
// return 0.0;
// }
// }
//
//	
// public double getStartValue() {
// try {
// return variable.getVariable().getMinMatrix().getAsDouble(0, number);
// } catch (MatrixException e) {
// return 0.0;
// }
// }
//
// }
//
// class MeanMarkerForVariable extends ValueMarker {
// private static final long serialVersionUID = 7345423855597100653L;
//
// private int number = 0;
//
// private VariableGUIObject variable = null;
//
// public MeanMarkerForVariable(VariableGUIObject v, int number) {
// super(0);
// this.variable = v;
// this.number = number;
// setPaint(new Color(200, 0, 0, 128));
// setLabelAnchor(RectangleAnchor.TOP);
// setLabelTextAnchor(TextAnchor.TOP_RIGHT);
// }
//
//	
// public String getLabel() {
// try {
// return
// StringUtil.format(variable.getVariable().getMeanMatrix().getAsDouble(0,
// number));
// } catch (MatrixException e) {
// return "";
// }
// }
//
//	
// public double getValue() {
// try {
// return variable.getVariable().getMeanMatrix().getAsDouble(0, number);
// } catch (MatrixException e) {
// return 0.0;
// }
// }
//
//	
// public void setValue(double arg0) {
// }

