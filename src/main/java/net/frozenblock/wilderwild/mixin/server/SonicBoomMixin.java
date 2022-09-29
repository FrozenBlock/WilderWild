package net.frozenblock.wilderwild.mixin.server;

import net.fabricmc.loader.api.FabricLoader;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.block.EchoGlassBlock;
import net.frozenblock.wilderwild.entity.render.animations.WilderWarden;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.Unit;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.warden.SonicBoom;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.level.ClipBlockStateContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = SonicBoom.class, priority = 1001)
public class SonicBoomMixin {
    @Final
    @Shadow
    private static int DURATION;

    @Inject(at = @At("HEAD"), method = "tick(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/monster/warden/Warden;J)V", cancellable = true)
    public void tick(ServerLevel serverLevel, Warden wardenEntity, long l, CallbackInfo info) {
        if (!FabricLoader.getInstance().isModLoaded("customsculk")) {
            if (!wardenEntity.getBrain().hasMemoryValue(MemoryModuleType.SONIC_BOOM_SOUND_DELAY) && !wardenEntity.getBrain().hasMemoryValue(MemoryModuleType.SONIC_BOOM_SOUND_COOLDOWN)) {
                wardenEntity.getBrain().setMemoryWithExpiry(MemoryModuleType.SONIC_BOOM_SOUND_COOLDOWN, Unit.INSTANCE, DURATION - 34);
                wardenEntity.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).filter((target) -> {
                    return wardenEntity.closerThan(target, 15.0D, 20.0D);
                }).ifPresent((target) -> {
                    Vec3 vec3d = wardenEntity.position().add(0.0D, 1.600000023841858D, 0.0D);
                    Vec3 vec3d2 = target.getEyePosition().subtract(vec3d);
                    Vec3 vec3d3 = vec3d2.normalize();

                    boolean blocked = false;
                    for (int i = 1; i < Mth.floor(vec3d2.length()) + 7; ++i) {
                        Vec3 vec3d4 = vec3d.add(vec3d3.scale(i));
                        serverLevel.sendParticles(ParticleTypes.SONIC_BOOM, vec3d4.x, vec3d4.y, vec3d4.z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
                        BlockPos hitPos = isOccluded(serverLevel, vec3d, vec3d4);
                        if (hitPos != null) {
                            BlockState state = serverLevel.getBlockState(hitPos);
                            if (state.is(RegisterBlocks.ECHO_GLASS)) {
                                i = Mth.floor(vec3d2.length()) + 10;
                                blocked = true;
                                EchoGlassBlock.damage(serverLevel, hitPos);
                            }
                        }
                    }

                    String string = ChatFormatting.stripFormatting(wardenEntity.getName().getString());
                    if (((WilderWarden) wardenEntity).isOsmiooo()) {
                        wardenEntity.playSound(RegisterSounds.ENTITY_WARDEN_BRAP, 3.0F, 1.0F);
                    } else {
                        wardenEntity.playSound(SoundEvents.WARDEN_SONIC_BOOM, 3.0F, 1.0F);
                    }
                    if (!blocked) {
                        target.hurt(DamageSource.sonicBoom(wardenEntity), 10.0F);
                        double d = 0.5D * (1.0D - target.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                        double e = 2.5D * (1.0D - target.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                        target.push(vec3d3.x() * e, vec3d3.y() * d, vec3d3.z() * e);
                    }
                });
            }
            info.cancel();
        }
    }

    private static BlockPos isOccluded(Level level, Vec3 start, Vec3 end) {
        Vec3 vec3d = new Vec3((double) Mth.floor(start.x) + 0.5D, (double) Mth.floor(start.y) + 0.5D, (double) Mth.floor(start.z) + 0.5D);
        Vec3 vec3d2 = new Vec3((double) Mth.floor(end.x) + 0.5D, (double) Mth.floor(end.y) + 0.5D, (double) Mth.floor(end.z) + 0.5D);
        BlockPos hitPos = null;
        boolean blocked = true;
        for (Direction direction : Direction.values()) {
            Vec3 vec3d3 = vec3d.relative(direction, 9.999999747378752E-6D);
            BlockHitResult hit = level.isBlockInLine(new ClipBlockStateContext(vec3d3, vec3d2, (state) -> state.is(RegisterBlocks.ECHO_GLASS)));
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
