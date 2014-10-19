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

package org.ujmp.core.util;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import org.ujmp.core.interfaces.HasDependencies;
import org.ujmp.core.interfaces.HasDescription;
import org.ujmp.core.interfaces.HasStatus;

public abstract class AbstractPlugin implements HasDescription, HasDependencies, HasStatus {

	protected final Set<String> dependencies = new TreeSet<String>();
	protected final Set<String> neededClasses = new TreeSet<String>();

	private final String decription;

	public AbstractPlugin(String description) {
		this.decription = description;
	}

	public String getStatus() {
		for (String s : getNeededClasses()) {
			try {
				Class.forName(s);
			} catch (ClassNotFoundException e) {
				return "Error: Class " + s + " not found";
			} catch (Throwable e) {
				return "Error: Class " + s + " has error: " + e;
			}
		}
		return "ok";
	}

	public boolean isAvailable() {
		return "ok".equals(getStatus());
	}

	public final void setDescription(String description) {
	}

	public final String getDescription() {
		return decription;
	}

	public final Collection<String> getNeededClasses() {
		return neededClasses;
	}

	public final Collection<String> getDependencies() {
		return dependencies;
	}
}
