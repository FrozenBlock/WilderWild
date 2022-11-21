package net.frozenblock.wilderwild.world.feature.features;

import com.mojang.serialization.Codec;
import net.frozenblock.wilderwild.world.feature.features.config.WilderPillarConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.material.Fluids;

public class UpwardsPillarFeature extends Feature<WilderPillarConfig> {
	public UpwardsPillarFeature(Codec<WilderPillarConfig> codec) {
		super(codec);
	}

	public boolean place(FeaturePlaceContext<WilderPillarConfig> context) {
		boolean bl = false;
		BlockPos blockPos = context.origin();
		WorldGenLevel level = context.level();
		RandomSource random = level.getRandom();
		BlockPos.MutableBlockPos mutable = blockPos.mutable();
		int bx = blockPos.getX();
		int bz = blockPos.getZ();
		int by = blockPos.getY();
		int height = context.config().height.sample(random);
		for (int y = 0; y < height; y++) {
			if (context.config().replaceable.contains(level.getBlockState(mutable).getBlockHolder()) || level.getBlockState(mutable).isAir() || level.getBlockState(mutable).getFluidState() != Fluids.EMPTY.defaultFluidState()) {
				bl = true;
				level.setBlock(mutable, context.config().columnBlock, 3);
				mutable.set(bx, by + y, bz);
			} else {
				mutable.set(bx, by + y, bz);
			}
		}
		return bl;
	}

}
