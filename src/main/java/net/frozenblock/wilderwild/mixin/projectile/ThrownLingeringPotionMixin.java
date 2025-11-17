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

package net.frozenblock.wilderwild.mixin.projectile;

import net.frozenblock.wilderwild.config.WWItemConfig;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.projectile.ThrownLingeringPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ThrownLingeringPotion.class)
public class ThrownLingeringPotionMixin {

	@Inject(method = "onHitAsPotion", at = @At("HEAD"))
	public void wilderWild$onHitAsPotion(ServerLevel level, ItemStack stack, HitResult hitResult, CallbackInfo info) {
		if (!WWItemConfig.get().projectileLandingSounds.potionLandingSounds) return;
		ThrownLingeringPotion.class.cast(this).playSound(WWSounds.ITEM_POTION_LINGERING, 1F, 1F + (level.getRandom().nextFloat() * 0.2F));
	}

}
