package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.misc.interfaces.WilderEnderman;
import net.frozenblock.wilderwild.misc.config.ClothConfigInteractionHandler;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mob.class)
public abstract class MobMixin extends LivingEntity {

    @Shadow
    public void setPathfindingMalus(BlockPathTypes blockPathTypes, float f) {
		throw new AssertionError("Mixin injection failed - WilderWild MobMixin.");
    }

    private MobMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void addUnpassableRail(EntityType<? extends Mob> entityType, Level level, CallbackInfo ci) {
        if (ClothConfigInteractionHandler.unpassableRail()) {
            this.setPathfindingMalus(BlockPathTypes.UNPASSABLE_RAIL, 0.0F);
        }
    }

	//TODO: Test and see if this means water will also cause this to happen.
	@Inject(method = "playHurtSound", at = @At("HEAD"))
	private void startAngerLoop(DamageSource source, CallbackInfo ci) {
		final Mob mob = Mob.class.cast(this);
		if (mob instanceof EnderMan) {
			((WilderEnderman) mob).createAngerLoop();
		}
	}
}
