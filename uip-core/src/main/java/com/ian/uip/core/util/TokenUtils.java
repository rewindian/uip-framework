package com.ian.uip.core.util;

import com.ian.uip.core.exception.CustomException;

import java.security.MessageDigest;
import java.util.UUID;

public final class TokenUtils {

    private TokenUtils() {
    }

    public static String generateToken() {
        return generateValue(UUID.randomUUID().toString());
    }

    private static final char[] hexCode = "0123456789abcdef".toCharArray();

    private static String toHexString(byte[] data) {
        if (data == null) {
            return null;
        }
        StringBuilder r = new StringBuilder(data.length * 2);
        for (byte b : data) {
            r.append(hexCode[(b >> 4) & 0xF]);
            r.append(hexCode[(b & 0xF)]);
        }
        return r.toString();
    }

    private static String generateValue(String param) {
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(param.getBytes());
            byte[] messageDigest = algorithm.digest();
            return toHexString(messageDigest);
        } catch (Exception e) {
            throw new CustomException("生成Token失败", e);
        }
    }
}
