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

package org.ujmp.core.util.io;

public interface ByteBufferConcatenation {

	public long getLength();

	public byte getByte(final long position);

	public void setByte(final byte b, final long position);

	public void getBytes(final byte[] bytes, final long position);

	public void setBytes(final byte[] bytes, final long position);

	public void getBytes(final byte[] bytes, final long position, final int length);

	public void deleteByte(final long position);

	public void deleteBytes(final long position, final int count);

	public void setBytes(final byte[] bytes, final long position, final int length);

	public void insertBytes(final byte[] bytes, final long position);

	public void appendBytes(final byte[] bytes);

	public void expand(final long bytesToAdd);

	public void shrink(final long bytesToRemove);

}
