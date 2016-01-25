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

import org.ujmp.core.util.AbstractPlugin;

public class Plugin extends AbstractPlugin {

	public Plugin() {
		super("Plugin to incorporate Jetty web server");
		dependencies.add("ujmp-core");
		dependencies.add("jetty-server.jar");
		dependencies.add("javax.servlet-api.jar");
		dependencies.add("jetty-http.jar");
		dependencies.add("jetty-util.jar");
		dependencies.add("jetty-io.jar");
		dependencies.add("json.jar");
		neededClasses.add("org.eclise.jetty.server.Server");
		neededClasses.add("javax.servlet.Servlet");
		neededClasses.add("org.eclipse.jetty.http.HttpField");
		neededClasses.add("org.eclipse.jetty.util.Jetty");
		neededClasses.add("org.eclipse.jetty.io.Connection");
		neededClasses.add("org.json.JSONObject");
	}

}
