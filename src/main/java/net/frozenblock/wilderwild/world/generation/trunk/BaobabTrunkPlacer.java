package net.frozenblock.wilderwild.world.generation.trunk;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.BiConsumer;
import net.frozenblock.lib.math.api.AdvancedMath;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import org.jetbrains.annotations.NotNull;

public class BaobabTrunkPlacer extends TrunkPlacer {
    public static final Codec<BaobabTrunkPlacer> CODEC = RecordCodecBuilder.create((instance) ->
            trunkPlacerParts(instance).apply(instance, BaobabTrunkPlacer::new));

    public BaobabTrunkPlacer(int i, int j, int k) {
        super(i, j, k);
    }

    protected TrunkPlacerType<?> type() {
        return WilderWild.BAOBAB_TRUNK_PLACER;
    }


    /**
     * Baobab Tree Generator
     * Made By LiukRast (Yes im alive!)
     * Process:
     * 1- Generate the main trunk (Just a big parallelepiped)
     * 2- Add Roots (External Vertical Parts)
     * - Step 1: Choose how many of them should be generated (Make a list and then remove random members)
     * - Step 2: Generate them
     * 3- Add Branches and Foliage
     * Easy, I guess ._.
     **/

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(@NotNull LevelSimulatedReader level, @NotNull BiConsumer<BlockPos, BlockState> replacer, @NotNull RandomSource random, int height, @NotNull BlockPos startPos, @NotNull TreeConfiguration config) {
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        BlockPos center = new BlockPos(startPos.getX() - 1, startPos.getY(), startPos.getZ() - 1);
        List<FoliagePlacer.FoliageAttachment> list = Lists.newArrayList();

        double percentage = 30;
        double branchpercentage = 40;
        float toppercentage = 25;

        for (int x = 0; x < 4; x++) { // X
            for (int z = 0; z < 4; z++) { // Z

                terraformDirtBelow(level, replacer, random, new BlockPos(center.getX() + x, startPos.getY() - 1, center.getZ() + z), config);
                for (int y = 0; y <= height; y++) {
                    setLog(level, replacer, random, mutable, config, center, x, y, z);
                }


                if (!AdvancedMath.squareBetween(x, z, 1, 2)) { // only sides

                    if (AdvancedMath.random().nextDouble() <= percentage / 100) {
						switch (x) {
							case 0 -> {
								setLogs(level, replacer, random, mutable, config, center, x - 1, 0, z, height / 2);
								setLogs(level, replacer, random, mutable, config, center, x - 2, 0, z, height / 2 - 1);
								terraformDirtBelow(level, replacer, random, new BlockPos(center.getX() + x - 1, startPos.getY() - 1, center.getZ() + z), config);
								terraformDirtBelow(level, replacer, random, new BlockPos(center.getX() + x - 2, startPos.getY() - 1, center.getZ() + z), config);
							}
							case 3 -> {
								setLogs(level, replacer, random, mutable, config, center, x + 1, 0, z, height / 2);
								setLogs(level, replacer, random, mutable, config, center, x + 2, 0, z, height / 2 - 1);
								terraformDirtBelow(level, replacer, random, new BlockPos(center.getX() + x + 1, startPos.getY() - 1, center.getZ() + z), config);
								terraformDirtBelow(level, replacer, random, new BlockPos(center.getX() + x + 2, startPos.getY() - 1, center.getZ() + z), config);
							}
						}
						switch (z) {
							case 0 -> {
								setLogs(level, replacer, random, mutable, config, center, x, 0, z - 1, height / 2);
								setLogs(level, replacer, random, mutable, config, center, x, 0, z - 2, height / 2 - 1);
								terraformDirtBelow(level, replacer, random, new BlockPos(center.getX() + x, startPos.getY() - 1, center.getZ() + z - 1), config);
								terraformDirtBelow(level, replacer, random, new BlockPos(center.getX() + x, startPos.getY() - 1, center.getZ() + z - 2), config);
							}
							case 3 -> {
								setLogs(level, replacer, random, mutable, config, center, x, 0, z + 1, height / 2);
								setLogs(level, replacer, random, mutable, config, center, x, 0, z + 2, height / 2 - 1);
								terraformDirtBelow(level, replacer, random, new BlockPos(center.getX() + x, startPos.getY() - 1, center.getZ() + z + 1), config);
								terraformDirtBelow(level, replacer, random, new BlockPos(center.getX() + x, startPos.getY() - 1, center.getZ() + z + 2), config);
							}
						}
                    }
                    Direction dir1 = Direction.WEST;
                    Direction dir2 = null;

                    if (x == 3) {
                        dir1 = Direction.EAST;
                    }
                    if (z == 0) {
                        dir1 = Direction.NORTH;
                    }
                    if (z == 3) {
                        dir1 = Direction.SOUTH;
                    }
                    if (x == 0 && z == 0) {
                        dir1 = Direction.WEST;
                        dir2 = Direction.NORTH;
                    }
                    if (x == 3 && z == 0) {
                        dir1 = Direction.EAST;
                        dir2 = Direction.NORTH;
                    }
                    if (x == 0 && z == 3) {
                        dir1 = Direction.WEST;
                        dir2 = Direction.SOUTH;
                    }
                    if (x == 3 && z == 3) {
                        dir1 = Direction.EAST;
                        dir2 = Direction.SOUTH;
                    }
                    if (AdvancedMath.random().nextDouble() <= toppercentage / 100) {
                        list.add(generateBranch(dir1, dir2, 1F / 4F, height, height / 4, 4, level, replacer, random, mutable, config, center, x, z));
                    }
                    if (AdvancedMath.random().nextDouble() <= branchpercentage / 100) {
                        float min = 1F / 3F, max = 1F;
                        float p = ((AdvancedMath.random().nextFloat() * (max - min)) + min);
                        list.add(generateBranch(dir1, dir2, p, height, height, 4, level, replacer, random, mutable, config, center, x, z));
                    }
                }
            }
        }
        return list;
    }

    private FoliagePlacer.FoliageAttachment generateBranch(Direction dir1, Direction dir2, float yequation, int h, int minh, int l, LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> replacer, RandomSource random, BlockPos.MutableBlockPos mutable, TreeConfiguration config, BlockPos startPos, int x, int z) {
        FoliagePlacer.FoliageAttachment node = null;

        int height = (int) ((AdvancedMath.random().nextDouble() * (h - minh)) + minh);

        for (int l1 = 1; l1 <= l; l1++) {
            int eq = (int) Math.floor(yequation * l1);
            if (dir2 == null) {
                BlockPos fpos = AdvancedMath.offset(startPos, dir1, l1);
                BlockPos fpos2 = new BlockPos(fpos.getX() + x, fpos.getY() + height + eq + 1, fpos.getZ() + z);
                setLog(level, replacer, random, mutable, config, fpos, x, height + eq, z);
                if (l1 == l) {
                    node = new FoliagePlacer.FoliageAttachment(fpos2, 0, true);
                }
            } else {
                BlockPos fpos = AdvancedMath.offset(AdvancedMath.offset(startPos, dir1, l1), dir2, l1);
                BlockPos fpos2 = new BlockPos(fpos.getX() + x, fpos.getY() + height + eq + 1, fpos.getZ() + z);
                setLog(level, replacer, random, mutable, config, fpos, x, height + eq, z);
                if (l1 == l) {
                    node = new FoliagePlacer.FoliageAttachment(fpos2, 0, true);
                }
            }
        }
        return node;
    }

    private void setLog(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> replacer, RandomSource random, BlockPos.MutableBlockPos pos, TreeConfiguration config, BlockPos startPos, int x, int y, int z, boolean condition) {
        if (condition) {
            pos.setWithOffset(startPos, x, y, z);
            placeLogIfFree(level, replacer, random, pos, config);
        }
    }

    private void setLogs(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> replacer, RandomSource random, BlockPos.MutableBlockPos pos, TreeConfiguration config, BlockPos startPos, int x, int y, int z, int height) {
        for (int h = 0; h <= height; h++) {
            setLog(level, replacer, random, pos, config, startPos, x, y + h, z);
        }
    }

    private void setLog(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> replacer, RandomSource random, BlockPos.MutableBlockPos pos, TreeConfiguration config, BlockPos startPos, int x, int y, int z) {
        setLog(level, replacer, random, pos, config, startPos, x, y, z, true);
    }

    private void terraformDirtBelow(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> replacer, RandomSource random, BlockPos startPos, TreeConfiguration config) {
        for (int y = 0; true; y++) {
            if ((!isSolid((BlockGetter) level, startPos.below(y))) || ((BlockGetter) level).getBlockState(startPos.below(y)).getBlock() == Blocks.GRASS_BLOCK) {
                setDirtAt(level, replacer, random, startPos.below(y), config);
            } else {
                break;
            }
        }
    }

    private boolean isSolid(BlockGetter level, BlockPos pos) {
        BlockState blockState = level.getBlockState(pos);
        return blockState.isFaceSturdy(level, pos, Direction.DOWN);
    }
}
