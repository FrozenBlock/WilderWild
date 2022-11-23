package net.frozenblock.wilderwild.world.additions.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.frozenblock.wilderwild.WilderWild;
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
        WilderWild.logWild("Adding Music And Ambience To Biomes for", true);

        /** MUSIC */
        BiomeModifications.create(WilderWild.id("modify_birch_forest_music")).add(ModificationPhase.REPLACEMENTS, (context) -> context.getBiomeKey().equals(Biomes.BIRCH_FOREST),
                (selectionContext, modificationContext) -> modificationContext.getEffects().setMusic(Musics.createGameMusic(RegisterSounds.MUSIC_OVERWORLD_WILD_FORESTS)));
        BiomeModifications.create(WilderWild.id("modify_old_birch_forest_music")).add(ModificationPhase.REPLACEMENTS, (context) -> context.getBiomeKey().equals(Biomes.OLD_GROWTH_BIRCH_FOREST),
                (selectionContext, modificationContext) -> modificationContext.getEffects().setMusic(Musics.createGameMusic(RegisterSounds.MUSIC_OVERWORLD_WILD_FORESTS)));
        BiomeModifications.create(WilderWild.id("modify_flower_forest_music")).add(ModificationPhase.REPLACEMENTS, (context) -> context.getBiomeKey().equals(Biomes.FLOWER_FOREST),
                (selectionContext, modificationContext) -> modificationContext.getEffects().setMusic(Musics.createGameMusic(RegisterSounds.MUSIC_OVERWORLD_WILD_FORESTS)));

        /** AMBIENCE */
        // Deep Dark
        BiomeModifications.create(WilderWild.id("modify_deep_dark_ambience")).add(ModificationPhase.REPLACEMENTS, (context) -> context.getBiomeKey().equals(Biomes.DEEP_DARK),
                (selectionContext, modificationContext) -> modificationContext.getEffects().setAmbientSound(RegisterSounds.AMBIENT_DEEP_DARK_LOOP));
        BiomeModifications.create(WilderWild.id("modify_deep_dark_additions")).add(ModificationPhase.REPLACEMENTS, (context) -> context.getBiomeKey().equals(Biomes.DEEP_DARK),
                (selectionContext, modificationContext) -> modificationContext.getEffects().setAdditionsSound(new AmbientAdditionsSettings(RegisterSounds.AMBIENT_DEEP_DARK_ADDITIONS, 0.005D)));

        // Dripstone Caves
        BiomeModifications.create(WilderWild.id("modify_dripstone_caves_ambience")).add(ModificationPhase.REPLACEMENTS, (context) -> context.getBiomeKey().equals(Biomes.DRIPSTONE_CAVES),
                (selectionContext, modificationContext) -> modificationContext.getEffects().setAmbientSound(RegisterSounds.AMBIENT_DRIPSTONE_CAVES_LOOP));
        BiomeModifications.create(WilderWild.id("modify_dripstone_caves_additions")).add(ModificationPhase.REPLACEMENTS, (context) -> context.getBiomeKey().equals(Biomes.DRIPSTONE_CAVES),
                (selectionContext, modificationContext) -> modificationContext.getEffects().setAdditionsSound(new AmbientAdditionsSettings(RegisterSounds.AMBIENT_DRIPSTONE_CAVES_ADDITIONS, 0.01D)));

        // Lush Caves
        BiomeModifications.create(WilderWild.id("modify_lush_caves_ambience")).add(ModificationPhase.REPLACEMENTS, (context) -> context.getBiomeKey().equals(Biomes.LUSH_CAVES),
                (selectionContext, modificationContext) -> modificationContext.getEffects().setAmbientSound(RegisterSounds.AMBIENT_LUSH_CAVES_LOOP));
        BiomeModifications.create(WilderWild.id("modify_lush_caves_additions")).add(ModificationPhase.REPLACEMENTS, (context) -> context.getBiomeKey().equals(Biomes.LUSH_CAVES),
                (selectionContext, modificationContext) -> modificationContext.getEffects().setAdditionsSound(new AmbientAdditionsSettings(RegisterSounds.AMBIENT_LUSH_CAVES_ADDITIONS, 0.01D)));

        // Generic Caves
        /*BiomeModifications.create(WilderWild.id("modify_caves_ambience")).add(ModificationPhase.REPLACEMENTS, (context) -> context.getBiomeKey().equals(Biomes.SOMETHING),
                (selectionContext, modificationContext) -> modificationContext.getEffects().setAmbientSound(RegisterSounds.AMBIENT_GENERIC_CAVES_LOOP));*/
    }
}
