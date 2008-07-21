package org.ujmp.jackcess;

import java.io.File;
import java.io.IOException;

import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.matrices.basic.DefaultDenseObjectMatrix2D;

public class ImportMatrixMDB {

	public static final Matrix fromFile(File file, Object... parameters) throws MatrixException,
			IOException {
		Matrix m = LinkMatrixMDB.toFile(file, parameters);
		return new DefaultDenseObjectMatrix2D(m);
	}

}
