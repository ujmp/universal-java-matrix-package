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

package org.ujmp.gui.table;

import javax.swing.event.ListSelectionEvent;

public class ListSelectionEvent64 extends ListSelectionEvent {
	private static final long serialVersionUID = 2401495244611675315L;

	private long firstIndex;
	private long lastIndex;

	public ListSelectionEvent64(Object source, long firstIndex, long lastIndex, boolean isAdjusting) {
		super(source, (int) firstIndex, (int) lastIndex, isAdjusting);
		this.firstIndex = firstIndex;
		this.lastIndex = lastIndex;
	}

	public long getFirstIndex64() {
		return firstIndex;
	}

	public long getLastIndex64() {
		return lastIndex;
	}

	public String toString() {
		String properties = " source=" + getSource() + " firstIndex= " + firstIndex + " lastIndex= " + lastIndex
				+ " isAdjusting= " + this.getValueIsAdjusting() + " ";
		return getClass().getName() + "[" + properties + "]";
	}
}
