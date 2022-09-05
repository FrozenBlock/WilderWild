package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NaturalSpawner.class)
public class NaturalSpawnerMixin {

    @Inject(method = "isValidSpawnPostitionForType", at = @At("HEAD"), cancellable = true)
    private static void isValidSpawnPostitionForType(ServerLevel serverLevel, MobCategory mobCategory, StructureManager structureManager, ChunkGenerator chunkGenerator, MobSpawnSettings.SpawnerData spawnerData, BlockPos.MutableBlockPos mutableBlockPos, double d, CallbackInfoReturnable<Boolean> info) {
        EntityType<?> entityType = spawnerData.type;
        if (entityType == RegisterEntities.JELLYFISH) {
            if (entityType.getCategory() == MobCategory.MISC) {
                info.cancel();
                info.setReturnValue(false);
                return;
            }
            if (!entityType.canSpawnFarFromPlayer() && d > (double) (entityType.getCategory().getDespawnDistance() * entityType.getCategory().getDespawnDistance())) {
                info.cancel();
                info.setReturnValue(false);
                return;
            }
            if (!entityType.canSummon() || !canSpawnMobAt(serverLevel, structureManager, chunkGenerator, mobCategory, spawnerData, mutableBlockPos)) {
                info.cancel();
                info.setReturnValue(false);
                return;
            }
            SpawnPlacements.Type type = SpawnPlacements.getPlacementType(entityType);
            if (!NaturalSpawner.isSpawnPositionOk(type, serverLevel, mutableBlockPos, entityType)) {
                info.cancel();
                info.setReturnValue(false);
                return;
            }
            if (!SpawnPlacements.checkSpawnRules(entityType, serverLevel, MobSpawnType.NATURAL, mutableBlockPos, serverLevel.random)) {
                info.cancel();
                info.setReturnValue(false);
                return;
            }
            info.cancel();
            info.setReturnValue(true);
        }
    }

    @Shadow
    private static boolean canSpawnMobAt(ServerLevel serverLevel, StructureManager structureManager, ChunkGenerator chunkGenerator, MobCategory mobCategory, MobSpawnSettings.SpawnerData spawnerData, BlockPos blockPos) {
        return true;
    }

}
