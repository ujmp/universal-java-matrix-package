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

package org.ujmp.gui.colormap;

import java.awt.Color;


public class ColorMapper {

	private double min = 0.0;

	private double max = 10.0;

	private double a = 10;

	private double b = 0;

	private ColorMap colorMap = null;

	private int colorCount = 0;

	public ColorMap getColorMap() {
		return colorMap;
	}

	public void setColorMap(ColorMap colorMap) {
		this.colorMap = colorMap;
		this.colorCount = colorMap.size();
		this.a = colorCount / (max - min);
		this.b = -min * a;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		if (this.max >= max) {
			return;
		}
		if (max == this.min) {
			this.min = max - 10;
		}
		this.max = max;
		this.a = colorCount / (max - min);
		this.b = -min * a;
	}

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		if (this.min <= min) {
			return;
		}
		if (min == this.max) {
			this.max = min + 10;
		}
		this.min = min;
		this.a = colorCount / (max - min);
		this.b = -min * a;
	}

	public ColorMapper() {
		colorMap = ColorMap.DEFAULT;
		colorCount = colorMap.size();
		setMax(1);
		setMin(0);
	}

	public ColorMapper(double minValue, double maxValue) {
		this();
		setMax(maxValue);
		setMin(minValue);
	}

	public Color fromDouble2(double v) {
		return colorMap.get(getIndex(v));
	}

	public static final Color fromDouble(double v) {
		// inf = 255 255 0 yellow
		// 1 = 0 255 0 green
		// 0 = 0 0 0 black
		// -1 = 255 0 0 red
		// -inf = 255 0 255 magenta
		// nan = 0 255 255 cyan
		if (v == Double.MIN_VALUE || Double.isNaN(v))
			return (Color.MAGENTA);
		else if (Double.isInfinite(v))
			return (Color.CYAN);
		else if (v > 1.0)
			return (ColorMap.colorGreenToYellow[(int) (255.0 * Math.tanh((v - 1.0) / 10.0))]);
		else if (v > 0.0)
			return (ColorMap.colorBlackToGreen[(int) (255.0 * v)]);
		else if (v > -1.0)
			return (ColorMap.colorRedToBlack[(int) (255.0 * (v + 1.0))]);
		else
			return (ColorMap.colorRedToMagenta[(int) (255.0 * Math.tanh((-v - 1.0) / 10.0))]);
	}

	public Color fromString(String s) {
		if (s == null)
			return Color.black;
		int hc = Math.abs(hash(s.hashCode()));
		int r = 192 + (hc % 256) / 4;
		hc = hc / 256;
		int g = 192 + (hc % 256) / 4;
		hc = hc / 256;
		int b = 192 + (hc % 256) / 4;
		return new Color(r > 255 ? 255 : r, g > 255 ? 255 : g, b > 255 ? 255 : b);
	}

	private static int hash(int h) {
		h ^= (h >>> 20) ^ (h >>> 12);
		return h ^ (h >>> 7) ^ (h >>> 4);
	}

	public Color fromObject(Object v) {
		if (v == null) {
			return Color.black;
		}
		if (v instanceof Double) {
			return fromDouble((Double) v);
		}
		return fromString(v.toString());
	}

	private int getIndex(double v) {
		int index = (int) (v * a + b);
		return index;
	}

}
