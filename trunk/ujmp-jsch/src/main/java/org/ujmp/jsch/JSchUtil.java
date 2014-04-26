/*
 * Copyright (C) 2008-2014 by Holger Arndt
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

package org.ujmp.jsch;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

public abstract class JSchUtil {

	public static String remoteWget(String hostname, int port, String username, File keyFile, final String passphrase,
			String url) throws JSchException, IOException {
		return remoteExecute(hostname, port, username, keyFile, passphrase, "wget -qO - " + url);
	}

	public static Session forwardLocalPort(String hostname, int port, String username, File keyFile,
			final String passphrase, int localPortToForward, String remoteHostToForwardTo, int remotePortToForwardTo)
			throws JSchException, IOException {
		JSch jsch = new JSch();
		jsch.addIdentity(keyFile.getAbsolutePath(), passphrase);
		Session session = jsch.getSession(username, hostname, port);
		UserInfo ui = createUserInfo(passphrase);
		session.setUserInfo(ui);
		session.setConfig("compression.s2c", "zlib@openssh.com,zlib,none");
		session.setConfig("compression.c2s", "zlib@openssh.com,zlib,none");
		session.setConfig("compression_level", "9");
		session.setPortForwardingL(localPortToForward, remoteHostToForwardTo, remotePortToForwardTo);
		session.connect();
		return session;
	}

	public static String remoteExecute(String hostname, int port, String username, File keyFile,
			final String passphrase, String command) throws JSchException, IOException {
		JSch jsch = new JSch();
		jsch.addIdentity(keyFile.getAbsolutePath(), passphrase);
		Session session = jsch.getSession(username, hostname, port);
		UserInfo ui = createUserInfo(passphrase);
		session.setUserInfo(ui);
		session.setConfig("compression.s2c", "zlib@openssh.com,zlib,none");
		session.setConfig("compression.c2s", "zlib@openssh.com,zlib,none");
		session.setConfig("compression_level", "9");
		session.connect();
		ChannelExec channel = (ChannelExec) session.openChannel("exec");
		channel.setCommand(command);
		InputStream in = channel.getInputStream();
		channel.setErrStream(System.err);
		channel.connect();

		ByteArrayOutputStream os = new ByteArrayOutputStream();

		final byte[] tmp = new byte[8192];
		while (true) {
			while (in.available() > 0) {
				int i = in.read(tmp, 0, 8192);
				os.write(tmp, 0, i);
				if (i < 0) {
					break;
				}
			}
			if (channel.isClosed()) {
				break;
			}
			try {
				Thread.sleep(10);
			} catch (Exception e) {
			}
		}
		channel.disconnect();
		session.disconnect();
		return os.toString();
	}

	public static int getRandomLocalPort() throws IOException {
		ServerSocket serverSocket = new ServerSocket(0);
		int port = serverSocket.getLocalPort();
		serverSocket.close();
		return port;
	}

	public static UserInfo createUserInfo(final String password) {
		UserInfo ui = new UserInfo() {
			public void showMessage(String arg0) {
			}

			public boolean promptYesNo(String arg0) {
				return true;
			}

			public boolean promptPassword(String arg0) {
				return true;
			}

			public boolean promptPassphrase(String arg0) {
				return false;
			}

			public String getPassword() {
				return password;
			}

			public String getPassphrase() {
				return null;
			}
		};
		return ui;
	}
}
