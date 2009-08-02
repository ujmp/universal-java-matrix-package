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

package org.ujmp.gui.actions;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.gui.MatrixGUIObject;

public class AddMissingValuesAction extends MatrixAction {
	private static final long serialVersionUID = -7585669703654474086L;

	private double percentMissing = 0.0;

	public AddMissingValuesAction(JComponent c, MatrixGUIObject m, GUIObject v) {
		super(c, m, v);
		putValue(Action.NAME, "Add missing values...");
		putValue(Action.SHORT_DESCRIPTION, "replaces a chosen percentage of the values with NaN");
	}

	@Override
	public Object call() throws MatrixException {
		while (!(percentMissing > 0.0 && percentMissing < 1.0)) {
			String s = JOptionPane.showInputDialog("Percent missing:", "");
			try {
				percentMissing = Double.parseDouble(s);
			} catch (Exception ex) {
			}
		}
		getMatrixObject().getMatrix().addMissing(Ret.ORIG, ALL, percentMissing);
		return getMatrixObject();
	}
}
