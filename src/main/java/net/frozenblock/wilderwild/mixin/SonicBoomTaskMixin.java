package net.frozenblock.wilderwild.mixin;

import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.SonicBoomTask;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SonicBoomTask.class)
public class SonicBoomTaskMixin {
    @Shadow private static int RUN_TIME;

    @Overwrite
    public void keepRunning(ServerWorld serverWorld, WardenEntity wardenEntity, long l) {
        if (!wardenEntity.getBrain().hasMemoryModule(MemoryModuleType.SONIC_BOOM_SOUND_DELAY) && !wardenEntity.getBrain().hasMemoryModule(MemoryModuleType.SONIC_BOOM_SOUND_COOLDOWN)) {
            wardenEntity.getBrain().remember(MemoryModuleType.SONIC_BOOM_SOUND_COOLDOWN, Unit.INSTANCE, RUN_TIME - 34);
            wardenEntity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).filter((target) -> {
                return wardenEntity.isInRange(target, 15.0D, 20.0D);
            }).ifPresent((target) -> {
                Vec3d vec3d = wardenEntity.getPos().add(0.0D, 1.600000023841858D, 0.0D);
                Vec3d vec3d2 = target.getEyePos().subtract(vec3d);
                Vec3d vec3d3 = vec3d2.normalize();
                boolean blocked = false;
                for(int i = 1; i < MathHelper.floor(vec3d2.length()) + 7; ++i) {
                    Vec3d vec3d4 = vec3d.add(vec3d3.multiply(i));
                    serverWorld.spawnParticles(ParticleTypes.SONIC_BOOM, vec3d4.x, vec3d4.y, vec3d4.z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
                    BlockPos checkBlockedPos = new BlockPos(vec3d4);
                    if (serverWorld.getBlockState(checkBlockedPos).isOf(RegisterBlocks.ECHO_GLASS)) {
                        blocked=true;
                        i = MathHelper.floor(vec3d2.length()) + 10;
                        BlockState state = serverWorld.getBlockState(checkBlockedPos);
                        if (state.get(RegisterProperties.DAMAGE)==3) {
                            serverWorld.breakBlock(checkBlockedPos, false);
                        } else {
                            serverWorld.setBlockState(checkBlockedPos, state.with(RegisterProperties.DAMAGE, state.get(RegisterProperties.DAMAGE) + 1));
                        }
                    }
                }

                wardenEntity.playSound(SoundEvents.ENTITY_WARDEN_SONIC_BOOM, 3.0F, 1.0F);
                if (!blocked) {
                    target.damage(DamageSource.field_39043, 10.0F);
                    double d = 0.5D * (1.0D - target.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE));
                    double e = 2.5D * (1.0D - target.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE));
                    target.addVelocity(vec3d3.getX() * e, vec3d3.getY() * d, vec3d3.getZ() * e);
                }
            });
        }
    }

}
