package net.frozenblock.api.simplemethods;

import net.minecraft.core.Direction;

public class WorldGeneration {

    public Direction randomDir(Direction.Axis axis) {
        double random = Math.random();
        if (axis == Direction.Axis.Y) {
            if (random > 0.5) {
                return Direction.UP;
            } else {
                return Direction.DOWN;
            }
        } else if (axis == Direction.Axis.X) {
            if (random > 0.5) {
                return Direction.EAST;
            } else {
                return Direction.WEST;
            }
        } else {
            if (random > 0.5) {
                return Direction.NORTH;
            } else {
                return Direction.SOUTH;
            }
        }
    }


}
