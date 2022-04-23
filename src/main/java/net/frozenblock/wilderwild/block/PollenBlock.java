package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterParticles;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.AbstractRandom;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class PollenBlock extends AbstractLichenBlock {
    private final LichenGrower grower = new LichenGrower(this);
    public PollenBlock(Settings settings) {
        super(settings);
    }

    public void randomDisplayTick(BlockState state, World world, BlockPos pos, AbstractRandom random) {
        int i = pos.getX();
        int j = pos.getY();
        int k = pos.getZ();
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        for(int l = 0; l < 7; ++l) {
            mutable.set(i + MathHelper.nextInt(random, -10, 10), j - random.nextInt(10), k + MathHelper.nextInt(random, -10, 10));
            BlockState blockState = world.getBlockState(mutable);
            if (!blockState.isFullCube(world, mutable)) {
                world.addParticle(RegisterParticles.POLLEN, (double)mutable.getX() + random.nextDouble(), (double)mutable.getY() + random.nextDouble(), (double)mutable.getZ() + random.nextDouble(), 0.0D, 0.0D, 0.0D);
            }
        }
    }


    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        Direction[] var2 = DIRECTIONS;
        int var3 = var2.length;

        for (Direction direction : var2) {
            if (this.canHaveDirection(direction)) {
                builder.add(getProperty(direction));
            }
        }
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        boolean bl = false;
        if (world.getBlockState(pos).isOf(Blocks.WATER)) { return false; }

        for (Direction direction : DIRECTIONS) {
            if (hasDirection(state, direction)) {
                BlockPos blockPos = pos.offset(direction);
                if (!canGrowOn(world, direction, blockPos, world.getBlockState(blockPos))) {
                    return false;
                }

                bl = true;
            }
        }

        return bl;
    }

    public static boolean canGrowOn(BlockView world, Direction direction, BlockPos pos, BlockState state) {
        return Block.isFaceFullSquare(state.getSidesShape(world, pos), direction.getOpposite()) || Block.isFaceFullSquare(state.getCollisionShape(world, pos), direction.getOpposite()) && !world.getBlockState(pos).isOf(Blocks.WATER);
    }

    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return !context.getStack().isOf(RegisterBlocks.POLLEN_BLOCK.asItem()) || super.canReplace(state, context);
    }

    @Override
    public LichenGrower getGrower() {
        return grower;
    }
}
