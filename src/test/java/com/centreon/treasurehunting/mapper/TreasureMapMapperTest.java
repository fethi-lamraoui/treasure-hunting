package com.centreon.treasurehunting.mapper;

import com.centreon.treasurehunting.factory.TreasureMapFactory;
import com.centreon.treasurehunting.model.Adventurer;
import com.centreon.treasurehunting.model.Position;
import com.centreon.treasurehunting.model.Size;
import com.centreon.treasurehunting.model.TreasureMap;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TreasureMapMapperTest {

    @Autowired
    TreasureMapMapper treasureMapMapper;

    @Test
    void mapLinesToMap() {
        TreasureMap expectedTreasureMap = TreasureMapFactory.getTreasureMap(TreasureMapFactory.CASUAL_CASE);
        List<String> lines = List.of("C 6 5", "T 4-2 1", "T 1-4 3", "M 5-3");

        TreasureMap treasureMap = treasureMapMapper.mapLinesToMap(lines);
        assertEquals(expectedTreasureMap.getSize(), treasureMap.getSize());
        assertEquals(expectedTreasureMap.getMountains(), treasureMap.getMountains());
        assertEquals(expectedTreasureMap.getTreasures(), treasureMap.getTreasures());
        assertEquals(expectedTreasureMap.getAdventurers(), treasureMap.getAdventurers());
    }
}