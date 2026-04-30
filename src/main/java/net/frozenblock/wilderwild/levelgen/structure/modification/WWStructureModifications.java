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

package net.frozenblock.wilderwild.levelgen.structure.modification;

import com.google.common.collect.ImmutableList;
import net.frozenblock.lib.worldgen.structure.api.BlockStateRespectingProcessorRule;
import net.frozenblock.lib.worldgen.structure.api.BlockStateRespectingRuleProcessor;
import net.frozenblock.lib.worldgen.structure.api.RandomPoolAliasApi;
import net.frozenblock.lib.worldgen.structure.api.StructureProcessorApi;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;

public class WWStructureModifications {

	public static void init() {
		if (WWWorldgenConfig.DECAYING_TRAIL_RUINS_GENERATION.get()) {
			StructureProcessorApi.addProcessor(
				BuiltinStructures.TRAIL_RUINS.identifier(),
				new RuleProcessor(
					ImmutableList.of(
						new ProcessorRule(new RandomBlockMatchTest(Blocks.MUD_BRICKS, 0.2F), AlwaysTrueTest.INSTANCE, WWBlocks.CRACKED_MUD_BRICKS.defaultBlockState()),
						new ProcessorRule(new RandomBlockMatchTest(Blocks.MUD_BRICKS, 0.05F), AlwaysTrueTest.INSTANCE, WWBlocks.MOSSY_MUD_BRICKS.defaultBlockState())
					)
				)
			);
			StructureProcessorApi.addProcessor(
				BuiltinStructures.TRAIL_RUINS.identifier(),
				new BlockStateRespectingRuleProcessor(
					ImmutableList.of(
						new BlockStateRespectingProcessorRule(new RandomBlockMatchTest(Blocks.MUD_BRICK_STAIRS, 0.05F), AlwaysTrueTest.INSTANCE, WWBlocks.MOSSY_MUD_BRICK_STAIRS),
						new BlockStateRespectingProcessorRule(new RandomBlockMatchTest(Blocks.MUD_BRICK_SLAB, 0.05F), AlwaysTrueTest.INSTANCE, WWBlocks.MOSSY_MUD_BRICK_SLAB),
						new BlockStateRespectingProcessorRule(new RandomBlockMatchTest(Blocks.MUD_BRICK_SLAB, 0.05F), AlwaysTrueTest.INSTANCE, WWBlocks.MOSSY_MUD_BRICK_WALL)
					)
				)
			);
		}

		if (WWWorldgenConfig.NEW_DESERT_VILLAGE_GENERATION.get()) {
			StructureProcessorApi.addProcessor(
				BuiltinStructures.VILLAGE_DESERT.identifier(),
				new BlockStateRespectingRuleProcessor(
					ImmutableList.of(
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.JUNGLE_BUTTON), AlwaysTrueTest.INSTANCE, WWBlocks.PALM_BUTTON),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.JUNGLE_DOOR), AlwaysTrueTest.INSTANCE, WWBlocks.PALM_DOOR),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.JUNGLE_FENCE), AlwaysTrueTest.INSTANCE, WWBlocks.PALM_FENCE),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.JUNGLE_FENCE_GATE), AlwaysTrueTest.INSTANCE, WWBlocks.PALM_FENCE_GATE),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.JUNGLE_HANGING_SIGN), AlwaysTrueTest.INSTANCE, WWBlocks.PALM_HANGING_SIGN),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.JUNGLE_WALL_HANGING_SIGN), AlwaysTrueTest.INSTANCE, WWBlocks.PALM_WALL_HANGING_SIGN),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.JUNGLE_SIGN), AlwaysTrueTest.INSTANCE, WWBlocks.PALM_SIGN),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.JUNGLE_HANGING_SIGN), AlwaysTrueTest.INSTANCE, WWBlocks.PALM_WALL_HANGING_SIGN),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.JUNGLE_LOG), AlwaysTrueTest.INSTANCE, WWBlocks.PALM_LOG),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.JUNGLE_WOOD), AlwaysTrueTest.INSTANCE, WWBlocks.PALM_WOOD),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.STRIPPED_JUNGLE_LOG), AlwaysTrueTest.INSTANCE, WWBlocks.STRIPPED_PALM_LOG),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.STRIPPED_JUNGLE_WOOD), AlwaysTrueTest.INSTANCE, WWBlocks.STRIPPED_PALM_WOOD),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(WWBlocks.HOLLOWED_JUNGLE_LOG), AlwaysTrueTest.INSTANCE, WWBlocks.HOLLOWED_PALM_LOG),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(WWBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG), AlwaysTrueTest.INSTANCE, WWBlocks.STRIPPED_HOLLOWED_PALM_LOG),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.JUNGLE_PLANKS), AlwaysTrueTest.INSTANCE, WWBlocks.PALM_PLANKS),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.JUNGLE_PRESSURE_PLATE), AlwaysTrueTest.INSTANCE, WWBlocks.PALM_PRESSURE_PLATE),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.JUNGLE_SLAB), AlwaysTrueTest.INSTANCE, WWBlocks.PALM_SLAB),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.JUNGLE_STAIRS), AlwaysTrueTest.INSTANCE, WWBlocks.PALM_STAIRS),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.JUNGLE_SAPLING), AlwaysTrueTest.INSTANCE, WWBlocks.COCONUT),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.JUNGLE_LEAVES), AlwaysTrueTest.INSTANCE, WWBlocks.PALM_FRONDS)
					)
				)
			);
		}

		if (WWEntityConfig.SCORCHED_IN_TRIAL_CHAMBERS.get()) {
			RandomPoolAliasApi.addTarget(
				WWConstants.vanillaId("trial_chambers/spawner/contents/small_melee"),
				WWConstants.id("trial_chambers/spawner/small_melee/scorched"),
				1
			);
		}

		if (WWBlockConfig.ADD_STONE_CHESTS.get()) {
			StructureProcessorApi.addProcessor(
				BuiltinStructures.ANCIENT_CITY.identifier(),
				new BlockStateRespectingRuleProcessor(
					ImmutableList.of(
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.CHEST), AlwaysTrueTest.INSTANCE, WWBlocks.STONE_CHEST)
					)
				)
			);
		}
	}
}
