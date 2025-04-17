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
 * You should have received a copy of the FrozenBlock Modding oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.mixin.block.termite;

import net.frozenblock.lib.FrozenBools;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = RotatedPillarBlock.class, priority = 990)
public class RotatedPillarBlockMixin {

	@Unique
	private static final boolean WILDERWILD$TERMITE_NATURAL_BLOCKS_ON_BOOT = WWBlockConfig.get().termite.onlyEatNaturalBlocks;

	@Inject(method = "createBlockStateDefinition", at = @At("TAIL"))
	private void addTermiteEdibleState(StateDefinition.Builder<Block, BlockState> builder, CallbackInfo info) {
		if (FrozenBools.IS_DATAGEN) return;
		if (WILDERWILD$TERMITE_NATURAL_BLOCKS_ON_BOOT) {
			BlockBehaviour.Properties properties = RotatedPillarBlock.class.cast(this).properties();
			if (properties.instrument == NoteBlockInstrument.BASS && properties.soundType != SoundType.STEM) {
				builder.add(WWBlockStateProperties.TERMITE_EDIBLE);
			}
		}
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void wilderWild$appendFalseTermiteEdibleToState(BlockBehaviour.Properties properties, CallbackInfo info) {
		if (FrozenBools.IS_DATAGEN) return;
		RotatedPillarBlock rotatedPillarBlock = RotatedPillarBlock.class.cast(this);
		BlockState defaultBlockState = rotatedPillarBlock.defaultBlockState();
		if (defaultBlockState.hasProperty(WWBlockStateProperties.TERMITE_EDIBLE)) {
			rotatedPillarBlock.registerDefaultState(defaultBlockState.setValue(WWBlockStateProperties.TERMITE_EDIBLE, false));
		}
	}
}
