package net.frozenblock.wilderwild.world.gen.trunk;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
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

import java.util.List;
import java.util.function.BiConsumer;

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
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        // MAIN TRUNK
        for(int x = -1; x <= 2; x++) {
            for(int z = -1; z <= 2; z++) {
                setToDirt(world, replacer, random, new BlockPos(blockPos.getX() + x, blockPos.getY(), blockPos.getZ() + z), config); // generates a 4x4 of dirt

                for(int h = 0; h <= height; h++) {
                    setLog(world, replacer, random, mutable, config, startPos, x, h, z); //generates the main trunk with a loop
                }
            }
        }

        // ROOTS
        float percentage = 30; // percentage for spawning a root

        for(int x = -2; x <= 3; x++) {
            for(int z = -2; z <= 3; z++) {
                if((x < -1 || x > 2) || (z < -1 || z > 2)) { //only border, dont affect trunk inside
                    boolean case1 = (x == -2 && z == -2);
                    boolean case2 = (x == 3 && z == 3);
                    boolean case3 = (x == -2 && z == 3);
                    boolean case4 = (x == 3 && z == -2);
                    if(!(case1 || case2 || case3 || case4)) { // check that the block isn't on the edges
                        if (Math.random() <= percentage / 100) {
                            for (int h = 0; h <= height/2; h++) { // uses 1/2 of the height
                                setLog(world, replacer, random, mutable, config, startPos, x, h, z); //generates the roots trunk with a loop
                            }
                            for (int h = 0; h <= height/3; h++) { // uses 1/3 of the height
                                if(x == 3) {
                                    x++;
                                } else if(x == -2) {
                                    x--;
                                } else if(z == 3) {
                                    z++;
                                } else if(z == -2) {
                                    z--;
                                }
                                setLog(world, replacer, random, mutable, config, startPos, x, h, z); //generates the roots trunk external with a loop
                            }
                        }
                    }
                }
            }
        }

        float branchpercentage = 50;

        int branchmin = 2;
        int branchmax = 4;

        // BRANCHES

        return branchBase(branchpercentage, branchmin, branchmax, height, world,  replacer, random, mutable, config, startPos);
    }

    private List<FoliagePlacer.TreeNode> branchBase(float branchpercentage, int branchmin, int branchmax, int height, TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos.Mutable mutable, TreeFeatureConfig config, BlockPos startPos) {
        List<FoliagePlacer.TreeNode> list = Lists.newArrayList();
        for(int x = -1; x <= 2; x++) {
            for(int z = -1; z <= 2; z++) {
                if((x < 0 || x > 1) || (z < 0 || z > 1)) { // only walls
                    if(Math.random() <= branchpercentage/200) {

                        int branchlenght = (int)AdvancedMath.range(branchmin, branchmax, (float)Math.random());

                        int fh = height - (int)AdvancedMath.range((height/4f)*3, height, (float)Math.random());

                        boolean case1 = (x == -1 && z == -1);
                        boolean case2 = (x == 2 && z == 2);
                        boolean case3 = (x == -1 && z == 2);
                        boolean case4 = (x == 2 && z == -1);
                        if (case1) {
                            list.add(generateBranch(height,branchlenght, 1, world, replacer, random, mutable, config, startPos, x, fh + 1, z));
                        } else if (case2) {
                            list.add(generateBranch(height,branchlenght, 2, world, replacer, random, mutable, config, startPos, x, fh + 1, z));
                        } else if (case3) {
                            list.add(generateBranch(height,branchlenght, 3, world, replacer, random, mutable, config, startPos, x, fh + 1, z));
                        } else if (case4) {
                            list.add(generateBranch(height,branchlenght, 4, world, replacer, random, mutable, config, startPos, x, fh + 1, z));
                        } else {
                            if(x == 2) {
                                list.add(generateBranch(height,branchlenght, 5, world, replacer, random, mutable, config, startPos, x, fh + 1, z));
                            } else if(x == -1) {
                                list.add(generateBranch(height,branchlenght, 6, world, replacer, random, mutable, config, startPos, x, fh + 1, z));
                            } else if(z == 2) {
                                list.add(generateBranch(height,branchlenght, 7, world, replacer, random, mutable, config, startPos, x, fh + 1, z));
                            } else {
                                list.add(generateBranch(height,branchlenght, 8, world, replacer, random, mutable, config, startPos, x, fh + 1, z));
                            }
                        }
                    }
                }
            }
        }
        for(int x = -1; x <= 2; x++) {
            for(int z = -1; z <= 2; z++) {
                if((x < 0 || x > 1) || (z < 0 || z > 1)) { // only walls
                    if(Math.random() <= branchpercentage/100) {

                        int branchlenght = (int)AdvancedMath.range(branchmin, branchmax, (float)Math.random());

                        boolean case1 = (x == -1 && z == -1);
                        boolean case2 = (x == 2 && z == 2);
                        boolean case3 = (x == -1 && z == 2);
                        boolean case4 = (x == 2 && z == -1);
                        if (case1) {
                            list.add(generateBranch(height,branchlenght, 1, world, replacer, random, mutable, config, startPos, x, height + 1, z));
                        } else if (case2) {
                            list.add(generateBranch(height,branchlenght, 2, world, replacer, random, mutable, config, startPos, x, height + 1, z));
                        } else if (case3) {
                            list.add(generateBranch(height,branchlenght, 3, world, replacer, random, mutable, config, startPos, x, height + 1, z));
                        } else if (case4) {
                            list.add(generateBranch(height,branchlenght, 4, world, replacer, random, mutable, config, startPos, x, height + 1, z));
                        } else {
                            if(x == 2) {
                                list.add(generateBranch(height,branchlenght, 5, world, replacer, random, mutable, config, startPos, x, height + 1, z));
                            } else if(x == -1) {
                                list.add(generateBranch(height,branchlenght, 6, world, replacer, random, mutable, config, startPos, x, height + 1, z));
                            } else if(z == 2) {
                                list.add(generateBranch(height,branchlenght, 7, world, replacer, random, mutable, config, startPos, x, height + 1, z));
                            } else {
                                list.add(generateBranch(height,branchlenght, 8, world, replacer, random, mutable, config, startPos, x, height + 1, z));
                            }
                        }
                    }
                }
            }
        }
        return list;
    }

    /** #HOW DIRECION WORKS? <p>
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
     * **/
    private FoliagePlacer.TreeNode generateBranch(int height, int length, int direction, TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos.Mutable pos, TreeFeatureConfig config, BlockPos startPos, int x, int y, int z) {
        FoliagePlacer.TreeNode node = null;
        int fy = startPos.getY() + y + 2;
        if(direction == 1) {
            for(int i = 0; i <= length - 1; i++) {
                setLog(world, replacer, random, pos, config, startPos, x - i, y, z - i);
            }
            setLog(world, replacer, random, pos, config, startPos, x - length, y + 1, z - length);
            int fx = startPos.getX() + x - length;
            int fz = startPos.getZ() + z - length;
            node = new FoliagePlacer.TreeNode(new BlockPos(fx, fy, fz), 0, true);
        } else if(direction == 2) {
            for(int i = 0; i <= length - 1; i++) {
                setLog(world, replacer, random, pos, config, startPos, x + i, y, z + i);
            }
            setLog(world, replacer, random, pos, config, startPos, x + length, y + 1, z + length);
            int fx = startPos.getX() + x + length;
            int fz = startPos.getZ() + z + length;
            node = new FoliagePlacer.TreeNode(new BlockPos(fx, fy, fz), 0, true);
        } else if(direction == 3) {
            for(int i = 0; i <= length - 1; i++) {
                setLog(world, replacer, random, pos, config, startPos, x - i, y, z + i);
            }
            setLog(world, replacer, random, pos, config, startPos, x - length, y + 1, z + length);
            int fx = startPos.getX() + x - length;
            int fz = startPos.getZ() + z + length;
            node = new FoliagePlacer.TreeNode(new BlockPos(fx, fy, fz), 0, true);
        } else if(direction == 4) {
            for(int i = 0; i <= length - 1; i++) {
                setLog(world, replacer, random, pos, config, startPos, x + i, y, z - i);
            }
            setLog(world, replacer, random, pos, config, startPos, x + length, y + 1, z - length);
            int fx = startPos.getX() + x + length;
            int fz = startPos.getZ() + z - length;
            node = new FoliagePlacer.TreeNode(new BlockPos(fx, fy, fz), 0, true);
        } else if(direction == 5) {
            for(int i = 0; i <= length - 1; i++) {
                setLog(world, replacer, random, pos, config, startPos, x + i, y, z );
            }
            setLog(world, replacer, random, pos, config, startPos, x + length, y + 1, z);
            int fx = startPos.getX() + x + length;
            int fz = startPos.getZ() + z;
            node = new FoliagePlacer.TreeNode(new BlockPos(fx, fy, fz), 0, true);
        } else if(direction == 6) {
            for(int i = 0; i <= length - 1; i++) {
                setLog(world, replacer, random, pos, config, startPos, x - i, y, z );
            }
            setLog(world, replacer, random, pos, config, startPos, x - length, y + 1, z);
            int fx = startPos.getX() + x - length;
            int fz = startPos.getZ() + z;
            node = new FoliagePlacer.TreeNode(new BlockPos(fx, fy, fz), 0, true);
        } else if(direction == 7) {
            for(int i = 0; i <= length - 1; i++) {
                setLog(world, replacer, random, pos, config, startPos, x, y, z + i );
            }
            setLog(world, replacer, random, pos, config, startPos, x, y + 1, z + length);
            int fx = startPos.getX() + x;
            int fz = startPos.getZ() + z + length;
            node = new FoliagePlacer.TreeNode(new BlockPos(fx, fy, fz), 0, true);
        } else if(direction == 8) {
            for(int i = 0; i <= length - 1; i++) {
                setLog(world, replacer, random, pos, config, startPos, x, y, z - i);
            }
            setLog(world, replacer, random, pos, config, startPos, x, y + 1, z - length);
            int fx = startPos.getX() + x;
            int fz = startPos.getZ() + z - length;
            node = new FoliagePlacer.TreeNode(new BlockPos(fx, fy, fz), 0, true);
        }

        return node;
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