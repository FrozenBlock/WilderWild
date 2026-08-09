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

package net.frozenblock.wilderwild.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public record TermiteEdibleBlockProcessor(boolean termiteEdible) implements StructureProcessor {
	private static final BooleanProperty TERMITE_EDIBLE = WWBlockStateProperties.TERMITE_EDIBLE;
	public static final MapCodec<TermiteEdibleBlockProcessor> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		Codec.BOOL.fieldOf("value").forGetter(TermiteEdibleBlockProcessor::termiteEdible)
	).apply(instance, TermiteEdibleBlockProcessor::new));

	@Override
	public StructureTemplate.StructureBlockInfo processBlock(
		LevelReader level,
		BlockPos targetPosition,
		BlockPos referencePos,
		BlockPos templateRelativePos,
		StructureTemplate.StructureBlockInfo processedBlockInfo,
		StructurePlaceSettings settings
	) {
		final BlockState originalState = processedBlockInfo.state();
		if (originalState.getValueOrElse(TERMITE_EDIBLE, this.termiteEdible) == this.termiteEdible) return processedBlockInfo;
		return new StructureTemplate.StructureBlockInfo(processedBlockInfo.pos(), originalState.setValue(TERMITE_EDIBLE, this.termiteEdible), processedBlockInfo.nbt());
	}

	@Override
	public MapCodec<? extends StructureProcessor> codec() {
		return MAP_CODEC;
	}
}
