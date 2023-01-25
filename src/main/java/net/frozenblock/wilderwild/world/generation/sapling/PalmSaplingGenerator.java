package net.frozenblock.wilderwild.world.generation.sapling;

import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.annotation.Nullable;
import net.frozenblock.wilderwild.world.additions.feature.WilderTreeConfigured;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class PalmSaplingGenerator extends AbstractTreeGrower {

	public PalmSaplingGenerator() {

	}

	@Nullable
	protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean largeHive) {
		return random.nextDouble() > 0.4 ? WilderTreeConfigured.PALM : random.nextDouble() > 0.3 ? WilderTreeConfigured.TALL_PALM : WilderTreeConfigured.TALL_WINE_PALM;
	}
}
