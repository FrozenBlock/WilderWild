package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.registry.RegisterParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MultifaceSpreader;
import net.minecraft.world.level.block.state.BlockState;

public class PollenBlock extends FlowerLichenBlock {
    private final MultifaceSpreader grower = new MultifaceSpreader(this);

    public PollenBlock(Properties settings) {
        super(settings);
    }

    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        int i = pos.getX();
        int j = pos.getY();
        int k = pos.getZ();
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        for (int l = 0; l < 7; ++l) {
            mutable.set(i + Mth.nextInt(random, -10, 10), j - random.nextInt(10), k + Mth.nextInt(random, -10, 10));
            BlockState blockState = world.getBlockState(mutable);
            if (!blockState.isCollisionShapeFullBlock(world, mutable)) {
                world.addParticle(RegisterParticles.POLLEN, (double) mutable.getX() + random.nextDouble(), (double) mutable.getY() + random.nextDouble(), (double) mutable.getZ() + random.nextDouble(), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    public static boolean canAttachTo(BlockGetter world, Direction direction, BlockPos pos, BlockState state) {
        return Block.isFaceFull(state.getBlockSupportShape(world, pos), direction.getOpposite()) || Block.isFaceFull(state.getCollisionShape(world, pos), direction.getOpposite()) && !world.getBlockState(pos).is(Blocks.WATER);
    }
}
