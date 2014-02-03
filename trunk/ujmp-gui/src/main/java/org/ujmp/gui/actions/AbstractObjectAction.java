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

package org.ujmp.gui.actions;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.event.SwingPropertyChangeSupport;

import org.ujmp.core.Matrix;
import org.ujmp.core.interfaces.CoreObject;
import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.gui.util.TaskQueue;

public abstract class AbstractObjectAction implements Action, Callable<Object>,
		Serializable {
	private static final long serialVersionUID = -118767390543995981L;

	public static final int ROW = Matrix.ROW;

	public static final int COLUMN = Matrix.COLUMN;

	public static final int ALL = Matrix.ALL;

	private transient GUIObject object = null;

	private transient JComponent component = null;

	private transient Icon icon = null;

	private boolean enabled = true;

	protected transient SwingPropertyChangeSupport changeSupport;

	private transient final HashMap<String, Object> arrayTable = new HashMap<String, Object>();

	public AbstractObjectAction(JComponent c, GUIObject o) {
		setGUIObject(o);
		this.component = c;
		icon = UIManager.getIcon("UJMP.icon." + getClass().getSimpleName());
		// putValue(Action.MNEMONIC_KEY, UIManager.get("UJMP.mnemonicKey." +
		// getClass().getName()));
		// putValue(Action.ACCELERATOR_KEY,
		// UIManager.get("UJMP.acceleratorKey." + getClass().getName()));
	}

	public final void setComponent(JComponent component) {
		this.component = component;
	}

	public final void setStatus(String status) {
		TaskQueue.setStatus(status);
	}

	public final void setProgress(double progress) {
		TaskQueue.setProgress(progress);
	}

	public final String toString() {
		return (String) getValue(Action.NAME) + " ("
				+ getValue(Action.SHORT_DESCRIPTION) + ")";
	}

	public final GUIObject getGUIObject() {
		return object;
	}

	public final CoreObject getCoreObject() {
		if (object == null) {
			return null;
		} else {
			return object.getCoreObject();
		}
	}

	public final void setGUIObject(GUIObject o) {
		if (o != null) {
			this.object = o;
		}
	}

	public final void actionPerformed(ActionEvent e) {
		try {
			call();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public final Future<?> executeInBackground() {
		Future<?> f = TaskQueue.submit(this);
		return f;
	}

	public abstract Object call();

	public final JComponent getComponent() {
		return component;
	}

	public Object getValue(String key) {
		if (arrayTable == null) {
			return null;
		}

		if ("enabled".equalsIgnoreCase(key)) {
			return enabled;
		} else if (key == Action.SMALL_ICON) {
			return icon;
		}

		return arrayTable.get(key);
	}

	public void setEnabled(boolean newValue) {
		this.enabled = newValue;
	}

	public boolean isEnabled() {
		return enabled;
	}

	protected void firePropertyChange(String propertyName, Object oldValue,
			Object newValue) {
		if (changeSupport == null
				|| (oldValue != null && newValue != null && oldValue
						.equals(newValue))) {
			return;
		}
		changeSupport.firePropertyChange(propertyName, oldValue, newValue);
	}

	public void putValue(String key, Object newValue) {
		Object oldValue = null;
		if (key == "enabled") {
			if (newValue == null || !(newValue instanceof Boolean)) {
				newValue = false;
			}
			oldValue = enabled;
			enabled = (Boolean) newValue;
		} else if (key == Action.SMALL_ICON) {
			oldValue = icon;
			icon = (ImageIcon) newValue;
		} else {
			if (arrayTable.containsKey(key))
				oldValue = arrayTable.get(key);
			if (newValue == null) {
				arrayTable.remove(key);
			} else {
				arrayTable.put(key, newValue);
			}
		}
		firePropertyChange(key, oldValue, newValue);
	}

	public synchronized void addPropertyChangeListener(
			PropertyChangeListener listener) {
		if (changeSupport == null) {
			changeSupport = new SwingPropertyChangeSupport(this);
		}
		changeSupport.addPropertyChangeListener(listener);
	}

	public synchronized void removePropertyChangeListener(
			PropertyChangeListener listener) {
		if (changeSupport == null) {
			return;
		}
		changeSupport.removePropertyChangeListener(listener);
	}

}
