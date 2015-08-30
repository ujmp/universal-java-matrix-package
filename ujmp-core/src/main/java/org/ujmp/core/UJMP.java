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


import org.ujmp.core.benchmark.DefaultDenseDoubleMatrix2DBenchmark;
import org.ujmp.core.util.UJMPSettings;

public class UJMP {

    public static final String UJMPVERSION = "0.3.0";
    public static final String UJMPJARNAME = "ujmp-complete-" + UJMP.UJMPVERSION + ".jar";
    public static final String UJMPLOCATION = "https://github.com/ujmp/universal-java-matrix-package/releases/download/" + UJMPVERSION + "/" + UJMPJARNAME;

    protected UJMP() throws Exception {
        this(UJMPSettings.getInstance());
    }

    protected UJMP(UJMPSettings ujmpSettings) throws Exception {
        if (ujmpSettings == null) {
            ujmpSettings = UJMPSettings.getInstance();
        }
        System.out.println("Welcome to the Universal Java Matrix Package (UJMP) v" + UJMPVERSION);
        System.out.println();
        System.out.println("UJMP is not a standalone program, but a Java library.");
        System.out.println("You can use it for matrix calculations in your own applications.");
        System.out.println("If you would like to find out more, please take a look at the");
        System.out.println("homepage of UJMP at https://ujmp.org/");
        System.out.println();

        System.out.println("These are the current settings:");
        System.out.println(ujmpSettings);

        if ("benchmark".equals(ujmpSettings.getAction())) {
            DefaultDenseDoubleMatrix2DBenchmark.main(null);
        }
    }


    protected UJMP(String... args) throws Exception {
        this(UJMPSettings.parse(args));
    }

    public static void main(String[] args) throws Exception {
        newInstance(args);
    }


    public static UJMP newInstance() throws Exception {
        return new UJMP();
    }

    public static UJMP newInstance(UJMPSettings ujmpSettings) throws Exception {
        return new UJMP(ujmpSettings);
    }


    public static UJMP newInstance(String... args) throws Exception {
        return new UJMP(args);
    }

}
