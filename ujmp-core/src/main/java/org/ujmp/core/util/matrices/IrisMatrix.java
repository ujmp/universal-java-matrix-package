/*
 * Copyright (C) 2008-2015 by Holger Arndt
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

import org.ujmp.core.stringmatrix.stub.AbstractDenseStringMatrix2D;

public class IrisMatrix extends AbstractDenseStringMatrix2D {
	private static final long serialVersionUID = -5331566135586871374L;

	private static final String[][] data = new String[][] {
			{ "5.1", "3.5", "1.4", "0.2", "Iris-setosa" },
			{ "4.9", "3.0", "1.4", "0.2", "Iris-setosa" },
			{ "4.7", "3.2", "1.3", "0.2", "Iris-setosa" },
			{ "4.6", "3.1", "1.5", "0.2", "Iris-setosa" },
			{ "5.0", "3.6", "1.4", "0.2", "Iris-setosa" },
			{ "5.4", "3.9", "1.7", "0.4", "Iris-setosa" },
			{ "4.6", "3.4", "1.4", "0.3", "Iris-setosa" },
			{ "5.0", "3.4", "1.5", "0.2", "Iris-setosa" },
			{ "4.4", "2.9", "1.4", "0.2", "Iris-setosa" },
			{ "4.9", "3.1", "1.5", "0.1", "Iris-setosa" },
			{ "5.4", "3.7", "1.5", "0.2", "Iris-setosa" },
			{ "4.8", "3.4", "1.6", "0.2", "Iris-setosa" },
			{ "4.8", "3.0", "1.4", "0.1", "Iris-setosa" },
			{ "4.3", "3.0", "1.1", "0.1", "Iris-setosa" },
			{ "5.8", "4.0", "1.2", "0.2", "Iris-setosa" },
			{ "5.7", "4.4", "1.5", "0.4", "Iris-setosa" },
			{ "5.4", "3.9", "1.3", "0.4", "Iris-setosa" },
			{ "5.1", "3.5", "1.4", "0.3", "Iris-setosa" },
			{ "5.7", "3.8", "1.7", "0.3", "Iris-setosa" },
			{ "5.1", "3.8", "1.5", "0.3", "Iris-setosa" },
			{ "5.4", "3.4", "1.7", "0.2", "Iris-setosa" },
			{ "5.1", "3.7", "1.5", "0.4", "Iris-setosa" },
			{ "4.6", "3.6", "1.0", "0.2", "Iris-setosa" },
			{ "5.1", "3.3", "1.7", "0.5", "Iris-setosa" },
			{ "4.8", "3.4", "1.9", "0.2", "Iris-setosa" },
			{ "5.0", "3.0", "1.6", "0.2", "Iris-setosa" },
			{ "5.0", "3.4", "1.6", "0.4", "Iris-setosa" },
			{ "5.2", "3.5", "1.5", "0.2", "Iris-setosa" },
			{ "5.2", "3.4", "1.4", "0.2", "Iris-setosa" },
			{ "4.7", "3.2", "1.6", "0.2", "Iris-setosa" },
			{ "4.8", "3.1", "1.6", "0.2", "Iris-setosa" },
			{ "5.4", "3.4", "1.5", "0.4", "Iris-setosa" },
			{ "5.2", "4.1", "1.5", "0.1", "Iris-setosa" },
			{ "5.5", "4.2", "1.4", "0.2", "Iris-setosa" },
			{ "4.9", "3.1", "1.5", "0.1", "Iris-setosa" },
			{ "5.0", "3.2", "1.2", "0.2", "Iris-setosa" },
			{ "5.5", "3.5", "1.3", "0.2", "Iris-setosa" },
			{ "4.9", "3.1", "1.5", "0.1", "Iris-setosa" },
			{ "4.4", "3.0", "1.3", "0.2", "Iris-setosa" },
			{ "5.1", "3.4", "1.5", "0.2", "Iris-setosa" },
			{ "5.0", "3.5", "1.3", "0.3", "Iris-setosa" },
			{ "4.5", "2.3", "1.3", "0.3", "Iris-setosa" },
			{ "4.4", "3.2", "1.3", "0.2", "Iris-setosa" },
			{ "5.0", "3.5", "1.6", "0.6", "Iris-setosa" },
			{ "5.1", "3.8", "1.9", "0.4", "Iris-setosa" },
			{ "4.8", "3.0", "1.4", "0.3", "Iris-setosa" },
			{ "5.1", "3.8", "1.6", "0.2", "Iris-setosa" },
			{ "4.6", "3.2", "1.4", "0.2", "Iris-setosa" },
			{ "5.3", "3.7", "1.5", "0.2", "Iris-setosa" },
			{ "5.0", "3.3", "1.4", "0.2", "Iris-setosa" },
			{ "7.0", "3.2", "4.7", "1.4", "Iris-versicolor" },
			{ "6.4", "3.2", "4.5", "1.5", "Iris-versicolor" },
			{ "6.9", "3.1", "4.9", "1.5", "Iris-versicolor" },
			{ "5.5", "2.3", "4.0", "1.3", "Iris-versicolor" },
			{ "6.5", "2.8", "4.6", "1.5", "Iris-versicolor" },
			{ "5.7", "2.8", "4.5", "1.3", "Iris-versicolor" },
			{ "6.3", "3.3", "4.7", "1.6", "Iris-versicolor" },
			{ "4.9", "2.4", "3.3", "1.0", "Iris-versicolor" },
			{ "6.6", "2.9", "4.6", "1.3", "Iris-versicolor" },
			{ "5.2", "2.7", "3.9", "1.4", "Iris-versicolor" },
			{ "5.0", "2.0", "3.5", "1.0", "Iris-versicolor" },
			{ "5.9", "3.0", "4.2", "1.5", "Iris-versicolor" },
			{ "6.0", "2.2", "4.0", "1.0", "Iris-versicolor" },
			{ "6.1", "2.9", "4.7", "1.4", "Iris-versicolor" },
			{ "5.6", "2.9", "3.6", "1.3", "Iris-versicolor" },
			{ "6.7", "3.1", "4.4", "1.4", "Iris-versicolor" },
			{ "5.6", "3.0", "4.5", "1.5", "Iris-versicolor" },
			{ "5.8", "2.7", "4.1", "1.0", "Iris-versicolor" },
			{ "6.2", "2.2", "4.5", "1.5", "Iris-versicolor" },
			{ "5.6", "2.5", "3.9", "1.1", "Iris-versicolor" },
			{ "5.9", "3.2", "4.8", "1.8", "Iris-versicolor" },
			{ "6.1", "2.8", "4.0", "1.3", "Iris-versicolor" },
			{ "6.3", "2.5", "4.9", "1.5", "Iris-versicolor" },
			{ "6.1", "2.8", "4.7", "1.2", "Iris-versicolor" },
			{ "6.4", "2.9", "4.3", "1.3", "Iris-versicolor" },
			{ "6.6", "3.0", "4.4", "1.4", "Iris-versicolor" },
			{ "6.8", "2.8", "4.8", "1.4", "Iris-versicolor" },
			{ "6.7", "3.0", "5.0", "1.7", "Iris-versicolor" },
			{ "6.0", "2.9", "4.5", "1.5", "Iris-versicolor" },
			{ "5.7", "2.6", "3.5", "1.0", "Iris-versicolor" },
			{ "5.5", "2.4", "3.8", "1.1", "Iris-versicolor" },
			{ "5.5", "2.4", "3.7", "1.0", "Iris-versicolor" },
			{ "5.8", "2.7", "3.9", "1.2", "Iris-versicolor" },
			{ "6.0", "2.7", "5.1", "1.6", "Iris-versicolor" },
			{ "5.4", "3.0", "4.5", "1.5", "Iris-versicolor" },
			{ "6.0", "3.4", "4.5", "1.6", "Iris-versicolor" },
			{ "6.7", "3.1", "4.7", "1.5", "Iris-versicolor" },
			{ "6.3", "2.3", "4.4", "1.3", "Iris-versicolor" },
			{ "5.6", "3.0", "4.1", "1.3", "Iris-versicolor" },
			{ "5.5", "2.5", "4.0", "1.3", "Iris-versicolor" },
			{ "5.5", "2.6", "4.4", "1.2", "Iris-versicolor" },
			{ "6.1", "3.0", "4.6", "1.4", "Iris-versicolor" },
			{ "5.8", "2.6", "4.0", "1.2", "Iris-versicolor" },
			{ "5.0", "2.3", "3.3", "1.0", "Iris-versicolor" },
			{ "5.6", "2.7", "4.2", "1.3", "Iris-versicolor" },
			{ "5.7", "3.0", "4.2", "1.2", "Iris-versicolor" },
			{ "5.7", "2.9", "4.2", "1.3", "Iris-versicolor" },
			{ "6.2", "2.9", "4.3", "1.3", "Iris-versicolor" },
			{ "5.1", "2.5", "3.0", "1.1", "Iris-versicolor" },
			{ "5.7", "2.8", "4.1", "1.3", "Iris-versicolor" },
			{ "6.3", "3.3", "6.0", "2.5", "Iris-virginica" },
			{ "5.8", "2.7", "5.1", "1.9", "Iris-virginica" },
			{ "7.1", "3.0", "5.9", "2.1", "Iris-virginica" },
			{ "6.3", "2.9", "5.6", "1.8", "Iris-virginica" },
			{ "6.5", "3.0", "5.8", "2.2", "Iris-virginica" },
			{ "7.6", "3.0", "6.6", "2.1", "Iris-virginica" },
			{ "4.9", "2.5", "4.5", "1.7", "Iris-virginica" },
			{ "7.3", "2.9", "6.3", "1.8", "Iris-virginica" },
			{ "6.7", "2.5", "5.8", "1.8", "Iris-virginica" },
			{ "7.2", "3.6", "6.1", "2.5", "Iris-virginica" },
			{ "6.5", "3.2", "5.1", "2.0", "Iris-virginica" },
			{ "6.4", "2.7", "5.3", "1.9", "Iris-virginica" },
			{ "6.8", "3.0", "5.5", "2.1", "Iris-virginica" },
			{ "5.7", "2.5", "5.0", "2.0", "Iris-virginica" },
			{ "5.8", "2.8", "5.1", "2.4", "Iris-virginica" },
			{ "6.4", "3.2", "5.3", "2.3", "Iris-virginica" },
			{ "6.5", "3.0", "5.5", "1.8", "Iris-virginica" },
			{ "7.7", "3.8", "6.7", "2.2", "Iris-virginica" },
			{ "7.7", "2.6", "6.9", "2.3", "Iris-virginica" },
			{ "6.0", "2.2", "5.0", "1.5", "Iris-virginica" },
			{ "6.9", "3.2", "5.7", "2.3", "Iris-virginica" },
			{ "5.6", "2.8", "4.9", "2.0", "Iris-virginica" },
			{ "7.7", "2.8", "6.7", "2.0", "Iris-virginica" },
			{ "6.3", "2.7", "4.9", "1.8", "Iris-virginica" },
			{ "6.7", "3.3", "5.7", "2.1", "Iris-virginica" },
			{ "7.2", "3.2", "6.0", "1.8", "Iris-virginica" },
			{ "6.2", "2.8", "4.8", "1.8", "Iris-virginica" },
			{ "6.1", "3.0", "4.9", "1.8", "Iris-virginica" },
			{ "6.4", "2.8", "5.6", "2.1", "Iris-virginica" },
			{ "7.2", "3.0", "5.8", "1.6", "Iris-virginica" },
			{ "7.4", "2.8", "6.1", "1.9", "Iris-virginica" },
			{ "7.9", "3.8", "6.4", "2.0", "Iris-virginica" },
			{ "6.4", "2.8", "5.6", "2.2", "Iris-virginica" },
			{ "6.3", "2.8", "5.1", "1.5", "Iris-virginica" },
			{ "6.1", "2.6", "5.6", "1.4", "Iris-virginica" },
			{ "7.7", "3.0", "6.1", "2.3", "Iris-virginica" },
			{ "6.3", "3.4", "5.6", "2.4", "Iris-virginica" },
			{ "6.4", "3.1", "5.5", "1.8", "Iris-virginica" },
			{ "6.0", "3.0", "4.8", "1.8", "Iris-virginica" },
			{ "6.9", "3.1", "5.4", "2.1", "Iris-virginica" },
			{ "6.7", "3.1", "5.6", "2.4", "Iris-virginica" },
			{ "6.9", "3.1", "5.1", "2.3", "Iris-virginica" },
			{ "5.8", "2.7", "5.1", "1.9", "Iris-virginica" },
			{ "6.8", "3.2", "5.9", "2.3", "Iris-virginica" },
			{ "6.7", "3.3", "5.7", "2.5", "Iris-virginica" },
			{ "6.7", "3.0", "5.2", "2.3", "Iris-virginica" },
			{ "6.3", "2.5", "5.0", "1.9", "Iris-virginica" },
			{ "6.5", "3.0", "5.2", "2.0", "Iris-virginica" },
			{ "6.2", "3.4", "5.4", "2.3", "Iris-virginica" },
			{ "5.9", "3.0", "5.1", "1.8", "Iris-virginica" } };

	public IrisMatrix() {
		super(data.length, data[0].length);
		setLabel("Iris flower data set");
		setColumnLabel(0, "Sepal Length");
		setColumnLabel(1, "Sepal Width");
		setColumnLabel(2, "Petal Length");
		setColumnLabel(3, "Petal Width");
		setColumnLabel(4, "Species");
	}

	public String getString(long row, long column) {
		return data[(int) row][(int) column];
	}

	public void setString(String value, long row, long column) {
	}

}
