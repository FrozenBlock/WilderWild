package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.ai.WardenMoveControl;
import net.frozenblock.wilderwild.entity.ai.WardenNavigation;
import net.frozenblock.wilderwild.entity.render.animations.WilderWarden;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Formatting;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.listener.GameEventListener;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WardenEntity.class)
public abstract class WardenEntityMixin extends HostileEntity implements WilderWarden {

    private final WardenEntity warden = WardenEntity.class.cast(this);

    @Inject(at = @At("HEAD"), method = "getDeathSound", cancellable = true)
    public void getDeathSound(CallbackInfoReturnable<SoundEvent> info) {
        String string = Formatting.strip(warden.getName().getString());
        boolean skipCheck = false;
        if (string != null) {
            if (string.equalsIgnoreCase("Osmiooo") || string.equalsIgnoreCase("Mossmio") || string.equalsIgnoreCase("kirby")) {
                warden.playSound(RegisterSounds.ENTITY_WARDEN_KIRBY_DEATH, 5.0F, 1.0F);
                skipCheck = true;
            }
        }
        if (!skipCheck) {
            if (!this.isSubmergedInWaterOrLava()) {
                warden.playSound(RegisterSounds.ENTITY_WARDEN_DYING, 5.0F, 1.0F);
            } else if (this.isSubmergedInWaterOrLava()) {
                warden.playSound(RegisterSounds.ENTITY_WARDEN_UNDERWATER_DYING, 0.75F, 1.0F);
            }
        }
        info.setReturnValue(SoundEvents.ENTITY_WARDEN_DEATH);
        info.cancel();
    }

    @Shadow
    public abstract Brain<WardenEntity> getBrain();

    @Shadow
    protected abstract void addDigParticles(AnimationState animationState);

    @Shadow
    protected abstract boolean isDiggingOrEmerging();

    protected WardenEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
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

    @Inject(at = @At("HEAD"), method = "initialize")
    public void initialize(ServerWorldAccess serverWorldAccess, LocalDifficulty localDifficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound nbtCompound, CallbackInfoReturnable<EntityData> info) {
        warden.getBrain().remember(MemoryModuleType.DIG_COOLDOWN, Unit.INSTANCE, 1200L);
        warden.getBrain().remember(MemoryModuleType.TOUCH_COOLDOWN, Unit.INSTANCE, WardenBrain.EMERGE_DURATION);
        if (spawnReason == SpawnReason.SPAWN_EGG && !this.isTouchingWaterOrLava()) { //still emerges when touching a liquid for some reason??
            warden.setPose(EntityPose.EMERGING);
            warden.getBrain().remember(MemoryModuleType.IS_EMERGING, Unit.INSTANCE, WardenBrain.EMERGE_DURATION);
            this.playSound(SoundEvents.ENTITY_WARDEN_AGITATED, 5.0F, 1.0F);
        }
    }

    @Inject(at = @At("HEAD"), method = "pushAway")
    protected void pushAway(Entity entity, CallbackInfo info) {
        if (!warden.getBrain().hasMemoryModule(MemoryModuleType.ATTACK_COOLING_DOWN) && !warden.getBrain().hasMemoryModule(MemoryModuleType.TOUCH_COOLDOWN) && !(entity instanceof WardenEntity) && !this.isDiggingOrEmerging() && !warden.isInPose(EntityPose.DYING) && !warden.isInPose(EntityPose.ROARING)) {
            if (!entity.isInvulnerable() && entity instanceof LivingEntity livingEntity) {
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

    @Inject(method = "accept", at = @At("HEAD"))
    private void accept(ServerWorld world, GameEventListener listener, BlockPos pos, GameEvent event, Entity entity, Entity sourceEntity, float distance, CallbackInfo ci) {
        WardenEntity warden = WardenEntity.class.cast(this);
        if (!warden.isDead()) {
            int additionalAnger = 0;
            if (world.getBlockState(pos).isOf(Blocks.SCULK_SENSOR)) {
                if (world.getBlockState(pos).get(RegisterProperties.HICCUPPING)) {
                    additionalAnger = 65;
                }
            }
            if (sourceEntity != null) {
                if (warden.isInRange(sourceEntity, 30.0D)) {
                    warden.increaseAngerAt(sourceEntity, additionalAnger, false);
                }
            } else {
                warden.increaseAngerAt(entity, additionalAnger, false);
            }
        }
    }

    @Inject(method = "onTrackedDataSet", at = @At("HEAD"), cancellable = true)
    public void onTrackedDataSet(TrackedData<?> data, CallbackInfo ci) {
        if (POSE.equals(data)) {
            if (warden.getPose() == EntityPose.DYING) {
                String string = Formatting.strip(warden.getName().getString());
                boolean skip = false;
                if (string != null) {
                    if (string.equalsIgnoreCase("Osmiooo") || string.equalsIgnoreCase("Mossmio") || string.equalsIgnoreCase("Kirby")) {
                        this.getKirbyDeathAnimationState().start(warden.age);
                        skip = true;
                        ci.cancel();
                    }
                }
                if (!skip) {
                    if (!this.isSubmergedInWaterOrLava()) {
                        this.getDyingAnimationState().start(warden.age);
                        ci.cancel();
                    } else {
                        this.getSwimmingDyingAnimationState().start(warden.age);
                        ci.cancel();
                    }
                }
            }
        }
    }

    private int deathTicks = 0;

    @Override
    public boolean isDead() {
        return super.isDead();
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
            this.world.addParticle(ParticleTypes.SCULK_CHARGE_POP, this.getParticleX(1.0), this.getRandomBodyY(), this.getParticleZ(1.0), d, e, f);
            this.world.addParticle(ParticleTypes.SCULK_SOUL, this.getParticleX(1.0), this.getRandomBodyY(), this.getParticleZ(1.0), d, e, f);
        }

    }

    @Override
    public void onDeath(DamageSource damageSource) {
        WardenEntity warden = WardenEntity.class.cast(this);
        super.onDeath(damageSource);
        warden.setAiDisabled(true);
    }

    @Override
    protected void updatePostDeath() {
        ++this.deathTicks;
        if (this.deathTicks == 35 && !warden.world.isClient()) {
            warden.deathTime = 35;
        }

        if (this.deathTicks == 53 && !warden.world.isClient()) {
            warden.world.sendEntityStatus(warden, EntityStatuses.ADD_DEATH_PARTICLES);
            warden.world.sendEntityStatus(warden, (byte) 69420);
        }

        if (this.deathTicks == 70 && !warden.world.isClient()) {
            warden.remove(Entity.RemovalReason.KILLED);
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo ci) {
        this.updateLeaningPitch();
        if (warden.getPose() == EntityPose.DYING) {
            this.addDigParticles(this.getDyingAnimationState());
        }
    }


    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    public void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo info) {
        nbt.putInt("death_ticks", this.deathTicks);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo info) {
        this.deathTicks = nbt.getInt("death_ticks");
    }

    private void updateLeaningPitch() {
        this.lastLeaningPitch = this.leaningPitch;
        if (this.isInSwimmingPose()) {
            this.leaningPitch = Math.min(1.0F, this.leaningPitch + 0.09F);
        } else {
            this.leaningPitch = Math.max(0.0F, this.leaningPitch - 0.09F);
        }

    }

    @Inject(method = "handleStatus", at = @At("HEAD"), cancellable = true)
    private void handleStatus(byte status, CallbackInfo ci) {
        if (status == (byte) 69420) {
            this.addAdditionalDeathParticles();
            ci.cancel();
        }
    }

    @Inject(at = @At("RETURN"), method = "createNavigation", cancellable = true)
    public void createNavigation(World world, CallbackInfoReturnable<EntityNavigation> info) {
        info.setReturnValue(new WardenNavigation(WardenEntity.class.cast(this), world));
        info.cancel();
    }

    @Override
    public void travel(Vec3d movementInput) {
        if (this.canMoveVoluntarily() && this.isTouchingWaterOrLava()) {
            this.updateVelocity(this.getMovementSpeed(), movementInput);
            this.move(MovementType.SELF, this.getVelocity());
            this.setVelocity(this.getVelocity().multiply(0.9));
            if (this.isSubmergedInWaterOrLava() && this.getMovementSpeed() > 0F) {
                warden.setPose(EntityPose.SWIMMING);
            }
        } else {
            super.travel(movementInput);
            if (!this.isSubmergedInWaterOrLava() && this.getMovementSpeed() <= 0F && !this.isDiggingOrEmerging() && !warden.isInPose(EntityPose.SNIFFING) && !warden.isInPose(EntityPose.DYING) && !warden.isInPose(EntityPose.ROARING)) {
                warden.setPose(EntityPose.STANDING);
            }
        }

    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void WardenEntity(EntityType<? extends HostileEntity> entityType, World world, CallbackInfo ci) {
        WardenEntity wardenEntity = WardenEntity.class.cast(this);
        wardenEntity.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
        wardenEntity.setPathfindingPenalty(PathNodeType.POWDER_SNOW, -1.0F);
        wardenEntity.setPathfindingPenalty(PathNodeType.DANGER_POWDER_SNOW, -1.0F);
        this.moveControl = new WardenMoveControl(wardenEntity, 3.0F, 26.0F, 0.13F, 1.0F, true);
    }

    @Override
    public boolean canBreatheInWater() {
        return true;
    }

    @Override
    public boolean isPushedByFluids() {
        return false;
    }

    @Override
    public SoundEvent getSwimSound() {
        return RegisterSounds.ENTITY_WARDEN_SWIM;
    }

    @Override
    public void swimUpward(TagKey<Fluid> fluid) {
    }

    public float getLeaningPitch(float tickDelta) {
        return MathHelper.lerp(tickDelta, this.lastLeaningPitch, this.leaningPitch);
    }

    @Override
    protected boolean updateWaterState() {
        this.fluidHeight.clear();
        warden.checkWaterState();
        boolean bl = warden.updateMovementInFluid(FluidTags.LAVA, 0.1D);
        if (this.isTouchingWaterOrLava()) {
            this.calculateDimensions();
            this.calculateBoundingBox();
            return true;
        }
        return bl;
    }

    private boolean isTouchingWaterOrLava() {
        return warden.isInsideWaterOrBubbleColumn() || warden.isInLava();
    }

    private boolean isSubmergedInWaterOrLava() {
        return warden.isSubmergedIn(FluidTags.WATER) || warden.isSubmergedIn(FluidTags.LAVA);
    }

    @Inject(method = "getDimensions", at = @At("RETURN"), cancellable = true)
    public void getDimensions(EntityPose pose, CallbackInfoReturnable<EntityDimensions> info) {
        if (this.isInSwimmingPose()) {
            info.setReturnValue(EntityDimensions.changing(warden.getType().getWidth(), 0.85F));
            info.cancel();
        }
        if (deathTicks > 0) {
            info.setReturnValue(EntityDimensions.fixed(warden.getType().getWidth(), 0.35F));
            info.cancel();
        }
    }
}
