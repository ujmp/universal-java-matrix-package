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

package org.ujmp.core.util;

import java.util.Collection;

import org.ujmp.core.interfaces.HasDependencies;
import org.ujmp.core.interfaces.HasDescription;
import org.ujmp.core.interfaces.HasStatus;

public abstract class AbstractPlugin implements HasDescription, HasDependencies, HasStatus {

	public abstract Collection<String> getNeededClasses();

	
	public String getStatus() {
		for (String s : getNeededClasses()) {
			try {
				Class.forName(s);
			} catch (ClassNotFoundException e) {
				return "Error: Class " + s + " not found";
			}
		}
		return "ok";
	}

	public boolean isAvailable() {
		return "ok".equals(getStatus());
	}

	public final void setDescription(String description) {
	}
}
