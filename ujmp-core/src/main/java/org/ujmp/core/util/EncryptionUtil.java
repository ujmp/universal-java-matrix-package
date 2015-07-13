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

package org.ujmp.core.util;

import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public abstract class EncryptionUtil {

	public static final byte[] IV16 = new byte[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13,
			14, 15 };

	public static byte[] aesEncrypt(byte[] input, Key key) throws Exception {
		return aesEncrypt(input, key, IV16);
	}

	public static byte[] aesEncrypt(byte[] input, byte[] key) throws Exception {
		return aesEncrypt(input, new SecretKeySpec(key, "AES"), IV16);
	}

	public static byte[] aesEncrypt(byte[] input, byte[] key, byte[] iv) throws Exception {
		return aesEncrypt(input, new SecretKeySpec(key, "AES"), iv);
	}

	public static byte[] aesEncrypt(byte[] input, Key secret, byte[] iv) throws Exception {
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, secret, paramSpec);
		return cipher.doFinal(input);
	}

	public static byte[] aesDecrypt(byte[] input, Key key) throws Exception {
		return aesDecrypt(input, key, IV16);
	}

	public static byte[] aesDecrypt(byte[] input, byte[] key) throws Exception {
		return aesDecrypt(input, new SecretKeySpec(key, "AES"), IV16);
	}

	public static byte[] aesDecrypt(byte[] input, byte[] key, byte[] iv) throws Exception {
		return aesDecrypt(input, new SecretKeySpec(key, "AES"), iv);
	}

	public static byte[] aesDecrypt(byte[] input, Key secret, byte[] iv) throws Exception {
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, secret, paramSpec);
		return cipher.doFinal(input);
	}

}
