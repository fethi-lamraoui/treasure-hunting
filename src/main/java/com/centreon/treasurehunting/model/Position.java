package com.centreon.treasurehunting.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(of = {"x", "y"})
public class Position {

    private int x;
    private int y;
}
