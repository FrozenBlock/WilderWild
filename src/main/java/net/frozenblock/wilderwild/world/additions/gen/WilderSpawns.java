package net.frozenblock.wilderwild.world.additions.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.frozenblock.lib.mobcategory.api.FrozenMobCategories;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.WilderEnumValues;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.frozenblock.wilderwild.tag.WilderBiomeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biomes;

public final class WilderSpawns {

    public static void addFireflies() {
        BiomeModifications.addSpawn(BiomeSelectors.tag(WilderBiomeTags.FIREFLY_SPAWNABLE_DURING_DAY),
				FrozenMobCategories.getCategory(WilderSharedConstants.MOD_ID, "fireflies"), RegisterEntities.FIREFLY, 12, 2, 4);

        BiomeModifications.addSpawn(BiomeSelectors.tag(WilderBiomeTags.FIREFLY_SPAWNABLE_CAVE),
				FrozenMobCategories.getCategory(WilderSharedConstants.MOD_ID, "fireflies"), RegisterEntities.FIREFLY, 5, 2, 4);

        BiomeModifications.addSpawn(BiomeSelectors.tag(WilderBiomeTags.FIREFLY_SPAWNABLE),
				FrozenMobCategories.getCategory(WilderSharedConstants.MOD_ID, "fireflies"), RegisterEntities.FIREFLY, 5, 1, 2);
    }

    public static void addJellyfish() {
        BiomeModifications.addSpawn(BiomeSelectors.tag(WilderBiomeTags.HAS_JELLYFISH),
				FrozenMobCategories.getCategory(WilderSharedConstants.MOD_ID, "jellyfish"), RegisterEntities.JELLYFISH, 2, 1, 1);
    }

	public static void addTumbleweed() {
		BiomeModifications.addSpawn(BiomeSelectors.tag(WilderBiomeTags.HAS_TUMBLEWEED_ENTITY),
				FrozenMobCategories.getCategory(WilderSharedConstants.MOD_ID, "tumbleweed"), RegisterEntities.TUMBLEWEED, 30, 1, 1);
	}

    public static void addRabbits() {
    BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.FOREST, Biomes.FLOWER_FOREST),
                MobCategory.CREATURE, EntityType.RABBIT, 10, 2, 4);
    }
}
