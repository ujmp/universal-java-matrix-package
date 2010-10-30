package org.ujmp.core.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.FieldPosition;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.bigdecimalmatrix.BigDecimalMatrix;
import org.ujmp.core.datematrix.DateMatrix;
import org.ujmp.core.exceptions.MatrixException;

public class UJMPFormat extends Format {
	private static final long serialVersionUID = -557618747324763226L;

	private static UJMPFormat multiLineInstance = new UJMPFormat(true, 10, true);

	private static UJMPFormat singleLineInstance = new UJMPFormat(false, 100, false);

	private NumberFormat defaultNumberFormat = null;

	private NumberFormat exponentialNumberFormat = null;

	private DateFormat dateFormat = null;

	private boolean multiLine = true;

	private boolean usePadding = false;

	private int width = 10;

	public UJMPFormat(boolean multiLine, int width, boolean usePadding) {
		DecimalFormatSymbols symbols = new DecimalFormatSymbols(UJMPSettings.getLocale());
		symbols.setNaN("NaN");
		symbols.setInfinity("Inf");
		defaultNumberFormat = new DecimalFormat("0.0000", symbols);
		exponentialNumberFormat = new DecimalFormat("0.000E000", symbols);
		dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		this.multiLine = multiLine;
		this.width = width;
		this.usePadding = usePadding;
	}

	public static final UJMPFormat getMultiLineInstance() {
		return multiLineInstance;
	}

	public static final UJMPFormat getSingleLineInstance() {
		return singleLineInstance;
	}

	@Override
	public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
		if (obj == null) {
			if (usePadding) {
				pad(toAppendTo, ' ', width);
			}
			return toAppendTo;
		} else if (obj instanceof String) {
			return format((String) obj, toAppendTo, pos);
		} else if (obj instanceof Matrix) {
			return format((Matrix) obj, toAppendTo, pos);
		} else if (obj instanceof Date) {
			return format((Date) obj, toAppendTo, pos);
		} else if (obj instanceof Number) {
			return format((Number) obj, toAppendTo, pos);
		} else {
			return format(String.valueOf(obj), toAppendTo, pos);
		}
	}

	private StringBuffer format(Matrix obj, StringBuffer toAppendTo, FieldPosition pos) {
		if (obj == null) {
			if (usePadding) {
				pad(toAppendTo, ' ', width);
			}
			return toAppendTo;
		} else if (multiLine) {
			return formatMultiLine(obj, toAppendTo, pos);
		} else {
			if (obj.getLabel() != null) {
				toAppendTo.append("[" + obj.getLabel() + "]");
			} else {
				toAppendTo.append("[Matrix]");
			}
			return toAppendTo;
		}
	}

	private StringBuffer format(Date obj, StringBuffer toAppendTo, FieldPosition pos) {
		int length = toAppendTo.length();
		toAppendTo = dateFormat.format(obj, toAppendTo, pos);
		length = width - (toAppendTo.length() - length);
		if (usePadding) {
			pad(toAppendTo, ' ', length);
		}
		return toAppendTo;
	}

	private StringBuffer pad(StringBuffer s, char c, int count) {
		for (int i = 0; i < count; i++) {
			s.append(c);
		}
		return s;
	}

	private StringBuffer format(String obj, StringBuffer toAppendTo, FieldPosition pos) {
		if (obj != null && obj.length() > width) {
			obj = obj.substring(0, width);
		}
		if (obj != null) {
			toAppendTo.append(obj);
			if (usePadding) {
				pad(toAppendTo, ' ', width - obj.length());
			}
			return toAppendTo;
		} else {
			if (usePadding) {
				pad(toAppendTo, ' ', width);
			}
			return toAppendTo;
		}
	}

	private StringBuffer format(Number obj, StringBuffer toAppendTo, FieldPosition pos) {
		String s = defaultNumberFormat.format(obj);
		if (s.length() > width) {
			s = exponentialNumberFormat.format(obj);
		}
		if (usePadding) {
			pad(toAppendTo, ' ', width - s.length());
		}
		toAppendTo.append(s);
		return toAppendTo;
	}

	private StringBuffer formatMultiLine(Matrix m, StringBuffer toAppendTo, FieldPosition pos) {
		long maxRows = UJMPSettings.getMaxRowsToPrint();
		long maxColumns = UJMPSettings.getMaxColumnsToPrint();

		final String EOL = System.getProperty("line.separator");

		long rowCount = m.getRowCount();
		long columnCount = m.getColumnCount();
		long[] cursor = new long[m.getDimensionCount()];

		if (m.getDimensionCount() > 2) {
			toAppendTo.append(m.getDimensionCount());
			toAppendTo.append("D-Matrix [");
			toAppendTo.append(Coordinates.toString('x', m.getSize()));
			toAppendTo.append("]: only two dimensions are printed");
			toAppendTo.append(EOL);
		}

		if (m.getAnnotation() != null) {
			format(m.getLabel(), toAppendTo, pos);
			toAppendTo.append("   ");
			for (int col = 0; col < columnCount && col < maxColumns; col++) {
				format(m.getColumnLabel(col), toAppendTo, pos);
				if (col < columnCount - 1) {
					toAppendTo.append(' ');
				}
			}
			toAppendTo.append(EOL);
			pad(toAppendTo, '=', width);
			toAppendTo.append("   ");
			for (int col = 0; col < columnCount && col < maxColumns; col++) {
				pad(toAppendTo, '-', width);
				if (col < columnCount - 1) {
					toAppendTo.append(' ');
				}
			}
			toAppendTo.append(EOL);
		}

		for (cursor[Matrix.ROW] = 0; cursor[Matrix.ROW] < rowCount && cursor[Matrix.ROW] < maxRows; cursor[Matrix.ROW]++) {
			if (m.getAnnotation() != null) {
				format(m.getRowLabel(cursor[Matrix.ROW]), toAppendTo, pos);
				toAppendTo.append(" | ");
			}
			for (cursor[Matrix.COLUMN] = 0; cursor[Matrix.COLUMN] < columnCount
					&& cursor[Matrix.COLUMN] < maxColumns; cursor[Matrix.COLUMN]++) {
				Object o = m.getAsObject(cursor);
				if (o == null && (m instanceof BigDecimalMatrix || m instanceof DateMatrix)) {
					toAppendTo = format(Double.NaN, toAppendTo, pos);
				} else {
					toAppendTo = format(o, toAppendTo, pos);
				}
				if (cursor[Matrix.COLUMN] < columnCount - 1) {
					toAppendTo.append(' ');
				}
			}
			toAppendTo.append(EOL);
		}

		if (rowCount == 0 || columnCount == 0) {
			toAppendTo.append("[" + rowCount + "x" + columnCount + "]" + EOL);
		} else if (rowCount > UJMPSettings.getMaxRowsToPrint()
				|| columnCount > UJMPSettings.getMaxColumnsToPrint()) {
			toAppendTo.append("[...]");
		}

		return toAppendTo;
	}

	@Override
	public Object parseObject(String source, ParsePosition pos) {
		throw new MatrixException("not implemented");
	}
}
