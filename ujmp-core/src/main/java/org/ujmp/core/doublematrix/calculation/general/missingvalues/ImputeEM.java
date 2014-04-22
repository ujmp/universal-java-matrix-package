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

package org.ujmp.core.doublematrix.calculation.general.missingvalues;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.calculation.AbstractDoubleCalculation;
import org.ujmp.core.doublematrix.calculation.general.missingvalues.Impute.ImputationMethod;
import org.ujmp.core.util.MathUtil;

public class ImputeEM extends AbstractDoubleCalculation {
	private static final long serialVersionUID = -1272010036598212696L;

	private Matrix bestGuess = null;

	private Matrix imputed = null;

	private double delta = 1e-6;

	private final double decay = 0.66;

	public ImputeEM(Matrix matrix) {
		super(matrix);
	}

	public ImputeEM(Matrix matrix, Matrix firstGuess) {
		super(matrix);
		this.bestGuess = firstGuess;
	}

	public ImputeEM(Matrix matrix, Matrix firstGuess, double delta) {
		super(matrix);
		this.bestGuess = firstGuess;
		this.delta = delta;
	}

	public double getDouble(long... coordinates) {
		if (imputed == null) {
			createMatrix();
		}
		double v = getSource().getAsDouble(coordinates);
		if (MathUtil.isNaNOrInfinite(v)) {
			return imputed.getAsDouble(coordinates);
		} else {
			return v;
		}
	}

	private void createMatrix() {
		try {
			ExecutorService executor = Executors.newFixedThreadPool(1);

			Matrix x = getSource();

			double valueCount = x.getValueCount();
			long missingCount = (long) x.countMissing(Ret.NEW, Matrix.ALL).getEuklideanValue();
			double percent = ((int) Math.round((missingCount * 1000.0 / valueCount))) / 10.0;
			System.out.println("missing values: " + missingCount + " (" + percent + "%)");
			System.out.println("============================================");

			if (bestGuess == null) {
				bestGuess = getSource().impute(Ret.NEW, ImputationMethod.RowMean);
			}

			int run = 0;

			while (true) {

				System.out.println("Iteration " + run++);

				List<Future<Long>> futures = new ArrayList<Future<Long>>();

				imputed = Matrix.Factory.zeros(x.getSize());

				long t0 = System.currentTimeMillis();

				for (long c = 0; c < x.getColumnCount(); c++) {
					futures.add(executor.submit(new PredictColumn(c)));
				}

				for (Future<Long> f : futures) {
					Long completedCols = f.get();
					long elapsedTime = System.currentTimeMillis() - t0;
					long remainingCols = x.getColumnCount() - completedCols;
					double colsPerMillisecond = (double) (completedCols + 1) / (double) elapsedTime;
					long remainingTime = (long) (remainingCols / colsPerMillisecond / 1000.0);
					System.out.println((completedCols * 1000 / x.getColumnCount() / 10.0)
							+ "% completed (" + remainingTime + " seconds remaining)");
				}

				double d = imputed.euklideanDistanceTo(bestGuess, true) / missingCount;
				System.out.println("delta: " + d);
				System.out.println("============================================");

				bestGuess = bestGuess.times(decay).plus(imputed.times(1 - decay));

				if (d < delta) {
					break;
				}

			}

			executor.shutdown();

			imputed = bestGuess;

			if (imputed.containsMissingValues()) {
				throw new RuntimeException("Matrix has still missing values after imputation");
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	class PredictColumn implements Callable<Long> {

		long column = 0;

		public PredictColumn(long column) {
			this.column = column;
		}

		public Long call() throws Exception {
			Matrix newColumn = replaceInColumn(getSource(), bestGuess, column);
			for (int r = 0; r < newColumn.getRowCount(); r++) {
				imputed.setAsDouble(newColumn.getAsDouble(r, 0), r, column);
			}
			return column;
		}

	}

	private static Matrix replaceInColumn(Matrix original, Matrix firstGuess, long column) {

		Matrix x = firstGuess.deleteColumns(Ret.NEW, column);
		Matrix y = original.selectColumns(Ret.NEW, column);

		List<Long> missingRows = new ArrayList<Long>();
		for (long i = y.getRowCount(); --i >= 0;) {
			double v = y.getAsDouble(i, 0);
			if (MathUtil.isNaNOrInfinite(v)) {
				missingRows.add(i);
			}
		}

		if (missingRows.isEmpty()) {
			return y;
		}

		Matrix xdel = x.deleteRows(Ret.NEW, missingRows);
		DenseDoubleMatrix2D bias1 = DenseDoubleMatrix2D.Factory.ones(xdel.getRowCount(), 1);
		Matrix xtrain = Matrix.Factory.horCat(xdel, bias1);
		Matrix ytrain = y.deleteRows(Ret.NEW, missingRows);

		Matrix xinv = xtrain.pinv();
		Matrix b = xinv.mtimes(ytrain);
		DenseDoubleMatrix2D bias2 = DenseDoubleMatrix2D.Factory.ones(x.getRowCount(), 1);
		Matrix yPredicted = Matrix.Factory.horCat(x, bias2).mtimes(b);

		// set non-missing values back to original values
		for (int row = 0; row < y.getRowCount(); row++) {
			double v = y.getAsDouble(row, 0);
			if (!Double.isNaN(v)) {
				yPredicted.setAsDouble(v, row, 0);
			}
		}

		return yPredicted;
	}

}
