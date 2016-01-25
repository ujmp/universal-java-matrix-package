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

package org.ujmp.jdbc;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.ujmp.jdbc.map.TestDerbyStringMap;
import org.ujmp.jdbc.map.TestH2StringMap;
import org.ujmp.jdbc.map.TestHSQLDBStringMap;
import org.ujmp.jdbc.map.TestSQLiteStringMap;
import org.ujmp.jdbc.matrix.TestJDBCSparseObjectMatrix;
import org.ujmp.jdbc.set.TestDerbyStringSet;
import org.ujmp.jdbc.set.TestH2StringSet;
import org.ujmp.jdbc.set.TestHSQLDBStringSet;
import org.ujmp.jdbc.set.TestSQLiteStringSet;

@RunWith(Suite.class)
@Suite.SuiteClasses({ TestJDBCSparseObjectMatrix.class, TestDerbyStringMap.class, TestH2StringMap.class,
		TestHSQLDBStringMap.class, TestSQLiteStringMap.class, TestDerbyStringSet.class, TestH2StringSet.class,
		TestHSQLDBStringSet.class, TestSQLiteStringSet.class })
public class AllTests {
}