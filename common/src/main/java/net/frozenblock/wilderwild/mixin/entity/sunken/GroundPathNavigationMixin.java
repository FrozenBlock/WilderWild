package net.frozenblock.wilderwild.mixin.entity.sunken;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.frozenblock.wilderwild.entity.monster.skeleton.SunkenPathNavigation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(GroundPathNavigation.class)
public abstract class GroundPathNavigationMixin extends PathNavigation {

	public GroundPathNavigationMixin(Mob mob, Level level) {
		super(mob, level);
	}

	@WrapOperation(
		method = "findSurfacePosition",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;isAir()Z",
			ordinal = 0
		)
	)
	private boolean wilderWild$initialCheckForWaterIfSunkenInLiquid(
		BlockState instance, Operation<Boolean> original,
		@Local(argsOnly = true) BlockPos pos
	) {
		if (this.wilderWild$isSunkenNavigation() && this.mob.isInLiquid()) {
			return SunkenPathNavigation.isWaterOrEmptyBlockState(this.level, this.mob, pos, instance);
		}

		return original.call(instance);
	}

	@WrapOperation(
		method = "findSurfacePosition",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;isAir()Z"
		),
		slice = @Slice(
			from = @At(
				value = "INVOKE",
				target = "Lnet/minecraft/core/BlockPos$MutableBlockPos;move(Lnet/minecraft/core/Direction;)Lnet/minecraft/core/BlockPos$MutableBlockPos;"
			)
		)
	)
	private boolean wilderWild$checkForWaterIfSunkenInLiquid(
		BlockState instance, Operation<Boolean> original,
		@Local(name = "columnPos") BlockPos.MutableBlockPos columnPos
	) {
		if (this.wilderWild$isSunkenNavigation() && this.mob.isInLiquid()) {
			return SunkenPathNavigation.isWaterOrEmptyBlockState(this.level, this.mob, columnPos, instance);
		}

		return original.call(instance);
	}

	@Unique
	private boolean wilderWild$isSunkenNavigation() {
		return GroundPathNavigation.class.cast(this) instanceof SunkenPathNavigation;
	}
}
