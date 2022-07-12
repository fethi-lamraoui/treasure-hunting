package com.centreon.treasurehunting.util;

import com.centreon.treasurehunting.exception.FileException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.centreon.treasurehunting.exception.FileException.ERROR_WHILE_READING;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class FileUtilTest {

    @Test
    void readFile_should_throw_exception_when_filename_is_null() {
        Exception exception = assertThrows(FileException.class, () -> {
            FileUtil.readFile(null);
        });

        assertEquals(ERROR_WHILE_READING, exception.getMessage());
    }

    @Test
    void readFile_should_throw_exception_when_filename_is_empty() {
        Exception exception = assertThrows(FileException.class, () -> {
            FileUtil.readFile("");
        });

        assertEquals(ERROR_WHILE_READING, exception.getMessage());
    }

    @Test
    void readFile_should_throw_exception_when_filename_is_innexistant() {
        Exception exception = assertThrows(FileException.class, () -> {
            FileUtil.readFile("innexistant");
        });

        assertEquals(ERROR_WHILE_READING, exception.getMessage());
    }

    @Test
    void readFile_happy_path() {
        List<String> expectedLines = List.of("John 1-1 E AADADAGA", "Doe 1-2 S AAGAAAAGAGADAA");
        List<String> lines = FileUtil.readFile("src/test/resources/adventurers");
        assertEquals(expectedLines, lines);
    }

    @Test
    void createFile() throws IOException {
        Path filePath = Paths.get("results");
        FileUtil.createFile();
        assertEquals(true, Files.exists(filePath));
    }
}