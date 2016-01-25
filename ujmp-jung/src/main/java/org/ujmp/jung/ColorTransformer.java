/*
 * Copyright (C) 2008-2016 by Holger Arndt
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

package org.ujmp.jung;

import java.awt.Paint;

import org.apache.commons.collections15.Transformer;
import org.ujmp.core.util.ColorUtil;
import org.ujmp.gui.util.UIDefaults;

import edu.uci.ics.jung.visualization.picking.PickedState;

public class ColorTransformer<T> implements Transformer<T, Paint> {

	private PickedState<T> pickedState = null;

	public ColorTransformer(PickedState<T> pickedState) {
		this.pickedState = pickedState;
	}

	public ColorTransformer() {
	}

	public Paint transform(T input) {
		if (pickedState == null) {
			return ColorUtil.fromObject(input, 100);
		} else if (pickedState.isPicked(input)) {
			return UIDefaults.SELECTEDCOLOR;
		} else {
			return ColorUtil.fromObject(input, 100);
		}
	}

}
