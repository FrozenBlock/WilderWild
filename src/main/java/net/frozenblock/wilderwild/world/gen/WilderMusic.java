package net.frozenblock.wilderwild.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.misc.config.ClothConfigInteractionHandler;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.minecraft.sounds.Musics;
import net.minecraft.world.level.biome.AmbientAdditionsSettings;
import net.minecraft.world.level.biome.Biomes;

public final class WilderMusic {
	private WilderMusic() {
		throw new UnsupportedOperationException("WilderMusic contains only static declarations.");
	}

    public static void playMusic() {
        WilderSharedConstants.logWild("Adding Music And Ambience To Biomes for", true);

        /** MUSIC */
        BiomeModifications.create(WilderSharedConstants.id("modify_birch_forest_music")).add(ModificationPhase.REPLACEMENTS, (context) -> context.getBiomeKey().equals(Biomes.BIRCH_FOREST),
                (selectionContext, modificationContext) -> {
					if (ClothConfigInteractionHandler.birchForestMusic()) {
						modificationContext.getEffects().setMusic(Musics.createGameMusic(RegisterSounds.MUSIC_OVERWORLD_WILD_FORESTS));
					}
		});
        BiomeModifications.create(WilderSharedConstants.id("modify_old_birch_forest_music")).add(ModificationPhase.REPLACEMENTS, (context) -> context.getBiomeKey().equals(Biomes.OLD_GROWTH_BIRCH_FOREST),
                (selectionContext, modificationContext) -> {
					if (ClothConfigInteractionHandler.birchForestMusic()) {
						modificationContext.getEffects().setMusic(Musics.createGameMusic(RegisterSounds.MUSIC_OVERWORLD_WILD_FORESTS));
					}
		});
        BiomeModifications.create(WilderSharedConstants.id("modify_flower_forest_music")).add(ModificationPhase.REPLACEMENTS, (context) -> context.getBiomeKey().equals(Biomes.FLOWER_FOREST),
                (selectionContext, modificationContext) -> {
					if (ClothConfigInteractionHandler.flowerForestMusic()) {
						modificationContext.getEffects().setMusic(Musics.createGameMusic(RegisterSounds.MUSIC_OVERWORLD_WILD_FORESTS));
					}
				});

        /** AMBIENCE */
        // Deep Dark
        BiomeModifications.create(WilderSharedConstants.id("modify_deep_dark_ambience")).add(ModificationPhase.REPLACEMENTS, (context) -> context.getBiomeKey().equals(Biomes.DEEP_DARK),
                (selectionContext, modificationContext) -> {
					if (ClothConfigInteractionHandler.deepDarkAmbience()) {
						modificationContext.getEffects().setAmbientSound(RegisterSounds.AMBIENT_DEEP_DARK_LOOP);
					}
		});
        BiomeModifications.create(WilderSharedConstants.id("modify_deep_dark_additions")).add(ModificationPhase.REPLACEMENTS, (context) -> context.getBiomeKey().equals(Biomes.DEEP_DARK),
                (selectionContext, modificationContext) -> {
					if (ClothConfigInteractionHandler.deepDarkAmbience()) {
						modificationContext.getEffects().setAdditionsSound(new AmbientAdditionsSettings(RegisterSounds.AMBIENT_DEEP_DARK_ADDITIONS, 0.005D));
					}
		});

        // Dripstone Caves
        BiomeModifications.create(WilderSharedConstants.id("modify_dripstone_caves_ambience")).add(ModificationPhase.REPLACEMENTS, (context) -> context.getBiomeKey().equals(Biomes.DRIPSTONE_CAVES),
                (selectionContext, modificationContext) -> {
					if (ClothConfigInteractionHandler.dripstoneCavesAmbience()) {
						modificationContext.getEffects().setAmbientSound(RegisterSounds.AMBIENT_DRIPSTONE_CAVES_LOOP);
					}
		});
        BiomeModifications.create(WilderSharedConstants.id("modify_dripstone_caves_additions")).add(ModificationPhase.REPLACEMENTS, (context) -> context.getBiomeKey().equals(Biomes.DRIPSTONE_CAVES),
                (selectionContext, modificationContext) -> {
					if (ClothConfigInteractionHandler.dripstoneCavesAmbience()) {
						modificationContext.getEffects().setAdditionsSound(new AmbientAdditionsSettings(RegisterSounds.AMBIENT_DRIPSTONE_CAVES_ADDITIONS, 0.01D));
					}
		});

        // Lush Caves
        BiomeModifications.create(WilderSharedConstants.id("modify_lush_caves_ambience")).add(ModificationPhase.REPLACEMENTS, (context) -> context.getBiomeKey().equals(Biomes.LUSH_CAVES),
                (selectionContext, modificationContext) -> {
					if (ClothConfigInteractionHandler.lushCavesAmbience()) {
						modificationContext.getEffects().setAmbientSound(RegisterSounds.AMBIENT_LUSH_CAVES_LOOP);
					}
		});
        BiomeModifications.create(WilderSharedConstants.id("modify_lush_caves_additions")).add(ModificationPhase.REPLACEMENTS, (context) -> context.getBiomeKey().equals(Biomes.LUSH_CAVES),
                (selectionContext, modificationContext) -> {
					if (ClothConfigInteractionHandler.lushCavesAmbience()) {
						modificationContext.getEffects().setAdditionsSound(new AmbientAdditionsSettings(RegisterSounds.AMBIENT_LUSH_CAVES_ADDITIONS, 0.01D));
					}
		});

        // Generic Caves
        /*BiomeModifications.create(WilderSharedConstants.id("modify_caves_ambience")).add(ModificationPhase.REPLACEMENTS, (context) -> context.getBiomeKey().equals(Biomes.SOMETHING),
                (selectionContext, modificationContext) -> modificationContext.getEffects().setAmbientSound(RegisterSounds.AMBIENT_GENERIC_CAVES_LOOP));*/
    }
}
