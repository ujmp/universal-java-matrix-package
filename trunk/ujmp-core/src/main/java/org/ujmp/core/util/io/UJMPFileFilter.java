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

package org.ujmp.core.util.io;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class UJMPFileFilter extends FileFilter {

	private String label = null;

	private String[] suffix = null;

	public UJMPFileFilter(String label, String... suffix) {
		this.label = label;
		this.suffix = suffix;
	}

	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}

		for (String s : suffix) {
			if (f.getName().toLowerCase().endsWith(s)) {
				return true;
			}
		}
		return false;
	}

	public String getDescription() {
		return toString();
	}

	public String[] getSuffix() {
		return suffix;
	}

	public String toString() {
		return label + " (" + suffix[0] + ")";
	}

}
