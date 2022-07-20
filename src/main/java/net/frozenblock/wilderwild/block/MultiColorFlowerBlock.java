package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.FlowerColors;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class MultiColorFlowerBlock extends BushBlock {
    private static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 5.0D, 16.0D);
    public static final EnumProperty<FlowerColors> COLORS = RegisterProperties.FLOWER_COLOR;

    public final List<FlowerColors> COLOR_LIST;

    public MultiColorFlowerBlock(Properties settings, List<FlowerColors> list) {
        super(settings);
        this.COLOR_LIST = list;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(COLORS);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (random.nextFloat() > 0.9F && state.getValue(COLORS) == FlowerColors.NONE) {
            world.setBlockAndUpdate(pos, state.setValue(COLORS, COLOR_LIST.get(WilderWild.random().nextInt(COLOR_LIST.size()))));
        }
    }

    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        Vec3 vec3d = state.getOffset(world, pos);
        return SHAPE.move(vec3d.x, vec3d.y, vec3d.z);
    }
}
