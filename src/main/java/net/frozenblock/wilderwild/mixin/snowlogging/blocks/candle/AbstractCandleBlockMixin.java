package net.frozenblock.wilderwild.mixin.snowlogging.blocks.candle;

import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCandleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractCandleBlock.class)
public class AbstractCandleBlockMixin {

	@Inject(method = "onProjectileHit", at = @At("HEAD"), cancellable = true)
	protected void wilderWild$onProjectileHit(Level world, BlockState state, BlockHitResult hit, Projectile projectile, CallbackInfo info) {
		if (SnowloggingUtils.shouldHitSnow(state, hit.getBlockPos(), world, hit.getLocation())) {
			info.cancel();
		}
	}
}
