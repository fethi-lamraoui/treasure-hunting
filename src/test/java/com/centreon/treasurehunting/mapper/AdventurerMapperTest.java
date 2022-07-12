package com.centreon.treasurehunting.mapper;

import com.centreon.treasurehunting.factory.AdventurerFactory;
import com.centreon.treasurehunting.model.Adventurer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AdventurerMapperTest {

    @Autowired
    AdventurerMapper adventurerMapper;

    @Test
    void mapLinesToAdventurers() {
        List<String> lines = List.of("John 1-1 E AADADAGA", "Doe 1-2 S AAGAAAAGAGADAA");
        List<Adventurer> expectedAdventurers = List.of(
                AdventurerFactory.getAdventurer(AdventurerFactory.JOHN_CASE),
                AdventurerFactory.getAdventurer(AdventurerFactory.DOE_CASE));

        List<Adventurer> adventurers = adventurerMapper.mapLinesToAdventurers(lines);
        assertEquals(expectedAdventurers, adventurers);
    }
}