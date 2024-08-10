/*
 * Copyright 2023-2024 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.registry;

import com.mojang.datafixers.types.Type;
import net.frozenblock.wilderwild.WilderConstants;
import net.frozenblock.wilderwild.block.entity.DisplayLanternBlockEntity;
import net.frozenblock.wilderwild.block.entity.GeyserBlockEntity;
import net.frozenblock.wilderwild.block.entity.HangingTendrilBlockEntity;
import net.frozenblock.wilderwild.block.entity.ScorchedBlockEntity;
import net.frozenblock.wilderwild.block.entity.StoneChestBlockEntity;
import net.frozenblock.wilderwild.block.entity.TermiteMoundBlockEntity;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;

public final class RegisterBlockEntities {
	private RegisterBlockEntities() {
		throw new UnsupportedOperationException("RegisterBlockEntities contains only static declarations.");
	}

	public static void register() {
		WilderConstants.logWithModId("Registering BlockEntities for", WilderConstants.UNSTABLE_LOGGING);
	}

	@NotNull
	private static <T extends BlockEntity> BlockEntityType<T> register(@NotNull String path, BlockEntityType.@NotNull Builder<T> builder) {
		Type<?> type = Util.fetchChoiceType(References.BLOCK_ENTITY, path);
		return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, WilderConstants.id(path), builder.build(type));
	}

	public static final BlockEntityType<HangingTendrilBlockEntity> HANGING_TENDRIL = register("hanging_tendril", BlockEntityType.Builder.of(HangingTendrilBlockEntity::new, RegisterBlocks.HANGING_TENDRIL));
	public static final BlockEntityType<TermiteMoundBlockEntity> TERMITE_MOUND = register("termite_mound", BlockEntityType.Builder.of(TermiteMoundBlockEntity::new, RegisterBlocks.TERMITE_MOUND));
	public static final BlockEntityType<DisplayLanternBlockEntity> DISPLAY_LANTERN = register("display_lantern", BlockEntityType.Builder.of(DisplayLanternBlockEntity::new, RegisterBlocks.DISPLAY_LANTERN));
	public static final BlockEntityType<StoneChestBlockEntity> STONE_CHEST = register("stone_chest", BlockEntityType.Builder.of(StoneChestBlockEntity::new, RegisterBlocks.STONE_CHEST));
	public static final BlockEntityType<ScorchedBlockEntity> SCORCHED_BLOCK = register("scorched_block", BlockEntityType.Builder.of(ScorchedBlockEntity::new, RegisterBlocks.SCORCHED_SAND, RegisterBlocks.SCORCHED_RED_SAND));
	public static final BlockEntityType<GeyserBlockEntity> GEYSER = register("geyser", BlockEntityType.Builder.of(GeyserBlockEntity::new, RegisterBlocks.GEYSER));

}
