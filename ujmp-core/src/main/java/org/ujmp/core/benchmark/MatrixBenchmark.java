package org.ujmp.core.benchmark;

import org.ujmp.core.doublematrix.DoubleMatrix2D;

public interface MatrixBenchmark {

	public BenchmarkConfig getConfig();

	public Class<? extends DoubleMatrix2D> getMatrixClass();

	public String getMatrixLabel();

	public void run();
}
