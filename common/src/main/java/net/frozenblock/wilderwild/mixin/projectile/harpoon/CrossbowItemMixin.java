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

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import java.util.function.Predicate;
import com.llamalad7.mixinextras.sugar.Local;
import net.frozenblock.wilderwild.entity.projectile.arrow.Harpoon;
import net.frozenblock.wilderwild.registry.WWAttachmentTypes;
import net.frozenblock.wilderwild.registry.WWItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CrossbowItem.class)
public class CrossbowItemMixin {

	@ModifyReturnValue(method = "getSupportedHeldProjectiles", at = @At("RETURN"))
	public Predicate<ItemStack> wilderWild$supportHeldHarpoons(Predicate<ItemStack> predicate) {
		return predicate.or(itemStack -> itemStack.is(WWItems.HARPOON.get()));
	}

	@ModifyReturnValue(method = "getAllSupportedProjectiles", at = @At("RETURN"))
	public Predicate<ItemStack> wilderWild$supportAllHarpoons(Predicate<ItemStack> predicate) {
		return predicate.or(itemStack -> itemStack.is(WWItems.HARPOON.get()));
	}

	@Inject(
		method = "use",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/player/Player;getProjectile(Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/world/item/ItemStack;"
		),
		cancellable = true
	)
	public void wilderWild$retractHarpoon(
		Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> info,
		@Local(name = "itemStack") ItemStack itemStack
	) {
		final Harpoon harpoon = EntityReference.get(WWAttachmentTypes.HARPOON.get(player), level, Harpoon.class);
		if (harpoon == null) return;

		if (!level.isClientSide()) {
			final int dmg = harpoon.retrieve(itemStack);
			itemStack.hurtAndBreak(dmg, player, hand.asEquipmentSlot());
		}

		info.setReturnValue(InteractionResult.SUCCESS);
	}

	@ModifyReturnValue(method = "getShootingPower", at = @At("RETURN"))
	private static float wilderWild$getShootingPowerForHarpoon(float original, ChargedProjectiles projectiles) {
		if (projectiles.contains(WWItems.HARPOON.get())) return 1.4F;
		return original;
	}

	@Inject(
		method = "createProjectile",
		at = @At("HEAD"),
		cancellable = true
	)
	public void wilderWild$createHarpoonProjectile(
		Level level,
		LivingEntity shooter,
		ItemStack heldItem,
		ItemStack projectile,
		boolean isCrit,
		CallbackInfoReturnable<Projectile> info
	) {
		if (projectile.is(WWItems.HARPOON.get())) {
			final Harpoon harpoon = new Harpoon(level, shooter, projectile.copyWithCount(1), heldItem);
			harpoon.setSoundEvent(SoundEvents.CROSSBOW_HIT);
			info.setReturnValue(harpoon);
		}
	}

	@ModifyReturnValue(method = "getDurabilityUse", at = @At("RETURN"))
	public int wilderWild$shootingHarpoonUses3Durability(int original, ItemStack projectile) {
		return projectile.is(WWItems.HARPOON.get()) ? 3 : original;
	}
}
