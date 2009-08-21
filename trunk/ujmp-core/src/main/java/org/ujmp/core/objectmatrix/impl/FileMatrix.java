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

package org.ujmp.core.objectmatrix.impl;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.enums.FileFormat;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.mapmatrix.AbstractMapMatrix;
import org.ujmp.core.util.MathUtil;

public class FileMatrix extends AbstractMapMatrix<String, Object> {
	private static final long serialVersionUID = 7869997158743678080L;

	public static final String CONTENT = "Content";

	public static final String TEXT = "Text";

	public static final String ID = "Id";

	public static final String BYTES = "Bytes";

	public static final String SIZE = "Size";

	public static final String CANREAD = "CanRead";

	public static final String CANWRITE = "CanWrite";

	public static final String ISHIDDEN = "IsHidden";

	public static final String ISFILE = "IsFile";

	public static final String ISDIRECTORY = "IsDirectory";

	public static final String LASTMODIFIED = "LastModified";

	public static final String FILENAME = "FileName";

	public static final String CANEXECUTE = "CanExecute";

	public static final String PATH = "Path";

	public static final String EXTENSION = "Extension";

	public static final String FILEFORMAT = "FileFormat";

	public static final String MD5 = "MD5";

	private Map<String, Object> map = null;

	public FileMatrix(File file, Object... parameters) throws IOException {
		map = new FileMap(file, parameters);
	}

	public FileMatrix(FileFormat fileFormat, File file, Object... parameters) throws IOException {
		map = new FileMap(fileFormat, file, parameters);
	}

	
	public Map<String, Object> getMap() {
		return map;
	}

	class FileMap implements Map<String, Object>, Serializable {
		private static final long serialVersionUID = -4946966403241068247L;

		private File file = null;

		private Map<String, Object> map = null;

		private transient SoftReference<Matrix> content = null;

		private transient SoftReference<Matrix> bytes = null;

		private FileFormat fileformat = null;

		private Object[] parameters = null;

		public FileMap(File file, Object... paramegters) throws IOException {
			this(null, file, paramegters);
		}

		public FileMap(FileFormat fileFormat, File file, Object... paramegters) throws IOException {
			if (fileFormat == null) {
				this.fileformat = FileFormat.guess(file);
			} else {
				this.fileformat = fileFormat;
			}
			this.parameters = paramegters;
			this.file = file;
			this.map = new HashMap<String, Object>();
			this.content = new SoftReference<Matrix>(null);
			map.put(CONTENT, null);
			map.put(MD5, null);
			map.put(ID, file.getAbsolutePath());
			map.put(PATH, file.getPath());
			map.put(FILENAME, file.getName());
			map.put(BYTES, null);
			String[] components = file.getName().split("\\.");
			if (components.length > 1) {
				map.put(EXTENSION, components[components.length - 1]);
			} else {
				map.put(EXTENSION, null);
			}
			map.put(CANREAD, file.canRead());
			map.put(CANWRITE, file.canWrite());
			map.put(ISHIDDEN, file.isHidden());
			map.put(ISDIRECTORY, file.isDirectory());
			map.put(ISFILE, file.isFile());
			map.put(LASTMODIFIED, file.lastModified());
			map.put(SIZE, file.length());
			map.put(FILEFORMAT, this.fileformat);
		}

		
		public void clear() {
			map.clear();
		}

		
		public boolean containsKey(Object key) {
			return map.containsKey(key);
		}

		
		public boolean containsValue(Object value) {
			return map.containsValue(value);
		}

		
		public Set<java.util.Map.Entry<String, Object>> entrySet() {
			throw new MatrixException("not implemented");
		}

		
		public Object get(Object key) {
			if (CONTENT.equals(key)) {
				if (fileformat == null) {
					return null;
				}
				Matrix m = null;
				if (content == null || content.get() == null) {
					try {
						m = MatrixFactory.linkToFile(fileformat, file, parameters);
						content = new SoftReference<Matrix>(m);
					} catch (Exception e) {
						throw new MatrixException(e);
					}
				}
				return content.get();
			} else if (MD5.equals(key)) {
				String md5 = (String) map.get(MD5);
				if (md5 == null) {
					try {
						md5 = MathUtil.getMD5Sum(file);
						map.put((String) key, md5);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				return md5;
			} else if (BYTES.equals(key)) {
				if (bytes == null || bytes.get() == null) {
					try {
						bytes = new SoftReference<Matrix>(MatrixFactory.linkToFile(FileFormat.RAW,
								file));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				return bytes.get();
			}
			return map.get(key);
		}

		
		public boolean isEmpty() {
			return map.isEmpty();
		}

		
		public Set<String> keySet() {
			return map.keySet();
		}

		
		public Object put(String key, Object value) {
			return map.put(key, value);
		}

		
		public void putAll(Map<? extends String, ? extends Object> m) {
			map.putAll(m);
		}

		
		public Object remove(Object key) {
			return map.remove(key);
		}

		
		public int size() {
			return map.size();
		}

		
		public Collection<Object> values() {
			throw new MatrixException("not implemented");
		}

	}

}
