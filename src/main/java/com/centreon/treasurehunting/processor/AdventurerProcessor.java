package com.centreon.treasurehunting.processor;

import com.centreon.treasurehunting.enums.MoveEnum;
import com.centreon.treasurehunting.enums.MoveOrientationEnum;
import com.centreon.treasurehunting.enums.OrientationEnum;
import com.centreon.treasurehunting.model.Adventurer;
import com.centreon.treasurehunting.model.Position;
import com.centreon.treasurehunting.model.TreasureMap;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(of = {"adventurer"}, callSuper=false)
public class AdventurerProcessor extends Thread {

    private Adventurer adventurer;
    private TreasureMap treasureMap;

    @Override
    public void run() {
        adventurer.getMoves().stream().forEach(move -> {
            try {
                if (MoveEnum.A.name().equals(String.valueOf(move))) {
                    moveForward();
                } else {
                    OrientationEnum newOrientation = MoveOrientationEnum
                            .valueOf(adventurer.getOrientation().name() + move)
                            .getOrientation();
                    adventurer.setOrientation(newOrientation);
                    treasureMap.getAdventurers().put(adventurer.getPosition(), adventurer);
                    adventurer.setElapsedTime(adventurer.getElapsedTime() + 1);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        treasureMap.getAdventurers().remove(adventurer.getPosition());
    }

    private synchronized void moveForward() throws InterruptedException {
        Position currentPosition = adventurer.getPosition();
        Position nextPosition = getNextPosition(currentPosition);


        if(!doesExceedBoundaries(nextPosition) && isEmptyPosition(nextPosition) && !isMountain(nextPosition)) {
            treasureMap.getAdventurers().remove(currentPosition);
            adventurer.setPosition(nextPosition);
            adventurer.setElapsedTime(adventurer.getElapsedTime() + 1);
            treasureMap.getAdventurers().put(nextPosition, adventurer);
            Thread.sleep(1000);
            if(hasTreasure(nextPosition)) {
                collectTreasure(nextPosition);
                adventurer.setElapsedTime(adventurer.getElapsedTime() + 1);
                treasureMap.getAdventurers().put(nextPosition, adventurer);
                Thread.sleep(1000);
            }
        } else if(!isEmptyPosition(nextPosition)) {
            // not clear should we wait with while loop, or just wait for 1 sec. In the spec: all the adventurers should be always moving....
            adventurer.setElapsedTime(adventurer.getElapsedTime() + 1);
            treasureMap.getAdventurers().put(nextPosition, adventurer);
            Thread.sleep(1000);
        }
    }

    private Position getNextPosition(Position position) {

        Position nextPosition = Position
                .builder()
                .x(position.getX())
                .y(position.getY())
                .build();
        OrientationEnum currentOrientation = adventurer.getOrientation();

        switch (currentOrientation) {
            case N: nextPosition.setY(position.getY() - 1); break;
            case S: nextPosition.setY(position.getY() + 1); break;
            case E: nextPosition.setX(position.getX() + 1); break;
            case W: nextPosition.setX(position.getX() - 1); break;
        }

        return nextPosition;
    }

    private boolean isEmptyPosition(Position position) {
        return !treasureMap.getAdventurers().containsKey(position);
    }

    private boolean doesExceedBoundaries(Position position) {
        return position.getX() < 1
                || position.getX() > treasureMap.getSize().getX()
                || position.getY() < 1
                || position.getY() > treasureMap.getSize().getY();
    }

    private boolean isMountain(Position position) {
        return treasureMap.getMountains().contains(position);
    }

    private boolean hasTreasure(Position position) {
        return treasureMap.getTreasures().containsKey(position);
    }

    private void collectTreasure(Position position) {
        adventurer.getTreasures().add(position);
        treasureMap.getAdventurers().put(position, adventurer);
        if(treasureMap.getTreasures().get(position) > 1) {
            treasureMap.getTreasures().put(position, treasureMap.getTreasures().get(position)-1);
        } else {
            treasureMap.getTreasures().remove(position);
        }
    }
}
