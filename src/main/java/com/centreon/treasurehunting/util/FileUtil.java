package com.centreon.treasurehunting.util;

import com.centreon.treasurehunting.exception.FileException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.centreon.treasurehunting.exception.FileException.ERROR_WHILE_READING;

public class FileUtil {

    public static List<String> readFile(String fileName) {

        List<String> lines;

        try {
            lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
        } catch (Exception exception) {
            throw new FileException(ERROR_WHILE_READING, exception);
        }

        return lines;
    }

    public static Path createFile() throws IOException {
        Path filePath = Paths.get("results");
        Files.deleteIfExists(filePath);
        return Files.createFile(filePath);
    }

}
