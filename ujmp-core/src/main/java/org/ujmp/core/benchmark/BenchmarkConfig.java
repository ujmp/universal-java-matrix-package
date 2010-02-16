/*
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

public class BenchmarkConfig {

	public static final double NOTAVAILABLE = 0;

	public static final double ERROR = Double.NaN;

	private int burnInRuns = 3;

	private int runs = 25;

	private int defaultTrialCount = 1;

	private int maxTrialCount = 5;

	private int maxTime = 10000; // maximal time for one operation: 10000ms

	private double maxStd = 10; // maximal standard deviation results may vary:
	// 10%

	private String defaultSizes = "2x2,3x3,4x4,5x5,10x10,20x20,50x50,100x100,200x200,500x500,1000x1000,2000x2000";

	private String transposeSizes = defaultSizes;

	private String timesSizes = defaultSizes;

	private String plusSizes = defaultSizes;

	private String mtimesSizes = defaultSizes;

	private String invSizes = defaultSizes;

	private String solveSquareSizes = defaultSizes;

	private String solveTallSizes = "4x2,6x3,8x4,10x5,20x10,40x20,100x50,200x100,400x200,1000x500,2000x1000";

	private String svdSizes = defaultSizes;

	private String eigSizes = defaultSizes;

	private String qrSizes = defaultSizes;

	private String luSizes = defaultSizes;

	private String cholSizes = defaultSizes;

	private boolean runTimesScalar = true;

	private boolean runPlusMatrix = true;

	private boolean runTranspose = true;

	private boolean runMtimes = true;

	private boolean runInv = true;

	private boolean runSolveSquare = true;

	private boolean runSolveTall = true;

	private boolean runSVD = true;

	private boolean runEig = true;

	private boolean runChol = true;

	private boolean runLU = false;

	private boolean runQR = false;

	private boolean runDefaultDenseDoubleMatrix2D = true;

	private boolean runArrayDenseDoubleMatrix2D = false;

	private boolean runMTJDenseDoubleMatrix2D = true;

	private boolean runOjalgoDenseDoubleMatrix2D = true;

	private boolean runOrbitalDenseDoubleMatrix2D = false;

	private boolean runOwlpackDenseDoubleMatrix2D = false;

	private boolean runJScienceDenseDoubleMatrix2D = true;

	private boolean runJSciDenseDoubleMatrix2D = false;

	private boolean runJMathArrayDenseDoubleMatrix2D = false;

	private boolean runJLinAlgDenseDoubleMatrix2D = false;

	private boolean runParallelColtDenseDoubleMatrix2D = true;

	private boolean runColtDenseDoubleMatrix2D = true;

	private boolean runSSTDenseDoubleMatrix2D = false;

	private boolean runCommonsMathArrayDenseDoubleMatrix2D = false;

	private boolean runCommonsMathBlockDenseDoubleMatrix2D = true;

	private boolean runEJMLDenseDoubleMatrix2D = true;

	private boolean runJamaDenseDoubleMatrix2D = true;

	private boolean runJampackDenseDoubleMatrix2D = false;

	private boolean runJMatricesDenseDoubleMatrix2D = false;

	private boolean runMantissaDenseDoubleMatrix2D = false;

	private boolean runVecMathDenseDoubleMatrix2D = false;

	private boolean gcMemory = true;

	private boolean purgeMemory = true;

	public BenchmarkConfig() {
	}

	public String getSvdSizes() {
		return svdSizes;
	}

	public void setSvdSizes(String svdSizes) {
		this.svdSizes = svdSizes;
	}

	public String getQrSizes() {
		return qrSizes;
	}

	public void setQrSizes(String qrSizes) {
		this.qrSizes = qrSizes;
	}

	public String getLuSizes() {
		return luSizes;
	}

	public void setLuSizes(String luSizes) {
		this.luSizes = luSizes;
	}

	public boolean isRunTimesScalar() {
		return runTimesScalar;
	}

	public void setRunTimesScalar(boolean runTimesScalar) {
		this.runTimesScalar = runTimesScalar;
	}

	public boolean isRunPlusMatrix() {
		return runPlusMatrix;
	}

	public void setRunPlusMatrix(boolean runPlusMatrix) {
		this.runPlusMatrix = runPlusMatrix;
	}

	public boolean isRunTranspose() {
		return runTranspose;
	}

	public void setRunTranspose(boolean runTranspose) {
		this.runTranspose = runTranspose;
	}

	public boolean isRunMtimes() {
		return runMtimes;
	}

	public void setRunMtimes(boolean runMtimes) {
		this.runMtimes = runMtimes;
	}

	public boolean isRunInv() {
		return runInv;
	}

	public void setRunInv(boolean runInv) {
		this.runInv = runInv;
	}

	public boolean isRunSolveSquare() {
		return runSolveSquare;
	}

	public void setRunSolveSquare(boolean runSolveSquare) {
		this.runSolveSquare = runSolveSquare;
	}

	public boolean isRunSolveTall() {
		return runSolveTall;
	}

	public void setRunSolveTall(boolean runSolveTall) {
		this.runSolveTall = runSolveTall;
	}

	public boolean isRunSVD() {
		return runSVD;
	}

	public void setRunSVD(boolean runSVD) {
		this.runSVD = runSVD;
	}

	public boolean isRunEig() {
		return runEig;
	}

	public void setRunEig(boolean runEig) {
		this.runEig = runEig;
	}

	public boolean isRunChol() {
		return runChol;
	}

	public void setRunChol(boolean runChol) {
		this.runChol = runChol;
	}

	public boolean isRunLU() {
		return runLU;
	}

	public void setRunLU(boolean runLU) {
		this.runLU = runLU;
	}

	public boolean isRunQR() {
		return runQR;
	}

	public void setRunQR(boolean runQR) {
		this.runQR = runQR;
	}

	public int getBurnInRuns() {
		return burnInRuns;
	}

	public int getDefaultTrialCount() {
		return defaultTrialCount;
	}

	public void setDefaultTrialCount(int defaultTrialCount) {
		this.defaultTrialCount = defaultTrialCount;
	}

	public int getMaxTrialCount() {
		return maxTrialCount;
	}

	public void setMaxTrialCount(int maxTrialCount) {
		this.maxTrialCount = maxTrialCount;
	}

	public void setBurnInRuns(int burnInRuns) {
		this.burnInRuns = burnInRuns;
	}

	public int getRuns() {
		return runs;
	}

	public void setRuns(int runs) {
		this.runs = runs;
	}

	public String getDefaultSizes() {
		return defaultSizes;
	}

	public void setDefaultSizes(String defaultSizes) {
		this.defaultSizes = defaultSizes;
	}

	public String getTransposeSizes() {
		return transposeSizes;
	}

	public void setTransposeSizes(String transposeSizes) {
		this.transposeSizes = transposeSizes;
	}

	public String getTimesSizes() {
		return timesSizes;
	}

	public void setTimesSizes(String timesSizes) {
		this.timesSizes = timesSizes;
	}

	public String getPlusSizes() {
		return plusSizes;
	}

	public void setPlusSizes(String plusSizes) {
		this.plusSizes = plusSizes;
	}

	public String getMtimesSizes() {
		return mtimesSizes;
	}

	public void setMtimesSizes(String mtimesSizes) {
		this.mtimesSizes = mtimesSizes;
	}

	public String getInvSizes() {
		return invSizes;
	}

	public void setInvSizes(String invSizes) {
		this.invSizes = invSizes;
	}

	public String getSolveSquareSizes() {
		return solveSquareSizes;
	}

	public void setSolveSquareSizes(String solveSquareSizes) {
		this.solveSquareSizes = solveSquareSizes;
	}

	public String getSolveTallSizes() {
		return solveTallSizes;
	}

	public void setSolveTallSizes(String solveTallSizes) {
		this.solveTallSizes = solveTallSizes;
	}

	public String getSVDSizes() {
		return svdSizes;
	}

	public void setSVDSizes(String svdSizes) {
		this.svdSizes = svdSizes;
	}

	public String getEigSizes() {
		return eigSizes;
	}

	public void setEigSizes(String eigSizes) {
		this.eigSizes = eigSizes;
	}

	public String getQRSizes() {
		return qrSizes;
	}

	public void setQRSizes(String qrSizes) {
		this.qrSizes = qrSizes;
	}

	public String getLUSizes() {
		return luSizes;
	}

	public void setLUSizes(String luSizes) {
		this.luSizes = luSizes;
	}

	public String getCholSizes() {
		return cholSizes;
	}

	public void setCholSizes(String cholSizes) {
		this.cholSizes = cholSizes;
	}

	public boolean isRunDefaultDenseDoubleMatrix2D() {
		return runDefaultDenseDoubleMatrix2D;
	}

	public void setRunDefaultDenseDoubleMatrix2D(boolean runDefaultDenseDoubleMatrix2D) {
		this.runDefaultDenseDoubleMatrix2D = runDefaultDenseDoubleMatrix2D;
	}

	public boolean isRunArrayDenseDoubleMatrix2D() {
		return runArrayDenseDoubleMatrix2D;
	}

	public void setRunArrayDenseDoubleMatrix2D(boolean runArrayDenseDoubleMatrix2D) {
		this.runArrayDenseDoubleMatrix2D = runArrayDenseDoubleMatrix2D;
	}

	public boolean isRunMTJDenseDoubleMatrix2D() {
		return runMTJDenseDoubleMatrix2D;
	}

	public void setRunMTJDenseDoubleMatrix2D(boolean runMTJDenseDoubleMatrix2D) {
		this.runMTJDenseDoubleMatrix2D = runMTJDenseDoubleMatrix2D;
	}

	public boolean isRunOjalgoDenseDoubleMatrix2D() {
		return runOjalgoDenseDoubleMatrix2D;
	}

	public void setRunOjalgoDenseDoubleMatrix2D(boolean runOjalgoDenseDoubleMatrix2D) {
		this.runOjalgoDenseDoubleMatrix2D = runOjalgoDenseDoubleMatrix2D;
	}

	public boolean isRunOrbitalDenseDoubleMatrix2D() {
		return runOrbitalDenseDoubleMatrix2D;
	}

	public void setRunOrbitalDenseDoubleMatrix2D(boolean runOrbitalDenseDoubleMatrix2D) {
		this.runOrbitalDenseDoubleMatrix2D = runOrbitalDenseDoubleMatrix2D;
	}

	public boolean isRunOwlpackDenseDoubleMatrix2D() {
		return runOwlpackDenseDoubleMatrix2D;
	}

	public void setRunOwlpackDenseDoubleMatrix2D(boolean runOwlpackDenseDoubleMatrix2D) {
		this.runOwlpackDenseDoubleMatrix2D = runOwlpackDenseDoubleMatrix2D;
	}

	public boolean isRunJScienceDenseDoubleMatrix2D() {
		return runJScienceDenseDoubleMatrix2D;
	}

	public void setRunJScienceDenseDoubleMatrix2D(boolean runJScienceDenseDoubleMatrix2D) {
		this.runJScienceDenseDoubleMatrix2D = runJScienceDenseDoubleMatrix2D;
	}

	public boolean isRunJSciDenseDoubleMatrix2D() {
		return runJSciDenseDoubleMatrix2D;
	}

	public void setRunJSciDenseDoubleMatrix2D(boolean runJSciDenseDoubleMatrix2D) {
		this.runJSciDenseDoubleMatrix2D = runJSciDenseDoubleMatrix2D;
	}

	public boolean isRunJMathArrayDenseDoubleMatrix2D() {
		return runJMathArrayDenseDoubleMatrix2D;
	}

	public void setRunJMathArrayDenseDoubleMatrix2D(boolean runJMathArrayDenseDoubleMatrix2D) {
		this.runJMathArrayDenseDoubleMatrix2D = runJMathArrayDenseDoubleMatrix2D;
	}

	public boolean isRunJLinAlgDenseDoubleMatrix2D() {
		return runJLinAlgDenseDoubleMatrix2D;
	}

	public void setRunJLinAlgDenseDoubleMatrix2D(boolean runJLinAlgDenseDoubleMatrix2D) {
		this.runJLinAlgDenseDoubleMatrix2D = runJLinAlgDenseDoubleMatrix2D;
	}

	public boolean isRunParallelColtDenseDoubleMatrix2D() {
		return runParallelColtDenseDoubleMatrix2D;
	}

	public void setRunParallelColtDenseDoubleMatrix2D(boolean runParallelColtDenseDoubleMatrix2D) {
		this.runParallelColtDenseDoubleMatrix2D = runParallelColtDenseDoubleMatrix2D;
	}

	public boolean isRunColtDenseDoubleMatrix2D() {
		return runColtDenseDoubleMatrix2D;
	}

	public void setRunColtDenseDoubleMatrix2D(boolean runColtDenseDoubleMatrix2D) {
		this.runColtDenseDoubleMatrix2D = runColtDenseDoubleMatrix2D;
	}

	public boolean isRunSSTDenseDoubleMatrix2D() {
		return runSSTDenseDoubleMatrix2D;
	}

	public void setRunSSTDenseDoubleMatrix2D(boolean runSSTDenseDoubleMatrix2D) {
		this.runSSTDenseDoubleMatrix2D = runSSTDenseDoubleMatrix2D;
	}

	public boolean isRunCommonsMathArrayDenseDoubleMatrix2D() {
		return runCommonsMathArrayDenseDoubleMatrix2D;
	}

	public void setRunCommonsMathArrayDenseDoubleMatrix2D(
			boolean runCommonsMathArrayDenseDoubleMatrix2D) {
		this.runCommonsMathArrayDenseDoubleMatrix2D = runCommonsMathArrayDenseDoubleMatrix2D;
	}

	public boolean isRunCommonsMathBlockDenseDoubleMatrix2D() {
		return runCommonsMathBlockDenseDoubleMatrix2D;
	}

	public void setRunCommonsMathBlockDenseDoubleMatrix2D(
			boolean runCommonsMathBlockDenseDoubleMatrix2D) {
		this.runCommonsMathBlockDenseDoubleMatrix2D = runCommonsMathBlockDenseDoubleMatrix2D;
	}

	public boolean isRunEJMLDenseDoubleMatrix2D() {
		return runEJMLDenseDoubleMatrix2D;
	}

	public void setRunEJMLDenseDoubleMatrix2D(boolean runEJMLDenseDoubleMatrix2D) {
		this.runEJMLDenseDoubleMatrix2D = runEJMLDenseDoubleMatrix2D;
	}

	public boolean isRunJamaDenseDoubleMatrix2D() {
		return runJamaDenseDoubleMatrix2D;
	}

	public void setRunJamaDenseDoubleMatrix2D(boolean runJamaDenseDoubleMatrix2D) {
		this.runJamaDenseDoubleMatrix2D = runJamaDenseDoubleMatrix2D;
	}

	public boolean isRunJampackDenseDoubleMatrix2D() {
		return runJampackDenseDoubleMatrix2D;
	}

	public void setRunJampackDenseDoubleMatrix2D(boolean runJampackDenseDoubleMatrix2D) {
		this.runJampackDenseDoubleMatrix2D = runJampackDenseDoubleMatrix2D;
	}

	public boolean isRunJMatricesDenseDoubleMatrix2D() {
		return runJMatricesDenseDoubleMatrix2D;
	}

	public void setRunJMatricesDenseDoubleMatrix2D(boolean runJMatricesDenseDoubleMatrix2D) {
		this.runJMatricesDenseDoubleMatrix2D = runJMatricesDenseDoubleMatrix2D;
	}

	public boolean isRunMantissaDenseDoubleMatrix2D() {
		return runMantissaDenseDoubleMatrix2D;
	}

	public void setRunMantissaDenseDoubleMatrix2D(boolean runMantissaDenseDoubleMatrix2D) {
		this.runMantissaDenseDoubleMatrix2D = runMantissaDenseDoubleMatrix2D;
	}

	public boolean isRunVecMathDenseDoubleMatrix2D() {
		return runVecMathDenseDoubleMatrix2D;
	}

	public void setRunVecMathDenseDoubleMatrix2D(boolean runVecMathDenseDoubleMatrix2D) {
		this.runVecMathDenseDoubleMatrix2D = runVecMathDenseDoubleMatrix2D;
	}

	public int getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(int maxTime) {
		this.maxTime = maxTime;
	}

	public double getMaxStd() {
		return maxStd;
	}

	public void setMaxStd(double maxStd) {
		this.maxStd = maxStd;
	}

	public boolean isGCMemory() {
		return gcMemory;
	}

	public void setGCMemory(boolean gcMemory) {
		this.gcMemory = gcMemory;
	}

	public boolean isPurgeMemory() {
		return purgeMemory;
	}

	public void setPurgeMemory(boolean purgeMemory) {
		this.purgeMemory = purgeMemory;
	}

}
