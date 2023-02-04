package net.frozenblock.wilderwild.world.generation.sapling;

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
import java.util.concurrent.atomic.AtomicBoolean;

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

    public boolean generateBaobabTree(ServerLevel level, ChunkGenerator chunkGenerator, BlockPos pos, BlockState state, RandomSource random, int xPos, int zPos) {
        Holder<? extends ConfiguredFeature<?, ?>> registryEntry = this.getBaobabTreeFeature(random);
        if (registryEntry == null) {
            return false;
        } else {
            ConfiguredFeature<?, ?> configuredFeature = registryEntry.value();
            BlockState blockState = Blocks.AIR.defaultBlockState();
			for (var x = xPos; x <= xPos + 3; ++x) {
				for (var z = zPos; z <= zPos + 3; ++z) {
					level.setBlock(pos.offset(x, 0, z), blockState, 3);
				}
			}
            if (configuredFeature.place(level, chunkGenerator, random, pos.offset(xPos, 0, zPos))) {
                return true;
            } else {
				for (var x = xPos; x <= xPos + 3; ++x) {
					for (var z = zPos; z <= zPos + 3; ++z) {
						level.setBlock(pos.offset(x, 0, z), state, 3);
					}
				}
                return false;
            }
        }
    }

    public static boolean canGenerateBaobabTree(BlockState state, BlockGetter level, BlockPos pos, int xPos, int zPos) {
        Block block = state.getBlock();
		boolean canGenerate = false;
		for (var x = xPos; x <= xPos + 3; ++x) {
			for (var z = zPos; z <= zPos + 3; ++z) {
				if (level.getBlockState(pos.offset(x, 0, z)).is(block))
					canGenerate = true;
			}
		}
        return canGenerate;
    }
}
