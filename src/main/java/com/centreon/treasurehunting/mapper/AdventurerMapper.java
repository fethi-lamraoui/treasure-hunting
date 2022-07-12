package com.centreon.treasurehunting.mapper;

import com.centreon.treasurehunting.enums.OrientationEnum;
import com.centreon.treasurehunting.model.Adventurer;
import com.centreon.treasurehunting.model.Position;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AdventurerMapper {

    public List<Adventurer> mapLinesToAdventurers(List<String> lines) {
        return lines
                .stream()
                .map(line -> {
                    String[] adventurer = line.split(" ");
                    String[] adventurerPositionElement = adventurer[1].split("-");
                    return Adventurer
                            .builder()
                            .name(adventurer[0])
                            .position(Position
                                    .builder()
                                    .x(Integer.parseInt(adventurerPositionElement[0]))
                                    .y(Integer.parseInt(adventurerPositionElement[1]))
                                    .build())
                            .orientation(OrientationEnum.valueOf(adventurer[2]))
                            .moves(adventurer[3]
                                    .chars()
                                    .mapToObj(move -> (char)move)
                                    .collect(Collectors.toList()))
                            .build();
                })
                .collect(Collectors.toList());
    }
}
