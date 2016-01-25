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

package org.ujmp.core;

import org.ujmp.core.annotation.HasMetaData;
import org.ujmp.core.calculation.*;
import org.ujmp.core.doublematrix.calculation.general.decomposition.*;
import org.ujmp.core.export.destinationselector.MatrixExportDestinationSelector;
import org.ujmp.core.importer.sourceselector.MatrixImportSourceSelector;
import org.ujmp.core.interfaces.*;
import org.ujmp.core.matrix.factory.DefaultDenseMatrixFactory;

import java.io.File;
import java.io.IOException;

/**
 * <code>Matrix</code> is the main class for storing any type of data. You have
 * to choose the suitable implementation for your needs, e.g.
 * <code>DefaultDenseDoubleMatrix2D</code> to store double values or
 * DefaultGenericMatrix if you want to specify the object type.
 * 
 * @author Holger Arndt
 */
public interface Matrix extends BaseMatrix, CoreObject, CoordinateFunctions, GettersAndSetters,
		CanPerformCalculations, DistanceMeasures, Comparable<Matrix>, HasMetaData, Conversions,
		ExtendedMatrixProperties {

	/**
	 * A factory for creating matrices.
	 */
	DefaultDenseMatrixFactory Factory = new DefaultDenseMatrixFactory();

	TransposeCalculation<Matrix, Matrix> transpose = Transpose.MATRIX;

	PlusMatrixCalculation<Matrix, Matrix, Matrix> plusMatrix = PlusMatrix.MATRIX;

	MinusMatrixCalculation<Matrix, Matrix, Matrix> minusMatrix = MinusMatrix.MATRIX;

	TimesMatrixCalculation<Matrix, Matrix, Matrix> timesMatrix = TimesMatrix.MATRIX;

	DivideMatrixCalculation<Matrix, Matrix, Matrix> divideMatrix = DivideMatrix.MATRIX;

	PlusScalarCalculation<Matrix, Matrix> plusScalar = PlusScalar.MATRIX;

	MinusScalarCalculation<Matrix, Matrix> minusScalar = MinusScalar.MATRIX;

	TimesScalarCalculation<Matrix, Matrix> timesScalar = TimesScalar.MATRIX;

	DivideScalarCalculation<Matrix, Matrix> divideScalar = DivideScalar.MATRIX;

	MtimesCalculation<Matrix, Matrix, Matrix> mtimes = Mtimes.MATRIX;

	SVD<Matrix> svd = org.ujmp.core.doublematrix.calculation.general.decomposition.SVD.INSTANCE;

	LU<Matrix> lu = org.ujmp.core.doublematrix.calculation.general.decomposition.LU.INSTANCE;

	QR<Matrix> qr = org.ujmp.core.doublematrix.calculation.general.decomposition.QR.INSTANCE;

	Inv<Matrix> inv = Inv.INSTANCE;

	Solve<Matrix> solve = Solve.INSTANCE;

	Chol<Matrix> chol = Chol.INSTANCE;

	Eig<Matrix> eig = Eig.INSTANCE;

	Matrix clone();

	MatrixExportDestinationSelector exportTo();

	MatrixImportSourceSelector importFrom();

	void share(String hostname, int port) throws IOException;

	void share(int port) throws IOException;

	void fireValueChanged();

	void fireValueChanged(Coordinates coordinates, Object object);

	void fireValueChanged(Coordinates start, Coordinates end);

	void save(File file) throws IOException;

	void save(String filename) throws IOException;

	String toHtml();

}
