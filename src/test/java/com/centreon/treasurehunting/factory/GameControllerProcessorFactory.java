package com.centreon.treasurehunting.factory;

import com.centreon.treasurehunting.processor.AdventurerProcessor;
import com.centreon.treasurehunting.processor.GameControllerProcessor;

import java.util.List;

public class GameControllerProcessorFactory {

    public static final String CASUAL_CASE = "casual";

    public static GameControllerProcessor getGameControllerProcessor(String type){
        switch (type) {
            case CASUAL_CASE: return getCasual();
        }

        return null;
    }

    private static GameControllerProcessor getCasual() {

        List<AdventurerProcessor> adventurerProcessors = List.of(AdventurerProcessorFactory.getAdventurerProcessor(AdventurerProcessorFactory.JOHN_PROCESSOR_CASE),
                AdventurerProcessorFactory.getAdventurerProcessor(AdventurerProcessorFactory.DOE_PROCESSOR_CASE));

        return GameControllerProcessor
                .builder()
                .adventurerProcessors(adventurerProcessors)
                .build();
    }
}
