package com.cypher.encryption;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

import static java.lang.System.out;

/**
 * Created by adlerd on 4/4/17.
 */

@SuppressWarnings("Duplicates")
public class CryptoImpl implements Crypto{

    Cipher cipher;
    IvParameterSpec ivspec;
    private String str;
    private String encrypted;
    private String decrypted;
    private String algorithm = "AES";
    private String mode = "CBC";
    private String padding = "PKCS5Padding";
    private String encryptionMethod = algorithm + "/" + mode + "/" + padding;
    private SecretKeySpec secretKey;
    private byte[] data;
    private byte[] iv = "wTmg4qj8dNszs2ji".getBytes(); // build the initialization vector using the default salt.





    /**
     * Constructor for encrypting a String
     *
     * @param data
     */
    public CryptoImpl(final String data) {
        this.str = data;
    }


    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getPadding() {
        return padding;
    }

    public void setPadding(String padding) {
        this.padding = padding;
    }


    /**
     * Method to encrypt input into a string
     *
     * @param key The encryption key generated to guide the encryption
     * @return
     * @throws Exception For any of the many exceptions that could be thrown
     */
    public String encryptString(final KeyFile key) throws Exception {
//        data = str.getBytes();
        cipher = Cipher.getInstance(encryptionMethod);
        ivspec = new IvParameterSpec(iv);
        secretKey = new SecretKeySpec(key.wrappedKey().getBytes(), algorithm);

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
        encrypted = Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes()));
//        return new String(cipher.doFinal(str.getBytes()));
        return encrypted;
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
        data = Base64.getDecoder().decode(in);
        cipher = Cipher.getInstance(encryptionMethod);
        ivspec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
        return new String(cipher.doFinal(data));
    }
}
