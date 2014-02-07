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

package org.ujmp.core.util;

public class Complex {

	final double real;
	final double img;

	public Complex(final double real, final double img) {
		this.real = real;
		this.img = img;
	}

	public final double abs() {
		return Math.sqrt(real * real + img * img);
	}

	public final Complex times(final Complex c2) {
		final double real2 = this.real * c2.real - this.img * c2.img;
		final double img2 = this.img * c2.real + this.real * c2.img;
		return new Complex(real2, img2);
	}

	public final Complex plus(final Complex c2) {
		final double real = this.real + c2.real;
		final double img = this.img + c2.img;
		return new Complex(real, img);
	}

	public final Complex minus(final Complex c2) {
		final double real = this.real - c2.real;
		final double img = this.img - c2.img;
		return new Complex(real, img);
	}

}
