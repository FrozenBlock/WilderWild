package net.frozenblock.wilderwild.world.gen.sapling;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.BlockView;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

public abstract class BaobabTreeSaplingGenerator extends SaplingGenerator {
    public BaobabTreeSaplingGenerator() {
    }

    public boolean generate(ServerWorld world, ChunkGenerator chunkGenerator, BlockPos pos, BlockState state, Random random) {
        for(int i = 0; i >= -1; --i) {
            for(int j = 0; j >= -1; --j) {
                if (canGenerateBaobabTree(state, world, pos, i, j)) {
                    return this.generateBaobabTree(world, chunkGenerator, pos, state, random, i, j);
                }
            }
        }

        return super.generate(world, chunkGenerator, pos, state, random);
    }

    @Nullable
    protected abstract RegistryEntry<? extends ConfiguredFeature<?, ?>> getBaobabTreeFeature(Random random);

    public boolean generateBaobabTree(ServerWorld world, ChunkGenerator chunkGenerator, BlockPos pos, BlockState state, Random random, int x, int z) {
        RegistryEntry<? extends ConfiguredFeature<?, ?>> registryEntry = this.getBaobabTreeFeature(random);
        if (registryEntry == null) {
            return false;
        } else {
            ConfiguredFeature<?, ?> configuredFeature = (ConfiguredFeature)registryEntry.value();
            BlockState blockState = Blocks.AIR.getDefaultState();
            world.setBlockState(pos.add(x, 0, z), blockState, 16);
            world.setBlockState(pos.add(x + 1, 0, z), blockState, 16);
            world.setBlockState(pos.add(x + 2, 0, z), blockState, 16);
            world.setBlockState(pos.add(x + 3, 0, z), blockState, 16);
            world.setBlockState(pos.add(x, 0, z + 1), blockState, 16);
            world.setBlockState(pos.add(x, 0, z + 2), blockState, 16);
            world.setBlockState(pos.add(x, 0, z + 3), blockState, 16);
            world.setBlockState(pos.add(x + 1, 0, z + 1), blockState, 16);
            world.setBlockState(pos.add(x + 2, 0, z + 1), blockState, 16);
            world.setBlockState(pos.add(x + 3, 0, z + 1), blockState, 16);
            world.setBlockState(pos.add(x + 1, 0, z + 2), blockState, 16);
            world.setBlockState(pos.add(x + 1, 0, z + 3), blockState, 16);
            world.setBlockState(pos.add(x + 2, 0, z + 2), blockState, 16);
            world.setBlockState(pos.add(x + 3, 0, z + 3), blockState, 16);
            world.setBlockState(pos.add(x + 3, 0, z + 2), blockState, 16);
            world.setBlockState(pos.add(x + 2, 0, z + 3), blockState, 16);
            if (configuredFeature.generate(world, chunkGenerator, random, pos.add(x, 0, z))) {
                return true;
            } else {
                world.setBlockState(pos.add(x, 0, z), state, 16);
                world.setBlockState(pos.add(x + 1, 0, z), state, 16);
                world.setBlockState(pos.add(x + 2, 0, z), blockState, 16);
                world.setBlockState(pos.add(x + 3, 0, z), blockState, 16);
                world.setBlockState(pos.add(x, 0, z + 1), state, 16);
                world.setBlockState(pos.add(x, 0, z + 2), blockState, 16);
                world.setBlockState(pos.add(x, 0, z + 3), blockState, 16);
                world.setBlockState(pos.add(x + 1, 0, z + 1), state, 16);
                world.setBlockState(pos.add(x + 2, 0, z + 1), blockState, 16);
                world.setBlockState(pos.add(x + 3, 0, z + 1), blockState, 16);
                world.setBlockState(pos.add(x + 1, 0, z + 2), blockState, 16);
                world.setBlockState(pos.add(x + 1, 0, z + 3), blockState, 16);
                world.setBlockState(pos.add(x + 2, 0, z + 2), blockState, 16);
                world.setBlockState(pos.add(x + 3, 0, z + 3), blockState, 16);
                world.setBlockState(pos.add(x + 3, 0, z + 2), blockState, 16);
                world.setBlockState(pos.add(x + 2, 0, z + 3), blockState, 16);
                return false;
            }
        }
    }

    public static boolean canGenerateBaobabTree(BlockState state, BlockView world, BlockPos pos, int x, int z) {
        Block block = state.getBlock();
        return world.getBlockState(pos.add(x, 0, z)).isOf(block)
                && world.getBlockState(pos.add(x + 1, 0, z)).isOf(block)
                && world.getBlockState(pos.add(x + 2, 0, z)).isOf(block)
                && world.getBlockState(pos.add(x + 3, 0, z)).isOf(block)
                && world.getBlockState(pos.add(x, 0, z + 1)).isOf(block)
                && world.getBlockState(pos.add(x, 0, z + 2)).isOf(block)
                && world.getBlockState(pos.add(x, 0, z + 3)).isOf(block)
                && world.getBlockState(pos.add(x + 1, 0, z + 1)).isOf(block)
                && world.getBlockState(pos.add(x + 2, 0, z + 1)).isOf(block)
                && world.getBlockState(pos.add(x + 3, 0, z + 1)).isOf(block)
                && world.getBlockState(pos.add(x + 1, 0, z + 2)).isOf(block)
                && world.getBlockState(pos.add(x + 1, 0, z + 3)).isOf(block)
                && world.getBlockState(pos.add(x + 2, 0, z + 2)).isOf(block)
                && world.getBlockState(pos.add(x + 3, 0, z + 3)).isOf(block)
                && world.getBlockState(pos.add(x + 3, 0, z + 2)).isOf(block)
                && world.getBlockState(pos.add(x + 2, 0, z + 3)).isOf(block);
    }
}
