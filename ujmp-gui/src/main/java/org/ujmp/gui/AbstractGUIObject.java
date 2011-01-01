/*
 * Copyright (C) 2008-2011 by Holger Arndt
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

package org.ujmp.gui;

import javax.swing.JFrame;

import org.ujmp.core.Matrix;
import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.gui.util.FrameManager;
import org.ujmp.gui.util.UIDefaults;

public abstract class AbstractGUIObject implements GUIObject {
	private static final long serialVersionUID = -2271465024665498798L;

	public static final int X = Matrix.X;

	public static final int Y = Matrix.Y;

	public static final int Z = Matrix.Z;

	public static final int ROW = Matrix.ROW;

	public static final int COLUMN = Matrix.COLUMN;

	public static final int ALL = Matrix.ALL;

	private int modCount = 0;

	static {
		UIDefaults.setDefaults();
	}

	public AbstractGUIObject() {
	}

	public AbstractGUIObject(String label) {
		this();
		setLabel(label);
	}

	public AbstractGUIObject(String label, String description) {
		this(label);
		setDescription(description);
	}

	
	public abstract String toString();

	public void fireValueChanged() {
		modCount++;
	}

	public final JFrame showGUI() {
		return FrameManager.showFrame(this);
	}

	public String getToolTipText() {
		StringBuilder s = new StringBuilder();
		s.append("<html>");
		s.append("<table>");
		s.append("<tr>");
		s.append("<td colspan=2><h3>" + getClass().getSimpleName()
				+ "</h3></td>");
		s.append("</tr>");
		s.append("<tr>");
		s.append("<td><b>Label:</b></td>");
		s.append("<td>" + getLabel() + "</td>");
		s.append("</tr>");
		s.append("<td><b>Description:</b></td>");
		s.append("<td>" + getDescription() + "</td>");
		s.append("</tr>");
		s.append("</table>");
		s.append("</html>");
		return s.toString();
	}

	public int getModCount() {
		return modCount;
	}

}
