package net.frozenblock.wilderwild.mixin.snowlogging;

import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.frozenblock.wilderwild.config.BlockConfig;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.BambooSaplingBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BambooSaplingBlock.class)
public abstract class BambooSaplingBlockMixin extends Block {
	public BambooSaplingBlockMixin(Properties settings) {
		super(settings);
	}

	@Unique
	@Override
	protected boolean isRandomlyTicking(BlockState state) {
		return super.isRandomlyTicking(state) || SnowloggingUtils.isSnowlogged(state);
	}

	@Unique
	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return SnowloggingUtils.getSnowPlacementState(super.getStateForPlacement(context), context);
	}

	@Unique
	@Override
	protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		if (BlockConfig.get().snowlogging.snowlogging) builder.add(SnowloggingUtils.SNOW_LAYERS);
	}
}
