package org.amit.utils;

import java.io.File;

public class FileUtils {
    public static String getJsonFilePath() {
        try {
            String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
                    + File.separator + "java" + File.separator + "testData";
            return path;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
