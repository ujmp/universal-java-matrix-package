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

package org.ujmp.gui.actions;

import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.gui.DefaultMatrixGUIObject;
import org.ujmp.gui.MatrixGUIObject;

public class MatrixActions extends ArrayList<JComponent> {
	private static final long serialVersionUID = -8960033736161810590L;

	public MatrixActions(JComponent c, MatrixGUIObject m, GUIObject v) {
		add(new JMenuItem(new CutToClipboardAction(c, m, v)));
		add(new JMenuItem(new CopyToClipboardAction(c, m, v)));
		add(new JSeparator());
		add(new JMenuItem(new ExportMatrixAction(c, m)));
		add(new JSeparator());
		add(new JMenuItem(new DuplicateAction(c, m, v)));
		add(new JMenuItem(new ConvertAction(c, m, v)));
		add(new JMenuItem(new SelectAction(c, m, v)));
		add(new JMenuItem(new DeleteAction(c, m, v)));
		add(new JSeparator());
		add(new AnnotationMenu(c, m, v));
		add(new JSeparator());
		add(new ContentMenu(c, m, v));
		add(new TransformMenu(c, m, v));
		add(new JSeparator());
		add(new TrigonometricMenu(c, m, v));
		add(new HyperbolicMenu(c, m, v));
		add(new JSeparator());
		add(new StatisticsMenu(c, m, v));
		add(new DecompositionMenu(c, m, v));
		add(new MissingValuesMenu(c, m, v));
		add(new JSeparator());
		add(new TextMenu(c, m, v));
	}

	class ContentMenu extends JMenu {
		private static final long serialVersionUID = 6816708899205796125L;

		public ContentMenu(JComponent c, MatrixGUIObject m, GUIObject v) {
			super("Set Content");
			add(new JMenuItem(new ClearMatrixAction(c, m, v)));
			add(new JSeparator());
			add(new JMenuItem(new EyeAction(c, m, v)));
			add(new JMenuItem(new OnesAction(c, m, v)));
			add(new JMenuItem(new ZerosAction(c, m, v)));
			add(new JMenuItem(new FillAction(c, m, v)));
			add(new JMenuItem(new RandAction(c, m, v)));
			add(new JMenuItem(new RandnAction(c, m, v)));
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
			add(new JMenuItem(new StandardDeviationAction(c, m, v)));
			add(new JMenuItem(new UniqueValueCountAction(c, m, v)));
			add(new JSeparator());
			add(new JMenuItem(new DiffAction(c, m, v)));
			add(new JMenuItem(new ProdAction(c, m, v)));
			add(new JMenuItem(new CumSumAction(c, m, v)));
			add(new JMenuItem(new CumProdAction(c, m, v)));
			add(new JSeparator());
			add(new JMenuItem(new CovarianceAction(c, m, v)));
			add(new JMenuItem(new CorrcoefAction(c, m, v)));
			add(new JMenuItem(new MutualinfAction(c, m, v)));
			add(new JMenuItem(new PairedTTestAction(c, m, v)));
		}
	}

	class TransformMenu extends JMenu {
		private static final long serialVersionUID = 883946296012278076L;

		public TransformMenu(JComponent c, MatrixGUIObject m, GUIObject v) {
			super("Transform");
			add(new JMenuItem(new TransposeAction(c, m, v)));
			add(new JMenuItem(new ReshapeAction(c, m, v)));
			add(new JMenuItem(new SqueezeAction(c, m, v)));
			add(new JMenuItem(new FlipdimAction(c, m, v)));
			add(new JSeparator());
			add(new JMenuItem(new PlusAction(c, m, v)));
			add(new JMenuItem(new MinusAction(c, m, v)));
			add(new JMenuItem(new MultiplyAction(c, m, v)));
			add(new JMenuItem(new DivideAction(c, m, v)));
			add(new JSeparator());
			add(new JMenuItem(new PowerAction(c, m, v)));
			add(new JMenuItem(new SqrtAction(c, m, v)));
			add(new JMenuItem(new SignAction(c, m, v)));
			add(new JMenuItem(new ExpAction(c, m, v)));
			add(new JMenuItem(new LogAction(c, m, v)));
			add(new JMenuItem(new Log10Action(c, m, v)));
			add(new JMenuItem(new Log2Action(c, m, v)));
			add(new JSeparator());
			add(new JMenuItem(new SortrowsAction(c, m, v)));
			add(new JMenuItem(new ShuffleAction(c, m, v)));
			add(new JMenuItem(new UniqueAction(c, m, v)));
			add(new JMenuItem(new BootstrapAction(c, m, v)));
			add(new JSeparator());
			add(new JMenuItem(new CenterAction(c, m, v)));
			add(new JMenuItem(new StandardizeAction(c, m, v)));
			add(new JMenuItem(new NormalizeAction(c, m, v)));
			add(new JMenuItem(new FadeInAction(c, m, v)));
			add(new JMenuItem(new FadeOutAction(c, m, v)));
			add(new JSeparator());
			add(new JMenuItem(new AbsAction(c, m, v)));
			add(new JMenuItem(new RoundAction(c, m, v)));
			add(new JMenuItem(new CeilAction(c, m, v)));
			add(new JMenuItem(new FloorAction(c, m, v)));
			add(new JSeparator());
			add(new JMenuItem(new DiscretizeAction(c, m, v)));
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
			add(new JMenuItem(new GinvAction(c, m, v)));
			add(new JSeparator());
			add(new JMenuItem(new CholAction(c, m, v)));
			add(new JMenuItem(new EigAction(c, m, v)));
			add(new JMenuItem(new LUAction(c, m, v)));
			add(new JMenuItem(new QRAction(c, m, v)));
			add(new JMenuItem(new SVDAction(c, m, v)));
			add(new JSeparator());
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

	class TrigonometricMenu extends JMenu {
		private static final long serialVersionUID = 5800051912623058943L;

		public TrigonometricMenu(JComponent c, MatrixGUIObject m, GUIObject v) {
			super("Trigonometric");
			add(new JMenuItem(new SinAction(c, m, v)));
			add(new JMenuItem(new CosAction(c, m, v)));
			add(new JMenuItem(new TanAction(c, m, v)));
		}
	}

	class HyperbolicMenu extends JMenu {
		private static final long serialVersionUID = 4420801549072051385L;

		public HyperbolicMenu(JComponent c, MatrixGUIObject m, GUIObject v) {
			super("Hyperbolic");
			add(new JMenuItem(new SinhAction(c, m, v)));
			add(new JMenuItem(new CoshAction(c, m, v)));
			add(new JMenuItem(new TanhAction(c, m, v)));
		}
	}

	class TextMenu extends JMenu {
		private static final long serialVersionUID = 3061912494435216531L;

		public TextMenu(JComponent c, MatrixGUIObject m, GUIObject v) {
			super("Text");
			add(new JMenuItem(new LowerCaseAction(c, m, v)));
			add(new JMenuItem(new UpperCaseAction(c, m, v)));
			add(new JMenuItem(new ReplaceRegexAction(c, m, v)));
			add(new JMenuItem(new RemovePunctuationAction(c, m, v)));
			add(new JMenuItem(new RemoveStopWordsAction(c, m, v)));
			add(new JMenuItem(new TfIdfAction(c, m, v)));
		}
	}
}
