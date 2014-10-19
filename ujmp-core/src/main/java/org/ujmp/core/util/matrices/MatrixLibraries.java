/*
 * Copyright (C) 2008-2014 by Holger Arndt
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
import org.ujmp.core.stringmatrix.impl.DefaultDenseStringMatrix2D;

public class MatrixLibraries extends DefaultDenseStringMatrix2D {
	private static final long serialVersionUID = -2575195318248762416L;

	public static final int VERSION = 0;
	public static final int DATE = 1;
	public static final int LICENSE = 2;
	public static final int JAVA14 = 3;
	public static final int JAVA5 = 4;
	public static final int JAVA6 = 5;
	public static final int JAVA7 = 6;
	public static final int JAVA8 = 7;
	public static final int DENSESA = 8;
	public static final int DENSEAA = 9;
	public static final int DENSEBLOCK = 10;
	public static final int SPARSEDOK = 11;
	public static final int SPARSELIL = 12;
	public static final int SPARSEYALE = 13;
	public static final int SPARSECRSCCS = 14;
	public static final int SPARSECDS = 15;
	public static final int DOUBLE = 16;
	public static final int FLOAT = 17;
	public static final int BIGDECIMAL = 18;
	public static final int STRINGS = 19;
	public static final int OBJECTS = 20;
	public static final int GENERICS = 21;
	public static final int COMPLEX = 22;
	public static final int D2 = 23;
	public static final int D3 = 24;
	public static final int D3PLUS = 25;
	public static final int MULTITHREADED = 26;
	public static final int INPLACE = 27;
	public static final int TRANSPOSE = 28;
	public static final int SCALE = 29;
	public static final int PLUSMINUS = 30;
	public static final int INV = 31;
	public static final int SOLVE = 32;
	public static final int LU = 33;
	public static final int QR = 34;
	public static final int SVD = 35;
	public static final int CHOL = 36;
	public static final int EIG = 37;
	public static final int CSVIO = 38;
	public static final int JDBCIO = 39;
	public static final int SERIALIZABLE = 40;
	public static final int VALUESPERDIMENSION = 41;
	public static final int MAXIMUMSIZE = 42;
	public static final int HOMEPAGE = 43;

	public static final int COLT = 0;
	public static final int COMMONSMATH = 1;
	public static final int EJML = 2;
	public static final int JAMA = 3;
	public static final int JBLAS = 4;
	public static final int JLINALG = 5;
	public static final int JMATHARRAY = 6;
	public static final int JMATRICES = 7;
	public static final int JSCI = 8;
	public static final int JSCIENCE = 9;
	public static final int LA4J = 10;
	public static final int MANTISSA = 11;
	public static final int MTJ = 12;
	public static final int OJALGO = 13;
	public static final int PARALLELCOLT = 14;
	public static final int SST = 15;
	public static final int UJMP = 16;
	public static final int VECMATH = 17;

	public static final String NONSINGULARLETTER = "n";
	public static final String NONSINGULARTEXT = "non-singular matrices only";
	public static final String ERRORTEXT = "error in implementation";
	public static final String SYMMETRICTEXT = "symmetric matrices only";

	private List<String> footnotes = new ArrayList<String>();

	public enum MatrixLibrariesFormat {
		DEFAULT, LATEX, HTMLTABLE, HTMLLIST
	};

	private MatrixLibrariesFormat format = MatrixLibrariesFormat.DEFAULT;

	public MatrixLibraries() {
		this(MatrixLibrariesFormat.DEFAULT);
	}

	public MatrixLibraries(MatrixLibrariesFormat format) {
		super(44, 18);
		int footnoteId = 1;
		this.format = format;

		setColumnLabel(COLT, rotate("Colt"));
		setColumnLabel(COMMONSMATH, rotate("Commons Math"));
		setColumnLabel(EJML, rotate("EJML"));
		setColumnLabel(JAMA, rotate("JAMA"));
		setColumnLabel(JBLAS, rotate("jblas"));
		setColumnLabel(JLINALG, rotate("JLinAlg"));
		setColumnLabel(JMATHARRAY, rotate("JMathArray"));
		setColumnLabel(JMATRICES, rotate("JMatrices"));
		setColumnLabel(JSCI, rotate("JSci"));
		setColumnLabel(JSCIENCE, rotate("JScience"));
		setColumnLabel(LA4J, rotate("la4j"));
		setColumnLabel(MANTISSA, rotate("Mantissa"));
		setColumnLabel(MTJ, rotate("MTJ"));
		setColumnLabel(OJALGO, rotate("ojAlgo"));
		setColumnLabel(PARALLELCOLT, rotate("Parallel Colt"));
		setColumnLabel(SST, rotate("SST"));
		setColumnLabel(UJMP, rotate("UJMP"));
		setColumnLabel(VECMATH, rotate("vecmath"));

		setString(link(rotate("Colt Homepage"), "http://acs.lbl.gov/software/colt/"), HOMEPAGE,
				COLT);
		setString(link(rotate("Commons Math Homepage"), "http://commons.apache.org/math/"),
				HOMEPAGE, COMMONSMATH);
		setString(
				link(rotate("EJML Homepage"),
						"https://code.google.com/p/efficient-java-matrix-library/"), HOMEPAGE, EJML);
		setString(link(rotate("JAMA Homepage"), "http://math.nist.gov/javanumerics/jama/"),
				HOMEPAGE, JAMA);
		setString(link(rotate("jblas Homepage"), "http://mikiobraun.github.io/jblas/"), HOMEPAGE,
				JBLAS);
		setString(link(rotate("JLinAlg Homepage"), "http://jlinalg.sourceforge.net/"), HOMEPAGE,
				JLINALG);
		setString(link(rotate("JMathArray Homepage"), "https://code.google.com/p/jmatharray/"),
				HOMEPAGE, JMATHARRAY);
		setString(link(rotate("JMatrices Homepage"), "http://jmatrices.sourceforge.net/"),
				HOMEPAGE, JMATRICES);
		setString(link(rotate("JSci Homepage"), "http://jsci.sourceforge.net/"), HOMEPAGE, JSCI);
		setString(link(rotate("JScience Homepage"), "http://jscience.org/"), HOMEPAGE, JSCIENCE);
		setString(link(rotate("la4j Homepage"), "http://la4j.org/"), HOMEPAGE, LA4J);
		setString(
				link(rotate("Mantissa Homepage"),
						"http://www.spaceroots.org/software/mantissa/index.html"), HOMEPAGE,
				MANTISSA);
		setString(link(rotate("MTJ Homepage"), "https://github.com/fommil/matrix-toolkits-java/"),
				HOMEPAGE, MTJ);
		setString(link(rotate("ojAlgo Homepage"), "http://ojalgo.org/"), HOMEPAGE, OJALGO);
		setString(
				link(rotate("Parallel Colt Homepage"),
						"https://sites.google.com/site/piotrwendykier/software/parallelcolt"),
				HOMEPAGE, PARALLELCOLT);
		setString(link(rotate("SST Homepage"), "http://freecode.com/projects/shared"), HOMEPAGE,
				SST);
		setString(link(rotate("UJMP Homepage"), "http://ujmp.org/"), HOMEPAGE, UJMP);
		setString(link(rotate("vecmath Homepage"), "https://java.net/projects/vecmath"), HOMEPAGE,
				VECMATH);

		setRowLabel(VERSION, "Current Version");
		setRowLabel(DATE, "Latest Release");
		setRowLabel(LICENSE, "License");
		setRowLabel(JAVA14, "Supports Java 1.4");
		setRowLabel(JAVA5, "Supports Java 5");
		setRowLabel(JAVA6, "Supports Java 6");
		setRowLabel(JAVA7, "Supports Java 7");
		setRowLabel(JAVA8, "Supports Java 8");
		setRowLabel(MULTITHREADED, "Uses Multi-Threaded Operations");
		setRowLabel(INPLACE, "Supports In-Place Operations");
		setRowLabel(DENSESA, "Stores Dense Data in Single Array");
		setRowLabel(DENSEAA, "Stores Dense Data in 2D Array");
		setRowLabel(DENSEBLOCK, "Stores Dense Data in Block Storage");
		setRowLabel(
				SPARSEDOK,
				"Stores Sparse Data in DOK"
						+ footnote("" + footnoteId++, "dictionary of key-value pairs"));
		setRowLabel(SPARSELIL,
				"Stores Sparse Data in LIL" + footnote("" + footnoteId++, "list of lists"));
		setRowLabel(
				SPARSECRSCCS,
				"Stores Sparse Data in CRS/CCS"
						+ footnote("" + footnoteId++, "compressed sparse row/column storare"));
		setRowLabel(
				SPARSECDS,
				"Stores Sparse Data in CDS"
						+ footnote("" + footnoteId++, "compressed sparse diagonal"));
		setRowLabel(SPARSEYALE, "Stores Sparse Data in Yale Format");
		setRowLabel(COMPLEX, "Can Store Complex Numbers");
		setRowLabel(DOUBLE, "Can Store Double Values");
		setRowLabel(FLOAT, "Can Store Float Values");
		setRowLabel(BIGDECIMAL, "Can Store BigDecimal Values");
		setRowLabel(STRINGS, "Can Store Strings");
		setRowLabel(OBJECTS, "Can Store Objects");
		setRowLabel(GENERICS, "Can Store Generic Objects");
		setRowLabel(D2, "Supports 2D Matrix");
		setRowLabel(D3, "Supports 3D Matrix");
		setRowLabel(D3PLUS, "Supports >3D Matrix");
		setRowLabel(TRANSPOSE, "Supports Matrix Transpose");
		setRowLabel(SCALE, "Supports Matrix Multiply/Divide");
		setRowLabel(PLUSMINUS, "Supports Plus/Minus");
		setRowLabel(INV, "Supports Matrix Inverse");
		setRowLabel(SOLVE, "Supports Solve Linear System");
		setRowLabel(SVD, "Supports Singular Value Decomposition");
		setRowLabel(LU, "Supports LU Decomposition");
		setRowLabel(QR, "Supports QR Decomposition");
		setRowLabel(CHOL, "Supports Cholesky Decomposition");
		setRowLabel(EIG, "Supports Eigen Decomposition");
		setRowLabel(CSVIO, "Can Import/Export CSV");
		setRowLabel(JDBCIO, "Can Import/Export JDBC");
		setRowLabel(SERIALIZABLE, "Matrix is Serializable");
		setRowLabel(VALUESPERDIMENSION, "Number of Values per Dimension");
		setRowLabel(MAXIMUMSIZE, "Maximum Matrix Size");
		setRowLabel(HOMEPAGE, "Homepage");

		setString(org.ujmp.core.UJMP.UJMPVERSION, VERSION, UJMP);
		setString(small("2014"), DATE, UJMP);
		setString(small("LGPL"), LICENSE, UJMP);
		setString(no(), JAVA14, UJMP);
		setString(yes(), JAVA5, UJMP);
		setString(yes(), JAVA6, UJMP);
		setString(yes(), JAVA7, UJMP);
		setString(yes(), JAVA8, UJMP);
		setString(yes(), MULTITHREADED, UJMP);
		setString(yes(), INPLACE, UJMP);
		setString(yes(), DENSEAA, UJMP);
		setString(yes(), DENSESA, UJMP);
		setString(yes(), DENSEBLOCK, UJMP);
		setString(yes(), SPARSEDOK, UJMP);
		setString(no(), SPARSEYALE, UJMP);
		setString(yes(), SPARSELIL, UJMP);
		setString(no(), SPARSECRSCCS, UJMP);
		setString(no(), SPARSECDS, UJMP);
		setString(yes(), DOUBLE, UJMP);
		setString(yes(), FLOAT, UJMP);
		setString(yes(), BIGDECIMAL, UJMP);
		setString(yes(), STRINGS, UJMP);
		setString(yes(), OBJECTS, UJMP);
		setString(yes(), GENERICS, UJMP);
		setString(no(), COMPLEX, UJMP);
		setString(yes(), D2, UJMP);
		setString(yes(), D3, UJMP);
		setString(yes(), D3PLUS, UJMP);
		setString(yes(), TRANSPOSE, UJMP);
		setString(yes(), SCALE, UJMP);
		setString(yes(), PLUSMINUS, UJMP);
		setString(yes(), INV, UJMP);
		setString(squareTall(), SOLVE, UJMP);
		setString(yes(), CHOL, UJMP);
		setString(yes(), EIG, UJMP);
		setString(all(), LU, UJMP);
		setString(squareTall(), QR, UJMP);
		setString(all(), SVD, UJMP);
		setString(yes(), CSVIO, UJMP);
		setString(yes(), JDBCIO, UJMP);
		setString(yes(), SERIALIZABLE, UJMP);
		setString(bit64(), VALUESPERDIMENSION, UJMP);
		setString(hdd(), MAXIMUMSIZE, UJMP);

		setString(small("1.2.0"), VERSION, COLT);
		setString(small("2004"), DATE, COLT);
		setString(small("BSD"), LICENSE, COLT);
		setString(yes(), JAVA14, COLT);
		setString(yes(), JAVA5, COLT);
		setString(yes(), JAVA6, COLT);
		setString(yes(), JAVA7, COLT);
		setString(yes(), JAVA8, COLT);
		setString(no(), MULTITHREADED, COLT);
		setString(yes(), INPLACE, COLT);
		setString(yes() + footnote("f", "flags matrix as transposed"), TRANSPOSE, COLT);
		setString(yes(), SCALE, COLT);
		setString(yes(), PLUSMINUS, COLT);
		setString(yes(), DENSESA, COLT);
		setString(no(), DENSEAA, COLT);
		setString(no(), DENSEBLOCK, COLT);
		setString(yes(), SPARSEDOK, COLT);
		setString(no(), SPARSEYALE, COLT);
		setString(no(), SPARSELIL, COLT);
		setString(no(), SPARSECRSCCS, COLT);
		setString(no(), SPARSECDS, COLT);
		setString(yes(), DOUBLE, COLT);
		setString(no(), FLOAT, COLT);
		setString(no(), BIGDECIMAL, COLT);
		setString(yes(), STRINGS, COLT);
		setString(yes(), OBJECTS, COLT);
		setString(no(), GENERICS, COLT);
		setString(no(), COMPLEX, COLT);
		setString(yes(), D2, COLT);
		setString(yes(), D3, COLT);
		setString(no(), D3PLUS, COLT);
		setString(yes(), INV, COLT);
		setString(squareTall(), SOLVE, COLT);
		setString(all(), SVD, COLT);
		setString(squareTall(), LU, COLT);
		setString(squareTall(), QR, COLT);
		setString(yes(), EIG, COLT);
		setString(yes(), CHOL, COLT);
		setString(no(), CSVIO, COLT);
		setString(no(), JDBCIO, COLT);
		setString(yes(), SERIALIZABLE, COLT);
		setString(no(), CSVIO, COLT);
		setString(no(), JDBCIO, COLT);
		setString(yes(), SERIALIZABLE, COLT);
		setString(bit32(), VALUESPERDIMENSION, COLT);
		setString(singleArray(), MAXIMUMSIZE, COLT);

		setString(small("3.2"), VERSION, COMMONSMATH);
		setString(small("2013"), DATE, COMMONSMATH);
		setString(small("Apache"), LICENSE, COMMONSMATH);
		setString(no(), JAVA14, COMMONSMATH);
		setString(yes(), JAVA5, COMMONSMATH);
		setString(yes(), JAVA6, COMMONSMATH);
		setString(yes(), JAVA7, COMMONSMATH);
		setString(yes(), JAVA8, COMMONSMATH);
		setString(no(), MULTITHREADED, COMMONSMATH);
		setString(yes(), INPLACE, COMMONSMATH);
		setString(yes(), TRANSPOSE, COMMONSMATH);
		setString(yes(), SCALE, COMMONSMATH);
		setString(yes(), PLUSMINUS, COMMONSMATH);
		setString(yes(), DENSEAA, COMMONSMATH);
		setString(no(), DENSESA, COMMONSMATH);
		setString(yes(), DENSEBLOCK, COMMONSMATH);
		setString(yes(), SPARSEDOK, COMMONSMATH);
		setString(no(), SPARSEYALE, COMMONSMATH);
		setString(no(), SPARSELIL, COMMONSMATH);
		setString(no(), SPARSECRSCCS, COMMONSMATH);
		setString(no(), SPARSECDS, COMMONSMATH);
		setString(yes(), DOUBLE, COMMONSMATH);
		setString(no(), FLOAT, COMMONSMATH);
		setString(yes(), BIGDECIMAL, COMMONSMATH);
		setString(yes(), STRINGS, COMMONSMATH);
		setString(yes(), OBJECTS, COMMONSMATH);
		setString(yes(), GENERICS, COMMONSMATH);
		setString(yes(), COMPLEX, COMMONSMATH);
		setString(yes(), D2, COMMONSMATH);
		setString(no(), D3, COMMONSMATH);
		setString(no(), D3PLUS, COMMONSMATH);
		setString(yes(), INV, COMMONSMATH);
		setString(squareTall(), SOLVE, COMMONSMATH);
		setString(all(), SVD, COMMONSMATH);
		setString(square() + footnote(NONSINGULARLETTER, NONSINGULARTEXT), LU, COMMONSMATH);
		setString(all(), QR, COMMONSMATH);
		setString(yes() + footnote("s", SYMMETRICTEXT), EIG, COMMONSMATH);
		setString(yes(), CHOL, COMMONSMATH);
		setString(no(), CSVIO, COMMONSMATH);
		setString(no(), JDBCIO, COMMONSMATH);
		setString(yes(), SERIALIZABLE, COMMONSMATH);
		setString(bit32(), VALUESPERDIMENSION, COMMONSMATH);
		setString(ram(), MAXIMUMSIZE, COMMONSMATH);

		setString(small("0.25"), VERSION, EJML);
		setString(small("2014"), DATE, EJML);
		setString(small("Apache"), LICENSE, EJML);
		setString(no(), JAVA14, EJML);
		setString(no(), JAVA5, EJML);
		setString(yes(), JAVA6, EJML);
		setString(yes(), JAVA7, EJML);
		setString(yes(), JAVA8, EJML);
		setString(no(), MULTITHREADED, EJML);
		setString(yes(), INPLACE, EJML);
		setString(yes(), DENSESA, EJML);
		setString(no(), DENSEAA, EJML);
		setString(yes(), DENSEBLOCK, EJML);
		setString(no(), SPARSEDOK, EJML);
		setString(no(), SPARSEYALE, EJML);
		setString(no(), SPARSELIL, EJML);
		setString(no(), SPARSECRSCCS, EJML);
		setString(no(), SPARSECDS, EJML);
		setString(yes(), DOUBLE, EJML);
		setString(no(), FLOAT, EJML);
		setString(no(), BIGDECIMAL, EJML);
		setString(no(), STRINGS, EJML);
		setString(no(), OBJECTS, EJML);
		setString(no(), GENERICS, EJML);
		setString(no(), COMPLEX, EJML);
		setString(yes(), D2, EJML);
		setString(no(), D3, EJML);
		setString(no(), D3PLUS, EJML);
		setString(yes(), TRANSPOSE, EJML);
		setString(yes(), SCALE, EJML);
		setString(yes(), PLUSMINUS, EJML);
		setString(yes(), INV, EJML);
		setString(squareTall(), SOLVE, EJML);
		setString(all(), SVD, EJML);
		setString(all(), LU, EJML);
		setString(square(), QR, EJML);
		setString(yes(), EIG, EJML);
		setString(yes() + footnote("e", ERRORTEXT), CHOL, EJML);
		setString(no(), CSVIO, EJML);
		setString(no(), JDBCIO, EJML);
		setString(yes(), SERIALIZABLE, EJML);
		setString(bit32(), VALUESPERDIMENSION, EJML);
		setString(singleArray(), MAXIMUMSIZE, EJML);

		setString(small("1.0.3"), VERSION, JAMA);
		setString(small("2012"), DATE, JAMA);
		setString(small("PD"), LICENSE, JAMA);
		setString(yes(), JAVA14, JAMA);
		setString(yes(), JAVA5, JAMA);
		setString(yes(), JAVA6, JAMA);
		setString(yes(), JAVA7, JAMA);
		setString(yes(), JAVA8, JAMA);
		setString(no(), MULTITHREADED, JAMA);
		setString(no(), INPLACE, JAMA);
		setString(yes(), DENSEAA, JAMA);
		setString(no(), DENSESA, JAMA);
		setString(no(), DENSEBLOCK, JAMA);
		setString(no(), SPARSEDOK, JAMA);
		setString(no(), SPARSEYALE, JAMA);
		setString(no(), SPARSELIL, JAMA);
		setString(no(), SPARSECRSCCS, JAMA);
		setString(no(), SPARSECDS, JAMA);
		setString(yes(), DOUBLE, JAMA);
		setString(no(), FLOAT, JAMA);
		setString(no(), BIGDECIMAL, JAMA);
		setString(no(), STRINGS, JAMA);
		setString(no(), OBJECTS, JAMA);
		setString(no(), GENERICS, JAMA);
		setString(no(), COMPLEX, JAMA);
		setString(yes(), D2, JAMA);
		setString(no(), D3, JAMA);
		setString(no(), D3PLUS, JAMA);
		setString(yes(), TRANSPOSE, JAMA);
		setString(yes(), SCALE, JAMA);
		setString(yes(), PLUSMINUS, JAMA);
		setString(yes(), INV, JAMA);
		setString(squareTall(), SOLVE, JAMA);
		setString(squareTall(), SVD, JAMA);
		setString(squareTall(), LU, JAMA);
		setString(squareTall(), QR, JAMA);
		setString(yes(), EIG, JAMA);
		setString(yes(), CHOL, JAMA);
		setString(no(), CSVIO, JAMA);
		setString(no(), JDBCIO, JAMA);
		setString(yes(), SERIALIZABLE, JAMA);
		setString(bit32(), VALUESPERDIMENSION, JAMA);
		setString(ram(), MAXIMUMSIZE, JAMA);

		setString(small("1.2.3"), VERSION, JBLAS);
		setString(small("2013"), DATE, JBLAS);
		setString(small("BSD"), LICENSE, JBLAS);
		setString(no(), JAVA14, JBLAS);
		setString(yes(), JAVA5, JBLAS);
		setString(yes(), JAVA6, JBLAS);
		setString(yes(), JAVA7, JBLAS);
		setString(yes(), JAVA8, JBLAS);
		setString(yes() + footnote("m", "using native machine code"), MULTITHREADED, JBLAS);
		setString(yes(), INPLACE, JBLAS);
		setString(yes(), DENSESA, JBLAS);
		setString(no(), DENSEAA, JBLAS);
		setString(no(), DENSEBLOCK, JBLAS);
		setString(no(), SPARSEDOK, JBLAS);
		setString(no(), SPARSEYALE, JBLAS);
		setString(no(), SPARSELIL, JBLAS);
		setString(no(), SPARSECRSCCS, JBLAS);
		setString(no(), SPARSECDS, JBLAS);
		setString(yes(), COMPLEX, JBLAS);
		setString(yes(), DOUBLE, JBLAS);
		setString(yes(), FLOAT, JBLAS);
		setString(no(), BIGDECIMAL, JBLAS);
		setString(no(), STRINGS, JBLAS);
		setString(no(), OBJECTS, JBLAS);
		setString(no(), GENERICS, JBLAS);
		setString(yes(), D2, JBLAS);
		setString(no(), D3, JBLAS);
		setString(no(), D3PLUS, JBLAS);
		setString(yes(), TRANSPOSE, JBLAS);
		setString(yes(), SCALE, JBLAS);
		setString(yes(), PLUSMINUS, JBLAS);
		setString(yes(), INV, JBLAS);
		setString(square(), SOLVE, JBLAS);
		setString(no(), SVD, JBLAS);
		setString(all(), LU, JBLAS);
		setString(no(), QR, JBLAS);
		setString(yes() + footnote("s", SYMMETRICTEXT), EIG, JBLAS);
		setString(yes(), CHOL, JBLAS);
		setString(no(), CSVIO, JBLAS);
		setString(no(), JDBCIO, JBLAS);
		setString(yes(), SERIALIZABLE, JBLAS);
		setString(bit32(), VALUESPERDIMENSION, JBLAS);
		setString(singleArray(), MAXIMUMSIZE, JBLAS);

		setString(small("0.6"), VERSION, JLINALG);
		setString(small("2009"), DATE, JLINALG);
		setString(small("GPL"), LICENSE, JLINALG);
		setString(no(), JAVA14, JLINALG);
		setString(no(), JAVA5, JLINALG);
		setString(yes(), JAVA6, JLINALG);
		setString(yes(), JAVA7, JLINALG);
		setString(yes(), JAVA8, JLINALG);
		setString(no(), MULTITHREADED, JLINALG);
		setString(yes(), INPLACE, JLINALG);
		setString(yes(), DENSEAA, JLINALG);
		setString(no(), DENSESA, JLINALG);
		setString(no(), DENSEBLOCK, JLINALG);
		setString(no(), SPARSEDOK, JLINALG);
		setString(no(), SPARSEYALE, JLINALG);
		setString(no(), SPARSELIL, JLINALG);
		setString(no(), SPARSECRSCCS, JLINALG);
		setString(no(), SPARSECDS, JLINALG);
		setString(yes(), COMPLEX, JLINALG);
		setString(yes(), DOUBLE, JLINALG);
		setString(no(), FLOAT, JLINALG);
		setString(yes(), BIGDECIMAL, JLINALG);
		setString(yes(), STRINGS, JLINALG);
		setString(yes(), OBJECTS, JLINALG);
		setString(yes(), GENERICS, JLINALG);
		setString(yes(), TRANSPOSE, JLINALG);
		setString(yes(), SCALE, JLINALG);
		setString(yes(), PLUSMINUS, JLINALG);
		setString(yes(), D2, JLINALG);
		setString(no(), D3, JLINALG);
		setString(no(), D3PLUS, JLINALG);
		setString(yes(), INV, JLINALG);
		setString(no() + footnote("v", "only for $A \\cdot X = \\mbox{vector}$"), SOLVE, JLINALG);
		setString(no(), SVD, JLINALG);
		setString(no(), LU, JLINALG);
		setString(no(), QR, JLINALG);
		setString(no(), EIG, JLINALG);
		setString(no(), CHOL, JLINALG);
		setString(no(), CSVIO, JLINALG);
		setString(no(), JDBCIO, JLINALG);
		setString(yes(), SERIALIZABLE, JLINALG);
		setString(bit32(), VALUESPERDIMENSION, JLINALG);
		setString(ram(), MAXIMUMSIZE, JLINALG);

		setString("", VERSION, JMATHARRAY);
		setString(small("2008"), DATE, JMATHARRAY);
		setString(small("BSD"), LICENSE, JMATHARRAY);
		setString(no(), JAVA14, JMATHARRAY);
		setString(yes(), JAVA5, JMATHARRAY);
		setString(yes(), JAVA6, JMATHARRAY);
		setString(yes(), JAVA7, JMATHARRAY);
		setString(yes(), JAVA8, JMATHARRAY);
		setString(no(), MULTITHREADED, JMATHARRAY);
		setString(no(), INPLACE, JMATHARRAY);
		setString(yes(), TRANSPOSE, JMATHARRAY);
		setString(yes(), SCALE, JMATHARRAY);
		setString(yes(), PLUSMINUS, JMATHARRAY);
		setString(yes(), DENSEAA, JMATHARRAY);
		setString(no(), DENSESA, JMATHARRAY);
		setString(no(), DENSEBLOCK, JMATHARRAY);
		setString(no(), SPARSEDOK, JMATHARRAY);
		setString(no(), SPARSEYALE, JMATHARRAY);
		setString(no(), SPARSELIL, JMATHARRAY);
		setString(no(), SPARSECRSCCS, JMATHARRAY);
		setString(no(), SPARSECDS, JMATHARRAY);
		setString(no(), COMPLEX, JMATHARRAY);
		setString(yes(), DOUBLE, JMATHARRAY);
		setString(no(), FLOAT, JMATHARRAY);
		setString(no(), BIGDECIMAL, JMATHARRAY);
		setString(no(), STRINGS, JMATHARRAY);
		setString(no(), OBJECTS, JMATHARRAY);
		setString(no(), GENERICS, JMATHARRAY);
		setString(yes(), D2, JMATHARRAY);
		setString(no(), D3, JMATHARRAY);
		setString(no(), D3PLUS, JMATHARRAY);
		setString(yes(), INV, JMATHARRAY);
		setString(squareTall(), SOLVE, JMATHARRAY);
		setString(squareTall(), SVD, JMATHARRAY);
		setString(squareTall(), LU, JMATHARRAY);
		setString(squareTall(), QR, JMATHARRAY);
		setString(yes(), EIG, JMATHARRAY);
		setString(yes(), CHOL, JMATHARRAY);
		setString(no(), CSVIO, JMATHARRAY);
		setString(no(), JDBCIO, JMATHARRAY);
		setString(yes(), SERIALIZABLE, JMATHARRAY);
		setString(bit32(), VALUESPERDIMENSION, JMATHARRAY);
		setString(ram(), MAXIMUMSIZE, JMATHARRAY);

		setString(small("0.6"), VERSION, JMATRICES);
		setString(small("2004"), DATE, JMATRICES);
		setString(small("LGPL"), LICENSE, JMATRICES);
		setString(yes(), JAVA14, JMATRICES);
		setString(yes(), JAVA5, JMATRICES);
		setString(yes(), JAVA6, JMATRICES);
		setString(yes(), JAVA7, JMATRICES);
		setString(yes(), JAVA8, JMATRICES);
		setString(no(), MULTITHREADED, JMATRICES);
		setString(no(), INPLACE, JMATRICES);
		setString(yes(), DENSEAA, JMATRICES);
		setString(no(), DENSESA, JMATRICES);
		setString(no(), DENSEBLOCK, JMATRICES);
		setString(no(), SPARSEDOK, JMATRICES);
		setString(no(), SPARSEYALE, JMATRICES);
		setString(no(), SPARSELIL, JMATRICES);
		setString(no(), SPARSECRSCCS, JMATRICES);
		setString(no(), SPARSECDS, JMATRICES);
		setString(yes(), COMPLEX, JMATRICES);
		setString(yes(), DOUBLE, JMATRICES);
		setString(no(), FLOAT, JMATRICES);
		setString(yes(), BIGDECIMAL, JMATRICES);
		setString(no(), STRINGS, JMATRICES);
		setString(no(), OBJECTS, JMATRICES);
		setString(no(), GENERICS, JMATRICES);
		setString(yes(), D2, JMATRICES);
		setString(no(), D3, JMATRICES);
		setString(no(), D3PLUS, JMATRICES);
		setString(yes(), TRANSPOSE, JMATRICES);
		setString(yes(), PLUSMINUS, JMATRICES);
		setString(yes(), SCALE, JMATRICES);
		setString(yes(), INV, JMATRICES);
		setString(squareTall(), SOLVE, JMATRICES);
		setString(square(), SVD, JMATRICES);
		setString(squareTall(), LU, JMATRICES);
		setString(squareTall(), QR, JMATRICES);
		setString(yes(), EIG, JMATRICES);
		setString(yes(), CHOL, JMATRICES);
		setString(no(), CSVIO, JMATRICES);
		setString(no(), JDBCIO, JMATRICES);
		setString(yes(), SERIALIZABLE, JMATRICES);
		setString(bit32(), VALUESPERDIMENSION, JMATRICES);
		setString(ram(), MAXIMUMSIZE, JMATRICES);

		setString(small("1.5.2"), VERSION, JSCI);
		setString(small("2009"), DATE, JSCI);
		setString(small("LGPL"), LICENSE, JSCI);
		setString(yes(), JAVA14, JSCI);
		setString(yes(), JAVA5, JSCI);
		setString(yes(), JAVA6, JSCI);
		setString(yes(), JAVA7, JSCI);
		setString(yes(), JAVA8, JSCI);
		setString(no(), MULTITHREADED, JSCI);
		setString(no(), INPLACE, JSCI);
		setString(yes(), DENSEAA, JSCI);
		setString(no(), DENSESA, JSCI);
		setString(no(), DENSEBLOCK, JSCI);
		setString(yes(), SPARSEYALE, JSCI);
		setString(no(), SPARSEDOK, JSCI);
		setString(no(), SPARSELIL, JSCI);
		setString(no(), SPARSECRSCCS, JSCI);
		setString(no(), SPARSECDS, JSCI);
		setString(yes(), COMPLEX, JSCI);
		setString(yes(), DOUBLE, JSCI);
		setString(no(), FLOAT, JSCI);
		setString(no(), BIGDECIMAL, JSCI);
		setString(no(), STRINGS, JSCI);
		setString(no(), OBJECTS, JSCI);
		setString(no(), GENERICS, JSCI);
		setString(yes(), D2, JSCI);
		setString(no(), D3, JSCI);
		setString(no(), D3PLUS, JSCI);
		setString(yes(), TRANSPOSE, JSCI);
		setString(yes(), PLUSMINUS, JSCI);
		setString(yes(), SCALE, JSCI);
		setString(no() + footnote("v", "only for $A \\cdot X = \\mbox{vector}$"), SOLVE, JSCI);
		setString(yes(), INV, JSCI);
		setString(square() + footnote(NONSINGULARLETTER, NONSINGULARTEXT), SVD, JSCI);
		setString(square() + footnote(NONSINGULARLETTER, NONSINGULARTEXT), LU, JSCI);
		setString(square(), QR, JSCI);
		setString(
				yes() + footnote("s", SYMMETRICTEXT)
						+ footnote("a", "results not directly accessible"), EIG, JSCI);
		setString(yes(), CHOL, JSCI);
		setString(no(), CSVIO, JSCI);
		setString(no(), JDBCIO, JSCI);
		setString(yes(), SERIALIZABLE, JSCI);
		setString(bit32(), VALUESPERDIMENSION, JSCI);
		setString(ram(), MAXIMUMSIZE, JSCI);

		setString(small("4.3.1"), VERSION, JSCIENCE);
		setString(small("2007"), DATE, JSCIENCE);
		setString(small("BSD"), LICENSE, JSCIENCE);
		setString(no(), JAVA14, JSCIENCE);
		setString(yes(), JAVA5, JSCIENCE);
		setString(yes(), JAVA6, JSCIENCE);
		setString(yes(), JAVA7, JSCIENCE);
		setString(yes(), JAVA8, JSCIENCE);
		setString(yes(), MULTITHREADED, JSCIENCE);
		setString(no(), INPLACE, JSCIENCE);
		setString(yes(), DENSEAA, JSCIENCE);
		setString(no(), DENSEBLOCK, JSCIENCE);
		setString(no(), DENSESA, JSCIENCE);
		setString(yes(), SPARSELIL, JSCIENCE);
		setString(no(), SPARSEYALE, JSCIENCE);
		setString(no(), SPARSECDS, JSCIENCE);
		setString(no(), SPARSECRSCCS, JSCIENCE);
		setString(yes(), SPARSEDOK, JSCIENCE);
		setString(yes(), COMPLEX, JSCIENCE);
		setString(yes(), DOUBLE, JSCIENCE);
		setString(no(), FLOAT, JSCIENCE);
		setString(no(), BIGDECIMAL, JSCIENCE);
		setString(yes(), STRINGS, JSCIENCE);
		setString(yes(), OBJECTS, JSCIENCE);
		setString(yes(), GENERICS, JSCIENCE);
		setString(yes(), D2, JSCIENCE);
		setString(no(), D3, JSCIENCE);
		setString(no(), D3PLUS, JSCIENCE);
		setString(yes() + footnote("f", "flags matrix as transposed"), TRANSPOSE, JSCIENCE);
		setString(yes(), SCALE, JSCIENCE);
		setString(yes(), PLUSMINUS, JSCIENCE);
		setString(yes(), INV, JSCIENCE);
		setString(square(), SOLVE, JSCIENCE);
		setString(no(), SVD, JSCIENCE);
		setString(square() + footnote(NONSINGULARLETTER, NONSINGULARTEXT), LU, JSCIENCE);
		setString(no(), QR, JSCIENCE);
		setString(no(), EIG, JSCIENCE);
		setString(no(), CHOL, JSCIENCE);
		setString(no(), CSVIO, JSCIENCE);
		setString(no(), JDBCIO, JSCIENCE);
		setString(no(), SERIALIZABLE, JSCIENCE);
		setString(bit32(), VALUESPERDIMENSION, JSCIENCE);
		setString(ram(), MAXIMUMSIZE, JSCIENCE);

		setString(small("0.4.9"), VERSION, LA4J);
		setString(small("2014"), DATE, LA4J);
		setString(small("Apache"), LICENSE, LA4J);
		setString(no(), JAVA14, LA4J);
		setString(yes(), JAVA5, LA4J);
		setString(yes(), JAVA6, LA4J);
		setString(yes(), JAVA7, LA4J);
		setString(yes(), JAVA8, LA4J);
		setString(no(), MULTITHREADED, LA4J);
		setString(yes(), INPLACE, LA4J);
		setString(yes(), TRANSPOSE, LA4J);
		setString(yes(), PLUSMINUS, LA4J);
		setString(yes(), SCALE, LA4J);
		setString(no(), DENSESA, LA4J);
		setString(yes(), DENSEAA, LA4J);
		setString(no(), DENSEBLOCK, LA4J);
		setString(no(), SPARSEDOK, LA4J);
		setString(no(), SPARSEYALE, LA4J);
		setString(no(), SPARSELIL, LA4J);
		setString(yes(), SPARSECRSCCS, LA4J);
		setString(no(), SPARSECDS, LA4J);
		setString(no(), COMPLEX, LA4J);
		setString(yes(), DOUBLE, LA4J);
		setString(no(), FLOAT, LA4J);
		setString(no(), BIGDECIMAL, LA4J);
		setString(no(), STRINGS, LA4J);
		setString(no(), OBJECTS, LA4J);
		setString(no(), GENERICS, LA4J);
		setString(yes(), D2, LA4J);
		setString(no(), D3, LA4J);
		setString(no(), D3PLUS, LA4J);
		setString(yes(), INV, LA4J);
		setString(squareTall(), SOLVE, LA4J);
		setString(all(), SVD, LA4J);
		setString(square(), LU, LA4J);
		setString(squareTall(), QR, LA4J);
		setString(yes(), EIG, LA4J);
		setString(yes(), CHOL, LA4J);
		setString(no(), CSVIO, LA4J);
		setString(no(), JDBCIO, LA4J);
		setString(yes(), SERIALIZABLE, LA4J);
		setString(bit32(), VALUESPERDIMENSION, LA4J);
		setString(ram(), MAXIMUMSIZE, LA4J);

		setString(small("7.2"), VERSION, MANTISSA);
		setString(small("2007"), DATE, MANTISSA);
		setString(small("BSD"), LICENSE, MANTISSA);
		setString(yes(), JAVA14, MANTISSA);
		setString(yes(), JAVA5, MANTISSA);
		setString(yes(), JAVA6, MANTISSA);
		setString(yes(), JAVA7, MANTISSA);
		setString(yes(), JAVA8, MANTISSA);
		setString(no(), MULTITHREADED, MANTISSA);
		setString(yes(), INPLACE, MANTISSA);
		setString(yes(), TRANSPOSE, MANTISSA);
		setString(yes(), PLUSMINUS, MANTISSA);
		setString(yes(), SCALE, MANTISSA);
		setString(yes(), DENSESA, MANTISSA);
		setString(no(), DENSEAA, MANTISSA);
		setString(no(), DENSEBLOCK, MANTISSA);
		setString(no(), SPARSEDOK, MANTISSA);
		setString(no(), SPARSEYALE, MANTISSA);
		setString(no(), SPARSELIL, MANTISSA);
		setString(no(), SPARSECRSCCS, MANTISSA);
		setString(no(), SPARSECDS, MANTISSA);
		setString(no(), COMPLEX, MANTISSA);
		setString(yes(), DOUBLE, MANTISSA);
		setString(no(), FLOAT, MANTISSA);
		setString(no(), BIGDECIMAL, MANTISSA);
		setString(no(), STRINGS, MANTISSA);
		setString(no(), OBJECTS, MANTISSA);
		setString(no(), GENERICS, MANTISSA);
		setString(yes(), D2, MANTISSA);
		setString(no(), D3, MANTISSA);
		setString(no(), D3PLUS, MANTISSA);
		setString(yes(), INV, MANTISSA);
		setString(square(), SOLVE, MANTISSA);
		setString(no(), SVD, MANTISSA);
		setString(square() + footnote("a", "results not directly accessible"), LU, MANTISSA);
		setString(no(), QR, MANTISSA);
		setString(no(), EIG, MANTISSA);
		setString(no(), CHOL, MANTISSA);
		setString(no(), CSVIO, MANTISSA);
		setString(no(), JDBCIO, MANTISSA);
		setString(yes(), SERIALIZABLE, MANTISSA);
		setString(bit32(), VALUESPERDIMENSION, MANTISSA);
		setString(singleArray(), MAXIMUMSIZE, MANTISSA);

		setString(small("1.0.1"), VERSION, MTJ);
		setString(small("2013"), DATE, MTJ);
		setString(small("LGPL"), LICENSE, MTJ);
		setString(no(), JAVA14, MTJ);
		setString(yes(), JAVA5, MTJ);
		setString(yes(), JAVA6, MTJ);
		setString(yes(), JAVA7, MTJ);
		setString(yes(), JAVA8, MTJ);
		setString(yes() + footnote("m", "using native machine code"), MULTITHREADED, MTJ);
		setString(yes(), INPLACE, MTJ);
		setString(yes(), DENSESA, MTJ);
		setString(no(), DENSEAA, MTJ);
		setString(no(), DENSEBLOCK, MTJ);
		setString(yes(), SPARSECRSCCS, MTJ);
		setString(yes(), SPARSECDS, MTJ);
		setString(yes(), SPARSELIL, MTJ);
		setString(no(), SPARSEDOK, MTJ);
		setString(no(), SPARSEYALE, MTJ);
		setString(no(), COMPLEX, MTJ);
		setString(yes(), DOUBLE, MTJ);
		setString(no(), FLOAT, MTJ);
		setString(no(), BIGDECIMAL, MTJ);
		setString(no(), STRINGS, MTJ);
		setString(no(), OBJECTS, MTJ);
		setString(no(), GENERICS, MTJ);
		setString(yes(), D2, MTJ);
		setString(no(), D3, MTJ);
		setString(no(), D3PLUS, MTJ);
		setString(yes(), TRANSPOSE, MTJ);
		setString(yes(), SCALE, MTJ);
		setString(yes(), PLUSMINUS, MTJ);
		setString(yes(), INV, MTJ);
		setString(squareTall(), SOLVE, MTJ);
		setString(all(), SVD, MTJ);
		setString(all() + footnote("e", ERRORTEXT), LU, MTJ);
		setString(squareTall(), QR, MTJ);
		setString(yes() + footnote("s", SYMMETRICTEXT), EIG, MTJ);
		setString(yes() + footnote("e", ERRORTEXT), CHOL, MTJ);
		setString(no(), CSVIO, MTJ);
		setString(no(), JDBCIO, MTJ);
		setString(yes(), SERIALIZABLE, MTJ);
		setString(bit32(), VALUESPERDIMENSION, MTJ);
		setString(singleArray(), MAXIMUMSIZE, MTJ);

		setString(small("35.0"), VERSION, OJALGO);
		setString(small("2013"), DATE, OJALGO);
		setString(small("MIT"), LICENSE, OJALGO);
		setString(no(), JAVA14, OJALGO);
		setString(yes(), JAVA5, OJALGO);
		setString(yes(), JAVA6, OJALGO);
		setString(yes(), JAVA7, OJALGO);
		setString(yes(), JAVA8, OJALGO);
		setString(yes(), MULTITHREADED, OJALGO);
		setString(yes(), INPLACE, OJALGO);
		setString(yes() + footnote("f", "flags matrix as transposed"), TRANSPOSE, OJALGO);
		setString(yes(), SCALE, OJALGO);
		setString(yes(), PLUSMINUS, OJALGO);
		setString(yes(), DENSESA, OJALGO);
		setString(no(), DENSEAA, OJALGO);
		setString(no(), DENSEBLOCK, OJALGO);
		setString(no(), SPARSECDS, OJALGO);
		setString(no(), SPARSECRSCCS, OJALGO);
		setString(no(), SPARSEYALE, OJALGO);
		setString(no(), SPARSELIL, OJALGO);
		setString(no(), SPARSEDOK, OJALGO);
		setString(yes(), COMPLEX, OJALGO);
		setString(yes(), DOUBLE, OJALGO);
		setString(yes(), FLOAT, OJALGO);
		setString(yes(), BIGDECIMAL, OJALGO);
		setString(no(), STRINGS, OJALGO);
		setString(no(), OBJECTS, OJALGO);
		setString(no(), GENERICS, OJALGO);
		setString(yes(), D2, OJALGO);
		setString(no(), D3, OJALGO);
		setString(no(), D3PLUS, OJALGO);
		setString(yes(), INV, OJALGO);
		setString(squareTall(), SOLVE, OJALGO);
		setString(all(), SVD, OJALGO);
		setString(all(), LU, OJALGO);
		setString(all(), QR, OJALGO);
		setString(yes(), EIG, OJALGO);
		setString(yes(), CHOL, OJALGO);
		setString(no(), CSVIO, OJALGO);
		setString(no(), JDBCIO, OJALGO);
		setString(yes(), SERIALIZABLE, OJALGO);
		setString(bit32(), VALUESPERDIMENSION, OJALGO);
		setString(singleArray(), MAXIMUMSIZE, OJALGO);

		setString(small("0.10.1"), VERSION, PARALLELCOLT);
		setString(small("2013"), DATE, PARALLELCOLT);
		setString(small("BSD"), LICENSE, PARALLELCOLT);
		setString(yes(), JAVA14, PARALLELCOLT);
		setString(yes(), JAVA5, PARALLELCOLT);
		setString(yes(), JAVA6, PARALLELCOLT);
		setString(yes(), JAVA7, PARALLELCOLT);
		setString(yes(), JAVA8, PARALLELCOLT);
		setString(yes(), MULTITHREADED, PARALLELCOLT);
		setString(yes(), INPLACE, PARALLELCOLT);
		setString(yes() + footnote("f", "flags matrix as transposed"), TRANSPOSE, PARALLELCOLT);
		setString(yes(), SCALE, PARALLELCOLT);
		setString(yes(), PLUSMINUS, PARALLELCOLT);
		setString(yes(), DENSESA, PARALLELCOLT);
		setString(yes(), DENSEAA, PARALLELCOLT);
		setString(no(), DENSEBLOCK, PARALLELCOLT);
		setString(yes(), SPARSECRSCCS, PARALLELCOLT);
		setString(yes(), SPARSELIL, PARALLELCOLT);
		setString(yes(), SPARSEDOK, PARALLELCOLT);
		setString(no(), SPARSEYALE, PARALLELCOLT);
		setString(no(), SPARSECDS, PARALLELCOLT);
		setString(yes(), COMPLEX, PARALLELCOLT);
		setString(yes(), DOUBLE, PARALLELCOLT);
		setString(yes(), FLOAT, PARALLELCOLT);
		setString(no(), BIGDECIMAL, PARALLELCOLT);
		setString(yes(), STRINGS, PARALLELCOLT);
		setString(yes(), OBJECTS, PARALLELCOLT);
		setString(no(), GENERICS, PARALLELCOLT);
		setString(yes(), D2, PARALLELCOLT);
		setString(yes(), D3, PARALLELCOLT);
		setString(no(), D3PLUS, PARALLELCOLT);
		setString(yes(), INV, PARALLELCOLT);
		setString(squareTall(), SOLVE, PARALLELCOLT);
		setString(all(), SVD, PARALLELCOLT);
		setString(squareTall(), LU, PARALLELCOLT);
		setString(squareTall(), QR, PARALLELCOLT);
		setString(yes(), EIG, PARALLELCOLT);
		setString(yes(), CHOL, PARALLELCOLT);
		setString(no(), CSVIO, PARALLELCOLT);
		setString(no(), JDBCIO, PARALLELCOLT);
		setString(yes(), SERIALIZABLE, PARALLELCOLT);
		setString(bit32(), VALUESPERDIMENSION, PARALLELCOLT);
		setString(ram(), MAXIMUMSIZE, PARALLELCOLT);

		setString(small("1.11"), VERSION, SST);
		setString(small("2010"), DATE, SST);
		setString(small("LGPL"), LICENSE, SST);
		setString(no(), JAVA14, SST);
		setString(yes() + footnote("j", "jar does not work with Java 5"), JAVA5, SST);
		setString(yes(), JAVA6, SST);
		setString(yes(), JAVA7, SST);
		setString(yes(), JAVA8, SST);
		setString(no(), MULTITHREADED, SST);
		setString(yes(), INPLACE, SST);
		setString(yes(), TRANSPOSE, SST);
		setString(yes(), SCALE, SST);
		setString(yes(), PLUSMINUS, SST);
		setString(yes(), DENSESA, SST);
		setString(no(), DENSEAA, SST);
		setString(no(), DENSEBLOCK, SST);
		setString(yes(), SPARSEDOK, SST);
		setString(no(), SPARSELIL, SST);
		setString(no(), SPARSEYALE, SST);
		setString(no(), SPARSECDS, SST);
		setString(no(), SPARSECRSCCS, SST);
		setString(yes(), COMPLEX, SST);
		setString(yes(), DOUBLE, SST);
		setString(no(), FLOAT, SST);
		setString(no(), BIGDECIMAL, SST);
		setString(yes(), STRINGS, SST);
		setString(yes(), OBJECTS, SST);
		setString(yes(), GENERICS, SST);
		setString(yes(), D2, SST);
		setString(yes(), D3, SST);
		setString(yes(), D3PLUS, SST);
		setString(yes(), INV, SST);
		setString(no(), SOLVE, SST);
		setString(all(), SVD, SST);
		setString(no(), LU, SST);
		setString(no(), QR, SST);
		setString(yes(), EIG, SST);
		setString(no(), CHOL, SST);
		setString(no(), CSVIO, SST);
		setString(no(), JDBCIO, SST);
		setString(no(), SERIALIZABLE, SST);
		setString(bit32(), VALUESPERDIMENSION, SST);
		setString(singleArray(), MAXIMUMSIZE, SST);

		setString(small("1.5.2"), VERSION, VECMATH);
		setString("2001?", DATE, VECMATH);
		setString(small("other"), LICENSE, VECMATH);
		setString(no(), JAVA14, VECMATH);
		setString(yes(), JAVA5, VECMATH);
		setString(yes(), JAVA6, VECMATH);
		setString(yes(), JAVA7, VECMATH);
		setString(yes(), JAVA8, VECMATH);
		setString(no(), MULTITHREADED, VECMATH);
		setString(yes(), INPLACE, VECMATH);
		setString(yes(), TRANSPOSE, VECMATH);
		setString(no(), SCALE, VECMATH);
		setString(yes(), PLUSMINUS, VECMATH);
		setString(yes(), DENSEAA, VECMATH);
		setString(no(), DENSESA, VECMATH);
		setString(no(), DENSEBLOCK, VECMATH);
		setString(no(), SPARSEDOK, VECMATH);
		setString(no(), SPARSELIL, VECMATH);
		setString(no(), SPARSECDS, VECMATH);
		setString(no(), SPARSECRSCCS, VECMATH);
		setString(no(), SPARSEYALE, VECMATH);
		setString(no(), COMPLEX, VECMATH);
		setString(yes(), DOUBLE, VECMATH);
		setString(no(), FLOAT, VECMATH);
		setString(no(), BIGDECIMAL, VECMATH);
		setString(no(), STRINGS, VECMATH);
		setString(no(), OBJECTS, VECMATH);
		setString(no(), GENERICS, VECMATH);
		setString(yes(), D2, VECMATH);
		setString(no(), D3, VECMATH);
		setString(no(), D3PLUS, VECMATH);
		setString(yes(), INV, VECMATH);
		setString(no(), SOLVE, VECMATH);
		setString(no() + footnote("e", ERRORTEXT), SVD, VECMATH);
		setString(square() + footnote(NONSINGULARLETTER, NONSINGULARTEXT), LU, VECMATH);
		setString(no(), QR, VECMATH);
		setString(no(), EIG, VECMATH);
		setString(no(), CHOL, VECMATH);
		setString(no(), CSVIO, VECMATH);
		setString(no(), JDBCIO, VECMATH);
		setString(yes(), SERIALIZABLE, VECMATH);
		setString(bit32(), VALUESPERDIMENSION, VECMATH);
		setString(ram(), MAXIMUMSIZE, VECMATH);

	}

	public String yes() {
		switch (format) {
		case LATEX:
			return "\\bf{$+$}";
		case HTMLTABLE:
			return "<span class=\"text-success fa fa-check-square\"></span>";
		case HTMLLIST:
			return "yes";
		default:
			return "yes";
		}
	}

	public String hdd() {
		switch (format) {
		case LATEX:
			return "~4TB (Disk)";
		case HTMLTABLE:
			return "~4TB (Disk)";
		case HTMLLIST:
			return "~4TB (Disk)";
		default:
			return "~4TB (Disk)";
		}
	}

	public String ram() {
		switch (format) {
		case LATEX:
			return "~64GB (RAM)";
		case HTMLTABLE:
			return "~64GB (RAM)";
		case HTMLLIST:
			return "~64GB (RAM)";
		default:
			return "~64GB (RAM)";
		}
	}

	public String singleArray() {
		switch (format) {
		case LATEX:
			return "16GB (Single Array)";
		case HTMLTABLE:
			return "16GB (Single Array)";
		case HTMLLIST:
			return "16GB (Single Array)";
		default:
			return "16GB (Single Array)";
		}
	}

	public String bit64() {
		switch (format) {
		case LATEX:
			return "2^{63}-1";
		case HTMLTABLE:
			return "2<sup>63</sup>-1";
		case HTMLLIST:
			return "2<sup>63</sup>-1";
		default:
			return "2^63-1";
		}
	}

	public String bit32() {
		switch (format) {
		case LATEX:
			return "2^{31}-1";
		case HTMLTABLE:
			return "2<sup>31</sup>-1";
		case HTMLLIST:
			return "2<sup>31</sup>-1";
		default:
			return "2^31-1";
		}
	}

	public String all() {
		switch (format) {
		case LATEX:
			return "\\scalebox{0.6}[1.0]{$\\square$}\\,$\\square$\\,\\scalebox{1.0}[0.6]{$\\square$}";
		case HTMLTABLE:
			return "<span class=\"fa fa-arrows-v\"></span> <span class=\"fa fa-arrows\"></span> <span class=\"fa fa-arrows-h\"></span>";
		default:
			return "all";
		}
	}

	public String squareTall() {
		switch (format) {
		case LATEX:
			return "\\scalebox{0.6}[1.0]{$\\square$}\\,$\\square$";
		case HTMLTABLE:
			return "<span class=\"fa fa-arrows-v\"></span> <span class=\"fa fa-arrows\"></span>";
		default:
			return "square, tall";
		}
	}

	public String square() {
		switch (format) {
		case LATEX:
			return "$\\square$";
		case HTMLTABLE:
			return "<span class=\"fa fa-arrows\"></span>";
		default:
			return "square";
		}
	}

	public String tall() {
		switch (format) {
		case LATEX:
			return "$\\tall$";
		case HTMLTABLE:
			return "<span class=\"fa fa-arrows-v\"></span>";
		default:
			return "tall";
		}
	}

	public String fat() {
		switch (format) {
		case LATEX:
			return "$\\fat$";
		case HTMLTABLE:
			return "<span class=\"fa fa-arrows-h\"></span>";
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

	public String no() {
		switch (format) {
		case LATEX:
			return "\\bf{$-$}";
		case HTMLTABLE:
			return "<span class=\"text-danger fa fa-minus-square\"></span>";
		case HTMLLIST:
			return "no";
		default:
			return "no";
		}
	}

	public String link(String label, String link) {
		switch (format) {
		case HTMLTABLE:
			return "<a href=\"" + link + "\">" + label + "</a>";
		case HTMLLIST:
			return "<a href=\"" + link + "\">" + label + "</a>";
		default:
			return label;
		}
	}

	public String rotate(String text) {
		switch (format) {
		case LATEX:
			return "\\begin{turn}{90}" + text + "\\end{turn}";
		default:
			return text;
		}
	}

	public String small(String text) {
		switch (format) {
		case LATEX:
			return "\\small " + text;
		case HTMLLIST:
			return text;
		case HTMLTABLE:
			return "<small>" + text + "</small>";
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
		case HTMLTABLE:
			return "";
		default:
			f = footnote + " " + text;
			if (!footnotes.contains(f)) {
				footnotes.add(f);
			}
			return " (" + text + ")";
		}
	}

	public List<String> getFootnotes() {
		return footnotes;
	}

	public static void main(String[] args) throws Exception {
		String s = new MatrixLibraries(MatrixLibrariesFormat.HTMLTABLE).getHtmlTable();
		System.out.println(s);
	}

	public static void printLatex(String[] args) throws Exception {
		MatrixLibraries ml = new MatrixLibraries(MatrixLibrariesFormat.LATEX);
		Matrix m = ml.deleteRows(Ret.NEW, ml.getRowCount() - 1);
		String s = m.exportTo().string().asLatex();
		s = s.replaceAll("table", "sidewaystable");
		s = s.replaceAll("\\\\centering", "");
		s = s.replaceAll("\\\\toprule", "");
		s = s.replaceAll(
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

	public String getHtmlTable() {
		StringBuilder s = new StringBuilder();
		s.append("<div class=\"table-responsive\" style=\"overflow-x: scroll;\">\n");
		s.append("<table class=\"text-center table table-striped table-hover table-condensed\">\n");
		s.append("<tbody>\n");

		s.append("<tr>\n");
		s.append("<th>\n");
		s.append("</th>\n");
		for (int c = 0; c < getColumnCount(); c++) {
			String label = getColumnLabel(c);
			s.append("<th class=\"text-center\">" + label + "</th>\n");
		}
		s.append("</tr>\n");

		for (int r = 0; r < getRowCount(); r++) {
			s.append("<tr>\n");
			String feature = getRowLabel(r);
			s.append("<th>" + feature + "</th>\n");
			for (int c = 0; c < getColumnCount(); c++) {
				String result = getString(r, c);
				s.append("<td>" + result + "</td>\n");
			}
			s.append("</tr>\n");
		}
		s.append("</tbody>\n");
		s.append("</table>\n");
		s.append("</div>\n");
		return s.toString();
	}

	public String getHtmlList() {
		StringBuilder s = new StringBuilder();
		for (int c = 0; c < getColumnCount(); c++) {
			String label = getColumnLabel(c);
			s.append("<h2>");
			s.append(label);
			s.append("</h2>\n");
			s.append("<p>These are the features of the <strong>" + label + "</strong> library:</p>");
			s.append("<ul>\n");
			for (int r = 0; r < getRowCount(); r++) {
				String feature = getRowLabel(r);
				String result = getString(r, c);
				if ("no".equals(result)) {
				} else if ("yes".equals(result)) {
					s.append("<li>" + label + " ");
					s.append(feature);
					s.append("</li>\n");
				} else if (!"no".equals(result)) {
					s.append("<li>" + label + " ");
					s.append(feature + ": ");
					s.append(getString(r, c));
					s.append("</li>\n");
				}
			}
			s.append("</ul>\n\n");
		}
		return s.toString();
	}

}
