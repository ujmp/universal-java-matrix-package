package org.ujmp.mail;

import java.io.IOException;
import java.util.Enumeration;

import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;

import org.ujmp.core.Matrix;
import org.ujmp.core.listmatrix.DefaultListMatrix;
import org.ujmp.core.listmatrix.ListMatrix;
import org.ujmp.core.mapmatrix.DefaultMapMatrix;

public class MessageMatrix extends DefaultMapMatrix<Object, Object> {
	private static final long serialVersionUID = 4973660519646290182L;

	public MessageMatrix(Message m) throws IOException, MessagingException {
		put("Content", m.getContent());
		put("ContentType", m.getContentType());
		put("Description", m.getDescription());
		put("Disposition", m.getDisposition());
		put("FileName", m.getFileName());
		put("SentDate", m.getSentDate());
		put("Subject", m.getSubject());
		put("Folder", m.getFolder());
		put("ReceivedDate", m.getReceivedDate());

		Flags flags = m.getFlags();
		Matrix flagMatrix = new DefaultListMatrix<String>(flags.getUserFlags());
		put("Flags", flagMatrix);

		Enumeration<?> headers = m.getAllHeaders();
		ListMatrix<Object> headerMatrix = new DefaultListMatrix<Object>();
		while (headers.hasMoreElements()) {
			headerMatrix.add(headers.nextElement());
		}
		put("AllHeaders", headerMatrix);

		Address[] recipients = m.getAllRecipients();
		ListMatrix<String> recipientsMatrix = new DefaultListMatrix<String>();
		for (Address a : recipients) {
			recipientsMatrix.add("" + a);
		}
		put("AllRecipients", recipientsMatrix);

		Address[] from = m.getFrom();
		ListMatrix<String> fromMatrix = new DefaultListMatrix<String>();
		for (Address a : from) {
			fromMatrix.add("" + a);
		}
		put("From", fromMatrix);

		Address[] replyTo = m.getReplyTo();
		ListMatrix<String> replyToMatrix = new DefaultListMatrix<String>();
		for (Address a : replyTo) {
			replyToMatrix.add("" + a);
		}
		put("ReplyTo", replyToMatrix);

	}
}
