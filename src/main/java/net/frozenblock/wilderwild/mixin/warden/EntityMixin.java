/*
 * Copyright 2025-2026 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.warden;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityFluidInteraction;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.level.material.Fluid;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Entity.class)
public class EntityMixin {

	@WrapWithCondition(
		method = "updateFluidInteraction",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/EntityFluidInteraction;applyCurrentTo(Lnet/minecraft/tags/TagKey;Lnet/minecraft/world/entity/Entity;D)V"
		),
		slice = @Slice(
			to = @At(
				value = "FIELD",
				target = "Lnet/minecraft/world/attribute/EnvironmentAttributes;FAST_LAVA:Lnet/minecraft/world/attribute/EnvironmentAttribute;",
				opcode = Opcodes.GETSTATIC
			)
		)
	)
	public boolean wilderWild$stopWaterFromPushingWardens(EntityFluidInteraction instance, TagKey<Fluid> fluid, Entity entity, double scale) {
		return !WWEntityConfig.WARDEN_SWIMS || (!(entity instanceof Warden));
	}

}
