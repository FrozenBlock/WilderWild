package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.misc.FlowerLichenParticleRegistry;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class FlowerLichenBlock extends MultifaceGrowthBlock {
    private final LichenGrower grower = new LichenGrower(this);

    public FlowerLichenBlock(Settings settings) {
        super(settings);
    }

    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (FlowerLichenParticleRegistry.blocks.contains(state.getBlock())) {
            int i = pos.getX();
            int j = pos.getY();
            int k = pos.getZ();
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            ParticleEffect particle = FlowerLichenParticleRegistry.particles.get(FlowerLichenParticleRegistry.blocks.indexOf(this));
            for (int l = 0; l < 7; ++l) {
                mutable.set(i + MathHelper.nextInt(random, -10, 10), j - random.nextInt(10), k + MathHelper.nextInt(random, -10, 10));
                BlockState blockState = world.getBlockState(mutable);
                if (!blockState.isFullCube(world, mutable)) {
                    world.addParticle(particle, (double) mutable.getX() + random.nextDouble(), (double) mutable.getY() + random.nextDouble(), (double) mutable.getZ() + random.nextDouble(), 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        for (Direction direction : DIRECTIONS) {
            if (this.canHaveDirection(direction)) {
                builder.add(getProperty(direction));
            }
        }
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        boolean bl = false;
        if (world.getBlockState(pos).isOf(Blocks.WATER)) {
            return false;
        }
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
        return !context.getStack().isOf(state.getBlock().asItem()) || super.canReplace(state, context);
    }

    @Override
    public LichenGrower getGrower() {
        return grower;
    }
}
