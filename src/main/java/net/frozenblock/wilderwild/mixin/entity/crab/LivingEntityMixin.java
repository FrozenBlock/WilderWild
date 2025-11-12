/*
 * Copyright 2025 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.entity.crab;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.frozenblock.wilderwild.entity.Crab;
import net.frozenblock.wilderwild.registry.WWItems;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

	@ModifyExpressionValue(
		method = "travelInWater",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/LivingEntity;onClimbable()Z"
		)
	)
	public boolean wilderWild$crabTravel(boolean original) {
		if (LivingEntity.class.cast(this) instanceof Crab crab) return crab.isCrabClimbing();
		return original;
	}

	@ModifyExpressionValue(
		method = "handleRelativeFrictionAndCalculateMovement",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/LivingEntity;onClimbable()Z"
		)
	)
	public boolean wilderWild$crabHandleRelativeFrictionAndCalculateMovement(boolean original) {
		if (LivingEntity.class.cast(this) instanceof Crab crab) return crab.isCrabClimbing();
		return original;
	}

	@Unique
	private boolean wilderWild$wasCrabClawReachEnabled = false;
	@Unique
	private boolean wilderWild$wasCrabClawAttackReachEnabled = false;

	@WrapOperation(
		method = "collectEquipmentChanges",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/LivingEntity;equipmentHasChanged(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)Z"
		)
	)
	private boolean wilderWild$markCrabClawAsChangedUponConfigChange(
		LivingEntity instance, ItemStack oldStack, ItemStack newStack, Operation<Boolean> original
	) {
		final boolean changed = original.call(instance, oldStack, newStack);
		if (changed) return true;
		if (!oldStack.is(WWItems.CRAB_CLAW) && !newStack.is(WWItems.CRAB_CLAW)) return false;

		final boolean reachChanged = this.wilderWild$wasCrabClawReachEnabled != WWEntityConfig.CRAB_CLAW_GIVES_REACH;
		final boolean attackChanged = this.wilderWild$wasCrabClawAttackReachEnabled != WWEntityConfig.REACH_AFFECTS_ATTACK;
		this.wilderWild$wasCrabClawReachEnabled = WWEntityConfig.CRAB_CLAW_GIVES_REACH;
		this.wilderWild$wasCrabClawAttackReachEnabled = WWEntityConfig.REACH_AFFECTS_ATTACK;

		if (!WWEntityConfig.CRAB_CLAW_GIVES_REACH || attackChanged) {
			final AttributeMap map = instance.getAttributes();
			Crab.ATTRIBUTE_MODIFIERS.forEach(EquipmentSlotGroup.HAND, (attribute, modifier) -> {
				final AttributeInstance attributeInstance = map.getInstance(attribute);
				if (attributeInstance != null) attributeInstance.removeModifier(modifier.id());
			});
		}
		return reachChanged || attackChanged;
	}

}
