package net.frozenblock.wilderwild.tag;

import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Instrument;

public final class WilderInstrumentTags {
	private WilderInstrumentTags() {
		throw new UnsupportedOperationException("WilderInstrumentTags contains only static declarations.");
	}

    public static final TagKey<Instrument> ANCIENT_HORNS = bind("ancient_horns");
    public static final TagKey<Instrument> COPPER_HORNS = bind("copper_horns");

    private static TagKey<Instrument> bind(String path) {
        return TagKey.create(Registries.INSTRUMENT, WilderSharedConstants.id(path));
    }
}
