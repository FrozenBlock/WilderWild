package net.frozenblock.wilderwild.mixin.snowlogging.blocks.candle;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.FireChargeItem;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin({FlintAndSteelItem.class, FireChargeItem.class})
public class CandleCanLightMixin {

	/**
	 * Replaces canLight() with canLight() and !shouldHitSnow()
	 *
	 * @param original canLight()
	 * @return canLight() and !shouldHitSnow()
	 */
	@ModifyExpressionValue(
		method = "useOn",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/CandleBlock;canLight(Lnet/minecraft/world/level/block/state/BlockState;)Z"
		)
	)
	public boolean wilderWild$useOn(
		boolean original, UseOnContext context,
		@Local Level level, @Local BlockPos blockPos, @Local BlockState blockState
	) {
		return original && !SnowloggingUtils.shouldHitSnow(blockState, blockPos, level, context.getClickLocation());
	}
}
