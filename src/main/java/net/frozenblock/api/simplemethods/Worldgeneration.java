package net.frozenblock.api.simplemethods;

import net.minecraft.util.math.Direction;

public class Worldgeneration {

    public Direction randomDir(Direction.Axis axis) {
        double random = Math.random();
        if(axis == Direction.Axis.Y) {
            if(Math.random() > 0.5) {
                return Direction.UP;
            } else {
                return Direction.DOWN;
            }
        } else if(axis == Direction.Axis.X) {
            if(Math.random() > 0.5) {
                return Direction.EAST;
            } else {
                return Direction.WEST;
            }
        } else {
            if(Math.random() > 0.5) {
                return Direction.NORTH;
            } else {
                return Direction.SOUTH;
            }
        }
    }


}
