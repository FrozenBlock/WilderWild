package net.frozenblock.wilderwild.tag;

import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public final class WilderEntityTags {
    public static final TagKey<EntityType<?>> CAN_SWIM_IN_ALGAE = of("can_swim_in_algae");

    private WilderEntityTags() {
    }

    private static TagKey<EntityType<?>> of(String path) {
        return TagKey.create(Registry.ENTITY_TYPE_REGISTRY, WilderWild.id(path));
    }
}
