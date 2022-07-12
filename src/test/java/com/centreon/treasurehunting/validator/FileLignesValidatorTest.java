package com.centreon.treasurehunting.validator;

import com.centreon.treasurehunting.exception.LineException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static com.centreon.treasurehunting.exception.LineException.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class FileLignesValidatorTest {

    @Autowired
    FileLignesValidator fileLignesValidator;

    @Test
    void validateMapFileLines_should_throw_exception_when_lines_are_null_or_empty() {
        Exception exception = assertThrows(LineException.class, () -> {
            fileLignesValidator.validateMapFileLines(null);
        });
        assertEquals(NO_LINES, exception.getMessage());

        exception = assertThrows(LineException.class, () -> {
            fileLignesValidator.validateMapFileLines(Collections.EMPTY_LIST);
        });
        assertEquals(NO_LINES, exception.getMessage());
    }

    @Test
    void validateMapFileLines_should_throw_exception_when_map_line_does_not_have_three_parts() {
        Exception exception = assertThrows(LineException.class, () -> {
            fileLignesValidator.validateMapFileLines(List.of("C65"));
        });
        assertEquals(INVALID_MAP_LINE, exception.getMessage());
    }

    @Test
    void validateMapFileLines_should_throw_exception_when_map_line_does_not_follow_map_line_directives() {
        Exception exception = assertThrows(LineException.class, () -> {
            fileLignesValidator.validateMapFileLines(List.of("CD 6 5"));
        });
        assertEquals(INVALID_MAP_LINE, exception.getMessage());

        exception = assertThrows(LineException.class, () -> {
            fileLignesValidator.validateMapFileLines(List.of("C -6 5"));
        });
        assertEquals(INVALID_MAP_LINE, exception.getMessage());

        exception = assertThrows(LineException.class, () -> {
            fileLignesValidator.validateMapFileLines(List.of("C 6 -5"));
        });
        assertEquals(INVALID_MAP_LINE, exception.getMessage());
    }

    @Test
    void validateMapFileLines_should_throw_exception_when_map_line_has_wrong_number_format() {
        Exception exception = assertThrows(LineException.class, () -> {
            fileLignesValidator.validateMapFileLines(List.of("C F 5"));
        });
        assertEquals(INVALID_MAP_LINE, exception.getMessage());

        exception = assertThrows(LineException.class, () -> {
            fileLignesValidator.validateMapFileLines(List.of("C 6 F"));
        });
        assertEquals(INVALID_MAP_LINE, exception.getMessage());
    }

    @Test
    void validateMapFileLines_should_throw_exception_when_treasure_line_does_not_have_three_parts() {
        Exception exception = assertThrows(LineException.class, () -> {
            fileLignesValidator.validateMapFileLines(List.of("C 6 5", "T 4-21"));
        });
        assertEquals(INVALID_TREASURE_LINE, exception.getMessage());
    }

    @Test
    void validateMapFileLines_should_throw_exception_when_treasure_line_does_not_follow_treasure_line_directives() {
        Exception exception = assertThrows(LineException.class, () -> {
            fileLignesValidator.validateMapFileLines(List.of("C 6 5", "TD 4-2 3"));
        });
        assertEquals(INVALID_TREASURE_LINE, exception.getMessage());

        exception = assertThrows(LineException.class, () -> {
            fileLignesValidator.validateMapFileLines(List.of("C 6 5", "T 4--2 3"));
        });
        assertEquals(INVALID_TREASURE_LINE, exception.getMessage());

        exception = assertThrows(LineException.class, () -> {
            fileLignesValidator.validateMapFileLines(List.of("C 6 5", "T 4-2 -5"));
        });
        assertEquals(INVALID_TREASURE_LINE, exception.getMessage());
    }

    @Test
    void validateMapFileLines_should_throw_exception_when_treasure_line_has_wrong_number_format() {
        Exception exception = assertThrows(LineException.class, () -> {
            fileLignesValidator.validateMapFileLines(List.of("C 6 5", "T 4-2 F"));
        });
        assertEquals(INVALID_TREASURE_LINE, exception.getMessage());
    }

    @Test
    void validateMapFileLines_should_throw_exception_when_mountain_line_does_not_have_two_parts() {
        Exception exception = assertThrows(LineException.class, () -> {
            fileLignesValidator.validateMapFileLines(List.of("C 6 5", "M3-1"));
        });
        assertEquals(INVALID_MOUNTAIN_LINE, exception.getMessage());
    }

    @Test
    void validateMapFileLines_should_throw_exception_when_mountain_line_does_not_follow_treasure_line_directives() {
        Exception exception = assertThrows(LineException.class, () -> {
            fileLignesValidator.validateMapFileLines(List.of("C 6 5", "MD 3-1"));
        });
        assertEquals(INVALID_MOUNTAIN_LINE, exception.getMessage());

        exception = assertThrows(LineException.class, () -> {
            fileLignesValidator.validateMapFileLines(List.of("C 6 5", "M 3--1"));
        });
        assertEquals(INVALID_MOUNTAIN_LINE, exception.getMessage());
    }

    @Test
    void validateMapFileLines_happy_path() {

    }

    @Test
    void validateAdventurerFileLines() {
    }
}