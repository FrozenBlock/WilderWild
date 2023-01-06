package net.frozenblock.wilderwild.mixin.server.general;

import net.frozenblock.wilderwild.misc.config.ClothConfigInteractionHandler;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mob.class)
public abstract class MobMixin {

    @Shadow
    public abstract void setPathfindingMalus(BlockPathTypes nodeType, float malus);

    @Inject(method = "<init>", at = @At("TAIL"))
    private void addUnpassableRail(EntityType<? extends Mob> entityType, Level level, CallbackInfo ci) {
        if (ClothConfigInteractionHandler.unpassableRail()) {
            this.setPathfindingMalus(BlockPathTypes.UNPASSABLE_RAIL, 0.0F);
        }
    }
}
