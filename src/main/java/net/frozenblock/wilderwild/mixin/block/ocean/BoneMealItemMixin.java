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
		ItemStack stack, Level level, BlockPos pos, @Nullable Direction direction,
		@Local RandomSource randomSource,
		@Local int i,
		@Local(ordinal  = 1) BlockPos offsetPos,
		@Local LocalRef<BlockState> state
	) {
		final WWWorldgenConfig.AquaticGeneration aquaticGeneration = WWWorldgenConfig.get().aquaticGeneration;

		if (aquaticGeneration.barnacle && instance.is(WWBiomeTags.PRODUCES_BARNACLES_FROM_BONEMEAL) && randomSource.nextInt(22) == 0) {
			BlockState barnaclesState = WWBlocks.BARNACLES.defaultBlockState();
			if (direction != null) {
				barnaclesState = barnaclesState
					.setValue(MultifaceBlock.getFaceProperty(direction), true)
					.setValue(BarnaclesBlock.WATERLOGGED, true);
			}

			for (int k = 0; !barnaclesState.canSurvive(level, offsetPos) && k < 8; k++) {
				barnaclesState = WWBlocks.BARNACLES.defaultBlockState()
					.setValue(MultifaceBlock.getFaceProperty(Direction.getRandom(randomSource)), true)
					.setValue(BarnaclesBlock.WATERLOGGED, true);
			}

			if (barnaclesState.canSurvive(level, offsetPos)) {
				state.set(barnaclesState);
				return false;
			}
		}

		if (aquaticGeneration.spongeBud && instance.is(WWBiomeTags.PRODUCES_SPONGE_BUDS_FROM_BONEMEAL) && randomSource.nextInt(22) == 0) {
			BlockState spongeBudState = WWBlocks.SPONGE_BUD.defaultBlockState();
			if (direction != null) {
				if (direction.getAxis() == Direction.Axis.Y) {
					spongeBudState = spongeBudState
						.setValue(SpongeBudBlock.AGE, randomSource.nextInt(SpongeBudBlock.MAX_AGE))
						.setValue(SpongeBudBlock.FACE, direction == Direction.UP ? AttachFace.CEILING : AttachFace.FLOOR)
						.setValue(SpongeBudBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(randomSource));
				} else {
					spongeBudState = spongeBudState
						.setValue(SpongeBudBlock.AGE, randomSource.nextInt(SpongeBudBlock.MAX_AGE))
						.setValue(SpongeBudBlock.FACE, AttachFace.WALL)
						.setValue(SpongeBudBlock.FACING, direction.getOpposite());
				}
			} else {
				spongeBudState = WWBlocks.SPONGE_BUD.randomBlockState(randomSource)
					.setValue(SpongeBudBlock.WATERLOGGED, true);
			}

			for (int k = 0; !spongeBudState.canSurvive(level, offsetPos) && k < 8; k++) {
				spongeBudState = WWBlocks.SPONGE_BUD.randomBlockState(randomSource)
					.setValue(SpongeBudBlock.WATERLOGGED, true);
			}

			if (spongeBudState.canSurvive(level, offsetPos)) {
				state.set(spongeBudState);
				return false;
			}
		}

		if (aquaticGeneration.seaAnemone && instance.is(WWBiomeTags.PRODUCES_SEA_ANEMONE_FROM_BONEMEAL) && randomSource.nextInt(19) == 0) {
			final BlockState seaAnemoneState = WWBlocks.SEA_ANEMONE.defaultBlockState();
			if (seaAnemoneState.canSurvive(level, offsetPos)) {
				state.set(seaAnemoneState);
				return false;
			}
		}

		if (aquaticGeneration.seaWhip && instance.is(WWBiomeTags.PRODUCES_SEA_WHIPS_FROM_BONEMEAL) && randomSource.nextInt(19) == 0) {
			final BlockState seaWhipSate = WWBlocks.SEA_WHIP.defaultBlockState();
			if (seaWhipSate.canSurvive(level, offsetPos)) {
				state.set(seaWhipSate);
				return false;
			}
		}

		if (aquaticGeneration.tubeWorm && instance.is(WWBiomeTags.PRODUCES_TUBE_WORMS_FROM_BONEMEAL) && randomSource.nextInt(30) == 0) {
			final BlockState tubeWormsState = WWBlocks.TUBE_WORMS.defaultBlockState();
			if (tubeWormsState.canSurvive(level, offsetPos)) {
				state.set(tubeWormsState);
				return false;
			}
		}

		return original.call(instance, tagKey);
	}

}
