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

import java.io.File;
import java.io.IOException;

import org.ujmp.core.annotation.HasMetaData;
import org.ujmp.core.calculation.CanPerformCalculations;
import org.ujmp.core.calculation.DivideMatrix;
import org.ujmp.core.calculation.DivideMatrixCalculation;
import org.ujmp.core.calculation.DivideScalar;
import org.ujmp.core.calculation.DivideScalarCalculation;
import org.ujmp.core.calculation.MinusMatrix;
import org.ujmp.core.calculation.MinusMatrixCalculation;
import org.ujmp.core.calculation.MinusScalar;
import org.ujmp.core.calculation.MinusScalarCalculation;
import org.ujmp.core.calculation.Mtimes;
import org.ujmp.core.calculation.MtimesCalculation;
import org.ujmp.core.calculation.PlusMatrix;
import org.ujmp.core.calculation.PlusMatrixCalculation;
import org.ujmp.core.calculation.PlusScalar;
import org.ujmp.core.calculation.PlusScalarCalculation;
import org.ujmp.core.calculation.TimesMatrix;
import org.ujmp.core.calculation.TimesMatrixCalculation;
import org.ujmp.core.calculation.TimesScalar;
import org.ujmp.core.calculation.TimesScalarCalculation;
import org.ujmp.core.calculation.Transpose;
import org.ujmp.core.calculation.TransposeCalculation;
import org.ujmp.core.doublematrix.calculation.general.decomposition.Chol;
import org.ujmp.core.doublematrix.calculation.general.decomposition.Eig;
import org.ujmp.core.doublematrix.calculation.general.decomposition.Inv;
import org.ujmp.core.doublematrix.calculation.general.decomposition.LU;
import org.ujmp.core.doublematrix.calculation.general.decomposition.QR;
import org.ujmp.core.doublematrix.calculation.general.decomposition.SVD;
import org.ujmp.core.doublematrix.calculation.general.decomposition.Solve;
import org.ujmp.core.export.destinationselector.MatrixExportDestinationSelector;
import org.ujmp.core.importer.sourceselector.MatrixImportSourceSelector;
import org.ujmp.core.interfaces.Conversions;
import org.ujmp.core.interfaces.CoordinateFunctions;
import org.ujmp.core.interfaces.CoreObject;
import org.ujmp.core.interfaces.DistanceMeasures;
import org.ujmp.core.interfaces.ExtendedMatrixProperties;
import org.ujmp.core.interfaces.GettersAndSetters;
import org.ujmp.core.matrix.factory.DefaultDenseMatrixFactory;

/**
 * <code>Matrix</code> is the main class for storing any type of data. You have
 * to choose the suitable implementation for your needs, e.g.
 * <code>DefaultDenseDoubleMatrix2D</code> to store double values or
 * DefaultGenericMatrix if you want to specify the object type.
 * 
 * @author Holger Arndt
 */
public interface Matrix extends BaseMatrix, CoreObject, CoordinateFunctions, GettersAndSetters, CanPerformCalculations,
		DistanceMeasures, Comparable<Matrix>, HasMetaData, Conversions, ExtendedMatrixProperties {

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

	MatrixExportDestinationSelector exportTo();

	MatrixImportSourceSelector importFrom();

	void share(String hostname, int port) throws IOException;

	void share(int port) throws IOException;

	void fireValueChanged(Coordinates coordinates, Object object);

	void fireValueChanged(Coordinates start, Coordinates end);

	void save(File file) throws IOException;

	void save(String filename) throws IOException;

	public Matrix clone();

}
