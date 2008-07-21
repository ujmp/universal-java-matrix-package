package org.ujmp.jackcess;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.matrices.stubs.AbstractDenseObjectMatrix2D;

import com.healthmarketscience.jackcess.Column;
import com.healthmarketscience.jackcess.Cursor;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.Table;

public class DenseJackcessMatrix2D extends AbstractDenseObjectMatrix2D {
	private static final long serialVersionUID = -6342663672866315180L;

	private Database database = null;

	private Table table = null;

	private List<Column> columns = null;

	Cursor cursor = null;

	public DenseJackcessMatrix2D(File file, String tablename) throws IOException {
		database = Database.open(file);
		table = database.getTable(tablename);
		columns = table.getColumns();
		cursor = Cursor.createCursor(table);
	}

	@Override
	public synchronized Object getObject(long row, long column) throws MatrixException {
		try {
			Column c = columns.get((int) column);
			cursor.reset();
			cursor.moveNextRows((int) row + 1);
			return cursor.getCurrentRowValue(c);
		} catch (IOException e) {
			throw new MatrixException(e);
		}
	}

	@Override
	public void setObject(Object value, long row, long column) {
	}

	@Override
	public long[] getSize() {
		return new long[] { table.getRowCount(), table.getColumnCount() };
	}
}
