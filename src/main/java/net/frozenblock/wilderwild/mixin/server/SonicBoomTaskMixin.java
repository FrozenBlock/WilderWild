package net.frozenblock.wilderwild.mixin.server;

import net.fabricmc.loader.api.FabricLoader;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.block.EchoGlassBlock;
import net.frozenblock.wilderwild.entity.render.animations.WilderWarden;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.SonicBoomTask;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Formatting;
import net.minecraft.util.Unit;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockStateRaycastContext;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SonicBoomTask.class)
public class SonicBoomTaskMixin {
    @Final
    @Shadow
    private static int RUN_TIME;

    @Inject(at = @At("HEAD"), method = "keepRunning(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/mob/WardenEntity;J)V", cancellable = true)
    public void keepRunning(ServerWorld serverWorld, WardenEntity wardenEntity, long l, CallbackInfo info) {
        if (FabricLoader.getInstance().getModContainer("customsculk").isEmpty()) {
            if (!wardenEntity.getBrain().hasMemoryModule(MemoryModuleType.SONIC_BOOM_SOUND_DELAY) && !wardenEntity.getBrain().hasMemoryModule(MemoryModuleType.SONIC_BOOM_SOUND_COOLDOWN)) {
                wardenEntity.getBrain().remember(MemoryModuleType.SONIC_BOOM_SOUND_COOLDOWN, Unit.INSTANCE, RUN_TIME - 34);
                wardenEntity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).filter((target) -> {
                    return wardenEntity.isInRange(target, 15.0D, 20.0D);
                }).ifPresent((target) -> {
                    Vec3d vec3d = wardenEntity.getPos().add(0.0D, 1.600000023841858D, 0.0D);
                    Vec3d vec3d2 = target.getEyePos().subtract(vec3d);
                    Vec3d vec3d3 = vec3d2.normalize();

                    boolean blocked = false;
                    for (int i = 1; i < MathHelper.floor(vec3d2.length()) + 7; ++i) {
                        Vec3d vec3d4 = vec3d.add(vec3d3.multiply(i));
                        serverWorld.spawnParticles(ParticleTypes.SONIC_BOOM, vec3d4.x, vec3d4.y, vec3d4.z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
                        BlockPos hitPos = isOccluded(serverWorld, vec3d, vec3d4);
                        if (hitPos != null) {
                            BlockState state = serverWorld.getBlockState(hitPos);
                            if (state.isOf(RegisterBlocks.ECHO_GLASS)) {
                                i = MathHelper.floor(vec3d2.length()) + 10;
                                blocked = true;
                                EchoGlassBlock.damage(serverWorld, hitPos);
                            }
                        }
                    }

                    String string = Formatting.strip(wardenEntity.getName().getString());
                    if (((WilderWarden) wardenEntity).isOsmiooo()) {
                        wardenEntity.playSound(RegisterSounds.ENTITY_WARDEN_BRAP, 3.0F, 1.0F);
                    } else {
                        wardenEntity.playSound(SoundEvents.ENTITY_WARDEN_SONIC_BOOM, 3.0F, 1.0F);
                    }
                    if (!blocked) {
                        target.damage(DamageSource.sonicBoom(wardenEntity), 10.0F);
                        double d = 0.5D * (1.0D - target.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE));
                        double e = 2.5D * (1.0D - target.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE));
                        target.addVelocity(vec3d3.getX() * e, vec3d3.getY() * d, vec3d3.getZ() * e);
                    }
                });
            }
            info.cancel();
        }
    }

    private static BlockPos isOccluded(World world, Vec3d start, Vec3d end) {
        Vec3d vec3d = new Vec3d((double) MathHelper.floor(start.x) + 0.5D, (double) MathHelper.floor(start.y) + 0.5D, (double) MathHelper.floor(start.z) + 0.5D);
        Vec3d vec3d2 = new Vec3d((double) MathHelper.floor(end.x) + 0.5D, (double) MathHelper.floor(end.y) + 0.5D, (double) MathHelper.floor(end.z) + 0.5D);
        BlockPos hitPos = null;
        boolean blocked = true;
        for (Direction direction : Direction.values()) {
            Vec3d vec3d3 = vec3d.withBias(direction, 9.999999747378752E-6D);
            BlockHitResult hit = world.raycast(new BlockStateRaycastContext(vec3d3, vec3d2, (state) -> state.isOf(RegisterBlocks.ECHO_GLASS)));
            if (hit.getType() != HitResult.Type.BLOCK) {
                blocked = false;
            } else {
                hitPos = hit.getBlockPos();
            }
        }
        if (blocked) {
            WilderWild.log("Warden Sonic Boom Blocked @ " + hitPos, WilderWild.UNSTABLE_LOGGING);
            return hitPos;
        } else {
            return null;
        }
    }

}
