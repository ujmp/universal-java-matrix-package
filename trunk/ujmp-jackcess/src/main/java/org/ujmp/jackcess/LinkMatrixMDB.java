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

package org.ujmp.jackcess;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.StringUtil;

import com.healthmarketscience.jackcess.Database;

public class LinkMatrixMDB {

	public static final JackcessDenseObjectMatrix2D toFile(File file, Object... parameters)
			throws MatrixException, IOException {
		Database db = Database.open(file);
		Set<String> tables = db.getTableNames();
		String tablename = null;

		if (parameters.length != 0) {
			tablename = StringUtil.convert(parameters[0]);
		}
		if (tablename == null) {
			if (tables.size() == 1) {
				tablename = db.getTableNames().iterator().next();
			}
		}

		db.close();

		if (tablename == null) {
			throw new IllegalArgumentException(
					"please append the table name, i.e. one of these tables: " + tables);
		}

		return new JackcessDenseObjectMatrix2D(file, tablename);
	}

}
