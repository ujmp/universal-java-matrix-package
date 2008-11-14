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

package org.ujmp.gui.util;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuItem;

import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.gui.MatrixGUIObject;
import org.ujmp.gui.actions.ObjectAction;
import org.ujmp.gui.frame.AbstractFrame;
import org.ujmp.gui.matrix.MatrixFrame;

public abstract class FrameManager {

	protected static final Map<GUIObject, AbstractFrame> frames = new HashMap<GUIObject, AbstractFrame>();

	protected static List<JComponent> actions = null;

	static {
		UIDefaults.setDefaults();
	}

	public static final JFrame showFrame(GUIObject o) {
		if (o instanceof MatrixGUIObject) {
			return showFrame((MatrixGUIObject) o);
		} else {
			try {
				Class<?> c = Class.forName("org.jdmp.gui.util.JDMPFrameManager");
				Method method = c.getMethod("showFrame", new Class[] { GUIObject.class });
				Object f = method.invoke(null, new Object[] { o });
				return (JFrame) f;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	public static final Collection<AbstractFrame> getFrameList() {
		return frames.values();
	}

	public static final Map<GUIObject, AbstractFrame> getFrames() {
		return frames;
	}

	public static final JFrame showFrame(MatrixGUIObject m) {
		try {
			AbstractFrame frame = frames.get(m);
			if (frame == null) {
				frame = new MatrixFrame(m);
				frames.put(m, frame);
			}
			frame.setVisible(true);
			return frame;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static final void hideFrame(GUIObject m) {
		AbstractFrame frame = frames.get(m);
		if (frame != null) {
			frame.setVisible(false);
		}
	}

	public static final void closeFrame(GUIObject m) {
		AbstractFrame frame = frames.get(m);
		if (frame != null) {
			frame.setVisible(false);
			frame.dispose();
			frame = null;
			frames.put(m, null);
		}
	}

	public static final void registerFrame(AbstractFrame frame) {
		frames.put(frame.getObject(), frame);
	}

	public static final Collection<JComponent> getActions() {
		actions = new LinkedList<JComponent>();
		for (AbstractFrame frame : frames.values()) {
			actions.add(new JMenuItem(new UJMPFrameAction(frame)));
		}
		return actions;
	}
}

class UJMPFrameAction extends ObjectAction {
	private static final long serialVersionUID = 6482324784226471840L;

	public UJMPFrameAction(AbstractFrame frame) {
		super(null, frame.getObject());
		GUIObject o = frame.getObject();
		putValue(Action.NAME, o.getClass().getSimpleName() + " " + o.getLabel());
		if (frame.isVisible()) {
			putValue(Action.SHORT_DESCRIPTION, "Show " + frame.getObject().getLabel());
		} else {
			putValue(Action.SHORT_DESCRIPTION, "Hide " + frame.getObject().getLabel());
		}
	}

	@Override
	public Object call() {
		return null;
	}

}
