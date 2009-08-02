/*
 * Copyright (C) 2008-2009 Holger Arndt, A. Naegele and M. Bundschus
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

package org.ujmp.gui.plot;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;

import org.ujmp.gui.MatrixGUIObject;

public class PlotSettings {

	private MatrixGUIObject matrixGUIObject = null;

	private long timeLimit = 5000;

	private int height = 600;

	private int width = 800;

	private Color axisColor = new Color(0, 0, 0, 100);

	private Color zeroAxisColor = new Color(0, 0, 0, 150);

	private Color plotBackGroundColor = new Color(216, 213, 196);

	private boolean showXAxis = true;

	private boolean showYAxis = true;

	private boolean showZeroAxis = true;

	private boolean showSelection = true;

	private boolean showPlotBackGround = true;

	private double minXValue = 0;

	private double maxXValue = 0;

	private double minYValue = -2;

	private double maxYValue = 2;

	private List<Color> plotColors = new ArrayList<Color>();

	private List<Stroke> plotStrokes = new ArrayList<Stroke>();

	private Stroke axisStroke = new BasicStroke(0.5f);

	private Stroke zeroAxisStroke = new BasicStroke(1.5f);

	public static final float[] DASHPATTERN = { 2f, 2f };

	public static final Stroke DASHEDSTROKE = new BasicStroke(0.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
			0.0f, DASHPATTERN, 0);

	private Stroke yGridStroke = DASHEDSTROKE;

	private Stroke xGridStroke = DASHEDSTROKE;

	private Color xGridColor = new Color(255, 255, 255, 180);

	private Color yGridColor = new Color(255, 255, 255, 180);

	private Color selectionColor = new Color(150, 150, 255, 80);

	private Color selectionLineColor = new Color(80, 80, 255);

	private List<Boolean> plotTraces = new ArrayList<Boolean>();

	private boolean showXGrid = true;

	private boolean showYGrid = true;

	private boolean showRunningAverage = true;

	private int runningAverageLength = 60;

	private Color runningAverageLineColor = new Color(100, 70, 0);

	private Stroke runningAverageStroke = new BasicStroke(0.5f);

	public void setAxisColor(Color axisColor) {
		this.axisColor = axisColor;
	}

	public PlotSettings(MatrixGUIObject m) {
		this();
		this.matrixGUIObject = m;
	}

	public PlotSettings() {
		plotColors.add(Color.BLUE);
		plotColors.add(Color.RED);
		plotColors.add(Color.GREEN);
		plotColors.add(Color.YELLOW);
		plotColors.add(Color.BLACK);
		plotColors.add(Color.WHITE);
		plotColors.add(Color.cyan);
		plotColors.add(Color.MAGENTA);
		plotColors.add(Color.orange);
		plotColors.add(Color.pink);
		plotStrokes.add(new BasicStroke(0.5f));
		plotStrokes.add(new BasicStroke(0.5f));
		plotStrokes.add(new BasicStroke(0.5f));
		plotStrokes.add(new BasicStroke(0.5f));
		plotStrokes.add(new BasicStroke(0.5f));
		plotStrokes.add(new BasicStroke(0.5f));
		plotStrokes.add(new BasicStroke(0.5f));
		plotStrokes.add(new BasicStroke(0.5f));
		plotStrokes.add(new BasicStroke(0.5f));
		plotStrokes.add(new BasicStroke(0.5f));
		plotTraces.add(true);
		plotTraces.add(true);
		plotTraces.add(true);
		plotTraces.add(true);
		plotTraces.add(true);
		plotTraces.add(true);
		plotTraces.add(true);
		plotTraces.add(true);
		plotTraces.add(true);
		plotTraces.add(true);
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public MatrixGUIObject getMatrixGUIObject() {
		return matrixGUIObject;
	}

	public void setMatrixGUIObject(MatrixGUIObject matrixGUIObject) {
		this.matrixGUIObject = matrixGUIObject;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public Color getAxisColor() {
		return axisColor;
	}

	public boolean isShowXAxis() {
		return showXAxis;
	}

	public void setShowXAxis(boolean showXAxis) {
		this.showXAxis = showXAxis;
	}

	public boolean isShowYAxis() {
		return showYAxis;
	}

	public void setShowYAxis(boolean showYAxis) {
		this.showYAxis = showYAxis;
	}

	public Stroke getAxisStroke() {
		return axisStroke;
	}

	public Color getPlotBackGroundColor() {
		return plotBackGroundColor;
	}

	public void setPlotBackGroundColor(Color plotBackGroundColor) {
		this.plotBackGroundColor = plotBackGroundColor;
	}

	public void setAxisStroke(Stroke axisStroke) {
		this.axisStroke = axisStroke;
	}

	public boolean isShowPlotBackGround() {
		return showPlotBackGround;
	}

	public double getMaxXValue() {
		return maxXValue;
	}

	public void setMaxXValue(double maxX) {
		this.maxXValue = maxX;
	}

	public double getMaxYValue() {
		return maxYValue;
	}

	public void setMaxYValue(double maxY) {
		this.maxYValue = maxY;
	}

	public double getMinXValue() {
		return minXValue;
	}

	public void setMinXValue(double minX) {
		this.minXValue = minX;
	}

	public double getMinYValue() {
		return minYValue;
	}

	public void setMinYValue(double minY) {
		this.minYValue = minY;
	}

	public void setShowPlotBackGround(boolean showPlotBackGround) {
		this.showPlotBackGround = showPlotBackGround;
	}

	public double getXStepSize() {
		double xs = (maxXValue - minXValue) / getWidth() / 2.0;
		return xs < 1 ? 1 : xs;
	}

	public double getXGridStepSize() {
		return 25.0 * (getMaxXValue() - getMinXValue()) / getWidth();
	}

	public double getXStepCount() {
		return (maxXValue - minXValue) / getXStepSize();
	}

	public Stroke getXGridStroke() {
		return xGridStroke;
	}

	public Stroke getYGridStroke() {
		return yGridStroke;
	}

	public void setXGridStroke(Stroke gridStroke) {
		xGridStroke = gridStroke;
	}

	public void setYGridStroke(Stroke gridStroke) {
		yGridStroke = gridStroke;
	}

	public Color getXGridColor() {
		return xGridColor;
	}

	public Color getYGridColor() {
		return yGridColor;
	}

	public double getXFactor() {
		return getWidth() / (getMaxXValue() - getMinXValue());
	}

	public double getYGridStepSize() {
		return 25.0 * (getMaxYValue() - getMinYValue()) / getHeight();
	}

	public boolean isShowXGrid() {
		return showXGrid;
	}

	public boolean isShowYGrid() {
		return showYGrid;
	}

	public double getYFactor() {
		return getHeight() / (getMaxYValue() - getMinYValue());
	}

	public boolean isShowTrace(int i) {
		return plotTraces.get(i);
	}

	public Stroke getTraceStroke(int t) {
		return plotStrokes.get(t);
	}

	public Color getTraceColor(int t) {
		return plotColors.get(t);
	}

	public List<Color> getPlotColors() {
		return plotColors;
	}

	public void setPlotColors(List<Color> plotColors) {
		this.plotColors = plotColors;
	}

	public List<Stroke> getPlotStrokes() {
		return plotStrokes;
	}

	public void setPlotStrokes(List<Stroke> plotStrokes) {
		this.plotStrokes = plotStrokes;
	}

	public List<Boolean> getPlotTraces() {
		return plotTraces;
	}

	public void setPlotTraces(List<Boolean> plotTraces) {
		this.plotTraces = plotTraces;
	}

	public void setShowXGrid(boolean showXGrid) {
		this.showXGrid = showXGrid;
	}

	public void setShowYGrid(boolean showYGrid) {
		this.showYGrid = showYGrid;
	}

	public void setXGridColor(Color gridColor) {
		xGridColor = gridColor;
	}

	public void setYGridColor(Color gridColor) {
		yGridColor = gridColor;
	}

	public long getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(long timeLimit) {
		this.timeLimit = timeLimit;
	}

	public Color getSelectionColor() {
		return selectionColor;
	}

	public void setSelectionColor(Color selectionColor) {
		this.selectionColor = selectionColor;
	}

	public boolean isShowSelection() {
		return showSelection;
	}

	public Color getSelectionLineColor() {
		return selectionLineColor;
	}

	public void setSelectionLineColor(Color selectionLineColor) {
		this.selectionLineColor = selectionLineColor;
	}

	public void setShowSelection(boolean showSelection) {
		this.showSelection = showSelection;
	}

	public boolean isShowZeroAxis() {
		return showZeroAxis;
	}

	public void setShowZeroAxis(boolean showZeroAxis) {
		this.showZeroAxis = showZeroAxis;
	}

	public Color getZeroAxisColor() {
		return zeroAxisColor;
	}

	public void setZeroAxisColor(Color zeroAxisColor) {
		this.zeroAxisColor = zeroAxisColor;
	}

	public Stroke getZeroAxisStroke() {
		return zeroAxisStroke;
	}

	public void setZeroAxisStroke(Stroke zeroAxisStroke) {
		this.zeroAxisStroke = zeroAxisStroke;
	}

	public boolean isShowRunningAverage() {
		return showRunningAverage;
	}

	public int getRunningAverageLength() {
		return runningAverageLength;
	}

	public Color getRunningAverageLineColor() {
		return runningAverageLineColor;
	}

	public Stroke getRunningAverageStroke() {
		return runningAverageStroke;
	}

	public void setShowRunningAverage(boolean showRunningAverage) {
		this.showRunningAverage = showRunningAverage;
	}

	public void setRunningAverageLength(int runningAverageLength) {
		this.runningAverageLength = runningAverageLength;
	}

	public void setRunningAverageLineColor(Color runningAverageLineColor) {
		this.runningAverageLineColor = runningAverageLineColor;
	}

	public void setRunningAverageStroke(Stroke runningAverageStroke) {
		this.runningAverageStroke = runningAverageStroke;
	}

}
