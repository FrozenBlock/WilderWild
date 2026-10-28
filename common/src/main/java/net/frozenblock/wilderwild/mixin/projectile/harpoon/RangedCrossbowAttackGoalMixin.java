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
import com.llamalad7.mixinextras.sugar.Local;
import java.util.List;
import net.frozenblock.wilderwild.entity.monster.skeleton.RangedHarpoonCrossbowAttackGoal;
import net.frozenblock.wilderwild.registry.WWItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.ai.goal.RangedCrossbowAttackGoal;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ChargedProjectiles;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RangedCrossbowAttackGoal.class)
public abstract class RangedCrossbowAttackGoalMixin<T extends Monster & RangedAttackMob & CrossbowAttackMob> {

	@Shadow
	@Final
	private T mob;

	@ModifyExpressionValue(
		method = "tick",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/world/entity/ai/goal/RangedCrossbowAttackGoal;attackRadiusSqr:F",
			opcode = Opcodes.GETFIELD
		)
	)
	public float wilderWild$useHarpoonAttackRadius(float original) {
		if (RangedCrossbowAttackGoal.class.cast(this) instanceof RangedHarpoonCrossbowAttackGoal harpoonGoal) {
			if (this.mob.isInWater()) return harpoonGoal.getAttackRadiusInWaterSqr();
		}
		return original;
	}

	@Inject(
		method = "tick",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/world/entity/ai/goal/RangedCrossbowAttackGoal;crossbowState:Lnet/minecraft/world/entity/ai/goal/RangedCrossbowAttackGoal$CrossbowState;",
			opcode = Opcodes.PUTFIELD,
			ordinal = 0
		),
		slice = @Slice(
			from = @At(
				value = "INVOKE",
				target = "Lnet/minecraft/world/entity/monster/Monster;releaseUsingItem()V"
			)
		)
	)
	public void wilderWild$setChargedHarpoon(
		CallbackInfo info,
		@Local(name = "useItem") ItemStack useItem
	) {
		if (RangedCrossbowAttackGoal.class.cast(this) instanceof RangedHarpoonCrossbowAttackGoal) {
			if (useItem.is(Items.CROSSBOW)) useItem.set(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.ofNonEmpty(List.of(WWItems.HARPOON.toStack())));
		}
	}
}
