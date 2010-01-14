/*
 * Copyright (C) 2008-2010 by Holger Arndt
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

package org.ujmp.gui.statusbar;

import java.text.NumberFormat;

import javax.swing.JLabel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.gui.MatrixGUIObject;

public class MatrixStatisticsBar extends JLabel implements TableModelListener,
		ListSelectionListener {
	private static final long serialVersionUID = 3434122072016632576L;

	private MatrixGUIObject matrixGUIObject = null;

	private UpdateThread updateThread = null;

	public MatrixStatisticsBar(MatrixGUIObject m) {
		this.matrixGUIObject = m;
		m.addTableModelListener(this);
		m.getRowSelectionModel().addListSelectionListener(this);
		m.getColumnSelectionModel().addListSelectionListener(this);
		update();
	}

	public void update() {
		if (updateThread != null) {
			updateThread.interrupt();
		}
		updateThread = new UpdateThread(matrixGUIObject, this);
		updateThread.start();
	}

	public void tableChanged(TableModelEvent e) {
		update();
	}

	public void valueChanged(ListSelectionEvent e) {
		update();
	}

}

class UpdateThread extends Thread {

	private MatrixGUIObject matrixGUIObject = null;

	private JLabel jLabel = null;

	private static final NumberFormat nf = NumberFormat.getNumberInstance();

	static {
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
	}

	public UpdateThread(MatrixGUIObject matrixGUIObject, JLabel jLabel) {
		this.matrixGUIObject = matrixGUIObject;
		this.jLabel = jLabel;
		this.setPriority(Thread.MIN_PRIORITY);
	}

	public void run() {
		jLabel.setText("calculating statistics...");

		long colMin = matrixGUIObject.getColumnSelectionModel()
				.getMinSelectionIndex();
		long colMax = matrixGUIObject.getColumnSelectionModel()
				.getMaxSelectionIndex();
		long rowMin = matrixGUIObject.getRowSelectionModel()
				.getMinSelectionIndex();
		long rowMax = matrixGUIObject.getRowSelectionModel()
				.getMaxSelectionIndex();

		Matrix m = null;

		if (colMin < 0 || colMax < 0 || rowMin < 0 || rowMax < 0) {
			m = matrixGUIObject.getMatrix();
		} else {
			m = matrixGUIObject.getMatrix().subMatrix(Ret.LINK, rowMin, colMin,
					rowMax, colMax);
		}

		long count = m.getValueCount();
		double min = m.getMinValue();
		double max = m.getMaxValue();
		double mean = m.getMeanValue();
		double std = m.getStdValue();
		double sum = m.getValueSum();

		StringBuffer s = new StringBuffer();
		s.append(count + " cells selected:");
		s.append(" min=" + nf.format(min));
		s.append(" max=" + nf.format(max));
		s.append(" mean=" + nf.format(mean));
		s.append(" std=" + nf.format(std));
		s.append(" sum=" + nf.format(sum));
		jLabel.setText(s.toString());
	}
}
