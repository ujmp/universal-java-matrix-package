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

package org.ujmp.gui.matrix;

import java.util.TimerTask;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.AbstractDenseDoubleMatrix2D;
import org.ujmp.gui.util.GlobalTimer;

public class MatrixMemoryUsage extends AbstractDenseDoubleMatrix2D {
	private static final long serialVersionUID = -3863745960302379726L;

	private static Matrix matrix = null;

	private MatrixMemoryUsage() {
		GlobalTimer.getInstance().schedule(new TimerTask() {

			@Override
			public void run() {
				matrix.notifyGUIObject();

			}
		}, 1000, 1000);
	}

	public static Matrix getInstance() {
		if (matrix == null) {
			matrix = new MatrixMemoryUsage();
			matrix.setLabel("Memory Usage");
		}
		return matrix;
	}

	public double getDouble(long row, long column) {
		switch ((int) row) {
		case 0:
			return Runtime.getRuntime().freeMemory();
		case 1:
			return Runtime.getRuntime().maxMemory();
		default:
			return Runtime.getRuntime().totalMemory();
		}
	}

	public long[] getSize() {
		return new long[] { 3, 1 };
	}

	public void setDouble(double value, long row, long column) {
	}

	@Override
	public boolean isReadOnly() {
		return true;
	}

}
