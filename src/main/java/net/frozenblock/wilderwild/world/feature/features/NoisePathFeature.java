package net.frozenblock.wilderwild.world.feature.features;

import com.mojang.serialization.Codec;
import net.frozenblock.lib.math.api.EasyNoiseSampler;
import net.frozenblock.wilderwild.world.feature.features.config.PathFeatureConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.synth.ImprovedNoise;

public class NoisePathFeature extends Feature<PathFeatureConfig> {
	public NoisePathFeature(Codec<PathFeatureConfig> codec) {
		super(codec);
	}

	public boolean place(FeaturePlaceContext<PathFeatureConfig> context) {
		boolean generated = false;
		PathFeatureConfig config = context.config();
		BlockPos blockPos = context.origin();
		WorldGenLevel level = context.level();
		int radiusSquared = config.radius * config.radius;
		EasyNoiseSampler.setSeed(level.getSeed());
		RandomSource random = level.getRandom();
		ImprovedNoise sampler = config.noise == 1 ? EasyNoiseSampler.perlinLocal : config.noise == 2 ? EasyNoiseSampler.perlinChecked : config.noise == 3 ? EasyNoiseSampler.perlinThreadSafe : EasyNoiseSampler.perlinXoro;
		int bx = blockPos.getX();
		int bz = blockPos.getZ();
		BlockPos.MutableBlockPos mutable = blockPos.mutable();

		for (int x = bx - config.radius; x <= bx + config.radius; x++) {
			for (int z = bz - config.radius; z <= bz + config.radius; z++) {
				double distance = ((bx - x) * (bx - x) + ((bz - z) * (bz - z)));
				if (distance < radiusSquared) {
					mutable.set(x, level.getHeight(Types.OCEAN_FLOOR, x, z) - 1, z);
					double sample = EasyNoiseSampler.sample(sampler, mutable, config.multiplier, config.multiplyY, config.useY);
					if (sample > config.minThresh && sample < config.maxThresh && level.getBlockState(mutable).is(config.replaceable)) {
						generated = true;
						level.setBlock(mutable, config.pathBlock.getState(random, mutable), 3);
					}
				}
			}
		}
		return generated;
	}

}
