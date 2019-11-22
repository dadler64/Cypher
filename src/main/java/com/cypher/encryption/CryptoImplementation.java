package com.cypher.encryption;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by adlerd on 4/4/17.
 */

@SuppressWarnings("Duplicates")
public class CryptoImplementation implements Crypto{

    private Cipher cipher;
    private IvParameterSpec parameterSpec;
    private String message;
    private static String algorithm = "AES";
    private static String mode = "CBC";
    private static String padding = "PKCS5Padding";
    private static final String ENCRYPTION_METHOD = algorithm + "/" + mode + "/" + padding;
    private SecretKeySpec secretKey;
    private byte[] saltAsBytes = "wTmg4qj8dNszs2ji".getBytes(); // build the initialization vector using the default salt.

    /**
     * Constructor for encrypting a String
     *
     * @param data
     */
    public CryptoImplementation(final String data) {
        this.message = data;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        CryptoImplementation.algorithm = algorithm;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        CryptoImplementation.mode = mode;
    }

    public String getPadding() {
        return padding;
    }

    public void setPadding(String padding) {
        CryptoImplementation.padding = padding;
    }

    /**
     * Method to encrypt input into a string
     *
     * @param key The encryption key generated to guide the encryption
     * @return
     * @throws Exception For any of the many exceptions that could be thrown
     */
    public String encryptString(final KeyFile key) throws Exception {
        cipher = Cipher.getInstance(ENCRYPTION_METHOD);
        parameterSpec = new IvParameterSpec(saltAsBytes);
        secretKey = new SecretKeySpec(key.wrappedKey().getBytes(), algorithm);

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);
        return Base64.getEncoder().encodeToString(cipher.doFinal(message.getBytes()));
    }

    /**
     * Method to decrypt input into a String
     *
     * @param key The encryption key generated to guide the encryption
     * @return
     * @throws Exception For any of the many exceptions that could be thrown
     */
    public String decryptString(String in, final KeyFile key) throws Exception {
        // NOTE: decrypting was fixed by introducing String 'in' as a parameter
        byte[] data = Base64.getDecoder().decode(in);
        cipher = Cipher.getInstance(ENCRYPTION_METHOD);
        parameterSpec = new IvParameterSpec(saltAsBytes);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec);
        return new String(cipher.doFinal(data));
    }
}
