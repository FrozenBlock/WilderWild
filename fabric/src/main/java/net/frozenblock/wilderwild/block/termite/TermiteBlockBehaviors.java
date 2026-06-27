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

package net.frozenblock.wilderwild.block.termite;

import java.util.List;
import java.util.Optional;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.frozenblock.wilderwild.tag.WWBlockItemTags;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;

public final class TermiteBlockBehaviors {

	private static ResourceKey<TermiteBlockBehavior> createKey(String string) {
		return ResourceKey.create(WilderWildRegistries.TERMITE_BLOCK_BEHAVIOR, WWConstants.id(string));
	}

	public static void register(
		BootstrapContext<TermiteBlockBehavior> context,
		ResourceKey<TermiteBlockBehavior> key,
		Block edibleBlock,
		@Nullable Block outputBlock,
		boolean copyProperties,
		boolean naturalTermite,
		boolean playerTermite,
		@Nullable SoundEvent eatSound
	) {
		context.register(
			key,
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
		BootstrapContext<TermiteBlockBehavior> context,
		ResourceKey<TermiteBlockBehavior> key,
		TagKey<Block> edibleBlocks,
		@Nullable Block outputBlock,
		boolean copyProperties,
		boolean naturalTermite,
		boolean playerTermite,
		@Nullable SoundEvent eatSound
	) {
		context.register(
			key,
			new TermiteBlockBehavior(
				context.lookup(Registries.BLOCK).getOrThrow(edibleBlocks),
				Optional.ofNullable(outputBlock),
				copyProperties,
				naturalTermite,
				playerTermite,
				Optional.ofNullable(eatSound)
			)
		);
	}

	private static void registerPlayerOnly(
		BootstrapContext<TermiteBlockBehavior> context,
		Block edibleBlock,
		Block outputBlock
	) {
		final Identifier edibleBlockId = BuiltInRegistries.BLOCK.getKey(edibleBlock);
		register(
			context,
			createKey(edibleBlockId.getPath()),
			edibleBlock,
			outputBlock,
			true,
			false,
			true,
			null
		);
	}

	private static void registerNaturalAndPlayer(
		BootstrapContext<TermiteBlockBehavior> context,
		Block edibleBlock,
		Block outputBlock
	) {
		final Identifier edibleBlockId = BuiltInRegistries.BLOCK.getKey(edibleBlock);
		register(
			context,
			createKey(edibleBlockId.getPath()),
			edibleBlock,
			outputBlock,
			true,
			true,
			true,
			null
		);
	}

	private static void registerHollowed(
		BootstrapContext<TermiteBlockBehavior> context,
		Block edibleBlock,
		Block outputBlock
	) {
		final Identifier edibleBlockId = BuiltInRegistries.BLOCK.getKey(edibleBlock);
		register(
			context,
			createKey(edibleBlockId.getPath()),
			edibleBlock,
			outputBlock,
			true,
			false,
			true,
			WWSounds.LOG_HOLLOWED
		);
	}

	private static void registerBreakable(
		BootstrapContext<TermiteBlockBehavior> context,
		Block edibleBlock
	) {
		final Identifier edibleBlockId = BuiltInRegistries.BLOCK.getKey(edibleBlock);
		register(
			context,
			createKey(edibleBlockId.getPath()),
			edibleBlock,
			null,
			false,
			false,
			true,
			null
		);
	}

	private static void registerBreakable(
		BootstrapContext<TermiteBlockBehavior> context,
		String name,
		TagKey<Block> edibleBlocks
	) {
		register(
			context,
			createKey(name),
			edibleBlocks,
			null,
			false,
			false,
			true,
			null
		);
	}

	public static Optional<Holder<TermiteBlockBehavior>> getTermiteBlockBehavior(RegistryAccess registryAccess, Block edibleBlock, boolean isNatural) {
		final Registry<TermiteBlockBehavior> registry = registryAccess.lookupOrThrow(WilderWildRegistries.TERMITE_BLOCK_BEHAVIOR);
		final List<Holder.Reference<TermiteBlockBehavior>> behaviors = registry.listElements()
			.filter(reference -> {
				TermiteBlockBehavior termiteBlockBehavior = reference.value();
				if (termiteBlockBehavior.getEdibleBlocks().contains(edibleBlock.builtInRegistryHolder())) {
					return isNatural ? termiteBlockBehavior.naturalTermiteUsable() : termiteBlockBehavior.playerPlacedTermiteUsable();
				}
				return false;
			})
			.toList();

		if (!behaviors.isEmpty()) return Optional.of(behaviors.getFirst());
		return Optional.empty();
	}

	public static void bootstrap(BootstrapContext<TermiteBlockBehavior> context) {
		// PLAYER-PLACED
		registerNaturalAndPlayer(context, Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG);
		registerHollowed(context, Blocks.STRIPPED_ACACIA_LOG, WWBlocks.STRIPPED_HOLLOWED_ACACIA_LOG);
		registerPlayerOnly(context, WWBlocks.HOLLOWED_ACACIA_LOG, WWBlocks.STRIPPED_HOLLOWED_ACACIA_LOG);
		registerNaturalAndPlayer(context, Blocks.ACACIA_WOOD, Blocks.STRIPPED_ACACIA_WOOD);

		registerNaturalAndPlayer(context, Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG);
		registerHollowed(context, Blocks.STRIPPED_BIRCH_LOG, WWBlocks.STRIPPED_HOLLOWED_BIRCH_LOG);
		registerPlayerOnly(context, WWBlocks.HOLLOWED_BIRCH_LOG, WWBlocks.STRIPPED_HOLLOWED_BIRCH_LOG);
		registerNaturalAndPlayer(context, Blocks.BIRCH_WOOD, Blocks.STRIPPED_BIRCH_WOOD);

		registerNaturalAndPlayer(context, Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG);
		registerHollowed(context, Blocks.STRIPPED_OAK_LOG, WWBlocks.STRIPPED_HOLLOWED_OAK_LOG);
		registerPlayerOnly(context, WWBlocks.HOLLOWED_OAK_LOG, WWBlocks.STRIPPED_HOLLOWED_OAK_LOG);
		registerNaturalAndPlayer(context, Blocks.OAK_WOOD, Blocks.STRIPPED_OAK_WOOD);

		registerNaturalAndPlayer(context, Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG);
		registerHollowed(context, Blocks.STRIPPED_DARK_OAK_LOG, WWBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG);
		registerPlayerOnly(context, WWBlocks.HOLLOWED_DARK_OAK_LOG, WWBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG);
		registerNaturalAndPlayer(context, Blocks.DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_WOOD);

		registerNaturalAndPlayer(context, Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG);
		registerHollowed(context, Blocks.STRIPPED_JUNGLE_LOG, WWBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG);
		registerPlayerOnly(context, WWBlocks.HOLLOWED_JUNGLE_LOG, WWBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG);
		registerNaturalAndPlayer(context, Blocks.JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_WOOD);

		registerNaturalAndPlayer(context, Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG);
		registerHollowed(context, Blocks.STRIPPED_SPRUCE_LOG, WWBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG);
		registerPlayerOnly(context, WWBlocks.HOLLOWED_SPRUCE_LOG, WWBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG);
		registerNaturalAndPlayer(context, Blocks.SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_WOOD);

		registerNaturalAndPlayer(context, Blocks.MANGROVE_LOG, Blocks.STRIPPED_MANGROVE_LOG);
		registerHollowed(context, Blocks.STRIPPED_MANGROVE_LOG, WWBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG);
		registerPlayerOnly(context, WWBlocks.HOLLOWED_MANGROVE_LOG, WWBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG);
		registerNaturalAndPlayer(context, Blocks.MANGROVE_WOOD, Blocks.STRIPPED_MANGROVE_WOOD);

		registerNaturalAndPlayer(context, Blocks.CHERRY_LOG, Blocks.STRIPPED_CHERRY_LOG);
		registerHollowed(context, Blocks.STRIPPED_CHERRY_LOG, WWBlocks.STRIPPED_HOLLOWED_CHERRY_LOG);
		registerPlayerOnly(context, WWBlocks.HOLLOWED_CHERRY_LOG, WWBlocks.STRIPPED_HOLLOWED_CHERRY_LOG);
		registerNaturalAndPlayer(context, Blocks.CHERRY_WOOD, Blocks.STRIPPED_CHERRY_WOOD);

		registerNaturalAndPlayer(context, WWBlocks.BAOBAB_LOG, WWBlocks.STRIPPED_BAOBAB_LOG);
		registerHollowed(context, WWBlocks.STRIPPED_BAOBAB_LOG, WWBlocks.STRIPPED_HOLLOWED_BAOBAB_LOG);
		registerPlayerOnly(context, WWBlocks.HOLLOWED_BAOBAB_LOG, WWBlocks.STRIPPED_HOLLOWED_BAOBAB_LOG);
		registerNaturalAndPlayer(context, WWBlocks.BAOBAB_WOOD, WWBlocks.STRIPPED_BAOBAB_WOOD);

		registerNaturalAndPlayer(context, WWBlocks.WILLOW_LOG, WWBlocks.STRIPPED_WILLOW_LOG);
		registerHollowed(context, WWBlocks.STRIPPED_WILLOW_LOG, WWBlocks.STRIPPED_HOLLOWED_WILLOW_LOG);
		registerPlayerOnly(context, WWBlocks.HOLLOWED_WILLOW_LOG, WWBlocks.STRIPPED_HOLLOWED_WILLOW_LOG);
		registerNaturalAndPlayer(context, WWBlocks.WILLOW_WOOD, WWBlocks.STRIPPED_WILLOW_WOOD);

		registerNaturalAndPlayer(context, WWBlocks.CYPRESS_LOG, WWBlocks.STRIPPED_CYPRESS_LOG);
		registerHollowed(context, WWBlocks.STRIPPED_CYPRESS_LOG, WWBlocks.STRIPPED_HOLLOWED_CYPRESS_LOG);
		registerPlayerOnly(context, WWBlocks.HOLLOWED_CYPRESS_LOG, WWBlocks.STRIPPED_HOLLOWED_CYPRESS_LOG);
		registerNaturalAndPlayer(context, WWBlocks.CYPRESS_WOOD, WWBlocks.STRIPPED_CYPRESS_WOOD);

		registerNaturalAndPlayer(context, WWBlocks.PALM_LOG, WWBlocks.STRIPPED_PALM_LOG);
		registerHollowed(context, WWBlocks.STRIPPED_PALM_LOG, WWBlocks.STRIPPED_HOLLOWED_PALM_LOG);
		registerPlayerOnly(context, WWBlocks.HOLLOWED_PALM_LOG, WWBlocks.STRIPPED_HOLLOWED_PALM_LOG);
		registerNaturalAndPlayer(context, WWBlocks.PALM_WOOD, WWBlocks.STRIPPED_PALM_WOOD);

		registerNaturalAndPlayer(context, WWBlocks.MAPLE_LOG, WWBlocks.STRIPPED_MAPLE_LOG);
		registerHollowed(context, WWBlocks.STRIPPED_MAPLE_LOG, WWBlocks.STRIPPED_HOLLOWED_MAPLE_LOG);
		registerPlayerOnly(context, WWBlocks.HOLLOWED_MAPLE_LOG, WWBlocks.STRIPPED_HOLLOWED_MAPLE_LOG);
		registerNaturalAndPlayer(context, WWBlocks.MAPLE_WOOD, WWBlocks.STRIPPED_MAPLE_WOOD);

		registerNaturalAndPlayer(context, Blocks.PALE_OAK_LOG, Blocks.STRIPPED_PALE_OAK_LOG);
		registerHollowed(context, Blocks.STRIPPED_PALE_OAK_LOG, WWBlocks.STRIPPED_HOLLOWED_PALE_OAK_LOG);
		registerPlayerOnly(context, WWBlocks.HOLLOWED_PALE_OAK_LOG, WWBlocks.STRIPPED_HOLLOWED_PALE_OAK_LOG);
		registerNaturalAndPlayer(context, Blocks.PALE_OAK_WOOD, Blocks.STRIPPED_PALE_OAK_WOOD);

		registerPlayerOnly(context, Blocks.BUSH, Blocks.DEAD_BUSH);
		registerPlayerOnly(context, WWBlocks.SHRUB, Blocks.DEAD_BUSH);

		registerBreakable(context, "leaves", BlockTags.LEAVES);
		registerBreakable(context, "leaf_litters", WWBlockItemTags.LEAF_LITTERS.block());
		registerBreakable(context, "stripped_hollowed_logs", WWBlockItemTags.STRIPPED_HOLLOWED_LOGS.block());
		registerBreakable(context, Blocks.BAMBOO);
		registerBreakable(context, Blocks.DEAD_BUSH);
	}
}
