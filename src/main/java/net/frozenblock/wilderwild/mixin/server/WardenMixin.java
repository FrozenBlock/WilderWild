package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.entity.ai.WardenMoveControl;
import net.frozenblock.wilderwild.entity.ai.WardenNavigation;
import net.frozenblock.wilderwild.entity.render.animations.WilderWarden;
import net.frozenblock.wilderwild.misc.config.ClothConfigInteractionHandler;
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
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.Unit;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
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
import org.jetbrains.annotations.NotNull;
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

	@Unique
    @Override
    public boolean isOsmiooo() {
        Warden warden = Warden.class.cast(this);
        String name = ChatFormatting.stripFormatting(warden.getName().getString());
        return name != null && (name.equalsIgnoreCase("Osmiooo") || name.equalsIgnoreCase("Mossmio") || name.equalsIgnoreCase("Osmio"));
    }

    @Inject(at = @At("RETURN"), method = "getDeathSound")
    public void getDeathSound(CallbackInfoReturnable<SoundEvent> info) {
        Warden warden = Warden.class.cast(this);
        if (this.isOsmiooo()) {
            warden.playSound(RegisterSounds.ENTITY_WARDEN_KIRBY_DEATH, 5.0F, 1.0F);
        } else {
            if (ClothConfigInteractionHandler.wardenDyingAnimation()) {
                if (!this.isSubmergedInWaterOrLava()) {
                    warden.playSound(RegisterSounds.ENTITY_WARDEN_DYING, 5.0F, 1.0F);
                } else {
                    warden.playSound(RegisterSounds.ENTITY_WARDEN_UNDERWATER_DYING, 0.75F, 1.0F);
                }
            }
        }
    }

    @Shadow
    public Brain<Warden> getBrain() {
		throw new AssertionError("Mixin injection failed - WilderWild WardenMixin.");
    }

    @Shadow
    private void clientDiggingParticles(AnimationState animationState) {
		throw new AssertionError("Mixin injection failed - WilderWild WardenMixin.");
    }

    @Shadow
    private boolean isDiggingOrEmerging() {
		throw new AssertionError("Mixin injection failed - WilderWild WardenMixin.");
    }

	@Shadow
	private int tendrilAnimationO;

	@Shadow
	private int tendrilAnimation;

	@Shadow
	private int heartAnimationO;

	@Shadow
	private int heartAnimation;

	@Shadow
	public AnimationState emergeAnimationState;

	@Shadow
	public AnimationState diggingAnimationState;

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
    @Override
    public AnimationState getDyingAnimationState() {
        return this.wilderWild$dyingAnimationState;
    }

	@Unique
    @Override
    public AnimationState getSwimmingDyingAnimationState() {
        return this.wilderWild$swimmingDyingAnimationState;
    }

	@Unique
    @Override
    public AnimationState getKirbyDeathAnimationState() {
        return this.wilderWild$kirbyDeathAnimationState;
    }

	@Unique
    private float wilderWild$leaningPitch;
	@Unique
    private float wilderWild$lastLeaningPitch;

	@Unique
    private boolean wilderWild$pogSwimming;

    @Inject(at = @At("RETURN"), method = "finalizeSpawn")
    public void finalizeSpawn(ServerLevelAccessor serverLevelAccess, DifficultyInstance localDifficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData, @Nullable CompoundTag nbtCompound, CallbackInfoReturnable<SpawnGroupData> info) {
        Warden warden = Warden.class.cast(this);
        if (ClothConfigInteractionHandler.wardenEmergesFromEgg()) {
            if (spawnReason == MobSpawnType.SPAWN_EGG) {
                warden.setPose(Pose.EMERGING);
                warden.getBrain().setMemoryWithExpiry(MemoryModuleType.IS_EMERGING, Unit.INSTANCE, WardenAi.EMERGE_DURATION);
                this.playSound(SoundEvents.WARDEN_AGITATED, 5.0F, 1.0F);
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "doPush")
	private void doPush(Entity entity, CallbackInfo info) {
        Warden warden = Warden.class.cast(this);
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
    private void accept(ServerLevel level, GameEventListener listener, BlockPos pos, GameEvent event, Entity entity, Entity sourceEntity, float distance, CallbackInfo ci) {
        Warden warden = Warden.class.cast(this);
        if (!warden.isDeadOrDying()) {
            int additionalAnger = 0;
            if (level.getBlockState(pos).is(Blocks.SCULK_SENSOR)) {
                if (level.getBlockState(pos).getValue(RegisterProperties.HICCUPPING)) {
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
    private void onSyncedDataUpdated(EntityDataAccessor<?> data, CallbackInfo ci) {
        Warden warden = Warden.class.cast(this);
        if (ClothConfigInteractionHandler.wardenDyingAnimation() || this.isOsmiooo()) {
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
    }

	@Unique
    private int wilderWild$deathTicks = 0;

    @Override
    public boolean isAlive() {
        if (ClothConfigInteractionHandler.wardenDyingAnimation() || this.isOsmiooo()) {
            return this.wilderWild$deathTicks < 70 && !this.isRemoved();
        } else return super.isAlive();
    }

	@Unique
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
    public void die(@NotNull DamageSource damageSource) {
        Warden warden = Warden.class.cast(this);
        super.die(damageSource);
        if (ClothConfigInteractionHandler.wardenDyingAnimation() || this.isOsmiooo()) {
            warden.getBrain().removeAllBehaviors();
            warden.setNoAi(true);
        }
    }

    @Override
    protected void tickDeath() {
        Warden warden = Warden.class.cast(this);
        if (ClothConfigInteractionHandler.wardenDyingAnimation() || this.isOsmiooo()) {
            ++this.wilderWild$deathTicks;
            if (this.wilderWild$deathTicks == 35 && !warden.level.isClientSide()) {
                warden.deathTime = 35;
            }

            if (this.wilderWild$deathTicks == 53 && !warden.level.isClientSide()) {
                warden.level.broadcastEntityEvent(warden, EntityEvent.POOF);
                warden.level.broadcastEntityEvent(warden, (byte) 69420);
            }

            if (this.wilderWild$deathTicks == 70 && !warden.level.isClientSide()) {
                warden.remove(Entity.RemovalReason.KILLED);
            }
        } else super.tickDeath();
    }

	@Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;playLocalSound(DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FFZ)V"))
	private void osmioHeartbeat(Level level, double x, double y, double z, SoundEvent sound, SoundSource category, float volume, float pitch, boolean distanceDelay) {
		if (this.isOsmiooo()) {
			level.playLocalSound(x, y, z, RegisterSounds.ENTITY_WARDEN_OSMIOOO_HEARTBEAT, category, volume, pitch, distanceDelay);
		} else {
			level.playLocalSound(x, y, z, sound, category, volume, pitch, distanceDelay);
		}
	}

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo ci) {
        Warden warden = Warden.class.cast(this);
        this.updateSwimAmount();
        if (ClothConfigInteractionHandler.wardenDyingAnimation() || this.isOsmiooo()) {
            if (warden.getPose() == Pose.DYING) {
                this.clientDiggingParticles(this.getDyingAnimationState());
            }
        }
    }


    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    public void addAdditionalSaveData(CompoundTag nbt, CallbackInfo info) {
        nbt.putInt("wilderDeathTicks", this.wilderWild$deathTicks);
        nbt.putBoolean("pogSwimming", this.wilderWild$pogSwimming);
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    public void readAdditionalSaveData(CompoundTag nbt, CallbackInfo info) {
        this.wilderWild$deathTicks = nbt.getInt("wilderDeathTicks");
        this.wilderWild$pogSwimming = nbt.getBoolean("pogSwimming");
    }

	@Unique
    private void updateSwimAmount() {
        this.wilderWild$lastLeaningPitch = this.wilderWild$leaningPitch;
        if (this.isVisuallySwimming()) {
            this.wilderWild$leaningPitch = Math.min(1.0F, this.wilderWild$leaningPitch + 0.09F);
        } else {
            this.wilderWild$leaningPitch = Math.max(0.0F, this.wilderWild$leaningPitch - 0.09F);
        }

    }

    @Override
    public boolean isVisuallySwimming() {
        return this.wilderWild$pogSwimming || super.isVisuallySwimming();
    }

    @Inject(method = "handleEntityEvent", at = @At("HEAD"), cancellable = true)
    private void handleEntityEvent(byte status, CallbackInfo ci) {
        if (status == (byte) 69420) {
            this.addAdditionalDeathParticles();
            ci.cancel();
        }
    }

    @Inject(at = @At("RETURN"), method = "createNavigation", cancellable = true)
    public void createNavigation(Level level, CallbackInfoReturnable<PathNavigation> info) {
        info.setReturnValue(new WardenNavigation(Warden.class.cast(this), level));
    }

    @Override
    public void travel(@NotNull Vec3 movementInput) {
        Warden warden = Warden.class.cast(this);
        if (this.isEffectiveAi() && this.isTouchingWaterOrLava()) {
            this.moveRelative(this.getSpeed(), movementInput);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
            if (!this.isDiggingOrEmerging() && !warden.hasPose(Pose.SNIFFING) && !warden.hasPose(Pose.DYING) && !warden.hasPose(Pose.ROARING)) {
                if (this.isSubmergedInWaterOrLava()) {
                    warden.setPose(Pose.SWIMMING);
                } else {
                    warden.setPose(Pose.STANDING);
                }
            }

            this.wilderWild$pogSwimming = this.getFluidHeight(FluidTags.WATER) >= this.getEyeHeight(this.getPose()) * 0.75 || this.getFluidHeight(FluidTags.LAVA) >= this.getEyeHeight(this.getPose()) * 0.75;
        } else {
            super.travel(movementInput);
            this.wilderWild$pogSwimming = false;
        }

    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void WardenEntity(EntityType<? extends Monster> entityType, Level level, CallbackInfo ci) {
        Warden wardenEntity = Warden.class.cast(this);
        wardenEntity.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        wardenEntity.setPathfindingMalus(BlockPathTypes.POWDER_SNOW, -1.0F);
        wardenEntity.setPathfindingMalus(BlockPathTypes.DANGER_POWDER_SNOW, -1.0F);
        this.moveControl = new WardenMoveControl(wardenEntity, 0.05F, 80.0F, 0.13F, 1.0F, true);
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
    public void jumpInLiquid(@NotNull TagKey<Fluid> fluid) {
    }

	@Unique
    public float getSwimAmount(float tickDelta) {
        return Mth.lerp(tickDelta, this.wilderWild$lastLeaningPitch, this.wilderWild$leaningPitch);
    }

    @Override
    protected boolean updateInWaterStateAndDoFluidPushing() {
        Warden warden = Warden.class.cast(this);
        this.fluidHeight.clear();
        warden.updateInWaterStateAndDoWaterCurrentPushing();
        boolean bl = warden.updateFluidHeightAndDoFluidPushing(FluidTags.LAVA, 0.1D);
        return this.isTouchingWaterOrLava() || bl;
    }

	@Unique
    private boolean isTouchingWaterOrLava() {
        Warden warden = Warden.class.cast(this);
        return warden.isInWaterOrBubble() || warden.isInLava();
    }

	@Unique
    private boolean isSubmergedInWaterOrLava() {
        Warden warden = Warden.class.cast(this);
        return warden.isEyeInFluid(FluidTags.WATER) || warden.isEyeInFluid(FluidTags.LAVA);
    }

    @Inject(method = "getDimensions", at = @At("RETURN"), cancellable = true)
    public void getDimensions(Pose pose, CallbackInfoReturnable<EntityDimensions> info) {
        Warden warden = Warden.class.cast(this);
        if (this.isVisuallySwimming()) {
            info.setReturnValue(EntityDimensions.scalable(warden.getType().getWidth(), 0.85F));
        }
        if (ClothConfigInteractionHandler.wardenDyingAnimation() || this.isOsmiooo()) {
            if (wilderWild$deathTicks > 0) {
                info.setReturnValue(EntityDimensions.fixed(warden.getType().getWidth(), 0.35F));
            }
        }
    }

}
