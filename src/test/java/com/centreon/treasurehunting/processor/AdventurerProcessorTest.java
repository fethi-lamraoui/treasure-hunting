package com.centreon.treasurehunting.processor;

import com.centreon.treasurehunting.enums.OrientationEnum;
import com.centreon.treasurehunting.factory.AdventurerFactory;
import com.centreon.treasurehunting.factory.TreasureMapFactory;
import com.centreon.treasurehunting.model.Adventurer;
import com.centreon.treasurehunting.model.Position;
import com.centreon.treasurehunting.model.TreasureMap;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdventurerProcessorTest {

    AdventurerProcessor adventurerProcessor;

    @Test
    void run_with_blocked_adventurer() {
        Adventurer expectedJohnAdventurer = Adventurer
                .builder()
                .name("John")
                .position(Position
                        .builder()
                        .x(2)
                        .y(3)
                        .build())
                .orientation(OrientationEnum.S)
                .moves(List.of('A', 'A', 'D', 'A', 'D', 'A', 'G', 'A'))
                .treasures(new ArrayList<>())
                .elapsedTime(8)
                .build();

        Adventurer johnAdventurer = AdventurerFactory.getAdventurer(AdventurerFactory.JOHN_CASE);
        Adventurer noMoveAdventurer = AdventurerFactory.getAdventurer(AdventurerFactory.NO_MOVE_CASE);

        TreasureMap treasureMap = TreasureMapFactory.getTreasureMap(TreasureMapFactory.CASUAL_CASE);
        treasureMap.getAdventurers().put(johnAdventurer.getPosition(), johnAdventurer);
        treasureMap.getAdventurers().put(noMoveAdventurer.getPosition(), noMoveAdventurer);

        adventurerProcessor = new AdventurerProcessor(johnAdventurer, treasureMap);
        AdventurerProcessor noMoveAdventurerProcessor = new AdventurerProcessor(noMoveAdventurer, treasureMap);

        noMoveAdventurerProcessor.start();
        adventurerProcessor.start();

        try {
            adventurerProcessor.join();
            noMoveAdventurerProcessor.join();
            assertEquals(Thread.State.TERMINATED, adventurerProcessor.getState());
            assertEquals(Thread.State.TERMINATED, noMoveAdventurerProcessor.getState());
            assertEquals(expectedJohnAdventurer.getOrientation(), adventurerProcessor.getAdventurer().getOrientation());
            assertEquals(expectedJohnAdventurer.getTreasures(), adventurerProcessor.getAdventurer().getTreasures());
            assertEquals(expectedJohnAdventurer.getElapsedTime(), adventurerProcessor.getAdventurer().getElapsedTime());
        } catch (InterruptedException e) {
            System.out.println("Error occured");
        }
    }

    @Test
    void run_happy_path() {
        Adventurer expectedAdventurer = Adventurer
                .builder()
                .name("John")
                .position(Position
                        .builder()
                        .x(2)
                        .y(3)
                        .build())
                .orientation(OrientationEnum.S)
                .moves(List.of('A', 'A', 'D', 'A', 'D', 'A', 'G', 'A'))
                .treasures(List.of(Position
                        .builder()
                        .x(3)
                        .y(1)
                        .build()))
                .elapsedTime(9)
                .build();

        TreasureMap treasureMap = TreasureMapFactory.getTreasureMap(TreasureMapFactory.CASUAL_CASE);
        treasureMap.getTreasures().put(Position
                .builder()
                .x(3)
                .y(1)
                .build(), 1);

        adventurerProcessor = new AdventurerProcessor(AdventurerFactory.getAdventurer(AdventurerFactory.JOHN_CASE), treasureMap);

        adventurerProcessor.start();

        try {
            adventurerProcessor.join();
            assertEquals(Thread.State.TERMINATED, adventurerProcessor.getState());
            assertEquals(expectedAdventurer.getOrientation(), adventurerProcessor.getAdventurer().getOrientation());
            assertEquals(expectedAdventurer.getTreasures(), adventurerProcessor.getAdventurer().getTreasures());
            assertEquals(expectedAdventurer.getElapsedTime(), adventurerProcessor.getAdventurer().getElapsedTime());
            assertFalse(treasureMap.getAdventurers().containsKey(adventurerProcessor.getAdventurer().getPosition()));
        } catch (InterruptedException e) {
            System.out.println("Error occured");
        }
    }
}