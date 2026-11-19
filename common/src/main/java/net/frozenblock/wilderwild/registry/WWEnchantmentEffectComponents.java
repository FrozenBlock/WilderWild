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

import java.util.List;
import lombok.experimental.UtilityClass;
import net.frozenblock.lib.platform.api.registry.DeferredHolder;
import net.frozenblock.lib.platform.api.registry.DeferredRegister;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.Unit;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.jetbrains.annotations.ApiStatus;

@UtilityClass
public final class WWEnchantmentEffectComponents {
	private static final DeferredRegister<DataComponentType<?>> REGISTER = DeferredRegister.create(
		Registries.ENCHANTMENT_EFFECT_COMPONENT_TYPE,
		WWConstants.MOD_ID
	);

	public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<ConditionalEffect<EnchantmentValueEffect>>>> HARPOON_GRAPPLING_TIME = REGISTER.register(
		"harpoon_grappling_time",
		() -> DataComponentType.<List<ConditionalEffect<EnchantmentValueEffect>>>builder()
			.persistent(
				EnchantmentEffectComponents.validatedListCodec(
					ConditionalEffect.codec(EnchantmentValueEffect.CODEC),
					LootContextParamSets.ENCHANTED_ENTITY
				)
			).build()
	);

	public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<ConditionalEffect<EnchantmentValueEffect>>>> HARPOON_GRAPPLE_STRENGTH = REGISTER.register(
		"harpoon_grapple_strength",
		() -> DataComponentType.<List<ConditionalEffect<EnchantmentValueEffect>>>builder()
			.persistent(
				EnchantmentEffectComponents.validatedListCodec(
					ConditionalEffect.codec(EnchantmentValueEffect.CODEC),
					LootContextParamSets.ENCHANTED_ENTITY
				)
			).build()
	);

	public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<ConditionalEffect<EnchantmentValueEffect>>>> HARPOON_GRAPPLE_MAX_COUNT = REGISTER.register(
		"harpoon_grapple_max_count",
		() -> DataComponentType.<List<ConditionalEffect<EnchantmentValueEffect>>>builder()
			.persistent(
				EnchantmentEffectComponents.validatedListCodec(
					ConditionalEffect.codec(EnchantmentValueEffect.CODEC),
					LootContextParamSets.ENCHANTED_ENTITY
				)
			).build()
	);

	public static final DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> PREVENT_HARPOON_ROPE_SNAP = REGISTER.register(
		"prevent_harpoon_rope_snap",
		() -> DataComponentType.<Unit>builder()
			.persistent(Unit.CODEC)
			.build()
	);

	static {
		REGISTER.register();
	}

	@ApiStatus.Internal
	public static void init() {}
}
