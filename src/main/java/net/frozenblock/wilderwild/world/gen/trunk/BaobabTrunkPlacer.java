package net.frozenblock.wilderwild.world.gen.trunk;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.frozenblock.api.mathematics.AdvancedMath;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import java.util.List;
import java.util.function.BiConsumer;

public class BaobabTrunkPlacer extends TrunkPlacer {
    public static final Codec<net.frozenblock.wilderwild.world.gen.trunk.BaobabTrunkPlacer> CODEC = RecordCodecBuilder.create((instance) ->
            trunkPlacerParts(instance).apply(instance, BaobabTrunkPlacer::new));

    public BaobabTrunkPlacer(int i, int j, int k) {
        super(i, j, k);
    }

    protected TrunkPlacerType<?> type() {
        return WilderWild.BAOBAB_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader world, BiConsumer<BlockPos, BlockState> replacer, RandomSource random, int height, BlockPos startPos, TreeConfiguration config) {
        // TRUNK
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        for (int x = -1; x <= 2; x++) {
            for (int z = -1; z <= 2; z++) {
                setDirtAt(world, replacer, random, new BlockPos(startPos.getX() + x, startPos.getY() - 1, startPos.getZ() + z), config);

                for (int h = 0; h <= height; h++) {
                    setLog(world, replacer, random, mutable, config, startPos, x, h, z);
                }
            }
        }

        float percentage = 30;
        // ROOTS
        for (int x = -2; x <= 3; x++) {
            for (int z = -2; z <= 3; z++) {
                if (!((x > -2 && x < 3) && (z > -2 && z < 3))) { // walls only
                    boolean one = x == -2 && z == -2;
                    boolean two = x == 3 && z == -2;
                    boolean three = x == -2 && z == 3;
                    boolean four = x == 3 && z == 3;
                    if (!(one || two || three || four)) { // no edges
                        if (Math.random() <= percentage / 100) {
                            setDirtAt(world, replacer, random, new BlockPos(startPos.getX() + x - 1, startPos.getY() - 1, startPos.getZ() + z), config);
                            setDirtAt(world, replacer, random, new BlockPos(startPos.getX() + x - 1, startPos.getY() - 1, startPos.getZ() + z), config);
                            for (int h = 0; h <= height / 3; h++) {
                                switch (x) {
                                    case -2 -> setLog(world, replacer, random, mutable, config, startPos, x - 1, h, z);
                                    case 3 -> setLog(world, replacer, random, mutable, config, startPos, x + 1, h, z);
                                    default -> {
                                        if (z == -2) {
                                            setLog(world, replacer, random, mutable, config, startPos, x, h, z - 1);
                                        } else {
                                            setLog(world, replacer, random, mutable, config, startPos, x, h, z + 1);
                                        }
                                    }
                                }
                            }
                            for (int h = 0; h <= height / 2; h++) {
                                switch (x) {
                                    case -2 -> setLog(world, replacer, random, mutable, config, startPos, x, h, z);
                                    case 3 -> setLog(world, replacer, random, mutable, config, startPos, x, h, z);
                                    default -> {
                                        if (z == -2) {
                                            setLog(world, replacer, random, mutable, config, startPos, x, h, z);
                                        } else {
                                            setLog(world, replacer, random, mutable, config, startPos, x, h, z);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // BRANCHES

        float branchpercentage = 50;

        int branchmin = 2;
        int branchmax = 4;

        return branchBase(branchpercentage, branchmin, branchmax, height, world, replacer, random, mutable, config, startPos);
    }

    private List<FoliagePlacer.FoliageAttachment> branchBase(float branchpercentage, int branchmin, int branchmax, int height, LevelSimulatedReader world, BiConsumer<BlockPos, BlockState> replacer, RandomSource random, BlockPos.MutableBlockPos mutable, TreeConfiguration config, BlockPos startPos) {
        List<FoliagePlacer.FoliageAttachment> list = Lists.newArrayList();
        for (int x = -1; x <= 2; x++) {
            for (int z = -1; z <= 2; z++) {
                if ((x < 0 || x > 1) || (z < 0 || z > 1)) { // only walls
                    if (Math.random() <= branchpercentage / 200) {

                        int branchlenght = (int) AdvancedMath.range(branchmin, branchmax, (float) Math.random());

                        int fh = height - (int) AdvancedMath.range((float) height / 2, height, (float) Math.random());

                        boolean case1 = (x == -1 && z == -1);
                        boolean case2 = (x == 2 && z == 2);
                        boolean case3 = (x == -1 && z == 2);
                        boolean case4 = (x == 2 && z == -1);
                        if (case1) {
                            list.add(generateBranch(height, branchlenght, 1, world, replacer, random, mutable, config, startPos, x, fh + 1, z));
                        } else if (case2) {
                            list.add(generateBranch(height, branchlenght, 2, world, replacer, random, mutable, config, startPos, x, fh + 1, z));
                        } else if (case3) {
                            list.add(generateBranch(height, branchlenght, 3, world, replacer, random, mutable, config, startPos, x, fh + 1, z));
                        } else if (case4) {
                            list.add(generateBranch(height, branchlenght, 4, world, replacer, random, mutable, config, startPos, x, fh + 1, z));
                        } else {
                            if (x == 2) {
                                list.add(generateBranch(height, branchlenght, 5, world, replacer, random, mutable, config, startPos, x, fh + 1, z));
                            } else if (x == -1) {
                                list.add(generateBranch(height, branchlenght, 6, world, replacer, random, mutable, config, startPos, x, fh + 1, z));
                            } else if (z == 2) {
                                list.add(generateBranch(height, branchlenght, 7, world, replacer, random, mutable, config, startPos, x, fh + 1, z));
                            } else {
                                list.add(generateBranch(height, branchlenght, 8, world, replacer, random, mutable, config, startPos, x, fh + 1, z));
                            }
                        }
                    }
                }
            }
        }
        for (int x = -1; x <= 2; x++) {
            for (int z = -1; z <= 2; z++) {
                if ((x < 0 || x > 1) || (z < 0 || z > 1)) { // only walls
                    if (Math.random() <= branchpercentage / 100) {

                        int branchlenght = (int) AdvancedMath.range(branchmin, branchmax, (float) Math.random());

                        boolean case1 = (x == -1 && z == -1);
                        boolean case2 = (x == 2 && z == 2);
                        boolean case3 = (x == -1 && z == 2);
                        boolean case4 = (x == 2 && z == -1);
                        if (case1) {
                            list.add(generateBranch(height, branchlenght, 1, world, replacer, random, mutable, config, startPos, x, height + 1, z));
                        } else if (case2) {
                            list.add(generateBranch(height, branchlenght, 2, world, replacer, random, mutable, config, startPos, x, height + 1, z));
                        } else if (case3) {
                            list.add(generateBranch(height, branchlenght, 3, world, replacer, random, mutable, config, startPos, x, height + 1, z));
                        } else if (case4) {
                            list.add(generateBranch(height, branchlenght, 4, world, replacer, random, mutable, config, startPos, x, height + 1, z));
                        } else {
                            if (x == 2) {
                                list.add(generateBranch(height, branchlenght, 5, world, replacer, random, mutable, config, startPos, x, height + 1, z));
                            } else if (x == -1) {
                                list.add(generateBranch(height, branchlenght, 6, world, replacer, random, mutable, config, startPos, x, height + 1, z));
                            } else if (z == 2) {
                                list.add(generateBranch(height, branchlenght, 7, world, replacer, random, mutable, config, startPos, x, height + 1, z));
                            } else {
                                list.add(generateBranch(height, branchlenght, 8, world, replacer, random, mutable, config, startPos, x, height + 1, z));
                            }
                        }
                    }
                }
            }
        }
        return list;
    }

    /**
     * #HOW DIRECION WORKS? <p>
     * 1 -> x- z- <p>
     * 2 -> x+ z+ <p>
     * 3 -> x- z+ <p>
     * 4 -> x+ z- <p>
     * 5 -> x+ <p>
     * 6 -> x- <p>
     * 7 -> z+ <p>
     * 8 -> z- <p>
     * any number different than these will make the game crash cause i want it :)
     * length must be > 0 or ill punch your head with xfrtrex's disc
     **/
    private FoliagePlacer.FoliageAttachment generateBranch(int height, int length, int direction, LevelSimulatedReader world, BiConsumer<BlockPos, BlockState> replacer, RandomSource random, BlockPos.MutableBlockPos pos, TreeConfiguration config, BlockPos startPos, int x, int y, int z) {
        FoliagePlacer.FoliageAttachment node = null;
        int fy = startPos.getY() + y + 2;
        switch (direction) {
            case 1 -> {
                for (int i = 0; i <= length - 1; i++) {
                    setLog(world, replacer, random, pos, config, startPos, x - i, y, z - i);
                }
                setLog(world, replacer, random, pos, config, startPos, x - length, y + 1, z - length);
                int fx = startPos.getX() + x - length;
                int fz = startPos.getZ() + z - length;
                node = new FoliagePlacer.FoliageAttachment(new BlockPos(fx, fy, fz), 0, true);
            }
            case 2 -> {
                for (int i = 0; i <= length - 1; i++) {
                    setLog(world, replacer, random, pos, config, startPos, x + i, y, z + i);
                }
                setLog(world, replacer, random, pos, config, startPos, x + length, y + 1, z + length);
                int fx = startPos.getX() + x + length;
                int fz = startPos.getZ() + z + length;
                node = new FoliagePlacer.FoliageAttachment(new BlockPos(fx, fy, fz), 0, true);
            }
            case 3 -> {
                for (int i = 0; i <= length - 1; i++) {
                    setLog(world, replacer, random, pos, config, startPos, x - i, y, z + i);
                }
                setLog(world, replacer, random, pos, config, startPos, x - length, y + 1, z + length);
                int fx = startPos.getX() + x - length;
                int fz = startPos.getZ() + z + length;
                node = new FoliagePlacer.FoliageAttachment(new BlockPos(fx, fy, fz), 0, true);
            }
            case 4 -> {
                for (int i = 0; i <= length - 1; i++) {
                    setLog(world, replacer, random, pos, config, startPos, x + i, y, z - i);
                }
                setLog(world, replacer, random, pos, config, startPos, x + length, y + 1, z - length);
                int fx = startPos.getX() + x + length;
                int fz = startPos.getZ() + z - length;
                node = new FoliagePlacer.FoliageAttachment(new BlockPos(fx, fy, fz), 0, true);
            }
            case 5 -> {
                for (int i = 0; i <= length - 1; i++) {
                    setLog(world, replacer, random, pos, config, startPos, x + i, y, z);
                }
                setLog(world, replacer, random, pos, config, startPos, x + length, y + 1, z);
                int fx = startPos.getX() + x + length;
                int fz = startPos.getZ() + z;
                node = new FoliagePlacer.FoliageAttachment(new BlockPos(fx, fy, fz), 0, true);
            }
            case 6 -> {
                for (int i = 0; i <= length - 1; i++) {
                    setLog(world, replacer, random, pos, config, startPos, x - i, y, z);
                }
                setLog(world, replacer, random, pos, config, startPos, x - length, y + 1, z);
                int fx = startPos.getX() + x - length;
                int fz = startPos.getZ() + z;
                node = new FoliagePlacer.FoliageAttachment(new BlockPos(fx, fy, fz), 0, true);
            }
            case 7 -> {
                for (int i = 0; i <= length - 1; i++) {
                    setLog(world, replacer, random, pos, config, startPos, x, y, z + i);
                }
                setLog(world, replacer, random, pos, config, startPos, x, y + 1, z + length);
                int fx = startPos.getX() + x;
                int fz = startPos.getZ() + z + length;
                node = new FoliagePlacer.FoliageAttachment(new BlockPos(fx, fy, fz), 0, true);
            }
            case 8 -> {
                for (int i = 0; i <= length - 1; i++) {
                    setLog(world, replacer, random, pos, config, startPos, x, y, z - i);
                }
                setLog(world, replacer, random, pos, config, startPos, x, y + 1, z - length);
                int fx = startPos.getX() + x;
                int fz = startPos.getZ() + z - length;
                node = new FoliagePlacer.FoliageAttachment(new BlockPos(fx, fy, fz), 0, true);
            }
        }

        return node;
    }


    private void setLog(LevelSimulatedReader world, BiConsumer<BlockPos, BlockState> replacer, RandomSource random, BlockPos.MutableBlockPos pos, TreeConfiguration config, BlockPos startPos, int x, int y, int z, boolean condition) {
        if (condition) {
            pos.setWithOffset(startPos, x, y, z);
            placeLogIfFree(world, replacer, random, pos, config);
        }
    }

    private void setLog(LevelSimulatedReader world, BiConsumer<BlockPos, BlockState> replacer, RandomSource random, BlockPos.MutableBlockPos pos, TreeConfiguration config, BlockPos startPos, int x, int y, int z) {
        setLog(world, replacer, random, pos, config, startPos, x, y, z, true);
    }
}