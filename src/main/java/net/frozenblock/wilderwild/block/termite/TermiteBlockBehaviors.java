/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.block.termite;

import java.util.List;
import java.util.Optional;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class TermiteBlockBehaviors {

	private static @NotNull ResourceKey<TermiteBlockBehavior> createKey(String string) {
		return ResourceKey.create(WilderWildRegistries.TERMITE_BLOCK_BEHAVIOR, WWConstants.id(string));
	}

	public static void register(
		@NotNull BootstrapContext<TermiteBlockBehavior> bootstrapContext,
		ResourceKey<TermiteBlockBehavior> resourceKey,
		Block edibleBlock,
		@Nullable Block outputBlock,
		boolean copyProperties,
		boolean naturalTermite,
		boolean playerTermite,
		@Nullable SoundEvent eatSound
	) {
		bootstrapContext.register(
			resourceKey,
			new TermiteBlockBehavior(
				HolderSet.direct(BuiltInRegistries.BLOCK.wrapAsHolder(edibleBlock)),
				Optional.ofNullable(outputBlock),
				copyProperties,
				naturalTermite,
				playerTermite,
				Optional.ofNullable(eatSound)
			)
		);
	}

	public static void register(
		@NotNull BootstrapContext<TermiteBlockBehavior> bootstrapContext,
		ResourceKey<TermiteBlockBehavior> resourceKey,
		TagKey<Block> edibleBlocks,
		@Nullable Block outputBlock,
		boolean copyProperties,
		boolean naturalTermite,
		boolean playerTermite,
		@Nullable SoundEvent eatSound
	) {
		bootstrapContext.register(
			resourceKey,
			new TermiteBlockBehavior(
				bootstrapContext.lookup(Registries.BLOCK).getOrThrow(edibleBlocks),
				Optional.ofNullable(outputBlock),
				copyProperties,
				naturalTermite,
				playerTermite,
				Optional.ofNullable(eatSound)
			)
		);
	}

	private static void registerPlayerOnly(
		@NotNull BootstrapContext<TermiteBlockBehavior> bootstrapContext,
		Block edibleBlock,
		@NotNull Block outputBlock
	) {
		ResourceLocation edibleBlockLocation = BuiltInRegistries.BLOCK.getKey(edibleBlock);
		register(
			bootstrapContext,
			createKey(edibleBlockLocation.getPath()),
			edibleBlock,
			outputBlock,
			true,
			false,
			true,
			null
		);
	}

	private static void registerNaturalAndPlayer(
		@NotNull BootstrapContext<TermiteBlockBehavior> bootstrapContext,
		Block edibleBlock,
		@NotNull Block outputBlock
	) {
		ResourceLocation edibleBlockLocation = BuiltInRegistries.BLOCK.getKey(edibleBlock);
		register(
			bootstrapContext,
			createKey(edibleBlockLocation.getPath()),
			edibleBlock,
			outputBlock,
			true,
			true,
			true,
			null
		);
	}

	private static void registerHollowed(
		@NotNull BootstrapContext<TermiteBlockBehavior> bootstrapContext,
		Block edibleBlock,
		@NotNull Block outputBlock
	) {
		ResourceLocation edibleBlockLocation = BuiltInRegistries.BLOCK.getKey(edibleBlock);
		register(
			bootstrapContext,
			createKey(edibleBlockLocation.getPath()),
			edibleBlock,
			outputBlock,
			true,
			false,
			true,
			WWSounds.LOG_HOLLOWED
		);
	}

	private static void registerBreakable(
		@NotNull BootstrapContext<TermiteBlockBehavior> bootstrapContext,
		Block edibleBlock
	) {
		ResourceLocation edibleBlockLocation = BuiltInRegistries.BLOCK.getKey(edibleBlock);
		register(
			bootstrapContext,
			createKey(edibleBlockLocation.getPath()),
			edibleBlock,
			null,
			false,
			false,
			true,
			null
		);
	}

	private static void registerBreakable(
		@NotNull BootstrapContext<TermiteBlockBehavior> bootstrapContext,
		String name,
		TagKey<Block> edibleBlocks
	) {
		register(
			bootstrapContext,
			createKey(name),
			edibleBlocks,
			null,
			false,
			false,
			true,
			null
		);
	}

	public static Optional<Holder<TermiteBlockBehavior>> getTermiteBlockBehavior(@NotNull RegistryAccess registryAccess, Block edibleBlock, boolean isNatural) {
		Registry<TermiteBlockBehavior> registry = registryAccess.lookupOrThrow(WilderWildRegistries.TERMITE_BLOCK_BEHAVIOR);
		List<Holder.Reference<TermiteBlockBehavior>> behaviors = registry.listElements()
			.filter(reference -> {
				TermiteBlockBehavior termiteBlockBehavior = reference.value();
				if (termiteBlockBehavior.getEdibleBlocks().contains(edibleBlock.builtInRegistryHolder())) {
					return isNatural ? termiteBlockBehavior.naturalTermiteUsable() : termiteBlockBehavior.playerPlacedTermiteUsable();
				}
				return false;
			})
			.toList();

		if (!behaviors.isEmpty()) {
			return Optional.of(behaviors.getFirst());
		}
		return Optional.empty();
	}

	public static void bootstrap(BootstrapContext<TermiteBlockBehavior> bootstrapContext) {
		// PLAYER-PLACED
		registerNaturalAndPlayer(bootstrapContext, Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG);
		registerHollowed(bootstrapContext, Blocks.STRIPPED_ACACIA_LOG, WWBlocks.STRIPPED_HOLLOWED_ACACIA_LOG);
		registerPlayerOnly(bootstrapContext, WWBlocks.HOLLOWED_ACACIA_LOG, WWBlocks.STRIPPED_HOLLOWED_ACACIA_LOG);
		registerNaturalAndPlayer(bootstrapContext, Blocks.ACACIA_WOOD, Blocks.STRIPPED_ACACIA_WOOD);

		registerNaturalAndPlayer(bootstrapContext, Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG);
		registerHollowed(bootstrapContext, Blocks.STRIPPED_BIRCH_LOG, WWBlocks.STRIPPED_HOLLOWED_BIRCH_LOG);
		registerPlayerOnly(bootstrapContext, WWBlocks.HOLLOWED_BIRCH_LOG, WWBlocks.STRIPPED_HOLLOWED_BIRCH_LOG);
		registerNaturalAndPlayer(bootstrapContext, Blocks.BIRCH_WOOD, Blocks.STRIPPED_BIRCH_WOOD);

		registerNaturalAndPlayer(bootstrapContext, Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG);
		registerHollowed(bootstrapContext, Blocks.STRIPPED_OAK_LOG, WWBlocks.STRIPPED_HOLLOWED_OAK_LOG);
		registerPlayerOnly(bootstrapContext, WWBlocks.HOLLOWED_OAK_LOG, WWBlocks.STRIPPED_HOLLOWED_OAK_LOG);
		registerNaturalAndPlayer(bootstrapContext, Blocks.OAK_WOOD, Blocks.STRIPPED_OAK_WOOD);

		registerNaturalAndPlayer(bootstrapContext, Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG);
		registerHollowed(bootstrapContext, Blocks.STRIPPED_DARK_OAK_LOG, WWBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG);
		registerPlayerOnly(bootstrapContext, WWBlocks.HOLLOWED_DARK_OAK_LOG, WWBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG);
		registerNaturalAndPlayer(bootstrapContext, Blocks.DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_WOOD);

		registerNaturalAndPlayer(bootstrapContext, Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG);
		registerHollowed(bootstrapContext, Blocks.STRIPPED_JUNGLE_LOG, WWBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG);
		registerPlayerOnly(bootstrapContext, WWBlocks.HOLLOWED_JUNGLE_LOG, WWBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG);
		registerNaturalAndPlayer(bootstrapContext, Blocks.JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_WOOD);

		registerNaturalAndPlayer(bootstrapContext, Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG);
		registerHollowed(bootstrapContext, Blocks.STRIPPED_SPRUCE_LOG, WWBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG);
		registerPlayerOnly(bootstrapContext, WWBlocks.HOLLOWED_SPRUCE_LOG, WWBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG);
		registerNaturalAndPlayer(bootstrapContext, Blocks.SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_WOOD);

		registerNaturalAndPlayer(bootstrapContext, Blocks.MANGROVE_LOG, Blocks.STRIPPED_MANGROVE_LOG);
		registerHollowed(bootstrapContext, Blocks.STRIPPED_MANGROVE_LOG, WWBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG);
		registerPlayerOnly(bootstrapContext, WWBlocks.HOLLOWED_MANGROVE_LOG, WWBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG);
		registerNaturalAndPlayer(bootstrapContext, Blocks.MANGROVE_WOOD, Blocks.STRIPPED_MANGROVE_WOOD);

		registerNaturalAndPlayer(bootstrapContext, Blocks.CHERRY_LOG, Blocks.STRIPPED_CHERRY_LOG);
		registerHollowed(bootstrapContext, Blocks.STRIPPED_CHERRY_LOG, WWBlocks.STRIPPED_HOLLOWED_CHERRY_LOG);
		registerPlayerOnly(bootstrapContext, WWBlocks.HOLLOWED_CHERRY_LOG, WWBlocks.STRIPPED_HOLLOWED_CHERRY_LOG);
		registerNaturalAndPlayer(bootstrapContext, Blocks.CHERRY_WOOD, Blocks.STRIPPED_CHERRY_WOOD);

		registerNaturalAndPlayer(bootstrapContext, WWBlocks.BAOBAB_LOG, WWBlocks.STRIPPED_BAOBAB_LOG);
		registerHollowed(bootstrapContext, WWBlocks.STRIPPED_BAOBAB_LOG, WWBlocks.STRIPPED_HOLLOWED_BAOBAB_LOG);
		registerPlayerOnly(bootstrapContext, WWBlocks.HOLLOWED_BAOBAB_LOG, WWBlocks.STRIPPED_HOLLOWED_BAOBAB_LOG);
		registerNaturalAndPlayer(bootstrapContext, WWBlocks.BAOBAB_WOOD, WWBlocks.STRIPPED_BAOBAB_WOOD);

		registerNaturalAndPlayer(bootstrapContext, WWBlocks.WILLOW_LOG, WWBlocks.STRIPPED_WILLOW_LOG);
		registerHollowed(bootstrapContext, WWBlocks.STRIPPED_WILLOW_LOG, WWBlocks.STRIPPED_HOLLOWED_WILLOW_LOG);
		registerPlayerOnly(bootstrapContext, WWBlocks.HOLLOWED_WILLOW_LOG, WWBlocks.STRIPPED_HOLLOWED_WILLOW_LOG);
		registerNaturalAndPlayer(bootstrapContext, WWBlocks.WILLOW_WOOD, WWBlocks.STRIPPED_WILLOW_WOOD);

		registerNaturalAndPlayer(bootstrapContext, WWBlocks.CYPRESS_LOG, WWBlocks.STRIPPED_CYPRESS_LOG);
		registerHollowed(bootstrapContext, WWBlocks.STRIPPED_CYPRESS_LOG, WWBlocks.STRIPPED_HOLLOWED_CYPRESS_LOG);
		registerPlayerOnly(bootstrapContext, WWBlocks.HOLLOWED_CYPRESS_LOG, WWBlocks.STRIPPED_HOLLOWED_CYPRESS_LOG);
		registerNaturalAndPlayer(bootstrapContext, WWBlocks.CYPRESS_WOOD, WWBlocks.STRIPPED_CYPRESS_WOOD);

		registerNaturalAndPlayer(bootstrapContext, WWBlocks.PALM_LOG, WWBlocks.STRIPPED_PALM_LOG);
		registerHollowed(bootstrapContext, WWBlocks.STRIPPED_PALM_LOG, WWBlocks.STRIPPED_HOLLOWED_PALM_LOG);
		registerPlayerOnly(bootstrapContext, WWBlocks.HOLLOWED_PALM_LOG, WWBlocks.STRIPPED_HOLLOWED_PALM_LOG);
		registerNaturalAndPlayer(bootstrapContext, WWBlocks.PALM_WOOD, WWBlocks.STRIPPED_PALM_WOOD);

		registerNaturalAndPlayer(bootstrapContext, WWBlocks.MAPLE_LOG, WWBlocks.STRIPPED_MAPLE_LOG);
		registerHollowed(bootstrapContext, WWBlocks.STRIPPED_MAPLE_LOG, WWBlocks.STRIPPED_HOLLOWED_MAPLE_LOG);
		registerPlayerOnly(bootstrapContext, WWBlocks.HOLLOWED_MAPLE_LOG, WWBlocks.STRIPPED_HOLLOWED_MAPLE_LOG);
		registerNaturalAndPlayer(bootstrapContext, WWBlocks.MAPLE_WOOD, WWBlocks.STRIPPED_MAPLE_WOOD);

		registerNaturalAndPlayer(bootstrapContext, Blocks.PALE_OAK_LOG, Blocks.STRIPPED_PALE_OAK_LOG);
		registerHollowed(bootstrapContext, Blocks.STRIPPED_PALE_OAK_LOG, WWBlocks.STRIPPED_HOLLOWED_PALE_OAK_LOG);
		registerPlayerOnly(bootstrapContext, WWBlocks.HOLLOWED_PALE_OAK_LOG, WWBlocks.STRIPPED_HOLLOWED_PALE_OAK_LOG);
		registerNaturalAndPlayer(bootstrapContext, Blocks.PALE_OAK_WOOD, Blocks.STRIPPED_PALE_OAK_WOOD);

		registerPlayerOnly(bootstrapContext, WWBlocks.BUSH, Blocks.DEAD_BUSH);

		registerBreakable(bootstrapContext, "leaves", BlockTags.LEAVES);
		registerBreakable(bootstrapContext, "leaf_litters", WWBlockTags.LEAF_LITTERS);
		registerBreakable(bootstrapContext, "stripped_hollowed_logs", WWBlockTags.STRIPPED_HOLLOWED_LOGS);
		registerBreakable(bootstrapContext, Blocks.BAMBOO);
		registerBreakable(bootstrapContext, Blocks.DEAD_BUSH);
	}
}
