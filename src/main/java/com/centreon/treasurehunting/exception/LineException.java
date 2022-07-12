package com.centreon.treasurehunting.exception;

public class LineException extends TreasureHuntingException {

    public static final String NO_LINES = "There is no lines in the map file";
    public static final String NO_MAP_LINES = "There is no map line";
    public static final String INVALID_MAP_LINE = "Invalid map line";
    public static final String INVALID_TREASURE_LINE = "Invalid treasure line";
    public static final String INVALID_MOUNTAIN_LINE = "Invalid mountain line";
    public static final String INVALID_ADVENTURER_LINE = "Invalid adventurer line";
    public static final String INVALID_LINE = "Invalid line";

    public LineException(String message) {
        super(message);
    }
    public LineException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
