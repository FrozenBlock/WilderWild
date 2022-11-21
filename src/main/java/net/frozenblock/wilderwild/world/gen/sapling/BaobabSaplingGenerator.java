package net.frozenblock.wilderwild.world.gen.sapling;

import net.frozenblock.wilderwild.world.feature.WilderTreeConfigured;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

public class BaobabSaplingGenerator extends BaobabTreeSaplingGenerator {

	public BaobabSaplingGenerator() {
	}


	@Nullable
	@Override
	protected Holder<? extends ConfiguredFeature<?, ?>> getBaobabTreeFeature(RandomSource random) {
		return random.nextFloat() < 0.856F ? WilderTreeConfigured.BAOBAB : WilderTreeConfigured.BAOBAB_TALL;
	}

	@Nullable
	@Override
	protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean bees) {
		return null;
	}
}
