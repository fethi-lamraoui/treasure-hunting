package com.centreon.treasurehunting.exception;

public class FileException extends TreasureHuntingException {

    public static final String ERROR_WHILE_READING = "Error while reading the file";

    public FileException(String message) {
        super(message);
    }

    public FileException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
