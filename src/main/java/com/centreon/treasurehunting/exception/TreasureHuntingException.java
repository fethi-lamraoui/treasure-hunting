package com.centreon.treasurehunting.exception;

public class TreasureHuntingException extends RuntimeException {

    public TreasureHuntingException(String message) {
        super(message);
    }

    public TreasureHuntingException(String message, Throwable throwable) {
        super(message, throwable);
    }


}
