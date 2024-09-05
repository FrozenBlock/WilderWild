package net.frozenblock.wilderwild.datagen.registry;

import net.frozenblock.wilderwild.registry.WWItems;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.Instruments;

public class WWInstrumentRegistry {

	public static void bootstrap(BootstrapContext<Instrument> registry) {
		Instruments.register(registry, WWItems.ANCIENT_HORN_INSTRUMENT, WWSounds.ITEM_ANCIENT_HORN_CALL, 300, 256F);
		Instruments.register(registry, WWItems.SAX_COPPER_HORN, WWSounds.ITEM_COPPER_HORN_SAX_LOOP, 32767, 64F);
		Instruments.register(registry, WWItems.TUBA_COPPER_HORN, WWSounds.ITEM_COPPER_HORN_TUBA_LOOP, 32767, 64F);
		Instruments.register(registry, WWItems.FLUTE_COPPER_HORN, WWSounds.ITEM_COPPER_HORN_FLUTE_LOOP, 32767, 64F);
		Instruments.register(registry, WWItems.OBOE_COPPER_HORN, WWSounds.ITEM_COPPER_HORN_OBOE_LOOP, 32767, 64F);
		Instruments.register(registry, WWItems.CLARINET_COPPER_HORN, WWSounds.ITEM_COPPER_HORN_CLARINET_LOOP, 32767, 64F);
		Instruments.register(registry, WWItems.TRUMPET_COPPER_HORN, WWSounds.ITEM_COPPER_HORN_TRUMPET_LOOP, 32767, 64F);
		Instruments.register(registry, WWItems.TROMBONE_COPPER_HORN, WWSounds.ITEM_COPPER_HORN_TROMBONE_LOOP, 32767, 64F);
	}
}
