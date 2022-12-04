package net.frozenblock.wilderwild.world.gen.trunk;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.BiConsumer;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

public class FallenTrunkWithLogs extends TrunkPlacer {
    public static final Codec<FallenTrunkWithLogs> CODEC = RecordCodecBuilder.create((instance) -> {
        return trunkPlacerParts(instance).and(instance.group(Codec.floatRange(0.0F, 1.0F).fieldOf("place_branch_chance").forGetter((trunkPlacer) -> {
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

    protected TrunkPlacerType<?> type() {
        return WilderWild.FALLEN_TRUNK_WITH_LOGS_PLACER_TYPE;
    }

    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> replacer, RandomSource random, int height, BlockPos startPos, TreeConfiguration config) {
        List<FoliagePlacer.FoliageAttachment> list = Lists.newArrayList();
        List<BlockPos> logs = Lists.newArrayList();
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        int maxLogs = this.maxLogs.sample(random);
        int maxAboveHole = this.maxHeightAboveHole.sample(random);
        int logsAboveHole = 0;
        Direction logDir = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        int placedLogs = 0;
        boolean solidBelowInitial = !TreeFeature.validTreePos(level, mutable.set(startPos.getX(), startPos.getY() - 1, startPos.getZ())) && !TreeFeature.isAirOrLeaves(level, mutable.set(startPos.getX(), startPos.getY() - 1, startPos.getZ()));
        if (solidBelowInitial) {
            for (int i = 0; i < height; ++i) {
                int x = startPos.getX() + (logDir.getStepX() * i);
                int z = startPos.getZ() + (logDir.getStepZ() * i);
                boolean solidBelow = !TreeFeature.validTreePos(level, mutable.set(x, startPos.getY() - 1, z)) && !TreeFeature.isAirOrLeaves(level, mutable.set(x, startPos.getY() - 1, z));
                if (solidBelow || logsAboveHole < maxAboveHole) {
                    int holeAddition = !solidBelow ? 1 : 0;
                    if (TreeFeature.validTreePos(level, mutable.set(x, startPos.getY(), z))) {
                        if (config.trunkProvider.getState(random, mutable.set(x, startPos.getY(), z)).hasProperty(BlockStateProperties.AXIS)) {
                            Direction.Axis axis = logDir.getStepX() != 0 ? Direction.Axis.X : (logDir.getStepY() != 0 ? Direction.Axis.Y : Direction.Axis.Z);
                            replacer.accept(mutable.set(x, startPos.getY(), z), config.trunkProvider.getState(random, mutable.set(x, startPos.getY(), z)).setValue(BlockStateProperties.AXIS, axis));
                            if (random.nextFloat() > 0.28) {
                                logs.add(new BlockPos(x, startPos.getY(), z));
                            }
                            if (i < height - 1 && random.nextFloat() < this.logChance && placedLogs < maxLogs) {
                                Direction direction = random.nextFloat() >= 0.33 ? Direction.Plane.HORIZONTAL.getRandomDirection(random) : Direction.Plane.VERTICAL.getRandomDirection(random);
                                this.generateExtraBranch(logs, level, replacer, random, config, mutable, logDir, i, direction);
                            }
                            ++placedLogs;
                            logsAboveHole += holeAddition;
                        } else if (config.trunkProvider.getState(random, mutable.set(x, startPos.getY(), z)).hasProperty(BlockStateProperties.FACING)) {
                            replacer.accept(mutable.set(x, startPos.getY(), z), config.trunkProvider.getState(random, mutable.set(x, startPos.getY(), z)).setValue(BlockStateProperties.FACING, logDir));
                            if (random.nextFloat() > 0.28) {
                                logs.add(new BlockPos(x, startPos.getY(), z));
                            }
                            if (i < height - 1 && random.nextFloat() < this.logChance && placedLogs < maxLogs) {
                                Direction direction = random.nextFloat() >= 0.33 ? Direction.Plane.HORIZONTAL.getRandomDirection(random) : Direction.Plane.VERTICAL.getRandomDirection(random);
                                this.generateExtraBranch(logs, level, replacer, random, config, mutable, logDir, i, direction);
                            }
                            ++placedLogs;
                            logsAboveHole += holeAddition;
                        } else if (this.placeLog(level, replacer, random, mutable.set(x, startPos.getY(), z), config)) {
                            if (random.nextFloat() > 0.28) {
                                logs.add(new BlockPos(x, startPos.getY(), z));
                            }
                            if (i < height - 1 && random.nextFloat() < this.logChance && placedLogs < maxLogs) {
                                Direction direction = random.nextFloat() >= 0.33 ? Direction.Plane.HORIZONTAL.getRandomDirection(random) : Direction.Plane.VERTICAL.getRandomDirection(random);
                                this.generateExtraBranch(logs, level, replacer, random, config, mutable, logDir, i, direction);
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
                    if (TreeFeature.validTreePos(level, pos.above())) {
                        replacer.accept(pos.above(), Blocks.MOSS_CARPET.defaultBlockState());
                    }
                }
            }
        }
        return list;
    }

    private void generateExtraBranch(List<BlockPos> logs, LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> replacer, RandomSource random, TreeConfiguration config, BlockPos.MutableBlockPos pos, Direction offsetDir, int offset, Direction direction) {
        int x = pos.getX();
        int z = pos.getZ();
        int y = pos.getY();
        if (offsetDir.getAxis() != direction.getAxis()) {
            x += offsetDir.getStepX() * offset;
            z += offsetDir.getStepZ() * offset;
            y += offsetDir.getStepY() * offset;
            x += direction.getStepX();
            z += direction.getStepZ();
            y += direction.getStepY();
            if (TreeFeature.validTreePos(level, pos.set(x, y, z))) {
                if (config.trunkProvider.getState(random, pos.set(x, y, z)).hasProperty(BlockStateProperties.AXIS)) {
                    Direction.Axis axis = direction.getStepX() != 0 ? Direction.Axis.X : (direction.getStepY() != 0 ? Direction.Axis.Y : Direction.Axis.Z);
                    replacer.accept(pos.set(x, y, z), config.trunkProvider.getState(random, pos.set(x, y, z)).setValue(BlockStateProperties.AXIS, axis));
                    if (random.nextFloat() > 0.28) {
                        logs.add(new BlockPos(x, y, z));
                    }
                } else if (config.trunkProvider.getState(random, pos.set(x, y, z)).hasProperty(BlockStateProperties.FACING)) {
                    replacer.accept(pos.set(x, y, z), config.trunkProvider.getState(random, pos.set(x, y, z)).setValue(BlockStateProperties.FACING, direction));
                    if (random.nextFloat() > 0.28) {
                        logs.add(new BlockPos(x, y, z));
                    }
                } else {
                    this.placeLog(level, replacer, random, pos.set(x, y, z), config);
                    if (random.nextFloat() > 0.28) {
                        logs.add(new BlockPos(x, y, z));
                    }
                }
            }
        }
    }

}
