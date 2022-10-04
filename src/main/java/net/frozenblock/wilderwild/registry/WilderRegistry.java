package net.frozenblock.wilderwild.registry;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.FireflyColor;
import net.frozenblock.wilderwild.misc.JellyfishVariant;
import net.minecraft.core.MappedRegistry;

public class WilderRegistry {

    public static final MappedRegistry<FireflyColor> FIREFLY_COLOR = FabricRegistryBuilder.createSimple(FireflyColor.class, WilderWild.id("firefly_color"))
            .attribute(RegistryAttribute.SYNCED)
            .buildAndRegister();
    public static final MappedRegistry<JellyfishVariant> JELLYFISH_VARIANT = FabricRegistryBuilder.createSimple(JellyfishVariant.class, WilderWild.id("jellyfish_color"))
            .attribute(RegistryAttribute.SYNCED)
            .buildAndRegister();

    public static void initRegistry() {

    }
}
