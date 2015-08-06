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

package org.ujmp.core.filematrix;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import org.ujmp.core.Matrix;
import org.ujmp.core.listmatrix.AbstractListMatrix;
import org.ujmp.core.mapmatrix.AbstractMapMatrix;
import org.ujmp.core.util.VerifyUtil;

public class DirectoryMatrix extends AbstractMapMatrix<String, Matrix> implements FileOrDirectoryMatrix {
    private static final long serialVersionUID = -4912495890644097086L;

    private final File path;

    public DirectoryMatrix() {
        this((File) null);
    }

    @Override
    protected synchronized void clearMap() {
        // TODO: delete all
    }

    @Override
    protected synchronized Matrix removeFromMap(Object key) {
        // TODO: delete
        return null;
    }

    @Override
    protected synchronized Matrix putIntoMap(String key, Matrix value) {
        try {
            Matrix old = get(key);
            value.exportTo().file(path.getAbsolutePath() + File.separator + key).asSerialized();
            return old;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public DirectoryMatrix(String path) {
        this(new File(path));
    }

    public DirectoryMatrix(File path) {
        this.path = path;
        if (path == null) {
            setLabel("/");
        } else {
            if (path.getParent() == null) {
                setLabel(path.getAbsolutePath());
            } else {
                setLabel(path.getName());
            }
            setMetaData(PATH, path.getPath());
            setMetaData(FILENAME, path.getName());
            setMetaData(CANEXECUTE, path.canExecute());
            setMetaData(CANREAD, path.canRead());
            setMetaData(CANWRITE, path.canWrite());
            setMetaData(ISHIDDEN, path.isHidden());
            setMetaData(ISDIRECTORY, path.isDirectory());
            setMetaData(ISFILE, path.isFile());
            setMetaData(LASTMODIFIED, path.lastModified());
            setMetaData(SIZE, path.length());
        }
    }


    public synchronized int size() {
        if (path == null) {
            return File.listRoots().length;
        } else {
            File[] files = path.listFiles();
            return files == null ? 0 : files.length;
        }
    }

    public synchronized Matrix get(Object key) {
        VerifyUtil.verifyNotNull(key);
        File file = new File(path.getName() + File.separator + key);
        if (file.exists()) {
            if (file.isFile()) {
                return new FileMatrix(file);
            } else {
                return new DirectoryMatrix(file);
            }
        } else {
            return null;
        }
    }

    public synchronized Set<String> keySet() {
        // TODO: avoid creation of set every time
        Set<String> files = new TreeSet<String>();
        if (path == null) {
            for (File f : File.listRoots()) {
                files.add(f.getName());
            }
        } else {
            for (File f : path.listFiles()) {
                files.add(f.getName());
            }
        }
        return files;
    }
}
