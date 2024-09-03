package net.frozenblock.wilderwild.block;

import com.mojang.serialization.MapCodec;
import net.frozenblock.wilderwild.registry.RegisterParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class MapleLeavesBlock  extends LeavesBlock {
	public static final MapCodec<MapleLeavesBlock> CODEC = simpleCodec(MapleLeavesBlock::new);

	@Override
	public @NotNull MapCodec<MapleLeavesBlock> codec() {
		return CODEC;
	}

	public MapleLeavesBlock(BlockBehaviour.Properties settings) {
		super(settings);
	}

	@Override
	public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
		super.animateTick(state, world, pos, random);
		if (random.nextInt(40) == 0) {
			BlockPos blockPos = pos.below();
			BlockState blockState = world.getBlockState(blockPos);
			if (!isFaceFull(blockState.getCollisionShape(world, blockPos), Direction.UP)) {
				ParticleUtils.spawnParticleBelow(world, pos, random, RegisterParticles.MAPLE_LEAVES);
			}
		}
	}
}
