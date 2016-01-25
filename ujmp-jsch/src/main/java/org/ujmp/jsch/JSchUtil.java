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

package org.ujmp.jsch;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.ujmp.core.UJMP;
import org.ujmp.core.util.io.HttpUtil;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.UserInfo;

public abstract class JSchUtil {

	public static String remoteWget(String hostname, int port, String username, File keyFile, final String passphrase,
			String url) throws JSchException, IOException {
		return remoteExecute(hostname, port, username, keyFile, passphrase, "wget -qO - '" + url + "'");
	}

	public static Session forwardLocalPort(String hostname, int port, String username, File keyFile,
			final String passphrase, int localPortToForward, String remoteHostToForwardTo, int remotePortToForwardTo)
			throws JSchException, IOException {
		Session session = createSession(hostname, port, username, keyFile, passphrase);
		session.setPortForwardingL(localPortToForward, remoteHostToForwardTo, remotePortToForwardTo);
		session.connect();
		return session;
	}

	public static void mkdir(String hostname, int port, String username, File keyFile, final String passphrase,
			String destinationFolder) throws JSchException, IOException, SftpException {
		remoteExecute(hostname, port, username, keyFile, passphrase, "mkdir -p '" + destinationFolder + "'");
	}

	public static void installUJMP(String hostname, int port, String username, File keyFile, final String passphrase,
			String destinationFolder) throws JSchException, IOException, SftpException {
		byte[] data = HttpUtil.getBytesFromUrl(UJMP.UJMPLOCATION);
		JSchUtil.mkdir(hostname, port, username, keyFile, passphrase, destinationFolder);
		JSchUtil.uploadFile(hostname, port, username, keyFile, passphrase, data, destinationFolder, UJMP.UJMPJARNAME);
	}

	public static void startUJMP(String hostname, int port, String username, File keyFile, final String passphrase,
			String destinationFolder) throws JSchException, IOException, SftpException {
		List<String> files = JSchUtil.ls(hostname, port, username, keyFile, passphrase, destinationFolder);
		String jar = null;
		for (String file : files) {
			if (file.startsWith("ujmp") && file.endsWith(".jar")) {
				jar = file;
			}
		}
		if (jar != null) {
			String result = JSchUtil.remoteExecute(hostname, port, username, keyFile, passphrase, destinationFolder
					+ "java -jar " + destinationFolder + "/" + jar);
			System.out.println(result);
		} else {
			throw new RuntimeException("UJMP not found in folder " + destinationFolder);
		}
	}

	public static Session createSession(String hostname, int port, String username, File keyFile,
			final String passphrase) throws JSchException {
		JSch jsch = new JSch();
		if (keyFile != null) {
			jsch.addIdentity(keyFile.getAbsolutePath(), passphrase);
		}
		Session session = jsch.getSession(username, hostname, port);
		UserInfo ui = createUserInfo(passphrase);
		session.setUserInfo(ui);
		session.setConfig("compression.s2c", "zlib@openssh.com,zlib,none");
		session.setConfig("compression.c2s", "zlib@openssh.com,zlib,none");
		session.setConfig("compression_level", "9");
		return session;
	}

	public static String pwd(String hostname, int port, String username, File keyFile, final String passphrase)
			throws JSchException, IOException, SftpException {
		Session session = createSession(hostname, port, username, keyFile, passphrase);
		session.connect();
		ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
		channel.connect();
		String pwd = channel.pwd();
		channel.disconnect();
		session.disconnect();
		return pwd;
	}

	public static List<String> ls(String hostname, int port, String username, File keyFile, final String passphrase,
			String path) throws JSchException, IOException, SftpException {
		Session session = createSession(hostname, port, username, keyFile, passphrase);
		session.connect();
		ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
		channel.connect();
		@SuppressWarnings("unchecked")
		Vector<LsEntry> vector = (Vector<LsEntry>) channel.ls(path);
		channel.disconnect();
		session.disconnect();
		List<String> files = new ArrayList<String>();
		for (LsEntry lse : vector) {
			files.add(lse.getFilename());
		}
		return files;
	}

	public static void uploadFile(String hostname, int port, String username, File keyFile, final String passphrase,
			File file, String destinationFolder) throws JSchException, IOException, SftpException {
		Session session = createSession(hostname, port, username, keyFile, passphrase);
		session.connect();
		ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
		channel.connect();
		channel.cd(destinationFolder);
		channel.put(new FileInputStream(file), file.getName());
		channel.disconnect();
		session.disconnect();
	}

	public static void uploadFile(String hostname, int port, String username, File keyFile, final String passphrase,
			byte[] data, String destinationFolder, String destinationFileName) throws JSchException, IOException,
			SftpException {
		Session session = createSession(hostname, port, username, keyFile, passphrase);
		session.connect();
		ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
		channel.connect();
		channel.cd(destinationFolder);
		channel.put(new ByteArrayInputStream(data), destinationFileName);
		channel.disconnect();
		session.disconnect();
	}

	public static String remoteExecute(String hostname, int port, String username, File keyFile,
			final String passphrase, String command) throws JSchException, IOException {
		Session session = createSession(hostname, port, username, keyFile, passphrase);
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

	public static void startOrInstallUJMP(String hostname, int port, String username, File keyFile,
			final String passphrase, String destinationFolder) throws JSchException, IOException, SftpException {
		JSchUtil.mkdir(hostname, port, username, keyFile, passphrase, destinationFolder);
		List<String> files = JSchUtil.ls(hostname, port, username, keyFile, passphrase, destinationFolder);
		String jar = null;
		for (String file : files) {
			if (file.startsWith("ujmp") && file.endsWith(".jar")) {
				jar = file;
				break;
			}
		}
		if (jar == null) {
			installUJMP(hostname, port, username, keyFile, passphrase, destinationFolder);
		}
		String result = JSchUtil.remoteExecute(hostname, port, username, keyFile, passphrase, "java -classpath "
				+ destinationFolder + "/" + jar + " org.ujmp.core.UJMP");
		System.out.println(result);
	}
}
