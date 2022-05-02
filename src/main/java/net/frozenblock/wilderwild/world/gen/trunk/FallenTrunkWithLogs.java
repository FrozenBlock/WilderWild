package net.frozenblock.wilderwild.world.gen.trunk;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.AbstractRandom;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;

public class FallenTrunkWithLogs extends TrunkPlacer {
    public static final Codec<FallenTrunkWithLogs> CODEC = RecordCodecBuilder.create((instance) -> {
        return fillTrunkPlacerFields(instance).and(instance.group(Codec.floatRange(0.0F, 1.0F).fieldOf("place_branch_chance").forGetter((trunkPlacer) -> {
            return trunkPlacer.logChance;
        }), IntProvider.NON_NEGATIVE_CODEC.fieldOf("max_logs").forGetter((trunkPlacer) -> {
            return trunkPlacer.maxLogs;
        }), IntProvider.NON_NEGATIVE_CODEC.fieldOf("log_height_from_top").forGetter((trunkPlacer) -> {
            return trunkPlacer.logHeightFromTop;
        }), IntProvider.NON_NEGATIVE_CODEC.fieldOf("extra_branch_length").forGetter((trunkPlacer) -> {
            return trunkPlacer.extraBranchLength;
        }), IntProvider.NON_NEGATIVE_CODEC.fieldOf("max_height_above_hole").forGetter((trunkPlacer) -> {
            return trunkPlacer.maxHeightAboveHole;
        }))).apply(instance, FallenTrunkWithLogs::new);
    });

    private final IntProvider extraBranchLength;
    private final float logChance;
    private final IntProvider maxLogs;
    private final IntProvider logHeightFromTop;
    private final IntProvider maxHeightAboveHole;

    public FallenTrunkWithLogs(int baseHeight, int firstRandomHeight, int secondRandomHeight, float logChance, IntProvider maxLogs, IntProvider logHeightFromTop, IntProvider extraBranchLength, IntProvider maxHeightAboveHole) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
        this.logChance = logChance;
        this.maxLogs = maxLogs;
        this.logHeightFromTop = logHeightFromTop;
        this.extraBranchLength = extraBranchLength;
        this.maxHeightAboveHole = maxHeightAboveHole;
    }

    protected TrunkPlacerType<?> getType() {
        return WilderWild.FALLEN_TRUNK_WITH_LOGS_PLACER_TYPE;
    }

    public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, AbstractRandom random, int height, BlockPos startPos, TreeFeatureConfig config) {
        List<FoliagePlacer.TreeNode> list = Lists.newArrayList();
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        int maxLogs = this.maxLogs.get(random);
        int logHeightFromTop = this.logHeightFromTop.get(random);
        int maxAboveHole = this.maxHeightAboveHole.get(random);
        int logsAboveHole = 0;
        Direction logDir = Direction.Type.HORIZONTAL.random(random);
        int placedLogs = 0;
        for (int i = 0; i < height; ++i) {
            int x = startPos.getX() + (logDir.getOffsetX() * i);
            int z = startPos.getZ() + (logDir.getOffsetZ() * i);
            boolean solidBelow = !TreeFeature.canReplace(world, mutable.set(x, startPos.getY(), z));
            if (solidBelow || logsAboveHole<maxAboveHole) {
                int holeAddition = !solidBelow ? 1 : 0;
                if (!TreeFeature.canReplace(world, mutable.set(x, startPos.getY(), z))) {
                    if (config.trunkProvider.getBlockState(random, mutable.set(x, startPos.getY(), z)).contains(Properties.AXIS)) {
                        Direction.Axis axis = logDir.getOffsetX() != 0 ? Direction.Axis.X : (logDir.getOffsetY() != 0 ? Direction.Axis.Y : Direction.Axis.Z);
                        replacer.accept(mutable.set(x, startPos.getY(), z), config.trunkProvider.getBlockState(random, mutable.set(x, startPos.getY(), z)).with(Properties.AXIS, axis));
                        if (i < height - 1 && random.nextFloat() < this.logChance && placedLogs < maxLogs && (height - 1) - i <= logHeightFromTop) {
                            Direction direction = random.nextFloat() >= 0.33 ? Direction.Type.HORIZONTAL.random(random) : Direction.Type.VERTICAL.random(random);
                            this.generateExtraBranch(world, replacer, random, config, mutable, logDir, i, direction, this.extraBranchLength.get(random));
                        }
                        ++placedLogs;
                        logsAboveHole += holeAddition;
                    } else if (this.getAndSetState(world, replacer, random, mutable.set(x, startPos.getY(), z), config)
                            && i < height - 1 && random.nextFloat() < this.logChance && placedLogs < maxLogs && (height - 1) - i <= logHeightFromTop) {
                        Direction direction = random.nextFloat() >= 0.33 ? Direction.Type.HORIZONTAL.random(random) : Direction.Type.VERTICAL.random(random);
                        this.generateExtraBranch(world, replacer, random, config, mutable, logDir, i, direction, this.extraBranchLength.get(random));
                        ++placedLogs;
                        logsAboveHole += holeAddition;
                    }
                }
            }
        }

        return list;
    }

    private void generateExtraBranch(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, AbstractRandom random, TreeFeatureConfig config, BlockPos.Mutable pos, Direction offsetDir, int offset, Direction direction, int length) {
        int x = pos.getX();
        int z = pos.getZ();
        int y = pos.getY();
        for (int l = 0; l < length; ++l) {
            x += offsetDir.getOffsetX() * offset;
            z += offsetDir.getOffsetZ() * offset;
            y += offsetDir.getOffsetY() * offset;
            x += direction.getOffsetX() * l;
            z += direction.getOffsetZ() * l;
            y += direction.getOffsetY() * l;
            if (TreeFeature.canReplace(world, pos.set(x, y, z))) {
                if (config.trunkProvider.getBlockState(random, pos.set(x, y, z)).contains(Properties.AXIS)) {
                    Direction.Axis axis = direction.getOffsetX()!=0 ? Direction.Axis.X : (direction.getOffsetY()!=0 ? Direction.Axis.Y : Direction.Axis.Z);
                    replacer.accept(pos.set(x, y, z), config.trunkProvider.getBlockState(random, pos.set(x, y, z)).with(Properties.AXIS, axis));
                } else {
                    this.getAndSetState(world, replacer, random, pos.set(x, y, z), config);
                }
            }
        }
    }

}