package net.frozenblock.wilderwild.registry;

import java.util.ArrayList;
import java.util.List;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.FireflyColor;
import net.frozenblock.wilderwild.misc.JellyfishVariant;
import net.minecraft.core.MappedRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public final class WilderRegistry {
	private WilderRegistry() {
		throw new UnsupportedOperationException("WilderRegistry contains only static declarations.");
	}

    public static final MappedRegistry<FireflyColor> FIREFLY_COLOR = FabricRegistryBuilder.createSimple(FireflyColor.class, WilderWild.id("firefly_color"))
            .attribute(RegistryAttribute.SYNCED)
            .buildAndRegister();
    public static final MappedRegistry<JellyfishVariant> JELLYFISH_VARIANT = FabricRegistryBuilder.createSimple(JellyfishVariant.class, WilderWild.id("jellyfish_color"))
            .attribute(RegistryAttribute.SYNCED)
            .buildAndRegister();

    public static final List<ResourceKey<Biome>> GRAVEL_BEACH_BIOMES = new ArrayList<>() {{
        add(Biomes.BIRCH_FOREST);
		add(Biomes.FROZEN_RIVER);
		add(RegisterWorldgen.MIXED_FOREST);
		add(Biomes.OLD_GROWTH_BIRCH_FOREST);
		add(Biomes.OLD_GROWTH_PINE_TAIGA);
		add(Biomes.OLD_GROWTH_SPRUCE_TAIGA);
        add(Biomes.TAIGA);
        add(Biomes.SNOWY_TAIGA);
    }};

    public static final List<ResourceKey<Biome>> SAND_BEACH_BIOMES = new ArrayList<>() {{
		add(Biomes.DARK_FOREST);
		add(Biomes.FLOWER_FOREST);
		add(Biomes.FOREST);
    }};

    public static final List<ResourceKey<Biome>> MULTILAYER_SAND_BEACH_BIOMES = new ArrayList<>() {{
		add(Biomes.BAMBOO_JUNGLE);
        add(Biomes.JUNGLE);
		add(Biomes.SAVANNA);
        add(Biomes.SPARSE_JUNGLE);
    }};

    public static void initRegistry() {

    }
}
