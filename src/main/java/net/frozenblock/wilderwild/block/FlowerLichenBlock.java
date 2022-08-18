package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.misc.FlowerLichenParticleRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.MultifaceSpreader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class FlowerLichenBlock extends MultifaceBlock {
    private final MultifaceSpreader grower = new MultifaceSpreader(this);

    public FlowerLichenBlock(Properties settings) {
        super(settings);
    }

    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (FlowerLichenParticleRegistry.blocks.contains(state.getBlock())) {
            int i = pos.getX();
            int j = pos.getY();
            int k = pos.getZ();
            BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
            ParticleOptions particle = FlowerLichenParticleRegistry.particles.get(FlowerLichenParticleRegistry.blocks.indexOf(this));
            for (int l = 0; l < 7; ++l) {
                mutable.set(i + Mth.nextInt(random, -10, 10), j - random.nextInt(10), k + Mth.nextInt(random, -10, 10));
                BlockState blockState = world.getBlockState(mutable);
                if (!blockState.isCollisionShapeFullBlock(world, mutable)) {
                    world.addParticle(particle, (double) mutable.getX() + random.nextDouble(), (double) mutable.getY() + random.nextDouble(), (double) mutable.getZ() + random.nextDouble(), 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        for (Direction direction : DIRECTIONS) {
            if (this.isFaceSupported(direction)) {
                builder.add(getFaceProperty(direction));
            }
        }
    }

    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        boolean bl = false;
        if (world.getBlockState(pos).is(Blocks.WATER)) {
            return false;
        }
        for (Direction direction : DIRECTIONS) {
            if (hasFace(state, direction)) {
                BlockPos blockPos = pos.relative(direction);
                if (!canAttachTo(world, direction, blockPos, world.getBlockState(blockPos))) {
                    return false;
                }
                bl = true;
            }
        }
        return bl;
    }

    public static boolean canAttachTo(BlockGetter world, Direction direction, BlockPos pos, BlockState state) {
        return Block.isFaceFull(state.getBlockSupportShape(world, pos), direction.getOpposite()) || Block.isFaceFull(state.getCollisionShape(world, pos), direction.getOpposite()) && !world.getBlockState(pos).is(Blocks.WATER);
    }

    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        return !context.getItemInHand().is(state.getBlock().asItem()) || super.canBeReplaced(state, context);
    }

    @Override
    public MultifaceSpreader getSpreader() {
        return grower;
    }
}
