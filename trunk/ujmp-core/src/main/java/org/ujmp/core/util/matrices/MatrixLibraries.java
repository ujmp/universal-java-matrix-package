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

package org.ujmp.core.util.matrices;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.enums.FileFormat;
import org.ujmp.core.stringmatrix.impl.DefaultDenseStringMatrix2D;

public class MatrixLibraries extends DefaultDenseStringMatrix2D {
	private static final long serialVersionUID = -2575195318248762416L;

	public static final int LABELCOLUMN = 0;

	public static final int VERSION = 1;

	public static final int DATE = 2;

	public static final int LICENCE = 3;

	public static final int JAVA14 = 4;

	public static final int JAVA5 = 5;

	public static final int JAVA6 = 6;

	public static final int DENSESA = 7;

	public static final int DENSEAA = 8;

	public static final int DENSEBLOCK = 9;

	public static final int SPARSEDOK = 10;

	public static final int SPARSELIL = 11;

	public static final int SPARSEYALE = 12;

	public static final int SPARSECRS = 13;

	public static final int SPARSECDS = 14;

	public static final int DOUBLE = 15;

	public static final int FLOAT = 16;

	public static final int BIGDECIMAL = 17;

	public static final int COMPLEX = 18;

	public static final int D2 = 19;

	public static final int D3 = 20;

	public static final int D3PLUS = 21;

	public static final int MULTITHREADED = 22;

	public static final int INPLACE = 23;

	public static final int CACHEDRESULTS = 24;

	public static final int TRANSPOSE = 25;

	public static final int SCALE = 26;

	public static final int PLUSMINUS = 27;

	public static final int INV = 28;

	public static final int SOLVE = 29;

	public static final int LU = 30;

	public static final int QR = 31;

	public static final int SVD = 32;

	public static final int CHOL = 33;

	public static final int EIG = 34;

	public static final int PACKAGE = 35;

	public static final int LABELROW = 0;

	public static final int ARRAY4J = 1;

	public static final int COLT = 2;

	public static final int COMMONSMATH = 3;

	public static final int EJML = 4;

	public static final int JAMA = 5;

	public static final int JAMPACK = 6;

	public static final int JBLAS = 7;

	public static final int JLINALG = 8;

	public static final int JMATHARRAY = 9;

	public static final int JMATRICES = 10;

	public static final int JSCI = 11;

	public static final int JSCIENCE = 12;

	public static final int MANTISSA = 13;

	public static final int MTJ = 14;

	public static final int OJALGO = 15;

	public static final int ORBITAL = 16;

	public static final int OWLPACK = 17;

	public static final int PARALLELCOLT = 18;

	public static final int SST = 19;

	public static final int UJMP = 20;

	public static final int VECMATH = 21;

	public static final String NONSINGULARLETTER = "n";

	public static final String NONSINGULARTEXT = "non-singular matrices only";

	public static final String ERRORTEXT = "error in implementation";

	private List<String> footnotes = new ArrayList<String>();

	public enum MatrixLibrariesFormat {
		DEFAULT, LATEX, HTML
	};

	private MatrixLibrariesFormat format = MatrixLibrariesFormat.DEFAULT;

	public MatrixLibraries() {
		this(MatrixLibrariesFormat.DEFAULT);
	}

	public MatrixLibraries(MatrixLibrariesFormat format) {
		super(36, 22);
		this.format = format;

		setAsString(turn("Array4J"), LABELROW, ARRAY4J);
		setAsString(turn("Colt"), LABELROW, COLT);
		setAsString(turn("commons-math"), LABELROW, COMMONSMATH);
		setAsString(turn("EJML"), LABELROW, EJML);
		setAsString(turn("JAMA"), LABELROW, JAMA);
		setAsString(turn("Jampack"), LABELROW, JAMPACK);
		setAsString(turn("JBlas"), LABELROW, JBLAS);
		setAsString(turn("JLinAlg"), LABELROW, JLINALG);
		setAsString(turn("JMathArray"), LABELROW, JMATHARRAY);
		setAsString(turn("JMatrices"), LABELROW, JMATRICES);
		setAsString(turn("JSci"), LABELROW, JSCI);
		setAsString(turn("JScience"), LABELROW, JSCIENCE);
		setAsString(turn("Mantissa"), LABELROW, MANTISSA);
		setAsString(turn("MTJ"), LABELROW, MTJ);
		setAsString(turn("ojAlgo"), LABELROW, OJALGO);
		setAsString(turn("Orbital"), LABELROW, ORBITAL);
		setAsString(turn("OWLPack"), LABELROW, OWLPACK);
		setAsString(turn("Parallel Colt"), LABELROW, PARALLELCOLT);
		setAsString(turn("SST"), LABELROW, SST);
		setAsString(turn("UJMP"), LABELROW, UJMP);
		setAsString(turn("vecmath"), LABELROW, VECMATH);

		setAsString("version", VERSION, LABELCOLUMN);
		setAsString("release", DATE, LABELCOLUMN);
		setAsString("licence", LICENCE, LABELCOLUMN);
		setAsString("Java 1.4", JAVA14, LABELCOLUMN);
		setAsString("Java 5", JAVA5, LABELCOLUMN);
		setAsString("Java 6", JAVA6, LABELCOLUMN);
		setAsString("multithreaded", MULTITHREADED, LABELCOLUMN);
		setAsString("in-place ops", INPLACE, LABELCOLUMN);
		setAsString("cached results", CACHEDRESULTS, LABELCOLUMN);
		setAsString("dense SA" + footnote("1", "single array"), DENSESA, LABELCOLUMN);
		setAsString("dense AA" + footnote("2", "array of arrays"), DENSEAA, LABELCOLUMN);
		setAsString("dense block", DENSEBLOCK, LABELCOLUMN);
		setAsString("sparse DOK" + footnote("3", "dictionary of key-value pairs"), SPARSEDOK,
				LABELCOLUMN);
		setAsString("sparse LIL" + footnote("4", "list of lists"), SPARSELIL, LABELCOLUMN);

		setAsString("sparse CSR" + footnote("5", "compressed sparse row/column"), SPARSECRS,
				LABELCOLUMN);
		setAsString("sparse CDS" + footnote("6", "compressed sparse diagonal"), SPARSECDS,
				LABELCOLUMN);
		setAsString("sparse Yale", SPARSEYALE, LABELCOLUMN);
		setAsString("complex", COMPLEX, LABELCOLUMN);
		setAsString("double", DOUBLE, LABELCOLUMN);
		setAsString("float", FLOAT, LABELCOLUMN);
		setAsString("BigDecimal", BIGDECIMAL, LABELCOLUMN);
		setAsString("2D", D2, LABELCOLUMN);
		setAsString("3D", D3, LABELCOLUMN);
		setAsString("$>$3D", D3PLUS, LABELCOLUMN);
		setAsString("transpose", TRANSPOSE, LABELCOLUMN);
		setAsString("scale", SCALE, LABELCOLUMN);
		setAsString("plus/minus", PLUSMINUS, LABELCOLUMN);
		setAsString("inverse", INV, LABELCOLUMN);
		setAsString("solve", SOLVE, LABELCOLUMN);
		setAsString("SVD", SVD, LABELCOLUMN);
		setAsString("LU", LU, LABELCOLUMN);
		setAsString("QR", QR, LABELCOLUMN);
		setAsString("Cholesky", CHOL, LABELCOLUMN);
		setAsString("Eigen", EIG, LABELCOLUMN);
		setAsString("package", PACKAGE, LABELCOLUMN);

		setAsString(org.ujmp.core.UJMP.UJMPVERSION, VERSION, UJMP);
		setAsString(small("2010"), DATE, UJMP);
		setAsString(small("LGPL"), LICENCE, UJMP);
		setAsString(notSupported(), JAVA14, UJMP);
		setAsString(yes(), JAVA5, UJMP);
		setAsString(yes(), JAVA6, UJMP);
		setAsString(both(), MULTITHREADED, UJMP);
		setAsString(yes(), INPLACE, UJMP);
		setAsString(notSupported(), CACHEDRESULTS, UJMP);
		setAsString(yes(), DENSEAA, UJMP);
		setAsString(yes(), DENSESA, UJMP);
		setAsString(yes(), DENSEBLOCK, UJMP);
		setAsString(yes(), SPARSEDOK, UJMP);
		setAsString(notSupported(), SPARSEYALE, UJMP);
		setAsString(yes(), SPARSELIL, UJMP);
		setAsString(notSupported(), SPARSECRS, UJMP);
		setAsString(notSupported(), SPARSECDS, UJMP);
		setAsString(notSupported(), COMPLEX, UJMP);
		setAsString(yes(), DOUBLE, UJMP);
		setAsString(yes(), FLOAT, UJMP);
		setAsString(yes(), BIGDECIMAL, UJMP);
		setAsString(yes(), D2, UJMP);
		setAsString(yes(), D3, UJMP);
		setAsString(yes(), D3PLUS, UJMP);
		setAsString(yes(), TRANSPOSE, UJMP);
		setAsString(yes(), SCALE, UJMP);
		setAsString(yes(), PLUSMINUS, UJMP);
		setAsString(yes(), INV, UJMP);
		setAsString(squareTall(), SOLVE, UJMP);
		setAsString(yes(), CHOL, UJMP);
		setAsString(yes(), EIG, UJMP);
		setAsString(all(), LU, UJMP);
		setAsString(squareTall(), QR, UJMP);
		setAsString(all(), SVD, UJMP);
		setAsString("org.ujmp.core", PACKAGE, UJMP);

		setAsString(small("SVN"), VERSION, ARRAY4J);
		setAsString(small("2008"), DATE, ARRAY4J);
		setAsString(small("BSD"), LICENCE, ARRAY4J);
		setAsString(notSupported(), JAVA14, ARRAY4J);
		setAsString(notSupported(), JAVA5, ARRAY4J);
		setAsString(yes(), JAVA6, ARRAY4J);
		setAsString(unknown() + footnote("m", "using native machine code"), MULTITHREADED, ARRAY4J);
		setAsString(yes(), INPLACE, ARRAY4J);
		setAsString(yes(), DENSESA, ARRAY4J);
		setAsString(notSupported(), DENSEAA, ARRAY4J);
		setAsString(notSupported(), DENSEBLOCK, ARRAY4J);
		setAsString(notSupported() + footnote("i", "interface only, no implementation"), SPARSEDOK,
				ARRAY4J);
		setAsString(notSupported() + footnote("i", "interface only, no implementation"),
				SPARSEYALE, ARRAY4J);
		setAsString(notSupported() + footnote("i", "interface only, no implementation"), SPARSELIL,
				ARRAY4J);
		setAsString(notSupported() + footnote("i", "interface only, no implementation"), SPARSECRS,
				ARRAY4J);
		setAsString(notSupported() + footnote("i", "interface only, no implementation"), SPARSECDS,
				ARRAY4J);
		setAsString(notSupported() + footnote("i", "interface only, no implementation"), COMPLEX,
				ARRAY4J);
		setAsString(notSupported() + footnote("i", "interface only, no implementation"), DOUBLE,
				ARRAY4J);
		setAsString(yes(), FLOAT, ARRAY4J);
		setAsString(notSupported(), BIGDECIMAL, ARRAY4J);
		setAsString(notSupported(), MULTITHREADED, ARRAY4J);
		setAsString(yes(), INPLACE, ARRAY4J);
		setAsString(notSupported(), CACHEDRESULTS, ARRAY4J);
		setAsString(yes(), PLUSMINUS, ARRAY4J);
		setAsString(yes(), SCALE, ARRAY4J);
		setAsString(yes(), TRANSPOSE, ARRAY4J);
		setAsString(yes(), D2, ARRAY4J);
		setAsString(notSupported(), D3, ARRAY4J);
		setAsString(notSupported(), D3PLUS, ARRAY4J);
		setAsString(notSupported(), SOLVE, ARRAY4J);
		setAsString(notSupported(), INV, ARRAY4J);
		setAsString(notSupported(), SVD, ARRAY4J);
		setAsString(notSupported(), QR, ARRAY4J);
		setAsString(notSupported(), LU, ARRAY4J);
		setAsString(notSupported(), EIG, ARRAY4J);
		setAsString(notSupported(), CHOL, ARRAY4J);
		setAsString(notSupported(), PACKAGE, ARRAY4J);

		setAsString(small("1.2.0"), VERSION, COLT);
		setAsString(small("2004"), DATE, COLT);
		setAsString(small("BSD"), LICENCE, COLT);
		setAsString(yes(), JAVA14, COLT);
		setAsString(yes(), JAVA5, COLT);
		setAsString(yes(), JAVA6, COLT);
		setAsString(notSupported(), MULTITHREADED, COLT);
		setAsString(yes(), INPLACE, COLT);
		setAsString(notSupported(), CACHEDRESULTS, COLT);
		setAsString(yes() + footnote("f", "flags matrix as transposed"), TRANSPOSE, COLT);
		setAsString(yes(), SCALE, COLT);
		setAsString(yes(), PLUSMINUS, COLT);
		setAsString(yes(), DENSESA, COLT);
		setAsString(notSupported(), DENSEAA, COLT);
		setAsString(notSupported(), DENSEBLOCK, COLT);
		setAsString(yes(), SPARSEDOK, COLT);
		setAsString(notSupported(), SPARSEYALE, COLT);
		setAsString(notSupported(), SPARSELIL, COLT);
		setAsString(notSupported(), SPARSECRS, COLT);
		setAsString(notSupported(), SPARSECDS, COLT);
		setAsString(notSupported(), COMPLEX, COLT);
		setAsString(yes(), DOUBLE, COLT);
		setAsString(notSupported(), FLOAT, COLT);
		setAsString(notSupported(), BIGDECIMAL, COLT);
		setAsString(yes(), D2, COLT);
		setAsString(yes(), D3, COLT);
		setAsString(notSupported(), D3PLUS, COLT);
		setAsString(yes(), INV, COLT);
		setAsString(squareTall(), SOLVE, COLT);
		setAsString(all(), SVD, COLT);
		setAsString(squareTall(), LU, COLT);
		setAsString(squareTall(), QR, COLT);
		setAsString(yes(), EIG, COLT);
		setAsString(yes(), CHOL, COLT);
		setAsString("org.ujmp.colt", PACKAGE, COLT);

		setAsString(small("2.1"), VERSION, COMMONSMATH);
		setAsString(small("2010"), DATE, COMMONSMATH);
		setAsString(small("Apache"), LICENCE, COMMONSMATH);
		setAsString(notSupported(), JAVA14, COMMONSMATH);
		setAsString(yes(), JAVA5, COMMONSMATH);
		setAsString(yes(), JAVA6, COMMONSMATH);
		setAsString(notSupported(), MULTITHREADED, COMMONSMATH);
		setAsString(yes(), INPLACE, COMMONSMATH);
		setAsString(notSupported(), CACHEDRESULTS, COMMONSMATH);
		setAsString(yes(), TRANSPOSE, COMMONSMATH);
		setAsString(yes(), SCALE, COMMONSMATH);
		setAsString(yes(), PLUSMINUS, COMMONSMATH);
		setAsString(yes(), DENSEAA, COMMONSMATH);
		setAsString(notSupported(), DENSESA, COMMONSMATH);
		setAsString(yes(), DENSEBLOCK, COMMONSMATH);
		setAsString(yes(), SPARSEDOK, COMMONSMATH);
		setAsString(notSupported(), SPARSEYALE, COMMONSMATH);
		setAsString(notSupported(), SPARSELIL, COMMONSMATH);
		setAsString(notSupported(), SPARSECRS, COMMONSMATH);
		setAsString(notSupported(), SPARSECDS, COMMONSMATH);
		setAsString(yes(), COMPLEX, COMMONSMATH);
		setAsString(yes(), DOUBLE, COMMONSMATH);
		setAsString(notSupported(), FLOAT, COMMONSMATH);
		setAsString(yes(), BIGDECIMAL, COMMONSMATH);
		setAsString(yes(), D2, COMMONSMATH);
		setAsString(notSupported(), D3, COMMONSMATH);
		setAsString(notSupported(), D3PLUS, COMMONSMATH);
		setAsString(yes(), INV, COMMONSMATH);
		setAsString(squareTall(), SOLVE, COMMONSMATH);
		setAsString(all(), SVD, COMMONSMATH);
		setAsString(square() + footnote(NONSINGULARLETTER, NONSINGULARTEXT), LU, COMMONSMATH);
		setAsString(all(), QR, COMMONSMATH);
		setAsString(yes() + footnote("s", "symmetric matrices only"), EIG, COMMONSMATH);
		setAsString(yes(), CHOL, COMMONSMATH);
		setAsString("org.ujmp.commonsmath", PACKAGE, COMMONSMATH);

		setAsString(small("0.11"), VERSION, EJML);
		setAsString(small("2010"), DATE, EJML);
		setAsString(small("LGPL"), LICENCE, EJML);
		setAsString(notSupported(), JAVA14, EJML);
		setAsString(notSupported(), JAVA5, EJML);
		setAsString(yes(), JAVA6, EJML);
		setAsString(notSupported(), MULTITHREADED, EJML);
		setAsString(notSupported(), CACHEDRESULTS, EJML);
		setAsString(yes(), INPLACE, EJML);
		setAsString(yes(), DENSESA, EJML);
		setAsString(notSupported(), DENSEAA, EJML);
		setAsString(yes(), DENSEBLOCK, EJML);
		setAsString(notSupported(), SPARSEDOK, EJML);
		setAsString(notSupported(), SPARSEYALE, EJML);
		setAsString(notSupported(), SPARSELIL, EJML);
		setAsString(notSupported(), SPARSECRS, EJML);
		setAsString(notSupported(), SPARSECDS, EJML);
		setAsString(notSupported(), COMPLEX, EJML);
		setAsString(yes(), DOUBLE, EJML);
		setAsString(notSupported(), FLOAT, EJML);
		setAsString(notSupported(), BIGDECIMAL, EJML);
		setAsString(yes(), D2, EJML);
		setAsString(notSupported(), D3, EJML);
		setAsString(notSupported(), D3PLUS, EJML);
		setAsString(yes(), TRANSPOSE, EJML);
		setAsString(yes(), SCALE, EJML);
		setAsString(yes(), PLUSMINUS, EJML);
		setAsString(yes(), INV, EJML);
		setAsString(squareTall(), SOLVE, EJML);
		setAsString(all(), SVD, EJML);
		setAsString(all(), LU, EJML);
		setAsString(square(), QR, EJML);
		setAsString(yes(), EIG, EJML);
		setAsString(yes(), CHOL, EJML);
		setAsString("org.ujmp.ejml", PACKAGE, EJML);

		setAsString(small("1.0.2"), VERSION, JAMA);
		setAsString(small("2005"), DATE, JAMA);
		setAsString(small("PD"), LICENCE, JAMA);
		setAsString(yes(), JAVA14, JAMA);
		setAsString(yes(), JAVA5, JAMA);
		setAsString(yes(), JAVA6, JAMA);
		setAsString(notSupported(), MULTITHREADED, JAMA);
		setAsString(notSupported(), INPLACE, JAMA);
		setAsString(notSupported(), CACHEDRESULTS, JAMA);
		setAsString(yes(), DENSEAA, JAMA);
		setAsString(notSupported(), DENSESA, JAMA);
		setAsString(notSupported(), DENSEBLOCK, JAMA);
		setAsString(notSupported(), SPARSEDOK, JAMA);
		setAsString(notSupported(), SPARSEYALE, JAMA);
		setAsString(notSupported(), SPARSELIL, JAMA);
		setAsString(notSupported(), SPARSECRS, JAMA);
		setAsString(notSupported(), SPARSECDS, JAMA);
		setAsString(notSupported(), COMPLEX, JAMA);
		setAsString(yes(), DOUBLE, JAMA);
		setAsString(notSupported(), FLOAT, JAMA);
		setAsString(notSupported(), BIGDECIMAL, JAMA);
		setAsString(yes(), D2, JAMA);
		setAsString(notSupported(), D3, JAMA);
		setAsString(notSupported(), D3PLUS, JAMA);
		setAsString(yes(), TRANSPOSE, JAMA);
		setAsString(yes(), SCALE, JAMA);
		setAsString(yes(), PLUSMINUS, JAMA);
		setAsString(yes(), INV, JAMA);
		setAsString(squareTall(), SOLVE, JAMA);
		setAsString(squareTall(), SVD, JAMA);
		setAsString(squareTall(), LU, JAMA);
		setAsString(squareTall(), QR, JAMA);
		setAsString(yes(), EIG, JAMA);
		setAsString(yes(), CHOL, JAMA);
		setAsString("org.ujmp.jama", PACKAGE, JAMA);

		setAsString(small("?"), VERSION, JAMPACK);
		setAsString(small("1999"), DATE, JAMPACK);
		setAsString(small("?"), LICENCE, JAMPACK);
		setAsString(yes(), JAVA14, JAMPACK);
		setAsString(yes(), JAVA5, JAMPACK);
		setAsString(yes(), JAVA6, JAMPACK);
		setAsString(notSupported(), MULTITHREADED, JAMPACK);
		setAsString(notSupported(), INPLACE, JAMPACK);
		setAsString(yes(), DENSEAA, JAMPACK);
		setAsString(notSupported(), DENSESA, JAMPACK);
		setAsString(notSupported(), DENSEBLOCK, JAMPACK);
		setAsString(notSupported(), SPARSEDOK, JAMPACK);
		setAsString(notSupported(), SPARSEYALE, JAMPACK);
		setAsString(notSupported(), SPARSELIL, JAMPACK);
		setAsString(notSupported(), SPARSECRS, JAMPACK);
		setAsString(notSupported(), SPARSECDS, JAMPACK);
		setAsString(yes(), COMPLEX, JAMPACK);
		setAsString(yes(), DOUBLE, JAMPACK);
		setAsString(notSupported(), FLOAT, JAMPACK);
		setAsString(notSupported(), BIGDECIMAL, JAMPACK);
		setAsString(yes(), D2, JAMPACK);
		setAsString(notSupported(), D3, JAMPACK);
		setAsString(notSupported(), D3PLUS, JAMPACK);
		setAsString(yes(), CACHEDRESULTS, JAMPACK);
		setAsString(yes(), TRANSPOSE, JAMPACK);
		setAsString(yes(), PLUSMINUS, JAMPACK);
		setAsString(yes(), SCALE, JAMPACK);
		setAsString(yes(), INV, JAMPACK);
		setAsString(square(), SOLVE, JAMPACK);
		setAsString(square(), SVD, JAMPACK);
		setAsString(all(), LU, JAMPACK);
		setAsString(all(), QR, JAMPACK);
		setAsString(yes(), EIG, JAMPACK);
		setAsString(yes() + footnote("e", ERRORTEXT), CHOL, JAMPACK);
		setAsString("org.ujmp.jampack", PACKAGE, JAMPACK);

		setAsString(small("1.0.2"), VERSION, JBLAS);
		setAsString(small("2010"), DATE, JBLAS);
		setAsString(small("BSD"), LICENCE, JBLAS);
		setAsString(notSupported(), JAVA14, JBLAS);
		setAsString(yes(), JAVA5, JBLAS);
		setAsString(yes(), JAVA6, JBLAS);
		setAsString(yes() + footnote("m", "using native machine code"), MULTITHREADED, JBLAS);
		setAsString(yes(), INPLACE, JBLAS);
		setAsString(notSupported(), CACHEDRESULTS, JBLAS);
		setAsString(yes(), DENSESA, JBLAS);
		setAsString(notSupported(), DENSEAA, JBLAS);
		setAsString(notSupported(), DENSEBLOCK, JBLAS);
		setAsString(notSupported(), SPARSEDOK, JBLAS);
		setAsString(notSupported(), SPARSEYALE, JBLAS);
		setAsString(notSupported(), SPARSELIL, JBLAS);
		setAsString(notSupported(), SPARSECRS, JBLAS);
		setAsString(notSupported(), SPARSECDS, JBLAS);
		setAsString(yes(), COMPLEX, JBLAS);
		setAsString(yes(), DOUBLE, JBLAS);
		setAsString(yes(), FLOAT, JBLAS);
		setAsString(notSupported(), BIGDECIMAL, JBLAS);
		setAsString(yes(), D2, JBLAS);
		setAsString(notSupported(), D3, JBLAS);
		setAsString(notSupported(), D3PLUS, JBLAS);
		setAsString(yes(), TRANSPOSE, JBLAS);
		setAsString(yes(), SCALE, JBLAS);
		setAsString(yes(), PLUSMINUS, JBLAS);
		setAsString(yes(), INV, JBLAS);
		setAsString(square(), SOLVE, JBLAS);
		setAsString(notSupported(), SVD, JBLAS);
		setAsString(all(), LU, JBLAS);
		setAsString(notSupported(), QR, JBLAS);
		setAsString(yes() + footnote("s", "symmetric matrices only"), EIG, JBLAS);
		setAsString(yes(), CHOL, JBLAS);
		setAsString("org.ujmp.jblas", PACKAGE, JBLAS);

		setAsString(small("0.6"), VERSION, JLINALG);
		setAsString(small("2009"), DATE, JLINALG);
		setAsString(small("GPL"), LICENCE, JLINALG);
		setAsString(notSupported(), JAVA14, JLINALG);
		setAsString(notSupported(), JAVA5, JLINALG);
		setAsString(yes(), JAVA6, JLINALG);
		setAsString(notSupported(), MULTITHREADED, JLINALG);
		setAsString(notSupported(), CACHEDRESULTS, JLINALG);
		setAsString(yes(), INPLACE, JLINALG);
		setAsString(yes(), DENSEAA, JLINALG);
		setAsString(notSupported(), DENSESA, JLINALG);
		setAsString(notSupported(), DENSEBLOCK, JLINALG);
		setAsString(notSupported(), SPARSEDOK, JLINALG);
		setAsString(notSupported(), SPARSEYALE, JLINALG);
		setAsString(notSupported(), SPARSELIL, JLINALG);
		setAsString(notSupported(), SPARSECRS, JLINALG);
		setAsString(notSupported(), SPARSECDS, JLINALG);
		setAsString(yes(), COMPLEX, JLINALG);
		setAsString(yes(), DOUBLE, JLINALG);
		setAsString(notSupported(), FLOAT, JLINALG);
		setAsString(yes(), BIGDECIMAL, JLINALG);
		setAsString(yes(), TRANSPOSE, JLINALG);
		setAsString(yes(), SCALE, JLINALG);
		setAsString(yes(), PLUSMINUS, JLINALG);
		setAsString(yes(), D2, JLINALG);
		setAsString(notSupported(), D3, JLINALG);
		setAsString(notSupported(), D3PLUS, JLINALG);
		setAsString(yes(), INV, JLINALG);
		setAsString(notSupported() + footnote("v", "only for $A \\cdot X = \\mbox{vector}$"),
				SOLVE, JLINALG);
		setAsString(notSupported(), SVD, JLINALG);
		setAsString(notSupported(), LU, JLINALG);
		setAsString(notSupported(), QR, JLINALG);
		setAsString(notSupported(), EIG, JLINALG);
		setAsString(notSupported(), CHOL, JLINALG);
		setAsString("org.ujmp.jlinalg", PACKAGE, JLINALG);

		setAsString(small("?"), VERSION, JMATHARRAY);
		setAsString(small("2009"), DATE, JMATHARRAY);
		setAsString(small("BSD"), LICENCE, JMATHARRAY);
		setAsString(notSupported(), JAVA14, JMATHARRAY);
		setAsString(yes(), JAVA5, JMATHARRAY);
		setAsString(yes(), JAVA6, JMATHARRAY);
		setAsString(notSupported(), MULTITHREADED, JMATHARRAY);
		setAsString(notSupported(), INPLACE, JMATHARRAY);
		setAsString(notSupported(), CACHEDRESULTS, JMATHARRAY);
		setAsString(yes(), TRANSPOSE, JMATHARRAY);
		setAsString(yes(), SCALE, JMATHARRAY);
		setAsString(yes(), PLUSMINUS, JMATHARRAY);
		setAsString(yes(), DENSEAA, JMATHARRAY);
		setAsString(notSupported(), DENSESA, JMATHARRAY);
		setAsString(notSupported(), DENSEBLOCK, JMATHARRAY);
		setAsString(notSupported(), SPARSEDOK, JMATHARRAY);
		setAsString(notSupported(), SPARSEYALE, JMATHARRAY);
		setAsString(notSupported(), SPARSELIL, JMATHARRAY);
		setAsString(notSupported(), SPARSECRS, JMATHARRAY);
		setAsString(notSupported(), SPARSECDS, JMATHARRAY);
		setAsString(notSupported(), COMPLEX, JMATHARRAY);
		setAsString(yes(), DOUBLE, JMATHARRAY);
		setAsString(notSupported(), FLOAT, JMATHARRAY);
		setAsString(notSupported(), BIGDECIMAL, JMATHARRAY);
		setAsString(yes(), D2, JMATHARRAY);
		setAsString(notSupported(), D3, JMATHARRAY);
		setAsString(notSupported(), D3PLUS, JMATHARRAY);
		setAsString(yes(), INV, JMATHARRAY);
		setAsString(squareTall(), SOLVE, JMATHARRAY);
		setAsString(squareTall(), SVD, JMATHARRAY);
		setAsString(squareTall(), LU, JMATHARRAY);
		setAsString(squareTall(), QR, JMATHARRAY);
		setAsString(yes(), EIG, JMATHARRAY);
		setAsString(yes(), CHOL, JMATHARRAY);
		setAsString("org.ujmp.jmatharray", PACKAGE, JMATHARRAY);

		setAsString(small("0.6"), VERSION, JMATRICES);
		setAsString(small("2004"), DATE, JMATRICES);
		setAsString(small("LGPL"), LICENCE, JMATRICES);
		setAsString(yes(), JAVA14, JMATRICES);
		setAsString(yes(), JAVA5, JMATRICES);
		setAsString(yes(), JAVA6, JMATRICES);
		setAsString(notSupported(), MULTITHREADED, JMATRICES);
		setAsString(notSupported(), INPLACE, JMATRICES);
		setAsString(notSupported(), CACHEDRESULTS, JMATRICES);
		setAsString(yes(), DENSEAA, JMATRICES);
		setAsString(notSupported(), DENSESA, JMATRICES);
		setAsString(notSupported(), DENSEBLOCK, JMATRICES);
		setAsString(notSupported(), SPARSEDOK, JMATRICES);
		setAsString(notSupported(), SPARSEYALE, JMATRICES);
		setAsString(notSupported(), SPARSELIL, JMATRICES);
		setAsString(notSupported(), SPARSECRS, JMATRICES);
		setAsString(notSupported(), SPARSECDS, JMATRICES);
		setAsString(yes(), COMPLEX, JMATRICES);
		setAsString(yes(), DOUBLE, JMATRICES);
		setAsString(notSupported(), FLOAT, JMATRICES);
		setAsString(yes(), BIGDECIMAL, JMATRICES);
		setAsString(yes(), D2, JMATRICES);
		setAsString(notSupported(), D3, JMATRICES);
		setAsString(notSupported(), D3PLUS, JMATRICES);
		setAsString(yes(), TRANSPOSE, JMATRICES);
		setAsString(yes(), PLUSMINUS, JMATRICES);
		setAsString(yes(), SCALE, JMATRICES);
		setAsString(yes(), INV, JMATRICES);
		setAsString(squareTall(), SOLVE, JMATRICES);
		setAsString(square(), SVD, JMATRICES);
		setAsString(squareTall(), LU, JMATRICES);
		setAsString(squareTall(), QR, JMATRICES);
		setAsString(yes(), EIG, JMATRICES);
		setAsString(yes(), CHOL, JMATRICES);
		setAsString("org.ujmp.jmatrices", PACKAGE, JMATRICES);

		setAsString(small("1.1"), VERSION, JSCI);
		setAsString(small("2009"), DATE, JSCI);
		setAsString(small("LGPL"), LICENCE, JSCI);
		setAsString(yes(), JAVA14, JSCI);
		setAsString(yes(), JAVA5, JSCI);
		setAsString(yes(), JAVA6, JSCI);
		setAsString(notSupported(), MULTITHREADED, JSCI);
		setAsString(notSupported(), INPLACE, JSCI);
		setAsString(notSupported(), CACHEDRESULTS, JSCI);
		setAsString(yes(), DENSEAA, JSCI);
		setAsString(notSupported(), DENSESA, JSCI);
		setAsString(notSupported(), DENSEBLOCK, JSCI);
		setAsString(yes(), SPARSEYALE, JSCI);
		setAsString(notSupported(), SPARSEDOK, JSCI);
		setAsString(notSupported(), SPARSELIL, JSCI);
		setAsString(notSupported(), SPARSECRS, JSCI);
		setAsString(notSupported(), SPARSECDS, JSCI);
		setAsString(yes(), COMPLEX, JSCI);
		setAsString(yes(), DOUBLE, JSCI);
		setAsString(notSupported(), FLOAT, JSCI);
		setAsString(notSupported(), BIGDECIMAL, JSCI);
		setAsString(yes(), D2, JSCI);
		setAsString(notSupported(), D3, JSCI);
		setAsString(notSupported(), D3PLUS, JSCI);
		setAsString(yes(), TRANSPOSE, JSCI);
		setAsString(yes(), PLUSMINUS, JSCI);
		setAsString(yes(), SCALE, JSCI);
		setAsString(notSupported() + footnote("v", "only for $A \\cdot X = \\mbox{vector}$"),
				SOLVE, JSCI);
		setAsString(yes(), INV, JSCI);
		setAsString(square(), SVD, JSCI);
		setAsString(square() + footnote(NONSINGULARLETTER, NONSINGULARTEXT), LU, JSCI);
		setAsString(square(), QR, JSCI);
		setAsString(yes() + footnote("s", "symmetric matrices only")
				+ footnote("a", "results not directly accessible"), EIG, JSCI);
		setAsString(yes(), CHOL, JSCI);
		setAsString("org.ujmp.jsci", PACKAGE, JSCI);

		setAsString(small("4.3.1"), VERSION, JSCIENCE);
		setAsString(small("2007"), DATE, JSCIENCE);
		setAsString(small("BSD"), LICENCE, JSCIENCE);
		setAsString(notSupported(), JAVA14, JSCIENCE);
		setAsString(yes(), JAVA5, JSCIENCE);
		setAsString(yes(), JAVA6, JSCIENCE);
		setAsString(yes(), MULTITHREADED, JSCIENCE);
		setAsString(notSupported(), INPLACE, JSCIENCE);
		setAsString(notSupported(), CACHEDRESULTS, JSCIENCE);
		setAsString(yes(), DENSEAA, JSCIENCE);
		setAsString(notSupported(), DENSEBLOCK, JSCIENCE);
		setAsString(notSupported(), DENSESA, JSCIENCE);
		setAsString(yes(), SPARSELIL, JSCIENCE);
		setAsString(notSupported(), SPARSEYALE, JSCIENCE);
		setAsString(notSupported(), SPARSECDS, JSCIENCE);
		setAsString(notSupported(), SPARSECRS, JSCIENCE);
		setAsString(yes(), SPARSEDOK, JSCIENCE);
		setAsString(yes(), COMPLEX, JSCIENCE);
		setAsString(yes(), DOUBLE, JSCIENCE);
		setAsString(notSupported(), FLOAT, JSCIENCE);
		setAsString(notSupported(), BIGDECIMAL, JSCIENCE);
		setAsString(yes(), D2, JSCIENCE);
		setAsString(notSupported(), D3, JSCIENCE);
		setAsString(notSupported(), D3PLUS, JSCIENCE);
		setAsString(yes() + footnote("f", "flags matrix as transposed"), TRANSPOSE, JSCIENCE);
		setAsString(yes(), SCALE, JSCIENCE);
		setAsString(yes(), PLUSMINUS, JSCIENCE);
		setAsString(yes(), INV, JSCIENCE);
		setAsString(square(), SOLVE, JSCIENCE);
		setAsString(notSupported(), SVD, JSCIENCE);
		setAsString(square() + footnote(NONSINGULARLETTER, NONSINGULARTEXT), LU, JSCIENCE);
		setAsString(notSupported(), QR, JSCIENCE);
		setAsString(notSupported(), EIG, JSCIENCE);
		setAsString(notSupported(), CHOL, JSCIENCE);
		setAsString("org.ujmp.jscience", PACKAGE, JSCIENCE);

		setAsString(small("7.2"), VERSION, MANTISSA);
		setAsString(small("2007"), DATE, MANTISSA);
		setAsString(small("BSD"), LICENCE, MANTISSA);
		setAsString(yes(), JAVA14, MANTISSA);
		setAsString(yes(), JAVA5, MANTISSA);
		setAsString(yes(), JAVA6, MANTISSA);
		setAsString(notSupported(), MULTITHREADED, MANTISSA);
		setAsString(notSupported(), CACHEDRESULTS, MANTISSA);
		setAsString(yes(), INPLACE, MANTISSA);
		setAsString(yes(), TRANSPOSE, MANTISSA);
		setAsString(yes(), PLUSMINUS, MANTISSA);
		setAsString(yes(), SCALE, MANTISSA);
		setAsString(yes(), DENSESA, MANTISSA);
		setAsString(notSupported(), DENSEAA, MANTISSA);
		setAsString(notSupported(), DENSEBLOCK, MANTISSA);
		setAsString(notSupported(), SPARSEDOK, MANTISSA);
		setAsString(notSupported(), SPARSEYALE, MANTISSA);
		setAsString(notSupported(), SPARSELIL, MANTISSA);
		setAsString(notSupported(), SPARSECRS, MANTISSA);
		setAsString(notSupported(), SPARSECDS, MANTISSA);
		setAsString(notSupported(), COMPLEX, MANTISSA);
		setAsString(yes(), DOUBLE, MANTISSA);
		setAsString(notSupported(), FLOAT, MANTISSA);
		setAsString(notSupported(), BIGDECIMAL, MANTISSA);
		setAsString(yes(), D2, MANTISSA);
		setAsString(notSupported(), D3, MANTISSA);
		setAsString(notSupported(), D3PLUS, MANTISSA);
		setAsString(yes(), INV, MANTISSA);
		setAsString(square(), SOLVE, MANTISSA);
		setAsString(notSupported(), SVD, MANTISSA);
		setAsString(square() + footnote("a", "results not directly accessible"), LU, MANTISSA);
		setAsString(notSupported(), QR, MANTISSA);
		setAsString(notSupported(), EIG, MANTISSA);
		setAsString(notSupported(), CHOL, MANTISSA);
		setAsString("org.ujmp.mantissa", PACKAGE, MANTISSA);

		setAsString(small("0.9.12"), VERSION, MTJ);
		setAsString(small("2009"), DATE, MTJ);
		setAsString(small("LGPL"), LICENCE, MTJ);
		setAsString(notSupported(), JAVA14, MTJ);
		setAsString(yes(), JAVA5, MTJ);
		setAsString(yes(), JAVA6, MTJ);
		setAsString(yes() + footnote("m", "using native machine code"), MULTITHREADED, MTJ);
		setAsString(yes(), INPLACE, MTJ);
		setAsString(notSupported(), CACHEDRESULTS, MTJ);
		setAsString(yes(), DENSESA, MTJ);
		setAsString(notSupported(), DENSEAA, MTJ);
		setAsString(notSupported(), DENSEBLOCK, MTJ);
		setAsString(yes(), SPARSECRS, MTJ);
		setAsString(yes(), SPARSECDS, MTJ);
		setAsString(yes(), SPARSELIL, MTJ);
		setAsString(notSupported(), SPARSEDOK, MTJ);
		setAsString(notSupported(), SPARSEYALE, MTJ);
		setAsString(notSupported(), COMPLEX, MTJ);
		setAsString(yes(), DOUBLE, MTJ);
		setAsString(notSupported(), FLOAT, MTJ);
		setAsString(notSupported(), BIGDECIMAL, MTJ);
		setAsString(yes(), D2, MTJ);
		setAsString(notSupported(), D3, MTJ);
		setAsString(notSupported(), D3PLUS, MTJ);
		setAsString(yes(), TRANSPOSE, MTJ);
		setAsString(yes(), SCALE, MTJ);
		setAsString(yes(), PLUSMINUS, MTJ);
		setAsString(yes(), INV, MTJ);
		setAsString(squareTall(), SOLVE, MTJ);
		setAsString(all(), SVD, MTJ);
		setAsString(all() + footnote("e", ERRORTEXT), LU, MTJ);
		setAsString(squareTall(), QR, MTJ);
		setAsString(yes() + footnote("s", "symmetric matrices only"), EIG, MTJ);
		setAsString(yes() + footnote("e", ERRORTEXT), CHOL, MTJ);
		setAsString("org.ujmp.mtj", PACKAGE, MTJ);

		setAsString(small("29.0"), VERSION, OJALGO);
		setAsString(small("2010"), DATE, OJALGO);
		setAsString(small("MIT"), LICENCE, OJALGO);
		setAsString(notSupported(), JAVA14, OJALGO);
		setAsString(yes(), JAVA5, OJALGO);
		setAsString(yes(), JAVA6, OJALGO);
		setAsString(both(), MULTITHREADED, OJALGO);
		setAsString(yes(), INPLACE, OJALGO);
		setAsString(notSupported(), CACHEDRESULTS, OJALGO);
		setAsString(yes() + footnote("f", "flags matrix as transposed"), TRANSPOSE, OJALGO);
		setAsString(yes(), SCALE, OJALGO);
		setAsString(yes(), PLUSMINUS, OJALGO);
		setAsString(yes(), DENSESA, OJALGO);
		setAsString(notSupported(), DENSEAA, OJALGO);
		setAsString(notSupported(), DENSEBLOCK, OJALGO);
		setAsString(notSupported(), SPARSECDS, OJALGO);
		setAsString(notSupported(), SPARSECRS, OJALGO);
		setAsString(notSupported(), SPARSEYALE, OJALGO);
		setAsString(notSupported(), SPARSELIL, OJALGO);
		setAsString(notSupported(), SPARSEDOK, OJALGO);
		setAsString(yes(), COMPLEX, OJALGO);
		setAsString(yes(), DOUBLE, OJALGO);
		setAsString(yes(), FLOAT, OJALGO);
		setAsString(yes(), BIGDECIMAL, OJALGO);
		setAsString(yes(), D2, OJALGO);
		setAsString(notSupported(), D3, OJALGO);
		setAsString(notSupported(), D3PLUS, OJALGO);
		setAsString(yes(), INV, OJALGO);
		setAsString(squareTall(), SOLVE, OJALGO);
		setAsString(all(), SVD, OJALGO);
		setAsString(all(), LU, OJALGO);
		setAsString(all(), QR, OJALGO);
		setAsString(yes(), EIG, OJALGO);
		setAsString(yes(), CHOL, OJALGO);
		setAsString("org.ujmp.ojalgo", PACKAGE, OJALGO);

		setAsString(small("1.3.0"), VERSION, ORBITAL);
		setAsString(small("2009"), DATE, ORBITAL);
		setAsString(small("custom"), LICENCE, ORBITAL);
		setAsString(yes(), JAVA14, ORBITAL);
		setAsString(yes(), JAVA5, ORBITAL);
		setAsString(yes(), JAVA6, ORBITAL);
		setAsString(notSupported(), MULTITHREADED, ORBITAL);
		setAsString(notSupported(), INPLACE, ORBITAL);
		setAsString(notSupported(), CACHEDRESULTS, ORBITAL);
		setAsString(yes(), DENSESA, ORBITAL);
		setAsString(yes(), DENSEAA, ORBITAL);
		setAsString(notSupported(), DENSEBLOCK, ORBITAL);
		setAsString(notSupported(), SPARSECDS, ORBITAL);
		setAsString(notSupported(), SPARSECRS, ORBITAL);
		setAsString(notSupported(), SPARSEDOK, ORBITAL);
		setAsString(notSupported(), SPARSELIL, ORBITAL);
		setAsString(notSupported(), SPARSEYALE, ORBITAL);
		setAsString(yes(), COMPLEX, ORBITAL);
		setAsString(yes(), DOUBLE, ORBITAL);
		setAsString(notSupported(), FLOAT, ORBITAL);
		setAsString(notSupported(), BIGDECIMAL, ORBITAL);
		setAsString(yes(), TRANSPOSE, ORBITAL);
		setAsString(yes(), PLUSMINUS, ORBITAL);
		setAsString(yes(), SCALE, ORBITAL);
		setAsString(yes(), D2, ORBITAL);
		setAsString(yes(), D3, ORBITAL);
		setAsString(yes(), D3PLUS, ORBITAL);
		setAsString(yes(), INV, ORBITAL);
		setAsString(notSupported(), SVD, ORBITAL);
		setAsString(notSupported() + footnote("v", "only for $A \\cdot X = \\mbox{vector}$"),
				SOLVE, ORBITAL);
		setAsString(square() + footnote(NONSINGULARLETTER, NONSINGULARTEXT), LU, ORBITAL);
		setAsString(notSupported(), QR, ORBITAL);
		setAsString(notSupported(), EIG, ORBITAL);
		setAsString(notSupported(), CHOL, ORBITAL);
		setAsString("org.ujmp.orbital", PACKAGE, ORBITAL);

		setAsString(unknown(), VERSION, OWLPACK);
		setAsString(small("1999"), DATE, OWLPACK);
		setAsString(unknown(), LICENCE, OWLPACK);
		setAsString(yes(), JAVA14, OWLPACK);
		setAsString(yes(), JAVA5, OWLPACK);
		setAsString(yes(), JAVA6, OWLPACK);
		setAsString(notSupported(), MULTITHREADED, OWLPACK);
		setAsString(yes(), INPLACE, OWLPACK);
		setAsString(notSupported(), CACHEDRESULTS, OWLPACK);
		setAsString(yes(), DENSEAA, OWLPACK);
		setAsString(notSupported(), DENSESA, OWLPACK);
		setAsString(notSupported(), DENSEBLOCK, OWLPACK);
		setAsString(notSupported(), SPARSECDS, OWLPACK);
		setAsString(notSupported(), SPARSECRS, OWLPACK);
		setAsString(notSupported(), SPARSEYALE, OWLPACK);
		setAsString(notSupported(), SPARSEDOK, OWLPACK);
		setAsString(notSupported(), SPARSELIL, OWLPACK);
		setAsString(yes(), COMPLEX, OWLPACK);
		setAsString(yes(), DOUBLE, OWLPACK);
		setAsString(yes(), FLOAT, OWLPACK);
		setAsString(notSupported(), BIGDECIMAL, OWLPACK);
		setAsString(yes(), TRANSPOSE, OWLPACK);
		setAsString(yes(), PLUSMINUS, OWLPACK);
		setAsString(yes(), SCALE, OWLPACK);
		setAsString(yes(), D2, OWLPACK);
		setAsString(notSupported(), D3, OWLPACK);
		setAsString(notSupported(), D3PLUS, OWLPACK);
		setAsString(notSupported() + footnote("v", "only for $A \\cdot X = \\mbox{vector}$"),
				SOLVE, OWLPACK);
		setAsString(yes() + footnote("e", ERRORTEXT), INV, OWLPACK);
		setAsString(circle() + footnote("e", ERRORTEXT), SVD, OWLPACK);
		setAsString(notSupported(), LU, OWLPACK);
		setAsString(circle() + footnote("u", "unuseable without documentation"), QR, OWLPACK);
		setAsString(notSupported(), EIG, OWLPACK);
		setAsString(notSupported(), CHOL, OWLPACK);
		setAsString("org.ujmp.owlpack", PACKAGE, OWLPACK);

		setAsString(small("0.9.4"), VERSION, PARALLELCOLT);
		setAsString(small("2010"), DATE, PARALLELCOLT);
		setAsString(small("BSD"), LICENCE, PARALLELCOLT);
		setAsString(yes(), JAVA14, PARALLELCOLT);
		setAsString(yes(), JAVA5, PARALLELCOLT);
		setAsString(yes(), JAVA6, PARALLELCOLT);
		setAsString(both(), MULTITHREADED, PARALLELCOLT);
		setAsString(yes(), INPLACE, PARALLELCOLT);
		setAsString(yes() + footnote("f", "flags matrix as transposed"), TRANSPOSE, PARALLELCOLT);
		setAsString(yes(), SCALE, PARALLELCOLT);
		setAsString(yes(), PLUSMINUS, PARALLELCOLT);
		setAsString(notSupported(), CACHEDRESULTS, PARALLELCOLT);
		setAsString(yes(), DENSESA, PARALLELCOLT);
		setAsString(yes(), DENSEAA, PARALLELCOLT);
		setAsString(notSupported(), DENSEBLOCK, PARALLELCOLT);
		setAsString(yes(), SPARSECRS, PARALLELCOLT);
		setAsString(yes(), SPARSELIL, PARALLELCOLT);
		setAsString(yes(), SPARSEDOK, PARALLELCOLT);
		setAsString(notSupported(), SPARSEYALE, PARALLELCOLT);
		setAsString(notSupported(), SPARSECDS, PARALLELCOLT);
		setAsString(yes(), COMPLEX, PARALLELCOLT);
		setAsString(yes(), DOUBLE, PARALLELCOLT);
		setAsString(yes(), FLOAT, PARALLELCOLT);
		setAsString(notSupported(), BIGDECIMAL, PARALLELCOLT);
		setAsString(yes(), D2, PARALLELCOLT);
		setAsString(yes(), D3, PARALLELCOLT);
		setAsString(notSupported(), D3PLUS, PARALLELCOLT);
		setAsString(yes(), INV, PARALLELCOLT);
		setAsString(squareTall(), SOLVE, PARALLELCOLT);
		setAsString(all(), SVD, PARALLELCOLT);
		setAsString(squareTall(), LU, PARALLELCOLT);
		setAsString(squareTall(), QR, PARALLELCOLT);
		setAsString(yes(), EIG, PARALLELCOLT);
		setAsString(yes(), CHOL, PARALLELCOLT);
		setAsString("org.ujmp.parallelcolt", PACKAGE, PARALLELCOLT);

		setAsString(small("1.10"), VERSION, SST);
		setAsString(small("2009"), DATE, SST);
		setAsString(small("LGPL"), LICENCE, SST);
		setAsString(notSupported(), JAVA14, SST);
		setAsString(yes() + footnote("j", "jar does not work with Java 5"), JAVA5, SST);
		setAsString(yes(), JAVA6, SST);
		setAsString(notSupported(), MULTITHREADED, SST);
		setAsString(yes(), INPLACE, SST);
		setAsString(yes(), TRANSPOSE, SST);
		setAsString(yes(), SCALE, SST);
		setAsString(yes(), PLUSMINUS, SST);
		setAsString(notSupported(), CACHEDRESULTS, SST);
		setAsString(yes(), DENSESA, SST);
		setAsString(notSupported(), DENSEAA, SST);
		setAsString(notSupported(), DENSEBLOCK, SST);
		setAsString(yes(), SPARSEDOK, SST);
		setAsString(notSupported(), SPARSELIL, SST);
		setAsString(notSupported(), SPARSEYALE, SST);
		setAsString(notSupported(), SPARSECDS, SST);
		setAsString(notSupported(), SPARSECRS, SST);
		setAsString(yes(), COMPLEX, SST);
		setAsString(yes(), DOUBLE, SST);
		setAsString(notSupported(), FLOAT, SST);
		setAsString(notSupported(), BIGDECIMAL, SST);
		setAsString(yes(), D2, SST);
		setAsString(yes(), D3, SST);
		setAsString(yes(), D3PLUS, SST);
		setAsString(yes(), INV, SST);
		setAsString(notSupported(), SOLVE, SST);
		setAsString(all(), SVD, SST);
		setAsString(notSupported(), LU, SST);
		setAsString(notSupported(), QR, SST);
		setAsString(yes(), EIG, SST);
		setAsString(notSupported(), CHOL, SST);
		setAsString("org.ujmp.sst", PACKAGE, SST);

		setAsString(small("1.5.1"), VERSION, VECMATH);
		setAsString(small("2007"), DATE, VECMATH);
		setAsString(small("other"), LICENCE, VECMATH);
		setAsString(notSupported(), JAVA14, VECMATH);
		setAsString(yes(), JAVA5, VECMATH);
		setAsString(yes(), JAVA6, VECMATH);
		setAsString(notSupported(), MULTITHREADED, VECMATH);
		setAsString(yes(), INPLACE, VECMATH);
		setAsString(yes(), TRANSPOSE, VECMATH);
		setAsString(notSupported(), SCALE, VECMATH);
		setAsString(yes(), PLUSMINUS, VECMATH);
		setAsString(notSupported(), CACHEDRESULTS, VECMATH);
		setAsString(yes(), DENSEAA, VECMATH);
		setAsString(notSupported(), DENSESA, VECMATH);
		setAsString(notSupported(), DENSEBLOCK, VECMATH);
		setAsString(notSupported(), SPARSEDOK, VECMATH);
		setAsString(notSupported(), SPARSELIL, VECMATH);
		setAsString(notSupported(), SPARSECDS, VECMATH);
		setAsString(notSupported(), SPARSECRS, VECMATH);
		setAsString(notSupported(), SPARSEYALE, VECMATH);
		setAsString(notSupported(), COMPLEX, VECMATH);
		setAsString(yes(), DOUBLE, VECMATH);
		setAsString(notSupported(), FLOAT, VECMATH);
		setAsString(notSupported(), BIGDECIMAL, VECMATH);
		setAsString(yes(), D2, VECMATH);
		setAsString(notSupported(), D3, VECMATH);
		setAsString(notSupported(), D3PLUS, VECMATH);
		setAsString(yes(), INV, VECMATH);
		setAsString(notSupported(), SOLVE, VECMATH);
		setAsString(circle() + footnote("e", ERRORTEXT), SVD, VECMATH);
		setAsString(square() + footnote(NONSINGULARLETTER, NONSINGULARTEXT), LU, VECMATH);
		setAsString(notSupported(), QR, VECMATH);
		setAsString(notSupported(), EIG, VECMATH);
		setAsString(notSupported(), CHOL, VECMATH);
		setAsString("org.ujmp.vecmath", PACKAGE, VECMATH);

	}

	public String yes() {
		switch (format) {
		case LATEX:
			return "\\bf{$+$}";
		default:
			return "yes";
		}
	}

	public String both() {
		switch (format) {
		case LATEX:
			return "\\bf{$+/-$}";
		default:
			return "configurable";
		}
	}

	public String all() {
		switch (format) {
		case LATEX:
			return "\\scalebox{0.6}[1.0]{$\\square$}\\,$\\square$\\,\\scalebox{1.0}[0.6]{$\\square$}";
		default:
			return "all";
		}
	}

	public String squareTall() {
		switch (format) {
		case LATEX:
			return "\\scalebox{0.6}[1.0]{$\\square$}\\,$\\square$";
		default:
			return "square, tall";
		}
	}

	public String square() {
		switch (format) {
		case LATEX:
			return "$\\square$";
		default:
			return "square";
		}
	}

	public String tall() {
		switch (format) {
		case LATEX:
			return "$\\tall$";
		default:
			return "tall";
		}
	}

	public String fat() {
		switch (format) {
		case LATEX:
			return "$\\fat$";
		default:
			return "fat";
		}
	}

	public String circle() {
		switch (format) {
		case LATEX:
			return "$\\circ$";
		default:
			return "square";
		}
	}

	public String notSupported() {
		switch (format) {
		case LATEX:
			return "\\bf{$-$}";
		default:
			return "yes";
		}
	}

	public String unknown() {
		switch (format) {
		default:
			return "?";
		}
	}

	public String turn(String text) {
		switch (format) {
		case LATEX:
			return "\\begin{turn}{90}" + text + "\\end{turn}";
		default:
			return "";
		}
	}

	public String small(String text) {
		switch (format) {
		case LATEX:
			return "\\small " + text;
		default:
			return text;
		}
	}

	public String footnote(String footnote, String text) {
		switch (format) {
		case LATEX:
			String f = "\\footnotesize{$^\\mathrm{" + footnote + "}$ " + text + "}";
			if (!footnotes.contains(f)) {
				footnotes.add(f);
			}
			return "$^{\\mathrm{" + footnote + "}}$";
		default:
			f = footnote + " " + text;
			if (!footnotes.contains(f)) {
				footnotes.add(f);
			}
			return text;
		}
	}

	public List<String> getFootnotes() {
		return footnotes;
	}

	public static void main(String[] args) throws Exception {
		MatrixLibraries ml = new MatrixLibraries(MatrixLibrariesFormat.LATEX);
		Matrix m = ml.deleteRows(Ret.NEW, ml.getRowCount() - 1);
		String s = m.exportToString(FileFormat.TEX);
		s = s.replaceAll("table", "sidewaystable");
		s = s.replaceAll("\\\\centering", "");
		s = s.replaceAll("\\\\toprule", "");
		s = s
				.replaceAll(
						"\\\\begin\\{tabular\\}",
						"\\\\caption{Overview of matrix libraries in Java}\n\\\\bigskip\n\\\\begin{centering}\n\\\\scalebox{0.8}{%\n\\\\begin{tabular}");
		s = s.replaceAll("latest version", "\\\\toprule\nlatest version");
		s = s.replaceAll("\\\\end\\{sidewaystable\\}", "");
		s = s.replaceAll("\\\\end\\{tabular\\}", "\\\\end{tabular}}\n\\\\end{centering}");
		s = s.replaceAll("version &", "\\\\toprule\nversion &");
		s = s.replaceAll("Java 1.4 &", "\\\\midrule\nJava 1.4 &");
		s = s.replaceAll("multithreaded &", "\\\\midrule\nmultithreaded &");
		s = s.replaceAll("dense SA", "\\\\midrule\ndense SA");
		s = s.replaceAll("sparse DOK", "\\\\midrule\nsparse DOK");
		s = s.replaceAll("double &", "\\\\midrule\ndouble &");
		s = s.replaceAll("2D &", "\\\\midrule\n2D &");
		s = s.replaceAll("transpose &", "\\\\midrule\ntranspose &");
		s = s.replaceAll("inverse &", "\\\\midrule\ninverse &");
		s = s + "\\medskip\n";
		s = s
				+ "\\begin{tabular}{p{0.25\\textwidth}p{0.25\\textwidth}p{0.25\\textwidth}p{0.25\\textwidth}}\n";
		s += "\\renewcommand{\\tabcolsep}{30pt}";
		List<String> fn = ml.getFootnotes();
		Collections.sort(fn);
		for (int i = 0; i < fn.size(); i++) {
			String f = fn.get(i);
			s = s + f;
			if (i % 4 == 3) {
				s = s + "\\\\\n";
			} else {
				s = s + " & ";
			}
		}
		s = s + "\\end{tabular}\n";
		s = s + "\\end{sidewaystable}";
		System.out.println(s);
	}

	public long getColumnForPackage(String label) {
		for (long c = getColumnCount(); --c != -1;) {
			String p = getAsString(PACKAGE, c);
			if (label.equals(p)) {
				return c;
			}
		}
		return -1;
	}
}
