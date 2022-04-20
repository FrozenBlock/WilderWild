package net.frozenblock.wilderwild.tag;

import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.event.GameEvent;

public class WildEventTags {
    public static final TagKey<GameEvent> ECHOER_CAN_LISTEN = of("echoer_can_listen");

    private WildEventTags() {
    }

    private static TagKey<GameEvent> of(String id) {
        return TagKey.of(Registry.GAME_EVENT_KEY, new Identifier(WilderWild.MOD_ID, id));
    }
}
