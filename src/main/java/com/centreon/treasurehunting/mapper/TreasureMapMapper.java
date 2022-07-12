package com.centreon.treasurehunting.mapper;

import com.centreon.treasurehunting.model.Position;
import com.centreon.treasurehunting.model.Size;
import com.centreon.treasurehunting.model.TreasureMap;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TreasureMapMapper {

    public TreasureMap mapLinesToMap(List<String> mapLines) {
        TreasureMap treasureMap = TreasureMap.builder().build();
        for(String line: mapLines) {
            if (line.startsWith("C")) {
                String[] mapLine = line.split(" ");
                treasureMap.setSize(Size
                        .builder()
                        .x(Integer.parseInt(mapLine[1]))
                        .y(Integer.parseInt(mapLine[2]))
                        .build());
            } else if (line.startsWith("T")) {
                String[] treasureLine = line.split(" ");
                String[] treasurePositionElement = treasureLine[1].split("-");
                Position treasurePosition = Position
                        .builder()
                        .x(Integer.parseInt(treasurePositionElement[0]))
                        .y(Integer.parseInt(treasurePositionElement[1]))
                        .build();
                treasureMap.addTreasure(treasurePosition, Integer.parseInt(treasureLine[2]));
            } else if (line.startsWith("M")) {
                String[] mountainLine = line.split(" ");
                String[] mountainPositionElement = mountainLine[1].split("-");
                Position mountainPosition = Position
                        .builder()
                        .x(Integer.parseInt(mountainPositionElement[0]))
                        .y(Integer.parseInt(mountainPositionElement[1]))
                        .build();
                treasureMap.addMountain(mountainPosition);
            }
        }

        return treasureMap;
    }
}
