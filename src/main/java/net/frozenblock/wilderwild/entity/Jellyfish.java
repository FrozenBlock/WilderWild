package net.frozenblock.wilderwild.entity;

import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Jellyfish extends AbstractFish {
    public float xBodyRot;
    public float xBodyRotO;
    public float zBodyRot;
    public float zBodyRotO;
    public float tentacleMovement;
    public float oldTentacleMovement;
    public float tentacleAngle;
    public float oldTentacleAngle;
    private float speed;
    private float tentacleSpeed;
    private float rotateSpeed;
    private float tx;
    private float ty;
    private float tz;

    private static final EntityDataAccessor<Integer> INHALE_TICKS = SynchedEntityData.defineId(Jellyfish.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PREV_INHALE_TICKS = SynchedEntityData.defineId(Jellyfish.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> INHALE_LENGTH = SynchedEntityData.defineId(Jellyfish.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PREV_PUSH_TICKS = SynchedEntityData.defineId(Jellyfish.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PUSH_TICKS = SynchedEntityData.defineId(Jellyfish.class, EntityDataSerializers.INT);

    public Vec3 target;
    public Vec3 preparedMovement;

    public Jellyfish(EntityType<? extends Jellyfish> entityType, Level level) {
        super(entityType, level);
        this.random.setSeed(this.getId());
        this.tentacleSpeed = 1.0f / (this.random.nextFloat() + 1.0f) * 0.2f;
    }

    @Override
    public void playerTouch(@NotNull Player player) {
        if (player instanceof ServerPlayer && player.hurt(DamageSource.mobAttack(this), 3)) {
            if (!this.isSilent()) {
                ((ServerPlayer)player).connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.PUFFER_FISH_STING, 0.0F));
            }

            player.addEffect(new MobEffectInstance(MobEffects.POISON, 300, 0), this);
        }

    }

    public static boolean canSpawn(EntityType<Jellyfish> type, ServerLevelAccessor world, MobSpawnType spawnReason, BlockPos pos, RandomSource random) {
        if (world.getBiome(pos).is(RegisterWorldgen.JELLYFISH_CAVES)) {
            return pos.getY() <= world.getSeaLevel() - 33
                    && world.getRawBrightness(pos, 0) <= 6
                    && world.getBlockState(pos).is(Blocks.WATER);
        }
        return false;
    }

    public static AttributeSupplier.Builder addAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 3.0D).add(Attributes.MOVEMENT_SPEED, 0.05F).add(Attributes.FLYING_SPEED, 0.05F).add(Attributes.FOLLOW_RANGE, 32);
    }

    public int getInhaleTicks() {
        return this.entityData.get(INHALE_TICKS);
    }

    public void setInhaleTicks(int value) {
        this.entityData.set(INHALE_TICKS, value);
    }

    public int getPrevInhaleTicks() {
        return this.entityData.get(PREV_INHALE_TICKS);
    }

    public void setPrevInhaleTicks(int value) {
        this.entityData.set(PREV_INHALE_TICKS, value);
    }

    public int getInhaleLength() {
        return this.entityData.get(INHALE_LENGTH);
    }

    public void setInhaleLength(int value) {
        this.entityData.set(INHALE_LENGTH, value);
    }

    public int getPushingTicks() {
        return this.entityData.get(PUSH_TICKS);
    }

    public void setPushTicks(int value) {
        this.entityData.set(PUSH_TICKS, value);
    }

    public int getPrevPushingTicks() {
        return this.entityData.get(PREV_PUSH_TICKS);
    }

    public void setPrevPushTicks(int value) {
        this.entityData.set(PREV_PUSH_TICKS, value);
    }


    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putInt("pushingTicks", this.getPushingTicks());
        nbt.putInt("prevPushingTicks", this.getPrevPushingTicks());
        nbt.putInt("inhalingTicks", this.getInhaleTicks());
        nbt.putInt("prevInTicks", this.getPrevInhaleTicks());
        nbt.putInt("inhaleLength", this.getInhaleLength());
        if (this.target != null) {
            nbt.putDouble("targx", this.target.x);
            nbt.putDouble("targy", this.target.y);
            nbt.putDouble("targz", this.target.z);
        } else {
            if (nbt.contains("targx")) {
                nbt.remove("targx");
            }
            if (nbt.contains("targy")) {
                nbt.remove("targy");
            }
            if (nbt.contains("targz")) {
                nbt.remove("targz");
            }
        }
        if (this.preparedMovement != null) {
            nbt.putDouble("preX", this.preparedMovement.x);
            nbt.putDouble("preY", this.preparedMovement.y);
            nbt.putDouble("preZ", this.preparedMovement.z);
        } else {
            if (nbt.contains("preX")) {
                nbt.remove("preX");
            }
            if (nbt.contains("preY")) {
                nbt.remove("preY");
            }
            if (nbt.contains("preZ")) {
                nbt.remove("preZ");
            }
        }
    }

    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.setPushTicks(nbt.getInt("pushingTicks"));
        this.setPrevPushTicks(nbt.getInt("prevPushingTicks"));
        this.setInhaleTicks(nbt.getInt("inhalingTicks"));
        this.setPrevInhaleTicks(nbt.getInt("prevInTicks"));
        this.setInhaleLength(nbt.getInt("inhaleLength"));
        if (nbt.contains("targx") && nbt.contains("targy") && nbt.contains("targz")) {
            this.target = new Vec3(nbt.getDouble("targx"), nbt.getDouble("targy"), nbt.getDouble("targz"));
        }
        if (nbt.contains("preX") && nbt.contains("preY") && nbt.contains("preZ")) {
            this.preparedMovement = new Vec3(nbt.getDouble("preX"), nbt.getDouble("preY"), nbt.getDouble("preZ"));
        }
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PUSH_TICKS, 0);
        this.entityData.define(PREV_PUSH_TICKS, 0);
        this.entityData.define(INHALE_TICKS, 0);
        this.entityData.define(PREV_INHALE_TICKS, 0);
        this.entityData.define(INHALE_LENGTH, 0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new Jellyfish.JellyUpDownGoal(this));
        this.goalSelector.addGoal(0, new Jellyfish.JellyRandomMovementGoal(this));
        this.goalSelector.addGoal(4, new Jellyfish.JellyToTargetGoal(this));
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose pose, EntityDimensions entityDimensions) {
        return entityDimensions.height * 0.5f;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return RegisterSounds.ENTITY_JELLYFISH_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return RegisterSounds.ENTITY_JELLYFISH_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return RegisterSounds.ENTITY_JELLYFISH_HURT;
    }

    protected SoundEvent getSquirtSound() {
        return RegisterSounds.ENTITY_WARDEN_SWIM;
    }

    @Override
    protected SoundEvent getSwimSound() {
        return RegisterSounds.ENTITY_JELLYFISH_SWIM;
    }

    @Override
    public boolean canBeLeashed(Player player) {
        return !this.isLeashed();
    }

    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }

    @Override
    protected Entity.MovementEmission getMovementEmission() {
        return Entity.MovementEmission.EVENTS;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.xBodyRotO = this.xBodyRot;
        this.zBodyRotO = this.zBodyRot;
        this.oldTentacleMovement = this.tentacleMovement;
        this.oldTentacleAngle = this.tentacleAngle;
        this.tentacleMovement += this.tentacleSpeed;
        if ((double)this.tentacleMovement > Math.PI * 2) {
            if (this.level.isClientSide) {
                this.tentacleMovement = (float)Math.PI * 2;
            } else {
                this.tentacleMovement -= (float)Math.PI * 2;
                if (this.random.nextInt(10) == 0) {
                    this.tentacleSpeed = 1.0f / (this.random.nextFloat() + 1.0f) * 0.2f;
                }
                this.level.broadcastEntityEvent(this, (byte)19);
            }
        }
        if (this.isInWaterOrBubble()) {
            if (this.tentacleMovement < (float)Math.PI) {
                float f = this.tentacleMovement / (float)Math.PI;
                this.tentacleAngle = Mth.sin(f * f * (float)Math.PI) * (float)Math.PI * 0.25f;
                if ((double)f > 0.75) {
                    this.speed = 1.0f;
                    this.rotateSpeed = 1.0f;
                } else {
                    this.rotateSpeed *= 0.8f;
                }
            } else {
                this.tentacleAngle = 0.0f;
                this.speed *= 0.9f;
                this.rotateSpeed *= 0.99f;
            }
            if (!this.level.isClientSide) {
                this.setDeltaMovement(this.tx * this.speed, this.ty * this.speed, this.tz * this.speed);
            }
            Vec3 vec3 = this.getDeltaMovement();
            double d = vec3.horizontalDistance();
            this.yBodyRot += (-((float)Mth.atan2(vec3.x, vec3.z)) * 57.295776f - this.yBodyRot) * 0.1f;
            this.setYRot(this.yBodyRot);
            this.zBodyRot += (float)Math.PI * this.rotateSpeed * 1.5f;
            this.xBodyRot += (-((float)Mth.atan2(d, vec3.y)) * 57.295776f - this.xBodyRot) * 0.1f;
        } else {
            this.tentacleAngle = Mth.abs(Mth.sin(this.tentacleMovement)) * (float)Math.PI * 0.25f;
            if (!this.level.isClientSide) {
                double e = this.getDeltaMovement().y;
                if (this.hasEffect(MobEffects.LEVITATION)) {
                    e = 0.05 * (double)(Objects.requireNonNull(this.getEffect(MobEffects.LEVITATION)).getAmplifier() + 1);
                } else if (!this.isNoGravity()) {
                    e -= 0.08;
                }
                this.setDeltaMovement(0.0, e * (double)0.98f, 0.0);
            }
            this.xBodyRot += (-90.0f - this.xBodyRot) * 0.02f;
        }
    }

    @Override
    public void tick() {
        super.tick();
        this.setPrevInhaleTicks(this.getInhaleTicks());
        this.setPrevPushTicks(this.getPushingTicks());
        if (this.getInhaleTicks() < this.getInhaleLength()) {
            this.setInhaleTicks(this.getInhaleTicks() + 1);
        } else if (this.canPush() && this.preparedMovement != null) {
            this.setPushTicks((int) (this.preparedMovement.length() * 2));
            this.setMovementVector((float) this.preparedMovement.x, (float) this.preparedMovement.y, (float) this.preparedMovement.z);
            this.preparedMovement = null;
        } else if (this.getPushingTicks() > 0) {
            this.setPushTicks(this.getPushingTicks() - 1);
        }
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.PUFFER_FISH_FLOP;
    }

    @Override
    public boolean hurt(DamageSource damageSource, float f) {
        if (super.hurt(damageSource, f) && this.getLastHurtByMob() != null) {
            if (!this.level.isClientSide) {
                this.spawnJelly();
                this.target = this.getLastHurtByMob().position();
            }
            return true;
        }
        return false;
    }

    private Vec3 rotateVector(Vec3 vec3) {
        Vec3 vec32 = vec3.xRot(this.xBodyRotO * ((float)Math.PI / 180));
        vec32 = vec32.yRot(-this.yBodyRotO * ((float)Math.PI / 180));
        return vec32;
    }

    private void spawnJelly() {
        this.playSound(this.getSquirtSound(), this.getSoundVolume(), this.getVoicePitch());
        Vec3 vec3 = this.rotateVector(new Vec3(0.0, -1.0, 0.0)).add(this.getX(), this.getY(), this.getZ());
        for (int i = 0; i < 30; ++i) {
            Vec3 vec32 = this.rotateVector(new Vec3((double)this.random.nextFloat() * 0.6 - 0.3, -1.0, (double)this.random.nextFloat() * 0.6 - 0.3));
            Vec3 vec33 = vec32.scale(0.3 + (double)(this.random.nextFloat() * 2.0f));
            ((ServerLevel)this.level).sendParticles(this.getJellyParticle(), vec3.x, vec3.y + 0.5, vec3.z, 0, vec33.x, vec33.y, vec33.z, 0.1f);
        }
    }

    protected ParticleOptions getJellyParticle() {
        return ParticleTypes.BUBBLE;
    }

    @Override
    public void travel(Vec3 vec3) {
        this.move(MoverType.SELF, this.getDeltaMovement());
    }

    @Override
    public void handleEntityEvent(byte b) {
        if (b == 19) {
            this.tentacleMovement = 0.0f;
        } else {
            super.handleEntityEvent(b);
        }
    }

    public void setMovementVector(float f, float g, float h) {
        this.tx = f;
        this.ty = g;
        this.tz = h;
    }

    public boolean hasMovementVector() {
        return this.tx != 0.0f || this.ty != 0.0f || this.tz != 0.0f;
    }

    @Override
    public ItemStack getBucketItemStack() {
        return null;
    }

    public boolean canPush() {
        return this.getInhaleTicks() > this.getInhaleLength();
    }

    static class JellyToTargetGoal extends Goal {
        private final Jellyfish jelly;

        public JellyToTargetGoal(Jellyfish jelly) {
            this.jelly = jelly;
        }

        @Override
        public boolean canUse() {
            return jelly.target != null;
        }

        @Override
        public void tick() {
            Vec3 target = this.jelly.target;
            if (target != null) {
                if (this.jelly.getPushingTicks() <= 0 && this.jelly.canPush()) {
                    float toX = (float) (Mth.clamp(target.x - this.jelly.position().x, -0.2, 0.2));
                    float toY = (float) (Mth.clamp(target.y - this.jelly.position().y, -0.05, 0.2));
                    float toZ = (float) (Mth.clamp(target.z - this.jelly.position().z, -0.2, 0.2));
                    this.jelly.preparedMovement = new Vec3(toX, toY, toZ);
                    this.jelly.setInhaleLength(15);
                }
            }
        }
    }

    static class JellyUpDownGoal extends Goal {
        private final Jellyfish jelly;

        public JellyUpDownGoal(Jellyfish jelly) {
            this.jelly = jelly;
        }

        @Override
        public boolean canUse() {
            return true;
        }

        @Override
        public void tick() {
            int i = this.jelly.getNoActionTime();
            if (i > 40) {
                boolean up = jelly.random.nextBoolean();
                this.jelly.setMovementVector(jelly.tx, (up ? 1 : -1) * (this.jelly.getRandom().nextFloat() * 0.1f), jelly.tz);
            }
        }
    }

    static class JellyRandomMovementGoal extends Goal {
        private final Jellyfish jelly;

        public JellyRandomMovementGoal(Jellyfish jelly) {
            this.jelly = jelly;
        }

        @Override
        public boolean canUse() {
            return true;
        }

        @Override
        public void tick() {
            int i = this.jelly.getNoActionTime();
            if (i < 40) {
                if (this.jelly.getRandom().nextInt(Jellyfish.JellyRandomMovementGoal.reducedTickDelay(50)) == 0 || !this.jelly.wasTouchingWater  || !this.jelly.hasMovementVector()) {
                    float f = this.jelly.getRandom().nextFloat() * ((float)Math.PI * 2);
                    float g = Mth.cos(f) * 0.1f;
                    float h = -0.1f + this.jelly.getRandom().nextFloat() * 0.2f;
                    float j = Mth.sin(f) * 0.1f;
                    this.jelly.setMovementVector(g, h, j);
                }
            }
        }
    }
}
