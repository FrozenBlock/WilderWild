package net.frozenblock.wilderwild.mixin.server.general;

import net.frozenblock.wilderwild.block.AlgaeBlock;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Mob.class)
public class MobMixin {

    @Shadow
    public void setPathfindingMalus(BlockPathTypes nodeType, float malus) {
		throw new AssertionError("Mixin injection failed - Wilder Wild MobMixin.");
	}

    @Inject(method = "<init>", at = @At("TAIL"))
    private void wilderWild$addUnpassableRail(EntityType<? extends Mob> entityType, Level level, CallbackInfo info) {
        if (WilderSharedConstants.CONFIG().unpassableRail()) {
            this.setPathfindingMalus(BlockPathTypes.UNPASSABLE_RAIL, 0.0F);
        }
    }

	@Inject(method = "checkSpawnObstruction", at = @At("HEAD"), cancellable = true)
	public void wilderWild$checkSpawnObstruction(LevelReader level, CallbackInfoReturnable<Boolean> info) {
		if (Mob.class.cast(this) instanceof Slime slime) {
			info.setReturnValue((!level.containsAnyLiquid(slime.getBoundingBox()) || AlgaeBlock.isAlgaeNearbyForSlimeSpawn(slime.getLevel(), slime.blockPosition(), 1)) && level.isUnobstructed(slime));
		}
	}

}
