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

import java.io.File;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.core.util.io.UJMPFileFilter;

public class ExportAction extends ObjectAction {
	private static final long serialVersionUID = -3132833245167910507L;

	public ExportAction(JComponent c, GUIObject o) {
		super(c, o);
		putValue(Action.NAME, "Export...");
		putValue(Action.SHORT_DESCRIPTION, "Export to file");
	}

	@Override
	public Object call() {
		File file = null;
		JFileChooser chooser = new JFileChooser();

		for (FileFilter f : UJMPFileFilter.getChoosableFileFilters(getObject())) {
			chooser.addChoosableFileFilter(f);
		}
		for (FileFilter f : UJMPFileFilter.getChoosableFileFilters(getComponent())) {
			chooser.addChoosableFileFilter(f);
		}

		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setDialogTitle("Export");

		int returnVal = chooser.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = chooser.getSelectedFile();

			FileFilter filter = chooser.getFileFilter();

			String suffix = ((UJMPFileFilter) filter).getSuffix();
			if (!file.getAbsolutePath().toLowerCase().endsWith(suffix)) {
				file = new File(file.getAbsolutePath() + suffix);
			}
		}

		if (file == null)
			return null;

		if (file.exists()) {
			int result = JOptionPane.showConfirmDialog(null, "File already exists. Overwrite?", "Warning",
					JOptionPane.YES_NO_OPTION);
			if (result != JOptionPane.YES_OPTION)
				return null;
		}

		return null;
	}
}
