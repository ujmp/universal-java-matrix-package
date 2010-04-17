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

import org.ujmp.core.enums.FileFormat;
import org.ujmp.core.stringmatrix.impl.DefaultDenseStringMatrix2D;

public class MatrixLibraries extends DefaultDenseStringMatrix2D {
	private static final long serialVersionUID = -2575195318248762416L;

	private static final int LABELCOLUMN = 0;

	private static final int VERSION = 1;

	private static final int DATE = 2;

	private static final int LICENCE = 3;

	private static final int JAVA14 = 4;

	private static final int JAVA5 = 5;

	private static final int JAVA6 = 6;

	private static final int DENSESA = 7;

	private static final int DENSEAA = 8;

	private static final int DENSEBLOCK = 9;

	private static final int SPARSEDOK = 10;

	private static final int SPARSELIL = 11;

	private static final int SPARSEYALE = 12;

	private static final int SPARSECRS = 13;

	private static final int SPARSECDS = 14;

	private static final int DOUBLE = 15;

	private static final int FLOAT = 16;

	private static final int BIGDECIMAL = 17;

	private static final int COMPLEX = 18;

	private static final int D2 = 19;

	private static final int D3 = 20;

	private static final int D3PLUS = 21;

	private static final int MULTITHREADED = 22;

	private static final int INPLACE = 23;

	private static final int CACHEDRESULTS = 24;

	private static final int TRANSPOSE = 25;

	private static final int SCALE = 26;

	private static final int PLUSMINUS = 27;

	private static final int INV = 28;

	private static final int SOLVE = 29;

	private static final int LU = 30;

	private static final int QR = 31;

	private static final int SVD = 32;

	private static final int CHOL = 33;

	private static final int EIG = 34;

	private static final int LABELROW = 0;

	private static final int ARRAY4J = 1;

	private static final int COLT = 2;

	private static final int COMMONSMATH = 3;

	private static final int EJML = 4;

	private static final int JAMA = 5;

	private static final int JAMPACK = 6;

	private static final int JBLAS = 7;

	private static final int JLINALG = 8;

	private static final int JMATHARRAY = 9;

	private static final int JMATRICES = 10;

	private static final int JSCI = 11;

	private static final int JSCIENCE = 12;

	private static final int MANTISSA = 13;

	private static final int MTJ = 14;

	private static final int OJALGO = 15;

	private static final int ORBITAL = 16;

	private static final int OWLPACK = 17;

	private static final int PARALLELCOLT = 18;

	private static final int SST = 19;

	private static final int UJMP = 20;

	private static final int VECMATH = 21;

	private List<String> footnotes = new ArrayList<String>();

	public enum MatrixLibrariesFormat {
		DEFAULT, LATEX, HTML
	};

	private MatrixLibrariesFormat format = MatrixLibrariesFormat.DEFAULT;

	public MatrixLibraries() {
		this(MatrixLibrariesFormat.DEFAULT);
	}

	public MatrixLibraries(MatrixLibrariesFormat format) {
		super(35, 22);
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

		setAsString(org.ujmp.core.UJMP.UJMPVERSION, VERSION, UJMP);
		setAsString(small("2010"), DATE, UJMP);
		setAsString(small("LGPL"), LICENCE, UJMP);
		setAsString(no(), JAVA14, UJMP);
		setAsString(yes(), JAVA5, UJMP);
		setAsString(yes(), JAVA6, UJMP);
		setAsString(both(), MULTITHREADED, UJMP);
		setAsString(yes(), INPLACE, UJMP);
		setAsString(no(), CACHEDRESULTS, UJMP);
		setAsString(yes(), DENSEAA, UJMP);
		setAsString(yes(), DENSESA, UJMP);
		setAsString(yes(), DENSEBLOCK, UJMP);
		setAsString(yes(), SPARSEDOK, UJMP);
		setAsString(no(), SPARSEYALE, UJMP);
		setAsString(yes(), SPARSELIL, UJMP);
		setAsString(no(), SPARSECRS, UJMP);
		setAsString(no(), SPARSECDS, UJMP);
		setAsString(no(), COMPLEX, UJMP);
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

		setAsString(small("SVN"), VERSION, ARRAY4J);
		setAsString(small("2008"), DATE, ARRAY4J);
		setAsString(small("BSD"), LICENCE, ARRAY4J);
		setAsString(no(), JAVA14, ARRAY4J);
		setAsString(no(), JAVA5, ARRAY4J);
		setAsString(yes(), JAVA6, ARRAY4J);
		setAsString(unknown() + footnote("m", "using native machine code"), MULTITHREADED, ARRAY4J);
		setAsString(yes(), INPLACE, ARRAY4J);
		setAsString(yes(), DENSESA, ARRAY4J);
		setAsString(no(), DENSEAA, ARRAY4J);
		setAsString(no(), DENSEBLOCK, ARRAY4J);
		setAsString(no() + footnote("i", "interface only, no implementation"), SPARSEDOK, ARRAY4J);
		setAsString(no() + footnote("i", "interface only, no implementation"), SPARSEYALE, ARRAY4J);
		setAsString(no() + footnote("i", "interface only, no implementation"), SPARSELIL, ARRAY4J);
		setAsString(no() + footnote("i", "interface only, no implementation"), SPARSECRS, ARRAY4J);
		setAsString(no() + footnote("i", "interface only, no implementation"), SPARSECDS, ARRAY4J);
		setAsString(no() + footnote("i", "interface only, no implementation"), COMPLEX, ARRAY4J);
		setAsString(no() + footnote("i", "interface only, no implementation"), DOUBLE, ARRAY4J);
		setAsString(yes(), FLOAT, ARRAY4J);
		setAsString(no(), BIGDECIMAL, ARRAY4J);
		setAsString(no(), MULTITHREADED, ARRAY4J);
		setAsString(yes(), INPLACE, ARRAY4J);
		setAsString(no(), CACHEDRESULTS, ARRAY4J);
		setAsString(yes(), PLUSMINUS, ARRAY4J);
		setAsString(yes(), SCALE, ARRAY4J);
		setAsString(yes(), TRANSPOSE, ARRAY4J);
		setAsString(yes(), D2, ARRAY4J);
		setAsString(no(), D3, ARRAY4J);
		setAsString(no(), D3PLUS, ARRAY4J);
		setAsString(no(), SOLVE, ARRAY4J);
		setAsString(no(), INV, ARRAY4J);
		setAsString(no(), SVD, ARRAY4J);
		setAsString(no(), QR, ARRAY4J);
		setAsString(no(), LU, ARRAY4J);
		setAsString(no(), EIG, ARRAY4J);
		setAsString(no(), CHOL, ARRAY4J);

		setAsString(small("1.2.0"), VERSION, COLT);
		setAsString(small("2004"), DATE, COLT);
		setAsString(small("BSD"), LICENCE, COLT);
		setAsString(yes(), JAVA14, COLT);
		setAsString(yes(), JAVA5, COLT);
		setAsString(yes(), JAVA6, COLT);
		setAsString(no(), MULTITHREADED, COLT);
		setAsString(yes(), INPLACE, COLT);
		setAsString(no(), CACHEDRESULTS, COLT);
		setAsString(yes() + footnote("f", "flags matrix as transposed"), TRANSPOSE, COLT);
		setAsString(yes(), SCALE, COLT);
		setAsString(yes(), PLUSMINUS, COLT);
		setAsString(yes(), DENSESA, COLT);
		setAsString(no(), DENSEAA, COLT);
		setAsString(no(), DENSEBLOCK, COLT);
		setAsString(yes(), SPARSEDOK, COLT);
		setAsString(no(), SPARSEYALE, COLT);
		setAsString(no(), SPARSELIL, COLT);
		setAsString(no(), SPARSECRS, COLT);
		setAsString(no(), SPARSECDS, COLT);
		setAsString(no(), COMPLEX, COLT);
		setAsString(yes(), DOUBLE, COLT);
		setAsString(no(), FLOAT, COLT);
		setAsString(no(), BIGDECIMAL, COLT);
		setAsString(yes(), D2, COLT);
		setAsString(yes(), D3, COLT);
		setAsString(no(), D3PLUS, COLT);
		setAsString(yes(), INV, COLT);
		setAsString(squareTall(), SOLVE, COLT);
		setAsString(squareTall(), SVD, COLT);
		setAsString(squareTall(), LU, COLT);
		setAsString(all(), QR, COLT);
		setAsString(yes(), EIG, COLT);
		setAsString(yes(), CHOL, COLT);

		setAsString(small("2.1"), VERSION, COMMONSMATH);
		setAsString(small("2010"), DATE, COMMONSMATH);
		setAsString(small("Apache"), LICENCE, COMMONSMATH);
		setAsString(no(), JAVA14, COMMONSMATH);
		setAsString(yes(), JAVA5, COMMONSMATH);
		setAsString(yes(), JAVA6, COMMONSMATH);
		setAsString(no(), MULTITHREADED, COMMONSMATH);
		setAsString(yes(), INPLACE, COMMONSMATH);
		setAsString(no(), CACHEDRESULTS, COMMONSMATH);
		setAsString(yes(), TRANSPOSE, COMMONSMATH);
		setAsString(yes(), SCALE, COMMONSMATH);
		setAsString(yes(), PLUSMINUS, COMMONSMATH);
		setAsString(yes(), DENSEAA, COMMONSMATH);
		setAsString(no(), DENSESA, COMMONSMATH);
		setAsString(yes(), DENSEBLOCK, COMMONSMATH);
		setAsString(yes(), SPARSEDOK, COMMONSMATH);
		setAsString(no(), SPARSEYALE, COMMONSMATH);
		setAsString(no(), SPARSELIL, COMMONSMATH);
		setAsString(no(), SPARSECRS, COMMONSMATH);
		setAsString(no(), SPARSECDS, COMMONSMATH);
		setAsString(yes(), COMPLEX, COMMONSMATH);
		setAsString(yes(), DOUBLE, COMMONSMATH);
		setAsString(no(), FLOAT, COMMONSMATH);
		setAsString(yes(), BIGDECIMAL, COMMONSMATH);
		setAsString(yes(), D2, COMMONSMATH);
		setAsString(no(), D3, COMMONSMATH);
		setAsString(no(), D3PLUS, COMMONSMATH);
		setAsString(yes(), INV, COMMONSMATH);
		setAsString(squareTall(), SOLVE, COMMONSMATH);
		setAsString(all(), SVD, COMMONSMATH);
		setAsString(square() + footnote("n", "non-singular matrices only"), LU, COMMONSMATH);
		setAsString(all(), QR, COMMONSMATH);
		setAsString(yes() + footnote("s", "symmetric matrices only"), EIG, COMMONSMATH);
		setAsString(yes(), CHOL, COMMONSMATH);

		setAsString(small("0.11"), VERSION, EJML);
		setAsString(small("2010"), DATE, EJML);
		setAsString(small("LGPL"), LICENCE, EJML);
		setAsString(no(), JAVA14, EJML);
		setAsString(no(), JAVA5, EJML);
		setAsString(yes(), JAVA6, EJML);
		setAsString(no(), MULTITHREADED, EJML);
		setAsString(no(), CACHEDRESULTS, EJML);
		setAsString(yes(), INPLACE, EJML);
		setAsString(yes(), DENSESA, EJML);
		setAsString(no(), DENSEAA, EJML);
		setAsString(yes(), DENSEBLOCK, EJML);
		setAsString(no(), SPARSEDOK, EJML);
		setAsString(no(), SPARSEYALE, EJML);
		setAsString(no(), SPARSELIL, EJML);
		setAsString(no(), SPARSECRS, EJML);
		setAsString(no(), SPARSECDS, EJML);
		setAsString(no(), COMPLEX, EJML);
		setAsString(yes(), DOUBLE, EJML);
		setAsString(no(), FLOAT, EJML);
		setAsString(no(), BIGDECIMAL, EJML);
		setAsString(yes(), D2, EJML);
		setAsString(no(), D3, EJML);
		setAsString(no(), D3PLUS, EJML);
		setAsString(yes(), TRANSPOSE, EJML);
		setAsString(yes(), SCALE, EJML);
		setAsString(yes(), PLUSMINUS, EJML);
		setAsString(yes(), INV, EJML);
		setAsString(squareTall(), SOLVE, EJML);
		setAsString(all(), SVD, EJML);
		setAsString(all(), LU, EJML);
		setAsString(squareTall(), QR, EJML);
		setAsString(yes(), EIG, EJML);
		setAsString(yes(), CHOL, EJML);

		setAsString(small("1.0.2"), VERSION, JAMA);
		setAsString(small("2005"), DATE, JAMA);
		setAsString(small("PD"), LICENCE, JAMA);
		setAsString(yes(), JAVA14, JAMA);
		setAsString(yes(), JAVA5, JAMA);
		setAsString(yes(), JAVA6, JAMA);
		setAsString(no(), MULTITHREADED, JAMA);
		setAsString(no(), INPLACE, JAMA);
		setAsString(no(), CACHEDRESULTS, JAMA);
		setAsString(yes(), DENSEAA, JAMA);
		setAsString(no(), DENSESA, JAMA);
		setAsString(no(), DENSEBLOCK, JAMA);
		setAsString(no(), SPARSEDOK, JAMA);
		setAsString(no(), SPARSEYALE, JAMA);
		setAsString(no(), SPARSELIL, JAMA);
		setAsString(no(), SPARSECRS, JAMA);
		setAsString(no(), SPARSECDS, JAMA);
		setAsString(no(), COMPLEX, JAMA);
		setAsString(yes(), DOUBLE, JAMA);
		setAsString(no(), FLOAT, JAMA);
		setAsString(no(), BIGDECIMAL, JAMA);
		setAsString(yes(), D2, JAMA);
		setAsString(no(), D3, JAMA);
		setAsString(no(), D3PLUS, JAMA);
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

		setAsString(small("?"), VERSION, JAMPACK);
		setAsString(small("1999"), DATE, JAMPACK);
		setAsString(small("?"), LICENCE, JAMPACK);
		setAsString(yes(), JAVA14, JAMPACK);
		setAsString(yes(), JAVA5, JAMPACK);
		setAsString(yes(), JAVA6, JAMPACK);
		setAsString(no(), MULTITHREADED, JAMPACK);
		setAsString(no(), INPLACE, JAMPACK);
		setAsString(yes(), DENSEAA, JAMPACK);
		setAsString(no(), DENSESA, JAMPACK);
		setAsString(no(), DENSEBLOCK, JAMPACK);
		setAsString(no(), SPARSEDOK, JAMPACK);
		setAsString(no(), SPARSEYALE, JAMPACK);
		setAsString(no(), SPARSELIL, JAMPACK);
		setAsString(no(), SPARSECRS, JAMPACK);
		setAsString(no(), SPARSECDS, JAMPACK);
		setAsString(yes(), COMPLEX, JAMPACK);
		setAsString(yes(), DOUBLE, JAMPACK);
		setAsString(no(), FLOAT, JAMPACK);
		setAsString(no(), BIGDECIMAL, JAMPACK);
		setAsString(yes(), D2, JAMPACK);
		setAsString(no(), D3, JAMPACK);
		setAsString(no(), D3PLUS, JAMPACK);
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
		setAsString(yes(), CHOL, JAMPACK);

		setAsString(small("1.0.2"), VERSION, JBLAS);
		setAsString(small("2010"), DATE, JBLAS);
		setAsString(small("BSD"), LICENCE, JBLAS);
		setAsString(no(), JAVA14, JBLAS);
		setAsString(yes(), JAVA5, JBLAS);
		setAsString(yes(), JAVA6, JBLAS);
		setAsString(yes() + footnote("m", "using native machine code"), MULTITHREADED, JBLAS);
		setAsString(yes(), INPLACE, JBLAS);
		setAsString(no(), CACHEDRESULTS, JBLAS);
		setAsString(yes(), DENSESA, JBLAS);
		setAsString(no(), DENSEAA, JBLAS);
		setAsString(no(), DENSEBLOCK, JBLAS);
		setAsString(no(), SPARSEDOK, JBLAS);
		setAsString(no(), SPARSEYALE, JBLAS);
		setAsString(no(), SPARSELIL, JBLAS);
		setAsString(no(), SPARSECRS, JBLAS);
		setAsString(no(), SPARSECDS, JBLAS);
		setAsString(yes(), COMPLEX, JBLAS);
		setAsString(yes(), DOUBLE, JBLAS);
		setAsString(yes(), FLOAT, JBLAS);
		setAsString(no(), BIGDECIMAL, JBLAS);
		setAsString(yes(), D2, JBLAS);
		setAsString(no(), D3, JBLAS);
		setAsString(no(), D3PLUS, JBLAS);
		setAsString(yes(), TRANSPOSE, JBLAS);
		setAsString(yes(), SCALE, JBLAS);
		setAsString(yes(), PLUSMINUS, JBLAS);
		setAsString(yes(), INV, JBLAS);
		setAsString(square(), SOLVE, JBLAS);
		setAsString(no(), SVD, JBLAS);
		setAsString(all(), LU, JBLAS);
		setAsString(no(), QR, JBLAS);
		setAsString(yes() + footnote("s", "symmetric matrices only"), EIG, JBLAS);
		setAsString(yes(), CHOL, JBLAS);

		setAsString(small("0.6"), VERSION, JLINALG);
		setAsString(small("2009"), DATE, JLINALG);
		setAsString(small("GPL"), LICENCE, JLINALG);
		setAsString(no(), JAVA14, JLINALG);
		setAsString(no(), JAVA5, JLINALG);
		setAsString(yes(), JAVA6, JLINALG);
		setAsString(no(), MULTITHREADED, JLINALG);
		setAsString(no(), CACHEDRESULTS, JLINALG);
		setAsString(yes(), INPLACE, JLINALG);
		setAsString(yes(), DENSEAA, JLINALG);
		setAsString(no(), DENSESA, JLINALG);
		setAsString(no(), DENSEBLOCK, JLINALG);
		setAsString(no(), SPARSEDOK, JLINALG);
		setAsString(no(), SPARSEYALE, JLINALG);
		setAsString(no(), SPARSELIL, JLINALG);
		setAsString(no(), SPARSECRS, JLINALG);
		setAsString(no(), SPARSECDS, JLINALG);
		setAsString(yes(), COMPLEX, JLINALG);
		setAsString(yes(), DOUBLE, JLINALG);
		setAsString(no(), FLOAT, JLINALG);
		setAsString(yes(), BIGDECIMAL, JLINALG);
		setAsString(yes(), TRANSPOSE, JLINALG);
		setAsString(yes(), SCALE, JLINALG);
		setAsString(yes(), PLUSMINUS, JLINALG);
		setAsString(yes(), D2, JLINALG);
		setAsString(no(), D3, JLINALG);
		setAsString(no(), D3PLUS, JLINALG);
		setAsString(yes(), INV, JLINALG);
		setAsString(no() + footnote("v", "only for $A \\cdot X = \\mbox{vector}$"), SOLVE, JLINALG);
		setAsString(no(), SVD, JLINALG);
		setAsString(no(), LU, JLINALG);
		setAsString(no(), QR, JLINALG);
		setAsString(no(), EIG, JLINALG);
		setAsString(no(), CHOL, JLINALG);

		setAsString(small("?"), VERSION, JMATHARRAY);
		setAsString(small("2009"), DATE, JMATHARRAY);
		setAsString(small("BSD"), LICENCE, JMATHARRAY);
		setAsString(no(), JAVA14, JMATHARRAY);
		setAsString(yes(), JAVA5, JMATHARRAY);
		setAsString(yes(), JAVA6, JMATHARRAY);
		setAsString(no(), MULTITHREADED, JMATHARRAY);
		setAsString(no(), INPLACE, JMATHARRAY);
		setAsString(no(), CACHEDRESULTS, JMATHARRAY);
		setAsString(yes(), TRANSPOSE, JMATHARRAY);
		setAsString(yes(), SCALE, JMATHARRAY);
		setAsString(yes(), PLUSMINUS, JMATHARRAY);
		setAsString(yes(), DENSEAA, JMATHARRAY);
		setAsString(no(), DENSESA, JMATHARRAY);
		setAsString(no(), DENSEBLOCK, JMATHARRAY);
		setAsString(no(), SPARSEDOK, JMATHARRAY);
		setAsString(no(), SPARSEYALE, JMATHARRAY);
		setAsString(no(), SPARSELIL, JMATHARRAY);
		setAsString(no(), SPARSECRS, JMATHARRAY);
		setAsString(no(), SPARSECDS, JMATHARRAY);
		setAsString(no(), COMPLEX, JMATHARRAY);
		setAsString(yes(), DOUBLE, JMATHARRAY);
		setAsString(no(), FLOAT, JMATHARRAY);
		setAsString(no(), BIGDECIMAL, JMATHARRAY);
		setAsString(yes(), D2, JMATHARRAY);
		setAsString(no(), D3, JMATHARRAY);
		setAsString(no(), D3PLUS, JMATHARRAY);
		setAsString(yes(), INV, JMATHARRAY);
		setAsString(squareTall(), SOLVE, JMATHARRAY);
		setAsString(squareTall(), SVD, JMATHARRAY);
		setAsString(squareTall(), LU, JMATHARRAY);
		setAsString(squareTall(), QR, JMATHARRAY);
		setAsString(yes(), EIG, JMATHARRAY);
		setAsString(yes(), CHOL, JMATHARRAY);

		setAsString(small("0.6"), VERSION, JMATRICES);
		setAsString(small("2004"), DATE, JMATRICES);
		setAsString(small("LGPL"), LICENCE, JMATRICES);
		setAsString(yes(), JAVA14, JMATRICES);
		setAsString(yes(), JAVA5, JMATRICES);
		setAsString(yes(), JAVA6, JMATRICES);
		setAsString(no(), MULTITHREADED, JMATRICES);
		setAsString(no(), INPLACE, JMATRICES);
		setAsString(no(), CACHEDRESULTS, JMATRICES);
		setAsString(yes(), DENSEAA, JMATRICES);
		setAsString(no(), DENSESA, JMATRICES);
		setAsString(no(), DENSEBLOCK, JMATRICES);
		setAsString(no(), SPARSEDOK, JMATRICES);
		setAsString(no(), SPARSEYALE, JMATRICES);
		setAsString(no(), SPARSELIL, JMATRICES);
		setAsString(no(), SPARSECRS, JMATRICES);
		setAsString(no(), SPARSECDS, JMATRICES);
		setAsString(yes(), COMPLEX, JMATRICES);
		setAsString(yes(), DOUBLE, JMATRICES);
		setAsString(no(), FLOAT, JMATRICES);
		setAsString(yes(), BIGDECIMAL, JMATRICES);
		setAsString(yes(), D2, JMATRICES);
		setAsString(no(), D3, JMATRICES);
		setAsString(no(), D3PLUS, JMATRICES);
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

		setAsString(small("1.1"), VERSION, JSCI);
		setAsString(small("2009"), DATE, JSCI);
		setAsString(small("LGPL"), LICENCE, JSCI);
		setAsString(yes(), JAVA14, JSCI);
		setAsString(yes(), JAVA5, JSCI);
		setAsString(yes(), JAVA6, JSCI);
		setAsString(no(), MULTITHREADED, JSCI);
		setAsString(no(), INPLACE, JSCI);
		setAsString(no(), CACHEDRESULTS, JSCI);
		setAsString(yes(), DENSEAA, JSCI);
		setAsString(no(), DENSESA, JSCI);
		setAsString(no(), DENSEBLOCK, JSCI);
		setAsString(yes(), SPARSEYALE, JSCI);
		setAsString(no(), SPARSEDOK, JSCI);
		setAsString(no(), SPARSELIL, JSCI);
		setAsString(no(), SPARSECRS, JSCI);
		setAsString(no(), SPARSECDS, JSCI);
		setAsString(yes(), COMPLEX, JSCI);
		setAsString(yes(), DOUBLE, JSCI);
		setAsString(no(), FLOAT, JSCI);
		setAsString(no(), BIGDECIMAL, JSCI);
		setAsString(yes(), D2, JSCI);
		setAsString(no(), D3, JSCI);
		setAsString(no(), D3PLUS, JSCI);
		setAsString(yes(), TRANSPOSE, JSCI);
		setAsString(yes(), PLUSMINUS, JSCI);
		setAsString(yes(), SCALE, JSCI);
		setAsString(no() + footnote("v", "only for $A \\cdot X = \\mbox{vector}$"), SOLVE, JSCI);
		setAsString(yes(), INV, JSCI);
		setAsString(square(), SVD, JSCI);
		setAsString(square() + footnote("n", "non-singular matrices only"), LU, JSCI);
		setAsString(square(), QR, JSCI);
		setAsString(yes() + footnote("s", "symmetric matrices only")
				+ footnote("a", "results not directly accessible"), EIG, JSCI);
		setAsString(yes(), CHOL, JSCI);

		setAsString(small("4.3.1"), VERSION, JSCIENCE);
		setAsString(small("2007"), DATE, JSCIENCE);
		setAsString(small("BSD"), LICENCE, JSCIENCE);
		setAsString(no(), JAVA14, JSCIENCE);
		setAsString(yes(), JAVA5, JSCIENCE);
		setAsString(yes(), JAVA6, JSCIENCE);
		setAsString(yes(), MULTITHREADED, JSCIENCE);
		setAsString(no(), INPLACE, JSCIENCE);
		setAsString(no(), CACHEDRESULTS, JSCIENCE);
		setAsString(yes(), DENSEAA, JSCIENCE);
		setAsString(no(), DENSEBLOCK, JSCIENCE);
		setAsString(no(), DENSESA, JSCIENCE);
		setAsString(yes(), SPARSELIL, JSCIENCE);
		setAsString(no(), SPARSEYALE, JSCIENCE);
		setAsString(no(), SPARSECDS, JSCIENCE);
		setAsString(no(), SPARSECRS, JSCIENCE);
		setAsString(yes(), SPARSEDOK, JSCIENCE);
		setAsString(yes(), COMPLEX, JSCIENCE);
		setAsString(yes(), DOUBLE, JSCIENCE);
		setAsString(no(), FLOAT, JSCIENCE);
		setAsString(no(), BIGDECIMAL, JSCIENCE);
		setAsString(yes(), D2, JSCIENCE);
		setAsString(no(), D3, JSCIENCE);
		setAsString(no(), D3PLUS, JSCIENCE);
		setAsString(yes() + footnote("f", "flags matrix as transposed"), TRANSPOSE, JSCIENCE);
		setAsString(yes(), SCALE, JSCIENCE);
		setAsString(yes(), PLUSMINUS, JSCIENCE);
		setAsString(yes(), INV, JSCIENCE);
		setAsString(square(), SOLVE, JSCIENCE);
		setAsString(no(), SVD, JSCIENCE);
		setAsString(square() + footnote("n", "non-singular matrices only"), LU, JSCIENCE);
		setAsString(no(), QR, JSCIENCE);
		setAsString(no(), EIG, JSCIENCE);
		setAsString(no(), CHOL, JSCIENCE);

		setAsString(small("7.2"), VERSION, MANTISSA);
		setAsString(small("2007"), DATE, MANTISSA);
		setAsString(small("BSD"), LICENCE, MANTISSA);
		setAsString(yes(), JAVA14, MANTISSA);
		setAsString(yes(), JAVA5, MANTISSA);
		setAsString(yes(), JAVA6, MANTISSA);
		setAsString(no(), MULTITHREADED, MANTISSA);
		setAsString(no(), CACHEDRESULTS, MANTISSA);
		setAsString(yes(), INPLACE, MANTISSA);
		setAsString(yes(), TRANSPOSE, MANTISSA);
		setAsString(yes(), PLUSMINUS, MANTISSA);
		setAsString(yes(), SCALE, MANTISSA);
		setAsString(yes(), DENSESA, MANTISSA);
		setAsString(no(), DENSEAA, MANTISSA);
		setAsString(no(), DENSEBLOCK, MANTISSA);
		setAsString(no(), SPARSEDOK, MANTISSA);
		setAsString(no(), SPARSEYALE, MANTISSA);
		setAsString(no(), SPARSELIL, MANTISSA);
		setAsString(no(), SPARSECRS, MANTISSA);
		setAsString(no(), SPARSECDS, MANTISSA);
		setAsString(no(), COMPLEX, MANTISSA);
		setAsString(yes(), DOUBLE, MANTISSA);
		setAsString(no(), FLOAT, MANTISSA);
		setAsString(no(), BIGDECIMAL, MANTISSA);
		setAsString(yes(), D2, MANTISSA);
		setAsString(no(), D3, MANTISSA);
		setAsString(no(), D3PLUS, MANTISSA);
		setAsString(yes(), INV, MANTISSA);
		setAsString(square(), SOLVE, MANTISSA);
		setAsString(no(), SVD, MANTISSA);
		setAsString(square() + footnote("a", "results not directly accessible"), LU, MANTISSA);
		setAsString(no(), QR, MANTISSA);
		setAsString(no(), EIG, MANTISSA);
		setAsString(no(), CHOL, MANTISSA);

		setAsString(small("0.9.12"), VERSION, MTJ);
		setAsString(small("2009"), DATE, MTJ);
		setAsString(small("LGPL"), LICENCE, MTJ);
		setAsString(no(), JAVA14, MTJ);
		setAsString(yes(), JAVA5, MTJ);
		setAsString(yes(), JAVA6, MTJ);
		setAsString(yes() + footnote("m", "using native machine code"), MULTITHREADED, MTJ);
		setAsString(yes(), INPLACE, MTJ);
		setAsString(no(), CACHEDRESULTS, MTJ);
		setAsString(yes(), DENSESA, MTJ);
		setAsString(no(), DENSEAA, MTJ);
		setAsString(no(), DENSEBLOCK, MTJ);
		setAsString(yes(), SPARSECRS, MTJ);
		setAsString(yes(), SPARSECDS, MTJ);
		setAsString(yes(), SPARSELIL, MTJ);
		setAsString(no(), SPARSEDOK, MTJ);
		setAsString(no(), SPARSEYALE, MTJ);
		setAsString(no(), COMPLEX, MTJ);
		setAsString(yes(), DOUBLE, MTJ);
		setAsString(no(), FLOAT, MTJ);
		setAsString(no(), BIGDECIMAL, MTJ);
		setAsString(yes(), D2, MTJ);
		setAsString(no(), D3, MTJ);
		setAsString(no(), D3PLUS, MTJ);
		setAsString(yes(), TRANSPOSE, MTJ);
		setAsString(yes(), SCALE, MTJ);
		setAsString(yes(), PLUSMINUS, MTJ);
		setAsString(yes(), INV, MTJ);
		setAsString(squareTall(), SOLVE, MTJ);
		setAsString(all(), SVD, MTJ);
		setAsString(all() + footnote("e", "error in implementation"), LU, MTJ);
		setAsString(squareTall(), QR, MTJ);
		setAsString(yes() + footnote("s", "symmetric matrices only"), EIG, MTJ);
		setAsString(yes(), CHOL, MTJ);

		setAsString(small("29.0"), VERSION, OJALGO);
		setAsString(small("2010"), DATE, OJALGO);
		setAsString(small("MIT"), LICENCE, OJALGO);
		setAsString(no(), JAVA14, OJALGO);
		setAsString(yes(), JAVA5, OJALGO);
		setAsString(yes(), JAVA6, OJALGO);
		setAsString(both(), MULTITHREADED, OJALGO);
		setAsString(yes(), INPLACE, OJALGO);
		setAsString(no(), CACHEDRESULTS, OJALGO);
		setAsString(yes() + footnote("f", "flags matrix as transposed"), TRANSPOSE, OJALGO);
		setAsString(yes(), SCALE, OJALGO);
		setAsString(yes(), PLUSMINUS, OJALGO);
		setAsString(yes(), DENSESA, OJALGO);
		setAsString(no(), DENSEAA, OJALGO);
		setAsString(no(), DENSEBLOCK, OJALGO);
		setAsString(no(), SPARSECDS, OJALGO);
		setAsString(no(), SPARSECRS, OJALGO);
		setAsString(no(), SPARSEYALE, OJALGO);
		setAsString(no(), SPARSELIL, OJALGO);
		setAsString(no(), SPARSEDOK, OJALGO);
		setAsString(yes(), COMPLEX, OJALGO);
		setAsString(yes(), DOUBLE, OJALGO);
		setAsString(yes(), FLOAT, OJALGO);
		setAsString(yes(), BIGDECIMAL, OJALGO);
		setAsString(yes(), D2, OJALGO);
		setAsString(no(), D3, OJALGO);
		setAsString(no(), D3PLUS, OJALGO);
		setAsString(yes(), INV, OJALGO);
		setAsString(squareTall(), SOLVE, OJALGO);
		setAsString(all(), SVD, OJALGO);
		setAsString(all(), LU, OJALGO);
		setAsString(all(), QR, OJALGO);
		setAsString(yes(), EIG, OJALGO);
		setAsString(yes(), CHOL, OJALGO);

		setAsString(small("1.3.0"), VERSION, ORBITAL);
		setAsString(small("2009"), DATE, ORBITAL);
		setAsString(small("custom"), LICENCE, ORBITAL);
		setAsString(yes(), JAVA14, ORBITAL);
		setAsString(yes(), JAVA5, ORBITAL);
		setAsString(yes(), JAVA6, ORBITAL);
		setAsString(no(), MULTITHREADED, ORBITAL);
		setAsString(no(), INPLACE, ORBITAL);
		setAsString(no(), CACHEDRESULTS, ORBITAL);
		setAsString(yes(), DENSESA, ORBITAL);
		setAsString(yes(), DENSEAA, ORBITAL);
		setAsString(no(), DENSEBLOCK, ORBITAL);
		setAsString(no(), SPARSECDS, ORBITAL);
		setAsString(no(), SPARSECRS, ORBITAL);
		setAsString(no(), SPARSEDOK, ORBITAL);
		setAsString(no(), SPARSELIL, ORBITAL);
		setAsString(no(), SPARSEYALE, ORBITAL);
		setAsString(yes(), COMPLEX, ORBITAL);
		setAsString(yes(), DOUBLE, ORBITAL);
		setAsString(no(), FLOAT, ORBITAL);
		setAsString(no(), BIGDECIMAL, ORBITAL);
		setAsString(yes(), TRANSPOSE, ORBITAL);
		setAsString(yes(), PLUSMINUS, ORBITAL);
		setAsString(yes(), SCALE, ORBITAL);
		setAsString(yes(), D2, ORBITAL);
		setAsString(yes(), D3, ORBITAL);
		setAsString(yes(), D3PLUS, ORBITAL);
		setAsString(yes(), INV, ORBITAL);
		setAsString(no(), SVD, ORBITAL);
		setAsString(no() + footnote("v", "only for $A \\cdot X = \\mbox{vector}$"), SOLVE, ORBITAL);
		setAsString(square() + footnote("n", "non-singular matrices only"), LU, ORBITAL);
		setAsString(no(), QR, ORBITAL);
		setAsString(no(), EIG, ORBITAL);
		setAsString(no(), CHOL, ORBITAL);

		setAsString(unknown(), VERSION, OWLPACK);
		setAsString(small("1999"), DATE, OWLPACK);
		setAsString(unknown(), LICENCE, OWLPACK);
		setAsString(yes(), JAVA14, OWLPACK);
		setAsString(yes(), JAVA5, OWLPACK);
		setAsString(yes(), JAVA6, OWLPACK);
		setAsString(no(), MULTITHREADED, OWLPACK);
		setAsString(yes(), INPLACE, OWLPACK);
		setAsString(no(), CACHEDRESULTS, OWLPACK);
		setAsString(yes(), DENSEAA, OWLPACK);
		setAsString(no(), DENSESA, OWLPACK);
		setAsString(no(), DENSEBLOCK, OWLPACK);
		setAsString(no(), SPARSECDS, OWLPACK);
		setAsString(no(), SPARSECRS, OWLPACK);
		setAsString(no(), SPARSEYALE, OWLPACK);
		setAsString(no(), SPARSEDOK, OWLPACK);
		setAsString(no(), SPARSELIL, OWLPACK);
		setAsString(yes(), COMPLEX, OWLPACK);
		setAsString(yes(), DOUBLE, OWLPACK);
		setAsString(yes(), FLOAT, OWLPACK);
		setAsString(no(), BIGDECIMAL, OWLPACK);
		setAsString(yes(), TRANSPOSE, OWLPACK);
		setAsString(yes(), PLUSMINUS, OWLPACK);
		setAsString(yes(), SCALE, OWLPACK);
		setAsString(yes(), D2, OWLPACK);
		setAsString(no(), D3, OWLPACK);
		setAsString(no(), D3PLUS, OWLPACK);
		setAsString(no() + footnote("v", "only for $A \\cdot X = \\mbox{vector}$"), SOLVE, OWLPACK);
		setAsString(yes() + footnote("e", "error in implementation"), INV, OWLPACK);
		setAsString(circle() + footnote("e", "error in implementation"), SVD, OWLPACK);
		setAsString(no(), LU, OWLPACK);
		setAsString(circle() + footnote("u", "unuseable without documentation"), QR, OWLPACK);
		setAsString(no(), EIG, OWLPACK);
		setAsString(no(), CHOL, OWLPACK);

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
		setAsString(no(), CACHEDRESULTS, PARALLELCOLT);
		setAsString(yes(), DENSESA, PARALLELCOLT);
		setAsString(yes(), DENSEAA, PARALLELCOLT);
		setAsString(no(), DENSEBLOCK, PARALLELCOLT);
		setAsString(yes(), SPARSECRS, PARALLELCOLT);
		setAsString(yes(), SPARSELIL, PARALLELCOLT);
		setAsString(yes(), SPARSEDOK, PARALLELCOLT);
		setAsString(no(), SPARSEYALE, PARALLELCOLT);
		setAsString(no(), SPARSECDS, PARALLELCOLT);
		setAsString(yes(), COMPLEX, PARALLELCOLT);
		setAsString(yes(), DOUBLE, PARALLELCOLT);
		setAsString(yes(), FLOAT, PARALLELCOLT);
		setAsString(no(), BIGDECIMAL, PARALLELCOLT);
		setAsString(yes(), D2, PARALLELCOLT);
		setAsString(yes(), D3, PARALLELCOLT);
		setAsString(no(), D3PLUS, PARALLELCOLT);
		setAsString(yes(), INV, PARALLELCOLT);
		setAsString(squareTall(), SOLVE, PARALLELCOLT);
		setAsString(all(), SVD, PARALLELCOLT);
		setAsString(squareTall(), LU, PARALLELCOLT);
		setAsString(squareTall(), QR, PARALLELCOLT);
		setAsString(yes(), EIG, PARALLELCOLT);
		setAsString(yes(), CHOL, PARALLELCOLT);

		setAsString(small("1.10"), VERSION, SST);
		setAsString(small("2009"), DATE, SST);
		setAsString(small("LGPL"), LICENCE, SST);
		setAsString(no(), JAVA14, SST);
		setAsString(yes() + footnote("j", "jar does not work with Java 5"), JAVA5, SST);
		setAsString(yes(), JAVA6, SST);
		setAsString(no(), MULTITHREADED, SST);
		setAsString(yes(), INPLACE, SST);
		setAsString(yes(), TRANSPOSE, SST);
		setAsString(yes(), SCALE, SST);
		setAsString(yes(), PLUSMINUS, SST);
		setAsString(no(), CACHEDRESULTS, SST);
		setAsString(yes(), DENSESA, SST);
		setAsString(no(), DENSEAA, SST);
		setAsString(no(), DENSEBLOCK, SST);
		setAsString(yes(), SPARSEDOK, SST);
		setAsString(no(), SPARSELIL, SST);
		setAsString(no(), SPARSEYALE, SST);
		setAsString(no(), SPARSECDS, SST);
		setAsString(no(), SPARSECRS, SST);
		setAsString(yes(), COMPLEX, SST);
		setAsString(yes(), DOUBLE, SST);
		setAsString(no(), FLOAT, SST);
		setAsString(no(), BIGDECIMAL, SST);
		setAsString(yes(), D2, SST);
		setAsString(yes(), D3, SST);
		setAsString(yes(), D3PLUS, SST);
		setAsString(yes(), INV, SST);
		setAsString(no(), SOLVE, SST);
		setAsString(all(), SVD, SST);
		setAsString(no(), LU, SST);
		setAsString(no(), QR, SST);
		setAsString(yes(), EIG, SST);
		setAsString(no(), CHOL, SST);

		setAsString(small("1.5.1"), VERSION, VECMATH);
		setAsString(small("2007"), DATE, VECMATH);
		setAsString(small("other"), LICENCE, VECMATH);
		setAsString(no(), JAVA14, VECMATH);
		setAsString(yes(), JAVA5, VECMATH);
		setAsString(yes(), JAVA6, VECMATH);
		setAsString(no(), MULTITHREADED, VECMATH);
		setAsString(yes(), INPLACE, VECMATH);
		setAsString(yes(), TRANSPOSE, VECMATH);
		setAsString(no(), SCALE, VECMATH);
		setAsString(yes(), PLUSMINUS, VECMATH);
		setAsString(no(), CACHEDRESULTS, VECMATH);
		setAsString(yes(), DENSEAA, VECMATH);
		setAsString(no(), DENSESA, VECMATH);
		setAsString(no(), DENSEBLOCK, VECMATH);
		setAsString(no(), SPARSEDOK, VECMATH);
		setAsString(no(), SPARSELIL, VECMATH);
		setAsString(no(), SPARSECDS, VECMATH);
		setAsString(no(), SPARSECRS, VECMATH);
		setAsString(no(), SPARSEYALE, VECMATH);
		setAsString(no(), COMPLEX, VECMATH);
		setAsString(yes(), DOUBLE, VECMATH);
		setAsString(no(), FLOAT, VECMATH);
		setAsString(no(), BIGDECIMAL, VECMATH);
		setAsString(yes(), D2, VECMATH);
		setAsString(no(), D3, VECMATH);
		setAsString(no(), D3PLUS, VECMATH);
		setAsString(yes(), INV, VECMATH);
		setAsString(no(), SOLVE, VECMATH);
		setAsString(circle() + footnote("e", "error in implementation"), SVD, VECMATH);
		setAsString(square() + footnote("n", "non-singular matrices only"), LU, VECMATH);
		setAsString(no(), QR, VECMATH);
		setAsString(no(), EIG, VECMATH);
		setAsString(no(), CHOL, VECMATH);
	}

	private String yes() {
		switch (format) {
		case LATEX:
			return "\\bf{$+$}";
		default:
			return "yes";
		}
	}

	private String both() {
		switch (format) {
		case LATEX:
			return "\\bf{$+/-$}";
		default:
			return "configurable";
		}
	}

	private String all() {
		switch (format) {
		case LATEX:
			return "\\scalebox{0.6}[1.0]{$\\square$}\\,$\\square$\\,\\scalebox{1.0}[0.6]{$\\square$}";
		default:
			return "all";
		}
	}

	private String squareTall() {
		switch (format) {
		case LATEX:
			return "\\scalebox{0.6}[1.0]{$\\square$}\\,$\\square$";
		default:
			return "square, tall";
		}
	}

	private String square() {
		switch (format) {
		case LATEX:
			return "$\\square$";
		default:
			return "square";
		}
	}

	private String circle() {
		switch (format) {
		case LATEX:
			return "$\\circ$";
		default:
			return "square";
		}
	}

	private String no() {
		switch (format) {
		case LATEX:
			return "\\bf{$-$}";
		default:
			return "yes";
		}
	}

	private String unknown() {
		switch (format) {
		default:
			return "?";
		}
	}

	private String turn(String text) {
		switch (format) {
		case LATEX:
			return "\\begin{turn}{90}" + text + "\\end{turn}";
		default:
			return "";
		}
	}

	private String small(String text) {
		switch (format) {
		case LATEX:
			return "\\small " + text;
		default:
			return text;
		}
	}

	private String footnote(String footnote, String text) {
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
		String s = ml.exportToString(FileFormat.TEX);
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
}
