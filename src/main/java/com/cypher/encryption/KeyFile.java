package com.cypher.encryption;

/**
 * Created by adlerd on 3/2/17.
 */

import static java.lang.System.err;
import static java.lang.System.out;

import com.cypher.utils.FileUtil;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class KeyFile {
    private String rawKey;
    private String salt; // MUST be 128 bits (16 characters)
    private int keyID;

    public KeyFile(String rawKey, int keyID) {
        this.rawKey = rawKey;
        this.keyID = keyID;
        this.salt = "wTmg4qj8dNszs2ji"; // Default salt
    }

    public KeyFile(String rawKey, int keyID, String salt) {
        this.rawKey = rawKey;
        this.keyID = keyID;
        if (salt.length() == 16) // Check if inputted salt is 128 bits
            this.salt = salt;
        else
            this.salt = "wTmg4qj8dNszs2ji";
    }

    // Set custom salt
    public void setSalt(String salt){
        if (salt.length() == 16) // Check if inputted salt is 128 bits
            this.salt = salt;
        else
            err.print("\n[ERROR] Salt MUST be 128 bits (16 characters)! \n");
    }

    private String getKey() throws NoSuchAlgorithmException {
        String output;
        String keySalt = String.format("%s:%s", rawKey, salt);

        // Hide user password
        out.print("[DEBUG] <KeyFile> Key-Salt: ");
        for (char c : rawKey.toCharArray())
            out.print("*");
        out.printf(":%s%n", salt);

//        out.printf("[DEBUG] <KeyFile> Key-Salt: %s:%s%n", rawKey, salt);

        output = Base64.getEncoder().encodeToString(keySalt.getBytes());
        out.printf("[DEBUG] <KeyFile> Encoded Key-Salt: %s%n", output);
        output = output.substring(0, 16).trim();   // Get the first 16 characters of the output
        out.printf("[DEBUG] <KeyFile> Trimmed Encoded Key-Salt: %s%n", output);
        return output;
    }

    // For catching the 'NoSuchAlgorithmException'
    public String wrappedKey() {
        try {
            return getKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Write the key to a file
    public void writeToFile(String path) throws IOException{
        final File file = new File(path, String.format("key%s.key", this.keyID));
        FileUtil.writeToFile(this.wrappedKey().getBytes(), file);
    }
}