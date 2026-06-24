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

package net.frozenblock.wilderwild.mixin.worldgen.tree;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import net.frozenblock.wilderwild.block.CoconutBlock;
import net.frozenblock.wilderwild.block.TermiteMoundBlock;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(TreeFeature.class)
public class TreeFeatureMixin {

	@Unique
	private static final float WILDERWILD$COCONUT_CHANCE = 0.25F;
	@Unique
	private static final int WILDERWILD$MAX_COCONUTS = 3;

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
		@Local(name = "foliage") Set<BlockPos> foliage,
		@Local(name = "level") WorldGenLevel level,
		@Local(name = "random") RandomSource random
	) {
		if (original.isEmpty() || !original.get()) return original;

		final TreeFeature treeFeature = TreeFeature.class.cast(this);
		if (!treeFeature.foliageProvider().equals(BlockStateProvider.simple(WWBlocks.PALM_FRONDS))) return original;

		final BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		final AtomicInteger coconutCount = new AtomicInteger();
		Util.toShuffledList(foliage.stream(), random).forEach(pos -> {
			final int currentCoconuts = coconutCount.get();
			if (currentCoconuts >= WILDERWILD$MAX_COCONUTS) return;
			if (level.getRandom().nextFloat() > WILDERWILD$COCONUT_CHANCE && currentCoconuts > 0) return;

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
			"lambda$place$0",
			"lambda$place$1",
			"lambda$place$2"
		},
		at = @At("HEAD"),
		ordinal = 0,
		argsOnly = true
	)
	private static BlockState wilderWild$setTermiteEdible(BlockState state) {
		return TermiteMoundBlock.setTermiteEdibleIfPossible(state);
	}
}
