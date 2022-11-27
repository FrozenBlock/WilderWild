package net.frozenblock.wilderwild.mixin.server;

import java.util.function.Consumer;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.block.EchoGlassBlock;
import net.frozenblock.wilderwild.entity.render.animations.WilderWarden;
import net.frozenblock.wilderwild.misc.interfaces.WilderSonicBoom;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.warden.SonicBoom;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.level.ClipBlockStateContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value = SonicBoom.class, priority = 1001)
public class SonicBoomMixin implements WilderSonicBoom {

	@Unique
	private static SonicBoom wilderWild$currentBoom;

	@Unique
	private boolean wilderWild$particlesEnded = false;

	@Unique
	private Vec3 wilderwild$particlePos = null;

	@Unique
	private Vec3 wilderWild$vec32 = null;

	@ModifyArg(method = "tick(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/monster/warden/Warden;J)V", at = @At(value = "INVOKE", target = "Ljava/util/Optional;ifPresent(Ljava/util/function/Consumer;)V", ordinal = 1))
	private Consumer<? super LivingEntity> setCurrent(Consumer<? super LivingEntity> original) {
		return target -> {
			wilderWild$currentBoom = SonicBoom.class.cast(this);
			original.accept(target);
		};
	}

	/*@ModifyConstant(method = "m_ehrxwrfs", constant = @Constant(intValue = 1))
	private static int sus(int original) {
		var vec32 = ((WilderSonicBoom) wilderWild$currentBoom).vec32();
		if (((WilderSonicBoom) wilderWild$currentBoom).particlesEnded()) {
			return Mth.floor(vec32.length()) + 10;
		}
		return original;
	}*/

	@ModifyVariable(method = {"m_ehrxwrfs","method_43265","lambda$tick$2"}, at = @At(value = "CONSTANT", args = "intValue=1", shift = At.Shift.BY, by = 3), require = 1)
	private static int modifyInt(int original) {
		var vec32 = ((WilderSonicBoom) wilderWild$currentBoom).vec32();
		if (((WilderSonicBoom) wilderWild$currentBoom).particlesEnded()) {
			return Mth.floor(vec32.length()) + 10;
		}
		return original;
	}

	@ModifyVariable(method = {"m_ehrxwrfs","method_43265","lambda$tick$2"}, at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/phys/Vec3;add(Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;"), ordinal = 0, require = 1)
	private static Vec3 modifyVec(Vec3 value) {
		((WilderSonicBoom) wilderWild$currentBoom).setVec32(value);
		return value;
	}

	@Inject(method = "stop(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/monster/warden/Warden;J)V", at = @At("TAIL"))
	private void reset(ServerLevel level, Warden entity, long gameTime, CallbackInfo ci) {
		this.wilderWild$particlesEnded = false;
		this.wilderwild$particlePos = null;
	}

	@Inject(method = {"m_ehrxwrfs","method_43265","lambda$tick$2"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;sendParticles(Lnet/minecraft/core/particles/ParticleOptions;DDDIDDDD)I", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD, require = 1)
	private static void stopParticles(Warden warden, ServerLevel level, LivingEntity livingEntity, CallbackInfo ci, Vec3 vec3, Vec3 vec32, Vec3 vec33, int i, Vec3 vec34) {
		BlockPos hitPos = isOccluded(level, vec3, vec34);
		if (hitPos != null) {
			BlockState state = level.getBlockState(hitPos);
			if (state.is(RegisterBlocks.ECHO_GLASS)) {
				((WilderSonicBoom) wilderWild$currentBoom).endParticles();
			}
		}
	}


	@Inject(method = {"m_ehrxwrfs","method_43265","lambda$tick$2"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true, require = 1)
	private static void tick(Warden warden, ServerLevel level, LivingEntity livingEntity, CallbackInfo ci, Vec3 vec3, Vec3 vec32, Vec3 vec33) {
		boolean blocked = false;
		for (int i = 1; i < Mth.floor(vec32.length()) + 7; ++i) {
			Vec3 vec34 = vec3.add(vec33.scale(i));
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

		if (blocked) {
			ci.cancel();
		}
	}

	@Redirect(method = {"m_ehrxwrfs","method_43265","lambda$tick$2"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/monster/warden/Warden;playSound(Lnet/minecraft/sounds/SoundEvent;FF)V"), require = 1)
	private static void modifySound(Warden warden, SoundEvent soundEvent, float volume, float pitch) {
		if (((WilderWarden) warden).isOsmiooo()) {
			warden.playSound(RegisterSounds.ENTITY_WARDEN_BRAP, volume, pitch);
		} else {
			warden.playSound(soundEvent, volume, pitch);
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
            WilderWild.log("Warden Sonic Boom Blocked @ " + hitPos, WilderWild.UNSTABLE_LOGGING);
            return hitPos;
        } else {
            return null;
        }
    }

	@Unique
	@Override
	public Vec3 particlePos() {
		return this.wilderwild$particlePos;
	}

	@Unique
	@Override
	public void setParticlePos(Vec3 pos) {
		this.wilderwild$particlePos = pos;
	}

	@Unique
	@Override
	public Vec3 vec32() {
		return this.wilderWild$vec32;
	}

	@Unique
	@Override
	public void setVec32(Vec3 vec32) {
		this.wilderWild$vec32 = vec32;
	}

	@Unique
	@Override
	public boolean particlesEnded() {
		return this.wilderWild$particlesEnded;
	}

	@Unique
	@Override
	public void endParticles() {
		this.wilderWild$particlesEnded = true;
	}
}
