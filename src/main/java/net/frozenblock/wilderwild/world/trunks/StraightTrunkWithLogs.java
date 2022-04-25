package net.frozenblock.wilderwild.world.trunks;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.AbstractRandom;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryCodecs;
import net.minecraft.util.registry.RegistryEntryList;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;

public class StraightTrunkWithLogs extends TrunkPlacer {
    public static final Codec<StraightTrunkWithLogs> CODEC = RecordCodecBuilder.create((instance) -> {
        return fillTrunkPlacerFields(instance).and(instance.group(Codec.floatRange(0.0F, 1.0F).fieldOf("place_branch_change").forGetter((trunkPlacer) -> {
            return trunkPlacer.logChance;
        }), IntProvider.NON_NEGATIVE_CODEC.fieldOf("extra_branch_length").forGetter((trunkPlacer) -> {
            return trunkPlacer.extraBranchLength;
        }), RegistryCodecs.entryList(Registry.BLOCK_KEY).fieldOf("can_grow_through").forGetter((trunkPlacer) -> {
            return trunkPlacer.canGrowThrough;
        }))).apply(instance, StraightTrunkWithLogs::new);
    });
    private final RegistryEntryList<Block> canGrowThrough;
    private final IntProvider extraBranchLength;
    private final float logChance;

    public StraightTrunkWithLogs(int baseHeight, int firstRandomHeight, int secondRandomHeight, float logChance, IntProvider extraBranchLength, RegistryEntryList<Block> canGrowThrough) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
        this.logChance = logChance;
        this.canGrowThrough = canGrowThrough;
        this.extraBranchLength = extraBranchLength;
    }

    protected TrunkPlacerType<?> getType() {
        return TrunkPlacerType.STRAIGHT_TRUNK_PLACER;
    }

    public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, AbstractRandom random, int height, BlockPos startPos, TreeFeatureConfig config) {
        setToDirt(world, replacer, random, startPos.down(), config);
        List<FoliagePlacer.TreeNode> list = Lists.newArrayList();
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        for(int i = 0; i < height; ++i) {
            int j = startPos.getY() + i;
            if (this.getAndSetState(world, replacer, random, mutable.set(startPos.getX(), j, startPos.getZ()), config) && i < height - 1 && random.nextFloat() < this.logChance) {
                Direction direction = Direction.Type.HORIZONTAL.random(random);
                this.generateExtraBranch(world, replacer, random, config, mutable, j, direction, this.extraBranchLength.get(random));
            }
            if (i == height - 1) {
                list.add(new FoliagePlacer.TreeNode(mutable.set(startPos.getX(), j + 1, startPos.getZ()), 0, false));
            }
        }

        return list;
    }

    private void generateExtraBranch(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, AbstractRandom random, TreeFeatureConfig config, BlockPos.Mutable pos, int yOffset, Direction direction, int length) {
        int j = pos.getX();
        int k = pos.getZ();

        for(int l = 0; l < length; ++l) {
            int m = yOffset;
            j += direction.getOffsetX();
            k += direction.getOffsetZ();
            this.getAndSetState(world, replacer, random, pos.set(j, m, k), config);
        }
    }

    protected boolean canReplace(TestableWorld world, BlockPos pos) {
        return super.canReplace(world, pos) || world.testBlockState(pos, (state) -> state.isIn(this.canGrowThrough));
    }

}
