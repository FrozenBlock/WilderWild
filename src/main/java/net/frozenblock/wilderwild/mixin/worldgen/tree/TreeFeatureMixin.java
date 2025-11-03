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

package net.frozenblock.wilderwild.mixin.worldgen.tree;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
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
		if (!(TreeFeature.class.cast(this) instanceof PalmTreeFeature)) return original;
		if (original.isEmpty() || !original.get()) return original;

		final BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		final AtomicInteger coconutCount = new AtomicInteger();
		Util.toShuffledList(foliageSet.stream(), randomSource).forEach(pos -> {
			final int currentCoconuts = coconutCount.get();
			if (currentCoconuts >= PalmTreeFeature.MAX_COCONUTS) return;
			if (level.getRandom().nextFloat() > PalmTreeFeature.COCONUT_CHANCE && currentCoconuts > 0) return;

			final BlockState state = level.getBlockState(pos);
			if (state.getOptionalValue(BlockStateProperties.DISTANCE).orElse(0) > CoconutBlock.VALID_FROND_DISTANCE) return;
			if (!level.getBlockState(mutable.setWithOffset(pos, 0, -1, 0)).isAir()) return;

			level.setBlock(mutable, WWBlocks.COCONUT.defaultBlockState().setValue(BlockStateProperties.HANGING, true), BLOCK_UPDATE_FLAGS);
			coconutCount.incrementAndGet();
		});
		return original;
	}

	@ModifyVariable(
		method = {
			"method_49238",
			"method_35364",
			"method_43162"
		},
		at = @At("HEAD"),
		ordinal = 0,
		argsOnly = true
	)
	private static BlockState wilderWild$setTermiteEdible(BlockState state) {
		return TermiteMoundBlock.setTermiteEdibleIfPossible(state);
	}

}
