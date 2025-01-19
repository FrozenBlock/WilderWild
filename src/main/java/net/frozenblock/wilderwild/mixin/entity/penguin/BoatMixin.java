package net.frozenblock.wilderwild.mixin.entity.penguin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.frozenblock.wilderwild.entity.impl.BoatBoostInterface;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Boat.class)
public class BoatMixin implements BoatBoostInterface {

	@Unique
	private int wilderWild$boostTicks;

	@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
	public void wilderWild$addAdditionalSaveData(CompoundTag nbt, CallbackInfo info) {
		if (this.wilderWild$boostTicks > 0) nbt.putInt("WilderWildBoatBoostTicks", this.wilderWild$boostTicks);
	}

	@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
	public void wilderWild$readAdditionalSaveData(CompoundTag nbt, CallbackInfo info) {
		if (nbt.contains("WilderWildBoatBoostTicks")) this.wilderWild$boostTicks = nbt.getInt("WilderWildBoatBoostTicks");
	}

	@Inject(method = "tick", at = @At("TAIL"))
	public void wilderWild$tick(CallbackInfo info) {
		this.wilderWild$boostTicks = Math.max(this.wilderWild$boostTicks - 1, 0);
	}

	@WrapOperation(
		method = "controlBoat",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/phys/Vec3;add(DDD)Lnet/minecraft/world/phys/Vec3;"
		)
	)
	public Vec3 wilderWild$bannerSpeedBoost(Vec3 instance, double x, double y, double z, Operation<Vec3> original) {
		double multiplier = this.wilderWild$boostTicks > 0 ? 2D : 1D;
		return original.call(instance, x * multiplier, y * multiplier, z * multiplier);
	}

	@Unique
	@Override
	public void wilderWild$setBoatBoostTicks(int ticks) {
		this.wilderWild$boostTicks = ticks;
	}

	@Unique
	@Override
	public int wilderWild$getBoatBoostTicks() {
		return this.wilderWild$boostTicks;
	}
}
