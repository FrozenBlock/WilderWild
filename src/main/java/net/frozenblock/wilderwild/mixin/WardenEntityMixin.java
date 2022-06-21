package net.frozenblock.wilderwild.mixin;

import com.mojang.logging.LogUtils;
import net.frozenblock.wilderwild.entity.render.animations.WardenAnimationInterface;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.Angriness;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.WardenBrain;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.listener.GameEventListener;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WardenEntity.class)
public abstract class WardenEntityMixin extends HostileEntity implements WardenAnimationInterface {
    @Shadow
    protected abstract SoundEvent getDeathSound();

    @Shadow
    protected abstract float getSoundVolume();

    @Shadow
    public abstract Brain<WardenEntity> getBrain();

    @Shadow
    public AnimationState emergingAnimationState;

    @Shadow
    public AnimationState diggingAnimationState;

    @Shadow
    public AnimationState roaringAnimationState;

    @Shadow
    public AnimationState sniffingAnimationState;

    @Shadow
    protected abstract void addDigParticles(AnimationState animationState);

    @Shadow public AnimationState chargingSonicBoomAnimationState;

    @Shadow public AnimationState attackingAnimationState;

    @Shadow private int tendrilPitch;

    protected WardenEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public AnimationState dyingAnimationState = new AnimationState();

    public AnimationState getDyingAnimationState() {
        return this.dyingAnimationState;
    }

    /*@Inject(at = @At("HEAD"), method = "isValidTarget", cancellable = true)
    public void isValidTarget(@Nullable Entity entity, CallbackInfoReturnable<Boolean> info) {
        if (entity instanceof SculkSensorTendrilEntity) {
            info.setReturnValue(false);
            info.cancel();
        }
    }*/

    @Inject(at = @At("HEAD"), method = "initialize")
    public void initialize(ServerWorldAccess serverWorldAccess, LocalDifficulty localDifficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound nbtCompound, CallbackInfoReturnable<EntityData> info) {
        WardenEntity entity = WardenEntity.class.cast(this);
        entity.getBrain().remember(MemoryModuleType.DIG_COOLDOWN, Unit.INSTANCE, 1200L);
        entity.getBrain().remember(MemoryModuleType.TOUCH_COOLDOWN, Unit.INSTANCE, WardenBrain.EMERGE_DURATION);
        if (spawnReason == SpawnReason.SPAWN_EGG || spawnReason == SpawnReason.COMMAND) {
            entity.setPose(EntityPose.EMERGING);
            entity.getBrain().remember(MemoryModuleType.IS_EMERGING, Unit.INSTANCE, WardenBrain.EMERGE_DURATION);
            entity.setPersistent();
        }
    }

    @Inject(at = @At("HEAD"), method = "pushAway")
    protected void pushAway(Entity entity, CallbackInfo info) {
        WardenEntity warden = WardenEntity.class.cast(this);
        if (!warden.getBrain().hasMemoryModule(MemoryModuleType.ATTACK_COOLING_DOWN) && !warden.getBrain().hasMemoryModule(MemoryModuleType.TOUCH_COOLDOWN) && !(entity instanceof WardenEntity) && !warden.isInPose(EntityPose.EMERGING) && !warden.isInPose(EntityPose.DIGGING) && !warden.isInPose(EntityPose.DYING)) {
            if (!entity.isInvulnerable()) {
                LivingEntity livingEntity = (LivingEntity) entity;
                if (!(entity instanceof PlayerEntity player)) {
                    warden.increaseAngerAt(entity, Angriness.ANGRY.getThreshold() + 20, false);

                    if (!livingEntity.isDead() && warden.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).isEmpty()) {
                        warden.updateAttackTarget(livingEntity);
                    }
                } else {
                    if (!player.isCreative()) {
                        warden.increaseAngerAt(entity, Angriness.ANGRY.getThreshold() + 20, false);

                        if (!player.isDead() && warden.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).isEmpty()) {
                            warden.updateAttackTarget(player);
                        }
                    }
                }
            }
        }
    }

    /**
     * @author FrozenBlock
     * @reason we need it to stop doing stuff when it dies lol
     */
    @Overwrite
    private boolean isDiggingOrEmerging() {
        return /*this.isInPose(EntityPose.DYING) || */this.isInPose(EntityPose.DIGGING) || this.isInPose(EntityPose.EMERGING);
    }

    @Inject(at = @At("HEAD"), method = "accept", cancellable = true)
    public void accept(ServerWorld world, GameEventListener listener, BlockPos pos, GameEvent event, @Nullable Entity entity, @Nullable Entity sourceEntity, float f, CallbackInfo info) {
        WardenEntity warden = WardenEntity.class.cast(this);
        int additionalAnger = 0;
        if (world.getBlockState(pos).isOf(Blocks.SCULK_SENSOR)) {
            if (!world.getBlockState(pos).get(RegisterProperties.NOT_HICCUPPING)) {
                additionalAnger = 65;
            }
        }
        warden.getBrain().remember(MemoryModuleType.VIBRATION_COOLDOWN, Unit.INSTANCE, 40L);
        world.sendEntityStatus(warden, (byte) 61);
        warden.playSound(SoundEvents.ENTITY_WARDEN_TENDRIL_CLICKS, 5.0F, warden.getSoundPitch());
        BlockPos blockPos = pos;
        if (sourceEntity != null) {
            if (warden.isInRange(sourceEntity, 30.0D)) {
                if (warden.getBrain().hasMemoryModule(MemoryModuleType.RECENT_PROJECTILE)) {
                    if (warden.isValidTarget(sourceEntity)) {
                        blockPos = sourceEntity.getBlockPos();
                    }

                    warden.increaseAngerAt(sourceEntity);
                    warden.increaseAngerAt(sourceEntity, additionalAnger, false);
                } else {
                    warden.increaseAngerAt(sourceEntity, 10, true);
                    warden.increaseAngerAt(sourceEntity, additionalAnger, false);
                }
            }

            warden.getBrain().remember(MemoryModuleType.RECENT_PROJECTILE, Unit.INSTANCE, 100L);
        } else {
            warden.increaseAngerAt(entity);
            warden.increaseAngerAt(entity, additionalAnger, false);
        }

        if (warden.getAngriness() != Angriness.ANGRY && (sourceEntity != null || warden.getAngerManager().getPrimeSuspect().map((suspect) -> {
            return suspect == entity;
        }).orElse(true))) {
            WardenBrain.lookAtDisturbance(warden, blockPos);
        }
        info.cancel();
    }

    /**
     * @author FrozenBlock
     * @reason HELP
     */
    @Overwrite
    public void onTrackedDataSet(TrackedData<?> data) {
        WardenEntity warden = WardenEntity.class.cast(this);
        if (POSE.equals(data)) {
            switch (this.getPose()) {
                case DYING -> this.getDyingAnimationState().start(warden.age);
                case EMERGING -> this.emergingAnimationState.start(warden.age);
                case DIGGING -> this.diggingAnimationState.start(warden.age);
                case ROARING -> this.roaringAnimationState.start(warden.age);
                case SNIFFING -> this.sniffingAnimationState.start(warden.age);
            }
        }

        super.onTrackedDataSet(data);
    }

    @Override
    public boolean isDead() {
        return this.deathTime >= 100;
    }

    @Override
    public boolean isAlive() {
        return super.isAlive();//this.deathTime < 100 && !this.isRemoved();
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        WardenEntity warden = WardenEntity.class.cast(this);
        if (!warden.isRemoved() && !warden.dead) {

            Entity entity = damageSource.getAttacker();
            LivingEntity livingEntity = warden.getPrimeAdversary();
            if (this.scoreAmount >= 0 && livingEntity != null) {
                livingEntity.updateKilledAdvancementCriterion(warden, this.scoreAmount, damageSource);
            }

            if (this.isSleeping()) {
                this.wakeUp();
            }

            if (!warden.world.isClient && this.hasCustomName()) {
                LogUtils.getLogger().info("Named entity {} died: {}", warden, warden.getDamageTracker().getDeathMessage().getString());
            }

            warden.dead = true;
            this.getDamageTracker().update();
            if (this.world instanceof ServerWorld) {
                if (entity == null || entity.onKilledOther((ServerWorld)warden.world, warden)) {
                    warden.emitGameEvent(GameEvent.ENTITY_DIE);
                    this.drop(damageSource);
                    this.onKilledBy(livingEntity);
                }

                warden.world.sendEntityStatus(warden, EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES);
            }
            warden.setHealth(1.0F);
            warden.setInvulnerable(true);
            warden.setPose(EntityPose.DYING);
            /*warden.getBrain().clear();
            warden.clearGoalsAndTasks();
            warden.setAiDisabled(true);
            */
        }
    }



    protected void updatePostDeath() {
        WardenEntity warden = WardenEntity.class.cast(this);
        ++warden.deathTime;
        if (warden.deathTime == 100 && !warden.world.isClient()) {
            warden.world.sendEntityStatus(warden, EntityStatuses.ADD_DEATH_PARTICLES);
            warden.remove(Entity.RemovalReason.KILLED);
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo ci) {
        WardenEntity warden = WardenEntity.class.cast(this);

        if (this.dead) {
            this.updatePostDeath();
        }

        switch (warden.getPose()) {
            case DYING:
                this.addDigParticles(this.getDyingAnimationState());
                break;
        }
    }
}
