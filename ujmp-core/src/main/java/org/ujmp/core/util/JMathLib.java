package org.ujmp.core.util;

import java.lang.reflect.Method;

import org.ujmp.core.exceptions.MatrixException;

public abstract class JMathLib {

	public static boolean isAvailable() {
		try {
			Class.forName("org.ujmp.jmathlib.JMathLib");
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	public static void showGUI() {
		try {
			Class<?> c = Class.forName("org.ujmp.jmathlib.JMathLib");
			Method method = c.getMethod("showGUI", new Class[] {});
			method.invoke(null);
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

}
