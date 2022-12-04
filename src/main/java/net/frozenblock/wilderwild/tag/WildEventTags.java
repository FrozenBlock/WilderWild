package net.frozenblock.wilderwild.tag;

import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.gameevent.GameEvent;

public class WildEventTags {
    public static final TagKey<GameEvent> ECHOER_CAN_LISTEN = of("echoer_can_listen");

    private WildEventTags() {
    }

    private static TagKey<GameEvent> of(String id) {
        return TagKey.create(Registry.GAME_EVENT_REGISTRY, WilderSharedConstants.id(id));
    }
}
