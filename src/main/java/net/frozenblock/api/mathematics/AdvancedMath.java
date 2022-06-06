package net.frozenblock.api.mathematics;

public class AdvancedMath {
    /**
     * ADVANCED MATH
     * <p>
     * Adds more math operations
     * <p>
     * Only for FrozenBlock Modders, ALL RIGHTS RESERVED
     * <p>
     *
     *
     * @author      LiukRast (2021-2022)
     * @since 4.0
     *
     */
    public static float range(float min, float max, float number) {
        return (number * max) + min;
    }

    public static double randomPosNeg() {
        return Math.random() * (Math.random()>=0.5 ? 1 : -1);
    }

    public static int waterToHollowedProperty(int value) {
        if(value > 8 ) {
            return 8;
        } else if(value < 0) {
            return -1;
        } else {
            return value;
        }
    }

    public static int waterLevelReduce(int value) {
        if(value < 8) {
            return value + 1;
        } else {
            return 8;
        }
    }
}