package net.frozenblock.wilderwild.misc;

import net.minecraft.util.StringRepresentable;

public enum FlowerColors implements StringRepresentable {
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

    public String getSerializedName() {
        return this.name;
    }
}
