package com.zlgewj.generator;
import java.security.SecureRandom;

public class KeyGenerator {

    public static void main(String[] args) {
        byte[] key = generate128BitKey();
        System.out.println("Generated Key (Hex): " + bytesToHex(key));
    }

    private static byte[] generate128BitKey() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[16];
        secureRandom.nextBytes(key);
        return key;
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexStringBuilder = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            hexStringBuilder.append(String.format("%02X", b));
        }
        return hexStringBuilder.toString();
    }
}