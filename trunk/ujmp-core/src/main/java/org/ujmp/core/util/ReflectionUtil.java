/*
 * Copyright (C) 2008-2013 by Holger Arndt
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

package org.ujmp.core.util;

import java.lang.reflect.Field;

public class ReflectionUtil {

	public static Object extractPrivateField(Object o, String fieldName) {
		return extractPrivateField(o.getClass(), o, fieldName);
	}

	public static Object extractPrivateField(Class<?> c, Object o, String fieldName) {
		Field[] fields = c.getDeclaredFields();
		if (fields != null) {
			for (Field f : fields) {
				try {
					if (fieldName.equals(f.getName())) {
						boolean isAccessible = f.isAccessible();
						f.setAccessible(true);
						Object value = f.get(o);
						f.setAccessible(isAccessible);
						return value;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

}
