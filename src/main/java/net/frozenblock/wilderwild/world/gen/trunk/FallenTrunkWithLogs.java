package net.frozenblock.wilderwild.world.gen.trunk;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
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
        }), Codec.floatRange(0.0F, 1.0F).fieldOf("moss_carpet_chance").forGetter((trunkPlacer) -> {
            return trunkPlacer.logChance;
        }), IntProvider.NON_NEGATIVE_CODEC.fieldOf("max_logs").forGetter((trunkPlacer) -> {
            return trunkPlacer.maxLogs;
        }), IntProvider.NON_NEGATIVE_CODEC.fieldOf("max_height_above_hole").forGetter((trunkPlacer) -> {
            return trunkPlacer.maxHeightAboveHole;
        }))).apply(instance, FallenTrunkWithLogs::new);
    });

    private final float logChance;
    private final IntProvider maxLogs;
    private final IntProvider maxHeightAboveHole;
    private final float mossChance;

    public FallenTrunkWithLogs(int baseHeight, int firstRandomHeight, int secondRandomHeight, float logChance, float mossChance, IntProvider maxLogs, IntProvider maxHeightAboveHole) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
        this.logChance = logChance;
        this.maxLogs = maxLogs;
        this.maxHeightAboveHole = maxHeightAboveHole;
        this.mossChance = mossChance;
    }

    protected TrunkPlacerType<?> getType() {
        return WilderWild.FALLEN_TRUNK_WITH_LOGS_PLACER_TYPE;
    }

    public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
        List<FoliagePlacer.TreeNode> list = Lists.newArrayList();
        List<BlockPos> logs = Lists.newArrayList();
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        int maxLogs = this.maxLogs.get(random);
        int maxAboveHole = this.maxHeightAboveHole.get(random);
        int logsAboveHole = 0;
        Direction logDir = Direction.Type.HORIZONTAL.random(random);
        int placedLogs = 0;
        boolean solidBelowInitial = !TreeFeature.canReplace(world, mutable.set(startPos.getX(), startPos.getY() - 1, startPos.getZ()));
        if (solidBelowInitial) {
            for (int i = 0; i < height; ++i) {
                int x = startPos.getX() + (logDir.getOffsetX() * i);
                int z = startPos.getZ() + (logDir.getOffsetZ() * i);
                boolean solidBelow = !TreeFeature.canReplace(world, mutable.set(x, startPos.getY() - 1, z));
                if (solidBelow || logsAboveHole < maxAboveHole) {
                    int holeAddition = !solidBelow ? 1 : 0;
                    if (TreeFeature.canReplace(world, mutable.set(x, startPos.getY(), z))) {
                        if (config.trunkProvider.getBlockState(random, mutable.set(x, startPos.getY(), z)).contains(Properties.AXIS)) {
                            Direction.Axis axis = logDir.getOffsetX() != 0 ? Direction.Axis.X : (logDir.getOffsetY() != 0 ? Direction.Axis.Y : Direction.Axis.Z);
                            replacer.accept(mutable.set(x, startPos.getY(), z), config.trunkProvider.getBlockState(random, mutable.set(x, startPos.getY(), z)).with(Properties.AXIS, axis));
                            if (random.nextFloat() > 0.28) {
                                logs.add(new BlockPos(x, startPos.getY(), z));
                            }
                            if (i < height - 1 && random.nextFloat() < this.logChance && placedLogs < maxLogs) {
                                Direction direction = random.nextFloat() >= 0.33 ? Direction.Type.HORIZONTAL.random(random) : Direction.Type.VERTICAL.random(random);
                                this.generateExtraBranch(logs, world, replacer, random, config, mutable, logDir, i, direction);
                            }
                            ++placedLogs;
                            logsAboveHole += holeAddition;
                        } else if (config.trunkProvider.getBlockState(random, mutable.set(x, startPos.getY(), z)).contains(Properties.FACING)) {
                            replacer.accept(mutable.set(x, startPos.getY(), z), config.trunkProvider.getBlockState(random, mutable.set(x, startPos.getY(), z)).with(Properties.FACING, logDir));
                            if (random.nextFloat() > 0.28) {
                                logs.add(new BlockPos(x, startPos.getY(), z));
                            }
                            if (i < height - 1 && random.nextFloat() < this.logChance && placedLogs < maxLogs) {
                                Direction direction = random.nextFloat() >= 0.33 ? Direction.Type.HORIZONTAL.random(random) : Direction.Type.VERTICAL.random(random);
                                this.generateExtraBranch(logs, world, replacer, random, config, mutable, logDir, i, direction);
                            }
                            ++placedLogs;
                            logsAboveHole += holeAddition;
                        } else if (this.getAndSetState(world, replacer, random, mutable.set(x, startPos.getY(), z), config)) {
                            if (random.nextFloat() > 0.28) {
                                logs.add(new BlockPos(x, startPos.getY(), z));
                            }
                            if (i < height - 1 && random.nextFloat() < this.logChance && placedLogs < maxLogs) {
                                Direction direction = random.nextFloat() >= 0.33 ? Direction.Type.HORIZONTAL.random(random) : Direction.Type.VERTICAL.random(random);
                                this.generateExtraBranch(logs, world, replacer, random, config, mutable, logDir, i, direction);
                                ++placedLogs;
                                logsAboveHole += holeAddition;
                            }
                        }
                    } else {
                        i = height + 2;
                    }
                }
            }
            if (random.nextFloat() > mossChance) {
                for (BlockPos pos : logs) {
                    if (TreeFeature.canReplace(world, pos.up())) {
                        replacer.accept(pos.up(), Blocks.MOSS_CARPET.getDefaultState());
                    }
                }
            }
        }
        return list;
    }

    private void generateExtraBranch(List<BlockPos> logs, TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, TreeFeatureConfig config, BlockPos.Mutable pos, Direction offsetDir, int offset, Direction direction) {
        int x = pos.getX();
        int z = pos.getZ();
        int y = pos.getY();
        if (offsetDir.getAxis() != direction.getAxis()) {
            x += offsetDir.getOffsetX() * offset;
            z += offsetDir.getOffsetZ() * offset;
            y += offsetDir.getOffsetY() * offset;
            x += direction.getOffsetX();
            z += direction.getOffsetZ();
            y += direction.getOffsetY();
            if (TreeFeature.canReplace(world, pos.set(x, y, z))) {
                if (config.trunkProvider.getBlockState(random, pos.set(x, y, z)).contains(Properties.AXIS)) {
                    Direction.Axis axis = direction.getOffsetX() != 0 ? Direction.Axis.X : (direction.getOffsetY() != 0 ? Direction.Axis.Y : Direction.Axis.Z);
                    replacer.accept(pos.set(x, y, z), config.trunkProvider.getBlockState(random, pos.set(x, y, z)).with(Properties.AXIS, axis));
                    if (random.nextFloat() > 0.28) {
                        logs.add(new BlockPos(x, y, z));
                    }
                } else {
                    this.getAndSetState(world, replacer, random, pos.set(x, y, z), config);
                    if (random.nextFloat() > 0.28) {
                        logs.add(new BlockPos(x, y, z));
                    }
                }
            }
        }
    }

}