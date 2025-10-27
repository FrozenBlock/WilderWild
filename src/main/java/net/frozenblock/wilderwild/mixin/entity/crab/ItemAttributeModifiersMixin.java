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

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.frozenblock.wilderwild.entity.Crab;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.apache.commons.lang3.function.TriConsumer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import java.util.function.BiConsumer;

@Mixin(ItemAttributeModifiers.class)
public class ItemAttributeModifiersMixin {

	@WrapWithCondition(
		method = {
			"forEach(Lnet/minecraft/world/entity/EquipmentSlot;Ljava/util/function/BiConsumer;)V",
			"forEach(Lnet/minecraft/world/entity/EquipmentSlotGroup;Ljava/util/function/BiConsumer;)V"
		},
		at = @At(
			value = "INVOKE",
			target = "Ljava/util/function/BiConsumer;accept(Ljava/lang/Object;Ljava/lang/Object;)V"
		)
	)
	public boolean wilderWild$fixCrabClawAttributes(BiConsumer instance, Object object1, Object object2) {
		return wilderWild$canAttributeModifierBeUsed(object2);
	}

	@WrapWithCondition(
		method = "forEach(Lnet/minecraft/world/entity/EquipmentSlotGroup;Lorg/apache/commons/lang3/function/TriConsumer;)V",
		at = @At(
			value = "INVOKE",
			target = "Lorg/apache/commons/lang3/function/TriConsumer;accept(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V"
		)
	)
	public boolean wilderWild$fixCrabClawAttributes(TriConsumer instance, Object object1, Object object2, Object object3) {
		return wilderWild$canAttributeModifierBeUsed(object2);
	}

	@Unique
	private static boolean wilderWild$canAttributeModifierBeUsed(Object object) {
		if (object instanceof AttributeModifier attributeModifier) {
			final ResourceLocation id = attributeModifier.id();
			if (id.equals(Crab.BLOCK_REACH_BOOST_MODIFIER_ID)) return WWEntityConfig.CRAB_CLAW_GIVES_REACH;
			if (id.equals(Crab.ENTITY_REACH_BOOST_MODIFIER_ID)) return WWEntityConfig.CRAB_CLAW_GIVES_REACH && WWEntityConfig.REACH_AFFECTS_ATTACK;
		}
		return true;
	}

}
