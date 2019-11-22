package com.cypher.encryption;

/**
 * Created by adlerd on 4/9/17.
 */
public interface Crypto {

    String getAlgorithm();

    void setAlgorithm(String algorithm);

    String getMode();

    void setMode(String mode);

    String getPadding();

    void setPadding(String padding);

    String encryptString(final KeyFile key) throws Exception;

    String decryptString(String in, final KeyFile key) throws Exception;
}
