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

package org.ujmp.core.util.matrices;

import java.io.File;

import org.ujmp.core.stringmatrix.stub.AbstractDenseStringMatrix2D;
import org.ujmp.core.util.io.FileUtil;

public class TextContentMatrix extends AbstractDenseStringMatrix2D {
	private static final long serialVersionUID = 8281354495947325089L;

	private final long[] size = new long[] { 1, 1 };

	private final File file;

	private String content = null;

	public TextContentMatrix(File file) {
		super(1, 1);
		this.file = file;
	}

	public String getString(long row, long column) {
		if (content == null) {
			synchronized (this) {
				if (content == null) {
					content = FileUtil.loadToString(file);
				}
			}
		}
		return content;
	}

	public void setString(String value, long row, long column) {
	}

	public long[] getSize() {
		return size;
	}

}
