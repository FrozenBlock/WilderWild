package net.frozenblock.wilderwild.world.gen.trunk;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.BiConsumer;

import net.frozenblock.api.mathematics.AdvancedMath;
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
    public static final Codec<net.frozenblock.wilderwild.world.gen.trunk.BaobabTrunkPlacer> CODEC = RecordCodecBuilder.create((instance) ->
            fillTrunkPlacerFields(instance).apply(instance, BaobabTrunkPlacer::new));

    public BaobabTrunkPlacer(int i, int j, int k) {
        super(i, j, k);
    }

    protected TrunkPlacerType<?> getType() {
        return WilderWild.BAOBAB_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
        BlockPos blockPos = startPos.down();

        // generate dirt below tree's base
        setToDirt(world, replacer, random, blockPos, config); // CENTER
        setToDirt(world, replacer, random, blockPos.east(), config); // EAST
        setToDirt(world, replacer, random, blockPos.west(), config); // WEST
        setToDirt(world, replacer, random, blockPos.north(), config); // NORTH
        setToDirt(world, replacer, random, blockPos.north().east(), config); // NORTH-EAST
        setToDirt(world, replacer, random, blockPos.north().west(), config); // NORTH-WEST
        setToDirt(world, replacer, random, blockPos.south(), config); // SOUTH
        setToDirt(world, replacer, random, blockPos.south().east(), config); // SOUTH-EAST
        setToDirt(world, replacer, random, blockPos.south().west(), config); // SOUTH-WEST
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        int min = 6;
        int max = 8;


        //Random Height between min & max
        int h = (int) AdvancedMath.range(min, max, (float)Math.random());
        int h_nw = (int)AdvancedMath.range((float)min/3, (float)max/3, (float)Math.random());
        int h_sw = (int)AdvancedMath.range((float)min/3, (float)max/3, (float)Math.random());
        int h_ne = (int)AdvancedMath.range((float)min/3, (float)max/3, (float)Math.random());
        int h_se = (int) AdvancedMath.range((float)min/3, (float)max/3, (float)Math.random());

        for(int i = 0; i < h; i++) {
            setLog(world, replacer, random, mutable, config, startPos, 0, i, 0); // CENTER

            setLog(world, replacer, random, mutable, config, startPos, 1, i, 0);
            setLog(world, replacer, random, mutable, config, startPos, -1, i, 0);
            setLog(world, replacer, random, mutable, config, startPos, 0, i, 1);
            setLog(world, replacer, random, mutable, config, startPos, 0, i, -1);

            setLog(world, replacer, random, mutable, config, startPos, 1, i, 1, i <= h_nw);
            setLog(world, replacer, random, mutable, config, startPos, -1, i, 1, i <= h_sw);
            setLog(world, replacer, random, mutable, config, startPos, 1, i, -1, i <= h_ne);
            setLog(world, replacer, random, mutable, config, startPos, -1, i, -1, i <= h_se);
        }
        return ImmutableList.of(new FoliagePlacer.TreeNode(startPos.up(h), 0, false));
    }

    private void setLog(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos.Mutable pos, TreeFeatureConfig config, BlockPos startPos, int x, int y, int z, boolean condition) {
        if(condition) {
            pos.set(startPos, x, y, z);
            trySetState(world, replacer, random, pos, config);
        }
    }

    private void setLog(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos.Mutable pos, TreeFeatureConfig config, BlockPos startPos, int x, int y, int z) {
        setLog(world, replacer, random, pos, config, startPos, x, y, z, true);
    }
}