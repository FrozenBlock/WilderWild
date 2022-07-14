package net.frozenblock.wilderwild.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.client.sound.MusicType;
import net.minecraft.sound.BiomeAdditionsSound;
import net.minecraft.world.biome.BiomeKeys;

public class WildMusic {
    public static void playMusic() {
        WilderWild.logWild("Adding Music And Ambience To Biomes for", true);

        /** MUSIC */
        BiomeModifications.create(WilderWild.id("modify_birch_forest_music")).add(ModificationPhase.REPLACEMENTS, (context) -> context.getBiomeKey().equals(BiomeKeys.BIRCH_FOREST),
                (selectionContext, modificationContext) -> modificationContext.getEffects().setMusic(MusicType.createIngameMusic(RegisterSounds.MUSIC_OVERWORLD_WILD_FORESTS)));
        BiomeModifications.create(WilderWild.id("modify_old_birch_forest_music")).add(ModificationPhase.REPLACEMENTS, (context) -> context.getBiomeKey().equals(BiomeKeys.OLD_GROWTH_BIRCH_FOREST),
                (selectionContext, modificationContext) -> modificationContext.getEffects().setMusic(MusicType.createIngameMusic(RegisterSounds.MUSIC_OVERWORLD_WILD_FORESTS)));
        BiomeModifications.create(WilderWild.id("modify_flower_forest_music")).add(ModificationPhase.REPLACEMENTS, (context) -> context.getBiomeKey().equals(BiomeKeys.FLOWER_FOREST),
                (selectionContext, modificationContext) -> modificationContext.getEffects().setMusic(MusicType.createIngameMusic(RegisterSounds.MUSIC_OVERWORLD_WILD_FORESTS)));

        /** AMBIENCE */
        //deep dark
        BiomeModifications.create(WilderWild.id("modify_deep_dark_ambience")).add(ModificationPhase.REPLACEMENTS, (context) -> context.getBiomeKey().equals(BiomeKeys.DEEP_DARK),
                (selectionContext, modificationContext) -> modificationContext.getEffects().setAmbientSound(RegisterSounds.AMBIENT_DEEP_DARK_LOOP));
        BiomeModifications.create(WilderWild.id("modify_deep_dark_additions")).add(ModificationPhase.REPLACEMENTS, (context) -> context.getBiomeKey().equals(BiomeKeys.DEEP_DARK),
                (selectionContext, modificationContext) -> modificationContext.getEffects().setAdditionsSound(new BiomeAdditionsSound(RegisterSounds.AMBIENT_DEEP_DARK_ADDITIONS, 0.005D)));

        //dripstone caves
        BiomeModifications.create(WilderWild.id("modify_dripstone_caves_ambience")).add(ModificationPhase.REPLACEMENTS, (context) -> context.getBiomeKey().equals(BiomeKeys.DRIPSTONE_CAVES),
                (selectionContext, modificationContext) -> modificationContext.getEffects().setAmbientSound(RegisterSounds.AMBIENT_DRIPSTONE_CAVES_LOOP));

        //lush caves
        BiomeModifications.create(WilderWild.id("modify_lush_caves_ambience")).add(ModificationPhase.REPLACEMENTS, (context) -> context.getBiomeKey().equals(BiomeKeys.LUSH_CAVES),
                (selectionContext, modificationContext) -> modificationContext.getEffects().setAmbientSound(RegisterSounds.AMBIENT_LUSH_CAVES_LOOP));
    }
}
