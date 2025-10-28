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

package net.frozenblock.wilderwild.item;

import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.jetbrains.annotations.NotNull;

public class CrabClawItem extends Item {
	private static final double REACH_BOOST = 3D;
	public static final ItemAttributeModifiers MODIFIERS =  makeBlockReachBuilder().build();
	public static final ItemAttributeModifiers MODIFIERS_WITH_ATTACK_REACH = makeBlockReachBuilder()
		.add(
			Attributes.ENTITY_INTERACTION_RANGE,
			new AttributeModifier(
				WWConstants.id("entity_reach_boost"),
				REACH_BOOST,
				AttributeModifier.Operation.ADD_VALUE),
			EquipmentSlotGroup.HAND
		)
		.build();

	public CrabClawItem(@NotNull Properties settings) {
		super(settings);
	}

	private static ItemAttributeModifiers.Builder makeBlockReachBuilder() {
		return ItemAttributeModifiers.builder()
			.add(
				Attributes.BLOCK_INTERACTION_RANGE,
				new AttributeModifier(
					WWConstants.id("block_reach_boost"),
					REACH_BOOST,
					AttributeModifier.Operation.ADD_VALUE),
				EquipmentSlotGroup.HAND
			);
	}

	@Override
	public ItemAttributeModifiers getDefaultAttributeModifiers() {
		if (!WWEntityConfig.CRAB_CLAW_GIVES_REACH) return super.getDefaultAttributeModifiers();
		return WWEntityConfig.REACH_AFFECTS_ATTACK ? MODIFIERS_WITH_ATTACK_REACH : MODIFIERS;
	}
}
