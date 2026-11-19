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

package net.frozenblock.wilderwild.registry;

import lombok.experimental.UtilityClass;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.tag.WWEnchantmentTags;
import net.frozenblock.wilderwild.tag.WWItemTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.AddValue;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
@UtilityClass
public final class WWEnchantments {
	public static final ResourceKey<Enchantment> GRAPPLING = createKey("grappling");
	public static final ResourceKey<Enchantment> TENSION = createKey("tension");

	public static void bootstrap(BootstrapContext<Enchantment> context) {
		final HolderGetter<Enchantment> enchantments = context.lookup(Registries.ENCHANTMENT);
		final HolderGetter<Item> items = context.lookup(Registries.ITEM);

		register(
			context,
			GRAPPLING,
			Enchantment.enchantment(
				Enchantment.definition(
					items.getOrThrow(WWItemTags.HARPOON_ENCHANTABLE),
					2,
					3,
					Enchantment.dynamicCost(15, 9),
					Enchantment.dynamicCost(65, 9),
					4,
					EquipmentSlotGroup.MAINHAND
				)
			)
			.exclusiveWith(enchantments.getOrThrow(WWEnchantmentTags.HARPOON_EXCLUSIVE))
			.withEffect(WWEnchantmentEffectComponents.HARPOON_GRAPPLING_TIME.get(), new AddValue(LevelBasedValue.perLevel(0.58333F)))
			.withEffect(WWEnchantmentEffectComponents.HARPOON_GRAPPLE_STRENGTH.get(), new AddValue(LevelBasedValue.perLevel(0.0075F)))
			.withEffect(WWEnchantmentEffectComponents.HARPOON_GRAPPLE_MAX_COUNT.get(), new AddValue(LevelBasedValue.perLevel(2F, 1F)))
		);
	}

	private static void register(BootstrapContext<Enchantment> context, ResourceKey<Enchantment> key, Enchantment.Builder builder) {
		context.register(key, builder.build(key.identifier()));
	}

	private static ResourceKey<Enchantment> createKey(String name) {
		return ResourceKey.create(Registries.ENCHANTMENT, WWConstants.id(name));
	}
}
