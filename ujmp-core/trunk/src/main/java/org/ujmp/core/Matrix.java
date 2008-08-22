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

package org.ujmp.core;

import java.io.Serializable;

import org.ujmp.core.annotation.HasAnnotation;
import org.ujmp.core.calculation.CanPerformCalculations;
import org.ujmp.core.interfaces.BasicMatrixProperties;
import org.ujmp.core.interfaces.CanBeReshaped;
import org.ujmp.core.interfaces.Clearable;
import org.ujmp.core.interfaces.CoordinateFunctions;
import org.ujmp.core.interfaces.DistanceMeasures;
import org.ujmp.core.interfaces.HasGUIObject;
import org.ujmp.core.interfaces.HasLabel;
import org.ujmp.core.interfaces.MatrixEntryGettersAndSetters;
import org.ujmp.core.io.ExportMatrixInterface;

/**
 * <code>Matrix</code> is the main class for storing any type of data. You have
 * to choose the suitable implementation for your needs, e.g.
 * <code>DefaultDenseDoubleMatrix2D</code> to store double values or
 * DefaultGenericMatrix if you want to specify the object type.
 * 
 * 
 * @author Holger Arndt
 * @version $Revision$
 * @date $Date$
 * 
 * @log $Log$
 * 
 */
public interface Matrix extends Serializable, ExportMatrixInterface, CoordinateFunctions,
		MatrixEntryGettersAndSetters, BasicMatrixProperties, CanPerformCalculations, CanBeReshaped,
		DistanceMeasures, Comparable<Matrix>, Cloneable, Clearable, HasAnnotation, HasLabel,
		HasGUIObject {

	public static final int Y = 0;

	public static final int X = 1;

	public static final int Z = 2;

	public static final int ROW = 0;

	public static final int COLUMN = 1;

	public static final int ALL = 0x7fffffff;

	public static final int NONE = -1;

	public Matrix clone();

	// TODO: move to interface
	public Iterable<Object> allValues();

}
