package com.centreon.treasurehunting.enums;

/**
 *
 *     NORTH + LEFT -> WEST
 *     NORTH + RIGHT -> EAST
 *     SOUTH + LEFT -> EAST
 *     SOUTH + RIGHT -> WEST
 *     EAST + LEFT -> NORTH
 *     EAST + RIGHT -> SOUTH
 *     WEST + LEFT -> SOUTH
 *     WEST + RIGHT -> NORTH
 *
 * **/
public enum MoveOrientationEnum {

    NG(OrientationEnum.W),
    ND(OrientationEnum.E),
    SG(OrientationEnum.E),
    SD(OrientationEnum.W),
    EG(OrientationEnum.N),
    ED(OrientationEnum.S),
    WG(OrientationEnum.S),
    WD(OrientationEnum.N);

    private OrientationEnum orientation;

    MoveOrientationEnum(OrientationEnum orientation) {
        this.orientation = orientation;
    }

    public OrientationEnum getOrientation() {
        return orientation;
    }

}
