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

package org.ujmp.gui.util;

import org.ujmp.core.Matrix;
import org.ujmp.core.coordinates.Coordinates;

public abstract class TooltipUtil {

	public static String getTooltip(Matrix matrix, long... coordinates) {
		String toolTip = "<html><b>[" + Coordinates.toString(coordinates)
				+ "]</b>";

		if (coordinates[0] == -1) {
			System.out.println();
		}

		String columnLabel = matrix.getColumnLabel(coordinates[Matrix.COLUMN]);
		if (columnLabel != null) {
			toolTip += " <b>(" + columnLabel + ")</b>";
		}

		toolTip += "<br><br>";

		toolTip += "<table cellpadding=1 cellspacing=1>";

		toolTip += "<tr>";
		toolTip += "<td>";
		toolTip += "<b>Object:</b>";
		toolTip += "</td>";
		toolTip += "<td>";
		Object o = matrix.getAsObject(coordinates);
		if (o != null) {
			toolTip += o.getClass();
		} else {
			toolTip += "[null]";
		}
		toolTip += "</td>";
		toolTip += "</tr>";

		toolTip += "<tr>";
		toolTip += "<td>";
		toolTip += "<b>String:</b>";
		toolTip += "</td>";
		toolTip += "<td>";
		String s = matrix.getAsString(coordinates);
		if (s != null && s.length() > 25) {
			s = s.substring(0, 25);
		}
		toolTip += s;
		toolTip += "</td>";
		toolTip += "</tr>";

		toolTip += "<tr>";
		toolTip += "<td>";
		toolTip += "<b>Double:</b>";
		toolTip += "</td>";
		toolTip += "<td>";
		toolTip += matrix.getAsDouble(coordinates);
		toolTip += "</td>";
		toolTip += "</tr>";

		toolTip += "<tr>";
		toolTip += "<td>";
		toolTip += "<b>Float:</b>";
		toolTip += "</td>";
		toolTip += "<td>";
		toolTip += matrix.getAsFloat(coordinates);
		toolTip += "</td>";
		toolTip += "</tr>";

		toolTip += "<tr>";
		toolTip += "<td>";
		toolTip += "<b>Long:</b>";
		toolTip += "</td>";
		toolTip += "<td>";
		toolTip += matrix.getAsLong(coordinates);
		toolTip += "</td>";
		toolTip += "</tr>";

		toolTip += "<tr>";
		toolTip += "<td>";
		toolTip += "<b>Short:</b>";
		toolTip += "</td>";
		toolTip += "<td>";
		toolTip += matrix.getAsShort(coordinates);
		toolTip += "</td>";
		toolTip += "</tr>";

		toolTip += "<tr>";
		toolTip += "<td>";
		toolTip += "<b>Int:</b>";
		toolTip += "</td>";
		toolTip += "<td>";
		toolTip += matrix.getAsInt(coordinates);
		toolTip += "</td>";
		toolTip += "</tr>";

		toolTip += "<tr>";
		toolTip += "<td>";
		toolTip += "<b>Byte:</b>";
		toolTip += "</td>";
		toolTip += "<td>";
		toolTip += matrix.getAsByte(coordinates);
		toolTip += "</td>";
		toolTip += "</tr>";

		toolTip += "<tr>";
		toolTip += "<td>";
		toolTip += "<b>Char:</b>";
		toolTip += "</td>";
		toolTip += "<td>";
		toolTip += matrix.getAsChar(coordinates);
		toolTip += "</td>";
		toolTip += "</tr>";

		toolTip += "<tr>";
		toolTip += "<td>";
		toolTip += "<b>Boolean:</b>";
		toolTip += "</td>";
		toolTip += "<td>";
		toolTip += matrix.getAsBoolean(coordinates);
		toolTip += "</td>";
		toolTip += "</tr>";

		toolTip += "<tr>";
		toolTip += "<td>";
		toolTip += "<b>Date:</b>";
		toolTip += "</td>";
		toolTip += "<td>";
		toolTip += matrix.getAsDate(coordinates);
		toolTip += "</td>";
		toolTip += "</tr>";

		toolTip += "</table>";

		toolTip += "</html>";
		return toolTip;
	}

}
