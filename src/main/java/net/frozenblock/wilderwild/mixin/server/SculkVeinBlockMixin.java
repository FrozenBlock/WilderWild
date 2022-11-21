package net.frozenblock.wilderwild.mixin.server;

import java.util.Iterator;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.tag.WilderBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.MultifaceSpreader;
import net.minecraft.world.level.block.SculkBehaviour;
import net.minecraft.world.level.block.SculkSpreader;
import net.minecraft.world.level.block.SculkVeinBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(SculkVeinBlock.class)
public abstract class SculkVeinBlockMixin extends MultifaceBlock implements SculkBehaviour, SimpleWaterloggedBlock {

	@Final
	@Shadow
	private MultifaceSpreader veinSpreader;

	private SculkVeinBlockMixin(Properties properties) {
		super(properties);
	}

	@Inject(method = "attemptPlaceSculk", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/Direction;getOpposite()Lnet/minecraft/core/Direction;", opcode = 0, shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD)
	private void attemptPlaceSculk(SculkSpreader sculkBehavior, LevelAccessor level, BlockPos pos, RandomSource random, CallbackInfoReturnable<Boolean> cir, BlockState blockState, TagKey<Block> replaceableBlocks, Iterator<Direction> var7, Direction direction, BlockPos blockPos, BlockState blockState2, BlockState blockState3) {
		boolean canReturn = false;
		if (blockState2.is(WilderBlockTags.SCULK_STAIR_REPLACEABLE_WORLDGEN) || blockState2.is(WilderBlockTags.SCULK_STAIR_REPLACEABLE)) {
			blockState3 = RegisterBlocks.SCULK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, blockState2.getValue(StairBlock.FACING)).setValue(StairBlock.HALF, blockState2.getValue(StairBlock.HALF)).setValue(StairBlock.SHAPE, blockState2.getValue(StairBlock.SHAPE)).setValue(StairBlock.WATERLOGGED, blockState2.getValue(StairBlock.WATERLOGGED));
			canReturn = true;
		} else if (blockState2.is(WilderBlockTags.SCULK_WALL_REPLACEABLE_WORLDGEN) || blockState2.is(WilderBlockTags.SCULK_WALL_REPLACEABLE)) {
			blockState3 = RegisterBlocks.SCULK_WALL.defaultBlockState().setValue(WallBlock.UP, blockState2.getValue(WallBlock.UP)).setValue(WallBlock.NORTH_WALL, blockState2.getValue(WallBlock.NORTH_WALL))
					.setValue(WallBlock.EAST_WALL, blockState2.getValue(WallBlock.EAST_WALL)).setValue(WallBlock.WEST_WALL, blockState2.getValue(WallBlock.WEST_WALL))
					.setValue(WallBlock.SOUTH_WALL, blockState2.getValue(WallBlock.SOUTH_WALL)).setValue(WallBlock.WATERLOGGED, blockState2.getValue(WallBlock.WATERLOGGED));
			canReturn = true;
		} else if (blockState2.is(WilderBlockTags.SCULK_SLAB_REPLACEABLE_WORLDGEN) || blockState2.is(WilderBlockTags.SCULK_SLAB_REPLACEABLE)) {
			blockState3 = RegisterBlocks.SCULK_SLAB.defaultBlockState().setValue(SlabBlock.WATERLOGGED, blockState2.getValue(SlabBlock.WATERLOGGED)).setValue(SlabBlock.TYPE, blockState2.getValue(SlabBlock.TYPE));
			canReturn = true;
		}

		if (canReturn) {
			level.setBlock(blockPos, blockState3, 3);
			Block.pushEntitiesUp(blockState2, blockState3, level, blockPos);
			this.veinSpreader.spreadAll(blockState3, level, blockPos, sculkBehavior.isWorldGeneration());
		}
	}

	@Redirect(method = "onDischarged", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z", ordinal = 1))
	private boolean onDischarged(BlockState state, Block block) {
		return state.is(WilderBlockTags.SCULK_VEIN_REMOVE);
	}

    /*@Inject(method = "onDischarged", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/LevelAccessor;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    public void onDischarged(LevelAccessor level, BlockState state, BlockPos pos, RandomSource random, CallbackInfo ci) {
        boolean canReturn = false;
        if (state.is(SculkVeinBlock.class.cast(this))) {
            for(Direction direction : DIRECTIONS) {
                BooleanProperty booleanProperty = getFaceProperty(direction);
                if (state.getValue(booleanProperty) && level.getBlockState(pos.relative(direction)).is(WilderBlockTags.SCULK_VEIN_REMOVE)) {
                    state = state.setValue(booleanProperty, false);
                    canReturn = true;
                }
            }

            if (!hasAnyFace(state)) {
                FluidState fluidState = level.getFluidState(pos);
                state = (fluidState.isEmpty() ? Blocks.AIR : Blocks.WATER).defaultBlockState();
            }

            if (canReturn) {
                level.setBlock(pos, state, 3);
                SculkBehaviour.super.onDischarged(level, state, pos, random);
                ci.cancel();
            }
        }
    }*/
}
