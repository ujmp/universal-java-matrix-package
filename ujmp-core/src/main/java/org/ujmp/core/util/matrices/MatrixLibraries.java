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

	private static final int D2 = 10;

	private static final int D3 = 11;

	private static final int D4 = 12;

	private static final int D4PLUS = 13;

	private static final int INV = 14;

	private static final int SVD = 15;

	private static final int LU = 16;

	private static final int QR = 17;

	private static final int CHOL = 18;

	private static final int EVD = 19;

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

	private static final int PARALLELCOLT = 14;

	private static final int SST = 15;

	private static final int UJMP = 16;

	private static final int VECMATH = 17;

	public enum MatrixLibrariesFormat {
		DEFAULT, LATEX, HTML
	};

	private MatrixLibrariesFormat format = MatrixLibrariesFormat.DEFAULT;

	public MatrixLibraries() {
		this(MatrixLibrariesFormat.DEFAULT);
	}

	public MatrixLibraries(MatrixLibrariesFormat format) {
		super(20, 17);
		this.format = format;

		setAsString(beginTurn() + "Array4J" + endTurn(), LABELROW, ARRAY4J);
		setAsString(beginTurn() + "Colt" + endTurn(), LABELROW, COLT);
		setAsString(beginTurn() + "commons-math" + endTurn(), LABELROW, COMMONSMATH);
		setAsString(beginTurn() + "JAMA" + endTurn(), LABELROW, JAMA);
		setAsString(beginTurn() + "Jampack" + endTurn(), LABELROW, JAMPACK);
		setAsString(beginTurn() + "JMathArray" + endTurn(), LABELROW, JMATHARRAY);
		setAsString(beginTurn() + "JMatrices" + endTurn(), LABELROW, JMATRICES);
		setAsString(beginTurn() + "JSci" + endTurn(), LABELROW, JSCI);
		setAsString(beginTurn() + "JScience" + endTurn(), LABELROW, JSCIENCE);
		setAsString(beginTurn() + "Mantissa" + endTurn(), LABELROW, MANTISSA);
		setAsString(beginTurn() + "MTJ" + endTurn(), LABELROW, MTJ);
		setAsString(beginTurn() + "ojAlgo" + endTurn(), LABELROW, OJALGO);
		setAsString(beginTurn() + "Orbital" + endTurn(), LABELROW, ORBITAL);
		setAsString(beginTurn() + "Parallel Colt" + endTurn(), LABELROW, PARALLELCOLT);
		setAsString(beginTurn() + "SST" + endTurn(), LABELROW, SST);
		setAsString(beginTurn() + "UJMP" + endTurn(), LABELROW, UJMP);
		setAsString(beginTurn() + "vecmath" + endTurn(), LABELROW, VECMATH);

		setAsString("latest version", VERSION, LABELCOLUMN);
		setAsString("latest release", DATE, LABELCOLUMN);
		setAsString("licence", LICENCE, LABELCOLUMN);
		setAsString("Java 1.4", JAVA14, LABELCOLUMN);
		setAsString("Java 5", JAVA5, LABELCOLUMN);
		setAsString("Java 6", JAVA6, LABELCOLUMN);
		setAsString("dense matrices", DENSE, LABELCOLUMN);
		setAsString("sparse matrices", SPARSE, LABELCOLUMN);
		setAsString("complex numbers", COMPLEX, LABELCOLUMN);
		setAsString("2D matrices", D2, LABELCOLUMN);
		setAsString("3D matrices", D3, LABELCOLUMN);
		setAsString("4D matrices", D4, LABELCOLUMN);
		setAsString("matrices $>$4D", D4PLUS, LABELCOLUMN);
		setAsString("inverse", INV, LABELCOLUMN);
		setAsString("SVD", SVD, LABELCOLUMN);
		setAsString("LU", LU, LABELCOLUMN);
		setAsString("QR", QR, LABELCOLUMN);
		setAsString("Cholesky", CHOL, LABELCOLUMN);
		setAsString("EVD", EVD, LABELCOLUMN);

		setAsString(org.ujmp.core.UJMP.UJMPVERSION, VERSION, UJMP);
		setAsString("09/2009", DATE, UJMP);
		setAsString("LGPL", LICENCE, UJMP);
		setAsString(no(), JAVA14, UJMP);
		setAsString(yes(), JAVA5, UJMP);
		setAsString(yes(), JAVA6, UJMP);
		setAsString(yes(), DENSE, UJMP);
		setAsString(yes(), SPARSE, UJMP);
		setAsString(no(), COMPLEX, UJMP);
		setAsString(yes(), D2, UJMP);
		setAsString(yes(), D3, UJMP);
		setAsString(yes(), D4, UJMP);
		setAsString(yes(), D4PLUS, UJMP);
		setAsString(yes(), INV, UJMP);
		setAsString(yes(), SVD, UJMP);
		setAsString(yes(), LU, UJMP);
		setAsString(yes(), QR, UJMP);
		setAsString(yes(), EVD, UJMP);
		setAsString(yes(), CHOL, UJMP);

		setAsString("SVN", VERSION, ARRAY4J);
		setAsString("2008/05", DATE, ARRAY4J);
		setAsString("BSD", LICENCE, ARRAY4J);
		setAsString(unknown(), JAVA14, ARRAY4J);
		setAsString(unknown(), JAVA5, ARRAY4J);
		setAsString(yes(), JAVA6, ARRAY4J);
		setAsString(yes(), DENSE, ARRAY4J);
		setAsString(no(), SPARSE, ARRAY4J);
		setAsString(no(), COMPLEX, ARRAY4J);
		setAsString(yes(), D2, ARRAY4J);
		setAsString(no(), D3, ARRAY4J);
		setAsString(no(), D4, ARRAY4J);
		setAsString(no(), D4PLUS, ARRAY4J);
		setAsString(unknown(), INV, ARRAY4J);
		setAsString(unknown(), SVD, ARRAY4J);
		setAsString(unknown(), LU, ARRAY4J);
		setAsString(unknown(), QR, ARRAY4J);
		setAsString(unknown(), EVD, ARRAY4J);
		setAsString(unknown(), CHOL, ARRAY4J);

		setAsString("1.2.0", VERSION, COLT);
		setAsString("2004/09", DATE, COLT);
		setAsString("BSD", LICENCE, COLT);
		setAsString(unknown(), JAVA14, COLT);
		setAsString(unknown(), JAVA5, COLT);
		setAsString(yes(), JAVA6, COLT);
		setAsString(yes(), DENSE, COLT);
		setAsString(unknown(), SPARSE, COLT);
		setAsString(unknown(), COMPLEX, COLT);
		setAsString(yes(), D2, COLT);
		setAsString(unknown(), D3, COLT);
		setAsString(unknown(), D4, COLT);
		setAsString(unknown(), D4PLUS, COLT);
		setAsString(unknown(), INV, COLT);
		setAsString(unknown(), SVD, COLT);
		setAsString(unknown(), LU, COLT);
		setAsString(unknown(), QR, COLT);
		setAsString(unknown(), EVD, COLT);
		setAsString(unknown(), CHOL, COLT);

		setAsString("2.0", VERSION, COMMONSMATH);
		setAsString("2009/08", DATE, COMMONSMATH);
		setAsString("Apache", LICENCE, COMMONSMATH);
		setAsString(unknown(), JAVA14, COMMONSMATH);
		setAsString(unknown(), JAVA5, COMMONSMATH);
		setAsString(yes(), JAVA6, COMMONSMATH);
		setAsString(yes(), DENSE, COMMONSMATH);
		setAsString(unknown(), SPARSE, COMMONSMATH);
		setAsString(unknown(), COMPLEX, COMMONSMATH);
		setAsString(yes(), D2, COMMONSMATH);
		setAsString(unknown(), D3, COMMONSMATH);
		setAsString(unknown(), D4, COMMONSMATH);
		setAsString(unknown(), D4PLUS, COMMONSMATH);
		setAsString(unknown(), INV, COMMONSMATH);
		setAsString(unknown(), SVD, COMMONSMATH);
		setAsString(unknown(), LU, COMMONSMATH);
		setAsString(unknown(), QR, COMMONSMATH);
		setAsString(unknown(), EVD, COMMONSMATH);
		setAsString(unknown(), CHOL, COMMONSMATH);

		setAsString("1.0.2", VERSION, JAMA);
		setAsString("2005/07", DATE, JAMA);
		setAsString("PD", LICENCE, JAMA);
		setAsString(unknown(), JAVA14, JAMA);
		setAsString(unknown(), JAVA5, JAMA);
		setAsString(yes(), JAVA6, JAMA);
		setAsString(yes(), DENSE, JAMA);
		setAsString(unknown(), SPARSE, JAMA);
		setAsString(unknown(), COMPLEX, JAMA);
		setAsString(yes(), D2, JAMA);
		setAsString(unknown(), D3, JAMA);
		setAsString(unknown(), D4, JAMA);
		setAsString(unknown(), D4PLUS, JAMA);
		setAsString(unknown(), INV, JAMA);
		setAsString(unknown(), SVD, JAMA);
		setAsString(unknown(), LU, JAMA);
		setAsString(unknown(), QR, JAMA);
		setAsString(unknown(), EVD, JAMA);
		setAsString(unknown(), CHOL, JAMA);

		setAsString("unknown", VERSION, JAMPACK);
		setAsString("1999/02", DATE, JAMPACK);
		setAsString("unknown", LICENCE, JAMPACK);
		setAsString(unknown(), JAVA14, JAMPACK);
		setAsString(unknown(), JAVA5, JAMPACK);
		setAsString(yes(), JAVA6, JAMPACK);
		setAsString(yes(), DENSE, JAMPACK);
		setAsString(unknown(), SPARSE, JAMPACK);
		setAsString(unknown(), COMPLEX, JAMPACK);
		setAsString(yes(), D2, JAMPACK);
		setAsString(unknown(), D3, JAMPACK);
		setAsString(unknown(), D4, JAMPACK);
		setAsString(unknown(), D4PLUS, JAMPACK);
		setAsString(unknown(), INV, JAMPACK);
		setAsString(unknown(), SVD, JAMPACK);
		setAsString(unknown(), LU, JAMPACK);
		setAsString(unknown(), QR, JAMPACK);
		setAsString(unknown(), EVD, JAMPACK);
		setAsString(unknown(), CHOL, JAMPACK);

		setAsString("unknown", VERSION, JMATHARRAY);
		setAsString("2009/07", DATE, JMATHARRAY);
		setAsString("BSD", LICENCE, JMATHARRAY);
		setAsString(unknown(), JAVA14, JMATHARRAY);
		setAsString(unknown(), JAVA5, JMATHARRAY);
		setAsString(yes(), JAVA6, JMATHARRAY);
		setAsString(yes(), DENSE, JMATHARRAY);
		setAsString(unknown(), SPARSE, JMATHARRAY);
		setAsString(unknown(), COMPLEX, JMATHARRAY);
		setAsString(yes(), D2, JMATHARRAY);
		setAsString(unknown(), D3, JMATHARRAY);
		setAsString(unknown(), D4, JMATHARRAY);
		setAsString(unknown(), D4PLUS, JMATHARRAY);
		setAsString(unknown(), INV, JMATHARRAY);
		setAsString(unknown(), SVD, JMATHARRAY);
		setAsString(unknown(), LU, JMATHARRAY);
		setAsString(unknown(), QR, JMATHARRAY);
		setAsString(unknown(), EVD, JMATHARRAY);
		setAsString(unknown(), CHOL, JMATHARRAY);

		setAsString("0.6", VERSION, JMATRICES);
		setAsString("2004/05", DATE, JMATRICES);
		setAsString("LGPL", LICENCE, JMATRICES);
		setAsString(unknown(), JAVA14, JMATRICES);
		setAsString(unknown(), JAVA5, JMATRICES);
		setAsString(yes(), JAVA6, JMATRICES);
		setAsString(yes(), DENSE, JMATRICES);
		setAsString(unknown(), SPARSE, JMATRICES);
		setAsString(unknown(), COMPLEX, JMATRICES);
		setAsString(yes(), D2, JMATRICES);
		setAsString(unknown(), D3, JMATRICES);
		setAsString(unknown(), D4, JMATRICES);
		setAsString(unknown(), D4PLUS, JMATRICES);
		setAsString(unknown(), INV, JMATRICES);
		setAsString(unknown(), SVD, JMATRICES);
		setAsString(unknown(), LU, JMATRICES);
		setAsString(unknown(), QR, JMATRICES);
		setAsString(unknown(), EVD, JMATRICES);
		setAsString(unknown(), CHOL, JMATRICES);

		setAsString("1.1", VERSION, JSCI);
		setAsString("2009/07", DATE, JSCI);
		setAsString("LGPL", LICENCE, JSCI);
		setAsString(yes(), JAVA14, JSCI);
		setAsString(yes(), JAVA5, JSCI);
		setAsString(yes(), JAVA6, JSCI);
		setAsString(yes(), DENSE, JSCI);
		setAsString(unknown(), SPARSE, JSCI);
		setAsString(unknown(), COMPLEX, JSCI);
		setAsString(yes(), D2, JSCI);
		setAsString(unknown(), D3, JSCI);
		setAsString(unknown(), D4, JSCI);
		setAsString(unknown(), D4PLUS, JSCI);
		setAsString(unknown(), INV, JSCI);
		setAsString(unknown(), SVD, JSCI);
		setAsString(unknown(), LU, JSCI);
		setAsString(unknown(), QR, JSCI);
		setAsString(unknown(), EVD, JSCI);
		setAsString(unknown(), CHOL, JSCI);

		setAsString("4.3.1", VERSION, JSCIENCE);
		setAsString("2007/10", DATE, JSCIENCE);
		setAsString("BSD", LICENCE, JSCIENCE);
		setAsString(unknown(), JAVA14, JSCIENCE);
		setAsString(unknown(), JAVA5, JSCIENCE);
		setAsString(yes(), JAVA6, JSCIENCE);
		setAsString(yes(), DENSE, JSCIENCE);
		setAsString(unknown(), SPARSE, JSCIENCE);
		setAsString(unknown(), COMPLEX, JSCIENCE);
		setAsString(yes(), D2, JSCIENCE);
		setAsString(unknown(), D3, JSCIENCE);
		setAsString(unknown(), D4, JSCIENCE);
		setAsString(unknown(), D4PLUS, JSCIENCE);
		setAsString(unknown(), INV, JSCIENCE);
		setAsString(unknown(), SVD, JSCIENCE);
		setAsString(unknown(), LU, JSCIENCE);
		setAsString(unknown(), QR, JSCIENCE);
		setAsString(unknown(), EVD, JSCIENCE);
		setAsString(unknown(), CHOL, JSCIENCE);

		setAsString("7.2", VERSION, MANTISSA);
		setAsString("2007/10", DATE, MANTISSA);
		setAsString("BSD", LICENCE, MANTISSA);
		setAsString(unknown(), JAVA14, MANTISSA);
		setAsString(unknown(), JAVA5, MANTISSA);
		setAsString(yes(), JAVA6, MANTISSA);
		setAsString(yes(), DENSE, MANTISSA);
		setAsString(unknown(), SPARSE, MANTISSA);
		setAsString(unknown(), COMPLEX, MANTISSA);
		setAsString(yes(), D2, MANTISSA);
		setAsString(unknown(), D3, MANTISSA);
		setAsString(unknown(), D4, MANTISSA);
		setAsString(unknown(), D4PLUS, MANTISSA);
		setAsString(unknown(), INV, MANTISSA);
		setAsString(unknown(), SVD, MANTISSA);
		setAsString(unknown(), LU, MANTISSA);
		setAsString(unknown(), QR, MANTISSA);
		setAsString(unknown(), EVD, MANTISSA);
		setAsString(unknown(), CHOL, MANTISSA);

		setAsString("0.9.12", VERSION, MTJ);
		setAsString("2009/04", DATE, MTJ);
		setAsString("LGPL", LICENCE, MTJ);
		setAsString(unknown(), JAVA14, MTJ);
		setAsString(unknown(), JAVA5, MTJ);
		setAsString(yes(), JAVA6, MTJ);
		setAsString(yes(), DENSE, MTJ);
		setAsString(unknown(), SPARSE, MTJ);
		setAsString(unknown(), COMPLEX, MTJ);
		setAsString(yes(), D2, MTJ);
		setAsString(unknown(), D3, MTJ);
		setAsString(unknown(), D4, MTJ);
		setAsString(unknown(), D4PLUS, MTJ);
		setAsString(unknown(), INV, MTJ);
		setAsString(unknown(), SVD, MTJ);
		setAsString(unknown(), LU, MTJ);
		setAsString(unknown(), QR, MTJ);
		setAsString(unknown(), EVD, MTJ);
		setAsString(unknown(), CHOL, MTJ);

		setAsString("28.0", VERSION, OJALGO);
		setAsString("2009/08", DATE, OJALGO);
		setAsString("MIT", LICENCE, OJALGO);
		setAsString(unknown(), JAVA14, OJALGO);
		setAsString(unknown(), JAVA5, OJALGO);
		setAsString(yes(), JAVA6, OJALGO);
		setAsString(yes(), DENSE, OJALGO);
		setAsString(unknown(), SPARSE, OJALGO);
		setAsString(unknown(), COMPLEX, OJALGO);
		setAsString(yes(), D2, OJALGO);
		setAsString(unknown(), D3, OJALGO);
		setAsString(unknown(), D4, OJALGO);
		setAsString(unknown(), D4PLUS, OJALGO);
		setAsString(unknown(), INV, OJALGO);
		setAsString(unknown(), SVD, OJALGO);
		setAsString(unknown(), LU, OJALGO);
		setAsString(unknown(), QR, OJALGO);
		setAsString(unknown(), EVD, OJALGO);
		setAsString(unknown(), CHOL, OJALGO);

		setAsString("1.3.0", VERSION, ORBITAL);
		setAsString("2009/03", DATE, ORBITAL);
		setAsString("Custom", LICENCE, ORBITAL);
		setAsString(unknown(), JAVA14, ORBITAL);
		setAsString(unknown(), JAVA5, ORBITAL);
		setAsString(yes(), JAVA6, ORBITAL);
		setAsString(yes(), DENSE, ORBITAL);
		setAsString(unknown(), SPARSE, ORBITAL);
		setAsString(unknown(), COMPLEX, ORBITAL);
		setAsString(yes(), D2, ORBITAL);
		setAsString(unknown(), D3, ORBITAL);
		setAsString(unknown(), D4, ORBITAL);
		setAsString(unknown(), D4PLUS, ORBITAL);
		setAsString(unknown(), INV, ORBITAL);
		setAsString(unknown(), SVD, ORBITAL);
		setAsString(unknown(), LU, ORBITAL);
		setAsString(unknown(), QR, ORBITAL);
		setAsString(unknown(), EVD, ORBITAL);
		setAsString(unknown(), CHOL, ORBITAL);

		setAsString("0.9.1", VERSION, PARALLELCOLT);
		setAsString("2009/09", DATE, PARALLELCOLT);
		setAsString("BSD", LICENCE, PARALLELCOLT);
		setAsString(unknown(), JAVA14, PARALLELCOLT);
		setAsString(unknown(), JAVA5, PARALLELCOLT);
		setAsString(yes(), JAVA6, PARALLELCOLT);
		setAsString(yes(), DENSE, PARALLELCOLT);
		setAsString(unknown(), SPARSE, PARALLELCOLT);
		setAsString(unknown(), COMPLEX, PARALLELCOLT);
		setAsString(yes(), D2, PARALLELCOLT);
		setAsString(unknown(), D3, PARALLELCOLT);
		setAsString(unknown(), D4, PARALLELCOLT);
		setAsString(unknown(), D4PLUS, PARALLELCOLT);
		setAsString(unknown(), INV, PARALLELCOLT);
		setAsString(unknown(), SVD, PARALLELCOLT);
		setAsString(unknown(), LU, PARALLELCOLT);
		setAsString(unknown(), QR, PARALLELCOLT);
		setAsString(unknown(), EVD, PARALLELCOLT);
		setAsString(unknown(), CHOL, PARALLELCOLT);

		setAsString("1.10", VERSION, SST);
		setAsString("2009/09", DATE, SST);
		setAsString("LGPL", LICENCE, SST);
		setAsString(unknown(), JAVA14, SST);
		setAsString(unknown(), JAVA5, SST);
		setAsString(yes(), JAVA6, SST);
		setAsString(yes(), DENSE, SST);
		setAsString(unknown(), SPARSE, SST);
		setAsString(unknown(), COMPLEX, SST);
		setAsString(yes(), D2, SST);
		setAsString(unknown(), D3, SST);
		setAsString(unknown(), D4, SST);
		setAsString(unknown(), D4PLUS, SST);
		setAsString(unknown(), INV, SST);
		setAsString(unknown(), SVD, SST);
		setAsString(unknown(), LU, SST);
		setAsString(unknown(), QR, SST);
		setAsString(unknown(), EVD, SST);
		setAsString(unknown(), CHOL, SST);

		setAsString("1.5.1", VERSION, VECMATH);
		setAsString("2007/06", DATE, VECMATH);
		setAsString("other", LICENCE, VECMATH);
		setAsString(unknown(), JAVA14, VECMATH);
		setAsString(unknown(), JAVA5, VECMATH);
		setAsString(yes(), JAVA6, VECMATH);
		setAsString(yes(), DENSE, VECMATH);
		setAsString(unknown(), SPARSE, VECMATH);
		setAsString(unknown(), COMPLEX, VECMATH);
		setAsString(yes(), D2, VECMATH);
		setAsString(no(), D3, VECMATH);
		setAsString(no(), D4, VECMATH);
		setAsString(no(), D4PLUS, VECMATH);
		setAsString(yes(), INV, VECMATH);
		setAsString(yes(), SVD, VECMATH);
		setAsString(yes(), LU, VECMATH);
		setAsString(no(), QR, VECMATH);
		setAsString(no(), EVD, VECMATH);
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

	private String no() {
		switch (format) {
		case LATEX:
			return "\\bf{$+$}";
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

	private String beginTurn() {
		switch (format) {
		case LATEX:
			return "\\bf{$+$}";
		default:
			return "";
		}
	}

	private String endTurn() {
		switch (format) {
		case LATEX:
			return "\\end{turn}";
		default:
			return "";
		}
	}

	public static void main(String[] args) {
		System.out.println(new MatrixLibraries());
	}

}
