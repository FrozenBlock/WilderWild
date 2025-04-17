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

package net.frozenblock.wilderwild.mixin.worldgen.tree;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import net.frozenblock.wilderwild.block.CoconutBlock;
import net.frozenblock.wilderwild.block.TermiteMoundBlock;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.worldgen.impl.feature.PalmTreeFeature;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import net.minecraft.Util;

@Mixin(TreeFeature.class)
public class TreeFeatureMixin {

	@Shadow
	@Final
	private static int BLOCK_UPDATE_FLAGS;

	@ModifyExpressionValue(
		method = "place",
		at = @At(
			value = "INVOKE",
			target = "Ljava/util/Optional;map(Ljava/util/function/Function;)Ljava/util/Optional;"
		)
	)
	public Optional<Boolean> wilderWild$addCoconuts(
		Optional<Boolean> original,
		@Local(ordinal = 2) Set<BlockPos> foliageSet,
		@Local WorldGenLevel level,
		@Local RandomSource randomSource
	) {
		if (TreeFeature.class.cast(this) instanceof PalmTreeFeature) {
			if (original.isPresent() && original.get()) {
				BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
				AtomicBoolean hasPlacedCoconut = new AtomicBoolean();
				Util.toShuffledList(foliageSet.stream(), randomSource).forEach(pos -> {
					if (level.getRandom().nextFloat() <= PalmTreeFeature.COCONUT_CHANCE || !hasPlacedCoconut.get()) {
						BlockState blockState = level.getBlockState(pos);
						if (!blockState.hasProperty(BlockStateProperties.DISTANCE) || blockState.getValue(BlockStateProperties.DISTANCE) <= CoconutBlock.VALID_FROND_DISTANCE) {
							BlockPos coconutPos = mutablePos.setWithOffset(pos, 0, -1, 0);
							if (level.getBlockState(coconutPos).isAir()) {
								level.setBlock(coconutPos, WWBlocks.COCONUT.defaultBlockState().setValue(BlockStateProperties.HANGING, true), BLOCK_UPDATE_FLAGS);
								hasPlacedCoconut.set(true);
							}
						}
					}
				});
			}
		}
		return original;
	}

	@ModifyVariable(method = "method_49238", at = @At("HEAD"), ordinal = 0, argsOnly = true)
	private static BlockState wilderWild$setTermiteEdibleA(BlockState state) {
		return TermiteMoundBlock.setTermiteEdibleIfPossible(state);
	}

	@ModifyVariable(method = "method_35364", at = @At("HEAD"), ordinal = 0, argsOnly = true)
	private static BlockState wilderWild$setTermiteEdibleB(BlockState state) {
		return TermiteMoundBlock.setTermiteEdibleIfPossible(state);
	}

	@ModifyVariable(method = "method_43162", at = @At("HEAD"), ordinal = 0, argsOnly = true)
	private static BlockState wilderWild$setTermiteEdibleC(BlockState state) {
		return TermiteMoundBlock.setTermiteEdibleIfPossible(state);
	}

}
