package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NaturalSpawner.class)
public class NatrualSpawnerMixin {

    @Inject(method = "isValidSpawnPostitionForType", at = @At("HEAD"), cancellable = true)
    private static void isValidSpawnPostitionForType(ServerLevel serverLevel, MobCategory mobCategory, StructureManager structureManager, ChunkGenerator chunkGenerator, MobSpawnSettings.SpawnerData spawnerData, BlockPos.MutableBlockPos mutableBlockPos, double d, CallbackInfoReturnable<Boolean> info) {
        EntityType<?> entityType = spawnerData.type;
        if (entityType == RegisterEntities.JELLYFISH) {
            if (entityType.getCategory() == MobCategory.MISC) {
                info.cancel();
                info.setReturnValue(false);
            }
            if (!entityType.canSpawnFarFromPlayer() && d > (double) (entityType.getCategory().getDespawnDistance() * entityType.getCategory().getDespawnDistance())) {
                info.cancel();
                info.setReturnValue(false);
            }
            if (!entityType.canSummon() || !canSpawnMobAt(serverLevel, structureManager, chunkGenerator, mobCategory, spawnerData, mutableBlockPos)) {
                info.cancel();
                info.setReturnValue(false);
            }
            SpawnPlacements.Type type = SpawnPlacements.getPlacementType(entityType);
            if (!NaturalSpawner.isSpawnPositionOk(type, serverLevel, mutableBlockPos, entityType)) {
                info.cancel();
                info.setReturnValue(false);
            }
            if (!SpawnPlacements.checkSpawnRules(entityType, serverLevel, MobSpawnType.NATURAL, mutableBlockPos, serverLevel.random)) {
                info.cancel();
                info.setReturnValue(false);
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
