/*
 * Copyright (C) 2008-2011 by Holger Arndt
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

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.impl.DenseFileMatrix;
import org.ujmp.core.interfaces.Erasable;
import org.ujmp.core.objectmatrix.impl.SerializedSparseObjectMatrix;
import org.ujmp.core.util.MathUtil;
import org.ujmp.ehcache.EhcacheSparseObjectMatrix;
import org.ujmp.jdbc.matrix.JDBCSparseObjectMatrix;
import org.ujmp.lucene.LuceneSparseObjectMatrix;

public class VeryLargeMatrixBenchmark {

	public static final int MATRIXSIZE = 10000;

	public static final int MAXTIME = 30; // s

	public static final int BURNINTIME = 10; // s

	public static final int MAXENTRYCOUNT = 100000;

	public static void main(String args[]) throws Exception {
		System.out.println("Disk Matrix Benchmark");
		System.out.println("=====================");
		System.out.println("This benchmark creates a very large matrix on disk");
		System.out.println("and measures the time for reading and writing data");
		System.out.println();
		System.out.println("Matrix size: " + MATRIXSIZE + "x" + MATRIXSIZE);
		System.out.println("Main Memory: " + (Runtime.getRuntime().maxMemory() / 1000000) + "M");
		System.out.println();

		List<Matrix> matricesToTest = new LinkedList<Matrix>();
		matricesToTest.add(new DenseFileMatrix(MATRIXSIZE, MATRIXSIZE));
		matricesToTest.add(new SerializedSparseObjectMatrix(MATRIXSIZE, MATRIXSIZE));
		matricesToTest.add(new LuceneSparseObjectMatrix(MATRIXSIZE, MATRIXSIZE));
		matricesToTest.add(new JDBCSparseObjectMatrix(MATRIXSIZE, MATRIXSIZE));
		matricesToTest.add(new EhcacheSparseObjectMatrix(MATRIXSIZE, MATRIXSIZE));

		for (Matrix m : matricesToTest) {
			System.out.println(m.getClass().getSimpleName());

			// run the performance test
			runPerformanceTest(m);

			// clean up afterwards
			if (m instanceof Erasable) {
				((Erasable) m).erase();
			}
		}
	}

	private static void runPerformanceTest(Matrix m) throws Exception {
		long t = System.currentTimeMillis();
		long seed = t;

		// burn in
		Random random = new Random();
		while (System.currentTimeMillis() - t < BURNINTIME * 1000) {
			double value = MathUtil.nextDouble();
			m.setAsObject(value, random.nextInt(MATRIXSIZE), random.nextInt(MATRIXSIZE));
		}

		// write random entries to matrix
		t = System.currentTimeMillis();
		random = new Random(seed);
		int writtenEntryCount = 0;
		while (System.currentTimeMillis() - t < MAXTIME * 1000 && writtenEntryCount < MAXENTRYCOUNT) {
			double value = MathUtil.nextDouble();
			m.setAsObject(value, random.nextInt(MATRIXSIZE), random.nextInt(MATRIXSIZE));
			writtenEntryCount++;
		}

		System.out.println(" Writing random entries");
		printStatistics(System.currentTimeMillis() - t, writtenEntryCount, -1);

		// burn in
		t = System.currentTimeMillis();
		while (System.currentTimeMillis() - t < BURNINTIME * 1000) {
			m.getAsDouble(random.nextInt(MATRIXSIZE), random.nextInt(MATRIXSIZE));
		}

		// read random entries from matrix
		random = new Random();
		t = System.currentTimeMillis();
		int readEntryCount = 0;
		int hitCount = 0;
		while (System.currentTimeMillis() - t < MAXTIME * 1000) {
			int row = random.nextInt(MATRIXSIZE);
			int col = random.nextInt(MATRIXSIZE);
			double v = m.getAsDouble(row, col);
			readEntryCount++;
			if (v != 0.0) {
				hitCount++;
			}
		}

		System.out.println(" Reading random entries");
		printStatistics(System.currentTimeMillis() - t, readEntryCount, hitCount);

		// burn in
		t = System.currentTimeMillis();
		while (System.currentTimeMillis() - t < BURNINTIME * 1000) {
			m.getAsDouble(random.nextInt(MATRIXSIZE), random.nextInt(MATRIXSIZE));
		}

		// read/write random entries in matrix
		random = new Random();
		t = System.currentTimeMillis();
		readEntryCount = 0;
		hitCount = 0;
		while (System.currentTimeMillis() - t < MAXTIME * 1000 && readEntryCount < MAXENTRYCOUNT) {
			int row = random.nextInt(MATRIXSIZE);
			int col = random.nextInt(MATRIXSIZE);
			double v = m.getAsDouble(row, col);
			m.setAsDouble(v + 1, row, col);
			readEntryCount++;
			if (v != 0.0) {
				hitCount++;
			}
		}

		System.out.println(" Reading/writing random entries");
		printStatistics(System.currentTimeMillis() - t, readEntryCount, hitCount);

		// read non-zero entries from matrix
		t = System.currentTimeMillis();
		random = new Random(seed);
		readEntryCount = 0;
		hitCount = 0;
		while (System.currentTimeMillis() - t < MAXTIME * 1000
				&& readEntryCount < writtenEntryCount) {
			int row = random.nextInt(MATRIXSIZE);
			int col = random.nextInt(MATRIXSIZE);
			double v = m.getAsDouble(row, col);
			readEntryCount++;
			if (v != 0.0) {
				hitCount++;
			}
		}

		System.out.println(" Reading random non-zero entries");
		printStatistics(System.currentTimeMillis() - t, readEntryCount, hitCount);

		// burn in
		t = System.currentTimeMillis();
		while (System.currentTimeMillis() - t < BURNINTIME * 1000) {
			m.getAsDouble(random.nextInt(MATRIXSIZE), random.nextInt(MATRIXSIZE));
		}

		// read/write non-zero entries from matrix
		t = System.currentTimeMillis();
		random = new Random(seed);
		readEntryCount = 0;
		hitCount = 0;
		while (System.currentTimeMillis() - t < MAXTIME * 1000
				&& readEntryCount < writtenEntryCount) {
			int row = random.nextInt(MATRIXSIZE);
			int col = random.nextInt(MATRIXSIZE);
			double v = m.getAsDouble(row, col);
			m.setAsDouble(v + 1, row, col);
			readEntryCount++;
			if (v != 0.0) {
				hitCount++;
			}
		}

		System.out.println(" Reading/writing random non-zero entries");
		printStatistics(System.currentTimeMillis() - t, readEntryCount, hitCount);

		// lucene takes a long time to recover after sequential write
		if (m instanceof LuceneSparseObjectMatrix) {
			System.out.println();
			return;
		}

		// burn in
		while (System.currentTimeMillis() - t < BURNINTIME * 1000) {
			double value = MathUtil.nextDouble();
			m.setAsObject(value, random.nextInt(MATRIXSIZE), random.nextInt(MATRIXSIZE));
		}

		// write consecutive entries to matrix
		t = System.currentTimeMillis();
		writtenEntryCount = 0;
		for (long r = 0; r < m.getRowCount(); r++) {
			for (long c = 0; r < m.getColumnCount(); c++) {
				double value = MathUtil.nextDouble();
				m.setAsObject(value, r, c);
				writtenEntryCount++;
				if (System.currentTimeMillis() - t > MAXTIME * 1000
						|| writtenEntryCount > MAXENTRYCOUNT) {
					break;
				}
			}
		}

		System.out.println(" Writing consecutive entries");
		printStatistics(System.currentTimeMillis() - t, writtenEntryCount, -1);

		// burn in
		t = System.currentTimeMillis();
		while (System.currentTimeMillis() - t < BURNINTIME * 1000) {
			m.getAsDouble(random.nextInt(MATRIXSIZE), random.nextInt(MATRIXSIZE));
		}

		// read consecutive entries from matrix
		t = System.currentTimeMillis();
		readEntryCount = 0;
		hitCount = 0;
		for (long r = 0; r < m.getRowCount(); r++) {
			for (long c = 0; r < m.getColumnCount(); c++) {
				double v = m.getAsDouble(r, c);
				readEntryCount++;
				if (v != 0.0) {
					hitCount++;
				}
				if (System.currentTimeMillis() - t > MAXTIME * 1000
						|| readEntryCount > writtenEntryCount) {
					break;
				}
			}
		}

		System.out.println(" Reading consecutive entries");
		printStatistics(System.currentTimeMillis() - t, readEntryCount, hitCount);

		// burn in
		t = System.currentTimeMillis();
		while (System.currentTimeMillis() - t < BURNINTIME * 1000) {
			m.getAsDouble(random.nextInt(MATRIXSIZE), random.nextInt(MATRIXSIZE));
		}

		// read/write consecutive entries from matrix
		t = System.currentTimeMillis();
		readEntryCount = 0;
		hitCount = 0;
		for (long r = 0; r < m.getRowCount(); r++) {
			for (long c = 0; r < m.getColumnCount(); c++) {
				double v = m.getAsDouble(r, c);
				m.setAsDouble(v + 1, r, c);
				readEntryCount++;
				if (v != 0.0) {
					hitCount++;
				}
				if (System.currentTimeMillis() - t > MAXTIME * 1000
						|| readEntryCount > writtenEntryCount) {
					break;
				}
			}
		}

		System.out.println(" Reading/writing consecutive entries");
		printStatistics(System.currentTimeMillis() - t, readEntryCount, hitCount);

		System.out.println();
	}

	private static void printStatistics(long time, int count, int hits) {
		double speed = (double) count / time * 1000.0;
		double throughput = (double) speed * 8.0 / 1000.0;
		System.out.println("  time: " + time / 1000 + "s");
		if (hits >= 0) {
			System.out.println("  count: " + hits + " hits of " + count);
		} else {
			System.out.println("  count: " + count);
		}
		System.out.println("  speed: " + speed + " entries/s");
		System.out.println("  throughput: " + throughput + " KB/s");

	}
}
