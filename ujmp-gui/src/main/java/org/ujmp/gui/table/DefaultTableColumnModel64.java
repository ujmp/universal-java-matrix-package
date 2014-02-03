package org.ujmp.gui.table;

import java.util.Enumeration;

import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import org.ujmp.core.util.MathUtil;

public class DefaultTableColumnModel64 extends DefaultTableColumnModel implements TableColumnModel64 {
	private static final long serialVersionUID = 6012896374236252508L;

	public static final int COLUMNWIDTH = 50;

	private final TableModel64 tableModel64;

	public DefaultTableColumnModel64(TableModel64 m) {
		this.tableModel64 = m;
	}

	public TableColumn64 getColumn(int columnIndex) {
		TableColumn64 tableColumn = new TableColumn64(columnIndex, COLUMNWIDTH);
		return tableColumn;
	}

	public ListSelectionModel64 getSelectionModel() {
		if (selectionModel instanceof ListSelectionModel64) {
			return (ListSelectionModel64) selectionModel;
		} else {
			return null;
		}
	}

	public void addColumnModelListener(TableColumnModelListener x) {
		if (x instanceof TableColumnModelListener64) {
			super.addColumnModelListener(x);
		} else {
			throw new IllegalArgumentException("use TableColumnModelListener64");
		}
	}

	public void removeColumnModelListener(TableColumnModelListener x) {
		if (x instanceof TableColumnModelListener64) {
			super.removeColumnModelListener(x);
		} else {
			throw new IllegalArgumentException("use TableColumnModelListener64");
		}
	}

	public long getColumnCount64() {
		return tableModel64.getColumnCount64();
	}

	public int getColumnCount() {
		return MathUtil.longToInt(getColumnCount64());
	}

	public void addColumn(TableColumn64 aColumn) {
		throw new UnsupportedOperationException("not implemented");
	}

	public void addColumn(TableColumn aColumn) {
		throw new UnsupportedOperationException("not implemented");
	}

	public void removeColumn(TableColumn64 column) {
		throw new UnsupportedOperationException("not implemented");
	}

	public void moveColumn(long columnIndex, long newIndex) {
		throw new UnsupportedOperationException("not implemented");
	}

	public Enumeration<TableColumn64> getColumns64() {
		throw new UnsupportedOperationException("not implemented");
	}

	public Enumeration<TableColumn> getColumns() {
		return new ConstantTableColumnEnumeration(tableModel64, COLUMNWIDTH);
	}

	public long getColumnIndex64(Object columnIdentifier) {
		throw new UnsupportedOperationException("not implemented");
	}

	public TableColumn64 getColumn(long columnIndex) {
		throw new UnsupportedOperationException("not implemented");
	}

	public long[] getSelectedColumns64() {
		throw new UnsupportedOperationException("not implemented");
	}

	public long getSelectedColumnCount64() {
		throw new UnsupportedOperationException("not implemented");
	}

	public void setSelectionModel(ListSelectionModel64 newModel) {
		super.setSelectionModel(newModel);
	}

	public void setSelectionModel(ListSelectionModel newModel) {
		if (newModel instanceof ListSelectionModel64) {
			setSelectionModel((ListSelectionModel64) newModel);
		} else {
			throw new IllegalArgumentException("use ListSelectionModel64");
		}
	}

	public void addColumnModelListener(TableColumnModelListener64 x) {
		throw new UnsupportedOperationException("not implemented");
	}

	public void removeColumnModelListener(TableColumnModelListener64 x) {
		throw new UnsupportedOperationException("not implemented");
	}

	// public Enumeration<TableColumn> getColumns() {
	// return new TableColumnEnumeration(tableModel64, COLUMNWIDTH);
	// }

	protected ListSelectionModel64 createSelectionModel() {
		return new FastListSelectionModel64();
	}

	public void removeColumn(TableColumn column) {
		if (column instanceof TableColumn64) {
			removeColumn((TableColumn64) column);
		} else {
			throw new IllegalArgumentException("use TableColumn64");
		}
	}

	public void moveColumn(int columnIndex, int newIndex) {
		moveColumn((long) columnIndex, (long) newIndex);
	}

	public void setColumnMargin(int newMargin) {
		if (newMargin != columnMargin) {
			columnMargin = newMargin;
			fireColumnMarginChanged();
		}
	}

	protected void fireColumnMarginChanged() {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == TableColumnModelListener64.class) {

				if (changeEvent == null) {
					changeEvent = new ChangeEvent(this);
				}
				((TableColumnModelListener64) listeners[i + 1]).columnMarginChanged(changeEvent);
			} else if (listeners[i] == TableColumnModelListener.class) {

				if (changeEvent == null) {
					changeEvent = new ChangeEvent(this);
				}
				((TableColumnModelListener) listeners[i + 1]).columnMarginChanged(changeEvent);
			}
		}
	}

	public int getColumnIndex(Object columnIdentifier) {
		return MathUtil.longToInt(getColumnIndex64(columnIdentifier));
	}

	public int getColumnIndexAtX(int xPosition) {
		return MathUtil.longToInt(getColumnIndexAtX((long) xPosition));
	}

	public long getColumnIndexAtX(long xPosition) {
		if (xPosition < 0 || xPosition > COLUMNWIDTH * getColumnCount64()) {
			return -1;
		} else {
			return xPosition / COLUMNWIDTH;
		}
	}

	public int getTotalColumnWidth() {
		if (totalColumnWidth == -1) {
			recalcWidthCache();
		}
		return totalColumnWidth;
	}

	protected void recalcWidthCache() {
		totalColumnWidth = MathUtil.longToInt(getColumnCount64() * COLUMNWIDTH);
	}

	public void setColumnSelectionAllowed(boolean flag) {
		columnSelectionAllowed = flag;
	}

	public boolean getColumnSelectionAllowed() {
		return columnSelectionAllowed;
	}

}

class ConstantTableColumnEnumeration implements Enumeration<TableColumn> {

	private final TableColumn64 tableColumn64;
	private final TableModel64 tableModel64;
	private long index = 0;

	public ConstantTableColumnEnumeration(TableModel64 tableModel64, int columnWidth) {
		this.tableModel64 = tableModel64;
		this.tableColumn64 = new TableColumn64(0, columnWidth);
	}

	public boolean hasMoreElements() {
		return index < tableModel64.getColumnCount();
	}

	public TableColumn nextElement() {
		tableColumn64.setModelIndex(index++);
		return tableColumn64;
	}
}
