package com.cypher.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by adlerd on 3/16/17.
 */
public class FileUtil {
    public static void writeToFile(byte[] bytes, File file) throws IOException{
        final FileOutputStream outputStream = new FileOutputStream(file);
        outputStream.write(bytes);
    }
}
