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

package org.ujmp.core.util.io;

import java.awt.Component;
import java.io.File;

import javax.swing.JFileChooser;

public abstract class FileSelector {

	public static final File selectFile(String label, String suffix) {
		return selectFile(null, label, suffix);
	}

	public static final File selectFile(String suffix) {
		return selectFile(null, "*." + suffix, suffix);
	}

	public static final File selectFile(Component c, String label, String suffix) {
		JFileChooser chooser = new JFileChooser();
		UJMPFileFilter filter = new UJMPFileFilter(label, suffix);
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(c);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			if (!file.getAbsolutePath().toLowerCase().endsWith(suffix))
				file = new File(file.getAbsolutePath() + suffix);
			return file;
		}
		return null;
	}

}
