package net.frozenblock.wilderwild.world.additions.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.WilderEnumValues;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.frozenblock.wilderwild.tag.WilderBiomeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biomes;

public final class WilderSpawns {

    public static void addFireflies() {
        BiomeModifications.addSpawn(BiomeSelectors.tag(WilderBiomeTags.FIREFLY_SPAWNABLE_DURING_DAY),
				WilderEnumValues.FIREFLIES, RegisterEntities.FIREFLY, 12, 2, 4);

        BiomeModifications.addSpawn(BiomeSelectors.tag(WilderBiomeTags.FIREFLY_SPAWNABLE_CAVE),
				WilderEnumValues.FIREFLIES, RegisterEntities.FIREFLY, 5, 2, 4);

        BiomeModifications.addSpawn(BiomeSelectors.tag(WilderBiomeTags.FIREFLY_SPAWNABLE),
				WilderEnumValues.FIREFLIES, RegisterEntities.FIREFLY, 5, 1, 2);
    }

    public static void addJellyfish() {
        BiomeModifications.addSpawn(BiomeSelectors.tag(WilderBiomeTags.HAS_JELLYFISH),
				WilderEnumValues.JELLYFISH, RegisterEntities.JELLYFISH, 2, 1, 1);
    }

	public static void addTumbleweed() {
		BiomeModifications.addSpawn(BiomeSelectors.tag(WilderBiomeTags.HAS_TUMBLEWEED_ENTITY),
				WilderWild.TUMBLEWEED, RegisterEntities.TUMBLEWEED, 30, 1, 1);
	}

    public static void addRabbits() {
    BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.FOREST, Biomes.FLOWER_FOREST),
                MobCategory.AMBIENT, EntityType.RABBIT, 10, 2, 4);
    }
}
