package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.tag.WilderBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;

import static net.minecraft.world.level.block.MultifaceBlock.getFaceProperty;
import static net.minecraft.world.level.block.MultifaceBlock.hasFace;

@Mixin(SculkVeinBlock.class)
public class SculkVeinBlockMixin {

    @Final
    @Shadow
    private MultifaceSpreader veinSpreader;

    @Inject(at = @At("HEAD"), method = "attemptPlaceSculk", cancellable = true)
    private void attemptPlaceSculk(SculkSpreader spreadManager, LevelAccessor world, BlockPos pos, RandomSource random, CallbackInfoReturnable<Boolean> info) {
        BlockState blockState = world.getBlockState(pos);
        TagKey<Block> tagKey = spreadManager.replaceableBlocks();

        for (Direction direction : Direction.allShuffled(random)) {
            if (hasFace(blockState, direction)) {
                BlockPos blockPos = pos.relative(direction);
                BlockState blockState2 = world.getBlockState(blockPos);
                if (blockState2.is(tagKey)) {
                    BlockState blockState3 = Blocks.SCULK.defaultBlockState();
                    if (blockState2.is(WilderBlockTags.SCULK_STAIR_REPLACEABLE_WORLDGEN)) {
                        blockState3 = RegisterBlocks.SCULK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, blockState2.getValue(StairBlock.FACING)).setValue(StairBlock.HALF, blockState2.getValue(StairBlock.HALF)).setValue(StairBlock.SHAPE, blockState2.getValue(StairBlock.SHAPE)).setValue(StairBlock.WATERLOGGED, blockState2.getValue(StairBlock.WATERLOGGED));
                    } else if (blockState2.is(WilderBlockTags.SCULK_WALL_REPLACEABLE_WORLDGEN)) {
                        blockState3 = RegisterBlocks.SCULK_WALL.defaultBlockState().setValue(WallBlock.UP, blockState2.getValue(WallBlock.UP)).setValue(WallBlock.NORTH_WALL, blockState2.getValue(WallBlock.NORTH_WALL))
                                .setValue(WallBlock.EAST_WALL, blockState2.getValue(WallBlock.EAST_WALL)).setValue(WallBlock.WEST_WALL, blockState2.getValue(WallBlock.WEST_WALL))
                                .setValue(WallBlock.SOUTH_WALL, blockState2.getValue(WallBlock.SOUTH_WALL)).setValue(WallBlock.WATERLOGGED, blockState2.getValue(WallBlock.WATERLOGGED));
                    } else if (blockState2.is(WilderBlockTags.SCULK_SLAB_REPLACEABLE_WORLDGEN)) {
                        blockState3 = RegisterBlocks.SCULK_SLAB.defaultBlockState().setValue(SlabBlock.WATERLOGGED, blockState2.getValue(SlabBlock.WATERLOGGED)).setValue(SlabBlock.TYPE, blockState2.getValue(SlabBlock.TYPE));
                    }

                    world.setBlock(blockPos, blockState3, 3);
                    Block.pushEntitiesUp(blockState2, blockState3, world, blockPos);
                    world.playSound(null, blockPos, SoundEvents.SCULK_BLOCK_SPREAD, SoundSource.BLOCKS, 1.0F, 1.0F);
                    this.veinSpreader.spreadAll(blockState3, world, blockPos, spreadManager.isWorldGeneration());
                    Direction direction2 = direction.getOpposite();

                    for (Direction direction3 : Direction.values()) {
                        if (direction3 != direction2) {
                            BlockPos blockPos2 = blockPos.relative(direction3);
                            BlockState blockState4 = world.getBlockState(blockPos2);
                            if (blockState4.is(Blocks.SCULK_VEIN)) {
                                this.onDischarged(world, blockState4, blockPos2, random);
                            }
                        }
                    }

                    info.setReturnValue(true);
                    info.cancel();
                }
            }
        }

        info.setReturnValue(false);
        info.cancel();
    }

    @Shadow
    public void onDischarged(LevelAccessor world, BlockState state, BlockPos pos, RandomSource random) {
    }

    @Inject(at = @At("HEAD"), method = "onDischarged", cancellable = true)
    public void onDischarged(LevelAccessor world, BlockState state, BlockPos pos, RandomSource random, CallbackInfo info) {
        if (state.is(Blocks.SCULK_VEIN)) {

            for (Direction direction : Direction.values()) {
                BooleanProperty booleanProperty = getFaceProperty(direction);
                if (state.getValue(booleanProperty) && world.getBlockState(pos.relative(direction)).is(WilderBlockTags.SCULK_VEIN_REMOVE)) {
                    state = state.setValue(booleanProperty, false);
                }
            }

            if (!hasAnyDirection(state)) {
                FluidState fluidState = world.getFluidState(pos);
                state = (fluidState.isEmpty() ? Blocks.AIR : Blocks.WATER).defaultBlockState();
            }

            world.setBlock(pos, state, 3);
        }
    }

    private static boolean hasAnyDirection(BlockState state) {
        return Arrays.stream(Direction.values()).anyMatch((direction) -> {
            return hasFace(state, direction);
        });
    }

}
