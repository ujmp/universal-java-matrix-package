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

package org.ujmp.mail;

import java.net.InetAddress;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.ujmp.core.util.UJMPSettings;

public class MailUtil {

	public static void sendSystemOut(String recipient, String subject, String userName, String smtpServer)
			throws Exception {
		sendMessage(recipient, subject, UJMPSettings.getSystemOut(), userName, smtpServer);
	}

	public static void sendSystemErr(String recipient, String subject, String userName, String smtpServer)
			throws Exception {
		sendMessage(recipient, subject, UJMPSettings.getSystemErr(), userName, smtpServer);
	}

	public static void sendMessage(String recipient, String subject, String text, String userName, String smtpServer)
			throws Exception {

		Properties properties = new Properties();
		// properties.put("mail.store.protocol", "pop3");
		properties.put("mail.transport.protocol", "smtp");
		properties.put("mail.user", userName);
		// properties.put("mail.pop3.host", "pop3.provider.de");
		properties.put("mail.smtp.host", smtpServer);
		// properties.put("User", "user");
		// properties.put("Password", "passwd");
		properties.put("mail.from", "ujmp@" + InetAddress.getLocalHost().getHostName());

		Session mailSession = Session.getInstance(properties);

		Message message = new MimeMessage(mailSession);
		message.setSubject(subject);
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
		MimeMultipart mimeMultipart = new MimeMultipart();
		MimeBodyPart textPart = new MimeBodyPart();
		textPart.setText(text);
		textPart.setDisposition(MimeBodyPart.INLINE);
		mimeMultipart.addBodyPart(textPart);
		message.setContent(mimeMultipart);
		message.saveChanges();

		Transport.send(message);
	}

}
