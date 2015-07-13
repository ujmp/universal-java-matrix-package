/*
 * Copyright (C) 2008-2015 by Holger Arndt
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

package org.ujmp.core.text;

import org.ujmp.core.Matrix;

public abstract class German {

	public static final double AVERAGE_WORD_LENGTH = 6;

	public static final char[] ALPHABET = new char[] { ' ', '-', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
			'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
			'y', 'z', 'ä', 'ö', 'ß', 'ü' };

	public static final char[] ALLOWEDCHARACTERS = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g',
			'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
			'y', 'z', 'ä', 'ö', 'ß', 'ü', ' ', '\'', '"', ',', '.', ':', ';', '-', '!', '?', '1',
			'2', '3', '4', '5', '6', '7', '8', '9', '0', '(', ')', '´', '`' };

	public static final char[] PUNCTUATIONCHARACTERS = new char[] { '\'', '"', ',', '.', ':', ';',
			'!', '?', '(', ')', '´', '`' };

	public static final Matrix CHARFREQUENCIES = Matrix.Factory.zeros(256, 1);
	public static final Matrix CHARBIGRAMFREQUENCIES = Matrix.Factory.zeros(65536, 1);

	static {
		CHARFREQUENCIES.setAsDouble(0.06516, 'a', 0);
		CHARFREQUENCIES.setAsDouble(0.01886, 'b', 0);
		CHARFREQUENCIES.setAsDouble(0.02732, 'c', 0);
		CHARFREQUENCIES.setAsDouble(0.05076, 'd', 0);
		CHARFREQUENCIES.setAsDouble(0.17396, 'e', 0);
		CHARFREQUENCIES.setAsDouble(0.01656, 'f', 0);
		CHARFREQUENCIES.setAsDouble(0.03009, 'g', 0);
		CHARFREQUENCIES.setAsDouble(0.04757, 'h', 0);
		CHARFREQUENCIES.setAsDouble(0.07550, 'i', 0);
		CHARFREQUENCIES.setAsDouble(0.00268, 'j', 0);
		CHARFREQUENCIES.setAsDouble(0.01417, 'k', 0);
		CHARFREQUENCIES.setAsDouble(0.03437, 'l', 0);
		CHARFREQUENCIES.setAsDouble(0.02534, 'm', 0);
		CHARFREQUENCIES.setAsDouble(0.09776, 'n', 0);
		CHARFREQUENCIES.setAsDouble(0.02594, 'o', 0);
		CHARFREQUENCIES.setAsDouble(0.00670, 'p', 0);
		CHARFREQUENCIES.setAsDouble(0.00018, 'q', 0);
		CHARFREQUENCIES.setAsDouble(0.07003, 'r', 0);
		CHARFREQUENCIES.setAsDouble(0.07273, 's', 0);
		CHARFREQUENCIES.setAsDouble(0.06154, 't', 0);
		CHARFREQUENCIES.setAsDouble(0.04346, 'u', 0);
		CHARFREQUENCIES.setAsDouble(0.00846, 'v', 0);
		CHARFREQUENCIES.setAsDouble(0.01921, 'w', 0);
		CHARFREQUENCIES.setAsDouble(0.00034, 'x', 0);
		CHARFREQUENCIES.setAsDouble(0.00039, 'y', 0);
		CHARFREQUENCIES.setAsDouble(0.01134, 'z', 0);
		CHARFREQUENCIES.setAsDouble(0.00447, 'ä', 0);
		CHARFREQUENCIES.setAsDouble(0.00573, 'ö', 0);
		CHARFREQUENCIES.setAsDouble(0.00307, 'ß', 0);
		CHARFREQUENCIES.setAsDouble(0.00995, 'ü', 0);

		CHARBIGRAMFREQUENCIES.setAsDouble(5.969088E-4, ' ' * 256 + '-', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.011399873, ' ' * 256 + 'a', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.00706786, ' ' * 256 + 'b', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(9.2274614E-4, ' ' * 256 + 'c', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.02169465, ' ' * 256 + 'd', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.010382136, ' ' * 256 + 'e', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0048683095, ' ' * 256 + 'f', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0061566043, ' ' * 256 + 'g', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0046237917, ' ' * 256 + 'h', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.007806343, ' ' * 256 + 'i', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0017850088, ' ' * 256 + 'j', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0045947456, ' ' * 256 + 'k', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0027481995, ' ' * 256 + 'l', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.006881352, ' ' * 256 + 'm', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0048250062, ' ' * 256 + 'n', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0013975427, ' ' * 256 + 'o', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0030789664, ' ' * 256 + 'p', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.5423694E-4, ' ' * 256 + 'q', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0025096284, ' ' * 256 + 'r', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.013021005, ' ' * 256 + 's', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0024831572, ' ' * 256 + 't', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0057839635, ' ' * 256 + 'u', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0059691705, ' ' * 256 + 'v', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0074963393, ' ' * 256 + 'w', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.0929355E-4, ' ' * 256 + 'y', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.004509431, ' ' * 256 + 'z', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.453399E-4, ' ' * 256 + 'ä', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.66286E-4, ' ' * 256 + 'ö', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(8.188586E-4, ' ' * 256 + 'ü', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(7.8856654E-4, '-' * 256 + ' ', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.9516706E-4, '-' * 256 + 'a', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.6257507E-4, '-' * 256 + 'b', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.00763005E-4, '-' * 256 + 'c', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.3409718E-4, '-' * 256 + 'f', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.2963491E-4, '-' * 256 + 'g', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.6127396E-4, '-' * 256 + 'j', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.568575E-4, '-' * 256 + 'k', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.6351884E-4, '-' * 256 + 'm', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.9235408E-4, '-' * 256 + 'p', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.0907364E-4, '-' * 256 + 'r', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(3.0354434E-4, '-' * 256 + 's', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.5163473E-4, '-' * 256 + 't', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.0857885E-4, '-' * 256 + 'w', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0012044281, 'a' * 256 + ' ', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.374141E-4, 'a' * 256 + '-', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.8944283E-4, 'a' * 256 + 'a', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0026079267, 'a' * 256 + 'b', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.002482424, 'a' * 256 + 'c', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(8.9132704E-4, 'a' * 256 + 'd', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0010878685, 'a' * 256 + 'f', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0025208986, 'a' * 256 + 'g', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0020196766, 'a' * 256 + 'h', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(5.522861E-4, 'a' * 256 + 'i', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(6.640994E-4, 'a' * 256 + 'k', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.005091313, 'a' * 256 + 'l', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.002584699, 'a' * 256 + 'm', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.008885965, 'a' * 256 + 'n', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(4.5967798E-4, 'a' * 256 + 'p', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0046354374, 'a' * 256 + 'r', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0042639053, 'a' * 256 + 's', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0038234692, 'a' * 256 + 't', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0070152385, 'a' * 256 + 'u', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.9305962E-4, 'a' * 256 + 'v', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.7451416E-4, 'a' * 256 + 'y', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.943424E-4, 'a' * 256 + 'z', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.9944606E-4, 'a' * 256 + 'ß', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(5.7094154E-4, 'b' * 256 + ' ', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0016077826, 'b' * 256 + 'a', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.009236139, 'b' * 256 + 'e', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.6843008E-4, 'b' * 256 + 'g', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0013397989, 'b' * 256 + 'i', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(7.2323595E-4, 'b' * 256 + 'l', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.0232068E-4, 'b' * 256 + 'n', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(4.911246E-4, 'b' * 256 + 'o', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(9.527084E-4, 'b' * 256 + 'r', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(5.3022214E-4, 'b' * 256 + 's', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(4.0235565E-4, 'b' * 256 + 't', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(8.3197973E-4, 'b' * 256 + 'u', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.082515E-4, 'b' * 256 + 'ü', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.6494823E-4, 'c' * 256 + ' ', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.5313074E-4, 'c' * 256 + 'a', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(3.00768E-4, 'c' * 256 + 'e', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.02044844, 'c' * 256 + 'h', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0014985531, 'c' * 256 + 'k', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(3.7136718E-4, 'c' * 256 + 'o', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.005643379, 'd' * 256 + ' ', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.4138158E-4, 'd' * 256 + '-', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.004419178, 'd' * 256 + 'a', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.017955773, 'd' * 256 + 'e', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0075178533, 'd' * 256 + 'i', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.9798254E-4, 'd' * 256 + 'l', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.0555514E-4, 'd' * 256 + 'n', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(9.0674794E-4, 'd' * 256 + 'o', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.8534456E-4, 'd' * 256 + 'p', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(7.915078E-4, 'd' * 256 + 'r', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(3.7940292E-4, 'd' * 256 + 's', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(3.2352834E-4, 'd' * 256 + 't', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0010067871, 'd' * 256 + 'u', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.240731E-4, 'd' * 256 + 'ü', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.020428475, 'e' * 256 + ' ', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.4175059E-4, 'e' * 256 + '-', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(5.5519986E-4, 'e' * 256 + 'a', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.001771338, 'e' * 256 + 'b', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0011971529, 'e' * 256 + 'c', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0015801934, 'e' * 256 + 'd', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(3.7845917E-4, 'e' * 256 + 'e', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.001067518, 'e' * 256 + 'f', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0024293074, 'e' * 256 + 'g', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0028560727, 'e' * 256 + 'h', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.015943252, 'e' * 256 + 'i', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(8.688049E-4, 'e' * 256 + 'k', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0055244737, 'e' * 256 + 'l', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0034923723, 'e' * 256 + 'm', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.031716663, 'e' * 256 + 'n', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.8818502E-4, 'e' * 256 + 'o', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(3.7164206E-4, 'e' * 256 + 'p', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.031523027, 'e' * 256 + 'r', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.00812584, 'e' * 256 + 's', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0036548376, 'e' * 256 + 't', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0025362738, 'e' * 256 + 'u', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.0668466E-4, 'e' * 256 + 'v', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(8.8304386E-4, 'e' * 256 + 'w', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(3.499904E-4, 'e' * 256 + 'x', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(3.5685333E-4, 'e' * 256 + 'z', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.9898792E-4, 'e' * 256 + 'ß', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0020417222, 'f' * 256 + ' ', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.001480933, 'f' * 256 + 'a', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0022403987, 'f' * 256 + 'e', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(9.1802736E-4, 'f' * 256 + 'f', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.1452799E-4, 'f' * 256 + 'g', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(9.327519E-4, 'f' * 256 + 'i', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(5.383587E-4, 'f' * 256 + 'l', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.0975169E-4, 'f' * 256 + 'n', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(9.887273E-4, 'f' * 256 + 'o', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0013027446, 'f' * 256 + 'r', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.4129245E-4, 'f' * 256 + 's', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0016081949, 'f' * 256 + 't', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(4.1187578E-4, 'f' * 256 + 'u', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.416498E-4, 'f' * 256 + 'ä', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0018397564, 'f' * 256 + 'ü', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0038120432, 'g' * 256 + ' ', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0014108287, 'g' * 256 + 'a', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.012370394, 'g' * 256 + 'e', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.2614389E-4, 'g' * 256 + 'h', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0010279164, 'g' * 256 + 'i', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.2749081E-4, 'g' * 256 + 'k', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(8.518538E-4, 'g' * 256 + 'l', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.30178E-4, 'g' * 256 + 'n', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.6739718E-4, 'g' * 256 + 'o', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0013585826, 'g' * 256 + 'r', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.001124492, 'g' * 256 + 's', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0013461396, 'g' * 256 + 't', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(6.790714E-4, 'g' * 256 + 'u', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.07121974E-4, 'g' * 256 + 'ä', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.005750272, 'h' * 256 + ' ', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.004480156, 'h' * 256 + 'a', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.007367153, 'h' * 256 + 'e', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0015424978, 'h' * 256 + 'i', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.295891E-4, 'h' * 256 + 'k', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0017258723, 'h' * 256 + 'l', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(7.997909E-4, 'h' * 256 + 'm', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0012519371, 'h' * 256 + 'n', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0011897036, 'h' * 256 + 'o', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0037801384, 'h' * 256 + 'r', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(6.9049734E-4, 'h' * 256 + 's', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0038385603, 'h' * 256 + 't', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(6.8318547E-4, 'h' * 256 + 'u', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(5.3283357E-4, 'h' * 256 + 'w', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(6.840193E-4, 'h' * 256 + 'ä', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(4.1781325E-4, 'h' * 256 + 'ö', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.6264837E-4, 'h' * 256 + 'ü', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0019196192, 'i' * 256 + ' ', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(6.24388E-4, 'i' * 256 + 'a', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(5.363429E-4, 'i' * 256 + 'b', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.006906797, 'i' * 256 + 'c', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(9.05062E-4, 'i' * 256 + 'd', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.014058808, 'i' * 256 + 'e', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(4.5727732E-4, 'i' * 256 + 'f', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0031937668, 'i' * 256 + 'g', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(8.439555E-4, 'i' * 256 + 'h', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(8.3862274E-4, 'i' * 256 + 'k', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0023541087, 'i' * 256 + 'l', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.002465922, 'i' * 256 + 'm', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.015094238, 'i' * 256 + 'n', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0015944599, 'i' * 256 + 'o', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.3033377E-4, 'i' * 256 + 'p', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.001651663, 'i' * 256 + 'r', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0056855553, 'i' * 256 + 's', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.00698677, 'i' * 256 + 't', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.1932679E-4, 'i' * 256 + 'u', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(5.882225E-4, 'i' * 256 + 'v', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(4.5057017E-4, 'i' * 256 + 'z', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.3395057E-4, 'i' * 256 + 'ß', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(9.269702E-4, 'j' * 256 + 'a', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(6.2953745E-4, 'j' * 256 + 'e', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.6136559E-4, 'j' * 256 + 'o', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(3.3491765E-4, 'j' * 256 + 'u', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.0143439E-4, 'j' * 256 + 'ä', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(8.608975E-4, 'k' * 256 + ' ', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.001667368, 'k' * 256 + 'a', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0022487734, 'k' * 256 + 'e', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(5.0967006E-4, 'k' * 256 + 'i', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(8.5919315E-4, 'k' * 256 + 'l', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.3485769E-4, 'k' * 256 + 'n', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0018184347, 'k' * 256 + 'o', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(7.551957E-4, 'k' * 256 + 'r', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.988713E-4, 'k' * 256 + 's', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0016666166, 'k' * 256 + 't', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(7.847457E-4, 'k' * 256 + 'u', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(4.702793E-4, 'k' * 256 + 'ö', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.2595398E-4, 'k' * 256 + 'ü', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0026737337, 'l' * 256 + ' ', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.8449243E-4, 'l' * 256 + '-', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.003270102, 'l' * 256 + 'a', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(5.0927605E-4, 'l' * 256 + 'b', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.5417281E-4, 'l' * 256 + 'c', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(8.4888504E-4, 'l' * 256 + 'd', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.006164521, 'l' * 256 + 'e', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(4.112344E-4, 'l' * 256 + 'f', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(3.8426835E-4, 'l' * 256 + 'g', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.004990513, 'l' * 256 + 'i', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.3629874E-4, 'l' * 256 + 'k', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0041883676, 'l' * 256 + 'l', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.2266453E-4, 'l' * 256 + 'm', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.9426243E-4, 'l' * 256 + 'n', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(9.336499E-4, 'l' * 256 + 'o', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0015364412, 'l' * 256 + 's', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0024270352, 'l' * 256 + 't', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0010214751, 'l' * 256 + 'u', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.0002999E-4, 'l' * 256 + 'v', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.3949405E-4, 'l' * 256 + 'y', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.3523336E-4, 'l' * 256 + 'z', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(6.936402E-4, 'l' * 256 + 'ä', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.6781618E-4, 'l' * 256 + 'ö', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.6293243E-4, 'l' * 256 + 'ü', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0065787975, 'm' * 256 + ' ', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0029572665, 'm' * 256 + 'a', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(3.132935E-4, 'm' * 256 + 'b', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0041506444, 'm' * 256 + 'e', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0038112828, 'm' * 256 + 'i', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.097242E-4, 'm' * 256 + 'l', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0015312826, 'm' * 256 + 'm', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(8.15615E-4, 'm' * 256 + 'o', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(5.1492034E-4, 'm' * 256 + 'p', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(4.246212E-4, 'm' * 256 + 's', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(5.0888205E-4, 'm' * 256 + 't', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(6.6214777E-4, 'm' * 256 + 'u', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.2026389E-4, 'm' * 256 + 'ä', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.482653E-4, 'm' * 256 + 'ö', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(3.1887364E-4, 'm' * 256 + 'ü', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.028289638, 'n' * 256 + ' ', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.8244246E-4, 'n' * 256 + '-', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0030980615, 'n' * 256 + 'a', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(4.6352635E-4, 'n' * 256 + 'b', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(3.340197E-4, 'n' * 256 + 'c', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.009861178, 'n' * 256 + 'd', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.008597256, 'n' * 256 + 'e', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(8.491233E-4, 'n' * 256 + 'f', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0063544195, 'n' * 256 + 'g', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(3.8210594E-4, 'n' * 256 + 'h', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0031501611, 'n' * 256 + 'i', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0013554215, 'n' * 256 + 'k', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(4.5087255E-4, 'n' * 256 + 'l', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.34118E-4, 'n' * 256 + 'm', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0028809863, 'n' * 256 + 'n', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0011672548, 'n' * 256 + 'o', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.016518E-4, 'n' * 256 + 'p', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.6198866E-4, 'n' * 256 + 'r', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0031016627, 'n' * 256 + 's', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.004784974, 'n' * 256 + 't', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0011061116, 'n' * 256 + 'u', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.9861225E-4, 'n' * 256 + 'v', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.320747E-4, 'n' * 256 + 'w', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0012513141, 'n' * 256 + 'z', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.1355673E-4, 'n' * 256 + 'ä', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.1282122E-4, 'n' * 256 + 'ü', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0013052187, 'o' * 256 + ' ', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.1973911E-4, 'o' * 256 + 'a', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(7.30896E-4, 'o' * 256 + 'b', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0014085656, 'o' * 256 + 'c', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(6.7963946E-4, 'o' * 256 + 'd', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(6.6318316E-4, 'o' * 256 + 'f', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(5.1845715E-4, 'o' * 256 + 'g', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(6.2600063E-4, 'o' * 256 + 'h', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.3329335E-4, 'o' * 256 + 'k', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0024334583, 'o' * 256 + 'l', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.00146258, 'o' * 256 + 'm', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.005431032, 'o' * 256 + 'n', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.5359806E-4, 'o' * 256 + 'o', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(6.2444294E-4, 'o' * 256 + 'p', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.004082006, 'o' * 256 + 'r', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0011047188, 'o' * 256 + 's', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(9.08663E-4, 'o' * 256 + 't', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.777786E-4, 'o' * 256 + 'u', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.6431599E-4, 'o' * 256 + 'v', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(3.008688E-4, 'o' * 256 + 'w', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(4.385578E-4, 'o' * 256 + 'z', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.590499E-4, 'o' * 256 + 'ß', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.3130502E-4, 'p' * 256 + ' ', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0012533757, 'p' * 256 + 'a', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.001070954, 'p' * 256 + 'e', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(4.8069737E-4, 'p' * 256 + 'f', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.2482694E-4, 'p' * 256 + 'h', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(9.1740425E-4, 'p' * 256 + 'i', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(6.058975E-4, 'p' * 256 + 'l', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(8.8632415E-4, 'p' * 256 + 'o', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(4.4863683E-4, 'p' * 256 + 'p', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.002063484, 'p' * 256 + 'r', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.1485535E-4, 'p' * 256 + 's', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(3.1354092E-4, 'p' * 256 + 't', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(3.5628525E-4, 'p' * 256 + 'u', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.5188212E-4, 'p' * 256 + 'ä', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.6298073E-4, 'q' * 256 + 'u', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.018584823, 'r' * 256 + ' ', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.1000158E-4, 'r' * 256 + '-', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.004056158, 'r' * 256 + 'a', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0011409943, 'r' * 256 + 'b', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(6.171769E-4, 'r' * 256 + 'c', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.002879099, 'r' * 256 + 'd', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.008362065, 'r' * 256 + 'e', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(8.676779E-4, 'r' * 256 + 'f', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0015441013, 'r' * 256 + 'g', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(7.6434016E-4, 'r' * 256 + 'h', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0037743659, 'r' * 256 + 'i', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0013762667, 'r' * 256 + 'k', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0010924774, 'r' * 256 + 'l', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(8.865624E-4, 'r' * 256 + 'm', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0019381279, 'r' * 256 + 'n', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0028254143, 'r' * 256 + 'o', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.375174E-4, 'r' * 256 + 'p', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(6.0617237E-4, 'r' * 256 + 'r', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.002845783, 'r' * 256 + 's', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.004183713, 'r' * 256 + 't', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.002057629, 'r' * 256 + 'u', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.922991E-4, 'r' * 256 + 'v', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(6.0856383E-4, 'r' * 256 + 'w', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(7.8644074E-4, 'r' * 256 + 'z', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(6.515006E-4, 'r' * 256 + 'ä', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.4364729E-4, 'r' * 256 + 'ö', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(8.023748E-4, 'r' * 256 + 'ü', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.010850976, 's' * 256 + ' ', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(3.3740076E-4, 's' * 256 + '-', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0020536887, 's' * 256 + 'a', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(3.9600587E-4, 's' * 256 + 'b', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0066104364, 's' * 256 + 'c', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.3339164E-4, 's' * 256 + 'd', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.006453194, 's' * 256 + 'e', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.3402637E-4, 's' * 256 + 'f', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(5.002324E-4, 's' * 256 + 'g', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(4.2935836E-4, 's' * 256 + 'h', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.004523432, 's' * 256 + 'i', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(4.008713E-4, 's' * 256 + 'k', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(4.2097442E-4, 's' * 256 + 'l', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.7934543E-4, 's' * 256 + 'm', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0023265288, 's' * 256 + 'o', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0020266313, 's' * 256 + 'p', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.4235534E-4, 's' * 256 + 'r', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0036687648, 's' * 256 + 's', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.010180133, 's' * 256 + 't', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(6.558621E-4, 's' * 256 + 'u', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.381771E-4, 's' * 256 + 'v', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.9436324E-4, 's' * 256 + 'w', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.3944824E-4, 's' * 256 + 'y', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.9891463E-4, 's' * 256 + 'z', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.6537888E-4, 's' * 256 + 'ä', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.004698E-4, 's' * 256 + 'ü', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.012844952, 't' * 256 + ' ', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.9731114E-4, 't' * 256 + '-', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.00318257, 't' * 256 + 'a', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.7658494E-4, 't' * 256 + 'b', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.015626669, 't' * 256 + 'e', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.7269993E-4, 't' * 256 + 'f', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(3.0621068E-4, 't' * 256 + 'g', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(6.090312E-4, 't' * 256 + 'h', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.00403534, 't' * 256 + 'i', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(8.5397955E-4, 't' * 256 + 'l', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.126588E-4, 't' * 256 + 'm', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.5336649E-4, 't' * 256 + 'n', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0013612948, 't' * 256 + 'o', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.1727432E-4, 't' * 256 + 'p', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.002529017, 't' * 256 + 'r', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0024042106, 't' * 256 + 's', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0021259282, 't' * 256 + 't', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0014760585, 't' * 256 + 'u', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.1788823E-4, 't' * 256 + 'v', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(6.527559E-4, 't' * 256 + 'w', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0020117692, 't' * 256 + 'z', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(5.713172E-4, 't' * 256 + 'ä', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.8369777E-4, 't' * 256 + 'ü', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0016921717, 'u' * 256 + ' ', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(3.0681543E-4, 'u' * 256 + 'a', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(4.1237057E-4, 'u' * 256 + 'b', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0016089645, 'u' * 256 + 'c', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(3.302538E-4, 'u' * 256 + 'd', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0011240614, 'u' * 256 + 'e', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.002688614, 'u' * 256 + 'f', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(6.857419E-4, 'u' * 256 + 'g', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.820118E-4, 'u' * 256 + 'h', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.11428475E-4, 'u' * 256 + 'i', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.4116418E-4, 'u' * 256 + 'k', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(7.25151E-4, 'u' * 256 + 'l', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0021258732, 'u' * 256 + 'm', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.00997989, 'u' * 256 + 'n', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(3.6373458E-4, 'u' * 256 + 'p', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0033667784, 'u' * 256 + 'r', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0037184455, 'u' * 256 + 's', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.002144492, 'u' * 256 + 't', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.4200714E-4, 'u' * 256 + 'ß', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.4818036E-4, 'v' * 256 + ' ', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.8033502E-4, 'v' * 256 + 'a', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0034559595, 'v' * 256 + 'e', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(8.716729E-4, 'v' * 256 + 'i', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.003343853, 'v' * 256 + 'o', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.7378114E-4, 'w' * 256 + ' ', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.00215721, 'w' * 256 + 'a', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0043956474, 'w' * 256 + 'e', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0032023615, 'w' * 256 + 'i', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0011008614, 'w' * 256 + 'o', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(5.469809E-4, 'w' * 256 + 'u', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(3.6272669E-4, 'w' * 256 + 'ä', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.0415573E-4, 'w' * 256 + 'ü', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.345095E-4, 'x' * 256 + ' ', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.0518863E-4, 'x' * 256 + 'p', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.5633772E-4, 'y' * 256 + ' ', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.2409143E-4, 'y' * 256 + 'e', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.9838319E-4, 'y' * 256 + 's', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(7.7010354E-4, 'z' * 256 + ' ', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(3.0883125E-4, 'z' * 256 + 'a', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0027350327, 'z' * 256 + 'e', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(9.439122E-4, 'z' * 256 + 'i', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.4115252E-4, 'z' * 256 + 'l', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.4525744E-4, 'z' * 256 + 'o', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(6.8818836E-4, 'z' * 256 + 't', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.003552865, 'z' * 256 + 'u', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(7.213576E-4, 'z' * 256 + 'w', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.4699835E-4, 'ß' * 256 + ' ', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(5.966706E-4, 'ß' * 256 + 'e', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.3433541E-4, 'ß' * 256 + 't', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(3.3161906E-4, 'ä' * 256 + 'c', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.191252E-4, 'ä' * 256 + 'd', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.3120423E-4, 'ä' * 256 + 'f', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.013336E-4, 'ä' * 256 + 'g', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(5.888639E-4, 'ä' * 256 + 'h', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.7208854E-4, 'ä' * 256 + 'l', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.07332715E-4, 'ä' * 256 + 'm', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(7.854695E-4, 'ä' * 256 + 'n', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(5.9249235E-4, 'ä' * 256 + 'r', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(3.1757253E-4, 'ä' * 256 + 's', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(6.753055E-4, 'ä' * 256 + 't', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(3.5972128E-4, 'ä' * 256 + 'u', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.11382666E-4, 'ö' * 256 + 'c', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.5283504E-4, 'ö' * 256 + 'f', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.9149279E-4, 'ö' * 256 + 'g', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.986764E-4, 'ö' * 256 + 'h', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.6387619E-4, 'ö' * 256 + 'l', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(4.8080733E-4, 'ö' * 256 + 'n', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(3.7885315E-4, 'ö' * 256 + 'r', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.1260382E-4, 'ö' * 256 + 's', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.07323554E-4, 'ö' * 256 + 'ß', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(9.783367E-4, 'ü' * 256 + 'b', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(5.048504E-4, 'ü' * 256 + 'c', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.4477181E-4, 'ü' * 256 + 'g', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(5.1328016E-4, 'ü' * 256 + 'h', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(1.3503178E-4, 'ü' * 256 + 'l', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(6.058425E-4, 'ü' * 256 + 'n', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(0.0019630783, 'ü' * 256 + 'r', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.9406085E-4, 'ü' * 256 + 's', 0);
		CHARBIGRAMFREQUENCIES.setAsDouble(2.115959E-4, 'ü' * 256 + 't', 0);

	}

}
