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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ImportMatrixRSS {

	public static final Matrix fromStream(InputStream stream, Object... parameters)
			throws MatrixException, ParserConfigurationException, SAXException, IOException {
		Matrix m = null;
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(stream);

		NodeList items = doc.getElementsByTagName("item");
		m = MatrixFactory.dense(ValueType.OBJECT, items.getLength(), 5);
		m.setColumnLabel(0, "Id");
		m.setColumnLabel(1, "Label");
		m.setColumnLabel(2, "Link");
		m.setColumnLabel(3, "Description");
		m.setColumnLabel(4, "Date");
		for (int i = 0; i < items.getLength(); i++) {
			Node item = items.item(i);
			for (int c = 0; c < item.getChildNodes().getLength(); c++) {
				Node n = item.getChildNodes().item(c);
				if (n.getNodeName().equalsIgnoreCase("guid")) {
					m.setAsObject(n.getTextContent(), i, 0);
				} else if (n.getNodeName().equalsIgnoreCase("title")) {
					m.setAsObject(n.getTextContent(), i, 1);
				} else if (n.getNodeName().equalsIgnoreCase("link")) {
					m.setAsObject(n.getTextContent(), i, 2);
				} else if (n.getNodeName().equalsIgnoreCase("description")) {
					m.setAsObject(n.getTextContent(), i, 3);
				} else if (n.getNodeName().equalsIgnoreCase("pubDate")) {
					m.setAsObject(n.getTextContent(), i, 4);
				}
			}
		}

		NodeList channels = doc.getElementsByTagName("channel");
		if (channels.getLength() > 0) {
			Node channel = channels.item(0);
			for (int c = 0; c < channel.getChildNodes().getLength(); c++) {
				Node n = channel.getChildNodes().item(c);
				if (n.getNodeName().equalsIgnoreCase("title")) {
					m.setLabel(n.getTextContent());
				}
			}
		}

		return m;
	}

	public static final Matrix fromFile(File file, Object... parameters) throws MatrixException,
			ParserConfigurationException, SAXException, IOException {
		FileInputStream lr = new FileInputStream(file);
		Matrix m = fromStream(lr, parameters);
		lr.close();
		return m;
	}

}
