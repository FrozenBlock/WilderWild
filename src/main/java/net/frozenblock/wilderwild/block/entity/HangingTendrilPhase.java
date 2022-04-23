package net.frozenblock.wilderwild.block.entity;

import net.minecraft.util.StringIdentifiable;

public enum HangingTendrilPhase implements StringIdentifiable {
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

    public String asString() {
        return this.name;
    }
}
