package net.frozenblock.wilderwild.misc;

import net.minecraft.util.StringIdentifiable;

public enum FlowerColors implements StringIdentifiable {
    NONE("none"),
    BLUE("blue"),
    WHITE("white"),
    PINK("pink"),
    PURPLE("purple");

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
