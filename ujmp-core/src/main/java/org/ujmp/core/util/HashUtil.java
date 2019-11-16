package org.ujmp.core.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class HashUtil {

    public static byte[] getSHA128(String string) {
        return getSHA128(string.getBytes(StandardCharsets.UTF_8));
    }

    public static byte[] getSHA128(byte[] data) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-128");
            return md.digest(data);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getSHA128String(String string) {
        byte[] bytes = getSHA128(string);
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public static String getSHA128String(byte[] data) {
        byte[] bytes = getSHA128(data);
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public static byte[] getSHA256(String string) {
        return getSHA256(string.getBytes(StandardCharsets.UTF_8));
    }

    public static byte[] getSHA256(byte[] data) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return md.digest(data);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getSHA256String(String string) {
        byte[] bytes = getSHA256(string);
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public static String getSHA256String(byte[] data) {
        byte[] bytes = getSHA256(data);
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public static byte[] getSHA512(String string) {
        return getSHA512(string.getBytes(StandardCharsets.UTF_8));
    }

    public static byte[] getSHA512(byte[] data) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            return md.digest(data);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getSHA512String(String string) {
        byte[] bytes = getSHA512(string);
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public static String getSHA512String(byte[] data) {
        byte[] bytes = getSHA512(data);
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

}
