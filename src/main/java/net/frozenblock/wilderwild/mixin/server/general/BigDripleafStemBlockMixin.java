package net.frozenblock.wilderwild.mixin.server.general;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BigDripleafStemBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BigDripleafStemBlock.class)
public final class BigDripleafStemBlockMixin extends HorizontalDirectionalBlock {

	@Unique
    private static final BooleanProperty WILDERWILD$POWERED = BlockStateProperties.POWERED;

    private BigDripleafStemBlockMixin(Properties properties) {
        super(properties);
    }

    @Inject(at = @At("TAIL"), method = "createBlockStateDefinition")
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder, CallbackInfo info) {
        builder.add(WILDERWILD$POWERED);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void BigDripleafStemBlock(BlockBehaviour.Properties setting, CallbackInfo ci) {
        BigDripleafStemBlock stem = BigDripleafStemBlock.class.cast(this);
        stem.registerDefaultState(stem.defaultBlockState().setValue(WILDERWILD$POWERED, false));
    }

    @Nullable
    @Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(WILDERWILD$POWERED, context.getLevel().hasNeighborSignal(context.getClickedPos()));
    }

	@Override
    public void neighborChanged(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Block block, @NotNull BlockPos fromPos, boolean isMoving) {
        if (!level.isClientSide) {
            BlockState fromState = level.getBlockState(fromPos);
            if (fromState.is(Blocks.BIG_DRIPLEAF_STEM)) {
                if (pos.getY() > fromPos.getY()) {
                    level.setBlock(pos, state.setValue(WILDERWILD$POWERED, fromState.getValue(WILDERWILD$POWERED)), 3);
                }
            } else {
                boolean powered = state.getValue(WILDERWILD$POWERED);
                boolean power = level.hasNeighborSignal(pos);
                if (powered != power) {
                    if (powered) {
                        level.setBlock(pos, state.setValue(WILDERWILD$POWERED, false), 3);
                    } else {
                        level.setBlock(pos, state.setValue(WILDERWILD$POWERED, true), 3);
                    }
                }
            }
        }
    }

}
