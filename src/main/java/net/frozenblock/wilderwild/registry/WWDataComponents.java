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

package net.frozenblock.wilderwild.registry;

import java.util.List;
import java.util.function.UnaryOperator;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.entity.DisplayLanternBlockEntity;
import net.frozenblock.wilderwild.entity.variant.butterfly.ButterflyVariant;
import net.frozenblock.wilderwild.entity.variant.crab.CrabVariant;
import net.frozenblock.wilderwild.entity.variant.firefly.FireflyColor;
import net.frozenblock.wilderwild.entity.variant.jellyfish.JellyfishVariant;
import net.frozenblock.wilderwild.entity.variant.moobloom.MoobloomVariant;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.item.component.CustomData;

public final class WWDataComponents {
	public static final DataComponentType<List<DisplayLanternBlockEntity.Occupant>> FIREFLIES = register(
		"fireflies",
		builder -> builder.persistent(DisplayLanternBlockEntity.Occupant.LIST_CODEC)
			.networkSynchronized(DisplayLanternBlockEntity.Occupant.STREAM_CODEC.apply(ByteBufCodecs.list()))
	);
	public static final DataComponentType<CustomData> BOTTLE_ENTITY_DATA = register(
		"bottle_entity_data",
		builder -> builder.persistent(CustomData.CODEC)
			.networkSynchronized(CustomData.STREAM_CODEC)
	);

	public static final DataComponentType<Holder<FireflyColor>> FIREFLY_COLOR = register(
		"firefly/color",
		builder -> builder.persistent(FireflyColor.CODEC).networkSynchronized(FireflyColor.STREAM_CODEC)
	);
	public static final DataComponentType<Holder<ButterflyVariant>> BUTTERFLY_VARIANT = register(
		"butterfly/variant",
		builder -> builder.persistent(ButterflyVariant.CODEC).networkSynchronized(ButterflyVariant.STREAM_CODEC)
	);
	public static final DataComponentType<Holder<CrabVariant>> CRAB_VARIANT = register(
		"crab/variant",
		builder -> builder.persistent(CrabVariant.CODEC).networkSynchronized(CrabVariant.STREAM_CODEC)
	);
	public static final DataComponentType<Holder<JellyfishVariant>> JELLYFISH_VARIANT = register(
		"jellyfish/variant",
		builder -> builder.persistent(JellyfishVariant.CODEC).networkSynchronized(JellyfishVariant.STREAM_CODEC)
	);
	public static final DataComponentType<Holder<MoobloomVariant>> MOOBLOOM_VARIANT = register(
		"moobloom/variant",
		builder -> builder.persistent(MoobloomVariant.CODEC).networkSynchronized(MoobloomVariant.STREAM_CODEC)
	);

	public static void init() {}

	private static <T> DataComponentType<T> register(String id, UnaryOperator<DataComponentType.Builder<T>> unaryOperator) {
		return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, WWConstants.id(id), unaryOperator.apply(DataComponentType.builder()).build());
	}
}
