/*
 * Copyright (C) 2008-2009 Holger Arndt, A. Naegele and M. Bundschus
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

package org.ujmp.core.calculation;

import org.ujmp.core.booleanmatrix.calculation.BooleanCalculations;
import org.ujmp.core.doublematrix.calculation.DoubleCalculations;
import org.ujmp.core.intmatrix.calculation.IntCalculations;
import org.ujmp.core.objectmatrix.calculation.ObjectCalculations;
import org.ujmp.core.stringmatrix.calculation.StringCalculations;

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
 * </p> Standard calculations are very easy to use, since these calculations can
 * be directly used by calling the methods explicitely. E.g. for calculating the
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
public interface CanPerformCalculations extends DoubleCalculations, BooleanCalculations,
		IntCalculations, StringCalculations, ObjectCalculations {

}
