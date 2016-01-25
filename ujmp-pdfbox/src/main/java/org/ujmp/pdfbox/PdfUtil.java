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

package org.ujmp.pdfbox;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public abstract class PdfUtil {

	public static final String getTextFromFile(File file) throws IOException {
		PDDocument pdd = PDDocument.load(file);
		PDFTextStripper pts = new PDFTextStripper();
		String text = pts.getText(pdd);
		pdd.close();
		return text;
	}

	public static final String getTextFromStream(InputStream inputStream) throws IOException {
		PDDocument pdd = PDDocument.load(inputStream);
		PDFTextStripper pts = new PDFTextStripper();
		String text = pts.getText(pdd);
		pdd.close();
		return text;
	}

	public static final String getTextFromBytes(byte[] data) throws IOException {
		ByteArrayInputStream is = new ByteArrayInputStream(data);
		return getTextFromStream(is);
	}

}
