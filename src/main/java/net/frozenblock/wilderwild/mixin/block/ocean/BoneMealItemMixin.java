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

package net.frozenblock.wilderwild.mixin.block.ocean;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.frozenblock.wilderwild.block.BarnaclesBlock;
import net.frozenblock.wilderwild.block.SpongeBudBlock;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.tag.WWBiomeTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BoneMealItem.class)
public class BoneMealItemMixin {

	@WrapOperation(
		method = "growWaterPlant",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/core/Holder;is(Lnet/minecraft/tags/TagKey;)Z"
		)
	)
	private static boolean wilderWild$growBarnaclesA(
		Holder<Biome> instance, TagKey<Biome> tagKey, Operation<Boolean> original,
		ItemStack stack, Level level, BlockPos pos, @Nullable Direction clickedFace,
		@Local(name = "random") RandomSource random,
		@Local(name = "testPos") BlockPos testPos,
		@Local(name = "stateToGrow") LocalRef<BlockState> stateToGrow
	) {
		final WWWorldgenConfig.AquaticGeneration aquaticGeneration = WWWorldgenConfig.get().aquaticGeneration;

		if (aquaticGeneration.barnacle && instance.is(WWBiomeTags.PRODUCES_BARNACLES_FROM_BONEMEAL) && random.nextInt(22) == 0) {
			BlockState barnaclesState = WWBlocks.BARNACLES.defaultBlockState();
			if (clickedFace != null) {
				barnaclesState = barnaclesState
					.setValue(MultifaceBlock.getFaceProperty(clickedFace), true)
					.setValue(BarnaclesBlock.WATERLOGGED, true);
			}

			for (int k = 0; !barnaclesState.canSurvive(level, testPos) && k < 8; k++) {
				barnaclesState = WWBlocks.BARNACLES.defaultBlockState()
					.setValue(MultifaceBlock.getFaceProperty(Direction.getRandom(random)), true)
					.setValue(BarnaclesBlock.WATERLOGGED, true);
			}

			if (barnaclesState.canSurvive(level, testPos)) {
				stateToGrow.set(barnaclesState);
				return false;
			}
		}

		if (aquaticGeneration.spongeBud && instance.is(WWBiomeTags.PRODUCES_SPONGE_BUDS_FROM_BONEMEAL) && random.nextInt(22) == 0) {
			BlockState spongeBudState = WWBlocks.SPONGE_BUD.defaultBlockState();
			if (clickedFace != null) {
				if (clickedFace.getAxis() == Direction.Axis.Y) {
					spongeBudState = spongeBudState
						.setValue(SpongeBudBlock.AGE, random.nextInt(SpongeBudBlock.MAX_AGE))
						.setValue(SpongeBudBlock.FACE, clickedFace == Direction.UP ? AttachFace.CEILING : AttachFace.FLOOR)
						.setValue(SpongeBudBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(random));
				} else {
					spongeBudState = spongeBudState
						.setValue(SpongeBudBlock.AGE, random.nextInt(SpongeBudBlock.MAX_AGE))
						.setValue(SpongeBudBlock.FACE, AttachFace.WALL)
						.setValue(SpongeBudBlock.FACING, clickedFace.getOpposite());
				}
			} else {
				spongeBudState = WWBlocks.SPONGE_BUD.randomBlockState(random)
					.setValue(SpongeBudBlock.WATERLOGGED, true);
			}

			for (int k = 0; !spongeBudState.canSurvive(level, testPos) && k < 8; k++) {
				spongeBudState = WWBlocks.SPONGE_BUD.randomBlockState(random)
					.setValue(SpongeBudBlock.WATERLOGGED, true);
			}

			if (spongeBudState.canSurvive(level, testPos)) {
				stateToGrow.set(spongeBudState);
				return false;
			}
		}

		if (aquaticGeneration.seaAnemone && instance.is(WWBiomeTags.PRODUCES_SEA_ANEMONE_FROM_BONEMEAL) && random.nextInt(19) == 0) {
			final BlockState seaAnemoneState = WWBlocks.SEA_ANEMONE.defaultBlockState();
			if (seaAnemoneState.canSurvive(level, testPos)) {
				stateToGrow.set(seaAnemoneState);
				return false;
			}
		}

		if (aquaticGeneration.seaWhip && instance.is(WWBiomeTags.PRODUCES_SEA_WHIPS_FROM_BONEMEAL) && random.nextInt(19) == 0) {
			final BlockState seaWhipSate = WWBlocks.SEA_WHIP.defaultBlockState();
			if (seaWhipSate.canSurvive(level, testPos)) {
				stateToGrow.set(seaWhipSate);
				return false;
			}
		}

		if (aquaticGeneration.tubeWorm && instance.is(WWBiomeTags.PRODUCES_TUBE_WORMS_FROM_BONEMEAL) && random.nextInt(30) == 0) {
			final BlockState tubeWormsState = WWBlocks.TUBE_WORMS.defaultBlockState();
			if (tubeWormsState.canSurvive(level, testPos)) {
				stateToGrow.set(tubeWormsState);
				return false;
			}
		}

		return original.call(instance, tagKey);
	}

}
