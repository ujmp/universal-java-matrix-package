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

package org.ujmp.core.util.io;

import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.swing.filechooser.FileFilter;

public class UJMPFileFilter extends FileFilter {

	private String label = null;

	private String suffix = null;

	public static final UJMPFileFilter PDFFilter = new UJMPFileFilter("PDF Files", ".pdf");

	public static final UJMPFileFilter PLTFilter = new UJMPFileFilter("PLT Files", ".plt");

	public static final UJMPFileFilter PNGFilter = new UJMPFileFilter("PNG Files", ".png");

	public static final UJMPFileFilter JPEGFilter = new UJMPFileFilter("JPEG Files", ".jpg");

	public static final UJMPFileFilter XLSFilter = new UJMPFileFilter("XLS Files", ".xls");

	public static final UJMPFileFilter XMLFilter = new UJMPFileFilter("XML Files", ".xls");

	public static final UJMPFileFilter MatlabFilter = new UJMPFileFilter("Matlab Files", ".m");

	public UJMPFileFilter(String label, String suffix) {
		this.label = label;
		this.suffix = suffix;
	}

	public static Collection<FileFilter> getChoosableFileFilters(Object o) {
		List<FileFilter> filters = new LinkedList<FileFilter>();
		filters.add(UJMPFileFilter.XMLFilter);
		filters.add(UJMPFileFilter.MatlabFilter);
		filters.add(UJMPFileFilter.PLTFilter);
		filters.add(UJMPFileFilter.XLSFilter);
		filters.add(UJMPFileFilter.PDFFilter);
		filters.add(UJMPFileFilter.PNGFilter);
		filters.add(UJMPFileFilter.JPEGFilter);

		return filters;
	}

	@Override
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}

		if (f.getName().toLowerCase().endsWith(suffix)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String getDescription() {
		return label;
	}

	public String getSuffix() {
		return suffix;
	}

	@Override
	public String toString() {
		return label + " (" + suffix + ")";
	}

}
