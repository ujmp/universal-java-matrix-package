package org.ujmp.gui.panels;

import java.awt.BorderLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.lang.reflect.Constructor;

import javax.swing.JPanel;

import org.ujmp.core.Matrix;

public class GraphPanel extends JPanel implements ComponentListener {
	private static final long serialVersionUID = -8790011459377639922L;

	private JPanel graphPanel = null;

	private Matrix matrix = null;

	public GraphPanel(Matrix matrix) {
		this.matrix = matrix;
		this.addComponentListener(this);
	}

	
	public void componentHidden(ComponentEvent e) {
	}

	
	public void componentMoved(ComponentEvent e) {
	}

	
	public void componentResized(ComponentEvent e) {
	}

	
	public void componentShown(ComponentEvent e) {
		if (graphPanel == null) {
			try {
				Class<?> c = Class.forName("org.ujmp.jung.MatrixGraphPanel");
				Constructor<?> con = c.getConstructor(Matrix.class);
				graphPanel = (JPanel) con.newInstance(matrix);
				setLayout(new BorderLayout());
				add(graphPanel, BorderLayout.CENTER);
				repaint(1000);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
