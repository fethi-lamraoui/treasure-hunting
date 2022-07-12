package com.centreon.treasurehunting.factory;

import com.centreon.treasurehunting.processor.AdventurerProcessor;

public class AdventurerProcessorFactory {

    public static final String JOHN_PROCESSOR_CASE = "johnProcessor";
    public static final String DOE_PROCESSOR_CASE = "doeProcessor";

    public static AdventurerProcessor getAdventurerProcessor(String type){
        switch (type) {
            case JOHN_PROCESSOR_CASE: return getJohnProcessor();
            case DOE_PROCESSOR_CASE: return getDoeProcessor();
        }

        return null;
    }

    private static AdventurerProcessor getJohnProcessor() {
        return AdventurerProcessor
                .builder()
                .adventurer(AdventurerFactory.getAdventurer(AdventurerFactory.JOHN_CASE))
                .treasureMap(TreasureMapFactory.getTreasureMap(TreasureMapFactory.CASUAL_CASE))
                .build();
    }

    private static AdventurerProcessor getDoeProcessor() {
        return AdventurerProcessor
                .builder()
                .adventurer(AdventurerFactory.getAdventurer(AdventurerFactory.DOE_CASE))
                .treasureMap(TreasureMapFactory.getTreasureMap(TreasureMapFactory.CASUAL_CASE))
                .build();
    }
}
