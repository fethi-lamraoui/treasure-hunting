package com.centreon.treasurehunting.exception;

public class BusinessException extends TreasureHuntingException {

    public static final String TREASURE_EXCEEDS_BOUNDARIES = "Treasure exceeds boundaries.";
    public static final String MOUNTAIN_EXCEEDS_BOUNDARIES = "Mountain exceeds boundaries.";
    public static final String ADVENTURER_INCORRECT_POSITION = "Adventurer incorrect position.";
    public static final String BUSINESS_ERROR_OCCURED = "Business error occured.";

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable throwable) {
        super(message, throwable);
    }


}
