/**
 * Created by adlerd on 4/9/17.
 */

import static java.lang.System.err;

import java.security.InvalidKeyException;
import java.security.Key;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;

public class TestAES
{
    private String text = "Hello World";
    private String key = "Bar12345Bar12345"; // 128 bit key
    // Create key and cipher
    private Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
    private Cipher cipher;
    private byte[] encrypted;

    private void run() {
        try {
            cipher = Cipher.getInstance("AES");
            encrypt();
            decrypt();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private void encrypt() throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        // encrypt the text
        cipher.init(Cipher.ENCRYPT_MODE, aesKey);
        encrypted = cipher.doFinal(text.getBytes());
        err.printf("[DEBUG] <TestAES> Encoded Key-Salt: %s%n", new String(encrypted));

    }

    private void decrypt() throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        // decrypt the text
        cipher.init(Cipher.DECRYPT_MODE, aesKey);
        String decrypted = new String(cipher.doFinal(encrypted));
        err.printf("[DEBUG] <TestAES> Decrypted Method: %s%n", new String(encrypted));

    }

    public static void main(String... args) {
        TestAES app = new TestAES();
        app.run();
    }
}
