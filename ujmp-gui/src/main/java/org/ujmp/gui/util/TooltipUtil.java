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

package org.ujmp.gui.util;

import java.util.Date;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.StringUtil;
import org.ujmp.gui.MatrixGUIObject;

public abstract class TooltipUtil {

	public static String getTooltip(MatrixGUIObject matrix, long... coordinates) {
		StringBuilder toolTip = new StringBuilder(200);
		toolTip.append("<html><b>");
		toolTip.append(Coordinates.toString(coordinates));
		toolTip.append("</b>");

		String columnLabel = null;
		try {
			columnLabel = matrix.getColumnName(coordinates[Matrix.COLUMN]);
		} catch (Exception e) {
		}
		if (columnLabel != null) {
			toolTip.append(" <b>(");
			toolTip.append(columnLabel);
			toolTip.append(")</b>");
		}

		toolTip.append("<br><br>");

		toolTip.append("<table cellpadding=1 cellspacing=1>");

		toolTip.append("<tr>");
		toolTip.append("<td>");
		toolTip.append("<b>Object:</b>");
		toolTip.append("</td>");
		toolTip.append("<td>");

		Object o = null;
		try {
			o = matrix.getValueAt(coordinates[0], coordinates[1]);
		} catch (Exception e) {
		}

		if (o != null) {
			toolTip.append(o.getClass());
		} else {
			toolTip.append("[null]");
		}
		toolTip.append("</td>");
		toolTip.append("</tr>");

		toolTip.append("<tr>");
		toolTip.append("<td>");
		toolTip.append("<b>String:</b>");
		toolTip.append("</td>");
		toolTip.append("<td>");
		try {
			String s = StringUtil.getString(o);
			if (s != null && s.length() > 25) {
				s = s.substring(0, 25);
			}
			toolTip.append(s);
		} catch (Exception e) {
		}
		toolTip.append("</td>");
		toolTip.append("</tr>");

		toolTip.append("<tr>");
		toolTip.append("<td>");
		toolTip.append("<b>Double:</b>");
		toolTip.append("</td>");
		toolTip.append("<td>");
		if (o instanceof Matrix) {
			toolTip.append(Double.NaN);
		} else {
			toolTip.append(MathUtil.getDouble(o));
		}
		toolTip.append("</td>");
		toolTip.append("</tr>");

		toolTip.append("<tr>");
		toolTip.append("<td>");
		toolTip.append("<b>Float:</b>");
		toolTip.append("</td>");
		toolTip.append("<td>");
		if (o instanceof Matrix) {
			toolTip.append(Float.NaN);
		} else {
			toolTip.append(MathUtil.getFloat(o));
		}
		toolTip.append("</td>");
		toolTip.append("</tr>");

		toolTip.append("<tr>");
		toolTip.append("<td>");
		toolTip.append("<b>Long:</b>");
		toolTip.append("</td>");
		toolTip.append("<td>");
		if (o instanceof Matrix) {
			toolTip.append("0");
		} else {
			toolTip.append(MathUtil.getLong(o));
		}
		toolTip.append("</td>");
		toolTip.append("</tr>");

		toolTip.append("<tr>");
		toolTip.append("<td>");
		toolTip.append("<b>Short:</b>");
		toolTip.append("</td>");
		toolTip.append("<td>");
		if (o instanceof Matrix) {
			toolTip.append("0");
		} else {
			toolTip.append(MathUtil.getShort(o));
		}
		toolTip.append("</td>");
		toolTip.append("</tr>");

		toolTip.append("<tr>");
		toolTip.append("<td>");
		toolTip.append("<b>Int:</b>");
		toolTip.append("</td>");
		toolTip.append("<td>");
		if (o instanceof Matrix) {
			toolTip.append("0");
		} else {
			toolTip.append(MathUtil.getInt(o));
		}
		toolTip.append("</td>");
		toolTip.append("</tr>");

		toolTip.append("<tr>");
		toolTip.append("<td>");
		toolTip.append("<b>Byte:</b>");
		toolTip.append("</td>");
		toolTip.append("<td>");
		if (o instanceof Matrix) {
			toolTip.append("0");
		} else {
			toolTip.append(MathUtil.getByte(o));
		}
		toolTip.append("</td>");
		toolTip.append("</tr>");

		toolTip.append("<tr>");
		toolTip.append("<td>");
		toolTip.append("<b>Char:</b>");
		toolTip.append("</td>");
		toolTip.append("<td>");
		if (o instanceof Matrix) {
		} else {
			toolTip.append(MathUtil.getChar(o));
		}
		toolTip.append("</td>");
		toolTip.append("</tr>");

		toolTip.append("<tr>");
		toolTip.append("<td>");
		toolTip.append("<b>Boolean:</b>");
		toolTip.append("</td>");
		toolTip.append("<td>");
		if (o instanceof Matrix) {
			toolTip.append("false");
		} else {
			toolTip.append(MathUtil.getBoolean(o));
		}
		toolTip.append("</td>");
		toolTip.append("</tr>");

		toolTip.append("<tr>");
		toolTip.append("<td>");
		toolTip.append("<b>Date:</b>");
		toolTip.append("</td>");
		toolTip.append("<td>");
		if (o instanceof Matrix) {
			toolTip.append(MathUtil.getDate(null));
		} else if (o instanceof Long) {
			toolTip.append(MathUtil.getDate(o));
		} else if (o instanceof String) {
			toolTip.append(MathUtil.getDate(o));
		} else if (o instanceof Date) {
			toolTip.append(MathUtil.getDate(o));
		}
		toolTip.append("</td>");
		toolTip.append("</tr>");

		toolTip.append("</table>");

		toolTip.append("</html>");
		return toolTip.toString();
	}
}
