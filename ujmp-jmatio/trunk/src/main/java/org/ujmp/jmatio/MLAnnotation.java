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

package org.ujmp.jmatio;

import java.util.HashMap;
import java.util.Map;

import org.ujmp.core.annotation.Annotation;
import org.ujmp.core.annotation.DefaultAnnotation;
import org.ujmp.core.util.StringUtil;

import com.jmatio.types.MLArray;

public class MLAnnotation extends DefaultAnnotation {
	private static final long serialVersionUID = 86587787686451L;

	private transient MLArray matrix = null;

	public MLAnnotation(MLArray matrix) {
		this.matrix = matrix;
	}

	public MLAnnotation(MLAnnotation annotation) {
		this.matrix = annotation.matrix;
		matrixAnnotation = annotation.getMatrixAnnotation();
		axisAnnotation = new HashMap<Integer, Map<Integer, Object>>();
		axisLabelAnnotation = new HashMap<Integer, Object>(2);
		axisLabelAnnotation.putAll(annotation.getAxisLabelAnnotation());
		for (int i : annotation.getAxisAnnotation().keySet()) {
			Map<Integer, Object> m = annotation.getAxisAnnotation().get(i);
			Map<Integer, Object> mnew = new HashMap<Integer, Object>();
			mnew.putAll(m);
			axisAnnotation.put(i, mnew);
		}
	}

	@Override
	public Object getMatrixAnnotation() {
		return matrix.getName();
	}

	@Override
	public void setMatrixAnnotation(Object matrixAnnotation) {
		matrix.name = StringUtil.convert(matrixAnnotation);
	}

	@Override
	public Annotation clone() {
		return new MLAnnotation(this);
	}

	public void setMLArray(MLArray matrix) {
		this.matrix = matrix;
	}

}