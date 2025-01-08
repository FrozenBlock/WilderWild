package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.registry.WWParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.state.BlockState;

public class HugePaleMushroomBlock extends HugeMushroomBlock {
	public static final int MIN_PARTICLE_SPAWN_WIDTH = -4;
	public static final int MAX_PARTICLE_SPAWN_WIDTH = 4;
	public static final int MIN_PARTICLE_SPAWN_HEIGHT = -2;
	public static final int MAX_PARTICLE_SPAWN_HEIGHT = 2;
	public static final int PARTICLE_SPAWN_ATTEMPTS = 7;

	public HugePaleMushroomBlock(Properties properties) {
		super(properties);
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		super.animateTick(state, level, pos, random);
		if (PaleMushroomBlock.isNight(level)) {
			if (level.getBlockState(pos.below()).is(Blocks.MUSHROOM_STEM)) {
				int i = pos.getX();
				int j = pos.getY();
				int k = pos.getZ();
				BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
				for (int l = 0; l < PARTICLE_SPAWN_ATTEMPTS; ++l) {
					mutable.set(
						i + Mth.nextInt(random, MIN_PARTICLE_SPAWN_WIDTH, MAX_PARTICLE_SPAWN_WIDTH),
						j + Mth.nextInt(random, MIN_PARTICLE_SPAWN_HEIGHT, MAX_PARTICLE_SPAWN_HEIGHT),
						k + Mth.nextInt(random, MIN_PARTICLE_SPAWN_WIDTH, MAX_PARTICLE_SPAWN_WIDTH)
					);
					BlockState blockState = level.getBlockState(mutable);
					if (!blockState.isCollisionShapeFullBlock(level, mutable) && random.nextFloat() <= 0.025F) {
						level.addParticle(
							WWParticleTypes.PALE_FOG,
							mutable.getX() + random.nextDouble(),
							mutable.getY() + random.nextDouble(),
							mutable.getZ() + random.nextDouble(),
							0D,
							0D,
							0D
						);
					}
				}
			}
		}
	}
}
