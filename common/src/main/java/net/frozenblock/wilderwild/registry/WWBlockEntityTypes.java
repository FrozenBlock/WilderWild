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

import java.util.List;
import net.frozenblock.lib.platform.api.registry.DeferredBlockEntityType;
import net.frozenblock.lib.platform.api.registry.DeferredRegister;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.entity.DisplayLanternBlockEntity;
import net.frozenblock.wilderwild.block.entity.GeothermalVentBlockEntity;
import net.frozenblock.wilderwild.block.entity.HangingTendrilBlockEntity;
import net.frozenblock.wilderwild.block.entity.IcicleBlockEntity;
import net.frozenblock.wilderwild.block.entity.ScorchedBlockEntity;
import net.frozenblock.wilderwild.block.entity.StoneChestBlockEntity;
import net.frozenblock.wilderwild.block.entity.TermiteMoundBlockEntity;
import net.frozenblock.wilderwild.references.WWBlockEntityTypeIds;

public final class WWBlockEntityTypes {
	private static final DeferredRegister.BlockEntities REGISTER = DeferredRegister.createBlockEntities(WWConstants.MOD_ID);

	public static final DeferredBlockEntityType<HangingTendrilBlockEntity> HANGING_TENDRIL = REGISTER.register(WWBlockEntityTypeIds.HANGING_TENDRIL,
		HangingTendrilBlockEntity::new,
		WWBlocks.HANGING_TENDRIL
	);
	public static final DeferredBlockEntityType<TermiteMoundBlockEntity> TERMITE_MOUND = REGISTER.register(WWBlockEntityTypeIds.TERMITE_MOUND,
		TermiteMoundBlockEntity::new,
		WWBlocks.TERMITE_MOUND
	);
	public static final DeferredBlockEntityType<DisplayLanternBlockEntity> DISPLAY_LANTERN = REGISTER.register(WWBlockEntityTypeIds.DISPLAY_LANTERN,
		DisplayLanternBlockEntity::new,
		WWBlocks.DISPLAY_LANTERN
	);
	public static final DeferredBlockEntityType<StoneChestBlockEntity> STONE_CHEST = REGISTER.register(WWBlockEntityTypeIds.STONE_CHEST,
		StoneChestBlockEntity::new,
		WWBlocks.STONE_CHEST
	);
	public static final DeferredBlockEntityType<ScorchedBlockEntity> SCORCHED_BLOCK = REGISTER.register(WWBlockEntityTypeIds.SCORCHED_BLOCK,
		ScorchedBlockEntity::new,
		List.of(WWBlocks.SCORCHED_SAND, WWBlocks.SCORCHED_RED_SAND)
	);
	public static final DeferredBlockEntityType<GeothermalVentBlockEntity> GEOTHERMAL_VENT = REGISTER.register(WWBlockEntityTypeIds.GEOTHERMAL_VENT,
		GeothermalVentBlockEntity::new,
		WWBlocks.GEOTHERMAL_VENT
	);
	public static final DeferredBlockEntityType<IcicleBlockEntity> ICICLE = REGISTER.register(WWBlockEntityTypeIds.ICICLE,
		IcicleBlockEntity::new,
		WWBlocks.ICICLE
	);

	static {
		REGISTER.register();
	}

	public static void init() {}

	private WWBlockEntityTypes() {}
}
