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

	public static final int LABELCOLUMN = 0;
	public static final int VERSION = 1;
	public static final int DATE = 2;
	public static final int LICENSE = 3;
	public static final int JAVA14 = 4;
	public static final int JAVA5 = 5;
	public static final int JAVA6 = 6;
	public static final int JAVA7 = 7;
	public static final int JAVA8 = 8;
	public static final int DENSESA = 9;
	public static final int DENSEAA = 10;
	public static final int DENSEBLOCK = 11;
	public static final int SPARSEDOK = 12;
	public static final int SPARSELIL = 13;
	public static final int SPARSEYALE = 14;
	public static final int SPARSECRS = 15;
	public static final int SPARSECDS = 16;
	public static final int DOUBLE = 17;
	public static final int FLOAT = 18;
	public static final int BIGDECIMAL = 19;
	public static final int STRINGS = 20;
	public static final int OBJECTS = 21;
	public static final int GENERICS = 22;
	public static final int COMPLEX = 23;
	public static final int D2 = 24;
	public static final int D3 = 25;
	public static final int D3PLUS = 26;
	public static final int MULTITHREADED = 27;
	public static final int INPLACE = 28;
	public static final int TRANSPOSE = 29;
	public static final int SCALE = 30;
	public static final int PLUSMINUS = 31;
	public static final int INV = 32;
	public static final int SOLVE = 33;
	public static final int LU = 34;
	public static final int QR = 35;
	public static final int SVD = 36;
	public static final int CHOL = 37;
	public static final int EIG = 38;
	public static final int ALLITERATOR = 39;
	public static final int NONZEROITERATOR = 40;
	public static final int CSVIO = 41;
	public static final int JDBCIO = 42;
	public static final int SERIALIZABLE = 43;
	public static final int VALUESPERDIMENSION = 44;
	public static final int MAXIMUMSIZE = 45;

	public static final int LABELROW = 0;
	public static final int COLT = 1;
	public static final int COMMONSMATH = 2;
	public static final int EJML = 3;
	public static final int JAMA = 4;
	public static final int JAMPACK = 5;
	public static final int JBLAS = 6;
	public static final int JLINALG = 7;
	public static final int JMATHARRAY = 8;
	public static final int JMATRICES = 9;
	public static final int JSCI = 10;
	public static final int JSCIENCE = 11;
	public static final int LA4J = 12;
	public static final int MANTISSA = 13;
	public static final int MTJ = 14;
	public static final int OJALGO = 15;
	public static final int PARALLELCOLT = 16;
	public static final int SST = 17;
	public static final int UJMP = 18;
	public static final int VECMATH = 19;

	public static final String NONSINGULARLETTER = "n";
	public static final String NONSINGULARTEXT = "non-singular matrices only";
	public static final String ERRORTEXT = "error in implementation";

	private List<String> footnotes = new ArrayList<String>();

	public enum MatrixLibrariesFormat {
		DEFAULT, LATEX, HTMLTABLE, HTMLLIST
	};

	private MatrixLibrariesFormat format = MatrixLibrariesFormat.DEFAULT;

	public MatrixLibraries() {
		this(MatrixLibrariesFormat.DEFAULT);
	}

	public MatrixLibraries(MatrixLibrariesFormat format) {
		super(46, 20);
		int footnote = 1;
		this.format = format;

		setString(link(turn("Colt"), "http://acs.lbl.gov/software/colt/"), LABELROW, COLT);
		setString(link(turn("Commons Math"), "http://commons.apache.org/math/"), LABELROW,
				COMMONSMATH);
		setString(link(turn("EJML"), "https://code.google.com/p/efficient-java-matrix-library/"),
				LABELROW, EJML);
		setString(link(turn("JAMA"), "http://math.nist.gov/javanumerics/jama/"), LABELROW, JAMA);
		setString(
				link(turn("Jampack"), "ftp://math.nist.gov/pub/Jampack/Jampack/AboutJampack.html"),
				LABELROW, JAMPACK);
		setString(link(turn("jblas"), "http://mikiobraun.github.io/jblas/"), LABELROW, JBLAS);
		setString(link(turn("JLinAlg"), "http://jlinalg.sourceforge.net/"), LABELROW, JLINALG);
		setString(link(turn("JMathArray"), "https://code.google.com/p/jmatharray/"), LABELROW,
				JMATHARRAY);
		setString(link(turn("JMatrices"), "http://jmatrices.sourceforge.net/"), LABELROW, JMATRICES);
		setString(link(turn("JSci"), "http://jsci.sourceforge.net/"), LABELROW, JSCI);
		setString(link(turn("JScience"), "http://jscience.org/"), LABELROW, JSCIENCE);
		setString(link(turn("la4j"), "http://la4j.org/"), LABELROW, LA4J);
		setString(link(turn("Mantissa"), "http://www.spaceroots.org/software/mantissa/index.html"),
				LABELROW, MANTISSA);
		setString(link(turn("MTJ"), "https://github.com/fommil/matrix-toolkits-java/"), LABELROW,
				MTJ);
		setString(link(turn("ojAlgo"), "http://ojalgo.org/"), LABELROW, OJALGO);
		setString(
				link(turn("Parallel Colt"),
						"https://sites.google.com/site/piotrwendykier/software/parallelcolt"),
				LABELROW, PARALLELCOLT);
		setString(link(turn("SST"), "http://freecode.com/projects/shared"), LABELROW, SST);
		setString(link(turn("UJMP"), "http://ujmp.org/"), LABELROW, UJMP);
		setString(link(turn("vecmath"), "https://java.net/projects/vecmath"), LABELROW, VECMATH);

		setString("Current Version", VERSION, LABELCOLUMN);
		setString("Latest Release", DATE, LABELCOLUMN);
		setString("License", LICENSE, LABELCOLUMN);
		setString("Supports Java 1.4", JAVA14, LABELCOLUMN);
		setString("Supports Java 5", JAVA5, LABELCOLUMN);
		setString("Supports Java 6", JAVA6, LABELCOLUMN);
		setString("Supports Java 7", JAVA7, LABELCOLUMN);
		setString("Supports Java 8", JAVA8, LABELCOLUMN);
		setString("Multi-Threaded Operations", MULTITHREADED, LABELCOLUMN);
		setString("In-Place Operations", INPLACE, LABELCOLUMN);
		setString("Dense Data in Single Array", DENSESA, LABELCOLUMN);
		setString("Dense Data in 2D Array", DENSEAA, LABELCOLUMN);
		setString("Dense Data in Block Storage", DENSEBLOCK, LABELCOLUMN);
		setString(
				"Sparse Data in DOK" + footnote("" + footnote++, "dictionary of key-value pairs"),
				SPARSEDOK, LABELCOLUMN);
		setString("Sparse Data in LIL" + footnote("" + footnote++, "list of lists"), SPARSELIL,
				LABELCOLUMN);
		setString("Sparse Data in CSR" + footnote("" + footnote++, "compressed sparse row/column"),
				SPARSECRS, LABELCOLUMN);
		setString("Sparse Data in CDS" + footnote("" + footnote++, "compressed sparse diagonal"),
				SPARSECDS, LABELCOLUMN);
		setString("Sparse Data in Yale Format", SPARSEYALE, LABELCOLUMN);
		setString("Can Store Complex Numbers", COMPLEX, LABELCOLUMN);
		setString("Can Store Double Values", DOUBLE, LABELCOLUMN);
		setString("Can Store Float Values", FLOAT, LABELCOLUMN);
		setString("Can Store BigDecimal Values", BIGDECIMAL, LABELCOLUMN);
		setString("Can Store Strings", STRINGS, LABELCOLUMN);
		setString("Can Store Objects", OBJECTS, LABELCOLUMN);
		setString("Can Store Generic Objects", GENERICS, LABELCOLUMN);
		setString("Supports 2D Matrix", D2, LABELCOLUMN);
		setString("Supports 3D Matrix", D3, LABELCOLUMN);
		setString("Supports >3D Matrix", D3PLUS, LABELCOLUMN);
		setString("Supports Transpose", TRANSPOSE, LABELCOLUMN);
		setString("Supports Multiply/Divide", SCALE, LABELCOLUMN);
		setString("Supports Plus/Minus", PLUSMINUS, LABELCOLUMN);
		setString("Supports Inverse", INV, LABELCOLUMN);
		setString("Supports Solve", SOLVE, LABELCOLUMN);
		setString("Supports SVD", SVD, LABELCOLUMN);
		setString("Supports LU Decomposition", LU, LABELCOLUMN);
		setString("Supports QR Decomposition", QR, LABELCOLUMN);
		setString("Supports Cholesky Decomposition", CHOL, LABELCOLUMN);
		setString("Supports Eigen Decomposition", EIG, LABELCOLUMN);
		setString("Iterator for all Entries", ALLITERATOR, LABELCOLUMN);
		setString("Iterator for non-zero Entries", NONZEROITERATOR, LABELCOLUMN);
		setString("CSV Import/Export", CSVIO, LABELCOLUMN);
		setString("JDBC Import/Export", JDBCIO, LABELCOLUMN);
		setString("Serializable", SERIALIZABLE, LABELCOLUMN);
		setString("Values Per Dimension", VALUESPERDIMENSION, LABELCOLUMN);
		setString("Maximum Size", MAXIMUMSIZE, LABELCOLUMN);

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
		setString(no(), SPARSECRS, UJMP);
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
		setString(yes(), ALLITERATOR, UJMP);
		setString(yes(), NONZEROITERATOR, UJMP);
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
		setString(no(), SPARSECRS, COLT);
		setString(no(), SPARSECDS, COLT);
		setString(yes(), DOUBLE, COLT);
		setString(no(), FLOAT, COLT);
		setString(no(), BIGDECIMAL, COLT);
		setString(unknown(), STRINGS, COLT);
		setString(unknown(), OBJECTS, COLT);
		setString(unknown(), GENERICS, COLT);
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
		setString(unknown(), ALLITERATOR, UJMP);
		setString(unknown(), NONZEROITERATOR, UJMP);
		setString(no(), CSVIO, UJMP);
		setString(no(), JDBCIO, UJMP);
		setString(yes(), SERIALIZABLE, UJMP);
		setString(bit64(), VALUESPERDIMENSION, UJMP);
		setString(hdd(), MAXIMUMSIZE, UJMP);

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
		setString(no(), SPARSECRS, COMMONSMATH);
		setString(no(), SPARSECDS, COMMONSMATH);
		setString(yes(), DOUBLE, COMMONSMATH);
		setString(no(), FLOAT, COMMONSMATH);
		setString(yes(), BIGDECIMAL, COMMONSMATH);
		setString(unknown(), STRINGS, COMMONSMATH);
		setString(unknown(), OBJECTS, COMMONSMATH);
		setString(unknown(), GENERICS, COMMONSMATH);
		setString(yes(), COMPLEX, COMMONSMATH);
		setString(yes(), D2, COMMONSMATH);
		setString(no(), D3, COMMONSMATH);
		setString(no(), D3PLUS, COMMONSMATH);
		setString(yes(), INV, COMMONSMATH);
		setString(squareTall(), SOLVE, COMMONSMATH);
		setString(all(), SVD, COMMONSMATH);
		setString(square() + footnote(NONSINGULARLETTER, NONSINGULARTEXT), LU, COMMONSMATH);
		setString(all(), QR, COMMONSMATH);
		setString(yes() + footnote("s", "symmetric matrices only"), EIG, COMMONSMATH);
		setString(yes(), CHOL, COMMONSMATH);

		setString(small("0.24"), VERSION, EJML);
		setString(small("2013"), DATE, EJML);
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
		setString(no(), SPARSECRS, EJML);
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
		setString(yes(), CHOL, EJML);

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
		setString(no(), SPARSECRS, JAMA);
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

		setString("", VERSION, JAMPACK);
		setString(small("1999"), DATE, JAMPACK);
		setString("", LICENSE, JAMPACK);
		setString(yes(), JAVA14, JAMPACK);
		setString(yes(), JAVA5, JAMPACK);
		setString(yes(), JAVA6, JAMPACK);
		setString(yes(), JAVA7, JAMPACK);
		setString(yes(), JAVA8, JAMPACK);
		setString(no(), MULTITHREADED, JAMPACK);
		setString(no(), INPLACE, JAMPACK);
		setString(yes(), DENSEAA, JAMPACK);
		setString(no(), DENSESA, JAMPACK);
		setString(no(), DENSEBLOCK, JAMPACK);
		setString(no(), SPARSEDOK, JAMPACK);
		setString(no(), SPARSEYALE, JAMPACK);
		setString(no(), SPARSELIL, JAMPACK);
		setString(no(), SPARSECRS, JAMPACK);
		setString(no(), SPARSECDS, JAMPACK);
		setString(yes(), COMPLEX, JAMPACK);
		setString(yes(), DOUBLE, JAMPACK);
		setString(no(), FLOAT, JAMPACK);
		setString(no(), BIGDECIMAL, JAMPACK);
		setString(yes(), D2, JAMPACK);
		setString(no(), D3, JAMPACK);
		setString(no(), D3PLUS, JAMPACK);
		setString(yes(), TRANSPOSE, JAMPACK);
		setString(yes(), PLUSMINUS, JAMPACK);
		setString(yes(), SCALE, JAMPACK);
		setString(yes(), INV, JAMPACK);
		setString(square(), SOLVE, JAMPACK);
		setString(square(), SVD, JAMPACK);
		setString(all(), LU, JAMPACK);
		setString(all(), QR, JAMPACK);
		setString(yes(), EIG, JAMPACK);
		setString(yes() + footnote("e", ERRORTEXT), CHOL, JAMPACK);

		setString(small("1.0.3"), VERSION, JBLAS);
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
		setString(no(), SPARSECRS, JBLAS);
		setString(no(), SPARSECDS, JBLAS);
		setString(yes(), COMPLEX, JBLAS);
		setString(yes(), DOUBLE, JBLAS);
		setString(yes(), FLOAT, JBLAS);
		setString(no(), BIGDECIMAL, JBLAS);
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
		setString(yes() + footnote("s", "symmetric matrices only"), EIG, JBLAS);
		setString(yes(), CHOL, JBLAS);

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
		setString(no(), SPARSECRS, JLINALG);
		setString(no(), SPARSECDS, JLINALG);
		setString(yes(), COMPLEX, JLINALG);
		setString(yes(), DOUBLE, JLINALG);
		setString(no(), FLOAT, JLINALG);
		setString(yes(), BIGDECIMAL, JLINALG);
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
		setString(no(), SPARSECRS, JMATHARRAY);
		setString(no(), SPARSECDS, JMATHARRAY);
		setString(no(), COMPLEX, JMATHARRAY);
		setString(yes(), DOUBLE, JMATHARRAY);
		setString(no(), FLOAT, JMATHARRAY);
		setString(no(), BIGDECIMAL, JMATHARRAY);
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
		setString(no(), SPARSECRS, JMATRICES);
		setString(no(), SPARSECDS, JMATRICES);
		setString(yes(), COMPLEX, JMATRICES);
		setString(yes(), DOUBLE, JMATRICES);
		setString(no(), FLOAT, JMATRICES);
		setString(yes(), BIGDECIMAL, JMATRICES);
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
		setString(no(), SPARSECRS, JSCI);
		setString(no(), SPARSECDS, JSCI);
		setString(yes(), COMPLEX, JSCI);
		setString(yes(), DOUBLE, JSCI);
		setString(no(), FLOAT, JSCI);
		setString(no(), BIGDECIMAL, JSCI);
		setString(yes(), D2, JSCI);
		setString(no(), D3, JSCI);
		setString(no(), D3PLUS, JSCI);
		setString(yes(), TRANSPOSE, JSCI);
		setString(yes(), PLUSMINUS, JSCI);
		setString(yes(), SCALE, JSCI);
		setString(no() + footnote("v", "only for $A \\cdot X = \\mbox{vector}$"), SOLVE, JSCI);
		setString(yes(), INV, JSCI);
		setString(square(), SVD, JSCI);
		setString(square() + footnote(NONSINGULARLETTER, NONSINGULARTEXT), LU, JSCI);
		setString(square(), QR, JSCI);
		setString(
				yes() + footnote("s", "symmetric matrices only")
						+ footnote("a", "results not directly accessible"), EIG, JSCI);
		setString(yes(), CHOL, JSCI);

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
		setString(no(), SPARSECRS, JSCIENCE);
		setString(yes(), SPARSEDOK, JSCIENCE);
		setString(yes(), COMPLEX, JSCIENCE);
		setString(yes(), DOUBLE, JSCIENCE);
		setString(no(), FLOAT, JSCIENCE);
		setString(no(), BIGDECIMAL, JSCIENCE);
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

		setString(small("0.4.9"), VERSION, LA4J);
		setString(small("2014"), DATE, LA4J);
		setString(small("Apache"), LICENSE, LA4J);
		setString(no(), JAVA14, LA4J);
		setString(yes(), JAVA5, LA4J);
		setString(yes(), JAVA6, LA4J);
		setString(yes(), JAVA7, LA4J);
		setString(yes(), JAVA8, LA4J);
		setString(no(), MULTITHREADED, LA4J);
		setString(unknown(), INPLACE, LA4J);
		setString(yes(), TRANSPOSE, LA4J);
		setString(yes(), PLUSMINUS, LA4J);
		setString(yes(), SCALE, LA4J);
		setString(unknown(), DENSESA, LA4J);
		setString(unknown(), DENSEAA, LA4J);
		setString(unknown(), DENSEBLOCK, LA4J);
		setString(unknown(), SPARSEDOK, LA4J);
		setString(unknown(), SPARSEYALE, LA4J);
		setString(unknown(), SPARSELIL, LA4J);
		setString(unknown(), SPARSECRS, LA4J);
		setString(unknown(), SPARSECDS, LA4J);
		setString(no(), COMPLEX, LA4J);
		setString(yes(), DOUBLE, LA4J);
		setString(no(), FLOAT, LA4J);
		setString(no(), BIGDECIMAL, LA4J);
		setString(yes(), D2, LA4J);
		setString(no(), D3, LA4J);
		setString(no(), D3PLUS, LA4J);
		setString(unknown(), INV, LA4J);
		setString(unknown(), SOLVE, LA4J);
		setString(unknown(), SVD, LA4J);
		setString(unknown(), LU, LA4J);
		setString(unknown(), QR, LA4J);
		setString(unknown(), EIG, LA4J);
		setString(unknown(), CHOL, LA4J);

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
		setString(no(), SPARSECRS, MANTISSA);
		setString(no(), SPARSECDS, MANTISSA);
		setString(no(), COMPLEX, MANTISSA);
		setString(yes(), DOUBLE, MANTISSA);
		setString(no(), FLOAT, MANTISSA);
		setString(no(), BIGDECIMAL, MANTISSA);
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
		setString(yes(), SPARSECRS, MTJ);
		setString(yes(), SPARSECDS, MTJ);
		setString(yes(), SPARSELIL, MTJ);
		setString(no(), SPARSEDOK, MTJ);
		setString(no(), SPARSEYALE, MTJ);
		setString(no(), COMPLEX, MTJ);
		setString(yes(), DOUBLE, MTJ);
		setString(no(), FLOAT, MTJ);
		setString(no(), BIGDECIMAL, MTJ);
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
		setString(yes() + footnote("s", "symmetric matrices only"), EIG, MTJ);
		setString(yes() + footnote("e", ERRORTEXT), CHOL, MTJ);

		setString(small("v35"), VERSION, OJALGO);
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
		setString(no(), SPARSECRS, OJALGO);
		setString(no(), SPARSEYALE, OJALGO);
		setString(no(), SPARSELIL, OJALGO);
		setString(no(), SPARSEDOK, OJALGO);
		setString(yes(), COMPLEX, OJALGO);
		setString(yes(), DOUBLE, OJALGO);
		setString(yes(), FLOAT, OJALGO);
		setString(yes(), BIGDECIMAL, OJALGO);
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

		setString(small("0.9.4"), VERSION, PARALLELCOLT);
		setString(small("2010"), DATE, PARALLELCOLT);
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
		setString(yes(), SPARSECRS, PARALLELCOLT);
		setString(yes(), SPARSELIL, PARALLELCOLT);
		setString(yes(), SPARSEDOK, PARALLELCOLT);
		setString(no(), SPARSEYALE, PARALLELCOLT);
		setString(no(), SPARSECDS, PARALLELCOLT);
		setString(yes(), COMPLEX, PARALLELCOLT);
		setString(yes(), DOUBLE, PARALLELCOLT);
		setString(yes(), FLOAT, PARALLELCOLT);
		setString(no(), BIGDECIMAL, PARALLELCOLT);
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
		setString(no(), SPARSECRS, SST);
		setString(yes(), COMPLEX, SST);
		setString(yes(), DOUBLE, SST);
		setString(no(), FLOAT, SST);
		setString(no(), BIGDECIMAL, SST);
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

		setString(small("1.5.2"), VERSION, VECMATH);
		setString("", DATE, VECMATH);
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
		setString(no(), SPARSECRS, VECMATH);
		setString(no(), SPARSEYALE, VECMATH);
		setString(no(), COMPLEX, VECMATH);
		setString(yes(), DOUBLE, VECMATH);
		setString(no(), FLOAT, VECMATH);
		setString(no(), BIGDECIMAL, VECMATH);
		setString(yes(), D2, VECMATH);
		setString(no(), D3, VECMATH);
		setString(no(), D3PLUS, VECMATH);
		setString(yes(), INV, VECMATH);
		setString(no(), SOLVE, VECMATH);
		setString(circle() + footnote("e", ERRORTEXT), SVD, VECMATH);
		setString(square() + footnote(NONSINGULARLETTER, NONSINGULARTEXT), LU, VECMATH);
		setString(no(), QR, VECMATH);
		setString(no(), EIG, VECMATH);
		setString(no(), CHOL, VECMATH);

	}

	public String yes() {
		switch (format) {
		case LATEX:
			return "\\bf{$+$}";
		case HTMLTABLE:
			return "<span class=\"text-success glyphicon glyphicon-ok-sign\"></span>";
		case HTMLLIST:
			return "<span class=\"text-success glyphicon glyphicon-ok-sign\">yes</span>";
		default:
			return "yes";
		}
	}

	public String hdd() {
		switch (format) {
		case LATEX:
			return "~4000GB (Disk)";
		case HTMLTABLE:
			return "~4000GB (Disk)";
		case HTMLLIST:
			return "~4000GB (Disk)";
		default:
			return "~4000GB (Disk)";
		}
	}

	public String ram() {
		switch (format) {
		case LATEX:
			return "~32GB (RAM)";
		case HTMLTABLE:
			return "~32GB (RAM)";
		case HTMLLIST:
			return "~32GB (RAM)";
		default:
			return "~32GB (RAM)";
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

	public String no() {
		switch (format) {
		case LATEX:
			return "\\bf{$-$}";
		case HTMLTABLE:
			return "<span class=\"text-danger glyphicon glyphicon-minus-sign\"></span>";
		case HTMLLIST:
			return "<span class=\"text-danger glyphicon glyphicon-minus-sign\">no</span>";
		default:
			return "no";
		}
	}

	public String unknown() {
		switch (format) {
		default:
			return "?";
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

	public String turn(String text) {
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
		String s = new MatrixLibraries(MatrixLibrariesFormat.HTMLLIST).getHtmlList();
		System.out.println(s);
	}

	public static void printLatex(String[] args) throws Exception {
		MatrixLibraries ml = new MatrixLibraries(MatrixLibrariesFormat.LATEX);
		Matrix m = ml.deleteRows(Ret.NEW, ml.getRowCount() - 1);
		String s = m.export().toStringFormatted().asLatex();
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

	public String getHtmlList() {
		StringBuilder s = new StringBuilder();
		for (int c = 1; c < getColumnCount(); c++) {
			s.append("<h2>");
			s.append(getString(0, c));
			s.append("</h2>\n");
			s.append("<ul>\n");
			for (int r = 1; r < getRowCount(); r++) {
				s.append("<li>");
				s.append(getString(r, 0));
				s.append(": ");
				s.append(getString(r, c));
				s.append("</li>\n");
			}
			s.append("</ul>\n\n");
		}
		return s.toString();
	}

	public long getColumnForPackage(String packageName) {
		if ("org.ujmp.core".equals(packageName)) {
			return UJMP;
		} else if ("org.ujmp.colt".equals(packageName)) {
			return COLT;
		} else if ("org.ujmp.commonsmath".equals(packageName)) {
			return COMMONSMATH;
		} else if ("org.ujmp.ejml".equals(packageName)) {
			return EJML;
		} else if ("org.ujmp.jama".equals(packageName)) {
			return JAMA;
		} else if ("org.ujmp.jblas".equals(packageName)) {
			return JBLAS;
		} else if ("org.ujmp.jlinalg".equals(packageName)) {
			return JLINALG;
		} else if ("org.ujmp.jmatharray".equals(packageName)) {
			return JMATHARRAY;
		} else if ("org.ujmp.jmatrices".equals(packageName)) {
			return JMATRICES;
		} else if ("org.ujmp.jsci".equals(packageName)) {
			return JSCI;
		} else if ("org.ujmp.jscience".equals(packageName)) {
			return JSCIENCE;
		} else if ("org.ujmp.la4j".equals(packageName)) {
			return LA4J;
		} else if ("org.ujmp.mantissa".equals(packageName)) {
			return MANTISSA;
		} else if ("org.ujmp.mtj".equals(packageName)) {
			return MTJ;
		} else if ("org.ujmp.ojalgo".equals(packageName)) {
			return OJALGO;
		} else if ("org.ujmp.parallelcolt".equals(packageName)) {
			return PARALLELCOLT;
		} else if ("org.ujmp.sst".equals(packageName)) {
			return SST;
		} else if ("org.ujmp.vecmath".equals(packageName)) {
			return VECMATH;
		} else {
			throw new RuntimeException("unknown package: " + packageName);
		}
	}
}
