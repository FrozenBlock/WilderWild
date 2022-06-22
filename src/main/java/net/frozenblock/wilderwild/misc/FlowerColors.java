package net.frozenblock.wilderwild.misc;

import net.minecraft.util.StringIdentifiable;

public enum FlowerColors implements StringIdentifiable {
    NONE("none"),
    BLUE("blue"),
    WHITE("white"),
    PINK("pink"),
    PURPLE("purple"),
    ORANGE("orange"),
    YELLOW("yellow"),
    CYAN("cyan"),
    LIGHT_BLUE("light_blue"),
    GRAY("gray"),
    LIGHT_GRAY("light_gray"),
    LIME("lime"),
    GREEN("green"),
    BLACK("black"),
    BROWN("brown"),
    RED("red"),
    MAGENTA("magenta");

    private final String name;

    FlowerColors(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String asString() {
        return this.name;
    }
}
