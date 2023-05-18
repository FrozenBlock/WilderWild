/*
 * Copyright 2022-2023 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.mixin.server.warden;

import net.frozenblock.wilderwild.entity.render.animations.WilderWarden;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.misc.interfaces.SwimmingWarden;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Unit;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.warden.AngerLevel;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.monster.warden.WardenAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Warden.class, priority = 69420)
public final class WardenMixin extends Monster implements WilderWarden {

	@Shadow
	public Brain<Warden> getBrain() {
		throw new AssertionError("Mixin injection failed - Wilder Wild WardenMixin.");
	}

	@Shadow
	private void clientDiggingParticles(AnimationState animationState) {
		throw new AssertionError("Mixin injection failed - Wilder Wild WardenMixin.");
	}

	@Shadow
	private boolean isDiggingOrEmerging() {
		throw new AssertionError("Mixin injection failed - Wilder Wild WardenMixin.");
	}

	private WardenMixin(EntityType<? extends Monster> entityType, Level level) {
		super(entityType, level);
	}

	@Unique
	private final AnimationState wilderWild$dyingAnimationState = new AnimationState();

	@Unique
	private final AnimationState wilderWild$swimmingDyingAnimationState = new AnimationState();

	@Unique
	private final AnimationState wilderWild$kirbyDeathAnimationState = new AnimationState();

	@Unique
	private int wilderWild$deathTicks = 0;

	@Unique
	@Override
	public AnimationState wilderWild$getDyingAnimationState() {
		return this.wilderWild$dyingAnimationState;
	}

	@Unique
	@Override
	public AnimationState wilderWild$getSwimmingDyingAnimationState() {
		return this.wilderWild$swimmingDyingAnimationState;
	}

	@Unique
	@Override
	public AnimationState wilderWild$getKirbyDeathAnimationState() {
		return this.wilderWild$kirbyDeathAnimationState;
	}

	@Unique
	@Override
	public boolean wilderWild$isStella() {
		Warden warden = Warden.class.cast(this);
		String name = ChatFormatting.stripFormatting(warden.getName().getString());
		return name != null && (name.equalsIgnoreCase("Stella") || name.equalsIgnoreCase("Osmiooo") || name.equalsIgnoreCase("Mossmio") || name.equalsIgnoreCase("Osmio"));
	}

	@Inject(at = @At("RETURN"), method = "getDeathSound")
	public void wilderWild$getDeathSound(CallbackInfoReturnable<SoundEvent> info) {
		Warden warden = Warden.class.cast(this);
		if (this.wilderWild$isStella()) {
			warden.playSound(RegisterSounds.ENTITY_WARDEN_KIRBY_DEATH, 5.0F, 1.0F);
		} else {
			if (WilderSharedConstants.config().wardenDyingAnimation()) {
				if (warden instanceof SwimmingWarden swim && swim.wilderWild$isSubmergedInWaterOrLava()) {
					warden.playSound(RegisterSounds.ENTITY_WARDEN_UNDERWATER_DYING, 0.75F, 1.0F);
				} else {
					warden.playSound(RegisterSounds.ENTITY_WARDEN_DYING, 5.0F, 1.0F);
				}
			}
		}
	}

	@Inject(at = @At("RETURN"), method = "finalizeSpawn")
	public void wilderWild$finalizeSpawn(ServerLevelAccessor serverLevelAccess, DifficultyInstance localDifficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData, @Nullable CompoundTag nbtCompound, CallbackInfoReturnable<SpawnGroupData> info) {
		Warden warden = Warden.class.cast(this);
		if ((WilderSharedConstants.config().wardenEmergesFromEgg() && spawnReason == MobSpawnType.SPAWN_EGG) || (WilderSharedConstants.config().wardenEmergesFromCommand() && spawnReason == MobSpawnType.COMMAND)) {
			warden.setPose(Pose.EMERGING);
			warden.getBrain().setMemoryWithExpiry(MemoryModuleType.IS_EMERGING, Unit.INSTANCE, WardenAi.EMERGE_DURATION);
			this.playSound(SoundEvents.WARDEN_AGITATED, 5.0F, 1.0F);
		}
	}

	@Inject(at = @At("HEAD"), method = "doPush")
	private void wilderWild$doPush(Entity entity, CallbackInfo info) {
		Warden warden = Warden.class.cast(this);
		if (!warden.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_COOLING_DOWN)
				&& !warden.getBrain().hasMemoryValue(MemoryModuleType.TOUCH_COOLDOWN)
				&& !(entity instanceof Warden)
				&& entity instanceof LivingEntity livingEntity
				&& !entity.isInvulnerable()
				&& !warden.isDiggingOrEmerging()
				&& !warden.hasPose(Pose.DYING)
				&& !warden.hasPose(Pose.ROARING)
				&& WilderSharedConstants.config().wardenAttacksImmediately()
		) {
			if (!(entity instanceof Player player)) {
				warden.increaseAngerAt(entity, AngerLevel.ANGRY.getMinimumAnger() + 20, false);

				if (!livingEntity.isDeadOrDying() && warden.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).isEmpty()) {
					warden.setAttackTarget(livingEntity);
				}
			} else {
				if (!player.isCreative()) {
					warden.increaseAngerAt(entity, AngerLevel.ANGRY.getMinimumAnger() + 20, false);

					if (!player.isDeadOrDying() && warden.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).isEmpty()) {
						warden.setAttackTarget(player);
					}
				}
			}
		}
	}

	@Mixin(Warden.VibrationUser.class)
	public static class VibrationUserMixin {

		@Inject(method = "onReceiveVibration", at = @At("HEAD"))
		private void wilderWild$onReceiveVibration(ServerLevel world, BlockPos pos, GameEvent event, Entity sourceEntity, Entity entity, float distance, CallbackInfo ci) {
			Warden warden = Warden.class.cast(this);
			if (!warden.isDeadOrDying()) {
				int additionalAnger = 0;
				if (warden.level().getBlockState(pos).is(Blocks.SCULK_SENSOR)) {
					if (warden.level().getBlockState(pos).getValue(RegisterProperties.HICCUPPING)) {
						additionalAnger = 65;
					}
				}
				if (sourceEntity != null) {
					if (warden.closerThan(sourceEntity, 30.0D)) {
						warden.increaseAngerAt(sourceEntity, additionalAnger, false);
					}
				} else {
					warden.increaseAngerAt(entity, additionalAnger, false);
				}
			}
		}
	}

	@Inject(method = "onSyncedDataUpdated", at = @At("HEAD"), cancellable = true)
	private void wilderWild$onSyncedDataUpdated(EntityDataAccessor<?> data, CallbackInfo info) {
		Warden warden = Warden.class.cast(this);
		if (WilderSharedConstants.config().wardenDyingAnimation() || this.wilderWild$isStella()) {
			if (DATA_POSE.equals(data)) {
				if (warden.getPose() == Pose.DYING) {
					if (this.wilderWild$isStella()) {
						this.wilderWild$getKirbyDeathAnimationState().start(warden.tickCount);
					} else {
						if (warden instanceof SwimmingWarden swim && swim.wilderWild$isSubmergedInWaterOrLava()) {
							this.wilderWild$getSwimmingDyingAnimationState().start(warden.tickCount);
						} else {
							this.wilderWild$getDyingAnimationState().start(warden.tickCount);
						}
					}
					info.cancel();
				}
			}
		}
	}

	@Unique
	private void wilderWild$addAdditionalDeathParticles() {
		for (int i = 0; i < 20; ++i) {
			double d = this.random.nextGaussian() * 0.02;
			double e = this.random.nextGaussian() * 0.02;
			double f = this.random.nextGaussian() * 0.02;
			this.level().addParticle(ParticleTypes.SCULK_CHARGE_POP, this.getRandomX(1.0), this.getRandomY(), this.getRandomZ(1.0), d, e, f);
			this.level().addParticle(ParticleTypes.SCULK_SOUL, this.getRandomX(1.0), this.getRandomY(), this.getRandomZ(1.0), d, e, f);
		}
	}

	@Unique
	@Override
	public int wilderWild$getDeathTicks() {
		return this.wilderWild$deathTicks;
	}

	@Unique
	@Override
	public void wilderWild$setDeathTicks(int i) {
		this.wilderWild$deathTicks = i;
	}

	@Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;playLocalSound(DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FFZ)V"))
	private void wilderWild$stellaHeartbeat(Level level, double x, double y, double z, SoundEvent sound, SoundSource category, float volume, float pitch, boolean distanceDelay) {
		if (this.wilderWild$isStella()) {
			level.playLocalSound(x, y, z, RegisterSounds.ENTITY_WARDEN_STELLA_HEARTBEAT, category, volume, pitch, distanceDelay);
		} else {
			level.playLocalSound(x, y, z, sound, category, volume, pitch, distanceDelay);
		}
	}

	@Inject(method = "tick", at = @At("TAIL"))
	private void wilderWild$tick(CallbackInfo info) {
		Warden warden = Warden.class.cast(this);
		if (WilderSharedConstants.config().wardenDyingAnimation() || this.wilderWild$isStella()) {
			if (warden.getPose() == Pose.DYING) {
				this.clientDiggingParticles(this.wilderWild$getDyingAnimationState());
			}
		}
	}


	@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
	public void wilderWild$addAdditionalSaveData(CompoundTag nbt, CallbackInfo info) {
		nbt.putInt("wilderDeathTicks", this.wilderWild$deathTicks);
	}

	@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
	public void wilderWild$readAdditionalSaveData(CompoundTag nbt, CallbackInfo info) {
		this.wilderWild$deathTicks = nbt.getInt("wilderDeathTicks");
	}

	@Inject(method = "handleEntityEvent", at = @At("HEAD"), cancellable = true)
	private void wilderWild$handleEntityEvent(byte status, CallbackInfo ci) {
		if (status == (byte) 69420) {
			this.wilderWild$addAdditionalDeathParticles();
			ci.cancel();
		}
	}

	@Inject(method = "getDimensions", at = @At("RETURN"), cancellable = true)
	public void wilderWild$getDimensions(Pose pose, CallbackInfoReturnable<EntityDimensions> info) {
		Warden warden = Warden.class.cast(this);
		if (this.isVisuallySwimming()) {
			info.setReturnValue(EntityDimensions.scalable(warden.getType().getWidth(), 0.85F));
		}
		if (WilderSharedConstants.config().wardenDyingAnimation() || this.wilderWild$isStella()) {
			if (wilderWild$deathTicks > 0) {
				info.setReturnValue(EntityDimensions.fixed(warden.getType().getWidth(), 0.35F));
			}
		}
	}

}
