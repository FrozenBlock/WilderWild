package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.entity.ai.WardenMoveControl;
import net.frozenblock.wilderwild.entity.ai.WardenNavigation;
import net.frozenblock.wilderwild.entity.render.animations.WilderWarden;
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
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.Unit;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
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
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Warden.class)
public abstract class WardenEntityMixin extends Monster implements WilderWarden {

    private final Warden warden = Warden.class.cast(this);

    @Override
    public boolean isOsmiooo() {
        String name = ChatFormatting.stripFormatting(warden.getName().getString());
        return name != null && (name.equalsIgnoreCase("Osmiooo") || name.equalsIgnoreCase("Mossmio") || name.equalsIgnoreCase("Osmio"));
    }

    @Inject(at = @At("RETURN"), method = "getDeathSound")
    public void getDeathSound(CallbackInfoReturnable<SoundEvent> info) {
        boolean skipCheck = false;
        if (this.isOsmiooo()) {
            warden.playSound(RegisterSounds.ENTITY_WARDEN_KIRBY_DEATH, 5.0F, 1.0F);
            skipCheck = true;
        }
        if (!skipCheck) {
            if (!this.isSubmergedInWaterOrLava()) {
                warden.playSound(RegisterSounds.ENTITY_WARDEN_DYING, 5.0F, 1.0F);
            } else if (this.isSubmergedInWaterOrLava()) {
                warden.playSound(RegisterSounds.ENTITY_WARDEN_UNDERWATER_DYING, 0.75F, 1.0F);
            }
        }
    }

    @Shadow
    public abstract Brain<Warden> getBrain();

    @Shadow
    protected abstract void clientDiggingParticles(AnimationState animationState);

    @Shadow
    protected abstract boolean isDiggingOrEmerging();

    protected WardenEntityMixin(EntityType<? extends Monster> entityType, Level world) {
        super(entityType, world);
    }

    private final AnimationState dyingAnimationState = new AnimationState();

    private final AnimationState swimmingDyingAnimationState = new AnimationState();

    private final AnimationState kirbyDeathAnimationState = new AnimationState();

    @Override
    public AnimationState getDyingAnimationState() {
        return this.dyingAnimationState;
    }

    @Override
    public AnimationState getSwimmingDyingAnimationState() {
        return this.swimmingDyingAnimationState;
    }

    @Override
    public AnimationState getKirbyDeathAnimationState() {
        return this.kirbyDeathAnimationState;
    }

    private float leaningPitch;
    private float lastLeaningPitch;

    @Inject(at = @At("RETURN"), method = "finalizeSpawn")
    public void finalizeSpawn(ServerLevelAccessor serverWorldAccess, DifficultyInstance localDifficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData, @Nullable CompoundTag nbtCompound, CallbackInfoReturnable<SpawnGroupData> info) {
        if (spawnReason == MobSpawnType.SPAWN_EGG) {
            warden.setPose(Pose.EMERGING);
            warden.getBrain().setMemoryWithExpiry(MemoryModuleType.IS_EMERGING, Unit.INSTANCE, WardenAi.EMERGE_DURATION);
            this.playSound(SoundEvents.WARDEN_AGITATED, 5.0F, 1.0F);
        }
    }

    @Inject(at = @At("HEAD"), method = "doPush")
    protected void doPush(Entity entity, CallbackInfo info) {
        if (!warden.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_COOLING_DOWN) && !warden.getBrain().hasMemoryValue(MemoryModuleType.TOUCH_COOLDOWN) && !(entity instanceof Warden) && !this.isDiggingOrEmerging() && !warden.hasPose(Pose.DYING) && !warden.hasPose(Pose.ROARING)) {
            if (!entity.isInvulnerable() && entity instanceof LivingEntity livingEntity) {
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
    }

    @Inject(method = "onSignalReceive", at = @At("HEAD"))
    private void accept(ServerLevel world, GameEventListener listener, BlockPos pos, GameEvent event, Entity entity, Entity sourceEntity, float distance, CallbackInfo ci) {
        Warden warden = Warden.class.cast(this);
        if (!warden.isDeadOrDying()) {
            int additionalAnger = 0;
            if (world.getBlockState(pos).is(Blocks.SCULK_SENSOR)) {
                if (world.getBlockState(pos).getValue(RegisterProperties.HICCUPPING)) {
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

    @Inject(method = "onSyncedDataUpdated", at = @At("HEAD"), cancellable = true)
    public void onSyncedDataUpdated(EntityDataAccessor<?> data, CallbackInfo ci) {
        if (DATA_POSE.equals(data)) {
            if (warden.getPose() == Pose.DYING) {
                if (this.isOsmiooo()) {
                    this.getKirbyDeathAnimationState().start(warden.tickCount);
                } else {
                    if (!this.isSubmergedInWaterOrLava()) {
                        this.getDyingAnimationState().start(warden.tickCount);
                    } else {
                        this.getSwimmingDyingAnimationState().start(warden.tickCount);
                    }
                }
                ci.cancel();
            }
        }
    }

    private int deathTicks = 0;

    @Override
    public boolean isDeadOrDying() {
        return super.isDeadOrDying();
    }

    @Override
    public boolean isAlive() {
        return this.deathTicks < 70 && !this.isRemoved();
    }

    private void addAdditionalDeathParticles() {
        for (int i = 0; i < 20; ++i) {
            double d = this.random.nextGaussian() * 0.02;
            double e = this.random.nextGaussian() * 0.02;
            double f = this.random.nextGaussian() * 0.02;
            this.level.addParticle(ParticleTypes.SCULK_CHARGE_POP, this.getRandomX(1.0), this.getRandomY(), this.getRandomZ(1.0), d, e, f);
            this.level.addParticle(ParticleTypes.SCULK_SOUL, this.getRandomX(1.0), this.getRandomY(), this.getRandomZ(1.0), d, e, f);
        }

    }

    @Override
    public void die(DamageSource damageSource) {
        Warden warden = Warden.class.cast(this);
        super.die(damageSource);
        warden.getBrain().removeAllBehaviors();
    }

    @Override
    protected void tickDeath() {
        ++this.deathTicks;
        if (this.deathTicks == 35 && !warden.level.isClientSide()) {
            warden.deathTime = 35;
        }

        if (this.deathTicks == 53 && !warden.level.isClientSide()) {
            warden.level.broadcastEntityEvent(warden, EntityEvent.POOF);
            warden.level.broadcastEntityEvent(warden, (byte) 69420);
        }

        if (this.deathTicks == 70 && !warden.level.isClientSide()) {
            warden.remove(Entity.RemovalReason.KILLED);
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo ci) {
        this.updateSwimAmount();
        if (warden.getPose() == Pose.DYING) {
            this.clientDiggingParticles(this.getDyingAnimationState());
        }
    }


    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    public void addAdditionalSaveData(CompoundTag nbt, CallbackInfo info) {
        nbt.putInt("death_ticks", this.deathTicks);
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    public void readAdditionalSaveData(CompoundTag nbt, CallbackInfo info) {
        this.deathTicks = nbt.getInt("death_ticks");
    }

    private void updateSwimAmount() {
        this.lastLeaningPitch = this.leaningPitch;
        if (this.isVisuallySwimming()) {
            this.leaningPitch = Math.min(1.0F, this.leaningPitch + 0.09F);
        } else {
            this.leaningPitch = Math.max(0.0F, this.leaningPitch - 0.09F);
        }

    }

    @Inject(method = "handleEntityEvent", at = @At("HEAD"), cancellable = true)
    private void handleEntityEvent(byte status, CallbackInfo ci) {
        if (status == (byte) 69420) {
            this.addAdditionalDeathParticles();
            ci.cancel();
        }
    }

    @Inject(at = @At("RETURN"), method = "createNavigation", cancellable = true)
    public void createNavigation(Level world, CallbackInfoReturnable<PathNavigation> info) {
        info.setReturnValue(new WardenNavigation(Warden.class.cast(this), world));
        info.cancel();
    }

    @Override
    public void travel(Vec3 movementInput) {
        if (this.isEffectiveAi() && this.isTouchingWaterOrLava()) {
            this.moveRelative(this.getSpeed(), movementInput);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
            if (this.isSubmergedInWaterOrLava() && this.getSpeed() > 0F) {
                warden.setPose(Pose.SWIMMING);
            }
        } else {
            super.travel(movementInput);
            if (!this.isSubmergedInWaterOrLava() && this.getSpeed() <= 0F && !this.isDiggingOrEmerging() && !warden.hasPose(Pose.SNIFFING) && !warden.hasPose(Pose.DYING) && !warden.hasPose(Pose.ROARING)) {
                warden.setPose(Pose.STANDING);
            }
        }

    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void WardenEntity(EntityType<? extends Monster> entityType, Level world, CallbackInfo ci) {
        Warden wardenEntity = Warden.class.cast(this);
        wardenEntity.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        wardenEntity.setPathfindingMalus(BlockPathTypes.POWDER_SNOW, -1.0F);
        wardenEntity.setPathfindingMalus(BlockPathTypes.DANGER_POWDER_SNOW, -1.0F);
        this.moveControl = new WardenMoveControl(wardenEntity, 3.0F, 26.0F, 0.13F, 1.0F, true);
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    public SoundEvent getSwimSound() {
        return RegisterSounds.ENTITY_WARDEN_SWIM;
    }

    @Override
    public void jumpInLiquid(TagKey<Fluid> fluid) {
    }

    public float getSwimAmount(float tickDelta) {
        return Mth.lerp(tickDelta, this.lastLeaningPitch, this.leaningPitch);
    }

    @Override
    protected boolean updateInWaterStateAndDoFluidPushing() {
        this.fluidHeight.clear();
        warden.updateInWaterStateAndDoWaterCurrentPushing();
        boolean bl = warden.updateFluidHeightAndDoFluidPushing(FluidTags.LAVA, 0.1D);
        this.refreshDimensions();
        this.makeBoundingBox();
        return this.isTouchingWaterOrLava() || bl;
    }

    private boolean isTouchingWaterOrLava() {
        Warden warden = Warden.class.cast(this);
        return warden.isInWaterOrBubble() || warden.isInLava();
    }

    private boolean isSubmergedInWaterOrLava() {
        Warden warden = Warden.class.cast(this);
        return warden.isEyeInFluid(FluidTags.WATER) || warden.isEyeInFluid(FluidTags.LAVA);
    }

    @Inject(method = "getDimensions", at = @At("RETURN"), cancellable = true)
    public void getDimensions(Pose pose, CallbackInfoReturnable<EntityDimensions> info) {
        if (this.isVisuallySwimming()) {
            info.setReturnValue(EntityDimensions.scalable(warden.getType().getWidth(), 0.85F));
            info.cancel();
        }
        if (deathTicks > 0) {
            info.setReturnValue(EntityDimensions.fixed(warden.getType().getWidth(), 0.35F));
            info.cancel();
        }
    }
}
