package org.ujmp.core.calculation;

public interface CalculationMethod<S, T> {

	public T calculate(S source1, S source2);

}
