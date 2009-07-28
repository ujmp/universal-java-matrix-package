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

import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.gui.MatrixGUIObject;

public class MatrixActions extends ArrayList<JComponent> {
	private static final long serialVersionUID = -8960033736161810590L;

	public MatrixActions(JComponent c, MatrixGUIObject m, GUIObject v) {
		add(new JMenuItem(new DuplicateAction(c, m, v)));
		add(new JSeparator());
		add(new JMenuItem(new ExportMatrixAction(c, m)));
		add(new JSeparator());
		add(new AnnotationMenu(c, m, v));
		add(new JSeparator());
		add(new ContentMenu(c, m, v));
		add(new TransformMenu(c, m, v));
		add(new StatisticsMenu(c, m, v));
		add(new MissingValuesMenu(c, m, v));
	}

	class ContentMenu extends JMenu {
		private static final long serialVersionUID = 6816708899205796125L;

		public ContentMenu(JComponent c, MatrixGUIObject m, GUIObject v) {
			super("Set Content");
			add(new JMenuItem(new ClearMatrixAction(c, m, v)));
			add(new JSeparator());
			add(new JMenuItem(new FillGaussianAction(c, m, v)));
			add(new JMenuItem(new FillUniformAction(c, m, v)));
			add(new JMenuItem(new FillWithValueAction(c, m, v)));
		}
	}

	class StatisticsMenu extends JMenu {
		private static final long serialVersionUID = 6246586375135512937L;

		public StatisticsMenu(JComponent c, MatrixGUIObject m, GUIObject v) {
			super("Statistics");
			add(new JMenuItem(new MinAction(c, m, v)));
			add(new JMenuItem(new MaxAction(c, m, v)));
			add(new JMenuItem(new SumAction(c, m, v)));
			add(new JMenuItem(new MeanAction(c, m, v)));
			add(new JMenuItem(new VarianceAction(c, m, v)));
			add(new JMenuItem(new CovarianceAction(c, m, v)));
			add(new JMenuItem(new StandardDeviationAction(c, m, v)));
			add(new JMenuItem(new CorrcoefAction(c, m, v)));
			add(new JMenuItem(new MutualinfAction(c, m, v)));
			add(new JMenuItem(new PairedTTestAction(c, m, v)));
		}
	}

	class TransformMenu extends JMenu {
		private static final long serialVersionUID = 883946296012278076L;

		public TransformMenu(JComponent c, MatrixGUIObject m, GUIObject v) {
			super("Transform");
			add(new JMenuItem(new PlusAction(c, m, v)));
			add(new JMenuItem(new MinusAction(c, m, v)));
			add(new JMenuItem(new MultiplyAction(c, m, v)));
			add(new JMenuItem(new DivideAction(c, m, v)));
			add(new JMenuItem(new PowerAction(c, m, v)));
			add(new JSeparator());
			add(new JMenuItem(new TransposeAction(c, m, v)));
			add(new JSeparator());
			add(new JMenuItem(new SortAction(c, m, v)));
			add(new JMenuItem(new BootstrapAction(c, m, v)));
			add(new JSeparator());
			add(new JMenuItem(new CenterAction(c, m, v)));
			add(new JMenuItem(new StandardizeAction(c, m, v)));
			add(new JSeparator());
			add(new JMenuItem(new AbsAction(c, m, v)));
			add(new JMenuItem(new RoundAction(c, m, v)));
			add(new JMenuItem(new CeilAction(c, m, v)));
			add(new JMenuItem(new FloorAction(c, m, v)));
		}
	}

	class MissingValuesMenu extends JMenu {
		private static final long serialVersionUID = 4966321671368633082L;

		public MissingValuesMenu(JComponent c, MatrixGUIObject m, GUIObject v) {
			super("Missing Values");
			add(new JMenuItem(new AddMissingValuesAction(c, m, v)));
			add(new JSeparator());
			add(new JMenuItem(new ReplaceByMeanAction(c, m, v)));
			add(new JMenuItem(new ReplaceByNearestNeighborAction(c, m, v)));
		}
	}

	class DecompositionMenu extends JMenu {
		private static final long serialVersionUID = 3877560425228015085L;

		public DecompositionMenu(JComponent c, MatrixGUIObject m, GUIObject v) {
			super("Decomposition");
			add(new JMenuItem(new InvAction(c, m, v)));
			add(new JMenuItem(new PinvAction(c, m, v)));
			add(new JMenuItem(new PrincompAction(c, m, v)));
		}
	}

	class AnnotationMenu extends JMenu {
		private static final long serialVersionUID = 7243016348847260111L;

		public AnnotationMenu(JComponent c, MatrixGUIObject m, GUIObject v) {
			super("Annotation");
			add(new JMenuItem(new SetLabelAction(c, m)));
		}
	}
}
