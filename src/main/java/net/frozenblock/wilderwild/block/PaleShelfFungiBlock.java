package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.registry.WWParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class PaleShelfFungiBlock extends ShelfFungiBlock {
	public static final int MIN_PARTICLE_SPAWN_WIDTH = -4;
	public static final int MAX_PARTICLE_SPAWN_WIDTH = 4;
	public static final int MIN_PARTICLE_SPAWN_HEIGHT = -4;
	public static final int MAX_PARTICLE_SPAWN_HEIGHT = 4;
	public static final int PARTICLE_SPAWN_ATTEMPTS = 7;

	public PaleShelfFungiBlock(@NotNull Properties settings) {
		super(settings);
	}

	@Override
	public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		if (PaleMushroomBlock.isNight(level)) {
			int i = pos.getX();
			int j = pos.getY();
			int k = pos.getZ();
			BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
			for (int l = 0; l < PARTICLE_SPAWN_ATTEMPTS; ++l) {
				if (random.nextBoolean()) continue;
				mutable.set(
					i + Mth.nextInt(random, MIN_PARTICLE_SPAWN_WIDTH, MAX_PARTICLE_SPAWN_WIDTH),
					j + Mth.nextInt(random, MIN_PARTICLE_SPAWN_HEIGHT, MAX_PARTICLE_SPAWN_HEIGHT),
					k + Mth.nextInt(random, MIN_PARTICLE_SPAWN_WIDTH, MAX_PARTICLE_SPAWN_WIDTH)
				);
				BlockState blockState = level.getBlockState(mutable);
				if (!blockState.isCollisionShapeFullBlock(level, mutable) && !level.isRainingAt(mutable)) {
					level.addParticle(
						WWParticleTypes.PALE_SPORE,
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
