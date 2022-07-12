package com.centreon.treasurehunting.factory;

import com.centreon.treasurehunting.model.Adventurer;
import com.centreon.treasurehunting.model.Position;
import com.centreon.treasurehunting.model.Size;
import com.centreon.treasurehunting.model.TreasureMap;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class TreasureMapFactory {

    public static final String CASUAL_CASE = "casual";

    public static TreasureMap getTreasureMap(String type){
        switch (type) {
            case CASUAL_CASE: return getCasual();
        }

        return null;
    }

    private static TreasureMap getCasual() {
        Size size = Size
                .builder()
                .x(6)
                .y(5)
                .build();
        Set<Position> mountains = new HashSet<>();
        mountains.add(Position
                .builder()
                .x(5)
                .y(3)
                .build());
        Map<Position, Integer> treasures = new ConcurrentHashMap<>();
        treasures.put(Position
                .builder()
                .x(4)
                .y(2)
                .build(), 1);
        treasures.put(Position
                .builder()
                .x(1)
                .y(4)
                .build(), 3);
        Map<Position, Adventurer> adventurers = new ConcurrentHashMap<>();

        return TreasureMap
                .builder()
                .size(size)
                .mountains(mountains)
                .treasures(treasures)
                .adventurers(adventurers)
                .build();
    }

}
