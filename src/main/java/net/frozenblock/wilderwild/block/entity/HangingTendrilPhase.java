package net.frozenblock.wilderwild.block.entity;

import net.minecraft.util.StringIdentifiable;

public enum HangingTendrilPhase implements StringIdentifiable {
    INACTIVE("inactive"),
    ACTIVE("active"),
    COOLDOWN("cooldown"),
    TWITCH1("twitch1"),
    TWITCH2("twitch2");

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
