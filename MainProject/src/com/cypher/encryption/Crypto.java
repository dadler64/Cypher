package com.cypher.encryption;

import java.security.Key;

/**
 * Created by adlerd on 4/9/17.
 */
public interface Crypto {

    public String getAlgorithm();

    public void setAlgorithm(String algorithm);

    public String getMode();

    public void setMode(String mode);

    public String getPadding();

    public void setPadding(String padding);

    String encryptString(final KeyFile key) throws Exception;

    String decryptString(String in, final KeyFile key) throws Exception;
}
