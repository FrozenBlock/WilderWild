package net.frozenblock.wilderwild.world.gen.sapling;

import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.annotation.Nullable;
import net.frozenblock.wilderwild.world.feature.WilderTreeConfigured;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class ShrubBushGrower extends AbstractTreeGrower {
	public ShrubBushGrower() {}

	@Nullable
	protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean largeHive) {
		return WilderTreeConfigured.BIG_SHRUB;
	}
}
