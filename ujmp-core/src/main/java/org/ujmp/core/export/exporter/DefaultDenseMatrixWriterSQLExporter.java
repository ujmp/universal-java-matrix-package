/*
 * Copyright (C) 2008-2014 by Holger Arndt
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

package org.ujmp.core.export.exporter;

import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.ujmp.core.DenseMatrix;
import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.DoubleMatrix;
import org.ujmp.core.enums.DBType;
import org.ujmp.core.export.format.MatrixSQLExportFormat;
import org.ujmp.core.floatmatrix.FloatMatrix;
import org.ujmp.core.intmatrix.IntMatrix;
import org.ujmp.core.longmatrix.LongMatrix;
import org.ujmp.core.stringmatrix.StringMatrix;

public class DefaultDenseMatrixWriterSQLExporter extends AbstractDenseMatrixWriterExporter
		implements MatrixSQLExportFormat {

	public DefaultDenseMatrixWriterSQLExporter(DenseMatrix matrix, Writer writer) {
		super(matrix, writer);
	}

	public void asSQL(DBType db, String databaseName, String tableName) throws IOException {
		final String EOL = System.getProperty("line.separator");
		final Matrix matrix = getMatrix();
		final Writer writer = getWriter();

		writer.write("CREATE DATABASE IF NOT EXISTS `" + databaseName + "`;" + EOL);
		writer.write("USE `" + databaseName + "`;" + EOL);

		writer.write("CREATE TABLE IF NOT EXISTS `" + tableName + "` (");
		for (int c = 0; c < matrix.getColumnCount(); c++) {
			writer.write("`" + getColumnName(matrix, c) + "` ");
			writer.write(getColumnType(db, matrix, c));
			if (c < matrix.getColumnCount() - 1) {
				writer.write(", ");
			}
		}
		writer.write(");" + EOL);

		writer.write("INSERT IGNORE INTO `" + tableName + "` (");
		for (int c = 0; c < matrix.getColumnCount(); c++) {
			writer.write("`" + getColumnName(matrix, c) + "`");
			if (c < matrix.getColumnCount() - 1) {
				writer.write(", ");
			}
		}

		writer.write(") VALUES ");

		for (int r = 0; r < matrix.getRowCount(); r++) {
			writer.write("(");
			for (int c = 0; c < matrix.getColumnCount(); c++) {
				writer.write(encodeData(matrix.getAsObject(r, c)));
				if (c < matrix.getColumnCount() - 1) {
					writer.write(", ");
				}
			}
			if (r < matrix.getRowCount() - 1) {
				writer.write("), ");
			}
		}

		writer.write(");" + EOL);

		writer.close();
	}

	private static String encodeData(Object o) {
		if (o == null) {
			return "null";
		} else if (o instanceof Number) {
			return o.toString();
		} else {
			return "'" + o + "'";
		}
	}

	private static final String getColumnName(Matrix matrix, long column) {
		String columnName = matrix.getColumnLabel(column);
		if (columnName == null || columnName.length() == 0) {
			columnName = "column " + column;
		}
		return columnName;
	}

	private static final String getIntType(DBType db) {
		if (db == DBType.MySQL) {
			return "INT";
		} else {
			return "TEXT";
		}
	}

	private static final String getLongType(DBType db) {
		if (db == DBType.MySQL) {
			return "BIGINT";
		} else {
			return "TEXT";
		}
	}

	private static final String getFloatType(DBType db) {
		if (db == DBType.MySQL) {
			return "DOUBLE";
		} else {
			return "TEXT";
		}
	}

	private static final String getDoubleType(DBType db) {
		if (db == DBType.MySQL) {
			return "DOUBLE";
		} else {
			return "TEXT";
		}
	}

	private static final String getByteArrayType(DBType db) {
		if (db == DBType.MySQL) {
			return "BLOB";
		} else {
			return "TEXT";
		}
	}

	private static final String getBigIntType(DBType db) {
		if (db == DBType.MySQL) {
			return "BIGINT";
		} else {
			return "TEXT";
		}
	}

	private static final String getBigDecimalType(DBType db) {
		if (db == DBType.MySQL) {
			return "DOUBLE";
		} else {
			return "TEXT";
		}
	}

	private static final String getStringType(DBType db) {
		if (db == DBType.MySQL) {
			return "TEXT";
		} else {
			return "TEXT";
		}
	}

	private static final String getColumnType(DBType db, Matrix matrix, long column) {
		if (matrix instanceof StringMatrix) {
			return getStringType(db);
		} else if (matrix instanceof IntMatrix) {
			return getIntType(db);
		} else if (matrix instanceof LongMatrix) {
			return getLongType(db);
		} else if (matrix instanceof FloatMatrix) {
			return getFloatType(db);
		} else if (matrix instanceof DoubleMatrix) {
			return getDoubleType(db);
		} else {
			for (int r = 0; r < matrix.getRowCount(); r++) {
				Object o = matrix.getAsObject(r, column);
				if (o instanceof String) {
					return getStringType(db);
				} else if (o instanceof Integer) {
					return getIntType(db);
				} else if (o instanceof Long) {
					return getLongType(db);
				} else if (o instanceof Float) {
					return getFloatType(db);
				} else if (o instanceof Double) {
					return getDoubleType(db);
				} else if (o instanceof BigInteger) {
					return getBigIntType(db);
				} else if (o instanceof BigDecimal) {
					return getBigDecimalType(db);
				} else if (o instanceof byte[]) {
					return getByteArrayType(db);
				}
			}
		}
		return getStringType(db);
	}

}
