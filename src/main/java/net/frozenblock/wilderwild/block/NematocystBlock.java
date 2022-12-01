package net.frozenblock.wilderwild.block;

import net.frozenblock.lib.block.api.FaceClusterBlock;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.MultifaceSpreader;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class NematocystBlock extends FaceClusterBlock {

    private final MultifaceSpreader spreader = new MultifaceSpreader(this);

    public NematocystBlock(int height, int xzOffset, Properties properties) {
        super(height, xzOffset, properties);
    }

    public NematocystBlock(Properties properties) {
        this(7, 3, properties);
    }

	//TODO: Slow down projectiles passing through (somehow)
    @Override
    public void onProjectileHit(@NotNull Level level, @NotNull BlockState blockState, @NotNull BlockHitResult blockHitResult, @NotNull Projectile projectile) {
    }

    @Override
    public boolean skipRendering(@NotNull BlockState blockState, BlockState blockState2, @NotNull Direction direction) {
        return blockState2.is(this) || super.skipRendering(blockState, blockState2, direction);
    }

    @Override
    public MultifaceSpreader getSpreader() {
        return this.spreader;
    }
}
