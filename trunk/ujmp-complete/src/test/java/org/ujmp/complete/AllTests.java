/*
 * Copyright (C) 2008-2010 by Holger Arndt
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

package org.ujmp.complete;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { org.ujmp.complete.TestPlugins.class,
		org.ujmp.complete.TestCompareMatrices.class, org.ujmp.core.AllTests.class,
		org.ujmp.gui.AllTests.class, org.ujmp.bpca.AllTests.class, org.ujmp.hadoop.AllTests.class,
		org.ujmp.colt.AllTests.class, org.ujmp.jackcess.AllTests.class,
		org.ujmp.itext.AllTests.class, org.ujmp.jampack.AllTests.class,
		org.ujmp.jama.AllTests.class, org.ujmp.jfreechart.AllTests.class,
		org.ujmp.jexcelapi.AllTests.class, org.ujmp.jdbc.AllTests.class,
		org.ujmp.jlinalg.AllTests.class, org.ujmp.jmatharray.AllTests.class,
		org.ujmp.jmathplot.AllTests.class, org.ujmp.jblas.AllTests.class,
		org.ujmp.commonsmath.AllTests.class, org.ujmp.jmatrices.AllTests.class,
		org.ujmp.jmatio.AllTests.class, org.ujmp.parallelcolt.AllTests.class,
		org.ujmp.jsci.AllTests.class, org.ujmp.jscience.AllTests.class,
		org.ujmp.jung.AllTests.class, org.ujmp.lsimpute.AllTests.class,
		org.ujmp.lucene.AllTests.class, org.ujmp.mail.AllTests.class,
		org.ujmp.mantissa.AllTests.class, org.ujmp.mtj.AllTests.class,
		org.ujmp.ojalgo.AllTests.class, org.ujmp.orbital.AllTests.class,
		org.ujmp.owlpack.AllTests.class, org.ujmp.pdfbox.AllTests.class,
		org.ujmp.vecmath.AllTests.class, org.ujmp.ejml.AllTests.class, org.ujmp.sst.AllTests.class })
public class AllTests {
}
