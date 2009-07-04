/*
 * Copyright (C) 2008-2009 Holger Arndt, A. Naegele and M. Bundschus
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

package org.ujmp.mail;

import java.io.Closeable;
import java.security.Security;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

import org.ujmp.core.listmatrix.DefaultListMatrix;
import org.ujmp.core.listmatrix.ListMatrix;

public class ImapUtil implements Closeable {

	private String host = null;

	private String user = null;

	private String password = null;

	private final int connectiontimeout = 5000;

	private final int timeout = 5000;

	private final String protocol = "imaps";

	private final boolean sslEnable = true;

	private final int port = 993;

	private final boolean ignoreCertificate = true;

	private Session session = null;

	private Store store = null;

	public ImapUtil(String host, String user, String password) {
		this.host = host;
		this.user = user;
		this.password = password;
		Properties props = System.getProperties();
		props.setProperty("mail.imap.host", host);
		props.setProperty("mail.imap.port", "" + port);
		props
				.setProperty("mail.imap.connectiontimeout", ""
						+ connectiontimeout);
		props.setProperty("mail.imap.timeout", "" + timeout);
		props.setProperty("mail.store.protocol", protocol);
		props.setProperty("mail.imap.ssl.enable", "" + sslEnable);

		if (ignoreCertificate) {
			Security.setProperty("ssl.SocketFactory.provider",
					DummySSLSocketFactory.class.getName());
		}

		session = Session.getDefaultInstance(System.getProperties(), null);
	}

	public ListMatrix<Folder> getPersonalFolders() throws Exception {
		Folder[] folders = getStore().getPersonalNamespaces();
		ListMatrix<Folder> folderMatrix = new DefaultListMatrix<Folder>(folders);
		return folderMatrix;
	}

	public ListMatrix<Folder> getSubFolders(Folder folder) throws Exception {
		Folder[] folders = folder.list();
		ListMatrix<Folder> folderMatrix = new DefaultListMatrix<Folder>(folders);
		return folderMatrix;
	}

	public MessagesMatrix getMessages(String folderName) throws Exception {
		Folder folder = getStore().getFolder(folderName);
		return getMessages(folder);
	}

	public Folder getFolder(String folderName) throws Exception {
		Folder folder = getStore().getFolder(folderName);
		return folder;
	}

	public ListMatrix<Folder> getSubFolders(String folderName) throws Exception {
		Folder folder = getStore().getFolder(folderName);
		return getSubFolders(folder);
	}

	public MessagesMatrix getMessages(Folder folder) throws Exception {
		return new MessagesMatrix(folder);
	}

	private Store getStore() throws Exception {
		if (store == null) {
			store = session.getStore(protocol);
		}
		if (!store.isConnected()) {
			store.connect(host, user, password);
		}
		return store;
	}

	public void close() {
		if (store != null && store.isConnected()) {
			try {
				store.close();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
	}

	public ListMatrix<Folder> getSharedFolders() throws Exception {
		Folder[] folders = getStore().getSharedNamespaces();
		ListMatrix<Folder> folderMatrix = new DefaultListMatrix<Folder>(folders);
		store.close();
		return folderMatrix;
	}

}
