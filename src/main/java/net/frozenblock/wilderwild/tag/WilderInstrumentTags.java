package net.frozenblock.wilderwild.tag;

import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Instrument;

public final class WilderInstrumentTags {
    public static final TagKey<Instrument> ANCIENT_HORNS = bind("ancient_horns");
    public static final TagKey<Instrument> COPPER_HORNS = bind("copper_horns");

    private WilderInstrumentTags() {
    }

    private static TagKey<Instrument> bind(String path) {
        return TagKey.create(Registry.INSTRUMENT_REGISTRY, WilderWild.id(path));
    }
}
