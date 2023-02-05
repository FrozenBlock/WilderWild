package net.frozenblock.wilderwild.mixin.server.general;

import net.frozenblock.wilderwild.misc.JellyfishBlockCollisions;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NaturalSpawner.class)
public class NaturalSpawnerMixin {

    @Inject(method = "isValidSpawnPostitionForType", at = @At(value = "RETURN", ordinal = 5, shift = At.Shift.BEFORE), cancellable = true)
	private static void wilderWild$isValidSpawnPostitionForType(ServerLevel level, MobCategory category, StructureManager structureManager, ChunkGenerator generator, MobSpawnSettings.SpawnerData data, BlockPos.MutableBlockPos pos, double distance, CallbackInfoReturnable<Boolean> info) {
		if (data.type == RegisterEntities.JELLYFISH) {
			info.setReturnValue(JellyfishBlockCollisions.noJellyCollision(level, null, data.type.getAABB((double) pos.getX() + 0.5, pos.getY(), (double) pos.getZ() + 0.5)));
		}
	}

}
