package net.frozenblock.wilderwild.mixin.worldgen.general;

import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Cat;
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
	@Shadow
    private boolean spawnedCat;

	@Unique
    private final SwampHutPiece wilderWild$swampHut = SwampHutPiece.class.cast(this);

    @Inject(method = "postProcess", at = @At("HEAD"), cancellable = true)
    public void postProcess(WorldGenLevel level, StructureManager structureAccessor, ChunkGenerator chunkGenerator, RandomSource random, BoundingBox chunkBox, ChunkPos chunkPos, BlockPos pivot, CallbackInfo info) {
        info.cancel();
        if (wilderWild$swampHut.updateAverageGroundHeight(level, chunkBox, 0)) {
            wilderWild$swampHut.generateBox(level, chunkBox, 1, 1, 1, 5, 1, 7, RegisterBlocks.CYPRESS_PLANKS.defaultBlockState(), RegisterBlocks.CYPRESS_PLANKS.defaultBlockState(), false);
            wilderWild$swampHut.generateBox(level, chunkBox, 1, 4, 2, 5, 4, 7, RegisterBlocks.CYPRESS_PLANKS.defaultBlockState(), RegisterBlocks.CYPRESS_PLANKS.defaultBlockState(), false);
            wilderWild$swampHut.generateBox(level, chunkBox, 2, 1, 0, 4, 1, 0, RegisterBlocks.CYPRESS_PLANKS.defaultBlockState(), RegisterBlocks.CYPRESS_PLANKS.defaultBlockState(), false);
            wilderWild$swampHut.generateBox(level, chunkBox, 2, 2, 2, 3, 3, 2, RegisterBlocks.CYPRESS_PLANKS.defaultBlockState(), RegisterBlocks.CYPRESS_PLANKS.defaultBlockState(), false);
            wilderWild$swampHut.generateBox(level, chunkBox, 1, 2, 3, 1, 3, 6, RegisterBlocks.CYPRESS_PLANKS.defaultBlockState(), RegisterBlocks.CYPRESS_PLANKS.defaultBlockState(), false);
            wilderWild$swampHut.generateBox(level, chunkBox, 5, 2, 3, 5, 3, 6, RegisterBlocks.CYPRESS_PLANKS.defaultBlockState(), RegisterBlocks.CYPRESS_PLANKS.defaultBlockState(), false);
            wilderWild$swampHut.generateBox(level, chunkBox, 2, 2, 7, 4, 3, 7, RegisterBlocks.CYPRESS_PLANKS.defaultBlockState(), RegisterBlocks.CYPRESS_PLANKS.defaultBlockState(), false);
            wilderWild$swampHut.generateBox(level, chunkBox, 1, 0, 2, 1, 3, 2, Blocks.MANGROVE_LOG.defaultBlockState(), Blocks.MANGROVE_LOG.defaultBlockState(), false);
            wilderWild$swampHut.generateBox(level, chunkBox, 5, 0, 2, 5, 3, 2, Blocks.MANGROVE_LOG.defaultBlockState(), Blocks.MANGROVE_LOG.defaultBlockState(), false);
            wilderWild$swampHut.generateBox(level, chunkBox, 1, 0, 7, 1, 3, 7, Blocks.MANGROVE_LOG.defaultBlockState(), Blocks.MANGROVE_LOG.defaultBlockState(), false);
            wilderWild$swampHut.generateBox(level, chunkBox, 5, 0, 7, 5, 3, 7, Blocks.MANGROVE_LOG.defaultBlockState(), Blocks.MANGROVE_LOG.defaultBlockState(), false);
            wilderWild$swampHut.placeBlock(level, Blocks.MANGROVE_FENCE.defaultBlockState().setValue(BlockStateProperties.WEST, true).setValue(BlockStateProperties.EAST, true), 2, 3, 2, chunkBox);
            wilderWild$swampHut.placeBlock(level, Blocks.MANGROVE_FENCE.defaultBlockState().setValue(BlockStateProperties.WEST, true).setValue(BlockStateProperties.EAST, true), 3, 3, 7, chunkBox);
            wilderWild$swampHut.placeBlock(level, Blocks.AIR.defaultBlockState(), 1, 3, 4, chunkBox);
            wilderWild$swampHut.placeBlock(level, Blocks.AIR.defaultBlockState(), 5, 3, 4, chunkBox);
            wilderWild$swampHut.placeBlock(level, Blocks.AIR.defaultBlockState(), 5, 3, 5, chunkBox);
            wilderWild$swampHut.placeBlock(level, Blocks.POTTED_RED_MUSHROOM.defaultBlockState(), 1, 3, 5, chunkBox);
            wilderWild$swampHut.placeBlock(level, Blocks.CRAFTING_TABLE.defaultBlockState(), 3, 2, 6, chunkBox);
            wilderWild$swampHut.placeBlock(level, Blocks.CAULDRON.defaultBlockState(), 4, 2, 6, chunkBox);
            wilderWild$swampHut.placeBlock(level, Blocks.MANGROVE_FENCE.defaultBlockState().setValue(BlockStateProperties.NORTH, true), 1, 2, 1, chunkBox);
            wilderWild$swampHut.placeBlock(level, Blocks.MANGROVE_FENCE.defaultBlockState().setValue(BlockStateProperties.NORTH, true), 5, 2, 1, chunkBox);
            BlockState blockState = RegisterBlocks.CYPRESS_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH);
            BlockState blockState2 = RegisterBlocks.CYPRESS_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST);
            BlockState blockState3 = RegisterBlocks.CYPRESS_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST);
            BlockState blockState4 = RegisterBlocks.CYPRESS_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH);
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
                    wilderWild$swampHut.fillColumnDown(level, Blocks.MANGROVE_LOG.defaultBlockState(), j, -1, i, chunkBox);
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

    @Shadow
    private void spawnCat(ServerLevelAccessor level, BoundingBox box) {
        if (!this.spawnedCat) {
            BlockPos blockPos = wilderWild$swampHut.getWorldPos(2, 2, 5);
            if (box.isInside(blockPos)) {
                this.spawnedCat = true;
                Cat catEntity = EntityType.CAT.create(level.getLevel());
                assert catEntity != null;
                catEntity.setPersistenceRequired();
                catEntity.moveTo((double) blockPos.getX() + 0.5, blockPos.getY(), (double) blockPos.getZ() + 0.5, 0.0F, 0.0F);
                catEntity.finalizeSpawn(level, level.getCurrentDifficultyAt(blockPos), MobSpawnType.STRUCTURE, null, null);
                level.addFreshEntityWithPassengers(catEntity);
            }
        }
    }

}
