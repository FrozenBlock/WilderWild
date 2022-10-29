package net.frozenblock.wilderwild.mixin.server;

import net.fabricmc.loader.api.FabricLoader;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.block.EchoGlassBlock;
import net.frozenblock.wilderwild.entity.render.animations.WilderWarden;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterSounds;
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
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = SonicBoom.class, priority = 1001)
public class SonicBoomMixin {

    @Final
    @Shadow
    private static int DURATION;

	@Shadow
	@Final
	private static int TICKS_BEFORE_PLAYING_SOUND;

	@Inject(at = @At("HEAD"), method = "tick(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/monster/warden/Warden;J)V", cancellable = true)
    public void tick(ServerLevel level, Warden owner, long gameTime, CallbackInfo info) {
        if (!FabricLoader.getInstance().isModLoaded("customsculk")) {
			owner.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).ifPresent(livingEntity -> owner.getLookControl().setLookAt(livingEntity.position()));
            if (!owner.getBrain().hasMemoryValue(MemoryModuleType.SONIC_BOOM_SOUND_DELAY)
				&& !owner.getBrain().hasMemoryValue(MemoryModuleType.SONIC_BOOM_SOUND_COOLDOWN)) {
				owner.getBrain().setMemoryWithExpiry(MemoryModuleType.SONIC_BOOM_SOUND_COOLDOWN, Unit.INSTANCE, DURATION - TICKS_BEFORE_PLAYING_SOUND);
                owner.getBrain()
						.getMemory(MemoryModuleType.ATTACK_TARGET)
						.filter((target) -> owner.closerThan(target, 15.0D, 20.0D))
						.ifPresent((target) -> {
							Vec3 vec3 = owner.position().add(0F, 1.6F, 0F);
							Vec3 vec32 = target.getEyePosition().subtract(vec3);
							Vec3 vec33 = vec32.normalize();

							boolean blocked = false;
							for (int i = 1; i < Mth.floor(vec32.length()) + 7; ++i) {
								Vec3 vec34 = vec3.add(vec33.scale(i));
								level.sendParticles(ParticleTypes.SONIC_BOOM, vec34.x, vec34.y, vec34.z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
								BlockPos hitPos = isOccluded(level, vec3, vec34);
								if (hitPos != null) {
									BlockState state = level.getBlockState(hitPos);
									if (state.is(RegisterBlocks.ECHO_GLASS)) {
										i = Mth.floor(vec32.length()) + 10;
										blocked = true;
										EchoGlassBlock.damage(level, hitPos);
									}
								}
							}

							if (((WilderWarden) owner).isOsmiooo()) {
								owner.playSound(RegisterSounds.ENTITY_WARDEN_BRAP, 3.0F, 1.0F);
							} else {
								owner.playSound(SoundEvents.WARDEN_SONIC_BOOM, 3.0F, 1.0F);
							}
							if (!blocked) {
								target.hurt(DamageSource.sonicBoom(owner), 10.0F);
								double verticalKnockback = 0.5D * (1.0D - target.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
								double horizontalKnockback = 2.5D * (1.0D - target.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
								target.push(vec33.x() * horizontalKnockback, vec33.y() * verticalKnockback, vec33.z() * horizontalKnockback);
							}
                });
            }
            info.cancel();
        }
    }

	@Unique
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
            WilderWild.log("Warden Sonic Boom Blocked @ " + hitPos, WilderSharedConstants.UNSTABLE_LOGGING);
            return hitPos;
        } else {
            return null;
        }
    }

}
