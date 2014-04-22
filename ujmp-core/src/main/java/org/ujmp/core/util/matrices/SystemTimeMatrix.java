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

package org.ujmp.core.util.matrices;

import java.util.TimerTask;

import org.ujmp.core.DenseMatrix2D;
import org.ujmp.core.Matrix;
import org.ujmp.core.longmatrix.stub.AbstractDenseLongMatrix2D;
import org.ujmp.core.matrix.factory.DenseMatrix2DFactory;
import org.ujmp.core.util.GlobalTimer;

public class SystemTimeMatrix extends AbstractDenseLongMatrix2D {
	private static final long serialVersionUID = 8552917654861598011L;

	private final Matrix matrix;

	public SystemTimeMatrix() {
		super(new long[] { 1, 1 });
		setLabel("System Time");
		setColumnLabel(0, "System.currentTimeMillis()");
		matrix = this;
		GlobalTimer.getInstance().schedule(task, 1000, 1000);
	}

	TimerTask task = new TimerTask() {

		@Override
		public void run() {
			matrix.notifyGUIObject();
		}
	};

	public long[] getSize() {
		return new long[] { 1, 1 };
	}

	public long getLong(long row, long column) {
		return System.currentTimeMillis();
	}

	public void setLong(long value, long row, long column) {
	}

	public long getLong(int row, int column) {
		return System.currentTimeMillis();
	}

	public void setLong(long value, int row, int column) {
	}

	public boolean isReadOnly() {
		return true;
	}

	public DenseMatrix2DFactory<? extends DenseMatrix2D> getFactory() {
		throw new RuntimeException("not implemented");
	}
}
