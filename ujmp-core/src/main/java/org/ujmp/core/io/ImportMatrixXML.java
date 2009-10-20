/*
 * Copyright (C) 2008-2009 by Holger Arndt
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


public class ImportMatrixXML {

//	private XMLEventReader eventReader = null;
//
//	private XMLEvent currentEvent = null;
//
//	public ImportMatrixXML() {
//	}
//
//	public static final Matrix fromString(String string, Object... parameters)
//			throws MatrixException, XMLStreamException {
//		StringReader sr = new StringReader(string);
//		IntelligentFileReader r = new IntelligentFileReader(sr);
//		Matrix m = fromReader(r, parameters);
//		r.close();
//		return m;
//	}
//
//	public static final Matrix fromStream(InputStream stream, Object... parameters)
//			throws MatrixException, IOException, XMLStreamException {
//		InputStreamReader r = new InputStreamReader(stream);
//		Matrix m = fromReader(r, parameters);
//		r.close();
//		return m;
//	}
//
//	public static final Matrix fromFile(File file, Object... parameters) throws MatrixException,
//			IOException, XMLStreamException {
//		FileReader lr = new FileReader(file);
//		Matrix m = fromReader(lr, parameters);
//		lr.close();
//		return m;
//	}
//
//	private void skipCharacters() throws XMLStreamException {
//		while (currentEvent.getEventType() == XMLEvent.CHARACTERS) {
//			currentEvent = eventReader.nextEvent();
//			// ignore
//		}
//	}
//
//	private void skipStartDocument() throws XMLStreamException {
//		if (currentEvent.getEventType() == XMLEvent.START_DOCUMENT) {
//			currentEvent = eventReader.nextEvent();
//			// ignore
//		}
//	}
//
//	private void skipStartTag() throws XMLStreamException {
//		if (currentEvent.getEventType() == XMLEvent.START_ELEMENT) {
//			currentEvent = eventReader.nextEvent();
//			// ignore
//		}
//	}
//
//	private void skipEndTag() throws XMLStreamException {
//		if (currentEvent.getEventType() == XMLEvent.END_ELEMENT) {
//			currentEvent = eventReader.nextEvent();
//			// ignore
//		}
//	}
//
//	private void skipInfo() throws XMLStreamException {
//		if ("info".equals(currentEvent.asStartElement().getName().getLocalPart())) {
//			currentEvent = eventReader.nextEvent();
//			skipCharacters();
//			while (currentEvent.getEventType() != XMLEvent.END_ELEMENT) {
//				skipStartTag();
//				skipCharacters();
//				skipEndTag();
//				skipCharacters();
//			}
//			if ("info".equals(currentEvent.asEndElement().getName().getLocalPart())) {
//				// ignore
//				currentEvent = eventReader.nextEvent();
//			} else {
//				throw new MatrixException("xml does not have excected format: " + currentEvent);
//			}
//		}
//	}
//
//	private void addCell(Matrix matrix) throws XMLStreamException {
//		if ("cell".equals(currentEvent.asStartElement().getName().getLocalPart())) {
//			StartElement element = currentEvent.asStartElement();
//			String po = element.getAttributeByName(new QName("pos")).getValue();
//			long[] pos = Coordinates.parseString(po);
//			currentEvent = eventReader.nextEvent();
//			String s = currentEvent.asCharacters().getData();
//			matrix.setAsObject(s, pos);
//			currentEvent = eventReader.nextEvent();
//			if (!"cell".equals(currentEvent.asEndElement().getName().getLocalPart())) {
//				throw new MatrixException("xml does not have excected format: " + currentEvent);
//			}
//			currentEvent = eventReader.nextEvent();
//		}
//	}
//
//	private Matrix parseMatrix() throws XMLStreamException {
//		Matrix matrix = null;
//		if ("matrix".equals(currentEvent.asStartElement().getName().getLocalPart())) {
//			StartElement element = currentEvent.asStartElement();
//			String si = element.getAttributeByName(new QName("size")).getValue();
//			String st = element.getAttributeByName(new QName("storageType")).getValue();
//			String vt = element.getAttributeByName(new QName("valueType")).getValue();
//			long[] size = Coordinates.parseString(si);
//			ValueType valueType = ValueType.valueOf(vt);
//			StorageType storageType = StorageType.valueOf(st);
//
//			switch (storageType) {
//			case SPARSE:
//				matrix = MatrixFactory.sparse(valueType, size);
//				break;
//			default:
//				matrix = MatrixFactory.dense(valueType, size);
//				break;
//			}
//
//			currentEvent = eventReader.nextEvent();
//			skipCharacters();
//			skipInfo();
//			skipCharacters();
//
//			if (currentEvent.getEventType() == XMLEvent.START_ELEMENT
//					&& "data".equals(currentEvent.asStartElement().getName().getLocalPart())) {
//				currentEvent = eventReader.nextEvent();
//				skipCharacters();
//			} else {
//				throw new MatrixException("xml does not have excected format: " + currentEvent);
//			}
//
//			while (currentEvent.getEventType() == XMLEvent.START_ELEMENT
//					&& "cell".equals(currentEvent.asStartElement().getName().getLocalPart())) {
//				addCell(matrix);
//				skipCharacters();
//			}
//
//			if (!"data".equals(currentEvent.asEndElement().getName().getLocalPart())) {
//				throw new MatrixException("xml does not have excected format: " + currentEvent);
//			}
//
//			currentEvent = eventReader.nextEvent();
//			skipCharacters();
//
//			if (!"matrix".equals(currentEvent.asEndElement().getName().getLocalPart())) {
//				throw new MatrixException("xml does not have excected format: " + currentEvent);
//			}
//
//		} else {
//			throw new MatrixException("xml does not have excected format: " + currentEvent);
//		}
//		return matrix;
//	}
//
//	public Matrix read(Reader reader) throws MatrixException, XMLStreamException {
//		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
//		eventReader = inputFactory.createXMLEventReader(reader);
//		currentEvent = eventReader.nextEvent();
//
//		skipCharacters();
//		skipStartDocument();
//		skipCharacters();
//		Matrix matrix = parseMatrix();
//
//		eventReader.close();
//
//		return matrix;
//	}
//
//	public static final Matrix fromReader(Reader reader, Object... parameters)
//			throws MatrixException, XMLStreamException {
//		return new ImportMatrixXML().read(reader);
//
//	}

}
