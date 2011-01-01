/*
 * Copyright (C) 2008-2011 by Holger Arndt
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


public class ImportMatrixGRAPHML {

//	private XMLEventReader eventReader = null;
//
//	private XMLEvent currentEvent = null;
//
//	private List<Key> keys = new ArrayList<Key>();
//
//	private Map<String, Long> nodeMap = new HashMap<String, Long>();
//
//	public ImportMatrixGRAPHML() {
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
//	private void addKey() throws XMLStreamException {
//		if ("key".equals(currentEvent.asStartElement().getName().getLocalPart())) {
//			StartElement element = currentEvent.asStartElement();
//			Key key = new Key();
//			key.id = element.getAttributeByName(new QName("id")).getValue();
//			key._for = element.getAttributeByName(new QName("for")).getValue();
//			key.attr_name = element.getAttributeByName(new QName("attr.name")).getValue();
//			key.attr_type = element.getAttributeByName(new QName("attr.type")).getValue();
//			currentEvent = eventReader.nextEvent();
//			skipCharacters();
//			if (currentEvent.getEventType() == XMLEvent.START_ELEMENT
//					&& "default".equals(currentEvent.asStartElement().getName().getLocalPart())) {
//				// skip default
//				currentEvent = eventReader.nextEvent();
//				skipCharacters();
//				if (!"default".equals(currentEvent.asEndElement().getName().getLocalPart())) {
//					throw new MatrixException("xml does not have excected format: " + currentEvent);
//				}
//				currentEvent = eventReader.nextEvent();
//			}
//			if (!"key".equals(currentEvent.asEndElement().getName().getLocalPart())) {
//				throw new MatrixException("xml does not have excected format: " + currentEvent);
//			}
//			currentEvent = eventReader.nextEvent();
//			keys.add(key);
//		}
//	}
//
//	private void addNode(GraphMatrix matrix) throws XMLStreamException {
//		if ("node".equals(currentEvent.asStartElement().getName().getLocalPart())) {
//			StartElement element = currentEvent.asStartElement();
//			String id = element.getAttributeByName(new QName("id")).getValue();
//
//			currentEvent = eventReader.nextEvent();
//			skipCharacters();
//
//			MapMatrix<String, String> node = new DefaultMapMatrix<String, String>();
//			node.setLabel(id);
//
//			while (currentEvent.getEventType() == XMLEvent.START_ELEMENT
//					&& "data".equals(currentEvent.asStartElement().getName().getLocalPart())) {
//				String key = currentEvent.asStartElement().getAttributeByName(new QName("key"))
//						.getValue();
//				currentEvent = eventReader.nextEvent();
//				String data = currentEvent.asCharacters().getData();
//				currentEvent = eventReader.nextEvent();
//				if (!"data".equals(currentEvent.asEndElement().getName().getLocalPart())) {
//					throw new MatrixException("xml does not have excected format: " + currentEvent);
//				}
//				currentEvent = eventReader.nextEvent();
//				skipCharacters();
//
//				node.put(key, data);
//			}
//			if (!"node".equals(currentEvent.asEndElement().getName().getLocalPart())) {
//				throw new MatrixException("xml does not have excected format: " + currentEvent);
//			}
//			currentEvent = eventReader.nextEvent();
//
//			nodeMap.put(id, matrix.getRowCount());
//			matrix.addNode(node);
//		}
//	}
//
//	private void addEdge(GraphMatrix matrix) throws XMLStreamException {
//		if ("edge".equals(currentEvent.asStartElement().getName().getLocalPart())) {
//			StartElement element = currentEvent.asStartElement();
//			String id = element.getAttributeByName(new QName("id")).getValue();
//			String source = element.getAttributeByName(new QName("source")).getValue();
//			String target = element.getAttributeByName(new QName("target")).getValue();
//
//			currentEvent = eventReader.nextEvent();
//			skipCharacters();
//
//			MapMatrix<String, String> edge = new DefaultMapMatrix<String, String>();
//			edge.setLabel(id);
//
//			while (currentEvent.getEventType() == XMLEvent.START_ELEMENT
//					&& "data".equals(currentEvent.asStartElement().getName().getLocalPart())) {
//				String key = currentEvent.asStartElement().getAttributeByName(new QName("key"))
//						.getValue();
//				currentEvent = eventReader.nextEvent();
//				String data = currentEvent.asCharacters().getData();
//				currentEvent = eventReader.nextEvent();
//				if (!"data".equals(currentEvent.asEndElement().getName().getLocalPart())) {
//					throw new MatrixException("xml does not have excected format: " + currentEvent);
//				}
//				currentEvent = eventReader.nextEvent();
//				skipCharacters();
//
//				edge.put(key, data);
//			}
//			if (!"edge".equals(currentEvent.asEndElement().getName().getLocalPart())) {
//				throw new MatrixException("xml does not have excected format: " + currentEvent);
//			}
//			currentEvent = eventReader.nextEvent();
//
//			long sourceId = nodeMap.get(source);
//			long targetId = nodeMap.get(target);
//
//			matrix.setEdge(edge, sourceId, targetId);
//		}
//	}
//
//	private List<Matrix> parseGraphML() throws XMLStreamException {
//		List<Matrix> result = new ArrayList<Matrix>();
//		if ("graphml".equals(currentEvent.asStartElement().getName().getLocalPart())) {
//
//			currentEvent = eventReader.nextEvent();
//			skipCharacters();
//
//			while (currentEvent.getEventType() == XMLEvent.START_ELEMENT
//					&& "key".equals(currentEvent.asStartElement().getName().getLocalPart())) {
//				addKey();
//				skipCharacters();
//			}
//
//			while (currentEvent.getEventType() == XMLEvent.START_ELEMENT
//					&& "graph".equals(currentEvent.asStartElement().getName().getLocalPart())) {
//				Matrix m = parseGraph();
//				result.add(m);
//				skipCharacters();
//			}
//
//		} else {
//			throw new MatrixException("xml does not have excected format: " + currentEvent);
//		}
//		return result;
//	}
//
//	private GraphMatrix parseGraph() throws XMLStreamException {
//		GraphMatrix matrix = new DefaultGraphMatrix();
//		if ("graph".equals(currentEvent.asStartElement().getName().getLocalPart())) {
//
//			StartElement element = currentEvent.asStartElement();
//			String id = element.getAttributeByName(new QName("id")).getValue();
//			String edgedefault = element.getAttributeByName(new QName("edgedefault")).getValue();
//
//			currentEvent = eventReader.nextEvent();
//			skipCharacters();
//
//			while (currentEvent.getEventType() == XMLEvent.START_ELEMENT
//					&& "node".equals(currentEvent.asStartElement().getName().getLocalPart())) {
//				addNode(matrix);
//				skipCharacters();
//			}
//
//			while (currentEvent.getEventType() == XMLEvent.START_ELEMENT
//					&& "edge".equals(currentEvent.asStartElement().getName().getLocalPart())) {
//				addEdge(matrix);
//				skipCharacters();
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
//		Matrix graph = parseGraphML().get(0);
//
//		eventReader.close();
//
//		return graph;
//	}
//
//	public static final Matrix fromReader(Reader reader, Object... parameters)
//			throws MatrixException, XMLStreamException {
//		return new ImportMatrixGRAPHML().read(reader);
//
//	}
//
//	class Key {
//		public String id;
//
//		public String _for;
//
//		public String attr_name;
//
//		public String attr_type;
//
//	}

}
