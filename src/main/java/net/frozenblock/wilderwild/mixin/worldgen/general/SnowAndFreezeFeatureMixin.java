/*
 * Copyright 2022-2023 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.mixin.worldgen.general;

import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.SnowAndFreezeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SnowAndFreezeFeature.class)
public class SnowAndFreezeFeatureMixin {

	@Unique
	private static final BlockState wilderWild$snowState = Blocks.SNOW.defaultBlockState();

	@Unique
	private static final BlockState wilderWild$iceState = Blocks.ICE.defaultBlockState();

    @Inject(method = "place", at = @At("HEAD"), cancellable = true)
	public void wilderWild$place(FeaturePlaceContext<NoneFeatureConfiguration> context, CallbackInfoReturnable<Boolean> info) {
		if (WilderSharedConstants.config().snowBelowTrees()) {
			info.setReturnValue(wilderWild$place(context));
		}
	}

	@Unique
	public boolean wilderWild$place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		BlockPos pos = context.origin();
		WorldGenLevel level = context.level();
		BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
		BlockPos.MutableBlockPos mutablePlacementPos = new BlockPos.MutableBlockPos();
		boolean returnValue = false;
		int posX = pos.getX();
		int posZ = pos.getZ();
		for(int i = 0; i < 16; i++) {
			int x = posX + i;
			for(int j = 0; j < 16; j++) {
				int z = posZ + j;
				mutablePos.set(x, level.getHeight(Heightmap.Types.MOTION_BLOCKING, x, z), z);
				mutablePlacementPos.set(x, level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z), z);
				if (wilderWild$placeSnowAndIceAtPos(level, mutablePos, mutablePlacementPos)) {
					returnValue = true;
				}
			}
		}
		return returnValue;
	}

	@Unique
	private static boolean wilderWild$placeSnowAndIceAtPos(WorldGenLevel level, BlockPos.MutableBlockPos motionBlockingPos, BlockPos.MutableBlockPos belowLeavesPos) {
		boolean returnValue = false;

		if (level.getBiome(motionBlockingPos.move(Direction.DOWN)).value().shouldFreeze(level, motionBlockingPos, false)) {
			level.setBlock(motionBlockingPos, wilderWild$iceState, 2);
			returnValue = true;
		}
		motionBlockingPos.move(Direction.UP);

		if (!motionBlockingPos.equals(belowLeavesPos)) {
			if (level.getBiome(belowLeavesPos.move(Direction.DOWN)).value().shouldFreeze(level, belowLeavesPos, false)) {
				level.setBlock(belowLeavesPos, wilderWild$iceState, 2);
				returnValue = true;
			}
			belowLeavesPos.move(Direction.UP);
		}

		int lowestY = belowLeavesPos.getY() - 1;
		while (motionBlockingPos.getY() > lowestY) {
			if (wilderWild$placeSnowLayer(level, motionBlockingPos)) {
				returnValue = true;
			}
		}
		return returnValue;
	}

	@Unique
	private static boolean wilderWild$placeSnowLayer(WorldGenLevel level, BlockPos.MutableBlockPos pos) {
		Holder<Biome> biomeHolder = level.getBiome(pos);
		if (biomeHolder.value().shouldSnow(level, pos) && level.getBlockState(pos).isAir() && wilderWild$snowState.canSurvive(level, pos)) {
			level.setBlock(pos, wilderWild$snowState, 3);
			BlockState belowState = level.getBlockState(pos.move(Direction.DOWN));
			if (belowState.hasProperty(BlockStateProperties.SNOWY)) {
				level.setBlock(pos, belowState.setValue(BlockStateProperties.SNOWY, true), 2);
			}
			return true;
		}
		return false;
	}

}
