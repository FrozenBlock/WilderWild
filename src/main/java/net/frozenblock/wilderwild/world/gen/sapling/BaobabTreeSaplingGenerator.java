package net.frozenblock.wilderwild.world.gen.sapling;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

public abstract class BaobabTreeSaplingGenerator extends AbstractTreeGrower {
    public BaobabTreeSaplingGenerator() {
    }

    public boolean growTree(ServerLevel world, ChunkGenerator chunkGenerator, BlockPos pos, BlockState state, RandomSource random) {
        for (int i = 0; i >= -1; --i) {
            for (int j = 0; j >= -1; --j) {
                if (canGenerateBaobabTree(state, world, pos, i, j)) {
                    return this.generateBaobabTree(world, chunkGenerator, pos, state, random, i, j);
                }
            }
        }

        return super.growTree(world, chunkGenerator, pos, state, random);
    }

    @Nullable
    protected abstract Holder<? extends ConfiguredFeature<?, ?>> getBaobabTreeFeature(RandomSource random);

    public boolean generateBaobabTree(ServerLevel world, ChunkGenerator chunkGenerator, BlockPos pos, BlockState state, RandomSource random, int x, int z) {
        Holder<? extends ConfiguredFeature<?, ?>> registryEntry = this.getBaobabTreeFeature(random);
        if (registryEntry == null) {
            return false;
        } else {
            ConfiguredFeature<?, ?> configuredFeature = registryEntry.value();
            BlockState blockState = Blocks.AIR.defaultBlockState();
            world.setBlock(pos.offset(x, 0, z), blockState, 16);
            world.setBlock(pos.offset(x + 1, 0, z), blockState, 16);
            world.setBlock(pos.offset(x + 2, 0, z), blockState, 16);
            world.setBlock(pos.offset(x + 3, 0, z), blockState, 16);
            world.setBlock(pos.offset(x, 0, z + 1), blockState, 16);
            world.setBlock(pos.offset(x, 0, z + 2), blockState, 16);
            world.setBlock(pos.offset(x, 0, z + 3), blockState, 16);
            world.setBlock(pos.offset(x + 1, 0, z + 1), blockState, 16);
            world.setBlock(pos.offset(x + 2, 0, z + 1), blockState, 16);
            world.setBlock(pos.offset(x + 3, 0, z + 1), blockState, 16);
            world.setBlock(pos.offset(x + 1, 0, z + 2), blockState, 16);
            world.setBlock(pos.offset(x + 1, 0, z + 3), blockState, 16);
            world.setBlock(pos.offset(x + 2, 0, z + 2), blockState, 16);
            world.setBlock(pos.offset(x + 3, 0, z + 3), blockState, 16);
            world.setBlock(pos.offset(x + 3, 0, z + 2), blockState, 16);
            world.setBlock(pos.offset(x + 2, 0, z + 3), blockState, 16);
            if (configuredFeature.place(world, chunkGenerator, random, pos.offset(x, 0, z))) {
                return true;
            } else {
                world.setBlock(pos.offset(x, 0, z), state, 16);
                world.setBlock(pos.offset(x + 1, 0, z), state, 16);
                world.setBlock(pos.offset(x + 2, 0, z), blockState, 16);
                world.setBlock(pos.offset(x + 3, 0, z), blockState, 16);
                world.setBlock(pos.offset(x, 0, z + 1), state, 16);
                world.setBlock(pos.offset(x, 0, z + 2), blockState, 16);
                world.setBlock(pos.offset(x, 0, z + 3), blockState, 16);
                world.setBlock(pos.offset(x + 1, 0, z + 1), state, 16);
                world.setBlock(pos.offset(x + 2, 0, z + 1), blockState, 16);
                world.setBlock(pos.offset(x + 3, 0, z + 1), blockState, 16);
                world.setBlock(pos.offset(x + 1, 0, z + 2), blockState, 16);
                world.setBlock(pos.offset(x + 1, 0, z + 3), blockState, 16);
                world.setBlock(pos.offset(x + 2, 0, z + 2), blockState, 16);
                world.setBlock(pos.offset(x + 3, 0, z + 3), blockState, 16);
                world.setBlock(pos.offset(x + 3, 0, z + 2), blockState, 16);
                world.setBlock(pos.offset(x + 2, 0, z + 3), blockState, 16);
                return false;
            }
        }
    }

    public static boolean canGenerateBaobabTree(BlockState state, BlockGetter world, BlockPos pos, int x, int z) {
        Block block = state.getBlock();
        return world.getBlockState(pos.offset(x, 0, z)).is(block)
                && world.getBlockState(pos.offset(x + 1, 0, z)).is(block)
                && world.getBlockState(pos.offset(x + 2, 0, z)).is(block)
                && world.getBlockState(pos.offset(x + 3, 0, z)).is(block)
                && world.getBlockState(pos.offset(x, 0, z + 1)).is(block)
                && world.getBlockState(pos.offset(x, 0, z + 2)).is(block)
                && world.getBlockState(pos.offset(x, 0, z + 3)).is(block)
                && world.getBlockState(pos.offset(x + 1, 0, z + 1)).is(block)
                && world.getBlockState(pos.offset(x + 2, 0, z + 1)).is(block)
                && world.getBlockState(pos.offset(x + 3, 0, z + 1)).is(block)
                && world.getBlockState(pos.offset(x + 1, 0, z + 2)).is(block)
                && world.getBlockState(pos.offset(x + 1, 0, z + 3)).is(block)
                && world.getBlockState(pos.offset(x + 2, 0, z + 2)).is(block)
                && world.getBlockState(pos.offset(x + 3, 0, z + 3)).is(block)
                && world.getBlockState(pos.offset(x + 3, 0, z + 2)).is(block)
                && world.getBlockState(pos.offset(x + 2, 0, z + 3)).is(block);
    }
}
