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

package org.ujmp.gui.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import org.ujmp.core.interfaces.GUIObject;

public abstract class FrameManager {

	private static final Map<GUIObject, JFrame> frames = new HashMap<GUIObject, JFrame>();

	public static final JFrame showFrame(GUIObject o) {
		JFrame frame = o.getFrame();
		frames.put(o, frame);
		frame.setVisible(true);
		return frame;
	}

	public static final Collection<JFrame> getFrameList() {
		return frames.values();
	}

	public static final Map<GUIObject, JFrame> getFrames() {
		return frames;
	}

	public static final void hideFrame(GUIObject m) {
		JFrame frame = frames.get(m);
		if (frame != null) {
			frame.setVisible(false);
		}
	}

	public static final void closeFrame(GUIObject m) {
		JFrame frame = frames.get(m);
		if (frame != null) {
			frame.setVisible(false);
			frame.dispose();
			frame = null;
			frames.put(m, null);
		}
	}

	public static final void registerFrame(GUIObject object, JFrame frame) {
		frames.put(object, frame);
	}

}
