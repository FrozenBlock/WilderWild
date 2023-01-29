package net.frozenblock.wilderwild.entity.ai.jellyfish;

import jdk.jfr.Experimental;
import net.frozenblock.lib.entity.api.behavior.MoveToBlockBehavior;
import net.frozenblock.wilderwild.entity.Jellyfish;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;

@Experimental
public class JellyfishHide extends MoveToBlockBehavior<Jellyfish> {

	public JellyfishHide(Jellyfish mob, double speedModifier, int searchRange, int verticalSearchRange) {
		super(mob, speedModifier, searchRange, verticalSearchRange);
	}

	@Override
	public void start(@NotNull ServerLevel level, @NotNull Jellyfish entity, long gameTime) {
		super.start(level, entity, gameTime);
	}

	@Override
	public boolean checkExtraStartConditions(@NotNull ServerLevel level, @NotNull Jellyfish owner) {
		return owner.shouldHide() && super.checkExtraStartConditions(level, owner);
	}

	@Override
	public boolean canStillUse(@NotNull ServerLevel level, @NotNull Jellyfish entity, long gameTime) {
		return entity.shouldHide() && super.canStillUse(level, entity, gameTime);
	}

	@Override
	protected void tick(@NotNull ServerLevel level, @NotNull Jellyfish owner, long gameTime) {
		if (this.isReachedTarget()) {
			//TODO: Hide sound
			owner.playSound(RegisterSounds.BLOCK_MESOGLEA_BREAK, 0.6F, 0.9F + level.random.nextFloat() * 0.2F);
			owner.vanishing = true;
		}

		super.tick(level, owner, gameTime);
	}

	@Override
	public boolean isValidTarget(LevelReader level, BlockPos pos) {
		BlockState state = level.getBlockState(pos);
		return state.is(this.mob.getVariant().mesogleaType) && state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED);
	}

	@Override
	protected BlockPos getMoveToTarget() {
		return this.blockPos;
	}
}
