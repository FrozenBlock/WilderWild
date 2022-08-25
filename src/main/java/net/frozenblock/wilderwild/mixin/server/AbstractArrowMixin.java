package net.frozenblock.wilderwild.mixin.server;

import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrow.class)
public class AbstractArrowMixin {

    @Inject(method = "onHitBlock", at = @At("HEAD"))
    public void onHitBlock(BlockHitResult blockHitResult, CallbackInfo info) {
        AbstractArrow arrow = AbstractArrow.class.cast(this);
        Vec3 vec3 = blockHitResult.getLocation().subtract(arrow.getX(), arrow.getY(), arrow.getZ());
        Vec3 speed = arrow.getDeltaMovement();
        if (!arrow.level.isClientSide) {
            if (arrow.level instanceof ServerLevel server) {
                BlockState state = server.getBlockState(blockHitResult.getBlockPos());
                double d = speed.length();
                int decide = ((int) ((d * d) * 2));
                if (decide > 0) {
                    server.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state), blockHitResult.getLocation().x(), blockHitResult.getLocation().y(), blockHitResult.getLocation().z(), server.random.nextIntBetweenInclusive(1, ((int) ((d * d) / 2))), 0, 0, 0, 0.05D);
                }
            }
        }
    }

}
