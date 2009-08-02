/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
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

package org.ujmp.core.util;

import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import sun.misc.Signal;
import sun.misc.SignalHandler;

public class UJMPSignalHandler implements SignalHandler {

	private static final Logger logger = Logger.getLogger(UJMPSignalHandler.class.getName());

	private SignalHandler oldHandler = null;

	/**
	 * Registers handlers for the signals SIGCONT (18), SIGCHLD (17) and SIGUSR2
	 * (12). When one of these signals is sent to the application, the graphical
	 * user interface will be displayed.
	 */
	static {
		try {

			if ("Linux".equals(System.getProperty("os.name"))) {
				// Linux SEGV, ILL, FPE, BUS, SYS, CPU, FSZ, ABRT, INT, TERM,
				// HUP, USR1, QUIT, BREAK, TRAP, PIPE
				UJMPSignalHandler.install("USR2"); // 12
				UJMPSignalHandler.install("CHLD"); // 17
				UJMPSignalHandler.install("CONT"); // 18
			} else {
				// Windows SEGV, ILL, FPE, ABRT, INT, TERM, BREAK
				UJMPSignalHandler.install("SEGV");
				UJMPSignalHandler.install("ILL");
			}
		} catch (Exception e) {
			logger.log(Level.INFO, "could not install signal handler", e);
		}
	}

	/**
	 * This metod does nothing. The actual work is done in the static part of
	 * this class.
	 * 
	 */
	public static void initialize() {
	}

	private static SignalHandler install(String signalName) {
		Signal diagSignal = new Signal(signalName);
		UJMPSignalHandler instance = new UJMPSignalHandler();
		instance.oldHandler = Signal.handle(diagSignal, instance);
		return instance;
	}

	public void handle(Signal signal) {
		logger.log(Level.INFO, "Signal handler called for signal " + signal);
		try {
			signalAction(signal);
			// Chain back to previous handler, if one exists
			if (oldHandler != SIG_DFL && oldHandler != SIG_IGN) {
				oldHandler.handle(signal);
			}

		} catch (Exception e) {
			logger.log(Level.WARNING, "handle Signal handler failed" + e);
		}
	}

	public void signalAction(Signal signal) {
		logger.log(Level.INFO, "trying to open GUI");
		try {
			Class<?> c = Class.forName("org.jdmp.gui.util.FrameManager");
			Method method = c.getMethod("showFrame");
			method.invoke(null);
		} catch (Exception e) {
			logger.log(Level.WARNING, "cannot show GUI", e);
		}
	}

}
