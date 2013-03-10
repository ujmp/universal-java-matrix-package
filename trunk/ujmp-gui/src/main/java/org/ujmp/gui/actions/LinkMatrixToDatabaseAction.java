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

package org.ujmp.gui.actions;

import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.enums.DB;
import org.ujmp.core.interfaces.GUIObject;

public class LinkMatrixToDatabaseAction extends ObjectAction {
	private static final long serialVersionUID = -8221902945958386445L;

	public LinkMatrixToDatabaseAction(JComponent c, GUIObject m) {
		super(c, m);
		putValue(Action.NAME, "to Database...");
		putValue(Action.SHORT_DESCRIPTION, "link a matrix to a JDBC database");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
	}

	
	public Object call() {
		try {
			DB type = DB.values()[JOptionPane.showOptionDialog(getComponent(),
					"Select database type", "Link Matrix",
					JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					DB.values(), DB.MySQL)];

			String host = null;
			while (host == null) {
				host = JOptionPane.showInputDialog("Enter host name:",
						"localhost");
			}
			int port = 0;
			while (port <= 0) {
				try {
					port = Integer.parseInt(JOptionPane.showInputDialog(
							"Enter port:", "3306"));
				} catch (Exception e) {
				}
			}
			String database = null;
			while (database == null) {
				database = JOptionPane.showInputDialog("Enter database name:",
						null);
			}
			String sql = null;
			while (sql == null) {
				sql = JOptionPane.showInputDialog("Enter SQL statement:",
						"SELECT * FROM ");
			}
			String username = null;
			username = JOptionPane.showInputDialog("Enter user name:", "root");
			String password = null;
			password = JOptionPane.showInputDialog("Enter password:", null);

			Matrix m = MatrixFactory.linkToJDBC(type, host, port, database,
					sql, username, password);
			m.showGUI();
			return m;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
