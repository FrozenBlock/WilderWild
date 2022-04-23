package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterParticles;
import net.minecraft.block.AbstractLichenBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.LichenGrower;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.AbstractRandom;
import net.minecraft.world.World;

public class PollenBlock extends AbstractLichenBlock {
    private final LichenGrower grower = new LichenGrower(this);
    public PollenBlock(Settings settings) {
        super(settings);
    }

    public void randomDisplayTick(BlockState state, World world, BlockPos pos, AbstractRandom random) {
        int i = pos.getX();
        int j = pos.getY();
        int k = pos.getZ();
        double d = (double)i + random.nextDouble();
        double e = (double)j + 0.7D;
        double f = (double)k + random.nextDouble();
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        for(int l = 0; l < 14; ++l) {
            mutable.set(i + MathHelper.nextInt(random, -10, 10), j - random.nextInt(10), k + MathHelper.nextInt(random, -10, 10));
            BlockState blockState = world.getBlockState(mutable);
            if (!blockState.isFullCube(world, mutable)) {
                world.addParticle(RegisterParticles.POLLEN, (double)mutable.getX() + random.nextDouble(), (double)mutable.getY() + random.nextDouble(), (double)mutable.getZ() + random.nextDouble(), 0.0D, 0.0D, 0.0D);
            }
        }

    }

    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return !context.getStack().isOf(RegisterBlocks.POLLEN_BLOCK.asItem()) || super.canReplace(state, context);
    }

    @Override
    public LichenGrower getGrower() {
        return grower;
    }
}
