/*
 * Copyright (C) 2008-2015 by Holger Arndt
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
import java.io.IOException;

import javax.mail.Folder;
import javax.mail.MessagingException;

import org.ujmp.core.Matrix;
import org.ujmp.core.listmatrix.AbstractListMatrix;

public class MessagesMatrix extends AbstractListMatrix<Matrix> implements Closeable {
	private static final long serialVersionUID = -8489199262755536077L;

	private final Folder folder;

	public MessagesMatrix(Folder folder) throws Exception {
		this.folder = folder;
	}

	public MessagesMatrix(String url, String user, String password, String folderName) throws Exception {
		ImapUtil util = new ImapUtil(url, user, password);
		folder = util.getFolder(folderName);
	}

	public Matrix get(int index) {
		try {
			if (!folder.isOpen()) {
				folder.open(Folder.READ_ONLY);
			}
			return new MessageMatrix(folder.getMessage(index + 1));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public int size() {
		try {
			return folder.getMessageCount();
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	public void close() throws IOException {
		try {
			folder.close(false);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean addToList(Matrix t) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addToList(int index, Matrix element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Matrix removeFromList(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeFromList(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Matrix setToList(int index, Matrix element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clearList() {
		throw new UnsupportedOperationException();
	}

}
