package net.frozenblock.wilderwild.world.additions.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;

public final class WilderBiomeSettings {

    static void init() {
        BiomeModifications.create(WilderSharedConstants.id("replace_deep_dark_fog")).add(ModificationPhase.REPLACEMENTS,
                (context) -> context.getBiomeKey().equals(Biomes.DEEP_DARK),
                (modificationContext) -> modificationContext.getEffects().setFogColor(0));

		BiomeModifications.create(WilderSharedConstants.id("replace_badlands_foliage_color")).add(ModificationPhase.REPLACEMENTS,
				(context) -> context.hasTag(BiomeTags.IS_BADLANDS),
				(modificationContext) -> modificationContext.getEffects().setFoliageColor(11445290));

        WilderMusic.playMusic();
        WilderSpawns.addFireflies();
        WilderSpawns.addJellyfish();
		WilderSpawns.addTumbleweed();
		WilderSpawns.addRabbits();
    }
}
