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

package org.ujmp.core.text;

import java.util.Arrays;
import java.util.Collection;

import org.ujmp.core.listmatrix.DefaultListMatrix;

public class DefaultTextBlock extends DefaultListMatrix<TextSentence> implements TextBlock {
	private static final long serialVersionUID = -8988613694606138950L;

	private static int textBlockId = 1;

	public DefaultTextBlock(TextSentence... textSentences) {
		this(Arrays.asList(textSentences));
	}

	public DefaultTextBlock(Collection<TextSentence> textSentences) {
		setMetaData(ID, "TextBlock" + (textBlockId++));
		addAll(textSentences);
	}

	public DefaultTextBlock(String text) {
		this(TextUtil.convertToTextBlockToSentences(text));
	}

	public String toJson() {
		StringBuilder sb = new StringBuilder();
		sb.append("{\n");
		sb.append("  \"Type\": \"Text\",\n");
		sb.append("  \"Id\": ");
		sb.append("\"");
		sb.append(getMetaData("_id"));
		sb.append("\",\n");
		sb.append("  \"ArticleUrl\": ");
		sb.append("\"");
		sb.append(getMetaData("article_url"));
		sb.append("\",\n");
		sb.append("  \"Sentences\":\n");
		sb.append("  [\n");
		int i = 0;
		for (TextSentence sentence : this) {
			sb.append(sentence.toJson());
			if (i++ < size() - 1) {
				sb.append(",\n");
			}
		}
		sb.append("\n  ]\n");
		sb.append("}");
		return sb.toString();
	}

	public boolean setTag(String token, String tag) {
		boolean tokenFound = false;
		for (TextSentence sentence : this) {
			boolean tmp = sentence.setTag(token, tag);
			if (tokenFound && tmp) {
				throw new RuntimeException("multiple matching tokens found");
			}
			tokenFound = tmp;
		}
		return tokenFound;
	}

}
