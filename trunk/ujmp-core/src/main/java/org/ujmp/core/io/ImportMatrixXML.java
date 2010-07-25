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

package org.ujmp.core.io;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.ujmp.core.Matrix;
import org.ujmp.core.graphmatrix.DefaultGraphMatrix;
import org.ujmp.core.graphmatrix.GraphMatrix;
import org.ujmp.core.mapmatrix.DefaultMapMatrix;
import org.ujmp.core.mapmatrix.MapMatrix;
import org.ujmp.core.util.io.IntelligentFileReader;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class ImportMatrixXML {

	public ImportMatrixXML() {
	}

	public static final Matrix fromString(String string, Object... parameters)
			throws ParserConfigurationException, SAXException, IOException {
		StringReader sr = new StringReader(string);
		IntelligentFileReader r = new IntelligentFileReader(sr);
		Matrix m = fromReader(r, parameters);
		r.close();
		return m;
	}

	public static final Matrix fromStream(InputStream stream, Object... parameters)
			throws IOException, ParserConfigurationException, SAXException {
		InputStreamReader r = new InputStreamReader(stream);
		Matrix m = fromReader(r, parameters);
		r.close();
		return m;
	}

	public static final Matrix fromFile(File file, Object... parameters) throws IOException,
			ParserConfigurationException, SAXException {
		FileReader lr = new FileReader(file);
		Matrix m = fromReader(lr, parameters);
		lr.close();
		return m;
	}

	// private void addCell(Matrix matrix) {
	// if
	// ("cell".equals(currentEvent.asStartElement().getName().getLocalPart())) {
	// StartElement element = currentEvent.asStartElement();
	// String po = element.getAttributeByName(new QName("pos")).getValue();
	// long[] pos = Coordinates.parseString(po);
	// currentEvent = eventReader.nextEvent();
	// String s = currentEvent.asCharacters().getData();
	// matrix.setAsObject(s, pos);
	// currentEvent = eventReader.nextEvent();
	// if (!"cell".equals(currentEvent.asEndElement().getName().getLocalPart()))
	// {
	// throw new MatrixException("xml does not have excected format: " +
	// currentEvent);
	// }
	// currentEvent = eventReader.nextEvent();
	// }
	// }

	// private Matrix parseMatrix() {
	// Matrix matrix = null;
	// if
	// ("matrix".equals(currentEvent.asStartElement().getName().getLocalPart()))
	// {
	// StartElement element = currentEvent.asStartElement();
	// String si = element.getAttributeByName(new QName("size")).getValue();
	// String st = element.getAttributeByName(new
	// QName("storageType")).getValue();
	// String vt = element.getAttributeByName(new
	// QName("valueType")).getValue();
	// long[] size = Coordinates.parseString(si);
	// ValueType valueType = ValueType.valueOf(vt);
	// StorageType storageType = StorageType.valueOf(st);
	//
	// switch (storageType) {
	// case SPARSE:
	// matrix = MatrixFactory.sparse(valueType, size);
	// break;
	// default:
	// matrix = MatrixFactory.dense(valueType, size);
	// break;
	// }
	//
	// currentEvent = eventReader.nextEvent();
	// skipCharacters();
	// skipInfo();
	// skipCharacters();
	//
	// if (currentEvent.getEventType() == XMLEvent.START_ELEMENT
	// && "data".equals(currentEvent.asStartElement().getName().getLocalPart()))
	// {
	// currentEvent = eventReader.nextEvent();
	// skipCharacters();
	// } else {
	// throw new MatrixException("xml does not have excected format: " +
	// currentEvent);
	// }
	//
	// while (currentEvent.getEventType() == XMLEvent.START_ELEMENT
	// && "cell".equals(currentEvent.asStartElement().getName().getLocalPart()))
	// {
	// addCell(matrix);
	// skipCharacters();
	// }
	//
	// if (!"data".equals(currentEvent.asEndElement().getName().getLocalPart()))
	// {
	// throw new MatrixException("xml does not have excected format: " +
	// currentEvent);
	// }
	//
	// currentEvent = eventReader.nextEvent();
	// skipCharacters();
	//
	// if
	// (!"matrix".equals(currentEvent.asEndElement().getName().getLocalPart()))
	// {
	// throw new MatrixException("xml does not have excected format: " +
	// currentEvent);
	// }
	//
	// } else {
	// throw new MatrixException("xml does not have excected format: " +
	// currentEvent);
	// }
	// return matrix;
	// }

	public Matrix read(Reader reader) throws ParserConfigurationException, SAXException,
			IOException {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		spf.setNamespaceAware(true);
		SAXParser saxParser = spf.newSAXParser();
		XMLReader xmlReader = saxParser.getXMLReader();
		UJMPContentHandler contentHandler = new UJMPContentHandler();
		xmlReader.setContentHandler(contentHandler);
		xmlReader.parse(new InputSource(reader));
		// System.out.println(contentHandler.getResult());
		return contentHandler.getResult();
	}

	public static final Matrix fromReader(Reader reader, Object... parameters)
			throws ParserConfigurationException, SAXException, IOException {
		return new ImportMatrixXML().read(reader);
	}

}

class UJMPContentHandler implements ContentHandler {

	private GraphMatrix<Object, Boolean> graph = new DefaultGraphMatrix<Object, Boolean>();

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