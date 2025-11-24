package org.gallup.access.utils;

import java.nio.file.Files;
import java.nio.file.Paths;

public class PayloadReader {
    public static String getPayload(String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (Exception e) {
            throw new RuntimeException("Failed to read payload file", e);
        }
    }

}
