package com.centreon.treasurehunting.factory;

import com.centreon.treasurehunting.enums.OrientationEnum;
import com.centreon.treasurehunting.model.Adventurer;
import com.centreon.treasurehunting.model.Position;

import java.util.ArrayList;
import java.util.List;

public class AdventurerFactory {

    public static final String JOHN_CASE = "John";
    public static final String DOE_CASE = "Doe";
    public static final String NO_MOVE_CASE = "noMove";
    public static final String EXCEEDS_BOUNDARIES_CASE = "exceedsBoundaries";
    public static final String MOUNTAIN_SPOT_CASE = "mountainSpot";
    public static final String TREASURE_SPOT_CASE = "treasureSpot";

    public static Adventurer getAdventurer(String type){
        switch (type) {
            case JOHN_CASE: return getJohn();
            case DOE_CASE: return getDoe();
            case EXCEEDS_BOUNDARIES_CASE: return getExceedsBoundaries();
            case MOUNTAIN_SPOT_CASE: return getMountainSpot();
            case TREASURE_SPOT_CASE: return getTreasureSpot();
            case NO_MOVE_CASE: return noMove();
        }

        return null;
    }

    private static Adventurer getJohn() {
        return Adventurer
                .builder()
                .name("John")
                .position(Position
                        .builder()
                        .x(1)
                        .y(1)
                        .build())
                .orientation(OrientationEnum.E)
                .moves(List.of('A', 'A', 'D', 'A', 'D', 'A', 'G', 'A'))
                .treasures(new ArrayList<>())
                .elapsedTime(0)
                .build();
    }

    private static Adventurer getDoe() {
        return Adventurer
                .builder()
                .name("Doe")
                .position(Position
                        .builder()
                        .x(1)
                        .y(2)
                        .build())
                .orientation(OrientationEnum.S)
                .moves(List.of('A', 'A', 'G', 'A', 'A', 'A', 'A', 'G', 'A', 'G', 'A', 'D', 'A', 'A'))
                .treasures(new ArrayList<>())
                .elapsedTime(0)
                .build();
    }

    private static Adventurer getExceedsBoundaries() {
        return Adventurer
                .builder()
                .name("Doe")
                .position(Position
                        .builder()
                        .x(-1)
                        .y(2)
                        .build())
                .orientation(OrientationEnum.S)
                .moves(List.of('A', 'A', 'G', 'A', 'A', 'A', 'A', 'G', 'A', 'G', 'A', 'D', 'A', 'A'))
                .treasures(new ArrayList<>())
                .elapsedTime(0)
                .build();
    }

    private static Adventurer getMountainSpot() {
        return Adventurer
                .builder()
                .name("Doe")
                .position(Position
                        .builder()
                        .x(5)
                        .y(3)
                        .build())
                .orientation(OrientationEnum.S)
                .moves(List.of('A', 'A', 'G', 'A', 'A', 'A', 'A', 'G', 'A', 'G', 'A', 'D', 'A', 'A'))
                .treasures(new ArrayList<>())
                .elapsedTime(0)
                .build();
    }

    private static Adventurer getTreasureSpot() {
        return Adventurer
                .builder()
                .name("Doe")
                .position(Position
                        .builder()
                        .x(4)
                        .y(2)
                        .build())
                .orientation(OrientationEnum.S)
                .moves(List.of('A', 'A', 'G', 'A', 'A', 'A', 'A', 'G', 'A', 'G', 'A', 'D', 'A', 'A'))
                .treasures(new ArrayList<>())
                .elapsedTime(0)
                .build();
    }

    private static Adventurer noMove() {
        return Adventurer
                .builder()
                .name("noMove")
                .position(Position
                        .builder()
                        .x(3)
                        .y(1)
                        .build())
                .orientation(OrientationEnum.E)
                .moves(List.of('G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'))
                .treasures(new ArrayList<>())
                .elapsedTime(0)
                .build();
    }
}
