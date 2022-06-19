package net.frozenblock.wilderwild.mixin;

import net.minecraft.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityType.class)
public class EntityTypeMixin {

    /*@Inject(at = @At("HEAD"), method = "register", cancellable = true)
    private static <T extends Entity> void register(String id, EntityType.Builder<T> type, CallbackInfoReturnable<EntityType<T>> info) {
        if (Objects.equals(id, "tadpole")) {
            info.setReturnValue(Registry.register(Registry.ENTITY_TYPE, id, ((EntityType.Builder<T>) EntityType.Builder.create(TadpoleEntity::new, SpawnGroup.WATER_CREATURE).setDimensions(TadpoleEntity.WIDTH, TadpoleEntity.HEIGHT).maxTrackingRange(10)).build(id)));
            info.cancel();
        }
    }*/

}
