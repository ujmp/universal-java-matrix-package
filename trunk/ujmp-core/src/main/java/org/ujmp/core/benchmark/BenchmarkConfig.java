/*
 * Copyright (C) 2008-2010 by Holger Arndt
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

import org.ujmp.core.mapmatrix.DefaultMapMatrix;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.StringUtil;

public class BenchmarkConfig extends DefaultMapMatrix<String, Object> {
	private static final long serialVersionUID = 2267893158983793396L;

	public static final double NOTAVAILABLE = 0;

	public static final double ERROR = Double.NaN;

	public BenchmarkConfig() {
		put("name", null);

		put("largeMatrices", false);
		put("singleThreaded", false);
		put("reverse", false);
		put("shuffle", false);
		put("gcMemory", true);
		put("purgeMemory", true);

		put("burnInRuns", 3);
		put("runs", 25);
		put("defaultTrialCount", 1);
		put("maxTrialCount", 5);
		put("maxTime", 10000); // maximal time for one operation
		put("maxStd", 10); // maximal standard deviation results may vary

		put("runTimesScalar", true);
		put("runPlusMatrix", true);
		put("runTranspose", true);
		put("runMtimes", true);
		put("runInv", true);
		put("runSolveSquare", true);
		put("runSolveTall", true);
		put("runSVD", true);
		put("runEig", true);
		put("runChol", true);
		put("runLU", false);
		put("runQR", false);

		put("runDefaultDenseDoubleMatrix2D", true);
		put("runArrayDenseDoubleMatrix2D", false);
		put("runJBlasDenseDoubleMatrix2D", true);
		put("runMTJDenseDoubleMatrix2D", true);
		put("runOjalgoDenseDoubleMatrix2D", true);
		put("runOrbitalDenseDoubleMatrix2D", false);
		put("runOwlpackDenseDoubleMatrix2D", false);
		put("runJScienceDenseDoubleMatrix2D", false);
		put("runJSciDenseDoubleMatrix2D", false);
		put("runJMathArrayDenseDoubleMatrix2D", false);
		put("runJLinAlgDenseDoubleMatrix2D", false);
		put("runParallelColtDenseDoubleMatrix2D", true);
		put("runColtDenseDoubleMatrix2D", true);
		put("runSSTDenseDoubleMatrix2D", false);
		put("runCommonsMathArrayDenseDoubleMatrix2D", false);
		put("runCommonsMathBlockDenseDoubleMatrix2D", true);
		put("runEJMLDenseDoubleMatrix2D", true);
		put("runJamaDenseDoubleMatrix2D", true);
		put("runJampackDenseDoubleMatrix2D", false);
		put("runJMatricesDenseDoubleMatrix2D", false);
		put("runMantissaDenseDoubleMatrix2D", false);
		put("runVecMathDenseDoubleMatrix2D", false);
	}

	public String getSvdSizes() {
		return getSquareSizes();
	}

	public String getQrSizes() {
		return getSquareSizes();
	}

	public String getLuSizes() {
		return getSquareSizes();
	}

	public boolean isRunTimesScalar() {
		return MathUtil.getBoolean(get("runTimesScalar"));
	}

	public void setRunTimesScalar(boolean runTimesScalar) {
		put("runTimesScalar", runTimesScalar);
	}

	public boolean isRunPlusMatrix() {
		return MathUtil.getBoolean(get("runPlusMatrix"));
	}

	public void setRunPlusMatrix(boolean runPlusMatrix) {
		put("runPlusMatrix", runPlusMatrix);
	}

	public boolean isRunTranspose() {
		return MathUtil.getBoolean(get("runTranspose"));
	}

	public void setRunTranspose(boolean runTranspose) {
		put("runTranspose", runTranspose);
	}

	public boolean isRunMtimes() {
		return MathUtil.getBoolean(get("runMtimes"));
	}

	public void setRunMtimes(boolean runMtimes) {
		put("runMtimes", runMtimes);
	}

	public boolean isRunInv() {
		return MathUtil.getBoolean(get("runInv"));
	}

	public void setRunInv(boolean runInv) {
		put("runInv", runInv);
	}

	public boolean isRunSolveSquare() {
		return MathUtil.getBoolean(get("runSolveSquare"));
	}

	public void setRunSolveSquare(boolean runSolveSquare) {
		put("runSolveSquare", runSolveSquare);
	}

	public boolean isRunSolveTall() {
		return MathUtil.getBoolean(get("runSolveTall"));
	}

	public void setRunSolveTall(boolean runSolveTall) {
		put("runSolveTall", runSolveTall);
	}

	public boolean isRunSVD() {
		return MathUtil.getBoolean(get("runSVD"));
	}

	public void setRunSVD(boolean runSVD) {
		put("runSVD", runSVD);
	}

	public boolean isSingleThreaded() {
		return MathUtil.getBoolean(get("singleThreaded"));
	}

	public void setSingleThreaded(boolean singleThreaded) {
		put("singleThreaded", singleThreaded);
	}

	public boolean isLargeMatrices() {
		return MathUtil.getBoolean(get("largeMatrices"));
	}

	public void setLargeMatrices(boolean largeMatrices) {
		put("largeMatrices", largeMatrices);
	}

	public boolean isRunEig() {
		return MathUtil.getBoolean(get("runEig"));
	}

	public void setRunEig(boolean runEig) {
		put("runEig", runEig);
	}

	public boolean isRunChol() {
		return MathUtil.getBoolean(get("runChol"));
	}

	public void setRunChol(boolean runChol) {
		put("runChol", runChol);
	}

	public boolean isRunLU() {
		return MathUtil.getBoolean(get("runLU"));
	}

	public void setRunLU(boolean runLU) {
		put("runLU", runLU);
	}

	public boolean isRunQR() {
		return MathUtil.getBoolean(get("runQR"));
	}

	public void setRunQR(boolean runQR) {
		put("runQR", runQR);
	}

	public int getBurnInRuns() {
		return MathUtil.getInt(get("burnInRuns"));
	}

	public int getDefaultTrialCount() {
		return MathUtil.getInt(get("defaultTrialCount"));
	}

	public void setDefaultTrialCount(int defaultTrialCount) {
		put("defaultTrialCount", defaultTrialCount);
	}

	public int getMaxTrialCount() {
		return MathUtil.getInt(get("maxTrialCount"));
	}

	public void setMaxTrialCount(int maxTrialCount) {
		put("maxTrialCount", maxTrialCount);
	}

	public void setBurnInRuns(int burnInRuns) {
		put("burnInRuns", burnInRuns);
	}

	public int getRuns() {
		return MathUtil.getInt(get("runs"));
	}

	public void setRuns(int runs) {
		put("runs", runs);
	}

	public String getSquareSizes() {
		if (isLargeMatrices()) {
			return "2x2,3x3,4x4,5x5,10x10,20x20,50x50,100x100,200x200,500x500,1000x1000,2000x2000,3000x3000,4000x4000,5000x5000,6000x6000,7000x7000,8000x8000,9000x9000,10000x10000";
		} else {
			return "2x2,3x3,4x4,5x5,10x10,20x20,50x50,100x100,200x200,500x500,1000x1000,2000x2000";
		}
	}

	public String getTallSizes() {
		if (isLargeMatrices()) {
			return "4x2,6x3,8x4,10x5,20x10,40x20,100x50,200x100,400x200,1000x500,2000x1000,3000x1500,4000x2000,5000x2500,6000x3000,7000x3500,8000x4000,9000x4500,10000x5000";
		} else {
			return "4x2,6x3,8x4,10x5,20x10,40x20,100x50,200x100,400x200,1000x500,2000x1000,4000x2000";
		}
	}

	public String getTransposeSizes() {
		return getSquareSizes();
	}

	public String getTimesSizes() {
		return getSquareSizes();
	}

	public String getPlusSizes() {
		return getSquareSizes();
	}

	public String getMtimesSizes() {
		return getSquareSizes();
	}

	public String getInvSizes() {
		return getSquareSizes();
	}

	public String getSolveSquareSizes() {
		return getSquareSizes();
	}

	public String getSolveTallSizes() {
		return getTallSizes();
	}

	public String getSVDSizes() {
		return getSquareSizes();
	}

	public String getEigSizes() {
		return getSquareSizes();
	}

	public String getQRSizes() {
		return getSquareSizes();
	}

	public String getLUSizes() {
		return getSquareSizes();
	}

	public String getCholSizes() {
		return getSquareSizes();
	}

	public boolean isRunDefaultDenseDoubleMatrix2D() {
		return MathUtil.getBoolean(get("runDefaultDenseDoubleMatrix2D"));
	}

	public void setRunDefaultDenseDoubleMatrix2D(boolean runDefaultDenseDoubleMatrix2D) {
		put("runDefaultDenseDoubleMatrix2D", runDefaultDenseDoubleMatrix2D);
	}

	public boolean isRunArrayDenseDoubleMatrix2D() {
		return MathUtil.getBoolean(get("runArrayDenseDoubleMatrix2D"));
	}

	public void setRunArrayDenseDoubleMatrix2D(boolean runArrayDenseDoubleMatrix2D) {
		put("runArrayDenseDoubleMatrix2D", runArrayDenseDoubleMatrix2D);
	}

	public boolean isRunMTJDenseDoubleMatrix2D() {
		return MathUtil.getBoolean(get("runMTJDenseDoubleMatrix2D"));
	}

	public void setRunMTJDenseDoubleMatrix2D(boolean runMTJDenseDoubleMatrix2D) {
		put("runMTJDenseDoubleMatrix2D", runMTJDenseDoubleMatrix2D);
	}

	public boolean isRunOjalgoDenseDoubleMatrix2D() {
		return MathUtil.getBoolean(get("runOjalgoDenseDoubleMatrix2D"));
	}

	public void setRunOjalgoDenseDoubleMatrix2D(boolean runOjalgoDenseDoubleMatrix2D) {
		put("runOjalgoDenseDoubleMatrix2D", runOjalgoDenseDoubleMatrix2D);
	}

	public boolean isRunOrbitalDenseDoubleMatrix2D() {
		return MathUtil.getBoolean(get("runOrbitalDenseDoubleMatrix2D"));
	}

	public void setRunOrbitalDenseDoubleMatrix2D(boolean runOrbitalDenseDoubleMatrix2D) {
		put("runOrbitalDenseDoubleMatrix2D", runOrbitalDenseDoubleMatrix2D);
	}

	public boolean isRunOwlpackDenseDoubleMatrix2D() {
		return MathUtil.getBoolean(get("runOwlpackDenseDoubleMatrix2D"));
	}

	public void setRunOwlpackDenseDoubleMatrix2D(boolean runOwlpackDenseDoubleMatrix2D) {
		put("runOwlpackDenseDoubleMatrix2D", runOwlpackDenseDoubleMatrix2D);
	}

	public boolean isRunJScienceDenseDoubleMatrix2D() {
		return MathUtil.getBoolean(get("runJScienceDenseDoubleMatrix2D"));
	}

	public void setRunJScienceDenseDoubleMatrix2D(boolean runJScienceDenseDoubleMatrix2D) {
		put("runJScienceDenseDoubleMatrix2D", runJScienceDenseDoubleMatrix2D);
	}

	public boolean isRunJSciDenseDoubleMatrix2D() {
		return MathUtil.getBoolean(get("runJSciDenseDoubleMatrix2D"));
	}

	public void setRunJSciDenseDoubleMatrix2D(boolean runJSciDenseDoubleMatrix2D) {
		put("runJSciDenseDoubleMatrix2D", runJSciDenseDoubleMatrix2D);
	}

	public boolean isRunJMathArrayDenseDoubleMatrix2D() {
		return MathUtil.getBoolean(get("runJMathArrayDenseDoubleMatrix2D"));
	}

	public void setRunJMathArrayDenseDoubleMatrix2D(boolean runJMathArrayDenseDoubleMatrix2D) {
		put("runJMathArrayDenseDoubleMatrix2D", runJMathArrayDenseDoubleMatrix2D);
	}

	public boolean isRunJLinAlgDenseDoubleMatrix2D() {
		return MathUtil.getBoolean(get("runJLinAlgDenseDoubleMatrix2D"));
	}

	public void setRunJLinAlgDenseDoubleMatrix2D(boolean runJLinAlgDenseDoubleMatrix2D) {
		put("runJLinAlgDenseDoubleMatrix2D", runJLinAlgDenseDoubleMatrix2D);
	}

	public boolean isRunParallelColtDenseDoubleMatrix2D() {
		return MathUtil.getBoolean(get("runParallelColtDenseDoubleMatrix2D"));
	}

	public void setRunParallelColtDenseDoubleMatrix2D(boolean runParallelColtDenseDoubleMatrix2D) {
		put("runParallelColtDenseDoubleMatrix2D", runParallelColtDenseDoubleMatrix2D);
	}

	public boolean isRunColtDenseDoubleMatrix2D() {
		return MathUtil.getBoolean(get("runColtDenseDoubleMatrix2D"));
	}

	public void setRunColtDenseDoubleMatrix2D(boolean runColtDenseDoubleMatrix2D) {
		put("runColtDenseDoubleMatrix2D", runColtDenseDoubleMatrix2D);
	}

	public boolean isRunSSTDenseDoubleMatrix2D() {
		return MathUtil.getBoolean(get("runSSTDenseDoubleMatrix2D"));
	}

	public void setRunSSTDenseDoubleMatrix2D(boolean runSSTDenseDoubleMatrix2D) {
		put("runSSTDenseDoubleMatrix2D", runSSTDenseDoubleMatrix2D);
	}

	public boolean isRunCommonsMathArrayDenseDoubleMatrix2D() {
		return MathUtil.getBoolean(get("runCommonsMathArrayDenseDoubleMatrix2D"));
	}

	public void setRunCommonsMathArrayDenseDoubleMatrix2D(
			boolean runCommonsMathArrayDenseDoubleMatrix2D) {
		put("runCommonsMathArrayDenseDoubleMatrix2D", runCommonsMathArrayDenseDoubleMatrix2D);
	}

	public boolean isRunCommonsMathBlockDenseDoubleMatrix2D() {
		return MathUtil.getBoolean(get("runCommonsMathBlockDenseDoubleMatrix2D"));
	}

	public void setRunCommonsMathBlockDenseDoubleMatrix2D(
			boolean runCommonsMathBlockDenseDoubleMatrix2D) {
		put("runCommonsMathBlockDenseDoubleMatrix2D", runCommonsMathBlockDenseDoubleMatrix2D);
	}

	public boolean isRunEJMLDenseDoubleMatrix2D() {
		return MathUtil.getBoolean(get("runEJMLDenseDoubleMatrix2D"));
	}

	public void setRunEJMLDenseDoubleMatrix2D(boolean runEJMLDenseDoubleMatrix2D) {
		put("runEJMLDenseDoubleMatrix2D", runEJMLDenseDoubleMatrix2D);
	}

	public boolean isRunJamaDenseDoubleMatrix2D() {
		return MathUtil.getBoolean(get("runJamaDenseDoubleMatrix2D"));
	}

	public void setRunJamaDenseDoubleMatrix2D(boolean runJamaDenseDoubleMatrix2D) {
		put("runJamaDenseDoubleMatrix2D", runJamaDenseDoubleMatrix2D);
	}

	public boolean isRunJampackDenseDoubleMatrix2D() {
		return MathUtil.getBoolean(get("runJampackDenseDoubleMatrix2D"));
	}

	public void setRunJampackDenseDoubleMatrix2D(boolean runJampackDenseDoubleMatrix2D) {
		put("runJampackDenseDoubleMatrix2D", runJampackDenseDoubleMatrix2D);
	}

	public boolean isRunJMatricesDenseDoubleMatrix2D() {
		return MathUtil.getBoolean(get("runJMatricesDenseDoubleMatrix2D"));
	}

	public void setRunJMatricesDenseDoubleMatrix2D(boolean runJMatricesDenseDoubleMatrix2D) {
		put("runJMatricesDenseDoubleMatrix2D", runJMatricesDenseDoubleMatrix2D);
	}

	public boolean isRunMantissaDenseDoubleMatrix2D() {
		return MathUtil.getBoolean(get("runMantissaDenseDoubleMatrix2D"));
	}

	public void setRunMantissaDenseDoubleMatrix2D(boolean runMantissaDenseDoubleMatrix2D) {
		put("runMantissaDenseDoubleMatrix2D", runMantissaDenseDoubleMatrix2D);
	}

	public boolean isRunVecMathDenseDoubleMatrix2D() {
		return MathUtil.getBoolean(get("runVecMathDenseDoubleMatrix2D"));
	}

	public void setRunVecMathDenseDoubleMatrix2D(boolean runVecMathDenseDoubleMatrix2D) {
		put("runVecMathDenseDoubleMatrix2D", runVecMathDenseDoubleMatrix2D);
	}

	public int getMaxTime() {
		return MathUtil.getInt(get("maxTime"));
	}

	public void setMaxTime(int maxTime) {
		put("maxTime", maxTime);
	}

	public double getMaxStd() {
		return MathUtil.getDouble(get("maxStd"));
	}

	public void setMaxStd(double maxStd) {
		put("maxStd", maxStd);
	}

	public boolean isGCMemory() {
		return MathUtil.getBoolean(get("gcMemory"));
	}

	public void setGCMemory(boolean gcMemory) {
		put("gcMemory", gcMemory);
	}

	public boolean isPurgeMemory() {
		return MathUtil.getBoolean(get("purgeMemory"));
	}

	public void setPurgeMemory(boolean purgeMemory) {
		put("purgeMemory", purgeMemory);
	}

	public boolean isShuffle() {
		return MathUtil.getBoolean(get("shuffle"));
	}

	public void setShuffle(boolean shuffle) {
		put("shuffle", shuffle);
	}

	public boolean isReverse() {
		return MathUtil.getBoolean(get("reverse"));
	}

	public void setReverse(boolean reverse) {
		put("reverse", reverse);
	}

	public String getName() {
		return StringUtil.getString(get("name"));
	}

	public boolean isRunJBlasDenseDoubleMatrix2D() {
		return MathUtil.getBoolean(get("runJBlasDenseDoubleMatrix2D"));
	}

	public void setRunJBlasDenseDoubleMatrix2D(boolean runJBlas) {
		put("runJBlasDenseDoubleMatrix2D", runJBlas);
	}

	public void setName(String name) {
		put("name", name);
	}
}
