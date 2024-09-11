package net.frozenblock.wilderwild.mixin.block.nether_fungus;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.HugeFungusConfiguration;
import net.minecraft.world.level.levelgen.feature.HugeFungusFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(HugeFungusFeature.class)
public class HugeFungusFeatureMixin {
	@ModifyVariable(method = "place", at = @At(value = "STORE"))
	public boolean wilderWild$placeThickener(boolean original, FeaturePlaceContext<HugeFungusConfiguration> context) {
		return original || (context.config().planted && WWBlockConfig.get().bigFungusGrowth);
	}

	@ModifyVariable(method = "placeStem", at = @At(value = "STORE"), ordinal = 1)
	public boolean wilderWild$placeStemShouldPlace(
		boolean isCorner, @Local(argsOnly = true) RandomSource random,
		@Share("wilderWild$shouldPlace") LocalRef<Boolean> shouldPlace
	) {
		shouldPlace.set(!isCorner || random.nextFloat() < 0.1F);
		return isCorner;
	}

	@WrapOperation(method = "placeStem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/WorldGenLevel;destroyBlock(Lnet/minecraft/core/BlockPos;Z)Z"))
	public boolean wilderWild$placeStemPlantedA(
		WorldGenLevel instance, BlockPos pos, boolean drop, Operation<Boolean> original,
		@Share("wilderWild$shouldPlace") LocalRef<Boolean> shouldPlace
	) {
		if (!shouldPlace.get()) {
			return false;
		}
		return original.call(instance, pos, drop);
	}

	@WrapOperation(method = "placeStem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/WorldGenLevel;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"))
	public boolean wilderWild$placeStemPlantedB(
		WorldGenLevel instance, BlockPos pos, BlockState blockState, int flag, Operation<Boolean> original,
		@Share("wilderWild$shouldPlace") LocalRef<Boolean> shouldPlace
	) {
		if (!shouldPlace.get()) {
			return false;
		}
		return original.call(instance, pos, blockState, flag);
	}
}
