package org.ujmp.gui.table;

import java.util.EventObject;

public class TableColumnModelEvent64 extends EventObject {
	private static final long serialVersionUID = -1752342614756515488L;

	protected final long fromIndex;

	protected final long toIndex;

	public TableColumnModelEvent64(TableColumnModel64 source, long from, long to) {
		super(source);
		fromIndex = from;
		toIndex = to;
	}

	public long getFromIndex64() {
		return fromIndex;
	};

	public long getToIndex64() {
		return toIndex;
	};

}
