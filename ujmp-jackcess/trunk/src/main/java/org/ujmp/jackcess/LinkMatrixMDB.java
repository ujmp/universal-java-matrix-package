package org.ujmp.jackcess;

import java.io.File;
import java.io.IOException;

import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;

import com.healthmarketscience.jackcess.Database;

public class LinkMatrixMDB {

	public static final Matrix toFile(File file, Object... parameters) throws MatrixException,
			IOException {
		String tablename = null;
		if (parameters.length != 0) {
			tablename = parameters[0].toString();
		} else {
			Database db = Database.open(file);
			tablename = db.getTableNames().iterator().next();
			db.close();
		}
		return new DenseJackcessMatrix2D(file, tablename);
	}

}
