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

import java.util.Set;
import net.frozenblock.lib.platform.api.registry.FrozenHolder;
import net.frozenblock.wilderwild.block.ScorchedBlock;
import net.frozenblock.wilderwild.block.entity.DisplayLanternBlockEntity;
import net.frozenblock.wilderwild.block.entity.GeothermalVentBlockEntity;
import net.frozenblock.wilderwild.block.entity.HangingTendrilBlockEntity;
import net.frozenblock.wilderwild.block.entity.IcicleBlockEntity;
import net.frozenblock.wilderwild.block.entity.ScorchedBlockEntity;
import net.frozenblock.wilderwild.block.entity.StoneChestBlockEntity;
import net.frozenblock.wilderwild.block.entity.TermiteMoundBlockEntity;
import net.frozenblock.wilderwild.references.WWBlockEntityTypeIds;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public final class WWBlockEntityTypes {
	public static final BlockEntityType<HangingTendrilBlockEntity> HANGING_TENDRIL = register(WWBlockEntityTypeIds.HANGING_TENDRIL,
		HangingTendrilBlockEntity::new,
		WWBlocks.HANGING_TENDRIL.get()
	);
	public static final BlockEntityType<TermiteMoundBlockEntity> TERMITE_MOUND = register(WWBlockEntityTypeIds.TERMITE_MOUND,
		TermiteMoundBlockEntity::new,
		WWBlocks.TERMITE_MOUND.get()
	);
	public static final BlockEntityType<DisplayLanternBlockEntity> DISPLAY_LANTERN = register(WWBlockEntityTypeIds.DISPLAY_LANTERN,
		DisplayLanternBlockEntity::new,
		WWBlocks.DISPLAY_LANTERN.get()
	);
	public static final BlockEntityType<StoneChestBlockEntity> STONE_CHEST = register(WWBlockEntityTypeIds.STONE_CHEST,
		StoneChestBlockEntity::new,
		WWBlocks.STONE_CHEST.get()
	);
	public static final BlockEntityType<ScorchedBlockEntity> SCORCHED_BLOCK = register(WWBlockEntityTypeIds.SCORCHED_BLOCK,
		ScorchedBlockEntity::new,
		WWBlocks.SCORCHED_SAND.get(), WWBlocks.SCORCHED_RED_SAND.get()
	);
	public static final BlockEntityType<GeothermalVentBlockEntity> GEOTHERMAL_VENT = register(WWBlockEntityTypeIds.GEOTHERMAL_VENT,
		GeothermalVentBlockEntity::new,
		WWBlocks.GEOTHERMAL_VENT.get()
	);
	public static final BlockEntityType<IcicleBlockEntity> ICICLE = register(WWBlockEntityTypeIds.ICICLE,
		IcicleBlockEntity::new,
		WWBlocks.ICICLE.get()
	);

	public static void init() {}

	private static <T extends BlockEntity> BlockEntityType<T> register(ResourceKey<BlockEntityType<?>> id, BlockEntityType.BlockEntitySupplier<T> builder, Block... blocks) {
		return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id, new BlockEntityType<>(builder, Set.of(blocks)));
	}
}
