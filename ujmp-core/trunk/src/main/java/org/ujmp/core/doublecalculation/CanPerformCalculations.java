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

package org.ujmp.core.doublecalculation;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublecalculation.Calculation.Calc;
import org.ujmp.core.doublecalculation.Calculation.Ret;
import org.ujmp.core.doublecalculation.basic.BasicDoubleCalculations;
import org.ujmp.core.doublecalculation.entrywise.EntrywiseDoubleCalculations;
import org.ujmp.core.doublecalculation.general.GeneralDoubleCalculations;
import org.ujmp.core.exceptions.MatrixException;

/**
 * <p>
 * This interface declares many methods for performing calculations on a matrix.
 * Generally, there are three types of calculations:
 * <ul>
 * <li>Standard calculations such as <code>sum</code> or <code>plus</code>.</li>
 * <li>Advanced calculations.</li>
 * <li>Own implementations of calculations.</li>
 * </ul>
 * </p>
 * 
 * </p>
 * Standard calculations are very easy to use, since these calculations can be
 * directly used by calling the methods explicitely. E.g. for calculating the
 * mean of a matrix, the method <code>mean</code> can be used. More advanced
 * calculations are available by using the <code>calc</code> methods. The
 * calculation to call can be either given by a String (containing the name of
 * the calculation) or the corresponding enum from {@link Calculcation.Calc}.
 * </p>
 * 
 * <p>
 * Own calculations can be implemented by implementing the interface
 * {@link Calculation} and using the the method <code>calc<code>.
 * </p>
 * 
 * 
 * 
 * @see Calculation.Calc for available calculations
 * @author Holger Arndt
 *
 */
public interface CanPerformCalculations extends BasicDoubleCalculations, EntrywiseDoubleCalculations, GeneralDoubleCalculations {

	public Matrix calcNew(Calculation calculation) throws MatrixException;

	public Matrix calc(Calculation calculation, Ret returnType) throws MatrixException;

	public Matrix calcNew(String calculation, Matrix... matrices) throws MatrixException;

	public Matrix calcNew(String calculation, int dimension, Matrix... matrices) throws MatrixException;

	public Matrix calc(String calculation, Ret returnType, Matrix... matrices) throws MatrixException;

	public Matrix calc(String calculation, Ret returnType, int dimension, Matrix... matrices) throws MatrixException;

	public Matrix calcNew(Calc calculation, Matrix... matrices) throws MatrixException;

	public Matrix calcNew(Calc calculation, int dimension, Matrix... matrices) throws MatrixException;

	public Matrix calc(Calc calculation, Ret returnType, Matrix... matrices) throws MatrixException;

	public Matrix calc(Calc calculation, Ret returnType, int dimension, Matrix... matrices) throws MatrixException;

}
