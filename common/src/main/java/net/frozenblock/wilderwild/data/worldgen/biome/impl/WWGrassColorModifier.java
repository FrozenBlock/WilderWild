package net.frozenblock.wilderwild.data.worldgen.biome.impl;

import net.frozenblock.wilderwild.data.worldgen.biome.Tundra;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSpecialEffects;

/**
 * Used becaused Enum extension is not supported on common.
 */
public final class WWGrassColorModifier {
	public static BiomeSpecialEffects.GrassColorModifier WILDERWILD_TUNDRA;

	static {
		BiomeSpecialEffects.GrassColorModifier.values();
	}

	public static int modifyColorTundra(double x, double z, int baseColor) {
		final double noise = Biome.BIOME_INFO_NOISE.get(x * 0.0225D, z * 0.0225D);
		if (noise < -0.5D) return Tundra.GRASS_COLOR_BROWN;
		if (noise < -0.35D) return Tundra.GRASS_COLOR_ORANGE;
		if (noise > 0.8D) return Tundra.GRASS_COLOR_BLUE_GREENISH;
		if (noise > 0.5D) return Tundra.GRASS_COLOR_LIGHTER_GREEN;
		return baseColor;
	}
}
