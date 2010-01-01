/*
 * Copyright (C) 2008-2009 by Holger Arndt
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

package org.ujmp.gui.actions;

import javax.swing.Action;
import javax.swing.JComponent;

import org.ujmp.core.Matrix;
import org.ujmp.core.interfaces.GUIObject;

public class ShowInFrameAction extends ObjectAction {
	private static final long serialVersionUID = -5025569936825456099L;

	private Class<?> guiClass = null;

	// public ShowInFrameAction(JComponent c, Matrix matrix) {
	// this(c, matrix.getGUIObject());
	// }
	//
	// public ShowInFrameAction(JComponent c, GUIObject object) {
	// super(c, object);
	// putValue(Action.NAME, object.getLabel());
	// putValue(Action.SHORT_DESCRIPTION, "Show " + object.getLabel()
	// + " in a new Window");
	// }

	public ShowInFrameAction(JComponent c, String label, Class<?> object) {
		super(c, null);
		putValue(Action.NAME, label);
		putValue(Action.SHORT_DESCRIPTION, "Show " + label + " in a new Window");
		this.guiClass = object;
	}

	public Object call() {
		if (guiClass != null && getGUIObject() == null) {
			try {
				Object o = guiClass.newInstance();
				if (o instanceof Matrix) {
					setGUIObject(((Matrix) o).getGUIObject());
				} else {
					setGUIObject((GUIObject) o);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		getGUIObject().showGUI();
		return null;
	}
}
