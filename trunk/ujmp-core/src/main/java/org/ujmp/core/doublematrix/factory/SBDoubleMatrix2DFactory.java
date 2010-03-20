package org.ujmp.core.doublematrix.factory;

import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.impl.BlockDenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.impl.BlockOrder;
import org.ujmp.core.exceptions.MatrixException;

public class SBDoubleMatrix2DFactory extends AbstractDoubleMatrix2DFactory {

	private static final long serialVersionUID = -3135863010482586727L;

	public DenseDoubleMatrix2D dense(final long rows, final long columns) throws MatrixException {
		return new BlockDenseDoubleMatrix2D(rows, columns, BlockOrder.ROWMAJOR);
	}

}
