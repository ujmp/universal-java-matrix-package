/*
 * Copyright (C) 2008-2009 Holger Arndt, A. Naegele and M. Bundschus
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

package org.ujmp.complete.benchmark;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ujmp.colt.benchmark.ColtDenseDoubleMatrix2DBenchmark;
import org.ujmp.colt.benchmark.ColtSparseDoubleMatrix2DBenchmark;
import org.ujmp.commonsmath.benchmark.CommonsMathDenseDoubleMatrix2DBenchmark;
import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.benchmark.AbstractMatrix2DBenchmark;
import org.ujmp.core.benchmark.ArrayDenseDoubleMatrix2DBenchmark;
import org.ujmp.core.benchmark.DefaultDenseDoubleMatrix2DBenchmark;
import org.ujmp.core.benchmark.DefaultSparseDoubleMatrixBenchmark;
import org.ujmp.core.objectmatrix.EmptyMatrix;
import org.ujmp.jama.benchmark.JamaDenseDoubleMatrix2DBenchmark;
import org.ujmp.jmatrices.benchmark.JMatricesDenseDoubleMatrix2DBenchmark;
import org.ujmp.mtj.benchmark.MTJDenseDoubleMatrix2DBenchmark;
import org.ujmp.parallelcolt.benchmark.ParallelColtDenseDoubleMatrix2DBenchmark;
import org.ujmp.parallelcolt.benchmark.ParallelColtSparseDoubleMatrix2DBenchmark;
import org.ujmp.sst.benchmark.SSTDenseDoubleMatrixBenchmark;
import org.ujmp.vecmath.benchmark.VecMathDenseDoubleMatrix2DBenchmark;

public class MatrixBenchmark {

	public static List<AbstractMatrix2DBenchmark> getDenseBenchmarks() {
		List<AbstractMatrix2DBenchmark> list = new ArrayList<AbstractMatrix2DBenchmark>();
		list.add(new DefaultDenseDoubleMatrix2DBenchmark());
		list.add(new MTJDenseDoubleMatrix2DBenchmark());
		list.add(new CommonsMathDenseDoubleMatrix2DBenchmark());
		list.add(new JMatricesDenseDoubleMatrix2DBenchmark());
		list.add(new ArrayDenseDoubleMatrix2DBenchmark());
		list.add(new SSTDenseDoubleMatrixBenchmark());
		list.add(new ColtDenseDoubleMatrix2DBenchmark());
		list.add(new VecMathDenseDoubleMatrix2DBenchmark());
		list.add(new ParallelColtDenseDoubleMatrix2DBenchmark());
		list.add(new JamaDenseDoubleMatrix2DBenchmark());
		Collections.reverse(list);
		return list;
	}

	public static List<AbstractMatrix2DBenchmark> getSparseBenchmarks() {
		List<AbstractMatrix2DBenchmark> list = new ArrayList<AbstractMatrix2DBenchmark>();
		list.add(new ParallelColtSparseDoubleMatrix2DBenchmark());
		list.add(new DefaultSparseDoubleMatrixBenchmark());
		list.add(new ColtSparseDoubleMatrix2DBenchmark());
		Collections.reverse(list);
		return list;
	}

	public static void main(String[] args) throws Exception {
		List<AbstractMatrix2DBenchmark> benchmarks = getDenseBenchmarks();

		List<Matrix> results = new ArrayList<Matrix>();
		for (int i = 0; i < 10; i++) {
			results.add(MatrixFactory.emptyMatrix());
		}

		for (int j = 0; j < benchmarks.size(); j++) {
			AbstractMatrix2DBenchmark benchmark = benchmarks.get(j);
			List<Matrix> l = benchmark.run();
			for (int i = 0; i < l.size(); i++) {
				Matrix m = l.get(i).appendVertically(results.get(i));
				m.setLabel(l.get(i).getLabel());
				results.set(i, m);
			}
		}

		for (int i = 0; i < results.size(); i++) {
			Matrix m = results.get(i);
			for (int j = 0; j < benchmarks.size(); j++) {
				m.setRowLabel(benchmarks.size() - 1 - j, benchmarks.get(j).getClass()
						.getSimpleName());
			}
		}

		for (Matrix m : results) {
			if (m != null && !(m instanceof EmptyMatrix)) {
				m.showGUI();
			}
		}

	}
}
