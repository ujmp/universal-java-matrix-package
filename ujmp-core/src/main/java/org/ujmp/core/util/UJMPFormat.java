/*
 * Copyright (C) 2008-2015 by Holger Arndt
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

package org.ujmp.core.util;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.FieldPosition;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sun.istack.internal.NotNull;
import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.bigdecimalmatrix.BaseBigDecimalMatrix;
import org.ujmp.core.mapmatrix.MapMatrix;

public class UJMPFormat extends Format {
    private static final long serialVersionUID = -557618747324763226L;

    private static UJMPFormat multiLineInstance = new UJMPFormat(true, 10, true);

    private static UJMPFormat mapInstance = new UJMPFormat(true, 30, true);

    private static UJMPFormat singleLineInstance = new UJMPFormat(false, 100, false);

    private NumberFormat integerFormat = null;

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
        integerFormat = new DecimalFormat("0", symbols);
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

    public static final UJMPFormat getMapInstance() {
        return mapInstance;
    }

    public static final UJMPFormat getSingleLineInstance() {
        return singleLineInstance;
    }

    @Override
    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
        return format(obj, toAppendTo, pos, width, usePadding);
    }

    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos, int width, boolean usePadding) {
        if (obj == null) {
            if (usePadding) {
                pad(toAppendTo, ' ', width);
            }
            return toAppendTo;
        } else if (obj instanceof String) {
            return formatString((String) obj, toAppendTo, width, usePadding);
        } else if (obj instanceof Matrix) {
            return formatMatrix((Matrix) obj, toAppendTo, pos, width, usePadding);
        } else if (obj instanceof Date) {
            return formatDate((Date) obj, toAppendTo, pos, width, usePadding);
        } else if (obj instanceof byte[]) {
            return formatByteArray((byte[]) obj, toAppendTo, width, usePadding);
        } else if (obj instanceof Number) {
            return formatNumber((Number) obj, toAppendTo, width, usePadding);
        } else {
            return formatString(String.valueOf(obj), toAppendTo, width, usePadding);
        }
    }

    private StringBuffer formatMatrix(Matrix obj, StringBuffer toAppendTo, FieldPosition pos, int width, boolean usePadding) {
        if (obj == null) {
            if (usePadding) {
                pad(toAppendTo, ' ', width);
            }
            return toAppendTo;
        } else if (multiLine) {
            return formatMultiLine(obj, toAppendTo, pos);
        } else {
            if (obj.getLabel() != null) {
                toAppendTo.append("[").append(obj.getLabel()).append("]");
            } else {
                toAppendTo.append("[Matrix]");
            }
            return toAppendTo;
        }
    }

    private StringBuffer formatDate(Date obj, StringBuffer toAppendTo, FieldPosition pos, int width, boolean usePadding) {
        int length = toAppendTo.length();
        toAppendTo = dateFormat.format(obj, toAppendTo, pos);
        length = width - (toAppendTo.length() - length);
        if (usePadding) {
            pad(toAppendTo, ' ', length);
        }
        return toAppendTo;
    }

    private StringBuffer formatByteArray(byte[] obj, StringBuffer toAppendTo, int width, boolean usePadding) {
        for (byte anObj : obj) {
            toAppendTo.append(anObj);
        }
        return toAppendTo;
    }

    private static StringBuffer pad(StringBuffer s, char c, int count) {
        for (int i = 0; i < count; i++) {
            s.append(c);
        }
        return s;
    }

    private static StringBuffer formatString(String obj, StringBuffer toAppendTo, int width, boolean usePadding) {
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

    private StringBuffer formatNumber(Number obj, StringBuffer toAppendTo, int width, boolean usePadding) {
        String s;
        if (obj instanceof Integer || obj instanceof Long || obj instanceof BigInteger) {
            s = integerFormat.format(obj);
        } else {
            s = defaultNumberFormat.format(obj);
        }
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
        long maxRows = UJMPSettings.getInstance().getMaxRowsToPrint();
        long maxColumns = UJMPSettings.getInstance().getMaxColumnsToPrint();

        final String EOL = System.getProperty("line.separator");

        long rowCount = m.getRowCount();
        long columnCount = m.getColumnCount();
        long[] cursor = new long[m.getDimensionCount()];

        if (m.getDimensionCount() > 2) {
            toAppendTo.append(m.getDimensionCount());
            toAppendTo.append("D-Matrix [");
            toAppendTo.append(Coordinates.toString("x", m.getSize()));
            toAppendTo.append("]: only two dimensions are printed");
            toAppendTo.append(EOL);
        }

        if (m.getMetaData() != null) {
            formatString(m.getLabel(), toAppendTo, width, usePadding);
            toAppendTo.append("   ");
            for (int col = 0; col < columnCount && col < maxColumns; col++) {
                formatString(m.getColumnLabel(col), toAppendTo, width, usePadding);
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
            if (m.getMetaData() != null) {
                formatString(m.getRowLabel(cursor[Matrix.ROW]), toAppendTo, width, usePadding);
                toAppendTo.append(" | ");
            }
            for (cursor[Matrix.COLUMN] = 0; cursor[Matrix.COLUMN] < columnCount
                    && cursor[Matrix.COLUMN] < maxColumns; cursor[Matrix.COLUMN]++) {
                Object o = m.getAsObject(cursor);
                if (o == null && m instanceof BaseBigDecimalMatrix) {
                    toAppendTo = formatNumber(Double.NaN, toAppendTo, width, usePadding);
                } else if (o instanceof Matrix) {
                    toAppendTo.append("[Matrix]");
                } else {
                    if (cursor[Matrix.COLUMN] == 0 && m.getColumnCount() == 1) {
                        toAppendTo = format(o, toAppendTo, pos, 80, usePadding);
                    } else if (cursor[Matrix.COLUMN] == 1 && m instanceof MapMatrix) {
                        toAppendTo = format(o, toAppendTo, pos, 60, usePadding);
                    } else {
                        toAppendTo = format(o, toAppendTo, pos, width, usePadding);
                    }
                }
                if (cursor[Matrix.COLUMN] < columnCount - 1) {
                    toAppendTo.append(' ');
                }
            }
            toAppendTo.append(EOL);
        }

        if (rowCount == 0 || columnCount == 0)

        {
            toAppendTo.append("[").append(rowCount).append("x").append(columnCount).append("]").append(EOL);
        } else if (rowCount > UJMPSettings.getInstance().

                getMaxRowsToPrint()

                || columnCount > UJMPSettings.getInstance().

                getMaxColumnsToPrint()

                )

        {
            toAppendTo.append("[...]");
        }

        return toAppendTo;
    }

    @Override
    public Object parseObject(String source, ParsePosition pos) {
        throw new RuntimeException("not implemented");
    }
}
