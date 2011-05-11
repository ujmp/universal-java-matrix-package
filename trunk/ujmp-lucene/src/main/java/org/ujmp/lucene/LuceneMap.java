/*
 * Copyright (C) 2008-2011 by Holger Arndt
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
import java.util.HashSet;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.ujmp.core.collections.AbstractMap;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.Erasable;
import org.ujmp.core.objectmatrix.ObjectMatrix2D;
import org.ujmp.core.util.StringUtil;
import org.ujmp.core.util.io.FileUtil;

public class LuceneMap<K, V> extends AbstractMap<K, V> implements Flushable,
		Closeable, Erasable {
	private static final long serialVersionUID = 8998898900190996038L;

	private static final String KEYSTRING = "KS";

	private static final String KEYDATA = "KD";

	private static final String VALUESTRING = "VS";

	private static final String VALUEDATA = "VD";

	private transient Directory directory = null;

	private transient IndexWriter indexWriter = null;

	private transient IndexSearcher indexSearcher = null;

	private static final int MAXSIZE = 1000000;

	private static final int AUTOFLUSHCOUNT = 100000;

	private boolean readOnly = false;

	private transient File path = null;

	private transient Analyzer analyzer = null;

	private int count = 0;

	public LuceneMap() throws IOException {
		this(null, false);
	}

	public LuceneMap(String dir) throws IOException {
		this(new File(dir));
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
			directory = FSDirectory.open(getPath());
		}
		return directory;
	}

	public File getPath() throws IOException {
		if (path == null) {
			path = File.createTempFile("lucene", "");
			path.delete();
			path.mkdir();
		}
		return path;
	}

	public synchronized void optimize() throws CorruptIndexException,
			IOException {
		getIndexWriter().optimize();
	}

	public synchronized void clear() {
		try {
			getIndexWriter().deleteAll();
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

	public synchronized ObjectMatrix2D search(String searchString) {
		try {
			MultiFieldQueryParser p = new MultiFieldQueryParser(
					Version.LUCENE_31, new String[] { VALUESTRING },
					getAnalyzer());
			Query query = p.parse(searchString);
			TopDocs docs = getIndexSearcher().search(query, 100);
			ObjectMatrix2D result = ObjectMatrix2D.factory.zeros(
					docs.totalHits, 3);
			for (int row = 0; row < docs.totalHits; row++) {
				ScoreDoc match = docs.scoreDocs[row];
				Document doc = getIndexSearcher().doc(match.doc);
				result.setAsFloat(match.score, row, 0);
				result.setAsObject(
						getObjectFromBytes(doc.getBinaryValue(KEYDATA)), row, 1);
				result.setAsObject(
						getObjectFromBytes(doc.getBinaryValue(VALUEDATA)), row,
						2);
			}
			return result;
		} catch (Exception e) {
			throw new MatrixException("could not search documents: "
					+ searchString, e);
		}
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
		} else if (o instanceof String) {
			return (String) o;
		} else {
			return StringUtil.encodeToHex((Serializable) o);
		}
	}

	public synchronized V put(K key, V value) {
		try {
			Term term = new Term(KEYSTRING, getUniqueString(key));
			Document doc = new Document();
			doc.add(new Field(KEYSTRING, getUniqueString(key), Field.Store.YES,
					Field.Index.NOT_ANALYZED));
			doc.add(new Field(KEYDATA, getBytes(key)));
			doc.add(new Field(VALUESTRING, getUniqueString(value),
					Field.Store.YES, Field.Index.NOT_ANALYZED));
			doc.add(new Field(VALUEDATA, getBytes(value)));
			getIndexWriter().updateDocument(term, doc);

			// auto flush from time to time
			if (++count % AUTOFLUSHCOUNT == 0) {
				flush();
			}
			return null;
		} catch (Exception e) {
			throw new MatrixException("could not add document: " + key, e);
		}
	}

	public synchronized V remove(Object key) {
		try {
			Term term = new Term(KEYSTRING, getUniqueString(key));
			getIndexWriter().deleteDocuments(term);
			// auto flush from time to time
			if (++count % AUTOFLUSHCOUNT == 0) {
				flush();
			}
			return null;
		} catch (Exception e) {
			throw new MatrixException("could not delete document: " + key, e);
		}
	}

	public Analyzer getAnalyzer() {
		if (analyzer == null) {
			analyzer = new StandardAnalyzer(Version.LUCENE_31);
		}
		return analyzer;
	}

	public void setAnalyzer(Analyzer analyzer) {
		this.analyzer = analyzer;
	}

	public synchronized int size() {
		try {
			flush();
			if (indexSearcher != null
					&& indexSearcher.getIndexReader().isCurrent()) {
				return indexSearcher.getIndexReader().numDocs();
			} else {
				int size = getIndexWriter().numDocs();
				return size;
			}
		} catch (Exception e) {
			throw new MatrixException("could not count documents", e);
		}
	}

	public synchronized void flush() throws IOException {
		IndexWriter iw = getIndexWriter();
		iw.expungeDeletes(true);
		iw.commit();
		iw.close(true);
		indexWriter = null;

	}

	public synchronized void close() throws IOException {
		if (indexSearcher != null) {
			indexSearcher.close();
			indexSearcher = null;
		}
		if (indexWriter != null) {
			indexWriter.close(true);
			indexWriter = null;
		}

	}

	private synchronized IndexWriter getIndexWriter() {
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
								new IndexWriterConfig(Version.LUCENE_31,
										getAnalyzer()));
					}
				} else {
					if (!readOnly) {
						indexWriter = new IndexWriter(getDirectory(),
								new IndexWriterConfig(Version.LUCENE_31,
										getAnalyzer()));
					}
				}
			}
			return indexWriter;
		} catch (Exception e) {
			throw new MatrixException("could not prepare writher", e);
		}
	}

	private synchronized IndexSearcher getIndexSearcher() {
		try {
			if (!IndexReader.indexExists(getDirectory())) {
				getIndexWriter();
			}
			if (indexWriter != null) {
				if (indexSearcher != null) {
					indexSearcher.close();
					indexSearcher = null;
				}
				indexWriter.commit();
				indexWriter.waitForMerges();
				indexWriter.expungeDeletes(true);
				indexWriter.close();
				indexWriter = null;
			}
			if (indexSearcher != null) {
				if (!indexSearcher.getIndexReader().isCurrent()) {
					indexSearcher.close();
					indexSearcher = null;
				}
			}
			if (indexSearcher == null) {
				indexSearcher = new IndexSearcher(directory, true);
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

	public synchronized void erase() throws IOException {
		close();
		FileUtil.deleteRecursive(path);
	}

}
