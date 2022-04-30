package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.TintedGlassBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.AbstractRandom;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class EchoGlassBlock extends TintedGlassBlock {
    public static final IntProperty DAMAGE = RegisterProperties.DAMAGE;

    public EchoGlassBlock(Settings settings) {
        super(settings);
        this.setDefaultState( this.getDefaultState().with(DAMAGE, 0));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(DAMAGE);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, AbstractRandom random) {
        int light = getLightLevel(world, pos);
        if (light<=7) {
            if (state.get(DAMAGE)>0) {
                world.setBlockState(pos, state.with(DAMAGE, state.get(DAMAGE) - 1));
                //Echo Glass Healing Sound Here
            }
        } else {
            if (state.get(DAMAGE)<3) {
                world.setBlockState(pos, state.with(DAMAGE, state.get(DAMAGE) + 1));
                //Echo Glass Crack Sound Here
            } else {
                world.breakBlock(pos, false);
            }
        }
    }

    public static int getLightLevel(World world, BlockPos blockPos) {
        int finalLight=0;
        for (Direction direction : DIRECTIONS) {
            BlockPos pos = blockPos.offset(direction);
            int skyLight = world.getLightLevel(LightType.SKY, pos);
            int blockLight = world.getLightLevel(LightType.BLOCK, pos);
            finalLight = Math.max(finalLight, Math.max(skyLight, blockLight));
        }
        return finalLight;
    }

    @Override
    public boolean hasRandomTicks(BlockState state) { return true; }
}
