package net.frozenblock.wilderwild.block.entity;

import net.minecraft.util.StringRepresentable;

public enum HangingTendrilPhase implements StringRepresentable {
    INACTIVE("inactive"),
    ACTIVE("active"),
    COOLDOWN("cooldown");

    private final String name;

    HangingTendrilPhase(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String getSerializedName() {
        return this.name;
    }
}
