package net.frozenblock.wilderwild.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.client.sound.MusicType;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.BiomeKeys;

public class WildMusic {
    public static void playMusic() {
        BiomeModifications.create(new Identifier(WilderWild.MOD_ID, "modify_birch_forest_music")).add(ModificationPhase.REPLACEMENTS, (context) -> context.getBiomeKey().equals(BiomeKeys.BIRCH_FOREST),
                (selectionContext, modificationContext) -> modificationContext.getEffects().setMusic(MusicType.createIngameMusic(RegisterSounds.MUSIC_OVERWORLD_BIRCH_FOREST)));
    }
}
