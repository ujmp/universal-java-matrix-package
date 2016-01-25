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

package org.ujmp.jetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.ujmp.jetty.handlers.FaviconHandler;
import org.ujmp.jetty.handlers.HomeHandler;
import org.ujmp.jetty.handlers.RobotsHandler;
import org.ujmp.jetty.handlers.StylesheetHandler;

public class UJMPJettyServer {

	public static final int DEFAULTPORT = 5555;

	private Server server = null;
	private final int port;

	public UJMPJettyServer(int port) {
		this.port = port;
	}

	public static void main(String[] args) throws Exception {
		int port = DEFAULTPORT;
		if (args != null && args.length == 1) {
			port = new Integer(args[0]);
		}
		UJMPJettyServer server = new UJMPJettyServer(port);
		server.start();
	}

	public boolean isConnected() {
		return server != null && server.isRunning();
	}

	public void start() throws Exception {
		server = new Server(port);

		ContextHandler homeHandler = new ContextHandler();
		homeHandler.setContextPath("/");
		homeHandler.setHandler(new HomeHandler());

		ContextHandler faviconHandler = new ContextHandler();
		faviconHandler.setContextPath("/favicon.ico");
		faviconHandler.setAllowNullPathInfo(true);
		faviconHandler.setHandler(new FaviconHandler());

		ContextHandler robotsHandler = new ContextHandler();
		robotsHandler.setContextPath("/robots.txt");
		robotsHandler.setAllowNullPathInfo(true);
		robotsHandler.setHandler(new RobotsHandler());

		ContextHandler stylesheetHandler = new ContextHandler();
		stylesheetHandler.setContextPath("/style.css");
		stylesheetHandler.setAllowNullPathInfo(true);
		stylesheetHandler.setHandler(new StylesheetHandler());

		ContextHandlerCollection contexts = new ContextHandlerCollection();
		contexts.addHandler(homeHandler);
		contexts.addHandler(faviconHandler);
		contexts.addHandler(robotsHandler);
		contexts.addHandler(stylesheetHandler);

		server.setHandler(contexts);
		server.start();
	}

	public void stop() throws Exception {
		server.stop();
	}

}
