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

package org.ujmp.core.io;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.annotation.Annotation;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.io.IntelligentFileWriter;

public class ExportMatrixXML {

	private static boolean createXMLHeader = true;

	public static void toFile(File file, Matrix matrix, Object... parameters) throws IOException,
			MatrixException, XMLStreamException {
		IntelligentFileWriter writer = new IntelligentFileWriter(file);
		toWriter(writer, matrix, parameters);
		writer.close();
	}

	public static void toStream(OutputStream outputStream, Matrix matrix, Object... parameters)
			throws IOException, MatrixException, XMLStreamException {
		OutputStreamWriter writer = new OutputStreamWriter(outputStream);
		toWriter(writer, matrix, parameters);
		writer.close();
	}

	private static void writeDense(XMLEventWriter eventWriter, Matrix matrix) {

	}

	public static void toWriter(Writer writer, Matrix matrix, Object... parameters)
			throws IOException, MatrixException, XMLStreamException {
		if (parameters != null && parameters.length > 0) {
			if (parameters[0] instanceof Boolean) {
				createXMLHeader = (Boolean) createXMLHeader;
			}
		}

		XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
		XMLEventWriter eventWriter = outputFactory.createXMLEventWriter(writer);
		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		XMLEvent newline = eventFactory.createDTD(System.getProperty("line.separator"));

		if (createXMLHeader == true) {
			StartDocument startDocument = eventFactory.createStartDocument();
			eventWriter.add(startDocument);
			eventWriter.add(newline);
		}

		StartElement matrixStart = eventFactory.createStartElement("", "", "matrix");
		eventWriter.add(matrixStart);

		String st = null;
		if (matrix.isSparse()) {
			st = "sparse";
		} else {
			st = "dense";
		}
		Attribute storageType = eventFactory.createAttribute("storageType", st);
		eventWriter.add(storageType);

		String vt = matrix.getValueType().name().toLowerCase();
		Attribute valueType = eventFactory.createAttribute("valueType", vt);
		eventWriter.add(valueType);

		eventWriter.add(newline);

		Annotation annotation = matrix.getAnnotation();
		if (annotation != null) {
			eventWriter.add(eventFactory.createStartElement("", "", "annotation"));
			eventWriter.add(newline);

			Object label = annotation.getMatrixAnnotation();
			if (label != null) {
				eventWriter.add(eventFactory.createStartElement("", "", "label"));
				eventWriter.add(eventFactory.createCharacters("" + label));
				eventWriter.add(eventFactory.createEndElement("", "", "label"));
				eventWriter.add(newline);
			}

			eventWriter.add(eventFactory.createEndElement("", "", "annotation"));
			eventWriter.add(newline);
		}

		for (long[] c : matrix.availableCoordinates()) {
			eventWriter.add(eventFactory.createStartElement("", "", "cell"));
			eventWriter.add(eventFactory.createEndElement("", "", "cell"));
			eventWriter.add(newline);
		}

		eventWriter.add(eventFactory.createEndElement("", "", "matrix"));
		eventWriter.add(newline);
		eventWriter.add(eventFactory.createEndDocument());
		eventWriter.close();
	}

	private static void createNode(XMLEventWriter eventWriter, String name, String value)
			throws XMLStreamException {

		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		XMLEvent end = eventFactory.createDTD("\n");
		XMLEvent tab = eventFactory.createDTD("\t");
		// Create Start node
		StartElement sElement = eventFactory.createStartElement("", "", name);
		eventWriter.add(tab);
		eventWriter.add(sElement);
		// Create Content
		Characters characters = eventFactory.createCharacters(value);
		eventWriter.add(characters);
		// Create End node
		EndElement eElement = eventFactory.createEndElement("", "", name);
		eventWriter.add(eElement);
		eventWriter.add(end);
	}

	public static void main(String[] args) throws Exception {
		Matrix matrix = MatrixFactory.randn(3, 4);
		matrix.setLabel("test");
		toStream(System.out, matrix);
	}

}
