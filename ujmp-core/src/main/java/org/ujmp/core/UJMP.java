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

package org.ujmp.core;

import org.ujmp.core.filematrix.DirectoryMatrix;
import org.ujmp.core.listmatrix.ListMatrix;
import org.ujmp.core.util.UJMPSettings;

public class UJMP {

    public static final String UJMPVERSION = "0.3.1-SNAPSHOT";
    public static final String UJMPLOCATION = "https://oss.sonatype.org/content/repositories/snapshots/org/ujmp/ujmp-core/0.3.1-SNAPSHOT/ujmp-core-0.3.1-SNAPSHOT-20141019.102010-5.jar";
    public static final String UJMPJARNAME = "ujmp-core-" + UJMP.UJMPVERSION + ".jar";

    private final Matrix workspace;
    private Matrix settings;

    private UJMP(Matrix workspace) {
        this.workspace = workspace;
        System.out.println(workspace.getRowCount());
        if (workspace instanceof ListMatrix) {
            ListMatrix lm = (ListMatrix) workspace;
            for (int i = 0; i < lm.size(); i++) {
                Object o = lm.get(i);
                if (o != null && o instanceof Matrix) {
                    Matrix m = (Matrix) o;
                    if ("ujmp.properties".equals(m.getLabel())) {
                        settings = m;
                    }
                }
            }
            if (settings == null) {
                settings = UJMPSettings.getInstance();
                lm.add(settings);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Welcome to the Universal Java Matrix Package (UJMP) v" + UJMPVERSION);
        System.out.println();
        System.out.println("UJMP is not a standalone program, but a Java library.");
        System.out.println("You can use it for matrix calculations in your own applications.");
        System.out.println("If you would like to find out more, please take a look at the");
        System.out.println("homepage of UJMP at https://ujmp.org/");
        System.out.println();
        newInstance();
    }


    public static UJMP newInstance() {
        return newInstance(System.getProperties().getProperty("user.home") + "/.ujmp/");
    }

    public static UJMP newInstance(String workspaceDirectory) {
        return newInstance(new DirectoryMatrix(workspaceDirectory));
    }

    private static UJMP newInstance(Matrix workspace) {
        return new UJMP(workspace);
    }
}
