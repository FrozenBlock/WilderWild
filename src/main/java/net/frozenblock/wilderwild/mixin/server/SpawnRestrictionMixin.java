package net.frozenblock.wilderwild.mixin.server;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(SpawnRestriction.class)
public class SpawnRestrictionMixin {

    @Final
    @Shadow
    private static Map<EntityType<?>, SpawnRestriction.Entry> RESTRICTIONS;

    @Inject(method = "register", at = @At("HEAD"), cancellable = true)
    private static <T extends MobEntity> void register(EntityType<T> type, SpawnRestriction.Location location, Heightmap.Type heightmapType, SpawnRestriction.SpawnPredicate<T> predicate, CallbackInfo info) {
        if (type == EntityType.SLIME) {
            info.cancel();
            SpawnRestriction.Entry entry = RESTRICTIONS.put(type, new SpawnRestriction.Entry(heightmapType, SpawnRestriction.Location.NO_RESTRICTIONS, predicate));
            if (entry != null) {
                throw new IllegalStateException("Duplicate registration for type " + Registry.ENTITY_TYPE.getId(type));
            }
        }
    }

}
