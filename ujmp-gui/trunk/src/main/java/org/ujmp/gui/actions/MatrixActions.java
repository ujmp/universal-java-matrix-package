/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
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

import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.gui.MatrixGUIObject;

public class MatrixActions extends ArrayList<JComponent> {
	private static final long serialVersionUID = -8960033736161810590L;

	public MatrixActions(JComponent c, MatrixGUIObject m, GUIObject v) {
		this.add(new JMenuItem(new ClearMatrixAction(c, m, v)));
		this.add(new JMenuItem(new ClearMatrixAction(c, m, v)));
		this.add(new JMenuItem(new AddMissingValuesAction(c, m, v)));
		this.add(new JMenuItem(new ReplaceByMeanAction(c, m, v)));
		this.add(new JMenuItem(new ReplaceByNearestNeighborAction(c, m, v)));
		this.add(new JMenuItem(new FillGaussianAction(c, m, v)));
		this.add(new JMenuItem(new FillUniformAction(c, m, v)));
		this.add(new JMenuItem(new FillWithValueAction(c, m, v)));
		this.add(new JMenuItem(new CopyMatrixAction(c, m, v)));
		this.add(new JMenuItem(new LinkMatrixAction(c, m, v)));
		this.add(new JMenuItem(new TransposeMatrixAction(c, m, v)));
		this.add(new JMenuItem(new RescaleMatrixAction(c, m, v)));
		this.add(new JMenuItem(new FadeInAction(c, m, v)));
		this.add(new JMenuItem(new FadeOutAction(c, m, v)));
		this.add(new JMenuItem(new MinAction(c, m, v)));
		this.add(new JMenuItem(new MaxAction(c, m, v)));
		this.add(new JMenuItem(new SumAction(c, m, v)));
		this.add(new JMenuItem(new MeanAction(c, m, v)));
		this.add(new JMenuItem(new VarianceAction(c, m, v)));
		this.add(new JMenuItem(new CovarianceAction(c, m, v)));
		this.add(new JMenuItem(new StandardDeviationAction(c, m, v)));
		this.add(new JMenuItem(new CenterAction(c, m, v)));
		this.add(new JMenuItem(new StandardizeAction(c, m, v)));
		this.add(new JMenuItem(new SortAction(c, m, v)));

		this.add(new JSeparator());
		this.add(new JMenuItem(new ExitAction(c, m)));
	}

}
