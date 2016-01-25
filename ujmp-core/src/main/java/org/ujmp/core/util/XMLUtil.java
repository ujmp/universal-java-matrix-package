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

package org.ujmp.core.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.ujmp.core.Matrix;
import org.ujmp.core.mapmatrix.DefaultMapMatrix;
import org.ujmp.core.mapmatrix.MapMatrix;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class XMLUtil {

	private static final String XMLHEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

	public static Matrix parse(File file) throws FileNotFoundException, IOException, SAXException,
			ParserConfigurationException {
		InputStream fis = new FileInputStream(file);
		InputStream bis = new BufferedInputStream(fis);
		Matrix m = parse(bis);
		bis.close();
		fis.close();
		return m;
	}

	public static Matrix parse(InputStream is) throws FileNotFoundException, IOException,
			SAXException, ParserConfigurationException {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		spf.setNamespaceAware(true);
		SAXParser saxParser = spf.newSAXParser();
		XMLReader xmlReader = saxParser.getXMLReader();
		UJMPContentHandler contentHandler = new UJMPContentHandler();
		xmlReader.setContentHandler(contentHandler);
		xmlReader.parse(new InputSource(is));
		// System.out.println(contentHandler.getResult());
		return contentHandler.getResult();
	}

	public static void write(File file, Matrix matrix) throws IOException {
		OutputStream fos = new FileOutputStream(file);
		OutputStream bos = new BufferedOutputStream(fos);
		write(bos, matrix);
		bos.close();
		fos.close();
	}

	public static void write(OutputStream out, Matrix matrix) throws IOException {
		PrintWriter pw = new PrintWriter(out);
		write(pw, matrix);
		pw.close();
	}

	public static void write(Writer pw, Matrix matrix) throws IOException {
		VerifyUtil.verifyNotNull(matrix, "matrix is null");
		final String EOL = System.getProperty("line.separator");
		pw.write(XMLHEADER);
		pw.write(EOL);
		if (matrix == null) {
			pw.write("<emptyMatrix>");
			pw.write(EOL);
			pw.write("</emptyMatrix>");
			pw.write(EOL);
		} else if (matrix instanceof Map<?, ?>) {
			pw.write("<map>");
			pw.write(EOL);
			Map<?, ?> map = (Map<?, ?>) matrix;
			for (Map.Entry<?, ?> e : map.entrySet()) {
				Object key = e.getKey();
				Object value = e.getValue();
				pw.write("<" + key + ">");
				pw.write(String.valueOf(value));
				pw.write("</" + key + ">");
				pw.write(EOL);
			}
			pw.write("</map>");
			pw.write(EOL);
		}
	}
}

class UJMPContentHandler implements ContentHandler {

	private String key = null;

	private String value = null;

	private MapMatrix<String, Object> root = null;

	public void characters(char[] ch, int start, int length) throws SAXException {
		if (root != null) {
			if (key != null) {
				value = new String(ch, start, length);
				root.put(key, value);
			}
			// System.out.println("characters: " + value);
		}
	}

	public void endDocument() throws SAXException {
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {
		if ("map".equalsIgnoreCase(localName)) {
		} else {
			key = null;
		}
		// System.out.println("end: " + localName);
	}

	public void endPrefixMapping(String prefix) throws SAXException {
	}

	public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
	}

	public void processingInstruction(String target, String data) throws SAXException {
	}

	public void setDocumentLocator(Locator locator) {
	}

	public void skippedEntity(String name) throws SAXException {
	}

	public void startDocument() throws SAXException {
	}

	public void startElement(String uri, String localName, String qName, Attributes atts)
			throws SAXException {
		if ("map".equalsIgnoreCase(localName)) {
			root = new DefaultMapMatrix<String, Object>();
		} else {
			key = localName;
		}
		// System.out.println("start: " + localName);
	}

	public void startPrefixMapping(String prefix, String uri) throws SAXException {
	}

	public Matrix getResult() {
		return root;
	}
}