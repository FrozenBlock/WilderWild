package net.frozenblock.wilderwild.registry;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.frozenblock.wilderwild.misc.FireflyColor;
import net.frozenblock.wilderwild.misc.JellyfishVariant;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.core.MappedRegistry;

public final class WilderRegistry {
	private WilderRegistry() {
		throw new UnsupportedOperationException("WilderRegistry contains only static declarations.");
	}

    public static final MappedRegistry<FireflyColor> FIREFLY_COLOR = FabricRegistryBuilder.createSimple(FireflyColor.class, WilderSharedConstants.id("firefly_color"))
            .attribute(RegistryAttribute.SYNCED)
            .buildAndRegister();
    public static final MappedRegistry<JellyfishVariant> JELLYFISH_VARIANT = FabricRegistryBuilder.createSimple(JellyfishVariant.class, WilderSharedConstants.id("jellyfish_color"))
            .attribute(RegistryAttribute.SYNCED)
            .buildAndRegister();

    public static void initRegistry() {
    }
}
