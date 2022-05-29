package net.frozenblock.wilderwild.world.gen.trunk;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.BiConsumer;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class BaobabTrunkPlacer extends TrunkPlacer {
    public static final Codec<net.frozenblock.wilderwild.world.gen.trunk.BaobabTrunkPlacer> CODEC = RecordCodecBuilder.create((instance) -> {
        return fillTrunkPlacerFields(instance).apply(instance, net.frozenblock.wilderwild.world.gen.trunk.BaobabTrunkPlacer::new);
    });

    public BaobabTrunkPlacer(int i, int j, int k) {
        super(i, j, k);
    }

    protected TrunkPlacerType<?> getType() {
        return WilderWild.BAOBAB_TRUNK_PLACER;
    }

    public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
        BlockPos blockPos = startPos.down();
        setToDirt(world, replacer, random, blockPos, config);
        setToDirt(world, replacer, random, blockPos.east(), config);
        setToDirt(world, replacer, random, blockPos.east().east(), config);
        setToDirt(world, replacer, random, blockPos.east().east().east(), config);
        setToDirt(world, replacer, random, blockPos.south(), config);
        setToDirt(world, replacer, random, blockPos.south().south(), config);
        setToDirt(world, replacer, random, blockPos.south().south().south(), config);
        setToDirt(world, replacer, random, blockPos.south().east(), config);
        setToDirt(world, replacer, random, blockPos.south().east().east(), config);
        setToDirt(world, replacer, random, blockPos.south().east().east().east(), config);
        setToDirt(world, replacer, random, blockPos.south().south().east(), config);
        setToDirt(world, replacer, random, blockPos.south().south().east().east(), config);
        setToDirt(world, replacer, random, blockPos.south().south().east().east().east(), config);
        setToDirt(world, replacer, random, blockPos.south().south().south().east(), config);
        setToDirt(world, replacer, random, blockPos.south().south().south().east().east(), config);
        setToDirt(world, replacer, random, blockPos.south().south().south().east().east().east(), config);
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        for(int i = 0; i < height; ++i) {
            this.setLog(world, replacer, random, mutable, config, startPos, 0, i, 0);
            if (i < height - 1) {
                this.setLog(world, replacer, random, mutable, config, startPos, 1, i, 0);
                this.setLog(world, replacer, random, mutable, config, startPos, 1, i, 1);
                this.setLog(world, replacer, random, mutable, config, startPos, 0, i, 1);
                this.setLog(world, replacer, random, mutable, config, startPos, 2, i, 0);
                this.setLog(world, replacer, random, mutable, config, startPos, 3, i, 0);
                this.setLog(world, replacer, random, mutable, config, startPos, 1, i, 2);
                this.setLog(world, replacer, random, mutable, config, startPos, 1, i, 3);
                this.setLog(world, replacer, random, mutable, config, startPos, 2, i, 1);
                this.setLog(world, replacer, random, mutable, config, startPos, 3, i, 1);
                this.setLog(world, replacer, random, mutable, config, startPos, 1, i, 2);
                this.setLog(world, replacer, random, mutable, config, startPos, 1, i, 3);
                this.setLog(world, replacer, random, mutable, config, startPos, 2, i, 2);
                this.setLog(world, replacer, random, mutable, config, startPos, 2, i, 3);
                this.setLog(world, replacer, random, mutable, config, startPos, 3, i, 3);
                this.setLog(world, replacer, random, mutable, config, startPos, 3, i, 2);
                this.setLog(world, replacer, random, mutable, config, startPos, 0, i, 2);
                this.setLog(world, replacer, random, mutable, config, startPos, 0, i, 3);
            }
        }

        return ImmutableList.of(new FoliagePlacer.TreeNode(startPos.up(height), 3, true));
    }

    private void setLog(TestableWorld testableWorld, BiConsumer<BlockPos, BlockState> biConsumer, Random random, BlockPos.Mutable mutable, TreeFeatureConfig treeFeatureConfig, BlockPos blockPos, int i, int j, int k) {
        mutable.set(blockPos, i, j, k);
        this.trySetState(testableWorld, biConsumer, random, mutable, treeFeatureConfig);
    }
}