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

package net.frozenblock.wilderwild.data.worldgen.structure;

import com.google.common.collect.ImmutableList;
import java.util.List;
import net.frozenblock.lib.config.v2.entry.predicates.ConfigPredicate;
import net.frozenblock.lib.levelgen.structure.api.processor.BlockStateRespectingProcessorRule;
import net.frozenblock.lib.levelgen.structure.api.processor.BlockStateRespectingRuleProcessor;
import net.frozenblock.lib.levelgen.structure.api.processor.StructureProcessorListAdditions;
import net.frozenblock.lib.levelgen.structure.impl.processor.StructureProcessorListAddition;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;

public final class WWStructureProcessorListAdditions {

	public static void bootstrap(BootstrapContext<StructureProcessorListAddition> context) {
		final HolderGetter<Structure> structures = context.lookup(Registries.STRUCTURE);

		StructureProcessorListAdditions.register(
			context,
			WWConstants.id("decaying_trail_ruins"),
			HolderSet.direct(structures.getOrThrow(BuiltinStructures.TRAIL_RUINS)),
			List.of(
				new RuleProcessor(
					ImmutableList.of(
						new ProcessorRule(new RandomBlockMatchTest(Blocks.MUD_BRICKS, 0.2F), AlwaysTrueTest.INSTANCE, WWBlocks.CRACKED_MUD_BRICKS.get().defaultBlockState()),
						new ProcessorRule(new RandomBlockMatchTest(Blocks.MUD_BRICKS, 0.05F), AlwaysTrueTest.INSTANCE, WWBlocks.MOSSY_MUD_BRICKS.get().defaultBlockState())
					)
				),
				new BlockStateRespectingRuleProcessor(
					ImmutableList.of(
						new BlockStateRespectingProcessorRule(new RandomBlockMatchTest(Blocks.MUD_BRICK_STAIRS, 0.05F), AlwaysTrueTest.INSTANCE, WWBlocks.MOSSY_MUD_BRICK_STAIRS.get()),
						new BlockStateRespectingProcessorRule(new RandomBlockMatchTest(Blocks.MUD_BRICK_SLAB, 0.05F), AlwaysTrueTest.INSTANCE, WWBlocks.MOSSY_MUD_BRICK_SLAB.get()),
						new BlockStateRespectingProcessorRule(new RandomBlockMatchTest(Blocks.MUD_BRICK_SLAB, 0.05F), AlwaysTrueTest.INSTANCE, WWBlocks.MOSSY_MUD_BRICK_WALL.get())
					)
				)
			),
			ConfigPredicate.equalTo(WWWorldgenConfig.DECAYING_TRAIL_RUINS_GENERATION, true)
		);

		StructureProcessorListAdditions.register(
			context,
			WWConstants.id("palm_village_desert"),
			HolderSet.direct(structures.getOrThrow(BuiltinStructures.VILLAGE_DESERT)),
			List.of(
				new BlockStateRespectingRuleProcessor(
					ImmutableList.of(
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.JUNGLE_BUTTON), AlwaysTrueTest.INSTANCE, WWBlocks.PALM_BUTTON.get()),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.JUNGLE_DOOR), AlwaysTrueTest.INSTANCE, WWBlocks.PALM_DOOR.get()),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.JUNGLE_FENCE), AlwaysTrueTest.INSTANCE, WWBlocks.PALM_FENCE.get()),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.JUNGLE_FENCE_GATE), AlwaysTrueTest.INSTANCE, WWBlocks.PALM_FENCE_GATE.get()),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.JUNGLE_HANGING_SIGN), AlwaysTrueTest.INSTANCE, WWBlocks.PALM_HANGING_SIGN.get()),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.JUNGLE_WALL_HANGING_SIGN), AlwaysTrueTest.INSTANCE, WWBlocks.PALM_WALL_HANGING_SIGN.get()),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.JUNGLE_SIGN), AlwaysTrueTest.INSTANCE, WWBlocks.PALM_SIGN.get()),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.JUNGLE_HANGING_SIGN), AlwaysTrueTest.INSTANCE, WWBlocks.PALM_WALL_HANGING_SIGN.get()),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.JUNGLE_LOG), AlwaysTrueTest.INSTANCE, WWBlocks.PALM_LOG.get()),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.JUNGLE_WOOD), AlwaysTrueTest.INSTANCE, WWBlocks.PALM_WOOD.get()),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.STRIPPED_JUNGLE_LOG), AlwaysTrueTest.INSTANCE, WWBlocks.STRIPPED_PALM_LOG.get()),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.STRIPPED_JUNGLE_WOOD), AlwaysTrueTest.INSTANCE, WWBlocks.STRIPPED_PALM_WOOD.get()),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(WWBlocks.HOLLOWED_JUNGLE_LOG.get()), AlwaysTrueTest.INSTANCE, WWBlocks.HOLLOWED_PALM_LOG.get()),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(WWBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG.get()), AlwaysTrueTest.INSTANCE, WWBlocks.STRIPPED_HOLLOWED_PALM_LOG.get()),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.JUNGLE_PLANKS), AlwaysTrueTest.INSTANCE, WWBlocks.PALM_PLANKS.get()),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.JUNGLE_PRESSURE_PLATE), AlwaysTrueTest.INSTANCE, WWBlocks.PALM_PRESSURE_PLATE.get()),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.JUNGLE_SLAB), AlwaysTrueTest.INSTANCE, WWBlocks.PALM_SLAB.get()),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.JUNGLE_STAIRS), AlwaysTrueTest.INSTANCE, WWBlocks.PALM_STAIRS.get()),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.JUNGLE_SAPLING), AlwaysTrueTest.INSTANCE, WWBlocks.COCONUT.get()),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.JUNGLE_LEAVES), AlwaysTrueTest.INSTANCE, WWBlocks.PALM_FRONDS.get())
					)
				)
			),
			ConfigPredicate.equalTo(WWWorldgenConfig.NEW_DESERT_VILLAGE_GENERATION, true)
		);

		StructureProcessorListAdditions.register(
			context,
			WWConstants.id("willow_abandoned_camp_swamp"),
			HolderSet.direct(structures.getOrThrow(BuiltinStructures.ABANDONDED_CAMP_SWAMP)),
			List.of(
				new BlockStateRespectingRuleProcessor(
					ImmutableList.of(
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.OAK_BUTTON), AlwaysTrueTest.INSTANCE, WWBlocks.WILLOW_BUTTON.get()),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.OAK_DOOR), AlwaysTrueTest.INSTANCE, WWBlocks.WILLOW_DOOR.get()),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.OAK_FENCE), AlwaysTrueTest.INSTANCE, WWBlocks.WILLOW_FENCE.get()),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.OAK_FENCE_GATE), AlwaysTrueTest.INSTANCE, WWBlocks.WILLOW_FENCE_GATE.get()),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.OAK_HANGING_SIGN), AlwaysTrueTest.INSTANCE, WWBlocks.WILLOW_HANGING_SIGN.get()),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.OAK_WALL_HANGING_SIGN), AlwaysTrueTest.INSTANCE, WWBlocks.WILLOW_WALL_HANGING_SIGN.get()),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.OAK_SIGN), AlwaysTrueTest.INSTANCE, WWBlocks.WILLOW_SIGN.get()),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.OAK_HANGING_SIGN), AlwaysTrueTest.INSTANCE, WWBlocks.WILLOW_WALL_HANGING_SIGN.get()),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.OAK_LOG), AlwaysTrueTest.INSTANCE, WWBlocks.WILLOW_LOG.get()),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.OAK_WOOD), AlwaysTrueTest.INSTANCE, WWBlocks.WILLOW_WOOD.get()),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.STRIPPED_OAK_LOG), AlwaysTrueTest.INSTANCE, WWBlocks.STRIPPED_WILLOW_LOG.get()),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.STRIPPED_OAK_WOOD), AlwaysTrueTest.INSTANCE, WWBlocks.STRIPPED_WILLOW_WOOD.get()),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(WWBlocks.HOLLOWED_OAK_LOG.get()), AlwaysTrueTest.INSTANCE, WWBlocks.HOLLOWED_WILLOW_LOG.get()),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(WWBlocks.STRIPPED_HOLLOWED_OAK_LOG.get()), AlwaysTrueTest.INSTANCE, WWBlocks.STRIPPED_HOLLOWED_WILLOW_LOG.get()),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.OAK_PLANKS), AlwaysTrueTest.INSTANCE, WWBlocks.WILLOW_PLANKS.get()),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.OAK_PRESSURE_PLATE), AlwaysTrueTest.INSTANCE, WWBlocks.WILLOW_PRESSURE_PLATE.get()),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.OAK_SLAB), AlwaysTrueTest.INSTANCE, WWBlocks.WILLOW_SLAB.get()),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.OAK_STAIRS), AlwaysTrueTest.INSTANCE, WWBlocks.WILLOW_STAIRS.get()),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.OAK_SAPLING), AlwaysTrueTest.INSTANCE, WWBlocks.WILLOW_SAPLING.get()),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.OAK_LEAVES), AlwaysTrueTest.INSTANCE, WWBlocks.WILLOW_LEAVES.get())
					)
				)
			),
			ConfigPredicate.equalTo(WWWorldgenConfig.NEW_ABANDONED_CAMP_GENERATION, true)
		);

		StructureProcessorListAdditions.register(
			context,
			WWConstants.id("stone_chest_ancient_city"),
			HolderSet.direct(structures.getOrThrow(BuiltinStructures.ANCIENT_CITY)),
			List.of(
				new BlockStateRespectingRuleProcessor(
					ImmutableList.of(
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.CHEST), AlwaysTrueTest.INSTANCE, WWBlocks.STONE_CHEST.get())
					)
				)
			),
			ConfigPredicate.equalTo(WWBlockConfig.ADD_STONE_CHESTS, true)
		);
	}
}
