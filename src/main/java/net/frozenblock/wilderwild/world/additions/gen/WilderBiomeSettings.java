package net.frozenblock.wilderwild.world.additions.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.world.level.biome.Biomes;

public final class WilderBiomeSettings {

    static void init() {
        BiomeModifications.create(WilderWild.id("replace_deep_dark_fog")).add(ModificationPhase.REPLACEMENTS,
                (context) -> context.getBiomeKey().equals(Biomes.DEEP_DARK),
                (modificationContext) -> modificationContext.getEffects().setFogColor(0));

        WilderMusic.playMusic();
        WilderSpawns.addFireflies();
        WilderSpawns.addJellyfish();
    }
}
