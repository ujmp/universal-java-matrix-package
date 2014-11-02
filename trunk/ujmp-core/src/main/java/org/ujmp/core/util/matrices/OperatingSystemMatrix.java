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

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.TimerTask;

import org.ujmp.core.Matrix;
import org.ujmp.core.objectmatrix.stub.AbstractDenseObjectMatrix2D;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.UJMPTimer;

public class OperatingSystemMatrix extends AbstractDenseObjectMatrix2D {
	private static final long serialVersionUID = 8686188406504918019L;

	private final Matrix matrix;
	private final UJMPTimer timer;
	private final OperatingSystemMXBean os;

	public OperatingSystemMatrix() {
		super(6, 1);
		setLabel("Operating System");
		setRowLabel(0, "Arch");
		setRowLabel(1, "AvailableProcessors");
		setRowLabel(2, "Name");
		setRowLabel(3, "ObjectName");
		setRowLabel(4, "SystemLoadAverage");
		setRowLabel(5, "Version");
		matrix = this;
		os = ManagementFactory.getOperatingSystemMXBean();
		timer = UJMPTimer.newInstance(this.getClass().getSimpleName());
		timer.schedule(task, 1000, 1000);
	}

	TimerTask task = new TimerTask() {

		@Override
		public void run() {
			matrix.fireValueChanged();
		}
	};

	public boolean isReadOnly() {
		return true;
	}

	@Override
	public Object getObject(long row, long column) {
		switch (MathUtil.longToInt(row)) {
		case 0:
			return os.getArch();
		case 1:
			return os.getAvailableProcessors();
		case 2:
			return os.getName();
		case 3:
			return os.getObjectName();
		case 4:
			return os.getSystemLoadAverage();
		case 5:
			return os.getVersion();
		default:
			return null;
		}
	}

	@Override
	public void setObject(Object value, long row, long column) {
	}

}
