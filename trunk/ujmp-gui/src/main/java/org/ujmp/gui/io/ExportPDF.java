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

package org.ujmp.gui.io;

import java.awt.Component;
import java.io.File;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ujmp.core.util.io.FileSelector;

public class ExportPDF {

	private static final Logger logger = Logger.getLogger(ExportPDF.class.getName());

	public static final File selectFile() {
		return selectFile(null);
	}

	public static final File selectFile(Component c) {
		return FileSelector.selectFile(c, "PDF Files", ".pdf");
	}

	public static final void save(File file, Component component) {
		try {
			Class<?> c = Class.forName("org.ujmp.itext.ExportPDF");
			Method method = c.getMethod("save", new Class[] { File.class, Component.class });
			method.invoke(null, new Object[] { file, component });
		} catch (Exception e) {
			logger.log(Level.WARNING, "cannot show frame", e);
		}
	}

	public static boolean isSupported() {
		try {
			Class<?> c = Class.forName("org.ujmp.itext.ExportPDF");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
