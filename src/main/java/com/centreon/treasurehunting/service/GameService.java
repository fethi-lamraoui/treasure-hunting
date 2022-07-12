package com.centreon.treasurehunting.service;

import com.centreon.treasurehunting.exception.BusinessException;
import com.centreon.treasurehunting.model.Adventurer;
import com.centreon.treasurehunting.model.TreasureMap;
import com.centreon.treasurehunting.processor.AdventurerProcessor;
import com.centreon.treasurehunting.processor.GameControllerProcessor;
import com.centreon.treasurehunting.validator.BusinessValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameService {

    private final BusinessValidator businessValidator;

    public GameService(BusinessValidator businessValidator) {
        this.businessValidator = businessValidator;
    }

    public void validatePositions(TreasureMap treasureMap, List<Adventurer> adventurers) {
        businessValidator.validateMapPositions(treasureMap);
        businessValidator.validateAdventurersPotisions(treasureMap, adventurers);
    }

    public void placeAdventurersOnMap(TreasureMap treasureMap, List<Adventurer> adventurers) {
        try {
            treasureMap.setAdventurers(adventurers.stream().collect(Collectors.toMap(Adventurer::getPosition, adventurer -> adventurer)));
        } catch (IllegalStateException exception) {
            throw new BusinessException(BusinessException.BUSINESS_ERROR_OCCURED, exception);
        }
    }

    public GameControllerProcessor prepareGameControllerProcessor(TreasureMap treasureMap, List<Adventurer> adventurers) {
        return GameControllerProcessor
                .builder()
                .adventurerProcessors(adventurers
                        .stream()
                        .map(adventurer -> AdventurerProcessor
                                .builder()
                                .adventurer(adventurer)
                                .treasureMap(treasureMap)
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
