package com.centreon.treasurehunting.model;

import com.centreon.treasurehunting.enums.OrientationEnum;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@EqualsAndHashCode(of = {"position"})
public class Adventurer {

    private String name;
    private Position position;
    private OrientationEnum orientation;
    private List<Character> moves;
    @Builder.Default
    private List<Position> treasures = new ArrayList<>();
    @Builder.Default
    private Integer elapsedTime = 0;
}
