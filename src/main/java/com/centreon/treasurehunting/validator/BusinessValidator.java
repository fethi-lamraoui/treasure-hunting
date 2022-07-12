package com.centreon.treasurehunting.validator;

import com.centreon.treasurehunting.exception.BusinessException;
import com.centreon.treasurehunting.model.Adventurer;
import com.centreon.treasurehunting.model.Position;
import com.centreon.treasurehunting.model.Size;
import com.centreon.treasurehunting.model.TreasureMap;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BusinessValidator {

    public void validateMapPositions(TreasureMap treasureMap) {
        Size mapSize = treasureMap.getSize();
        treasureMap
                .getTreasures()
                .keySet()
                .stream()
                .filter(position -> doesExceedBoundaries(position, mapSize))
                .findAny()
                .ifPresent(treasure -> {
                    throw new BusinessException(BusinessException.TREASURE_EXCEEDS_BOUNDARIES);
                });

        treasureMap
                .getMountains()
                .stream()
                .filter(position -> doesExceedBoundaries(position, mapSize))
                .findAny()
                .ifPresent(treasure -> {
                    throw new BusinessException(BusinessException.MOUNTAIN_EXCEEDS_BOUNDARIES);
                });
    }

    public void validateAdventurersPotisions(TreasureMap treasureMap, List<Adventurer> adventurers) {
        Size mapSize = treasureMap.getSize();
        adventurers
                .stream()
                .filter(adventurer -> doesExceedBoundaries(adventurer.getPosition(), mapSize)
                        || hasTreasure(treasureMap, adventurer.getPosition())
                        || isMountain(treasureMap, adventurer.getPosition()))
                .findAny()
                .ifPresent(adventurer -> {
                    throw new BusinessException(BusinessException.ADVENTURER_INCORRECT_POSITION);
                });
    }

    private boolean doesExceedBoundaries(Position position, Size mapSize) {
        return position.getX() < 0
                || position.getX() > mapSize.getX()
                || position.getY() < 0
                || position.getY() > mapSize.getY();
    }

    private boolean isMountain(TreasureMap treasureMap, Position position) {
        return treasureMap.getMountains().contains(position);
    }

    private boolean hasTreasure(TreasureMap treasureMap, Position position) {
        return treasureMap.getTreasures().containsKey(position);
    }
}
