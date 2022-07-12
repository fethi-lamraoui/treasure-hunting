package com.centreon.treasurehunting.service;

import com.centreon.treasurehunting.exception.BusinessException;
import com.centreon.treasurehunting.factory.AdventurerFactory;
import com.centreon.treasurehunting.factory.GameControllerProcessorFactory;
import com.centreon.treasurehunting.factory.TreasureMapFactory;
import com.centreon.treasurehunting.model.Adventurer;
import com.centreon.treasurehunting.model.TreasureMap;
import com.centreon.treasurehunting.processor.GameControllerProcessor;
import com.centreon.treasurehunting.validator.BusinessValidator;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class GameServiceTest {

    @InjectMocks
    GameService gameService;
    @Mock
    BusinessValidator businessValidator;

    @Test
    void validatePositions_shoud_throw_exception_when_treasure_exceeds_boundaries() {
        TreasureMap treasureMap = TreasureMapFactory.getTreasureMap(TreasureMapFactory.CASUAL_CASE);
        List<Adventurer> adventurers = new ArrayList<>();

        doThrow(new BusinessException(BusinessException.TREASURE_EXCEEDS_BOUNDARIES))
                .when(businessValidator).validateMapPositions(any(TreasureMap.class));

        Exception exception = assertThrows(BusinessException.class, () -> {
            gameService.validatePositions(treasureMap, adventurers);
        });

        assertEquals(BusinessException.TREASURE_EXCEEDS_BOUNDARIES, exception.getMessage());
    }

    @Test
    void validatePositions_shoud_throw_exception_when_moutain_exceeds_boundaries() {
        TreasureMap treasureMap = TreasureMapFactory.getTreasureMap(TreasureMapFactory.CASUAL_CASE);
        List<Adventurer> adventurers = new ArrayList<>();

        doThrow(new BusinessException(BusinessException.MOUNTAIN_EXCEEDS_BOUNDARIES))
                .when(businessValidator).validateMapPositions(any(TreasureMap.class));

        Exception exception = assertThrows(BusinessException.class, () -> {
            gameService.validatePositions(treasureMap, adventurers);
        });

        assertEquals(BusinessException.MOUNTAIN_EXCEEDS_BOUNDARIES, exception.getMessage());
    }

    @Test
    void validatePositions_shoud_throw_exception_when_adenturer_exceeds_boundaries() {
        TreasureMap treasureMap = TreasureMapFactory.getTreasureMap(TreasureMapFactory.CASUAL_CASE);
        List<Adventurer> adventurers = List.of(AdventurerFactory.getAdventurer(AdventurerFactory.EXCEEDS_BOUNDARIES_CASE));

        doThrow(new BusinessException(BusinessException.ADVENTURER_INCORRECT_POSITION))
                .when(businessValidator).validateAdventurersPotisions(any(TreasureMap.class), anyList());

        Exception exception = assertThrows(BusinessException.class, () -> {
            gameService.validatePositions(treasureMap, adventurers);
        });

        assertEquals(BusinessException.ADVENTURER_INCORRECT_POSITION, exception.getMessage());
    }

    @Test
    void validatePositions_shoud_throw_exception_when_adenturer_first_position_is_moutain() {
        TreasureMap treasureMap = TreasureMapFactory.getTreasureMap(TreasureMapFactory.CASUAL_CASE);
        List<Adventurer> adventurers = List.of(AdventurerFactory.getAdventurer(AdventurerFactory.MOUNTAIN_SPOT_CASE));

        doThrow(new BusinessException(BusinessException.ADVENTURER_INCORRECT_POSITION))
                .when(businessValidator).validateAdventurersPotisions(any(TreasureMap.class), anyList());

        Exception exception = assertThrows(BusinessException.class, () -> {
            gameService.validatePositions(treasureMap, adventurers);
        });

        assertEquals(BusinessException.ADVENTURER_INCORRECT_POSITION, exception.getMessage());
    }

    @Test
    void validatePositions_shoud_throw_exception_when_adenturer_first_position_is_treasure() {
        TreasureMap treasureMap = TreasureMapFactory.getTreasureMap(TreasureMapFactory.CASUAL_CASE);
        List<Adventurer> adventurers = List.of(AdventurerFactory.getAdventurer(AdventurerFactory.TREASURE_SPOT_CASE));

        doThrow(new BusinessException(BusinessException.ADVENTURER_INCORRECT_POSITION))
                .when(businessValidator).validateAdventurersPotisions(any(TreasureMap.class), anyList());

        Exception exception = assertThrows(BusinessException.class, () -> {
            gameService.validatePositions(treasureMap, adventurers);
        });

        assertEquals(BusinessException.ADVENTURER_INCORRECT_POSITION, exception.getMessage());
    }

    @Test
    void validatePositions_happy_path() {
        TreasureMap treasureMap = TreasureMapFactory.getTreasureMap(TreasureMapFactory.CASUAL_CASE);
        List<Adventurer> adventurers = new ArrayList<>();

        doNothing().when(businessValidator).validateMapPositions(any(TreasureMap.class));
        doNothing().when(businessValidator).validateAdventurersPotisions(any(TreasureMap.class), anyList());

        gameService.validatePositions(treasureMap, adventurers);

        verify(businessValidator, times(1)).validateMapPositions(any(TreasureMap.class));
        verify(businessValidator, times(1)).validateAdventurersPotisions(any(TreasureMap.class), anyList());
    }

    @Test
    void placeAdventurersOnMap_should_throw_exception_when_two_adventurers_with_the_same_position() {
        TreasureMap treasureMap = TreasureMapFactory.getTreasureMap(TreasureMapFactory.CASUAL_CASE);
        List<Adventurer> adventurers = List.of(AdventurerFactory.getAdventurer(AdventurerFactory.JOHN_CASE),
                AdventurerFactory.getAdventurer(AdventurerFactory.JOHN_CASE));

        Exception exception = assertThrows(BusinessException.class, () -> {
            gameService.placeAdventurersOnMap(treasureMap, adventurers);
        });

        assertEquals(BusinessException.BUSINESS_ERROR_OCCURED, exception.getMessage());
    }

    @Test
    void placeAdventurersOnMap_happy_path() {
        TreasureMap treasureMap = TreasureMapFactory.getTreasureMap(TreasureMapFactory.CASUAL_CASE);
        List<Adventurer> adventurers = List.of(AdventurerFactory.getAdventurer(AdventurerFactory.JOHN_CASE),
                AdventurerFactory.getAdventurer(AdventurerFactory.DOE_CASE));

        assertDoesNotThrow(() -> {
            gameService.placeAdventurersOnMap(treasureMap, adventurers);
        });

        Adventurer adventurerJohn = AdventurerFactory.getAdventurer(AdventurerFactory.JOHN_CASE);
        Adventurer adventurerDoe = AdventurerFactory.getAdventurer(AdventurerFactory.DOE_CASE);

        assertEquals(treasureMap.getAdventurers().get(adventurerJohn.getPosition()), adventurerJohn);
        assertEquals(treasureMap.getAdventurers().get(adventurerDoe.getPosition()), adventurerDoe);
    }

    @Test
    void prepareGameControllerProcessor() {
        TreasureMap treasureMap = TreasureMapFactory.getTreasureMap(TreasureMapFactory.CASUAL_CASE);
        List<Adventurer> adventurers = List.of(AdventurerFactory.getAdventurer(AdventurerFactory.JOHN_CASE),
                AdventurerFactory.getAdventurer(AdventurerFactory.DOE_CASE));

        GameControllerProcessor gameControllerProcessor = gameService.prepareGameControllerProcessor(treasureMap, adventurers);
        assertEquals(gameControllerProcessor, GameControllerProcessorFactory.getGameControllerProcessor(GameControllerProcessorFactory.CASUAL_CASE));
    }
}