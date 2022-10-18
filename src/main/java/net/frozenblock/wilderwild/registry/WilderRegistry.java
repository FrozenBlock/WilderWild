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

public class WilderRegistry {

    public static final MappedRegistry<FireflyColor> FIREFLY_COLOR = FabricRegistryBuilder.createSimple(FireflyColor.class, WilderWild.id("firefly_color"))
            .attribute(RegistryAttribute.SYNCED)
            .buildAndRegister();
    public static final MappedRegistry<JellyfishVariant> JELLYFISH_VARIANT = FabricRegistryBuilder.createSimple(JellyfishVariant.class, WilderWild.id("jellyfish_color"))
            .attribute(RegistryAttribute.SYNCED)
            .buildAndRegister();

    public static final List<ResourceKey<Biome>> GRAVEL_BEACH_BIOMES = new ArrayList<>() {{
        add(Biomes.BIRCH_FOREST);
        add(Biomes.TAIGA);
        add(Biomes.FROZEN_RIVER);
        add(Biomes.OLD_GROWTH_BIRCH_FOREST);
        add(Biomes.OLD_GROWTH_PINE_TAIGA);
        add(Biomes.OLD_GROWTH_SPRUCE_TAIGA);
        add(Biomes.SNOWY_TAIGA);
        add(RegisterWorldgen.MIXED_FOREST);
    }};

    public static final List<ResourceKey<Biome>> SAND_BEACH_BIOMES = new ArrayList<>() {{
        add(Biomes.FLOWER_FOREST);
        add(Biomes.FOREST);
        add(Biomes.DARK_FOREST);
    }};

    public static final List<ResourceKey<Biome>> OTHER_SAND_BEACH_BIOMES = new ArrayList<>() {{
        add(Biomes.JUNGLE);
        add(Biomes.SPARSE_JUNGLE);
        add(Biomes.BAMBOO_JUNGLE);
        add(Biomes.SAVANNA);
    }};

	public static final List<ResourceKey<Biome>> TAIGA_PATH_BIOMES = new ArrayList<>() {{
		add(Biomes.TAIGA);
		add(Biomes.OLD_GROWTH_PINE_TAIGA);
		add(Biomes.OLD_GROWTH_SPRUCE_TAIGA);
		add(Biomes.WINDSWEPT_FOREST);
	}};

    public static void initRegistry() {

    }
}
