/*
 * Copyright (C) 2008-2013 by Holger Arndt
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
import javax.swing.JOptionPane;

import org.ujmp.core.interfaces.GUIObject;

public class SetLabelAction extends ObjectAction {
	private static final long serialVersionUID = 8660922548207382801L;

	public SetLabelAction(JComponent c, GUIObject o) {
		super(c, o);
		putValue(Action.NAME, "Set Label...");
		putValue(Action.SHORT_DESCRIPTION, "Change the name of this object");
	}

	
	public Object call() {
		String label = JOptionPane.showInputDialog("Enter the new Label for this Object:", getGUIObject().getLabel());
		getGUIObject().setLabel(label);
		return null;
	}

}
