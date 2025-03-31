/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.mixin.block.frozen_vegetation;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import java.util.Optional;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.worldgen.features.placed.WWPlacedFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GrassBlock.class)
public class GrassBlockMixin {

	@ModifyExpressionValue(
		method = "performBonemeal",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/core/BlockPos;above()Lnet/minecraft/core/BlockPos;",
			ordinal = 0
		)
	)
	public BlockPos wilderWild$checkIfSnowy(
		BlockPos original,
		ServerLevel level,
		@Share("wilderWild$isSnowy") LocalBooleanRef isSnowy
	) {
		isSnowy.set(level.getBiome(original).value().coldEnoughToSnow(original));
		return original;
	}

	@ModifyExpressionValue(
		method = "performBonemeal",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/Block;defaultBlockState()Lnet/minecraft/world/level/block/state/BlockState;",
			ordinal = 0
		)
	)
	public BlockState wilderWild$replaceWithFrozenShortGrass(
		BlockState original,
		@Share("wilderWild$isSnowy") LocalBooleanRef isSnowy
	) {
		return isSnowy.get() ? WWBlocks.FROZEN_SHORT_GRASS.defaultBlockState() : original;
	}

	@WrapOperation(
		method = "performBonemeal",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/core/Registry;getHolder(Lnet/minecraft/resources/ResourceKey;)Ljava/util/Optional;"
		)
	)
	public Optional<Holder.Reference> wilderWild$replaceWithFrozenGrassFeature(
		Registry instance, ResourceKey key, Operation<Optional<Holder.Reference>> original,
		@Share("wilderWild$isSnowy") LocalBooleanRef isSnowy
	) {
		return isSnowy.get() ? original.call(instance, WWPlacedFeatures.PATCH_GRASS_FROZEN_BONEMEAL.getKey()) : original.call(instance, key);
	}

	@WrapOperation(
		method = "performBonemeal",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;isAir()Z"
		)
	)
	public boolean wilderWild$allowSnowlogging(
		BlockState instance, Operation<Boolean> original,
		@Share("wilderWild$isSnowy") LocalBooleanRef isSnowy
	) {
		boolean originalValue = original.call(instance);
		return isSnowy.get() ? originalValue || (WWBlockConfig.SNOWLOGGING && instance.is(Blocks.SNOW)) : originalValue;
	}

}
