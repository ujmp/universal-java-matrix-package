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

	private static final int DENSE = 7;

	private static final int SPARSE = 8;

	private static final int COMPLEX = 9;

	private static final int DOUBLE = 10;

	private static final int FLOAT = 11;

	private static final int BIGDECIMAL = 12;

	private static final int D2 = 13;

	private static final int D3 = 14;

	private static final int D4 = 15;

	private static final int D4PLUS = 16;

	private static final int INV = 17;

	private static final int CHOL = 18;

	private static final int EIG = 19;

	private static final int LU = 20;

	private static final int QR = 21;

	private static final int SVD = 22;

	private static final int LABELROW = 0;

	private static final int ARRAY4J = 1;

	private static final int COLT = 2;

	private static final int COMMONSMATH = 3;

	private static final int JAMA = 4;

	private static final int JAMPACK = 5;

	private static final int JMATHARRAY = 6;

	private static final int JMATRICES = 7;

	private static final int JSCI = 8;

	private static final int JSCIENCE = 9;

	private static final int MANTISSA = 10;

	private static final int MTJ = 11;

	private static final int OJALGO = 12;

	private static final int ORBITAL = 13;

	private static final int OWLPACK = 14;

	private static final int PARALLELCOLT = 15;

	private static final int SST = 16;

	private static final int UJMP = 17;

	private static final int VECMATH = 18;

	private List<String> footnotes = new ArrayList<String>();

	public enum MatrixLibrariesFormat {
		DEFAULT, LATEX, HTML
	};

	private MatrixLibrariesFormat format = MatrixLibrariesFormat.DEFAULT;

	public MatrixLibraries() {
		this(MatrixLibrariesFormat.DEFAULT);
	}

	public MatrixLibraries(MatrixLibrariesFormat format) {
		super(23, 19);
		this.format = format;

		setAsString(turn("Array4J"), LABELROW, ARRAY4J);
		setAsString(turn("Colt"), LABELROW, COLT);
		setAsString(turn("commons-math"), LABELROW, COMMONSMATH);
		setAsString(turn("JAMA"), LABELROW, JAMA);
		setAsString(turn("Jampack"), LABELROW, JAMPACK);
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

		setAsString("latest version", VERSION, LABELCOLUMN);
		setAsString("latest release", DATE, LABELCOLUMN);
		setAsString("licence", LICENCE, LABELCOLUMN);
		setAsString("Java 1.4", JAVA14, LABELCOLUMN);
		setAsString("Java 5", JAVA5, LABELCOLUMN);
		setAsString("Java 6", JAVA6, LABELCOLUMN);
		setAsString("dense matrices", DENSE, LABELCOLUMN);
		setAsString("sparse matrices", SPARSE, LABELCOLUMN);
		setAsString("complex numbers", COMPLEX, LABELCOLUMN);
		setAsString("double values", DOUBLE, LABELCOLUMN);
		setAsString("float values", FLOAT, LABELCOLUMN);
		setAsString("BigDecimal values", BIGDECIMAL, LABELCOLUMN);
		setAsString("2D matrices", D2, LABELCOLUMN);
		setAsString("3D matrices", D3, LABELCOLUMN);
		setAsString("4D matrices", D4, LABELCOLUMN);
		setAsString("matrices $>$4D", D4PLUS, LABELCOLUMN);
		setAsString("inverse", INV, LABELCOLUMN);
		setAsString("SVD", SVD, LABELCOLUMN);
		setAsString("LU", LU, LABELCOLUMN);
		setAsString("QR", QR, LABELCOLUMN);
		setAsString("Cholesky", CHOL, LABELCOLUMN);
		setAsString("Eigen", EIG, LABELCOLUMN);

		setAsString(org.ujmp.core.UJMP.UJMPVERSION, VERSION, UJMP);
		setAsString(small("2009"), DATE, UJMP);
		setAsString(small("LGPL"), LICENCE, UJMP);
		setAsString(no(), JAVA14, UJMP);
		setAsString(yes(), JAVA5, UJMP);
		setAsString(yes(), JAVA6, UJMP);
		setAsString(yes(), DENSE, UJMP);
		setAsString(yes(), SPARSE, UJMP);
		setAsString(no(), COMPLEX, UJMP);
		setAsString(yes(), DOUBLE, UJMP);
		setAsString(yes(), FLOAT, UJMP);
		setAsString(yes(), BIGDECIMAL, UJMP);
		setAsString(yes(), D2, UJMP);
		setAsString(yes(), D3, UJMP);
		setAsString(yes(), D4, UJMP);
		setAsString(yes(), D4PLUS, UJMP);
		setAsString(yes(), INV, UJMP);
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
		setAsString(yes(), DENSE, ARRAY4J);
		setAsString(no() + footnote("i", "interface only"), SPARSE, ARRAY4J);
		setAsString(no() + footnote("i", "interface only"), COMPLEX, ARRAY4J);
		setAsString(no() + footnote("i", "interface only"), DOUBLE, ARRAY4J);
		setAsString(yes(), FLOAT, ARRAY4J);
		setAsString(no(), BIGDECIMAL, ARRAY4J);
		setAsString(yes(), D2, ARRAY4J);
		setAsString(no(), D3, ARRAY4J);
		setAsString(no(), D4, ARRAY4J);
		setAsString(no(), D4PLUS, ARRAY4J);
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
		setAsString(yes(), DENSE, COLT);
		setAsString(yes(), SPARSE, COLT);
		setAsString(no(), COMPLEX, COLT);
		setAsString(yes(), DOUBLE, COLT);
		setAsString(no(), FLOAT, COLT);
		setAsString(no(), BIGDECIMAL, COLT);
		setAsString(yes(), D2, COLT);
		setAsString(yes(), D3, COLT);
		setAsString(no(), D4, COLT);
		setAsString(no(), D4PLUS, COLT);
		setAsString(yes(), INV, COLT);
		setAsString(squareTall(), SVD, COLT);
		setAsString(squareTall(), LU, COLT);
		setAsString(all(), QR, COLT);
		setAsString(yes(), EIG, COLT);
		setAsString(yes(), CHOL, COLT);

		setAsString(small("2.0"), VERSION, COMMONSMATH);
		setAsString(small("2009"), DATE, COMMONSMATH);
		setAsString(small("Apache"), LICENCE, COMMONSMATH);
		setAsString(unknown(), JAVA14, COMMONSMATH);
		setAsString(yes(), JAVA5, COMMONSMATH);
		setAsString(yes(), JAVA6, COMMONSMATH);
		setAsString(yes(), DENSE, COMMONSMATH);
		setAsString(yes(), SPARSE, COMMONSMATH);
		setAsString(yes(), COMPLEX, COMMONSMATH);
		setAsString(yes(), DOUBLE, COMMONSMATH);
		setAsString(no(), FLOAT, COMMONSMATH);
		setAsString(yes(), BIGDECIMAL, COMMONSMATH);
		setAsString(yes(), D2, COMMONSMATH);
		setAsString(no(), D3, COMMONSMATH);
		setAsString(no(), D4, COMMONSMATH);
		setAsString(no(), D4PLUS, COMMONSMATH);
		setAsString(yes(), INV, COMMONSMATH);
		setAsString(all() + footnote("e", "error in implementation"), SVD, COMMONSMATH);
		setAsString(square() + footnote("n", "non-singular matrices only"), LU, COMMONSMATH);
		setAsString(all(), QR, COMMONSMATH);
		setAsString(square() + footnote("s", "symmetric matrices only"), EIG, COMMONSMATH);
		setAsString(yes(), CHOL, COMMONSMATH);

		setAsString(small("1.0.2"), VERSION, JAMA);
		setAsString(small("2005"), DATE, JAMA);
		setAsString(small("PD"), LICENCE, JAMA);
		setAsString(unknown(), JAVA14, JAMA);
		setAsString(yes(), JAVA5, JAMA);
		setAsString(yes(), JAVA6, JAMA);
		setAsString(yes(), DENSE, JAMA);
		setAsString(no(), SPARSE, JAMA);
		setAsString(no(), COMPLEX, JAMA);
		setAsString(yes(), DOUBLE, JAMA);
		setAsString(no(), FLOAT, JAMA);
		setAsString(no(), BIGDECIMAL, JAMA);
		setAsString(yes(), D2, JAMA);
		setAsString(no(), D3, JAMA);
		setAsString(no(), D4, JAMA);
		setAsString(no(), D4PLUS, JAMA);
		setAsString(yes(), INV, JAMA);
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
		setAsString(yes(), DENSE, JAMPACK);
		setAsString(no(), SPARSE, JAMPACK);
		setAsString(yes(), COMPLEX, JAMPACK);
		setAsString(yes(), DOUBLE, JAMPACK);
		setAsString(no(), FLOAT, JAMPACK);
		setAsString(no(), BIGDECIMAL, JAMPACK);
		setAsString(yes(), D2, JAMPACK);
		setAsString(no(), D3, JAMPACK);
		setAsString(no(), D4, JAMPACK);
		setAsString(no(), D4PLUS, JAMPACK);
		setAsString(yes(), INV, JAMPACK);
		setAsString(square(), SVD, JAMPACK);
		setAsString(all(), LU, JAMPACK);
		setAsString(yes(), QR, JAMPACK);
		setAsString(yes(), EIG, JAMPACK);
		setAsString(yes(), CHOL, JAMPACK);

		setAsString(small("?"), VERSION, JMATHARRAY);
		setAsString(small("2009"), DATE, JMATHARRAY);
		setAsString(small("BSD"), LICENCE, JMATHARRAY);
		setAsString(unknown(), JAVA14, JMATHARRAY);
		setAsString(yes(), JAVA5, JMATHARRAY);
		setAsString(yes(), JAVA6, JMATHARRAY);
		setAsString(yes(), DENSE, JMATHARRAY);
		setAsString(no(), SPARSE, JMATHARRAY);
		setAsString(no(), COMPLEX, JMATHARRAY);
		setAsString(yes(), DOUBLE, JMATHARRAY);
		setAsString(no(), FLOAT, JMATHARRAY);
		setAsString(no(), BIGDECIMAL, JMATHARRAY);
		setAsString(yes(), D2, JMATHARRAY);
		setAsString(no(), D3, JMATHARRAY);
		setAsString(no(), D4, JMATHARRAY);
		setAsString(no(), D4PLUS, JMATHARRAY);
		setAsString(yes(), INV, JMATHARRAY);
		setAsString(squareTall(), SVD, JMATHARRAY);
		setAsString(squareTall(), LU, JMATHARRAY);
		setAsString(squareTall(), QR, JMATHARRAY);
		setAsString(yes(), EIG, JMATHARRAY);
		setAsString(yes(), CHOL, JMATHARRAY);

		setAsString(small("0.6"), VERSION, JMATRICES);
		setAsString(small("2004"), DATE, JMATRICES);
		setAsString(small("LGPL"), LICENCE, JMATRICES);
		setAsString(unknown(), JAVA14, JMATRICES);
		setAsString(yes(), JAVA5, JMATRICES);
		setAsString(yes(), JAVA6, JMATRICES);
		setAsString(yes(), DENSE, JMATRICES);
		setAsString(no(), SPARSE, JMATRICES);
		setAsString(yes(), COMPLEX, JMATRICES);
		setAsString(yes(), DOUBLE, JMATRICES);
		setAsString(no(), FLOAT, JMATRICES);
		setAsString(yes(), BIGDECIMAL, JMATRICES);
		setAsString(yes(), D2, JMATRICES);
		setAsString(no(), D3, JMATRICES);
		setAsString(no(), D4, JMATRICES);
		setAsString(no(), D4PLUS, JMATRICES);
		setAsString(yes(), INV, JMATRICES);
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
		setAsString(yes(), DENSE, JSCI);
		setAsString(yes(), SPARSE, JSCI);
		setAsString(yes(), COMPLEX, JSCI);
		setAsString(yes(), DOUBLE, JSCI);
		setAsString(no(), FLOAT, JSCI);
		setAsString(no(), BIGDECIMAL, JSCI);
		setAsString(yes(), D2, JSCI);
		setAsString(no(), D3, JSCI);
		setAsString(no(), D4, JSCI);
		setAsString(no(), D4PLUS, JSCI);
		setAsString(yes(), INV, JSCI);
		setAsString(square(), SVD, JSCI);
		setAsString(square() + footnote("e", "error in implementation"), LU, JSCI);
		setAsString(square(), QR, JSCI);
		setAsString(no(), EIG, JSCI);
		setAsString(yes(), CHOL, JSCI);

		setAsString(small("4.3.1"), VERSION, JSCIENCE);
		setAsString(small("2007"), DATE, JSCIENCE);
		setAsString(small("BSD"), LICENCE, JSCIENCE);
		setAsString(unknown(), JAVA14, JSCIENCE);
		setAsString(yes(), JAVA5, JSCIENCE);
		setAsString(yes(), JAVA6, JSCIENCE);
		setAsString(yes(), DENSE, JSCIENCE);
		setAsString(yes(), SPARSE, JSCIENCE);
		setAsString(yes(), COMPLEX, JSCIENCE);
		setAsString(yes(), DOUBLE, JSCIENCE);
		setAsString(no(), FLOAT, JSCIENCE);
		setAsString(no(), BIGDECIMAL, JSCIENCE);
		setAsString(yes(), D2, JSCIENCE);
		setAsString(no(), D3, JSCIENCE);
		setAsString(no(), D4, JSCIENCE);
		setAsString(no(), D4PLUS, JSCIENCE);
		setAsString(yes(), INV, JSCIENCE);
		setAsString(no(), SVD, JSCIENCE);
		setAsString(square() + footnote("e", "error in implementation"), LU, JSCIENCE);
		setAsString(no(), QR, JSCIENCE);
		setAsString(no(), EIG, JSCIENCE);
		setAsString(no(), CHOL, JSCIENCE);

		setAsString(small("7.2"), VERSION, MANTISSA);
		setAsString(small("2007"), DATE, MANTISSA);
		setAsString(small("BSD"), LICENCE, MANTISSA);
		setAsString(unknown(), JAVA14, MANTISSA);
		setAsString(yes(), JAVA5, MANTISSA);
		setAsString(yes(), JAVA6, MANTISSA);
		setAsString(yes(), DENSE, MANTISSA);
		setAsString(no(), SPARSE, MANTISSA);
		setAsString(no(), COMPLEX, MANTISSA);
		setAsString(yes(), DOUBLE, MANTISSA);
		setAsString(no(), FLOAT, MANTISSA);
		setAsString(no(), BIGDECIMAL, MANTISSA);
		setAsString(yes(), D2, MANTISSA);
		setAsString(no(), D3, MANTISSA);
		setAsString(no(), D4, MANTISSA);
		setAsString(no(), D4PLUS, MANTISSA);
		setAsString(yes(), INV, MANTISSA);
		setAsString(no(), SVD, MANTISSA);
		setAsString(square() + footnote("o", "not accessible from outside"), LU, MANTISSA);
		setAsString(no(), QR, MANTISSA);
		setAsString(no(), EIG, MANTISSA);
		setAsString(no(), CHOL, MANTISSA);

		setAsString(small("0.9.12"), VERSION, MTJ);
		setAsString(small("2009"), DATE, MTJ);
		setAsString(small("LGPL"), LICENCE, MTJ);
		setAsString(unknown(), JAVA14, MTJ);
		setAsString(yes(), JAVA5, MTJ);
		setAsString(yes(), JAVA6, MTJ);
		setAsString(yes(), DENSE, MTJ);
		setAsString(yes(), SPARSE, MTJ);
		setAsString(no(), COMPLEX, MTJ);
		setAsString(yes(), DOUBLE, MTJ);
		setAsString(no(), FLOAT, MTJ);
		setAsString(no(), BIGDECIMAL, MTJ);
		setAsString(yes(), D2, MTJ);
		setAsString(no(), D3, MTJ);
		setAsString(no(), D4, MTJ);
		setAsString(no(), D4PLUS, MTJ);
		setAsString(yes(), INV, MTJ);
		setAsString(all(), SVD, MTJ);
		setAsString(all() + footnote("e", "error in implementation"), LU, MTJ);
		setAsString(squareTall(), QR, MTJ);
		setAsString(yes(), EIG, MTJ);
		setAsString(yes(), CHOL, MTJ);

		setAsString(small("28.17"), VERSION, OJALGO);
		setAsString(small("2009"), DATE, OJALGO);
		setAsString(small("MIT"), LICENCE, OJALGO);
		setAsString(unknown(), JAVA14, OJALGO);
		setAsString(yes(), JAVA5, OJALGO);
		setAsString(yes(), JAVA6, OJALGO);
		setAsString(yes(), DENSE, OJALGO);
		setAsString(no(), SPARSE, OJALGO);
		setAsString(yes(), COMPLEX, OJALGO);
		setAsString(yes(), DOUBLE, OJALGO);
		setAsString(yes(), FLOAT, OJALGO);
		setAsString(yes(), BIGDECIMAL, OJALGO);
		setAsString(yes(), D2, OJALGO);
		setAsString(no(), D3, OJALGO);
		setAsString(no(), D4, OJALGO);
		setAsString(no(), D4PLUS, OJALGO);
		setAsString(yes(), INV, OJALGO);
		setAsString(all(), SVD, OJALGO);
		setAsString(unknown(), LU, OJALGO);
		setAsString(squareTall(), QR, OJALGO);
		setAsString(yes(), EIG, OJALGO);
		setAsString(yes(), CHOL, OJALGO);

		setAsString(small("1.3.0"), VERSION, ORBITAL);
		setAsString(small("2009"), DATE, ORBITAL);
		setAsString(small("custom"), LICENCE, ORBITAL);
		setAsString(unknown(), JAVA14, ORBITAL);
		setAsString(yes(), JAVA5, ORBITAL);
		setAsString(yes(), JAVA6, ORBITAL);
		setAsString(yes(), DENSE, ORBITAL);
		setAsString(no(), SPARSE, ORBITAL);
		setAsString(yes(), COMPLEX, ORBITAL);
		setAsString(yes(), DOUBLE, ORBITAL);
		setAsString(no(), FLOAT, ORBITAL);
		setAsString(no(), BIGDECIMAL, ORBITAL);
		setAsString(yes(), D2, ORBITAL);
		setAsString(no(), D3, ORBITAL);
		setAsString(no(), D4, ORBITAL);
		setAsString(no(), D4PLUS, ORBITAL);
		setAsString(yes(), INV, ORBITAL);
		setAsString(no(), SVD, ORBITAL);
		setAsString(square() + footnote("e", "error in implementation"), LU, ORBITAL);
		setAsString(no(), QR, ORBITAL);
		setAsString(no(), EIG, ORBITAL);
		setAsString(no(), CHOL, ORBITAL);

		setAsString(unknown(), VERSION, OWLPACK);
		setAsString(small("1999"), DATE, OWLPACK);
		setAsString(unknown(), LICENCE, OWLPACK);
		setAsString(yes(), JAVA14, OWLPACK);
		setAsString(yes(), JAVA5, OWLPACK);
		setAsString(yes(), JAVA6, OWLPACK);
		setAsString(yes(), DENSE, OWLPACK);
		setAsString(no(), SPARSE, OWLPACK);
		setAsString(yes(), COMPLEX, OWLPACK);
		setAsString(yes(), DOUBLE, OWLPACK);
		setAsString(yes(), FLOAT, OWLPACK);
		setAsString(no(), BIGDECIMAL, OWLPACK);
		setAsString(yes(), D2, OWLPACK);
		setAsString(no(), D3, OWLPACK);
		setAsString(no(), D4, OWLPACK);
		setAsString(no(), D4PLUS, OWLPACK);
		setAsString(yes() + footnote("e", "error in implementation"), INV, OWLPACK);
		setAsString(yes() + footnote("e", "error in implementation"), SVD, OWLPACK);
		setAsString(no(), LU, OWLPACK);
		setAsString(circle() + footnote("u", "not useable without documentation"), QR, OWLPACK);
		setAsString(no(), EIG, OWLPACK);
		setAsString(no(), CHOL, OWLPACK);

		setAsString(small("0.9.1"), VERSION, PARALLELCOLT);
		setAsString(small("2009"), DATE, PARALLELCOLT);
		setAsString(small("BSD"), LICENCE, PARALLELCOLT);
		setAsString(unknown(), JAVA14, PARALLELCOLT);
		setAsString(yes(), JAVA5, PARALLELCOLT);
		setAsString(yes(), JAVA6, PARALLELCOLT);
		setAsString(yes(), DENSE, PARALLELCOLT);
		setAsString(yes(), SPARSE, PARALLELCOLT);
		setAsString(yes(), COMPLEX, PARALLELCOLT);
		setAsString(yes(), DOUBLE, PARALLELCOLT);
		setAsString(yes(), FLOAT, PARALLELCOLT);
		setAsString(no(), BIGDECIMAL, PARALLELCOLT);
		setAsString(yes(), D2, PARALLELCOLT);
		setAsString(yes(), D3, PARALLELCOLT);
		setAsString(no(), D4, PARALLELCOLT);
		setAsString(no(), D4PLUS, PARALLELCOLT);
		setAsString(yes(), INV, PARALLELCOLT);
		setAsString(all(), SVD, PARALLELCOLT);
		setAsString(squareTall(), LU, PARALLELCOLT);
		setAsString(circle() + footnote("e", "error in implementation"), QR, PARALLELCOLT);
		setAsString(yes(), EIG, PARALLELCOLT);
		setAsString(yes(), CHOL, PARALLELCOLT);

		setAsString(small("1.10"), VERSION, SST);
		setAsString(small("2009"), DATE, SST);
		setAsString(small("LGPL"), LICENCE, SST);
		setAsString(no(), JAVA14, SST);
		setAsString(no(), JAVA5, SST);
		setAsString(yes(), JAVA6, SST);
		setAsString(yes(), DENSE, SST);
		setAsString(yes(), SPARSE, SST);
		setAsString(yes(), COMPLEX, SST);
		setAsString(yes(), DOUBLE, SST);
		setAsString(no(), FLOAT, SST);
		setAsString(no(), BIGDECIMAL, SST);
		setAsString(yes(), D2, SST);
		setAsString(yes(), D3, SST);
		setAsString(yes(), D4, SST);
		setAsString(yes(), D4PLUS, SST);
		setAsString(yes(), INV, SST);
		setAsString(unknown(), SVD, SST);
		setAsString(no(), LU, SST);
		setAsString(no(), QR, SST);
		setAsString(yes(), EIG, SST);
		setAsString(no(), CHOL, SST);

		setAsString(small("1.5.1"), VERSION, VECMATH);
		setAsString(small("2007"), DATE, VECMATH);
		setAsString(small("other"), LICENCE, VECMATH);
		setAsString(unknown(), JAVA14, VECMATH);
		setAsString(yes(), JAVA5, VECMATH);
		setAsString(yes(), JAVA6, VECMATH);
		setAsString(yes(), DENSE, VECMATH);
		setAsString(no(), SPARSE, VECMATH);
		setAsString(no(), COMPLEX, VECMATH);
		setAsString(yes(), DOUBLE, VECMATH);
		setAsString(no(), FLOAT, VECMATH);
		setAsString(no(), BIGDECIMAL, VECMATH);
		setAsString(yes(), D2, VECMATH);
		setAsString(no(), D3, VECMATH);
		setAsString(no(), D4, VECMATH);
		setAsString(no(), D4PLUS, VECMATH);
		setAsString(yes(), INV, VECMATH);
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

	private String all() {
		switch (format) {
		case LATEX:
			return "\\scalebox{0.6}[1.0]{$\\square$} $\\square$ \\scalebox{1.0}[0.6]{$\\square$}";
		default:
			return "all";
		}
	}

	private String squareTall() {
		switch (format) {
		case LATEX:
			return "\\scalebox{0.6}[1.0]{$\\square$} $\\square$";
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
			String f = "$^\\mathrm{" + footnote + "}$ " + text;
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
				.replaceAll("\\\\begin\\{tabular\\}",
						"\\\\caption{Overview of matrix libraries in Java}\n\\\\bigskip\n\\\\begin{tabular}");
		s = s.replaceAll("latest version", "\\\\toprule\nlatest version");
		s = s
				.replace("{ccccccccccccccccccc}",
						"{l@{ }c@{ }c@{ }c@{ }c@{ }c@{ }c@{ }c@{ }c@{ }c@{ }c@{ }c@{ }c@{ }c@{ }c@{ }c@{ }c@{ }c@{ }c}");
		s = s.replaceAll("\\\\end\\{sidewaystable\\}", "");
		s = s + "\\medskip" + "\n";
		List<String> fn = ml.getFootnotes();
		Collections.sort(fn);
		for (String f : fn) {
			s = s + f + "\\\\" + "\n";
		}
		s = s + "\\end{sidewaystable}";
		System.out.println(s);
	}
}
