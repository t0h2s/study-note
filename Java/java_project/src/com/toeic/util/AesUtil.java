package com.toeic.util;

import java.security.SecureRandom;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

public class AesUtil {

    private static final int keySize = 128;
    private static final int iterationCount = 10000;
    private static String salt = "d04f26c091f93abadb2dbe4b2573129cb64396925052a3afc983312a95428146";
    private static String iv = "6832fa3c32b249d260a3f4d96f485c6a";
    private static final String passPhrase = "passPhrase";

    private final Cipher cipher;
    
    
    public AesUtil() {
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    
    public String encrypt(String plaintext) {
    	String result = "";
    	try {
    		result = encrypt(salt, iv, passPhrase, plaintext);
    	} catch(Exception e) {
    	}
        return result;
    }
    
    
    public String decrypt(String ciphertext) throws Exception {
    	String result = "";
    	try {
    		result = decrypt(salt, iv, passPhrase, ciphertext);
    	} catch(Exception e) {
    	}
        return result;
    }
    
    
    private String encrypt(String salt, String iv, String passPhrase, String plaintext) throws Exception {
        SecretKey key = generateKey(salt, passPhrase);
        byte[] encrypted = doFinal(Cipher.ENCRYPT_MODE, key, iv, plaintext.getBytes("UTF-8"));
        return encodeBase64(encrypted);
    }

    
    private String decrypt(String salt, String iv, String passPhrase, String ciphertext) throws Exception {
        SecretKey key = generateKey(salt, passPhrase);
        byte[] decrypted = doFinal(Cipher.DECRYPT_MODE, key, iv, decodeBase64(ciphertext));
        return new String(decrypted, "UTF-8");
    }

    
    private byte[] doFinal(int encryptMode, SecretKey key, String iv, byte[] bytes) throws Exception {
        cipher.init(encryptMode, key, new IvParameterSpec(decodeHex(iv)));
        return cipher.doFinal(bytes);
    }

    
    private SecretKey generateKey(String salt, String passPhrase) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec spec = new PBEKeySpec(passPhrase.toCharArray(), decodeHex(salt), iterationCount, keySize);
        SecretKey key = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
        return key;
    }
    
    
    private static String encodeBase64(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }

    
    private static byte[] decodeBase64(String str) {
        return Base64.decodeBase64(str);
    }

    
    private static String encodeHex(byte[] bytes) {
        return Hex.encodeHexString(bytes);
    }

    
    private static byte[] decodeHex(String str) throws Exception {
        return Hex.decodeHex(str.toCharArray());
    }
    
    
    public static String getRandomHexString(int length) {
        byte[] salt = new byte[length];
        new SecureRandom().nextBytes(salt);
        return encodeHex(salt);
    }
}