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

package org.ujmp.core.mapper;

import java.lang.reflect.Constructor;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.calculation.basic.Plus;

public class CalculationMapper implements ClassMapper {

	private transient static final Logger logger = Logger.getLogger(CalculationMapper.class
			.getName());

	private static CalculationMapper instance = null;

	private Constructor<?> plusDenseDoubleCalculation2DConstructor = null;

	public static CalculationMapper getInstance() {
		if (instance == null) {
			instance = new CalculationMapper();
		}
		return instance;
	}

	private CalculationMapper() {
		findMatrixClasses();
	}

	private void findMatrixClasses() {
		try {
			setPlusDenseDoubleCalculation2DClassName(Plus.class.getName());
		} catch (Exception e) {
			logger.log(Level.SEVERE,
					"Default Calculation classes not found, this should never happen", e);
		}
	}

	public void setPlusDenseDoubleCalculation2DClassName(String className) throws Exception {
		Class<?> calculationClass = null;
		try {
			calculationClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			calculationClass = Plus.class;
			logger.log(Level.WARNING, "Could not find " + className + ", using " + calculationClass
					+ " instead.");
		}
		plusDenseDoubleCalculation2DConstructor = calculationClass.getConstructor(Boolean.TYPE,
				Matrix.class, Matrix.class);
	}

	public Constructor<?> getPlusDenseDoubleCalculation2DConstructor() {
		return plusDenseDoubleCalculation2DConstructor;
	}

}
