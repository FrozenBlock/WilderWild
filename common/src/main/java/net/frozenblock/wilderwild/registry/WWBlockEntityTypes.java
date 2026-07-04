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

package net.frozenblock.wilderwild.registry;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.frozenblock.lib.platform.api.registry.FrozenDeferredRegister;
import net.frozenblock.lib.platform.api.registry.FrozenHolder;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.entity.DisplayLanternBlockEntity;
import net.frozenblock.wilderwild.block.entity.GeothermalVentBlockEntity;
import net.frozenblock.wilderwild.block.entity.HangingTendrilBlockEntity;
import net.frozenblock.wilderwild.block.entity.IcicleBlockEntity;
import net.frozenblock.wilderwild.block.entity.ScorchedBlockEntity;
import net.frozenblock.wilderwild.block.entity.StoneChestBlockEntity;
import net.frozenblock.wilderwild.block.entity.TermiteMoundBlockEntity;
import net.frozenblock.wilderwild.references.WWBlockEntityTypeIds;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public final class WWBlockEntityTypes {
	private static final FrozenDeferredRegister<BlockEntityType<?>> REGISTER = FrozenDeferredRegister.create(
		Registries.BLOCK_ENTITY_TYPE,
		WWConstants.MOD_ID
	);

	public static final FrozenHolder<BlockEntityType<?>, BlockEntityType<HangingTendrilBlockEntity>> HANGING_TENDRIL = register(WWBlockEntityTypeIds.HANGING_TENDRIL,
		HangingTendrilBlockEntity::new,
		WWBlocks.HANGING_TENDRIL::get
	);
	public static final FrozenHolder<BlockEntityType<?>, BlockEntityType<TermiteMoundBlockEntity>> TERMITE_MOUND = register(WWBlockEntityTypeIds.TERMITE_MOUND,
		TermiteMoundBlockEntity::new,
		WWBlocks.TERMITE_MOUND::get
	);
	public static final FrozenHolder<BlockEntityType<?>, BlockEntityType<DisplayLanternBlockEntity>> DISPLAY_LANTERN = register(WWBlockEntityTypeIds.DISPLAY_LANTERN,
		DisplayLanternBlockEntity::new,
		WWBlocks.DISPLAY_LANTERN::get
	);
	public static final FrozenHolder<BlockEntityType<?>, BlockEntityType<StoneChestBlockEntity>> STONE_CHEST = register(WWBlockEntityTypeIds.STONE_CHEST,
		StoneChestBlockEntity::new,
		WWBlocks.STONE_CHEST::get
	);
	public static final FrozenHolder<BlockEntityType<?>, BlockEntityType<ScorchedBlockEntity>> SCORCHED_BLOCK = register(WWBlockEntityTypeIds.SCORCHED_BLOCK,
		ScorchedBlockEntity::new,
		WWBlocks.SCORCHED_SAND::get, WWBlocks.SCORCHED_RED_SAND::get
	);
	public static final FrozenHolder<BlockEntityType<?>, BlockEntityType<GeothermalVentBlockEntity>> GEOTHERMAL_VENT = register(WWBlockEntityTypeIds.GEOTHERMAL_VENT,
		GeothermalVentBlockEntity::new,
		WWBlocks.GEOTHERMAL_VENT::get
	);
	public static final FrozenHolder<BlockEntityType<?>, BlockEntityType<IcicleBlockEntity>> ICICLE = register(WWBlockEntityTypeIds.ICICLE,
		IcicleBlockEntity::new,
		WWBlocks.ICICLE::get
	);

	static {
		REGISTER.register();
	}

	public static void init() {}

	@SafeVarargs
	private static <T extends BlockEntity> FrozenHolder<BlockEntityType<?>, BlockEntityType<T>> register(ResourceKey<BlockEntityType<?>> id, BlockEntityType.BlockEntitySupplier<T> builder, Supplier<Block>... blocks) {
		return REGISTER.register(id, () -> new BlockEntityType<>(builder, Arrays.stream(blocks).map(Supplier::get).collect(Collectors.toSet())));
	}
}
