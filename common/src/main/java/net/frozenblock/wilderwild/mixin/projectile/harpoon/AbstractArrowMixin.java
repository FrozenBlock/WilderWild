/*
 * Copyright 2026 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.mixin.projectile.harpoon;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import java.util.Collection;
import net.frozenblock.wilderwild.entity.projectile.arrow.Harpoon;
import net.frozenblock.wilderwild.registry.WWCriteria;
import net.minecraft.advancements.triggers.KilledByArrowTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrow.class)
public class AbstractArrowMixin {

	@Inject(method = "addBubbleParticles", at = @At("HEAD"), cancellable = true)
	private void wilderWild$cancelBubbleParticlesIfHookedHarpoon(Vec3 position, CallbackInfo info) {
		if (AbstractArrow.class.cast(this) instanceof Harpoon harpoon && harpoon.getHookedIn() != null) info.cancel();
	}

	@WrapWithCondition(
		method = "onHitEntity",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/LivingEntity;setArrowCount(I)V"
		)
	)
	public boolean wilderWild$skipIncreaseArrowCountIfHarpoon(LivingEntity instance, int count) {
		return !(AbstractArrow.class.cast(this) instanceof Harpoon);
	}

	@Inject(
		method = "onHitEntity",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/Entity;hurtOrSimulate(Lnet/minecraft/world/damagesource/DamageSource;F)Z"
		)
	)
	public void wilderWild$setDealtDamageIfHarpoon(EntityHitResult hitResult, CallbackInfo info) {
		if (AbstractArrow.class.cast(this) instanceof Harpoon harpoon) harpoon.setDealtDamage(true);
	}

	@WrapOperation(
		method = "onHitEntity",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/advancements/triggers/KilledByArrowTrigger;trigger(Lnet/minecraft/server/level/ServerPlayer;Ljava/util/Collection;Lnet/minecraft/world/item/ItemStack;)V"
		)
	)
	public void wilderWild$useHarpoonCriteriaTrigger(
		KilledByArrowTrigger instance, ServerPlayer player, Collection<Entity> victims, @Nullable ItemStack firedByWeapon, Operation<Void> original
	) {
		if (AbstractArrow.class.cast(this) instanceof Harpoon) {
			WWCriteria.KILLED_BY_HARPOON.get().trigger(player, victims, firedByWeapon);
			return;
		}

		original.call(instance, player, victims, firedByWeapon);
	}

	@WrapWithCondition(
		method = "onHitEntity",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/projectile/arrow/AbstractArrow;discard()V"
		)
	)
	public boolean wilderWild$skipDiscardIfHarpoon(AbstractArrow instance) {
		return !(AbstractArrow.class.cast(this) instanceof Harpoon);
	}

	@ModifyExpressionValue(
		method = "onHitEntity",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/phys/Vec3;lengthSqr()D"
		),
		slice = @Slice(
			from = @At(
				value = "FIELD",
				target = "Lnet/minecraft/world/entity/projectile/ProjectileDeflection;REVERSE:Lnet/minecraft/world/entity/projectile/ProjectileDeflection;",
				opcode = Opcodes.GETSTATIC
			)
		)
	)
	public double wilderWild$skipDropOnHitIfHarpoon(double original) {
		return (AbstractArrow.class.cast(this) instanceof Harpoon) ? 0.1D : original;
	}
}
