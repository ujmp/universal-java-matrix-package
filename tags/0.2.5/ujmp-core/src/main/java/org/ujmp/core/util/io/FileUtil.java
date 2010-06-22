/*
 * Copyright (C) 2008-2010 by Holger Arndt
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

package org.ujmp.core.util.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.ujmp.core.util.MathUtil;

public class FileUtil {

	public static boolean deleteRecursive(File path) {
		if (path != null && path.exists()) {
			File[] files = path.listFiles();
			for (File f : files) {
				if (f.isDirectory()) {
					deleteRecursive(f);
				} else {
					f.delete();
				}
			}
		}
		if (path != null) {
			return path.delete();
		} else {
			return false;
		}
	}

	// TODO: this can be made faster by reading into a byte buffer
	public static boolean equalsContent(File file1, File file2) throws Exception {
		if (file1.length() != file2.length()) {
			return false;
		}
		boolean areEqual = true;
		BufferedInputStream in1 = new BufferedInputStream(new FileInputStream(file1));
		BufferedInputStream in2 = new BufferedInputStream(new FileInputStream(file2));
		while (true) {
			int i1 = in1.read();
			int i2 = in2.read();
			if (i1 != i2) {
				areEqual = false;
				break;
			}
			if (i1 == -1) {
				break;
			}
		}
		in1.close();
		in2.close();
		return areEqual;
	}

	public static void move(File source, File target) {

	}

	public static String md5Sum(File file) throws Exception {
		return MathUtil.md5(file);
	}

	public static List<List<File>> findDuplicates(File path) throws Exception {
		return findDuplicates(path, new HashMap<String, List<File>>());
	}

	private static List<List<File>> findDuplicates(File path, Map<String, List<File>> md5Map)
			throws Exception {
		List<List<File>> list = new ArrayList<List<File>>();
		File[] files = path.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				List<List<File>> subDirList = findDuplicates(file);
				list.addAll(subDirList);
			} else {
				System.out.print(file);
				String md5 = md5Sum(file);
				System.out.println(" [" + md5 + "]");
				List<File> similarFiles = md5Map.get(md5);
				if (similarFiles == null) {
					similarFiles = new LinkedList<File>();
					md5Map.put(md5, similarFiles);
				}
				if (!similarFiles.isEmpty()) {
					System.out.println("   " + similarFiles.size() + " files with same md5");
					for (File similarFile : similarFiles) {
						if (equalsContent(file, similarFile)) {
							System.out.println("   match found: " + similarFile);
							List<File> set = new LinkedList<File>();
							set.add(file);
							set.add(similarFile);
							list.add(set);
						}
					}
				}
				similarFiles.add(file);
			}
		}
		return list;
	}

	public static int countFiles(File path) {
		int count = 0;
		File[] files = path.listFiles();
		if (files != null) {
			for (File f : files) {
				if (f.isDirectory()) {
					count += countFiles(f);
				} else {
					count++;
				}
			}
		}
		return count;
	}

}
