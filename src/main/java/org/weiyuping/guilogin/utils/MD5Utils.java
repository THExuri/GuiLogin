package org.weiyuping.guilogin.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
    static final char[] hex;

    public static String encrypt(final String plainText, final String saltValue) {
        try {
            final MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            md.update(saltValue.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        }
        catch (final NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String encrypt(final String plainText) {
        try {
            final MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(plainText.getBytes(StandardCharsets.UTF_8));
            final byte[] md = mdTemp.digest();
            final int j = md.length;
            final char[] str = new char[j * 2];
            int k = 0;
            for (final byte byte0 : md) {
                str[k++] = MD5Utils.hex[byte0 >>> 4 & 0xF];
                str[k++] = MD5Utils.hex[byte0 & 0xF];
            }
            return new String(str);
        }
        catch (final Exception e) {
            return null;
        }
    }

    public static boolean valid(final String text, final String md5) {
        return md5.equals(encrypt(text));
    }

    static {
        hex = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    }
}
