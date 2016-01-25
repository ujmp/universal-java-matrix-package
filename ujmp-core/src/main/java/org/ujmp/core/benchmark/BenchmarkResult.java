/*
 * Copyright (C) 2008-2016 by Holger Arndt
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

package org.ujmp.core.benchmark;

public class BenchmarkResult {

	public static final BenchmarkResult NOTAVAILABLE = new BenchmarkResult(
			BenchmarkConfig.NOTAVAILABLE, -1);

	public static final BenchmarkResult ERROR = new BenchmarkResult(BenchmarkConfig.ERROR, -1);

	private double time = 0.0;

	private double difference = Double.NaN;

	private long mem = -1;

	public BenchmarkResult(double time, double difference, long mem) {
		this.time = time;
		this.difference = difference;
		this.mem = mem;
	}

	public BenchmarkResult(double time, long mem) {
		this.time = time;
		this.mem = mem;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public double getDifference() {
		return difference;
	}

	public void setDifference(double difference) {
		this.difference = difference;
	}

	public long getMem() {
		return mem;
	}

	public void setMem(long mem) {
		this.mem = mem;
	}

}
