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

package org.ujmp.gui.print;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.RepaintManager;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/*
 * taken from here:
 * http://forum.java.sun.com/thread.jspa?threadID=601884&messageID=4215335
 */

public class PrintPreviewPanel extends JPanel implements ActionListener, ChangeListener {
	private static final long serialVersionUID = -3281742799998775189L;

	static final double INITIAL_SCALE_FACTOR = 1.0;

	Component targetComponent;

	PageFormat pageFormat = new PageFormat();

	double xScaleFactor = INITIAL_SCALE_FACTOR;

	double yScaleFactor = INITIAL_SCALE_FACTOR;

	BufferedImage bufferedImage;

	JPanel settingsPanel = new JPanel();

	PreviewPanel previewPanel;

	ButtonGroup orientationButtonGroup = new ButtonGroup();

	JRadioButton portraitButton;

	JRadioButton landscapeButton;

	JLabel xScaleLabel = new JLabel("X-Scale:", JLabel.LEFT);

	JLabel yScaleLabel = new JLabel("Y-Scale:", JLabel.LEFT);

	JButton print = new JButton("Print");

	JSpinner xsp, ysp;

	SpinnerNumberModel snmx, snmy;

	int pcw, pch;

	double wh, hw;

	public PrintPreviewPanel(Component pc) {
		targetComponent = pc;

		setLayout(new BorderLayout());

		if (targetComponent.getWidth() < 1)
			targetComponent.setSize(600, targetComponent.getHeight());
		if (targetComponent.getHeight() < 1)
			targetComponent.setSize(targetComponent.getWidth(), 400);

		bufferedImage = new BufferedImage(pcw = targetComponent.getWidth(), pch = targetComponent.getHeight(),
				BufferedImage.TYPE_INT_ARGB);
		Graphics g = bufferedImage.createGraphics();
		targetComponent.paint(g);
		g.dispose();
		wh = (double) pcw / (double) pch;
		hw = (double) pch / (double) pcw;

		pageFormat.setPaper(new PaperA4());
		pageFormat.setOrientation(PageFormat.PORTRAIT);
		previewPanel = new PreviewPanel();

		snmx = new SpinnerNumberModel(1.0, 0.1, 10.0, 0.1);
		snmy = new SpinnerNumberModel(1.0, 0.1, 10.0, 0.1);
		xsp = new JSpinner(snmx);
		ysp = new JSpinner(snmy);

		xsp.addChangeListener(this);
		ysp.addChangeListener(this);

		portraitButton = new JRadioButton("Portrait");
		portraitButton.setActionCommand("1");
		portraitButton.setSelected(true);
		portraitButton.addActionListener(this);

		landscapeButton = new JRadioButton("Landscape");
		landscapeButton.setActionCommand("2");
		landscapeButton.addActionListener(this);

		orientationButtonGroup.add(portraitButton);
		orientationButtonGroup.add(landscapeButton);

		print.addActionListener(this);

		previewPanel.setBackground(Color.white);
		settingsPanel.setBorder(BorderFactory.createTitledBorder("Settings"));
		settingsPanel.setLayout(new GridBagLayout());

		GridBagConstraints c1 = new GridBagConstraints();

		c1.insets = new Insets(15, 45, 0, 5);
		c1 = buildConstraints(c1, 0, 0, 2, 1, 0.0, 0.0);
		settingsPanel.add(portraitButton, c1);

		c1.insets = new Insets(2, 45, 0, 5);
		c1 = buildConstraints(c1, 0, 1, 2, 1, 0.0, 0.0);
		settingsPanel.add(landscapeButton, c1);

		c1.insets = new Insets(25, 5, 0, 5);
		c1 = buildConstraints(c1, 0, 2, 1, 1, 0.0, 0.0);
		settingsPanel.add(xScaleLabel, c1);

		c1.insets = new Insets(25, 5, 0, 35);
		c1 = buildConstraints(c1, 1, 2, 1, 1, 0.0, 0.0);
		settingsPanel.add(xsp, c1);

		c1.insets = new Insets(5, 5, 0, 5);
		c1 = buildConstraints(c1, 0, 3, 1, 1, 0.0, 0.0);
		settingsPanel.add(yScaleLabel, c1);

		c1.insets = new Insets(15, 5, 0, 35);
		c1 = buildConstraints(c1, 1, 3, 1, 1, 0.0, 0.0);
		settingsPanel.add(ysp, c1);

		c1.insets = new Insets(5, 35, 25, 35);
		c1 = buildConstraints(c1, 0, 7, 2, 1, 0.0, 0.0);
		settingsPanel.add(print, c1);

		add(settingsPanel, BorderLayout.WEST);
		add(previewPanel, BorderLayout.CENTER);
	}

	GridBagConstraints buildConstraints(GridBagConstraints gbc, int gx, int gy, int gw, int gh, double wx, double wy) {
		gbc.gridx = gx;
		gbc.gridy = gy;
		gbc.gridwidth = gw;
		gbc.gridheight = gh;
		gbc.weightx = wx;
		gbc.weighty = wy;
		gbc.fill = GridBagConstraints.BOTH;
		return gbc;
	}

	public class PreviewPanel extends JPanel {
		private static final long serialVersionUID = -868466190084572319L;

		public PreviewPanel() {
			setPreferredSize(new Dimension(640, 640));
		}

		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			Graphics2D g2d = (Graphics2D) g;

			int xOffsetPaper = (int) pageFormat.getImageableX();
			int yOffsetPaper = (int) pageFormat.getImageableY();
			int widthImage = (int) pageFormat.getImageableWidth();
			int heightImage = (int) pageFormat.getImageableHeight();

			int widthScaledImage = (int) Math.rint(widthImage * xScaleFactor);
			int heightScaledImage = (int) Math.rint((widthImage * hw) * yScaleFactor);

			g2d.setColor(Color.black);
			g2d.drawRect(0, 0, widthImage + 2 * xOffsetPaper, heightImage + 2 * yOffsetPaper);
			g2d.setColor(Color.lightGray);
			g2d.drawRect(xOffsetPaper, yOffsetPaper, widthImage, heightImage);
			Image image = bufferedImage.getScaledInstance(widthScaledImage, heightScaledImage, Image.SCALE_DEFAULT);
			g2d.drawImage(image, xOffsetPaper, yOffsetPaper, this);
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == portraitButton) {
			updatePreview();
		} else if (e.getSource() == landscapeButton) {
			updatePreview();
		} else if (e.getSource() == print) {
			print();
		}
	}

	public void updatePreview() {
		if (portraitButton.isSelected()) {
			pageFormat.setOrientation(PageFormat.PORTRAIT);
		} else if (landscapeButton.isSelected()) {
			pageFormat.setOrientation(PageFormat.LANDSCAPE);
		}
		setScales();
		previewPanel.repaint();
	}

	public void setScales() {
		try {
			xScaleFactor = ((Double) xsp.getValue()).doubleValue();
			yScaleFactor = ((Double) ysp.getValue()).doubleValue();
		} catch (NumberFormatException e) {
		}
	}

	public void print() {
		PrinterJob printerJob = PrinterJob.getPrinterJob();
		Book book = new Book();
		book.append(new PrintPage(), pageFormat);
		printerJob.setPageable(book);
		boolean doPrint = printerJob.printDialog();
		if (doPrint) {
			try {
				printerJob.print();
			} catch (PrinterException exception) {
				System.err.println("Printing error: " + exception);
			}
		}
	}

	class PrintPage implements Printable {

		public int print(Graphics g, PageFormat format, int pageIndex) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.translate(format.getImageableX(), format.getImageableY());

			double dw = format.getImageableWidth();
			double dh = format.getImageableHeight();
			setScales();
			double xScale = dw / (targetComponent.getWidth() * xScaleFactor);
			// double yScale = dh / (targetComponent.getHeight() *
			// yScaleFactor);

			g2d.scale(xScale, xScale);
			targetComponent.paint(g2d);
			return Printable.PAGE_EXISTS;
		}

		public void disableDoubleBuffering(Component c) {
			RepaintManager currentManager = RepaintManager.currentManager(c);
			currentManager.setDoubleBufferingEnabled(false);
		}

		public void enableDoubleBuffering(Component c) {
			RepaintManager currentManager = RepaintManager.currentManager(c);
			currentManager.setDoubleBufferingEnabled(true);
		}
	}

	public void stateChanged(ChangeEvent e) {
		updatePreview();
	}
}

class PaperA4 extends Paper {

	private static final int CM = 28;

	private static final double A4_WIDTH = 21.0 * CM;

	private static final double A4_HEIGHT = 29.7 * CM;

	public PaperA4() {
		setSize(A4_WIDTH, A4_HEIGHT);
		setImageableArea(2 * CM, 2 * CM, A4_WIDTH - 4 * CM, A4_HEIGHT - 4 * CM);
	}

}