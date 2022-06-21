package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.misc.FlowerColors;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

import java.util.List;

public class MultiColorFlowerBlock extends PlantBlock {
    private static final VoxelShape SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 5.0D, 16.0D);
    public static final EnumProperty<FlowerColors> COLORS = RegisterProperties.FLOWER_COLOR;

    public final List<FlowerColors> COLOR_LIST;

    public MultiColorFlowerBlock(Settings settings, List<FlowerColors> list) {
        super(settings);
        this.COLOR_LIST = list;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(COLORS);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextFloat() > 0.9F && state.get(COLORS) == FlowerColors.NONE) {
            world.setBlockState(pos, state.with(COLORS, COLOR_LIST.get((int) (Math.random() * COLOR_LIST.size()))));
        }
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Vec3d vec3d = state.getModelOffset(world, pos);
        return SHAPE.offset(vec3d.x, vec3d.y, vec3d.z);
    }
}
