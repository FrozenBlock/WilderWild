package net.frozenblock.wilderwild.mixin;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.SculkCatalystBlock;
import net.minecraft.block.entity.SculkCatalystBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(SculkCatalystBlockEntity.class)
public class SculkCatalystBlockEntityMixin {

    /** @author FrozenBlock
     * @reason Fix annoying complications with Charges instantly dying off if the mob is near a non-spreadable block */
    @Overwrite
    public boolean listen(ServerWorld world, GameEvent.Message event) {
        SculkCatalystBlockEntity catalyst = SculkCatalystBlockEntity.class.cast(this);
        if (catalyst.isRemoved()) {
            return false;
        } else {
            GameEvent.Emitter emitter = event.getEmitter();
            if (event.getEvent() == GameEvent.ENTITY_DIE) {
                Entity var5 = emitter.sourceEntity();
                if (var5 instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity)var5;
                    if (!livingEntity.isExperienceDroppingDisabled()) {
                        int i = livingEntity.getXpToDrop();
                        if (livingEntity.shouldDropXp() && i > 0) {
                            catalyst.getSpreadManager().spread(new BlockPos(event.getEmitterPos()), i);
                        }
                        livingEntity.disableExperienceDropping();
                        LivingEntity livingEntity2 = livingEntity.getAttacker();
                        if (livingEntity2 instanceof ServerPlayerEntity serverPlayerEntity) {
                            DamageSource damageSource = livingEntity.getRecentDamageSource() == null ? DamageSource.player(serverPlayerEntity) : livingEntity.getRecentDamageSource();
                            Criteria.KILL_MOB_NEAR_SCULK_CATALYST.trigger(serverPlayerEntity, emitter.sourceEntity(), damageSource);
                        }
                        SculkCatalystBlock.bloom(world, catalyst.getPos(), catalyst.getCachedState(), world.getRandom());
                    } return true;
                }
            }
            return false;
        }
    }

}
