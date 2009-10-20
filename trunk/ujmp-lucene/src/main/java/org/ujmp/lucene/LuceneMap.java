/*
 * Copyright (C) 2008-2009 by Holger Arndt
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

package org.ujmp.lucene;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.Flushable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JLabel;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.Erasable;
import org.ujmp.core.util.SerializationUtil;
import org.ujmp.core.util.io.FileUtil;

public class LuceneMap<K, V> implements Map<K, V>, Flushable, Closeable,
		Erasable, Serializable {
	private static final long serialVersionUID = 8998898900190996038L;

	private static final String KEYSTRING = "KS";

	private static final String KEYDATA = "KD";

	private static final String VALUESTRING = "VS";

	private static final String VALUEDATA = "VD";

	private transient Directory directory = null;

	private transient IndexWriter indexWriter = null;

	private transient IndexSearcher indexSearcher = null;

	private static final int MAXSIZE = 1000000;

	private boolean readOnly = false;

	private transient File path = null;

	private transient Analyzer analyzer = null;

	public LuceneMap() throws IOException {
		this(null, false);
	}

	public LuceneMap(File dir) throws IOException {
		this(dir, false);
	}

	public LuceneMap(File path, boolean readOnly) throws IOException {
		this.readOnly = readOnly;
		this.path = path;
	}

	public Directory getDirectory() throws IOException {
		if (directory == null) {
			directory = FSDirectory.getDirectory(getPath());
		}
		return directory;
	}

	public File getPath() throws IOException {
		if (path == null) {
			path = File.createTempFile("lucene", "");
			path.delete();
			path.mkdir();
			path.deleteOnExit();
		}
		return path;
	}

	public synchronized void optimize() throws CorruptIndexException,
			IOException {
		getIndexWriter().optimize();
	}

	public synchronized void clear() {
		try {
			getIndexWriter().deleteDocuments(new WildcardQuery(new Term("*")));
		} catch (Exception e) {
			throw new MatrixException("cannot clear index", e);
		}
	}

	public synchronized boolean containsKey(Object key) {
		try {
			Term term = new Term(KEYSTRING, getUniqueString(key));
			return getIndexSearcher().docFreq(term) > 0;
		} catch (Exception e) {
			throw new MatrixException("could not search documents: " + key, e);
		}
	}

	public synchronized boolean containsValue(Object value) {
		try {
			Term term = new Term(VALUESTRING, getUniqueString(value));
			return getIndexSearcher().docFreq(term) > 0;
		} catch (Exception e) {
			throw new MatrixException("could not search documents: " + value, e);
		}
	}

	public synchronized Set<java.util.Map.Entry<K, V>> entrySet() {
		throw new MatrixException("not implemented");
	}

	public synchronized V get(Object key) {
		try {
			Term term = new Term(KEYSTRING, getUniqueString(key));
			TermQuery query = new TermQuery(term);
			TopDocs docs = getIndexSearcher().search(query, 1);
			if (docs.totalHits > 0) {
				ScoreDoc match = docs.scoreDocs[0];
				Document doc = getIndexSearcher().doc(match.doc);
				return getObjectFromBytes(doc.getBinaryValue(VALUEDATA));
			}
		} catch (Exception e) {
			throw new MatrixException("could not search documents: " + key, e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private V getObjectFromBytes(byte[] bytes) {
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bis);
			Object o = ois.readObject();
			ois.close();
			bis.close();
			return (V) o;
		} catch (Exception e) {
			throw new MatrixException("could not convert to object", e);
		}
	}

	public synchronized boolean isEmpty() {
		try {
			return getIndexWriter().numDocs() == 0;
		} catch (Exception e) {
			throw new MatrixException("could not search documents", e);
		}
	}

	@SuppressWarnings("unchecked")
	public synchronized Set<K> keySet() {
		Set<K> set = new HashSet<K>();
		if (isEmpty()) {
			return set;
		}
		try {
			Term term = new Term(KEYSTRING, "*");
			WildcardQuery query = new WildcardQuery(term);
			TopDocs docs = getIndexSearcher().search(query, MAXSIZE);

			for (ScoreDoc sd : docs.scoreDocs) {
				Document d = getIndexSearcher().doc(sd.doc);
				set.add((K) getObjectFromBytes(d.getBinaryValue(KEYDATA)));
			}
			return set;
		} catch (Exception e) {
			throw new MatrixException("could not search documents", e);
		}
	}

	private static String getUniqueString(Object o) throws IOException {
		if (o == null) {
			return "";
		} else {
			return new String(SerializationUtil.serialize((Serializable) o));
		}
	}

	public synchronized V put(K key, V value) {
		try {
			Term term = new Term(KEYSTRING, getUniqueString(key));
			Document doc = new Document();
			doc.add(new Field(KEYSTRING, getUniqueString(key), Field.Store.YES,
					Field.Index.NOT_ANALYZED));
			doc.add(new Field(KEYDATA, getBytes(key), Field.Store.YES));
			doc.add(new Field(VALUESTRING, getUniqueString(value),
					Field.Store.YES, Field.Index.NOT_ANALYZED));
			doc.add(new Field(VALUEDATA, getBytes(value), Field.Store.YES));
			getIndexWriter().updateDocument(term, doc);
			return null;
		} catch (Exception e) {
			throw new MatrixException("could not add document: " + key, e);
		}
	}

	public void putAll(Map<? extends K, ? extends V> m) {
		for (K key : m.keySet()) {
			V value = m.get(key);
			put(key, value);
		}
	}

	public synchronized V remove(Object key) {
		try {
			Term term = new Term(KEYSTRING, getUniqueString(key));
			getIndexWriter().deleteDocuments(term);
			return null;
		} catch (Exception e) {
			throw new MatrixException("could not delete document: " + key, e);
		}
	}

	public Analyzer getAnalyzer() {
		if (analyzer == null) {
			analyzer = new StandardAnalyzer();
		}
		return analyzer;
	}

	public void setAnalyzer(Analyzer analyzer) {
		this.analyzer = analyzer;
	}

	public synchronized int size() {
		try {
			return getIndexWriter().numDocs();
		} catch (Exception e) {
			throw new MatrixException("could not count documents", e);
		}
	}

	public synchronized Collection<V> values() {
		throw new MatrixException("not implemented");
	}

	public synchronized void flush() throws IOException {
		getIndexWriter().commit();
	}

	public synchronized void close() throws IOException {
		getIndexWriter().close();
	}

	private IndexWriter getIndexWriter() {
		try {
			if (!readOnly && indexSearcher != null) {
				indexSearcher.close();
				indexSearcher = null;
			}
			if (indexWriter == null) {
				if (IndexReader.indexExists(getDirectory())) {
					if (!readOnly) {
						if (IndexWriter.isLocked(getDirectory())) {
							IndexWriter.unlock(getDirectory());
						}
						indexWriter = new IndexWriter(getDirectory(),
								getAnalyzer(), MaxFieldLength.UNLIMITED);
					}
				} else {
					if (!readOnly) {
						indexWriter = new IndexWriter(getDirectory(),
								getAnalyzer(), true, MaxFieldLength.UNLIMITED);
					}
				}
			}
			return indexWriter;
		} catch (Exception e) {
			throw new MatrixException("could not prepare writher", e);
		}
	}

	private IndexSearcher getIndexSearcher() {
		try {
			if (!IndexReader.indexExists(getDirectory())) {
				getIndexWriter();
			}
			if (indexWriter != null) {
				indexWriter.commit();
			}
			if (indexSearcher != null
					&& !indexSearcher.getIndexReader().isCurrent()) {
				indexSearcher.close();
				indexSearcher = null;
			}
			if (indexSearcher == null) {
				indexSearcher = new IndexSearcher(directory);
			}
			return indexSearcher;
		} catch (Exception e) {
			throw new MatrixException("could not prepare reader", e);
		}
	}

	private byte[] getBytes(Object o) {
		try {
			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bao);
			oos.writeObject(o);
			oos.close();
			bao.close();
			return bao.toByteArray();
		} catch (Exception e) {
			throw new MatrixException("could not convert to bytes: " + o, e);
		}
	}

	@SuppressWarnings("unchecked")
	private void readObject(ObjectInputStream s) throws IOException,
			ClassNotFoundException {
		s.defaultReadObject();
		while (true) {
			try {
				K k = (K) s.readObject();
				V v = (V) s.readObject();
				put(k, v);
			} catch (OptionalDataException e) {
				return;
			}
		}
	}

	private void writeObject(ObjectOutputStream s) throws IOException,
			MatrixException {
		s.defaultWriteObject();
		for (Object k : keySet()) {
			Object v = get(k);
			s.writeObject(k);
			s.writeObject(v);
		}
	}

	public static void main(String[] args) throws Exception {

		Map<String, Object> map = new LuceneMap<String, Object>();

		System.out.println(map.keySet());
		map.put("key1", "test1");
		System.out.println(map.keySet());
		map.put("key2", new JLabel());
		System.out.println(map.keySet());
		map.put("key1", "test3");
		System.out.println(map.keySet());
		System.out.println(map.get("key1"));
		System.out.println(map.get("key2"));
		map.remove("key1");
		System.out.println(map.size());

	}

	
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Map)) {
			return false;
		}
		Map<?, ?> m2 = (Map<?, ?>) obj;
		for (Object k : keySet()) {
			Object v1 = get(k);
			Object v2 = m2.get(k);
			if (v1 == null && v2 != null) {
				return false;
			}
			if (v1 != null && v2 == null) {
				return false;
			}
			if (!v1.equals(v2)) {
				return false;
			}
		}
		return true;
	}

	
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("{ ");
		for (Object k : keySet()) {
			Object v = get(k);
			s.append(k + ":" + v + " ");
		}
		s.append("}");
		return s.toString();
	}

	
	public synchronized void erase() throws IOException {
		close();
		FileUtil.deleteRecursive(path);
	}

}
