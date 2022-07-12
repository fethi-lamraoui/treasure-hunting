package com.centreon.treasurehunting.validator;

import com.centreon.treasurehunting.exception.BusinessException;
import com.centreon.treasurehunting.factory.AdventurerFactory;
import com.centreon.treasurehunting.factory.TreasureMapFactory;
import com.centreon.treasurehunting.model.Adventurer;
import com.centreon.treasurehunting.model.Position;
import com.centreon.treasurehunting.model.TreasureMap;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.centreon.treasurehunting.exception.BusinessException.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BusinessValidatorTest {

    @Autowired
    private BusinessValidator businessValidator;

    @Test
    void validateMapPositions_shoud_throw_exception_when_treasure_exceeds_boundaries() {
        TreasureMap treasureMap = TreasureMapFactory.getTreasureMap(TreasureMapFactory.CASUAL_CASE);
        treasureMap.getTreasures().put(Position
                .builder()
                .x(19)
                .y(22)
                .build(), 1);

        Exception exception = assertThrows(BusinessException.class, () -> {
            businessValidator.validateMapPositions(treasureMap);
        });

        assertEquals(TREASURE_EXCEEDS_BOUNDARIES, exception.getMessage());
    }

    @Test
    void validateMapPositions_shoud_throw_exception_when_mountain_exceeds_boundaries() {
        TreasureMap treasureMap = TreasureMapFactory.getTreasureMap(TreasureMapFactory.CASUAL_CASE);
        treasureMap.getMountains().add(Position
                .builder()
                .x(19)
                .y(22)
                .build());

        Exception exception = assertThrows(BusinessException.class, () -> {
            businessValidator.validateMapPositions(treasureMap);
        });

        assertEquals(MOUNTAIN_EXCEEDS_BOUNDARIES, exception.getMessage());
    }

    @Test
    void validateMapPositions_happy_path() {
        TreasureMap treasureMap = TreasureMapFactory.getTreasureMap(TreasureMapFactory.CASUAL_CASE);
        assertDoesNotThrow(() -> businessValidator.validateMapPositions(treasureMap));
    }

    @Test
    void validateAdventurersPotisions_should_throw_exception_when_adventurer_exceeds_boundaries() {

        TreasureMap treasureMap = TreasureMapFactory.getTreasureMap(TreasureMapFactory.CASUAL_CASE);
        Adventurer adventurerExceedsBoundaries = AdventurerFactory.getAdventurer(AdventurerFactory.EXCEEDS_BOUNDARIES_CASE);
        List<Adventurer> adventurers = List.of(adventurerExceedsBoundaries);
        treasureMap.getAdventurers().put(adventurerExceedsBoundaries.getPosition(), adventurerExceedsBoundaries);

        Exception exception = assertThrows(BusinessException.class, () -> {
            businessValidator.validateAdventurersPotisions(treasureMap, adventurers);
        });

        assertEquals(ADVENTURER_INCORRECT_POSITION, exception.getMessage());
    }

    @Test
    void validateAdventurersPotisions_should_throw_exception_when_adventurer_is_on_mountain() {
        TreasureMap treasureMap = TreasureMapFactory.getTreasureMap(TreasureMapFactory.CASUAL_CASE);
        Adventurer adventurerExceedsBoundaries = AdventurerFactory.getAdventurer(AdventurerFactory.MOUNTAIN_SPOT_CASE);
        List<Adventurer> adventurers = List.of(adventurerExceedsBoundaries);
        treasureMap.getAdventurers().put(adventurerExceedsBoundaries.getPosition(), adventurerExceedsBoundaries);

        Exception exception = assertThrows(BusinessException.class, () -> {
            businessValidator.validateAdventurersPotisions(treasureMap, adventurers);
        });

        assertEquals(ADVENTURER_INCORRECT_POSITION, exception.getMessage());
    }

    @Test
    void validateAdventurersPotisions_should_throw_exception_when_adventurer_is_on_treasure() {
        TreasureMap treasureMap = TreasureMapFactory.getTreasureMap(TreasureMapFactory.CASUAL_CASE);
        Adventurer adventurerExceedsBoundaries = AdventurerFactory.getAdventurer(AdventurerFactory.TREASURE_SPOT_CASE);
        List<Adventurer> adventurers = List.of(adventurerExceedsBoundaries);
        treasureMap.getAdventurers().put(adventurerExceedsBoundaries.getPosition(), adventurerExceedsBoundaries);

        Exception exception = assertThrows(BusinessException.class, () -> {
            businessValidator.validateAdventurersPotisions(treasureMap, adventurers);
        });

        assertEquals(ADVENTURER_INCORRECT_POSITION, exception.getMessage());
    }

    @Test
    void validateAdventurersPotisions_happy_path() {
        TreasureMap treasureMap = TreasureMapFactory.getTreasureMap(TreasureMapFactory.CASUAL_CASE);
        Adventurer adventurer = AdventurerFactory.getAdventurer(AdventurerFactory.JOHN_CASE);
        List<Adventurer> adventurers = List.of(adventurer);
        assertDoesNotThrow(() -> businessValidator.validateAdventurersPotisions(treasureMap, adventurers));
    }
}