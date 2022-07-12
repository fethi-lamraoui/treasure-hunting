package com.centreon.treasurehunting.processor;

import com.centreon.treasurehunting.factory.AdventurerProcessorFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameControllerProcessorTest {

    private GameControllerProcessor gameControllerProcessor;

    @Test
    void startGame() {
        List<AdventurerProcessor> adventurerProcessors = List.of(AdventurerProcessorFactory.getAdventurerProcessor(AdventurerProcessorFactory.JOHN_PROCESSOR_CASE),
                AdventurerProcessorFactory.getAdventurerProcessor(AdventurerProcessorFactory.DOE_PROCESSOR_CASE));
        gameControllerProcessor = new GameControllerProcessor(adventurerProcessors);
        gameControllerProcessor.startGame();

        adventurerProcessors.forEach(adventurerProcessor -> {
            try {
                adventurerProcessor.join();
                assertEquals(Thread.State.TERMINATED, adventurerProcessor.getState());
            } catch (InterruptedException e) {
                System.out.println("Error when getting states");
            }
        });
    }

    @Test
    void gameStatus() {
    }
}