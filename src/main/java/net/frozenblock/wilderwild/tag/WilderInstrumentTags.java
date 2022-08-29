package net.frozenblock.wilderwild.tag;

import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Instrument;

public final class WilderInstrumentTags {
    public static final TagKey<Instrument> ANCIENT_HORNS = of("ancient_horns");
    public static final TagKey<Instrument> COPPER_HORNS = of("copper_horns");

    private WilderInstrumentTags() {
    }

    private static TagKey<Instrument> of(String path) {
        return TagKey.create(Registry.INSTRUMENT_REGISTRY, WilderWild.id(path));
    }
}
