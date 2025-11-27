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

import java.util.Set;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.entity.DisplayLanternBlockEntity;
import net.frozenblock.wilderwild.block.entity.GeyserBlockEntity;
import net.frozenblock.wilderwild.block.entity.HangingTendrilBlockEntity;
import net.frozenblock.wilderwild.block.entity.IcicleBlockEntity;
import net.frozenblock.wilderwild.block.entity.ScorchedBlockEntity;
import net.frozenblock.wilderwild.block.entity.StoneChestBlockEntity;
import net.frozenblock.wilderwild.block.entity.TermiteMoundBlockEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public final class WWBlockEntityTypes {
	private WWBlockEntityTypes() {
		throw new UnsupportedOperationException("WWBlockEntityTypes contains only static declarations.");
	}

	public static void register() {
		WWConstants.logWithModId("Registering BlockEntities for", WWConstants.UNSTABLE_LOGGING);
	}

	private static <T extends BlockEntity> BlockEntityType<T> register(String path, BlockEntityType.BlockEntitySupplier<T> builder, Block... blocks) {
		Util.fetchChoiceType(References.BLOCK_ENTITY, WWConstants.string(path));
		return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, WWConstants.id(path), new BlockEntityType<>(builder, Set.of(blocks)));
	}

	public static final BlockEntityType<HangingTendrilBlockEntity> HANGING_TENDRIL = register(
		"hanging_tendril",
		HangingTendrilBlockEntity::new,
		WWBlocks.HANGING_TENDRIL
	);
	public static final BlockEntityType<TermiteMoundBlockEntity> TERMITE_MOUND = register(
		"termite_mound",
		TermiteMoundBlockEntity::new,
		WWBlocks.TERMITE_MOUND
	);
	public static final BlockEntityType<DisplayLanternBlockEntity> DISPLAY_LANTERN = register(
		"display_lantern",
		DisplayLanternBlockEntity::new,
		WWBlocks.DISPLAY_LANTERN
	);
	public static final BlockEntityType<StoneChestBlockEntity> STONE_CHEST = register(
		"stone_chest",
		StoneChestBlockEntity::new,
		WWBlocks.STONE_CHEST
	);
	public static final BlockEntityType<ScorchedBlockEntity> SCORCHED_BLOCK = register(
		"scorched_block",
		ScorchedBlockEntity::new,
		WWBlocks.SCORCHED_SAND, WWBlocks.SCORCHED_RED_SAND
	);
	public static final BlockEntityType<GeyserBlockEntity> GEYSER = register(
		"geyser",
		GeyserBlockEntity::new,
		WWBlocks.GEYSER
	);
	public static final BlockEntityType<IcicleBlockEntity> ICICLE = register(
		"icicle",
		IcicleBlockEntity::new,
		WWBlocks.ICICLE
	);

}
