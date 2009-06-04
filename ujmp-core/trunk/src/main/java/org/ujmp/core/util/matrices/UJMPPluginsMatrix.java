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

package org.ujmp.core.util.matrices;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.ujmp.core.stringmatrix.AbstractDenseStringMatrix2D;

public class UJMPPluginsMatrix extends AbstractDenseStringMatrix2D {
	private static final long serialVersionUID = 9076856922668700140L;

	private List<String> classes = new ArrayList<String>();

	public UJMPPluginsMatrix() {
		setLabel("UJMP Plugins");
		setColumnLabel(0, "Name");
		setColumnLabel(1, "Is Available");
		setColumnLabel(2, "Description");
		setColumnLabel(3, "Dependencies");
		setColumnLabel(4, "Status");
		addClass("ujmp-gui");
		addClass("ujmp-colt");
		addClass("ujmp-commonsmath");
		addClass("ujmp-jama");
	}

	protected void addClass(String c) {
		classes.add(c);
	}

	protected Class<?> getClass(String c) throws ClassNotFoundException {
		if (c.startsWith("ujmp")) {
			return Class.forName("org.ujmp." + c.substring(5) + ".Plugin");
		} else {
			return Class.forName("org.jdmp." + c.substring(5) + ".Plugin");
		}
	}

	@Override
	public String getString(long row, long column) {
		String c = classes.get((int) row);
		switch ((int) column) {
		case 0:
			return c;
		case 1:
			try {
				getClass(c);
				return "yes";
			} catch (Exception e) {
				return "no";
			}
		case 2:
			try {
				Class<?> cl = getClass(c);
				Object o = cl.newInstance();
				Method m = cl.getMethod("getDescription");
				String s = (String) m.invoke(o);
				return s;
			} catch (Exception e) {
				return "n/a";
			}
		case 3:
			try {
				Class<?> cl = getClass(c);
				Object o = cl.newInstance();
				Method m = cl.getMethod("getDependencies");
				String s = "" + m.invoke(o);
				return s;
			} catch (Exception e) {
				return "n/a";
			}
		case 4:
			try {
				Class<?> cl = getClass(c);
				Object o = cl.newInstance();
				Method m = cl.getMethod("getStatus");
				String s = (String) m.invoke(o);
				return s;
			} catch (Exception e) {
				return "n/a";
			}
		}
		return null;
	}

	@Override
	public void setString(String value, long row, long column) {
	}

	@Override
	public long[] getSize() {
		return new long[] { classes.size(), 5 };
	}

}
