package com.centreon.treasurehunting.validator;

import com.centreon.treasurehunting.exception.LineException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

import static com.centreon.treasurehunting.exception.LineException.*;

@Component
public class FileLignesValidator {

    public void validateMapFileLines(List<String> lines) {

        if(lines == null || lines.isEmpty()) {
            throw new LineException(NO_LINES);
        }

        boolean hasMapDimension = false;

        for(String line: lines) {
            if(line.startsWith("C")) {
                hasMapDimension = true;
                isValidMapLine(line);
            } else if(line.startsWith("T")) {
                isValidTreasureLine(line);
            } else if(line.startsWith("M")) {
                isValidMountainLine(line);
            } else {
                throw new LineException(INVALID_LINE);
            }
        }

        if(!hasMapDimension) {
            throw new LineException(NO_MAP_LINES);
        }
    }

    private void isValidMapLine(String line) {
        String[] parts = line.split(" ");
        if(parts.length != 3) {
            throw new LineException(INVALID_MAP_LINE);
        } else {
            try {
                if(!parts[0].equals("C") || Integer.parseInt(parts[1]) < 0 || Integer.parseInt(parts[2]) < 0) {
                    throw new LineException(INVALID_MAP_LINE, new IllegalArgumentException());
                }
            } catch (NumberFormatException numberFormatException) {
                throw new LineException(INVALID_MAP_LINE, numberFormatException);
            }
        }
    }

    private void isValidTreasureLine(String line) {
        String[] parts = line.split(" ");
        if(parts.length != 3) {
            throw new LineException(INVALID_TREASURE_LINE);
        } else {
            try {
                if(!parts[0].equals("T") || !parts[1].matches("[0-9]+-[0-9]+") || Integer.parseInt(parts[2]) < 0) {
                    throw new LineException(INVALID_TREASURE_LINE, new IllegalArgumentException());
                }
            } catch (NumberFormatException numberFormatException) {
                throw new LineException(INVALID_TREASURE_LINE, numberFormatException);
            }
        }
    }

    private void isValidMountainLine(String line) {
        String[] parts = line.split(" ");
        if(parts.length != 2) {
            throw new LineException(INVALID_MOUNTAIN_LINE);
        } else {
            if(!parts[0].equals("M") || !parts[1].matches("[0-9]+-[0-9]+")) {
                throw new LineException(INVALID_MOUNTAIN_LINE, new IllegalArgumentException());
            }
        }
    }

    private Predicate<String> adventurerFileLinePredicate = line -> {
        String[] parts = line.split(" ");
        if (parts.length != 4) {
            throw new LineException(INVALID_ADVENTURER_LINE);
        } else {
            if (!(parts[0].trim().length() > 0)
                    || !parts[1].matches("[0-9]+-[0-9]+")
                    || !parts[2].matches("^[EWNS]{1}$")
                    || !parts[3].matches("^[ADG]*$")) {
                throw new LineException(INVALID_ADVENTURER_LINE, new IllegalArgumentException());
            }
        }
        return true;
    };
    public void validateAdventurerFileLines(List<String> lines) {

        if(lines == null || lines.isEmpty()) {
            throw new LineException(NO_LINES);
        } else {
            lines.stream().allMatch(adventurerFileLinePredicate);
        }
    }
}
