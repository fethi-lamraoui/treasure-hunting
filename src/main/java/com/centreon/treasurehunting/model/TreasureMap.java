package com.centreon.treasurehunting.model;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Data
@Builder
public class TreasureMap {

    private Size size;
    @Builder.Default
    private Set<Position> mountains = new HashSet<>();
    @Builder.Default
    private Map<Position, Integer> treasures = new ConcurrentHashMap<>();
    @Builder.Default
    private Map<Position, Adventurer> adventurers = new ConcurrentHashMap<>();

    public void addMountain(Position position) {
        mountains.add(position);
    }

    public void addTreasure(Position position, Integer quantity) {
        treasures.put(position, quantity);
    }
}
