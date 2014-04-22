package org.ujmp.core.bigdecimalmatrix;

import java.math.BigDecimal;

import org.ujmp.core.genericmatrix.GenericMatrix;

public interface BigDecimalMatrix extends DenseBigDecimalMatrix2D, DenseBigDecimalMatrixMultiD,
		SparseBigDecimalMatrix2D, SparseBigDecimalMatrixMultiD, GenericMatrix<BigDecimal> {

}
