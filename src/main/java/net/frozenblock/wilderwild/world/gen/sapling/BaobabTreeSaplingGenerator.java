package net.frozenblock.wilderwild.world.gen.sapling;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.grower.AbstractMegaTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

public abstract class BaobabTreeSaplingGenerator extends AbstractMegaTreeGrower {
    public BaobabTreeSaplingGenerator() {
    }

    public boolean growTree(ServerLevel level, ChunkGenerator chunkGenerator, BlockPos pos, BlockState state, RandomSource random) {
        for (int i = 0; i >= -4; --i) {
            for (int j = 0; j >= -4; --j) {
                if (canGenerateBaobabTree(state, level, pos, i, j)) {
                    return this.generateBaobabTree(level, chunkGenerator, pos, state, random, i, j);
                }
            }
        }

        return super.growTree(level, chunkGenerator, pos, state, random);
    }

    @Nullable
    protected abstract Holder<? extends ConfiguredFeature<?, ?>> getBaobabTreeFeature(RandomSource random);

    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredMegaFeature(RandomSource random) {
        return null;
    }

    public boolean generateBaobabTree(ServerLevel level, ChunkGenerator chunkGenerator, BlockPos pos, BlockState state, RandomSource random, int x, int z) {
        Holder<? extends ConfiguredFeature<?, ?>> registryEntry = this.getBaobabTreeFeature(random);
        if (registryEntry == null) {
            return false;
        } else {
            ConfiguredFeature<?, ?> configuredFeature = registryEntry.value();
            BlockState blockState = Blocks.AIR.defaultBlockState();
            level.setBlock(pos.offset(x, 0, z), blockState, 16);
            level.setBlock(pos.offset(x + 1, 0, z), blockState, 16);
            level.setBlock(pos.offset(x + 2, 0, z), blockState, 16);
            level.setBlock(pos.offset(x + 3, 0, z), blockState, 16);
            level.setBlock(pos.offset(x, 0, z + 1), blockState, 16);
            level.setBlock(pos.offset(x, 0, z + 2), blockState, 16);
            level.setBlock(pos.offset(x, 0, z + 3), blockState, 16);
            level.setBlock(pos.offset(x + 1, 0, z + 1), blockState, 16);
            level.setBlock(pos.offset(x + 2, 0, z + 1), blockState, 16);
            level.setBlock(pos.offset(x + 3, 0, z + 1), blockState, 16);
            level.setBlock(pos.offset(x + 1, 0, z + 2), blockState, 16);
            level.setBlock(pos.offset(x + 1, 0, z + 3), blockState, 16);
            level.setBlock(pos.offset(x + 2, 0, z + 2), blockState, 16);
            level.setBlock(pos.offset(x + 3, 0, z + 3), blockState, 16);
            level.setBlock(pos.offset(x + 3, 0, z + 2), blockState, 16);
            level.setBlock(pos.offset(x + 2, 0, z + 3), blockState, 16);
            level.getChunkSource().blockChanged(pos.offset(x, 0, z));
            level.getChunkSource().blockChanged(pos.offset(x + 1, 0, z));
            level.getChunkSource().blockChanged(pos.offset(x + 2, 0, z));
            level.getChunkSource().blockChanged(pos.offset(x + 3, 0, z));
            level.getChunkSource().blockChanged(pos.offset(x, 0, z + 1));
            level.getChunkSource().blockChanged(pos.offset(x, 0, z + 2));
            level.getChunkSource().blockChanged(pos.offset(x, 0, z + 3));
            level.getChunkSource().blockChanged(pos.offset(x + 1, 0, z + 1));
            level.getChunkSource().blockChanged(pos.offset(x + 2, 0, z + 1));
            level.getChunkSource().blockChanged(pos.offset(x + 3, 0, z + 1));
            level.getChunkSource().blockChanged(pos.offset(x + 1, 0, z + 2));
            level.getChunkSource().blockChanged(pos.offset(x + 1, 0, z + 3));
            level.getChunkSource().blockChanged(pos.offset(x + 2, 0, z + 2));
            level.getChunkSource().blockChanged(pos.offset(x + 3, 0, z + 3));
            level.getChunkSource().blockChanged(pos.offset(x + 3, 0, z + 2));
            level.getChunkSource().blockChanged(pos.offset(x + 2, 0, z + 3));
            if (configuredFeature.place(level, chunkGenerator, random, pos.offset(x, 0, z))) {
                return true;
            } else {
                level.setBlock(pos.offset(x, 0, z), state, 16);
                level.setBlock(pos.offset(x + 1, 0, z), state, 16);
                level.setBlock(pos.offset(x + 2, 0, z), blockState, 16);
                level.setBlock(pos.offset(x + 3, 0, z), blockState, 16);
                level.setBlock(pos.offset(x, 0, z + 1), state, 16);
                level.setBlock(pos.offset(x, 0, z + 2), blockState, 16);
                level.setBlock(pos.offset(x, 0, z + 3), blockState, 16);
                level.setBlock(pos.offset(x + 1, 0, z + 1), state, 16);
                level.setBlock(pos.offset(x + 2, 0, z + 1), blockState, 16);
                level.setBlock(pos.offset(x + 3, 0, z + 1), blockState, 16);
                level.setBlock(pos.offset(x + 1, 0, z + 2), blockState, 16);
                level.setBlock(pos.offset(x + 1, 0, z + 3), blockState, 16);
                level.setBlock(pos.offset(x + 2, 0, z + 2), blockState, 16);
                level.setBlock(pos.offset(x + 3, 0, z + 3), blockState, 16);
                level.setBlock(pos.offset(x + 3, 0, z + 2), blockState, 16);
                level.setBlock(pos.offset(x + 2, 0, z + 3), blockState, 16);
                level.getChunkSource().blockChanged(pos.offset(x, 0, z));
                level.getChunkSource().blockChanged(pos.offset(x + 1, 0, z));
                level.getChunkSource().blockChanged(pos.offset(x + 2, 0, z));
                level.getChunkSource().blockChanged(pos.offset(x + 3, 0, z));
                level.getChunkSource().blockChanged(pos.offset(x, 0, z + 1));
                level.getChunkSource().blockChanged(pos.offset(x, 0, z + 2));
                level.getChunkSource().blockChanged(pos.offset(x, 0, z + 3));
                level.getChunkSource().blockChanged(pos.offset(x + 1, 0, z + 1));
                level.getChunkSource().blockChanged(pos.offset(x + 2, 0, z + 1));
                level.getChunkSource().blockChanged(pos.offset(x + 3, 0, z + 1));
                level.getChunkSource().blockChanged(pos.offset(x + 1, 0, z + 2));
                level.getChunkSource().blockChanged(pos.offset(x + 1, 0, z + 3));
                level.getChunkSource().blockChanged(pos.offset(x + 2, 0, z + 2));
                level.getChunkSource().blockChanged(pos.offset(x + 3, 0, z + 3));
                level.getChunkSource().blockChanged(pos.offset(x + 3, 0, z + 2));
                level.getChunkSource().blockChanged(pos.offset(x + 2, 0, z + 3));
                return false;
            }
        }
    }

    public static boolean canGenerateBaobabTree(BlockState state, BlockGetter level, BlockPos pos, int x, int z) {
        Block block = state.getBlock();
        return level.getBlockState(pos.offset(x, 0, z)).is(block)
                && level.getBlockState(pos.offset(x + 1, 0, z)).is(block)
                && level.getBlockState(pos.offset(x + 2, 0, z)).is(block)
                && level.getBlockState(pos.offset(x + 3, 0, z)).is(block)
                && level.getBlockState(pos.offset(x, 0, z + 1)).is(block)
                && level.getBlockState(pos.offset(x, 0, z + 2)).is(block)
                && level.getBlockState(pos.offset(x, 0, z + 3)).is(block)
                && level.getBlockState(pos.offset(x + 1, 0, z + 1)).is(block)
                && level.getBlockState(pos.offset(x + 2, 0, z + 1)).is(block)
                && level.getBlockState(pos.offset(x + 3, 0, z + 1)).is(block)
                && level.getBlockState(pos.offset(x + 1, 0, z + 2)).is(block)
                && level.getBlockState(pos.offset(x + 1, 0, z + 3)).is(block)
                && level.getBlockState(pos.offset(x + 2, 0, z + 2)).is(block)
                && level.getBlockState(pos.offset(x + 3, 0, z + 3)).is(block)
                && level.getBlockState(pos.offset(x + 3, 0, z + 2)).is(block)
                && level.getBlockState(pos.offset(x + 2, 0, z + 3)).is(block);
    }
}
