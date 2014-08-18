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

package org.ujmp.core.text;

import java.util.Arrays;
import java.util.Collection;

import org.ujmp.core.listmatrix.DefaultListMatrix;

public class DefaultTextSentence extends DefaultListMatrix<TextToken> implements TextSentence {
	private static final long serialVersionUID = -1411406267646623488L;

	private static int sentenceId = 1;

	public DefaultTextSentence(String sentence) {
		this(TextUtil.convertSentenceToTextTokens(sentence));
	}

	public DefaultTextSentence(TextToken... textTokens) {
		this(Arrays.asList(textTokens));
	}

	public DefaultTextSentence(Collection<TextToken> textTokens) {
		setMetaData(ID, "Sentence" + (sentenceId++));
		addAll(textTokens);
	}

	public String toJson() {
		StringBuilder sb = new StringBuilder();
		sb.append("  {\n");
		sb.append("    \"Type\": \"Sentence\",\n");
		sb.append("    \"Id\": ");
		sb.append("\"");
		sb.append(getMetaData("Id"));
		sb.append("\",\n");
		sb.append("    \"Tokens\":\n");
		sb.append("    [\n");
		int i = 0;
		for (TextToken token : this) {
			sb.append(token.toJson());
			if (i++ < size() - 1) {
				sb.append(",\n");
			}
		}
		sb.append("\n    ]\n");
		sb.append("  }");
		return sb.toString();
	}

	public boolean setTag(String token, String tag) {
		boolean tokenFound = false;
		for (TextToken text : this) {
			if (token.equals(text.getText())) {
				if (tokenFound) {
					throw new RuntimeException("multiple matching tokens found");
				}
				text.setTag(tag);
				tokenFound = true;
			}
		}
		return tokenFound;
	}

}
