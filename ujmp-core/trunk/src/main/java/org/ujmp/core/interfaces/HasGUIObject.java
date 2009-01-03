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

package org.ujmp.core.interfaces;

import javax.swing.JFrame;

/**
 * Declares, that an object suitable for displaying on the screen with
 * listeners, icon, etc. can be returned.
 * 
 * @see GUIObject
 * @author Holger Arndt
 * 
 */
public interface HasGUIObject {

	/**
	 * Returns an object suitable for displaying on the screen. Ensures that,
	 * for each basic object, only one GUIObject can be returned.
	 * 
	 * @return object suitable for displaying on the screen
	 */
	public GUIObject getGUIObject();

	/**
	 * This method is used to signal changes in the object to the corresponding
	 * GUIObject if it exists.
	 * <p>
	 * Maybe this method can be deleted, when all changes are made trough the
	 * GUIObject?
	 * 
	 */
	public void notifyGUIObject();

	/**
	 * This method will show the object in a JFrame on the screen.
	 * 
	 */
	public JFrame showGUI();

}
