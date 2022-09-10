package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.tag.WilderBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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

@Mixin(SculkVeinBlock.class)
public abstract class SculkVeinBlockMixin extends MultifaceBlock {

    @Final
    @Shadow
    private MultifaceSpreader veinSpreader;

    public SculkVeinBlockMixin(Properties properties) {
        super(properties);
    }

    @Inject(at = @At(value = "RETURN", opcode = 0), method = "attemptPlaceSculk")
    private void attemptPlaceSculk(SculkSpreader spreadManager, LevelAccessor world, BlockPos pos, RandomSource randomSource, CallbackInfoReturnable<Boolean> info) {
        BlockState blockState = world.getBlockState(pos);
        TagKey<Block> tagKey = spreadManager.replaceableBlocks();
        boolean canReturn = false;

        for (Direction direction : Direction.allShuffled(randomSource)) {
            if (hasFace(blockState, direction)) {
                BlockPos blockPos = pos.relative(direction);
                BlockState blockState2 = world.getBlockState(blockPos);
                if (blockState2.is(tagKey)) {
                    BlockState blockState3 = Blocks.SCULK.defaultBlockState();
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
                        world.setBlock(blockPos, blockState3, 3);
                        Block.pushEntitiesUp(blockState2, blockState3, world, blockPos);
                        this.veinSpreader.spreadAll(blockState3, world, blockPos, spreadManager.isWorldGeneration());

                    }
                }
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "onDischarged")
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
