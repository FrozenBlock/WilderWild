package net.frozenblock.wilderwild.data.worldgen.biome.impl;

import net.minecraft.world.level.biome.BiomeSpecialEffects;

/**
 * Used becaused Enum extension is not supported on common.
 */
public final class WWGrassColorModifier {
	public static BiomeSpecialEffects.GrassColorModifier WILDERWILD_TUNDRA;

	static {
		BiomeSpecialEffects.GrassColorModifier.values();
	}
}
