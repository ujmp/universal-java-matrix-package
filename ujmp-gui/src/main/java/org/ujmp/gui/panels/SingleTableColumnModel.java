package org.ujmp.gui.panels;

import org.ujmp.gui.table.DefaultTableColumnModel64;
import org.ujmp.gui.table.TableModel64;

public class SingleTableColumnModel extends DefaultTableColumnModel64 {
	private static final long serialVersionUID = -5579773064106309764L;

	public SingleTableColumnModel(TableModel64 tableModel64) {
		super(tableModel64);
		setDefaultColumnWidth(50);
	}

}
