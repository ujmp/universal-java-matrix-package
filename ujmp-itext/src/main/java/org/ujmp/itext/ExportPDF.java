/*
 * Copyright (C) 2008-2013 by Holger Arndt
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

package org.ujmp.itext;

import java.awt.Component;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;

import org.ujmp.core.UJMP;
import org.ujmp.core.enums.FileFormat;
import org.ujmp.gui.interfaces.CanRenderGraph;

import com.itextpdf.text.Document;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.DefaultFontMapper;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

public abstract class ExportPDF {

	private static final Logger logger = Logger.getLogger(ExportPDF.class
			.getName());

	public static final File selectFile() {
		return selectFile(null);
	}

	public static final File selectFile(Component c) {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(FileFormat.PDF.getFileFilter());
		int returnVal = chooser.showOpenDialog(c);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			if (!file.getAbsolutePath().toLowerCase().endsWith(".pdf"))
				file = new File(file.getAbsolutePath() + ".pdf");
			return file;
		}
		return null;
	}

	public static final void save(File file, Component c) {
		save(file, c, c.getWidth(), c.getHeight());
	}

	public static final void save(File file, Component c, int width, int height) {
		if (file == null) {
			logger.log(Level.WARNING, "no file selected");
			return;
		}
		if (c == null) {
			logger.log(Level.WARNING, "no component provided");
			return;
		}
		try {
			Document document = new Document(new Rectangle(width, height));
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream(file.getAbsolutePath()));
			document.addAuthor("UJMP v" + UJMP.UJMPVERSION);
			document.open();
			PdfContentByte cb = writer.getDirectContent();
			PdfTemplate tp = cb.createTemplate(width, height);
			Graphics2D g2 = tp.createGraphics(width, height,
					new DefaultFontMapper());
			if (c instanceof CanRenderGraph) {
				((CanRenderGraph) c).renderGraph(g2);
			} else {
				c.paint(g2);
			}
			g2.dispose();
			cb.addTemplate(tp, 0, 0);
			document.close();
			writer.close();
		} catch (Exception e) {
			logger.log(Level.WARNING, "could not save PDF file", e);
		}
	}
}
