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

package net.frozenblock.wilderwild.mixin.worldgen;

import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.structures.SwampHutPiece;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(value = SwampHutPiece.class, priority = 999)
public class SwampHutPieceMixin {

    @Shadow
    private boolean spawnedWitch;

	@Unique
    private final SwampHutPiece wilderWild$swampHut = SwampHutPiece.class.cast(this);

    @Inject(method = "postProcess", at = @At("HEAD"), cancellable = true)
    public void wilderWild$postProcess(WorldGenLevel level, StructureManager structureAccessor, ChunkGenerator chunkGenerator, RandomSource random, BoundingBox chunkBox, ChunkPos chunkPos, BlockPos pivot, CallbackInfo info) {
        if (WilderSharedConstants.config().newWitchHuts()) {
			info.cancel();
			if (wilderWild$swampHut.updateAverageGroundHeight(level, chunkBox, 0)) {
				BlockState plankState = RegisterBlocks.CYPRESS_PLANKS.defaultBlockState();
				BlockState logState = Blocks.OAK_LOG.defaultBlockState();
				BlockState fenceState = Blocks.OAK_FENCE.defaultBlockState();
				BlockState stairState = RegisterBlocks.CYPRESS_STAIRS.defaultBlockState();
				BlockState airState = Blocks.AIR.defaultBlockState();
				wilderWild$swampHut.generateBox(level, chunkBox, 1, 1, 1, 5, 1, 7, plankState, plankState, false);
				wilderWild$swampHut.generateBox(level, chunkBox, 1, 4, 2, 5, 4, 7, plankState, plankState, false);
				wilderWild$swampHut.generateBox(level, chunkBox, 2, 1, 0, 4, 1, 0, plankState, plankState, false);
				wilderWild$swampHut.generateBox(level, chunkBox, 2, 2, 2, 3, 3, 2, plankState, plankState, false);
				wilderWild$swampHut.generateBox(level, chunkBox, 1, 2, 3, 1, 3, 6, plankState, plankState, false);
				wilderWild$swampHut.generateBox(level, chunkBox, 5, 2, 3, 5, 3, 6, plankState, plankState, false);
				wilderWild$swampHut.generateBox(level, chunkBox, 2, 2, 7, 4, 3, 7, plankState, plankState, false);
				wilderWild$swampHut.generateBox(level, chunkBox, 1, 0, 2, 1, 3, 2, logState, logState, false);
				wilderWild$swampHut.generateBox(level, chunkBox, 5, 0, 2, 5, 3, 2, logState, logState, false);
				wilderWild$swampHut.generateBox(level, chunkBox, 1, 0, 7, 1, 3, 7, logState, logState, false);
				wilderWild$swampHut.generateBox(level, chunkBox, 5, 0, 7, 5, 3, 7, logState, logState, false);
				wilderWild$swampHut.placeBlock(level, fenceState.setValue(BlockStateProperties.WEST, true).setValue(BlockStateProperties.EAST, true), 2, 3, 2, chunkBox);
				wilderWild$swampHut.placeBlock(level, fenceState.setValue(BlockStateProperties.WEST, true).setValue(BlockStateProperties.EAST, true), 3, 3, 7, chunkBox);
				wilderWild$swampHut.placeBlock(level, airState, 1, 3, 4, chunkBox);
				wilderWild$swampHut.placeBlock(level, airState, 5, 3, 4, chunkBox);
				wilderWild$swampHut.placeBlock(level, airState, 5, 3, 5, chunkBox);
				wilderWild$swampHut.placeBlock(level, Blocks.POTTED_RED_MUSHROOM.defaultBlockState(), 1, 3, 5, chunkBox);
				wilderWild$swampHut.placeBlock(level, Blocks.CRAFTING_TABLE.defaultBlockState(), 3, 2, 6, chunkBox);
				wilderWild$swampHut.placeBlock(level, Blocks.CAULDRON.defaultBlockState(), 4, 2, 6, chunkBox);
				wilderWild$swampHut.placeBlock(level, fenceState.setValue(BlockStateProperties.NORTH, true), 1, 2, 1, chunkBox);
				wilderWild$swampHut.placeBlock(level, fenceState.setValue(BlockStateProperties.NORTH, true), 5, 2, 1, chunkBox);
				BlockState blockState = stairState.setValue(StairBlock.FACING, Direction.NORTH);
				BlockState blockState2 = stairState.setValue(StairBlock.FACING, Direction.EAST);
				BlockState blockState3 = stairState.setValue(StairBlock.FACING, Direction.WEST);
				BlockState blockState4 = stairState.setValue(StairBlock.FACING, Direction.SOUTH);
				wilderWild$swampHut.generateBox(level, chunkBox, 0, 4, 1, 6, 4, 1, blockState, blockState, false);
				wilderWild$swampHut.generateBox(level, chunkBox, 0, 4, 2, 0, 4, 7, blockState2, blockState2, false);
				wilderWild$swampHut.generateBox(level, chunkBox, 6, 4, 2, 6, 4, 7, blockState3, blockState3, false);
				wilderWild$swampHut.generateBox(level, chunkBox, 0, 4, 8, 6, 4, 8, blockState4, blockState4, false);
				wilderWild$swampHut.placeBlock(level, blockState.setValue(StairBlock.SHAPE, StairsShape.OUTER_RIGHT), 0, 4, 1, chunkBox);
				wilderWild$swampHut.placeBlock(level, blockState.setValue(StairBlock.SHAPE, StairsShape.OUTER_LEFT), 6, 4, 1, chunkBox);
				wilderWild$swampHut.placeBlock(level, blockState4.setValue(StairBlock.SHAPE, StairsShape.OUTER_LEFT), 0, 4, 8, chunkBox);
				wilderWild$swampHut.placeBlock(level, blockState4.setValue(StairBlock.SHAPE, StairsShape.OUTER_RIGHT), 6, 4, 8, chunkBox);

				for (int i = 2; i <= 7; i += 5) {
					for (int j = 1; j <= 5; j += 4) {
						wilderWild$swampHut.fillColumnDown(level, logState, j, -1, i, chunkBox);
					}
				}

				if (!this.spawnedWitch) {
					BlockPos blockPos = wilderWild$swampHut.getWorldPos(2, 2, 5);
					if (chunkBox.isInside(blockPos)) {
						this.spawnedWitch = true;
						Witch witchEntity = EntityType.WITCH.create(level.getLevel());
						assert witchEntity != null;
						witchEntity.setPersistenceRequired();
						witchEntity.moveTo((double) blockPos.getX() + 0.5, blockPos.getY(), (double) blockPos.getZ() + 0.5, 0.0F, 0.0F);
						witchEntity.finalizeSpawn(level, level.getCurrentDifficultyAt(blockPos), MobSpawnType.STRUCTURE, null, null);
						level.addFreshEntityWithPassengers(witchEntity);
					}
				}
				this.spawnCat(level, chunkBox);
			}
		}
    }

    @Shadow
    private void spawnCat(ServerLevelAccessor level, BoundingBox box) {
		throw new AssertionError("Mixin injection failed - Wilder Wild SwampHutPieceMixin.");
	}

}
